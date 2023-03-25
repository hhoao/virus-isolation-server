package org.hhoa.vi.security.component;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 动态权限数据源，用于获取动态权限规则.
 *
 * @author hhoa
 */
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<RequestMatcher, ConfigAttribute> configAttributeMap = null;
    private final DynamicSecurityService dynamicSecurityService;

    public DynamicSecurityMetadataSource(DynamicSecurityService dynamicSecurityService) {
        this.dynamicSecurityService = dynamicSecurityService;
        dynamicSecurityService.loadDataSource();
    }

    /**
     * 获取请求的Url所需要的权限
     * @param o the object being secured
     * @return 权限列表
     * @throws IllegalArgumentException 如果传递的对象不是SecurityMetadataSource实现支持的类型，则抛出该异常
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (configAttributeMap == null) {
            configAttributeMap = dynamicSecurityService.loadDataSource();
        }
        List<ConfigAttribute> configAttributes = new ArrayList<>();


        for (Map.Entry<RequestMatcher, ConfigAttribute> entry :
                configAttributeMap.entrySet()) {
            if (entry.getKey().matches(((FilterInvocation) o).getHttpRequest())) {
                configAttributes.add(entry.getValue());
            }
        }
        // 未设置操作请求权限，返回空集合
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> supportClass) {
        return true;
    }

}
