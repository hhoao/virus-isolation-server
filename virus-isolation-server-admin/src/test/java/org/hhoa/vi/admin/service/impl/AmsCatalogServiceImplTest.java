package org.hhoa.vi.admin.service.impl;


import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.service.AmsCatalogService;
import org.hhoa.vi.admin.utils.ServiceTransactionTest;
import org.hhoa.vi.mgb.model.generator.AmsCatalog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * AmsCatalogServiceTest.
 *
 * @author hhoa
 * @since 2022/12/20
 **/

class AmsCatalogServiceImplTest extends ServiceTransactionTest {
    @Autowired
    AmsCatalogService catalogService;

    @Test
    void selectList() {
        AmsCatalog amsCatalog = new AmsCatalog();
        amsCatalog.setName("testName");
        catalogService.addCatalog(amsCatalog);
        List<AmsCatalog> list = catalogService.selectList(
                null, new PageInfo(1, 0));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    void addCatalog() {
        AmsCatalog amsCatalog = new AmsCatalog();
        Assertions.assertThrows(Exception.class, () -> catalogService.addCatalog(amsCatalog));
        amsCatalog.setName("testName");
        catalogService.addCatalog(amsCatalog);
        List<AmsCatalog> list = catalogService.selectList(
                null, new PageInfo(1, 0));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    void deleteCatalog() {
        AmsCatalog amsCatalog = new AmsCatalog();
        amsCatalog.setName("testName");
        catalogService.addCatalog(amsCatalog);
        List<AmsCatalog> list = catalogService.selectList(
                null, new PageInfo(1, 0));
        AmsCatalog amsCatalog1 = list.get(0);
        catalogService.deleteCatalog(amsCatalog1.getId());
    }

    @Test
    void updateCatalog() {
    }


    @Test
    void getCatalogArticles() {
    }
}
