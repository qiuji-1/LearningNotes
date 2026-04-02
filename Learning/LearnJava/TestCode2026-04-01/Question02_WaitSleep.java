/**
 * Question02: Java 中 wait() 和 sleep() 的区别
 * 
 * 本文件演示了 wait() 和 sleep() 的各种用法和区别
 */
public class Question02_WaitSleep {
    
    private static final Object lock = new Object();
    
    public static void main(String[] args) {
        System.out.println("===== Java wait() vs sleep() =====\n");
        
        testWaitBasic();
        testSleepBasic();
        testWaitNotify();
        testSleepWithLock();
        testProducerConsumer();
    }
    
    /**
     * 测试1：wait() 基本用法
     */
    private static void testWaitBasic() {
        System.out.println("【测试1】wait() 基本用法");
        System.out.println("----------------------------------------");
        
        Thread waitingThread = new Thread(() -> {
            synchronized (lock) {
                System.out.println("等待线程：获取锁，准备等待...");
                try {
                    lock.wait(2000);  // 最多等待2秒
                    System.out.println("等待线程：被唤醒或超时");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("等待线程：被中断");
                }
                System.out.println("等待线程：继续执行\n");
            }
        });
        
        Thread notifyingThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                System.out.println("通知线程：获取锁，发送唤醒信号");
                lock.notify();
                System.out.println("通知线程：唤醒信号已发送");
            }
        });
        
        waitingThread.start();
        notifyingThread.start();
        
        // 等待两个线程完成
        try {
            waitingThread.join();
            notifyingThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 测试2：sleep() 基本用法
     */
    private static void testSleepBasic() {
        System.out.println("【测试2】sleep() 基本用法");
        System.out.println("----------------------------------------");
        
        System.out.println("开始执行...");
        
        try {
            System.out.println("暂停1秒...");
            Thread.sleep(1000);
            System.out.println("1秒后继续执行");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("休眠被中断");
        }
        
        System.out.println("完成！\n");
    }
    
    /**
     * 测试3：wait() 和 notify() 协作
     */
    private static void testWaitNotify() {
        System.out.println("【测试3】wait() 和 notify() 协作");
        System.out.println("----------------------------------------");
        
        for (int i = 1; i <= 3; i++) {
            final int threadId = i;
            Thread thread = new Thread(() -> {
                synchronized (lock) {
                    System.out.println("线程" + threadId + "：开始等待");
                    try {
                        lock.wait();
                        System.out.println("线程" + threadId + "：被唤醒！");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("线程" + threadId + "：被中断");
                    }
                }
            });
            thread.start();
        }
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        synchronized (lock) {
            System.out.println("主线程：发送 notifyAll() 唤醒所有线程");
            lock.notifyAll();
        }
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
    
    /**
     * 测试4：sleep() 不释放锁
     */
    private static void testSleepWithLock() {
        System.out.println("【测试4】sleep() 不释放锁");
        System.out.println("----------------------------------------");
        
        Thread thread1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("线程1：获取锁，准备休眠2秒");
                try {
                    Thread.sleep(2000);
                    System.out.println("线程1：休眠结束，继续持有锁");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("线程1：释放锁");
            }
        });
        
        Thread thread2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("线程2：获取锁了！说明线程1释放了锁");
            }
        });
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println();
    }
    
    /**
     * 测试5：生产者-消费者模式
     */
    private static void testProducerConsumer() {
        System.out.println("【测试5】生产者-消费者模式");
        System.out.println("----------------------------------------");
        
        SharedBuffer buffer = new SharedBuffer();
        
        // 生产者
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    buffer.produce(i);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        // 消费者
        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    buffer.consume();
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        producer.start();
        consumer.start();
        
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println();
    }
}

/**
 * 共享缓冲区（生产者-消费者模式）
 */
class SharedBuffer {
    private static final int MAX_SIZE = 3;
    private final java.util.Queue<Integer> queue = new java.util.LinkedList<>();
    
    public void produce(int value) throws InterruptedException {
        synchronized (queue) {
            while (queue.size() >= MAX_SIZE) {
                System.out.println("队列已满，生产者等待...");
                queue.wait();
            }
            
            queue.add(value);
            System.out.println("生产: " + value + "，队列大小: " + queue.size());
            queue.notifyAll();
        }
    }
    
    public void consume() throws InterruptedException {
        synchronized (queue) {
            while (queue.isEmpty()) {
                System.out.println("队列为空，消费者等待...");
                queue.wait();
            }
            
            int value = queue.poll();
            System.out.println("消费: " + value + "，队列大小: " + queue.size());
            queue.notifyAll();
        }
    }
}
