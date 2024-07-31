# SNS社交网络系统


## 前端

vue + axios
## 后端
SpringBoot + MyBatis
## 数据库
MySQL

## 计划

前端先用server.js模拟后端 

后端根据接口文档编写

数据库设计

## 文档
文档在doc文件夹下，序号8,12,14不用写。

## 进度
全部功能模块已完成，测试通过。

## 使用说明
在application.properties中配置数据库连接信息。

在src/main/resources/db中分别运行shema.sql和data.sql文件，导入数据库。

然后使用Maven导入依赖项。

在src/main/frontend 中，使用
```
npm install
npm install axios
npm install cors
```
下载依赖。

然后启动后端src/main/java/com/sns/SnsApplication.java

在src/main/frontend中使用
```
npm run serve
```
启动前端。

进入http://localhost:8080/ 即可访问。

用于测试的账号+密码

**管理员：1 1**

**在校学生：2 2**

**已就业学生：3 3**
