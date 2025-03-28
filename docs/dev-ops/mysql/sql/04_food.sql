DROP TABLE IF EXISTS `food`;
CREATE TABLE IF NOT EXISTS `food`
(
    -- 主键
    `id`          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    -- 美食id
    `food_id`     VARCHAR(36)      NOT NULL DEFAULT '' COMMENT '美食id',
    -- 名称
    `food_name`   VARCHAR(16)      NOT NULL DEFAULT '' COMMENT '美食名',
    -- 英文名称
    `english_name` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '英文名',
    -- 美食介绍
    `introduce`   TEXT COMMENT '美食介绍',
    -- 美食封面图URL
    `image_url`   VARCHAR(255)     NOT NULL DEFAULT '' COMMENT '美食封面图URL',
    -- 开放时间
    `open_time`   VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '开放时间',
    -- 人均价格
    `price`       VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '人均价格',
    -- 美食地址
    `address`          VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '美食地址',
    -- 联系电话
    `phone`       VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '联系电话',
    -- 著名美食列表
    `food_list`   VARCHAR(255)     NOT NULL DEFAULT '' COMMENT '著名美食列表（顿号分隔）',
    -- 美食图片 通过/间隔
    `photo_url`        VARCHAR(1024)     NOT NULL DEFAULT '' COMMENT '美食图片（通过/间隔）',
    -- 标签 通过_间隔
    `tag_list`    VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '标签（通过_间隔）',

    -- 乐观锁版本号 --
    `version`     INT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    -- 点赞数量
    `like_count`  INT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '点赞数量',
    -- 所属城市(逻辑外键)
    `city_id`     VARCHAR(36)      NOT NULL DEFAULT '' COMMENT '城市id',
    -- 创建时间
    `create_time` DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 更新时间
    `update_time` DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 软删除标识 0-未删除 1-已删除
    `is_deleted`  TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除;1-已删除'
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin COMMENT '美食表';
-- 美食id唯一索引
CREATE UNIQUE INDEX uk_food_food_id ON `food` (food_id);
-- 美食名称唯一索引
CREATE UNIQUE INDEX uk_food_food_name ON `food` (food_name);

