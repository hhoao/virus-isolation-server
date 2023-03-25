package org.hhoa.vi.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.hhoa.vi.mgb.model.AmsArticle;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.portal.dao.AmsArticleDao;
import org.hhoa.vi.portal.service.AmsArticleService;
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
    public AmsArticle selectById(Long articleId) {
        return articleDao.selectById(articleId);
    }
}
