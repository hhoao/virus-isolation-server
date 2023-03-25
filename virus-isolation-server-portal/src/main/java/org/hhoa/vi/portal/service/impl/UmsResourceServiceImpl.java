package org.hhoa.vi.portal.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.hhoa.vi.common.exception.Asserts;
import org.hhoa.vi.mgb.model.UmsResource;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.portal.dao.UmsResourceDao;
import org.hhoa.vi.portal.service.UmsResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Ums resource service.
 *
 * @author hhoa
 * @since 2022 /5/5
 */
@Service
@RequiredArgsConstructor
public class UmsResourceServiceImpl implements UmsResourceService {
    private final UmsResourceDao resourceDao;

    @Override
    public UmsResource getResource(Long resourceId) {
        UmsResource retResource = resourceDao.selectById(resourceId);
        if (retResource == null) {
            Asserts.fail("没有该资源");
        }
        return retResource;
    }

    @Override
    public List<UmsResource> getAllResources() {
        return resourceDao.selectList(new QueryWrapper<>());
    }


    @Override
    public List<UmsResource> list(PageInfo pageInfo, UmsResource resource) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        return resourceDao.selectList(new QueryWrapper<>(resource));
    }
}
