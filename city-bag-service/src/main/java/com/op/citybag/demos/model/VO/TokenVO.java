package com.op.citybag.demos.model.VO;

import lombok.*;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/1/20 16:58
 * @Version: 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TokenVO {

    private String token;

    private String userId;

    private String openid;

    private String phone;
}
