package com.op.citybag.demos.web.controller;

import com.op.citybag.demos.model.VO.page.list.CityListVO;
import com.op.citybag.demos.model.VO.page.list.DormitoryListVO;
import com.op.citybag.demos.model.VO.page.list.FoodListVO;
import com.op.citybag.demos.model.VO.page.list.ScenicSpotListVO;
import com.op.citybag.demos.model.VO.page.object.CityVO;
import com.op.citybag.demos.model.VO.page.object.DormitoryVO;
import com.op.citybag.demos.model.VO.page.object.FoodVO;
import com.op.citybag.demos.model.VO.page.object.ScenicSpotVO;
import com.op.citybag.demos.service.ICityService;
import com.op.citybag.demos.web.common.DTO.city.*;
import com.op.citybag.demos.web.common.OPResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 原神
 * @Description: 城市控制器
 * @Date: 2025/2/26 20:02
 * @Version: 1.0
 */

//${app.config.api-version}
@Slf4j
@Validated
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/v1/city/")//测试
@RequiredArgsConstructor
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
    public OPResult querySingleCity(@RequestBody QuerySingleCityDTO querySingleCityDTO) {
        try {
            log.info("查询单个城市信息开始");

            CityVO cityVO = cityService.querySingleCity(querySingleCityDTO.getCityId());

            log.info("查询单个城市信息成功");
            return OPResult.SUCCESS(cityVO);
        } catch (Exception e) {
            log.error("查询单个城市信息失败,{}", e.getMessage());
            return OPResult.FAIL(e);
        }
    }

    /**
     * 查询城市信息(模糊匹配)
     *
     * @param queryCityLikeDTO
     * @return 城市信息
     */
    @PostMapping("/queryCityLike")
    public OPResult queryCityLike(@RequestBody QueryCityLikeDTO queryCityLikeDTO) {
        try {
            log.info("查询城市信息开始");

            //查询城市信息(分页查询)
//            CityListVO cityListVO = cityService.queryCityLike(queryCityLikeDTO.getCityName(), queryCityLikeDTO.getPageNum(), queryCityLikeDTO.getPageSize());

            //查询城市信息(全部返回)
            CityListVO cityListVO = cityService.queryCityLike(queryCityLikeDTO.getCityName());

            log.info("查询城市信息成功");
            return OPResult.SUCCESS(cityListVO);
        } catch (Exception e) {
            log.error("查询城市信息失败,{}", e.getMessage());
            return OPResult.FAIL(e);
        }
    }


    /**
     * 查询城市对应的景点
     *
     * @param queryCityContentDTO
     * @return
     */
    @PostMapping("/querycityscenicspot")
    public OPResult queryCityScenicSpot(@RequestBody QueryCityContentDTO queryCityContentDTO) {
        try {
            log.info("查询城市对应的景点开始");

            ScenicSpotListVO scenicSpotListVO = cityService.queryCityScenicSpot(queryCityContentDTO.getCityId(), queryCityContentDTO.getPageNum(), queryCityContentDTO.getPageSize());

            log.info("查询城市对应的景点成功");
            return OPResult.SUCCESS(scenicSpotListVO);
        } catch (Exception e) {
            log.error("查询城市对应的景点失败,{}", e.getMessage());
            return OPResult.FAIL(e);
        }
    }

    /**
     * 查询城市对应的美食
     *
     * @param queryCityContentDTO
     * @return
     */
    @PostMapping("/querycityfood")
    public OPResult queryCityFood(@RequestBody QueryCityContentDTO queryCityContentDTO) {
        try {
            log.info("查询城市对应的美食开始");

            FoodListVO foodListVO = cityService.queryCityFood(queryCityContentDTO.getCityId(), queryCityContentDTO.getPageNum(), queryCityContentDTO.getPageSize());

            log.info("查询城市对应的美食成功");
            return OPResult.SUCCESS(foodListVO);
        } catch (Exception e) {
            log.error("查询城市对应的美食失败,{}", e.getMessage());
            return OPResult.FAIL(e);
        }

    }

    /**
     * 查询城市对应的住宿
     *
     * @param queryCityContentDTO
     * @return
     */
    @PostMapping("/querycitydormitory")
    public OPResult queryCityDormitory(@RequestBody QueryCityContentDTO queryCityContentDTO) {
        try {
            log.info("查询城市对应的住宿开始");

            DormitoryListVO dormitoryListVO = cityService.queryCityDormitory(queryCityContentDTO.getCityId(), queryCityContentDTO.getPageNum(), queryCityContentDTO.getPageSize());

            log.info("查询城市对应的住宿成功");
            return OPResult.SUCCESS(dormitoryListVO);
        } catch (Exception e) {
            log.error("查询城市对应的住宿失败,{}", e.getMessage());
            return OPResult.FAIL(e);
        }
    }

    /**
     * 查询单个景点信息
     *
     * @param querySingleScenicSpotDTOsingleScenicSpotDTO
     * @return
     **/
    @PostMapping("/querysinglescenicspot")
    public OPResult querySingleScenicSpot(@RequestBody QuerySingleScenicSpotDTO querySingleScenicSpotDTOsingleScenicSpotDTO) {
        try {
            log.info("查询单个景点信息开始");
            ScenicSpotVO scenicSpotVO = cityService.querySingleScenicSpot(querySingleScenicSpotDTOsingleScenicSpotDTO.getScenicSpotId());
            log.info("查询单个景点信息成功");
            return OPResult.SUCCESS(scenicSpotVO);
        } catch (Exception e) {
            log.error("查询单个景点信息失败,{}", e.getMessage());
            return OPResult.FAIL(e);
        }
    }

    /**
     * 查询单个美食信息
     *
     * @param querySingleFoodDTO
     * @return
     **/
    @PostMapping("/querysinglefood")
    public OPResult querySingleFood(@RequestBody QuerySingleFoodDTO querySingleFoodDTO) {
        try {
            log.info("查询单个美食信息开始");
            FoodVO foodVO = cityService.querySingleFood(querySingleFoodDTO.getFoodId());
            log.info("查询单个美食信息成功");
            return OPResult.SUCCESS(foodVO);
        } catch (Exception e) {
            log.error("查询单个美食信息失败,{}", e.getMessage());
            return OPResult.FAIL(e);
        }
    }

    /**
     * 查询单个住宿信息
     *
     * @param querySingleDormitoryDTO
     * @return
     */
    @PostMapping("/querysingledormitory")
    public OPResult querySingleDormitory(@RequestBody QuerySingleDormitoryDTO querySingleDormitoryDTO) {
        try {
            log.info("查询单个住宿信息开始");
            DormitoryVO dormitoryVO = cityService.querySingleDormitory(querySingleDormitoryDTO.getDormitoryId());
            log.info("查询单个住宿信息成功");
            return OPResult.SUCCESS(dormitoryVO);
        } catch (Exception e) {
            log.error("查询单个住宿信息失败,{}", e.getMessage());
            return OPResult.FAIL(e);
        }
    }
}
