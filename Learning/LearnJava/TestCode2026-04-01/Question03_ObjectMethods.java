import java.util.*;

/**
 * Question03: Java Object 类中有什么方法，有什么作用
 * 
 * 本文件演示了 Object 类中各个方法的使用
 */
public class Question03_ObjectMethods {
    
    public static void main(String[] args) {
        System.out.println("===== Java Object 类方法演示 =====\n");
        
        testEquals();
        testHashCode();
        testToString();
        testClone();
        testGetClass();
    }
    
    /**
     * 测试1：equals() 方法
     */
    private static void testEquals() {
        System.out.println("【测试1】equals() 方法");
        System.out.println("----------------------------------------");
        
        // 默认 equals：比较地址
        Object obj1 = new Object();
        Object obj2 = new Object();
        System.out.println("1. 默认 equals（比较地址）：");
        System.out.println("   obj1.equals(obj2): " + obj1.equals(obj2));
        
        // 重写 equals：比较内容
        Person p1 = new Person("张三", 25);
        Person p2 = new Person("张三", 25);
        Person p3 = new Person("李四", 30);
        
        System.out.println("\n2. 重写 equals（比较内容）：");
        System.out.println("   p1.equals(p2): " + p1.equals(p2));  // true
        System.out.println("   p1.equals(p3): " + p1.equals(p3));  // false
        
        // equals 契约
        System.out.println("\n3. equals 契约验证：");
        System.out.println("   自反性 p1.equals(p1): " + p1.equals(p1));  // true
        System.out.println("   对称性 p1.equals(p2) == p2.equals(p1): " + 
                          (p1.equals(p2) == p2.equals(p1)));  // true
        System.out.println("   非空性 p1.equals(null): " + p1.equals(null));  // false
        
        System.out.println();
    }
    
    /**
     * 测试2：hashCode() 方法
     */
    private static void testHashCode() {
        System.out.println("【测试2】hashCode() 方法");
        System.out.println("----------------------------------------");
        
        Person p1 = new Person("张三", 25);
        Person p2 = new Person("张三", 25);
        Person p3 = new Person("李四", 30);
        
        System.out.println("1. 相等对象必须有相同的 hashCode：");
        System.out.println("   p1.equals(p2): " + p1.equals(p2));
        System.out.println("   p1.hashCode(): " + p1.hashCode());
        System.out.println("   p2.hashCode(): " + p2.hashCode());
        System.out.println("   p1.hashCode() == p2.hashCode(): " + 
                          (p1.hashCode() == p2.hashCode()));
        
        System.out.println("\n2. 不相等对象可能有相同 hashCode（哈希冲突）：");
        System.out.println("   p1.hashCode(): " + p1.hashCode());
        System.out.println("   p3.hashCode(): " + p3.hashCode());
        
        System.out.println("\n3. 在 HashMap 中的使用：");
        Map<Person, String> map = new HashMap<>();
        Person key1 = new Person("001", 20);
        Person key2 = new Person("001", 20);
        map.put(key1, "张三");
        System.out.println("   map.containsKey(key2): " + map.containsKey(key2));
        System.out.println("   map.get(key2): " + map.get(key2));
        
        System.out.println();
    }
    
    /**
     * 测试3：toString() 方法
     */
    private static void testToString() {
        System.out.println("【测试3】toString() 方法");
        System.out.println("----------------------------------------");
        
        // 默认 toString
        Object obj = new Object();
        System.out.println("1. 默认 toString（地址）：");
        System.out.println("   obj.toString(): " + obj.toString());
        System.out.println("   直接输出: " + obj);
        
        // 重写 toString
        Person person = new Person("张三", 25);
        System.out.println("\n2. 重写 toString（内容）：");
        System.out.println("   person.toString(): " + person.toString());
        System.out.println("   直接输出: " + person);
        
        // 字符串拼接
        System.out.println("\n3. 字符串拼接：");
        String msg = "用户信息：" + person;
        System.out.println("   " + msg);
        
        System.out.println();
    }
    
    /**
     * 测试4：clone() 方法
     */
    private static void testClone() {
        System.out.println("【测试4】clone() 方法");
        System.out.println("----------------------------------------");
        
        // 浅拷贝
        System.out.println("1. 浅拷贝：");
        ShallowCopyExample original1 = new ShallowCopyExample(1, "张三", new int[]{90, 85, 95});
        ShallowCopyExample copy1 = original1.clone();
        
        System.out.println("   原对象: " + original1);
        System.out.println("   拷贝对象: " + copy1);
        System.out.println("   original1 == copy1: " + (original1 == copy1));  // false
        
        // 修改拷贝对象的数组
        copy1.getScores()[0] = 100;
        System.out.println("   修改拷贝对象的数组后：");
        System.out.println("   原对象数组: " + Arrays.toString(original1.getScores()));  // 受影响
        System.out.println("   拷贝对象数组: " + Arrays.toString(copy1.getScores()));
        
        // 深拷贝
        System.out.println("\n2. 深拷贝：");
        DeepCopyExample original2 = new DeepCopyExample(1, "张三", new int[]{90, 85, 95});
        DeepCopyExample copy2 = original2.clone();
        
        System.out.println("   原对象: " + original2);
        System.out.println("   拷贝对象: " + copy2);
        
        // 修改拷贝对象的数组
        copy2.getScores()[0] = 100;
        System.out.println("   修改拷贝对象的数组后：");
        System.out.println("   原对象数组: " + Arrays.toString(original2.getScores()));  // 不受影响
        System.out.println("   拷贝对象数组: " + Arrays.toString(copy2.getScores()));
        
        System.out.println();
    }
    
    /**
     * 测试5：getClass() 方法
     */
    private static void testGetClass() {
        System.out.println("【测试5】getClass() 方法");
        System.out.println("----------------------------------------");
        
        Object obj = new String("Hello");
        Person person = new Person("张三", 25);
        
        System.out.println("1. 获取类信息：");
        System.out.println("   obj.getClass().getName(): " + obj.getClass().getName());
        System.out.println("   obj.getClass().getSimpleName(): " + obj.getClass().getSimpleName());
        
        System.out.println("\n2. 反射应用：");
        try {
            // 通过类名获取 Class 对象
            Class<?> strClass = Class.forName("java.lang.String");
            System.out.println("   Class.forName('java.lang.String'): " + strClass.getName());
            
            // 获取方法并调用
            java.lang.reflect.Method method = strClass.getMethod("length");
            String str = "Hello World";
            int length = (int) method.invoke(str);
            System.out.println("   字符串长度: " + length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("\n3. instanceof vs getClass()：");
        System.out.println("   person instanceof Person: " + (person instanceof Person));
        System.out.println("   person.getClass() == Person.class: " + 
                          (person.getClass() == Person.class));
        
        System.out.println();
    }
}

/**
 * Person 类：演示 equals 和 hashCode
 */
class Person {
    private String name;
    private int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return age == person.age && Objects.equals(name, person.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
    
    @Override
    public String toString() {
        return new StringBuilder()
            .append("Person{")
            .append("name='").append(name).append('\'')
            .append(", age=").append(age)
            .append('}')
            .toString();
    }
}

/**
 * 浅拷贝示例
 */
class ShallowCopyExample implements Cloneable {
    private int id;
    private String name;
    private int[] scores;
    
    public ShallowCopyExample(int id, String name, int[] scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }
    
    public int[] getScores() {
        return scores;
    }
    
    @Override
    protected ShallowCopyExample clone() {
        try {
            return (ShallowCopyExample) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    
    @Override
    public String toString() {
        return "ShallowCopyExample{id=" + id + ", name='" + name + "', scores=" + Arrays.toString(scores) + "}";
    }
}

/**
 * 深拷贝示例
 */
class DeepCopyExample implements Cloneable {
    private int id;
    private String name;
    private int[] scores;
    
    public DeepCopyExample(int id, String name, int[] scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }
    
    public int[] getScores() {
        return scores;
    }
    
    @Override
    protected DeepCopyExample clone() {
        try {
            DeepCopyExample copy = (DeepCopyExample) super.clone();
            copy.scores = scores.clone();  // 深拷贝数组
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    
    @Override
    public String toString() {
        return "DeepCopyExample{id=" + id + ", name='" + name + "', scores=" + Arrays.toString(scores) + "}";
    }
}
