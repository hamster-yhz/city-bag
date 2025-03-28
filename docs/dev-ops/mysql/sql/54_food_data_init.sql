-- 美食表示例数据
INSERT INTO `food` (id, food_id, food_name, english_name, introduce, image_url, open_time, price, address, phone, food_list, photo_url,
                    tag_list, like_count, city_id, create_time, update_time,
                    is_deleted)
VALUES (NULL, -- 自动生成的主键
        UUID(), -- 美食id
        '羊肉泡馍', -- 美食名称
        'Yangrou Pao Mo', -- 英文名称
        '西安的传统美食，以其独特的制作工艺和丰富的口味著称。', -- 介绍
        '',
        '08:30-17:00',
        '30元',
        '西安回民街123号', -- 补充地址
        '13800138000',
        '羊肉泡馍',
        '',
        '传统美食',
        500,
        (SELECT city_id FROM city WHERE city_name = '西安' LIMIT 1),
        CURRENT_TIMESTAMP, -- 创建时间
        CURRENT_TIMESTAMP, -- 更新时间
        0 -- 未删除
       ),
       (NULL, -- 自动生成的主键
        UUID(), -- 美食id
        '北京烤鸭', -- 美食名称
        'Beijing Roast Duck', -- 英文名称
        '北京烤鸭以其薄脆的皮和嫩滑的肉质闻名，传统的烤鸭搭配薄饼、葱和甜面酱一起吃。', -- 介绍
        '', -- 图片地址
        '08:30-17:00',-- 开放时间
        '80元', -- 人均价格
        '北京市东城区前门大街456号', -- 补充地址
        '13800138000', -- 联系电话
        '北京烤鸭,北京烤鸭,北京烤鸭', -- 著名美食列表
        '',
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
        'Xiao Long Bao', -- 英文名称
        '上海的小笼包以其鲜美的汁香和松软的面皮著称，种类有猪肉、鸡肉、鲍鱼等。',
        '', -- 图片地址
        '08:30-17:00',
        '20元',
        '上海市黄浦区南京东路789号', -- 补充地址
        '13800138000',
        '小笼包',
        '',
        '种类多样_松软',
        750,
        (SELECT city_id FROM city WHERE city_name = '上海' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '肠粉',
        'Chang Fen', -- 英文名称
        '广州的早餐代表，与香港肠粉类似，口感细腻，搭配各种配料如鲜片、猪肉、defgroup等。',
        '', -- 图片地址
        '08:30-17:00',
        '6元',
        '广州市天河区天河路101号', -- 补充地址
        '13800138000',
        '小笼包',
        '',
        '种类多样_松软',
        600,
        (SELECT city_id FROM city WHERE city_name = '广州' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '火锅',
        'Hot Pot', -- 英文名称
        '成都的火锅以麻辣鲜香著称，底料选择鸳鸯锅，一边是麻辣味，一边是清鲜味，食材丰富多样.',
        '', -- 图片地址
        '08:30-17:00',
        '400元',
        '成都市武侯区锦里中路202号', -- 补充地址
        '13800138000',
        '小笼包',
        '',
        '麻辣鲜香',
        1000,
        (SELECT city_id FROM city WHERE city_name = '成都' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '盐水鸭',
        'Salted Duck', -- 英文名称
        '南京的传统美食，鸭子肉质鲜嫩，皮酥麻，搭配独特的调料和腌制工艺，风味独特。',
        '', -- 图片地址
        '08:30-17:00',
        '100元',
        '南京市玄武区中山路303号', -- 补充地址
        '13800138000',
        '小笼包',
        '',
        '皮酥麻',
        400,
        (SELECT city_id FROM city WHERE city_name = '南京' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '凉皮',
        'Liang Pi', -- 英文名称
        '西安的传统小吃，口感爽滑，酸辣适中，是夏日消暑佳品。',
        '',
        '08:30-17:00',
        '10元',
        '西安回民街123号', -- 补充地址
        '13800138000',
        '凉皮',
        '',
        '传统小吃',
        400,
        (SELECT city_id FROM city WHERE city_name = '西安' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '肉夹馍',
        'Rou Jia Mo', -- 英文名称
        '西安的传统小吃，以其独特的肉质和馍皮著称，口感丰富。',
        '',
        '08:30-17:00',
        '15元',
        '西安回民街123号', -- 补充地址
        '13800138000',
        '肉夹馍',
        '',
        '传统小吃',
        350,
        (SELECT city_id FROM city WHERE city_name = '西安' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '腊汁肉',
        'La Zhu Rou', -- 英文名称
        '西安的传统美食，以其浓郁的肉香和独特的烹饪方法著称。',
        '',
        '08:30-17:00',
        '20元',
        '西安回民街123号', -- 补充地址
        '13800138000',
        '腊汁肉',
        '',
        '传统美食',
        300,
        (SELECT city_id FROM city WHERE city_name = '西安' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '酸汤水饺',
        'Suan Tang Jiao Zi', -- 英文名称
        '西安的传统美食，以其酸辣鲜美的汤底和Q弹的饺子皮著称。',
        '',
        '08:30-17:00',
        '25元',
        '西安回民街123号', -- 补充地址
        '13800138000',
        '酸汤水饺',
        '',
        '传统美食',
        250,
        (SELECT city_id FROM city WHERE city_name = '西安' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '凉面',
        'Liang Mian', -- 英文名称
        '西安的传统小吃，口感爽滑，酸辣适中，是夏日消暑佳品。',
        '',
        '08:30-17:00',
        '12元',
        '西安回民街123号', -- 补充地址
        '13800138000',
        '凉面',
        '',
        '传统小吃',
        380,
        (SELECT city_id FROM city WHERE city_name = '西安' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '科尔沁烤全羊',
        'Korqin Roast Whole Sheep', -- 英文名称
        '科尔沁的传统美食，以其独特的烤制工艺和鲜美的口感著称。', -- 介绍
        '',
        '08:30-17:00',
        '60元',
        '科尔沁市科尔沁大街404号', -- 补充地址
        '13800138000',
        '科尔沁烤全羊',
        '',
        '传统美食',
        150,
        (SELECT city_id FROM city WHERE city_name = '科尔沁' LIMIT 1),
        CURRENT_TIMESTAMP, -- 创建时间
        CURRENT_TIMESTAMP, -- 更新时间
        0 -- 未删除
       ),
       (NULL,
        UUID(),
        '科尔沁奶酪',
        'Korqin Cheese', -- 英文名称
        '科尔沁的传统美食，以其浓郁的奶香和独特的口感著称。', -- 介绍
        '',
        '08:30-17:00',
        '20元',
        '科尔沁市科尔沁大街404号', -- 补充地址
        '13800138000',
        '科尔沁奶酪',
        '',
        '传统美食',
        120,
        (SELECT city_id FROM city WHERE city_name = '科尔沁' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '科尔沁手抓肉',
        'Korqin Hand-Grabbed Meat', -- 英文名称
        '科尔沁的传统美食，以其鲜嫩的肉质和独特的烹饪方法著称。', -- 介绍
        '',
        '08:30-17:00',
        '40元',
        '科尔沁市科尔沁大街404号', -- 补充地址
        '13800138000',
        '科尔沁手抓肉',
        '',
        '传统美食',
        100,
        (SELECT city_id FROM city WHERE city_name = '科尔沁' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '巴音布鲁克手抓肉',
        'Bayinbuluk Hand-Grabbed Meat', -- 英文名称
        '巴音布鲁克的传统美食，以其独特的烹饪方法和鲜美的口感著称。',
        '',
        '08:30-17:00',
        '40元',
        '巴音布鲁克市巴音布鲁克路505号', -- 补充地址
        '13800138000',
        '巴音布鲁克手抓肉',
        '',
        '传统美食',
        100,
        (SELECT city_id FROM city WHERE city_name = '巴音布鲁克' LIMIT 1),
        CURRENT_TIMESTAMP, -- 创建时间
        CURRENT_TIMESTAMP, -- 更新时间
        0 -- 未删除
       ),
       (NULL,
        UUID(),
        '巴音布鲁克烤肉串',
        'Bayinbuluk Skewers', -- 英文名称
        '巴音布鲁克的传统美食，以其独特的烤制工艺和鲜美的口感著称。',
        '',
        '08:30-17:00',
        '20元',
        '巴音布鲁克市巴音布鲁克路505号', -- 补充地址
        '13800138000',
        '巴音布鲁克烤肉串',
        '',
        '传统美食',
        150,
        (SELECT city_id FROM city WHERE city_name = '巴音布鲁克' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL,
        UUID(),
        '巴音布鲁克奶茶',
        'Bayinbuluk Milk Tea', -- 英文名称
        '巴音布鲁克的传统美食，以其浓郁的奶香和独特的口感著称。',
        '',
        '08:30-17:00',
        '15元',
        '巴音布鲁克市巴音布鲁克路505号', -- 补充地址
        '13800138000',
        '巴音布鲁克奶茶',
        '',
        '传统美食',
        120,
        (SELECT city_id FROM city WHERE city_name = '巴音布鲁克' LIMIT 1),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0),
       (NULL, UUID(), '面线糊',
        'Mian Xian Hu', -- 英文名称
        '泉州特色早餐，以细面线煮成糊状，配卤大肠、醋肉等配料，佐以油条食用。',
        '',
        '06:00-13:00',
        '10元',
        '泉州市鲤城区中山路606号', -- 补充地址
        '13800138000',
        '面线糊',
        '',
        '传统早餐',
        2800, (SELECT city_id FROM city WHERE city_name = '泉州' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '海蛎煎',
        'Hai Shi Jian', -- 英文名称
        '闽南经典小吃，新鲜海蛎与地瓜粉煎制，外酥里嫩搭配甜辣酱。',
        '',
        '10:00-22:00',
        '20元',
        '泉州市鲤城区中山路606号', -- 补充地址
        '13800138000',
        '海蛎煎',
        '',
        '海鲜小吃',
        3200, (SELECT city_id FROM city WHERE city_name = '泉州' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '土笋冻',
        'Tu Sun Dong', -- 英文名称
        '特色清凉小吃，用星虫熬煮冷却成冻，佐以蒜泥酱油食用。',
        '',
        '10:00-21:00',
        '15元',
        '泉州市鲤城区中山路606号', -- 补充地址
        '13800138000',
        '土笋冻',
        '',
        '特色冷食',
        2500, (SELECT city_id FROM city WHERE city_name = '泉州' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '桂林米粉',
        'Gui Lin Mian Fen', -- 英文名称
        '卤水香浓，配锅烧牛肉，地道桂林早餐',
        '',
        '06:00-14:00',
        '5元',
        '桂林市象山区中山南路707号', -- 补充地址
        '13800138000',
        '卤菜粉',
        '',
        '特色小吃',
        4200, (SELECT city_id FROM city WHERE city_name = '桂林' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '阳朔啤酒鱼',
        'Yang Shuo Beer Fish', -- 英文名称
        '漓江鲜鱼佐以啤酒红烧，肉质鲜嫩',
        '',
        '11:00-22:00',
        '88元',
        '阳朔县阳朔镇漓江路808号', -- 补充地址
        '13800138000',
        '剑骨鱼',
        '',
        '地方名菜',
        3800, (SELECT city_id FROM city WHERE city_name = '桂林' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '荔浦扣肉',
        'Li Pu Kao Rou', -- 英文名称
        '芋头与五花肉层层相扣，肥而不腻',
        '',
        '10:30-21:30',
        '68元',
        '荔浦市荔浦镇荔江路909号', -- 补充地址
        '13800138000',
        '传统扣肉',
        '',
        '宴席硬菜',
        3200, (SELECT city_id FROM city WHERE city_name = '桂林' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);