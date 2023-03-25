package org.hhoa.vi.mgb.model.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
 * 账户组织关系
 * @author hhoa .
 * @author 2023-03-25
 */
@Schema(description = "账户组织关系")
public class OmsAccountOrganizationRelation implements Serializable {
    private Long id;

    @Schema(description = "账户id")
    private Long accountId;

    @Schema(description = "组织id")
    private Long organizationId;

    @Schema(description = "职位id")
    private Long positionId;

    private static final long serialVersionUID = 1L;

    /**
     * OmsAccountOrganizationRelation

     */
    public OmsAccountOrganizationRelation(Long id, Long accountId, Long organizationId, Long positionId) {
        this.id = id;
        this.accountId = accountId;
        this.organizationId = organizationId;
        this.positionId = positionId;
    }

    /**
     * OmsAccountOrganizationRelation

     */
    public OmsAccountOrganizationRelation() {
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
     * 账户id
     * @return account_id 账户id
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * 账户id
     * @param accountId 账户id
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 组织id
     * @return organization_id 组织id
     */
    public Long getOrganizationId() {
        return organizationId;
    }

    /**
     * 组织id
     * @param organizationId 组织id
     */
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 职位id
     * @return position_id 职位id
     */
    public Long getPositionId() {
        return positionId;
    }

    /**
     * 职位id
     * @param positionId 职位id
     */
    public void setPositionId(Long positionId) {
        this.positionId = positionId;
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
        sb.append(", accountId=").append(accountId);
        sb.append(", organizationId=").append(organizationId);
        sb.append(", positionId=").append(positionId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}