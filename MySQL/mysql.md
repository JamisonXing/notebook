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

### 1. 基本使用

- **需求1**：查询各个部门中最高工资比10000高的部门信息

错误的写法：

```mysql
SELECT department_id, MAX(salary)
FROM employees
WHERE MAX(salary) > 10000
GROUP BY department_id;
```

为了解决以上问题：

**要求1：如果过滤条件中使用了聚合函数，则必须使用HAVING来替换WHERE。否则会报错**

**要求2：HAVING必须声明在GROUP BY的后面**

所以正确写法为：

```mysql
SELECT department_id, MAX(salary)
FROM employees
GROUP BY department_id
HAVING MAX(salary) > 10000;
```

**要求3：开发中我们使用HAVING的前提是SQL中使用了GROUP BY。**

- **需求2**：查询部门id为10， 20， 30，40这四个部门中最高工资比10000高的部门信息

方式1：

```mysql
SELECT department_id, MAX(salary)
FROM employees
WHERE department_id IN(10, 20, 30, 40)
GROUP BY department_id
HAVING MAX(salary) > 10000;
```

方式2：

```mysql
SELECT department_id, MAX(salary)
FROM employees
GROUP BY department_id
HAVING MAX(salary) > 10000 AND department_id IN(10, 20, 30, 40);
```

要求：当过滤条件中有聚合函数时，则此过滤条件必须声明在HAVING中。

​			当过滤条件中没有聚合函数时，则此过滤条件声明在WHERE或HAVING中都可以。但是开发中建议声明在			WHERE中，即方式一，效率更高。

### 2. WHERE和HAVING的对比

![image-20221219133529190](/Users/jamison/Library/Application Support/typora-user-images/image-20221219133529190.png)

![image-20221219134542702](/Users/jamison/Library/Application Support/typora-user-images/image-20221219134542702.png)

当数据量特别大的时候，运行效率会有很大区别，所以要注意他们的使用。

### 3. SQL底层执行原理

- select语句结构

  ![image-20221219134918216](/Users/jamison/Library/Application Support/typora-user-images/image-20221219134918216.png)

- SQL语句的执行顺序

  from-> on -> (left right join) -> where -> group by -> having -> select -> distinct -> order by -> limit

  ![image-20221219135100561](/Users/jamison/Library/Application Support/typora-user-images/image-20221219135100561.png)
  
  在select语句中执行这些步骤的时候，每个步骤都会产生一个`虚拟表`，然后将这个虚拟表传入下一个步骤中作为输入。需要注意的是，这些步骤隐含在SQL的执行过程中，对于我们来说是不可见的。
  
- SQL语句的执行原理

  ![image-20221219140758938](/Users/jamison/Library/Application Support/typora-user-images/image-20221219140758938.png)

## 聚合函数练习题

![image-20221219141218330](/Users/jamison/Library/Application Support/typora-user-images/image-20221219141218330.png)

解答：

```mysql
#1
#不可以

#2
SELECT MAX(salary), MIN(salary), AVG(salary), SUM(salary)
FROM employees;

#3
SELECT job_id, MAX(salary), MIN(salary), AVG(salary), SUM(salary)
FROM employees
GROUP BY job_id;

#4
SELECT job_id, COUNT(*)
FROM employees
GROUP BY job_id;

#5
SELECT MAX(salary) - MIN(salary) "gap"
FROM employees;

#6
SELECT manager_id, MIN(salary)
FROM employees
WHERE manager_id IS NOT NULL
GROUP BY manager_id
HAVING MIN(salary) > 6000;

#7
SELECT d.department_name, d.location_id, COUNT(employee_id), AVG(salary) "avg_salary"
FROM employees e RIGHT JOIN departments d
ON e.department_id = d.department_id
GROUP BY department_name, location_id
ORDER BY avg_salary DESC;

#8, 这一题好像有点问题，网友说需要满外连接
SELECT d.department_name, e.job_id, MIN(salary)
FROM employees e RIGHT JOIN departments d
ON e.department_id = d.department_id
GROUP BY department_name, job_id;
```



# 第七章 子查询

子查询指的是一个查询语句嵌套在另一个查询语句内部的查询，这个特性从MySQL 4.1开始引入。

SQL中子查询的使用大大增加了查询的能力，因为很多时候查询需要从结果集中获取数据，或者需要从同一个表中先计算得出一个数据结果，然后与这个数据结果（可能是某个标量，也可能是某个集合）进行比较。

## 从实际问题出发

### 1. 实际问题

需求：谁的工资比Abel高？

```MySQL
#方式一
SELECT salary
FROM employees
WHERE last_name = 'Abel';

SELECT last_name, salary
FROM employees
WHERE salary > 11000;

#方式二：自连接
SELECT e2.last_name, e2.salary
FROM employees e1 JOIN employees e2
ON e1.last_name = 'Abel' AND e1.salary < e2.salary;

#方式三：子查询
SELECT last_name, salary
FROM employees
WHERE salary > (
								SELECT salary
								FROM employees
								WHERE last_name = 'Abel';
								);
```

### 2. 子查询的基本使用

- 子查询的语法结构

  ![image-20221219163445203](/Users/jamison/Library/Application Support/typora-user-images/image-20221219163445203.png)

- 子查询（内查询）在主查询之前一次执行完成。

- 子查询的结果供主查询（外查询）使用。

- **注意事项：**

  - 子查询要包含在括号内
  - 将子查询放在比较条件的右侧（写在左侧不会报错但是可读性差）
  - 单行操作符对应单行子查询，多行操作符对应对行子查询

### 3. 子查询的分类

![image-20221219164538468](/Users/jamison/Library/Application Support/typora-user-images/image-20221219164538468.png)

小结：

从内查询返回的结果的条目数分为单行和多行子查询，根据内查询是否被执行多次，分为相关和不相关子查询。

![image-20221219164818837](/Users/jamison/Library/Application Support/typora-user-images/image-20221219164818837.png)

## 单行子查询

### 1. 单行比较操作符

![image-20221219165035146](/Users/jamison/Library/Application Support/typora-user-images/image-20221219165035146.png)

- 查询工资大于149号员工工资的员工信息

  ![image-20221219165353229](/Users/jamison/Library/Application Support/typora-user-images/image-20221219165353229.png)

- 返回job_id和141号员工相同，salary比143号员工多的员工姓名，job_id和工资

  ![image-20221219170032167](/Users/jamison/Library/Application Support/typora-user-images/image-20221219170032167.png)

- 返回公司中工资最少的员工的last_name, job_id和salary

  ![image-20221219170347974](/Users/jamison/Library/Application Support/typora-user-images/image-20221219170347974.png)

- 题目：

  ![image-20221219171747896](/Users/jamison/Library/Application Support/typora-user-images/image-20221219171747896.png)

  解答：

  方式一：

  ![image-20221219171813446](/Users/jamison/Library/Application Support/typora-user-images/image-20221219171813446.png)

  方式二（了解）：

  ![image-20221219171904188](/Users/jamison/Library/Application Support/typora-user-images/image-20221219171904188.png)

### 2. HAVING中的子查询

- 首先执行子查询。
- 向主查询中的HAVING子句返回结果。

**题目：查询最低工资大于50号部门最低工资的部门id和其最低工资**

```mysql
SELECT department_id, MIN(salary)
FROM employees
WHERE department_id IS NOT NULL
GROUP BY department_id
HAVING MIN(salary) > (SELECT MIN(salary) FROM employees WHERE department_id = 50);
```

### 3. CASE中的子查询

题目：

![image-20221219174301374](/Users/jamison/Library/Application Support/typora-user-images/image-20221219174301374.png)

解答：

```mysql
SELECT employee_id, last_name, (CASE department_id
	WHEN (SELECT department_id FROM departments WHERE location_id = 1800) THEN
		'Canada'
	ELSE
		'USA'
END
) "locations"
FROM employees;
```

### 4. 子查询中的空值问题

![image-20221219175051899](/Users/jamison/Library/Application Support/typora-user-images/image-20221219175051899.png)

### 5. 非法使用子查询

![image-20221219175216874](/Users/jamison/Library/Application Support/typora-user-images/image-20221219175216874.png)

## 多行子查询

![image-20221220102257051](/Users/jamison/Library/Application Support/typora-user-images/image-20221220102257051.png)

### 	1. 多行比较操作符

![image-20221220102335704](/Users/jamison/Library/Application Support/typora-user-images/image-20221220102335704.png)

**ANY/ALL**

题目：

![image-20221220103451922](/Users/jamison/Library/Application Support/typora-user-images/image-20221220103451922.png)

解答：

![image-20221220103813873](/Users/jamison/Library/Application Support/typora-user-images/image-20221220103813873.png)

题目2：

![image-20221220103743076](/Users/jamison/Library/Application Support/typora-user-images/image-20221220103743076.png)

解答：

![image-20221220103730515](/Users/jamison/Library/Application Support/typora-user-images/image-20221220103730515.png)

题目3：

![image-20221220105030339](/Users/jamison/Library/Application Support/typora-user-images/image-20221220105030339.png)

解答：

- 方式1：

  ![image-20221220105107289](/Users/jamison/Library/Application Support/typora-user-images/image-20221220105107289.png)

- 方式2：

  ![image-20221220105534976](/Users/jamison/Library/Application Support/typora-user-images/image-20221220105534976.png) 

### 2. 空值问题

![image-20221220105634160](/Users/jamison/Library/Application Support/typora-user-images/image-20221220105634160.png)

## 相关子查询

### 1. 相关子查询执行流程

如果子查询依赖于外部查询，通常情况下都是因为子查询中的表使用到了外部的表，并进行了条件关联，因此每执行一次外部查询，子查询都要重新计算一次，这样的子查询就称为关联子查询。

相关子查询按照一行接一行的顺序执行，主查询的每一行都执行一次子查询。

![image-20221220111008688](/Users/jamison/Library/Application Support/typora-user-images/image-20221220111008688.png)

**说明：**子查询使用主查询中的列

### 2. 代码示例

题目1：

![image-20221220112412776](/Users/jamison/Library/Application Support/typora-user-images/image-20221220112412776.png)

解答：

- 方式1：相关子查询

  ![image-20221220112432684](/Users/jamison/Library/Application Support/typora-user-images/image-20221220112432684.png)

- 方式2：在FROM中声明子查询

  ![image-20221220112529129](/Users/jamison/Library/Application Support/typora-user-images/image-20221220112529129.png)

题目2：

![image-20221220114323226](/Users/jamison/Library/Application Support/typora-user-images/image-20221220114323226.png)

解答：GROUP BY中的子查询

![image-20221220114520257](/Users/jamison/Library/Application Support/typora-user-images/image-20221220114520257.png)

**结论：除了LIMIT和GROUP BY不能写子查询之外，其他位置都可以声明子查询**

题目3：

![image-20221220114810977](/Users/jamison/Library/Application Support/typora-user-images/image-20221220114810977.png)

 解答：

![image-20221220115223389](/Users/jamison/Library/Application Support/typora-user-images/image-20221220115223389.png)

### 3. EXIST与NOT EXIST关键字

题目1：

![image-20221220120703995](/Users/jamison/Library/Application Support/typora-user-images/image-20221220120703995.png)

解答：

- 方式1：自连接

  ![image-20221220120725500](/Users/jamison/Library/Application Support/typora-user-images/image-20221220120725500.png)

- 方式2：

  ![image-20221220120844798](/Users/jamison/Library/Application Support/typora-user-images/image-20221220120844798.png)

- 方式3：EXIST

  ![image-20221220120910976](/Users/jamison/Library/Application Support/typora-user-images/image-20221220120910976.png)

题目2：

![image-20221220121013847](/Users/jamison/Library/Application Support/typora-user-images/image-20221220121013847.png)

解答：

- 方式1：自连接

  ![image-20221220121031527](/Users/jamison/Library/Application Support/typora-user-images/image-20221220121031527.png)

- 方式2：NOT EXIST

  ![image-20221220121314001](/Users/jamison/Library/Application Support/typora-user-images/image-20221220121314001.png)

### 4. 相关更新

![image-20221220121550472](/Users/jamison/Library/Application Support/typora-user-images/image-20221220121550472.png)

### 5. 相关删除

![image-20221220121829935](/Users/jamison/Library/Application Support/typora-user-images/image-20221220121829935.png)

### 6. 抛出一个问题

问题：谁的工资比Abel高

解答：

![image-20221220122349269](/Users/jamison/Library/Application Support/typora-user-images/image-20221220122349269.png)

## 子查询课后练习题

题目：

![image-20221220122803083](/Users/jamison/Library/Application Support/typora-user-images/image-20221220122803083.png)

 ![image-20221220122840620](/Users/jamison/Library/Application Support/typora-user-images/image-20221220122840620.png)

![image-20221220122858987](/Users/jamison/Library/Application Support/typora-user-images/image-20221220122858987.png)

解答：

```mysql
	 #1
SELECT last_name, salary
FROM employees
WHERE department_id = (
												SELECT department_id 
												FROM employees
												WHERE last_name = 'Zlotkey'
);

#2
SELECT employee_id, last_name, salary
FROM employees
WHERE salary > (
									SELECT AVG(salary)
									FROM employees
);

#3
SELECT last_name, job_id, salary
FROM employees
WHERE salary > ALL(
								SELECT salary
								FROM employees
								WHERE job_id = 'SA_MAN'
);

#4
SELECT employee_id, last_name
FROM employees
WHERE department_id IN (
												SELECT DISTINCT department_id
												FROM employees
												WHERE last_name REGEXP '[u]'
);

#5
SELECT employee_id
FROM employees
WHERE department_id IN (
												SELECT department_id
												FROM departments
												WHERE location_id = 1700
);

#6
SELECT last_name, salary
FROM employees
WHERE manager_id IN (
										SELECT employee_id
										FROM employees
										WHERE last_name = 'King'
);

#7
SELECT last_name, salary
FROM employees
WHERE salary <= ALL(
										SELECT salary
										FROM employees
);

#8
#方法1
SELECT *
FROM departments
WHERE department_id = (
											SELECT department_id
											FROM employees
											GROUP BY department_id
											HAVING AVG(salary) = (
																						SELECT MIN(avg_sal)
																						FROM(
																								SELECT AVG(salary) avg_sal
																								FROM employees
																								GROUP BY department_id
																								) t_dept_avg_sal
																						)
													);
#方法2
SELECT *
FROM departments
WHERE department_id = (
											SELECT department_id
											FROM employees
											GROUP BY department_id
											HAVING AVG(salary) <= ALL(
																								SELECT AVG(salary)
																								FROM employees
																								GROUP BY department_id
																						)
													);
#方法3
SELECT *
FROM departments
WHERE department_id = (
											SELECT department_id
											FROM employees
											GROUP BY department_id
											HAVING AVG(salary) = (
																								SELECT AVG(salary) avg_sal
																								FROM employees
																								GROUP BY department_id
																								ORDER BY avg_sal
																								LIMIT 1
																						)
											);
#方法4
SELECT d.*
FROM departments d, (
											SELECT department_id, AVG(salary) avg_sal
											FROM employees
											GROUP BY department_id
											ORDER BY avg_sal ASC
											LIMIT 0,1
										) t_dept_avg_sal
WHERE d.department_id = t_dept_avg_sal.department_id;

#9
#方法1
SELECT d.*, (SELECT AVG(salary) FROM employees WHERE department_id = d.department_id) avg_sal
FROM departments d
WHERE department_id = (
											SELECT department_id
											FROM employees
											GROUP BY department_id
											HAVING AVG(salary) = (
																						SELECT MIN(avg_sal)
																						FROM(
																								SELECT AVG(salary) avg_sal
																								FROM employees
																								GROUP BY department_id
																								) t_dept_avg_sal
																						)
													);
#方法2
SELECT d.*, (SELECT AVG(salary) FROM employees WHERE department_id = d.department_id) avg_sal
FROM departments d
WHERE department_id = (
											SELECT department_id
											FROM employees
											GROUP BY department_id
											HAVING AVG(salary) <= ALL(
																								SELECT AVG(salary)
																								FROM employees
																								GROUP BY department_id
																						)
													);
#方法3
SELECT d.*, (SELECT AVG(salary) FROM employees WHERE department_id = d.department_id) avg_sal
FROM departments d
WHERE department_id = (
											SELECT department_id
											FROM employees
											GROUP BY department_id
											HAVING AVG(salary) = (
																								SELECT AVG(salary) avg_sal
																								FROM employees
																								GROUP BY department_id
																								ORDER BY avg_sal
																								LIMIT 1
																						)
											);
#方法4：
SELECT d.*, (SELECT AVG(salary) FROM employees WHERE department_id = d.department_id) avg_sal
FROM departments d, (
											SELECT department_id, AVG(salary) avg_sal
											FROM employees
											GROUP BY department_id
											ORDER BY avg_sal ASC
											LIMIT 0,1
										) t_dept_avg_sal
WHERE d.department_id = t_dept_avg_sal.department_id;


#10
#方式1
SELECT *
FROM jobs
WHERE job_id = (
								SELECT job_id
								FROM employees
								GROUP BY job_id
								HAVING AVG(salary) = (
																			SELECT MAX(avg_sal)
																			FROM (
																						SELECT AVG(salary) avg_sal
																						FROM employees
																						GROUP BY job_id
																						) t_job_avg_sal
																			)
							);
#方式2
SELECT *
FROM jobs
WHERE job_id = (
								SELECT job_id
								FROM employees
								GROUP BY job_id
								HAVING AVG(salary) >= ALL(
																			SELECT AVG(salary)
																			FROM employees
																			GROUP BY job_id
																			)
							);
#方式3
SELECT *
FROM jobs
WHERE job_id = (
								SELECT job_id
								FROM employees
								GROUP BY job_id
								HAVING AVG(salary) = (
																			SELECT AVG(salary) avg_sal
																			FROM employees
																			GROUP BY job_id
																			ORDER BY avg_sal desc
																			LIMIT 0,1
																			)
							);
#方式4
SELECT j.*
FROM jobs j, (
							SELECT job_id, AVG(salary) avg_sal
							FROM employees
							GROUP BY job_id
							ORDER BY avg_sal DESC
							LIMIT 0,1
							) t_job_avg_sal
WHERE j.job_id = t_job_avg_sal.job_id;


#11
SELECT department_id
FROM employees
WHERE department_id IS NOT NULL
GROUP BY department_id
HAVING AVG(salary) > (
								SELECT AVG(salary)
								FROM employees
);

#12
SELECT employee_id, last_name
FROM employees
WHERE employee_id IN (
											SELECT DISTINCT manager_id
											FROM employees
											);

#13
#方法1
SELECT MIN(salary)
FROM employees
WHERE department_id = (
												SELECT department_id
												FROM employees
												GROUP BY department_id
												HAVING MAX(salary) = (
																							SELECT MIN(max_sal)
																							FROM(
																									SELECT MAX(salary) max_sal
																									FROM employees 
																									WHERE department_id IS NOT NULL
																									GROUP BY department_id
																									) t_dept_max_sal
																							)
											);
#方法2
SELECT MIN(salary)
FROM employees
WHERE department_id = (
												SELECT department_id
												FROM employees
												GROUP BY department_id
												HAVING MAX(salary) <= ALL(
																								SELECT MAX(salary) max_sal
																								FROM employees 
																								WHERE department_id IS NOT NULL
																								GROUP BY department_id
 																							)
											);	
#方法3
SELECT MIN(salary)
FROM employees
WHERE department_id = (
												SELECT department_id
												FROM employees
												GROUP BY department_id
												HAVING MAX(salary) = (
																								SELECT MAX(salary) max_sal
																								FROM employees 
																								WHERE department_id IS NOT NULL
																								GROUP BY department_id
																								ORDER BY max_sal
																								LIMIT 0,1
 																							)
											);			
#方法4
SELECT MIN(salary)
FROM employees e, (
									SELECT department_id, MAX(salary) max_sal
									FROM employees 
									WHERE department_id IS NOT NULL
									GROUP BY department_id
									ORDER BY max_sal
									LIMIT 0,1
									) t_dept_max_sal
WHERE e.department_id = t_dept_max_sal.department_id;

#14
SELECT last_name, department_id, email, salary
FROM employees
WHERE employee_id IN (
											SELECT DISTINCT manager_id
											FROM employees e, (
																					SELECT department_id, AVG(salary) avg_sal
																					FROM employees
																					GROUP BY department_id
																					ORDER BY avg_sal DESC
																					LIMIT 0,1
																				) t_dept_avg_sal
											WHERE e.department_id = t_dept_avg_sal.department_id
										);
										
#15
#方法1
SELECT department_id
FROM departments
WHERE department_id NOT IN(
													SELECT DISTINCT department_id
													FROM employees
													WHERE job_id = 'ST_CLERK'
													);
#方法2
SELECT department_id
FROM departments d
WHERE NOT EXISTS(
									SELECT *
									FROM employees e
									WHERE d.department_id = e.department_id
									AND e.job_id = 'ST_CLERK'
								);
								
#16
SELECT last_name
FROM employees emp
WHERE NOT EXISTS (
									SELECT *
									FROM employees mgr
									WHERE emp.manager_id = mgr.employee_id
									);
									
#17
#方式1
SELECT employee_id, last_name, hire_date, salary
FROM employees
WHERE manager_id = (
										SELECT employee_id
										FROM employees
										WHERE last_name = 'De Haan'
										);
#方式2
SELECT employee_id, last_name, hire_date, salary
FROM employees e1
WHERE EXISTS(
							SELECT *
							FROM employees e2
							WHERE e1.manager_id = e2.employee_id
							AND last_name = 'De Haan'
						);
						
#18
#之前讲过

#19
SELECT department_name
FROM departments d
WHERE 5 < (
						SELECT COUNT(*)
						FROM employees e
						WHERE d.department_id = e.department_id
					);
					
#20
SELECT country_id
FROM locations l
WHERE 2 < (
						SELECT COUNT(*)
						FROM departments d
						WHERE l.location_id = d.location_id
					);
```

**子查询的编写技巧**：从里面往，从外往里怎么选择

1. 如果子查询比较简单，建议从外往里写，反之从里往外。
2. 如果是想关子查询的话通常都是从外往里写。



# 第七章 创建和管理表

## 基础知识

### 1. 一条数据存储的过程

![image-20221221115343686](/Users/jamison/Library/Application Support/typora-user-images/image-20221221115343686.png)

### 2. 标识符命名的规则

- 数据库，表名不得超过30字符，变量名限制为29个
- 必须只能包含A-Z,a-z,0-9,_共63个字符
- 数据库名，表名，字段名等对象名中间不要包含空格
- 同一个mysql软件中，数据库不能同名；同一个库中表不能同名；同一个表中，字段不能重名
- 必须保证你的字段没有保留字、数据库系统或常用方法冲突。如果坚持使用，请用`（着重号）引起来
- 保存字段名和类型的一致性，在命名字段并为其指定数据类型的时候一定要保证一致性。加入数据类型在一个表里是整数，那在另一个表里可别变成字符型了

### 3. MySQL中基本数据类型

![image-20221221114846857](/Users/jamison/Library/Application Support/typora-user-images/image-20221221114846857.png)

其中，比较常用的数据类型有：

![image-20221221115227421](/Users/jamison/Library/Application Support/typora-user-images/image-20221221115227421.png)

## 创建和管理数据库

### 1. 创建数据库

![image-20221221112533325](/Users/jamison/Library/Application Support/typora-user-images/image-20221221112533325.png)

### 2. 管理数据库

![image-20221221113825501](/Users/jamison/Library/Application Support/typora-user-images/image-20221221113825501.png)

### 3. 修改数据库

![image-20221221113858672](/Users/jamison/Library/Application Support/typora-user-images/image-20221221113858672.png)

### 4. 删除数据库

![image-20221221114256009](/Users/jamison/Library/Application Support/typora-user-images/image-20221221114256009.png)

## 创建数据表

方式1：

![image-20221221115901378](/Users/jamison/Library/Application Support/typora-user-images/image-20221221115901378.png)

方式2：

![image-20221221120354910](/Users/jamison/Library/Application Support/typora-user-images/image-20221221120354910.png)

练习：

![image-20221221120738851](/Users/jamison/Library/Application Support/typora-user-images/image-20221221120738851.png)

## 修改表

### 1. 添加一个字段

![image-20221221121425619](/Users/jamison/Library/Application Support/typora-user-images/image-20221221121425619.png)

### 2. 修改一个字段：数据类型，长度，默认值...

![image-20221221121539515](/Users/jamison/Library/Application Support/typora-user-images/image-20221221121539515.png)

### 3. 重命名一个字段

重命名的过程中可以修改字段

![image-20221221121737079](/Users/jamison/Library/Application Support/typora-user-images/image-20221221121737079.png)

### 4. 删除一个字段

![image-20221221121937521](/Users/jamison/Library/Application Support/typora-user-images/image-20221221121937521.png)

## 重命名表

![image-20221221121953055](/Users/jamison/Library/Application Support/typora-user-images/image-20221221121953055.png)

## 删除表

![image-20221221122234512](/Users/jamison/Library/Application Support/typora-user-images/image-20221221122234512.png)

## 清空表

![image-20221221122219180](/Users/jamison/Library/Application Support/typora-user-images/image-20221221122219180.png)

## DCL中COMMIT和ROLLBACK

![image-20221221123532590](/Users/jamison/Library/Application Support/typora-user-images/image-20221221123532590.png)

## 对比TRUNCATE TABLE 和 DELETE FROM

![image-20221221123651932](/Users/jamison/Library/Application Support/typora-user-images/image-20221221123651932.png)

## DDL和DCL的说明

![image-20221221124253403](/Users/jamison/Library/Application Support/typora-user-images/image-20221221124253403.png)

演示：

![image-20221221124102372](/Users/jamison/Library/Application Support/typora-user-images/image-20221221124102372.png)

![image-20221221124201418](/Users/jamison/Library/Application Support/typora-user-images/image-20221221124201418.png)

阿里巴巴开发规范：

![image-20221221124528950](/Users/jamison/Library/Application Support/typora-user-images/image-20221221124528950.png)	

## 内容扩展

### 拓展1：阿里巴巴MySQL字段命名

![image-20221221124756841](/Users/jamison/Library/Application Support/typora-user-images/image-20221221124756841.png)

### 拓展2：如何理解删除表、清空表等操作需谨慎？

![image-20221221124952076](/Users/jamison/Library/Application Support/typora-user-images/image-20221221124952076.png)

### 拓展3：MySQL8新特性--DDL的原子化

![image-20221221125355677](/Users/jamison/Library/Application Support/typora-user-images/image-20221221125355677.png)

5.7中book1被删除，8.0book1不会被删除。

## 创建和管理表习题

练习一：

![image-20221221125627459](/Users/jamison/Library/Application Support/typora-user-images/image-20221221125627459.png)

![image-20221221125645164](/Users/jamison/Library/Application Support/typora-user-images/image-20221221125645164.png)

![image-20221221125712398](/Users/jamison/Library/Application Support/typora-user-images/image-20221221125712398.png)

![image-20221221125731222](/Users/jamison/Library/Application Support/typora-user-images/image-20221221125731222.png)

![image-20221221125824291](/Users/jamison/Library/Application Support/typora-user-images/image-20221221125824291.png)

![image-20221221125843561](/Users/jamison/Library/Application Support/typora-user-images/image-20221221125843561.png)

![image-20221221125905271](/Users/jamison/Library/Application Support/typora-user-images/image-20221221125905271.png)

![image-20221221125919549](/Users/jamison/Library/Application Support/typora-user-images/image-20221221125919549.png)

![image-20221221125947309](/Users/jamison/Library/Application Support/typora-user-images/image-20221221125947309.png)	

![image-20221221130006507](/Users/jamison/Library/Application Support/typora-user-images/image-20221221130006507.png)

练习2：

![image-20221221130049753](/Users/jamison/Library/Application Support/typora-user-images/image-20221221130049753.png)

![image-20221221130104790](/Users/jamison/Library/Application Support/typora-user-images/image-20221221130104790.png)

![image-20221221130139419](/Users/jamison/Library/Application Support/typora-user-images/image-20221221130139419.png)

![image-20221221130201300](/Users/jamison/Library/Application Support/typora-user-images/image-20221221130201300.png)

![image-20221221130225673](/Users/jamison/Library/Application Support/typora-user-images/image-20221221130225673.png)

![image-20221221130235845](/Users/jamison/Library/Application Support/typora-user-images/image-20221221130235845.png)

![image-20221221130248023](/Users/jamison/Library/Application Support/typora-user-images/image-20221221130248023.png)

![image-20221221130309533](/Users/jamison/Library/Application Support/typora-user-images/image-20221221130309533.png)

![image-20221221130326290](/Users/jamison/Library/Application Support/typora-user-images/image-20221221130326290.png)

![image-20221221130336741](/Users/jamison/Library/Application Support/typora-user-images/image-20221221130336741.png)



# 第八章 数据处理之增删改

准备工作：

![image-20221221134529008](/Users/jamison/Library/Application Support/typora-user-images/image-20221221134529008.png)

## 插入数据

### 1. 实际问题

![image-20221221134329752](/Users/jamison/Library/Application Support/typora-user-images/image-20221221134329752.png)

### 2. 方式1：一条条的添加数据

![image-20221221134743480](/Users/jamison/Library/Application Support/typora-user-images/image-20221221134743480.png)

![image-20221221134922724](/Users/jamison/Library/Application Support/typora-user-images/image-20221221134922724.png)

### 3. 方式2：将查询结果插入到表中

![image-20221221135501407](/Users/jamison/Library/Application Support/typora-user-images/image-20221221135501407.png)

注意：

- VALUES也可以写成VALUE,但是前者是标准写法。
- 字符和日期数据应包含在单引号中。

## 更新数据

![image-20221221135750014](/Users/jamison/Library/Application Support/typora-user-images/image-20221221135750014.png)

- 使用UPDATE语句更新数据，语法如下

  ```mysql
  UPDATE table_name
  SET column1 = value1, column2 = value2, ...
  [WHERE condition]
  ```

![image-20221221140059067](/Users/jamison/Library/Application Support/typora-user-images/image-20221221140059067.png)

**注意：**

添加,修改和删除数据的时候是可能存在不成功的情况的，可能是由于约束造成的

![image-20221221140241259](/Users/jamison/Library/Application Support/typora-user-images/image-20221221140241259.png)

## 删除数据

```mysql
DELETE FROM ... WHERE ...
```

![image-20221221140443272](/Users/jamison/Library/Application Support/typora-user-images/image-20221221140443272.png)

## MySQL8新特性：计算列

![image-20221221141300226](/Users/jamison/Library/Application Support/typora-user-images/image-20221221141300226.png)

## DDL和DML的综合案例

案例：

![image-20221221141732237](/Users/jamison/Library/Application Support/typora-user-images/image-20221221141732237.png)

![image-20221221141821090](/Users/jamison/Library/Application Support/typora-user-images/image-20221221141821090.png)

![image-20221221141843103](/Users/jamison/Library/Application Support/typora-user-images/image-20221221141843103.png)

![image-20221221141938488](/Users/jamison/Library/Application Support/typora-user-images/image-20221221141938488.png)

![image-20221221142029564](/Users/jamison/Library/Application Support/typora-user-images/image-20221221142029564.png)

解答：

![image-20221221142141574](/Users/jamison/Library/Application Support/typora-user-images/image-20221221142141574.png)

![image-20221221142218779](/Users/jamison/Library/Application Support/typora-user-images/image-20221221142218779.png)

![image-20221221142325547](/Users/jamison/Library/Application Support/typora-user-images/image-20221221142325547.png)

![image-20221221142411391](/Users/jamison/Library/Application Support/typora-user-images/image-20221221142411391.png)

![image-20221221142439795](/Users/jamison/Library/Application Support/typora-user-images/image-20221221142439795.png)

![image-20221221142507315](/Users/jamison/Library/Application Support/typora-user-images/image-20221221142507315.png)

![image-20221221142643413](/Users/jamison/Library/Application Support/typora-user-images/image-20221221142643413.png)

![image-20221221142715527](/Users/jamison/Library/Application Support/typora-user-images/image-20221221142715527.png)

![image-20221221142757936](/Users/jamison/Library/Application Support/typora-user-images/image-20221221142757936.png)

![image-20221221142843285](/Users/jamison/Library/Application Support/typora-user-images/image-20221221142843285.png)

![image-20221221143042483](/Users/jamison/Library/Application Support/typora-user-images/image-20221221143042483.png)

![image-20221221143209452](/Users/jamison/Library/Application Support/typora-user-images/image-20221221143209452.png)

![image-20221221143309145](/Users/jamison/Library/Application Support/typora-user-images/image-20221221143309145.png)

![image-20221221143433172](/Users/jamison/Library/Application Support/typora-user-images/image-20221221143433172.png)

![image-20221221143627812](/Users/jamison/Library/Application Support/typora-user-images/image-20221221143627812.png)

![image-20221221143758510](/Users/jamison/Library/Application Support/typora-user-images/image-20221221143758510.png)

![image-20221221143934496](/Users/jamison/Library/Application Support/typora-user-images/image-20221221143934496.png)

![image-20221221144000647](/Users/jamison/Library/Application Support/typora-user-images/image-20221221144000647.png)

![image-20221221144015032](/Users/jamison/Library/Application Support/typora-user-images/image-20221221144015032.png)

![image-20221221144041552](/Users/jamison/Library/Application Support/typora-user-images/image-20221221144041552.png)

![image-20221221144103508](/Users/jamison/Library/Application Support/typora-user-images/image-20221221144103508.png)

![image-20221221144137646](/Users/jamison/Library/Application Support/typora-user-images/image-20221221144137646.png)

## 数据处理之增删改课后练习



# 第九章 MySQL数据类型精讲

## MySQL中的数据类型

![image-20221221114846857](/Users/jamison/Library/Application Support/typora-user-images/image-20221221114846857.png)

其中，比较常用的数据类型有：

![image-20221221115227421](/Users/jamison/Library/Application Support/typora-user-images/image-20221221115227421.png)

常见数据类型的属性，如下：

![image-20221222115933770](/Users/jamison/Library/Application Support/typora-user-images/image-20221222115933770.png)

## 整数类型

### 1. 类型介绍

整数类型一共会有五种，包括TINYINT、SMALLINT、MEDIUMINT、INT(INTEGER)和BIGINT。

他们的区别如下表所示：

![image-20221222115650995](/Users/jamison/Library/Application Support/typora-user-images/image-20221222115650995.png)

### 2. 可选属性

1. M

![image-20221222121316620](/Users/jamison/Library/Application Support/typora-user-images/image-20221222121316620.png)

2. UNSIGNED

![image-20221222121538634](/Users/jamison/Library/Application Support/typora-user-images/image-20221222121538634.png)

3. ZEROFILL

![image-20221222121654319](/Users/jamison/Library/Application Support/typora-user-images/image-20221222121654319.png)

### 3. 使用场景

![image-20221222121736015](/Users/jamison/Library/Application Support/typora-user-images/image-20221222121736015.png)

### 4. 如何选择？

![image-20221222121929866](/Users/jamison/Library/Application Support/typora-user-images/image-20221222121929866.png)

## 浮点类型

### 1. 类型介绍

![image-20221222122216897](/Users/jamison/Library/Application Support/typora-user-images/image-20221222122216897.png)	

![image-20221222122248518](/Users/jamison/Library/Application Support/typora-user-images/image-20221222122248518.png)

### 2. 数据精度说明

![image-20221222123112016](/Users/jamison/Library/Application Support/typora-user-images/image-20221222123112016.png)

![image-20221222123131902](/Users/jamison/Library/Application Support/typora-user-images/image-20221222123131902.png)

### 3. 精度误差说明

![image-20221222123337665](/Users/jamison/Library/Application Support/typora-user-images/image-20221222123337665.png)

![image-20221222123401532](/Users/jamison/Library/Application Support/typora-user-images/image-20221222123401532.png)

## 定点数类型

### 1. 类型介绍

![image-20221222123626267](/Users/jamison/Library/Application Support/typora-user-images/image-20221222123626267.png)

![image-20221222124038426](/Users/jamison/Library/Application Support/typora-user-images/image-20221222124038426.png)

### 2. 开发中的经验

![image-20221222124208825](/Users/jamison/Library/Application Support/typora-user-images/image-20221222124208825.png)

## 位类型

![image-20221222124826117](/Users/jamison/Library/Application Support/typora-user-images/image-20221222124826117.png)

![image-20221222125113962](/Users/jamison/Library/Application Support/typora-user-images/image-20221222125113962.png)

## 日期和时间类型

![image-20221222125221299](/Users/jamison/Library/Application Support/typora-user-images/image-20221222125221299.png)

### 1. YEAR类型

![image-20221222125725831](/Users/jamison/Library/Application Support/typora-user-images/image-20221222125725831.png)

### 2. DATE类型

![image-20221222125953532](/Users/jamison/Library/Application Support/typora-user-images/image-20221222125953532.png)

![image-20221222130120442](/Users/jamison/Library/Application Support/typora-user-images/image-20221222130120442.png)

### 3. TIME类型

![image-20221222130253588](/Users/jamison/Library/Application Support/typora-user-images/image-20221222130253588.png)

![image-20221222130340579](/Users/jamison/Library/Application Support/typora-user-images/image-20221222130340579.png)

### 4. DATETIME类型

![image-20221222130508216](/Users/jamison/Library/Application Support/typora-user-images/image-20221222130508216.png)

![image-20221222130613664](/Users/jamison/Library/Application Support/typora-user-images/image-20221222130613664.png)

### 5. TIMESTAMP

![image-20221222132756450](/Users/jamison/Library/Application Support/typora-user-images/image-20221222132756450.png)

![image-20221222132827072](/Users/jamison/Library/Application Support/typora-user-images/image-20221222132827072.png)

![image-20221222133033532](/Users/jamison/Library/Application Support/typora-user-images/image-20221222133033532.png)	![image-20221222133209202](/Users/jamison/Library/Application Support/typora-user-images/image-20221222133209202.png)

![image-20221222133325132](/Users/jamison/Library/Application Support/typora-user-images/image-20221222133325132.png)

### 6. 开发中的经验

![image-20221222133443293](/Users/jamison/Library/Application Support/typora-user-images/image-20221222133443293.png)

## 文本字符串类型

![image-20221222133826674](/Users/jamison/Library/Application Support/typora-user-images/image-20221222133826674.png)

### 1. CHAR和VARCHAR类型

![image-20221222134559674](/Users/jamison/Library/Application Support/typora-user-images/image-20221222134559674.png)

![image-20221222135106898](/Users/jamison/Library/Application Support/typora-user-images/image-20221222135106898.png)

![image-20221222135359802](/Users/jamison/Library/Application Support/typora-user-images/image-20221222135359802.png)

![image-20221222135609157](/Users/jamison/Library/Application Support/typora-user-images/image-20221222135609157.png)

### 2. TEXT类型

![image-20221222135840813](/Users/jamison/Library/Application Support/typora-user-images/image-20221222135840813.png)

![image-20221222135929484](/Users/jamison/Library/Application Support/typora-user-images/image-20221222135929484.png)

![image-20221222140038563](/Users/jamison/Library/Application Support/typora-user-images/image-20221222140038563.png)

## ENUM类型

![image-20221222140138377](/Users/jamison/Library/Application Support/typora-user-images/image-20221222140138377.png)

![image-20221222140447147](/Users/jamison/Library/Application Support/typora-user-images/image-20221222140447147.png)

## SET类型

![image-20221222140518466](/Users/jamison/Library/Application Support/typora-user-images/image-20221222140518466.png)

![image-20221222140618186](/Users/jamison/Library/Application Support/typora-user-images/image-20221222140618186.png)

## 二进制字符串类型

![image-20221222143049325](/Users/jamison/Library/Application Support/typora-user-images/image-20221222143049325.png)

### 1. BINARY和VARBINARY类型

![image-20221222143129046](/Users/jamison/Library/Application Support/typora-user-images/image-20221222143129046.png)

![image-20221222143323797](/Users/jamison/Library/Application Support/typora-user-images/image-20221222143323797.png)

![image-20221222143307467](/Users/jamison/Library/Application Support/typora-user-images/image-20221222143307467.png)

### 2. BLOB类型

![image-20221222143406931](/Users/jamison/Library/Application Support/typora-user-images/image-20221222143406931.png)

![image-20221222143653301](/Users/jamison/Library/Application Support/typora-user-images/image-20221222143653301.png)

## JSON类型

![image-20221222144051564](/Users/jamison/Library/Application Support/typora-user-images/image-20221222144051564.png)

![image-20221222144136267](/Users/jamison/Library/Application Support/typora-user-images/image-20221222144136267.png)

## 空间类型

![image-20221222144252867](/Users/jamison/Library/Application Support/typora-user-images/image-20221222144252867.png)

![image-20221222144318896](/Users/jamison/Library/Application Support/typora-user-images/image-20221222144318896.png)

![image-20221222144335420](/Users/jamison/Library/Application Support/typora-user-images/image-20221222144335420.png)

## 小结和选择建议

![image-20221222144447918](/Users/jamison/Library/Application Support/typora-user-images/image-20221222144447918.png)



# 第十章 约束

## 约束(Constraint)概述

### 1. 为什么需要约束

![image-20221223140556138](/Users/jamison/Library/Application Support/typora-user-images/image-20221223140556138.png)

### 2. 什么是约束

对表中字段的限制

### 3. 约束的分类

单列约束，多列约束；列级约束，表级约束；非空，唯一，主键，外键，检查，默认值约束。

![image-20221223141306194](/Users/jamison/Library/Application Support/typora-user-images/image-20221223141306194.png)

![image-20221223141416393](/Users/jamison/Library/Application Support/typora-user-images/image-20221223141416393.png)

### 4. 如何添加/删除约束

![image-20221223141529326](/Users/jamison/Library/Application Support/typora-user-images/image-20221223141529326.png)

### 5. 查看某个表已有的约束

![image-20221223142307936](/Users/jamison/Library/Application Support/typora-user-images/image-20221223142307936.png)

 

## 非空约束

### 1. 特点

![image-20221223144002873](/Users/jamison/Library/Application Support/typora-user-images/image-20221223144002873.png)

### 2. 使用方法

![image-20221223144859277](/Users/jamison/Library/Application Support/typora-user-images/image-20221223144859277.png)

![image-20221223144933181](/Users/jamison/Library/Application Support/typora-user-images/image-20221223144933181.png)

## 唯一约束

### 1. 特点

![image-20221223145152478](/Users/jamison/Library/Application Support/typora-user-images/image-20221223145152478.png)

### 2.使用方法

建表时添加：

![image-20221223145611997](/Users/jamison/Library/Application Support/typora-user-images/image-20221223145611997.png)

建表后添加：

![image-20221223150408532](/Users/jamison/Library/Application Support/typora-user-images/image-20221223150408532.png)

![image-20221223150351225](/Users/jamison/Library/Application Support/typora-user-images/image-20221223150351225.png)

### 3. 复合的唯一性约束

![image-20221223150601912](/Users/jamison/Library/Application Support/typora-user-images/image-20221223150601912.png)

### 4. 删除唯一性约束

![image-20221223151539636](/Users/jamison/Library/Application Support/typora-user-images/image-20221223151539636.png)

如何删除：

![image-20221223151942387](/Users/jamison/Library/Application Support/typora-user-images/image-20221223151942387.png)

## PRIMARY KEY主键约束

### 1. 特点

![image-20221223152336278](/Users/jamison/Library/Application Support/typora-user-images/image-20221223152336278.png)

![image-20221223152354662](/Users/jamison/Library/Application Support/typora-user-images/image-20221223152354662.png)

### 2. 使用方法

创建表时

![image-20221223153637602](/Users/jamison/Library/Application Support/typora-user-images/image-20221223153637602.png)

没有必要起名字因为主键名总为PRIMARY

![image-20221223154802586](/Users/jamison/Library/Application Support/typora-user-images/image-20221223154802586.png)

### 3. 删除主键（开发中不会去用）

![image-20221223154852918](/Users/jamison/Library/Application Support/typora-user-images/image-20221223154852918.png)

## 自增列：AUTO_INCREMENT

### 1. 特点

![image-20221223155243078](/Users/jamison/Library/Application Support/typora-user-images/image-20221223155243078.png)

通常结合主键使用

### 2. 使用方法

建表时：

![image-20221223160221452](/Users/jamison/Library/Application Support/typora-user-images/image-20221223160221452.png)

建表后：

![image-20221223160317403](/Users/jamison/Library/Application Support/typora-user-images/image-20221223160317403.png)

### 3. 删除

![image-20221223160402483](/Users/jamison/Library/Application Support/typora-user-images/image-20221223160402483.png)

### 4. MySql8.0新特性-自增变量的持久化

![image-20221223160959909](/Users/jamison/Library/Application Support/typora-user-images/image-20221223160959909.png)

![image-20221223161046275](/Users/jamison/Library/Application Support/typora-user-images/image-20221223161046275.png)

![image-20221223161134590](/Users/jamison/Library/Application Support/typora-user-images/image-20221223161134590.png)

![image-20221223161217167](/Users/jamison/Library/Application Support/typora-user-images/image-20221223161217167.png)

## FOREIGN KEY外键约束

### 1. 作用

![image-20221223161440138](/Users/jamison/Library/Application Support/typora-user-images/image-20221223161440138.png)

### 2. 主表和从表/父表和子表

![image-20221223161540390](/Users/jamison/Library/Application Support/typora-user-images/image-20221223161540390.png)

### 3. 特点

![image-20221223161609558](/Users/jamison/Library/Application Support/typora-user-images/image-20221223161609558.png)

### 4. 使用方法

建表时：

![image-20221223163032492](/Users/jamison/Library/Application Support/typora-user-images/image-20221223163032492.png)

上面会报错，dept_id没有主键或者唯一约束

![image-20221223163201537](/Users/jamison/Library/Application Support/typora-user-images/image-20221223163201537.png)

建表后：

![image-20221223163641452](/Users/jamison/Library/Application Support/typora-user-images/image-20221223163641452.png)

### 5. 约束等级

![image-20221223164335956](/Users/jamison/Library/Application Support/typora-user-images/image-20221223164335956.png)

演示：

![image-20221223164506788](/Users/jamison/Library/Application Support/typora-user-images/image-20221223164506788.png)

### 6. 删除外键约束

![image-20221223164727754](/Users/jamison/Library/Application Support/typora-user-images/image-20221223164727754.png)

### 7. 开发场景

![image-20221223165204425](/Users/jamison/Library/Application Support/typora-user-images/image-20221223165204425.png)

### 8. 阿里开发规范

![image-20221223165317178](/Users/jamison/Library/Application Support/typora-user-images/image-20221223165317178.png)

应用层解决意思就是在代码中解决。

## CHECK约束

### 1. 作用

检查某个字段的值是否符合xx要求，一般指的是值的范围

### 2. 使用方法

说明：MySQL 5.7不支持

![image-20221223165601076](/Users/jamison/Library/Application Support/typora-user-images/image-20221223165601076.png)

## DEFAULT约束

### 1. 使用方法

建表前

![image-20221223170518830](/Users/jamison/Library/Application Support/typora-user-images/image-20221223170518830.png)

建表后

![image-20221223170557007](/Users/jamison/Library/Application Support/typora-user-images/image-20221223170557007.png)

### 2. 删除约束

![image-20221223170640087](/Users/jamison/Library/Application Support/typora-user-images/image-20221223170640087.png)

## 面试

![image-20221223171024140](/Users/jamison/Library/Application Support/typora-user-images/image-20221223171024140.png)

## 约束的课后练习题

### 练习1

![image-20221223172625368](/Users/jamison/Library/Application Support/typora-user-images/image-20221223172625368.png)

 

题目：

![image-20221223172954375](/Users/jamison/Library/Application Support/typora-user-images/image-20221223172954375.png)

解答：

```mysql
CREATE DATABASE test04_emp;
USE test04_emp;
CREATE TABLE emp2(
id INT,
emp_name VARCHAR(15)
);

CREATE TABLE dept2(
id INT,
dept_name VARCHAR(15)
);

ALTER TABLE emp2
ADD PRIMARY KEY(id);

ALTER TABLE dept2
ADD PRIMARY KEY(id);

ALTER TABLE emp2
ADD dept_id INT;

DESC emp2; 

ALTER TABLE emp2
ADD CONSTRAINT fk_emp2_deptid FOREIGN KEY(dept_id) REFERENCES dept2(id);
```

### 练习2

![image-20221223173725929](/Users/jamison/Library/Application Support/typora-user-images/image-20221223173725929.png)

![image-20221223173742679](/Users/jamison/Library/Application Support/typora-user-images/image-20221223173742679.png)

解答：

![image-20221223174442314](/Users/jamison/Library/Application Support/typora-user-images/image-20221223174442314.png)

### 练习3

![image-20221223174538656](/Users/jamison/Library/Application Support/typora-user-images/image-20221223174538656.png)

![image-20221223175556859](/Users/jamison/Library/Application Support/typora-user-images/image-20221223175556859.png)

解答：

```mysql
CREATE TABLE IF NOT EXISTS offices(
officeCode INT(10) PRIMARY KEY,
city VARCHAR(50) NOT NULL,
address VARCHAR(50),
country VARCHAR(50) NOT NULL,
postalCode VARCHAR(15),
CONSTRAINT uk_off_poscode UNIQUE(postalCode)
);

DESC offices;
```

![image-20221223175456176](/Users/jamison/Library/Application Support/typora-user-images/image-20221223175456176.png)

```mysql
ALTER TABLE employees
MODIFY moblie VARCHAR(25) AFTER officeCode;

ALTER TABLE employees
CHANGE birth employee_birth DATETIME;

ALTER TABLE employees
MODIFY sex CHAR(1) NOT NULL;

ALTER TABLE employees
DROP COLUMN note;

ALTER TABLE employees
ADD f_a VARCHAR(100);

RENAME TABLE employees
TO employees_info;
```



# 第十一章 视图

## 常见的数据库对象

![image-20221224121514981](/Users/jamison/Library/Application Support/typora-user-images/image-20221224121514981.png)

## 视图概述

![image-20221224122111892](/Users/jamison/Library/Application Support/typora-user-images/image-20221224122111892.png)

### 1. 为什么要使用视图

![image-20221224122223391](/Users/jamison/Library/Application Support/typora-user-images/image-20221224122223391.png)

### 2. 视图的理解

![image-20221224122356342](/Users/jamison/Library/Application Support/typora-user-images/image-20221224122356342.png)

![image-20221224122836297](/Users/jamison/Library/Application Support/typora-user-images/image-20221224122836297.png)

## 创建视图

![image-20221224123140931](/Users/jamison/Library/Application Support/typora-user-images/image-20221224123140931.png)

### 1. 单表创建视图

![image-20221224123709882](/Users/jamison/Library/Application Support/typora-user-images/image-20221224123709882.png)

![image-20221224123754119](/Users/jamison/Library/Application Support/typora-user-images/image-20221224123754119.png)

### 2. 多表创建视图

![image-20221224123918471](/Users/jamison/Library/Application Support/typora-user-images/image-20221224123918471.png)

### 3. 应用：利用视图对数据进行格式化

![image-20221224124340817](/Users/jamison/Library/Application Support/typora-user-images/image-20221224124340817.png)

### 4. 基于视图创建视图

![image-20221224124504388](/Users/jamison/Library/Application Support/typora-user-images/image-20221224124504388.png)

## 查看视图

![image-20221224124548834](/Users/jamison/Library/Application Support/typora-user-images/image-20221224124548834.png)

## 更新视图的数据

### 1. 一般情况

![image-20221224131003874](/Users/jamison/Library/Application Support/typora-user-images/image-20221224131003874.png)

### 2. 不可更新的视图

视图不能更新基表中不存在的字段

![image-20221224130502757](/Users/jamison/Library/Application Support/typora-user-images/image-20221224130502757.png)

还有

![image-20221224130911107](/Users/jamison/Library/Application Support/typora-user-images/image-20221224130911107.png)

> 虽然可以更新视图数据，但总的来说，视图作为`虚拟表`，主要为了方便查询，不建议更新视图的数据。对视图数据的更改，都是通过对实际数据表里数据的操作来完成的。

## 删除和修改视图

### 1. 修改

![image-20221224131546569](/Users/jamison/Library/Application Support/typora-user-images/image-20221224131546569.png)

### 2. 删除

![image-20221224132038817](/Users/jamison/Library/Application Support/typora-user-images/image-20221224132038817.png)

## 总结

### 1. 视图优点

![image-20221224132541750](/Users/jamison/Library/Application Support/typora-user-images/image-20221224132541750.png)

![image-20221224132622258](/Users/jamison/Library/Application Support/typora-user-images/image-20221224132622258.png)

### 2. 视图不足

![image-20221224132942305](/Users/jamison/Library/Application Support/typora-user-images/image-20221224132942305.png)

## 视图课后练习

### 练习1

题目：

![image-20221224132914500](/Users/jamison/Library/Application Support/typora-user-images/image-20221224132914500.png)

解答：

```MySQL
CREATE OR REPLACE VIEW employee_vu
AS
SELECT last_name, employee_id, department_id
FROM employees;

DESC employee_vu;

SELECT * FROM employee_vu;

CREATE OR REPLACE VIEW employee_vu
AS
SELECT last_name, employee_id, department_id
FROM employees
WHERE department_id = 80;
```

### 练习2

题目：

![image-20221224133818928](/Users/jamison/Library/Application Support/typora-user-images/image-20221224133818928.png)

解答：

```mysql
CREATE TABLE emps
AS
SELECT * FROM employees;
#1
CREATE OR REPLACE VIEW emp_v1
AS
SELECT last_name, salary, email
FROM emps
WHERE phone_number LIKE '011%'
AND email LIKE '%e%';

#2
CREATE OR REPLACE VIEW emp_v1
AS
SELECT last_name, salary, email, phone_number
FROM emps
WHERE phone_number LIKE '011%'
AND email LIKE '%e%';

#3
-- 会失败，因为没办法添加基表的其他字段
INSERT INTO emp_v1
VALUES('Jamison', 'qq@.coom', '011332');

#4
UPDATE emp_v1
SET salary = salary + 1000;

#5
DELETE FROM emp_v1
WHERE last_name = 'Olsen';

#6
CREATE OR REPLACE VIEW emp_v2
AS
SELECT department_id, MAX(salary)
FROM emps
GROUP BY department_id
HAVING MAX(salary) > 12000;

#7
-- 不可以,1471 - The target table emp_v2 of the INSERT is not insertable-into
INSERT INTO emp_v2
VALUES (4000, 20000);

#8
DROP VIEW IF EXISTS emp_v1, emp_v2;
```



# 第十二章 存储过程和函数

![image-20221224141912009](/Users/jamison/Library/Application Support/typora-user-images/image-20221224141912009.png)

## 存储过程概述

### 1. 理解

![image-20221226105144404](/Users/jamison/Library/Application Support/typora-user-images/image-20221226105144404.png)

### 2. 分类

![image-20221226105308252](/Users/jamison/Library/Application Support/typora-user-images/image-20221226105308252.png)

## 创建存储过程

### 1. 语法分析

![image-20221226105540751](/Users/jamison/Library/Application Support/typora-user-images/image-20221226105540751.png)

![image-20221226110639449](/Users/jamison/Library/Application Support/typora-user-images/image-20221226110639449.png)

![image-20221226111039452](/Users/jamison/Library/Application Support/typora-user-images/image-20221226111039452.png)

![image-20221226111140670](/Users/jamison/Library/Application Support/typora-user-images/image-20221226111140670.png)

### 2. 代码举例

![image-20221226112158436](/Users/jamison/Library/Application Support/typora-user-images/image-20221226112158436.png)

![image-20221229112742113](/Users/jamison/Library/Application Support/typora-user-images/image-20221229112742113.png)

![image-20221229113635294](/Users/jamison/Library/Application Support/typora-user-images/image-20221229113635294.png)	![image-20221229114239945](/Users/jamison/Library/Application Support/typora-user-images/image-20221229114239945.png)

![image-20221229114537553](/Users/jamison/Library/Application Support/typora-user-images/image-20221229114537553.png)

![image-20221229115252756](/Users/jamison/Library/Application Support/typora-user-images/image-20221229115252756.png)

![image-20221229115555638](/Users/jamison/Library/Application Support/typora-user-images/image-20221229115555638.png)

## 调用存储过程

### 1. 格式

![image-20221229115855698](/Users/jamison/Library/Application Support/typora-user-images/image-20221229115855698.png)

### 2. 如何调试

![image-20221229120025477](/Users/jamison/Library/Application Support/typora-user-images/image-20221229120025477.png)

## 存储函数的使用

![image-20221229120259426](/Users/jamison/Library/Application Support/typora-user-images/image-20221229120259426.png)

### 1. 语法分析

![image-20221229120608687](/Users/jamison/Library/Application Support/typora-user-images/image-20221229120608687.png)

### 2. 调用存储函数

![image-20221229120718756](/Users/jamison/Library/Application Support/typora-user-images/image-20221229120718756.png)

### 3. 代码举例

**注意：**

![image-20221229121132093](/Users/jamison/Library/Application Support/typora-user-images/image-20221229121132093.png)

![image-20221229121247521](/Users/jamison/Library/Application Support/typora-user-images/image-20221229121247521.png)

![image-20221229121435589](/Users/jamison/Library/Application Support/typora-user-images/image-20221229121435589.png)

![image-20221229121955600](/Users/jamison/Library/Application Support/typora-user-images/image-20221229121955600.png)

## 对比存储函数和存储过程

![image-20221229122325052](/Users/jamison/Library/Application Support/typora-user-images/image-20221229122325052.png)

## 存储过程和存储函数的查看、删除和修改

### 1. 查看

![image-20221229123716248](/Users/jamison/Library/Application Support/typora-user-images/image-20221229123716248.png)

![image-20221229124518835](/Users/jamison/Library/Application Support/typora-user-images/image-20221229124518835.png)

![image-20221229124546728](/Users/jamison/Library/Application Support/typora-user-images/image-20221229124546728.png)

![image-20221229124751188](/Users/jamison/Library/Application Support/typora-user-images/image-20221229124751188.png)

### 2. 修改

![image-20221229125044646](/Users/jamison/Library/Application Support/typora-user-images/image-20221229125044646.png)

![image-20221229125100934](/Users/jamison/Library/Application Support/typora-user-images/image-20221229125100934.png)

![image-20221229125247424](/Users/jamison/Library/Application Support/typora-user-images/image-20221229125247424.png)

### 3. 删除

![image-20221229125331925](/Users/jamison/Library/Application Support/typora-user-images/image-20221229125331925.png)

## 关于存储过程使用的争议

![image-20221229125803587](/Users/jamison/Library/Application Support/typora-user-images/image-20221229125803587.png)

### 1. 优点

![image-20221229125754506](/Users/jamison/Library/Application Support/typora-user-images/image-20221229125754506.png)

###  2. 缺点

![image-20221226110728541](/Users/jamison/Library/Application Support/typora-user-images/image-20221226110728541.png)

## 存储过程和函数练习题

### 1. 存储过程练习

![image-20221229130238431](/Users/jamison/Library/Application Support/typora-user-images/image-20221229130238431.png)	 ![image-20221229130252102](/Users/jamison/Library/Application Support/typora-user-images/image-20221229130252102.png)

![image-20221229130606584](/Users/jamison/Library/Application Support/typora-user-images/image-20221229130606584.png)

#2

![image-20221229130908652](/Users/jamison/Library/Application Support/typora-user-images/image-20221229130908652.png)

#3

![image-20221229131229821](/Users/jamison/Library/Application Support/typora-user-images/image-20221229131229821.png)

![image-20221229131454286](/Users/jamison/Library/Application Support/typora-user-images/image-20221229131454286.png)

![image-20221229131514853](/Users/jamison/Library/Application Support/typora-user-images/image-20221229131514853.png)

![image-20221229131602344](/Users/jamison/Library/Application Support/typora-user-images/image-20221229131602344.png)

![image-20221229131732561](/Users/jamison/Library/Application Support/typora-user-images/image-20221229131732561.png)

### 2. 存储函数练习

![image-20221229131909438](/Users/jamison/Library/Application Support/typora-user-images/image-20221229131909438.png)

![image-20221229131925244](/Users/jamison/Library/Application Support/typora-user-images/image-20221229131925244.png)

![image-20221229132418241](/Users/jamison/Library/Application Support/typora-user-images/image-20221229132418241.png)

![image-20221229132028490](/Users/jamison/Library/Application Support/typora-user-images/image-20221229132028490.png)

![image-20221229132103379](/Users/jamison/Library/Application Support/typora-user-images/image-20221229132103379.png)

![image-20221229132441918](/Users/jamison/Library/Application Support/typora-user-images/image-20221229132441918.png)

![image-20221229132531802](/Users/jamison/Library/Application Support/typora-user-images/image-20221229132531802.png)



# 第十三章 变量、流程控制和游标

## 变量

![image-20221229133847622](/Users/jamison/Library/Application Support/typora-user-images/image-20221229133847622.png)

### 1. 系统变量

#### 1.1 系统变量分类

![image-20221230093911590](/Users/jamison/Library/Application Support/typora-user-images/image-20221230093911590.png)

![image-20221230094007449](/Users/jamison/Library/Application Support/typora-user-images/image-20221230094007449.png)

#### 1.2 查看系统变量

- 查看所有或部分系统变量

![image-20221230094225040](/Users/jamison/Library/Application Support/typora-user-images/image-20221230094225040.png)

- 查看指定系统变量

  ![image-20221230094456260](/Users/jamison/Library/Application Support/typora-user-images/image-20221230094456260.png)

  ![image-20221230100321229](/Users/jamison/Library/Application Support/typora-user-images/image-20221230100321229.png)

- 修改系统变量

  ![image-20221230100415190](/Users/jamison/Library/Application Support/typora-user-images/image-20221230100415190.png)

  ![image-20221230100549225](/Users/jamison/Library/Application Support/typora-user-images/image-20221230100549225.png)

### 2. 用户变量

#### 2.1 用户变量分类

![image-20221230100724800](/Users/jamison/Library/Application Support/typora-user-images/image-20221230100724800.png)

#### 2.2 会话用户变量

- 变量的定义

  ![image-20221230101001544](/Users/jamison/Library/Application Support/typora-user-images/image-20221230101001544.png)

  ![image-20221230101409819](/Users/jamison/Library/Application Support/typora-user-images/image-20221230101409819.png)

#### 2.3 局部变量

![image-20221230101448133](/Users/jamison/Library/Application Support/typora-user-images/image-20221230101448133.png)

![image-20221230101509587](/Users/jamison/Library/Application Support/typora-user-images/image-20221230101509587.png)

![image-20221230104149924](/Users/jamison/Library/Application Support/typora-user-images/image-20221230104149924.png)

![image-20221230104312322](/Users/jamison/Library/Application Support/typora-user-images/image-20221230104312322.png) ![image-20221230104555399](/Users/jamison/Library/Application Support/typora-user-images/image-20221230104555399.png)

![image-20221230104621482](/Users/jamison/Library/Application Support/typora-user-images/image-20221230104621482.png)

![image-20221230104846138](/Users/jamison/Library/Application Support/typora-user-images/image-20221230104846138.png)

![image-20221230105110770](/Users/jamison/Library/Application Support/typora-user-images/image-20221230105110770.png)

#### 2.4 对比会话变量与局部变量

![image-20221230105336740](/Users/jamison/Library/Application Support/typora-user-images/image-20221230105336740.png)

## 定义条件与处理程序

相当于异常处理

![image-20221230105459837](/Users/jamison/Library/Application Support/typora-user-images/image-20221230105459837.png)

### 1. 定义条件

![image-20221230110100879](/Users/jamison/Library/Application Support/typora-user-images/image-20221230110100879.png)

![image-20221230110126525](/Users/jamison/Library/Application Support/typora-user-images/image-20221230110126525.png)

- 案例1

  ![image-20221230110427529](/Users/jamison/Library/Application Support/typora-user-images/image-20221230110427529.png)

- 案例2

  ![image-20221230110448839](/Users/jamison/Library/Application Support/typora-user-images/image-20221230110448839.png)

### 2. 定义处理程序

![image-20221230110526649](/Users/jamison/Library/Application Support/typora-user-images/image-20221230110526649.png)

![image-20221230110953980](/Users/jamison/Library/Application Support/typora-user-images/image-20221230110953980.png)

![image-20221230111242824](/Users/jamison/Library/Application Support/typora-user-images/image-20221230111242824.png)

## 流程控制

![image-20221230111537514](/Users/jamison/Library/Application Support/typora-user-images/image-20221230111537514.png)

### 1. 分支结构IF

![image-20221230111843391](/Users/jamison/Library/Application Support/typora-user-images/image-20221230111843391.png)

### 2. 分支结构CASE

![image-20221230112214640](/Users/jamison/Library/Application Support/typora-user-images/image-20221230112214640.png)

### 3. 循环结构LOOP

![image-20221230112340838](/Users/jamison/Library/Application Support/typora-user-images/image-20221230112340838.png)

### 4. 循环结构WHILE

![image-20221230113141696](/Users/jamison/Library/Application Support/typora-user-images/image-20221230113141696.png)

![image-20221230113223585](/Users/jamison/Library/Application Support/typora-user-images/image-20221230113223585.png)

### 5. 循环结构REPEAT

![image-20221230113403310](/Users/jamison/Library/Application Support/typora-user-images/image-20221230113403310.png)

![image-20221230113527398](/Users/jamison/Library/Application Support/typora-user-images/image-20221230113527398.png)

**对比这三种循环结构**：

![image-20221230113645477](/Users/jamison/Library/Application Support/typora-user-images/image-20221230113645477.png)

### 6. 跳转语句LEAVE

![image-20221230114907952](/Users/jamison/Library/Application Support/typora-user-images/image-20221230114907952.png)

![image-20221230133724382](/Users/jamison/Library/Application Support/typora-user-images/image-20221230133724382.png)

### 7. 跳转语句ITERATE

![image-20221230133910570](/Users/jamison/Library/Application Support/typora-user-images/image-20221230133910570.png)

![image-20221230134009717](/Users/jamison/Library/Application Support/typora-user-images/image-20221230134009717.png)

## 游标

### 1. 什么是游标（或光标）

![image-20221230134239283](/Users/jamison/Library/Application Support/typora-user-images/image-20221230134239283.png)

![image-20221230134856584](/Users/jamison/Library/Application Support/typora-user-images/image-20221230134856584.png)

### 2. 使用游标步骤

![image-20221230135035831](/Users/jamison/Library/Application Support/typora-user-images/image-20221230135035831.png)

- 第二步 打开游标

  ![image-20221230135139882](/Users/jamison/Library/Application Support/typora-user-images/image-20221230135139882.png)

- 第三步 使用游标

  ![image-20221230135319455](/Users/jamison/Library/Application Support/typora-user-images/image-20221230135319455.png)

- 第四步 关闭游标

  ![image-20221230135357485](/Users/jamison/Library/Application Support/typora-user-images/image-20221230135357485.png)

![image-20221230140039659](/Users/jamison/Library/Application Support/typora-user-images/image-20221230140039659.png)

### 3. 小结

![image-20221230140115239](/Users/jamison/Library/Application Support/typora-user-images/image-20221230140115239.png)

## 补充：MySQL 8.0 新特性

![image-20221230140410647](/Users/jamison/Library/Application Support/typora-user-images/image-20221230140410647.png)



# 第十四章 触发器

![image-20221230140950595](/Users/jamison/Library/Application Support/typora-user-images/image-20221230140950595.png)

## 触发器概述

![image-20221230141107602](/Users/jamison/Library/Application Support/typora-user-images/image-20221230141107602.png)

## 触发器创建

### 1. 创建触发器语法

![image-20221230141335354](/Users/jamison/Library/Application Support/typora-user-images/image-20221230141335354.png)

### 2. 代码案例

- 案例一

  ![image-20221230141708395](/Users/jamison/Library/Application Support/typora-user-images/image-20221230141708395.png)

- 举例二

  ![image-20221230141811814](/Users/jamison/Library/Application Support/typora-user-images/image-20221230141811814.png)

## 查看、删除触发器

### 1. 查看

![image-20221230142131076](/Users/jamison/Library/Application Support/typora-user-images/image-20221230142131076.png)

### 2. 删除

![image-20221230142149048](/Users/jamison/Library/Application Support/typora-user-images/image-20221230142149048.png)

## 触发器优缺点

### 1. 优点

- 触发器可以确保数据的完整性

  ![image-20221230142534568](/Users/jamison/Library/Application Support/typora-user-images/image-20221230142534568.png)

  ![image-20221230142552259](/Users/jamison/Library/Application Support/typora-user-images/image-20221230142552259.png)

- 可以帮助我们记录日志

  ![image-20221230142621337](/Users/jamison/Library/Application Support/typora-user-images/image-20221230142621337.png)

- 可以用在操作数据前，对数据进行合法性检查

  ![image-20221230142710914](/Users/jamison/Library/Application Support/typora-user-images/image-20221230142710914.png)

### 2. 缺点

- 最大的问题就是可读性差

  ![image-20221230142900421](/Users/jamison/Library/Application Support/typora-user-images/image-20221230142900421.png)

- 相关数据的变更，可能导致触发器出错

  ![image-20221230142932701](/Users/jamison/Library/Application Support/typora-user-images/image-20221230142932701.png)

### 3. 注意点

![image-20221230143057782](/Users/jamison/Library/Application Support/typora-user-images/image-20221230143057782.png)
