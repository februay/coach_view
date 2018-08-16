


服务器：

host： 207.148.82.48
user： root
password： 5eW)8gjTM{vt_G7W

mysql：

  mysql -uroot -pclairnet123 -P3306 
 
  mysql -ucoach_view -P3306  -p    （coach_view123!@#）

  mysql -h207.148.82.48 -ucoach_view -P3306 -p 

访问地址：http://207.148.82.48:5555/swagger-ui.html

=============================

创建数据库：

CREATE DATABASE coach_view default character set utf8mb4 collate utf8mb4_general_ci;

创建用户：


CREATE USER 'coach_view'@'localhost' IDENTIFIED BY 'coach_view123!@#';
GRANT ALL PRIVILEGES ON coach_view.* TO 'coach_view'@'localhost';
flush privileges;

CREATE USER 'coach_view'@'%' IDENTIFIED BY 'coach_view123!@#';
GRANT ALL PRIVILEGES ON coach_view.* TO 'coach_view'@'%';
flush privileges;

修改密码:

　方法1: mysqladmin -u用户名 -p旧密码 password 新密码。 

  方法2: set password for root@localhost = password('123');  

==============================

测试地址:

http://47.92.95.175/login

-------

http://gamma.weizehao.com/ 

用户：
13012345678 - 进入管理后台
13112345678 - 进入前台管理员
其他任意 - 进入场次数据


