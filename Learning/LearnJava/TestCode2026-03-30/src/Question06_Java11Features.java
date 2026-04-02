/**
 * 第6题：Java 11有哪些新特性
 * 
 * 【核心概念】
 * Java 11是Java 8后的第一个LTS版本，聚焦于优化高频开发场景。
 * 
 * 【主要特性】
 * 1. String增强
 *    - isBlank()：判断空或空白
 *    - strip()：去除Unicode空白
 *    - lines()：多行转Stream
 *    - repeat(n)：重复字符串
 * 
 * 2. Files简化
 *    - writeString()：一行代码写文件
 *    - readString()：一行代码读文件
 * 
 * 3. Optional.isEmpty()
 *    - 反向判断空值
 *    - 更直观的API
 * 
 * 4. HTTP客户端
 *    - 原生支持HTTP/2
 *    - 支持同步/异步
 *    - 无需第三方库
 * 
 * 【题目跳转】
 * @see Question01_Serialization 第1题：序列化
 * @see Question02_JavaAdvantages 第2题：Java优势
 * @see Question03_MultipleInheritance 第3题：多重继承
 * @see Question04_MethodOverloadOverride 第4题：方法重载重写
 * @see Question05_Java25Features 第5题：Java 25新特性
 * @see Question07_Java17Features 第7题：Java 17新特性
 * @see Question08_Java21Features 第8题：Java 21新特性
 * → [第4题：方法重载重写](Question04_MethodOverloadOverride.java)
 * → [第5题：Java 25新特性](Question05_Java25Features.java)
 * → [第7题：Java 17新特性](Question07_Java17Features.java)
 * → [第8题：Java 21新特性](Question08_Java21Features.java)
 * 
 * 运行要求：Java 11+（✓ 当前环境Java 17支持）
 */

// ===== 导入说明 =====
// java.net.URI：统一资源标识符，用于表示HTTP请求的URL
// java.net.http.*：Java 11新增的HTTP客户端API
// java.nio.file.*：NIO文件操作类，提供简化的文件读写方法
// java.util.Optional：可选值容器类，避免null引用

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * 主类：Java 11新特性演示
 */
public class Question06_Java11Features {
    
    /**
     * main方法：程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) throws Exception {
        System.out.println("========== 第6题：Java 11新特性 ==========\n");
        
        // 特性1：String增强
        testStringEnhancements();
        
        // 特性2：Files简化
        testFilesSimplification();
        
        // 特性3：Optional.isEmpty()
        testOptionalIsEmpty();
        
        // 特性4：HTTP客户端（需要网络，注释掉）
        // testHttpClient();
    }
    
    /**
     * 特性1：String增强
     * 
     * 【新增方法】
     * 1. isBlank()：判断是否为空或全是空白字符
     *    - ""：返回true
     *    - "   "：返回true
     *    - "hello"：返回false
     * 
     * 2. strip()：去除所有Unicode空白字符
     *    - 支持全角空格等Unicode空白
     *    - 比 trim() 更强大
     * 
     * 3. lines()：将多行字符串转换为Stream
     *    - 按换行符分隔
     *    - 返回 Stream<String>
     * 
     * 4. repeat(n)：重复字符串n次
     *    - "*" * 5 → "*****"
     */
    private static void testStringEnhancements() {
        System.out.println("【特性1：String增强】");
        System.out.println();
        
        // ===== isBlank()演示 =====
        System.out.println("1. isBlank() - 判断空或空白");
        System.out.println();
        
        String str1 = "";      // 空字符串
        String str2 = "   ";   // 全是空格
        String str3 = "hello"; // 有内容
        
        // isEmpty() vs isBlank()
        // isEmpty()：只判断长度是否为0
        // isBlank()：判断长度为0 或 全是空白字符
        System.out.println("isEmpty() vs isBlank()：");
        System.out.println("  \"\".isEmpty() = " + str1.isEmpty());      // true（长度=0）
        System.out.println("  \"   \".isEmpty() = " + str2.isEmpty());   // false（长度=3）
        System.out.println();
        System.out.println("  \"\".isBlank() = " + str1.isBlank());      // true（空字符串）
        System.out.println("  \"   \".isBlank() = " + str2.isBlank());   // true（全是空白）
        System.out.println("  \"hello\".isBlank() = " + str3.isBlank()); // false（有内容）
        System.out.println();
        
        // ===== strip()演示 =====
        System.out.println("2. strip() - 去除Unicode空白");
        System.out.println();
        
        String text = "  你好世界  ";
        System.out.println("原字符串：\"" + text + "\"");
        System.out.println("  strip()：\"" + text.strip() + "\"");          // 去除两端空白
        System.out.println("  stripLeading()：\"" + text.stripLeading() + "\"");   // 去除前导空白
        System.out.println("  stripTrailing()：\"" + text.stripTrailing() + "\""); // 去除尾部空白
        System.out.println();
        
        // trim() vs strip()
        // trim()：只能去除ASCII空白字符（码值<=127）
        // strip()：支持所有Unicode空白字符（包括全角空格）
        System.out.println("trim() vs strip()：");
        String fullAngleSpace = "\u3000你好\u3000";  // \u3000是全角空格
        System.out.println("  全角空格.trim() = \"" + fullAngleSpace.trim() + "\"");
        System.out.println("  全角空格.strip() = \"" + fullAngleSpace.strip() + "\"");
        System.out.println("  注意：trim()无法去除全角空格，strip()可以");
        System.out.println();
        
        // ===== lines()演示 =====
        System.out.println("3. lines() - 多行字符串转Stream");
        System.out.println();
        
        String poem = "春眠不觉晓\n处处闻啼鸟\n夜来风雨声";
        System.out.println("原字符串：");
        System.out.println("  " + poem.replace("\n", "\\n"));
        System.out.println();
        System.out.println("按行输出：");
        
        // lines()返回Stream<String>，可以逐行处理
        // forEach()：对每个元素执行操作
        poem.lines().forEach(line -> System.out.println("  " + line));
        System.out.println();
        
        // ===== repeat()演示 =====
        System.out.println("4. repeat() - 重复字符串");
        System.out.println();
        
        String star = "*";
        System.out.println("\"*\".repeat(5) = " + star.repeat(5));
        System.out.println("\"-\".repeat(10) = " + "-".repeat(10));
        System.out.println();
    }
    
    /**
     * 特性2：Files简化
     * 
     * 【新增方法】
     * 1. Files.writeString()：一行代码写入字符串到文件
     * 2. Files.readString()：一行代码读取文件内容为字符串
     * 
     * 【优势】
     * - 简化文件操作
     * - 自动处理字符编码（UTF-8）
     * - 自动关闭文件流
     * 
     * 【对比旧方式】
     * - 旧方式：FileWriter、BufferedReader等，需要手动关闭
     * - 新方式：一行代码搞定
     */
    private static void testFilesSimplification() throws Exception {
        System.out.println("【特性2：Files简化】");
        System.out.println();
        
        // 文件名
        String filename = "test_java11.txt";
        
        // 要写入的内容
        String content = "Hello Java 11!\n你好，Java 11！";
        
        System.out.println("演示：一行代码读写文件");
        System.out.println();
        
        // ===== 写文件 =====
        // Path：表示文件路径
        // Paths.get()：创建Path对象
        Path path = Paths.get(filename);
        
        // Files.writeString()：写入字符串到文件
        // 自动使用UTF-8编码
        // 自动关闭文件流
        Files.writeString(path, content);
        System.out.println("✓ 写入文件：" + filename);
        System.out.println("  内容：" + content.replace("\n", "\\n"));
        System.out.println();
        
        // ===== 读文件 =====
        // Files.readString()：读取文件内容为字符串
        // 自动使用UTF-8编码
        String readContent = Files.readString(path);
        System.out.println("✓ 读取文件：" + filename);
        System.out.println("  内容：" + readContent.replace("\n", "\\n"));
        System.out.println();
        
        // ===== 对比旧方式 =====
        System.out.println("对比旧方式：");
        System.out.println();
        System.out.println("旧方式（繁琐）：");
        System.out.println("  FileWriter writer = new FileWriter(\"test.txt\");");
        System.out.println("  writer.write(\"Hello\");");
        System.out.println("  writer.close();  // 手动关闭");
        System.out.println();
        System.out.println("新方式（简洁）：");
        System.out.println("  Files.writeString(Paths.get(\"test.txt\"), \"Hello\");");
        System.out.println();
        
        // ===== 清理测试文件 =====
        // Files.delete()：删除文件
        Files.delete(path);
        System.out.println("✓ 清理测试文件");
        System.out.println();
    }
    
    /**
     * 特性3：Optional.isEmpty()
     * 
     * 【新增方法】
     * - isEmpty()：判断Optional是否为空
     * - 与isPresent()相反
     * 
     * 【优势】
     * - 更直观：if (opt.isEmpty()) 比 if (!opt.isPresent()) 更易读
     * - 避免取反操作
     */
    private static void testOptionalIsEmpty() {
        System.out.println("【特性3：Optional.isEmpty()】");
        System.out.println();
        
        System.out.println("Optional类：");
        System.out.println("  - 用于表示可能为空的值");
        System.out.println("  - 避免null引用");
        System.out.println("  - 强制处理空值情况");
        System.out.println();
        
        // 创建Optional对象
        // Optional.of()：创建非空Optional
        Optional<String> opt1 = Optional.of("hello");
        
        // Optional.empty()：创建空Optional
        Optional<String> opt2 = Optional.empty();
        
        System.out.println("对比：isPresent() vs isEmpty()");
        System.out.println();
        
        // ===== 旧方法：isPresent() =====
        // isPresent()：判断是否有值（非空）
        System.out.println("旧方法（isPresent）：");
        System.out.println("  opt1.isPresent() = " + opt1.isPresent());  // true（有值）
        System.out.println("  opt2.isPresent() = " + opt2.isPresent());  // false（无值）
        System.out.println();
        
        // 判断空值需要取反
        System.out.println("判断空值：");
        System.out.println("  if (!opt2.isPresent()) { ... }  // 需要取反，不直观");
        System.out.println();
        
        // ===== 新方法：isEmpty() =====
        // isEmpty()：判断是否为空
        System.out.println("新方法（isEmpty）：");
        System.out.println("  opt1.isEmpty() = " + opt1.isEmpty());  // false（不为空）
        System.out.println("  opt2.isEmpty() = " + opt2.isEmpty());  // true（为空）
        System.out.println();
        
        // 判断空值更直观
        System.out.println("判断空值：");
        System.out.println("  if (opt2.isEmpty()) { ... }  // ✅ 更直观");
        System.out.println();
        
        System.out.println("代码示例：");
        System.out.println("  // 旧写法");
        System.out.println("  if (!opt.isPresent()) {");
        System.out.println("      System.out.println(\"值为空\");");
        System.out.println("  }");
        System.out.println();
        System.out.println("  // 新写法（Java 11+）");
        System.out.println("  if (opt.isEmpty()) {");
        System.out.println("      System.out.println(\"值为空\");");
        System.out.println("  }");
        System.out.println();
    }
    
    /**
     * 特性4：HTTP客户端（需要网络）
     * 
     * 【新特性】
     * - 原生支持HTTP/2
     * - 支持同步/异步请求
     * - 支持WebSocket
     * - 无需第三方库（Apache HttpClient）
     * 
     * 【核心类】
     * - HttpClient：HTTP客户端
     * - HttpRequest：HTTP请求
     * - HttpResponse：HTTP响应
     */
    private static void testHttpClient() throws Exception {
        System.out.println("【特性4：HTTP客户端】");
        System.out.println();
        System.out.println("注意：此功能需要网络连接");
        System.out.println();
        
        // ===== 创建HTTP客户端 =====
        // HttpClient.newHttpClient()：创建默认HTTP客户端
        HttpClient client = HttpClient.newHttpClient();
        
        // ===== 创建HTTP请求 =====
        // HttpRequest.newBuilder()：创建请求构建器
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.github.com"))  // 设置URL
            .GET()                                       // GET请求
            .build();                                    // 构建请求
        
        // ===== 发送请求 =====
        // client.send()：发送请求（同步）
        // HttpResponse.BodyHandlers.ofString()：将响应体转为字符串
        HttpResponse<String> response = client.send(request, 
            HttpResponse.BodyHandlers.ofString());
        
        // ===== 处理响应 =====
        System.out.println("状态码：" + response.statusCode());
        System.out.println("响应长度：" + response.body().length() + " 字符");
        System.out.println();
        
        // ===== 对比旧方式 =====
        System.out.println("对比旧方式：");
        System.out.println();
        System.out.println("旧方式（HttpURLConnection）：");
        System.out.println("  URL url = new URL(\"https://...\");");
        System.out.println("  HttpURLConnection conn = (HttpURLConnection) url.openConnection();");
        System.out.println("  conn.setRequestMethod(\"GET\");");
        System.out.println("  // ... 繁琐的流处理");
        System.out.println();
        System.out.println("新方式（HttpClient）：");
        System.out.println("  HttpClient client = HttpClient.newHttpClient();");
        System.out.println("  HttpRequest request = HttpRequest.newBuilder()");
        System.out.println("      .uri(URI.create(\"https://...\"))");
        System.out.println("      .GET()");
        System.out.println("      .build();");
        System.out.println("  HttpResponse<String> response = client.send(request, ...);");
        System.out.println();
    }
}
