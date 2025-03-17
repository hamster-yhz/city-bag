use city_bag;

DROP TABLE IF EXISTS `user_collection`;
CREATE TABLE IF NOT EXISTS `user_collection`
(
    `id`            BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `collection_id` VARCHAR(36)      NOT NULL DEFAULT '' COMMENT '收藏记录ID',
    `user_id`       VARCHAR(36)      NOT NULL DEFAULT '' COMMENT '用户ID',
    `entity_type`   VARCHAR(36)      NOT NULL DEFAULT '' COMMENT '实体类型 city food dormitory scenic_spot',
    `entity_id`     VARCHAR(36)      NOT NULL DEFAULT '' COMMENT '实体ID',
    `create_time`   DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`   DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_deleted`    TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除 1-已删除'

) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户收藏表';

CREATE UNIQUE INDEX idx_user_entity on `user_collection`(user_id, entity_type, entity_id);