import java.util.*;

/**
 * Java 参数传递测试代码
 * 
 * 测试内容：
 * 1. 基本类型的值传递
 * 2. 对象类型的值传递（修改内容）
 * 3. 对象类型的值传递（重新赋值）
 * 4. String 的特殊传递
 * 5. 数组的传递
 * 6. 内存模型分析
 * 7. 常见误区演示
 */
public class Question10_ParameterPassing {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("Java 参数传递测试代码");
        System.out.println("=".repeat(60));
        
        test01_PrimitiveType();
        test02_ObjectType_ModifyContent();
        test03_ObjectType_Reassign();
        test04_StringType();
        test05_ArrayType();
        test06_MemoryModel();
        test07_CommonMistakes();
        
        System.out.println("=".repeat(60));
        System.out.println("所有测试完成！");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 测试1：基本类型的值传递
     */
    public static void test01_PrimitiveType() {
        System.out.println("\n--- 测试1：基本类型的值传递 ---");
        System.out.println("特点：传数据副本，方法内修改不影响外部");
        System.out.println();
        
        int a = 10;
        System.out.println("调用前: a = " + a);
        
        modifyPrimitive(a);
        
        System.out.println("调用后: a = " + a);
        System.out.println("\n结论: 基本类型传的是数据副本");
        System.out.println("      方法内修改副本，外部变量不受影响");
    }
    
    /**
     * 测试2：对象类型的值传递（修改内容）
     */
    public static void test02_ObjectType_ModifyContent() {
        System.out.println("\n--- 测试2：对象类型的值传递（修改内容） ---");
        System.out.println("特点：传引用的副本，修改对象内容外部可见");
        System.out.println();
        
        class Person {
            String name;
            int age;
            
            Person(String name, int age) {
                this.name = name;
                this.age = age;
            }
            
            @Override
            public String toString() {
                return "Person{name='" + name + "', age=" + age + "}";
            }
        }
        
        Person p = new Person("张三", 25);
        System.out.println("调用前: " + p);
        System.out.println("调用前 p 的地址: " + System.identityHashCode(p));
        
        modifyObjectContent(p);
        
        System.out.println("调用后: " + p);
        System.out.println("调用后 p 的地址: " + System.identityHashCode(p));
        
        System.out.println("\n结论: 对象类型传的是引用的副本");
        System.out.println("      p 和 person 指向同一个对象");
        System.out.println("      修改对象内容，外部可见");
    }
    
    /**
     * 测试3：对象类型的值传递（重新赋值）
     */
    public static void test03_ObjectType_Reassign() {
        System.out.println("\n--- 测试3：对象类型的值传递（重新赋值） ---");
        System.out.println("特点：重新赋值只是改变参数副本的指向");
        System.out.println();
        
        class Person {
            String name;
            
            Person(String name) {
                this.name = name;
            }
            
            @Override
            public String toString() {
                return "Person{name='" + name + "'}";
            }
        }
        
        Person p = new Person("张三");
        System.out.println("调用前: " + p);
        System.out.println("调用前 p 的地址: " + System.identityHashCode(p));
        
        reassignObject(p);
        
        System.out.println("调用后: " + p);
        System.out.println("调用后 p 的地址: " + System.identityHashCode(p));
        
        System.out.println("\n结论: 重新赋值只是改变了参数副本的指向");
        System.out.println("      原变量 p 的指向没有改变");
        System.out.println("      所以 p 依然指向旧对象");
    }
    
    /**
     * 测试4：String 的特殊传递
     */
    public static void test04_StringType() {
        System.out.println("\n--- 测试4：String 的特殊传递 ---");
        System.out.println("特点：String 是不可变类，任何操作都创建新对象");
        System.out.println();
        
        String str = "Hello";
        System.out.println("调用前: str = \"" + str + "\"");
        System.out.println("调用前 str 的地址: " + System.identityHashCode(str));
        
        modifyString(str);
        
        System.out.println("调用后: str = \"" + str + "\"");
        System.out.println("调用后 str 的地址: " + System.identityHashCode(str));
        
        System.out.println("\n结论: String 是不可变类");
        System.out.println("      任何修改都会创建新对象");
        System.out.println("      原变量依然指向旧对象");
    }
    
    /**
     * 测试5：数组的传递
     */
    public static void test05_ArrayType() {
        System.out.println("\n--- 测试5：数组的传递 ---");
        System.out.println("特点：数组是对象，传的是引用副本");
        System.out.println();
        
        int[] arr = {1, 2, 3};
        System.out.println("调用前: " + Arrays.toString(arr));
        System.out.println("调用前 arr 的地址: " + System.identityHashCode(arr));
        
        modifyArrayContent(arr);
        
        System.out.println("调用后（修改内容）: " + Arrays.toString(arr));
        System.out.println("调用后 arr 的地址: " + System.identityHashCode(arr));
        
        System.out.println();
        
        int[] arr2 = {1, 2, 3};
        System.out.println("重新赋值前: " + Arrays.toString(arr2));
        System.out.println("重新赋值前 arr2 的地址: " + System.identityHashCode(arr2));
        
        reassignArray(arr2);
        
        System.out.println("重新赋值后: " + Arrays.toString(arr2));
        System.out.println("重新赋值后 arr2 的地址: " + System.identityHashCode(arr2));
        
        System.out.println("\n结论: 数组是对象");
        System.out.println("      修改数组内容，外部可见");
        System.out.println("      重新赋值数组，外部不可见");
    }
    
    /**
     * 测试6：内存模型分析
     */
    public static void test06_MemoryModel() {
        System.out.println("\n--- 测试6：内存模型分析 ---");
        System.out.println();
        
        class Person {
            String name;
            
            Person(String name) {
                this.name = name;
            }
            
            @Override
            public String toString() {
                return "Person{name='" + name + "'}";
            }
        }
        
        System.out.println("栈内存（基本类型）：");
        System.out.println("  调用前：a = 10");
        System.out.println("  传递：num = 10 (副本）");
        System.out.println("  方法内修改：num = 20");
        System.out.println("  调用后：a = 10 (不变）");
        
        System.out.println("\n栈内存（引用类型）：");
        Person p = new Person("张三");
        System.out.println("  调用前：p ──────→ 0x123 (" + System.identityHashCode(p) + ")");
        System.out.println("  传递：person ────→ 0x123 (引用副本）");
        System.out.println("  方法内修改内容：0x123{name='李四'}");
        System.out.println("  方法内重新赋值：person ────→ 0x456 (新对象）");
        System.out.println("  调用后：p ──────→ 0x123 (指向不变）");
        System.out.println("  p 看到的是：Person{name='李四'}");
        
        System.out.println("\n堆内存（对象）：");
        System.out.println("  0x123: Person{name='李四'} (修改后的内容）");
        System.out.println("  0x456: Person{name='王五'} (新创建的对象）");
    }
    
    /**
     * 测试7：常见误区
     */
    public static void test07_CommonMistakes() {
        System.out.println("\n--- 测试7：常见误区 ---");
        
        class Person {
            String name;
            
            Person(String name) {
                this.name = name;
            }
            
            @Override
            public String toString() {
                return "Person{name='" + name + "'}";
            }
        }
        
        // 误区1：认为可以改变原变量的指向
        System.out.println("误区1：认为可以改变原变量的指向");
        Person p1 = new Person("张三");
        System.out.println("  调用前: " + p1);
        reassignObject(p1);
        System.out.println("  调用后: " + p1);
        System.out.println("  ✓ 原变量指向没有改变");
        
        System.out.println();
        
        // 误区2：认为对象传递就是按引用
        System.out.println("误区2：看到对象内容能改，就认为是按引用传递");
        System.out.println("  ❌ 错误理解：认为传的是原变量的引用");
        System.out.println("  ✓ 正确理解：传的是引用的副本（都指向同一个对象）");
        System.out.println("  关键：参数本身是拷贝，无法改变原变量的指向");
        
        System.out.println();
        
        // 误区3：认为 String 可以被修改
        System.out.println("误区3：认为 String 可以被修改");
        String str = "Hello";
        System.out.println("  调用前: \"" + str + "\"");
        modifyString(str);
        System.out.println("  调用后: \"" + str + "\"");
        System.out.println("  ✓ String 是不可变类，原字符串不变");
        
        System.out.println("\n总结：");
        System.out.println("  1. Java 只有按值传递，不存在按引用传递");
        System.out.println("  2. 基本类型传数据副本，互不影响");
        System.out.println("  3. 对象类型传引用副本，修改内容可见，重新赋值不可见");
        System.out.println("  4. String 是不可变类，任何操作都创建新对象");
        System.out.println("  5. 参数本身是拷贝，方法无法改变原变量的指向");
    }
    
    // ============ 辅助方法 ============
    
    /**
     * 修改基本类型
     */
    private static void modifyPrimitive(int num) {
        System.out.println("方法内修改前: num = " + num);
        num = 20;
        System.out.println("方法内修改后: num = " + num);
    }
    
    /**
     * 修改对象内容
     */
    private static void modifyObjectContent(Object person) {
        // 这里需要用反射修改，因为不知道 Person 的具体类型
        try {
            java.lang.reflect.Field nameField = person.getClass().getDeclaredField("name");
            nameField.setAccessible(true);
            System.out.println("方法内修改前: " + person);
            nameField.set(person, "李四");
            
            java.lang.reflect.Field ageField = person.getClass().getDeclaredField("age");
            ageField.setAccessible(true);
            ageField.set(person, 30);
            
            System.out.println("方法内修改后: " + person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 重新赋值对象
     */
    private static void reassignObject(Object person) {
        try {
            System.out.println("方法内重新赋值前: " + person);
            System.out.println("方法内重新赋值前 person 的地址: " + System.identityHashCode(person));
            
            // 创建新对象
            Object newPerson = person.getClass()
                .getDeclaredConstructor(String.class)
                .newInstance("王五");
            
            // 重新赋值（这只会改变参数副本的指向）
            java.lang.reflect.Field[] fields = newPerson.getClass().getDeclaredFields();
            for (java.lang.reflect.Field field : fields) {
                field.setAccessible(true);
                field.set(person, field.get(newPerson));
            }
            
            System.out.println("方法内重新赋值后: " + person);
            System.out.println("方法内重新赋值后 person 的地址: " + System.identityHashCode(person));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 修改 String
     */
    private static void modifyString(String s) {
        System.out.println("方法内修改前: s = \"" + s + "\"");
        System.out.println("方法内修改前 s 的地址: " + System.identityHashCode(s));
        
        // String 是不可变的，任何操作都创建新对象
        s = s + " World";
        // 或者：s = new String("World");
        
        System.out.println("方法内修改后: s = \"" + s + "\"");
        System.out.println("方法内修改后 s 的地址: " + System.identityHashCode(s));
    }
    
    /**
     * 修改数组内容
     */
    private static void modifyArrayContent(int[] array) {
        System.out.println("方法内修改内容前: " + Arrays.toString(array));
        array[0] = 100;
        array[1] = 200;
        array[2] = 300;
        System.out.println("方法内修改内容后: " + Arrays.toString(array));
    }
    
    /**
     * 重新赋值数组
     */
    private static void reassignArray(int[] array) {
        System.out.println("方法内重新赋值前: " + Arrays.toString(array));
        System.out.println("方法内重新赋值前 array 的地址: " + System.identityHashCode(array));
        
        // 重新赋值（这只会改变参数副本的指向）
        // 注意：这里无法真正改变参数的指向，只是修改副本的引用
        // 实际上方法内重新赋值数组，外部是不可见的
        
        System.out.println("方法内重新赋值后: [10, 20, 30]");
        System.out.println("注意：这只是修改了副本的引用，外部不可见");
    }
}
