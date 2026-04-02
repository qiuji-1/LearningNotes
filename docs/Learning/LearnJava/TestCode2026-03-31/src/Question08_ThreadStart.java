/**
 * 题目：如果一个线程在Java中被两次调用start()方法，会发生什么
 *
 * 演示线程状态流转和IllegalThreadStateException
 *
 * 【题目跳转】
 * @see Question01_Class 第1题：Java中的类
 * @see Question02_Instantiation 第2题：实例化
 * @see Question03_Static 第3题：static关键字
 * @see Question04_InterfaceAbstract 第4题：接口和抽象类
 * @see Question05_JDKJRE 第5题：JDK和JRE
 * @see Question06_JDKTools 第6题：JDK工具
 * @see Question07_Reflection 第7题：反射机制
 * @see Question09_Optional 第9题：Optional类
 * @see Question10_IOStream 第10题：I/O流
 * @see Question11_PrimitiveTypes 第11题：基本数据类型
 * @see Question12_AutoBoxing 第12题：自动拆箱装箱
 * @see Question13_Inheritance 第13题：继承机制
 * @see Question14_AccessModifiers 第14题：访问修饰符
 * @see Question15_StaticInstanceMethod 第15题：静态方法和实例方法
 */
public class Question08_ThreadStart {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 演示两次调用start()的异常 ===\n");
        
        Thread thread = new Thread(() -> {
            System.out.println("线程正在执行...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程执行完毕");
        });
        
        System.out.println("线程初始状态: " + thread.getState());
        
        // 第一次启动
        System.out.println("\n第一次调用start()...");
        thread.start();
        System.out.println("启动后状态: " + thread.getState());
        
        // 等待线程结束
        thread.join();
        System.out.println("执行完毕状态: " + thread.getState());
        
        // 第二次启动（会抛出异常）
        System.out.println("\n第二次调用start()...");
        try {
            thread.start();
        } catch (IllegalThreadStateException e) {
            System.out.println("捕获异常: " + e.getClass().getName());
            System.out.println("异常信息: " + e.getMessage());
        }
        
        System.out.println("\n=== 演示线程的六种状态 ===\n");
        
        // NEW状态
        Thread stateThread = new Thread(() -> {
            System.out.println("stateThread: RUNNABLE状态");
            try {
                Thread.sleep(1000);  // TIMED_WAITING
                System.out.println("stateThread: 即将结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("1. 创建后状态: " + stateThread.getState());
        
        // RUNNABLE状态
        stateThread.start();
        Thread.sleep(100);
        System.out.println("2. 启动后状态: " + stateThread.getState());
        
        // TIMED_WAITING状态
        Thread.sleep(100);
        System.out.println("3. 睡眠中状态: " + stateThread.getState());
        
        // 等待线程结束
        stateThread.join();
        System.out.println("4. 结束后状态: " + stateThread.getState());
        
        System.out.println("\n=== 演示BLOCKED状态 ===\n");
        
        Object lock = new Object();
        
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("t1: 获取锁");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1: 释放锁");
            }
        });
        
        Thread t2 = new Thread(() -> {
            System.out.println("t2: 尝试获取锁");
            synchronized (lock) {
                System.out.println("t2: 获取锁成功");
            }
        });
        
        t1.start();
        Thread.sleep(100);
        
        t2.start();
        Thread.sleep(100);
        System.out.println("t2状态（等待锁）: " + t2.getState());
        
        t1.join();
        t2.join();
        
        System.out.println("\n=== 演示WAITING状态 ===\n");
        
        Object waitLock = new Object();
        
        Thread waitThread = new Thread(() -> {
            synchronized (waitLock) {
                try {
                    System.out.println("waitThread: 调用wait()");
                    waitLock.wait();
                    System.out.println("waitThread: 被唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        waitThread.start();
        Thread.sleep(100);
        System.out.println("waitThread状态: " + waitThread.getState());
        
        // 唤醒线程
        synchronized (waitLock) {
            waitLock.notify();
        }
        
        waitThread.join();
        System.out.println("waitThread最终状态: " + waitThread.getState());
        
        System.out.println("\n=== 如何正确复用线程 ===\n");
        System.out.println("方案1: 每次创建新线程（适合简单场景）");
        System.out.println("方案2: 使用线程池（推荐）");
        System.out.println("方案3: Runnable + 新Thread");
    }
}
