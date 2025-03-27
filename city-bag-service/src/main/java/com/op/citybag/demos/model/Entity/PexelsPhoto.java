package com.op.citybag.demos.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Map;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/25 19:41
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PexelsPhoto {

    //图片id
    private Long id;

    //作者
    private String photographer;

    //图片链接
    private Map<String, String> src;

    //图片描述
    private String alt;

}
