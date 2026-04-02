/**
 * 第8题：Java 21有哪些特性
 * 
 * ⚠️ 版本要求：Java 21+
 * 当前环境：Java 17
 * 说明：此代码在Java 17环境下无法编译，需要升级到Java 21
 * 
 * 【核心概念】
 * Java 21核心特性聚焦于并发编程革新、代码简洁性提升和垃圾回收优化。
 * 
 * 【主要特性】
 * 1. Virtual Threads（虚拟线程）
 *    - 轻量级线程，几KB内存
 *    - 支持百万级创建
 *    - 阻塞时自动让出OS线程
 *    - 无需异步编程，同步代码即可
 * 
 * 2. Switch模式匹配
 *    - 根据类型匹配
 *    - 支持when条件判断
 *    - 无需instanceof嵌套
 * 
 * 3. Record模式
 *    - 嵌套解构Record
 *    - 一次性提取字段
 *    - 简化数据处理
 * 
 * 4. 分代ZGC
 *    - 分代设计
 *    - 超低延迟（<1ms）
 *    - 吞吐量提升20%~40%
 * 
 * 【题目跳转】
 * @see Question01_Serialization 第1题：序列化
 * @see Question02_JavaAdvantages 第2题：Java优势
 * @see Question03_MultipleInheritance 第3题：多重继承
 * @see Question04_MethodOverloadOverride 第4题：方法重载重写
 * @see Question05_Java25Features 第5题：Java 25新特性
 * @see Question06_Java11Features 第6题：Java 11新特性
 * @see Question07_Java17Features 第7题：Java 17新特性
 */

// 没有导入：此示例为演示说明

/**
 * 主类：Java 21新特性演示
 */
public class Question08_Java21Features {
    
    /**
     * main方法：程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        System.out.println("========== 第8题：Java 21新特性 ==========\n");
        System.out.println("⚠️ 此代码需要Java 21+环境");
        System.out.println("当前环境：Java 17");
        System.out.println("请在Java 21环境下运行此代码\n");
        
        // 由于Java 17不支持Java 21特性，这里只展示代码结构
        // 实际运行需要Java 21环境
        
        System.out.println("【Java 21主要特性】\n");
        
        // 特性1：Virtual Threads（虚拟线程）
        System.out.println("1. Virtual Threads（虚拟线程）");
        System.out.println("   - 轻量级线程，几KB内存");
        System.out.println("   - 支持百万级创建");
        System.out.println("   - 阻塞时自动让出OS线程");
        System.out.println("   - 无需异步编程，同步代码即可");
        System.out.println();
        
        // 特性2：Switch模式匹配
        System.out.println("2. Switch模式匹配");
        System.out.println("   - 根据类型匹配并绑定变量");
        System.out.println("   - 支持when条件判断");
        System.out.println("   - 无需instanceof嵌套");
        System.out.println();
        
        // 特性3：Record模式
        System.out.println("3. Record模式");
        System.out.println("   - 嵌套解构Record");
        System.out.println("   - 一次性提取字段");
        System.out.println("   - 简化数据处理");
        System.out.println();
        
        // 特性4：分代ZGC
        System.out.println("4. 分代ZGC");
        System.out.println("   - 分代设计（年轻代、老年代）");
        System.out.println("   - 超低延迟（<1ms）");
        System.out.println("   - 吞吐量提升20%~40%");
        System.out.println();
        
        // 代码示例（注释形式）
        System.out.println("【代码示例】（需要Java 21环境）\n");
        
        System.out.println("特性1：Virtual Threads示例");
        System.out.println("  // 创建虚拟线程");
        System.out.println("  Thread virtualThread = Thread.ofVirtual().start(() -> {");
        System.out.println("      System.out.println(\"虚拟线程执行中...\");");
        System.out.println("  });");
        System.out.println();
        System.out.println("  // 使用虚拟线程执行器");
        System.out.println("  try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {");
        System.out.println("      for (int i = 0; i < 1_000_000; i++) {");
        System.out.println("          executor.submit(() -> {");
        System.out.println("              Thread.sleep(1000);  // 阻塞时自动让出OS线程");
        System.out.println("              return null;");
        System.out.println("          });");
        System.out.println("      }");
        System.out.println("  }");
        System.out.println();
        
        System.out.println("特性2：Switch模式匹配示例");
        System.out.println("  Object obj = \"hello\";");
        System.out.println("  String result = switch (obj) {");
        System.out.println("      case Integer i -> \"整数: \" + i;");
        System.out.println("      case String s -> \"字符串: \" + s;");
        System.out.println("      case null -> \"null\";");
        System.out.println("      default -> \"未知类型\";");
        System.out.println("  };");
        System.out.println();
        System.out.println("  // 结合when条件判断");
        System.out.println("  switch (obj) {");
        System.out.println("      case Integer i when i > 0 -> \"正数\";");
        System.out.println("      case Integer i when i < 0 -> \"负数\";");
        System.out.println("      case Integer i -> \"零\";");
        System.out.println("      default -> \"其他\";");
        System.out.println("  }");
        System.out.println();
        
        System.out.println("特性3：Record模式示例");
        System.out.println("  record Point(int x, int y) {}");
        System.out.println("  Point point = new Point(10, 20);");
        System.out.println();
        System.out.println("  // 嵌套解构");
        System.out.println("  if (point instanceof Point(int x, int y)) {");
        System.out.println("      System.out.println(\"x=\" + x + \", y=\" + y);");
        System.out.println("  }");
        System.out.println();
        
        System.out.println("特性4：分代ZGC");
        System.out.println("  # 启用分代ZGC");
        System.out.println("  java -XX:+UseZGC -XX:+ZGenerational -jar myapp.jar");
        System.out.println();
        
        System.out.println("【性能对比】");
        System.out.println();
        System.out.println("Virtual Threads vs 传统线程：");
        System.out.println("| 对比项 | 传统线程 | 虚拟线程 |");
        System.out.println("|--------|---------|----------|");
        System.out.println("| 内存占用 | ~1MB/线程 | ~几KB/线程 |");
        System.out.println("| 最大线程数 | ~几千个 | ~百万级 |");
        System.out.println("| I/O阻塞时 | 占用OS线程 | 自动让出OS线程 |");
        System.out.println("| 代码复杂度 | 需要异步编程 | 同步代码即可 |");
        System.out.println("| 适用场景 | CPU密集型 | I/O密集型 |");
        System.out.println();
        
        System.out.println("分代ZGC vs 单代ZGC：");
        System.out.println("| 场景 | 单代ZGC | 分代ZGC |");
        System.out.println("|------|---------|---------|");
        System.out.println("| 暂停时间 | <10ms | <1ms |");
        System.out.println("| 吞吐量 | 基准 | +20%~40% |");
        System.out.println("| 内存分配密集场景 | 性能下降 | 性能优异 |");
        System.out.println();
        
        System.out.println("【总结】");
        System.out.println("Java 21主要特性：");
        System.out.println("  1. Virtual Threads - 虚拟线程（百万级创建）");
        System.out.println("  2. Switch模式匹配 - 类型匹配 + when条件");
        System.out.println("  3. Record模式 - 嵌套解构");
        System.out.println("  4. 分代ZGC - 性能大幅提升");
        System.out.println();
        
        System.out.println("详细代码示例请参考：");
        System.out.println("- Java2026-03-30.md 文件中的第8题");
        System.out.println();
    }
}
