-- ============================================
-- 题目2：InnoDB引擎中的聚簇索引和非聚簇索引有什么区别？
-- 演示聚簇索引和非聚簇索引的存储结构和查询差异
-- ============================================

-- 创建测试数据库
CREATE DATABASE IF NOT EXISTS test_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE test_db;

-- ============================================
-- 1. 创建带聚簇索引的表（主键索引就是聚簇索引）
-- ============================================

CREATE TABLE user_clustered (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键（聚簇索引）',
    name VARCHAR(50) NOT NULL,
    age INT,
    email VARCHAR(100),
    address VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_name (name) COMMENT '非聚簇索引'
) ENGINE=InnoDB;

-- 插入测试数据
INSERT INTO user_clustered (name, age, email, address) VALUES
('张三', 25, 'zhangsan@example.com', '浙江省杭州市西湖区'),
('李四', 30, 'lisi@example.com', '浙江省杭州市滨江区'),
('王五', 28, 'wangwu@example.com', '浙江省杭州市余杭区'),
('赵六', 32, 'zhaoliu@example.com', '浙江省杭州市拱墅区'),
('孙七', 27, 'sunqi@example.com', '浙江省杭州市萧山区');

-- ============================================
-- 2. 演示聚簇索引查询（一步到位）
-- ============================================

-- EXPLAIN分析查询
EXPLAIN SELECT * FROM user_clustered WHERE id = 3;

-- 实际查询
SELECT * FROM user_clustered WHERE id = 3;

/*
聚簇索引查询特点：
- id是主键，对应聚簇索引
- 叶子节点存储完整数据行
- 查询时：通过B+树找到id=3的记录 → 直接返回完整数据
- 不需要回表操作
- 效率最高，一次索引查找即可
*/

-- ============================================
-- 3. 演示非聚簇索引查询（需要回表）
-- ============================================

-- EXPLAIN分析查询
EXPLAIN SELECT * FROM user_clustered WHERE name = '王五';

-- 实际查询（先查非聚簇索引，再回表）
SELECT * FROM user_clustered WHERE name = '王五';

/*
非聚簇索引查询特点：
- name是非聚簇索引
- 叶子节点存储（name, id）组合
- 查询时：通过name索引B+树找到name='王五'的记录，得到id=3
- 回表：拿着id=3去主键索引（聚簇索引）B+树查找
- 需要两次B+树查找：一次非聚簇索引 + 一次聚簇索引
- 比聚簇索引查询多一次IO操作
*/

-- ============================================
-- 4. 演示覆盖索引（不需要回表）
-- ============================================

-- 只查询索引包含的列，不需要回表
EXPLAIN SELECT id, name FROM user_clustered WHERE name = '王五';

SELECT id, name FROM user_clustered WHERE name = '王五';

/*
覆盖索引特点：
- 查询的列（id, name）都在name索引中
- 不需要回表去主键索引查找完整数据
- EXPLAIN Extra列显示 "Using index"
- 性能最优，没有回表开销
*/

-- ============================================
-- 5. 对比分析
-- ============================================

-- 查询1：聚簇索引查询（最快）
SELECT SQL_NO_CACHE 
    id, name, age, email, address, created_at
FROM user_clustered 
WHERE id = 3;

-- 查询2：非聚簇索引查询 + 回表（较慢）
SELECT SQL_NO_CACHE 
    id, name, age, email, address, created_at
FROM user_clustered 
WHERE name = '王五';

-- 查询3：非聚簇索引查询 + 覆盖索引（不回表，较快）
SELECT SQL_NO_CACHE 
    id, name
FROM user_clustered 
WHERE name = '王五';

-- ============================================
-- 6. 查看索引信息
-- ============================================

-- 查看表的所有索引
SHOW INDEX FROM user_clustered;

-- 查看表的详细状态
SHOW TABLE STATUS LIKE 'user_clustered';

-- ============================================
-- 7. 主键有序性测试（自增主键 vs UUID主键）
-- ============================================

-- 创建使用自增主键的表（推荐）
CREATE TABLE user_auto_increment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 创建使用UUID主键的表（不推荐）
CREATE TABLE user_uuid (
    id CHAR(36) PRIMARY KEY COMMENT 'UUID主键',
    name VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 批量插入自增主键表（顺序插入，不产生页分裂）
INSERT INTO user_auto_increment (name)
SELECT 
    CONCAT('用户', n) AS name,
    NOW() AS created_at
FROM (
    SELECT 1 AS n UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
) AS numbers;

-- 批量插入UUID主键表（随机插入，可能产生页分裂）
INSERT INTO user_uuid (id, name, created_at)
SELECT 
    MD5(CONCAT('uuid', n, NOW())) AS id,
    CONCAT('用户', n) AS name,
    NOW() AS created_at
FROM (
    SELECT 1 AS n UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
) AS numbers;

-- ============================================
-- 7.5. 查看插入的数据
-- ============================================

-- 查看聚簇索引表数据（自增主键）
SELECT '=== 聚簇索引表（自增主键）===' AS '';
SELECT * FROM user_clustered LIMIT 5;

-- 查看自增主键表数据
SELECT '=== 自增主键表 ===' AS '';
SELECT * FROM user_auto_increment LIMIT 5;

-- 查看UUID主键表数据
SELECT '=== UUID主键表 ===' AS '';
SELECT * FROM user_uuid LIMIT 5;

-- ============================================
-- 8. 清理测试数据
-- ============================================

-- 删除所有测试表
DROP TABLE IF EXISTS user_clustered;
DROP TABLE IF EXISTS user_auto_increment;
DROP TABLE IF EXISTS user_uuid;

-- 删除测试数据库
-- DROP DATABASE IF EXISTS test_db;

-- ============================================
-- 总结：聚簇索引 vs 非聚簇索引
-- ============================================

/*
核心区别对比：

聚簇索引（主键索引）：
- 叶子节点：存储完整的行数据
- 查询方式：一次B+树查找 → 直接返回数据
- 回表：不需要
- 性能：最优（一次IO）
- 数量：一张表只能有一个
- 示例：id主键索引

非聚簇索引（二级索引）：
- 叶子节点：存储（索引列值 + 主键值）
- 查询方式：非聚簇索引查找 → 拿主键 → 聚簇索引查找 → 返回数据
- 回表：需要（如果查询列不在索引中）
- 性能：较慢（两次IO）
- 数量：一张表可以有多个
- 示例：name索引、email索引等

最佳实践：
1. 查询时尽量使用聚簇索引（主键）
2. 避免SELECT *，只查询需要的列（可能走覆盖索引）
3. 为高频查询的列建立合适的索引
4. 主键尽量使用自增类型，避免页分裂
5. 定期分析慢查询，优化索引策略
*/
