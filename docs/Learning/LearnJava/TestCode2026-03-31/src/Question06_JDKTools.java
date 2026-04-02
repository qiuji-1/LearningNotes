/**
 * 题目：你使用哪些JDK提供的工具
 *
 * 演示JDK工具的分类和使用
 *
 * 【题目跳转】
 * @see Question01_Class 第1题：Java中的类
 * @see Question02_Instantiation 第2题：实例化
 * @see Question03_Static 第3题：static关键字
 * @see Question04_InterfaceAbstract 第4题：接口和抽象类
 * @see Question05_JDKJRE 第5题：JDK和JRE
 * @see Question07_Reflection 第7题：反射机制
 * @see Question08_ThreadStart 第8题：线程start()方法
 * @see Question09_Optional 第9题：Optional类
 * @see Question10_IOStream 第10题：I/O流
 * @see Question11_PrimitiveTypes 第11题：基本数据类型
 * @see Question12_AutoBoxing 第12题：自动拆箱装箱
 * @see Question13_Inheritance 第13题：继承机制
 * @see Question14_AccessModifiers 第14题：访问修饰符
 * @see Question15_StaticInstanceMethod 第15题：静态方法和实例方法
 */
public class Question06_JDKTools {
    public static void main(String[] args) {
        System.out.println("=== JDK工具分类 ===\n");
        
        System.out.println("1. 开发编译类");
        System.out.println("   - javac    编译器：.java → .class");
        System.out.println("   - java     运行器：执行字节码");
        System.out.println("   - jar      打包工具：打包成JAR");
        System.out.println("   - javadoc  文档生成器");
        System.out.println("   - jdb      命令行调试器");
        System.out.println();
        
        System.out.println("2. 监控诊断类");
        System.out.println("   - jps      列出Java进程");
        System.out.println("   - jinfo    查看JVM参数");
        System.out.println("   - jstat    查看GC情况");
        System.out.println("   - jstack   导出线程栈");
        System.out.println("   - jmap     导出堆快照");
        System.out.println();
        
        System.out.println("3. 性能分析类");
        System.out.println("   - jconsole      图形化监控工具");
        System.out.println("   - jvisualvm     功能更强的分析工具");
        System.out.println();
        
        System.out.println("=== 常用命令示例 ===\n");
        
        System.out.println("开发编译：");
        System.out.println("  javac HelloWorld.java         # 编译");
        System.out.println("  java HelloWorld               # 运行");
        System.out.println("  jar cvf app.jar *.class       # 打包");
        System.out.println();
        
        System.out.println("监控诊断：");
        System.out.println("  jps                           # 列出Java进程");
        System.out.println("  jstat -gc <pid> 1000          # 每秒打印GC情况");
        System.out.println("  jstack <pid>                  # 导出线程栈");
        System.out.println("  jmap -histo <pid>             # 查看堆内存对象统计");
        System.out.println("  jmap -dump <pid>              # 导出堆快照");
        System.out.println();
        
        System.out.println("性能分析：");
        System.out.println("  jconsole                      # 启动图形化监控");
        System.out.println("  jvisualvm                     # 启动分析工具");
        System.out.println();
        
        System.out.println("=== 实际问题排查流程 ===\n");
        
        System.out.println("场景1：CPU飙高排查");
        System.out.println("  1. jps 找到进程pid");
        System.out.println("  2. top -H -p <pid> 找到占用CPU的线程");
        System.out.println("  3. jstack <pid> 导出线程栈分析");
        System.out.println();
        
        System.out.println("场景2：内存溢出排查");
        System.out.println("  1. jstat -gcutil <pid> 查看GC情况");
        System.out.println("  2. jmap -histo <pid> 查看对象统计");
        System.out.println("  3. jmap -dump 导出堆快照用MAT分析");
        System.out.println();
        
        System.out.println("场景3：死锁排查");
        System.out.println("  1. jstack <pid> 导出线程栈");
        System.out.println("  2. 查找 'Found one Java-level deadlock'");
        System.out.println("  3. 分析死锁原因修改代码");
        System.out.println();
        
        // 演示：创建一些对象用于演示
        System.out.println("=== 当前程序运行信息 ===\n");
        Runtime runtime = Runtime.getRuntime();
        System.out.println("可用CPU核心: " + runtime.availableProcessors());
        System.out.println("最大内存: " + runtime.maxMemory() / 1024 / 1024 + " MB");
        System.out.println("已分配内存: " + runtime.totalMemory() / 1024 / 1024 + " MB");
        System.out.println("空闲内存: " + runtime.freeMemory() / 1024 / 1024 + " MB");
        
        // 可以用jps找到这个进程，然后用jstat、jstack等工具分析
        System.out.println("\n提示：可以在另一个终端用 jps 查看此进程");
    }
}
