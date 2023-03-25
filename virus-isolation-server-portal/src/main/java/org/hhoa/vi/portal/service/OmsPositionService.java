package org.hhoa.vi.portal.service;

import org.hhoa.vi.mgb.model.generator.OmsPosition;
import org.hhoa.vi.mgb.model.generator.OmsPositionResourceRelation;
import org.hhoa.vi.mgb.model.generator.UmsPositionResourceRelation;
import org.hhoa.vi.mgb.model.generator.UmsResource;
import org.hhoa.vi.portal.bean.PageInfo;

import java.util.List;

/**
 * OmsPositionService
 *
 * @author hhoa
 * @since 2023 /3/20
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
     * Gets position resources.
     *
     * @param positionId the position id
     * @return the position resources
     */
    List<UmsResource> getPositionResources(Integer positionId);

    /**
     * Gets all position resources.
     *
     * @return the all position resources
     */
    List<OmsPositionResourceRelation> getAllPositionResources();

    /**
     * List all position resources list.
     *
     * @return the list
     */
    List<UmsResource> listAllPositionResources();
}
