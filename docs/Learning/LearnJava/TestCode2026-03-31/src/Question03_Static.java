/**
 * 题目：static关键字的作用
 *
 * 演示静态变量、静态方法的使用
 *
 * 【题目跳转】
 * @see Question01_Class 第1题：Java中的类
 * @see Question02_Instantiation 第2题：实例化
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
 * @see Question15_StaticInstanceMethod 第15题：静态方法和实例方法
 */
public class Question03_Static {
    public static void main(String[] args) {
        System.out.println("=== 静态变量 vs 实例变量 ===\n");
        
        // 创建两个学生
        Student03 s1 = new Student03("张三");
        Student03 s2 = new Student03("李四");
        
        System.out.println("s1.name: " + s1.name);
        System.out.println("s2.name: " + s2.name);
        System.out.println("Student03.count: " + Student03.count);  // 静态变量用类名访问
        
        System.out.println("\n=== 静态方法调用 ===\n");
        Student03.showCount();  // 静态方法用类名调用
        
        System.out.println("\n=== 静态方法限制 ===\n");
        System.out.println("静态方法：");
        System.out.println("✅ 可以访问静态变量和静态方法");
        System.out.println("❌ 不能访问实例变量和实例方法");
        System.out.println("❌ 不能使用 this 关键字");
        
        System.out.println("\n=== 静态代码块 ===\n");
        System.out.println("静态代码块在类加载时执行一次");
        System.out.println("常用于初始化静态资源");
    }
}

class Student03 {
    String name;           // 实例变量：每个对象独有
    static int count = 0;  // 静态变量：所有对象共享
    
    // 静态代码块：类加载时执行一次
    static {
        System.out.println("静态代码块执行：类被加载了");
    }
    
    public Student03(String name) {
        this.name = name;
        count++;  // 每创建一个学生，count+1
        System.out.println("创建学生：" + name);
    }
    
    // 静态方法
    public static void showCount() {
        System.out.println("学生总数：" + count);
        // System.out.println(name);  // 错误！不能访问实例变量
    }
    
    // 实例方法
    public void study() {
        System.out.println(name + "正在学习");
        showCount();  // 可以访问静态方法
    }
}
