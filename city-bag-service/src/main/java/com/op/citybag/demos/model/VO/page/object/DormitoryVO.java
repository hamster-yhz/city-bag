package com.op.citybag.demos.model.VO.page.object;

import lombok.*;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/2 21:11
 * @Version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DormitoryVO {

    private String dormitoryId;

    private String dormitoryName;

    private String introduce;

    private String DormitoryImg;

    private Integer type;

    private Integer likeCount;

    private String cityId;
}
