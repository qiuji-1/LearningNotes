import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.net.*;
import java.util.*;

/**
 * Java NIO Channel 测试代码
 * 
 * 测试内容：
 * 1. FileChannel 基本读写
 * 2. SocketChannel 客户端通信（模拟）
 * 3. Buffer 的 flip() 方法
 * 4. 非阻塞模式配置
 * 5. Channel 的资源释放
 */
public class Question04_Channel {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("Java NIO Channel 测试代码");
        System.out.println("=".repeat(60));
        
        test01_FileChannelBasic();
        test02_BufferFlip();
        test03_BufferReuse();
        test04_TryWithResources();
        test05_ChannelInfo();
        
        System.out.println("=".repeat(60));
        System.out.println("所有测试完成！");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 测试1：FileChannel 基本读写操作
     */
    public static void test01_FileChannelBasic() {
        System.out.println("\n--- 测试1：FileChannel 基本读写 ---");
        
        String filePath = "test_channel_data.txt";
        
        try {
            // 1. 写文件
            RandomAccessFile writeFile = new RandomAccessFile(filePath, "rw");
            FileChannel writeChannel = writeFile.getChannel();
            
            String data = "Hello, NIO Channel!\n这是一个测试文件。";
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(data.getBytes());
            buffer.flip();  // 切换为读模式
            
            int bytesWritten = writeChannel.write(buffer);
            System.out.println("写入字节数: " + bytesWritten);
            
            writeChannel.close();
            writeFile.close();
            
            // 2. 读文件
            RandomAccessFile readFile = new RandomAccessFile(filePath, "r");
            FileChannel readChannel = readFile.getChannel();
            
            buffer.clear();  // 清空缓冲区
            int bytesRead = readChannel.read(buffer);
            System.out.println("读取字节数: " + bytesRead);
            
            buffer.flip();  // 切换为读模式
            String content = new String(buffer.array(), 0, buffer.limit());
            System.out.println("读取内容:\n" + content);
            
            readChannel.close();
            readFile.close();
            
            // 3. 删除测试文件
            new File(filePath).delete();
            
        } catch (IOException e) {
            System.err.println("文件操作异常: " + e.getMessage());
        }
    }
    
    /**
     * 测试2：Buffer 的 flip() 方法重要性
     */
    public static void test02_BufferFlip() {
        System.out.println("\n--- 测试2：Buffer 的 flip() 方法 ---");
        
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("Hello, Buffer!".getBytes());
        
        System.out.println("put() 之后:");
        System.out.println("  position: " + buffer.position());
        System.out.println("  limit: " + buffer.limit());
        System.out.println("  capacity: " + buffer.capacity());
        
        // ❌ 没有 flip()，直接读取
        System.out.println("\n没有 flip() 读取:");
        System.out.println("  可读取字节数: " + (buffer.limit() - buffer.position()));
        
        // ✅ flip() 之后
        buffer.flip();
        System.out.println("\nflip() 之后:");
        System.out.println("  position: " + buffer.position());
        System.out.println("  limit: " + buffer.limit());
        System.out.println("  可读取字节数: " + (buffer.limit() - buffer.position()));
        
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        System.out.println("  读取内容: " + new String(data));
    }
    
    /**
     * 测试3：复用 Buffer 减少内存分配
     */
    public static void test03_BufferReuse() {
        System.out.println("\n--- 测试3：复用 Buffer ---");
        
        // ❌ 每次创建新 Buffer（低效）
        System.out.println("低效方式：每次创建新 Buffer");
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.putInt(i);
        }
        long endTime = System.nanoTime();
        System.out.println("耗时: " + (endTime - startTime) / 1000000.0 + " ms");
        
        // ✅ 复用同一个 Buffer（高效）
        System.out.println("\n高效方式：复用同一个 Buffer");
        startTime = System.nanoTime();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        for (int i = 0; i < 1000; i++) {
            buffer.clear();
            buffer.putInt(i);
        }
        endTime = System.nanoTime();
        System.out.println("耗时: " + (endTime - startTime) / 1000000.0 + " ms");
    }
    
    /**
     * 测试4：try-with-resources 自动关闭 Channel
     */
    public static void test04_TryWithResources() {
        System.out.println("\n--- 测试4：try-with-resources 自动关闭 ---");
        
        String filePath = "test_auto_close.txt";
        
        try (FileChannel channel = FileChannel.open(Paths.get(filePath),
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            
            ByteBuffer buffer = ByteBuffer.wrap("自动关闭测试".getBytes());
            channel.write(buffer);
            
            System.out.println("✓ 文件写入成功");
            System.out.println("✓ Channel 会自动关闭，无需手动 close()");
            
        } catch (IOException e) {
            System.err.println("文件操作异常: " + e.getMessage());
        }
        
        // 删除测试文件
        new File(filePath).delete();
    }
    
    /**
     * 测试5：Channel 信息展示
     */
    public static void test05_ChannelInfo() {
        System.out.println("\n--- 测试5：Channel 信息 ---");
        
        // FileChannel 信息
        try {
            String filePath = "test_channel_info.txt";
            FileChannel channel = FileChannel.open(Paths.get(filePath),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            
            System.out.println("FileChannel 信息:");
            System.out.println("  是否打开: " + channel.isOpen());
            System.out.println("  通道类型: " + channel.getClass().getSimpleName());
            
            channel.close();
            System.out.println("\n关闭后:");
            System.out.println("  是否打开: " + channel.isOpen());
            
            new File(filePath).delete();
            
        } catch (IOException e) {
            System.err.println("文件操作异常: " + e.getMessage());
        }
        
        // 常见 Channel 类型
        System.out.println("\n常见 Channel 类型:");
        System.out.println("  1. FileChannel      - 文件通道");
        System.out.println("  2. SocketChannel    - TCP 客户端通道");
        System.out.println("  3. ServerSocketChannel - TCP 服务端通道");
        System.out.println("  4. DatagramChannel  - UDP 通道");
    }
}
