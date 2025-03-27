package com.op.citybag.demos.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: 严豪哲
 * @Description:
 * @Date: 2025/1/18 18:33
 * @Version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
public class OPResult {

    private String code;
    private String msg;
    private Object data;



    public static OPResult SUCCESS() {
        return OPResult.builder()
                .code("200")
                .msg("success")
                .build();
    }

    public static OPResult SUCCESS(Object data) {
        return OPResult.builder()
               .code("200")
               .msg("success")
               .data(data)
               .build();
    }

    public static OPResult FAIL() {
        return OPResult.builder()
              .code("500")
              .msg("fail")
              .build();
    }

    public static OPResult FAIL(Object data) {
        return OPResult.builder()
             .code("500")
             .msg("fail")
             .data(data)
             .build();
    }

    public static OPResult RESPONSE(String code,String msg) {
        return OPResult.builder()
             .code(code)
             .msg(msg)
             .build();
    }

}
