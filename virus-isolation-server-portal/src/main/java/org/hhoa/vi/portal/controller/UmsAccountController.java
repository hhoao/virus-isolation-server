package org.hhoa.vi.portal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.mgb.model.AccountOrganization;
import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.hhoa.vi.mgb.model.IdentifyType;
import org.hhoa.vi.portal.bean.ResponseTokenInfo;
import org.hhoa.vi.portal.bean.UmsAccountRegisterParam;
import org.hhoa.vi.portal.bean.UmsAccountWrapper;
import org.hhoa.vi.portal.bean.UmsLoginParam;
import org.hhoa.vi.portal.service.UmsAccountService;
import org.hhoa.vi.security.config.JwtSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 账户管理.
 *
 * @author hhoa
 * @since 2022/5/8
 **/
@Tags({@Tag(name = "账户管理", description = "UmsAccountController")})
@RestController
public class UmsAccountController {
    private final UmsAccountService accountService;

    private JwtSecurityProperties securityProperties;

    public UmsAccountController(UmsAccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    private void setSecurityProperties(@Lazy JwtSecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Operation(summary = "已认证获取账户信息")
    @GetMapping("/account")
    public CommonResult<UmsAccountWrapper> getAccount(@RequestHeader Map<String, String> headers) {
        String authorization = securityProperties.getTokenHeader().toLowerCase();
        UmsAccountWrapper accountByAuthorization = accountService.getAccountByAuthorization(
                headers.get(authorization));
        return CommonResult.success(accountByAuthorization);
    }

    @Operation(summary = "账户登录")
    @PostMapping("/accounts/auth/token")
    public CommonResult<ResponseTokenInfo> login(@RequestBody UmsLoginParam loginParam) {
        String token = accountService.login(loginParam);
        ResponseTokenInfo tokenInfo =
                new ResponseTokenInfo(token, securityProperties.getTokenHead());
        return CommonResult.success(tokenInfo);
    }

    @Operation(description = "账户退出, 需要前端删除token", summary = "账户退出")
    @DeleteMapping("/account/auth/token")
    public CommonResult<String> logout(@RequestHeader Map<String, String> headers) {
        String bearer = headers.get(securityProperties.getTokenHeader().toLowerCase());
        accountService.logout(bearer);
        return CommonResult.success(null);
    }

    @Operation(summary = "刷新token")
    @PatchMapping(value = "/account/auth/token")
    @ResponseBody
    public CommonResult<ResponseTokenInfo> refreshToken(HttpServletRequest request) {
        String authorization = request.getHeader(securityProperties.getTokenHeader());
        String refreshToken = accountService.refreshToken(authorization);
        ResponseTokenInfo tokenInfo =
                new ResponseTokenInfo(refreshToken, securityProperties.getTokenHead());
        return CommonResult.success(tokenInfo);
    }

    @Operation(summary = "获取组织")
    @GetMapping("/account/organizations")
    public CommonResult<List<AccountOrganization>> getAccountOrganizations(@RequestHeader Map<String, String> headers) {
        String bearer = headers.get(securityProperties.getTokenHeader().toLowerCase());
        List<AccountOrganization> accountOrganizations = accountService.getAccountOrganizations(bearer);
        return CommonResult.success(accountOrganizations);
    }

    @GetMapping(value="/accounts/auth/code")
    @Operation(summary = "获取注册验证码")
    public CommonResult<String> generateAuthCode(@Schema(description = "验证类型", required = true, allowableValues = {"phone", "email"})
                                                 @RequestParam("type") IdentifyType type,
                                                 @Schema(description = "手机号码或者email", required = true)
                                                 @RequestParam("body") String phoneOrMail){
        //目前通过phone获取验证码还没有开放
        if (type.name().equals(IdentifyType.email.name())) {
            accountService.sendAccountRegisterMail(phoneOrMail);
        }
        return CommonResult.success(null);
    }
    @Operation(summary = "注册用户")
    @PostMapping("/accounts")
    public CommonResult<String> register(@RequestBody UmsAccountRegisterParam accountRegisterParam){
        accountService.register(accountRegisterParam);
        return CommonResult.success(null);
    }

    @Operation(summary = "已认证发送绑定手机验证码", description = "未开通")
    @GetMapping("/authCodes/binding/phone")
    public CommonResult<String> sendBindPhoneCode(){
        return CommonResult.failed("未开通");
    }
    @Operation(summary = "已认证绑定电话号码")
    @PatchMapping("/account/auths/phone")
    public CommonResult<String> updatePhone(@RequestHeader Map<String, String> headers,
                                            @RequestParam("phone") String phone,
                                            @RequestParam("authCode") String authCode){
        accountService.bindPhone(phone, authCode, headers.get(securityProperties.getTokenHeader().toLowerCase()));
        return CommonResult.success(null);
    }
    @Operation(summary = "已认证更新用户名")
    @PatchMapping("/account/auths/accountname")
    public CommonResult<String> updateUsername(@RequestHeader Map<String, String> headers,
                                               @RequestParam("newUsername") String newUsername){
        accountService.updateUsername(newUsername, headers.get(securityProperties.getTokenHeader().toLowerCase()));
        return CommonResult.success(null);
    }
    @Operation(summary = "已认证发送绑定邮箱验证码")
    @GetMapping("/authCodes/binding/email")
    public CommonResult<String> sendBindEmailCode(@RequestHeader Map<String, String> headers,
                                                  @RequestParam("email") String email){
        accountService.sendBindEmailCode(email, headers.get(securityProperties.getTokenHeader().toLowerCase()));
        return CommonResult.success(null);
    }

    @Operation(summary = "已认证绑定邮箱")
    @PatchMapping("/account/auths/email")
    public CommonResult<String> updateEmail(@RequestHeader Map<String, String> headers,
                                            @RequestParam("email") String email,
                                            @RequestParam("authCode") String authCode){
        accountService.bindEmail(email, authCode, headers.get(securityProperties.getTokenHeader().toLowerCase()));
        return CommonResult.success(null);
    }
    @Operation(summary = "已认证更新用户资料")
    @PatchMapping("/account")
    public CommonResult<String> updateAccount(@RequestBody UmsAccount account,
                                           @RequestHeader Map<String, String> headers){
        String authorization = headers.get(securityProperties.getTokenHeader().toLowerCase());
        accountService.updateAccount(account, authorization);
        return CommonResult.success(null);
    }

    @Operation(summary = "已认证获取用户认证")
    @GetMapping("/account/auths")
    public CommonResult<Map<String, String>> getAccountAuths(@RequestHeader Map<String, String> headers){
        String authorization = headers.get(securityProperties.getTokenHeader().toLowerCase());
        Map<String, String> accountAuth = accountService.getVerifiedAccountAuths(authorization);
        return CommonResult.success(accountAuth);
    }
}
