# MySQL 测试代码 - 2026年4月

> 作者：秋霁
> 创建日期：2026年4月3日

---

## 📁 文件说明

本目录包含 2026年4月2日 MySQL 学习中所有题目的测试代码。

## 📋 测试文件列表

| 题号 | 文件名 | 说明 |
|------|--------|------|
| 1 | [Question01_StorageEngines.sql](Question01_StorageEngines.sql) | MySQL的存储引擎有哪些？ |
| 2 | [Question02_ClusterIndex.sql](Question02_ClusterIndex.sql) | InnoDB引擎中的聚簇索引和非聚簇索引有什么区别？ |
| 3 | [Question03_IndexTypes.sql](Question03_IndexTypes.sql) | MySQL的索引类型有哪些？ |

---

## 🚀 如何使用

### 准备工作

1. **安装 MySQL**：
   - 确保 MySQL 5.7 或更高版本已安装
   - 8.0 及以上版本支持更多新特性（如 CTE、窗口函数）

2. **启动 MySQL 服务**：
   ```bash
   # Windows
   net start mysql

   # Linux/Mac
   sudo systemctl start mysql
   # 或
   sudo service mysql start
   ```

3. **创建测试数据库**：
   ```bash
   mysql -u root -p
   ```

   ```sql
   CREATE DATABASE IF NOT EXISTS test_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   USE test_db;
   ```

---

### 运行SQL文件

#### 方法1：命令行执行

```bash
# 执行单个SQL文件
mysql -u root -p test_db < Question01_StorageEngines.sql

# 或在MySQL命令行中执行
mysql -u root -p
USE test_db;
SOURCE /path/to/Question01_StorageEngines.sql;
```

#### 方法2：MySQL Workbench

1. 打开 MySQL Workbench
2. 连接到本地 MySQL 服务器
3. 选择 `test_db` 数据库
4. 点击 File → Run SQL Script
5. 选择对应的 .sql 文件执行

#### 方法3：Navicat / DBeaver

1. 连接到 MySQL 服务器
2. 打开对应的 SQL 文件
3. 选中 SQL 语句执行

---

## 📝 注意事项

1. **所有测试代码均可独立运行**
2. **执行前请确保 MySQL 服务已启动**
3. **部分脚本需要先执行表创建语句**
4. **建议按顺序执行**：先建表，再插入数据，最后查询
5. **如果遇到错误**，请检查：
   - MySQL 版本是否支持相关语法
   - 数据库编码是否正确（推荐 utf8mb4）
   - 表名或字段名是否与保留字冲突
   - SQL 语句分隔符是否正确（默认为分号）

---

## 🔗 相关文档

- [学习笔记](../MySQL2026-04-02.md)
- [MySQL 官方文档](https://dev.mysql.com/doc/)
- [MySQL 参考手册](https://dev.mysql.com/doc/refman/8.0/en/)
