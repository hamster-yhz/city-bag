package com.op.citybag.demos.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.op.citybag.demos.model.Entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/1/18 21:27
 * @Version: 1.0
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
