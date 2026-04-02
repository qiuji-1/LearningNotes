import java.math.BigDecimal;

/**
 * 题目：Java中的基本数据类型有哪些
 *
 * 演示Java的8种基本数据类型及其使用
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
 * @see Question12_AutoBoxing 第12题：自动拆箱装箱
 * @see Question13_Inheritance 第13题：继承机制
 * @see Question14_AccessModifiers 第14题：访问修饰符
 * @see Question15_StaticInstanceMethod 第15题：静态方法和实例方法
 */
public class Question11_PrimitiveTypes {
    public static void main(String[] args) {
        System.out.println("=== Java 8种基本数据类型 ===\n");
        
        // 1. 整型
        System.out.println("整型（4种）：");
        byte byteVar = 127;
        short shortVar = 32767;
        int intVar = 2147483647;
        long longVar = 9223372036854775807L;
        
        System.out.println("  byte:   " + byteVar + " (1字节，范围：-128~127)");
        System.out.println("  short:  " + shortVar + " (2字节，范围：-32768~32767)");
        System.out.println("  int:    " + intVar + " (4字节，默认整型)");
        System.out.println("  long:   " + longVar + "L (8字节，大整数)");
        
        // 2. 浮点型
        System.out.println("\n浮点型（2种）：");
        float floatVar = 3.14159f;
        double doubleVar = 3.14159265358979;
        
        System.out.println("  float:  " + floatVar + "f (4字节，单精度)");
        System.out.println("  double: " + doubleVar + " (8字节，双精度，默认)");
        
        // 3. 字符型
        System.out.println("\n字符型（1种）：");
        char charVar = '中';
        char charA = 'A';
        
        System.out.println("  char: '" + charVar + "' (2字节，Unicode字符)");
        System.out.println("  char: '" + charA + "' (ASCII: " + (int)charA + ")");
        
        // 4. 布尔型
        System.out.println("\n布尔型（1种）：");
        boolean boolVar = true;
        
        System.out.println("  boolean: " + boolVar + " (逻辑值，true/false)");
        
        System.out.println("\n=== 类型大小和范围 ===\n");
        
        System.out.println("整型范围：");
        System.out.println("  byte:   " + Byte.MIN_VALUE + " ~ " + Byte.MAX_VALUE);
        System.out.println("  short:  " + Short.MIN_VALUE + " ~ " + Short.MAX_VALUE);
        System.out.println("  int:    " + Integer.MIN_VALUE + " ~ " + Integer.MAX_VALUE);
        System.out.println("  long:   " + Long.MIN_VALUE + " ~ " + Long.MAX_VALUE);
        
        System.out.println("\n浮点型范围：");
        System.out.println("  float:  " + Float.MIN_VALUE + " ~ " + Float.MAX_VALUE);
        System.out.println("  double: " + Double.MIN_VALUE + " ~ " + Double.MAX_VALUE);
        
        System.out.println("\n类型大小（字节）：");
        System.out.println("  byte:    " + Byte.BYTES + " 字节");
        System.out.println("  short:   " + Short.BYTES + " 字节");
        System.out.println("  int:     " + Integer.BYTES + " 字节");
        System.out.println("  long:    " + Long.BYTES + " 字节");
        System.out.println("  float:   " + Float.BYTES + " 字节");
        System.out.println("  double:  " + Double.BYTES + " 字节");
        System.out.println("  char:    " + Character.BYTES + " 字节");
        
        System.out.println("\n=== 常见陷阱演示 ===\n");
        
        // 陷阱1：整数溢出
        System.out.println("陷阱1：整数溢出");
        int max = Integer.MAX_VALUE;
        System.out.println("  int最大值：" + max);
        System.out.println("  max + 1 = " + (max + 1) + "（溢出）");
        
        // 正确方式
        long bigNum = (long)max + 1;
        System.out.println("  用long： " + bigNum);
        
        // 陷阱2：浮点数精度
        System.out.println("\n陷阱2：浮点数精度问题");
        double d1 = 0.1;
        double d2 = 0.2;
        System.out.println("  0.1 + 0.2 = " + (d1 + d2));
        System.out.println("  （预期0.3，实际有误差）");
        
        // 正确方式
        BigDecimal bd1 = new BigDecimal("0.1");
        BigDecimal bd2 = new BigDecimal("0.2");
        System.out.println("  BigDecimal: " + bd1.add(bd2) + "（精确）");
        
        // 陷阱3：类型提升
        System.out.println("\n陷阱3：byte运算自动提升为int");
        byte a = 10;
        byte b = 20;
        // byte c = a + b;  // 错误！
        byte c = (byte)(a + b);
        System.out.println("  byte运算需要强制转换：" + a + " + " + b + " = " + c);
        
        // 陷阱4：long字面量后缀
        System.out.println("\n陷阱4：long类型要加L后缀");
        System.out.println("  正确：long num = 2147483648L");
        System.out.println("  推荐：用大写L，避免和数字1混淆");
        
        // 陷阱5：char范围
        System.out.println("\n陷阱5：char是无符号的（0~65535）");
        System.out.println("  char最小值：" + (int)Character.MIN_VALUE);
        System.out.println("  char最大值：" + (int)Character.MAX_VALUE);
        
        System.out.println("\n=== 类型转换 ===\n");
        
        // 自动类型转换（小→大）
        System.out.println("自动类型转换（小→大）：");
        int i = 100;
        long l = i;      // int → long
        double d = l;    // long → double
        System.out.println("  int " + i + " → long " + l + " → double " + d);
        
        // 强制类型转换（大→小）
        System.out.println("\n强制类型转换（大→小）：");
        double pi = 3.14159;
        int intPi = (int)pi;
        System.out.println("  double " + pi + " → int " + intPi + "（截断小数）");
        
        long bigLong = 1000L;
        int intFromLong = (int)bigLong;
        System.out.println("  long " + bigLong + " → int " + intFromLong);
        
        System.out.println("\n=== 最佳实践 ===\n");
        
        System.out.println("1. 一般整数用int，大数用long");
        System.out.println("2. 小数默认用double，精确计算用BigDecimal");
        System.out.println("3. long类型加L后缀（大写）");
        System.out.println("4. float类型加f后缀");
        System.out.println("5. 注意整数溢出和浮点精度");
        System.out.println("6. 使用包装类常量：Integer.MAX_VALUE等");
        
        System.out.println("\n=== 基本类型 vs 包装类 ===\n");
        
        // 基本类型
        int primitive = 100;
        
        // 包装类（引用类型）
        Integer wrapper = Integer.valueOf(100);
        
        System.out.println("基本类型：直接存储值");
        System.out.println("  int i = " + primitive);
        System.out.println("  存储在栈中，效率高");
        
        System.out.println("\n包装类：存储对象引用");
        System.out.println("  Integer wrapper = " + wrapper);
        System.out.println("  存储在堆中，可以null");
        
        // 自动装箱/拆箱
        Integer autoBox = primitive;      // 自动装箱
        int autoUnbox = wrapper;           // 自动拆箱
        
        System.out.println("\n自动装箱/拆箱：");
        System.out.println("  Integer autoBox = " + autoBox);
        System.out.println("  int autoUnbox = " + autoUnbox);
    }
}
