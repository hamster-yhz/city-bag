use city_bag;


-- 用户表示例数据
INSERT INTO `user` (id, user_id, user_name, phone, openid, stu_id, password, gender, birthday,
                    personalized_signature, jurisdiction, like_count, create_time, update_time, is_deleted)
VALUES (NULL, -- 自动生成的主键
        UUID(), -- 随机生成的user_id
        '张三', -- 用户名
        '13800138000', -- 手机号码
        'openid1234567890', -- openid
        'stu1234567890', -- stu_id
        'password123456', -- 密码
        1, -- 性别：男
        '19970101', -- 生日
        '我喜欢旅行和美食！', -- 个性签名
        0, -- 权限：普通用户
        0, -- 点赞数量
        CURRENT_TIMESTAMP, -- 创建时间
        CURRENT_TIMESTAMP, -- 更新时间
        0 -- 未删除
       ),
       (NULL,
        UUID(),
        '李四',
        '13900139000',
        'openid2345678901',
        'stu2345678901',
        'password2345678901',
        2,
        '19980202',
        '喜欢摄影和登山！',
        1, -- 权限：VIP用户
        5,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '王五',
        '13600136000',
        'openid3456789012',
        'stu3456789012',
        'password3456789012',
        1,
        '19990303',
        '喜欢阅读和运动！',
        2, -- 权限：管理员
        10,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '赵六',
        '13700137000',
        'openid4567890123',
        'stu4567890123',
        'password4567890123',
        2,
        '20000404',
        '喜欢音乐和绘画！',
        0,
        0,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '陈七',
        '13800138001',
        'openid5678901234',
        'stu5678901234',
        'password5678901234',
        1,
        '20010505',
        '喜欢科技和编程！',
        3, -- 权限：超级管理员
        15,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0);


-- 城市表示例数据
INSERT INTO `city` (id, city_id, city_name, city_introduce, food_introduce, scenic_spot_introduce, image_url,
                    like_count, create_time, update_time, is_deleted)
VALUES (NULL, -- 自动生成的主键
        UUID(), -- 城市id
        '北京', -- 城市名称
        '北京是中国的首都，历史悠久，文化底蕴深厚，拥有众多历史遗迹和现代化都市景观。', -- 城市介绍
        '北京烤鸭、涮羊肉、炸酱面、豆汁儿、烧饼夹肉）は北京特色美食。', -- 美食介绍
        '故宫、长城、天坛、颐和园是北京的代表性景点。', -- 景点介绍
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0', -- 图片地址
        1000, -- 点赞数量
        CURRENT_TIMESTAMP, -- 创建时间
        CURRENT_TIMESTAMP, -- 更新时间
        0 -- 未删除
       ),
       (NULL,
        UUID(),
        '上海',
        '上海是中国的经济中心，现代化都市，历史文化与现代文明交织，拥有独特的城市风貌。',
        '小笼包、生煎包、蟹黄汤包、海鲜、粽子、汤包等是上海的代表性美食。',
        '外滩、东方明珠、豫园、城隍庙、朱家角古镇是上海的著名景点。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0', -- 图片地址
        500,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '广州',
        '广州是中国南方的重要城市，历史悠久，美食丰富，气候温暖，拥有许多自然和人文景观。',
        '肠粉、艇仔粥、沙河粉、烧鸭、炒河粉、虾饺、叉烧包等是广州特色美食。',
        '越秀公园、广州塔、沙面岛、上下九步行街、白云山都是广州的盛景。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0', -- 图片地址
        800,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '成都',
        '成都是四川省的省会，历史文化名城，美食之都，周围有丰富的自然资源和历史遗迹。',
        '火锅、披盖面、担担面、夫妻肺片、麻辣烫、糍粑、香辣虾等是成都的特色美食。',
        '都江堰、宽窄巷子、武侯祠、青羊宫、成都大熊猫繁育研究基地是成都的代表性景点。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0', -- 图片地址
        1200,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '南京',
        '南京是六朝古都，历史悠久，途径丰富，现代化与历史文化并存。',
        '盐水鸭、鸭血粉丝汤、银丝卷、汤包、板鸭、桂花糖芋苗等是南京的特色美食。',
        '中山陵、紫峰大厦、明孝陵、秦淮河、夫子庙、总统府是南京的著名景点。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0', -- 图片地址
        600,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0);


-- 住宿表示例数据
INSERT INTO `dormitory` (id, dormitory_id, dormitory_name, introduce, image_url, price, phone, tag_list, type, like_count, city_id,
                         create_time, update_time, is_deleted)
VALUES (NULL, -- 自动生成的主键
        UUID(), -- 住宿id
        '北京王府井大酒店', -- 住宿名称
        '位于北京市中心，交通便利，设施完善，服务周到，是商务和休闲的理想选择。', -- 介绍
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0', -- 图片地址
        '1000元',
        '13800138000',
        '环境优美_设施齐全_服务到位',
        1, -- 类型：酒店
        200, -- 点赞数量
        (SELECT city_id FROM city WHERE city_name = '北京' LIMIT 1), -- 所属城市：北京
        CURRENT_TIMESTAMP, -- 创建时间
        CURRENT_TIMESTAMP, -- 更新时间
        0 -- 未删除
       ),
       (NULL,
        UUID(),
        '上海外滩皇冠洲际酒店',
        '坐落于上海外滩，拥有迷人的浦江景观，客房设施豪华，服务高端，是高端商务和休闲的最佳选择。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0', -- 图片地址
        '2000元',
        '13800138000',
        '环境优美_服务到位',
        1,
        300,
        (SELECT city_id FROM city WHERE city_name = '上海' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '广州塔 Teeth_Residential_Tower',
        '广州塔附近的时尚居住空间，交通便利，景观壮丽，适合短期入住。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0', -- 图片地址
        '600元',
        '13800138000',
        '小而美',
        2, -- 类型：民宿
        100,
        (SELECT city_id FROM city WHERE city_name = '广州' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '成都宽窄巷子特色民宿',
        '位于成都历史文化街区，保留了古典四川建筑风格，提供独特的入住体验。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0', -- 图片地址
        '200元',
        '13800138000',
        '民风淳朴_环境优美',
        2,
        150,
        (SELECT city_id FROM city WHERE city_name = '成都' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '南京夫子庙国际青年旅舍',
        '靠近夫子庙景区，适合背包客和游客，提供经济实惠的住宿选择。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0', -- 图片地址
        '260元',
        '13800138000',
        '经济实惠',
        2,
        90,
        (SELECT city_id FROM city WHERE city_name = '南京' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0);


-- 美食表示例数据
INSERT INTO `food` (id, food_id, food_name, introduce, image_url, open_time, price, phone, food_list, tag_list, like_count, city_id, create_time, update_time,
                    is_deleted)
VALUES (NULL, -- 自动生成的主键
        UUID(), -- 美食id
        '北京烤鸭', -- 美食名称
        '北京烤鸭以其薄脆的皮和嫩滑的肉质闻名，传统的烤鸭搭配薄饼、葱和甜面酱一起吃。', -- 介绍
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0', -- 图片地址
        '08:30-17:00',-- 开放时间
        '80元', -- 人均价格
        '13800138000', -- 联系电话
        '北京烤鸭,北京烤鸭,北京烤鸭', -- 著名美食列表
        'yummy', -- 标签列表
        1000, -- 点赞数量
        (SELECT city_id FROM city WHERE city_name = '北京' LIMIT 1), -- 所属城市
        CURRENT_TIMESTAMP, -- 创建时间
        CURRENT_TIMESTAMP, -- 更新时间
        0 -- 未删除
       ),
       (NULL,
        UUID(),
        '小笼包',
        '上海的小笼包以其鲜美的汁香和松软的面皮著称，种类有猪肉、鸡肉、鲍鱼等。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0', -- 图片地址
        '08:30-17:00',
        '20元',
        '13800138000',
        '小笼包',
        '种类多样_松软',
        750,
        (SELECT city_id FROM city WHERE city_name = '上海' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '肠粉',
        '广州的早餐代表，与香港肠粉类似，口感细腻，搭配各种配料如鲜片、猪肉、defgroup等。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0', -- 图片地址
        '08:30-17:00',
        '6元',
        '13800138000',
        '小笼包',
        '种类多样_松软',
        600,
        (SELECT city_id FROM city WHERE city_name = '广州' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '火锅',
        '成都的火锅以麻辣鲜香著称，底料选择鸳鸯锅，一边是麻辣味，一边是清鲜味，食材丰富多样.',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0', -- 图片地址
        '08:30-17:00',
        '400元',
        '13800138000',
        '小笼包',
        '麻辣鲜香',
        1000,
        (SELECT city_id FROM city WHERE city_name = '成都' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '盐水鸭',
        '南京的传统美食，鸭子肉质鲜嫩，皮酥麻，搭配独特的调料和腌制工艺，风味独特。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0', -- 图片地址
        '08:30-17:00',
        '100元',
        '13800138000',
        '小笼包',
        '皮酥麻',
        400,
        (SELECT city_id FROM city WHERE city_name = '南京' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0);


-- 景点表示例数据
INSERT INTO `scenic_spot` (id, scenic_spot_id, scenic_spot_name, introduce, image_url,
                           address, open_time, price, phone, scenic_spot_list, photo_url, tag_list, visit_time,
                           type, like_count, city_id, create_time, update_time, is_deleted)
VALUES (NULL, UUID(), '故宫',
        '北京的紫禁城，明清两朝皇宫，规模宏大，文化历史价值极高，吸引着全球游客。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0',
        '北京市东城区景山前街4号',
        '08:30-17:00',
        '60元',
        '010-85007421',
        '太和殿、乾清宫、御花园、钟表馆',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0/d5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0/d5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0',
        '最好看的景点_最舒适的环境',
        '3小时',
        1, 10000, (SELECT city_id FROM city WHERE city_name = '北京' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '外滩',
        '上海的象征之一，黄浦江边的历史文化景区，拥有各种风格的建筑群，夜景璀璨。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0',
        '上海市黄浦区中山东一路',
        '全天开放',
        '免费',
        '021-63213553',
        '外白渡桥、陈毅广场、黄浦公园、和平饭店',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0/d5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0',
        '热门景点_暑假必去',
        '2小时',
        1, 8000, (SELECT city_id FROM city WHERE city_name = '上海' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '广州塔',
        '又称小蛮腰，广州的地标建筑，高达610米， Observation decks 提供全景视角，夜晚灯光璀璨。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0',
        '广州市海珠区阅江西路222号',
        '09:30-22:30',
        '150元起',
        '020-89338222',
        '塔顶、影院',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0/d5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0/d5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0',
        '最高建筑',
        '2.5小时',
        2, 7500, (SELECT city_id FROM city WHERE city_name = '广州' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '都江堰',
        '成都的水利工程瑰宝，具有2000多年的历史，是古代中国水利智慧的结晶。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0',
        '成都市都江堰市公园路',
        '08:00-18:00',
        '80元',
        '028-87120836',
        '景点1、景点2',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0',
        '华丽_名胜古迹',
        '4小时',
        1, 5000, (SELECT city_id FROM city WHERE city_name = '成都' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '中山陵',
        '南京的历史景点，纪念孙中山先生，是中国近代史的重要象征，建筑宏伟壮丽。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0',
        '南京市玄武区石象路7号',
        '08:30-17:00',
        '免费',
        '025-84431174',
        '景点1、景点2',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0/d5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0',
        '历史景点',
        '2小时',
        1, 6000, (SELECT city_id FROM city WHERE city_name = '南京' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

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

