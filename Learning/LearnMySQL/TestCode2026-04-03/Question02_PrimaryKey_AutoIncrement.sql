-- 测试2: 主键类型对比(自增ID vs UUID)
-- 说明: 测试不同主键类型对页分裂和性能的影响

-- 测试1: 自增主键(推荐)
DROP TABLE IF EXISTS user_auto_increment;
CREATE TABLE user_auto_increment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);

-- 插入测试数据
INSERT INTO user_auto_increment (name)
VALUES ('张三'), ('李四'), ('王五'), ('赵六'), ('钱七');

-- 查询数据
SELECT * FROM user_auto_increment;

-- 分析表
SHOW TABLE STATUS LIKE 'user_auto_increment';
