package com.op.citybag.demos.model.VO.page.cover;

import lombok.*;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/2 21:15
 * @Version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ScenicSpotCoverVO {

    private String scenicSpotId;

    private String scenicSpotName;

    private String visitTime;

    private String address;

    private String scenicSpotImg;

    private Integer IsCollect;

}
