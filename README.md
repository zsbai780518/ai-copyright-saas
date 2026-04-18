# AI 版权侵权舆情管控 SaaS 系统

## 项目简介

本系统为 SaaS 化全自动 AI 版权侵权舆情管控系统，基于多模态 AI 搜索引擎 + 分布式合规爬虫，专注解决企业/个人商标权、著作权、原创文字、图片、短视频等数字权益侵权监测问题。

## 核心功能

- ✅ **SaaS 多租户管理** - 账号权限、租户隔离、套餐订阅
- ✅ **原创资产入库** - 文字、图片、视频资产管理
- ✅ **全自动监测任务** - 定时轮询、全网检索
- ✅ **AI 侵权识别** - 多模态相似度比对
- ✅ **证据固化** - 自动截图、时间戳存证
- ✅ **预警通知** - 站内信、短信、邮箱、企微/钉钉
- ✅ **Word 报告导出** - 自动生成专业侵权分析报告

## 技术架构

### 后端
- **框架**: SpringBoot 3.x
- **ORM**: MyBatis-Plus
- **权限**: Sa-Token
- **数据库**: MySQL 8.0
- **缓存**: Redis
- **检索**: Elasticsearch
- **调度**: XXL-Job
- **存储**: MinIO/OSS
- **报告**: Apache POI

### 前端
- **框架**: UniApp + Vue3 + Vite
- **UI**: Element Plus
- **状态管理**: Pinia
- **语言**: TypeScript

## 快速开始

### 1. 数据库初始化

```bash
mysql -u root -p < database/schema.sql
```

### 2. 后端启动

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

服务启动在 `http://localhost:8080/api`

### 3. 前端启动

```bash
cd frontend
npm install
npm run dev
```

前端访问 `http://localhost:3000`

## API 文档

详见 [docs/API.md](docs/API.md)

## 项目结构

```
ai-copyright-saas/
├── backend/                 # SpringBoot 后端
│   ├── src/main/java/
│   │   └── com/copyright/saas/
│   │       ├── controller/  # REST API
│   │       ├── service/     # 业务逻辑
│   │       ├── mapper/      # 数据访问
│   │       ├── entity/      # 实体类
│   │       ├── dto/         # 数据传输对象
│   │       ├── config/      # 配置类
│   │       ├── job/         # 定时任务
│   │       └── util/        # 工具类
│   └── src/main/resources/
│       ├── application.yml
│       └── mapper/
├── frontend/                # Vue3 前端
│   └── src/
│       ├── pages/          # 页面组件
│       ├── components/     # 通用组件
│       ├── api/            # API 调用
│       ├── stores/         # 状态管理
│       └── router/         # 路由配置
├── database/               # 数据库脚本
│   └── schema.sql
└── docs/                   # 文档
    └── API.md
```

## 开发阶段规划

### V1.0 (核心功能)
- [x] SaaS 多租户账号、权限管理
- [x] 文字/图片原创资产入库
- [x] 监测任务配置与调度
- [x] 基础侵权识别
- [x] Word 报告导出

### V2.0 (功能完善)
- [ ] 视频侵权 AI 识别
- [ ] 扩展监测平台覆盖
- [ ] 数据统计可视化

### V3.0 (功能升级)
- [ ] 侵权维权建议生成
- [ ] 批量任务并行监测
- [ ] 系统安全加固

## 合规说明

1. 网络爬虫严格遵守各平台 robots 协议
2. 仅抓取全网公开可访问内容
3. AI 侵权判定结果仅供维权参考，不具备法律直接判定效力
4. 用户数据全程加密存储

## License

MIT License - See [LICENSE](LICENSE) for details.

## Author

GitHub: [@195610775](https://github.com/195610775)

## Support

If you have any questions or issues, please open an issue on GitHub.
