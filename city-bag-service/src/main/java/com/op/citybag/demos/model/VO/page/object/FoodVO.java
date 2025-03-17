package com.op.citybag.demos.model.VO.page.object;

import lombok.*;

import java.util.List;

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

    private String openTime;

    private String price;

    private String phone;

    private String foodList;

    private List<String> tagList;

    private Integer tagCount;

    private Integer IsCollect;

    private Integer likeCount;

    private String cityId;
}
