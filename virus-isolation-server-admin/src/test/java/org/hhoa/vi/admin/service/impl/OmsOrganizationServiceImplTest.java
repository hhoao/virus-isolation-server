package org.hhoa.vi.admin.service.impl;

import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.bean.UmsAccountAuthParam;
import org.hhoa.vi.admin.service.OmsOrganizationService;
import org.hhoa.vi.admin.utils.ServiceTransactionTest;
import org.hhoa.vi.mgb.model.OrganizationAccount;
import org.hhoa.vi.mgb.model.generator.OmsOrganization;
import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Stream;


/**
 * OmsOrganizationServiceImplTest
 *
 * @author hhoa
 * @since 2023/3/19
 **/

class OmsOrganizationServiceImplTest extends ServiceTransactionTest {
    @Autowired
    private OmsOrganizationService organizationService;

    @Test
    void list() {
        OmsOrganization amsOrganization = new OmsOrganization();
        amsOrganization.setName("test_organization");
        List<OmsOrganization> list = organizationService.list(
                amsOrganization, new PageInfo(1, 0));
        Assertions.assertTrue(list.size() > 0);
        list.forEach(omsOrganization -> {
            Assertions.assertEquals(omsOrganization.getName(), "test_organization");
        });
    }

    @Test
    void addOrganization() {
        OmsOrganization amsOrganization = new OmsOrganization();
        Assertions.assertThrows(Exception.class, () -> organizationService.addOrganization(amsOrganization));
        amsOrganization.setName("testOrganization1");
        amsOrganization.setEmail("test1@test.com");
        amsOrganization.setPhone("1234567890");
        organizationService.addOrganization(amsOrganization);
        List<OmsOrganization> list = organizationService.list(null, new PageInfo(1, 0));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    void deleteOrganization() {
        OmsOrganization amsOrganization = new OmsOrganization();
        amsOrganization.setName("testOrganization1");
        amsOrganization.setEmail("test1@test.com");
        amsOrganization.setPhone("1234567890");
        organizationService.addOrganization(amsOrganization);
        List<OmsOrganization> list = organizationService.list(null, new PageInfo(1, 0));
        OmsOrganization amsOrganization1 = list.get(0);
        organizationService.deleteOrganization(amsOrganization1.getId());
        Assertions.assertThrows(Exception.class, () -> organizationService.addOrganization(new OmsOrganization()));
    }

    @Test
    void updateOrganization() {
        OmsOrganization omsOrganization = new OmsOrganization();
        omsOrganization.setId(1L);
        omsOrganization.setEmail("test1@test1.com");
        organizationService.updateOrganization(omsOrganization);
        List<OmsOrganization> list = organizationService.list(omsOrganization, new PageInfo(1, 5));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    void listOrganizationAccounts() {
        UmsAccount account=  new UmsAccount();
        account.setId(1L);
        List<OrganizationAccount> umsAccounts =
                organizationService.listOrganizationAccounts(
                        new PageInfo(1, 5), 1L, account);
        Assertions.assertTrue(umsAccounts.size() > 0);
        umsAccounts.forEach(organizationAccount -> {
            Assertions.assertSame(organizationAccount.getId(), account.getId());
        });
        Assertions.assertFalse(
                organizationService.listOrganizationAccounts(
                        new PageInfo(
                                1, 0), 0L, null).size() > 0);
    }

    @Test
    void deleteOrganizationAccountByUserId() {
        organizationService.deleteOrganizationAccountByUserId(1L, 1L);
        Assertions.assertEquals(
                0, organizationService.listOrganizationAccounts(
                        new PageInfo(1, 5), 1L, null).size());
    }

    @Test
    void updateOrganizationAccountPosition() {
        organizationService.updateOrganizationAccountPosition(1L, 1L, 2L);
        List<OrganizationAccount> organizationAccounts =
                organizationService.listOrganizationAccounts(
                        new PageInfo(1, 5), 1L, null);
        Stream<OrganizationAccount> organizationAccountStream =
                organizationAccounts.stream().filter(
                        (account) -> account.getPositionId() == 2);
        Assertions.assertTrue(organizationAccountStream.findAny().isPresent());
    }

    @Test
    void deleteByAccountIdAndOrganizationId() {
        List<OrganizationAccount> organizationAccounts =
                organizationService.listOrganizationAccounts(
                        new PageInfo(1, 5), 1L, null);
        organizationService.deleteByAccountIdAndOrganizationId(
                organizationAccounts.get(0).getId(), 1L);
        OmsOrganization omsOrganization = new OmsOrganization();
        omsOrganization.setId(1L);
        List<OmsOrganization> list =
                organizationService.list(
                        omsOrganization, new PageInfo(1, 0));
        Assertions.assertEquals(0, list.size());
    }
}
