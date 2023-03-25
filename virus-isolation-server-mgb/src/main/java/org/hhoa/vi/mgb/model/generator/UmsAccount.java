package org.hhoa.vi.mgb.model.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Date;

/**
 * 账户
 * @author hhoa .
 * @author 2023-03-25
 */
@Schema(description = "账户")
public class UmsAccount implements Serializable {
    private Long id;

    @Schema(description = "账号启用状态: FALSE->禁言， TRUE->启用")
    private Boolean status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "最后登录时间")
    private Date latestTime;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "角色id")
    private Long roleId;

    @Schema(description = "头衔")
    private String title;

    @Schema(description = "介绍")
    private String introduce;

    @Schema(description = "未阅读消息数")
    private Integer unreadCount;

    @Schema(description = "消息数")
    private Integer notifyCount;

    @Schema(description = "国家")
    private String country;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "电话号码")
    private String phone;

    @Schema(description = "电子邮箱")
    private String email;

    @Schema(description = "个性签名")
    private String signature;

    private static final long serialVersionUID = 1L;

    /**
     * UmsAccount

     */
    public UmsAccount(Long id, Boolean status, Date createTime, String nickname, Date latestTime, String avatar, Long roleId, String title, String introduce, Integer unreadCount, Integer notifyCount, String country, String address, String phone, String email, String signature) {
        this.id = id;
        this.status = status;
        this.createTime = createTime;
        this.nickname = nickname;
        this.latestTime = latestTime;
        this.avatar = avatar;
        this.roleId = roleId;
        this.title = title;
        this.introduce = introduce;
        this.unreadCount = unreadCount;
        this.notifyCount = notifyCount;
        this.country = country;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.signature = signature;
    }

    /**
     * UmsAccount

     */
    public UmsAccount() {
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
     * 账号启用状态: FALSE->禁言， TRUE->启用
     * @return status 账号启用状态: FALSE->禁言， TRUE->启用
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 账号启用状态: FALSE->禁言， TRUE->启用
     * @param status 账号启用状态: FALSE->禁言， TRUE->启用
     */
    public void setStatus(Boolean status) {
        this.status = status;
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
     * 昵称
     * @return nickname 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 昵称
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 最后登录时间
     * @return latest_time 最后登录时间
     */
    public Date getLatestTime() {
        return latestTime;
    }

    /**
     * 最后登录时间
     * @param latestTime 最后登录时间
     */
    public void setLatestTime(Date latestTime) {
        this.latestTime = latestTime;
    }

    /**
     * 头像
     * @return avatar 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 头像
     * @param avatar 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 角色id
     * @return role_id 角色id
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 角色id
     * @param roleId 角色id
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 头衔
     * @return title 头衔
     */
    public String getTitle() {
        return title;
    }

    /**
     * 头衔
     * @param title 头衔
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 介绍
     * @return introduce 介绍
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * 介绍
     * @param introduce 介绍
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * 未阅读消息数
     * @return unread_count 未阅读消息数
     */
    public Integer getUnreadCount() {
        return unreadCount;
    }

    /**
     * 未阅读消息数
     * @param unreadCount 未阅读消息数
     */
    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    /**
     * 消息数
     * @return notify_count 消息数
     */
    public Integer getNotifyCount() {
        return notifyCount;
    }

    /**
     * 消息数
     * @param notifyCount 消息数
     */
    public void setNotifyCount(Integer notifyCount) {
        this.notifyCount = notifyCount;
    }

    /**
     * 国家
     * @return country 国家
     */
    public String getCountry() {
        return country;
    }

    /**
     * 国家
     * @param country 国家
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 地址
     * @return address 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 地址
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 电话号码
     * @return phone 电话号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 电话号码
     * @param phone 电话号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 电子邮箱
     * @return email 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 电子邮箱
     * @param email 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 个性签名
     * @return signature 个性签名
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 个性签名
     * @param signature 个性签名
     */
    public void setSignature(String signature) {
        this.signature = signature;
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
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", nickname=").append(nickname);
        sb.append(", latestTime=").append(latestTime);
        sb.append(", avatar=").append(avatar);
        sb.append(", roleId=").append(roleId);
        sb.append(", title=").append(title);
        sb.append(", introduce=").append(introduce);
        sb.append(", unreadCount=").append(unreadCount);
        sb.append(", notifyCount=").append(notifyCount);
        sb.append(", country=").append(country);
        sb.append(", address=").append(address);
        sb.append(", phone=").append(phone);
        sb.append(", email=").append(email);
        sb.append(", signature=").append(signature);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}