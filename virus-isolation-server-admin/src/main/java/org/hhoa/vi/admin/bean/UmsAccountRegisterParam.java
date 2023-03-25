package org.hhoa.vi.admin.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hhoa.vi.mgb.model.IdentifyType;

/**
 * 注册需要的参数
 * @author hhoa
 * @date 2022/5/12
 **/
@Data
@Schema(description= "用户注册参数")
public class UmsAccountRegisterParam {
    @Schema(description = "认证类型")
    private IdentifyType identifyType;

    @Schema(description = "认证标识")
    private String identifier;

    @Schema(description = "验证码")
    private String authCode;

}
