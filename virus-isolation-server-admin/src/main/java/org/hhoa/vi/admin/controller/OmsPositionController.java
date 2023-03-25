package org.hhoa.vi.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.service.OmsPositionService;
import org.hhoa.vi.common.api.CommonPage;
import org.hhoa.vi.common.api.CommonResult;
import org.hhoa.vi.mgb.model.generator.OmsPosition;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * OmsPositionController
 *
 * @author hhoa
 * @since 2023/3/19
 **/

@RestController
@Tag(name = "职位管理", description = "OmsOrganizationController")
@AllArgsConstructor
public class OmsPositionController {
    private OmsPositionService positionService;

    @Operation(summary = "分页获取职位列表")
    @GetMapping("/positions")
    public CommonResult<CommonPage<OmsPosition>> list(PageInfo pageInfo,
                                                          OmsPosition positionParam) {
        List<OmsPosition> positions = positionService.list(positionParam, pageInfo);
        return CommonResult.success(CommonPage.restPage(positions));
    }

    @Operation(summary = "添加职位")
    @PostMapping("/positions")
    public CommonResult<String> addPosition(@RequestBody @Validated OmsPosition positionParam) {
        positionService.addPosition(positionParam);
        return CommonResult.success(null);
    }

    @Operation(summary = "修改职位")
    @PutMapping("/positions")
    public CommonResult<String> updatePosition(@RequestBody OmsPosition positionParam) {
        positionService.updatePosition(positionParam);
        return CommonResult.success(null);
    }

    @Operation(summary = "删除职位")
    @DeleteMapping("/positions/{positionId}")
    public CommonResult<String> delPosition(@PathVariable Long positionId) {
        positionService.deletePosition(positionId);
        return CommonResult.success(null);
    }
}
