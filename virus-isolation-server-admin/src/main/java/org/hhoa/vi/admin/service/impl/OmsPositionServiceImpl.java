package org.hhoa.vi.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.mgb.dao.OmsPositionDao;
import org.hhoa.vi.admin.service.OmsPositionService;
import org.hhoa.vi.mgb.model.generator.OmsPosition;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OmsPositionServiceImpl
 *
 * @author hhoa
 * @since 2023/3/19
 **/

@AllArgsConstructor
@Service
public class OmsPositionServiceImpl implements OmsPositionService {
    private OmsPositionDao omsPositionDao;

    @Override
    public List<OmsPosition> list(OmsPosition positionParams, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo);
        QueryWrapper<OmsPosition> queryWrapper = new QueryWrapper<>(positionParams);
        return omsPositionDao.selectList(queryWrapper);
    }

    @Override
    public void addPosition(OmsPosition positionParam) {
        omsPositionDao.insert(positionParam);
    }

    @Override
    public void deletePosition(Long positionId) {
        omsPositionDao.deleteById(positionId);
    }

    @Override
    public void updatePosition(OmsPosition positionParam) {
        omsPositionDao.updateById(positionParam);
    }
}
