import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;

/**
 * Java NIO Selector 测试代码
 * 
 * 测试内容：
 * 1. Selector 基本信息和方法
 * 2. SelectionKey 事件类型判断
 * 3. 动态修改感兴趣的事件
 * 4. 附加对象的使用
 * 5. select() 超时时间设置
 * 6. 常见误区演示
 */
public class Question05_Selector {
    
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("Java NIO Selector 测试代码");
        System.out.println("=".repeat(60));
        
        test01_SelectorBasic();
        test02_SelectionKeyTypes();
        test03_ModifyInterestOps();
        test04_AttachmentObject();
        test05_SelectTimeout();
        test06_CommonMistakes();
        
        System.out.println("=".repeat(60));
        System.out.println("所有测试完成！");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 测试1：Selector 基本信息和方法
     */
    public static void test01_SelectorBasic() throws Exception {
        System.out.println("\n--- 测试1：Selector 基本信息 ---");
        
        // 打开 Selector
        Selector selector = Selector.open();
        
        System.out.println("Selector 信息:");
        System.out.println("  是否打开: " + selector.isOpen());
        System.out.println("  当前就绪通道数: " + selector.selectNow());
        
        // 创建 ServerSocketChannel 并注册
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(0));  // 随机端口
        
        // 注册到 Selector
        SelectionKey key = serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("\n注册后:");
        System.out.println("  已注册的通道数: " + selector.keys().size());
        System.out.println("  事件类型: " + key.interestOps());
        System.out.println("  OP_ACCEPT 值: " + SelectionKey.OP_ACCEPT);
        
        // 关闭资源
        serverChannel.close();
        selector.close();
        System.out.println("\n关闭后:");
        System.out.println("  Selector 是否打开: " + selector.isOpen());
    }
    
    /**
     * 测试2：SelectionKey 事件类型判断
     */
    public static void test02_SelectionKeyTypes() throws Exception {
        System.out.println("\n--- 测试2：SelectionKey 事件类型 ---");
        
        Selector selector = Selector.open();
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        
        // SocketChannel 可以注册 OP_CONNECT, OP_READ, OP_WRITE
        int interestOps = SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;
        SelectionKey key = channel.register(selector, interestOps);
        
        System.out.println("事件类型值:");
        System.out.println("  OP_ACCEPT = " + SelectionKey.OP_ACCEPT + " (二进制: " + 
            Integer.toBinaryString(SelectionKey.OP_ACCEPT) + ")");
        System.out.println("  OP_CONNECT = " + SelectionKey.OP_CONNECT + " (二进制: " + 
            Integer.toBinaryString(SelectionKey.OP_CONNECT) + ")");
        System.out.println("  OP_READ = " + SelectionKey.OP_READ + " (二进制: " + 
            Integer.toBinaryString(SelectionKey.OP_READ) + ")");
        System.out.println("  OP_WRITE = " + SelectionKey.OP_WRITE + " (二进制: " + 
            Integer.toBinaryString(SelectionKey.OP_WRITE) + ")");
        
        System.out.println("\n组合事件: OP_CONNECT | OP_READ | OP_WRITE = " + interestOps);
        System.out.println("  二进制: " + Integer.toBinaryString(interestOps));
        
        System.out.println("\n判断事件类型:");
        System.out.println("  isAcceptable(): " + key.isAcceptable());
        System.out.println("  isConnectable(): " + key.isConnectable());
        System.out.println("  isReadable(): " + key.isReadable());
        System.out.println("  isWritable(): " + key.isWritable());
        
        System.out.println("\n说明: 事件就绪状态需要实际的 I/O 操作才能改变");
        
        channel.close();
        selector.close();
    }
    
    /**
     * 测试3：动态修改感兴趣的事件
     */
    public static void test03_ModifyInterestOps() throws Exception {
        System.out.println("\n--- 测试3：动态修改感兴趣的事件 ---");
        
        Selector selector = Selector.open();
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        
        // 1. 初始只监听 OP_READ
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
        System.out.println("1. 初始事件: " + key.interestOps() + " (OP_READ)");
        
        // 2. 添加 OP_WRITE
        int newOps = key.interestOps() | SelectionKey.OP_WRITE;
        key.interestOps(newOps);
        System.out.println("2. 添加 OP_WRITE: " + key.interestOps() + 
            " (OP_READ | OP_WRITE)");
        
        // 3. 取消 OP_WRITE
        int removeWriteOps = key.interestOps() & ~SelectionKey.OP_WRITE;
        key.interestOps(removeWriteOps);
        System.out.println("3. 取消 OP_WRITE: " + key.interestOps() + " (OP_READ)");
        
        // 4. 添加 OP_CONNECT（SocketChannel 支持的事件）
        int socketOps = SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;
        key.interestOps(socketOps);
        System.out.println("4. SocketChannel 所有事件: " + key.interestOps());
        
        channel.close();
        selector.close();
    }
    
    /**
     * 测试4：附加对象的使用
     */
    public static void test04_AttachmentObject() throws Exception {
        System.out.println("\n--- 测试4：附加对象 ---");
        
        // 自定义附加对象类
        class ConnectionInfo {
            private String userId;
            private String clientIp;
            private long connectTime;
            
            public ConnectionInfo(String userId, String clientIp) {
                this.userId = userId;
                this.clientIp = clientIp;
                this.connectTime = System.currentTimeMillis();
            }
            
            @Override
            public String toString() {
                return String.format("用户: %s, IP: %s, 连接时间: %d", 
                    userId, clientIp, connectTime);
            }
        }
        
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(0));
        
        // 创建附加对象并注册
        ConnectionInfo info = new ConnectionInfo("user001", "192.168.1.100");
        SelectionKey key = serverChannel.register(selector, SelectionKey.OP_ACCEPT, info);
        
        System.out.println("注册时附加对象:");
        System.out.println("  " + info.toString());
        
        // 获取附加对象
        ConnectionInfo attachedInfo = (ConnectionInfo) key.attachment();
        System.out.println("\n获取附加对象:");
        System.out.println("  " + attachedInfo.toString());
        
        // 修改附加对象
        attachedInfo.userId = "user002";
        System.out.println("\n修改后:");
        System.out.println("  " + attachedInfo.toString());
        
        // 验证是否是同一个对象
        System.out.println("\n是否是同一个对象: " + (info == attachedInfo));
        
        serverChannel.close();
        selector.close();
    }
    
    /**
     * 测试5：select() 超时时间设置
     */
    public static void test05_SelectTimeout() throws Exception {
        System.out.println("\n--- 测试5：select() 超时时间 ---");
        
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(0));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        // 1. selectNow() - 非阻塞立即返回
        long startTime = System.nanoTime();
        int count = selector.selectNow();
        long endTime = System.nanoTime();
        System.out.println("1. selectNow():");
        System.out.println("  就绪通道数: " + count);
        System.out.println("  耗时: " + (endTime - startTime) / 1000000.0 + " ms");
        
        // 2. select(1000) - 设置 1 秒超时
        startTime = System.nanoTime();
        count = selector.select(1000);
        endTime = System.nanoTime();
        System.out.println("\n2. select(1000) [1秒超时]:");
        System.out.println("  就绪通道数: " + count);
        System.out.println("  耗时: " + (endTime - startTime) / 1000000.0 + " ms");
        
        // 3. select() - 无限阻塞（使用单独线程演示，避免阻塞主线程）
        System.out.println("\n3. select() [无限阻塞]:");
        System.out.println("  会一直阻塞直到有事件就绪...");
        System.out.println("  注意：实际使用中通常不直接测试，避免阻塞");
        
        serverChannel.close();
        selector.close();
    }
    
    /**
     * 测试6：常见误区演示
     */
    public static void test06_CommonMistakes() throws Exception {
        System.out.println("\n--- 测试6：常见误区演示 ---");
        
        // 误区1：阻塞模式无法注册到 Selector
        System.out.println("误区1: 阻塞模式无法注册到 Selector");
        try {
            ServerSocketChannel channel = ServerSocketChannel.open();
            // 没有调用 configureBlocking(false)
            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("  ❌ 不应该执行到这里");
        } catch (IllegalBlockingModeException e) {
            System.out.println("  ✓ 抛出 IllegalBlockingModeException: 正确！");
            System.out.println("  必须先调用 channel.configureBlocking(false)");
        }
        
        // 误区2：select() 返回的是就绪的通道数，不是所有注册的通道数
        System.out.println("\n误区2: select() 返回值");
        Selector selector = Selector.open();
        ServerSocketChannel channel1 = ServerSocketChannel.open();
        channel1.configureBlocking(false);
        channel1.bind(new InetSocketAddress(0));
        channel1.register(selector, SelectionKey.OP_ACCEPT);
        
        ServerSocketChannel channel2 = ServerSocketChannel.open();
        channel2.configureBlocking(false);
        channel2.bind(new InetSocketAddress(0));
        channel2.register(selector, SelectionKey.OP_ACCEPT);
        
        System.out.println("  注册的通道数: " + selector.keys().size());
        int readyCount = selector.selectNow();
        System.out.println("  select() 返回的就绪通道数: " + readyCount);
        System.out.println("  ✓ select() 只返回就绪的，不是所有注册的");
        
        channel1.close();
        channel2.close();
        selector.close();
        
        // 误区3：忘记关闭 Selector
        System.out.println("\n误区3: 必须及时关闭 Selector");
        System.out.println("  ❌ 不关闭 Selector 会泄漏系统资源（文件描述符）");
        System.out.println("  ✓ 正确做法: 使用 try-with-resources 或 finally 块");
        
        try (Selector autoCloseSelector = Selector.open()) {
            // 使用 selector
            System.out.println("  ✓ 使用 try-with-resources 自动关闭");
        }
        
        System.out.println("\n常见误区总结:");
        System.out.println("  1. 阻塞模式无法注册到 Selector");
        System.out.println("  2. 必须移除已处理的 SelectionKey");
        System.out.println("  3. select() 返回就绪的通道数");
        System.out.println("  4. 在事件处理中不要执行耗时操作");
        System.out.println("  5. 必须及时关闭 Selector 和 Channel");
    }
}
