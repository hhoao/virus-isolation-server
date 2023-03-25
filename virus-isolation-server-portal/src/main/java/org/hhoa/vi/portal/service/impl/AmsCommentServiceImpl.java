package org.hhoa.vi.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.hhoa.vi.mgb.dao.AmsCommentDao;
import org.hhoa.vi.mgb.model.generator.AmsComment;
import org.hhoa.vi.portal.bean.PageInfo;
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
    public List<AmsComment> getArticleComments(Long articleId, PageInfo pageInfo) {
        AmsComment comment = new AmsComment();
        comment.setArticleId(articleId);
        return commentDao.selectList(new QueryWrapper<>(comment));
    }
}
