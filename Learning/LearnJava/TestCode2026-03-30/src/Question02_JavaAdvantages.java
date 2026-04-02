/**
 * 第2题：Java的优势是什么
 * 
 * 【核心概念】
 * Java的四大核心优势：
 * 1. 跨平台性（Write Once, Run Anywhere）
 * 2. 自动垃圾回收（Garbage Collection）
 * 3. 丰富的生态系统（框架、库、工具）
 * 4. 工程化支持（Maven、Gradle）
 * 
 * 【为什么Java重要？】
 * - 企业级开发首选语言
 * - 生态完善，社区活跃
 * - 性能稳定，安全性高
 * - 学习资源丰富
 * 
 * 【题目跳转】
 * @see Question01_Serialization 第1题：序列化
 * @see Question03_MultipleInheritance 第3题：多重继承
 * @see Question04_MethodOverloadOverride 第4题：方法重载重写
 * @see Question05_Java25Features 第5题：Java 25新特性
 * @see Question06_Java11Features 第6题：Java 11新特性
 * @see Question07_Java17Features 第7题：Java 17新特性
 * @see Question08_Java21Features 第8题：Java 21新特性
 * 
 * 运行要求：Java 8+
 */

// 没有导入：此示例不需要额外的类库

/**
 * 主类：Java优势演示
 */
public class Question02_JavaAdvantages {
    
    /**
     * main方法：程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        System.out.println("========== 第2题：Java的优势 ==========\n");
        
        // 优势1：跨平台性
        demonstrateCrossPlatform();
        
        // 优势2：垃圾回收
        demonstrateGarbageCollection();
        
        // 优势3：丰富的生态
        demonstrateEcosystem();
        
        // 优势4：工程化支持
        demonstrateEngineering();
    }
    
    /**
     * 优势1：跨平台性（Write Once, Run Anywhere）
     * 
     * 【原理】
     * - Java源代码（.java）编译成字节码（.class）
     * - 字节码运行在JVM（Java虚拟机）上
     * - JVM为不同平台提供统一接口
     * - 同一份字节码可以在Windows、Linux、Mac等平台运行
     * 
     * 【关键API】
     * - System.getProperty("os.name")：获取操作系统名称
     * - System.getProperty("java.version")：获取Java版本
     */
    private static void demonstrateCrossPlatform() {
        System.out.println("【优势1：跨平台性】");
        System.out.println();
        
        // System类：Java核心类，提供系统相关功能
        // getProperty()：获取系统属性
        System.out.println("原理：Java编译后的字节码可以在任何安装了JVM的平台上运行");
        System.out.println();
        
        // 演示：获取当前平台信息
        // System.getProperty("os.name")：返回操作系统名称
        System.out.println("当前运行平台：" + System.getProperty("os.name"));
        
        // System.getProperty("java.version")：返回Java版本
        System.out.println("Java版本：" + System.getProperty("java.version"));
        
        // System.getProperty("java.vendor")：返回Java供应商
        System.out.println("Java供应商：" + System.getProperty("java.vendor"));
        
        System.out.println();
        System.out.println("其他系统属性：");
        System.out.println("  - os.name: " + System.getProperty("os.name"));
        System.out.println("  - os.version: " + System.getProperty("os.version"));
        System.out.println("  - user.name: " + System.getProperty("user.name"));
        System.out.println("  - user.home: " + System.getProperty("user.home"));
        System.out.println();
    }
    
    /**
     * 优势2：自动垃圾回收（Garbage Collection）
     * 
     * 【什么是垃圾回收？】
     * - 自动管理内存，不需要手动释放对象
     * - GC会自动回收不再使用的对象
     * - 避免内存泄漏和悬空指针
     * 
     * 【垃圾回收机制】
     * - 年轻代（Young Generation）：新创建的对象
     * - 老年代（Old Generation）：长期存活的对象
     * - 元空间（Metaspace）：类信息、方法信息
     * 
     * 【常见垃圾回收器】
     * - Serial GC：单线程垃圾回收器
     * - Parallel GC：多线程垃圾回收器
     * - G1 GC：面向服务器的垃圾回收器
     * - ZGC：低延迟垃圾回收器（Java 11+）
     */
    private static void demonstrateGarbageCollection() {
        System.out.println("【优势2：自动垃圾回收】");
        System.out.println();
        
        // 创建大量临时对象
        // 这些对象会被垃圾回收器自动回收
        System.out.println("创建1000个临时对象...");
        for (int i = 0; i < 1000; i++) {
            String temp = "临时对象" + i;  // 创建对象
            // 使用后，temp变量超出作用域，对象变成垃圾
        }
        System.out.println("✓ 临时对象创建完成，等待垃圾回收");
        System.out.println();
        
        // System.gc()：建议JVM进行垃圾回收
        // 注意：这只是建议，JVM不一定会立即执行
        System.gc();
        System.out.println("✓ 已建议JVM进行垃圾回收");
        System.out.println();
        
        // 垃圾回收机制说明
        System.out.println("垃圾回收机制：");
        System.out.println("  - 年轻代：新对象分配在这里，频繁GC");
        System.out.println("  - 老年代：长期存活的对象，低频率GC");
        System.out.println("  - 元空间：类信息、方法信息，很少GC");
        System.out.println();
        
        // Runtime类：获取JVM运行时信息
        Runtime runtime = Runtime.getRuntime();
        System.out.println("内存信息：");
        System.out.println("  - 总内存：" + (runtime.totalMemory() / 1024 / 1024) + " MB");
        System.out.println("  - 空闲内存：" + (runtime.freeMemory() / 1024 / 1024) + " MB");
        System.out.println("  - 最大内存：" + (runtime.maxMemory() / 1024 / 1024) + " MB");
        System.out.println();
        
        // 对比C/C++
        System.out.println("对比C/C++：");
        System.out.println("  C/C++: 需要手动管理内存（malloc/free）");
        System.out.println("  Java: 自动垃圾回收，无需手动释放");
        System.out.println();
    }
    
    /**
     * 优势3：丰富的生态系统
     * 
     * 【Java生态包括】
     * - Web框架：Spring Boot、Spring Cloud、Struts
     * - ORM框架：MyBatis、Hibernate、JPA
     * - 微服务：Spring Cloud、Dubbo、gRPC
     * - 大数据：Hadoop、Spark、Flink、Kafka
     * - 构建工具：Maven、Gradle
     * - 测试框架：JUnit、TestNG、Mockito
     * 
     * 【为什么生态重要？】
     * - 提高开发效率
     * - 降低开发难度
     * - 社区支持完善
     * - 学习资源丰富
     */
    private static void demonstrateEcosystem() {
        System.out.println("【优势3：丰富的生态系统】");
        System.out.println();
        
        System.out.println("常用框架和库：");
        System.out.println();
        
        // Web框架
        System.out.println("1. Web框架：");
        System.out.println("   - Spring Boot：简化Spring应用开发");
        System.out.println("   - Spring Cloud：微服务框架");
        System.out.println("   - Struts：老牌MVC框架");
        System.out.println();
        
        // ORM框架
        System.out.println("2. ORM框架（对象关系映射）：");
        System.out.println("   - MyBatis：半自动ORM框架");
        System.out.println("   - Hibernate：全自动ORM框架");
        System.out.println("   - JPA：Java持久化API规范");
        System.out.println();
        
        // 微服务
        System.out.println("3. 微服务：");
        System.out.println("   - Spring Cloud：Spring生态微服务");
        System.out.println("   - Dubbo：阿里巴巴开源RPC框架");
        System.out.println("   - gRPC：Google高性能RPC框架");
        System.out.println();
        
        // 大数据
        System.out.println("4. 大数据：");
        System.out.println("   - Hadoop：分布式存储和计算");
        System.out.println("   - Spark：快速大数据处理");
        System.out.println("   - Flink：流式数据处理");
        System.out.println("   - Kafka：消息队列");
        System.out.println();
        
        // 构建工具
        System.out.println("5. 构建工具：");
        System.out.println("   - Maven：项目管理和构建工具");
        System.out.println("   - Gradle：灵活的构建工具");
        System.out.println();
    }
    
    /**
     * 优势4：工程化支持
     * 
     * 【什么是工程化？】
     * - 项目构建：编译、测试、打包
     * - 依赖管理：自动下载和管理第三方库
     * - 版本控制：Git、SVN
     * - 持续集成：Jenkins、GitLab CI
     * 
     * 【Maven核心概念】
     * - pom.xml：项目配置文件
     * - dependency：依赖声明
     * - repository：仓库（本地仓库、中央仓库、私服）
     * 
     * 【Gradle核心概念】
     * - build.gradle：项目配置文件
     * - DSL：领域特定语言（Groovy或Kotlin）
     * - 更灵活、性能更好
     */
    private static void demonstrateEngineering() {
        System.out.println("【优势4：工程化支持】");
        System.out.println();
        
        // Maven示例
        System.out.println("Maven依赖管理示例：");
        System.out.println();
        System.out.println("pom.xml配置：");
        System.out.println("  <dependencies>");
        System.out.println("    <dependency>");
        System.out.println("      <groupId>org.springframework.boot</groupId>");
        System.out.println("      <artifactId>spring-boot-starter-web</artifactId>");
        System.out.println("      <version>3.2.0</version>");
        System.out.println("    </dependency>");
        System.out.println("  </dependencies>");
        System.out.println();
        
        // Gradle示例
        System.out.println("Gradle依赖管理示例：");
        System.out.println();
        System.out.println("build.gradle配置：");
        System.out.println("  dependencies {");
        System.out.println("    implementation 'org.springframework.boot:spring-boot-starter-web:3.2.0'");
        System.out.println("  }");
        System.out.println();
        
        // 优势说明
        System.out.println("工程化优势：");
        System.out.println("  ✓ 自动下载依赖（无需手动下载jar包）");
        System.out.println("  ✓ 版本管理（避免版本冲突）");
        System.out.println("  ✓ 构建自动化（编译、测试、打包）");
        System.out.println("  ✓ 团队协作（统一开发环境）");
        System.out.println();
        
        // 常用命令
        System.out.println("常用Maven命令：");
        System.out.println("  - mvn clean: 清理");
        System.out.println("  - mvn compile: 编译");
        System.out.println("  - mvn test: 测试");
        System.out.println("  - mvn package: 打包");
        System.out.println("  - mvn install: 安装到本地仓库");
        System.out.println();
    }
}
