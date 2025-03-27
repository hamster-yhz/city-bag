package com.op.citybag.demos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.op.citybag.demos.model.Entity.UserCollection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserCollectionMapper extends BaseMapper<UserCollection> {
    @Delete("DELETE FROM user_collection WHERE user_id = #{userId} AND entity_type = #{entityType} AND entity_id = #{entityId}")
    int physicalDelete(@Param("userId") String userId,
                       @Param("entityType") String entityType,
                       @Param("entityId") String entityId);


}
