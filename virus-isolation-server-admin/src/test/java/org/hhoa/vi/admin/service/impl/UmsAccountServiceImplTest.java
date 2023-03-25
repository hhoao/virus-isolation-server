package org.hhoa.vi.admin.service.impl;


import org.hhoa.vi.admin.bean.AccountAuthWrapper;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.bean.UmsAccountDetails;
import org.hhoa.vi.admin.bean.UmsLoginParam;
import org.hhoa.vi.admin.service.UmsAccountService;
import org.hhoa.vi.admin.utils.ServiceTransactionTest;
import org.hhoa.vi.common.exception.ApiException;
import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.hhoa.vi.mgb.model.generator.UmsResource;
import org.hhoa.vi.security.util.JwtTokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * UmsAccountServiceImplTest.
 *
 * @author hhoa
 * @since 2022/5/31
 **/

class UmsAccountServiceImplTest extends ServiceTransactionTest {
    @Autowired
    UmsAccountService accountService;
    @Autowired
    JwtTokenService jwtTokenService;
    String testPassword = "123456";
    String testAccountName = "test1";
    String testErrorIdentifier = "error";

    @Test
    void logout() {
        UmsLoginParam umsLoginParam = new UmsLoginParam();
        umsLoginParam.setPassword(testPassword);
        umsLoginParam.setUsername(testAccountName);
        String login = accountService.login(umsLoginParam);
        accountService.logout(jwtTokenService.getTokenHead() + login);
    }

    @Test
    void getAccountDetails() {
        UserDetails test = accountService.getAccountDetails(testAccountName);
        Assertions.assertInstanceOf(UmsAccountDetails.class, test);
        Assertions.assertThrows(ApiException.class,
                () -> accountService.getAccountDetails(testErrorIdentifier));
    }

    public String getAuthorization() {
        UmsLoginParam umsLoginParam = new UmsLoginParam();
        umsLoginParam.setPassword("123456");
        umsLoginParam.setUsername("test0");
        String token = accountService.login(umsLoginParam);
        return jwtTokenService.getTokenHead() + token;
    }

    @Test
    void refreshToken() {
        String authorization = getAuthorization();
        accountService.refreshToken(authorization);
    }


    @Test
    void updateAccount() {
        AccountAuthWrapper accountAuthWrapper = new AccountAuthWrapper();
        UmsAccount account = new UmsAccount();
        account.setId(2L);
        account.setNickname("updateNickName");
        accountAuthWrapper.setAccountInfo(account);
        accountService.updateAccount(accountAuthWrapper);
        Assertions.assertTrue(accountService.getAccount(account).size() >0);
    }

    @Test
    void deleteAccountByAccountId() {
        accountService.deleteAccountByAccountId(1L);
        Assertions.assertThrows(Exception.class,
                () -> accountService.getAccountByAccountNameUseAccountDetailsCache("admin"));
    }

    @Test
    void getAccount() {
        accountService.getAccounts(1L);
        UmsAccount account = new UmsAccount();
        account.setIntroduce("Hello");
        List<UmsAccount> account1 = accountService.getAccount(account);
        for (UmsAccount account2 : account1) {
            Assertions.assertEquals(account2.getIntroduce(), "HelloWorld");
        }
    }

    @Test
    void list() {
        List<UmsAccount> list = accountService.list(new PageInfo(1, 5), new UmsAccount());
        Assertions.assertTrue(list.size() <= 5);
        list = accountService.list(new PageInfo(1, 5), null);
        Assertions.assertTrue(list.size() <= 5);
    }

    @Test
    void getAccountByName() {
        UmsAccount accountByName = accountService.getAccountByAccountNameUseAccountDetailsCache(testAccountName);
        Assertions.assertNotNull(accountByName);
    }

    @Test
    void getAccountResources() {
        List<UmsResource> accountResources = accountService.getAccountResources(1L);
        Assertions.assertTrue(accountResources.size() > 0);
    }

    @Test
    void deleteAccounts() {
        UmsAccount account = new UmsAccount();
        account.setRoleId(3L);
        accountService.deleteAccounts(account);
        List<UmsAccount> account1 = accountService.getAccount(account);
        Assertions.assertEquals(account1.size(), 0);
    }
}
