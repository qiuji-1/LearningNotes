import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Java Exception 和 Error 区别测试代码
 * 
 * 测试内容：
 * 1. Exception（异常）- 可恢复的异常情况
 * 2. Error（错误）- JVM 严重问题
 * 3. Checked Exception（编译期异常）
 * 4. Unchecked Exception（运行时异常）
 * 5. 实际开发中的异常处理
 * 6. 常见误区演示
 */
public class Question08_ExceptionError {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("Java Exception 和 Error 区别测试代码");
        System.out.println("=".repeat(60));
        
        test01_ExceptionBasics();
        test02_ErrorBasics();
        test03_CheckedUnchecked();
        test04_CheckedHandling();
        test05_UncheckedHandling();
        test06_RealWorldScenario();
        test07_CommonMistakes();
        
        System.out.println("=".repeat(60));
        System.out.println("所有测试完成！");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 测试1：Exception（异常）基础
     */
    public static void test01_ExceptionBasics() {
        System.out.println("\n--- 测试1：Exception（异常） ---");
        System.out.println("定义：程序能预见并处理的异常情况");
        System.out.println("特点：可以恢复，使用 try-catch 捕获");
        System.out.println();
        
        // 常见 Exception 示例
        System.out.println("常见 Exception：");
        System.out.println("  1. FileNotFoundException - 文件没找到");
        System.out.println("  2. IOException - 输入输出异常");
        System.out.println("  3. SocketTimeoutException - 网络超时");
        System.out.println("  4. SQLException - 数据库异常");
        System.out.println();
        
        // 示例：try-catch 捕获异常
        try {
            System.out.println("尝试读取不存在的文件...");
            FileReader reader = new FileReader("non_existent_file.txt");
        } catch (FileNotFoundException e) {
            System.out.println("✓ 捕获到异常: FileNotFoundException");
            System.out.println("  处理方式: 使用默认配置");
        }
        
        System.out.println("\n说明: Exception 可以通过代码逻辑恢复");
        System.out.println("      如使用默认配置、重试、降级等");
    }
    
    /**
     * 测试2：Error（错误）基础
     */
    public static void test02_ErrorBasics() {
        System.out.println("\n--- 测试2：Error（错误） ---");
        System.out.println("定义：JVM 自身出了严重问题");
        System.out.println("特点：不可恢复，一般会导致应用崩溃");
        System.out.println();
        
        // 常见 Error
        System.out.println("常见 Error：");
        System.out.println("  1. OutOfMemoryError - 内存溢出");
        System.out.println("  2. StackOverflowError - 栈溢出");
        System.out.println("  3. NoClassDefFoundError - 类加载失败");
        System.out.println("  4. VirtualMachineError - 虚拟机错误");
        System.out.println();
        
        System.out.println("说明: Error 表示 JVM 严重问题");
        System.out.println("      程序本身搞不定，即使捕获也很难恢复");
        System.out.println("      最佳做法: 快速失败，让监控告警");
    }
    
    /**
     * 测试3：Checked vs Unchecked
     */
    public static void test03_CheckedUnchecked() {
        System.out.println("\n--- 测试3：Checked vs Unchecked ---");
        System.out.println("Checked Exception: 编译期异常，编译器强制处理");
        System.out.println("Unchecked Exception: 运行时异常，编译器不强制处理");
        System.out.println();
        
        System.out.println("Checked Exception 示例：");
        System.out.println("  - FileNotFoundException");
        System.out.println("  - IOException");
        System.out.println("  - SQLException");
        System.out.println("  - ClassNotFoundException");
        System.out.println();
        
        System.out.println("Unchecked Exception 示例：");
        System.out.println("  - NullPointerException");
        System.out.println("  - IndexOutOfBoundsException");
        System.out.println("  - ArithmeticException");
        System.out.println("  - ClassCastException");
        System.out.println();
        
        System.out.println("说明: Error 和 RuntimeException 都属于 Unchecked");
        System.out.println("      编译器不强制你处理");
    }
    
    /**
     * 测试4：Checked Exception 的处理
     */
    public static void test04_CheckedHandling() {
        System.out.println("\n--- 测试4：Checked Exception 处理 ---");
        System.out.println("Checked Exception 必须显式处理");
        System.out.println();
        
        // 方法1：try-catch 捕获
        System.out.println("方式1：try-catch 捕获");
        try {
            readFile("non_existent.txt");
        } catch (FileNotFoundException e) {
            System.out.println("  ✓ 捕获到 FileNotFoundException");
            System.out.println("    处理: 使用默认配置");
        } catch (IOException e) {
            System.out.println("  ✓ 捕获到 IOException");
            System.out.println("    处理: 重试");
        }
        
        System.out.println();
        
        // 方法2：throws 声明
        System.out.println("方式2：throws 声明（在方法签名上）");
        try {
            methodWithThrows();
        } catch (IOException e) {
            System.out.println("  ✓ 捕获到声明的异常");
        }
        
        System.out.println("\n说明: Checked Exception 必须在编译期处理");
        System.out.println("      要么 try-catch，要么 throws 声明");
    }
    
    /**
     * 测试5：Unchecked Exception 的处理
     */
    public static void test05_UncheckedHandling() {
        System.out.println("\n--- 测试5：Unchecked Exception 处理 ---");
        System.out.println("Unchecked Exception 不强制处理，运行时抛出");
        System.out.println();
        
        // 示例1：NullPointerException
        System.out.println("示例1：NullPointerException");
        String str = null;
        try {
            int length = str.length();
        } catch (NullPointerException e) {
            System.out.println("  ✓ 捕获到 NullPointerException");
            System.out.println("    原因: 对象为 null");
        }
        
        System.out.println();
        
        // 示例2：IndexOutOfBoundsException
        System.out.println("示例2：IndexOutOfBoundsException");
        int[] arr = {1, 2, 3};
        try {
            int value = arr[5];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("  ✓ 捕获到 ArrayIndexOutOfBoundsException");
            System.out.println("    原因: 数组越界");
        }
        
        System.out.println();
        
        // 示例3：ArithmeticException
        System.out.println("示例3：ArithmeticException");
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("  ✓ 捕获到 ArithmeticException");
            System.out.println("    原因: 除零错误");
        }
        
        System.out.println("\n说明: Unchecked Exception 不强制处理");
        System.out.println("      但建议在适当的地方捕获");
    }
    
    /**
     * 测试6：实际开发场景
     */
    public static void test06_RealWorldScenario() {
        System.out.println("\n--- 测试6：实际开发场景 ---");
        System.out.println();
        
        // 场景1：服务调用超时，重试
        System.out.println("场景1：服务调用超时 - 重试机制");
        Result result = callServiceWithRetry();
        System.out.println("  结果: " + result);
        
        System.out.println();
        
        // 场景2：数据库连接失败，降级
        System.out.println("场景2：数据库连接失败 - 降级处理");
        dataOperationWithFallback();
        
        System.out.println("\n说明: Exception 应该做重试或降级");
        System.out.println("      而不是直接失败或吞掉异常");
    }
    
    /**
     * 测试7：常见误区
     */
    public static void test07_CommonMistakes() {
        System.out.println("\n--- 测试7：常见误区 ---");
        
        // 误区1：吞掉异常
        System.out.println("误区1：吞掉异常");
        System.out.println("  ❌ 错误：空的 catch 块");
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            // 空的 catch 块，吞掉了异常
        }
        System.out.println("  ✓ 正确：至少要记录日志");
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("    记录异常: " + e.getMessage());
        }
        
        System.out.println();
        
        // 误区2：捕获过宽泛
        System.out.println("误区2：捕获过宽泛的异常");
        System.out.println("  ❌ 错误：直接 catch Exception");
        try {
            doSomething();
        } catch (Exception e) {
            System.out.println("    捕获了所有异常");
        }
        System.out.println("  ✓ 正确：捕获具体的异常");
        try {
            doSomething();
        } catch (IOException e) {
            System.out.println("    处理 IO 异常");
        } catch (SQLException e) {
            System.out.println("    处理 SQL 异常");
        }
        
        System.out.println();
        
        // 误区3：在 catch 中做耗时操作
        System.out.println("误区3：在 catch 中做耗时操作");
        System.out.println("  ❌ 错误：在 catch 中执行耗时操作");
        System.out.println("    catch 块应该快速处理，避免阻塞");
        System.out.println("  ✓ 正确：快速处理或提交到线程池");
        
        System.out.println("\n总结常见误区：");
        System.out.println("  1. 不要吞掉异常，至少要记录日志");
        System.out.println("  2. 不要捕获过宽泛的异常，要精确");
        System.out.println("  3. 不要在 catch 中做耗时操作");
        System.out.println("  4. 不要捕获 Error，应该快速失败");
        System.out.println("  5. 不要滥用 Checked Exception");
    }
    
    // ============ 辅助方法 ============
    
    /**
     * 读取文件（Checked Exception）
     */
    private static void readFile(String filename) throws FileNotFoundException, IOException {
        FileReader reader = new FileReader(filename);
        BufferedReader br = new BufferedReader(reader);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }
    
    /**
     * 带throws声明的方法
     */
    private static void methodWithThrows() throws IOException {
        throw new IOException("模拟 IO 异常");
    }
    
    /**
     * 服务调用（带重试）
     */
    private static Result callServiceWithRetry() {
        int maxRetries = 3;
        for (int i = 0; i < maxRetries; i++) {
            try {
                // 模拟服务调用
                if (i < 2) {
                    throw new IOException("模拟超时");
                }
                return Result.success("成功");
            } catch (IOException e) {
                System.out.println("  第 " + (i + 1) + " 次调用失败: " + e.getMessage());
                if (i == maxRetries - 1) {
                    System.out.println("  重试用完，返回降级结果");
                    return Result.fallback();
                }
            }
        }
        return Result.error("未知错误");
    }
    
    /**
     * 数据操作（带降级）
     */
    private static void dataOperationWithFallback() {
        try {
            // 模拟数据库操作
            throw new SQLException("连接失败");
        } catch (SQLException e) {
            System.out.println("  ✓ 数据库连接失败: " + e.getMessage());
            System.out.println("    降级: 使用缓存数据");
        }
    }
    
    /**
     * 模拟操作
     */
    private static void doSomething() throws IOException, SQLException {
        // 模拟操作
    }
    
    /**
     * 结果类
     */
    static class Result {
        private boolean success;
        private String message;
        
        private Result(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        
        public static Result success(String message) {
            return new Result(true, message);
        }
        
        public static Result error(String message) {
            return new Result(false, message);
        }
        
        public static Result fallback() {
            return new Result(false, "降级结果");
        }
        
        @Override
        public String toString() {
            return String.format("Result{success=%b, message='%s'}", success, message);
        }
    }
}
