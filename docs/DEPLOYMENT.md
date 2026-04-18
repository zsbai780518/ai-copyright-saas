# 部署指南

本文档介绍如何将 AI 版权侵权舆情管控 SaaS 系统部署到生产环境。

---

## 📋 部署前准备

### 服务器要求

| 组件 | 最低配置 | 推荐配置 |
|------|---------|---------|
| CPU | 2 核 | 4 核+ |
| 内存 | 4GB | 8GB+ |
| 磁盘 | 20GB | 50GB+ SSD |
| 带宽 | 5Mbps | 10Mbps+ |

### 软件依赖

- JDK 17+
- MySQL 8.0+
- Redis 7.0+
- Nginx (可选，用于反向代理)

---

## 🚀 部署方式一：单机部署

### 1. 安装依赖

```bash
# 安装 JDK 17
sudo apt update
sudo apt install openjdk-17-jdk -y

# 安装 MySQL 8.0
sudo apt install mysql-server -y

# 安装 Redis
sudo apt install redis-server -y
```

### 2. 配置数据库

```bash
# 登录 MySQL
mysql -u root -p

# 创建数据库和用户
CREATE DATABASE ai_copyright_saas DEFAULT CHARACTER SET utf8mb4;
CREATE USER 'copyright'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON ai_copyright_saas.* TO 'copyright'@'localhost';
FLUSH PRIVILEGES;
EXIT;

# 导入表结构
mysql -u copyright -p ai_copyright_saas < database/schema.sql
```

### 3. 部署后端

```bash
# 上传或克隆项目
git clone https://github.com/zsbai780518/ai-copyright-saas.git
cd ai-copyright-saas/backend

# 修改配置文件
vim src/main/resources/application.yml
# 修改数据库连接、Redis 连接等配置

# 编译打包
mvn clean package -DskipTests

# 创建启动脚本
cat > start.sh << 'EOF'
#!/bin/bash
nohup java -jar -Xms512m -Xmx2g \
  -Dspring.profiles.active=prod \
  target/ai-copyright-saas-1.0.0.jar \
  > app.log 2>&1 &
echo "Application started!"
EOF

chmod +x start.sh

# 启动服务
./start.sh

# 查看日志
tail -f app.log
```

### 4. 部署前端

```bash
cd ../frontend

# 安装依赖
npm install

# 修改 API 地址
vim src/api/request.ts
# 将 baseURL 改为生产环境后端地址

# 构建生产版本
npm run build

# 使用 Nginx 托管
sudo cp -r dist/* /var/www/html/

# 配置 Nginx
sudo vim /etc/nginx/sites-available/copyright-saas
```

Nginx 配置示例：

```nginx
server {
    listen 80;
    server_name your-domain.com;
    
    # 前端静态文件
    location / {
        root /var/www/html;
        index index.html;
        try_files $uri $uri/ /index.html;
    }
    
    # 后端 API 代理
    location /api/ {
        proxy_pass http://localhost:8080/api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
```

```bash
# 启用站点
sudo ln -s /etc/nginx/sites-available/copyright-saas /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl restart nginx
```

---

## 🐳 部署方式二：Docker 部署

### 1. 创建 Docker Compose 配置

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: ai_copyright_saas
      MYSQL_USER: copyright
      MYSQL_PASSWORD: your_password
    volumes:
      - mysql-data:/var/lib/mysql
      - ./database/schema.sql:/docker-entrypoint-initdb.d/schema.sql
    ports:
      - "3306:3306"
    networks:
      - copyright-network

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"
    networks:
      - copyright-network

  backend:
    build: ./backend
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/ai_copyright_saas?useSSL=false&serverTimezone=Asia/Shanghai
      SPRING_DATA_REDIS_HOST: redis
    depends_on:
      - mysql
      - redis
    ports:
      - "8080:8080"
    networks:
      - copyright-network

  frontend:
    build: ./frontend
    depends_on:
      - backend
    ports:
      - "80:80"
    networks:
      - copyright-network

volumes:
  mysql-data:

networks:
  copyright-network:
    driver: bridge
```

### 2. 创建 Dockerfile

**backend/Dockerfile**:
```dockerfile
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**frontend/Dockerfile**:
```dockerfile
FROM node:18-alpine AS build
WORKDIR /app
COPY . .
RUN npm install && npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
```

### 3. 启动服务

```bash
docker-compose up -d

# 查看日志
docker-compose logs -f

# 停止服务
docker-compose down
```

---

## ☁️ 部署方式三：云服务器部署

### 阿里云 ECS

1. 购买 ECS 实例（推荐 4 核 8G）
2. 配置安全组（开放 80、443、8080 端口）
3. 按照单机部署步骤操作
4. 绑定域名并配置 SSL 证书

### 腾讯云 CVM

1. 购买 CVM 实例
2. 配置防火墙
3. 使用镜像市场快速部署 LAMP/LNMP
4. 部署应用

---

## 🔒 安全加固建议

### 1. 数据库安全

```sql
-- 限制远程访问
UPDATE mysql.user SET host='localhost' WHERE user='copyright';
FLUSH PRIVILEGES;

-- 定期备份
mysqldump -u copyright -p ai_copyright_saas > backup_$(date +%Y%m%d).sql
```

### 2. 应用安全

- 修改默认密码
- 启用 HTTPS
- 配置防火墙
- 定期更新依赖

### 3. 日志监控

```bash
# 配置日志轮转
sudo vim /etc/logrotate.d/copyright-saas

# 添加监控告警（如使用 Prometheus + Grafana）
```

---

## 📊 性能优化

### 1. JVM 调优

```bash
java -Xms2g -Xmx4g \
  -XX:+UseG1GC \
  -XX:MaxGCPauseMillis=200 \
  -jar app.jar
```

### 2. MySQL 调优

```ini
# /etc/mysql/my.cnf
[mysqld]
innodb_buffer_pool_size = 2G
max_connections = 500
query_cache_size = 64M
```

### 3. Redis 优化

```conf
# /etc/redis/redis.conf
maxmemory 2gb
maxmemory-policy allkeys-lru
```

---

## 🆘 常见问题

### Q1: 启动失败，端口被占用

```bash
# 查看端口占用
netstat -tlnp | grep 8080

# 杀死占用进程
kill -9 <PID>
```

### Q2: 数据库连接失败

检查配置文件中的数据库连接信息，确保 MySQL 服务正常运行。

### Q3: 前端页面空白

检查浏览器控制台错误，确认 API 地址配置正确。

---

## 📞 技术支持

如有问题，请提交 Issue: https://github.com/zsbai780518/ai-copyright-saas/issues
