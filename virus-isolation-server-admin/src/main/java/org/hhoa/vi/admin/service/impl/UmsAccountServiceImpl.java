package org.hhoa.vi.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hhoa.vi.admin.bean.AccountAuthWrapper;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.bean.UmsAccountAuthParam;
import org.hhoa.vi.admin.bean.UmsAccountDetails;
import org.hhoa.vi.admin.bean.UmsAccountWrapper;
import org.hhoa.vi.admin.bean.UmsLoginParam;
import org.hhoa.vi.admin.service.UmsAccountAuthService;
import org.hhoa.vi.admin.service.UmsAccountCacheService;
import org.hhoa.vi.admin.service.UmsAccountService;
import org.hhoa.vi.admin.service.UmsRoleService;
import org.hhoa.vi.common.exception.Asserts;
import org.hhoa.vi.mgb.dao.OmsAccountOrganizationDao;
import org.hhoa.vi.mgb.dao.UmsAccountDao;
import org.hhoa.vi.mgb.model.AccountOrganization;
import org.hhoa.vi.mgb.model.IdentifyType;
import org.hhoa.vi.mgb.model.OrganizationPosition;
import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.hhoa.vi.mgb.model.generator.UmsAccountAuth;
import org.hhoa.vi.mgb.model.generator.UmsResource;
import org.hhoa.vi.mgb.model.generator.UmsRole;
import org.hhoa.vi.security.util.JwtTokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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

    private final UmsAccountDao accountDao;
    private final UmsAccountAuthService accountAuthService;

    private final JwtTokenService jwtTokenService;
    private final UmsRoleService roleService;
    private final OmsAccountOrganizationDao accountOrganizationDao;

    @Override
    public String login(UmsLoginParam loginParam) {
        String token = null;
        try {
            UmsAccount account = getAccountByAccountNameUseAccountDetailsCache(loginParam.getUsername());
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

    private UmsAccountDetails getAccountDetailsNoCache(String accountName) {
        Long accountIdByAccountName = accountAuthService.getAccountIdByAccountName(accountName);
        UmsAccount umsAccount1 = accountDao.selectById(accountIdByAccountName);
        if (umsAccount1 == null) {
            Asserts.fail("没有该用户名");
        }
        UmsAccount umsAccount = accountDao.selectById(umsAccount1);
        UmsAccount account = accountDao.selectById(umsAccount.getId());
        List<UmsResource> accountResources = getAccountResources(account.getId());
        List<AccountOrganization> accountOrganizationsByAccountName = getAccountOrganizationsByAccountName(accountName);
        List<OrganizationPosition> organizationPositions = accountOrganizationsByAccountName.stream().map(
                accountOrganization ->
                        new OrganizationPosition(
                                accountOrganization.getId(),
                                accountOrganization.getPositionId())).toList();
        return new UmsAccountDetails(account, accountResources, organizationPositions);
    }

    private List<AccountOrganization> getAccountOrganizationsByAccountNameUseAccountDetailsCache(String accountName) {
        UmsAccount accountByAccountName = getAccountByAccountNameUseAccountDetailsCache(accountName);
        return accountOrganizationDao.getAccountOrganizations(accountByAccountName.getId());
    }

    private UmsAccount getAccountByAccountName(String name) {
        Long accountIdByAccountName = accountAuthService.getAccountIdByAccountName(name);
        return getAccounts(accountIdByAccountName);
    }

    private List<AccountOrganization> getAccountOrganizationsByAccountName(String accountName) {
        UmsAccount accountByAccountName = getAccountByAccountName(accountName);
        return accountOrganizationDao.getAccountOrganizations(accountByAccountName.getId());
    }

    private void refreshAccountDetailsCache(String accountName) {
        UmsAccountDetails accountDetailsNoCache = getAccountDetailsNoCache(accountName);
        accountCacheService.setKey(accountName, accountDetailsNoCache);
    }

    @Override
    public UmsAccountDetails getAccountDetails(String accountName) {
        //使用了缓存
        UmsAccountDetails accountDetails = accountCacheService.getKey(accountName);
        if (accountDetails != null) {
            return accountDetails;
        }
        accountDetails = getAccountDetailsNoCache(accountName);
        refreshAccountDetailsCache(accountName);
        return accountDetails;
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
    public void updateAccount(AccountAuthWrapper newAccount) {
        int i = accountDao.updateById(newAccount.getAccountInfo());
        if (i == 0) {
            Asserts.fail("用户更新失败");
        }
        UmsAccountAuth accountAuth = accountAuthService.getAccountAuth(
                newAccount.getAccountInfo().getId(), IdentifyType.username);
        if (newAccount.getIdentifier() != null && newAccount.getIdentityType() != null) {
            UmsAccountAuthParam umsAccountAuthParam = new UmsAccountAuthParam();
            umsAccountAuthParam.setIdentifier(newAccount.getIdentifier());
            accountAuthService.updateAccountAuth(newAccount.getAccountInfo().getId(),
                    IdentifyType.get(newAccount.getIdentityType()), umsAccountAuthParam);
            clearAccountStatus(accountAuth.getIdentifier());
            //刷新用户token，使用户需要重新登陆
        } else if (newAccount.getAccountInfo().getStatus() != null ||
                newAccount.getAccountInfo().getRoleId() != null
        ) {
            clearAccountStatus(accountAuth.getIdentifier());
        }
        refreshAccountDetailsCache(accountAuth.getIdentifier());
    }

    @Override
    public List<UmsAccount> getAccount(UmsAccount account) {
        QueryWrapper<UmsAccount> accountQueryWrapper = new QueryWrapper<>(account);
        return accountDao.selectList(accountQueryWrapper);
    }

    @Override
    public UmsAccount getAccounts(Long accountId) {
        UmsAccount account = accountDao.selectById(accountId);
        if (account == null) {
            Asserts.fail("没有该用户");
        }
        return account;
    }


    @Override
    public void deleteAccountByAccountId(Long accountId) {
        //删除Auth
        UmsAccountAuth accountAuth = accountAuthService.getAccountAuth(accountId, IdentifyType.username);
        accountAuthService.deleteAllAccountAuth(accountId);
        clearAccountStatus(accountAuth.getIdentifier());
        //删除Account
        int i = accountDao.deleteById(accountId);
        if (i == 0) {
            Asserts.fail("删除失败");
        }

    }

    @Override
    public void deleteAccounts(UmsAccount account) {
        List<UmsAccount> accounts = getAccount(account);
        accounts.forEach((queryAccount) -> {
            accountAuthService.deleteAllAccountAuth(queryAccount.getId());
        });
        for (UmsAccount delAccount : accounts) {
            accountDao.deleteById(delAccount.getId());
        }
    }


    @Override
    public List<UmsAccount> list(PageInfo pageInfo, UmsAccount account) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        return getAccount(account);
    }

    @Override
    public List<UmsResource> getAccountResources(Long accountId) {
        UmsAccount account = accountDao.selectById(accountId);
        return roleService.getRoleResourcesUseRoleCache(account.getRoleId());
    }

    @Override
    public UmsAccountWrapper getAccountByAuthorization(String authorization) {
        String accountName = jwtTokenService.getSubjectFromAuthorization(authorization);
        Long accountIdByAccountName = accountAuthService.getAccountIdByAccountName(accountName);
        UmsAccount umsAccount = accountDao.selectById(accountIdByAccountName);
        UmsRole role = roleService.getRole(umsAccount.getRoleId());
        UmsAccountWrapper accountWrapper = new UmsAccountWrapper();
        BeanUtil.copyProperties(umsAccount, accountWrapper);
        accountWrapper.setRole(role);
        return accountWrapper;
    }

    @Override
    public void addAccount(AccountAuthWrapper accountAuthWrapper) {
        if (!StringUtils.hasLength(accountAuthWrapper.getIdentifier())) {
            Asserts.fail("请您输入用户名");
        }

        long i = accountDao.insert(accountAuthWrapper.getAccountInfo());
        accountAuthService.bind(i, accountAuthWrapper.getIdentifier(), IdentifyType.get(accountAuthWrapper.getIdentityType()));
        if (i == 0) {
            Asserts.fail("插入失败");
        }
    }
}
