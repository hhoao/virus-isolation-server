package org.hhoa.vi.portal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.hhoa.vi.common.api.CommonPage;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.mgb.model.OrganizationAccount;
import org.hhoa.vi.mgb.model.generator.OmsOrganization;
import org.hhoa.vi.portal.bean.OmsCreateOrganizationParam;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.portal.service.OmsOrganizationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * OmsOrganizationController
 *
 * @author hhoa
 * @since 2023/3/19
 **/


@RestController
@AllArgsConstructor
public class OmsOrganizationController {
    private OmsOrganizationService organizationService;

    @Operation(summary = "创建组织")
    @PostMapping("/organizations")
    public CommonResult<String> addOrganization(@RequestBody @Validated
                                                    OmsCreateOrganizationParam organizationParam) {
        organizationService.addOrganization(organizationParam);
        return CommonResult.success(null);
    }

    @Operation(summary = "修改组织")
    @PutMapping("/organizations/{organizationId}")
    public CommonResult<String> updateOrganization(@RequestBody OmsOrganization organizationParam,
                                                   @PathVariable("organizationId") Long organizationId) {
        organizationService.updateOrganization(organizationParam, organizationId);
        return CommonResult.success(null);
    }

    @Operation(summary = "删除当前组织")
    @DeleteMapping("/organizations/{organizationId}")
    public CommonResult<String> delOrganization(@PathVariable Long organizationId,
                                                @RequestHeader Map<String, String> headers) {
        organizationService.deleteOrganization(organizationId);
        return CommonResult.success(null);
    }

    @Operation(summary = "分页获取组织所有成员")
    @GetMapping("/organizations/{organizationId}/accounts")
    public CommonResult<CommonPage<OrganizationAccount>> listAccounts(PageInfo pageInfo,
                                                                      @PathVariable(value="organizationId") Long organizationId
                                                            ){
        List<OrganizationAccount> accounts = organizationService.listOrganizationAccounts(pageInfo, organizationId);
        return CommonResult.success(CommonPage.restPage(accounts));
    }

    @Operation(summary = "通过用户Id删除组织成员")
    @DeleteMapping("/organizations/{organizationId}/accounts/{accountId}")
    public CommonResult<String> deleteAccount(@PathVariable("organizationId") Long organizationId,
                                             @PathVariable("accountId") Long accountId){
        organizationService.deleteOrganizationAccountByUserId(organizationId, accountId);
        return CommonResult.success(null);
    }
    @Operation(summary = "更新组织成员职位")
    @PatchMapping("/organizations/{organizationId}/accounts/{accountId}")
    public CommonResult<String> updateAccountJob(@PathVariable("organizationId") Long organizationId,
                                                 @PathVariable(value = "accountId") Long accountId,
                                                 @Parameter(ref = "accountId") Long positionId){
        organizationService.updateOrganizationAccountPosition(organizationId, accountId, positionId);
        return CommonResult.success(null);
    }
}
