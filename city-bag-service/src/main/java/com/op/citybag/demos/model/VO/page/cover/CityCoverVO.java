package com.op.citybag.demos.model.VO.page.cover;

import lombok.*;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/23 21:09
 * @Version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CityCoverVO {

    private String cityId;

    private String cityName;

    private String cityImg;

}
