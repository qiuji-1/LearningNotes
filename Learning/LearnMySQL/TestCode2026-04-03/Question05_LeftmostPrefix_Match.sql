-- 测试5: 联合索引的最左前缀匹配原则
-- 说明: 测试联合索引的最左前缀匹配规则

-- 准备测试表
DROP TABLE IF EXISTS test_index;
CREATE TABLE test_index (
    id INT PRIMARY KEY,
    a INT,
    b INT,
    c INT,
    name VARCHAR(50),
    INDEX idx_abc (a, b, c)
);

-- 插入测试数据
INSERT INTO test_index (id, a, b, c, name) VALUES
(1, 1, 1, 1, '记录1'),
(2, 1, 1, 2, '记录2'),
(3, 1, 2, 1, '记录3'),
(4, 1, 2, 2, '记录4'),
(5, 2, 1, 1, '记录5'),
(6, 2, 1, 2, '记录6'),
(7, 2, 2, 1, '记录7'),
(8, 2, 2, 2, '记录8');

-- 测试1: 符合最左匹配原则 - 使用第一列
EXPLAIN SELECT * FROM test_index WHERE a = 1;
-- 预期: 使用索引

-- 测试2: 符合最左匹配原则 - 使用前两列
EXPLAIN SELECT * FROM test_index WHERE a = 1 AND b = 2;
-- 预期: 使用索引

-- 测试3: 符合最左匹配原则 - 使用全部列
EXPLAIN SELECT * FROM test_index WHERE a = 1 AND b = 2 AND c = 1;
-- 预期: 使用索引

-- 测试4: 不符合最左匹配原则 - 跳过第一列
EXPLAIN SELECT * FROM test_index WHERE b = 2;
-- 预期: 不使用索引,全表扫描

-- 测试5: 不符合最左匹配原则 - 跳过前两列
EXPLAIN SELECT * FROM test_index WHERE c = 1;
-- 预期: 不使用索引,全表扫描

-- 测试6: 不符合最左匹配原则 - 跳过第一列
EXPLAIN SELECT * FROM test_index WHERE b = 2 AND c = 1;
-- 预期: 不使用索引,全表扫描

-- 测试7: 部分匹配 - 使用第一列,跳过第二列,使用第三列
EXPLAIN SELECT * FROM test_index WHERE a = 1 AND c = 1;
-- 预期: 只使用a列,索引下推优化
