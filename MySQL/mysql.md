# 附录：常用的SQL标准有哪些

![常用的SQL标准有哪些](/Users/jamison/Library/Application Support/typora-user-images/image-20221216142539271.png)

# 第一章 基本的SELECT语句

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



# 第二章 运算符

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



# 第三章 排序与分页

## 排序

- **如果没有使用排序操作，默认情况下返回的数据是按照添加数据时的顺序显示的**

![image-20221211211503622](/Users/jamison/Library/Application Support/typora-user-images/image-20221211211503622.png)

- **使用 ORDER BY 对查询到的数据进行排序**

升序：ASC（ascend）

降序：DESC（descend）**默认是这个**

![**使用 ORDER BY 对查询到的数据进行排序**](/Users/jamison/Library/Application Support/typora-user-images/image-20221213115642495.png)

- **我们可以使用列的别名进行排序**

![我们可以使用列的别名进行排序](/Users/jamison/Library/Application Support/typora-user-images/image-20221213120044056.png)

注意：列的别名只能在ORDER BY中使用，不能在Where中使用，执行顺序问题，where执行的时候select还没执行，所以列的别名还没加载

![列的别名只能在ORDER BY中使用](/Users/jamison/Library/Application Support/typora-user-images/image-20221213120517390.png)

执行顺序：

![执行顺序](/Users/jamison/Library/Application Support/typora-user-images/image-20221213120745275.png)

- where需要声明在From后，order by 之前

![image-20221213120853924](/Users/jamison/Library/Application Support/typora-user-images/image-20221213120853924.png)

- 二级排序

当某个排序存在相等情况时，启用二级排序，如果还存在一样的就增加排序级数

![二级排序](/Users/jamison/Library/Application Support/typora-user-images/image-20221213122558933.png)

## 分页

为什么要分页

- 查询返回的记录太多了，查看起来很不方便
- 降低网络压力
- 一次请求部分数据，降低延迟提升用户体验
- ......



**mysql使用limit实现数据的分页显示**

- 每页显示pageSize条记录，此时显示第pageNo页
- 公式：LIMIT (pageNo - 1) * pageSize, pageSize
  - 每页显示20条记录，此时显示第一页

  ![image-20221216111712731](/Users/jamison/Library/Application Support/typora-user-images/image-20221216111712731.png)

  - 每页显示20条记录，此时显示第二页

  ![image-20221216111825880](/Users/jamison/Library/Application Support/typora-user-images/image-20221216111825880.png)

**MySql 8.0的新特性：LIMIT pageSize OFFSET offset**

![image-20221216112746265](/Users/jamison/Library/Application Support/typora-user-images/image-20221216112746265.png)

**排序和分页的课后练习**

![image-20221216120007727](/Users/jamison/Library/Application Support/typora-user-images/image-20221216120007727.png)	解答：

```mysql
#1
SELECT last_name, department_id, salary *(1 + IFNULL(commission_pct, 0)) * 12 AS "annue_salary"
FROM employees
ORDER BY annue_salary DESC, last_name ASC;

#2
SELECT last_name, salary
FROM employees
WHERE salary NOT BETWEEN 8000 AND 17000
ORDER BY salary DESC
LIMIT 20, 20;

#3
SELECT last_name, email, department_id
FROM employees
#WHERE email LIKE '%e%'
WHERE email REGEXP '[e]'
ORDER BY LENGTH(email) DESC, department_id ASC;
```



# 第四章 多表查询

指两个或者多个表一起完成查询操作。

前提条件：这些表之间是有关系的（一对一，一对多），他们之间一定是有关联字段，这个关联字段可能建立了外键，也可能没有外键。比如：员工表和部门表，这两个靠“部门编号”进行关联。

## 一个案例引发的多表查询

### 1. 案例说明

![image-20221216121255013](/Users/jamison/Library/Application Support/typora-user-images/image-20221216121255013.png)

**多表查询的实现方式：**

- 错误方式：出现笛卡尔积错误

  ![image-20221216124155537](/Users/jamison/Library/Application Support/typora-user-images/image-20221216124155537.png)

  笛卡尔积的理解：

  ![image-20221216124806008](/Users/jamison/Library/Application Support/typora-user-images/image-20221216124806008.png)

  ![image-20221216124855846](/Users/jamison/Library/Application Support/typora-user-images/image-20221216124855846.png)

- 正确方式：需要有连接条件

  ![image-20221216124635114](/Users/jamison/Library/Application Support/typora-user-images/image-20221216124635114.png)

### 2. 案例分析和问题解决

- **笛卡尔积错误会在下面条件下产生**

  - 忽略多个表的连接条件（或关联条件）
  - 连接条件（或关联条件）无效
  - 所有表中的所有行互相连接

- 为了避免笛卡尔积，可以在**WHERE加入有效的连接条件**

- 加入连接条件后，查询语法：

  ```mysql
  SELECT table1.column, table2.column
  FROM table1, table2
  WHERE table1.column1 = table2.column2; #连接条件
  ```

  - **在WHERE加入有效的连接条件**



## 多表查询

- 如果查询语句中出现了多个表中都存在的字段，则必须指明此字段所在的表，不明确会报错

  ![image-20221216130702871](/Users/jamison/Library/Application Support/typora-user-images/image-20221216130702871.png)

  **但是从sql优化的角度来说，建议多表查询时，每个字段前都指明其所在的表。不用去每个表查这个字段，直接指定表，节省了时间。**

  ![image-20221216130921810](/Users/jamison/Library/Application Support/typora-user-images/image-20221216130921810.png)

- 可以给表起别名，在select和where字段中使用别名

  ![image-20221216131239767](/Users/jamison/Library/Application Support/typora-user-images/image-20221216131239767.png)

  **注意：**如果给表起了别名，一旦在select和where字段中使用表名的话，则必须使用表的别名，而不能再使用表的原名，否则会报错。

  ![image-20221216131525431](/Users/jamison/Library/Application Support/typora-user-images/image-20221216131525431.png)

  - 练习：查询员工的employee_id, last_name, department_name, city

    ```mysql
    SELECT emp.employee_id, emp.last_name, dept.department_name, loc.city
    FROM employees emp, departments dept, locations loc
    WHERE emp.department_id = dept.department_id
    AND dept.location_id = loc.location_id;
    ```

    **结论：如果有n个表实现多表的查询，则需要至少n-1个连接条件。**

## 多表查询的分类

角度1：等值连接和非等值连接

角度2：自连接和非自连接

角度3：内连接和外连接

###  1.  等值连接和非等值连接

- 非等值连接的例子

  ![image-20221216134305806](/Users/jamison/Library/Application Support/typora-user-images/image-20221216134305806.png)

### 2. 自连接和非自连接

- 练习：查询员工id，员工姓名及其管理者的id和姓名

  ![image-20221216135432295](/Users/jamison/Library/Application Support/typora-user-images/image-20221216135432295.png)

  ![image-20221216135807199](/Users/jamison/Library/Application Support/typora-user-images/image-20221216135807199.png)

### 3. 内连接和外连接

**内连接：**合并具有同一列的两个以上表的行，结果集中不包括一个表和另一个表不匹配的行（简单来说就是不包含不匹配的行，上面写的都是内连接）

**外连接：**合并具有同一列的两个以上表的行，结果集中包括一个表和另一个表不匹配的行之外，还查询到了左表或者右表不匹配的行。

- 外链接的分类：左外连接、右外连接、慢外连接

  ![image-20221216140335134](/Users/jamison/Library/Application Support/typora-user-images/image-20221216140335134.png)

  - 左外连接：两个表在连接过程中除了返回满足连接条件的行以还返回左表中不满足条件的行
  - 右外连接：两个表在连接过程中除了返回满足连接条件的行以还返回右表中不满足条件的行
  - 满外连接：两个表在连接过程中除了返回满足连接条件的行以还返回左表和右表中不满足条件的行

**练习**：查询**所有的**员工的last_name, department_name信息（”所有的“就是提示你使用外连接）

![image-20221216143256037](/Users/jamison/Library/Application Support/typora-user-images/image-20221216143256037.png)

- SQL92语法（见附录）实现内连接：见上，略

- SQL92语法（见附录）实现外连接：使用 +       -------MySQL不支持SQL92语法中外连接的写法

  ![image-20221216143522793](/Users/jamison/Library/Application Support/typora-user-images/image-20221216143522793.png)

  思想类似于：左右脚不对称，所以就在右边加个东西垫一下

  ![image-20221216143345556](/Users/jamison/Library/Application Support/typora-user-images/image-20221216143345556.png)

  - SQL99语法使用JOIN ...ON的方式实现多表的查询。这种方式也能解决外连接的问题。MySQL是支持此种方式的。

    - SQL99语法实现内连接

      ![image-20221216151047323](/Users/jamison/Library/Application Support/typora-user-images/image-20221216151047323.png)

    - SQL99语法实现外连接

      ![image-20221216151640359](/Users/jamison/Library/Application Support/typora-user-images/image-20221216151640359.png)

      **注意：MySQL不支持满外连接的这种写法：FULL OUTER JOIN，只能换种方式实现。**

## UNION

利用union关键字，可以给出多条select语句，并将他们的结果组合为单个结果集。合并时，两个表对应的列数和数据类型必须相同，并且相互对应。各个SELECT语句之间使用union或者union all关键字分隔。

语法格式：

![image-20221216153526194](/Users/jamison/Library/Application Support/typora-user-images/image-20221216153526194.png)

**UNION操作符：**

![image-20221216153805718](/Users/jamison/Library/Application Support/typora-user-images/image-20221216153805718.png)

UNION操作符返回两个查询结果集的并集，去除了重复记录。

**UNION ALL操作符：**

![image-20221216153753432](/Users/jamison/Library/Application Support/typora-user-images/image-20221216153753432.png)

UNION ALL操作符返回两个查询结果的并集。对于两个结果集的重复部分，不去重。

> 注意：执行Union all 需要的资源比union 少。如果明确知道两个select查询不存在交集，则使用union all,以提高查询效率。

## 七种JOIN的实现

![七种JOIN的实现](/Users/jamison/Library/Application Support/typora-user-images/image-20221216154236843.png)

- 中图，左上图， 右上图

  ![image-20221216154501479](/Users/jamison/Library/Application Support/typora-user-images/image-20221216154501479.png)

- 左中图（右中图同）

  在左上图的基础上抹掉相同部分

  ![image-20221216155428340](/Users/jamison/Library/Application Support/typora-user-images/image-20221216155428340.png)

- 左下图（满外连接）

  - 方式1：左上图 UNION ALL 右中图

    ![image-20221216160153368](/Users/jamison/Library/Application Support/typora-user-images/image-20221216160153368.png)

    方式2：左中图 UNION ALL 右上图

    ![image-20221216160326690](/Users/jamison/Library/Application Support/typora-user-images/image-20221216160326690.png)

- 右下图：左中图 UNION ALL 右中图

  ![image-20221216160432157](/Users/jamison/Library/Application Support/typora-user-images/image-20221216160432157.png)

## SQL99语法的新特性

以上内容完全满足平时要求，新特性作为了解

- 自然连接

  ![image-20221216162733342](/Users/jamison/Library/Application Support/typora-user-images/image-20221216162733342.png)

- USING

  ![image-20221216162856542](/Users/jamison/Library/Application Support/typora-user-images/image-20221216162856542.png)

  ![image-20221216162928409](/Users/jamison/Library/Application Support/typora-user-images/image-20221216162928409.png)

  自连接不能用

## 本章小结

表连接的约束条件有三种方式：WHERE, ON, USING

- WHERE：适用于所有关联查询
- `ON`：只能和JOIN一起使用 ， 只能写关联条件，可读性更好。
- USING：可以和JOIN一起使用，而且要求两个关联字段在关联表中名称一致，而且只能表示关联字段相等。

![image-20221216164513134](/Users/jamison/Library/Application Support/typora-user-images/image-20221216164513134.png)

![image-20221216164531204](/Users/jamison/Library/Application Support/typora-user-images/image-20221216164531204.png)

**注意：**

我们要**控制表连接的数量**。多表连接就相当于嵌套for循环一样，非常消耗资源，会让SQL查询性能下降的严重，因此不要连接不必要的表。在许多DBMS中，也会有最大连接表的限制。

> ![image-20221216165007419](/Users/jamison/Library/Application Support/typora-user-images/image-20221216165007419.png)

## 多表查询课后习题

![image-20221216165439013](/Users/jamison/Library/Application Support/typora-user-images/image-20221216165439013.png)

​	 解答：

```mysql
#1
SELECT e.last_name, e.department_id, d.department_name
FROM employees e LEFT JOIN departments d
ON e.department_id = d.department_id;

#2
SELECT e.job_id, d.location_id, e.department_id
FROM employees e JOIN departments d
#ON e.department_id = 90 AND d.department_id = 90; 最好不要这样写，连接是连接筛选是筛选。
ON e.department_id = d.department_id
WHERE e.department_id = 90;

#3
SELECT e.last_name, d.department_name, d.location_id, l.city
FROM employees e LEFT JOIN departments d
ON e.department_id = d.department_id
LEFT JOIN locations l #两个地方都要加left，因为要求的是所有，且两个表满足条件的记录都比employees表少
ON l.location_id = d.location_id
WHERE e.commission_pct IS NOT NULL;

#4
SELECT e.last_name, e.job_id, d.department_id, d.department_name
FROM employees e JOIN departments d
ON e.department_id = d.department_id
JOIN locations l
ON d.location_id = l.location_id
WHERE l.city = 'Toronto';
#SQL92语法
SELECT e.last_name, e.job_id, d.department_id, d.department_name
FROM employees e, departments d, locations l
WHERE e.department_id = d.department_id
AND d.location_id = l.location_id
AND l.city = 'Toronto';

#5
SELECT d.department_name, l.street_address, e.last_name, e.job_id, e.salary
FROM employees e JOIN departments d
ON e.department_id = d.department_id
JOIN locations l
ON d.location_id = l.location_id
WHERE d.department_name = 'Executive';

#6
SELECT e.last_name "employees", e.employee_id "Emp#", m.last_name "manager", m.employee_id "Mgr#"
FROM employees e LEFT JOIN employees m #如果希望查出boss的话，就用左外
ON e.manager_id = m.employee_id;

#7
SELECT d.department_name
FROM employees e RIGHT JOIN departments d
ON e.department_id = d.department_id
WHERE e.department_id IS NULL;
#本题也可以使用子查询

#8
SELECT l.city
FROM departments d RIGHT JOIN locations l
ON d.location_id = l.location_id
WHERE d.location_id IS NULL;

#9
SELECT e.last_name, e.department_id, d.department_name
FROM employees e JOIN departments d
ON e.department_id = d.department_id
WHERE d.department_name IN ('Sales', 'IT');
```

# 第五章 单行函数

MySQL提供了丰富的内置函数，这些函数使得数据的管理和维护更加方便，能够更好的提供数据的分析和统计功能，在一定程度上提高了数据分析与统计的效率。

MySQL提供的内置函数从`实现功能的角度`可以分为数值函数，字符串函数，日期和时间函数，流程控制函数，加密和解密函数，获取MySQL信息函数，聚合函数等。这里，宋老师将这些丰富的内置函数再分为两类：`单行函数`和`聚合函数（分组函数）`。

![image-20221217110439169](/Users/jamison/Library/Application Support/typora-user-images/image-20221217110439169.png)

**单行函数：**

- 操作数据对象
- 接收参数返回一个结果
- **只对一行进行变换**
- **每行返回一个结果**
- 可以嵌套
- 参数可以是一列或者一个值

## 数值函数pp

### 1. 基本函数

![基本数值函数](/Users/jamison/Library/Application Support/typora-user-images/image-20221217111000735.png)

### 2. 角度与弧度互换函数

![角度与弧度互换函数](/Users/jamison/Library/Application Support/typora-user-images/image-20221217112037693.png)

### 3. 三角函数

![image-20221217112113527](/Users/jamison/Library/Application Support/typora-user-images/image-20221217112113527.png)

### 4. 指数和对数

![指数和对数](/Users/jamison/Library/Application Support/typora-user-images/image-20221217112542206.png)

### 5. 进制间的转换

![进制间的转换](/Users/jamison/Library/Application Support/typora-user-images/image-20221217112925701.png)

## 字符串函数

**注意：MySQL字符串的索引从1开始**

![字符串函数](/Users/jamison/Library/Application Support/typora-user-images/image-20221217113234399.png)

![image-20221217114559075](/Users/jamison/Library/Application Support/typora-user-images/image-20221217114559075.png)

![image-20221217115128258](/Users/jamison/Library/Application Support/typora-user-images/image-20221217115128258.png)

## 日期和时间函数

### 1. 获取日期和时间

![image-20221217115543819](/Users/jamison/Library/Application Support/typora-user-images/image-20221217115543819.png)

### 2. 日期与时间戳的转换

![image-20221217120419284](/Users/jamison/Library/Application Support/typora-user-images/image-20221217120419284.png)

### 3. 获取月份、星期、星期数、天数等函数

![image-20221217120612388](/Users/jamison/Library/Application Support/typora-user-images/image-20221217120612388.png)

### 4. 日期的操作函数

![image-20221217121107528](/Users/jamison/Library/Application Support/typora-user-images/image-20221217121107528.png)

### 5. 时间和秒钟的转换函数

![image-20221217121418666](/Users/jamison/Library/Application Support/typora-user-images/image-20221217121418666.png)

### 6. 计算日期和时间的函数

![image-20221217121626014](/Users/jamison/Library/Application Support/typora-user-images/image-20221217121626014.png)

例子：

![image-20221217121917208](/Users/jamison/Library/Application Support/typora-user-images/image-20221217121917208.png)

![image-20221217122003215](/Users/jamison/Library/Application Support/typora-user-images/image-20221217122003215.png)

例子：

![image-20221217122129680](/Users/jamison/Library/Application Support/typora-user-images/image-20221217122129680.png)

### 7. 日期的格式化和解析

格式化：日期 --> 字符串

解析：字符串 --> 日期

![image-20221217122826283](/Users/jamison/Library/Application Support/typora-user-images/image-20221217122826283.png)

格式：

![image-20221217123039613](/Users/jamison/Library/Application Support/typora-user-images/image-20221217123039613.png)

![image-20221217123110591](/Users/jamison/Library/Application Support/typora-user-images/image-20221217123110591.png)

Get_Format函数：

![image-20221217123423743](/Users/jamison/Library/Application Support/typora-user-images/image-20221217123423743.png)

## 流程控制函数

![image-20221217125018330](/Users/jamison/Library/Application Support/typora-user-images/image-20221217125018330.png)

练习一：

![image-20221217125000566](/Users/jamison/Library/Application Support/typora-user-images/image-20221217125000566.png)

练习二：

![image-20221217125210285](/Users/jamison/Library/Application Support/typora-user-images/image-20221217125210285.png)

## 加密和解密函数

![加密解密函数](/Users/jamison/Library/Application Support/typora-user-images/image-20221217155557818.png)

注意：password，decode, encode函数在mysql 8.0弃用 

## MySQL信息函数

![image-20221217160257829](/Users/jamison/Library/Application Support/typora-user-images/image-20221217160257829.png)

## 其他函数

## ![image-20221217160516036](/Users/jamison/Library/Application Support/typora-user-images/image-20221217160516036.png)	单行函数课后练习

![image-20221217161408822](/Users/jamison/Library/Application Support/typora-user-images/image-20221217161408822.png)

```mysql
#1
SELECT SYSDATE()
FROM DUAL;

#2
SELECT employee_id, last_name, salary, salary * 1.2 "new salary"
FROM employees;

#3
SELECT last_name, LENGTH(last_name) "name length"
FROM employees
ORDER BY last_name ASC;

#4
SELECT CONCAT(employee_id, ' ',last_name, ' ', salary) "OUT_PUT"
FROM employees;

#5
SELECT employee_id, DATEDIFF(SYSDATE(), hire_date)/365 "worked_years", DATEDIFF(SYSDATE(), hire_date) "worked_days"
FROM employees
ORDER BY worked_years DESC;

#6
SELECT last_name, hire_date, department_id
FROM employees
WHERE YEAR(hire_date) >= 1997 #hire_date > '1997-01-01'
AND department_id IN(80, 90, 110)
AND commission_pct IS NOT NULL;

#7
SELECT employee_id, hire_date
FROM employees
WHERE DATEDIFF(SYSDATE(), hire_date) > 10000;

#8
SELECT CONCAT(last_name, ' earns ', salary, 'monthly but wants ', salary * 3)
FROM employees;

#9
SELECT last_name, job_id "job", CASE job_id
																WHEN 'AD_PRES' THEN 'A'
																WHEN 'ST_MAN' THEN 'b'
																WHEN 'IT_PROG' THEN 'C'
																WHEN 'SA_REP' THEN 'D'
																ELSE 'E'
																END "grade"
FROM employees
```



# 第六章 聚合函数

## 常见的聚合函数

对应上一章，也可以叫做多行函数，聚合函数，他是对一组数据进行汇总的函数，输入的是一组数据的集合，输出的是单个值。

**常用的聚合函数：**

- AVG()
- SUM()
- MAX()
- MIN()
- COUNT()

**聚合函数语法：**

![image-20221217171412073](/Users/jamison/Library/Application Support/typora-user-images/image-20221217171412073.png)

例子：

![image-20221217172514452](/Users/jamison/Library/Application Support/typora-user-images/image-20221217172514452.png)

![image-20221217172837861](/Users/jamison/Library/Application Support/typora-user-images/image-20221217172837861.png)

练习：查询公司中的平均奖金率

```mysql
#错误的
SELECT AVG(commission_pct)
FROM employees;

#正确的
SELECT AVG(IFNULL(commission_pct, 0))
#或者
,SUM(commission_pct) / COUNT(IFNULL(commission_pct, 0))
FROM employees;
```

小问题：如果需要统计表中的记录数，使用count(*), count(1), count(具体字段)那个效率更高呢？

- 如果使用MyISAM存储引擎，则三者效率相同，都是O(1)
- 如果使用的是InnoDB引擎，则三者效率：count(*) = count(1) > count(字段)

## GROUP BY的使用

### 1. 基本使用

![image-20221217184103970](/Users/jamison/Library/Application Support/typora-user-images/image-20221217184103970.png)

需求：

- 查询各个部门的平均工资，最高工资

  ```mysql
  SELECT department_id, AVG(salary), MAX(salary)
  FROM employees
  GROUP BY department_id;
  ```

- 查询各个job_id的平均工资

  ```mysql
  SELECT job_id, AVG(salary)
  FROM employees
  GROUP BY job_id;
  ```

### 2. 使用多个列进行分组

![image-20221217184817238](/Users/jamison/Library/Application Support/typora-user-images/image-20221217184817238.png)

需求：

- 查询各个department_id, job_id的平均工资

  ```mysql
  SELECT department_id, job_id, AVG(salary)
  FROM employees
  GROUP BY department_id, job_id;
  #或者
  SELECT job_id, department_id, AVG(salary)
  FROM employees
  GROUP BY job_id, department_id;
  ```

**注意：**

以下是错误的，MySql不会报错但是Oracle会报错，MySQ中不报错但是显示结果错误，job_id显示不全

![image-20221217190332770](/Users/jamison/Library/Application Support/typora-user-images/image-20221217190332770.png)

**以上可以得出一个结论：select中非组函数字段必须声明在group by中，但是group by声明的字段可以不出现在select中。**

**GROUP BY语句的位置**：FROM后面，WHERE后面，ORDER BY前面，LIMIT前面

### 3. GROUP BY中使用WITH ROLLUP

![image-20221217191417066](/Users/jamison/Library/Application Support/typora-user-images/image-20221217191417066.png)

## HAVING的使用（用来过滤数据）
