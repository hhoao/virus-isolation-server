package org.hhoa.vi.admin.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.service.AmsCatalogService;
import org.hhoa.vi.common.api.CommonPage;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.mgb.model.generator.AmsArticle;
import org.hhoa.vi.mgb.model.generator.AmsCatalog;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Ams catalog controller.
 *
 * @author hhoa
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "目录管理", description = "AmsCatalogController")
public class AmsCatalogController {
    private final AmsCatalogService catalogService;

    @Operation(description = "分页获取目录列表", summary = "分页获取目录列表")
    @GetMapping("/catalogs")
    public CommonResult<CommonPage<AmsCatalog>> list(PageInfo pageInfo,
                                                     AmsCatalog catalogParams) {
        List<AmsCatalog> amsCatalogs = catalogService.selectList(catalogParams, pageInfo);
        return CommonResult.success(CommonPage.restPage(amsCatalogs));
    }

    @Operation(description = "添加目录", summary = "添加目录")
    @PostMapping("/catalogs")
    public CommonResult<String> addCatalog(@RequestBody AmsCatalog catalogParam) {
        catalogService.addCatalog(catalogParam);
        return CommonResult.success(null);
    }

    @Operation(summary = "修改目录")
    @PatchMapping("/catalogs")
    public CommonResult<String> updateCatalog(@RequestBody AmsCatalog catalogParam) {
        catalogService.updateCatalog(catalogParam);
        return CommonResult.success(null);
    }

    @Operation(summary = "删除目录")
    @DeleteMapping("/catalogs/{catalogId}")
    public CommonResult<String> delCatalog(@PathVariable Long catalogId) {
        catalogService.deleteCatalog(catalogId);
        return CommonResult.success(null);
    }

    @Operation(summary = "分页获取目录文章")
    @GetMapping("/catalogs/{catalogId}/articles")
    public CommonResult<CommonPage<AmsArticle>> getCatalogArticles(
            PageInfo pageInfo,
            @PathVariable Long catalogId) {
        List<AmsArticle> articles = catalogService.getCatalogArticles(catalogId, pageInfo);
        return CommonResult.success(CommonPage.restPage(articles));
    }
}
