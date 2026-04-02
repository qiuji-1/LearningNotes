/**
 * 第4题：方法重载和方法重写的区别
 * 
 * 【核心概念】
 * 方法重载（Overloading）：
 * - 同一个类中，方法名相同但参数列表不同
 * - 编译期绑定（静态绑定）
 * - 与返回类型无关
 * 
 * 方法重写（Overriding）：
 * - 父子类中，子类重新定义父类的方法
 * - 运行期绑定（动态绑定）
 * - 参数列表必须相同
 * 
 * 【关键区别】
 * 重载：同一类，参数不同，编译期
 * 重写：父子类，参数相同，运行期
 * 
 * 【题目跳转】
 * @see Question01_Serialization 第1题：序列化
 * @see Question02_JavaAdvantages 第2题：Java优势
 * @see Question03_MultipleInheritance 第3题：多重继承
 * @see Question05_Java25Features 第5题：Java 25新特性
 * @see Question06_Java11Features 第6题：Java 11新特性
 * @see Question07_Java17Features 第7题：Java 17新特性
 * @see Question08_Java21Features 第8题：Java 21新特性
 * 
 * 运行要求：Java 8+
 */

// 没有导入：此示例不需要额外的类库

/**
 * 主类：方法重载和重写演示
 */
public class Question04_MethodOverloadOverride {
    
    /**
     * main方法：程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        System.out.println("========== 第4题：方法重载和方法重写的区别 ==========\n");
        
        // 测试1：方法重载
        testMethodOverloading();
        
        // 测试2：方法重写
        testMethodOverriding();
        
        // 测试3：@Override注解的作用
        testOverrideAnnotation();
        
        // 测试4：重载 vs 重写对比
        testComparison();
    }
    
    /**
     * 测试1：方法重载（Overloading）
     * 
     * 【什么是方法重载？】
     * - 同一个类中，方法名相同
     * - 参数列表不同（个数、类型、顺序）
     * - 与返回类型无关
     * 
     * 【规则】
     * 1. 方法名必须相同
     * 2. 参数列表必须不同
     * 3. 返回类型可以相同也可以不同
     * 4. 访问权限可以不同
     * 
     * 【为什么需要重载？】
     * - 提供更灵活的方法调用
     * - 同名方法处理不同参数
     * - 提高代码可读性
     */
    private static void testMethodOverloading() {
        System.out.println("【测试1：方法重载（Overloading）】");
        System.out.println();
        
        System.out.println("定义：同一个类中，方法名相同但参数列表不同");
        System.out.println();
        
        // 创建Calculator对象
        Calculator calc = new Calculator();
        
        // 调用不同参数的add方法
        // 编译器根据参数类型选择调用哪个方法
        
        System.out.println("调用示例：");
        
        // 调用add(int, int)：两个整数参数
        // 编译器识别参数类型为(int, int)，调用第一个add方法
        System.out.println("  add(1, 2) = " + calc.add(1, 2));
        
        // 调用add(int, int, int)：三个整数参数
        // 编译器识别参数类型为(int, int, int)，调用第二个add方法
        System.out.println("  add(1, 2, 3) = " + calc.add(1, 2, 3));
        
        // 调用add(double, double)：两个浮点数参数
        // 编译器识别参数类型为(double, double)，调用第三个add方法
        System.out.println("  add(1.5, 2.5) = " + calc.add(1.5, 2.5));
        
        System.out.println();
        System.out.println("重载规则：");
        System.out.println("  1. 方法名必须相同");
        System.out.println("  2. 参数列表必须不同（个数、类型、顺序）");
        System.out.println("  3. 返回类型可以相同也可以不同");
        System.out.println("  4. 发生在编译期（静态绑定）");
        System.out.println();
        
        System.out.println("代码示例：");
        System.out.println("  class Calculator {");
        System.out.println("      int add(int a, int b) { return a + b; }");
        System.out.println("      int add(int a, int b, int c) { return a + b + c; }");
        System.out.println("      double add(double a, double b) { return a + b; }");
        System.out.println("  }");
        System.out.println();
    }
    
    /**
     * 测试2：方法重写（Overriding）
     * 
     * 【什么是方法重写？】
     * - 子类重新定义父类的方法
     * - 方法名、参数列表必须相同
     * - 返回类型必须相同或协变（子类返回类型）
     * 
     * 【规则】
     * 1. 方法名必须相同
     * 2. 参数列表必须相同
     * 3. 返回类型必须相同或协变
     * 4. 访问权限不能更严格（public > protected > default > private）
     * 5. 不能抛出新的受检异常
     * 
     * 【多态】
     * - 父类引用指向子类对象
     * - 运行时调用子类的方法
     * - 这是Java多态的核心
     */
    private static void testMethodOverriding() {
        System.out.println("【测试2：方法重写（Overriding）】");
        System.out.println();
        
        System.out.println("定义：子类重新定义父类的方法，参数列表必须相同");
        System.out.println();
        
        // 创建不同的Animal对象
        // 多态：父类引用指向子类对象
        
        // Animal对象
        Animal animal = new Animal();
        
        // Dog对象（Dog是Animal的子类）
        // 多态：Animal引用指向Dog对象
        Animal dog = new Dog();
        
        // Cat对象（Cat是Animal的子类）
        // 多态：Animal引用指向Cat对象
        Animal cat = new Cat();
        
        System.out.println("多态示例：");
        
        // 调用Animal的makeSound
        // 编译器看到的是Animal类型，调用Animal的makeSound
        System.out.print("  animal.makeSound(): ");
        animal.makeSound();  // 输出：动物发出声音
        
        // 调用Dog的makeSound（运行时多态）
        // 编译器看到的是Animal类型，但运行时调用Dog的makeSound
        System.out.print("  dog.makeSound(): ");
        dog.makeSound();     // 输出：狗汪汪叫
        
        // 调用Cat的makeSound（运行时多态）
        System.out.print("  cat.makeSound(): ");
        cat.makeSound();     // 输出：猫喵喵叫
        
        System.out.println();
        System.out.println("重写规则：");
        System.out.println("  1. 方法名必须相同");
        System.out.println("  2. 参数列表必须相同");
        System.out.println("  3. 返回类型必须相同或协变");
        System.out.println("  4. 访问权限不能更严格");
        System.out.println("  5. 发生在运行期（动态绑定）");
        System.out.println();
        
        System.out.println("代码示例：");
        System.out.println("  class Animal {");
        System.out.println("      void makeSound() { System.out.println(\"动物发出声音\"); }");
        System.out.println("  }");
        System.out.println();
        System.out.println("  class Dog extends Animal {");
        System.out.println("      @Override");
        System.out.println("      void makeSound() { System.out.println(\"狗汪汪叫\"); }");
        System.out.println("  }");
        System.out.println();
    }
    
    /**
     * 测试3：@Override注解的作用
     * 
     * 【@Override的作用】
     * 1. 编译期检查：确保正确重写父类方法
     * 2. 提高可读性：明确表示这是重写方法
     * 3. 防止错误：避免拼写错误或参数签名错误
     * 
     * 【如果不使用@Override】
     * - 方法名拼写错误，编译器不会报错
     * - 参数签名错误，编译器认为是新方法
     * - 运行时发现错误，难以调试
     */
    private static void testOverrideAnnotation() {
        System.out.println("【测试3：@Override注解的作用】");
        System.out.println();
        
        System.out.println("作用1：编译期检查");
        System.out.println("  确保方法签名正确（方法名、参数、返回类型）");
        System.out.println("  如果拼写错误或参数不匹配，编译器会报错");
        System.out.println();
        
        System.out.println("作用2：提高代码可读性");
        System.out.println("  明确表示这是重写父类的方法");
        System.out.println("  方便其他开发者理解代码意图");
        System.out.println();
        
        System.out.println("作用3：防止错误");
        System.out.println("  避免因拼写错误导致的问题");
        System.out.println("  示例：");
        System.out.println("    class Dog extends Animal {");
        System.out.println("      @Override");
        System.out.println("      void makesound() { ... }  // ❌ 编译错误：方法名错误");
        System.out.println("    }");
        System.out.println();
        
        // 测试@Override
        Dog dog = new Dog();
        dog.makeSound();
        
        System.out.println();
        System.out.println("建议：");
        System.out.println("  ✓ 所有重写方法都加@Override注解");
        System.out.println("  ✓ IDE会自动提示添加@Override");
        System.out.println();
    }
    
    /**
     * 测试4：重载 vs 重写对比
     * 
     * 【对比表格】
     * | 对比项 | 方法重载 | 方法重写 |
     * |--------|---------|---------|
     * | 发生位置 | 同一个类 | 父子类 |
     * | 方法名 | 相同 | 相同 |
     * | 参数列表 | 必须不同 | 必须相同 |
     * | 返回类型 | 可以不同 | 必须相同或协变 |
     * | 访问权限 | 可以不同 | 不能更严格 |
     * | 异常 | 可以不同 | 不能抛出新异常 |
     * | 发生时机 | 编译期 | 运行期 |
     */
    private static void testComparison() {
        System.out.println("【测试4：重载 vs 重写对比】");
        System.out.println();
        
        System.out.println("对比表格：");
        System.out.println();
        System.out.println("| 对比项 | 方法重载 | 方法重写 |");
        System.out.println("|--------|---------|---------|");
        System.out.println("| 发生位置 | 同一个类 | 父子类 |");
        System.out.println("| 方法名 | 相同 | 相同 |");
        System.out.println("| 参数列表 | 必须不同 | 必须相同 |");
        System.out.println("| 返回类型 | 可以不同 | 必须相同或协变 |");
        System.out.println("| 访问权限 | 可以不同 | 不能更严格 |");
        System.out.println("| 异常 | 可以不同 | 不能抛出新异常 |");
        System.out.println("| 发生时机 | 编译期（静态绑定） | 运行期（动态绑定） |");
        System.out.println("| 多态 | 不涉及 | 核心机制 |");
        System.out.println();
        
        System.out.println("记忆方法：");
        System.out.println("  重载：载入不同的参数");
        System.out.println("  重写：重新写一遍（子类重写父类）");
        System.out.println();
    }
}

/**
 * 计算器类（演示方法重载）
 * 
 * 【类说明】
 * - 演示方法重载的概念
 * - 同一个类中有多个同名方法
 * - 参数列表不同
 */
class Calculator {
    /**
     * 重载方法1：两个整数相加
     * 
     * @param a 第一个整数
     * @param b 第二个整数
     * @return 两个整数的和
     */
    public int add(int a, int b) {
        return a + b;
    }
    
    /**
     * 重载方法2：三个整数相加
     * 
     * @param a 第一个整数
     * @param b 第二个整数
     * @param c 第三个整数
     * @return 三个整数的和
     */
    public int add(int a, int b, int c) {
        return a + b + c;
    }
    
    /**
     * 重载方法3：两个浮点数相加
     * 
     * @param a 第一个浮点数
     * @param b 第二个浮点数
     * @return 两个浮点数的和
     */
    public double add(double a, double b) {
        return a + b;
    }
}

/**
 * 动物类（父类）
 * 
 * 【类说明】
 * - 作为父类，定义基本行为
 * - 子类可以重写父类的方法
 */
class Animal {
    /**
     * 发出声音方法
     * 
     * 【方法说明】
     * - 父类定义基本行为
     * - 子类可以重写此方法
     */
    public void makeSound() {
        System.out.println("动物发出声音");
    }
}

/**
 * 狗类（子类，重写makeSound）
 * 
 * 【类说明】
 * - extends Animal：继承Animal类
 * - 重写makeSound方法
 * - @Override注解确保正确重写
 */
class Dog extends Animal {
    /**
     * 重写makeSound方法
     * 
     * @Override：
     * - 编译期检查方法签名
     * - 确保正确重写父类方法
     * - 提高代码可读性
     */
    @Override
    public void makeSound() {
        System.out.println("狗汪汪叫");
    }
}

/**
 * 猫类（子类，重写makeSound）
 * 
 * 【类说明】
 * - 继承Animal类
 * - 重写makeSound方法
 */
class Cat extends Animal {
    /**
     * 重写makeSound方法
     */
    @Override
    public void makeSound() {
        System.out.println("猫喵喵叫");
    }
}
