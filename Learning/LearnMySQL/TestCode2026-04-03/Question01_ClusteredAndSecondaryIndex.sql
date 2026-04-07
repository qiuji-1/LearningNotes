-- 测试1: 聚簇索引和二级索引
-- 说明: 演示通过二级索引查询时需要回表的过程

-- 准备测试表
DROP TABLE IF EXISTS user;
CREATE TABLE user (
    id BIGINT PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(100),
    balance DECIMAL(10,2)
);

-- 创建二级索引
CREATE INDEX idx_name ON user(name);

-- 插入测试数据
INSERT INTO user (id, name, email, balance) VALUES
(1, '张三', 'zhangsan@example.com', 1000.00),
(2, '李四', 'lisi@example.com', 2000.00),
(3, '王五', 'wangwu@example.com', 3000.00);

-- 执行查询: 通过二级索引查询
-- 流程:
-- 1. 在name索引树中找到name='张三'的记录，得到id=1
-- 2. 拿着id=1去主键索引树中查找完整数据
-- 3. 返回完整数据行
SELECT * FROM user WHERE name = '张三';

-- 查看执行计划,验证是否回表
EXPLAIN SELECT * FROM user WHERE name = '张三';

-- 对比: 直接通过主键查询(不回表)
EXPLAIN SELECT * FROM user WHERE id = 1;
