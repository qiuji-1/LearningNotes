/**
 * 第3题：为什么Java不支持多重继承
 * 
 * 【核心概念】
 * 多重继承：一个类可以继承多个父类
 * Java不支持类的多重继承，但支持接口的多重实现
 * 
 * 【为什么不支持？】
 * 1. 菱形继承问题（Diamond Problem）
 *    - 两个父类有同名方法，子类调用时产生歧义
 *    - 继承关系形成菱形，导致二义性
 * 
 * 2. 复杂性增加
 *    - 编译器实现复杂
 *    - 代码可读性降低
 *    - 容易引入Bug
 * 
 * 【解决方案】
 * - 使用接口（interface）实现多重继承
 * - 使用组合（Composition）代替继承
 * - Java 8+接口可以有默认方法（default method）
 * 
 * 【题目跳转】
 * @see Question01_Serialization 第1题：序列化
 * @see Question02_JavaAdvantages 第2题：Java优势
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
 * 主类：多重继承问题演示
 */
public class Question03_MultipleInheritance {
    
    /**
     * main方法：程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        System.out.println("========== 第3题：为什么Java不支持多重继承 ==========\n");
        
        // 演示1：菱形继承问题
        demonstrateDiamondProblem();
        
        // 演示2：接口多重继承
        demonstrateInterfaceMultipleInheritance();
        
        // 演示3：接口默认方法冲突
        demonstrateDefaultMethodConflict();
    }
    
    /**
     * 演示1：菱形继承问题
     * 
     * 【什么是菱形继承？】
     *   A
     *  / \
     * B   C
     *  \ /
     *   D
     * 
     * - A是父类，有方法foo()
     * - B和C都继承了A，并重写了foo()
     * - D同时继承B和C（假设允许）
     * - 问题：D.foo()调用的是B的还是C的？
     * 
     * 【为什么叫菱形？】
     * - 继承关系形成菱形图案
     * - 也叫"钻石继承问题"
     */
    private static void demonstrateDiamondProblem() {
        System.out.println("【问题1：菱形继承问题】");
        System.out.println();
        
        System.out.println("假设Java支持多重继承：");
        System.out.println();
        
        // 绘制菱形继承关系图
        System.out.println("      class A { void foo() { ... } }");
        System.out.println("           /       \\");
        System.out.println("          /         \\");
        System.out.println("  class B extends A  class C extends A");
        System.out.println("  { void foo() {...}}  { void foo() {...}}");
        System.out.println("          \\         /");
        System.out.println("           \\       /");
        System.out.println("      class D extends B, C { }");
        System.out.println();
        
        System.out.println("问题分析：");
        System.out.println("  1. B和C都重写了A的foo()方法");
        System.out.println("  2. D调用foo()时，应该调用B的还是C的？");
        System.out.println("  3. 编译器无法决定，产生二义性");
        System.out.println();
        
        System.out.println("C++的解决方案：");
        System.out.println("  - 虚继承（Virtual Inheritance）");
        System.out.println("  - 显式指定调用哪个父类方法（A::B::foo()）");
        System.out.println();
        
        System.out.println("Java的解决方案：");
        System.out.println("  - 干脆不支持类的多重继承");
        System.out.println("  - 只支持接口的多重实现");
        System.out.println();
        
        System.out.println("示例代码（假设语法）：");
        System.out.println("  class D extends B, C {  // ❌ 不允许");
        System.out.println("      void test() {");
        System.out.println("          foo();  // 调用B还是C？编译器无法决定");
        System.out.println("      }");
        System.out.println("  }");
        System.out.println();
    }
    
    /**
     * 演示2：接口多重继承（Java允许）
     * 
     * 【为什么接口可以多重继承？】
     * - 接口只有方法声明，没有实现（Java 8之前）
     * - 即使多个接口有同名方法，也不会产生冲突
     * - 实现类必须提供具体实现
     * 
     * 【Java 8+的默认方法】
     * - 接口可以有默认实现（default方法）
     * - 如果多个接口有同名默认方法，实现类必须重写
     */
    private static void demonstrateInterfaceMultipleInheritance() {
        System.out.println("【解决方案1：接口多重继承】");
        System.out.println();
        
        System.out.println("Java允许实现多个接口：");
        System.out.println();
        
        // 创建Duck对象（实现Flyable和Swimmable两个接口）
        Duck duck = new Duck();
        
        // 调用fly方法（来自Flyable接口）
        duck.fly();
        
        // 调用swim方法（来自Swimmable接口）
        duck.swim();
        
        System.out.println();
        System.out.println("代码说明：");
        System.out.println("  interface Flyable { void fly(); }");
        System.out.println("  interface Swimmable { void swim(); }");
        System.out.println();
        System.out.println("  class Duck implements Flyable, Swimmable {");
        System.out.println("      void fly() { ... }   // 实现Flyable接口");
        System.out.println("      void swim() { ... }  // 实现Swimmable接口");
        System.out.println("  }");
        System.out.println();
        
        System.out.println("✓ Java允许实现多个接口，避免菱形继承问题");
        System.out.println("✓ 接口只是定义规范，不包含具体实现");
        System.out.println();
    }
    
    /**
     * 演示3：接口默认方法冲突
     * 
     * 【Java 8默认方法】
     * - 使用 default 关键字定义
     * - 接口可以提供默认实现
     * - 实现类可以选择重写或使用默认实现
     * 
     * 【冲突场景】
     * - 两个接口有同名默认方法
     * - 实现类必须重写该方法
     * - 可以使用 InterfaceName.super.method() 调用特定接口的默认方法
     */
    private static void demonstrateDefaultMethodConflict() {
        System.out.println("【问题2：接口默认方法冲突】");
        System.out.println();
        
        System.out.println("Java 8+允许接口有默认方法，可能产生冲突：");
        System.out.println();
        
        System.out.println("示例代码：");
        System.out.println("  interface A { default void foo() { System.out.println(\"A\"); } }");
        System.out.println("  interface B { default void foo() { System.out.println(\"B\"); } }");
        System.out.println();
        System.out.println("  class C implements A, B { }  // ❌ 编译错误");
        System.out.println();
        
        System.out.println("错误信息：");
        System.out.println("  Duplicate default methods named foo...");
        System.out.println();
        
        System.out.println("解决方案：类必须重写冲突方法");
        System.out.println();
        
        // 演示解决冲突
        ConflictResolver resolver = new ConflictResolver();
        resolver.foo();
        
        System.out.println();
        System.out.println("解决方案代码：");
        System.out.println("  class ConflictResolver implements A, B {");
        System.out.println("      @Override");
        System.out.println("      public void foo() {");
        System.out.println("          A.super.foo();  // 调用A的默认方法");
        System.out.println("          // 或 B.super.foo();");
        System.out.println("      }");
        System.out.println("  }");
        System.out.println();
        
        System.out.println("优势：");
        System.out.println("  ✓ 编译器强制解决冲突，避免运行时错误");
        System.out.println("  ✓ 代码更明确，减少歧义");
        System.out.println();
    }
}

/**
 * 飞行接口
 * 
 * 【接口说明】
 * - interface：定义接口
 * - 接口只定义规范，不包含实现
 * - 实现类必须实现接口的所有方法
 */
interface Flyable {
    /**
     * 飞行方法
     * 所有实现Flyable接口的类必须实现此方法
     */
    void fly();
}

/**
 * 游泳接口
 */
interface Swimmable {
    /**
     * 游泳方法
     */
    void swim();
}

/**
 * 鸭子类（实现多个接口）
 * 
 * 【实现说明】
 * - implements：实现接口
 * - 可以实现多个接口（用逗号分隔）
 * - 必须实现所有接口的方法
 */
class Duck implements Flyable, Swimmable {
    /**
     * 实现fly方法（来自Flyable接口）
     */
    @Override
    public void fly() {
        System.out.println("鸭子飞翔");
    }
    
    /**
     * 实现swim方法（来自Swimmable接口）
     */
    @Override
    public void swim() {
        System.out.println("鸭子游泳");
    }
}

/**
 * 接口A（有默认方法）
 * 
 * 【默认方法说明】
 * - default：默认方法关键字
 * - 提供默认实现
 * - 实现类可以重写或使用默认实现
 */
interface InterfaceA {
    /**
     * 默认方法：foo
     */
    default void foo() {
        System.out.println("InterfaceA的foo方法");
    }
}

/**
 * 接口B（有默认方法）
 */
interface InterfaceB {
    /**
     * 默认方法：foo（与InterfaceA冲突）
     */
    default void foo() {
        System.out.println("InterfaceB的foo方法");
    }
}

/**
 * 解决冲突的类
 * 
 * 【冲突解决说明】
 * - 同时实现InterfaceA和InterfaceB
 * - 两个接口都有foo()默认方法
 * - 必须重写foo()方法
 */
class ConflictResolver implements InterfaceA, InterfaceB {
    /**
     * 重写foo方法，解决冲突
     * 
     * 【实现方式】
     * 1. 完全自定义实现
     * 2. 调用某个接口的默认实现（InterfaceName.super.method()）
     * 3. 调用多个接口的默认实现
     */
    @Override
    public void foo() {
        System.out.println("解决冲突：显式指定调用哪个接口的方法");
        
        // 调用InterfaceA的默认方法
        // InterfaceA.super：访问接口的默认方法
        InterfaceA.super.foo();  // 输出：InterfaceA的foo方法
        
        // 也可以调用InterfaceB的默认方法
        // InterfaceB.super.foo();
    }
}
