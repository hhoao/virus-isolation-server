package org.hhoa.vi.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hhoa.vi.common.exception.Asserts;
import org.hhoa.vi.mgb.model.UmsAccount;
import org.hhoa.vi.mgb.model.UmsResource;
import org.hhoa.vi.mgb.model.UmsRole;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.portal.bean.UmsAccountDetails;
import org.hhoa.vi.portal.bean.UmsAccountWrapper;
import org.hhoa.vi.portal.bean.UmsLoginParam;
import org.hhoa.vi.portal.dao.UmsAccountDao;
import org.hhoa.vi.portal.service.UmsAccountCacheService;
import org.hhoa.vi.portal.service.UmsAccountService;
import org.hhoa.vi.portal.service.UmsRoleService;
import org.hhoa.vi.security.util.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private JwtTokenService jwtTokenService;
    private final UmsRoleService roleService;

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
            if (!passwordEncoder.matches(loginParam.getPassword(), account.getPassword())) {
                Asserts.fail("密码错误");
            }
            if (!account.getStatus()) {
                Asserts.fail("用户已被冻结");
            }
            UmsAccountDetails accountDetails = getAccountDetails(account.getUsername());
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(accountDetails,
                            null, accountDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            accountCacheService.setKey(account.getUsername(), accountDetails);
            token = jwtTokenService.generateToken(account.getUsername());
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
        QueryWrapper<UmsAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", accountName);
        List<UmsAccount> umsAccounts = accountDao.selectList(queryWrapper);
        if (umsAccounts.size() == 0) {
            Asserts.fail("没有该用户名");
        }
        UmsAccount umsAccount = accountDao.selectById(umsAccounts.get(0));
        UmsAccount account = accountDao.selectById(umsAccount.getId());
        List<UmsResource> accountResources = getAccountResources(account.getId());
        return new UmsAccountDetails(account, accountResources);
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
    public UmsAccount getAccountByAccountName(String accountName) {
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
    public List<UmsResource> getAccountResources(Long accountId) {
        UmsAccount account = accountDao.selectById(accountId);
        return roleService.getRoleResources(account.getRoleId());
    }

    @Override
    public UmsAccountWrapper getAccountByAuthorization(String authorization) {
        String accountName = jwtTokenService.getSubjectFromAuthorization(authorization);
        UmsAccount account = new UmsAccount();
        account.setUsername(accountName);
        UmsAccount umsAccount = accountDao.selectOne(new QueryWrapper<>(account));
        UmsRole role = roleService.getRole(umsAccount.getRoleId());
        UmsAccountWrapper accountWrapper = new UmsAccountWrapper();
        BeanUtil.copyProperties(umsAccount, accountWrapper);
        accountWrapper.setRole(role);
        return accountWrapper;
    }
}
