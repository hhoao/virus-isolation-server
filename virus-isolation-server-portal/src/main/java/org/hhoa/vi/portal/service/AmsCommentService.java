package org.hhoa.vi.portal.service;


import org.hhoa.vi.mgb.model.AmsComment;
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
     * List list.
     *
     * @param commentParams the comment params
     * @param pageInfo      the page info
     * @return the list
     */
    List<AmsComment> list(AmsComment commentParams, PageInfo pageInfo);
}
