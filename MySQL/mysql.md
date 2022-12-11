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

### 符号类型运算符

为真返回1，为假返回0，其他情况返回null。

![比较运算符](/Users/jamison/Library/Application Support/typora-user-images/image-20221210174015509.png)

#### 1. 等于= , 不等于!=

- 数字与字符串比较时，字符串若转换数值不成功，会隐式转换为0

![=](/Users/jamison/Library/Application Support/typora-user-images/image-20221211115452430.png)

- 两边都是字符的话，按照ANSI比较

![都是字符](/Users/jamison/Library/Application Support/typora-user-images/image-20221211115804188.png)

- 只要有null结果就为null

![null参与比较](/Users/jamison/Library/Application Support/typora-user-images/image-20221211115915192.png)

- 不能对null进行判断

![=不能对null进行判断](/Users/jamison/Library/Application Support/typora-user-images/image-20221211120359552.png)

#### 2. 安全等于<=>

**为NULL而生**

- 与=区别是可以**对null进行判断**

<img src="/Users/jamison/Library/Application Support/typora-user-images/image-20221211120546886.png" >对null进行判断" />

### 非符号类型运算符

![非符号类型运算符](/Users/jamison/Library/Application Support/typora-user-images/image-20221211121015910.png)

#### 1. IS NULL / IS NOT NULL / ISNULL



![判空](/Users/jamison/Library/Application Support/typora-user-images/image-20221211121345199.png)

#### 2. LEAST / GREATEST

![LEAST/GREATEST](/Users/jamison/Library/Application Support/typora-user-images/image-20221211122202946.png)

#### 3. BETWEEN ... AND

是**闭区间**[6000,8000]

![betweenAnd](/Users/jamison/Library/Application Support/typora-user-images/image-20221211122439979.png)

#### 4. IN / NOT IN

![IN / NOT IN](/Users/jamison/Library/Application Support/typora-user-images/image-20221211122830003.png)

#### 5.  LIKE 模糊查询

%代表**不定数量**的字符，_代表**一个**字符

![LIKE 模糊查询](/Users/jamison/Library/Application Support/typora-user-images/image-20221211123158891.png)

![image-20221211123744913](/Users/jamison/Library/Application Support/typora-user-images/image-20221211123744913.png)

转义字符**\或者自定义转义字符**

![image-20221211123936463](/Users/jamison/Library/Application Support/typora-user-images/image-20221211123936463.png)

#### 6. REGEXP /  RLIKE

![image-20221211124535174](/Users/jamison/Library/Application Support/typora-user-images/image-20221211124535174.png)

![image-20221211124722794](/Users/jamison/Library/Application Support/typora-user-images/image-20221211124722794.png)

## 逻辑运算符

![逻辑运算符](/Users/jamison/Library/Application Support/typora-user-images/image-20221211124801636.png)

**AND可以和OR一起使用但是AND优先级高于OR，注意顺序**

## 位运算符

![位运算符](/Users/jamison/Library/Application Support/typora-user-images/image-20221211125342054.png)

## 运算符的优先级

![运算符的优先级](/Users/jamison/Library/Application Support/typora-user-images/image-20221211125608022.png)

## 习题

![习题](/Users/jamison/Library/Application Support/typora-user-images/image-20221211131948653.png)

```mysql
#1
SELECT last_name,salary
FROM employees 
WHERE salary NOT BETWEEN 5000 AND 12000;
#2
SELECT last_name,department_id
FROM employees 
WHERE department_id IN (20, 50);
#3
SELECT last_name,job_id
FROM employees 
WHERE manager_id IS NULL;
#4
SELECT last_name,salary,commission_pct
FROM employees 
WHERE commission_pct IS NOT NULL;
#5
SELECT last_name
FROM employees 
WHERE last_name LIKE '__a%';
#6
SELECT last_name
FROM employees 
WHERE last_name LIKE '%a%k%' OR last_name LIKE '%k%a%';
#7
SELECT first_name,last_name
FROM employees 
-- WHERE first_name LIKE '%e';
WHERE first_name REGEXP 'e$'
#8
SELECT last_name,job_id
FROM employees 
WHERE department_id BETWEEN 80 AND 100;
#9
SELECT last_name,salary,manager_id
FROM employees 
WHERE manager_id IN (100,101,110);
```

