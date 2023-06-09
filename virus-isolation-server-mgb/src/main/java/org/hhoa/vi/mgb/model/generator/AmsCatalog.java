package org.hhoa.vi.mgb.model.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Date;

/**
 * 目
 * @author hhoa .
 * @author 2023-03-25
 */
@Schema(description = "目")
public class AmsCatalog implements Serializable {
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "等级")
    private Integer level;

    @Schema(description = "父级id")
    private Long parentId;

    @Schema(description = "菜单排序")
    private Integer sort;

    @Schema(description = "是否启用(0: 否, 1: 是)")
    private Boolean enable;

    @Schema(description = "创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    /**
     * AmsCatalog

     */
    public AmsCatalog(Long id, String name, String description, Integer level, Long parentId, Integer sort, Boolean enable, Date createTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.parentId = parentId;
        this.sort = sort;
        this.enable = enable;
        this.createTime = createTime;
    }

    /**
     * AmsCatalog

     */
    public AmsCatalog() {
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
     * 名称
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     * @param name 名称
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
     * 等级
     * @return level 等级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 等级
     * @param level 等级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 父级id
     * @return parent_id 父级id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 父级id
     * @param parentId 父级id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 菜单排序
     * @return sort 菜单排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 菜单排序
     * @param sort 菜单排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 是否启用(0: 否, 1: 是)
     * @return enable 是否启用(0: 否, 1: 是)
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * 是否启用(0: 否, 1: 是)
     * @param enable 是否启用(0: 否, 1: 是)
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
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
        sb.append(", level=").append(level);
        sb.append(", parentId=").append(parentId);
        sb.append(", sort=").append(sort);
        sb.append(", enable=").append(enable);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}