package org.hhoa.vi.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.service.OmsDocumentService;
import org.hhoa.vi.common.api.CommonPage;
import org.hhoa.vi.common.api.CommonResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * OmsDocumentController
 *
 * @author hhoa
 * @since 2023/3/19
 **/

@AllArgsConstructor
@Tag(name = "文档管理", description = "OmsDocumentController")
public class OmsDocumentController {

    private OmsDocumentService documentService;

    @Operation(summary = "分页获取文档列表")
    @GetMapping("/documents/{type}")
    public CommonResult<CommonPage<String>> list(@PathVariable("type") String documentType,
                                                 PageInfo pageInfo,
                                                 String filter) {
        List<String> documents = documentService.list(documentType, filter, pageInfo);
        return CommonResult.success(CommonPage.restPage(documents));
    }

    @Operation(summary = "添加文档")
    @PostMapping("/documents/{type}")
    public CommonResult<String> addDocument(@PathVariable("type") String documentType,
                                            @RequestBody @Validated String documentParam) {
        documentService.addDocument(documentType, documentParam);
        return CommonResult.success();
    }

    @Operation(summary = "修改文档")
    @PutMapping("/documents/{type}")
    public CommonResult<String> updateDocument(@PathVariable("type") String documentType,
                                               @RequestBody String documentParam, String filter) {
        documentService.updateDocument(documentType, filter, documentParam);
        return CommonResult.success();
    }

    @Operation(summary = "删除文档")
    @DeleteMapping("/documents/{type}/{documentId}")
    public CommonResult<String> delDocument(@PathVariable("type") String documentType,
                                            @PathVariable String documentParam) {
        documentService.deleteDocument(documentType, documentParam);
        return CommonResult.success();
    }
}
