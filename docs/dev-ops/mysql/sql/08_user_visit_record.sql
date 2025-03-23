DROP TABLE IF EXISTS `user_visit_record`;
CREATE TABLE IF NOT EXISTS `user_visit_record`
(
    `id`            BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `visit_record_id` VARCHAR(36)      NOT NULL DEFAULT '' COMMENT '浏览记录ID',
    `user_id`       VARCHAR(36)      NOT NULL DEFAULT '' COMMENT '用户ID',
    `entity_type`   VARCHAR(36)      NOT NULL DEFAULT '' COMMENT '实体类型 city food dormitory scenic_spot',
    `entity_id`     VARCHAR(36)      NOT NULL DEFAULT '' COMMENT '实体ID',
    `create_time`   DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`   DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_deleted`    TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除 1-已删除'

    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT CHARSET = utf8mb4 COMMENT ='用户浏览记录';

CREATE UNIQUE INDEX idx_user_entity on `user_visit_record`(user_id, entity_type, entity_id);