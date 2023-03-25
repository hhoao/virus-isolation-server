package org.hhoa.vi.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.mgb.dao.AmsTagDao;
import org.hhoa.vi.admin.service.AmsTagService;
import org.hhoa.vi.mgb.model.generator.AmsTag;
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

    @Override
    public void addTag(AmsTag tagParam) {
        amsTagDao.insert(tagParam);
    }

    @Override
    public void deleteTag(Long tagId) {
        amsTagDao.deleteById(tagId);
    }

    @Override
    public void updateTag(AmsTag tagParam) {
        amsTagDao.updateById(tagParam);
    }
}
