package com.op.citybag.demos.service;

import com.op.citybag.demos.model.Entity.PexelsSearchResponse;
import com.op.citybag.demos.model.VO.page.cover.CityCoverVO;
import com.op.citybag.demos.model.VO.page.list.CityListVO;
import com.op.citybag.demos.model.VO.page.list.DormitoryListVO;
import com.op.citybag.demos.model.VO.page.list.FoodListVO;
import com.op.citybag.demos.model.VO.page.list.ScenicSpotListVO;
import com.op.citybag.demos.model.VO.page.object.CityVO;
import com.op.citybag.demos.model.VO.page.object.DormitoryVO;
import com.op.citybag.demos.model.VO.page.object.FoodVO;
import com.op.citybag.demos.model.VO.page.object.ScenicSpotVO;

import java.util.List;

public interface ICityService {

    /**
     * 查询单个城市信息(精确匹配)
     *
     * @param cityId
     * @return 城市信息
     **/
    CityVO querySingleCity(String userId, String cityId);

    /**
     * 查询城市信息(模糊匹配)
     *
     * @param cityName
     * @return 城市信息
     */
    CityListVO queryCityLike(String cityName, Integer pageNum, Integer pageSize);

    /**
     * 查询城市信息
     *
     * @param cityName
     * @return
     */
    CityListVO queryCityLike(String cityName);


    /**
     * 查询城市对应的景点
     *
     * @param cityId
     * @return 城市对应的景点
     */
    ScenicSpotListVO queryCityScenicSpot(String userId, String cityId, Integer pageNum, Integer pageSize);

    /**
     * 查询城市对应的美食
     *
     * @param cityId
     * @return 城市对应的美食
     */
    FoodListVO queryCityFood(String userId, String cityId, Integer pageNum, Integer pageSize);

    /**
     * 查询城市对应的住宿
     *
     * @param cityId
     * @return 城市对应的住宿
     */
    DormitoryListVO queryCityDormitory(String userId, String cityId, Integer pageNum, Integer pageSize);

    /**
     * 查询单个景点信息
     *
     * @param scenicSpotId
     * @return 景点信息
     */
    ScenicSpotVO querySingleScenicSpot(String scenicSpotId, String userId);

    /**
     * 查询单个美食信息
     *
     * @param foodId
     * @return 美食信息
     */
    FoodVO querySingleFood(String foodId, String userId);

    /**
     * 查询单个住宿信息
     *
     * @param dormitoryId
     * @return 住宿信息
     */
    DormitoryVO querySingleDormitory(String dormitoryId, String userId);

    /**
     * 获取点赞量最高的前五个城市
     *
     * @return 城市信息列表
     */
    List<CityCoverVO> getTopCitiesByLikes();

    /**
     * 获取固定城市
     *
     * @return
     */
    List<CityCoverVO> acquiringFixedCity();


    String searchPhotos(String query);
}
