package org.hhoa.vi.portal.config;

import org.hhoa.vi.common.config.BaseSwaggerConfig;
import org.hhoa.vi.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置.
 *
 * @author hhoa
 * @since 2022/5/11
 **/
@Configuration
public class PortalSwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("org.hhoa.vi.portal.controller")
                .title("virus-isolation后端后台管理")
                .description("任何值得到达的地方，都没有捷径!")
                .contactName("hhoao")
                .contactUrl("")
                .contactEmail("huanghaohhoa@163.com")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
