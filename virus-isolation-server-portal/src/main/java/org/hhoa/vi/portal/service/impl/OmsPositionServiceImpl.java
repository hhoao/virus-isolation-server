package org.hhoa.vi.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.hhoa.vi.mgb.dao.OmsPositionDao;
import org.hhoa.vi.mgb.model.generator.OmsPosition;
import org.hhoa.vi.mgb.model.generator.OmsPositionResourceRelation;
import org.hhoa.vi.mgb.model.generator.UmsResource;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.mgb.dao.OmsPositionResourceDao;
import org.hhoa.vi.portal.service.OmsPositionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OmsPositionServiceImpl
 *
 * @author hhoa
 * @since 2023/3/20
 **/

@Service
@AllArgsConstructor
public class OmsPositionServiceImpl implements OmsPositionService {
    private OmsPositionDao omsPositionDao;
    private OmsPositionResourceDao positionResourceDao;

    @Override
    public List<OmsPosition> list(OmsPosition positionParams, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo);
        QueryWrapper<OmsPosition> queryWrapper = new QueryWrapper<>();
        return omsPositionDao.selectList(queryWrapper);
    }

    @Override
    public List<UmsResource> getPositionResources(Integer positionId){
        return omsPositionDao.getPositionResources(positionId);
    }

    @Override
    public List<OmsPositionResourceRelation> getAllPositionResources(){
        return positionResourceDao.selectList(new QueryWrapper<>());
    }

    @Override
    public List<UmsResource> listAllPositionResources(){
        return omsPositionDao.listAllPositionResources();
    }

}
