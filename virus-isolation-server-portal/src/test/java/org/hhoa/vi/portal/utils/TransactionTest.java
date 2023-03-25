package org.hhoa.vi.portal.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.portal.VIPortalApplication;
import org.hhoa.vi.portal.bean.ResponseTokenInfo;
import org.hhoa.vi.portal.bean.UmsLoginParam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import java.util.Map;

/**
 * TransactionTest.
 *
 * @author hhoa
 * @since 2022/5/31
 **/

@SuppressWarnings("unchecked")
@SpringBootTest(classes = VIPortalApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class TransactionTest {
    @Autowired
    PlatformTransactionManager transactionManager;
    TransactionStatus transactionStatus;
    private TestRestTemplate notVerifiedTemplate;
    private TestRestTemplate adminVerifiedTemplate;
    private TestRestTemplate guestVerifiedTemplate;
    private TestRestTemplate testVerifiedTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${rest-template.root-uri}")
    private String rootUri;

    /**
     * 获取未登陆的Template
     *
     * @return 未登陆的Template
     */
    public TestRestTemplate getNotVerifiedTemplate() {
        return notVerifiedTemplate;
    }
    public TestRestTemplate getGuestVerifiedTemplate() {
        if (guestVerifiedTemplate == null) {
            UmsLoginParam umsLoginParam = new UmsLoginParam("test3", "123456");
            CommonResult<Map<String, String>> commonResult =
                    (CommonResult<Map<String, String>>) notVerifiedTemplate.postForObject(
                            "/accounts/auth/token", umsLoginParam, CommonResult.class);
            guestVerifiedTemplate = new TestRestTemplate(
                    new RestTemplateBuilder().
                            rootUri(rootUri).
                            defaultHeader("Authorization",
                                    commonResult.getResult().get("tokenHead") +
                                            commonResult.getResult().get("token"))
            );
        }

        return guestVerifiedTemplate;
    }

    /**
     * 获取Admin用户登陆后的RestTemplate
     *
     * @return Admin用户登陆后的RestTemplate
     */
    @SuppressWarnings("unchecked")
    public TestRestTemplate getAdminVerifiedTemplate() {
        if (adminVerifiedTemplate == null) {
            UmsLoginParam umsLoginParam = new UmsLoginParam("admin", "123456");
            CommonResult<Map<String, String>> commonResult =
                    (CommonResult<Map<String, String>>) notVerifiedTemplate.postForObject(
                            "/accounts/auth/token", umsLoginParam, CommonResult.class);
            adminVerifiedTemplate = new TestRestTemplate(
                    new RestTemplateBuilder().
                            rootUri(rootUri).
                            defaultHeader("Authorization",
                                    commonResult.getResult().get("tokenHead") +
                                            commonResult.getResult().get("token"))
            );
        }

        return adminVerifiedTemplate;
    }


    /**
     * 获取Test用户登陆后的RestTemplate
     *
     * @return Test用户登陆后的RestTemplate
     */
    public TestRestTemplate getTestVerifiedTemplate() {
        if (testVerifiedTemplate == null) {
            UmsLoginParam umsLoginParam = new UmsLoginParam("test1", "123456");

            String commonResult = notVerifiedTemplate.postForObject(
                    "/accounts/auth/token", umsLoginParam, String.class);
            CommonResult<ResponseTokenInfo> responseTokenInfoCommonResult =
                    TestUtils.jsonStringToObject(commonResult, new TypeReference<CommonResult<ResponseTokenInfo>>() {
                    });

            testVerifiedTemplate = new TestRestTemplate(
                    new RestTemplateBuilder().
                            rootUri(rootUri).
                            defaultHeader("Authorization",
                                    responseTokenInfoCommonResult.getResult().getTokenHead() +
                                            responseTokenInfoCommonResult.getResult().getToken())
            );
        }
        return testVerifiedTemplate;
    }

    @BeforeEach
    public void setUp() {
        notVerifiedTemplate = new TestRestTemplate(new RestTemplateBuilder().rootUri(rootUri));
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionAttribute());
    }

    @AfterEach
    public void tearDown() {
        transactionManager.rollback(transactionStatus);
    }
}
