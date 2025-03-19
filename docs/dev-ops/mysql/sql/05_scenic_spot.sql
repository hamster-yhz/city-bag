use city_bag;

DROP TABLE IF EXISTS `scenic_spot`;
CREATE TABLE IF NOT EXISTS `scenic_spot`
(
    -- 主键
    `id`               BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    -- 景点id
    `scenic_spot_id`   VARCHAR(36)      NOT NULL DEFAULT '' COMMENT '景点id',
    -- 名称
    `scenic_spot_name` VARCHAR(16)      NOT NULL DEFAULT '' COMMENT '景点名',
    -- 景点介绍
    `introduce`        TEXT COMMENT '景点介绍',
    -- 景点封面图URL
    `image_url` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '景点封面图URL',
    -- 景点地址
    `address`          VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '景点地址',
    -- 开放时间
    `open_time`        VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '开放时间',
    -- 门票价格
    `price`            VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '门票价格',
    -- 联系电话
    `phone`            VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '联系电话',
    -- 内部景点列表
    `scenic_spot_list` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '内部景点列表（顿号分隔）',
    -- 旅拍图片 通过/间隔
    `photo_url`        VARCHAR(1024)     NOT NULL DEFAULT '' COMMENT '旅拍图片（通过/间隔）',
    -- 标签 通过_间隔
    `tag_list`              VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '标签（通过_间隔）',

    -- 参观用时
    `visit_time`       VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '参观用时',
    -- 景点类型
    `type`             TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '景点类型，0-未选择；1-vr；2-特色建筑',

    -- 乐观锁版本号 --
    `version` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    -- 点赞数量
    `like_count`       INT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '点赞数量',
    -- 所属城市(逻辑外键)
    `city_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '城市id',
    -- 创建时间
    `create_time`      DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 更新时间
    `update_time`      DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 软删除标识 0-未删除 1-已删除
    `is_deleted`       TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除;1-已删除'
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin COMMENT '景点表';
-- 景点id唯一索引
CREATE UNIQUE INDEX uk_scenic_spot_scenic_spot_id ON `scenic_spot` (scenic_spot_id);
-- 景点名称唯一索引
CREATE UNIQUE INDEX uk_scenic_spot_scenic_spot_name ON `scenic_spot` (scenic_spot_name);
