package org.hhoa.vi.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.AllArgsConstructor;
import org.hhoa.vi.mgb.dao.AmsArticleCatalogRelationDao;
import org.hhoa.vi.admin.service.AmsArticleCatalogRelationService;
import org.hhoa.vi.admin.service.AmsArticleService;
import org.hhoa.vi.mgb.model.generator.AmsArticle;
import org.hhoa.vi.mgb.model.generator.AmsArticleCatalogRelation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

/**
 * The type Ams article catalog relation service.
 *
 * @author hhoa
 * @since 2022 /9/4
 */
@AllArgsConstructor
@Service
public class AmsArticleCatalogRelationServiceImpl implements AmsArticleCatalogRelationService {
    private AmsArticleCatalogRelationDao articleCatalogRelationDao;
    private AmsArticleService articleService;


    @Override
    public List<AmsArticle> getCatalogArticles(Long catalogId) {
        AmsArticleCatalogRelation articleCatalogRelation = new AmsArticleCatalogRelation();
        articleCatalogRelation.setCatalogId(catalogId);
        Stream<AmsArticle> amsArticleStream =
                articleCatalogRelationDao.selectList(new QueryWrapper<>(articleCatalogRelation))
                        .stream().map(relation -> {
                            Long articleId = articleCatalogRelation.getArticleId();
                            return articleService.selectById(articleId);
                        });
        return amsArticleStream.toList();
    }
}
