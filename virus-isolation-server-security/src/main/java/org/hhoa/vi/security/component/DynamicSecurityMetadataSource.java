package org.hhoa.vi.security.component;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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

    private static Map<AntPathRequestMatcher, ConfigAttribute> configAttributeMap = null;
    private final DynamicSecurityService dynamicSecurityService;

    public DynamicSecurityMetadataSource(DynamicSecurityService dynamicSecurityService) {
        this.dynamicSecurityService = dynamicSecurityService;
        dynamicSecurityService.loadDataSource();
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (configAttributeMap == null) {
            configAttributeMap = dynamicSecurityService.loadDataSource();
        }
        List<ConfigAttribute> configAttributes = new ArrayList<>();


        for (Map.Entry<AntPathRequestMatcher, ConfigAttribute> entry :
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