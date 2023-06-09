package org.hhoa.vi.portal.service;


import org.hhoa.vi.mgb.model.generator.AmsArticle;

import java.util.List;

/**
 * The interface Ams article catalog relation service.
 *
 * @author hhoa
 * @since 2022 /9/4
 */
public interface AmsArticleCatalogRelationService {
    /**
     * Gets catalog articles.
     *
     * @param catalogId the catalog id
     * @return the catalog articles
     */
    List<AmsArticle> getCatalogArticles(Long catalogId);
}
