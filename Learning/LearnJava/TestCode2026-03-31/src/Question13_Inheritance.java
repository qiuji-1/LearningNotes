/**
 * 题目：什么是Java中的继承机制
 *
 * 演示Java继承的基本概念和使用
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
 * @see Question14_AccessModifiers 第14题：访问修饰符
 * @see Question15_StaticInstanceMethod 第15题：静态方法和实例方法
 */
public class Question13_Inheritance {
    public static void main(String[] args) {
        System.out.println("=== 继承的基本概念 ===\n");
        
        // 创建子类对象
        Dog13 dog = new Dog13("旺财");
        dog.eat();   // 调用继承的方法
        dog.bark();  // 调用自己的方法
        
        System.out.println("\n=== 构造方法的执行顺序 ===\n");
        
        Cat13 cat = new Cat13("咪咪", 3);
        System.out.println("猫的名字：" + cat.getName());
        System.out.println("猫的年龄：" + cat.getAge());
        
        System.out.println("\n=== 访问权限演示 ===\n");
        
        Son13 son = new Son13();
        son.accessFields();
        
        System.out.println("\n=== 继承链 ===\n");
        
        System.out.println("Java只支持单继承，但可以多层继承：");
        System.out.println("  Animal13 → Dog13 → GoldenRetriever13");
        
        GoldenRetriever13 gr = new GoldenRetriever13("大黄");
        gr.eat();      // Animal13的方法
        gr.bark();     // Dog13的方法
        gr.fetch();    // GoldenRetriever13的方法
        
        System.out.println("\n=== 方法重写 ===\n");
        
        Animal13 animal1 = new Animal13("动物");
        Animal13 animal2 = new Dog13("小狗");
        
        animal1.sound();  // Animal13的声音
        animal2.sound();  // Dog13重写后的声音（多态）
        
        System.out.println("\n=== 继承 vs 组合 ===\n");
        
        System.out.println("继承：is-a关系（Dog is a Animal）");
        System.out.println("组合：has-a关系（Car has a Engine）");
        System.out.println();
        System.out.println("推荐：优先使用组合而非继承");
        
        System.out.println("\n=== 常见陷阱 ===\n");
        
        // 陷阱1：静态方法不参与多态
        System.out.println("陷阱1：静态方法不参与多态");
        Father13 f = new Son13();
        f.staticMethod();  // 输出Father13的静态方法
        
        // 陷阱2：字段没有多态
        System.out.println("\n陷阱2：字段没有多态");
        Father13 f2 = new Son13();
        System.out.println("  Father13引用指向Son13对象");
        System.out.println("  f2.name = " + f2.name);  // Father13的字段
        
        Son13 s = new Son13();
        System.out.println("  Son13对象");
        System.out.println("  s.name = " + s.name);  // Son13的字段
        
        System.out.println("\n=== 最佳实践 ===\n");
        
        System.out.println("1. 继承关系要符合is-a原则");
        System.out.println("2. 优先使用组合而非继承");
        System.out.println("3. 父类设计要考虑扩展性");
        System.out.println("4. 父类构造方法要合理设计");
        System.out.println("5. 避免过深的继承层次（建议不超过3层）");
    }
}

// ========== 父类 ==========
class Animal13 {
    protected String name;
    
    // 无参构造
    public Animal13() {
        System.out.println("  Animal无参构造");
    }
    
    // 带参构造
    public Animal13(String name) {
        System.out.println("  Animal带参构造：" + name);
        this.name = name;
    }
    
    public void eat() {
        System.out.println(name + " is eating");
    }
    
    public void sound() {
        System.out.println(name + " makes a sound");
    }
    
    public String getName() {
        return name;
    }
}

// ========== 子类1 ==========
class Dog13 extends Animal13 {
    public Dog13(String name) {
        super(name);  // 调用父类构造方法
        System.out.println("  Dog构造：" + name);
    }
    
    public void bark() {
        System.out.println(name + " is barking");
    }
    
    @Override
    public void sound() {
        System.out.println(name + " 汪汪叫");
    }
}

// ========== 子类2（演示构造顺序）==========
class Cat13 extends Animal13 {
    private int age;
    
    public Cat13(String name, int age) {
        super(name);  // 必须第一行
        this.age = age;
        System.out.println("  Cat构造：" + name + ", " + age + "岁");
    }
    
    public int getAge() {
        return age;
    }
}

// ========== 多层继承 ==========
class GoldenRetriever13 extends Dog13 {
    public GoldenRetriever13(String name) {
        super(name);
        System.out.println("  GoldenRetriever构造：" + name);
    }
    
    public void fetch() {
        System.out.println(name + " is fetching the ball");
    }
}

// ========== 访问权限演示 ==========
class Father13 {
    public int publicField = 1;
    protected int protectedField = 2;
    int defaultField = 3;
    private int privateField = 4;
    
    public static void staticMethod() {
        System.out.println("  Father静态方法");
    }
    
    public String name = "Father";
}

class Son13 extends Father13 {
    public String name = "Son";
    
    public void accessFields() {
        System.out.println("  publicField: " + publicField);
        System.out.println("  protectedField: " + protectedField);
        // System.out.println(defaultField);  // 不同包无法访问
        // System.out.println(privateField);  // private无法访问
        System.out.println("  （private字段只能通过父类方法访问）");
    }
    
    public static void staticMethod() {
        System.out.println("  Son静态方法");
    }
}
