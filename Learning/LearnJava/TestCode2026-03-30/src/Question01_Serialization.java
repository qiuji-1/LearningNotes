/**
 * 第1题：什么是Java中的序列化和反序列化
 * 
 * 【核心概念】
 * 序列化（Serialization）：把内存中的Java对象转换为字节流（二进制数据）
 * 反序列化（Deserialization）：把字节流恢复成Java对象
 * 
 * 【为什么要序列化？】
 * 1. 持久化存储：把对象保存到硬盘文件
 * 2. 网络传输：把对象通过网络发送到其他机器
 * 3. 缓存：把对象缓存到Redis等中间件
 * 
 * 【关键点】
 * - 必须实现 Serializable 接口（这是一个标记接口，没有方法）
 * - transient 关键字：修饰的字段不会被序列化
 * - serialVersionUID：版本号，用于反序列化时版本检查
 * 
 * 【题目跳转】
 * @see Question02_JavaAdvantages 第2题：Java优势
 * @see Question03_MultipleInheritance 第3题：多重继承
 * @see Question04_MethodOverloadOverride 第4题：方法重载重写
 * @see Question05_Java25Features 第5题：Java 25新特性
 * @see Question06_Java11Features 第6题：Java 11新特性
 * @see Question07_Java17Features 第7题：Java 17新特性
 * @see Question08_Java21Features 第8题：Java 21新特性
 * 
 * 运行要求：Java 8+
 */

// ===== 导入说明 =====
// java.io.Serializable: 序列化接口，实现此接口的类可以被序列化
// java.io.ObjectOutputStream: 对象输出流，用于将对象序列化到文件
// java.io.ObjectInputStream: 对象输入流，用于从文件反序列化对象
// java.io.*: 包含所有I/O相关的类
import java.io.*;

/**
 * 主类：序列化和反序列化演示
 */
public class Question01_Serialization {
    
    /**
     * main方法：程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        System.out.println("========== 第1题：序列化和反序列化 ==========\n");
        
        // 测试1：基本序列化/反序列化
        testBasicSerialization();
        
        // 测试2：transient关键字
        testTransientKeyword();
        
        // 测试3：serialVersionUID
        testSerialVersionUID();
    }
    
    /**
     * 测试1：基本序列化/反序列化
     * 
     * 【步骤】
     * 1. 创建对象
     * 2. 使用 ObjectOutputStream 序列化到文件
     * 3. 使用 ObjectInputStream 从文件反序列化
     * 4. 比较原始对象和反序列化对象
     */
    private static void testBasicSerialization() {
        System.out.println("【测试1：基本序列化/反序列化】");
        
        // ===== 步骤1：创建User对象 =====
        User originalUser = new User("张三", 25, "zhangsan@example.com");
        System.out.println("原始对象：" + originalUser);
        
        // ===== 步骤2：序列化到文件 =====
        String filename = "user.ser";  // .ser 是序列化文件的常见后缀
        try {
            // ObjectOutputStream：对象输出流，用于序列化
            // FileOutputStream：文件输出流，用于写入文件
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            
            // writeObject()：将对象写入输出流
            oos.writeObject(originalUser);
            
            // 关闭流（重要！避免资源泄露）
            oos.close();
            System.out.println("✓ 序列化成功：" + filename);
            
            // ===== 步骤3：反序列化 =====
            // ObjectInputStream：对象输入流，用于反序列化
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            
            // readObject()：从输入流读取对象（返回Object类型，需要强制转换）
            User deserializedUser = (User) ois.readObject();
            
            // 关闭流
            ois.close();
            System.out.println("✓ 反序列化成功：" + deserializedUser);
            
            // ===== 步骤4：验证对象相等 =====
            // 注意：equals()方法需要重写，否则比较的是对象地址
            System.out.println("对象是否相等：" + originalUser.equals(deserializedUser));
            
        } catch (IOException e) {
            // IOException：输入输出异常（文件不存在、读写错误等）
            System.out.println("✗ I/O错误：" + e.getMessage());
        } catch (ClassNotFoundException e) {
            // ClassNotFoundException：类找不到异常（反序列化时类不存在）
            System.out.println("✗ 类找不到：" + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * 测试2：transient关键字
     * 
     * 【transient的作用】
     * - 修饰的字段不会被序列化
     * - 常用于敏感信息（密码、密钥等）
     * - 反序列化后，transient字段的值为默认值（引用类型为null，基本类型为0/false）
     */
    private static void testTransientKeyword() {
        System.out.println("【测试2：transient关键字】");
        
        // 创建带有密码的用户
        UserWithPassword user = new UserWithPassword("李四", 30, "lisi@example.com", "123456");
        System.out.println("原始对象：" + user);
        
        String filename = "user_with_password.ser";
        try {
            // 序列化
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(user);
            oos.close();
            System.out.println("✓ 序列化成功");
            
            // 反序列化
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            UserWithPassword deserializedUser = (UserWithPassword) ois.readObject();
            ois.close();
            System.out.println("✓ 反序列化成功：" + deserializedUser);
            
            // 注意：password字段为null（因为transient）
            // transient字段反序列化后是默认值：null（引用类型）、0（数值类型）、false（布尔类型）
            System.out.println("密码是否被序列化：" + (deserializedUser.getPassword() != null));
            // 输出：false，说明密码没有被序列化
            
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("✗ 错误：" + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * 测试3：serialVersionUID
     * 
     * 【serialVersionUID的作用】
     * - 版本控制：类版本号
     * - 反序列化时，JVM会比较字节流中的serialVersionUID和类的serialVersionUID
     * - 如果不一致，抛出 InvalidClassException
     * 
     * 【为什么要显式定义？】
     * - 如果不定义，JVM会自动生成（根据类结构）
     * - 类结构改变后，自动生成的serialVersionUID会变化
     * - 导致旧版本序列化的数据无法反序列化
     * 
     * 【最佳实践】
     * - 显式定义 serialVersionUID
     * - 类修改后，如果兼容旧数据，不要改变serialVersionUID
     */
    private static void testSerialVersionUID() {
        System.out.println("【测试3：serialVersionUID的作用】");
        System.out.println("serialVersionUID用于版本控制，如果类修改后serialVersionUID不同，反序列化会失败");
        System.out.println();
        System.out.println("建议：显式定义serialVersionUID，如：");
        System.out.println("  private static final long serialVersionUID = 1L;");
        System.out.println();
        System.out.println("示例：");
        System.out.println("  public class User implements Serializable {");
        System.out.println("      private static final long serialVersionUID = 1L;");
        System.out.println("      // ...");
        System.out.println("  }");
        System.out.println();
    }
}

/**
 * 基本用户类（用于序列化测试）
 * 
 * 【类说明】
 * - 实现 Serializable 接口：表明此类可以被序列化
 * - serialVersionUID：版本号，建议显式定义
 */
class User implements Serializable {
    // 版本号：用于反序列化时的版本检查
    private static final long serialVersionUID = 1L;
    
    // 成员变量：这些字段会被序列化
    private String name;   // 姓名
    private int age;       // 年龄
    private String email;  // 邮箱
    
    /**
     * 构造方法：创建User对象
     * @param name 姓名
     * @param age 年龄
     * @param email 邮箱
     */
    public User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
    
    /**
     * toString方法：返回对象的字符串表示
     * @return 对象的字符串表示
     */
    @Override
    public String toString() {
        return String.format("User{name='%s', age=%d, email='%s'}", name, age, email);
    }
    
    /**
     * equals方法：判断两个对象是否相等
     * @param obj 要比较的对象
     * @return true表示相等，false表示不相等
     */
    @Override
    public boolean equals(Object obj) {
        // 如果是同一个对象，返回true
        if (this == obj) return true;
        
        // 如果obj为null或类型不匹配，返回false
        if (obj == null || getClass() != obj.getClass()) return false;
        
        // 类型转换
        User user = (User) obj;
        
        // 比较所有字段
        return age == user.age && 
               name.equals(user.name) && 
               email.equals(user.email);
    }
}

/**
 * 带有密码的用户类（transient关键字测试）
 * 
 * 【类说明】
 * - 演示 transient 关键字的作用
 * - transient修饰的字段不会被序列化
 */
class UserWithPassword implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private int age;
    private String email;
    
    // transient：此字段不会被序列化
    // 常用于敏感信息（密码、密钥等）
    transient private String password;
    
    public UserWithPassword(String name, int age, String email, String password) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }
    
    /**
     * 获取密码
     * @return 密码（反序列化后为null）
     */
    public String getPassword() {
        return password;
    }
    
    @Override
    public String toString() {
        return String.format("UserWithPassword{name='%s', age=%d, email='%s', password='%s'}", 
                            name, age, email, password);
    }
}
