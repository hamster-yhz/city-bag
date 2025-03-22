package com.op.citybag.demos.model.VO.user;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/22 17:41
 * @Version: 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserVisitRecordVO {

    /**
     * 记录id
     */
    private String VisiRecordId;

    /**
     * 实体类型
     */
    private String entityType;

    /**
     * 实体id
     */
    private String entityId;

    /**
     * 实体名称
     */
    private String entityName;

    /**
     * 实体图片
     */
    private String entityImg;

    /**
     * 浏览时间
     */
    private LocalDateTime collectionTime;
}
