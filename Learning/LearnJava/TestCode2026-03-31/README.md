# 测试代码目录 - 2026年3月31日

本目录包含15个Java面试题的完整可运行代码。

## ✅ 问题已修复

**2026-04-01 修复：**
- ✅ 修复了类名冲突问题（Student → Student01/Student02/Student03/Student07）
- ✅ 修复了类名冲突问题（User → User15）
- ✅ 添加了UTF-8编码参数说明
- ✅ 所有15个文件均成功编译并可运行

## 📁 文件列表

| 文件名 | 说明 | 状态 |
|--------|------|------|
| [Question01_Class.java](src/Question01_Class.java) | 类的基本概念演示 | ✅ 可运行 |
| [Question02_Instantiation.java](src/Question02_Instantiation.java) | 实例化过程演示 | ✅ 可运行 |
| [Question03_Static.java](src/Question03_Static.java) | static关键字演示 | ✅ 可运行 |
| [Question04_InterfaceAbstract.java](src/Question04_InterfaceAbstract.java) | 接口和抽象类区别演示 | ✅ 可运行 |
| [Question05_JDKJRE.java](src/Question05_JDKJRE.java) | JDK和JRE区别演示 | ✅ 可运行 |
| [Question06_JDKTools.java](src/Question06_JDKTools.java) | JDK工具分类演示 | ✅ 可运行 |
| [Question07_Reflection.java](src/Question07_Reflection.java) | 反射机制演示 | ✅ 可运行 |
| [Question08_ThreadStart.java](src/Question08_ThreadStart.java) | 线程start()方法演示 | ✅ 可运行 |
| [Question09_Optional.java](src/Question09_Optional.java) | Optional类使用演示 | ✅ 可运行 |
| [Question10_IOStream.java](src/Question10_IOStream.java) | I/O流使用演示 | ✅ 可运行 |
| [Question11_PrimitiveTypes.java](src/Question11_PrimitiveTypes.java) | 基本数据类型演示 | ✅ 可运行 |
| [Question12_AutoBoxing.java](src/Question12_AutoBoxing.java) | 自动拆箱和装箱演示 | ✅ 可运行 |
| [Question13_Inheritance.java](src/Question13_Inheritance.java) | 继承机制演示 | ✅ 可运行 |
| [Question14_AccessModifiers.java](src/Question14_AccessModifiers.java) | 访问修饰符演示 | ✅ 可运行 |
| [Question15_StaticInstanceMethod.java](src/Question15_StaticInstanceMethod.java) | 静态方法和实例方法演示 | ✅ 可运行 |




---

## 🚀 如何运行

1. 确保已安装 Java 17 或更高版本
2. 进入src目录：
   ```bash
   cd src
   ```
3. **编译（重要：需要指定UTF-8编码）：**
   ```bash
   javac -encoding UTF-8 *.java
   ```
4. **运行：**
   ```bash
   java Question01_Class
   ```

## ⚠️ 注意事项

**编译时必须指定UTF-8编码**，因为Java文件包含中文注释。Windows系统默认使用GBK编码，不指定会报错。

**单个文件编译运行（推荐）：**
```bash
cd src
# 方法1：分步执行
javac -encoding UTF-8 Question01_Class.java
java Question01_Class

# 方法2：一步到位（先编译后运行）
javac -encoding UTF-8 Question01_Class.java && java Question01_Class
```

**批量编译运行：**
```bash
cd src
# 编译所有文件
javac -encoding UTF-8 *.java

# 运行所有程序
for %f in (Question*.class) do java %~nf
```

---

> 作者：秋霁
> 创建日期：2026年3月31日
