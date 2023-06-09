package org.hhoa.vi.admin.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.bean.UmsResourceParam;
import org.hhoa.vi.admin.service.UmsResourceService;
import org.hhoa.vi.common.api.CommonPage;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.mgb.model.generator.UmsResource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 资源管理.
 *
 * @author hhoa
 * @since 2022/5/16
 **/
@RestController
@AllArgsConstructor
@Tag(description = "UmsResourceController", name = "资源管理")
public class UmsResourceController {
    private final UmsResourceService resourceService;

    @Operation(description = "分页获取资源列表", summary = "分页获取资源列表")
    @GetMapping("/resources")
    public CommonResult<CommonPage<UmsResource>> list(PageInfo pageInfo,
                                                      UmsResource resource) {
        List<UmsResource> allResources = resourceService.list(pageInfo, resource);
        return CommonResult.success(CommonPage.restPage(allResources));
    }

    @Operation(description = "添加资源", summary = "添加资源")
    @PostMapping("/resources")
    public CommonResult<String> addResource(@RequestBody UmsResourceParam resourceParam) {
        resourceService.addResource(resourceParam);
        return CommonResult.success(null);
    }

    @Operation(summary = "修改资源")
    @PatchMapping("/resources/{resourceName}")
    public CommonResult<String> updateResource(@PathVariable("resourceName") String resourceName,
                                               @RequestBody UmsResourceParam resourceParam) {
        resourceService.updateResource(resourceName, resourceParam);
        return CommonResult.success(null);
    }

    @Operation(summary = "删除资源")
    @DeleteMapping("/resources/{resourceName}")
    public CommonResult<String> delResource(@PathVariable("resourceName") String resourceName) {
        resourceService.deleteResource(resourceName);
        return CommonResult.success(null);
    }
}
