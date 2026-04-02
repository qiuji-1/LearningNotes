import java.math.BigDecimal;

/**
 * Float 相等判断测试代码
 * 
 * 测试内容：
 * 1. 直接用 == 判断的错误
 * 2. 浮点数的精度问题
 * 3. 三种正确的相等判断方法
 * 4. Math.ulp() 的使用
 * 5. BigDecimal 精确计算
 * 6. 特殊情况处理（NaN、Infinity、-0.0）
 */
public class Question06_FloatComparison {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("Float 相等判断测试代码");
        System.out.println("=".repeat(60));
        
        test01_WrongComparison();
        test02_FloatPrecision();
        test03_ThreeMethods();
        test04_MathUlp();
        test05_BigDecimal();
        test06_SpecialCases();
        test07_RealWorldScenarios();
        
        System.out.println("=".repeat(60));
        System.out.println("所有测试完成！");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 测试1：错误的相等判断（用 ==）
     */
    public static void test01_WrongComparison() {
        System.out.println("\n--- 测试1：错误的相等判断 ---");
        
        float a = 0.1f * 3;
        float b = 0.3f;
        
        System.out.println("a = 0.1f * 3");
        System.out.println("b = 0.3f");
        System.out.println();
        System.out.printf("a 的实际值: %.20f\n", a);
        System.out.printf("b 的实际值: %.20f\n", b);
        System.out.println();
        System.out.println("差值: " + (a - b));
        System.out.println("a == b: " + (a == b));
        System.out.println();
        System.out.println("结论: ❌ 直接用 == 判断浮点数会出错！");
    }
    
    /**
     * 测试2：浮点数的精度问题
     */
    public static void test02_FloatPrecision() {
        System.out.println("\n--- 测试2：浮点数的精度问题 ---");
        
        System.out.println("为什么会出现精度问题？");
        System.out.println("十进制 0.1 在二进制中是无限循环的：");
        System.out.println("  0.000110011001100110011001100110011...");
        System.out.println();
        
        System.out.println("常见计算示例：");
        
        // 示例1
        float result1 = 0.1f + 0.2f;
        System.out.printf("  0.1f + 0.2f = %.20f\n", result1);
        System.out.println("  期望值: 0.3");
        System.out.println("  是否等于 0.3f: " + (result1 == 0.3f));
        
        // 示例2
        float result2 = 1.0f - 0.9f;
        System.out.printf("\n  1.0f - 0.9f = %.20f\n", result2);
        System.out.println("  期望值: 0.1");
        System.out.println("  是否等于 0.1f: " + (result2 == 0.1f));
        
        // 示例3
        float result3 = 0.1f * 10;
        System.out.printf("\n  0.1f * 10 = %.20f\n", result3);
        System.out.println("  期望值: 1.0");
        System.out.println("  是否等于 1.0f: " + (result3 == 1.0f));
    }
    
    /**
     * 测试3：三种正确的相等判断方法
     */
    public static void test03_ThreeMethods() {
        System.out.println("\n--- 测试3：三种正确的相等判断方法 ---");
        
        float a = 0.1f * 3;
        float b = 0.3f;
        
        System.out.println("测试用例: a = 0.1f * 3, b = 0.3f");
        System.out.println("直接用 ==: " + (a == b));
        System.out.println();
        
        // 方法1：固定阈值
        boolean result1 = equalsFixedEpsilon(a, b, 1e-6f);
        System.out.println("方法1 - 固定阈值 (epsilon = 1e-6): " + result1);
        
        // 方法2：Math.ulp()
        boolean result2 = equalsWithUlp(a, b);
        System.out.println("方法2 - Math.ulp(): " + result2);
        
        // 方法3：相对误差
        boolean result3 = equalsRelative(a, b, 1e-6f);
        System.out.println("方法3 - 相对误差 (epsilon = 1e-6): " + result3);
    }
    
    /**
     * 测试4：Math.ulp() 的使用
     */
    public static void test04_MathUlp() {
        System.out.println("\n--- 测试4：Math.ulp() 的使用 ---");
        
        System.out.println("ulp (Unit in the Last Place) 的含义：");
        System.out.println("  浮点数最后一位的单位值");
        System.out.println();
        
        float[] values = {0.0f, 1.0f, 100.0f, 1000.0f, 1000000.0f};
        for (float value : values) {
            System.out.printf("  Math.ulp(%.0f) = %.20f\n", value, Math.ulp(value));
        }
        
        System.out.println("\n使用 Math.ulp() 判断相等：");
        
        float a = 0.1f * 3;
        float b = 0.3f;
        float diff = Math.abs(a - b);
        float maxUlp = Math.max(Math.ulp(a), Math.ulp(b));
        
        System.out.printf("  a = %.20f\n", a);
        System.out.printf("  b = %.20f\n", b);
        System.out.printf("  |a - b| = %.20f\n", diff);
        System.out.printf("  max(Math.ulp(a), Math.ulp(b)) = %.20f\n", maxUlp);
        System.out.println("  |a - b| <= max(ulp(a), ulp(b)): " + (diff <= maxUlp));
    }
    
    /**
     * 测试5：BigDecimal 精确计算
     */
    public static void test05_BigDecimal() {
        System.out.println("\n--- 测试5：BigDecimal 精确计算 ---");
        
        System.out.println("1. float vs BigDecimal 对比：");
        
        // float 版本
        float fa = 0.1f * 3;
        float fb = 0.3f;
        System.out.println("  float: " + (fa == fb));
        
        // BigDecimal 版本
        BigDecimal ba = new BigDecimal("0.1").multiply(new BigDecimal("3"));
        BigDecimal bb = new BigDecimal("0.3");
        System.out.println("  BigDecimal: " + ba.equals(bb));
        
        System.out.println("\n2. BigDecimal 构造方式对比：");
        
        // ❌ 错误：使用 double 构造
        BigDecimal bd1 = new BigDecimal(0.1);
        System.out.println("  new BigDecimal(0.1): " + bd1);
        
        // ✅ 正确：使用字符串构造
        BigDecimal bd2 = new BigDecimal("0.1");
        System.out.println("  new BigDecimal(\"0.1\"): " + bd2);
        
        System.out.println("\n3. 金融计算示例：");
        
        BigDecimal amount = new BigDecimal("1000.00");
        BigDecimal rate = new BigDecimal("0.005");  // 0.5%
        BigDecimal interest = amount.multiply(rate);
        
        System.out.println("  本金: " + amount);
        System.out.println("  利率: " + rate);
        System.out.println("  利息: " + interest);
        System.out.println("  本息合计: " + amount.add(interest));
    }
    
    /**
     * 测试6：特殊情况处理
     */
    public static void test06_SpecialCases() {
        System.out.println("\n--- 测试6：特殊情况处理 ---");
        
        System.out.println("1. NaN 的特殊性：");
        System.out.println("  Float.NaN == Float.NaN: " + (Float.NaN == Float.NaN));
        System.out.println("  Float.compare(Float.NaN, Float.NaN) == 0: " + 
            (Float.compare(Float.NaN, Float.NaN) == 0));
        System.out.println("  Float.isNaN(Float.NaN): " + Float.isNaN(Float.NaN));
        
        System.out.println("\n2. Infinity 的比较：");
        System.out.println("  Float.POSITIVE_INFINITY == Float.POSITIVE_INFINITY: " + 
            (Float.POSITIVE_INFINITY == Float.POSITIVE_INFINITY));
        System.out.println("  Float.NEGATIVE_INFINITY == Float.NEGATIVE_INFINITY: " + 
            (Float.NEGATIVE_INFINITY == Float.NEGATIVE_INFINITY));
        
        System.out.println("\n3. -0.0f 和 0.0f 的关系：");
        System.out.println("  -0.0f == 0.0f: " + (-0.0f == 0.0f));
        System.out.println("  Float.compare(-0.0f, 0.0f): " + Float.compare(-0.0f, 0.0f));
        System.out.println("  1.0f / -0.0f: " + (1.0f / -0.0f));
        System.out.println("  1.0f / 0.0f: " + (1.0f / 0.0f));
        
        System.out.println("\n4. 推荐的 equals 方法（处理特殊情况）：");
        System.out.println("  equalsSafely(0.1f * 3, 0.3f): " + 
            equalsSafely(0.1f * 3, 0.3f));
        System.out.println("  equalsSafely(Float.NaN, Float.NaN): " + 
            equalsSafely(Float.NaN, Float.NaN));
        System.out.println("  equalsSafely(-0.0f, 0.0f): " + 
            equalsSafely(-0.0f, 0.0f));
    }
    
    /**
     * 测试7：实际场景
     */
    public static void test07_RealWorldScenarios() {
        System.out.println("\n--- 测试7：实际场景 ---");
        
        System.out.println("1. 大数场景（固定阈值可能失效）：");
        
        float a = 1000000.0f;
        float b = 1000000.1f;
        
        System.out.printf("  a = %.0f\n", a);
        System.out.printf("  b = %.1f\n", b);
        System.out.println("  相对误差: " + Math.abs(a - b) / a);
        System.out.println("  固定阈值 (1e-6): " + equalsFixedEpsilon(a, b, 1e-6f));
        System.out.println("  相对误差 (1e-6): " + equalsRelative(a, b, 1e-6f));
        
        System.out.println("\n2. 极小数场景：");
        
        float c = 0.000001f;
        float d = 0.0000011f;
        
        System.out.printf("  c = %.7f\n", c);
        System.out.printf("  d = %.7f\n", d);
        System.out.println("  固定阈值 (1e-6): " + equalsFixedEpsilon(c, d, 1e-6f));
        System.out.println("  Math.ulp(): " + equalsWithUlp(c, d));
        
        System.out.println("\n3. 累计运算场景：");
        
        float sum = 0.0f;
        for (int i = 0; i < 10; i++) {
            sum += 0.1f;
        }
        System.out.printf("  10 * 0.1f = %.20f\n", sum);
        System.out.println("  期望值: 1.0");
        System.out.println("  是否等于 1.0f: " + (sum == 1.0f));
        System.out.println("  用 Math.ulp() 判断: " + equalsWithUlp(sum, 1.0f));
    }
    
    // ============ 辅助方法 ============
    
    /**
     * 方法1：固定阈值
     */
    public static boolean equalsFixedEpsilon(float a, float b, float epsilon) {
        return Math.abs(a - b) < epsilon;
    }
    
    /**
     * 方法2：使用 Math.ulp()
     */
    public static boolean equalsWithUlp(float a, float b) {
        return Math.abs(a - b) <= Math.max(Math.ulp(a), Math.ulp(b));
    }
    
    /**
     * 方法3：相对误差
     */
    public static boolean equalsRelative(float a, float b, float epsilon) {
        if (a == b) return true;
        if (a == 0 || b == 0) return Math.abs(a - b) < epsilon;
        return Math.abs(a - b) / (Math.abs(a) + Math.abs(b)) < epsilon;
    }
    
    /**
     * 安全的 equals 方法（处理特殊情况）
     */
    public static boolean equalsSafely(float a, float b) {
        return Float.compare(a, b) == 0 || equalsWithUlp(a, b);
    }
}
