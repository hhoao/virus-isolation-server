package org.hhoa.vi.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.hhoa.vi.mgb.dao.AmsArticleDao;
import org.hhoa.vi.mgb.dao.AmsCommentDao;
import org.hhoa.vi.mgb.model.generator.AmsArticle;
import org.hhoa.vi.mgb.model.generator.AmsComment;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.portal.service.AmsArticleService;
import org.hhoa.vi.portal.service.AmsCommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Ams article service.
 *
 * @author hhoa
 * @since 2022 /9/3
 */
@AllArgsConstructor
@Service
public class AmsArticleServiceImpl implements AmsArticleService {
    private AmsArticleDao articleDao;
    private AmsCommentDao commentDao;
    private AmsCommentService commentService;

    @Override
    public List<AmsArticle> list(AmsArticle articleParams, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo);
        return articleDao.selectList(new QueryWrapper<>(articleParams));
    }

    @Override
    public AmsArticle selectById(Long articleId) {
        return articleDao.selectById(articleId);
    }

    @Override
    public List<AmsComment> getArticleComments(Long articleId, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo);
        return commentService.getArticleComments(articleId, pageInfo);
    }
}
