package com.op.citybag.demos.controller;

import com.op.citybag.demos.common.DTO.city.*;
import com.op.citybag.demos.common.OPResult;
import com.op.citybag.demos.model.VO.page.cover.CityCoverVO;
import com.op.citybag.demos.model.VO.page.list.CityListVO;
import com.op.citybag.demos.model.VO.page.list.DormitoryListVO;
import com.op.citybag.demos.model.VO.page.list.FoodListVO;
import com.op.citybag.demos.model.VO.page.list.ScenicSpotListVO;
import com.op.citybag.demos.model.VO.page.object.CityVO;
import com.op.citybag.demos.model.VO.page.object.DormitoryVO;
import com.op.citybag.demos.model.VO.page.object.FoodVO;
import com.op.citybag.demos.model.VO.page.object.ScenicSpotVO;
import com.op.citybag.demos.service.ICityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: 原神
 * @Description: 城市控制器
 * @Date: 2025/2/26 20:02
 * @Version: 1.0
 */

//${app.config.api-version}
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/v1/city/")
@RequiredArgsConstructor
@Validated
public class CityController {

    @Autowired
    private final ICityService cityService;

    /**
     * 查询单个城市信息(精确匹配)
     *
     * @param querySingleCityDTO
     * @return 城市信息
     */
    @PostMapping("/querysinglecity")
    public OPResult querySingleCity(@Valid @RequestBody QuerySingleCityDTO querySingleCityDTO) {

            log.info("查询单个城市信息开始");

            CityVO cityVO = cityService.querySingleCity(querySingleCityDTO.getUserId(), querySingleCityDTO.getCityId());

            log.info("查询单个城市信息成功");
            return OPResult.SUCCESS(cityVO);

    }

    /**
     * 查询城市信息(模糊匹配)
     *
     * @param queryCityLikeDTO
     * @return 城市信息
     */
    @PostMapping("/queryCityLike")
    public OPResult queryCityLike(@Valid @RequestBody QueryCityLikeDTO queryCityLikeDTO) {

            log.info("查询城市信息开始");

            //查询城市信息(分页查询)
//            CityListVO cityListVO = cityService.queryCityLike(queryCityLikeDTO.getCityName(), queryCityLikeDTO.getPageNum(), queryCityLikeDTO.getPageSize());

            //查询城市信息(默认查询)
            CityListVO cityListVO = cityService.queryCityLike(queryCityLikeDTO.getCityName());

            log.info("查询城市信息成功");
            return OPResult.SUCCESS(cityListVO);

    }


    /**
     * 查询城市对应的景点
     *
     * @param queryCityContentDTO
     * @return
     */
    @PostMapping("/querycityscenicspot")
    public OPResult queryCityScenicSpot(@Valid @RequestBody QueryCityContentDTO queryCityContentDTO) {

            log.info("查询城市对应的景点开始");

            ScenicSpotListVO scenicSpotListVO = cityService.queryCityScenicSpot(queryCityContentDTO.getUserId(), queryCityContentDTO.getCityId(), queryCityContentDTO.getPageNum(), queryCityContentDTO.getPageSize());

            log.info("查询城市对应的景点成功");
            return OPResult.SUCCESS(scenicSpotListVO);

    }

    /**
     * 查询城市对应的美食
     *
     * @param queryCityContentDTO
     * @return
     */
    @PostMapping("/querycityfood")
    public OPResult queryCityFood(@Valid @RequestBody QueryCityContentDTO queryCityContentDTO) {

            log.info("查询城市对应的美食开始");

            FoodListVO foodListVO = cityService.queryCityFood(queryCityContentDTO.getUserId(), queryCityContentDTO.getCityId(), queryCityContentDTO.getPageNum(), queryCityContentDTO.getPageSize());

            log.info("查询城市对应的美食成功");
            return OPResult.SUCCESS(foodListVO);


    }

    /**
     * 查询城市对应的住宿
     *
     * @param queryCityContentDTO
     * @return
     */
    @PostMapping("/querycitydormitory")
    public OPResult queryCityDormitory(@Valid @RequestBody QueryCityContentDTO queryCityContentDTO) {

            log.info("查询城市对应的住宿开始");

            DormitoryListVO dormitoryListVO = cityService.queryCityDormitory(queryCityContentDTO.getUserId(), queryCityContentDTO.getCityId(), queryCityContentDTO.getPageNum(), queryCityContentDTO.getPageSize());

            log.info("查询城市对应的住宿成功");
            return OPResult.SUCCESS(dormitoryListVO);

    }

    /**
     * 查询单个景点信息
     *
     * @param querySingleScenicSpotDTO
     * @return
     **/
    @PostMapping("/querysinglescenicspot")
    public OPResult querySingleScenicSpot(@Valid @RequestBody QuerySingleScenicSpotDTO querySingleScenicSpotDTO) {

            log.info("查询单个景点信息开始");
            ScenicSpotVO scenicSpotVO = cityService.querySingleScenicSpot(querySingleScenicSpotDTO.getScenicSpotId(), querySingleScenicSpotDTO.getUserId());
            log.info("查询单个景点信息成功");
            return OPResult.SUCCESS(scenicSpotVO);

    }

    /**
     * 查询单个美食信息
     *
     * @param querySingleFoodDTO
     * @return
     **/
    @PostMapping("/querysinglefood")
    public OPResult querySingleFood(@Valid @RequestBody QuerySingleFoodDTO querySingleFoodDTO) {

            log.info("查询单个美食信息开始");
            FoodVO foodVO = cityService.querySingleFood(querySingleFoodDTO.getFoodId(), querySingleFoodDTO.getUserId());
            log.info("查询单个美食信息成功");
            return OPResult.SUCCESS(foodVO);
    }

    /**
     * 查询单个住宿信息
     *
     * @param querySingleDormitoryDTO
     * @return
     */
    @PostMapping("/querysingledormitory")
    public OPResult querySingleDormitory(@Valid @RequestBody QuerySingleDormitoryDTO querySingleDormitoryDTO) {

            log.info("查询单个住宿信息开始");
            DormitoryVO dormitoryVO = cityService.querySingleDormitory(querySingleDormitoryDTO.getDormitoryId(), querySingleDormitoryDTO.getUserId());
            log.info("查询单个住宿信息成功");
            return OPResult.SUCCESS(dormitoryVO);
    }

    /**
     * 获取点赞量最高的前五个城市
     *
     * @return 城市信息列表
     */
    @GetMapping("/top-cities-by-likes")
    public OPResult getTopCitiesByLikes() {

            log.info("获取点赞量最高的前五个城市开始");
            List<CityCoverVO> cityCoverVOList = cityService.getTopCitiesByLikes();
            log.info("获取点赞量最高的前五个城市成功");
            return OPResult.SUCCESS(cityCoverVOList);

    }


    /**
     * 获取固定的城市
     *
     * @return 城市信息列表
     */
    @GetMapping("/acquiring-fixed-city")
    public OPResult acquiringFixedCity() {

            log.info("获取固定的五个城市开始");
            List<CityCoverVO> cityCoverVOList = cityService.acquiringFixedCity();
            log.info("获取固定的五个城市成功");
            return OPResult.SUCCESS(cityCoverVOList);

    }
}
