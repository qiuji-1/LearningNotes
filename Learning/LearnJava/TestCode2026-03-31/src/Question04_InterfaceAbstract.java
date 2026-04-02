/**
 * 题目：接口和抽象类有什么区别
 *
 * 演示接口和抽象类的区别与使用
 *
 * 【题目跳转】
 * @see Question01_Class 第1题：Java中的类
 * @see Question02_Instantiation 第2题：实例化
 * @see Question03_Static 第3题：static关键字
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
public class Question04_InterfaceAbstract {
    public static void main(String[] args) {
        System.out.println("=== 接口演示 ===\n");
        
        Duck duck = new Duck("鸭子");
        duck.fly();
        duck.swim();
        duck.eat();
        
        System.out.println("\n=== 接口的常量 ===\n");
        System.out.println("最大速度：" + Flyable.MAX_SPEED);
        
        System.out.println("\n=== 抽象类演示 ===\n");
        Dog dog = new Dog("旺财");
        dog.eat();
        dog.sleep();
        
        System.out.println("\n=== 多实现演示 ===\n");
        System.out.println("Duck实现了Flyable和Swimmable两个接口");
        System.out.println("抽象类只能单继承，接口可以多实现");
        
        System.out.println("\n=== 三种方法演示 ===\n");
        
        // 1. 抽象方法：必须实现
        Bird bird = new Bird("麻雀");
        bird.fly();
        
        // 2. default方法：可以不重写
        bird.defaultFly();
        
        // 3. static方法：用接口名调用
        Flyable.showInfo();
        
        System.out.println("\n=== 普通类 vs 抽象类 ===\n");
        System.out.println("普通类：可以直接new");
        System.out.println("抽象类：不能直接new，必须有子类继承");
    }
}

/**
 * 接口：定义"飞"的行为
 */
interface Flyable {
    int MAX_SPEED = 100;  // 默认是public static final
    
    void fly();  // 默认是public abstract（抽象方法）
    
    // Java 8: default方法（默认实现）
    default void defaultFly() {
        System.out.println("默认的飞行方式");
    }
    
    // Java 8: static方法（工具方法）
    static void showInfo() {
        System.out.println("这是Flyable接口，版本：1.0");
    }
}

/**
 * 接口：定义"游泳"的行为
 */
interface Swimmable {
    void swim();
}

/**
 * 抽象类：动物
 */
abstract class Animal {
    protected String name;
    
    // 抽象类可以有构造函数
    public Animal(String name) {
        this.name = name;
        System.out.println("创建动物：" + name);
    }
    
    // 抽象方法：子类必须实现
    public abstract void eat();
    
    // 具体方法：子类可以继承使用
    public void sleep() {
        System.out.println(name + "在睡觉");
    }
}

/**
 * 具体类：鸭子（多实现）
 */
class Duck extends Animal implements Flyable, Swimmable {
    
    public Duck(String name) {
        super(name);
    }
    
    @Override
    public void fly() {
        System.out.println(name + "在飞，速度：" + MAX_SPEED);
    }
    
    @Override
    public void swim() {
        System.out.println(name + "在游泳");
    }
    
    @Override
    public void eat() {
        System.out.println(name + "在吃小鱼");
    }
}

/**
 * 具体类：狗（单继承）
 */
class Dog extends Animal {
    
    public Dog(String name) {
        super(name);
    }
    
    @Override
    public void eat() {
        System.out.println(name + "在吃骨头");
    }
}

/**
 * 具体类：鸟（演示三种方法）
 */
class Bird extends Animal implements Flyable {
    
    public Bird(String name) {
        super(name);
    }
    
    @Override
    public void eat() {
        System.out.println(name + "在吃虫子");
    }
    
    @Override
    public void fly() {
        System.out.println(name + "在飞（实现了抽象方法）");
    }
    
    // defaultFly()可以不重写，直接继承使用
}
