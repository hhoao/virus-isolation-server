package org.hhoa.vi.admin.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Ums resource param.
 *
 * @author hhoa
 * @since 2022 /5/16
 */
@Data
@NoArgsConstructor
@Schema(description = "资源参数")
public class UmsResourceParam {
    @Schema(description = "资源名称")
    private String name;

    @Schema(description = "类型")
    private String type;
    @Schema(description = "请求方法")
    private String method;

    @Schema(description = "请求路径")
    private String url;

    @Schema(description = "描述")
    private String description;
}
