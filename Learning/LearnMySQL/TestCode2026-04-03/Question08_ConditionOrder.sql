-- 测试8: 查询条件顺序对索引使用的影响
-- 说明: 测试MySQL优化器自动调整条件顺序的功能

-- 准备测试表
DROP TABLE IF EXISTS test_condition;
CREATE TABLE test_condition (
    id INT PRIMARY KEY,
    a INT,
    b INT,
    c INT,
    name VARCHAR(50),
    INDEX idx_abc (a, b, c)
);

-- 插入测试数据
INSERT INTO test_condition (id, a, b, c, name) VALUES
(1, 1, 1, 1, '记录1'),
(2, 1, 1, 2, '记录2'),
(3, 1, 2, 1, '记录3'),
(4, 1, 2, 2, '记录4'),
(5, 2, 1, 1, '记录5'),
(6, 2, 1, 2, '记录6'),
(7, 2, 2, 1, '记录7'),
(8, 2, 2, 2, '记录8');

-- 测试1: 条件顺序与索引顺序一致
EXPLAIN SELECT * FROM test_condition WHERE a = 2 AND b = 1;
-- 预期: 能用到索引

-- 测试2: 条件顺序与索引顺序不一致(优化器自动调整)
EXPLAIN SELECT * FROM test_condition WHERE b = 1 AND a = 2;
-- 预期: 能用到索引,优化器会自动调整为 a=2 AND b=1

-- 测试3: 缺少最左列
EXPLAIN SELECT * FROM test_condition WHERE b = 1 AND c = 2;
-- 预期: 用不上索引,全表扫描

-- 测试4: 条件顺序不一致,但有所有必需的列
EXPLAIN SELECT * FROM test_condition WHERE c = 1 AND b = 2 AND a = 1;
-- 预期: 能用到索引,优化器自动调整

-- 对比执行
SELECT '=== 条件顺序一致 ===' AS test_type;
SELECT * FROM test_condition WHERE a = 1 AND b = 2;

SELECT '=== 条件顺序不一致 ===' AS test_type;
SELECT * FROM test_condition WHERE b = 2 AND a = 1;

-- 查看优化器重写后的执行计划
SHOW WARNINGS;
