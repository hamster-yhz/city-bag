package com.op.citybag.demos.model.VO.page;

import com.op.citybag.demos.model.Entity.Dormitory;
import lombok.*;

import java.util.List;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/2/27 16:55
 * @Version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DormitoryListVO {

    // 城市id
    private String cityId;
    // 城市名称
    private String cityName;

    // 景点列表
    private List<Dormitory> dormitoryList;

    // 分页信息
    private Integer pageNum;
    private Integer pageSize;
    private Long total;

}
