import java.util.*;

/**
 * Question01: Java 中 for 循环与 foreach 循环的区别
 * 
 * 本文件演示了 for 循环和 foreach 循环的各种用法和区别
 */
public class Question01_ForForeach {
    
    public static void main(String[] args) {
        System.out.println("===== Java for 循环 vs foreach 循环 =====\n");
        
        testArrayForForeach();
        testListForForeach();
        testLinkedListPerformance();
        testRemoveInForeach();
        testIteratorRemove();
        testRemoveIf();
        testMapIteration();
    }
    
    /**
     * 测试1：数组的 for 和 foreach 遍历
     */
    private static void testArrayForForeach() {
        System.out.println("【测试1】数组遍历");
        System.out.println("----------------------------------------");
        
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        System.out.println("1. 传统 for 循环 - 基本遍历：");
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();
        
        System.out.println("\n2. 传统 for 循环 - 偶数位元素：");
        for (int i = 0; i < nums.length; i += 2) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();
        
        System.out.println("\n3. 传统 for 循环 - 反向遍历：");
        for (int i = nums.length - 1; i >= 0; i--) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();
        
        System.out.println("\n4. foreach 遍历：");
        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println("\n");
    }
    
    /**
     * 测试2：List 的 for 和 foreach 遍历
     */
    private static void testListForForeach() {
        System.out.println("【测试2】List 遍历");
        System.out.println("----------------------------------------");
        
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));
        
        System.out.println("1. 传统 for 循环：");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("索引 " + i + ": " + list.get(i));
        }
        
        System.out.println("\n2. foreach 遍历：");
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println();
    }
    
    /**
     * 测试3：LinkedList 性能对比
     */
    private static void testLinkedListPerformance() {
        System.out.println("【测试3】LinkedList 性能对比");
        System.out.println("----------------------------------------");
        
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 1000; i++) {
            linkedList.add(i);
        }
        
        System.out.println("1. 传统 for 循环（不推荐）：");
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < linkedList.size(); i++) {
            Integer num = linkedList.get(i);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("耗时: " + (end1 - start1) + "ms");
        System.out.println("说明: 每次get(i)都要从头遍历，O(n²)复杂度");
        
        System.out.println("\n2. foreach 遍历（推荐）：");
        long start2 = System.currentTimeMillis();
        for (Integer num : linkedList) {
            // do nothing
        }
        long end2 = System.currentTimeMillis();
        System.out.println("耗时: " + (end2 - start2) + "ms");
        System.out.println("说明: 使用迭代器，O(n)复杂度，性能好很多\n");
    }
    
    /**
     * 测试4：foreach 中删除元素（会抛异常）
     */
    private static void testRemoveInForeach() {
        System.out.println("【测试4】foreach 中删除元素（错误示例）");
        System.out.println("----------------------------------------");
        
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        System.out.println("原始列表: " + list);
        
        try {
            System.out.println("\n尝试在 foreach 中删除元素...");
            for (String s : list) {
                if (s.equals("B")) {
                    list.remove(s);  // 会抛出 ConcurrentModificationException
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("❌ 抛出 ConcurrentModificationException");
            System.out.println("说明: foreach 中不能直接修改集合");
            System.out.println("原因: 迭代器检测到集合被修改（非通过迭代器）\n");
        }
    }
    
    /**
     * 测试5：使用迭代器正确删除元素
     */
    private static void testIteratorRemove() {
        System.out.println("【测试5】使用迭代器删除元素（正确示例）");
        System.out.println("----------------------------------------");
        
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        System.out.println("原始列表: " + list);
        
        System.out.println("\n使用迭代器删除元素 B...");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (s.equals("B")) {
                iterator.remove();  // 正确删除
            }
        }
        System.out.println("✅ 删除后的列表: " + list);
        System.out.println("说明: 通过迭代器的 remove() 方法可以安全删除\n");
    }
    
    /**
     * 测试6：使用 removeIf（Java 8+）
     */
    private static void testRemoveIf() {
        System.out.println("【测试6】使用 removeIf 删除元素（Java 8+）");
        System.out.println("----------------------------------------");
        
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        System.out.println("原始列表: " + list);
        
        System.out.println("\n使用 removeIf 删除所有长度为 1 的元素...");
        list.removeIf(s -> s.length() == 1);
        System.out.println("✅ 删除后的列表: " + list);
        System.out.println("说明: removeIf 是最简洁的删除方式\n");
    }
    
    /**
     * 测试7：Map 的遍历
     */
    private static void testMapIteration() {
        System.out.println("【测试7】Map 遍历");
        System.out.println("----------------------------------------");
        
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        
        System.out.println("1. 遍历键值对（推荐）：");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        
        System.out.println("\n2. 遍历键：");
        for (String key : map.keySet()) {
            System.out.println(key);
        }
        
        System.out.println("\n3. 遍历值：");
        for (Integer value : map.values()) {
            System.out.println(value);
        }
        
        System.out.println("\n4. Java 8+ forEach：");
        map.forEach((key, value) -> System.out.println(key + " = " + value));
    }
}
