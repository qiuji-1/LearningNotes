import java.util.*;

/**
 * Java 多态特性测试代码
 * 
 * 测试内容：
 * 1. 编译时多态（方法重载）
 * 2. 运行时多态（方法重写）
 * 3. 动态绑定原理
 * 4. 不参与多态的方法
 * 5. instanceof 和类型转换
 * 6. 多态的实际应用
 * 7. 多态的优势
 */
public class Question09_Polymorphism {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("Java 多态特性测试代码");
        System.out.println("=".repeat(60));
        
        test01_CompileTimePolymorphism();
        test02_RuntimePolymorphism();
        test03_DynamicBinding();
        test04_NonPolymorphicMethods();
        test05_InstanceOfAndCasting();
        test06_RealWorldApplication();
        test07_PolymorphismAdvantage();
        
        System.out.println("=".repeat(60));
        System.out.println("所有测试完成！");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 测试1：编译时多态（方法重载）
     */
    public static void test01_CompileTimePolymorphism() {
        System.out.println("\n--- 测试1：编译时多态（方法重载） ---");
        System.out.println("定义：同一个类里，方法名一样但参数列表不同");
        System.out.println("特点：编译器在编译阶段确定调用哪个方法");
        System.out.println();
        
        class Printer {
            public void print(int num) {
                System.out.println("  打印整数: " + num);
            }
            
            public void print(String str) {
                System.out.println("  打印字符串: " + str);
            }
            
            public void print(String str, int num) {
                System.out.println("  打印: " + str + ", " + num);
            }
            
            public void print(int num, String str) {
                System.out.println("  打印: " + num + ", " + str);
            }
        }
        
        Printer printer = new Printer();
        System.out.println("调用不同的重载方法：");
        printer.print(10);           // print(int)
        printer.print("Hello");       // print(String)
        printer.print("Hi", 20);      // print(String, int)
        printer.print(30, "World");  // print(int, String)
        
        System.out.println("\n说明: 编译器在编译期就确定了调用哪个方法");
        System.out.println("      根据参数的类型和个数匹配重载方法");
    }
    
    /**
     * 测试2：运行时多态（方法重写）
     */
    public static void test02_RuntimePolymorphism() {
        System.out.println("\n--- 测试2：运行时多态（方法重写） ---");
        System.out.println("定义：父类引用指向子类对象，调用子类的方法");
        System.out.println("特点：JVM 在运行时动态决定调用哪个方法");
        System.out.println();
        
        class Animal {
            public void makeSound() {
                System.out.println("  动物发出声音");
            }
        }
        
        class Dog extends Animal {
            @Override
            public void makeSound() {
                System.out.println("  汪汪汪");
            }
        }
        
        class Cat extends Animal {
            @Override
            public void makeSound() {
                System.out.println("  喵喵喵");
            }
        }
        
        System.out.println("多态调用：");
        Animal a1 = new Dog();
        Animal a2 = new Cat();
        Animal a3 = new Animal();
        
        System.out.print("  Dog 对象: ");
        a1.makeSound();  // 调用 Dog.makeSound()
        
        System.out.print("  Cat 对象: ");
        a2.makeSound();  // 调用 Cat.makeSound()
        
        System.out.print("  Animal 对象: ");
        a3.makeSound();  // 调用 Animal.makeSound()
        
        System.out.println("\n说明: JVM 在运行时根据实际类型调用方法");
        System.out.println("      引用类型是 Animal，实际类型是 Dog/Cat");
    }
    
    /**
     * 测试3：动态绑定原理
     */
    public static void test03_DynamicBinding() {
        System.out.println("\n--- 测试3：动态绑定原理 ---");
        System.out.println("核心机制：动态分派");
        System.out.println("JVM 根据对象的实际类型去方法区找对应的实现");
        System.out.println();
        
        class Parent {
            public void method() {
                System.out.println("  父类方法");
            }
        }
        
        class Child extends Parent {
            @Override
            public void method() {
                System.out.println("  子类方法");
            }
        }
        
        System.out.println("分析多态调用：");
        Parent p = new Child();
        
        System.out.println("  引用类型: " + p.getClass().getSuperclass().getSimpleName());
        System.out.println("  实际类型: " + p.getClass().getSimpleName());
        System.out.print("  调用 method(): ");
        p.method();  // 调用 Child.method()
        
        System.out.println("\n执行过程：");
        System.out.println("  1. 编译期：检查 Parent 类有 method 方法");
        System.out.println("  2. 运行期：获取 p 的实际类型 Child");
        System.out.println("  3. 动态绑定：在 Child 类中找 method 方法");
        System.out.println("  4. 执行：Child.method()");
    }
    
    /**
     * 测试4：不参与多态的方法
     */
    public static void test04_NonPolymorphicMethods() {
        System.out.println("\n--- 测试4：不参与多态的方法 ---");
        System.out.println("以下方法不参与多态：");
        System.out.println("  1. 静态方法（static）");
        System.out.println("  2. 私有方法（private）");
        System.out.println("  3. 构造方法");
        System.out.println();
        
        class Parent {
            public static void staticMethod() {
                System.out.println("  父类静态方法");
            }
            
            private void privateMethod() {
                System.out.println("  父类私有方法");
            }
            
            public void normalMethod() {
                System.out.println("  父类普通方法");
            }
        }
        
        class Child extends Parent {
            // 静态方法：隐藏父类方法，不是重写
            public static void staticMethod() {
                System.out.println("  子类静态方法");
            }
            
            // 私有方法：无法重写父类私有方法
            private void privateMethod() {
                System.out.println("  子类私有方法");
            }
            
            // 普通方法：重写父类方法
            @Override
            public void normalMethod() {
                System.out.println("  子类普通方法");
            }
        }
        
        Parent p = new Child();
        
        System.out.println("静态方法：");
        Parent.staticMethod();  // 调用父类静态方法
        Child.staticMethod();     // 调用子类静态方法
        
        System.out.println("\n普通方法：");
        p.normalMethod();  // 调用子类方法
        
        System.out.println("\n说明: 静态方法和私有方法不参与多态");
        System.out.println("      它们的调用在编译期就确定了");
    }
    
    /**
     * 测试5：instanceof 和类型转换
     */
    public static void test05_InstanceOfAndCasting() {
        System.out.println("\n--- 测试5：instanceof 和类型转换 ---");
        
        class Animal {
            public void makeSound() {
                System.out.println("  动物发声");
            }
        }
        
        class Dog extends Animal {
            public void bark() {
                System.out.println("  狗叫");
            }
        }
        
        class Cat extends Animal {
            public void meow() {
                System.out.println("  猫叫");
            }
        }
        
        List<Animal> animals = new ArrayList<>();
        animals.add(new Dog());
        animals.add(new Cat());
        animals.add(new Animal());
        
        System.out.println("使用 instanceof 判断类型：");
        for (Animal animal : animals) {
            System.out.print("  类型: " + animal.getClass().getSimpleName() + ", ");
            
            if (animal instanceof Dog) {
                Dog dog = (Dog) animal;  // 向下转型
                dog.bark();
            } else if (animal instanceof Cat) {
                Cat cat = (Cat) animal;  // 向下转型
                cat.meow();
            } else {
                animal.makeSound();
            }
        }
        
        System.out.println("\n说明: instanceof 判断对象的实际类型");
        System.out.println("      向下转型需要先判断，否则可能抛出 ClassCastException");
    }
    
    /**
     * 测试6：多态的实际应用
     */
    public static void test06_RealWorldApplication() {
        System.out.println("\n--- 测试6：多态的实际应用 ---");
        System.out.println("场景：支付系统的多态设计");
        System.out.println();
        
        // 支付接口
        interface Payment {
            void pay(double amount);
        }
        
        class Alipay implements Payment {
            @Override
            public void pay(double amount) {
                System.out.println("  使用支付宝支付: " + amount + " 元");
            }
        }
        
        class WechatPay implements Payment {
            @Override
            public void pay(double amount) {
                System.out.println("  使用微信支付: " + amount + " 元");
            }
        }
        
        class BankCard implements Payment {
            @Override
            public void pay(double amount) {
                System.out.println("  使用银行卡支付: " + amount + " 元");
            }
        }
        
        // 支付服务
        class PaymentService {
            public void processPayment(Payment payment, double amount) {
                System.out.println("  开始处理支付...");
                payment.pay(amount);
                System.out.println("  支付完成");
            }
        }
        
        // 使用多态
        PaymentService service = new PaymentService();
        
        System.out.println("不同的支付方式，同样的处理逻辑：");
        service.processPayment(new Alipay(), 100.0);
        System.out.println();
        service.processPayment(new WechatPay(), 200.0);
        System.out.println();
        service.processPayment(new BankCard(), 300.0);
        
        System.out.println("\n说明: 上层代码依赖接口（Payment），不依赖具体实现");
        System.out.println("      新增支付方式不需要修改 PaymentService");
    }
    
    /**
     * 测试7：多态的优势
     */
    public static void test07_PolymorphismAdvantage() {
        System.out.println("\n--- 测试7：多态的优势 ---");
        System.out.println();
        
        // 图形接口
        interface Shape {
            double getArea();
            double getPerimeter();
        }
        
        class Circle implements Shape {
            private double radius;
            
            public Circle(double radius) {
                this.radius = radius;
            }
            
            @Override
            public double getArea() {
                return Math.PI * radius * radius;
            }
            
            @Override
            public double getPerimeter() {
                return 2 * Math.PI * radius;
            }
            
            @Override
            public String toString() {
                return "圆形(半径=" + radius + ")";
            }
        }
        
        class Rectangle implements Shape {
            private double width;
            private double height;
            
            public Rectangle(double width, double height) {
                this.width = width;
                this.height = height;
            }
            
            @Override
            public double getArea() {
                return width * height;
            }
            
            @Override
            public double getPerimeter() {
                return 2 * (width + height);
            }
            
            @Override
            public String toString() {
                return "矩形(宽=" + width + ", 高=" + height + ")";
            }
        }
        
        // 图形计算器
        class ShapeCalculator {
            public double calculateTotalArea(List<Shape> shapes) {
                double total = 0;
                for (Shape shape : shapes) {
                    total += shape.getArea();
                }
                return total;
            }
            
            public double calculateTotalPerimeter(List<Shape> shapes) {
                double total = 0;
                for (Shape shape : shapes) {
                    total += shape.getPerimeter();
                }
                return total;
            }
        }
        
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Circle(5.0));
        shapes.add(new Rectangle(4.0, 6.0));
        shapes.add(new Circle(3.0));
        
        ShapeCalculator calculator = new ShapeCalculator();
        double totalArea = calculator.calculateTotalArea(shapes);
        double totalPerimeter = calculator.calculateTotalPerimeter(shapes);
        
        System.out.println("图形列表：");
        for (Shape shape : shapes) {
            System.out.println("  " + shape);
        }
        
        System.out.println("\n计算结果：");
        System.out.printf("  总面积: %.2f\n", totalArea);
        System.out.printf("  总周长: %.2f\n", totalPerimeter);
        
        System.out.println("\n说明: 多态的优势：");
        System.out.println("  1. 解耦：上层依赖接口，不依赖具体实现");
        System.out.println("  2. 扩展性：新增图形不需要修改计算器");
        System.out.println("  3. 灵活性：同一个接口，多种实现");
        System.out.println("  4. 可维护性：代码清晰，职责分明");
    }
}
