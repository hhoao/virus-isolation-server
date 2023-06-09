package org.hhoa.vi.search.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.hhoa.vi.common.api.CommonPage;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.search.bean.EsArticle;
import org.hhoa.vi.search.bean.EsPage;
import org.hhoa.vi.search.service.EsArticleService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * EsArticleController.
 *
 * @author hhoa
 * @since 2022 /8/4
 */
@Tags({@Tag(name = "文章搜索管理", description = "ArticleController")})
@RestController
@RequiredArgsConstructor
public class EsArticleController {
    private final EsArticleService esArticleService;

    @Operation(summary = "分页获取搜索文章")
    @GetMapping("/search/articles")
    public CommonResult<CommonPage<EsArticle>> search(
            String queryInfo,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNum,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize
    ) {
        EsPage page = new EsPage(pageNum, pageSize);
        Page<EsArticle> search = esArticleService.search(queryInfo, page);
        return CommonResult.success(CommonPage.restPage(search));
    }
}
