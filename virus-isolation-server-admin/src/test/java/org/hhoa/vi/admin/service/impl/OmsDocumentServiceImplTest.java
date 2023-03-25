package org.hhoa.vi.admin.service.impl;

import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.service.OmsDocumentService;
import org.hhoa.vi.admin.utils.ServiceTransactionTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * OmsDocumentServiceImplTest
 *
 * @author hhoa
 * @since 2023/3/20
 **/

class OmsDocumentServiceImplTest extends ServiceTransactionTest {
    @Autowired
    OmsDocumentService documentService;

    @Test
    void list() {
        List<String> healthClock = documentService.list("health_clock", "", new PageInfo(1, 5));
        Assertions.assertTrue(healthClock.size() > 0);
    }

    @Test
    void addDocument() {
        documentService.addDocument("health_clock", """
                {
                    "name": "testDocument1",
                    "accountName": "testAccount1",
                    "createTime": "12:50",
                    "temperature": 37.1,
                    "healthCondition": true,
                    "address": "testAddress1",
                    "remark": "testRemark1",
                }
                """);
        List<String> healthClock = documentService.list("health_clock", """
                {
                    "$and": [
                        {"name": "testDocument1"},
                        {"accountName": "testAccount1"},
                        {"healthCondition": true},
                    ]
                }
                """, new PageInfo(1, 0));
        Assertions.assertTrue(healthClock.size() > 0);
    }

    @Test
    void deleteDocument() {
        documentService.addDocument("health_clock", """
                {
                    "name": "testDocument1",
                    "accountName": "testAccount1",
                    "createTime": "12:50",
                    "temperature": 37.1,
                    "healthCondition": true,
                    "address": "testAddress1",
                    "remark": "testRemark1",
                }
                """);
        documentService.deleteDocument("health_clock", """
                {
                    "$and": [
                        {"name": "testDocument1"},
                        {"accountName": "testAccount1"},
                        {"healthCondition": true},
                    ]
                }
                """);
        List<String> healthClock = documentService.list("health_clock", """
                {
                    "$and": [
                        {"name": "testDocument1"},
                        {"accountName": "testAccount1"},
                        {"healthCondition": true},
                    ]
                }
                """, new PageInfo(1, 0));
        Assertions.assertEquals(0, healthClock.size());
    }

    @Test
    void updateDocument() {
        documentService.addDocument("health_clock", """
                {
                    "name": "testDocument1",
                    "accountName": "testAccount1",
                    "createTime": "12:50",
                    "temperature": 37.1,
                    "healthCondition": true,
                    "address": "testAddress1",
                    "remark": "testRemark1",
                }
                """);
        documentService.updateDocument("health_clock", """
                {
                    "$and": [
                        {"name": "testDocument1"},
                        {"accountName": "testAccount1"},
                        {"healthCondition": true},
                    ]
                }
                """, """
                {
                    "$set":
                    {
                        "name": "testDocument2",
                        "accountName": "testAccount2",
                        "createTime": "12:50",
                        "temperature": 37.1,
                        "healthCondition": true,
                        "address": "testAddress2",
                        "remark": "testRemark2",
                    },
                }
                """);
        List<String> healthClock = documentService.list("health_clock", """
                {
                    "$and": [
                        {"name": "testDocument2"},
                        {"accountName": "testAccount2"},
                        {"healthCondition": true},
                    ]
                }
                """, new PageInfo(1, 0));
        Assertions.assertTrue(healthClock.size() > 0);
    }
}
