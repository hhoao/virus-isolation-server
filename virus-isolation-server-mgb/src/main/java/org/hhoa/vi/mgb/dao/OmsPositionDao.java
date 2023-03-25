package org.hhoa.vi.mgb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hhoa.vi.mgb.model.generator.OmsPosition;
import org.hhoa.vi.mgb.model.generator.UmsResource;

import java.util.List;

/**
 * OmsPositionDao
 *
 * @author hhoa
 * @since 2023 /3/20
 */
public interface OmsPositionDao extends BaseMapper<OmsPosition> {
    /**
     * Gets position resources.
     *
     * @param positionId the position id
     * @return the position resources
     */
    List<UmsResource> getPositionResources(@Param("positionId") Integer positionId);

    /**
     * List all position resources list.
     *
     * @return the list
     */
    List<UmsResource> listAllPositionResources();
}
