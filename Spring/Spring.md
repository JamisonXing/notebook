# 第一章 初识Spring

## 1. 课程介绍

### 1.1 为什么要学

![image-20230123221211494](/Users/jamison/Library/Application Support/typora-user-images/image-20230123221211494.png)

### 1.2 学什么

![image-20230123221224514](/Users/jamison/Library/Application Support/typora-user-images/image-20230123221224514.png)

### 1.3 怎么学

![image-20230123221245451](/Users/jamison/Library/Application Support/typora-user-images/image-20230123221245451.png)

## 2. 初识Spring

### 2.1 Spring家族和Spring

![image-20230125155144998](/Users/jamison/Library/Application Support/typora-user-images/image-20230125155144998.png)

### 2.2 Spring发展史

![image-20230125155231853](/Users/jamison/Library/Application Support/typora-user-images/image-20230125155231853.png)

## 3. Spring FrameWork系统架构

### 3.1 Spring系统架构图

![image-20230125155638348](/Users/jamison/Library/Application Support/typora-user-images/image-20230125155638348.png)

### 3.2 学习路线

![image-20230125155725597](/Users/jamison/Library/Application Support/typora-user-images/image-20230125155725597.png)

## 4. 核心概念

### 4.1 IoC/DI思想

- 代码书写现状，不使用Spring

  ![image-20230125160604346](/Users/jamison/Library/Application Support/typora-user-images/image-20230125160604346.png)

  如果此时，业务层想换个新的实现：

  ![image-20230125160722389](/Users/jamison/Library/Application Support/typora-user-images/image-20230125160722389.png)

  导致耦合度偏高

- 解决方案

  - 适用对象时，在程序中不要主动使用new产生对象，转换为由**外部**提供对象

- IoC(Inversion of Control)控制反转

  ![image-20230125160944830](/Users/jamison/Library/Application Support/typora-user-images/image-20230125160944830.png)

  

### 4.2 IoC/Bean  ![image-20230125161028954](/Users/jamison/Library/Application Support/typora-user-images/image-20230125161028954.png)

### 4.3 DI(Dependency Injection)依赖注入

![image-20230125161152780](/Users/jamison/Library/Application Support/typora-user-images/image-20230125161152780.png)

- 最终目标：**充分解耦**
  - 使用IoC容器管理bean（IoC)
  - 在IoC容器内将有依赖关系的bean进行关系绑定（DI）
- 最终效果
  - 使用对象的时候不仅可以直接从IoC容器中获取，并且获取到的bean已经绑定了所有的依赖关系。

## 5. IoC入门案例

### 5.1 IoC入门案例思路分析

![image-20230125165432542](/Users/jamison/Library/Application Support/typora-user-images/image-20230125165432542.png)

### 5.2 步骤

![image-20230125165511579](/Users/jamison/Library/Application Support/typora-user-images/image-20230125165511579.png)

![image-20230125165528903](/Users/jamison/Library/Application Support/typora-user-images/image-20230125165528903.png)

![image-20230125165544199](/Users/jamison/Library/Application Support/typora-user-images/image-20230125165544199.png)

![image-20230125165558395](/Users/jamison/Library/Application Support/typora-user-images/image-20230125165558395.png)

## 6. DI入门案例

### 6.1 DI入门案例思路分析

![image-20230125210558898](/Users/jamison/Library/Application Support/typora-user-images/image-20230125210558898.png)

### 6.2 步骤

![image-20230125210646414](/Users/jamison/Library/Application Support/typora-user-images/image-20230125210646414.png)

![image-20230125210712069](/Users/jamison/Library/Application Support/typora-user-images/image-20230125210712069.png)

![image-20230125210745025](/Users/jamison/Library/Application Support/typora-user-images/image-20230125210745025.png)

