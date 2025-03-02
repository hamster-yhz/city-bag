package com.op.citybag.demos.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.op.citybag.demos.exception.AppException;
import com.op.citybag.demos.mapper.CityMapper;
import com.op.citybag.demos.mapper.DormitoryMapper;
import com.op.citybag.demos.mapper.FoodMapper;
import com.op.citybag.demos.mapper.ScenicSpotMapper;
import com.op.citybag.demos.model.Entity.City;
import com.op.citybag.demos.model.Entity.Dormitory;
import com.op.citybag.demos.model.Entity.Food;
import com.op.citybag.demos.model.Entity.ScenicSpot;
import com.op.citybag.demos.model.VO.page.DormitoryListVO;
import com.op.citybag.demos.model.VO.page.FoodListVO;
import com.op.citybag.demos.model.VO.page.ScenicSpotListVO;
import com.op.citybag.demos.model.VO.page.SingleCityVO;
import com.op.citybag.demos.model.common.GlobalServiceStatusCode;
import com.op.citybag.demos.oss.OSSServiceImpl;
import com.op.citybag.demos.service.ICityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 原神
 * @Description: 城市服务实现类
 * @Date: 2025/2/26 21:25
 * @Version: 1.0
 */

@Service
@Slf4j
public class CityServiceImpl implements ICityService {

    @Autowired
    private ScenicSpotMapper scenicSpotMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private DormitoryMapper dormitoryMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private OSSServiceImpl ossDemoService;

    @Override
    public SingleCityVO querySingleCity(String cityName) {

        // 构建查询条件
        QueryWrapper<City> wrapper = new QueryWrapper<>();
        wrapper.eq("city_name", cityName);

        // 执行查询
        City city = cityMapper.selectOne(wrapper);
        if (city == null) {
            log.info("未找到城市: {}", cityName);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.CITY_NOT_EXIST.getCode()), GlobalServiceStatusCode.CITY_NOT_EXIST.getMessage());
        }

        // 转换为VO
        SingleCityVO singleCityVO = City2SingleCityVO(city);

        // 查询城市图片
        String cityImg = ossDemoService.generatePresignedUrl
                (city.getImage_url(), 1000000000);
        singleCityVO.setCityImg(cityImg);

        return singleCityVO;
    }

    @Override
    public ScenicSpotListVO queryCityScenicSpot(String cityId) {

        // 查询景点列表
        List<ScenicSpot> spots = scenicSpotMapper.selectList(
                new QueryWrapper<ScenicSpot>().eq("city_id", cityId)
        );

        return ScenicSpotListVO.builder()
                .cityId(cityId)
                .scenicSpotList(spots)
                .build();
    }

    @Override
    public FoodListVO queryCityFood(String cityId) {
        List<Food> foods = foodMapper.selectList(
                new QueryWrapper<Food>().eq("city_id", cityId)
        );
        return FoodListVO.builder()
                .cityId(cityId)
                .foodList(foods)
                .build();
    }

    @Override
    public DormitoryListVO queryCityDormitory(String cityId) {
        List<Dormitory> dormitories = dormitoryMapper.selectList(
                new QueryWrapper<Dormitory>().eq("city_id", cityId)
        );
        return DormitoryListVO.builder()
                .cityId(cityId)
                .dormitoryList(dormitories)
                .build();
    }

    /**
     * City转CityVO
     */
    private SingleCityVO City2SingleCityVO(City city) {
        return SingleCityVO.builder()
                .cityId(city.getCityId())
                .cityName(city.getCityName())
                .cityIntroduce(city.getCityIntroduce())
                .foodIntroduce(city.getFoodIntroduce())
                .scenicSpotIntroduce(city.getScenicSpotIntroduce())
                .likeCount(city.getLikeCount())
                .build();
    }
}
