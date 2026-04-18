# 更新日志 (Changelog)

所有重要的项目变更都将记录在此文件中。

格式基于 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)，版本号遵循 [语义化版本](https://semver.org/lang/zh-CN/)。

---

## [未发布]

### 计划功能
- 视频侵权 AI 识别
- 数据可视化大屏
- 移动端小程序
- 批量任务并行处理
- 维权建议自动生成

---

## [1.0.0] - 2026-04-18

### ✨ 新增功能

#### SaaS 多租户管理
- 四级权限体系（企业主账号、子管理员、操作员、只读账号）
- 租户数据完全隔离
- 套餐订阅管理（基础版/专业版/旗舰版）
- 操作日志全程留痕

#### 原创资产入库
- 文字资产管理（品牌词、商标词、原创文案、文章全文）
- 图片资产管理（LOGO、摄影图、设计图、插画）
- 视频资产管理（短视频、口播视频、课程视频）
- AI 自动提取特征指纹

#### 全自动侵权监测
- 多平台覆盖（微信、抖音、微博、淘宝、百度等）
- 灵活监测频率（实时/小时级/每日/每周）
- 分布式爬虫自动抓取
- 多模态 AI 相似度比对

#### 侵权识别与证据固化
- 三级风险判定（疑似/高度/确认侵权）
- 自动截图、源码保存、链接留存
- 时间戳证据固化
- 实时预警通知

#### 报告生成与导出
- 标准化 Word 报告模板
- 按日/周/月/自定义周期生成
- 一键导出，直接用于维权举证

#### 合规白名单
- 授权主体白名单
- 站点白名单
- 内容白名单

### 🏗️ 技术架构

#### 后端
- SpringBoot 3.2+
- MyBatis-Plus 3.5.5
- Sa-Token 1.37+
- MySQL 8.0+
- Redis 7.0+
- Elasticsearch 8.x
- XXL-Job 2.4+
- Apache POI 5.2+

#### 前端
- Vue 3.4+
- Element Plus 2.4+
- Pinia 2.1+
- TypeScript 5.3+
- UniApp（跨端支持）

### 📚 文档

- README.md - 项目说明与快速开始
- docs/API.md - 完整 API 接口文档
- docs/DEPLOYMENT.md - 部署指南
- docs/QUICKSTART.md - 5 分钟快速开始
- .github/CONTRIBUTING.md - 贡献指南
- .github/CODE_OF_CONDUCT.md - 行为准则
- .github/SECURITY.md - 安全政策

### 🔧 开发工具

- GitHub Actions CI/CD
- Docker Compose 一键部署
- 多环境配置（dev/prod）
- Issue 和 PR 模板

### 📦 部署方式

- 本地开发部署
- Docker Compose 部署
- 云服务器部署（阿里云/腾讯云）

### 📝 数据库

- 12 张表完整设计
- 初始化数据脚本
- 演示数据

---

## 版本说明

### 语义化版本

- **主版本号（Major）**：不兼容的 API 变更
- **次版本号（Minor）**：向后兼容的功能新增
- **修订号（Patch）**：向后兼容的问题修复

### 版本标识

- `[未发布]` - 开发中功能
- `[1.0.0]` - 初始正式发布版本
- `v1.0.0-beta` - 测试版本
- `v1.0.0-alpha` - 预览版本

---

##  反馈

如有问题或建议，请通过以下方式反馈：

- 🐛 [GitHub Issues](https://github.com/zsbai780518/ai-copyright-saas/issues)
- 📧 Email: 195610775@qq.com
- 💬 讨论区：[GitHub Discussions](https://github.com/zsbai780518/ai-copyright-saas/discussions)

---

[未发布]: https://github.com/zsbai780518/ai-copyright-saas/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/zsbai780518/ai-copyright-saas/releases/tag/v1.0.0
