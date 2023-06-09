package org.hhoa.vi.admin.service;



import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.mgb.model.generator.AmsArticle;

import java.util.List;

/**
 * The interface Ams article service.
 *
 * @author hhoa
 * @since 2022 /9/3
 */
public interface AmsArticleService {
    /**
     * List list.
     *
     * @param articleParams the article params
     * @param pageInfo      the page info
     * @return the list
     */
    List<AmsArticle> list(AmsArticle articleParams, PageInfo pageInfo);

    /**
     * Add article.
     *
     * @param articleParam the article param
     */
    void addArticle(AmsArticle articleParam);

    /**
     * Update article.
     *
     * @param articleParam the article param
     */
    void updateArticle(AmsArticle articleParam);

    /**
     * Delete article.
     *
     * @param articleId the article id
     */
    void deleteArticle(Long articleId);

    /**
     * Select by id ams article.
     *
     * @param articleId the article id
     * @return the ams article
     */
    AmsArticle selectById(Long articleId);
}
