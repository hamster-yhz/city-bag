package com.op.citybag.demos.model.VO.page.list;

import com.op.citybag.demos.model.VO.page.cover.CityCoverVO;
import com.op.citybag.demos.model.VO.page.cover.DormitoryCoverVO;
import lombok.*;

import java.util.List;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/10 19:08
 * @Version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CityListVO {

    // 城市列表
    private List<CityCoverVO> cityList;

    // 分页信息
    private Integer pageNum;
    private Integer pageSize;
    private Long total;

}
