-- ============================================
-- 题目3：MySQL的索引类型有哪些？
-- 演示各种索引类型的使用场景和特点
-- ============================================

-- 创建测试数据库
CREATE DATABASE IF NOT EXISTS test_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE test_db;

-- ============================================
-- 1. 创建测试表
-- ============================================

-- 包含多种字段，用于演示不同类型的索引
CREATE TABLE index_test (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    age INT,
    email VARCHAR(100),
    gender ENUM('男', '女'),
    address VARCHAR(200),
    content TEXT,
    tags VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 插入测试数据
INSERT INTO index_test (name, age, email, gender, address, content, tags) VALUES
('张三', 25, 'zhangsan@example.com', '男', '浙江省杭州市西湖区', 'MySQL数据库优化技巧', 'MySQL,优化,索引'),
('李四', 30, 'lisi@example.com', '男', '浙江省杭州市滨江区', 'InnoDB存储引擎使用指南', 'MySQL,InnoDB,引擎'),
('王五', 28, 'wangwu@example.com', '女', '浙江省杭州市余杭区', '聚簇索引和非聚簇索引的区别', 'MySQL,聚簇,索引'),
('赵六', 32, 'zhaoliu@example.com', '女', '浙江省杭州市拱墅区', 'MySQL索引类型全解析', 'MySQL,索引,类型'),
('孙七', 27, 'sunqi@example.com', '男', '浙江省杭州市萧山区', 'B+树索引的实现原理', 'MySQL,B+树,原理'),
('周八', 29, 'zhouba@example.com', '女', '浙江省杭州市西湖区', '联合索引的最左前缀原则', 'MySQL,联合,索引'),
('吴九', 31, 'wujia@example.com', '男', '浙江省杭州市西湖区', '覆盖索引的概念和应用', 'MySQL,覆盖,索引'),
('郑十', 33, 'zhengshi@example.com', '女', '浙江省杭州市滨江区', 'MySQL事务隔离级别详解', 'MySQL,事务,隔离');

-- ============================================
-- 2. 主键索引（聚簇索引）
-- ============================================

-- 查看表结构（id已经是主键索引）
SHOW CREATE TABLE index_test;

-- 使用主键查询（最优）
EXPLAIN SELECT * FROM index_test WHERE id = 5;

-- 实际查询
SELECT * FROM index_test WHERE id = 5;

/*
主键索引特点：
- ✅ 唯一且非空
- ✅ 一张表只能有一个
- ✅ 自动创建聚簇索引
- ✅ 查询效率最高（一次B+树查找）
- ✅ 数据按主键物理顺序存储
*/

-- ============================================
-- 3. 唯一索引（Unique Index）
-- ============================================

-- 创建唯一索引（email唯一）
CREATE UNIQUE INDEX idx_unique_email ON index_test(email);

-- 尝试插入重复email（会失败）
-- INSERT INTO index_test (name, age, email, gender) VALUES
-- ('测试', 25, 'lisi@example.com', '男');

-- 查看索引信息
SHOW INDEX FROM index_test;

-- 实际查询（使用唯一索引）
EXPLAIN SELECT * FROM index_test WHERE email = 'wangwu@example.com';

SELECT * FROM index_test WHERE email = 'wangwu@example.com';

/*
唯一索引特点：
- ✅ 保证列值不重复
- ✅ 允许有多个NULL
- ✅ 可以建立多个
- ✅ 查询效率高（B+树）
- ✅ 适合email、手机号等唯一字段
*/

-- ============================================
-- 4. 普通索引（Normal Index）
-- ============================================

-- 创建普通索引（name可以重复）
CREATE INDEX idx_name ON index_test(name);

-- 创建普通索引（age）
CREATE INDEX idx_age ON index_test(age);

-- 查看索引信息
SHOW INDEX FROM index_test;

-- 实际查询（使用普通索引）
EXPLAIN SELECT * FROM index_test WHERE name = '张三';

SELECT * FROM index_test WHERE name = '张三';

/*
普通索引特点：
- ✅ 没有唯一约束
- ✅ 纯粹为了加速查询
- ✅ 可以建立多个
- ✅ 查询效率高（B+树）
- ✅ 适合频繁查询但不唯一的字段
*/

-- ============================================
-- 5. 联合索引（Composite Index）- 遵循最左前缀原则
-- ============================================

-- 创建联合索引（name, age）
CREATE INDEX idx_name_age ON index_test(name, age);

-- 创建联合索引（name, age, gender）
CREATE INDEX idx_name_age_gender ON index_test(name, age, gender);

-- 查看索引信息
SHOW INDEX FROM index_test;

-- ============================================
-- 测试最左前缀原则
-- ============================================

-- ✅ 能用索引：从最左边开始匹配
EXPLAIN SELECT * FROM index_test WHERE name = '张三';
EXPLAIN SELECT * FROM index_test WHERE name = '张三' AND age = 25;
EXPLAIN SELECT * FROM index_test WHERE name = '张三' AND age = 25 AND gender = '男';

-- ⚠️ 部分能用：跳过左边列，从第二个开始
EXPLAIN SELECT * FROM index_test WHERE age = 25;
EXPLAIN SELECT * FROM index_test WHERE age = 25 AND gender = '男';

-- ❌ 不能用索引：违背最左前缀原则
EXPLAIN SELECT * FROM index_test WHERE gender = '男';
EXPLAIN SELECT * FROM index_test WHERE gender = '男' AND age = 25;

-- 对比查询性能
SELECT * FROM index_test WHERE name = '王五' AND age = 28;
SELECT * FROM index_test WHERE age = 28 AND name = '王五';

/*
联合索引特点：
- ✅ 多列组合成一个索引
- ✅ 遵循最左前缀原则（必须从最左边列开始）
- ✅ 适合多列组合查询
- ✅ 可以减少索引数量
- ⚠️ 列顺序很重要（选择性高的放前面）
*/

-- ============================================
-- 6. 联合索引中的覆盖索引
-- ============================================

-- ✅ 覆盖索引查询：只需要索引包含的列，不需要回表
EXPLAIN SELECT id, name, age FROM index_test WHERE name = '张三';
SELECT id, name, age FROM index_test WHERE name = '张三';

-- ❌ 非覆盖索引查询：需要回表
EXPLAIN SELECT * FROM index_test WHERE name = '张三';
SELECT * FROM index_test WHERE name = '张三';

/*
覆盖索引特点：
- ✅ 查询的列都在索引中
- ✅ 不需要回表，性能最优
- ✅ EXPLAIN Extra列显示 "Using index"
- ✅ 避免SELECT *，只查询需要的列
*/

-- ============================================
-- 7. 全文索引（Fulltext Index）
-- ============================================

-- 创建包含全文索引的表（content字段）
CREATE TABLE article (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    content TEXT,
    author VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FULLTEXT INDEX ft_title_content (title, content)
) ENGINE=InnoDB;

-- 插入测试数据
INSERT INTO article (title, content, author) VALUES
('MySQL索引优化指南', 'MySQL数据库索引优化是提升查询性能的关键。合理的索引设计可以大幅提高查询效率。', '张三'),
('InnoDB存储引擎使用技巧', 'InnoDB是MySQL的默认存储引擎，支持事务、行锁、外键等特性。', '李四'),
('聚簇索引和非聚簇索引的区别', '聚簇索引的叶子节点存储完整数据，非聚簇索引只存储主键值，查询时需要回表。', '王五'),
('B+树索引的底层实现', 'B+树是MySQL常用的索引结构，支持快速查找和范围扫描。', '赵六'),
('联合索引的最左前缀原则', '联合索引必须从最左边的列开始使用，否则索引失效。', '孙七');

-- 使用全文索引查询（关键词搜索）
EXPLAIN SELECT * FROM article WHERE MATCH(title, content) AGAINST('MySQL 索引' IN NATURAL LANGUAGE MODE);

SELECT * FROM article WHERE MATCH(title, content) AGAINST('MySQL 索引' IN NATURAL LANGUAGE MODE);

-- 传统LIKE查询（无法用普通索引）
EXPLAIN SELECT * FROM article WHERE content LIKE '%MySQL%';

SELECT * FROM article WHERE content LIKE '%MySQL%';

/*
全文索引特点：
- ✅ 适合文本搜索（关键词检索）
- ✅ 支持自然语言模式和布尔模式
- ✅ 可以按相关性排序
- ⚠️ 对中文支持有限（需要ngram分词器）
- ⚠️ 不支持前缀匹配（LIKE 'keyword%'）
- ❌ 不支持范围查询（>、<、BETWEEN）
*/

-- ============================================
-- 8. 索引分析和优化建议
-- ============================================

-- 查看表的索引使用情况
ANALYZE TABLE index_test;

-- 查看索引基数（不重复值的数量）
SELECT 
    TABLE_NAME,
    INDEX_NAME,
    COLUMN_NAME,
    CARDINALITY,
    SEQ_IN_INDEX
FROM 
    information_schema.STATISTICS
WHERE 
    TABLE_SCHEMA = 'test_db'
    AND TABLE_NAME = 'index_test'
ORDER BY 
    TABLE_NAME,
    INDEX_NAME,
    SEQ_IN_INDEX;

-- ============================================
-- 9. 清理测试数据
-- ============================================

-- 删除所有测试表
-- ============================================
-- 7.5. 查看插入的数据
-- ============================================

-- 查看用户表数据
SELECT '=== 用户表数据 ===' AS '';
SELECT * FROM user LIMIT 5;

-- 查看文章表数据
SELECT '=== 文章表数据 ===' AS '';
SELECT * FROM article LIMIT 3;

-- 查看索引测试表数据
SELECT '=== 索引测试表数据 ===' AS '';
SELECT * FROM index_test LIMIT 5;

-- ============================================
-- 8. 清理测试数据
-- ============================================

-- 删除所有测试表
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS index_test;
DROP TABLE IF EXISTS article;

-- 删除测试数据库
-- DROP DATABASE IF EXISTS test_db;

-- ============================================
-- 总结：MySQL索引类型汇总
-- ============================================

/*
索引类型对比：

1. 主键索引（Primary Key）：
   - 特点：唯一、非空、一张表一个
   - 实现：聚簇索引
   - 场景：唯一标识字段（id）

2. 唯一索引（Unique）：
   - 特点：唯一、允许多NULL
   - 实现：非聚簇索引
   - 场景：email、手机号等唯一字段

3. 普通索引（Normal）：
   - 特点：无约束、多个
   - 实现：非聚簇索引
   - 场景：频繁查询的字段

4. 联合索引（Composite）：
   - 特点：多列组合、最左前缀
   - 实现：非聚簇索引
   - 场景：多列组合查询

5. 全文索引（Fulltext）：
   - 特点：文本搜索、关键词匹配
   - 实现：倒排索引
   - 场景：文章内容搜索

最佳实践：
1. 为WHERE、ORDER BY、JOIN列建立索引
2. 避免SELECT *，只查询需要的列
3. 选择性高的列放联合索引前面
4. 定期分析慢查询，优化索引
5. 避免过度索引，影响写入性能
6. 使用EXPLAIN分析查询执行计划
*/
