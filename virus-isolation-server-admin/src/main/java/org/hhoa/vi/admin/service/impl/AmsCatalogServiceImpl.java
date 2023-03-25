package org.hhoa.vi.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.mgb.dao.AmsCatalogDao;
import org.hhoa.vi.admin.service.AmsArticleCatalogRelationService;
import org.hhoa.vi.admin.service.AmsCatalogService;
import org.hhoa.vi.mgb.model.generator.AmsArticle;
import org.hhoa.vi.mgb.model.generator.AmsCatalog;
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
    public void addCatalog(AmsCatalog catalogParam) {
        catalogDao.insert(catalogParam);
    }

    @Override
    public void updateCatalog(AmsCatalog catalogParam) {
        catalogDao.updateById(catalogParam);
    }

    @Override
    public void deleteCatalog(Long catalogId) {
        catalogDao.deleteById(catalogId);
    }

    @Override
    public List<AmsArticle> getCatalogArticles(Long catalogId, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo);
        return articleCatalogRelationService.getCatalogArticles(catalogId);
    }
}
