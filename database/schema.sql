-- AI 版权侵权舆情管控 SaaS 系统 - 数据库初始化脚本
-- MySQL 8.0+

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ===========================
-- 1. 租户表
-- ===========================
DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '租户 ID',
  `tenant_code` VARCHAR(32) NOT NULL UNIQUE COMMENT '租户编码',
  `tenant_name` VARCHAR(100) NOT NULL COMMENT '租户名称',
  `contact_name` VARCHAR(50) COMMENT '联系人',
  `contact_phone` VARCHAR(20) COMMENT '联系电话',
  `contact_email` VARCHAR(100) COMMENT '联系邮箱',
  `subscription_plan_id` BIGINT COMMENT '套餐 ID',
  `subscription_start_time` DATETIME COMMENT '套餐开始时间',
  `subscription_end_time` DATETIME COMMENT '套餐结束时间',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_tenant_code` (`tenant_code`),
  INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户表';

-- ===========================
-- 2. 用户表
-- ===========================
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户 ID',
  `tenant_id` BIGINT NOT NULL COMMENT '租户 ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password_hash` VARCHAR(255) NOT NULL COMMENT '密码哈希',
  `nickname` VARCHAR(50) COMMENT '昵称',
  `avatar` VARCHAR(255) COMMENT '头像 URL',
  `phone` VARCHAR(20) COMMENT '手机号',
  `email` VARCHAR(100) COMMENT '邮箱',
  `role` TINYINT DEFAULT 3 COMMENT '角色：1-企业主账号，2-子管理员，3-操作员，4-只读账号',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
  `last_login_time` DATETIME COMMENT '最后登录时间',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_tenant_id` (`tenant_id`),
  INDEX `idx_username` (`username`),
  INDEX `idx_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ===========================
-- 3. 套餐表
-- ===========================
DROP TABLE IF EXISTS `subscription_plan`;
CREATE TABLE `subscription_plan` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '套餐 ID',
  `plan_name` VARCHAR(50) NOT NULL COMMENT '套餐名称',
  `plan_code` VARCHAR(32) NOT NULL UNIQUE COMMENT '套餐编码',
  `price` DECIMAL(10,2) COMMENT '价格',
  `monitor_frequency` VARCHAR(20) COMMENT '监测频次',
  `max_infringement_count` INT COMMENT '最大侵权数量',
  `max_platforms` INT COMMENT '最大监测平台数',
  `max_export_count` INT COMMENT '最大导出额度',
  `description` TEXT COMMENT '套餐描述',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-下架，1-上架',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_plan_code` (`plan_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='套餐表';

-- ===========================
-- 4. 文字资产表
-- ===========================
DROP TABLE IF EXISTS `asset_text`;
CREATE TABLE `asset_text` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '资产 ID',
  `tenant_id` BIGINT NOT NULL COMMENT '租户 ID',
  `asset_name` VARCHAR(100) NOT NULL COMMENT '资产名称',
  `content` TEXT NOT NULL COMMENT '文字内容',
  `asset_type` TINYINT DEFAULT 1 COMMENT '类型：1-品牌词，2-商标词，3-原创文案，4-文章全文',
  `keywords` VARCHAR(500) COMMENT '关键词',
  `semantic_fingerprint` TEXT COMMENT '语义指纹',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-删除，1-正常',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_tenant_id` (`tenant_id`),
  INDEX `idx_asset_type` (`asset_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文字资产表';

-- ===========================
-- 5. 图片资产表
-- ===========================
DROP TABLE IF EXISTS `asset_image`;
CREATE TABLE `asset_image` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '资产 ID',
  `tenant_id` BIGINT NOT NULL COMMENT '租户 ID',
  `asset_name` VARCHAR(100) NOT NULL COMMENT '资产名称',
  `image_url` VARCHAR(500) NOT NULL COMMENT '图片 URL',
  `image_hash` VARCHAR(64) COMMENT '图片哈希',
  `asset_type` TINYINT DEFAULT 1 COMMENT '类型：1-LOGO, 2-摄影图，3-设计图，4-插画',
  `global_feature` TEXT COMMENT '全局特征',
  `local_feature` TEXT COMMENT '局部特征',
  `watermark_info` VARCHAR(255) COMMENT '水印信息',
  `image_fingerprint` TEXT COMMENT '图像指纹',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-删除，1-正常',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_tenant_id` (`tenant_id`),
  INDEX `idx_asset_type` (`asset_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图片资产表';

-- ===========================
-- 6. 视频资产表
-- ===========================
DROP TABLE IF EXISTS `asset_video`;
CREATE TABLE `asset_video` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '资产 ID',
  `tenant_id` BIGINT NOT NULL COMMENT '租户 ID',
  `asset_name` VARCHAR(100) NOT NULL COMMENT '资产名称',
  `video_url` VARCHAR(500) NOT NULL COMMENT '视频 URL',
  `video_hash` VARCHAR(64) COMMENT '视频哈希',
  `duration` INT COMMENT '视频时长 (秒)',
  `key_frames` TEXT COMMENT '关键帧',
  `audio_fingerprint` TEXT COMMENT '音频指纹',
  `subtitle_text` TEXT COMMENT '字幕文本',
  `video_fingerprint` TEXT COMMENT '视频指纹',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-删除，1-正常',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频资产表';

-- ===========================
-- 7. 监测任务表
-- ===========================
DROP TABLE IF EXISTS `monitor_task`;
CREATE TABLE `monitor_task` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '任务 ID',
  `tenant_id` BIGINT NOT NULL COMMENT '租户 ID',
  `task_name` VARCHAR(100) NOT NULL COMMENT '任务名称',
  `asset_type` TINYINT NOT NULL COMMENT '资产类型：1-文字，2-图片，3-视频',
  `asset_ids` VARCHAR(500) COMMENT '关联资产 ID 列表',
  `monitor_platforms` TEXT COMMENT '监测平台列表',
  `monitor_frequency` TINYINT DEFAULT 2 COMMENT '监测频率：1-实时，2-小时级，3-每日，4-每周',
  `monitor_time_range` VARCHAR(50) COMMENT '监测时段',
  `infringement_threshold` DECIMAL(5,2) DEFAULT 0.8 COMMENT '侵权判定阈值',
  `priority` TINYINT DEFAULT 1 COMMENT '优先级：1-低，2-中，3-高',
  `status` TINYINT DEFAULT 0 COMMENT '状态：0-暂停，1-运行中，2-已停止',
  `last_run_time` DATETIME COMMENT '最后运行时间',
  `next_run_time` DATETIME COMMENT '下次运行时间',
  `total_scan_count` BIGINT DEFAULT 0 COMMENT '累计检索次数',
  `total_infringement_count` BIGINT DEFAULT 0 COMMENT '累计侵权数量',
  `created_by` BIGINT COMMENT '创建人',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_tenant_id` (`tenant_id`),
  INDEX `idx_status` (`status`),
  INDEX `idx_asset_type` (`asset_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='监测任务表';

-- ===========================
-- 8. 侵权记录表
-- ===========================
DROP TABLE IF EXISTS `infringement_record`;
CREATE TABLE `infringement_record` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录 ID',
  `tenant_id` BIGINT NOT NULL COMMENT '租户 ID',
  `task_id` BIGINT NOT NULL COMMENT '任务 ID',
  `asset_id` BIGINT NOT NULL COMMENT '资产 ID',
  `asset_type` TINYINT NOT NULL COMMENT '资产类型：1-文字，2-图片，3-视频',
  `infringement_url` VARCHAR(500) NOT NULL COMMENT '侵权链接',
  `infringement_title` VARCHAR(200) COMMENT '侵权标题',
  `infringement_content` TEXT COMMENT '侵权内容',
  `publish_account` VARCHAR(100) COMMENT '发布账号',
  `publish_platform` VARCHAR(50) COMMENT '发布平台',
  `publish_time` DATETIME COMMENT '发布时间',
  `infringement_type` VARCHAR(50) COMMENT '侵权类型',
  `similarity_score` DECIMAL(5,2) COMMENT '相似度分数',
  `risk_level` TINYINT DEFAULT 2 COMMENT '风险等级：1-疑似侵权，2-高度侵权，3-确认侵权',
  `spread_count` INT DEFAULT 0 COMMENT '传播数据',
  `process_status` TINYINT DEFAULT 0 COMMENT '处理状态：0-未处理，1-已处理，2-已忽略',
  `process_remark` VARCHAR(500) COMMENT '处理备注',
  `processed_by` BIGINT COMMENT '处理人',
  `processed_at` DATETIME COMMENT '处理时间',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_tenant_id` (`tenant_id`),
  INDEX `idx_task_id` (`task_id`),
  INDEX `idx_asset_id` (`asset_id`),
  INDEX `idx_risk_level` (`risk_level`),
  INDEX `idx_process_status` (`process_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='侵权记录表';

-- ===========================
-- 9. 证据表
-- ===========================
DROP TABLE IF EXISTS `evidence`;
CREATE TABLE `evidence` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '证据 ID',
  `tenant_id` BIGINT NOT NULL COMMENT '租户 ID',
  `infringement_id` BIGINT NOT NULL COMMENT '侵权记录 ID',
  `evidence_type` TINYINT DEFAULT 1 COMMENT '证据类型：1-截图，2-源码，3-链接',
  `evidence_url` VARCHAR(500) NOT NULL COMMENT '证据文件 URL',
  `timestamp` BIGINT NOT NULL COMMENT '时间戳',
  `timestamp_cert` VARCHAR(100) COMMENT '时间戳证书',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_tenant_id` (`tenant_id`),
  INDEX `idx_infringement_id` (`infringement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='证据表';

-- ===========================
-- 10. 白名单表
-- ===========================
DROP TABLE IF EXISTS `whitelist`;
CREATE TABLE `whitelist` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '白名单 ID',
  `tenant_id` BIGINT NOT NULL COMMENT '租户 ID',
  `whitelist_type` TINYINT NOT NULL COMMENT '类型：1-授权主体，2-站点，3-内容',
  `whitelist_value` VARCHAR(500) NOT NULL COMMENT '白名单值',
  `description` VARCHAR(200) COMMENT '描述',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_tenant_id` (`tenant_id`),
  INDEX `idx_whitelist_type` (`whitelist_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='白名单表';

-- ===========================
-- 11. 操作日志表
-- ===========================
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志 ID',
  `tenant_id` BIGINT NOT NULL COMMENT '租户 ID',
  `user_id` BIGINT NOT NULL COMMENT '用户 ID',
  `operation_type` VARCHAR(50) COMMENT '操作类型',
  `operation_module` VARCHAR(50) COMMENT '操作模块',
  `operation_content` TEXT COMMENT '操作内容',
  `request_ip` VARCHAR(50) COMMENT '请求 IP',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_tenant_id` (`tenant_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- ===========================
-- 12. 通知记录表
-- ===========================
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '通知 ID',
  `tenant_id` BIGINT NOT NULL COMMENT '租户 ID',
  `user_id` BIGINT NOT NULL COMMENT '用户 ID',
  `notification_type` TINYINT DEFAULT 1 COMMENT '类型：1-站内信，2-短信，3-邮箱，4-企业微信/钉钉',
  `title` VARCHAR(200) NOT NULL COMMENT '通知标题',
  `content` TEXT NOT NULL COMMENT '通知内容',
  `infringement_id` BIGINT COMMENT '关联侵权记录 ID',
  `is_read` TINYINT DEFAULT 0 COMMENT '是否已读：0-未读，1-已读',
  `send_status` TINYINT DEFAULT 0 COMMENT '发送状态：0-待发送，1-已发送，2-发送失败',
  `send_time` DATETIME COMMENT '发送时间',
  `read_time` DATETIME COMMENT '阅读时间',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_tenant_id` (`tenant_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_is_read` (`is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知记录表';

SET FOREIGN_KEY_CHECKS = 1;
