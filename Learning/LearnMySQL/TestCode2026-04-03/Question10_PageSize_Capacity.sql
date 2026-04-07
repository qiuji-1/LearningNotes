-- 测试10: 三层B+树的容量计算和页大小影响
-- 说明: 计算不同页大小下B+树的容量

-- 查看当前页大小
SHOW VARIABLES LIKE 'innodb_page_size';

-- 查看表统计信息(已存在的表)
-- SELECT 
--     TABLE_NAME,
--     ROUND(AVG_ROW_LENGTH) AS avg_row_length,
--     ROUND(AVG_ROW_LENGTH, 0) AS avg_row_length_rounded
-- FROM information_schema.TABLES
-- WHERE TABLE_SCHEMA = DATABASE()
--   AND TABLE_NAME IN ('user_auto_increment', 'user_uuid')
-- ORDER BY TABLE_NAME;

-- 计算理论容量
-- 假设:
-- - 主键BIGINT: 8字节
-- - 指针: 6字节
-- - 页大小: 16KB(默认)

-- 计算公式:
-- 非叶子节点key数 = 页大小 / (主键长度 + 指针长度)
-- 非叶子节点key数 = 16384 / (8 + 6) = 16384 / 14 = 1170个key

SELECT '三层B+树容量计算(页大小16KB, 主键BIGINT)' AS description;

SELECT 
    16384 AS page_size_bytes,
    8 AS primary_key_size,
    6 AS pointer_size,
    16384 / (8 + 6) AS non_leaf_keys_per_node,
    POWER(16384 / (8 + 6), 2) AS leaf_nodes;

-- 不同页大小下的容量对比
SELECT 
    '页大小(字节)' AS page_size,
    '非叶子节点key数' AS keys_per_node,
    '叶子节点数' AS leaf_nodes,
    '三层容量(条/页)' AS rows_per_page,
    '总容量(万条)' AS total_capacity_millions
FROM (
    SELECT 
        4096 AS page_size,
        FLOOR(4096 / 14) AS keys_per_node,
        POWER(FLOOR(4096 / 14), 2) AS leaf_nodes,
        FLOOR(4096 / 1024) AS rows_per_page,
        ROUND(POWER(FLOOR(4096 / 14), 2) * FLOOR(4096 / 1024) / 10000, 2) AS total_capacity_millions
    
    UNION ALL
    
    SELECT 
        8192 AS page_size,
        FLOOR(8192 / 14) AS keys_per_node,
        POWER(FLOOR(8192 / 14), 2) AS leaf_nodes,
        FLOOR(8192 / 1024) AS rows_per_page,
        ROUND(POWER(FLOOR(8192 / 14), 2) * FLOOR(8192 / 1024) / 10000, 2) AS total_capacity_millions
    
    UNION ALL
    
    SELECT 
        16384 AS page_size,
        FLOOR(16384 / 14) AS keys_per_node,
        POWER(FLOOR(16384 / 14), 2) AS leaf_nodes,
        FLOOR(16384 / 1024) AS rows_per_page,
        ROUND(POWER(FLOOR(16384 / 14), 2) * FLOOR(16384 / 1024) / 10000, 2) AS total_capacity_millions
    
    UNION ALL
    
    SELECT 
        32768 AS page_size,
        FLOOR(32768 / 14) AS keys_per_node,
        POWER(FLOOR(32768 / 14), 2) AS leaf_nodes,
        FLOOR(32768 / 1024) AS rows_per_page,
        ROUND(POWER(FLOOR(32768 / 14), 2) * FLOOR(32768 / 1024) / 10000, 2) AS total_capacity_millions
    
    UNION ALL
    
    SELECT 
        65536 AS page_size,
        FLOOR(65536 / 14) AS keys_per_node,
        POWER(FLOOR(65536 / 14), 2) AS leaf_nodes,
        FLOOR(65536 / 1024) AS rows_per_page,
        ROUND(POWER(FLOOR(65536 / 14), 2) * FLOOR(65536 / 1024) / 10000, 2) AS total_capacity_millions
) AS page_size_comparison;

-- 不同主键类型对容量的影响(页大小16KB)
SELECT 
    '主键类型' AS pk_type,
    '主键长度(字节)' AS pk_size,
    '索引项大小' AS index_item_size,
    '非叶子节点key数' AS keys_per_node,
    '三层容量(万条)' AS total_capacity_millions
FROM (
    SELECT 
        'INT' AS pk_type,
        4 AS pk_size,
        4 + 6 AS index_item_size,
        FLOOR(16384 / (4 + 6)) AS keys_per_node,
        ROUND(POWER(FLOOR(16384 / (4 + 6)), 2) * 16 / 10000, 2) AS total_capacity_millions
    
    UNION ALL
    
    SELECT 
        'BIGINT' AS pk_type,
        8 AS pk_size,
        8 + 6 AS index_item_size,
        FLOOR(16384 / (8 + 6)) AS keys_per_node,
        ROUND(POWER(FLOOR(16384 / (8 + 6)), 2) * 16 / 10000, 2) AS total_capacity_millions
    
    UNION ALL
    
    SELECT 
        'CHAR(36)' AS pk_type,
        36 AS pk_size,
        36 + 6 AS index_item_size,
        FLOOR(16384 / (36 + 6)) AS keys_per_node,
        ROUND(POWER(FLOOR(16384 / (36 + 6)), 2) * 16 / 10000, 2) AS total_capacity_millions
) AS pk_type_comparison;

-- 不同行大小对容量的影响
SELECT 
    '行大小(字节)' AS row_size,
    '每页记录数' AS rows_per_page,
    '三层容量(万条)' AS total_capacity_millions
FROM (
    SELECT 
        200 AS row_size,
        FLOOR(16384 / 200) AS rows_per_page,
        ROUND(POWER(FLOOR(16384 / 14), 2) * FLOOR(16384 / 200) / 10000, 2) AS total_capacity_millions
    
    UNION ALL
    
    SELECT 
        1024 AS row_size,
        FLOOR(16384 / 1024) AS rows_per_page,
        ROUND(POWER(FLOOR(16384 / 14), 2) * FLOOR(16384 / 1024) / 10000, 2) AS total_capacity_millions
    
    UNION ALL
    
    SELECT 
        5120 AS row_size,
        FLOOR(16384 / 5120) AS rows_per_page,
        ROUND(POWER(FLOOR(16384 / 14), 2) * FLOOR(16384 / 5120) / 10000, 2) AS total_capacity_millions
) AS row_size_comparison;
