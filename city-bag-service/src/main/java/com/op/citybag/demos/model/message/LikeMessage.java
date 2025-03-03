package com.op.citybag.demos.model.message;

import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeMessage implements Serializable {

    /**
     * 点赞消息
     */
    private String entityId;
    private int delta;
    private long timestamp;

}
