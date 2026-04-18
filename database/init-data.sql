-- AI 版权侵权舆情管控 SaaS 系统 - 初始化数据
-- MySQL 8.0+

SET NAMES utf8mb4;

-- ===========================
-- 1. 初始化套餐数据
-- ===========================
INSERT INTO `subscription_plan` (`plan_name`, `plan_code`, `price`, `monitor_frequency`, `max_infringement_count`, `max_platforms`, `max_export_count`, `description`, `status`) VALUES
('基础版', 'basic', 99.00, '每日', 100, 5, 10, '适合个人用户，基础侵权监测', 1),
('专业版', 'professional', 299.00, '小时级', 500, 10, 50, '适合中小企业，全面侵权监测', 1),
('旗舰版', 'enterprise', 999.00, '实时', 9999, 99, 999, '适合大型企业，全平台实时监测', 1);

-- ===========================
-- 2. 初始化管理员账号
-- ===========================
-- 密码：admin123 (SHA256 哈希)
INSERT INTO `user` (`tenant_id`, `username`, `password_hash`, `nickname`, `role`, `status`) VALUES
(1, 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '系统管理员', 1, 1);

-- ===========================
-- 3. 初始化演示数据 - 文字资产
-- ===========================
INSERT INTO `asset_text` (`tenant_id`, `asset_name`, `content`, `asset_type`, `keywords`, `status`) VALUES
(1, '品牌名称', '阿里云无影', 1, '阿里云，无影，云计算', 1),
(1, '商标词', 'JVS Claw', 2, 'JVS,Claw,OpenClaw', 1),
(1, '原创文案', '天行健，君子以自强不息。地势坤，君子以厚德载物。', 3, '易经，国学，传统文化', 1),
(1, '产品描述', 'AI 版权侵权舆情管控 SaaS 系统，基于多模态 AI 搜索引擎，实现 7×24 小时全自动全网侵权监测。', 4, 'AI，版权，侵权监测，SaaS', 1);

-- ===========================
-- 4. 初始化演示数据 - 监测任务
-- ===========================
INSERT INTO `monitor_task` (`tenant_id`, `task_name`, `asset_type`, `asset_ids`, `monitor_platforms`, `monitor_frequency`, `infringement_threshold`, `priority`, `status`) VALUES
(1, '品牌词监测', 1, '1,2', '微信公众号，抖音，微博，百度', 2, 0.8, 2, 1),
(1, '文案侵权监测', 1, '3,4', '微信公众号，知乎，简书', 3, 0.75, 1, 1);

-- ===========================
-- 5. 初始化演示数据 - 侵权记录
-- ===========================
INSERT INTO `infringement_record` (`tenant_id`, `task_id`, `asset_id`, `asset_type`, `infringement_url`, `infringement_title`, `publish_account`, `publish_platform`, `publish_time`, `infringement_type`, `similarity_score`, `risk_level`, `process_status`) VALUES
(1, 1, 1, 1, 'https://mp.weixin.qq.com/s/xxx', '某公众号使用相似品牌名', '某公众号', '微信公众号', '2026-04-15 10:30:00', '商标侵权', 0.92, 3, 0),
(1, 1, 2, 1, 'https://weibo.com/xxx', '微博账号冒用品牌', '某微博用户', '微博', '2026-04-16 14:20:00', '品牌冒用', 0.88, 2, 0),
(1, 2, 3, 1, 'https://www.zhihu.com/question/xxx', '知乎文章抄袭原创文案', '某知乎用户', '知乎', '2026-04-17 09:15:00', '文案抄袭', 0.95, 3, 1);

-- ===========================
-- 6. 初始化白名单数据
-- ===========================
INSERT INTO `whitelist` (`tenant_id`, `whitelist_type`, `whitelist_value`, `description`, `status`) VALUES
(1, 1, '阿里云官方', '阿里云官方账号', 1),
(1, 2, 'zhihu.com', '知乎平台（允许合理引用）', 1),
(1, 3, '转载或引用请注明出处', '合理引用声明', 1);

-- ===========================
-- 7. 初始化通知模板
-- ===========================
-- 这里可以添加通知模板表，如果需要的话

COMMIT;
