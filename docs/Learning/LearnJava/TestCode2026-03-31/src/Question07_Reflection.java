import java.lang.reflect.*;

/**
 * 题目：关于Java的反射机制，如何应用反射
 *
 * 演示反射的核心用法
 *
 * 【题目跳转】
 * @see Question01_Class 第1题：Java中的类
 * @see Question02_Instantiation 第2题：实例化
 * @see Question03_Static 第3题：static关键字
 * @see Question04_InterfaceAbstract 第4题：接口和抽象类
 * @see Question05_JDKJRE 第5题：JDK和JRE
 * @see Question06_JDKTools 第6题：JDK工具
 * @see Question08_ThreadStart 第8题：线程start()方法
 * @see Question09_Optional 第9题：Optional类
 * @see Question10_IOStream 第10题：I/O流
 * @see Question11_PrimitiveTypes 第11题：基本数据类型
 * @see Question12_AutoBoxing 第12题：自动拆箱装箱
 * @see Question13_Inheritance 第13题：继承机制
 * @see Question14_AccessModifiers 第14题：访问修饰符
 * @see Question15_StaticInstanceMethod 第15题：静态方法和实例方法
 */
public class Question07_Reflection {
    public static void main(String[] args) throws Exception {
        System.out.println("=== 三种获取Class对象的方式 ===\n");
        
        // 方式1：类名.class
        Class<Student07> clazz1 = Student07.class;
        System.out.println("方式1 - 类名.class: " + clazz1.getName());
        
        // 方式2：对象实例.getClass()
        Student07 student = new Student07("测试", 20);
        Class<?> clazz2 = student.getClass();
        System.out.println("方式2 - 对象.getClass(): " + clazz2.getName());
        
        // 方式3：Class.forName()
        Class<?> clazz3 = Class.forName("Student07");
        System.out.println("方式3 - Class.forName(): " + clazz3.getName());
        
        System.out.println("\n三种方式获取的是同一个Class对象: " + (clazz1 == clazz2 && clazz2 == clazz3));
        
        System.out.println("\n=== 获取类的基本信息 ===\n");
        Class<?> clazz = Student07.class;
        System.out.println("全限定名: " + clazz.getName());
        System.out.println("简单名称: " + clazz.getSimpleName());
        System.out.println("包名: " + clazz.getPackage().getName());
        System.out.println("修饰符: " + Modifier.toString(clazz.getModifiers()));
        
        System.out.println("\n=== 获取构造函数并创建实例 ===\n");
        Constructor<?> constructor = clazz.getConstructor(String.class, int.class);
        Object instance = constructor.newInstance("张三", 25);
        System.out.println("创建实例: " + instance);
        
        System.out.println("\n=== 获取字段并操作 ===\n");
        
        // 获取public字段
        Field ageField = clazz.getField("age");
        System.out.println("年龄字段值: " + ageField.get(instance));
        ageField.set(instance, 30);
        System.out.println("修改后年龄: " + ageField.get(instance));
        
        // 获取private字段（需要setAccessible）
        Field nameField = clazz.getDeclaredField("name");
        nameField.setAccessible(true);  // 突破private限制
        System.out.println("姓名字段值: " + nameField.get(instance));
        nameField.set(instance, "李四");
        System.out.println("修改后姓名: " + nameField.get(instance));
        
        System.out.println("\n=== 获取方法并调用 ===\n");
        
        // 获取public方法
        Method studyMethod = clazz.getMethod("study", String.class);
        studyMethod.invoke(instance, "数学");
        
        // 获取private方法
        Method sleepMethod = clazz.getDeclaredMethod("sleep");
        sleepMethod.setAccessible(true);
        sleepMethod.invoke(instance);
        
        // 获取静态方法
        Method showCountMethod = clazz.getMethod("showCount");
        showCountMethod.invoke(null);  // 静态方法传null
        
        System.out.println("\n=== 获取所有字段、方法、构造函数 ===\n");
        
        System.out.println("所有public字段：");
        for (Field field : clazz.getFields()) {
            System.out.println("  " + field.getName() + " - " + field.getType().getSimpleName());
        }
        
        System.out.println("\n所有声明字段（包括private）：");
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println("  " + field.getName() + " - " + field.getType().getSimpleName());
        }
        
        System.out.println("\n所有public方法：");
        for (Method method : clazz.getMethods()) {
            if (method.getDeclaringClass() != Object.class) {
                System.out.println("  " + method.getName());
            }
        }
        
        System.out.println("\n所有构造函数：");
        for (Constructor<?> cons : clazz.getConstructors()) {
            System.out.println("  " + cons);
        }
        
        System.out.println("\n=== 反射的实际应用 ===\n");
        System.out.println("1. 框架依赖注入（Spring IOC）");
        System.out.println("2. 动态代理（AOP）");
        System.out.println("3. 序列化/反序列化（JSON、XML）");
        System.out.println("4. 注解处理（ButterKnife、Retrofit）");
        System.out.println("5. 数据库ORM框架（MyBatis、Hibernate）");
    }
}

/**
 * 学生类：用于演示反射
 */
class Student07 {
    private String name;
    public int age;
    public static int count = 0;
    
    public Student07() {
        count++;
    }
    
    public Student07(String name, int age) {
        this.name = name;
        this.age = age;
        count++;
    }
    
    public void study(String subject) {
        System.out.println(name + "正在学习：" + subject);
    }
    
    private void sleep() {
        System.out.println(name + "正在睡觉");
    }
    
    public static void showCount() {
        System.out.println("学生总数：" + count);
    }
    
    @Override
    public String toString() {
        return "Student07{name='" + name + "', age=" + age + "}";
    }
}
