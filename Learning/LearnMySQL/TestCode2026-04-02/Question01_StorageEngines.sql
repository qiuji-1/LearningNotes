-- ============================================
-- 题目1：MySQL的存储引擎有哪些？
-- 演示不同存储引擎的特点和使用场景
-- ============================================

-- 创建测试数据库
CREATE DATABASE IF NOT EXISTS test_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE test_db;

-- ============================================
-- 1. 查看MySQL支持的存储引擎
-- ============================================

SHOW ENGINES;

-- ============================================
-- 2. InnoDB存储引擎演示（默认引擎）
-- ============================================

-- 创建InnoDB表（支持事务、行级锁、外键）
CREATE TABLE user_innodb (
    id INT AUTO_INCREMENT PRIMARY KEY,
  yy  username VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    age INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB;

-- 插入测试数据
INSERT INTO user_innodb (username, email, age) VALUES
('张三', 'zhangsan@example.com', 25),
('李四', 'lisi@example.com', 30),
('王五', 'wangwu@example.com', 28);

-- 演示事务（InnoDB支持）
START TRANSACTION;
UPDATE user_innodb SET age = 26 WHERE username = '张三';
INSERT INTO user_innodb (username, email, age) VALUES ('赵六', 'zhaoliu@example.com', 32);
COMMIT;

-- 查看表结构
SHOW CREATE TABLE user_innodb;

-- ============================================
-- 3. MyISAM存储引擎演示
-- ============================================

-- 创建MyISAM表（不支持事务，只有表级锁）
CREATE TABLE user_myisam (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    age INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=MyISAM;

-- 插入测试数据
INSERT INTO user_myisam (username, email, age) VALUES
('张三', 'zhangsan@example.com', 25),
('李四', 'lisi@example.com', 30);

-- 演示MyISAM不支持事务（会报错）
-- START TRANSACTION;
-- UPDATE user_myisam SET age = 26 WHERE username = '张三';
-- COMMIT;

-- 查看表结构
SHOW CREATE TABLE user_myisam;

-- ============================================
-- 4. MEMORY存储引擎演示
-- ============================================

-- 创建MEMORY表（数据存储在内存中）
CREATE TABLE user_memory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    age INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=MEMORY;

-- 插入测试数据
INSERT INTO user_memory (username, email, age) VALUES
('张三', 'zhangsan@example.com', 25),
('李四', 'lisi@example.com', 30);

-- 查看表结构
SHOW CREATE TABLE user_memory;

-- ============================================
-- 5. Archive存储引擎演示
-- ============================================

-- 创建Archive表（专门存归档数据，只支持INSERT和SELECT）
CREATE TABLE log_archive (
    id INT AUTO_INCREMENT PRIMARY KEY,
    log_level VARCHAR(20),
    log_message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=ARCHIVE;

-- 插入测试数据
INSERT INTO log_archive (log_level, log_message) VALUES
('INFO', '系统启动成功'),
('ERROR', '数据库连接失败'),
('INFO', '用户登录成功');

-- 注意：Archive引擎不支持UPDATE和DELETE
-- UPDATE log_archive SET log_message = '更新' WHERE id = 1;
-- DELETE FROM log_archive WHERE id = 1;

-- ============================================
-- 6. 查看表使用的存储引擎
-- ============================================

-- 查看所有表的引擎
SELECT 
    TABLE_NAME,
    TABLE_COMMENT,
    ENGINE
FROM 
    information_schema.TABLES
WHERE 
    TABLE_SCHEMA = 'test_db';

-- 查看特定表的引擎
SHOW TABLE STATUS LIKE 'user_%';

-- ============================================
-- 6.5. 查看插入的数据
-- ============================================

-- 查看InnoDB表数据
SELECT '=== InnoDB 表数据 ===' AS '';
SELECT * FROM user_innodb;

-- 查看MyISAM表数据
SELECT '=== MyISAM 表数据 ===' AS '';
SELECT * FROM user_myisam;

-- 查看MEMORY表数据
SELECT '=== MEMORY 表数据 ===' AS '';
SELECT * FROM user_memory;

-- ============================================
-- 7. 清理测试数据
-- ============================================

-- 删除所有测试表
DROP TABLE IF EXISTS user_innodb;
DROP TABLE IF EXISTS user_myisam;
DROP TABLE IF EXISTS user_memory;
DROP TABLE IF EXISTS log_archive;

-- 删除测试数据库
-- DROP DATABASE IF EXISTS test_db;

-- ============================================
-- 总结：不同存储引擎的对比
-- ============================================

/*
存储引擎对比：

InnoDB（推荐）：
- ✅ 支持事务（ACID）
- ✅ 支持行级锁
- ✅ 支持外键约束
- ✅ 支持崩溃恢复
- ✅ 适合高并发场景
- ⚠️ 占用空间较大

MyISAM（已淘汰）：
- ✅ 读性能好
- ❌ 不支持事务
- ❌ 只有表级锁
- ❌ 不支持外键
- ❌ 不支持崩溃恢复
- ⚠️ 适合读多写少场景

MEMORY（临时表）：
- ✅ 查询速度极快（内存存储）
- ✅ 适合临时表和会话缓存
- ❌ 数据易失（重启丢失）
- ❌ 不支持BLOB/TEXT类型

Archive（归档）：
- ✅ 压缩率高，节省空间
- ✅ 适合日志归档
- ❌ 只支持INSERT和SELECT
- ❌ 不支持索引（WHERE条件查询慢）
*/
