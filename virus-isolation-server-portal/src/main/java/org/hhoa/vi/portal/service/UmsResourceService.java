package org.hhoa.vi.portal.service;


import org.hhoa.vi.mgb.model.generator.UmsResource;
import org.hhoa.vi.portal.bean.PageInfo;

import java.util.List;

/**
 * The interface Ums resource service.
 *
 * @author hhoa
 * @since  2022 /5/6
 */
public interface UmsResourceService {
    /**
     * 获取所有资源.
     *
     * @return 所有资源 all resources
     */
    List<UmsResource> getAllResources();
}
