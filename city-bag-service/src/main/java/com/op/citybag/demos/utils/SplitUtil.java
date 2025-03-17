package com.op.citybag.demos.utils;

import java.util.List;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/17 19:39
 * @Version: 1.0
 */
public class SplitUtil {

    public static List<String> splitBySlash(String str) {
        return List.of(str.split("/"));
    }

    public static List<String> splitByUnderscore(String str) {
        return List.of(str.split("_"));
    }
}
