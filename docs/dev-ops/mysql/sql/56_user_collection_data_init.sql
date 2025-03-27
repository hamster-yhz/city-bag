-- 用户收藏表示例数据
INSERT INTO `user_collection` (collection_id, user_id, entity_type, entity_id, create_time, update_time, is_deleted)
-- 张三收藏北京的景点和美食
VALUES (UUID(), (SELECT user_id FROM `user` WHERE user_name = '张三' LIMIT 1), 'scenic_spot',
        (SELECT scenic_spot_id FROM scenic_spot WHERE scenic_spot_name = '故宫' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (UUID(), (SELECT user_id FROM `user` WHERE user_name = '张三' LIMIT 1), 'food',
        (SELECT food_id FROM food WHERE food_name = '北京烤鸭' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),

-- 李四收藏上海的住宿和景点
       (UUID(), (SELECT user_id FROM `user` WHERE user_name = '李四' LIMIT 1), 'dormitory',
        (SELECT dormitory_id FROM dormitory WHERE dormitory_name = '上海外滩皇冠洲际酒店' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (UUID(), (SELECT user_id FROM `user` WHERE user_name = '李四' LIMIT 1), 'scenic_spot',
        (SELECT scenic_spot_id FROM scenic_spot WHERE scenic_spot_name = '外滩' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),

-- 王五收藏成都的火锅和景点
       (UUID(), (SELECT user_id FROM `user` WHERE user_name = '王五' LIMIT 1), 'food',
        (SELECT food_id FROM food WHERE food_name = '火锅' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (UUID(), (SELECT user_id FROM `user` WHERE user_name = '王五' LIMIT 1), 'scenic_spot',
        (SELECT scenic_spot_id FROM scenic_spot WHERE scenic_spot_name = '都江堰' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),

-- 赵六收藏南京的景点和美食
       (UUID(), (SELECT user_id FROM `user` WHERE user_name = '赵六' LIMIT 1), 'scenic_spot',
        (SELECT scenic_spot_id FROM scenic_spot WHERE scenic_spot_name = '中山陵' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (UUID(), (SELECT user_id FROM `user` WHERE user_name = '赵六' LIMIT 1), 'food',
        (SELECT food_id FROM food WHERE food_name = '盐水鸭' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),

-- 陈七收藏所有城市的景点
       (UUID(), (SELECT user_id FROM `user` WHERE user_name = '陈七' LIMIT 1), 'scenic_spot',
        (SELECT scenic_spot_id FROM scenic_spot WHERE scenic_spot_name = '广州塔' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (UUID(), (SELECT user_id FROM `user` WHERE user_name = '陈七' LIMIT 1), 'scenic_spot',
        (SELECT scenic_spot_id FROM scenic_spot WHERE scenic_spot_name = '故宫' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
