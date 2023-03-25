package org.hhoa.vi.mgb.model.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
 * 用户授权信息
 * @author hhoa .
 * @author 2023-03-25
 */
@Schema(description = "用户授权信息")
public class UmsAccountAuth implements Serializable {
    private Long id;

    @Schema(description = "用户id")
    private Long accountId;

    @Schema(description = "类型")
    private String identityType;

    @Schema(description = "标识")
    private String identifier;

    @Schema(description = "凭证")
    private String credential;

    private static final long serialVersionUID = 1L;

    /**
     * UmsAccountAuth

     */
    public UmsAccountAuth(Long id, Long accountId, String identityType, String identifier, String credential) {
        this.id = id;
        this.accountId = accountId;
        this.identityType = identityType;
        this.identifier = identifier;
        this.credential = credential;
    }

    /**
     * UmsAccountAuth

     */
    public UmsAccountAuth() {
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
     * 用户id
     * @return account_id 用户id
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * 用户id
     * @param accountId 用户id
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 类型
     * @return identity_type 类型
     */
    public String getIdentityType() {
        return identityType;
    }

    /**
     * 类型
     * @param identityType 类型
     */
    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    /**
     * 标识
     * @return identifier 标识
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * 标识
     * @param identifier 标识
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * 凭证
     * @return credential 凭证
     */
    public String getCredential() {
        return credential;
    }

    /**
     * 凭证
     * @param credential 凭证
     */
    public void setCredential(String credential) {
        this.credential = credential;
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
        sb.append(", identityType=").append(identityType);
        sb.append(", identifier=").append(identifier);
        sb.append(", credential=").append(credential);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}