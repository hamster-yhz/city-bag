DROP TABLE IF EXISTS `city`;
CREATE TABLE IF NOT EXISTS `city` (
    -- 主键
    `id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    -- 城市id
    `city_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '城市id',
    -- 名称
    `city_name` VARCHAR(16) NOT NULL DEFAULT '' COMMENT '城市名',
    -- 英文名称
    `english_name` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '城市英文名',
    -- 城市介绍
    `city_introduce` TEXT COMMENT '城市介绍',
    -- 美食介绍
    `food_introduce` TEXT COMMENT '美食介绍',
    -- 景点介绍
    `scenic_spot_introduce` TEXT COMMENT '景点介绍',
    -- 城市封面图URL
    `image_url` VARCHAR(255) DEFAULT '' COMMENT '城市封面图URL',

    -- 乐观锁版本号 --
    `version` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    -- 点赞数量
    `like_count` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '点赞数量',
    -- 创建时间
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 更新时间
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 软删除标识 0-未删除 1-已删除
    `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除;1-已删除'
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '城市表';
-- 城市id唯一索引
CREATE UNIQUE INDEX uk_city_city_id ON `city`(city_id);
-- 城市名称唯一索引
CREATE UNIQUE INDEX uk_city_city_name ON `city`(city_name);