/**
 * 题目：Java中的访问修饰符有哪些
 *
 * 演示Java四种访问修饰符的使用
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
 * @see Question15_StaticInstanceMethod 第15题：静态方法和实例方法
 */
public class Question14_AccessModifiers {
    public static void main(String[] args) {
        System.out.println("=== 四种访问修饰符 ===\n");
        
        System.out.println("1. public: 任何地方都能访问");
        PublicClass pc = new PublicClass();
        pc.publicMethod();
        
        System.out.println("\n2. protected: 同包+子类能访问");
        Parent parent = new Parent();
        Child child = new Child();
        
        System.out.println("\n3. 默认: 同包能访问");
        DefaultClass dc = new DefaultClass();
        
        System.out.println("\n4. private: 只有本类能访问");
        PrivateClass pvc = new PrivateClass();
        pvc.publicMethod();  // 通过public方法访问私有内容
        
        System.out.println("\n=== 访问权限测试 ===\n");
        
        // 测试protected访问
        child.testProtectedAccess();
        
        System.out.println("\n=== 继承中的访问权限 ===\n");
        
        child.inheritAccess();
        
        System.out.println("\n=== 最佳实践演示 ===\n");
        
        Person person = new Person("张三", "杭州", "123456");
        System.out.println("姓名（public getter）：" + person.getName());
        System.out.println("密码（private）：" + "****");
        
        System.out.println("\n=== 访问修饰符选择原则 ===\n");
        
        System.out.println("1. 最小权限原则：能用private就不用protected");
        System.out.println("2. 字段用private，方法用public（封装）");
        System.out.println("3. 继承用protected，实现用public");
        System.out.println("4. 顶级类只能public或默认，不能用protected/private");
        
        System.out.println("\n=== 总结 ===\n");
        
        System.out.println("public:    任何地方都能访问");
        System.out.println("protected: 同包和子类能访问");
        System.out.println("默认:     同包能访问");
        System.out.println("private:   只有本类能访问");
    }
}

// ========== public类 ==========
class PublicClass {
    public int publicField = 1;
    
    public void publicMethod() {
        System.out.println("  publicField: " + publicField);
    }
}

// ========== protected演示 ==========
class Parent {
    protected int protectedField = 100;
    
    protected void protectedMethod() {
        System.out.println("  protectedField: " + protectedField);
    }
    
    public void doSomething() {
        System.out.println("  父类的protected方法");
    }
}

class Child extends Parent {
    public void testProtectedAccess() {
        System.out.println("子类可以访问父类的protected成员：");
        System.out.println("  protectedField: " + protectedField);
        protectedMethod();
    }
    
    // 子类无法访问父类的private成员
    // private int privateField;  // 父类没有private，但即使有也访问不了
    
    public void inheritAccess() {
        System.out.println("继承测试：");
        doSomething();  // 可以调用protected方法
    }
}

// ========== 默认类（同包）==========
class DefaultClass {
    int defaultField = 10;
    
    void defaultMethod() {
        System.out.println("  defaultField: " + defaultField);
    }
}

// ========== private类 ==========
class PrivateClass {
    private int privateField = 20;
    
    public void publicMethod() {
        System.out.println("  内部privateField: " + privateField);
        // 外部不能直接访问privateField，必须通过public方法
    }
}

// ========== 最佳实践演示 ==========
class Person {
    private String password;  // private: 敏感信息
    
    public Person(String name, String address, String password) {
        this.address = address;
        this.password = password;
    }
    
    private String name;
    private String address;
    
    // public getter: 对外提供访问
    public String getName() {
        return name;
    }
    
    // protected: 给子类扩展
    protected void display() {
        System.out.println("  姓名: " + name);
        System.out.println("  地址: " + address);
    }
    
    // 验证后才能访问敏感信息
    public String getPassword(String inputPassword) {
        if (inputPassword.equals(password)) {
            return password;
        }
        return null;
    }
}
