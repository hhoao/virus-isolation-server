package org.hhoa.vi.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.service.OmsOrganizationService;
import org.hhoa.vi.common.api.CommonPage;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.mgb.model.OrganizationAccount;
import org.hhoa.vi.mgb.model.generator.OmsOrganization;
import org.hhoa.vi.mgb.model.generator.UmsAccount;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * OmsOrganizationController
 *
 * @author hhoa
 * @since 2023/3/19
 **/


@RestController
@AllArgsConstructor
@Tag(name = "组织管理", description = "OmsOrganizationController")
public class OmsOrganizationController {
    private OmsOrganizationService organizationService;

    @Operation(summary = "分页获取组织列表")
    @GetMapping("/organizations")
    public CommonResult<CommonPage<OmsOrganization>> list(PageInfo pageInfo,
                                                          OmsOrganization organizationParam) {
        List<OmsOrganization> organizations = organizationService.list(organizationParam, pageInfo);
        return CommonResult.success(CommonPage.restPage(organizations));
    }

    @Operation(summary = "添加组织")
    @PostMapping("/organizations")
    public CommonResult<String> addOrganization(@RequestBody @Validated OmsOrganization organizationParam) {
        organizationService.addOrganization(organizationParam);
        return CommonResult.success(null);
    }

    @Operation(summary = "修改组织")
    @PutMapping("/organizations")
    public CommonResult<String> updateOrganization(@RequestBody OmsOrganization organizationParam) {
        organizationService.updateOrganization(organizationParam);
        return CommonResult.success(null);
    }

    @Operation(summary = "删除组织")
    @DeleteMapping("/organizations/{organizationId}")
    public CommonResult<String> delOrganization(@PathVariable Long organizationId) {
        organizationService.deleteOrganization(organizationId);
        return CommonResult.success(null);
    }

    @Operation(summary = "分页获取组织所有成员")
    @GetMapping("/organizations/{organizationId}/accounts")
    public CommonResult<CommonPage<OrganizationAccount>> listOrganizationAccounts(
            PageInfo pageInfo,
            @PathVariable(value = "organizationId") Long organizationId,
            UmsAccount accountParam
    ) {
        List<OrganizationAccount> accounts =
                organizationService.listOrganizationAccounts(
                        pageInfo, organizationId, accountParam);
        return CommonResult.success(CommonPage.restPage(accounts));
    }

    @Operation(summary = "通过用户名删除组织成员")
    @DeleteMapping("/organizations/{organizationId}/accounts/{accountId}")
    public CommonResult<String> deleteOrganizationAccount(@PathVariable("organizationId") Long organizationId,
                                                          @PathVariable("accountId") Long accountId) {
        organizationService.deleteOrganizationAccountByUserId(organizationId, accountId);
        return CommonResult.success(null);
    }

    @Operation(summary = "更新组织成员职位")
    @PatchMapping("/organizations/{organizationId}/accounts/{accountId}")
    public CommonResult<String> updateAccountJob(@PathVariable("organizationId") Long organizationId,
                                                 @PathVariable(value = "accountId") Long accountId,
                                                 @Parameter(ref = "accountId") Long positionId) {
        organizationService.updateOrganizationAccountPosition(organizationId, accountId, positionId);
        return CommonResult.success(null);
    }
}
