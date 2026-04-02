import java.util.ArrayList;
import java.util.List;

/**
 * 题目：什么是Java中的自动拆箱和装箱
 *
 * 演示自动装箱和拆箱的机制
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
 * @see Question13_Inheritance 第13题：继承机制
 * @see Question14_AccessModifiers 第14题：访问修饰符
 * @see Question15_StaticInstanceMethod 第15题：静态方法和实例方法
 */
public class Question12_AutoBoxing {
    public static void main(String[] args) {
        System.out.println("=== 自动装箱和拆箱 ===\n");
        
        // 自动装箱：基本类型 → 包装类型
        int primitive = 100;
        Integer wrapper = primitive;  // 自动装箱
        System.out.println("自动装箱：int " + primitive + " → Integer " + wrapper);
        
        // 自动拆箱：包装类型 → 基本类型
        Integer obj = Integer.valueOf(200);
        int value = obj;  // 自动拆箱
        System.out.println("自动拆箱：Integer " + obj + " → int " + value);
        
        System.out.println("\n=== 底层实现：valueOf()和intValue() ===\n");
        
        // 手动装箱（实际编译器做的事）
        Integer manualBox = Integer.valueOf(100);
        System.out.println("手动装箱：Integer.valueOf(100) = " + manualBox);
        
        // 手动拆箱
        int manualUnbox = manualBox.intValue();
        System.out.println("手动拆箱：Integer.intValue() = " + manualUnbox);
        
        System.out.println("\n=== 缓存机制：-128 ~ 127 ===\n");
        
        // 在缓存范围内
        Integer a = 100;
        Integer b = 100;
        System.out.println("Integer a = 100;");
        System.out.println("Integer b = 100;");
        System.out.println("a == b: " + (a == b) + "（同一个缓存对象）");
        System.out.println("a.equals(b): " + a.equals(b));
        
        System.out.println();
        
        // 超出缓存范围
        Integer c = 200;
        Integer d = 200;
        System.out.println("Integer c = 200;");
        System.out.println("Integer d = 200;");
        System.out.println("c == d: " + (c == d) + "（不同对象）");
        System.out.println("c.equals(d): " + c.equals(d));
        System.out.println("c.intValue() == d.intValue(): " + (c.intValue() == d.intValue()));
        
        System.out.println("\n=== 包装类缓存范围 ===\n");
        
        System.out.println("Integer缓存：-128 ~ 127");
        System.out.println("Long缓存：-128 ~ 127");
        System.out.println("Short缓存：-128 ~ 127");
        System.out.println("Byte缓存：-128 ~ 127（全部）");
        System.out.println("Character缓存：0 ~ 127");
        System.out.println("Boolean缓存：TRUE, FALSE");
        System.out.println("Float/Double：无缓存");
        
        System.out.println("\n=== 集合中的自动装箱拆箱 ===\n");
        
        List<Integer> list = new ArrayList<>();
        
        // 添加元素（自动装箱）
        list.add(10);     // int → Integer
        list.add(20);
        list.add(30);
        System.out.println("添加元素时自动装箱：list.add(10)");
        System.out.println("列表内容：" + list);
        
        // 获取元素（自动拆箱）
        int sum = 0;
        for (Integer num : list) {
            sum += num;   // Integer → int（自动拆箱）
        }
        System.out.println("计算总和时自动拆箱：" + sum);
        
        System.out.println("\n=== 常见陷阱 ===\n");
        
        // 陷阱1：NPE空指针
        System.out.println("陷阱1：拆箱时NPE");
        Integer nullObj = null;
        try {
            int n = nullObj;  // 自动拆箱抛NPE
        } catch (NullPointerException e) {
            System.out.println("  Integer为null时拆箱 → NullPointerException");
        }
        
        // 正确方式
        if (nullObj != null) {
            int n = nullObj;
        } else {
            System.out.println("  正确：先判空再拆箱");
        }
        
        // 陷阱2：性能问题
        System.out.println("\n陷阱2：循环中频繁装箱");
        System.out.println("  ❌ 错误：Integer sum = 0; 循环中sum += i;（频繁装箱拆箱）");
        System.out.println("  ✅ 正确：int sum = 0; （用基本类型）");
        
        // 陷阱3：比较问题
        System.out.println("\n陷阱3：== 比较陷阱");
        Integer x = 100;
        Integer y = 100;
        Integer z = 200;
        Integer w = 200;
        
        System.out.println("  Integer(100) == Integer(100): " + (x == y));
        System.out.println("  Integer(200) == Integer(200): " + (z == w));
        System.out.println("  推荐：使用equals比较");
        
        // 陷阱4：三目运算符
        System.out.println("\n陷阱4：三目运算符的空指针");
        Integer ternary = null;
        // int result = ternary == null ? 0 : ternary;  // NPE!
        int result = (ternary == null) ? 0 : ternary;
        System.out.println("  显式判断：(ternary == null) ? 0 : ternary = " + result);
        
        System.out.println("\n=== 最佳实践 ===\n");
        
        System.out.println("1. 集合用包装类，计算用基本类型");
        System.out.println("2. 拆箱前判空，避免NPE");
        System.out.println("3. 包装类比较用equals，不用==");
        System.out.println("4. 性能敏感场景用基本类型");
        System.out.println("5. 了解缓存范围，避免创建不必要的对象");
        
        System.out.println("\n=== 装箱拆箱的场景 ===\n");
        
        System.out.println("自动装箱场景：");
        System.out.println("  1. 基本类型赋值给包装类变量");
        System.out.println("  2. 基本类型放入集合");
        System.out.println("  3. 方法参数需要包装类型");
        
        System.out.println("\n自动拆箱场景：");
        System.out.println("  1. 包装类赋值给基本类型变量");
        System.out.println("  2. 包装类参与算术运算");
        System.out.println("  3. 包装类用于条件判断");
    }
}
