-- 住宿表示例数据
INSERT INTO `dormitory` (id, dormitory_id, dormitory_name, english_name, introduce, image_url, price, address, phone, photo_url, tag_list,
                         type, like_count, city_id,
                         create_time, update_time, is_deleted)
VALUES (NULL, -- 自动生成的主键
        UUID(), -- 住宿id
        '北京王府井大酒店', -- 住宿名称
        'Beijing Wangfujing Hotel', -- 英文名称
        '位于北京市中心，交通便利，设施完善，服务周到，是商务和休闲的理想选择。', -- 介绍
        '', -- 图片地址
        '1000元',
        '北京市东城区王府井大街1号', -- address
        '13800138000',
        '', -- 实拍图片
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
        'Shanghai Bund Crown Plaza Hotel', -- 英文名称
        '坐落于上海外滩，拥有迷人的浦江景观，客房设施豪华，服务高端，是高端商务和休闲的最佳选择。',
        '', -- 图片地址
        '2000元',
        '上海市黄浦区中山东一路1号', -- address
        '13800138000',
        '',
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
        'Guangzhou TV Tower Teeth Residential Tower', -- 英文名称
        '广州塔附近的时尚居住空间，交通便利，景观壮丽，适合短期入住。',
        '', -- 图片地址
        '600元',
        '广州市海珠区阅江西路222号', -- address
        '13800138000',
        '',
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
        'Chengdu Kuanzhaxiangzi Characteristic Homestay', -- 英文名称
        '位于成都历史文化街区，保留了古典四川建筑风格，提供独特的入住体验。',
        '', -- 图片地址
        '200元',
        '成都市青羊区宽窄巷子', -- address
        '13800138000',
        '',
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
        'Nanjing Fuzimiao International Youth Hostel', -- 英文名称
        '靠近夫子庙景区，适合背包客和游客，提供经济实惠的住宿选择。',
        '', -- 图片地址
        '260元',
        '南京市秦淮区夫子庙', -- address
        '13800138000',
        '',
        '经济实惠',
        2,
        90,
        (SELECT city_id FROM city WHERE city_name = '南京' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL, -- 自动生成的主键
        UUID(), -- 住宿id
        '重庆解放碑青年旅社',
        'Chongqing Jiefangbei Youth Hostel', -- 英文名称
        '位于解放碑商圈，交通便利，适合背包客和游客，提供经济实惠的住宿选择。',
        '', -- 图片地址
        '150元',
        '重庆市渝中区解放碑', -- address
        '13800138000',
        '',
        '经济实惠',
        2,
        120,
        (SELECT city_id FROM city WHERE city_name = '重庆' LIMIT 1),
        CURRENT_TIMESTAMP, -- 创建时间
        CURRENT_TIMESTAMP, -- 更新时间
        0 -- 未删除
       ),
       (NULL,
        UUID(),
        '重庆洪崖洞特色民宿',
        'Chongqing Hongyadong Characteristic Homestay', -- 英文名称
        '位于洪崖洞景区附近，保留了重庆老城的建筑风格，提供独特的入住体验。',
        '', -- 图片地址
        '300元',
        '重庆市渝中区洪崖洞', -- address
        '13800138000',
        '',
        '古典风格_特色民宿',
        2,
        200,
        (SELECT city_id FROM city WHERE city_name = '重庆' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL, -- 自动生成的主键
        UUID(), -- 住宿id
        '西安古城酒店',
        'Xi\'an Ancient City Hotel', -- 英文名称
        '位于西安古城墙附近，交通便利，设施完善，服务周到，是商务和休闲的理想选择。',
        '', -- 图片地址
        '800元',
        '西安市碑林区南门', -- address
        '13800138000',
        '',
        '环境优美_设施齐全_服务到位',
        1, -- 类型：酒店
        150,
        (SELECT city_id FROM city WHERE city_name = '西安' LIMIT 1), -- 所属城市：西安
        CURRENT_TIMESTAMP, -- 创建时间
        CURRENT_TIMESTAMP, -- 更新时间
        0 -- 未删除
       ),
       (NULL,
        UUID(),
        '杭州西湖畔民宿',
        'Hangzhou West Lake Side Homestay', -- 英文名称
        '位于西湖畔，交通便利，环境优美，适合短期入住。',
        '', -- 图片地址
        '500元',
        '杭州市西湖区断桥残雪', -- address
        '13800138000',
        '',
        '环境优美_服务到位',
        2, -- 类型：民宿
        100,
        (SELECT city_id FROM city WHERE city_name = '杭州' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '苏州平江路客栈',
        'Suzhou Pingjiang Road Inn', -- 英文名称
        '位于苏州平江路，交通便利，环境优雅，适合短期入住。',
        '', -- 图片地址
        '400元',
        '苏州市姑苏区平江路', -- address
        '13800138000',
        '',
        '环境优雅_服务到位',
        2, -- 类型：民宿
        120,
        (SELECT city_id FROM city WHERE city_name = '苏州' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '丽江古城客栈',
        'Lijiang Ancient City Inn', -- 英文名称
        '位于丽江古城，交通便利，环境优美，适合短期入住。',
        '', -- 图片地址
        '300元',
        '丽江市古城区丽江古城', -- address
        '13800138000',
        '',
        '环境优美_服务到位',
        2, -- 类型：民宿
        130,
        (SELECT city_id FROM city WHERE city_name = '丽江' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL, -- 自动生成的主键
        UUID(), -- 住宿id
        '科尔沁草原度假村',
        'Korqin Grassland Resort', -- 英文名称
        '位于科尔沁大草原附近，交通便利，环境优美，适合家庭和朋友短期入住。', -- 介绍
        '', -- 图片地址
        '500元',
        '科尔沁大草原', -- address
        '13800138000',
        '',
        '环境优美_服务到位',
        2, -- 类型：民宿
        80,
        (SELECT city_id FROM city WHERE city_name = '科尔沁' LIMIT 1), -- 所属城市：科尔沁
        CURRENT_TIMESTAMP, -- 创建时间
        CURRENT_TIMESTAMP, -- 更新时间
        0 -- 未删除
       ),
       (NULL, -- 自动生成的主键
        UUID(), -- 住宿id
        '巴音布鲁克草原度假村',
        'Bayinbuluk Grassland Resort', -- 英文名称
        '位于巴音布鲁克草原附近，交通便利，环境优美，适合家庭和朋友短期入住。',
        '', -- 图片地址
        '500元',
        '巴音布鲁克草原', -- address
        '13800138000',
        '',
        '环境优美_服务到位',
        2, -- 类型：民宿
        80,
        (SELECT city_id FROM city WHERE city_name = '巴音布鲁克' LIMIT 1), -- 所属城市：巴音布鲁克
        CURRENT_TIMESTAMP, -- 创建时间
        CURRENT_TIMESTAMP, -- 更新时间
        0 -- 未删除
       ),
       (NULL,
        UUID(),
        '泉州西湖宾馆',
        'Quanzhou West Lake Hotel', -- 英文名称
        '毗邻西湖公园，融合闽南建筑风格，设有观景餐厅和茶室。',
        '',
        '600元',
        '泉州市丰泽区西湖公园', -- address
        '0595-22289999',
        '',
        '湖景房_闽南风格',
        1,
        180,
        (SELECT city_id FROM city WHERE city_name = '泉州' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '桂林香格里拉大酒店',
        'Guilin Shangri-La Hotel', -- 英文名称
        '坐拥漓江美景，配备室外泳池和SPA中心',
        '',
        '800元',
        '桂林市象山区环城南一路1号', -- address
        '0773-2698888',
        '',
        '江景房_豪华设施',
        1,
        450,
        (SELECT city_id FROM city WHERE city_name = '桂林' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0);