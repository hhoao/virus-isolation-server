package org.hhoa.vi.mgb.model.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
 * 职位资源关系
 * @author hhoa .
 * @author 2023-03-25
 */
@Schema(description = "职位资源关系")
public class OmsPositionResourceRelation implements Serializable {
    private Long id;

    private Long positionId;

    private Long resourceId;

    private static final long serialVersionUID = 1L;

    /**
     * OmsPositionResourceRelation

     */
    public OmsPositionResourceRelation(Long id, Long positionId, Long resourceId) {
        this.id = id;
        this.positionId = positionId;
        this.resourceId = resourceId;
    }

    /**
     * OmsPositionResourceRelation

     */
    public OmsPositionResourceRelation() {
        super();
    }

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return position_id 
     */
    public Long getPositionId() {
        return positionId;
    }

    /**
     * 
     * @param positionId 
     */
    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    /**
     * 
     * @return resource_id 
     */
    public Long getResourceId() {
        return resourceId;
    }

    /**
     * 
     * @param resourceId 
     */
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * toString
     * @return java.lang.String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", positionId=").append(positionId);
        sb.append(", resourceId=").append(resourceId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}