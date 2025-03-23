DROP TABLE IF EXISTS `dormitory`;
CREATE TABLE IF NOT EXISTS `dormitory`
(
    -- 主键
    `id`             BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    -- 住宿id
    `dormitory_id`   VARCHAR(36)      NOT NULL DEFAULT '' COMMENT '住宿id',
    -- 昵称
    `dormitory_name` VARCHAR(32)      NOT NULL DEFAULT '' COMMENT '住宿名',
    -- 住宿
    `introduce`      TEXT COMMENT '介绍',
    -- 景点封面图URL
    `image_url`      VARCHAR(255)     NOT NULL DEFAULT '' COMMENT '景点封面图URL',
    -- 住宿类型
    `type`           TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '住宿类型，0-未选择；1-酒店；2-民宿',
    -- 人均价格
    `price`          VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '人均价格',
    -- 联系电话
    `phone`          VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '联系电话',
    -- 实地图片 通过/间隔
    `photo_url`        VARCHAR(1024)     NOT NULL DEFAULT '' COMMENT '实地图片图片（通过/间隔）',
    -- 标签 通过_间隔
    `tag_list`       VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '标签（通过_间隔）',
    -- 乐观锁版本号 --
    `version`        INT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    -- 点赞数量
    `like_count`     INT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '点赞数量',
    -- 所属城市(逻辑外键)
    `city_id`        VARCHAR(36)      NOT NULL DEFAULT '' COMMENT '城市id',
    -- 创建时间
    `create_time`    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 更新时间
    `update_time`    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 软删除标识 0-未删除 1-已删除
    `is_deleted`     TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除;1-已删除'
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin COMMENT '住宿表';
-- 住宿id唯一索引
CREATE UNIQUE INDEX uk_dormitory_user_id ON `dormitory` (dormitory_id);
-- 唯一索引
# CREATE UNIQUE INDEX uk_dormitory_ ON `dormitory`();
