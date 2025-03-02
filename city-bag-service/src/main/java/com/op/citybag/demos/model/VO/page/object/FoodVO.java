package com.op.citybag.demos.model.VO.page.object;

import lombok.*;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/2 21:14
 * @Version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FoodVO {

    private String foodId;

    private String foodName;

    private String introduce;

    private String foodImg;

    private Integer likeCount;

    private String cityId;
}
