package com.op.citybag.demos.model.VO.page.list;

import com.op.citybag.demos.model.VO.user.CollectionVO;
import lombok.*;

import java.util.List;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/15 22:06
 * @Version: 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CollectionListVO {


    /**
     * 收藏列表
     */
    private List<CollectionVO> collectionList;

    // 分页信息
    private Integer pageNum;
    private Integer pageSize;
    private Long total;
}
