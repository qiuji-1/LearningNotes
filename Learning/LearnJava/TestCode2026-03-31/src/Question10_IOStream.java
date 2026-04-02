import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * 题目：Java中的I/O流是什么
 *
 * 演示I/O流的使用和最佳实践
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
 * @see Question11_PrimitiveTypes 第11题：基本数据类型
 * @see Question12_AutoBoxing 第12题：自动拆箱装箱
 * @see Question13_Inheritance 第13题：继承机制
 * @see Question14_AccessModifiers 第14题：访问修饰符
 * @see Question15_StaticInstanceMethod 第15题：静态方法和实例方法
 */
public class Question10_IOStream {
    public static void main(String[] args) {
        System.out.println("=== 字节流 vs 字符流 ===\n");
        
        // 字节流：读取二进制文件
        System.out.println("字节流适合：图片、视频、压缩包等二进制文件");
        
        // 字符流：读取文本文件
        System.out.println("字符流适合：txt、csv、xml、json等文本文件");
        
        System.out.println("\n=== 文件读写示例 ===\n");
        
        String fileName = "test_io.txt";
        
        // 写入文件
        System.out.println("写入文件：");
        writeToFile(fileName, "Hello World\nJava I/O流示例\n你好世界");
        
        // 读取文件
        System.out.println("\n读取文件：");
        readFromFile(fileName);
        
        // 复制文件
        System.out.println("\n复制文件：");
        String copyFileName = "test_io_copy.txt";
        copyFile(fileName, copyFileName);
        
        System.out.println("\n=== 缓冲流的优势 ===\n");
        
        // 无缓冲
        System.out.println("无缓冲：每次read()都可能触发系统调用");
        
        // 有缓冲
        System.out.println("有缓冲：先读到内存，减少系统调用");
        System.out.println("默认缓冲区大小：8KB");
        
        System.out.println("\n=== 字节流转字符流 ===\n");
        
        // 指定编码读取
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            System.out.println("文件内容（UTF-8编码）：");
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("  " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n=== 常见错误示例 ===\n");
        
        // 错误1：忘记关闭流
        System.out.println("错误1：忘记关闭流 → 资源泄漏");
        System.out.println("解决：使用try-with-resources自动关闭");
        
        // 错误2：字符编码问题
        System.out.println("\n错误2：未指定编码 → 可能乱码");
        System.out.println("解决：显式指定编码（UTF-8）");
        
        // 错误3：忘记flush
        System.out.println("\n错误3：缓冲流忘记flush → 数据丢失");
        System.out.println("解决：close()会自动flush，或手动flush()");
        
        System.out.println("\n=== Java NIO简介 ===\n");
        
        System.out.println("传统BIO：阻塞式，一连接一线程");
        System.out.println("现代NIO：非阻塞，一线程多连接");
        System.out.println();
        System.out.println("NIO三大组件：");
        System.out.println("  1. Channel（通道） - 双向通道");
        System.out.println("  2. Buffer（缓冲区） - 数据容器");
        System.out.println("  3. Selector（选择器） - 多路复用");
        
        System.out.println("\n=== 实用工具方法 ===\n");
        
        // Java 7+: Files工具类
        try {
            // 快速读取所有行
            System.out.println("Files.readAllLines():");
            Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8)
                .forEach(line -> System.out.println("  " + line));
            
            // 快速写入
            Path tempFile = Paths.get("temp_test.txt");
            Files.write(tempFile, "快速写入测试\n".getBytes(StandardCharsets.UTF_8));
            System.out.println("\nFiles.write() 成功");
            
            // 删除临时文件
            Files.deleteIfExists(tempFile);
            Files.deleteIfExists(Paths.get(fileName));
            Files.deleteIfExists(Paths.get(copyFileName));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // 写入文件
    private static void writeToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            writer.flush();  // 确保写入磁盘
            System.out.println("写入成功：" + fileName);
        } catch (IOException e) {
            System.err.println("写入失败：" + e.getMessage());
        }
    }
    
    // 读取文件
    private static void readFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("  " + line);
            }
        } catch (IOException e) {
            System.err.println("读取失败：" + e.getMessage());
        }
    }
    
    // 复制文件（字节流）
    private static void copyFile(String source, String target) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(target))) {
            
            byte[] buffer = new byte[8192];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            System.out.println("复制成功：" + source + " -> " + target);
            
        } catch (IOException e) {
            System.err.println("复制失败：" + e.getMessage());
        }
    }
}
