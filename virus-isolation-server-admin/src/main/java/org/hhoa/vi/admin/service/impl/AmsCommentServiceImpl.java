package org.hhoa.vi.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
;
import lombok.AllArgsConstructor;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.mgb.dao.AmsCommentDao;
import org.hhoa.vi.admin.service.AmsCommentService;
import org.hhoa.vi.mgb.model.generator.AmsComment;
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

    @Override
    public void addComment(AmsComment commentParam) {
        commentDao.insert(commentParam);
    }

    @Override
    public void updateComment(AmsComment commentParam) {
        commentDao.updateById(commentParam);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentDao.deleteById(commentId);
    }
}
