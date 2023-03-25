package org.hhoa.vi.portal.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.hhoa.vi.mgb.model.generator.UmsAccountAuth;

/**
 * AccountAuthWrapper
 *
 * @author hhoa
 * @since 2023/3/21
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountAuthWrapper {
    @Schema(description = "用户基本信息")
    private UmsAccount accountInfo;
    
    @Schema(description = "用户认证类型")
    private String identityType;

    @Schema(description = "用户认证标识")
    private String identifier;

}
