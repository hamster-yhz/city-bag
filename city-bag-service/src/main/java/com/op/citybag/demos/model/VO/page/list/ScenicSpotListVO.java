package com.op.citybag.demos.model.VO.page.list;

import com.op.citybag.demos.model.Entity.ScenicSpot;
import com.op.citybag.demos.model.VO.page.cover.ScenicSpotCoverVO;
import lombok.*;

import java.util.List;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/2/27 16:49
 * @Version: 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ScenicSpotListVO {

    // 城市id
    private String cityId;
    // 城市名称
    private String cityName;

    // 景点列表
    private List<ScenicSpotCoverVO> scenicSpotList;

    // 分页信息
    private Integer pageNum;
    private Integer pageSize;
    private Long total;

}
