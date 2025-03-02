package com.op.citybag.demos.model.VO.page;

import com.op.citybag.demos.model.Entity.Food;
import lombok.*;

import java.util.List;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/2/27 16:50
 * @Version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FoodListVO {

    // 城市id
    private String cityId;
    // 城市名称
    private String cityName;

    // 食物列表
    private List<Food> foodList;

    // 分页信息
    private Integer pageNum;
    private Integer pageSize;
    private Long total;

}
