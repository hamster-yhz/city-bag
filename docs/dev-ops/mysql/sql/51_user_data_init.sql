-- 用户表示例数据
INSERT INTO `user` (id, user_id, user_name, image_url, auth_key, auth_secret, auth_type, gender, birthday,
                    personalized_signature, jurisdiction, like_count, create_time, update_time, is_deleted)
VALUES (NULL, -- 自动生成的主键
        UUID(), -- 随机生成的user_id
        '张三', -- 用户名
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0.png', -- 头像地址
        'stu1234567890', -- stu_id
        'password123456', -- 密码
        2,
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
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0.png',
        'stu2345678901',
        'password2345678901',
        2,
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
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0.png',
        'stu3456789012',
        'password3456789012',
        2,
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
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0.png',
        'stu4567890123',
        'password4567890123',
        2,
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
        'd5e87bc8-49ae-4ea7-a5e6-78d31f96f528_0.png',
        'stu5678901234',
        'password5678901234',
        2,
        1,
        '20010505',
        '喜欢科技和编程！',
        3, -- 权限：超级管理员
        15,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0);