package org.hhoa.vi.portal.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单请求参数.
 *
 * @author hhoa
 * @date 2022/6/17
 **/

@Schema(description = "菜单参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UmsMenuParam {
    @Schema(description = "菜单名称")
    private String title;

    @Schema(description = "菜单级数")
    private Integer level;

    @Schema(description = "菜单排序")
    private Integer sort;

    @Schema(description = "前端名称")
    private String name;

    @Schema(description = "前端图标")
    private String icon;

    @Schema(description = "前端隐藏")
    private Integer hidden;
}
