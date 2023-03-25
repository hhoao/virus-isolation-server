package org.hhoa.vi.mgb.model.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Date;

/**
 * 组织
 * @author hhoa .
 * @author 2023-03-25
 */
@Schema(description = "组织")
public class OmsOrganization implements Serializable {
    private Long id;

    @Schema(description = "组织名")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "投降")
    private String avatar;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    /**
     * OmsOrganization

     */
    public OmsOrganization(Long id, String name, String description, String avatar, String email, String phone, Date createTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.avatar = avatar;
        this.email = email;
        this.phone = phone;
        this.createTime = createTime;
    }

    /**
     * OmsOrganization

     */
    public OmsOrganization() {
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
     * 组织名
     * @return name 组织名
     */
    public String getName() {
        return name;
    }

    /**
     * 组织名
     * @param name 组织名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 描述
     * @return description 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 投降
     * @return avatar 投降
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 投降
     * @param avatar 投降
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 邮箱
     * @return email 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮箱
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 电话
     * @return phone 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 电话
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", avatar=").append(avatar);
        sb.append(", email=").append(email);
        sb.append(", phone=").append(phone);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}