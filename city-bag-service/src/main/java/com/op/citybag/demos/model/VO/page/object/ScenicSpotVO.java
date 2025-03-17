package com.op.citybag.demos.model.VO.page.object;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class ScenicSpotVO {

    private String scenicSpotId;

    private String scenicSpotName;

    private String introduce;

    private String openTime;

    private String price;

    private String address;

    private String phone;

    private String visitTime;

    private String scenicSpotList;

    private List<String> photoUrl;

    private List<String> tagList;

    private Integer photoCount;

    private Integer tagCount;

    private Integer IsCollect;

    private String scenicSpotImg;

    private Integer type;

    private Integer likeCount;

    private String cityId;
}
