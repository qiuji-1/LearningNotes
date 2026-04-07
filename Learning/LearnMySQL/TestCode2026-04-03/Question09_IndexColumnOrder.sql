-- 测试9: 联合索引列的顺序设计
-- 说明: 测试高频查询条件、区分度、范围查询对索引列顺序的影响

-- 准备测试表
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    id BIGINT PRIMARY KEY,
    user_id INT,
    order_date DATE,
    amount DECIMAL(10,2),
    status VARCHAR(20)
);

-- 插入测试数据
INSERT INTO orders (id, user_id, order_date, amount, status) VALUES
(1, 100, '2026-04-01', 100.00, '待支付'),
(2, 100, '2026-04-02', 200.00, '已支付'),
(3, 100, '2026-04-03', 300.00, '已支付'),
(4, 100, '2026-04-04', 400.00, '已取消'),
(5, 200, '2026-04-01', 150.00, '待支付'),
(6, 200, '2026-04-02', 250.00, '已支付'),
(7, 200, '2026-04-03', 350.00, '已支付'),
(8, 200, '2026-04-04', 450.00, '已取消'),
(9, 300, '2026-04-01', 200.00, '待支付'),
(10, 300, '2026-04-02', 300.00, '已支付');

-- 计算各列的区分度
SELECT 
    'user_id' AS column_name,
    COUNT(DISTINCT user_id) AS distinct_values,
    COUNT(*) AS total_rows,
    ROUND(COUNT(DISTINCT user_id) / COUNT(*) * 100, 2) AS selectivity_percent
FROM orders
UNION ALL
SELECT 
    'status' AS column_name,
    COUNT(DISTINCT status) AS distinct_values,
    COUNT(*) AS total_rows,
    ROUND(COUNT(DISTINCT status) / COUNT(*) * 100, 2) AS selectivity_percent
FROM orders
UNION ALL
SELECT 
    'amount' AS column_name,
    COUNT(DISTINCT amount) AS distinct_values,
    COUNT(*) AS total_rows,
    ROUND(COUNT(DISTINCT amount) / COUNT(*) * 100, 2) AS selectivity_percent
FROM orders;

-- 创建索引1: user_id放前面(高频查询)
CREATE INDEX idx_user_date ON orders(user_id, order_date);

-- 创建索引2: 区分度高的列放前面
CREATE INDEX idx_amount_status ON orders(amount, status);

-- 创建索引3: 范围查询的列放最后
CREATE INDEX idx_user_status ON orders(user_id, status);

-- 测试索引使用
-- 测试1: 高频查询 - 只用user_id
EXPLAIN SELECT * FROM orders WHERE user_id = 100;
-- 预期: 使用 idx_user_date

-- 测试2: 高频查询 - 用user_id和order_date
EXPLAIN SELECT * FROM orders WHERE user_id = 100 AND order_date = '2026-04-03';
-- 预期: 使用 idx_user_date

-- 测试3: 区分度高的列 - 先按amount过滤
EXPLAIN SELECT * FROM orders WHERE amount = 100.00 AND status = '已支付';
-- 预期: 使用 idx_amount_status

-- 测试4: 范围查询 - order_date是范围查询
EXPLAIN SELECT * FROM orders 
WHERE user_id = 100 AND order_date >= '2026-04-01' AND order_date <= '2026-04-30';
-- 预期: 使用 idx_user_date,但status用不上

-- 测试5: 排序查询 - 包含排序字段
EXPLAIN SELECT * FROM orders WHERE user_id = 100 ORDER BY order_date;
-- 预期: 使用 idx_user_date,不需要filesort

-- 查看所有索引
SHOW INDEX FROM orders;
