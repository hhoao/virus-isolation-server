package org.hhoa.vi.portal.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录参数.
 *
 * @author hhoa
 * @since 2022/5/11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户登录参数")
public class UmsLoginParam {
    @Schema(description = "认证标识")
    private String username;
    @Schema(description = "密码")
    private String password;
}
