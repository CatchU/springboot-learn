# 评论服务

非基础组件服务，属于业务服务，在这里用于示例其它基础组件的使用，如：消息中心  
搜索，缓存，分库分表等。

### hosts文件中配置访问域名
开发时在window系统中配置hosts，将dev.comments-mysql-write-0.catchu.com和dev.comments-mysql-write-1.catchu.com指向你的数据库  
生产中也需将online.comments-mysql-write改成你的数据库地址

### 执行数据库脚本
创建数据库comment,然后在不同数据库分别执行相同的脚本，脚本是./scripts/comment.sql

