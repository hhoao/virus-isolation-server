package org.hhoa.vi.admin.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hhoa.vi.admin.bean.ResponseTokenInfo;
import org.hhoa.vi.admin.bean.UmsLoginParam;
import org.hhoa.vi.common.api.CommonResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import java.util.Map;

/**
 * ServiceTransactionTest
 *
 * @author hhoa
 * @since 2022/5/31
 **/

@Configuration
public class ServiceTransactionTest extends BasicTest {
    @Autowired
    PlatformTransactionManager transactionManager;
    TransactionStatus transactionStatus;


    @BeforeEach
    public void setUp() {
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionAttribute());
    }

    @AfterEach
    public void tearDown() {
        transactionManager.rollback(transactionStatus);
    }
}
