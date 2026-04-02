/**
 * 题目：什么是Java中的类
 *
 * 演示类的基本结构和使用
 *
 * 【题目跳转】
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
 * @see Question15_StaticInstanceMethod 第15题：静态方法和实例方法
 */
public class Question01_Class {
    public static void main(String[] args) {
        System.out.println("=== 演示类的基本概念 ===\n");
        
        // 类是模板，对象是根据模板创建的具体实例
        Student01 s1 = new Student01();
        s1.name = "张三";
        s1.age = 20;
        s1.study();
        
        Student01 s2 = new Student01();
        s2.name = "李四";
        s2.age = 22;
        s2.study();
        
        System.out.println("\n=== 类的三要素 ===");
        System.out.println("1. 字段（属性）：name, age");
        System.out.println("2. 方法（行为）：study()");
        System.out.println("3. 构造器：Student()");
    }
}

/**
 * 学生类 - 演示类的基本结构
 */
class Student01 {
    // 字段（属性）
    String name;
    int age;
    
    // 方法（行为）
    public void study() {
        System.out.println(name + "正在学习，年龄：" + age);
    }
    
    // 构造器
    public Student01() {
        System.out.println("创建了一个学生对象");
    }
}
