package org.hhoa.vi.admin.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.hhoa.vi.admin.utils.ControllerTransactionTest;
import org.hhoa.vi.admin.utils.TestUtils;
import org.hhoa.vi.admin.utils.ServiceTransactionTest;
import org.hhoa.vi.common.api.CommonPage;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.mgb.model.OrganizationAccount;
import org.hhoa.vi.mgb.model.generator.OmsOrganization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * OmsOrganizationControllerTest
 *
 * @author hhoa
 * @since 2023/3/23
 **/

class OmsOrganizationControllerTestService extends ControllerTransactionTest {

    @Test
    void list() {
        String forObject = getAdminVerifiedTemplate().getForObject("/organizations", String.class);
        CommonResult<CommonPage<OmsOrganization>> omsOrganizationCommonResult =
                TestUtils.jsonStringToObject(
                        forObject, new TypeReference<CommonResult<CommonPage<OmsOrganization>>>() {
                        });
        List<OmsOrganization> list = omsOrganizationCommonResult.getResult().getList();
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    void addOrganization() {
        //添加组织
        OmsOrganization organization = new OmsOrganization();
        organization.setName("testAddOrganization");
        organization.setEmail("testAddOrganization@testAddOrganization.com");
        organization.setPhone("12345869014");
        CommonResult<?> commonResult = getAdminVerifiedTemplate().
                postForObject("/organizations", organization, CommonResult.class);
        System.out.println(commonResult.getCode());
        Assertions.assertEquals(commonResult.getCode(), 200);
        //查找添加的组织
        String parametersString = TestUtils.ObjectToParametersString(organization);
        String getOrganization = getAdminVerifiedTemplate().
                getForObject(
                        "/organizations?" + parametersString, String.class);
        CommonResult<CommonPage<OmsOrganization>> omsOrganizationCommonResult =
                TestUtils.jsonStringToObject(getOrganization,
                        new TypeReference<CommonResult<CommonPage<OmsOrganization>>>() {
                        });
        List<OmsOrganization> list = omsOrganizationCommonResult.getResult().getList();
        Assertions.assertTrue(list.size() > 0);
        list.forEach(omsOrganization -> {
            Assertions.assertEquals(organization.getName(), omsOrganization.getName());
        });
    }

    @Test
    @SneakyThrows
    void updateOrganization() {
        //更新组织
        OmsOrganization organization = new OmsOrganization();
        organization.setId(1L);
        organization.setName("testAddOrganization");
        organization.setEmail("testAddOrganization@testAddOrganization.com");
        organization.setPhone("12345869014");
        getAdminVerifiedTemplate().
                put("/organizations", organization);
        //查找更新的组织
        String parametersString = TestUtils.ObjectToParametersString(organization);
        String getOrganization = getAdminVerifiedTemplate().
                getForObject(
                        "/organizations?" + parametersString, String.class);
        CommonResult<CommonPage<OmsOrganization>> omsOrganizationCommonResult =
                TestUtils.jsonStringToObject(getOrganization,
                        new TypeReference<CommonResult<CommonPage<OmsOrganization>>>() {
                        });
        List<OmsOrganization> list = omsOrganizationCommonResult.getResult().getList();
        Assertions.assertTrue(list.size() > 0);
        list.forEach(omsOrganization -> {
            Assertions.assertEquals(organization.getName() + organization.getId() + organization.getEmail(),
                    omsOrganization.getName() + organization.getId() + organization.getEmail());
        });
    }

    @Test
    void delOrganization() {
        //删除组织
        getAdminVerifiedTemplate().
                delete("/organizations/{organizationId}",
                        Map.of("organizationId", 1L));
        //查找删除的组织
        OmsOrganization organization = new OmsOrganization();
        organization.setId(1L);
        String parametersString = TestUtils.ObjectToParametersString(organization);
        String getOrganization = getAdminVerifiedTemplate().
                getForObject(
                        "/organizations?" + parametersString, String.class);
        CommonResult<CommonPage<OmsOrganization>> omsOrganizationCommonResult =
                TestUtils.jsonStringToObject(getOrganization,
                        new TypeReference<CommonResult<CommonPage<OmsOrganization>>>() {
                        });
        List<OmsOrganization> list = omsOrganizationCommonResult.getResult().getList();
        assertEquals(0, list.size());
    }

    List<OrganizationAccount> listOrganizationAccounts(Long organizationId, OrganizationAccount parameters) {
        String parametersString = "";
        if (parameters != null) {
            parametersString = TestUtils.ObjectToParametersString(parameters);
        }
        String accountsResult = getAdminVerifiedTemplate().
                getForObject("/organizations/{organizationId}/accounts?" +
                                parametersString, String.class,
                        Map.of("organizationId", organizationId));
        CommonResult<CommonPage<OrganizationAccount>> omsOrganizationCommonResult =
                TestUtils.jsonStringToObject(accountsResult,
                        new TypeReference<CommonResult<CommonPage<OrganizationAccount>>>() {
                        });

        return omsOrganizationCommonResult.getResult().getList();
    }

    @Test
    void listOrganizationAccountsTest() {
        //获取组织成员
        OrganizationAccount account = new OrganizationAccount();
        account.setId(1L);
        List<OrganizationAccount> organizationAccounts = listOrganizationAccounts(1L, account);
        Assertions.assertTrue(organizationAccounts.size() > 0);
        organizationAccounts.forEach(organizationAccount -> {
            Assertions.assertSame(organizationAccount.getId(), account.getId());
        });

        account.setId(2L);
        organizationAccounts = listOrganizationAccounts(1L, account);
        assertEquals(0, organizationAccounts.size());
    }

    void deleteOrganizationAccount(Long organizationId, Long accountId) {
        getAdminVerifiedTemplate().
                delete("/organizations/{organizationId}/accounts/{accountId}",
                        Map.of("organizationId", organizationId, "accountId", accountId));

    }

    @ParameterizedTest
    @CsvSource({"1, 1, 0"})
    public void deleteOrganizationAccountTest(Long organizationId, Long accountId, int expected) {
        deleteOrganizationAccount(organizationId, accountId);
        List<OrganizationAccount> organizationAccounts = listOrganizationAccounts(organizationId, null);
        assertEquals(expected, organizationAccounts.size());
    }

    @Test
    void updateAccountJob() {
    }
}
