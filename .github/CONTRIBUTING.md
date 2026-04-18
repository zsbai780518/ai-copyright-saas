# 贡献指南

感谢您对本项目的关注！以下是参与贡献的指南。

## 📋 目录

- [行为准则](#行为准则)
- [我能如何贡献？](#我能如何贡献)
- [开发环境设置](#开发环境设置)
- [提交代码](#提交代码)
- [代码规范](#代码规范)

---

## 行为准则

本项目采用 [贡献者公约](CODE_OF_CONDUCT.md)。参与即表示您同意遵守该准则。

---

## 我能如何贡献？

### 报告 Bug

发现 Bug？请通过 [GitHub Issues](https://github.com/zsbai780518/ai-copyright-saas/issues) 报告。

提交 Bug 报告时请包含：
- 清晰的标题和描述
- 复现步骤
- 预期行为和实际行为
- 环境信息（操作系统、Java 版本等）
- 截图（如适用）

### 提出功能建议

欢迎提出新功能建议！请创建 Issue 并描述：
- 功能描述
- 使用场景
- 实现建议（可选）

### 提交代码

1. Fork 本仓库
2. 创建特性分支
3. 提交更改
4. 推送到分支
5. 创建 Pull Request

---

## 开发环境设置

### 后端

```bash
# 克隆仓库
git clone https://github.com/zsbai780518/ai-copyright-saas.git
cd ai-copyright-saas

# 安装依赖
cd backend
mvn clean install

# 运行
mvn spring-boot:run
```

### 前端

```bash
cd frontend

# 安装依赖
npm install

# 开发模式
npm run dev

# 构建
npm run build
```

---

## 提交代码

### 分支命名

- `feature/xxx` - 新功能
- `bugfix/xxx` - Bug 修复
- `hotfix/xxx` - 紧急修复
- `docs/xxx` - 文档更新
- `refactor/xxx` - 代码重构

### 提交信息规范

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Type 类型**:
- `feat`: 新功能
- `fix`: Bug 修复
- `docs`: 文档
- `style`: 格式
- `refactor`: 重构
- `test`: 测试
- `chore`: 构建/工具

**示例**:
```
feat(asset): 添加批量导入文字资产功能

- 支持 Excel 批量导入
- 自动提取关键词
- 添加导入进度提示

Closes #123
```

---

## 代码规范

### Java

- 遵循 [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- 使用 4 个空格缩进
- 类名使用 PascalCase
- 方法名和变量名使用 camelCase
- 常量使用 UPPER_SNAKE_CASE

### Vue/TypeScript

- 遵循 [Vue Style Guide](https://vuejs.org/style-guide/)
- 使用 2 个空格缩进
- 组件名使用 PascalCase
- 使用 TypeScript 类型注解

### 通用

- 保持代码简洁
- 添加必要的注释
- 编写单元测试
- 避免硬编码

---

## 审查流程

1. 所有 PR 需要至少一名维护者审查
2. CI 检查必须通过
3. 解决所有审查意见
4. 合并到主分支

---

## 许可证

提交代码即表示您同意根据 [MIT 许可证](LICENSE) 授权您的贡献。

---

感谢您的贡献！🎉
