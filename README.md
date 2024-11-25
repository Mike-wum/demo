<h1 style="text-align: center">Book Store</h1>

### 项目简介
使用springboot开发的一个示例程序，使用idea导入项目，gradle加载依赖。

### 数据库
数据库使用mysql，脚本在db目录下，默认连接如下，如需修改请更改applicatioin.yml

url: jdbc:mysql://127.0.0.1:3306/bookstore

username: root

password: root@2022

### 用户注册与登录
首先执行UserTests.testRegister方法创建用户，然后执行UserTests.testLogin进行用户登录。

登录后，console窗口会有一个token输出token:eyJhbGciOiJIUzUxM...

项目中使用spring security实现了jwt用户认证，将用户登录后的token复制到BaseTest.init方法中，后续测试案例会在http header中设置token，服务器会对每次请求验证用户。

认证实现详见com.demo.bookstore.security

### 业务功能和订单处理
在创建订单时会扣减库存，使用sql where条件控制并发的情况，如果扣减库存失败，会回滚事务。

在取消订单时会将库存加回来。

所有业务restful请求详见Controller，都可以在test包下面测试。

### 异常
异常处理在com.demo.bookstore.common.exception下，在捕获异常后会转成HttpStatus code返回给客户端。

### 日志
使用aop记录每个controller方法的输入输出，详见com.demo.bookstore.common.aspect.LogAspect

### 缓存
使用spring cache缓存book数据，详见com.demo.bookstore.service.impl.BookServiceImpl

### 部署
Dockerfile在docker目录下，将打包完的jar包放在同级目录下就可以build image
