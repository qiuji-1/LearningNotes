-- 测试3: 主键类型对比(UUID主键-不推荐)
-- 说明: 测试UUID主键导致的页分裂问题

-- 测试2: UUID主键(不推荐)
DROP TABLE IF EXISTS user_uuid;
CREATE TABLE user_uuid (
    id CHAR(36) PRIMARY KEY,  -- UUID字符串
    name VARCHAR(50)
);

-- 插入测试数据(使用UUID函数)
INSERT INTO user_uuid (id, name)
VALUES (UUID(), '张三'), 
       (UUID(), '李四'), 
       (UUID(), '王五'), 
       (UUID(), '赵六'), 
       (UUID(), '钱七');

-- 查询数据
SELECT * FROM user_uuid;

-- 分析表
SHOW TABLE STATUS LIKE 'user_uuid';

-- 查看索引大小
SHOW INDEX FROM user_uuid;
