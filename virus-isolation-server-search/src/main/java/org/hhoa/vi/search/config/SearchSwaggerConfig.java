package org.hhoa.vi.search.config;

import org.hhoa.vi.common.config.BaseSwaggerConfig;
import org.hhoa.vi.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;

/**
 * search Swagger配置.
 *
 * @author hhoa
 * @since 2022/5/11
 **/
@Configuration
public class SearchSwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.hhoa.vi.search")
                .title("vi search module")
                .description("任何值得到达的地方，都没有捷径!")
                .contactName("hhoao")
                .contactUrl("")
                .contactEmail("huanghaohhoa@163.com")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
