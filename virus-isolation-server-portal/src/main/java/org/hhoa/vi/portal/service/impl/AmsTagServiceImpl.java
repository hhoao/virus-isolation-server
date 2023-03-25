package org.hhoa.vi.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.hhoa.vi.mgb.dao.AmsTagDao;
import org.hhoa.vi.mgb.model.generator.AmsTag;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.portal.service.AmsTagService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Ams tag service.
 *
 * @author hhoa
 * @since 2022 /12/13
 */
@AllArgsConstructor
@Service
public class AmsTagServiceImpl implements AmsTagService {
    private AmsTagDao amsTagDao;

    @Override
    public List<AmsTag> list(AmsTag tagParams, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo);
        QueryWrapper<AmsTag> queryWrapper = new QueryWrapper<>();
        return amsTagDao.selectList(queryWrapper);
    }
}
