# 第一章、基本的SELECT语句

## SQL分类

**DDL**：数据定义语言。create, alter, drop, rename, **truncate(只能作用于表，清除表中数据，不能回退)**

**DML**：数据操作语言。insert, delete, update, select

**DCL**：数据控制语言。commit, rollback, savepoint, grant, revoke

有人将select拿出来叫**DQL**，把commit, rollback拿出来叫**TCL**（Transaction Control Language）。

## SQL语言的规则和规范

### 1.基本规则

- sql可以写在一行或多行。我饿了提高可读性，各子句分行写，必要时使用缩进。
- 每条命令以;或者\g或者\G结束。
- 关键字不能分行，不要缩写。
- 关于标点符号
	- 必须保证所有的()，单引号，双引号都是成对结束的
	- 字符串型和日期时间类型可以使用单引号表示
	- 列的别名尽量使用双引号，而且不建议省略as

### 2.SQL大小写
- Mysql在Windows环境下是大小写不敏感的。
- Mysql在Linux环境下是大小写敏感的。
	- 数据库名，表名，表的别名，变量名是严格区分大小写的。
	- 关键字，函数名，列名，列的别名忽略大小写的。
- 推荐采用统一的书写规范：
	- 数据库名，表名，表别名，字段名，字段别名等都小写。
	- SQL关键字，函数名， 绑定变量等都大写。

### 3.注释

```mysql
#注释
-- 注释（--后面必须包含一个空格。）
/*注释*/
```

### 4.命名规则
- 数据库，表名不得超过30字符，变量名限制为29个
- 必须只能包含A-Z,a-z,0-9,_共63个字符
- 数据库名，表名，字段名等对象名中间不要包含空格
- 同一个mysql软件中，数据库不能同名；同一个库中表不能同名；同一个表中，字段不能重名
- 必须保证你的字段没有保留字、数据库系统或常用方法冲突。如果坚持使用，请用`（着重号）引起来
- 保存字段名和类型的一致性，在命名字段并为其指定数据类型的时候一定要保证一致性。加入数据类型在一个表里是整数，那在另一个表里可别变成字符型了

## 基本的SELECT语句

### 1. 列的别名

空格之后或者AS之后，最好都使用使用双引号引起来。

### 2. 去除重复行

注意DISTINCT的位置。

```mysql
SELECT employee_id, DISTINCT department_id
FROM employees
```

### 3. 空值参与运算
1. 空值：null
2. null不等同于0
3. 空值参与运算，结果也 为空

![空值参与运算](/Users/jamison/Library/Application Support/typora-user-images/image-20221210163740933.png)

4. 解决方法

```mysql
SELECT employee_id, salary "月工资", salary * (1 + IFNULL(commission_pct, 0)) * 12 "年工资"
FROM employees
```



### 4. 着重号

当表名和关键字冲突时使用

```mysql
SELECT * FROM `order`
```



### 5. 查询常数

![查询常数](/Users/jamison/Library/Application Support/typora-user-images/image-20221210165758197.png)



### 6. 显示表结构

DESCRIBE或者DESC

![显示表结构](/Users/jamison/Library/Application Support/typora-user-images/image-20221210170116889.png)

###  7. 过滤条件

WHERE，必须放在select后面

```mysql
SELECT employee_id, first_name, last_name
FROM employees
WHERE department_id = 90;
```



# 第二章、运算符

## 算数运算符

/和%还可以分别用DIV和MOD表示

![算术运算符](/Users/jamison/Library/Application Support/typora-user-images/image-20221210172003273.png)

### 1. 加法

![add](/Users/jamison/Library/Application Support/typora-user-images/image-20221210172542002.png)

MySQL**字符串会隐式转换**

DUAL是虚表

### 2. 乘除

![算数运算符综合](/Users/jamison/Library/Application Support/typora-user-images/image-20221210172841963.png)

**注意除数为0时不报错，返回NULL。**

### 3. 取余

![取余](/Users/jamison/Library/Application Support/typora-user-images/image-20221210173540290.png)

**可见符号只与被取余数有关**

## 比较运算符

![比较运算符](/Users/jamison/Library/Application Support/typora-user-images/image-20221210174015509.png)

### 1. 
