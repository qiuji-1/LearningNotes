/**
 * Question 15: 静态方法和实例方法的区别
 *
 * 学习目标：
 * 1. 理解静态方法和实例方法的区别
 * 2. 掌握静态方法的调用方式
 * 3. 掌握实例方法的调用方式
 * 4. 了解访问权限的差异
 *
 * 【题目跳转】
 * @see Question01_Class 第1题：Java中的类
 * @see Question02_Instantiation 第2题：实例化
 * @see Question03_Static 第3题：static关键字
 * @see Question04_InterfaceAbstract 第4题：接口和抽象类
 * @see Question05_JDKJRE 第5题：JDK和JRE
 * @see Question06_JDKTools 第6题：JDK工具
 * @see Question07_Reflection 第7题：反射机制
 * @see Question08_ThreadStart 第8题：线程start()方法
 * @see Question09_Optional 第9题：Optional类
 * @see Question10_IOStream 第10题：I/O流
 * @see Question11_PrimitiveTypes 第11题：基本数据类型
 * @see Question12_AutoBoxing 第12题：自动拆箱装箱
 * @see Question13_Inheritance 第13题：继承机制
 * @see Question14_AccessModifiers 第14题：访问修饰符
 */

import java.util.ArrayList;
import java.util.List;

// ==================== 静态方法示例 ====================

/**
 * 静态方法示例：工具类
 * 特点：
 * - 方法用static修饰
 * - 通过类名直接调用
 * - 不依赖对象实例
 * - 不能访问实例成员
 */
class StringUtils {
    /**
     * 判断字符串是否为空
     * 静态方法：不需要对象就能调用
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    
    /**
     * 反转字符串
     * 静态方法：工具类方法
     */
    public static String reverse(String str) {
        if (str == null) return null;
        return new StringBuilder(str).reverse().toString();
    }
    
    /**
     * 获取字符串长度
     * 静态方法：纯计算逻辑
     */
    public static int length(String str) {
        return str == null ? 0 : str.length();
    }
}

// ==================== 实例方法示例 ====================

/**
 * 实例方法示例：需要操作对象状态
 * 特点：
 * - 方法不用static修饰
 * - 必须通过对象调用
 * - 依赖对象实例
 * - 可以访问实例成员和静态成员
 */
class Counter {
    private int count = 0;  // 实例变量
    
    /**
     * 增加计数
     * 实例方法：操作对象状态
     */
    public void increment() {
        count++;
    }
    
    /**
     * 减少计数
     * 实例方法：操作对象状态
     */
    public void decrement() {
        count--;
    }
    
    /**
     * 获取当前计数值
     * 实例方法：访问实例状态
     */
    public int getCount() {
        return count;
    }
}

/**
 * 实例方法示例：用户类
 * 特点：需要维护用户状态
 */
class User15 {
    private String name;  // 实例变量
    
    public User15(String name) {
        this.name = name;
    }
    
    /**
     * 获取用户名
     * 实例方法：访问实例变量
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * 设置用户名
     * 实例方法：修改实例状态
     */
    public void setName(String name) {
        this.name = name;
    }
}

// ==================== 混合示例 ====================

/**
 * 混合示例：同时包含静态方法和实例方法
 */
class Calculator {
    private double result = 0;  // 实例变量
    
    /**
     * 静态方法：计算最大值
     * 不依赖对象实例
     */
    public static int max(int a, int b) {
        return a > b ? a : b;
    }
    
    /**
     * 静态方法：计算最小值
     * 不依赖对象实例
     */
    public static int min(int a, int b) {
        return a < b ? a : b;
    }
    
    /**
     * 实例方法：添加数值
     * 依赖对象实例
     */
    public void add(double value) {
        result += value;
    }
    
    /**
     * 实例方法：获取结果
     * 依赖对象实例
     */
    public double getResult() {
        return result;
    }
}

// ==================== 主类 ====================

public class Question15_StaticInstanceMethod {
    
    public static void main(String[] args) {
        System.out.println("=== 静态方法和实例方法的区别 ===\n");
        
        // ==================== 静态方法演示 ====================
        System.out.println("1. 静态方法演示：");
        System.out.println("   特点：通过类名直接调用，不需要创建对象\n");
        
        // 调用静态方法：StringUtils.isEmpty()
        boolean empty1 = StringUtils.isEmpty("hello");
        boolean empty2 = StringUtils.isEmpty("");
        boolean empty3 = StringUtils.isEmpty(null);
        System.out.println("   StringUtils.isEmpty(\"hello\") = " + empty1);
        System.out.println("   StringUtils.isEmpty(\"\") = " + empty2);
        System.out.println("   StringUtils.isEmpty(null) = " + empty3);
        
        // 调用静态方法：StringUtils.reverse()
        String reversed = StringUtils.reverse("Hello World");
        System.out.println("   StringUtils.reverse(\"Hello World\") = " + reversed);
        
        // 调用静态方法：StringUtils.length()
        int length = StringUtils.length("hello");
        System.out.println("   StringUtils.length(\"hello\") = " + length + "\n");
        
        // ==================== 实例方法演示 ====================
        System.out.println("2. 实例方法演示：");
        System.out.println("   特点：必须通过对象调用，依赖对象实例\n");
        
        // 使用实例方法：Counter
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();
        
        System.out.println("   Counter1: increment() 3次");
        counter1.increment();
        counter1.increment();
        counter1.increment();
        System.out.println("   Counter1.getCount() = " + counter1.getCount());
        
        System.out.println("\n   Counter2: increment() 1次");
        counter2.increment();
        System.out.println("   Counter2.getCount() = " + counter2.getCount());
        System.out.println("   说明：每个对象有自己的实例变量\n");
        
        // 使用实例方法：User
        User15 user = new User15("张三");
        System.out.println("   User15.getName() = " + user.getName());
        user.setName("李四");
        System.out.println("   User15.setName(\"李四\")");
        System.out.println("   User15.getName() = " + user.getName() + "\n");
        
        // ==================== 混合方法演示 ====================
        System.out.println("3. 混合方法演示：");
        System.out.println("   静态方法和实例方法可以在同一个类中\n");
        
        // 调用静态方法
        int max = Calculator.max(10, 20);
        int min = Calculator.min(10, 20);
        System.out.println("   Calculator.max(10, 20) = " + max);
        System.out.println("   Calculator.min(10, 20) = " + min);
        
        // 调用实例方法
        Calculator calc = new Calculator();
        calc.add(10);
        calc.add(20);
        System.out.println("   calc.add(10); calc.add(20);");
        System.out.println("   calc.getResult() = " + calc.getResult() + "\n");
        
        // ==================== 区别总结 ====================
        System.out.println("4. 区别总结：");
        System.out.println("   ┌─────────────┬───────────────┬────────────────┐");
        System.out.println("   │   特性      │   静态方法    │   实例方法    │");
        System.out.println("   ├─────────────┼───────────────┼────────────────┤");
        System.out.println("   │   修饰符    │   static     │   无          │");
        System.out.println("   │   调用方式  │   类名.方法() │   对象.方法() │");
        System.out.println("   │   依赖对象  │   ❌ 不依赖   │   ✅ 依赖     │");
        System.out.println("   │   访问this  │   ❌ 不能用  │   ✅ 可以用  │");
        System.out.println("   │   访问实例成员│  ❌ 不能访问 │   ✅ 可以访问 │");
        System.out.println("   │   访问静态成员│  ✅ 可以访问 │   ✅ 可以访问 │");
        System.out.println("   │   存在时间  │   类加载时   │   创建对象时  │");
        System.out.println("   └─────────────┴───────────────┴────────────────┘\n");
        
        // ==================== 使用场景 ====================
        System.out.println("5. 使用场景：");
        System.out.println("   静态方法适用场景：");
        System.out.println("   - 工具类方法：StringUtils.isEmpty()");
        System.out.println("   - 工厂方法：Calendar.getInstance()");
        System.out.println("   - 数学计算：Math.max()\n");
        
        System.out.println("   实例方法适用场景：");
        System.out.println("   - 操作对象状态：counter.increment()");
        System.out.println("   - 访问对象属性：user.getName()");
        System.out.println("   - 实现多态：animal.sound()\n");
        
        // ==================== 常见陷阱 ====================
        System.out.println("6. 常见陷阱：");
        System.out.println("   ❌ 错误1：静态方法中不能使用this");
        System.out.println("   ❌ 错误2：静态方法不能访问实例变量");
        System.out.println("   ❌ 错误3：静态方法不能直接调用实例方法");
        System.out.println("   ❌ 错误4：静态方法无法重写（只能隐藏）\n");
        
        // ==================== 最佳实践 ====================
        System.out.println("7. 最佳实践：");
        System.out.println("   ✅ 纯工具类使用静态方法");
        System.out.println("   ✅ 需要状态的操作使用实例方法");
        System.out.println("   ✅ 工厂方法使用静态方法");
        System.out.println("   ✅ 优先使用静态方法，避免不必要的对象创建\n");
        
        System.out.println("=== 演示完成 ===");
    }
}
