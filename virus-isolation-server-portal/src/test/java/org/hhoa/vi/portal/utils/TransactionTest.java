package org.hhoa.vi.portal;


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

/**
 * TransactionTest.
 *
 * @author hhoa
 * @since 2022/5/31
 **/

@SpringBootTest(classes = VIPortalApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class TransactionTest {
    @Autowired
    PlatformTransactionManager transactionManager;
    TransactionStatus transactionStatus;
    private TestRestTemplate notVerifiedTemplate;
    private TestRestTemplate adminVerifiedTemplate;
    private TestRestTemplate testVerifiedTemplate;
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

    /**
     * 获取Admin用户登陆后的RestTemplate
     *
     * @return Admin用户登陆后的RestTemplate
     */
    public TestRestTemplate getAdminVerifiedTemplate() {
        if (adminVerifiedTemplate == null) {
            UmsLoginParam umsLoginParam = new UmsLoginParam("admin", "123456");
            ResponseTokenInfo responseTokenInfo =
                    notVerifiedTemplate.postForObject(
                            "/accounts/auth/token", umsLoginParam, ResponseTokenInfo.class);
            adminVerifiedTemplate = new TestRestTemplate(
                    new RestTemplateBuilder().
                            rootUri(rootUri).
                            defaultHeader("Authorization",
                                    responseTokenInfo.getTokenHead() + responseTokenInfo.getToken())
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
            UmsLoginParam umsLoginParam = new UmsLoginParam("test", "123456");
            ResponseTokenInfo responseTokenInfo =
                    notVerifiedTemplate.postForObject(
                            "/accounts/auth/token", umsLoginParam, ResponseTokenInfo.class);
            testVerifiedTemplate = new TestRestTemplate(
                    new RestTemplateBuilder().
                            rootUri(rootUri).
                            defaultHeader("Authorization",
                                    responseTokenInfo.getTokenHead() + responseTokenInfo.getToken())
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
