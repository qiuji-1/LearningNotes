/**
 * 第7题：Java 17有哪些新特性
 * 
 * 【核心概念】
 * Java 17是目前最受欢迎的LTS版本，核心特性聚焦于代码设计安全性与JDK稳定性。
 * 
 * 【主要特性】
 * 1. Sealed密封类
 *    - 精确控制继承权限
 *    - 通过permits指定允许继承的子类
 *    - 配合switch实现类型穷举检查
 * 
 * 2. 增强的伪随机数生成器
 *    - RandomGenerator接口
 *    - 支持多种算法
 *    - 性能提升
 * 
 * 3. 强封装JDK内部API
 *    - 禁止反射访问JDK内部类
 *    - 提升安全性
 *    - 推动使用标准API
 * 
 * 【题目跳转】
 * @see Question01_Serialization 第1题：序列化
 * @see Question02_JavaAdvantages 第2题：Java优势
 * @see Question03_MultipleInheritance 第3题：多重继承
 * @see Question04_MethodOverloadOverride 第4题：方法重载重写
 * @see Question05_Java25Features 第5题：Java 25新特性
 * @see Question06_Java11Features 第6题：Java 11新特性
 * @see Question08_Java21Features 第8题：Java 21新特性
 * 
 * 运行要求：Java 17+（✓ 当前环境Java 17支持）
 */

// ===== 导入说明 =====
// java.util.random.RandomGenerator：随机数生成器接口（Java 17新增）
// java.util.random.RandomGeneratorFactory：随机数生成器工厂（Java 17新增）
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * 主类：Java 17新特性演示
 */
public class Question07_Java17Features {
    
    /**
     * main方法：程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        System.out.println("========== 第7题：Java 17新特性 ==========\n");
        
        // 特性1：Sealed密封类
        testSealedClasses();
        
        // 特性2：增强的伪随机数生成器
        testRandomGenerator();
        
        // 特性3：强封装JDK内部API
        testStrongEncapsulation();
    }
    
    /**
     * 特性1：Sealed密封类
     * 
     * 【什么是密封类？】
     * - 使用 sealed 关键字修饰的类
     * - 通过 permits 指定允许继承的子类
     * - 被允许的子类必须声明继承策略
     * 
     * 【继承策略】
     * 1. final：最终类，禁止继续继承
     * 2. sealed：密封类，继续限制继承
     * 3. non-sealed：非密封类，开放继承
     * 
     * 【应用场景】
     * - 有限的类层次结构（如形状类：Circle、Rectangle、Triangle）
     * - 编译期类型检查
     * - switch模式匹配配合使用
     */
    private static void testSealedClasses() {
        System.out.println("【特性1：Sealed密封类】");
        System.out.println();
        
        System.out.println("定义：通过permits关键字精确指定允许继承的子类");
        System.out.println();
        
        System.out.println("语法示例：");
        System.out.println("  public sealed class Shape permits Circle, Rectangle, Triangle {");
        System.out.println("      // 只有Circle、Rectangle、Triangle可以继承Shape");
        System.out.println("  }");
        System.out.println();
        
        // ===== 创建不同的Shape对象 =====
        Circle circle = new Circle(5.0);
        Rectangle rectangle = new Rectangle(3.0, 4.0);
        Triangle triangle = new Triangle(3.0, 4.0, 5.0);
        
        // ===== 计算面积 =====
        System.out.println("计算面积演示：");
        System.out.println("  圆（半径5）：" + calculateArea(circle));
        System.out.println("  矩形（3x4）：" + calculateArea(rectangle));
        System.out.println("  三角形（3,4,5）：" + calculateArea(triangle));
        System.out.println();
        
        // ===== 继承策略说明 =====
        System.out.println("继承策略对比：");
        System.out.println();
        System.out.println("| 修饰符 | 作用 | 能否继续被继承 |");
        System.out.println("|--------|------|---------------|");
        System.out.println("| final | 最终类，禁止继承 | ❌ 不能 |");
        System.out.println("| sealed | 密封类，继续限制继承 | ✅ 能（需指定permits） |");
        System.out.println("| non-sealed | 非密封类，开放继承 | ✅ 能（无限制） |");
        System.out.println();
        
        System.out.println("代码示例：");
        System.out.println("  public sealed class Shape permits Circle, Rectangle, Triangle { }");
        System.out.println();
        System.out.println("  final class Circle extends Shape { }");
        System.out.println("  sealed class Rectangle extends Shape permits Square { }");
        System.out.println("  non-sealed class Triangle extends Shape { }");
        System.out.println();
        
        System.out.println("优势：");
        System.out.println("  ✓ 编译期检查所有子类");
        System.out.println("  ✓ 类型安全，避免运行时意外");
        System.out.println("  ✓ 配合switch实现类型穷举检查");
        System.out.println("  ✓ 清晰的继承关系文档");
        System.out.println();
    }
    
    /**
     * 计算面积（演示Sealed类 + switch模式匹配）
     * 
     * 【方法说明】
     * - 接收Shape参数（Sealed接口）
     * - 使用instanceof判断具体类型
     * - Java 17支持switch模式匹配（预览特性，Java 21正式）
     * 
     * @param shape 形状对象
     * @return 面积
     */
    private static double calculateArea(Shape shape) {
        // 使用传统if-else演示（Java 17的switch模式匹配是预览特性）
        if (shape instanceof Circle c) {
            // instanceof判断类型并自动转换
            // c 是Circle类型，无需手动转换
            return Math.PI * c.radius() * c.radius();
        } else if (shape instanceof Rectangle r) {
            return r.width() * r.height();
        } else if (shape instanceof Triangle t) {
            // 海伦公式：s = (a+b+c)/2, area = √(s(s-a)(s-b)(s-c))
            double s = (t.a() + t.b() + t.c()) / 2;
            return Math.sqrt(s * (s - t.a()) * (s - t.b()) * (s - t.c()));
        }
        return 0;
    }
    
    /**
     * 特性2：增强的伪随机数生成器
     * 
     * 【新接口】
     * - RandomGenerator：随机数生成器接口
     * - RandomGeneratorFactory：随机数生成器工厂
     * 
     * 【优势】
     * - 统一API
     * - 支持多种算法
     * - 性能提升
     * 
     * 【常用算法】
     * - L32X64MixRandom
     * - Xoroshiro128PlusPlus
     * - Xoshiro256PlusPlus
     */
    private static void testRandomGenerator() {
        System.out.println("【特性2：增强的伪随机数生成器】");
        System.out.println();
        
        System.out.println("新接口：RandomGenerator");
        System.out.println();
        
        // ===== 旧版本：Random =====
        System.out.println("旧版本（Random）：");
        
        // java.util.Random：传统随机数生成器
        // 算法：线性同余生成器（LCG）
        // 问题：算法单一，性能有限
        java.util.Random oldRandom = new java.util.Random();
        System.out.println("  随机整数：" + oldRandom.nextInt(100));
        System.out.println("  问题：算法单一，性能有限");
        System.out.println();
        
        // ===== 新版本：RandomGenerator =====
        System.out.println("新版本（RandomGenerator）：");
        System.out.println();
        
        // 方式1：使用默认随机数生成器
        // RandomGenerator.getDefault()：返回默认随机数生成器
        RandomGenerator random = RandomGenerator.getDefault();
        System.out.println("  默认算法随机数：" + random.nextInt(100));
        System.out.println();
        
        // 方式2：使用特定算法
        // RandomGeneratorFactory.of()：根据名称创建工厂
        // create()：创建随机数生成器实例
        RandomGenerator fastRandom = RandomGeneratorFactory.of("L32X64MixRandom").create();
        System.out.println("  L32X64MixRandom：" + fastRandom.nextInt(100));
        System.out.println();
        
        // 方式3：列出所有可用算法
        System.out.println("可用算法（部分）：");
        RandomGeneratorFactory.all()
            .limit(5)  // 限制显示5个
            .forEach(factory -> System.out.println("    - " + factory.name()));
        System.out.println();
        
        // ===== 算法对比 =====
        System.out.println("常用算法对比：");
        System.out.println();
        System.out.println("| 算法名称 | 性能 | 周期 | 适用场景 |");
        System.out.println("|---------|------|------|---------|");
        System.out.println("| Random（旧） | 中等 | 2^48 | 通用场景 |");
        System.out.println("| L32X64MixRandom | 高 | 2^96 | 科学计算、模拟 |");
        System.out.println("| Xoroshiro128PlusPlus | 很高 | 2^128 | 高性能需求 |");
        System.out.println("| Xoshiro256PlusPlus | 极高 | 2^256 | 密码学、统计 |");
        System.out.println();
        
        System.out.println("优势：");
        System.out.println("  ✓ 统一API：RandomGenerator接口标准化");
        System.out.println("  ✓ 算法丰富：支持10+种现代算法");
        System.out.println("  ✓ 性能提升：部分算法比Random快数倍");
        System.out.println("  ✓ 易于扩展：通过工厂模式灵活选择算法");
        System.out.println();
    }
    
    /**
     * 特性3：强封装JDK内部API
     * 
     * 【什么是强封装？】
     * - Java 17彻底禁止反射访问JDK内部类
     * - sun.misc.Unsafe、jdk.internal.*等不可见
     * 
     * 【影响】
     * - 依赖内部API的老代码会编译失败或运行时错误
     * - 推动开发者使用标准API
     * 
     * 【迁移建议】
     * - sun.misc.Unsafe → java.lang.invoke.VarHandle
     * - jdk.internal.* → 使用标准API
     */
    private static void testStrongEncapsulation() {
        System.out.println("【特性3：强封装JDK内部API】");
        System.out.println();
        
        System.out.println("Java 17彻底禁止反射访问JDK内部类");
        System.out.println();
        
        System.out.println("影响范围：");
        System.out.println("  ❌ sun.misc.Unsafe 不再可通过反射访问");
        System.out.println("  ❌ jdk.internal.* 包不可见");
        System.out.println();
        
        System.out.println("迁移建议：");
        System.out.println();
        System.out.println("| 旧API（已禁用） | 新API（替代方案） | 说明 |");
        System.out.println("|----------------|------------------|------|");
        System.out.println("| sun.misc.Unsafe | java.lang.invoke.VarHandle | 变量句柄 |");
        System.out.println("| sun.misc.Unsafe | java.util.concurrent.atomic.* | 原子类 |");
        System.out.println("| jdk.internal.* | 标准API | 使用官方公开API |");
        System.out.println("| sun.reflect.Reflection | java.lang.StackWalker | 堆栈遍历 |");
        System.out.println();
        
        System.out.println("代码示例（VarHandle替代Unsafe）：");
        System.out.println();
        System.out.println("  // 旧代码（Unsafe，已禁用）");
        System.out.println("  Unsafe unsafe = ...;");
        System.out.println("  unsafe.compareAndSwapInt(obj, offset, expect, update);");
        System.out.println();
        System.out.println("  // 新代码（VarHandle，推荐）");
        System.out.println("  VarHandle handle = MethodHandles.lookup()");
        System.out.println("      .findVarHandle(MyClass.class, \"count\", int.class);");
        System.out.println("  handle.compareAndSet(obj, expect, update);");
        System.out.println();
        
        System.out.println("优势：");
        System.out.println("  ✓ 提升JDK安全性");
        System.out.println("  ✓ 提升JDK稳定性");
        System.out.println("  ✓ 推动使用标准API");
        System.out.println("  ✓ 避免依赖内部API导致的升级问题");
        System.out.println();
    }
}

/**
 * Shape密封接口
 * 
 * 【接口说明】
 * - sealed：密封接口，限制实现类
 * - permits Circle, Rectangle, Triangle：只允许这三个类实现
 */
sealed interface Shape permits Circle, Rectangle, Triangle {}

/**
 * 圆类（final子类，禁止继续继承）
 * 
 * 【类说明】
 * - final：最终类，不能被继承
 * - implements Shape：实现Shape接口
 */
final class Circle implements Shape {
    private final double radius;  // 半径
    
    /**
     * 构造方法
     * @param radius 半径
     */
    public Circle(double radius) {
        this.radius = radius;
    }
    
    /**
     * 获取半径
     * @return 半径
     */
    public double radius() {
        return radius;
    }
}

/**
 * 矩形类（sealed子类，继续密封）
 * 
 * 【类说明】
 * - sealed：密封类，继续限制继承
 * - permits Square：只允许Square继承
 */
sealed class Rectangle implements Shape permits Square {
    private final double width;   // 宽
    private final double height;  // 高
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    public double width() {
        return width;
    }
    
    public double height() {
        return height;
    }
}

/**
 * 正方形类（继承Rectangle）
 * 
 * 【类说明】
 * - final：最终类，不能被继承
 * - extends Rectangle：继承Rectangle类
 */
final class Square extends Rectangle {
    /**
     * 构造方法
     * @param side 边长
     */
    public Square(double side) {
        super(side, side);  // 正方形宽高相同
    }
}

/**
 * 三角形类（non-sealed子类，开放继承）
 * 
 * 【类说明】
 * - non-sealed：非密封类，允许任意类继承
 * - 打破密封链
 */
non-sealed class Triangle implements Shape {
    private final double a, b, c;  // 三条边
    
    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public double a() {
        return a;
    }
    
    public double b() {
        return b;
    }
    
    public double c() {
        return c;
    }
}
