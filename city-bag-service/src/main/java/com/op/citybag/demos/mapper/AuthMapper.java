package com.op.citybag.demos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.op.citybag.demos.model.Entity.Auth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper extends BaseMapper<Auth> {
}
