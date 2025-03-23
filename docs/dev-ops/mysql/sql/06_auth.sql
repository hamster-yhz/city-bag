DROP TABLE IF EXISTS `auth`;
CREATE TABLE IF NOT EXISTS `auth`
(
    -- 主键
    `id`          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    -- 用户id
    `user_id`     VARCHAR(36)      NOT NULL DEFAULT '' COMMENT '用户id',
    -- 手机号
    `phone`       CHAR(11)         NOT NULL DEFAULT '' COMMENT '手机号码',
    -- 认证类型
    `auth_type`   TINYINT          NOT NULL DEFAULT '' COMMENT '认证类型（1-学号 2-微信 3-手机）',
    -- 认证标识 如openid/学号
    `auth_key`    VARCHAR(128)     NOT NULL DEFAULT '' COMMENT '认证标识（openid/学号等）',
    -- 认证凭证 如密码
    `auth_secret` VARCHAR(128)              DEFAULT '' COMMENT '认证凭证（密码等）',
    -- 创建时间
    `create_time` DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 更新时间
    `update_time` DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 软删除标识 0-未删除 1-已删除
    `is_deleted`  TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除;1-已删除'

) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin COMMENT '用户认证关联表';

CREATE UNIQUE INDEX idx_auth_unique ON `auth` (auth_type, auth_key) COMMENT '认证方式唯一约束';
CREATE INDEX idx_user_id ON `auth` (user_id) COMMENT '用户ID索引';;