package org.hhoa.vi.portal.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hhoa.vi.common.api.CommonPage;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.mgb.model.generator.AmsTag;
import org.hhoa.vi.portal.bean.PageInfo;
import org.hhoa.vi.portal.service.AmsTagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文章标签管理Controller.
 *
 * @author hhoa
 * @since 2022/12/12
 **/

@RestController
@RequiredArgsConstructor
@Tag(name = "文章标签管理", description = "AmsTagController")
public class AmsTagController {
    private final AmsTagService tagService;

    @Operation(summary = "分页获取标签列表")
    @GetMapping("/tags")
    public CommonResult<CommonPage<AmsTag>> list(PageInfo pageInfo,
                                                 AmsTag tag) {
        List<AmsTag> tags = tagService.list(tag, pageInfo);
        return CommonResult.success(CommonPage.restPage(tags));
    }
}
