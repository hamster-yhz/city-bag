package com.op.citybag.demos.utils;

import com.op.citybag.demos.model.Entity.*;
import com.op.citybag.demos.model.VO.login.LoginVO;
import com.op.citybag.demos.model.VO.page.cover.DormitoryCoverVO;
import com.op.citybag.demos.model.VO.page.cover.FoodCoverVO;
import com.op.citybag.demos.model.VO.page.cover.ScenicSpotCoverVO;
import com.op.citybag.demos.model.VO.page.object.CityVO;
import com.op.citybag.demos.model.VO.page.object.DormitoryVO;
import com.op.citybag.demos.model.VO.page.object.FoodVO;
import com.op.citybag.demos.model.VO.page.object.ScenicSpotVO;
import com.op.citybag.demos.model.VO.user.UserVO;

/**
 * @Author: 原神
 * @Description:
 * @Date: 2025/3/2 21:25
 * @Version: 1.0
 */
public class Entity2VO {

    /**
     * City转CityVO
     */
    public static CityVO City2CityVO(City city) {
        return CityVO.builder()
                .cityId(city.getCityId())
                .cityName(city.getCityName())
                .cityIntroduce(city.getCityIntroduce())
                .foodIntroduce(city.getFoodIntroduce())
                .scenicSpotIntroduce(city.getScenicSpotIntroduce())
                .likeCount(city.getLikeCount())
                .build();
    }

    /**
     * User转LoginVO
     */
    public static LoginVO User2LoginVO(User user) {
        return LoginVO.builder()
                .userId(user.getUserId())
                .phone(user.getPhone())
                .stuId(user.getStuId())
                .userName(user.getUserName())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .personalizedSignature(user.getPersonalizedSignature())
                .jurisdiction(user.getJurisdiction())
                .likeCount(user.getLikeCount())
                .updateTime(user.getUpdateTime())
                .build();
    }

    /**
     * User转UserVO
     */
    public static UserVO User2UserVO(User user) {
        return UserVO.builder()
                .userId(user.getUserId())
                .phone(user.getPhone())
                .userName(user.getUserName())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .personalizedSignature(user.getPersonalizedSignature())
                .jurisdiction(user.getJurisdiction())
                .likeCount(user.getLikeCount())
                .updateTime(user.getUpdateTime())
                .build();
    }

    /**
     * Food转FoodVO
     */
    public static FoodVO Food2FoodVO(Food food) {
        return FoodVO.builder()
                .foodId(food.getFoodId())
                .foodName(food.getFoodName())
                .introduce(food.getIntroduce())
                .likeCount(food.getLikeCount())
                .cityId(food.getCityId())
                .build();
    }

    /**
     * ScenicSpot转ScenicSpotVO
     */
    public static ScenicSpotVO ScenicSpot2ScenicSpotVO(ScenicSpot spot) {
        return ScenicSpotVO.builder()
                .scenicSpotId(spot.getScenicSpotId())
                .scenicSpotName(spot.getScenicSpotName())
                .introduce(spot.getIntroduce())
                .openTime(spot.getOpenTime())
                .price(spot.getPrice())
                .address(spot.getAddress())
                .phone(spot.getPhone())
                .visitTime(spot.getVisitTime())
                .scenicSpotList(spot.getScenicSpotList())
                .likeCount(spot.getLikeCount())
                .cityId(spot.getCityId())
                .type(spot.getType())
                .build();
    }

    /**
     * Dormitory转DormitoryVO
     */
    public static DormitoryVO Dormitory2DormitoryVO(Dormitory dormitory) {
        return DormitoryVO.builder()
                .dormitoryId(dormitory.getDormitoryId())
                .dormitoryName(dormitory.getDormitoryName())
                .introduce(dormitory.getIntroduce())
                .DormitoryImg(dormitory.getImageUrl())
                .likeCount(dormitory.getLikeCount())
                .cityId(dormitory.getCityId())
                .type(dormitory.getType())
                .build();
    }



}
