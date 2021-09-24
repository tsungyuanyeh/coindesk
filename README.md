# coindesk API

## Init

Create DB (Mysql)

1.Recommend docker https://myapollo.com.tw/zh-tw/docker-mysql/
```
$ docker run --name=mysql8 -d -p 3306:3306 --env MYSQL_ROOT_PASSWORD=@1987sUPERmAN mysql/mysql-server:8.0
```
2.Login and add setting for use tools
```
docker exec -it mysql8 mysql -uroot -p
```
```
mysql> ALTER USER 'root'@'localhost' IDENTIFIED BY '@2021sUPERmAN';
mysql> UPDATE mysql.user SET HOST='%' WHERE USER = 'root' LIMIT 1;
mysql> FLUSH PRIVILEGES;
```
then you can use tools app to view or change db
3.Create database/table
```
mysql> CREATE DATABASE coin_demo;
mysql> use coin_demo;
mysql> create table coin_currency(
   id INT NOT NULL AUTO_INCREMENT,
   code VARCHAR(10) NOT NULL,
   symbol VARCHAR(20) NOT NULL,
   description VARCHAR(100) NOT NULL,
   rate Float( 12, 4 ) NOT NULL,
   PRIMARY KEY ( id )
);
```

## Upload to gitHub

https://gitbook.tw/chapters/github/push-to-github.html

## Start to use

http://localhost:8080/