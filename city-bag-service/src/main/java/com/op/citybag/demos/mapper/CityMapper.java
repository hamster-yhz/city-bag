package com.op.citybag.demos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.op.citybag.demos.model.Entity.City;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CityMapper extends BaseMapper<City> {
}
