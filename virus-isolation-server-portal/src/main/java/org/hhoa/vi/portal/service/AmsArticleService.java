package org.hhoa.vi.portal.service;


import org.hhoa.vi.mgb.model.generator.AmsArticle;
import org.hhoa.vi.mgb.model.generator.AmsComment;
import org.hhoa.vi.portal.bean.PageInfo;

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
     * Select by id ams article.
     *
     * @param articleId the article id
     * @return the ams article
     */
    AmsArticle selectById(Long articleId);

    /**
     * Gets article comments.
     *
     * @param articleId the article id
     * @param pageInfo  the page info
     * @return the article comments
     */
    List<AmsComment> getArticleComments(Long articleId, PageInfo pageInfo);
}
