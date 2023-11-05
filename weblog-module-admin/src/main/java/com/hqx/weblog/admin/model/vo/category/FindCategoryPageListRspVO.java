package com.hqx.weblog.admin.model.vo.category;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "响应 VO")
public class FindCategoryPageListRspVO {
    /**
     * 分类 ID
     */
    private Long id;
    
    /**
     * 分类名称
     */
    private String name;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}