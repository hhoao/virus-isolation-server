package org.hhoa.vi.admin.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.AllArgsConstructor;
import org.hhoa.vi.admin.bean.AccountAuthWrapper;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.bean.ResponseTokenInfo;
import org.hhoa.vi.admin.bean.UmsAccountWrapper;
import org.hhoa.vi.admin.bean.UmsLoginParam;
import org.hhoa.vi.admin.service.UmsAccountService;
import org.hhoa.vi.common.api.CommonPage;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.hhoa.vi.security.config.JwtSecurityProperties;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
@AllArgsConstructor
@Tags({@Tag(name = "账户管理", description = "UmsAccountController")})
@RestController
public class UmsAccountController {
    private final UmsAccountService accountService;
    private final JwtSecurityProperties securityProperties;

    @Operation(summary = "已认证获取账户信息")
    @GetMapping("/account")
    public CommonResult<UmsAccountWrapper> getAccount(@RequestHeader Map<String, String> headers) {
        String authorization = securityProperties.getTokenHeader().toLowerCase();
        UmsAccountWrapper accountByAuthorization = accountService.getAccountByAuthorization(
                headers.get(authorization));
        return CommonResult.success(accountByAuthorization);
    }

    @Operation(summary = "添加账户")
    @PostMapping("/accounts")
    public CommonResult<String> addAccount(@RequestBody AccountAuthWrapper accountAuthWrapper) {
        accountService.addAccount(accountAuthWrapper);
        return CommonResult.success(null);
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

    @Operation(summary = "分页获取账户列表")
    @GetMapping("/accounts")
    public CommonResult<CommonPage<UmsAccount>> getAccountByAccountParam(UmsAccount account,
                                                                         PageInfo pageInfo) {
        List<UmsAccount> accounts = accountService.list(pageInfo, account);
        return CommonResult.success(CommonPage.restPage(accounts));
    }

    @Operation(summary = "更新账户资料")
    @PatchMapping("/accounts")
    public CommonResult<String> updateAccount(@RequestBody AccountAuthWrapper accountAuthWrapper) {
        accountService.updateAccount(accountAuthWrapper);
        return CommonResult.success(null);
    }

    @Operation(summary = "删除账户")
    @DeleteMapping("/accounts/{accountId}")
    public CommonResult<String> deleteAccount(@PathVariable("accountId") Long accountId) {
        accountService.deleteAccountByAccountId(accountId);
        return CommonResult.success(null);
    }
}
