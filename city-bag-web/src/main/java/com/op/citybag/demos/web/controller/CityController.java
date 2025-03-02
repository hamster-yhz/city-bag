package com.op.citybag.demos.web.controller;

import com.op.citybag.demos.model.VO.page.list.DormitoryListVO;
import com.op.citybag.demos.model.VO.page.list.FoodListVO;
import com.op.citybag.demos.model.VO.page.list.ScenicSpotListVO;
import com.op.citybag.demos.model.VO.page.object.CityVO;
import com.op.citybag.demos.service.ICityService;
import com.op.citybag.demos.web.common.OPResult;
import com.op.citybag.demos.web.common.dto.city.QueryCityContentDTO;
import com.op.citybag.demos.web.common.dto.city.QuerySingleCityDTO;
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
@RequestMapping("/api/v1/auth/")//测试
@RequiredArgsConstructor
public class CityController {

    @Autowired
    private final ICityService cityService;

    /**
     * 查询单个城市信息(精确匹配)
     * @param querySingleCityDTO
     * @return 城市信息
     */
    @PostMapping("/querysinglecity")
    public OPResult querySingleCity(@RequestBody QuerySingleCityDTO querySingleCityDTO){
        try {
            log.info("查询单个城市信息开始");

            CityVO cityVO = cityService.querySingleCity(querySingleCityDTO.getCityName());

            log.info("查询单个城市信息成功");
            return OPResult.SUCCESS(cityVO);
        } catch (Exception e) {
            log.error("查询单个城市信息失败,{}", e.getMessage());
            return OPResult.FAIL(e);
        }
    }


    /**
     * 查询城市对应的景点
     * @param queryCityContentDTO
     * @return
     */
    @PostMapping("/querycityscenicspot")
    public OPResult queryCityScenicSpot(@RequestBody QueryCityContentDTO queryCityContentDTO){
        try {
            log.info("查询城市对应的景点开始");

            ScenicSpotListVO scenicSpotListVO = cityService.queryCityScenicSpot(queryCityContentDTO.getCityId());

            log.info("查询城市对应的景点成功");
            return OPResult.SUCCESS(scenicSpotListVO);
        }   catch (Exception e) {
            log.error("查询城市对应的景点失败,{}", e.getMessage());
            return OPResult.FAIL(e);
        }
    }

    /**
     * 查询城市对应的美食
     * @param queryCityContentDTO
     * @return
     */
    @PostMapping("/querycityfood")
    public OPResult queryCityFood(@RequestBody QueryCityContentDTO queryCityContentDTO){
        try {
            log.info("查询城市对应的美食开始");

            FoodListVO foodListVO = cityService.queryCityFood(queryCityContentDTO.getCityId());

            log.info("查询城市对应的美食成功");
            return OPResult.SUCCESS(foodListVO);
        }   catch (Exception e) {
            log.error("查询城市对应的美食失败,{}", e.getMessage());
            return OPResult.FAIL(e);
        }

    }

    /**
     * 查询城市对应的住宿
     * @param queryCityContentDTO
     * @return
     */
    @PostMapping("/querycitydormitory")
    public OPResult queryCityDormitory(@RequestBody QueryCityContentDTO queryCityContentDTO){
        try {
            log.info("查询城市对应的住宿开始");

            DormitoryListVO dormitoryListVO = cityService.queryCityDormitory(queryCityContentDTO.getCityId());

            log.info("查询城市对应的住宿成功");
            return OPResult.SUCCESS(dormitoryListVO);
        }   catch (Exception e) {
            log.error("查询城市对应的住宿失败,{}", e.getMessage());
            return OPResult.FAIL(e);
        }
    }

}
