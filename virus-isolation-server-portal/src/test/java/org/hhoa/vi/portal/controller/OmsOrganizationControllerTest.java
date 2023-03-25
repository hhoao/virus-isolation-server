package org.hhoa.vi.portal.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.hhoa.vi.common.api.CommonPage;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.mgb.model.OrganizationAccount;
import org.hhoa.vi.mgb.model.generator.OmsOrganization;
import org.hhoa.vi.portal.utils.TestUtils;
import org.hhoa.vi.portal.utils.TransactionTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * OmsOrganizationControllerTest
 *
 * @author hhoa
 * @since 2023/3/21
 **/

class OmsOrganizationControllerTest extends TransactionTest {

    @Test
    void addOrganization() {

    }

    @Test
    void updateOrganization() {
    }

    @Test
    void delOrganization() {
    }

    @Test
    void listAccounts() {
        ResponseEntity<String> forEntity =
                getGuestVerifiedTemplate().getForEntity(
                        "/organizations/2/accounts", String.class);
        CommonResult<CommonPage<OrganizationAccount>> commonPageCommonResult =
                TestUtils.jsonStringToObject(forEntity.getBody(),
                        new TypeReference<CommonResult<CommonPage<OrganizationAccount>>>() {
        });
        CommonPage<OrganizationAccount> result = commonPageCommonResult.getResult();
        List<OrganizationAccount> list = result.getList();
        Assertions.assertTrue(list.size() > 0);
//        //测试没有权限
        forEntity = getGuestVerifiedTemplate().getForEntity("/organizations/1/accounts", String.class);
        CommonResult<String> objectCommonResult = TestUtils.jsonStringToObject(forEntity.getBody(), new TypeReference<CommonResult<String>>() {
        });
        assertEquals(403, objectCommonResult.getCode());
    }

    @Test
    void deleteAccount() {
    }

    @Test
    void updateAccountJob() {
    }
}
