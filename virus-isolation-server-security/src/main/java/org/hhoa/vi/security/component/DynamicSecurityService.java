package org.hhoa.vi.security.component;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Map;


/**
 * 动态权限相关业务类.
 *
 * @author hhoa
 */
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应MAP.
     * 资源ANT通配符用于匹配请求路径, 资源为该路径需要的权限
     *
     * @return 资源ANT通配符和资源对应MAP
     */
    Map<RequestMatcher, ConfigAttribute> loadDataSource();
}
