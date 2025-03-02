package com.op.citybag.demos.service;

import com.op.citybag.demos.model.VO.page.list.DormitoryListVO;
import com.op.citybag.demos.model.VO.page.list.FoodListVO;
import com.op.citybag.demos.model.VO.page.list.ScenicSpotListVO;
import com.op.citybag.demos.model.VO.page.object.CityVO;

public interface ICityService {

    /**
     * 查询单个城市信息(精确匹配)
     * @param cityName
     * @return 城市信息
     **/
    CityVO querySingleCity(String cityName);

    /**
     * 查询城市对应的景点
     * @param cityId
     * @return 城市对应的景点
     */
    ScenicSpotListVO queryCityScenicSpot(String cityId);

    /**
     * 查询城市对应的美食
     * @param cityId
     * @return 城市对应的美食
     */
    FoodListVO queryCityFood(String cityId);

    /**
     * 查询城市对应的住宿
     * @param cityId
     * @return 城市对应的住宿
     */
    DormitoryListVO queryCityDormitory(String cityId);

}
