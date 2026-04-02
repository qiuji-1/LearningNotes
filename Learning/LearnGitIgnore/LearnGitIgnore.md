# Git 忽略文件（.gitignore）完整总结

## 一、问题背景

**什么是 .gitignore？**

`.gitignore` 是 Git 的配置文件，用于告诉 Git 哪些文件或目录不需要被版本控制。这些被忽略的文件不会被 `git add`、`git commit`、`git push` 等操作跟踪。

**为什么需要忽略文件？**

在开发过程中，会产生很多不需要纳入版本控制的文件：

| 文件类型 | 示例 | 为什么需要忽略 |
|----------|------|----------------|
| 编译产物 | `.class`、`.exe`、`target/` | 可以重新生成，占用空间 |
| IDE 配置 | `.idea/`、`.vscode/`、`*.iml` | 每个开发者的配置不同 |
| 系统文件 | `.DS_Store`、`Thumbs.db` | 操作系统生成，无关项目 |
| 依赖目录 | `node_modules/`、`vendor/` | 体积大，可通过包管理器恢复 |
| 敏感信息 | `config.local`、`.env`、`password.txt` | 不应该被提交到仓库 |
| 日志文件 | `*.log`、`logs/` | 运行时生成，不需要版本控制 |
| 临时文件 | `*.tmp`、`*.swp`、`*~` | 编辑器或系统临时生成 |

**不使用 .gitignore 会导致的问题**：

1. **仓库体积膨胀**：编译产物、依赖包占用大量空间
2. **提交冲突频繁**：每个人的 IDE 配置不同，容易产生冲突
3. **安全隐患**：敏感信息可能被意外提交
4. **代码审查困难**：大量无关文件干扰代码审查
5. **克隆速度慢**：不必要的文件增加克隆时间

---

## 二、基础语法

### 1. 注释

以 `#` 开头的行是注释，会被 Git 忽略。

```gitignore
# 这是一个注释
# 下面的规则忽略所有 .log 文件
*.log
```

---

### 2. 文件匹配规则

#### 基本通配符

| 符号 | 含义 | 示例 | 说明 |
|------|------|------|------|
| `*` | 匹配任意字符（不包括 `/`） | `*.log` | 忽略所有 .log 文件 |
| `**` | 匹配任意层级的目录 | `**/target/` | 忽略任意目录下的 target 文件夹 |
| `?` | 匹配单个字符 | `file?.txt` | 匹配 file1.txt、file2.txt 等 |
| `[]` | 匹配方括号中的任意一个字符 | `file[123].txt` | 匹配 file1.txt、file2.txt、file3.txt |

#### 示例

```gitignore
# 忽略所有 .class 文件
*.class

# 忽略任意目录下的 target 文件夹
**/target/

# 忽略 file1.txt、file2.txt、file3.txt
file[123].txt
```

---

### 3. 目录匹配

#### 匹配目录

```gitignore
# 忽略根目录下的 target 文件夹
/target/

# 忽略所有目录下的 target 文件夹
target/

# 忽略任意层级目录下的 node_modules
**/node_modules/
```

#### 区分文件和目录

```gitignore
# 忽略所有名为 target 的文件和目录
target

# 只忽略根目录下的 target 目录
/target/

# 只忽略根目录下的 target 文件（不是目录）
target
```

---

### 4. 否定模式

使用 `!` 可以取消忽略（在前面规则的基础上排除某些文件）。

```gitignore
# 忽略所有 .log 文件
*.log

# 但保留 important.log 文件
!important.log
```

**注意**：否定模式必须放在忽略规则之后才能生效。

**实际应用**：

```gitignore
# 忽略所有文件
/*

# 但不忽略根目录下的 docs 和 README.md
!/docs/
!/README.md
```

---

### 5. 路径规则

| 规则 | 含义 |
|------|------|
| `/` 开头 | 只匹配根目录 |
| `/` 结尾 | 只匹配目录 |
| 不包含 `/` | 匹配任意位置 |

**示例**：

```gitignore
# 只匹配根目录下的 config 文件
/config

# 只匹配根目录下的 config 目录
/config/

# 匹配任意位置的 config
config
```

---

## 三、常用规则

### 1. Java 项目

```gitignore
# 编译产物
*.class
*.jar
*.war
*.ear
target/
out/

# IDE 配置
.idea/
*.iml
*.ipr
*.iws
.vscode/
*.swp
*.swo
*~

# 日志文件
*.log
logs/

# 操作系统
.DS_Store
Thumbs.db
```

---

### 2. Node.js 项目

```gitignore
# 依赖目录
node_modules/

# 构建产物
dist/
build/
*.tsbuildinfo

# 日志
npm-debug.log*
yarn-debug.log*
yarn-error.log*

# 环境变量
.env
.env.local
.env.*.local

# IDE
.idea/
.vscode/
*.swp
```

---

### 3. Python 项目

```gitignore
# 字节码
__pycache__/
*.py[cod]
*$py.class
*.so

# 虚拟环境
venv/
ENV/
env/

# IDE
.idea/
.vscode/
*.swp

# 测试
.pytest_cache/
.coverage
htmlcov/

# 分发
dist/
build/
*.egg-info/
```

---

### 4. Spring Boot 项目

```gitignore
# Maven
target/
pom.xml.tag
pom.xml.releaseBackup
pom.xml.versionsBackup
pom.xml.next
release.properties
dependency-reduced-pom.xml
buildNumber.properties
.mvn/timing.properties
.mvn/wrapper/maven-wrapper.jar

# Gradle
.gradle/
build/
!gradle/wrapper/gradle-wrapper.jar

# IDE
.idea/
*.iml
*.ipr
*.iws
.project
.classpath
.settings/
.vscode/
*.swp
*.swo

# 日志
*.log
logs/

# 操作系统
.DS_Store
Thumbs.db

# 敏感配置
application-local.yml
application-local.properties
```

---

### 5. Vue 项目

```gitignore
# 依赖
node_modules/

# 构建产物
dist/
dist-ssr/
*.local

# 日志
npm-debug.log*
yarn-debug.log*
yarn-error.log*
pnpm-debug.log*
lerna-debug.log*

# 编辑器
.vscode/*
!.vscode/extensions.json
.idea/
*.suo
*.ntvs*
*.njsproj
*.sln
*.sw?

# 操作系统
.DS_Store
Thumbs.db
```

---

## 四、实际案例解析

### 本项目的 .gitignore 配置

让我们分析本项目中的 `.gitignore` 文件：

```gitignore
# Java 编译产物
*.class
target/

# Maven
.mvn/

# IDE 配置
.idea/
*.iml
*.ipr
*.iws
.vscode/

# 操作系统
.DS_Store
Thumbs.db

# 日志文件
*.log

# 敏感信息
.env
application-local.properties
```

**逐行解析**：

| 规则 | 作用 |
|------|------|
| `*.class` | 忽略所有 Java 编译产物 |
| `target/` | 忽略 Maven 构建目录 |
| `.mvn/` | 忽略 Maven wrapper 配置 |
| `.idea/` | 忽略 IntelliJ IDEA 配置 |
| `*.iml` | 忽略 IDEA 模块文件 |
| `.vscode/` | 忽略 VSCode 配置 |
| `*.log` | 忽略日志文件 |

---

## 五、常见问题与解决方案

### 问题1：规则不生效

**现象**：添加了 `.gitignore` 规则，但文件仍然被跟踪。

**原因**：文件已经被 Git 跟踪，`.gitignore` 只对未跟踪的文件生效。

**解决方案**：

```bash
# 从 Git 缓存中移除文件（保留本地文件）
git rm --cached <file>

# 从 Git 缓存中移除所有被忽略的文件
git rm -r --cached .
git add .
git commit -m "chore: 更新 .gitignore"
```

**示例**：

```bash
# 假设 target 目录已被跟踪
git rm -r --cached target/
git commit -m "chore: 从 Git 中移除 target 目录"
```

---

### 问题2：已经提交了敏感信息

**现象**：不小心提交了密码、密钥等敏感信息到仓库。

**解决方案**：

```bash
# 1. 从 Git 历史中彻底删除文件
git filter-branch --force --index-filter \
  'git rm --cached --ignore-unmatch path/to/sensitive-file' \
  --prune-empty --tag-name-filter cat -- --all

# 2. 强制推送到远程仓库
git push origin --force --all

# 3. 添加到 .gitignore
echo "path/to/sensitive-file" >> .gitignore
git add .gitignore
git commit -m "chore: 添加敏感文件到 .gitignore"
```

**注意**：强制推送会影响其他开发者，需要提前沟通。

---

### 问题3：.gitignore 文件本身被忽略

**现象**：修改了 `.gitignore` 但没有生效。

**原因**：`.gitignore` 文件本身被忽略了。

**解决方案**：

```bash
# 确保 .gitignore 被跟踪
git add .gitignore
git commit -m "chore: 添加 .gitignore"
```

---

### 问题4：全局忽略和项目忽略冲突

**现象**：全局 `.gitignore` 与项目 `.gitignore` 规则冲突。

**解决方案**：

```bash
# 查看全局 .gitignore 配置
git config --global core.excludesfile

# 编辑全局 .gitignore
git config --global core.excludesfile ~/.gitignore_global

# 在项目 .gitignore 中使用否定模式
!.idea/  # 即使全局忽略，项目中仍然跟踪
```

---

## 六、最佳实践

### 1. 分层管理

推荐使用三层 `.gitignore` 结构：

```
├── 全局 .gitignore (~/.gitignore_global)
│   └── 适用于所有项目的通用规则
│
├── 项目根目录 .gitignore
│   └── 项目特定的忽略规则
│
└── 子目录 .gitignore
    └── 子模块特定的忽略规则
```

**全局 .gitignore 示例**：

```bash
# 设置全局 .gitignore
git config --global core.excludesfile ~/.gitignore_global
```

```gitignore
# ~/.gitignore_global

# 操作系统
.DS_Store
Thumbs.db
*~

# IDE
.idea/
.vscode/
*.swp
*.swo

# 编译产物
*.class
*.exe
*.dll
```

---

### 2. 使用模板

GitHub 提供了大量 `.gitignore` 模板：

- 仓库：https://github.com/github/gitignore
- 在创建仓库时可以选择模板

**常用模板**：

| 语言/框架 | 模板文件 |
|-----------|----------|
| Java | Java.gitignore |
| Node.js | Node.gitignore |
| Python | Python.gitignore |
| Vue | Node.gitignore + 自定义 |
| Spring Boot | Java.gitignore + Maven.gitignore |

---

### 3. 定期维护

```bash
# 查看 .gitignore 是否有遗漏
git status --ignored

# 清理无效的规则
# 1. 备份当前 .gitignore
cp .gitignore .gitignore.bak

# 2. 测试每个规则是否生效
git check-ignore -v <file>

# 3. 删除无效规则
```

---

### 4. 团队协作

**团队统一规则**：

```gitignore
# 团队统一的 .gitignore

# 编译产物（必须忽略）
*.class
target/

# IDE 配置（建议忽略）
.idea/
*.iml
.vscode/

# 可选：保留部分 IDE 配置
!.vscode/settings.json
!.vscode/extensions.json
```

**提交前检查**：

```bash
# 检查是否有不该提交的文件
git status

# 检查 .gitignore 规则是否生效
git check-ignore -v <file>
```

---

## 七、注意事项

### 1. 文件位置

`.gitignore` 文件可以放在：

- **根目录**：适用于整个项目
- **子目录**：只适用于该子目录

**示例**：

```
project/
├── .gitignore          # 作用于整个项目
├── api/
│   └── .gitignore      # 只作用于 api 目录
└── view/
    └── .gitignore      # 只作用于 view 目录
```

---

### 2. 编码格式

- 推荐使用 **UTF-8** 编码
- 行尾使用 **LF**（Unix 格式）或 **CRLF**（Windows 格式）
- 避免使用 BOM

---

### 3. 跨平台问题

不同操作系统的文件名大小写敏感性不同：

```gitignore
# 在 Windows 上不区分大小写
# 在 Linux/Mac 上区分大小写

# 推荐统一使用小写
*.class
*.log

# 或使用 [Cc]lass 匹配大小写
*[Cc]lass
```

---

### 4. 空格处理

```gitignore
# 错误：文件名中的空格需要转义
my file.txt

# 正确：使用反斜杠转义
my\ file.txt

# 或使用引号（不推荐，可能不兼容）
"my file.txt"
```

---

### 5. 规则顺序

`.gitignore` 规则是从上到下匹配的：

```gitignore
# 错误：否定模式在忽略规则之前
!important.log
*.log

# 正确：否定模式在忽略规则之后
*.log
!important.log
```

---

## 八、常用命令

### 查看 .gitignore 状态

```bash
# 查看所有被忽略的文件
git status --ignored

# 查看某个文件为什么被忽略
git check-ignore -v <file>

# 查看所有被忽略的文件列表
git ls-files --others --ignored --exclude-standard
```

---

### 测试 .gitignore 规则

```bash
# 测试某个文件是否被忽略
git check-ignore <file>

# 显示详细的忽略原因
git check-ignore -v <file>

# 测试规则但不实际应用
echo "test" | git check-ignore --stdin
```

---

### 清理被忽略的文件

```bash
# 删除所有被忽略的文件（危险操作！）
git clean -Xfd

# 只显示将被删除的文件（不实际删除）
git clean -Xfd --dry-run
```

---

## 九、总结

### 关键要点

| 知识点 | 要点 |
|--------|------|
| 基础语法 | `*` 匹配任意字符，`**` 匹配任意目录，`!` 否定模式 |
| 目录匹配 | `/` 结尾表示目录，`/` 开头表示根目录 |
| 规则顺序 | 否定模式必须放在忽略规则之后 |
| 已跟踪文件 | 需要 `git rm --cached` 才能忽略 |
| 分层管理 | 全局 `.gitignore` + 项目 `.gitignore` + 子目录 `.gitignore` |

### 常见错误

1. ❌ 规则不生效（文件已被跟踪）
2. ❌ 敏感信息提交到仓库
3. ❌ IDE 配置冲突
4. ❌ 规则顺序错误

### 最佳实践

1. ✅ 使用 GitHub 模板作为起点
2. ✅ 分层管理忽略规则
3. ✅ 定期检查和清理
4. ✅ 团队统一规则
5. ✅ 提交前使用 `git status --ignored` 检查

---

**文档创建日期**：2026-03-31  
**作者**：秋霁
