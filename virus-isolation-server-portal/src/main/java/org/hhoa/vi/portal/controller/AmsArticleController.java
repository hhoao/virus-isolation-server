package org.hhoa.vi.portal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hhoa.vi.common.api.CommonPage;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.mgb.model.generator.AmsArticle;
import org.hhoa.vi.mgb.model.generator.AmsComment;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.portal.service.AmsArticleService;
//import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Ams article controller.
 *
 * @author hhoa
 * @since 2022 /9/3
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "文章管理", description = "AmsArticleController")
public class AmsArticleController {
    private final AmsArticleService articleService;

    @Operation(description = "分页获取文章列表", summary = "分页获取文章列表")
    @GetMapping("/articles")
    public CommonResult<CommonPage<AmsArticle>> list(PageInfo pageInfo,
                                                     AmsArticle articleParams,
                                                     @Parameter(name = "base") String base) {
        List<AmsArticle> amsArticles = articleService.list(articleParams, pageInfo);
        return CommonResult.success(CommonPage.restPage(amsArticles));
    }

    @Operation(summary = "通过Id获取文章")
    @GetMapping("/articles/{articleId}")
    public CommonResult<AmsArticle> selectById(@PathVariable("articleId") Long articleId) {
        AmsArticle article = articleService.selectById(articleId);
        return CommonResult.success(article);
    }
    @Operation(summary = "分页获取文章评论")
    @GetMapping("/articles/{articleId}/comments")
    public CommonResult<CommonPage<AmsComment>> getArticleComments(PageInfo pageInfo,
                                                                   @PathVariable("articleId") Long articleId) {
        List<AmsComment> articles = articleService.getArticleComments(articleId, pageInfo);
        return CommonResult.success(CommonPage.restPage(articles));
    }
}
