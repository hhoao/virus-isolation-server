package org.hhoa.vi.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.hhoa.vi.mgb.model.AmsArticle;
import org.hhoa.vi.mgb.model.AmsCatalog;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.portal.dao.AmsCatalogDao;
import org.hhoa.vi.portal.service.AmsArticleCatalogRelationService;
import org.hhoa.vi.portal.service.AmsCatalogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Ams catalog service.
 *
 * @author hhoa
 * @since 2022 /9/3
 */
@Service
@AllArgsConstructor
public class AmsCatalogServiceImpl implements AmsCatalogService {
    private AmsCatalogDao catalogDao;
    private AmsArticleCatalogRelationService articleCatalogRelationService;

    @Override
    public List<AmsCatalog> selectList(AmsCatalog amsCatalogQueryWrapper, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo);
        return catalogDao.selectList(new QueryWrapper<>(amsCatalogQueryWrapper));
    }

    @Override
    public List<AmsArticle> getCatalogArticles(Long catalogId, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo);
        return articleCatalogRelationService.getCatalogArticles(catalogId);
    }
}
