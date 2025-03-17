 use city_bag;

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user`
(
    -- 主键
    `id`                     BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    -- 用户id
    `user_id`                VARCHAR(36)      NOT NULL DEFAULT '' COMMENT '用户id',
    -- 昵称
    `user_name`              VARCHAR(16)      NOT NULL DEFAULT '' COMMENT '用户姓名',
    -- 用户头像URL
    `image_url` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '用户头像URL',
    -- 手机号
    `phone`                  CHAR(11)                  DEFAULT '' COMMENT '手机号码',
    -- 用户类型
    `auth_type`              TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户类型，0-微信用户; 1-学生用户',
    -- openid
    `openid`                 CHAR(36)                  DEFAULT '' COMMENT 'openid',
    -- unionid
    `unionid`                CHAR(36)                  DEFAULT '' COMMENT 'unionid',
    -- stu_id
    `stu_id`                 CHAR(36)                  DEFAULT '' COMMENT 'stu_id',
    -- password
    `password`               CHAR(36)                  DEFAULT '' COMMENT 'password',
    -- 认证标识 如openid/学号
    `auth_key`    VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '认证标识（openid/学号等）',
    -- 认证凭证 如密码
    `auth_secret` VARCHAR(128)              DEFAULT '' COMMENT '认证凭证（密码等）',
    -- 性别 为0-未选择；1-男；2-女
    `gender`                 TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '性别，0-未选择；1-男；2-女',
    -- 生日
    `birthday`               DATE             NOT NULL DEFAULT (CURRENT_DATE) COMMENT '生日',
    -- 个性签名
    `personalized_signature` TEXT COMMENT '个性签名',
    -- 权限 0-普通用户; 1-vip用户; 2-管理员; 3-超级管理员
    `jurisdiction`           TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '权限 0-普通用户; 1-vip用户; 2-管理员; 3-超级管理员',
    -- 点赞数量
    `like_count`             INT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '点赞数量',
    -- 创建时间
    `create_time`            DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 更新时间
    `update_time`            DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 软删除标识 0-未删除 1-已删除
    `is_deleted`             TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除;1-已删除'
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin COMMENT '用户表';
-- 用户id唯一索引
CREATE UNIQUE INDEX uk_user_user_id ON `user` (user_id);
# -- 手机号唯一索引
# CREATE UNIQUE INDEX uk_user_phone ON `user` (phone);
