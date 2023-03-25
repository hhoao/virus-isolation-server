package org.hhoa.vi.portal.service.impl;

import org.hhoa.vi.common.exception.ApiException;
import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.hhoa.vi.mgb.model.generator.UmsResource;
import org.hhoa.vi.portal.utils.TransactionTest;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.portal.bean.UmsAccountDetails;
import org.hhoa.vi.portal.bean.UmsLoginParam;
import org.hhoa.vi.portal.service.UmsAccountService;
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

class UmsAccountServiceImplTest extends TransactionTest {
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
        umsLoginParam.setUsername("test1");
        String token = accountService.login(umsLoginParam);
        return jwtTokenService.getTokenHead() + token;
    }

    @Test
    void refreshToken() {
        String authorization = getAuthorization();
        accountService.refreshToken(authorization);
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
        Assertions.assertEquals(accountByName.getId(), 3L);
    }
}
