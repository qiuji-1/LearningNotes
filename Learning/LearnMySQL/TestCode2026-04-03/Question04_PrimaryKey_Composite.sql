-- 测试4: 复合主键(不推荐)
-- 说明: 测试复合主键的场景

-- 测试3: 复合主键(不推荐)
DROP TABLE IF EXISTS user_composite;
CREATE TABLE user_composite (
    user_id INT,
    order_id INT,
    PRIMARY KEY (user_id, order_id),  -- 8字节
    name VARCHAR(50)
);

-- 插入测试数据
INSERT INTO user_composite (user_id, order_id, name)
VALUES 
(1, 1, '张三'), 
(1, 2, '李四'), 
(1, 3, '王五'), 
(2, 1, '赵六'), 
(2, 2, '钱七');

-- 查询数据
SELECT * FROM user_composite;

-- 分析表
SHOW TABLE STATUS LIKE 'user_composite';
