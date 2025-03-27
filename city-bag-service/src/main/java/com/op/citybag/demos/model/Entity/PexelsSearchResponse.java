package com.op.citybag.demos.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/25 19:42
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PexelsSearchResponse {

    // 图片列表
    private List<PexelsPhoto> photos;

    // 图片总数
    private Integer totalResults;

    // 当前页码
    private Integer page;

    // 每页图片数量
    private Integer perPage;

}

