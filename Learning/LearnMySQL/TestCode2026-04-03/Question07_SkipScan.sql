-- 测试7: MySQL 8.0 Skip Scan 优化
-- 说明: 测试MySQL 8.0 Skip Scan Range Access Method
-- 注意: 需要MySQL 8.0.13及以上版本

-- 检查MySQL版本
SELECT VERSION();

-- 准备测试表
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    order_date DATE NOT NULL,
    amount DECIMAL(10,2),
    INDEX idx_user_date (user_id, order_date)
);

-- 插入测试数据
INSERT INTO orders (user_id, order_date, amount)
SELECT 
    FLOOR(RAND() * 100) + 1 AS user_id,
    DATE('2026-01-01') + INTERVAL FLOOR(RAND() * 90) DAY AS order_date,
    ROUND(RAND() * 1000, 2) AS amount
FROM (
    SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
) t1,
(
    SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
) t2,
(
    SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
) t3,
(
    SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
) t4;

-- 查看数据量
SELECT COUNT(*) FROM orders;

-- 测试1: 查询使用第二列(可能触发Skip Scan)
EXPLAIN SELECT * FROM orders WHERE order_date = '2026-03-15';
-- 检查Extra字段是否有 "Using index for skip-scan"

-- 测试2: 强制使用索引
SELECT /*+ INDEX(orders idx_user_date) */ * FROM orders 
WHERE order_date = '2026-03-15';

-- 测试3: 禁止使用索引(强制全表扫描)
SELECT /*+ NO_INDEX(orders idx_user_date) */ * FROM orders 
WHERE order_date = '2026-03-15';

-- 测试4: 查询使用第一列(不触发Skip Scan)
EXPLAIN SELECT * FROM orders WHERE user_id = 50;
-- 预期: 使用普通的索引查找

-- 测试5: 范围查询(可能触发Skip Scan)
EXPLAIN SELECT COUNT(*) FROM orders 
WHERE order_date BETWEEN '2026-03-01' AND '2026-03-31';
-- 检查Extra字段是否有 "Using index for skip-scan"

-- 查看索引统计信息
SELECT 
    TABLE_NAME,
    INDEX_NAME,
    CARDINALITY,
    SUB_PART,
    NULLABLE,
    INDEX_TYPE
FROM information_schema.STATISTICS
WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'orders';
