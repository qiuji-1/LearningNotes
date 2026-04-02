/**
 * 题目：什么是实例化
 *
 * 演示实例化的过程和对象与引用的关系
 *
 * 【题目跳转】
 * @see Question01_Class 第1题：Java中的类
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
public class Question02_Instantiation {
    public static void main(String[] args) {
        System.out.println("=== 实例化的完整过程 ===\n");
        
        // 1. 声明引用变量
        // 2. new 创建对象
        // 3. 调用构造器初始化
        // 4. 将对象地址赋给引用
        Student02 s1 = new Student02("张三", 20);
        
        System.out.println("\n=== 对象与引用的区别 ===\n");
        
        Student02 s2 = new Student02("李四", 22);
        Student02 s3 = s2;  // s3和s2指向同一个对象
        
        System.out.println("s2: " + s2.name);
        System.out.println("s3: " + s3.name);
        
        s3.name = "李四改了";
        System.out.println("修改s3.name后：");
        System.out.println("s2: " + s2.name);  // s2也被改了
        System.out.println("s3: " + s3.name);
        
        System.out.println("\n=== 实例初始化顺序 ===\n");
        Student02 s4 = new Student02("王五", 25);
    }
}

class Student02 {
    String name = initName();      // 字段初始化
    int age;
    static int count = 0;          // 静态变量（类加载时初始化）
    
    // 静态代码块（类加载时执行）
    static {
        System.out.println("1. 静态代码块执行（类加载时）");
    }
    
    // 字段初始化方法
    private String initName() {
        System.out.println("2. 字段初始化");
        return "默认值";
    }
    
    // 构造器
    public Student02(String name, int age) {
        System.out.println("3. 构造器执行");
        this.name = name;
        this.age = age;
    }
}
