package com.op.citybag.demos.model.VO.page.object;

import lombok.*;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/2/26 21:26
 * @Version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CityVO {

    private String cityId;

    private String cityName;

    private String cityImg;

    private String cityIntroduce;

    private String foodIntroduce;

    private String scenicSpotIntroduce;

    private Integer likeCount;

    private Integer IsCollect;

}
