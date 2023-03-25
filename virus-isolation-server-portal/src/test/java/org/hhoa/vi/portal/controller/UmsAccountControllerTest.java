package org.hhoa.vi.portal.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.mgb.model.AccountOrganization;
import org.hhoa.vi.portal.bean.UmsAccountWrapper;
import org.hhoa.vi.portal.utils.TestUtils;
import org.hhoa.vi.portal.utils.TransactionTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * UmsAccountControllerTest
 *
 * @author hhoa
 * @since 2023/3/20
 **/


class UmsAccountControllerTest extends TransactionTest {

    @Test
    void getAccount() {
        ResponseEntity<String> forEntity =
                getTestVerifiedTemplate().getForEntity("/account", String.class);
        CommonResult<UmsAccountWrapper> commonResult =
                TestUtils.jsonStringToObject(
                        forEntity.getBody(), new TypeReference<CommonResult<UmsAccountWrapper>>() {
                        });
        Assertions.assertEquals("test1", commonResult.getResult().getNickname());
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
    void getAccountOrganizations() {
        TestRestTemplate adminVerifiedTemplate = getTestVerifiedTemplate();
        ResponseEntity<String> forEntity = adminVerifiedTemplate.getForEntity("/account/organizations", String.class);
        CommonResult<List<AccountOrganization>> stringCommonResult =
                TestUtils.jsonStringToObject(forEntity.getBody(),
                        new TypeReference<CommonResult<List<AccountOrganization>>>() {
        });
        List<AccountOrganization> result = stringCommonResult.getResult();
        Assertions.assertEquals(result.get(0).getName(), "test_organization");
    }
}
