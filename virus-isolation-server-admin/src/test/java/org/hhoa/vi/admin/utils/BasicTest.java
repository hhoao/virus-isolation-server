package org.hhoa.vi.admin.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.hhoa.vi.admin.VIAdminApplication;
import org.hhoa.vi.admin.bean.ResponseTokenInfo;
import org.hhoa.vi.admin.bean.UmsLoginParam;
import org.hhoa.vi.common.api.CommonResult;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

/**
 * BasicTest
 *
 * @author hhoa
 * @since 2023/3/24
 **/

@SpringBootTest(classes = VIAdminApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BasicTest {
    private TestRestTemplate notVerifiedTemplate;
    private TestRestTemplate adminVerifiedTemplate;
    private TestRestTemplate guestVerifiedTemplate;
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

    public TestRestTemplate getGuestVerifiedTemplate() {
        if (guestVerifiedTemplate == null) {
            UmsLoginParam umsLoginParam = new UmsLoginParam("test3", "123456");
            guestVerifiedTemplate = getLoginTemplate(umsLoginParam);
        }

        return guestVerifiedTemplate;
    }

    public TestRestTemplate getLoginTemplate(UmsLoginParam umsLoginParam) {
        String tokenJson = notVerifiedTemplate.postForObject(
                "/accounts/auth/token", umsLoginParam, String.class);
        CommonResult<ResponseTokenInfo> commonResult = TestUtils.jsonStringToObject(tokenJson, new TypeReference<CommonResult<ResponseTokenInfo>>() {
        });
        return new TestRestTemplate(
                new RestTemplateBuilder().
                        rootUri(rootUri).
                        defaultHeader("Authorization",
                                commonResult.getResult().getTokenHead()+
                                        commonResult.getResult().getToken())
        );
    }

    /**
     * 获取Admin用户登陆后的RestTemplate
     *
     * @return Admin用户登陆后的RestTemplate
     */
    public TestRestTemplate getAdminVerifiedTemplate() {
        if (adminVerifiedTemplate == null) {
            UmsLoginParam umsLoginParam = new UmsLoginParam("hhoa", "123456");
            adminVerifiedTemplate = getLoginTemplate(umsLoginParam);
        }

        return adminVerifiedTemplate;
    }


    /**
     * 获取Test用户登陆后的RestTemplate
     *
     * @return Test用户登陆后的RestTemplate
     */
    public TestRestTemplate getTest0VerifiedTemplate() {
        if (testVerifiedTemplate == null) {
            UmsLoginParam umsLoginParam = new UmsLoginParam("test0", "123456");
            testVerifiedTemplate = getLoginTemplate(umsLoginParam);
        }
        return testVerifiedTemplate;
    }

    @BeforeEach
    public void setUp() {
        notVerifiedTemplate = new TestRestTemplate(new RestTemplateBuilder().rootUri(rootUri));
    }
}
