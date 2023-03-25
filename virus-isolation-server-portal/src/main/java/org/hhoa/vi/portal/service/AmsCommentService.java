package org.hhoa.vi.portal.service;


import org.hhoa.vi.mgb.model.generator.AmsComment;
import org.hhoa.vi.portal.bean.PageInfo;

import java.util.List;

/**
 * The interface Ams comment service.
 *
 * @author hhoa
 * @since 2022 /9/3
 */
public interface AmsCommentService {
    /**
     * Gets article comments.
     *
     * @param articleId the article id
     * @param pageInfo  the page info
     * @return article comments
     */
    List<AmsComment> getArticleComments(Long articleId, PageInfo pageInfo);
}
