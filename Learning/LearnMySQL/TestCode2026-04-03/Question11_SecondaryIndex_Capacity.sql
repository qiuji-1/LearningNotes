-- 测试11: 二级索引的容量计算
-- 说明: 测试二级索引相比主键索引能支撑的数据量

-- 准备测试表
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    id BIGINT PRIMARY KEY,
    user_id BIGINT,
    order_date DATE,
    amount DECIMAL(10,2)
);

-- 插入测试数据
INSERT INTO orders (id, user_id, order_date, amount) VALUES
(1, 100, '2026-04-01', 1000.00),
(2, 100, '2026-04-02', 2000.00),
(3, 100, '2026-04-03', 3000.00),
(4, 200, '2026-04-01', 1500.00),
(5, 200, '2026-04-02', 2500.00),
(6, 300, '2026-04-01', 1800.00),
(7, 300, '2026-04-02', 2800.00),
(8, 400, '2026-04-01', 1200.00);

-- 创建二级索引
CREATE INDEX idx_user_id ON orders(user_id);

-- 计算理论容量
SELECT '二级索引容量计算' AS description;

SELECT 
    '主键索引' AS index_type,
    '完整数据行(假设100字节)' AS leaf_content,
    16 AS rows_per_page,
    '1170' AS fanout_per_node,
    ROUND(POWER(1170, 2) * 16 / 10000, 2) AS capacity_millions
UNION ALL
SELECT 
    '二级索引' AS index_type,
    '主键值+索引值(16字节)' AS leaf_content,
    1024 AS rows_per_page,
    '1170' AS fanout_per_node,
    ROUND(POWER(1170, 2) * 1024 / 10000, 2) AS capacity_millions;

-- 测试二级索引查询
-- 场景1: 返回少量记录(适合使用二级索引)
EXPLAIN SELECT * FROM orders WHERE user_id = 100;
-- 查看rows字段,如果返回记录少,性能好

-- 场景2: 使用覆盖索引(避免回表)
EXPLAIN SELECT id, user_id FROM orders WHERE user_id = 100;
-- 查看Extra字段,如果有"Using index",说明使用了覆盖索引

-- 场景3: 限制返回数量
EXPLAIN SELECT * FROM orders WHERE user_id = 100 LIMIT 10;
-- 通过LIMIT限制回表次数

-- 查看索引大小
SELECT 
    INDEX_NAME,
    ROUND(STAT_VALUE * @@innodb_page_size / 1024 / 1024, 2) AS size_mb
FROM information_schema.INNODB_SYS_INDEXES
WHERE TABLE_ID = (
    SELECT TABLE_ID FROM information_schema.INNODB_SYS_TABLES
    WHERE NAME = CONCAT(DATABASE(), '/orders')
)
ORDER BY size_mb DESC;

-- 计算二级索引的开销
SELECT 
    '二级索引数量' AS metric,
    '5个' AS value
UNION ALL
SELECT 
    '主键类型(BIGINT)' AS metric,
    '8字节' AS value
UNION ALL
SELECT 
    '1000万条数据' AS metric,
    '80MB' AS value
UNION ALL
SELECT 
    '1000万条数据(CHAR(36)主键)' AS metric,
    '360MB' AS value;
