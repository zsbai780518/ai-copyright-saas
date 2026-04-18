# API 接口文档

## 认证接口

### 用户登录
```
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}

Response:
{
  "code": 200,
  "message": "success",
  "data": {
    "tokenName": "Authorization",
    "tokenValue": "xxx-xxx-xxx"
  }
}
```

### 用户注册
```
POST /api/auth/register
Content-Type: application/json

{
  "username": "newuser",
  "password": "123456",
  "phone": "13800138000",
  "tenantName": "测试企业"
}
```

### 获取当前用户
```
GET /api/auth/current
Authorization: <token>
```

### 退出登录
```
POST /api/auth/logout
Authorization: <token>
```

---

## 资产管理接口

### 获取文字资产列表
```
GET /api/asset/text/list?pageNum=1&pageSize=10
Authorization: <token>
```

### 创建文字资产
```
POST /api/asset/text
Authorization: <token>
Content-Type: application/json

{
  "assetName": "品牌名称",
  "assetType": 1,
  "content": "资产内容",
  "keywords": "关键词 1，关键词 2"
}
```

### 删除文字资产
```
DELETE /api/asset/text/{id}
Authorization: <token>
```

### 批量导入文字资产
```
POST /api/asset/text/batch
Authorization: <token>
Content-Type: application/json

[
  {"assetName": "资产 1", "assetType": 1, "content": "内容 1"},
  {"assetName": "资产 2", "assetType": 2, "content": "内容 2"}
]
```

---

## 监测任务接口

### 获取任务列表
```
GET /api/task/list
Authorization: <token>
```

### 创建监测任务
```
POST /api/task
Authorization: <token>
Content-Type: application/json

{
  "taskName": "品牌词监测",
  "assetType": 1,
  "assetIds": "1,2,3",
  "monitorPlatforms": "微信公众号，抖音，微博",
  "monitorFrequency": 2,
  "infringementThreshold": 0.8,
  "priority": 2
}
```

### 启动任务
```
PUT /api/task/{id}/start
Authorization: <token>
```

### 暂停任务
```
PUT /api/task/{id}/stop
Authorization: <token>
```

### 删除任务
```
DELETE /api/task/{id}
Authorization: <token>
```

---

## 侵权记录接口

### 获取侵权记录列表
```
GET /api/infringement/list?pageNum=1&pageSize=10&riskLevel=2&processStatus=0
Authorization: <token>

参数说明:
- riskLevel: 风险等级 (1-疑似，2-高度，3-确认)
- processStatus: 处理状态 (0-未处理，1-已处理，2-已忽略)
```

### 标记为已处理
```
PUT /api/infringement/{id}/process
Authorization: <token>
Content-Type: text/plain

处理备注内容
```

### 忽略侵权记录
```
PUT /api/infringement/{id}/ignore
Authorization: <token>
```

---

## 报告导出接口

### 导出侵权分析报告
```
GET /api/report/export?startDate=2026-04-01&endDate=2026-04-18
Authorization: <token>

返回：Word 文档二进制流 (application/octet-stream)
```

---

## 响应格式说明

### 成功响应
```json
{
  "code": 200,
  "message": "success",
  "data": { ... }
}
```

### 错误响应
```json
{
  "code": 500,
  "message": "错误信息",
  "data": null
}
```

### 分页响应
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [...],
    "total": 100,
    "size": 10,
    "current": 1,
    "pages": 10
  }
}
```

---

## 数据字典

### 资产类型 (assetType)
| 值 | 说明 |
|---|---|
| 1 | 品牌词 |
| 2 | 商标词 |
| 3 | 原创文案 |
| 4 | 文章全文 |

### 监测频率 (monitorFrequency)
| 值 | 说明 |
|---|---|
| 1 | 实时 (5 分钟) |
| 2 | 小时级 |
| 3 | 每日 |
| 4 | 每周 |

### 风险等级 (riskLevel)
| 值 | 说明 |
|---|---|
| 1 | 疑似侵权 |
| 2 | 高度侵权 |
| 3 | 确认侵权 |

### 用户角色 (role)
| 值 | 说明 |
|---|---|
| 1 | 企业主账号 |
| 2 | 子管理员 |
| 3 | 操作员 |
| 4 | 只读账号 |
