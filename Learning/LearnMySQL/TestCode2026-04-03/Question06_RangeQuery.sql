-- 测试6: 范围查询的特殊规则
-- 说明: 测试联合索引在范围查询时的行为

-- 准备测试表
DROP TABLE IF EXISTS test_range;
CREATE TABLE test_range (
    id INT PRIMARY KEY,
    user_id INT,
    order_date DATE,
    amount DECIMAL(10,2),
    INDEX idx_user_date_amount (user_id, order_date, amount)
);

-- 插入测试数据
INSERT INTO test_range (id, user_id, order_date, amount) VALUES
(1, 100, '2026-04-01', 100.00),
(2, 100, '2026-04-02', 200.00),
(3, 100, '2026-04-03', 300.00),
(4, 100, '2026-04-04', 400.00),
(5, 100, '2026-04-05', 500.00),
(6, 200, '2026-04-01', 100.00),
(7, 200, '2026-04-02', 200.00),
(8, 200, '2026-04-03', 300.00);

-- 测试1: 完全使用索引(等值查询)
EXPLAIN SELECT * FROM test_range 
WHERE user_id = 100 AND order_date = '2026-04-03' AND amount = 300.00;
-- 预期: 使用索引 user_id, order_date, amount

-- 测试2: 部分使用索引(范围查询在第二列)
EXPLAIN SELECT * FROM test_range 
WHERE user_id = 100 AND order_date > '2026-04-01';
-- 预期: 使用索引 user_id, order_date
-- amount用不上,因为是范围查询之后的列

-- 测试3: 只能用第一列(范围查询在第一列)
EXPLAIN SELECT * FROM test_range 
WHERE user_id = 100 AND order_date > '2026-04-01' AND amount > 500;
-- 预期: 使用索引 user_id, order_date
-- amount用不上

-- 测试4: 范围查询在第一列,等值查询在第二列(用不上第二列)
EXPLAIN SELECT * FROM test_range 
WHERE user_id > 100 AND order_date = '2026-04-03';
-- 预期: 只能用 user_id
-- order_date用不上,因为user_id是范围查询

-- 测试5: 范围查询在第一列,范围查询在第二列
EXPLAIN SELECT * FROM test_range 
WHERE user_id > 100 AND order_date > '2026-04-01';
-- 预期: 只能用 user_id
-- order_date用不上

-- 测试6: >=操作后续能用到
EXPLAIN SELECT * FROM test_range 
WHERE user_id >= 100 AND order_date = '2026-04-03';
-- 预期: 能用 user_id, order_date

-- 测试7: >操作后续用不上
EXPLAIN SELECT * FROM test_range 
WHERE user_id > 100 AND order_date = '2026-04-03';
-- 预期: 只能用 user_id
