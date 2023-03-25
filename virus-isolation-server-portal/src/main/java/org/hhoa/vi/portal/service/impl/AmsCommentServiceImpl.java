package org.hhoa.vi.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.hhoa.vi.mgb.model.AmsComment;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.portal.dao.AmsCommentDao;
import org.hhoa.vi.portal.service.AmsCommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Ams comment service.
 *
 * @author hhoa
 * @since 2022 /9/3
 */
@Service
@AllArgsConstructor
public class AmsCommentServiceImpl implements AmsCommentService {
    private AmsCommentDao commentDao;

    @Override
    public List<AmsComment> list(AmsComment commentParams, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo);
        return commentDao.selectList(new QueryWrapper<>(commentParams));
    }
}
