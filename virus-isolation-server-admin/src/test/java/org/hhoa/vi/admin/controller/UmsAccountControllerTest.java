package org.hhoa.vi.admin.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.hhoa.vi.admin.bean.UmsAccountWrapper;
import org.hhoa.vi.admin.utils.TestUtils;
import org.hhoa.vi.admin.utils.ServiceTransactionTest;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * UmsAccountControllerTest
 *
 * @author hhoa
 * @since 2023/3/22
 **/

class UmsAccountControllerTest extends ServiceTransactionTest {

    @Test
    void getAccount() {
        String account = getAdminVerifiedTemplate().getForObject("/account", String.class);
        CommonResult<UmsAccountWrapper> commonResult = TestUtils.jsonStringToObject(account, new TypeReference<CommonResult<UmsAccountWrapper>>(){});
        UmsAccount result = commonResult.getResult();
        Assertions.assertEquals(result.getId(), 1L);
    }

    @Test
    void addAccount() {
    }

    @Test
    void login() {
    }

    @Test
    void logout() {
    }

    @Test
    void refreshToken() {
    }

    @Test
    void getAccountByAccountParam() {
    }

    @Test
    void updateAccount() {
    }

    @Test
    void deleteAccount() {
    }
}
