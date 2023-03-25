package org.hhoa.vi.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hhoa.vi.common.exception.Asserts;
import org.hhoa.vi.mgb.dao.UmsAccountDao;
import org.hhoa.vi.mgb.model.AccountOrganization;
import org.hhoa.vi.mgb.model.OrganizationPosition;
import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.hhoa.vi.mgb.model.generator.UmsAccountAuth;
import org.hhoa.vi.mgb.model.generator.UmsResource;
import org.hhoa.vi.mgb.model.generator.UmsRole;
import org.hhoa.vi.mgb.model.IdentifyType;
import org.hhoa.vi.mgb.model.MailType;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.portal.bean.UmsAccountAuthParam;
import org.hhoa.vi.portal.bean.UmsAccountDetails;
import org.hhoa.vi.portal.bean.UmsAccountRegisterParam;
import org.hhoa.vi.portal.bean.UmsAccountWrapper;
import org.hhoa.vi.portal.bean.UmsLoginParam;
import org.hhoa.vi.portal.bean.UmsUpdateAccountPasswordParam;
import org.hhoa.vi.mgb.dao.OmsAccountOrganizationDao;
import org.hhoa.vi.portal.service.OmsMailService;
import org.hhoa.vi.portal.service.UmsAccountAuthService;
import org.hhoa.vi.portal.service.UmsAccountCacheService;
import org.hhoa.vi.portal.service.UmsAccountService;
import org.hhoa.vi.security.util.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Ums account service.
 *
 * @author hhoa
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UmsAccountServiceImpl implements UmsAccountService {
    private final PasswordEncoder passwordEncoder;
    private final UmsAccountCacheService accountCacheService;

    private final OmsAccountOrganizationDao accountOrganizationDao;

    private final UmsAccountDao accountDao;
    private final UmsAccountAuthService accountAuthService;
    private final OmsMailService mailService;
    private JwtTokenService jwtTokenService;

    @Autowired
    @Lazy
    public void setJwtTokenService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public String login(UmsLoginParam loginParam) {
        String token = null;
        try {
            UmsAccount account = getAccountByAccountName(loginParam.getUsername());
            UmsAccountAuth accountAuth = accountAuthService.getAccountAuth(account.getId(), IdentifyType.username);
            if (!passwordEncoder.matches(loginParam.getPassword(), accountAuth.getCredential())) {
                Asserts.fail("密码错误");
            }
            if (!account.getStatus()) {
                Asserts.fail("用户已被冻结");
            }
            UmsAccountDetails accountDetails = getAccountDetails(accountAuth.getIdentifier());
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(accountDetails,
                            null, accountDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            accountCacheService.setKey(accountAuth.getIdentifier(), accountDetails);
            token = jwtTokenService.generateToken(accountAuth.getIdentifier());
            log.info(accountDetails.getUsername() + "登录成功");
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }

        return token;
    }

    @Override
    public void logout(String authorization) {
        String accountName = jwtTokenService.getSubjectFromAuthorization(authorization);
        clearAccountStatus(accountName);
    }

    private void clearAccountStatus(String accountName) {
        accountCacheService.delKey(accountName);
    }


    @Override
    public String refreshToken(String authorization) {
        String accountName = jwtTokenService.getSubjectFromAuthorization(authorization);
        boolean b = accountCacheService.hasKey(accountName);
        if (!b) {
            Asserts.fail("用户未登陆");
        }
        String tokenFromAuthorization = jwtTokenService.getTokenFromAuthorization(authorization);
        String retToken = jwtTokenService.refreshHeadToken(tokenFromAuthorization);
        if (retToken == null) {
            Asserts.fail("token已经过期");
        }
        return retToken;
    }

    @Override
    public UmsAccount getAccountByAccountNameUseAccountDetailsCache(String accountName) {
        UmsAccountDetails accountDetails = getAccountDetails(accountName);
        return accountDetails.getAccount();
    }


    @Override
    public List<UmsAccount> getAccounts(UmsAccount account) {
        QueryWrapper<UmsAccount> accountQueryWrapper = new QueryWrapper<>(account);
        return accountDao.selectList(accountQueryWrapper);
    }

    @Override
    public List<UmsAccount> list(PageInfo pageInfo, UmsAccount account) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        return getAccounts(account);
    }

    @Override
    public UmsAccountWrapper getAccountByAuthorization(String authorization) {
        String accountName = jwtTokenService.getSubjectFromAuthorization(authorization);
        Long accountIdByAccountName = accountAuthService.getAccountIdByAccountName(accountName);
        UmsAccount umsAccount = accountDao.selectById(accountIdByAccountName);
        UmsAccountWrapper accountWrapper = new UmsAccountWrapper();
        BeanUtil.copyProperties(umsAccount, accountWrapper);
        return accountWrapper;
    }

    @Override
    public List<AccountOrganization> getAccountOrganizations(String authorization) {
        String accountName = jwtTokenService.getSubjectFromAuthorization(authorization);
        return getAccountOrganizationsByAccountName(accountName);
    }

    private List<AccountOrganization> getAccountOrganizationsByAccountName(String accountName) {
        UmsAccount accountByAccountName = getAccountByAccountName(accountName);
        return accountOrganizationDao.getAccountOrganizations(accountByAccountName.getId());
    }


    private UmsAccountDetails getAccountDetailsNoCache(String accountName) {
        Long accountIdByAccountName = accountAuthService.getAccountIdByAccountName(accountName);
        UmsAccount account= accountDao.selectById(accountIdByAccountName);
        if (account== null) {
            Asserts.fail("没有该用户名");
        }
        List<AccountOrganization> accountOrganizationsByAccountName = getAccountOrganizationsByAccountName(accountName);
        List<OrganizationPosition> organizationPositions = accountOrganizationsByAccountName.stream().map(
                accountOrganization ->
                        new OrganizationPosition(
                                accountOrganization.getId(),
                                accountOrganization.getPositionId())).toList();
        return new UmsAccountDetails(account, organizationPositions);
    }


    private UmsAccount getAccountByAccountName(String name) {
        Long accountIdByAccountName = accountAuthService.getAccountIdByAccountName(name);
        return accountDao.selectById(accountIdByAccountName);
    }

    private List<AccountOrganization> getAccountOrganizationsByAccountNameUseAccountDetailsCache(String accountName) {
        UmsAccount accountByAccountName = getAccountByAccountName(accountName);
        return accountOrganizationDao.getAccountOrganizations(accountByAccountName.getId());
    }


    private void refreshAccountDetailsCache(String accountName) {
        UmsAccountDetails accountDetailsNoCache = getAccountDetailsNoCache(accountName);
        accountCacheService.setKey(accountName, accountDetailsNoCache);
    }

    @Override
    public UmsAccountDetails getAccountDetails(String username) {
        //使用了缓存
        UmsAccountDetails accountDetails = accountCacheService.getKey(username);
        if (accountDetails != null) {
            return accountDetails;
        }
        accountDetails = getAccountDetailsNoCache(username);
        refreshAccountDetailsCache(username);
        return accountDetails;
    }

    private Long registerDefaultAccount() {
        UmsAccount account = new UmsAccount();
        int insert = accountDao.insert(account);
        if (insert == 0) {
            Asserts.fail("注册失败");
        }
        return account.getId();
    }

    @Override
    public Long register(UmsAccountRegisterParam registerParam) {
        IdentifyType identifyType = registerParam.getIdentifyType();
        String identifier = registerParam.getIdentifier();
        if (StringUtils.hasLength(identifier) && accountAuthService.exists(identifyType, identifier)) {
            Asserts.fail("该认证方式已存在");
        }
        if (registerParam.getIdentifyType() == IdentifyType.email) {
            boolean b = mailService.validateMessage(registerParam.getIdentifier(), registerParam.getAuthCode(), MailType.USER_REGISTER);
            if (!b) {
                Asserts.fail("验证码错误");
            }
        } else {
            Asserts.fail("没有开通该认证方式");
            //...手机号注册没有开放
        }
        Long accountId = registerDefaultAccount();
        accountAuthService.bind(accountId, identifier, identifyType);
        return accountId;
    }


    @Override
    public void updateAccountPassword(UmsUpdateAccountPasswordParam passwordParam) {
        String identifier = passwordParam.getIdentifier();
        UmsAccountAuth usernameAuth = null;
        switch (passwordParam.getIdentifyType()) {
            case email -> {
                if (!accountAuthService.exists(IdentifyType.email, identifier)) {
                    Asserts.fail("该邮箱不存在");
                }
                if (!mailService.validateMessage(identifier, passwordParam.getAuthCode(), MailType.UPDATE_PASSWORD)) {
                    Asserts.fail("验证码错误");
                }
            }
            case phone -> {
                //TODO
            }
            case username -> {
                if (passwordParam.getNewPassword() != null) {
                    UmsAccountAuth accountAuth = accountAuthService.getAccountAuth(passwordParam.getIdentifier());
                    if (!accountAuth.getIdentityType().equals(passwordParam.getIdentifyType().value())) {
                        usernameAuth = accountAuthService.getAccountAuth(accountAuth.getAccountId(), IdentifyType.username);
                    } else {
                        usernameAuth = accountAuth;
                    }
                }
            }
            default -> Asserts.fail("没有该验证方式");
        }
        if (usernameAuth == null) {
            usernameAuth = accountAuthService.getAccountAuth(passwordParam.getIdentifyType(), identifier);
        }
        if (passwordParam.getOldPassword().equals(usernameAuth.getCredential())) {
            Asserts.fail("密码错误");
        } else {
            usernameAuth.setCredential(passwordParam.getNewPassword());
            accountAuthService.updateCredential(usernameAuth.getAccountId(), usernameAuth.getCredential());
        }
        //刷新用户登陆状态
        clearAccountStatus(usernameAuth.getIdentifier());
    }


    @Override
    public UmsAccount getAccountByUsername(String username) {
        UmsAccountDetails accountDetails = getAccountDetails(username);
        return accountDetails.getAccount();
    }


    @Override
    public void sendAccountRegisterMail(String mail) {
        mailService.sendUserRegisterMail(mail);
    }


    @Override
    public UmsAccountAuth getAccountEmailByUsername(String username) {
        UmsAccountAuth usernameAuth = accountAuthService.getAccountAuth(IdentifyType.username, username);
        return accountAuthService.getAccountAuth(usernameAuth.getAccountId(), IdentifyType.email);
    }

    private void updateAccount(UmsAccount newAccount) {
        int i = accountDao.updateById(newAccount);
        if (i == 0) {
            Asserts.fail("用户更新失败");
        }
        UmsAccountAuth accountAuth = accountAuthService.getAccountAuth(newAccount.getId(), IdentifyType.username);
        //刷新用户token，使用户需要重新登陆
        if (newAccount.getStatus() != null) {
            clearAccountStatus(accountAuth.getIdentifier());
        }
        refreshAccountDetailsCache(accountAuth.getIdentifier());
    }

    @Override
    public void updateAccount(UmsAccount newAccount, String authorization) {
        String username = jwtTokenService.getSubjectFromAuthorization(authorization);
        UmsAccountAuth accountAuth = accountAuthService.getAccountAuth(IdentifyType.username, username);
        newAccount.setId(accountAuth.getAccountId());
        updateAccount(newAccount);
    }

    @Override
    public void unbindAccountAuth(IdentifyType authType, String authorization) {
        String username = jwtTokenService.getSubjectFromAuthorization(authorization);
        Long accountId = accountAuthService.getAccountIdByAccountName(username);
        accountAuthService.deleteAccountAuth(accountId, authType);
        clearAccountStatus(username);
    }

    @Override
    public void updateUsername(String newUsername, String authorization) {
        String username = jwtTokenService.getSubjectFromAuthorization(authorization);
        UmsAccountAuth accountAuth = accountAuthService.getAccountAuth(IdentifyType.username, username);
        UmsAccountAuthParam accountAuthParam = new UmsAccountAuthParam();
        accountAuthParam.setIdentifier(newUsername);
        IdentifyType identifyType = Enum.valueOf(IdentifyType.class, accountAuth.getIdentityType());
        accountAuthService.updateAccountAuth(accountAuth.getAccountId(), identifyType, accountAuthParam);
        clearAccountStatus(username);
    }

    @Override
    public Map<String, String> getAccountAuths(Long accountId) {
        List<UmsAccountAuth> accountAuths = accountAuthService.getAccountAuth(accountId);
        Map<String, String> authMap = new HashMap<>();
        for (UmsAccountAuth accountAuth : accountAuths) {
            authMap.put(accountAuth.getIdentityType(), accountAuth.getIdentifier());
        }
        return authMap;
    }

    @Override
    public void bindEmail(String email, String authCode, String authorization) {
        String username = jwtTokenService.getSubjectFromAuthorization(authorization);
        if (!mailService.existMessage(email, MailType.BIND_EMAIL)) {
            Asserts.fail("没有该验证码");
        }
        UmsAccountAuth accountAuth = accountAuthService.getAccountAuth(username);
        accountAuthService.bind(accountAuth.getAccountId(), email, IdentifyType.email);
    }

    @Override
    public void bindPhone(String phone, String authCode, String authorization) {
        //TODO
    }

    @Override
    public void sendBindEmailCode(String email, String authorization) {
        if (accountAuthService.exists(IdentifyType.email, email)) {
            Asserts.fail("该邮箱已被使用");
        }
        String username = jwtTokenService.getSubjectFromAuthorization(authorization);
        UmsAccountAuth accountAuth = accountAuthService.getAccountAuth(username);
        if (accountAuthService.exists(accountAuth.getAccountId(), IdentifyType.email)) {
            Asserts.fail("该用户名已绑定邮箱");
        }
        mailService.sendBindMail(email);
    }

    @Override
    public Map<String, String> getVerifiedAccountAuths(String authorization) {
        String username = jwtTokenService.getSubjectFromAuthorization(authorization);
        Long accountIdByAccountName = accountAuthService.getAccountIdByAccountName(username);
        return getAccountAuths(accountIdByAccountName);
    }

}
