package com.op.citybag.demos.web.controller;

import com.op.citybag.demos.model.VO.page.list.CityListVO;
import com.op.citybag.demos.model.common.GlobalServiceStatusCode;
import com.op.citybag.demos.service.ICityService;
import com.op.citybag.demos.web.common.DTO.login.LocationDTO;
import com.op.citybag.demos.web.common.OPResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/17 19:07
 * @Version: 1.0
 */
@Slf4j
@RestController
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/v1/recommend/")
@RequiredArgsConstructor
public class RecommendController {

    private final ICityService cityService;

    //private final WeChatLocationServiceImpl weChatLocationService;

//    /**
//     * 根据定位推荐附近城市
//     */
//    @PostMapping("city")
//    public OPResult recommendCity(@RequestBody LocationDTO dto) {
//        try {
//            return OPResult.SUCCESS(cityService.queryCityNearby(dto.getLatitude(), dto.getLongitude()));
//        } catch (Exception e) {
//            log.error("城市推荐失败", e);
//            return OPResult.FAIL(e);
//        }
//    }
//
//    /**
//     * 推荐城市美食
//     */
//    @PostMapping("food")
//    public OPResult recommendFood(@RequestBody LocationDTO dto) {
//        try {
//            String cityId = getCityIdByLocation(dto);
//            return OPResult.SUCCESS(cityService.queryCityFood(cityId, 1, 10));
//        } catch (Exception e) {
//            log.error("美食推荐失败", e);
//            return OPResult.FAIL(e);
//        }
//    }
//
//    /**
//     * 推荐城市景点
//     */
//    @PostMapping("scenic-spot")
//    public OPResult recommendScenicSpot(@RequestBody LocationDTO dto) {
//        try {
//            String cityId = getCityIdByLocation(dto);
//            return OPResult.SUCCESS(cityService.queryCityScenicSpot(cityId, 1, 10));
//        } catch (Exception e) {
//            log.error("景点推荐失败", e);
//            return OPResult.FAIL(e);
//        }
//    }
//
//    /**
//     * 推荐城市住宿
//     */
//    @PostMapping("dormitory")
//    public OPResult recommendDormitory(@RequestBody LocationDTO dto) {
//        try {
//            String cityId = getCityIdByLocation(dto);
//            return OPResult.SUCCESS(cityService.queryCityDormitory(cityId, 1, 10));
//        } catch (Exception e) {
//            log.error("住宿推荐失败", e);
//            return OPResult.FAIL(e);
//        }
//    }

//    private String getCityIdByLocation(LocationDTO dto) {
//        // 优先使用微信定位服务
//        String cityName = weChatLocationService.getCityName(dto.getLatitude(), dto.getLongitude());
//        if (StringUtils.isEmpty(cityName)) {
//            cityName = dto.getCityName(); // 降级使用前端传入的城市名
//        }
//
//        CityListVO cityList = cityService.queryCityLike(cityName, 1, 1);
//        if (CollectionUtils.isEmpty(cityList.getCityList())) {
//            throw new AppException(GlobalServiceStatusCode.SYSTEM_SERVICE_ERROR, "未找到匹配城市");
//        }
//        return cityList.getCityList().get(0).getCityId();
//    }
}
