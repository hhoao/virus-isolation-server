package org.hhoa.vi.portal.impl;

import org.hhoa.vi.mgb.model.generator.UmsResource;
import org.hhoa.vi.portal.TransactionTest;
import org.hhoa.vi.portal.service.UmsResourceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

/**
 * UmsResourceServiceImplTest.
 *
 * @author hhoa
 * @since 2022/6/4
 **/

class UmsResourceServiceImplTest extends TransactionTest {
    @Autowired
    UmsResourceService resourceService;
    @Autowired
    ConfigurableApplicationContext applicationContext;

    @Test
    void getResource() {
    }

    @Test
    void getAllResources() {
        List<UmsResource> allResources = resourceService.getAllResources();
        Assertions.assertTrue(allResources.size() > 0);
    }
}
