package org.hhoa.vi.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;

import lombok.AllArgsConstructor;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.mgb.dao.AmsArticleDao;
import org.hhoa.vi.admin.service.AmsArticleService;
import org.hhoa.vi.mgb.model.generator.AmsArticle;
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

    @Override
    public List<AmsArticle> list(AmsArticle articleParams, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo);
        return articleDao.selectList(new QueryWrapper<>(articleParams));
    }

    @Override
    public void addArticle(AmsArticle articleParam) {
        articleDao.insert(articleParam);
    }

    @Override
    public void updateArticle(AmsArticle articleParam) {
        articleDao.updateById(articleParam);
    }

    @Override
    public void deleteArticle(Long articleId) {
        articleDao.deleteById(articleId);
    }

    @Override
    public AmsArticle selectById(Long articleId) {
        return articleDao.selectById(articleId);
    }
}
