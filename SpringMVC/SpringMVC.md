		![image-20230129160758828](/Users/jamison/Library/Application Support/typora-user-images/image-20230129160758828.png)

![image-20230129160815777](/Users/jamison/Library/Application Support/typora-user-images/image-20230129160815777.png)

# 第一章 SpringMVC简介

## 1. SpringMVC简介

### 1.1 SpringMVC概述

请求响应过程模式演进过程：

- 三层架构

  ![image-20230129161059792](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161059792.png)

  ![image-20230129161111862](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161111862.png)

- MVC模式

  ![image-20230129161137485](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161137485.png)

- 异步调用

  ![image-20230129161221041](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161221041.png)

  ![image-20230129161240288](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161240288.png)

  最终：

  ![image-20230129161256934](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161256934.png)

### 1.2 小结

![image-20230129161344574](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161344574.png)

## 2. 入门案例

### 2.1 Servlet和SpringMVC开发web程序流程

![image-20230129161645526](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161645526.png)

### 2.2 步骤

![image-20230129161729433](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161729433.png)

![image-20230129161737974](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161737974.png)

![image-20230129161749001](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161749001.png)

![image-20230129161800147](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161800147.png)

![image-20230129161819767](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161819767.png)

![image-20230129161832970](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161832970.png)

![image-20230129161846150](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161846150.png)

![image-20230129161854145](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161854145.png)

### 2.3 主要类、方法、注解介绍

- @ResponseBody

  ![image-20230129161945154](/Users/jamison/Library/Application Support/typora-user-images/image-20230129161945154.png)

- @RequestMapping

  ![image-20230129162029958](/Users/jamison/Library/Application Support/typora-user-images/image-20230129162029958.png)

- AbstractDispatcherServletInitializer

  ![image-20230129162113261](/Users/jamison/Library/Application Support/typora-user-images/image-20230129162113261.png)

  ![image-20230129162250683](/Users/jamison/Library/Application Support/typora-user-images/image-20230129162250683.png)

  ![image-20230129162235609](/Users/jamison/Library/Application Support/typora-user-images/image-20230129162235609.png)

### 2.4 小结

![image-20230129162401963](/Users/jamison/Library/Application Support/typora-user-images/image-20230129162401963.png)

## 3. 入门案例工作流程分析

**启动服务器初始化过程和单次请求过程：**

**启动服务器初始化过程：**

![image-20230129162449133](/Users/jamison/Library/Application Support/typora-user-images/image-20230129162449133.png)

图解：

1. 初始化web容器

   ![image-20230129162518077](/Users/jamison/Library/Application Support/typora-user-images/image-20230129162518077.png)

2. 执行方法，创建SpringMVC容器（体现为WebApplicationContext对象）

   ![image-20230129162650973](/Users/jamison/Library/Application Support/typora-user-images/image-20230129162650973.png)

   ![image-20230129162702410](/Users/jamison/Library/Application Support/typora-user-images/image-20230129162702410.png)

   再将这个springMVC容器放到servlet容器中

   ![image-20230129162949055](/Users/jamison/Library/Application Support/typora-user-images/image-20230129162949055.png)

3. 加载配置类

   ![image-20230129163118982](/Users/jamison/Library/Application Support/typora-user-images/image-20230129163118982.png)

4. 执行@ComponentScan，加载对应的bean

   ![image-20230129163241989](/Users/jamison/Library/Application Support/typora-user-images/image-20230129163241989.png)

   ![image-20230129163307195](/Users/jamison/Library/Application Support/typora-user-images/image-20230129163307195.png)

5. 加载UserController bean，注册映射

   ![image-20230129163511899](/Users/jamison/Library/Application Support/typora-user-images/image-20230129163511899.png)

   ![image-20230129163527064](/Users/jamison/Library/Application Support/typora-user-images/image-20230129163527064.png)

   这里有点问题，实际上所有的springmvc映射都是放在一起统一管理的，而不是放在每一个bean中

6. 定义请求通过springmvc执行

   ![image-20230129163731972](/Users/jamison/Library/Application Support/typora-user-images/image-20230129163731972.png)
   

**单次请求过程：**

![image-20230129163816510](/Users/jamison/Library/Application Support/typora-user-images/image-20230129163816510.png)

## 4. bean加载控制

![image-20230129164149970](/Users/jamison/Library/Application Support/typora-user-images/image-20230129164149970.png)

因为功能不同，如何避免Spring加载到SpringMVC的bean？

> 加载Spring控制的bean的时候排除掉SpringMVC控制的bean，具体方法如下图

![image-20230129164433316](/Users/jamison/Library/Application Support/typora-user-images/image-20230129164433316.png)

1. 方式一：

   ![image-20230129164655688](/Users/jamison/Library/Application Support/typora-user-images/image-20230129164655688.png)

2. 方式二：

   ![image-20230129164543996](/Users/jamison/Library/Application Support/typora-user-images/image-20230129164543996.png)

**简化bean的加载代码：**

原来的：

![image-20230129164747103](/Users/jamison/Library/Application Support/typora-user-images/image-20230129164747103.png)

![image-20230129164814090](/Users/jamison/Library/Application Support/typora-user-images/image-20230129164814090.png)

## 5. PostMan

![image-20230129164837751](/Users/jamison/Library/Application Support/typora-user-images/image-20230129164837751.png)

# 第二章 请求与响应

## 1. 请求映射路径

![image-20230129175143761](/Users/jamison/Library/Application Support/typora-user-images/image-20230129175143761.png)

类的上也要加@RequestMapping(“/模块名”)，防止请求出现冲突，比如有两个模块user和book他们都有save方法，那么只有@RequestMapping(“/save”)是不行的，会冲突。

## 2. get请求和post请求发送普通参数

- get

  ![image-20230129175617973](/Users/jamison/Library/Application Support/typora-user-images/image-20230129175617973.png)

- post

  ![image-20230129175632851](/Users/jamison/Library/Application Support/typora-user-images/image-20230129175632851.png)

**中文乱码处理：**

![image-20230129175736414](/Users/jamison/Library/Application Support/typora-user-images/image-20230129175736414.png)

## 3. 五种类型参数传递

**五种请求参数：**

![image-20230129175946599](/Users/jamison/Library/Application Support/typora-user-images/image-20230129175946599.png)

- 普通参数

  ![image-20230129180006104](/Users/jamison/Library/Application Support/typora-user-images/image-20230129180006104.png)

  如果参数名与形参变量名不同，使用@RequestParam绑定参数关系：

  ![image-20230129180116525](/Users/jamison/Library/Application Support/typora-user-images/image-20230129180116525.png)

  关于@RequestParam注解：

  ![image-20230129180203839](/Users/jamison/Library/Application Support/typora-user-images/image-20230129180203839.png)

- POJO参数

  ![image-20230129180514877](/Users/jamison/Library/Application Support/typora-user-images/image-20230129180514877.png)

- 嵌套POJO参数

  ![image-20230129180558249](/Users/jamison/Library/Application Support/typora-user-images/image-20230129180558249.png)

  ![image-20230129180618564](/Users/jamison/Library/Application Support/typora-user-images/image-20230129180618564.png)

- 数组参数

  相同名称传递就行

  ![image-20230129180720638](/Users/jamison/Library/Application Support/typora-user-images/image-20230129180720638.png)

- 集合保存普通参数

  ![image-20230129180827969](/Users/jamison/Library/Application Support/typora-user-images/image-20230129180827969.png)

## 4. json数据传递参数

请求参数：

![image-20230129181026425](/Users/jamison/Library/Application Support/typora-user-images/image-20230129181026425.png)

### 4.1 准备步骤

1. 添加json数据转换相关坐标

   ![image-20230129181108804](/Users/jamison/Library/Application Support/typora-user-images/image-20230129181108804.png)

2. 设置postman

   ![image-20230129181128186](/Users/jamison/Library/Application Support/typora-user-images/image-20230129181128186.png)

3. 开启自动转换json数据的支持

   ![image-20230129181204078](/Users/jamison/Library/Application Support/typora-user-images/image-20230129181204078.png)

4. 设置接受json数据

   ![image-20230129181233522](/Users/jamison/Library/Application Support/typora-user-images/image-20230129181233522.png)

   这里不用@RequestPram是@RequestBody，因为需要的数据在body中。

### 4.2 相关注解

- ![image-20230129181400051](/Users/jamison/Library/Application Support/typora-user-images/image-20230129181400051.png)
- ![image-20230129181454420](/Users/jamison/Library/Application Support/typora-user-images/image-20230129181454420.png)

### 4.3 请求参数

1. 传递json对象

   ![image-20230129181612760](/Users/jamison/Library/Application Support/typora-user-images/image-20230129181612760.png)

2. json集合对象（json数组同理）

   ![image-20230129181716420](/Users/jamison/Library/Application Support/typora-user-images/image-20230129181716420.png)

### 4.4 @RequestBody和@RequestParam区别

![image-20230129181858909](/Users/jamison/Library/Application Support/typora-user-images/image-20230129181858909.png)

@RequestParam

![image-20230129182035500](/Users/jamison/Library/Application Support/typora-user-images/image-20230129182035500.png)

@RequestBody:

![image-20230129182027941](/Users/jamison/Library/Application Support/typora-user-images/image-20230129182027941.png)

## 5. 日期类型参数传递

### 5.1 @DateTimeFormat

![image-20230129182118255](/Users/jamison/Library/Application Support/typora-user-images/image-20230129182118255.png)

![image-20230129182154265](/Users/jamison/Library/Application Support/typora-user-images/image-20230129182154265.png)

**相关注解：@DateTimeFormat**

![image-20230129182231995](/Users/jamison/Library/Application Support/typora-user-images/image-20230129182231995.png)

### 5.2 类型转换器

类型转换靠的是类型转换器：

![image-20230129182300972](/Users/jamison/Library/Application Support/typora-user-images/image-20230129182300972.png)

## 6. 响应

![image-20230129182434049](/Users/jamison/Library/Application Support/typora-user-images/image-20230129182434049.png)

- 响应页面（页面跳转）

  ![image-20230129182535508](/Users/jamison/Library/Application Support/typora-user-images/image-20230129182535508.png)

- 响应文本

  ![image-20230129182559673](/Users/jamison/Library/Application Support/typora-user-images/image-20230129182559673.png)

  @ResponseBody：

  ![image-20230129182911031](/Users/jamison/Library/Application Support/typora-user-images/image-20230129182911031.png)

  这里的作用只是其中之一，后面还会讲一个。

- 响应json数据（对象转json)

  ![image-20230129182642990](/Users/jamison/Library/Application Support/typora-user-images/image-20230129182642990.png)

- 响应json数据（对象集合转json数组)

  ![image-20230129182716785](/Users/jamison/Library/Application Support/typora-user-images/image-20230129182716785.png)

**@ResponseBody注解：**

![image-20230129183050139](/Users/jamison/Library/Application Support/typora-user-images/image-20230129183050139.png)

这里的响应体为String，如果为pojo或者集合等，会自动转化为json，且使用的转换器不再是convert接口而是httpMessageConvert。

![image-20230129183451111](/Users/jamison/Library/Application Support/typora-user-images/image-20230129183451111.png)

![image-20230129183550727](/Users/jamison/Library/Application Support/typora-user-images/image-20230129183550727.png)

# 第三章 RESTful

## 1. REST简介

表现形式状态转换

![image-20230131145955921](/Users/jamison/Library/Application Support/typora-user-images/image-20230131145955921.png)

restful风格：

开发中常用的四种，get 查询,post 新增/保存,put 修改/查询,delete 删除

![image-20230131150050275](/Users/jamison/Library/Application Support/typora-user-images/image-20230131150050275.png)

## 2. RESTful入门案例

请求方案设定，请求参数设定

### 2.1 步骤

![image-20230131150317177](/Users/jamison/Library/Application Support/typora-user-images/image-20230131150317177.png)

![image-20230131150332125](/Users/jamison/Library/Application Support/typora-user-images/image-20230131150332125.png)

### 2.2 注解

- @RequestMapping

  ![image-20230131150453418](/Users/jamison/Library/Application Support/typora-user-images/image-20230131150453418.png)

- @PathVariable

  ![image-20230131150545075](/Users/jamison/Library/Application Support/typora-user-images/image-20230131150545075.png)

### 2.3 @RequestBody,@RequestParam,@PathVariable区别与应用

![image-20230131150814545](/Users/jamison/Library/Application Support/typora-user-images/image-20230131150814545.png)

## 3. RESTful快速开发

### 3.1 简化前后

简化前：

![image-20230131150959485](/Users/jamison/Library/Application Support/typora-user-images/image-20230131150959485.png)

![image-20230131150949946](/Users/jamison/Library/Application Support/typora-user-images/image-20230131150949946.png)

简化后：

![image-20230131151100507](/Users/jamison/Library/Application Support/typora-user-images/image-20230131151100507.png)

### 3.2 使用的注解

- @RestController

  ![image-20230131151231906](/Users/jamison/Library/Application Support/typora-user-images/image-20230131151231906.png)

- @Get/Post/Delete/Put-Mapping

  ![image-20230131151431445](/Users/jamison/Library/Application Support/typora-user-images/image-20230131151431445.png)

## 4. 案例：基于RESTful页面数据交互

步骤：

1. 编写controller

   ![image-20230131151703684](/Users/jamison/Library/Application Support/typora-user-images/image-20230131151703684.png)

2. 设置静态资源放行

   因为ServletContainersInitConfig(web容器配置类)类中对所有路径都设置为要通过mvc处理：

   ![image-20230131151946286](/Users/jamison/Library/Application Support/typora-user-images/image-20230131151946286.png)

   所以要再编写一个SpringMvcSupport放行静态资源：

   ![image-20230131152106214](/Users/jamison/Library/Application Support/typora-user-images/image-20230131152106214.png)

3. 前端页面异步调用

   ![image-20230131152131751](/Users/jamison/Library/Application Support/typora-user-images/image-20230131152131751.png)

# 第四章 表现层和前端数据传输

## 1. SSM整合

![image-20230131185614284](/Users/jamison/Library/Application Support/typora-user-images/image-20230131185614284.png)

### 1.1 Spring整合Mybatis

![image-20230131185647876](/Users/jamison/Library/Application Support/typora-user-images/image-20230131185647876.png)

![image-20230131185702408](/Users/jamison/Library/Application Support/typora-user-images/image-20230131185702408.png)

![image-20230131185723865](/Users/jamison/Library/Application Support/typora-user-images/image-20230131185723865.png)

事务处理：

![image-20230131185812244](/Users/jamison/Library/Application Support/typora-user-images/image-20230131185812244.png)

### 1.2 Spring整合SpringMVC

![image-20230131185936391](/Users/jamison/Library/Application Support/typora-user-images/image-20230131185936391.png)

![image-20230131185948868](/Users/jamison/Library/Application Support/typora-user-images/image-20230131185948868.png)

![image-20230131190008646](/Users/jamison/Library/Application Support/typora-user-images/image-20230131190008646.png)

## 2. 表现层数据封装

后端返回的数据可以有很多格式

![image-20230131190148124](/Users/jamison/Library/Application Support/typora-user-images/image-20230131190148124.png)

为了方便前后端对接，需要同一格式：

根据项目组的约定来决定使用某一种格式

- data

  ![image-20230131190445714](/Users/jamison/Library/Application Support/typora-user-images/image-20230131190445714.png)

- code

  ![image-20230131190505934](/Users/jamison/Library/Application Support/typora-user-images/image-20230131190505934.png)

- message

  ![image-20230131190523454](/Users/jamison/Library/Application Support/typora-user-images/image-20230131190523454.png)

后端需要编写统一数据返回的结果类：

根据项目组的约定来决定

![image-20230131190635600](/Users/jamison/Library/Application Support/typora-user-images/image-20230131190635600.png)

![image-20230131192120623](/Users/jamison/Library/Application Support/typora-user-images/image-20230131192120623.png)

![image-20230131192144570](/Users/jamison/Library/Application Support/typora-user-images/image-20230131192144570.png)



# 第五章 异常处理&前后台协议联调

## 1.异常处理器

### 1.1 常见异常和思考

开发过程中难免遇到异常，出现异常的常见位置和诱因如下：

![image-20230202170023175](/Users/jamison/Library/Application Support/typora-user-images/image-20230202170023175.png)

思考：

![image-20230202170059421](/Users/jamison/Library/Application Support/typora-user-images/image-20230202170059421.png)

![image-20230202170109450](/Users/jamison/Library/Application Support/typora-user-images/image-20230202170109450.png)

### 1.2 异常处理器

集中的、统一的处理项目中出现的异常

![image-20230202170321278](/Users/jamison/Library/Application Support/typora-user-images/image-20230202170321278.png)

效果对比：

![image-20230202170459265](/Users/jamison/Library/Application Support/typora-user-images/image-20230202170459265.png)

### 1.3 注解

![image-20230202170421049](/Users/jamison/Library/Application Support/typora-user-images/image-20230202170421049.png)

## 2. 项目异常处理

### 2.1 项目异常分类

![image-20230202170637572](/Users/jamison/Library/Application Support/typora-user-images/image-20230202170637572.png)

### 2.2 项目异常处理方案

![image-20230202170711797](/Users/jamison/Library/Application Support/typora-user-images/image-20230202170711797.png)

### 2.3 项目异常处理步骤

1. 自定义项目系统级异常

   ![image-20230202170808986](/Users/jamison/Library/Application Support/typora-user-images/image-20230202170808986.png)

2. 自定义项目业务级异常

   ![image-20230202170844769](/Users/jamison/Library/Application Support/typora-user-images/image-20230202170844769.png)

3. 自定义异常编码

   ![image-20230202170927790](/Users/jamison/Library/Application Support/typora-user-images/image-20230202170927790.png)

4. 触发自定义异常

   ![image-20230202170953033](/Users/jamison/Library/Application Support/typora-user-images/image-20230202170953033.png)

5. 拦截并处理异常

   ![image-20230202171025243](/Users/jamison/Library/Application Support/typora-user-images/image-20230202171025243.png)

6. 异常处理器效果对比

   ![image-20230202171051252](/Users/jamison/Library/Application Support/typora-user-images/image-20230202171051252.png)

## 3. SSM整合标准开发

自己看视频动手实践



# 第六章 拦截器

## 1. 拦截器简介

### 1.1 拦截器概念

![image-20230202171632580](/Users/jamison/Library/Application Support/typora-user-images/image-20230202171632580.png)

![image-20230202171908437](/Users/jamison/Library/Application Support/typora-user-images/image-20230202171908437.png)

### 1.2 拦截器与过滤器的区别

![image-20230202171929410](/Users/jamison/Library/Application Support/typora-user-images/image-20230202171929410.png)

## 2. 拦截器入门案例

### 2.1 实现步骤

1. 声明拦截器bean，并实现HandlerIntercepter接口（注意：扫描加载bean)

   ![image-20230202172132342](/Users/jamison/Library/Application Support/typora-user-images/image-20230202172132342.png)

2. 定义配置类，继承WebConfigurationSupport，实现addInterceptror方法（注意：扫描加载配置）

   ![image-20230202172247507](/Users/jamison/Library/Application Support/typora-user-images/image-20230202172247507.png)

3. 添加拦截器并设定拦截的访问路径，路径可以通过可变参数设置多个

   ![image-20230202172344461](/Users/jamison/Library/Application Support/typora-user-images/image-20230202172344461.png)

4. 使用标准接口WebConfigurer简化开发（注意：侵入性较强）

   ![image-20230202172635940](/Users/jamison/Library/Application Support/typora-user-images/image-20230202172635940.png)

### 2.2 执行流程

![image-20230202172725415](/Users/jamison/Library/Application Support/typora-user-images/image-20230202172725415.png)

### 2.3 小结

![image-20230202172756245](/Users/jamison/Library/Application Support/typora-user-images/image-20230202172756245.png)

## 3. 拦截器参数

1. 前置处理方法的参数 preHandle

   ![image-20230202172919540](/Users/jamison/Library/Application Support/typora-user-images/image-20230202172919540.png)

2. 后置处理方法的参数 postHandle

   ![image-20230202173041953](/Users/jamison/Library/Application Support/typora-user-images/image-20230202173041953.png)

3. 完成后处理方法的参数 afterCompletion

   ![image-20230202173127560](/Users/jamison/Library/Application Support/typora-user-images/image-20230202173127560.png)

**小结：**

![image-20230202173204190](/Users/jamison/Library/Application Support/typora-user-images/image-20230202173204190.png)

## 4. 拦截器链配置

![image-20230202173554495](/Users/jamison/Library/Application Support/typora-user-images/image-20230202173554495.png)

pre其中一个报错会导致后面的controller，post和**部分**after不运行，部分after的意思是成功运行的pre的after方法会运行，比如pre3报错，那么pre1,pre2的after2，after1会运行。
