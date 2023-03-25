package org.hhoa.vi.admin.service;
;

import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.mgb.model.AmsTag;

import java.util.List;

/**
 * The interface Ams tag service.
 *
 * @author hhoa
 * @since 2022 /12/13
 */
public interface AmsTagService {
    /**
     * List list.
     *
     * @param tagParams the tag params
     * @param pageInfo  the page info
     * @return the list
     */
    List<AmsTag> list(AmsTag tagParams, PageInfo pageInfo);

    /**
     * Add tag.
     *
     * @param tagParam the tag param
     */
    void addTag(AmsTag tagParam);

    /**
     * Delete tag.
     *
     * @param tagId the tag id
     */
    void deleteTag(Long tagId);

    /**
     * Update tag.
     *
     * @param tagParam the tag param
     */
    void updateTag(AmsTag tagParam);
}
