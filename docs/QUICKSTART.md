# 5 分钟快速开始

本指南帮助您在 5 分钟内快速启动项目并进行测试。

---

## 📋 前置要求

- ✅ JDK 17+
- ✅ MySQL 8.0+
- ✅ Redis 7.0+
- ✅ Maven 3.8+
- ✅ Node.js 18+

---

## 🚀 方式一：本地快速启动（推荐开发使用）

### 步骤 1：启动依赖服务

```bash
# 使用 Docker Compose 快速启动 MySQL 和 Redis
docker run -d --name copyright-mysql \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=ai_copyright_saas \
  -p 3306:3306 \
  mysql:8.0

docker run -d --name copyright-redis \
  -p 6379:6379 \
  redis:7-alpine
```

### 步骤 2：初始化数据库

```bash
mysql -u root -proot ai_copyright_saas < database/schema.sql
mysql -u root -proot ai_copyright_saas < database/init-data.sql
```

### 步骤 3：启动后端

```bash
cd backend

# 使用开发配置启动
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

后端启动后访问：http://localhost:8080/api/auth/current

### 步骤 4：启动前端

```bash
cd frontend

# 安装依赖（首次需要）
npm install

# 启动开发服务器
npm run dev
```

前端访问：http://localhost:3000

### 步骤 5：登录测试

```
用户名：admin
密码：admin123
```

---

## 🐳 方式二：Docker Compose 一键启动（推荐生产使用）

```bash
# 启动所有服务
docker-compose up -d

# 查看日志
docker-compose logs -f

# 访问前端
http://localhost:80

# 访问后端 API
http://localhost:8080/api
```

停止服务：
```bash
docker-compose down
```

---

## 🧪 测试 API

### 使用 curl 测试

```bash
# 登录
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# 获取当前用户（替换 token）
curl -X GET http://localhost:8080/api/auth/current \
  -H "Authorization: <your_token>"

# 获取资产列表
curl -X GET "http://localhost:8080/api/asset/text/list?pageNum=1&pageSize=10" \
  -H "Authorization: <your_token>"

# 创建监测任务
curl -X POST http://localhost:8080/api/task \
  -H "Content-Type: application/json" \
  -H "Authorization: <your_token>" \
  -d '{
    "taskName": "测试任务",
    "assetType": 1,
    "monitorFrequency": 2,
    "infringementThreshold": 0.8
  }'
```

### 使用 Postman

导入 Postman 集合（待添加）：
- 下载 [postman_collection.json](./postman_collection.json)
- 导入到 Postman
- 设置环境变量

---

## 🔧 常见问题

### Q1: 后端启动失败，提示端口被占用

```bash
# 查看端口占用
lsof -i :8080

# 杀死占用进程
kill -9 <PID>

# 或修改端口
vim backend/src/main/resources/application.yml
# 修改 server.port: 8081
```

### Q2: 前端启动失败，提示 node_modules 问题

```bash
# 删除 node_modules 重新安装
rm -rf node_modules package-lock.json
npm install
```

### Q3: 数据库连接失败

检查 MySQL 是否正常运行：
```bash
docker ps | grep mysql
mysql -u root -proot -e "SHOW DATABASES;"
```

### Q4: 登录提示密码错误

默认账号密码：
- 用户名：admin
- 密码：admin123

如果修改过密码，重新初始化：
```bash
mysql -u root -proot ai_copyright_saas < database/init-data.sql
```

---

## 📊 验证部署

### 后端健康检查

```bash
curl http://localhost:8080/api/auth/current
```

### 前端页面检查

浏览器访问 http://localhost:3000，应该看到登录页面。

### 数据库检查

```bash
mysql -u root -proot ai_copyright_saas -e "SHOW TABLES;"
```

应该看到 12 张表。

---

## 🎯 下一步

- 📖 阅读 [API 文档](./API.md) 了解完整接口
- 📚 阅读 [部署指南](./DEPLOYMENT.md) 了解生产部署
- 🔧 修改配置文件适配您的环境
- 🚀 开始使用系统功能

---

## 📞 需要帮助？

- 查看 [常见问题](https://github.com/zsbai780518/ai-copyright-saas/issues)
- 提交 [Issue](https://github.com/zsbai780518/ai-copyright-saas/issues/new)
- 联系作者：195610775@qq.com
