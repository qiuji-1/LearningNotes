# Git 的使用技巧

## 一、四个应用

git bash、git cmd、git gui、git release notes

### 1. git bash

是 Git for Windows 提供的一个命令行环境，模拟了 Linux/Unix 下的 Bash shell。它会启动一个基于 MinGW 的终端，让你在 Windows 上也能使用 ls、ssh、git 等 Unix 风格命令。（要用Linux语言）

### 2. git cmd

是通常指在 Windows 命令提示符（cmd.exe）中使用 Git。Git for Windows 安装时会将 git 命令添加到系统 PATH，所以你可以直接在 cmd 里输入 git 命令。

### 3. git gui

是 Git 官方自带的一个简易图形界面，基于 Tcl/Tk 开发。安装 Git for Windows 或 Linux 包后通常会自动附带。

### 4. git release notes

是文档，不是可执行的程序或界面。它是 Git 官方在每次发布新版本时（如 v2.40.0）发布的说明文件，记录新功能、性能改进、兼容性变化、Bug 修复等。

---

## 二、命令

### Git 基础命令

#### 查看 Git 版本

```bash
git --version
```

#### 查看 Git Bash 当前使用的文本编辑器

```bash
git config --global core.editor
```

#### 查看 Git 的安装位置

```bash
where git
```

#### 查看 Git 的全局配置文件

```bash
git config --global --edit     //默认编辑器
```

---

### 对于第一次正式的存放 Git Hub 文件

#### 1. 进入你的项目路径

```bash
cd "你的项目路径"       //windows
```

#### 2. 初始化本地 Git 仓库

```bash
git init
```

#### 3. 添加所有文件到暂存区

```bash
git add .
```

#### 4. 提交到本地仓库

```bash
git commit -m "初次提交"
```

#### 5. 关联远程 Git Hub 仓库

```bash
git remote add origin https://github.com/你的用户名/仓库名.git
//将地址替换为你复制的实际地址
```

#### 6. 推送代码到 Git Hub

```bash
git branch                         //先确认当前分支名
git push -u origin master          //如果显示的是*master
git push -u origin main            //如果显示的是*main
```

到此你的项目就已经被放到 Git Hub 上了。

---

### 重命名并关联本地分支

```bash
git branch -M main          //重命名本地分支
git push -u origin main     //推送并关联远程 main 分支
```

---

### 如何新建分支

#### 1. 新建并切换分支

```bash
git branch dev      //创建分支
git checkout dev    //切换分支
git checkout -b dev    //新建并切换分支，相当于上两句的整合版
```

#### 2. 查看当前分支

```bash
git branch        //有*的就是当前分支
```

#### 3. 将新分支推送到远程仓库（GitHub）

```bash
git push -u origin dev        /*这是当你第一次推送新分支时，
                                需要告诉 Git 关联远程分支*/
//git push 是推送 ;-u 是关联
```

#### 4. 在远程仓库（GitHub）上确认

刷新 git hub 页面即可

---

## 版本控制管理工具

![版本控制管理工具](../原始文件/版本控制管理工具.png)
