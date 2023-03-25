package org.hhoa.vi.admin.service;

import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.mgb.model.generator.OmsPosition;

import java.util.List;

/**
 * OmsPositionService
 *
 * @author hhoa
 * @since 2023 /3/19
 */
public interface OmsPositionService {
    /**
     * List list.
     *
     * @param positionParams the position params
     * @param pageInfo       the page info
     * @return the list
     */
    List<OmsPosition> list(OmsPosition positionParams, PageInfo pageInfo);

    /**
     * Add position.
     *
     * @param positionParam the position param
     */
    void addPosition(OmsPosition positionParam);

    /**
     * Delete position.
     *
     * @param positionId the position id
     */
    void deletePosition(Long positionId);

    /**
     * Update position.
     *
     * @param positionParam the position param
     */
    void updatePosition(OmsPosition positionParam);
}
