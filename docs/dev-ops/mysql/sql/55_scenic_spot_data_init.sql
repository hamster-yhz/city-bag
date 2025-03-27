-- 景点表示例数据
INSERT INTO `scenic_spot` (id, scenic_spot_id, scenic_spot_name, introduce, image_url,
                           address, open_time, price, phone, scenic_spot_list, photo_url, tag_list, visit_time,
                           type, like_count, city_id, create_time, update_time, is_deleted)
VALUES (NULL, UUID(), '兵马俑',
        '西安的著名景点，秦始皇陵兵马俑坑，规模宏大，历史价值极高，吸引着全球游客。',
        '',
        '西安市临潼区秦始皇兵马俑博物馆',
        '08:30-17:00',
        '150元',
        '029-81399166',
        '一号坑、二号坑、三号坑',
        '',
        '历史景点_必去景点',
        '3小时',
        1, 12000, (SELECT city_id FROM city WHERE city_name = '西安' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '故宫',
        '北京的紫禁城，明清两朝皇宫，规模宏大，文化历史价值极高，吸引着全球游客。',
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0.png',
        '北京市东城区景山前街4号',
        '08:30-17:00',
        '60元',
        '010-85007421',
        '太和殿、乾清宫、御花园、钟表馆',
        '城市详情/城市详情/北京/旅游景点/故宫.png',
        '最好看的景点_最舒适的环境',
        '3小时',
        1, 10000, (SELECT city_id FROM city WHERE city_name = '北京' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '外滩',
        '上海的象征之一，黄浦江边的历史文化景区，拥有各种风格的建筑群，夜景璀璨。',
        '',
        '上海市黄浦区中山东一路',
        '全天开放',
        '免费',
        '021-63213553',
        '外白渡桥、陈毅广场、黄浦公园、和平饭店',
        '',
        '热门景点_暑假必去',
        '2小时',
        1, 8000, (SELECT city_id FROM city WHERE city_name = '上海' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '广州塔',
        '又称小蛮腰，广州的地标建筑，高达610米， Observation decks 提供全景视角，夜晚灯光璀璨。',
        '',
        '广州市海珠区阅江西路222号',
        '09:30-22:30',
        '150元起',
        '020-89338222',
        '塔顶、影院',
        '',
        '最高建筑',
        '2.5小时',
        2, 7500, (SELECT city_id FROM city WHERE city_name = '广州' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '都江堰',
        '成都的水利工程瑰宝，具有2000多年的历史，是古代中国水利智慧的结晶。',
        '城市详情/城市详情/成都/旅游景点/乐山大佛.png',
        '成都市都江堰市公园路',
        '08:00-18:00',
        '80元',
        '028-87120836',
        '景点1、景点2',
        '',
        '华丽_名胜古迹',
        '4小时',
        1, 5000, (SELECT city_id FROM city WHERE city_name = '成都' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '中山陵',
        '南京的历史景点，纪念孙中山先生，是中国近代史的重要象征，建筑宏伟壮丽。',
        '',
        '南京市玄武区石象路7号',
        '08:30-17:00',
        '免费',
        '025-84431174',
        '景点1、景点2',
        '',
        '历史景点',
        '2小时',
        1, 6000, (SELECT city_id FROM city WHERE city_name = '南京' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '大雁塔',
        '西安的著名景点，唐代佛塔，历史悠久，是中国佛教建筑的代表作之一。',
        '',
        '西安市南郊大慈恩寺内',
        '08:30-17:30',
        '50元',
        '029-85433285',
        '大雁塔南广场、大雁塔北广场',
        '',
        '历史景点_必去景点',
        '2小时',
        1, 11000, (SELECT city_id FROM city WHERE city_name = '西安' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '古城墙',
        '西安的著名景点，明朝城墙，保存完好，是西安的象征性建筑之一。',
        '',
        '西安市城墙南门',
        '08:30-18:00',
        '30元',
        '029-87306677',
        '南门、北门、东门、西门',
        '',
        '历史景点_必去景点',
        '3小时',
        1, 10000, (SELECT city_id FROM city WHERE city_name = '西安' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '华清池',
        '西安的著名景点，唐代皇家园林，以温泉和园林景观著称，是中国古代园林艺术的典范。',
        '',
        '西安市临潼区华清路',
        '08:30-17:30',
        '120元',
        '029-81399166',
        '华清池景区、华清宫',
        '',
        '历史景点_必去景点',
        '2小时',
        1, 9000, (SELECT city_id FROM city WHERE city_name = '西安' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '钟鼓楼',
        '西安的著名景点，钟楼和鼓楼是西安的标志性建筑，历史悠久，是西安的象征之一。',
        '',
        '西安市碑林区钟楼街',
        '08:30-18:00',
        '30元',
        '029-87251277',
        '钟楼、鼓楼',
        '',
        '历史景点_必去景点',
        '1.5小时',
        1, 8500, (SELECT city_id FROM city WHERE city_name = '西安' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '回民街',
        '西安的著名景点，回民街是西安的美食街，拥有众多特色小吃和美食，是体验西安美食文化的绝佳去处。',
        '',
        '西安市碑林区回民街',
        '09:00-22:00',
        '免费',
        '029-87251277',
        '羊肉泡馍、凉皮、肉夹馍、腊汁肉',
        '',
        '美食街_必去景点',
        '2小时',
        1, 8000, (SELECT city_id FROM city WHERE city_name = '西安' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '科尔沁大草原',
        '科尔沁的著名景点，广阔的草原风光，适合徒步和露营。', -- 介绍
        '',
        '科尔沁大草原',
        '08:00-18:00',
        '免费',
        '13800138000',
        '科尔沁大草原',
        '',
        '自然风光_必去景点',
        '全天',
        1, 1200, (SELECT city_id FROM city WHERE city_name = '科尔沁' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '科尔沁博物馆',
        '科尔沁的著名景点，展示科尔沁的历史文化，适合文化爱好者。', -- 介绍
        '',
        '科尔沁博物馆',
        '09:00-17:00',
        '30元',
        '13800138000',
        '科尔沁博物馆',
        '',
        '历史文化_必去景点',
        '2小时',
        1, 1000, (SELECT city_id FROM city WHERE city_name = '科尔沁' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '科尔沁温泉',
        '科尔沁的著名景点，提供温泉泡澡，适合放松身心。', -- 介绍
        '',
        '科尔沁温泉',
        '08:00-20:00',
        '50元',
        '13800138000',
        '科尔沁温泉',
        '',
        '温泉_必去景点',
        '3小时',
        1, 800, (SELECT city_id FROM city WHERE city_name = '科尔沁' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '巴音布鲁克草原',
        '巴音布鲁克的著名景点，广阔的草原风光，适合徒步和露营。',
        '',
        '巴音布鲁克草原',
        '08:00-18:00',
        '免费',
        '13800138000',
        '巴音布鲁克草原',
        '',
        '自然风光_必去景点',
        '全天',
        1, 1200, (SELECT city_id FROM city WHERE city_name = '巴音布鲁克' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '天鹅湖',
        '巴音布鲁克的著名景点，以其清澈的湖水和成群的天鹅闻名。',
        '',
        '天鹅湖',
        '08:00-18:00',
        '免费',
        '13800138000',
        '天鹅湖',
        '',
        '自然风光_必去景点',
        '全天',
        1, 1000, (SELECT city_id FROM city WHERE city_name = '巴音布鲁克' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '草原石人',
        '巴音布鲁克的著名景点，古老的石人遗迹，具有重要的历史价值。',
        '',
        '草原石人遗址',
        '08:00-18:00',
        '免费',
        '13800138000',
        '草原石人',
        '',
        '历史文化_必去景点',
        '1小时',
        1, 800, (SELECT city_id FROM city WHERE city_name = '巴音布鲁克' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '清源山',
        '泉州城北屏障，有"闽海蓬莱第一山"美誉，包含老君岩、千手岩等道教文化景点。',
        '',
        '泉州市丰泽区清源山风景名胜区',
        '07:00-18:00',
        '70元',
        '0595-22771928',
        '老君岩、千手岩、天湖',
        '',
        '道教文化_自然风光',
        '4小时',
        1, 3500, (SELECT city_id FROM city WHERE city_name = '泉州' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '开元寺',
        '福建省内规模最大的佛教寺院，存有宋代石塔和弘一法师纪念馆。',
        '',
        '泉州市鲤城区西街176号',
        '06:30-17:30',
        '免费',
        '0595-22383285',
        '东西塔、大雄宝殿',
        '',
        '佛教文化_古建筑',
        '2小时',
        1, 4200, (SELECT city_id FROM city WHERE city_name = '泉州' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '崇武古城',
        '中国现存最完整的花岗岩滨海石城，可观惠安女民俗表演。',
        '',
        '泉州市惠安县崇武镇',
        '07:00-19:00',
        '45元',
        '0595-87683297',
        '古城墙、石雕园',
        '',
        '海防遗址_民俗文化',
        '3小时',
        1, 3800, (SELECT city_id FROM city WHERE city_name = '泉州' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '漓江',
        '中国锦绣河山的一颗明珠，桂林山水的精华所在',
        '',
        '桂林市灵川县',
        '08:00-17:00',
        '210元',
        '0773-8810132',
        '杨堤-兴坪段',
        '',
        '山水画廊_必游景点',
        '5小时',
        1, 9500, (SELECT city_id FROM city WHERE city_name = '桂林' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '象鼻山',
        '桂林城徽，形似巨象饮水的神奇山峰',
        '',
        '桂林市象山区民主路1号',
        '06:30-19:00',
        '55元',
        '0773-2586602',
        '象鼻山观景台',
        '',
        '城徽地标_摄影胜地',
        '1.5小时',
        1, 7800, (SELECT city_id FROM city WHERE city_name = '桂林' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
       (NULL, UUID(), '龙脊梯田',
        '世界梯田原乡，壮丽的高山梯田景观',
        '',
        '桂林市龙胜各族自治县',
        '全天开放',
        '80元',
        '0773-7583188',
        '金坑大寨',
        '',
        '农耕奇观_民族风情',
        '全天',
        1, 6800, (SELECT city_id FROM city WHERE city_name = '桂林' LIMIT 1),
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
