package org.hhoa.vi.admin.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * 登录参数.
 *
 * @author hhoa
 * @since 2022/5/11
 **/
@Data
@Schema(description = "用户登录参数")
@NoArgsConstructor
@AllArgsConstructor
public class UmsLoginParam {
    @Schema(description = "认证标识")
    private String username;
    @Schema(description = "密码")
    private String password;

}
