package org.hhoa.vi.common.api;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页数据封装类.
 *
 * @author hhoa
 */
@Schema(description = "分页数据")
@Data
@NoArgsConstructor
public class CommonPage<T> {
    /**
     * 当前页码.
     */
    @Schema(description = "页数")
    private Integer pageNum;
    /**
     * 每页数量.
     */
    @Schema(description = "单页大小")
    private Integer pageSize;
    /**
     * 总页数.
     */
    @Schema(description = "总页数")
    private Integer totalPage;
    /**
     * 总条数.
     */
    @Schema(description = "总条数")
    private Long total;
    /**
     * 分页数据.
     */
    @Schema(description = "分页数据")
    private List<T> list;

    /**
     * 将PageHelper分页后的list转为分页信息.
     */
    @JsonIgnore
    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> result = new CommonPage<>();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

    /**
     * 将SpringData分页后的list转为分页信息.
     */
    @JsonIgnore
    public static <T> CommonPage<T> restPage(Page<T> pageInfo) {
        CommonPage<T> result = new CommonPage<>();
        result.setTotalPage(pageInfo.getTotalPages());
        result.setPageNum(pageInfo.getNumber());
        result.setPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotalElements());
        result.setList(pageInfo.getContent());
        return result;
    }
}

