# Java刷题测试代码

## 📁 文件说明

本目录包含8道Java面试题的完整测试代码，每个题目独立一个文件，代码注释详细，适合学习和实践。

## 🚀 快速开始

### 方法1：使用javac和java命令（推荐新手）

```bash
# 步骤1：编译Java文件
javac Question01_Serialization.java

# 步骤2：运行程序
java Question01_Serialization
```

### 方法2：使用IDE运行

直接在IDE（如IntelliJ IDEA、Eclipse、VS Code）中打开文件，右键点击main方法，选择"Run"。

### 方法3：批量编译运行

```bash
# Windows PowerShell
Get-ChildItem *.java | ForEach-Object { javac $_.Name }
Get-ChildItem *.class | ForEach-Object { java $_.BaseName }

# Linux/Mac
for file in *.java; do javac "$file"; done
for file in *.class; do java "${file%.class}"; done
```

## 📋 文件列表

| 文件名 | 题目 | Java版本要求 | 状态 | 详细说明 |
|--------|------|-------------|------|---------|
| Question01_Serialization.java | 序列化和反序列化 | Java 8+ | ✅ 可运行 | 演示ObjectOutputStream、ObjectInputStream、transient关键字 |
| Question02_JavaAdvantages.java | Java的优势 | Java 8+ | ✅ 可运行 | 演示跨平台性、垃圾回收、生态系统、工程化支持 |
| Question03_MultipleInheritance.java | 为什么不支持多重继承 | Java 8+ | ✅ 可运行 | 演示菱形继承问题、接口多重实现、默认方法冲突 |
| Question04_MethodOverloadOverride.java | 方法重载和重写 | Java 8+ | ✅ 可运行 | 演示重载规则、重写规则、@Override注解 |
| Question05_Java25Features.java | Java 25新特性 | Java 25+ | ⚠️ 需升级 | Scoped Values、模块导入、紧凑源文件、值类型 |
| Question06_Java11Features.java | Java 11新特性 | Java 11+ | ✅ 可运行 | String增强、Files简化、Optional.isEmpty()、HTTP客户端 |
| Question07_Java17Features.java | Java 17新特性 | Java 17+ | ✅ 可运行 | Sealed密封类、RandomGenerator、强封装内部API |
| Question08_Java21Features.java | Java 21新特性 | Java 21+ | ⚠️ 需升级 | Virtual Threads、Switch模式匹配、Record模式、分代ZGC |

## 📌 注意事项

### 当前环境：Java 17

**可以运行的题目：**
- ✅ 第1题：序列化
- ✅ 第2题：Java优势
- ✅ 第3题：多重继承
- ✅ 第4题：方法重载重写
- ✅ 第6题：Java 11新特性
- ✅ 第7题：Java 17新特性

**需要升级Java版本：**
- ⚠️ 第5题：需要Java 25（尚未发布）
- ⚠️ 第8题：需要Java 21

### 如何升级Java版本

**方式1：手动下载安装**
- 访问 [Adoptium](https://adoptium.net/) 下载Java 21或更高版本
- 安装后配置环境变量JAVA_HOME

**方式2：使用包管理器**

```bash
# Windows (使用Chocolatey)
choco install temurin21

# Mac (使用Homebrew)
brew install openjdk@21

# Linux (使用apt)
sudo apt install openjdk-21-jdk
```

**方式3：多版本管理（推荐）**

```bash
# Windows: 使用jabba
jabba install 21.0.1-tem
jabba use 21.0.1-tem

# Mac: 使用jenv
jenv add /path/to/java21
jenv global 21.0

# Linux: 使用SDKMAN
sdk install java 21.0.1-tem
sdk use java 21.0.1-tem
```

## 📖 代码结构说明

### 每个Java文件包含：

1. **文件头部注释**
   - 题目说明
   - 核心概念
   - 题目跳转链接

2. **导入说明**
   - 每个import语句上方有详细注释
   - 说明导入的类的作用

3. **类和方法注释**
   - 类的作用说明
   - 方法的参数、返回值、作用
   - 使用场景说明

4. **代码行注释**
   - 关键代码行的解释
   - 语法说明
   - 概念解释

5. **测试代码**
   - main方法入口
   - 多个测试方法
   - 输出示例和对比

## 🔗 题目跳转

每个Java文件顶部都有跳转链接，使用JavaDoc标准的`@see`标签：

```java
/**
 * 【题目跳转】
 * @see Question01_Serialization 第1题：序列化
 * @see Question02_JavaAdvantages 第2题：Java优势
 * ...
 */
```

**在IntelliJ IDEA中使用：**
1. 按住 `Ctrl` 键，点击类名（如 `Question02_JavaAdvantages`）
2. 即可跳转到对应的文件

**在其他IDE中使用：**
- Eclipse: 按住 `Ctrl` 点击
- VS Code: 按住 `Ctrl` 点击（需要Java扩展插件）

## 📚 学习建议

### 初学者（Java 8-11）

1. **先运行基础题目**
   - 第1题：序列化
   - 第2题：Java优势
   - 第3题：多重继承
   - 第4题：方法重载重写

2. **理解核心概念**
   - 阅读代码注释
   - 运行代码观察输出
   - 修改代码进行实验

3. **深入学习**
   - 第6题：Java 11新特性
   - 第7题：Java 17新特性

### 进阶学习（Java 17+）

1. **升级Java版本**
   - 安装Java 21
   - 运行第8题：Java 21新特性

2. **实践新特性**
   - Virtual Threads
   - Switch模式匹配
   - Record模式
   - 分代ZGC

### 实验建议

**修改代码进行实验：**

```java
// 示例1：修改序列化的对象属性
User user = new User("张三", 25, "zhangsan@example.com");
// 尝试修改年龄、邮箱等属性

// 示例2：尝试不同的String方法
String text = "  你好世界  ";
System.out.println(text.strip());      // 去除空白
System.out.println(text.repeat(3));     // 重复3次

// 示例3：尝试不同的随机数算法
RandomGenerator random = RandomGeneratorFactory.of("Xoroshiro128PlusPlus").create();
System.out.println(random.nextInt(100));
```

## 🐛 常见问题

### 1. 编译错误：找不到符号

**原因**：Java版本不支持某些特性

**解决**：
- 检查Java版本：`java -version`
- 升级到对应版本
- 或跳过该题目

### 2. 运行错误：ClassNotFoundException

**原因**：序列化的类文件不存在

**解决**：
- 确保编译成功：`javac Question01_Serialization.java`
- 检查.class文件是否存在

### 3. 编译错误：sealed、record等关键字

**原因**：Java版本过低

**解决**：
- Sealed类需要Java 17+
- Record需要Java 14+
- 升级Java版本

### 4. 输出乱码

**原因**：控制台编码问题

**解决**：
```bash
# Windows
chcp 65001

# Linux/Mac
export LANG=en_US.UTF-8
```

## 📖 相关文档

- [完整题目和解析](../Java2026-03-30.md)
- [Markdown学习笔记](../../LearnMarkdown/学习/Markdown学习.md)

## 💡 提示

### 如何阅读代码

1. **从main方法开始**
   - 每个文件的main方法是入口
   - 按顺序调用各个测试方法

2. **先读注释，再看代码**
   - 注释解释了核心概念
   - 理解注释后再看代码实现

3. **运行代码观察输出**
   - 运行代码比看代码更直观
   - 输出结果帮助理解概念

### 如何调试代码

```java
// 方法1：使用System.out.println调试
System.out.println("变量值：" + variable);

// 方法2：使用IDE断点调试
// 在IDE中设置断点，逐步执行

// 方法3：使用日志框架（生产环境推荐）
import java.util.logging.Logger;
Logger logger = Logger.getLogger("Test");
logger.info("调试信息");
```

## 📝 更新日志

- 2026-03-30：创建初始版本
- 2026-03-30：重命名目录为TestCode2026-03-30
- 2026-03-30：增强代码注释，添加详细说明

---

**作者：** 秋霁  
**创建日期：** 2026年3月30日  
**最后更新：** 2026年3月30日
