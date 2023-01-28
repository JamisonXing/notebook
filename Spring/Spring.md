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

# 第二章 bean实例化

## 1. bean基础配置

### 1.1 bean基础配置

![image-20230125212620175](/Users/jamison/Library/Application Support/typora-user-images/image-20230125212620175.png)

### 1.2 bean别名配置

![image-20230125212721847](/Users/jamison/Library/Application Support/typora-user-images/image-20230125212721847.png)

### 1.3 bean作用范围说明

bean默认为单例，可以设置为多例：

```xml
<bean id="bookDao" name="dao" class="com.jamison.dao.impl.BookDaoImpl" scope="prototype"/>
```

```java
public class APP2 {
    public static void main(String[] args) {
        //3. 获取IoC容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //4.获取bean（根据bean配置id获取）
        BookDao bookDao = (BookDao) ctx.getBean("bookDao");
        BookDao bookDao1 = (BookDao) ctx.getBean("bookDao");
//        bookDao.save();
//        BookService bookService = (BookService) ctx.getBean("bookService");
//        bookService.save();

        System.out.println(bookDao);
        System.out.println(bookDao1);
    }
}
```

result:

```java
com.jamison.dao.impl.BookDaoImpl@69ea3742
com.jamison.dao.impl.BookDaoImpl@4b952a2d
```

![image-20230125213327285](/Users/jamison/Library/Application Support/typora-user-images/image-20230125213327285.png)

## 2. bean实例化

bean是如何创建的，实例化bean的三种方式：

![image-20230126121116329](/Users/jamison/Library/Application Support/typora-user-images/image-20230126121116329.png)

### 2.1 构造方法

![image-20230126111649967](/Users/jamison/Library/Application Support/typora-user-images/image-20230126111649967.png)

实际上调用的是无参构造，有参构造会报错：

![image-20230126111731647](/Users/jamison/Library/Application Support/typora-user-images/image-20230126111731647.png)

而且private修饰的构造方法，spring也可以调用因为是通过反射实现的：

![image-20230126111852799](/Users/jamison/Library/Application Support/typora-user-images/image-20230126111852799.png)

### 2.2 静态工厂(了解)

- 静态工厂

  ```java
  public class BookDaoFactory {
      public static BookDao bookDaoFactory() {
          System.out.println("bookDaoFactory setup...");
          return new BookDaoImpl();
      }
  }
  ```

- 配置

  ```xml
  <bean id="bookDao" class="com.jamison.factory.BookDaoFactory" factory-method="bookDaoFactory"/>
  ```

- 运行

  ```java
  public class APP3 {
      public static void main(String[] args) {
          ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
          BookDao bookDao = (BookDao) ctx.getBean("bookDao");
          bookDao.save();
      }
  }
  ```

### 2.3 实例工厂

工厂方法不是静态的，需要先创建工厂类对象，才能调用该方法

![image-20230126121333096](/Users/jamison/Library/Application Support/typora-user-images/image-20230126121333096.png)

普通方式：

![image-20230126121429238](/Users/jamison/Library/Application Support/typora-user-images/image-20230126121429238.png)

spring方式：

![image-20230126121510572](/Users/jamison/Library/Application Support/typora-user-images/image-20230126121510572.png)

![image-20230126121531744](/Users/jamison/Library/Application Support/typora-user-images/image-20230126121531744.png)

但是有缺点：

![image-20230126121602756](/Users/jamison/Library/Application Support/typora-user-images/image-20230126121602756.png)

于是有了第四种方式：

### 2.4 FactoryBean（常用）

![image-20230126121816347](/Users/jamison/Library/Application Support/typora-user-images/image-20230126121816347.png)

![image-20230126121826729](/Users/jamison/Library/Application Support/typora-user-images/image-20230126121826729.png)

![image-20230126121832637](/Users/jamison/Library/Application Support/typora-user-images/image-20230126121832637.png)

如果想创建多例或者单例的对象，可以再bean类中加上isSingleton方法：![image-20230126121936471](/Users/jamison/Library/Application Support/typora-user-images/image-20230126121936471.png)

小结：

![image-20230126121953792](/Users/jamison/Library/Application Support/typora-user-images/image-20230126121953792.png)

## 3. bean的声明周期

### 3.1 bean声明周期控制

![image-20230126125357792](/Users/jamison/Library/Application Support/typora-user-images/image-20230126125357792.png)

- 配置

  ![image-20230126125805655](/Users/jamison/Library/Application Support/typora-user-images/image-20230126125805655.png)

  ![image-20230126125829533](/Users/jamison/Library/Application Support/typora-user-images/image-20230126125829533.png)

- 运行

  要关闭容器才能触发destory方法，否则虚拟机直接关闭来不及执行destory方法，而ApplicationContext接口没有定义close方法，所以使用ClassPathXmlApplicationContext。

  ![image-20230126130226528](/Users/jamison/Library/Application Support/typora-user-images/image-20230126130226528.png)

  ![image-20230126130021342](/Users/jamison/Library/Application Support/typora-user-images/image-20230126130021342.png)

  也可以使用registerShutdownHook方法关闭容器，比close温和，它不一定要放在最后：

  ![image-20230126130527304](/Users/jamison/Library/Application Support/typora-user-images/image-20230126130527304.png)

  还可以不自己声明init和destory方法，实现InitializingBean, DisposableBean两个接口，override afterProperties和destory方法即可，而且不用配置。

  ```java
  public class BookServiceImpl implements BookService, InitializingBean, DisposableBean {
      //5.删除业务层中使用new的方式创建的dao对象
      private BookDao bookDao;
  
      public void save() {
          System.out.println("bookService save..");
          bookDao.save();
      }
  
      //6. 提供对应的Set方法
      public void setBookDao(BookDao bookDao) {
          this.bookDao = bookDao;
      }
  
      public void destroy() throws Exception {
          System.out.println("destroy...");
      }
  
      public void afterPropertiesSet() throws Exception {
          System.out.println("init...");
      }
  }
  ```

### 3.2 小结

![image-20230126125714460](/Users/jamison/Library/Application Support/typora-user-images/image-20230126125714460.png)

# 第三章 注入

## 1. 两种依赖注入方式

![image-20230126134006789](/Users/jamison/Library/Application Support/typora-user-images/image-20230126134006789.png)

### 1.1 setter注入

- 引用类型

  ![image-20230126134116116](/Users/jamison/Library/Application Support/typora-user-images/image-20230126134116116.png)

- 简单类型

  ![image-20230126134620425](/Users/jamison/Library/Application Support/typora-user-images/image-20230126134620425.png)

### 1.2 构造器注入

- 引用类型

  ![image-20230126135703137](/Users/jamison/Library/Application Support/typora-user-images/image-20230126135703137.png)

- 简单类型

  ![image-20230126135840376](/Users/jamison/Library/Application Support/typora-user-images/image-20230126135840376.png)

  ![image-20230126135848296](/Users/jamison/Library/Application Support/typora-user-images/image-20230126135848296.png)

### 1.3 依赖注入方式选择

实际上开发过程中使用setter比较多

![image-20230126140119571](/Users/jamison/Library/Application Support/typora-user-images/image-20230126140119571.png)

小结：

![image-20230126140131648](/Users/jamison/Library/Application Support/typora-user-images/image-20230126140131648.png)

## 2. 依赖自动装配

![image-20230126155358501](/Users/jamison/Library/Application Support/typora-user-images/image-20230126155358501.png)

setter方式注入：

![image-20230126155416278](/Users/jamison/Library/Application Support/typora-user-images/image-20230126155416278.png)

- 按类型

  ![image-20230126155603914](/Users/jamison/Library/Application Support/typora-user-images/image-20230126155603914.png)

- 按名称

  ![image-20230126155629766](/Users/jamison/Library/Application Support/typora-user-images/image-20230126155629766.png)

**依赖自动装配的特征：**

![image-20230126155736705](/Users/jamison/Library/Application Support/typora-user-images/image-20230126155736705.png)

## 3. 集合注入

```java
public class BookDaoImpl implements BookDao {

    private int[] array;

    private List<String> list;

    private Set<String> set;

    private Map<String,String> map;

    private Properties properties;




    public void setArray(int[] array) {
        this.array = array;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }




    public void save() {
        System.out.println("book dao save ...");

        System.out.println("遍历数组:" + Arrays.toString(array));

        System.out.println("遍历List" + list);

        System.out.println("遍历Set" + set);

        System.out.println("遍历Map" + map);

        System.out.println("遍历Properties" + properties);
    }
}
```

```java
    <bean id="bookDao" class="com.itheima.dao.impl.BookDaoImpl">
        <!--数组注入-->
        <property name="array">
            <array>
                <value>100</value>
                <value>200</value>
                <value>300</value>
            </array>
        </property>
        <!--list集合注入-->
        <property name="list">
            <list>
                <value>itcast</value>
                <value>itheima</value>
                <value>boxuegu</value>
                <value>chuanzhihui</value>
            </list>
        </property>
        <!--set集合注入-->
        <property name="set">
            <set>
                <value>itcast</value>
                <value>itheima</value>
                <value>boxuegu</value>
                <value>boxuegu</value>
            </set>
        </property>
        <!--map集合注入-->
        <property name="map">
            <map>
                <entry key="country" value="china"/>
                <entry key="province" value="henan"/>
                <entry key="city" value="kaifeng"/>
            </map>
        </property>
        <!--Properties注入-->
        <property name="properties">
            <props>
                <prop key="country">china</prop>
                <prop key="province">henan</prop>
                <prop key="city">kaifeng</prop>
            </props>
        </property>
    </bean>
```

## 4. 加载properties文件

1. 开启context命名空间

   灰色部分是新加的

   ![image-20230126161020186](/Users/jamison/Library/Application Support/typora-user-images/image-20230126161020186.png)

2. 使用context空间加载properties文件

   其实这里的写法不规范，后面会讲。

   ![image-20230126161148037](/Users/jamison/Library/Application Support/typora-user-images/image-20230126161148037.png)

3. 使用属性占位符${}读取properties文件中的属性

   ![image-20230126161318851](/Users/jamison/Library/Application Support/typora-user-images/image-20230126161318851.png)

4. 细节和注意点

   ![image-20230126161438684](/Users/jamison/Library/Application Support/typora-user-images/image-20230126161438684.png)

   ![image-20230126161453326](/Users/jamison/Library/Application Support/typora-user-images/image-20230126161453326.png)

   不加载系统属性的原因是，某些properties文件中的属性a是系统属性a，使用${a}读取的是系统属性a，为了额避免这种情况。

# 第四章 注解开发&整合MyBatis

## 1. 容器

### 1.1 创建容器

![image-20230127225128578](/Users/jamison/Library/Application Support/typora-user-images/image-20230127225128578.png)

### 1.2 获取bean

![image-20230127225143816](/Users/jamison/Library/Application Support/typora-user-images/image-20230127225143816.png)

### 1.3 BeanFactory是顶级接口

![image-20230127225711854](/Users/jamison/Library/Application Support/typora-user-images/image-20230127225711854.png)

![image-20230127230201186](/Users/jamison/Library/Application Support/typora-user-images/image-20230127230201186.png)

BeanFactory采用延迟加载的方式加载bean，我们常用的都是即时加载：

![image-20230127230548239](/Users/jamison/Library/Application Support/typora-user-images/image-20230127230548239.png)

即时加载。

### 1.4 小结

1. 容器类层次结构图

   ![image-20230127230303841](/Users/jamison/Library/Application Support/typora-user-images/image-20230127230303841.png)

2. BeanFactory初始化

   ![image-20230127230332278](/Users/jamison/Library/Application Support/typora-user-images/image-20230127230332278.png)

3. 小结

   ![image-20230127230744537](/Users/jamison/Library/Application Support/typora-user-images/image-20230127230744537.png)

## 2. 核心容器总结（前四章）

### 2.1 容器相关

![image-20230127231055830](/Users/jamison/Library/Application Support/typora-user-images/image-20230127231055830.png)

### 2.2 bean相关

![image-20230127231119579](/Users/jamison/Library/Application Support/typora-user-images/image-20230127231119579.png)

### 2.3 依赖注入相关

![image-20230127231144166](/Users/jamison/Library/Application Support/typora-user-images/image-20230127231144166.png)

## 3. 注解开发

### 3.1 注解开发定义bean

![image-20230127235608324](/Users/jamison/Library/Application Support/typora-user-images/image-20230127235608324.png)

![image-20230127235620824](/Users/jamison/Library/Application Support/typora-user-images/image-20230127235620824.png)

### 3.2 纯注解开发

创建一个配置类，注解声明，代替xml配置文件。

![image-20230128000340899](/Users/jamison/Library/Application Support/typora-user-images/image-20230128000340899.png)

![image-20230128000540453](/Users/jamison/Library/Application Support/typora-user-images/image-20230128000540453.png)

### 3.3 小结

![image-20230128000611748](/Users/jamison/Library/Application Support/typora-user-images/image-20230128000611748.png)

## 4. bean管理

可以与之前学的进行对比，记住注解就行。

### 4.1 bean作用范围

![image-20230128000759778](/Users/jamison/Library/Application Support/typora-user-images/image-20230128000759778.png)

### 4.2 bean证明周期

![image-20230128000818315](/Users/jamison/Library/Application Support/typora-user-images/image-20230128000818315.png)

### 4.3 小结

![image-20230128000840225](/Users/jamison/Library/Application Support/typora-user-images/image-20230128000840225.png)

## 5. 依赖注入

### 5.1 自动装配

![image-20230128001026864](/Users/jamison/Library/Application Support/typora-user-images/image-20230128001026864.png)

当然了，bookDao已经是bean了，才可以autowired。

![image-20230128001257822](/Users/jamison/Library/Application Support/typora-user-images/image-20230128001257822.png)

Qualifer用于区分不了用的是哪个bean的情况

### 5.2 加载properties文件

![image-20230128001613168](/Users/jamison/Library/Application Support/typora-user-images/image-20230128001613168.png)

注意不支持通配符

### 5.3 小结

![image-20230128001700618](/Users/jamison/Library/Application Support/typora-user-images/image-20230128001700618.png)



## 6. 第三方bean管理

### 6.1 第三方bean管理

![image-20230128002248754](/Users/jamison/Library/Application Support/typora-user-images/image-20230128002248754.png)

![image-20230128002259079](/Users/jamison/Library/Application Support/typora-user-images/image-20230128002259079.png)

![image-20230128002319404](/Users/jamison/Library/Application Support/typora-user-images/image-20230128002319404.png)

### 6.2 第三方bean依赖注入

![image-20230128002921113](/Users/jamison/Library/Application Support/typora-user-images/image-20230128002921113.png)

![image-20230128002931435](/Users/jamison/Library/Application Support/typora-user-images/image-20230128002931435.png)

### 6.3 小结

![image-20230128002955300](/Users/jamison/Library/Application Support/typora-user-images/image-20230128002955300.png)

## 7. 注解开发总结

### 7.1 XML配置与注解配置比较

![image-20230128003304217](/Users/jamison/Library/Application Support/typora-user-images/image-20230128003304217.png)

## 8. Spring整合MyBatis

![image-20230128003402745](/Users/jamison/Library/Application Support/typora-user-images/image-20230128003402745.png)

![image-20230128003438112](/Users/jamison/Library/Application Support/typora-user-images/image-20230128003438112.png)

### 8.1 整合

![image-20230128004906164](/Users/jamison/Library/Application Support/typora-user-images/image-20230128004906164.png)

![image-20230128004929044](/Users/jamison/Library/Application Support/typora-user-images/image-20230128004929044.png)

### 8.2 小结

![image-20230128004946881](/Users/jamison/Library/Application Support/typora-user-images/image-20230128004946881.png)

# 第五章 Spring整合junit&AOP入门

## 1. Spring整合junit

```xml
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>4.12</version>
  <scope>test</scope>
</dependency>

<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-test</artifactId>
  <version>5.2.10.RELEASE</version>
</dependency>
```

![image-20230128144655149](/Users/jamison/Library/Application Support/typora-user-images/image-20230128144655149.png)

小结：

![image-20230128144719291](/Users/jamison/Library/Application Support/typora-user-images/image-20230128144719291.png)

## 2. AOP简介

### 2.1 AOP简介与作用

![image-20230128145002272](/Users/jamison/Library/Application Support/typora-user-images/image-20230128145002272.png)

### 2.2 AOP核心概念

![image-20230128145036566](/Users/jamison/Library/Application Support/typora-user-images/image-20230128145036566.png)

![image-20230128145212001](/Users/jamison/Library/Application Support/typora-user-images/image-20230128145212001.png)

### 2.3 小结

![image-20230128145240194](/Users/jamison/Library/Application Support/typora-user-images/image-20230128145240194.png)

## 3. AOP入门案例

### 3.1 入门案例思路分析

![image-20230128145431134](/Users/jamison/Library/Application Support/typora-user-images/image-20230128145431134.png)

### 3.2 注解版步骤

1. 导坐标

   ![image-20230128145548778](/Users/jamison/Library/Application Support/typora-user-images/image-20230128145548778.png)

2. 定义dao接口与实现类

   ![image-20230128145636626](/Users/jamison/Library/Application Support/typora-user-images/image-20230128145636626.png)

3. 定义通知类，制作通知

   ![image-20230128145705283](/Users/jamison/Library/Application Support/typora-user-images/image-20230128145705283.png)

4. 定义切入点

   ![image-20230128145735960](/Users/jamison/Library/Application Support/typora-user-images/image-20230128145735960.png)

5. 绑定切入点和通知的关系，并指定通知添加到原始连接点的具体执行位置

   ![image-20230128145852831](/Users/jamison/Library/Application Support/typora-user-images/image-20230128145852831.png)

6. 定义通知类受Spring容器管理，并定义当前类为切面类

   ![image-20230128145953569](/Users/jamison/Library/Application Support/typora-user-images/image-20230128145953569.png)

7. 开启Spring对AOP的注解驱动支持

   ![image-20230128150027325](/Users/jamison/Library/Application Support/typora-user-images/image-20230128150027325.png)

## 4. AOP工作流程

![image-20230128150410579](/Users/jamison/Library/Application Support/typora-user-images/image-20230128150410579.png)

![image-20230128150441919](/Users/jamison/Library/Application Support/typora-user-images/image-20230128150441919.png)

AOP是通过代理模式实现的，本质是代理模式。

AOP核心概念：

![image-20230128150616014](/Users/jamison/Library/Application Support/typora-user-images/image-20230128150616014.png)

小结：

![image-20230128150629705](/Users/jamison/Library/Application Support/typora-user-images/image-20230128150629705.png)

# 第六章 AOP切入点表达式&通知类型

## 1. AOP切入点表达式

![image-20230128172327412](/Users/jamison/Library/Application Support/typora-user-images/image-20230128172327412.png)

通常采用描述方法一，方法二用的是实现类耦合度高。

### 1.1 语法格式

![image-20230128172441278](/Users/jamison/Library/Application Support/typora-user-images/image-20230128172441278.png)

### 1.2 通配符

![image-20230128172523437](/Users/jamison/Library/Application Support/typora-user-images/image-20230128172523437.png)

### 1.3 书写技巧

![image-20230128172553241](/Users/jamison/Library/Application Support/typora-user-images/image-20230128172553241.png)

## 2. AOP通知类型

最重要的环绕通知

![image-20230128172721536](/Users/jamison/Library/Application Support/typora-user-images/image-20230128172721536.png)

- 前置通知

  ![image-20230128172815320](/Users/jamison/Library/Application Support/typora-user-images/image-20230128172815320.png)

- 后置通知

  ![image-20230128172841303](/Users/jamison/Library/Application Support/typora-user-images/image-20230128172841303.png)

- 环绕通知

  ![image-20230128172918604](/Users/jamison/Library/Application Support/typora-user-images/image-20230128172918604.png)

  如果有多个参数的话，ProceedingJoinPoint必须放在参数列表的第一位，还有一些环绕通知的注意事项：

  ![image-20230128173118052](/Users/jamison/Library/Application Support/typora-user-images/image-20230128173118052.png)

- 返回后通知

  ![image-20230128173204147](/Users/jamison/Library/Application Support/typora-user-images/image-20230128173204147.png)

- 抛出异常后通知

  ![image-20230128173243250](/Users/jamison/Library/Application Support/typora-user-images/image-20230128173243250.png)

## 3. 案例：测量业务层接口万次执行效率

通过需求可知使用环绕通知实现。

![image-20230128173416402](/Users/jamison/Library/Application Support/typora-user-images/image-20230128173416402.png)

核心代码优化过程：

![image-20230128173618380](/Users/jamison/Library/Application Support/typora-user-images/image-20230128173618380.png)

turn to

![image-20230128173636939](/Users/jamison/Library/Application Support/typora-user-images/image-20230128173636939.png)

## 4. AOP通知获取数据

![image-20230128173813797](/Users/jamison/Library/Application Support/typora-user-images/image-20230128173813797.png)

### 4.1 获取参数

分为around和其他;

![image-20230128173927075](/Users/jamison/Library/Application Support/typora-user-images/image-20230128173927075.png)

### 4.2 获取返回值

afterReturning和around

![image-20230128174108980](/Users/jamison/Library/Application Support/typora-user-images/image-20230128174108980.png)

### 4.3 获取异常

afterThrowing和around

![image-20230128174224320](/Users/jamison/Library/Application Support/typora-user-images/image-20230128174224320.png)

## 5. 案例：百度网盘密码数据兼容处理

![image-20230128174344339](/Users/jamison/Library/Application Support/typora-user-images/image-20230128174344339.png)

步骤：

![image-20230128174417072](/Users/jamison/Library/Application Support/typora-user-images/image-20230128174417072.png)

## 6. AOP总结

![image-20230128174455859](/Users/jamison/Library/Application Support/typora-user-images/image-20230128174455859.png)

![image-20230128174519031](/Users/jamison/Library/Application Support/typora-user-images/image-20230128174519031.png)

![image-20230128174532237](/Users/jamison/Library/Application Support/typora-user-images/image-20230128174532237.png)

![image-20230128174547721](/Users/jamison/Library/Application Support/typora-user-images/image-20230128174547721.png)

![image-20230128174558355](/Users/jamison/Library/Application Support/typora-user-images/image-20230128174558355.png)

# 第七章 Spring事务

Spring事务简介：

![image-20230128174719682](/Users/jamison/Library/Application Support/typora-user-images/image-20230128174719682.png)

## 1. 案例：银行账户转账

![image-20230128174807195](/Users/jamison/Library/Application Support/typora-user-images/image-20230128174807195.png)

步骤：

![image-20230128174839040](/Users/jamison/Library/Application Support/typora-user-images/image-20230128174839040.png)

![image-20230128174855141](/Users/jamison/Library/Application Support/typora-user-images/image-20230128174855141.png)

![image-20230128174906321](/Users/jamison/Library/Application Support/typora-user-images/image-20230128174906321.png)

## 2. Spring事务角色

**事务角色**：事务管理员和事务协调员

![image-20230128175028916](/Users/jamison/Library/Application Support/typora-user-images/image-20230128175028916.png)

![image-20230128175045613](/Users/jamison/Library/Application Support/typora-user-images/image-20230128175045613.png)

![image-20230128175101361](/Users/jamison/Library/Application Support/typora-user-images/image-20230128175101361.png)

![image-20230128175112432](/Users/jamison/Library/Application Support/typora-user-images/image-20230128175112432.png)

## 3. 事务相关配置

### 3.1 事务配置

![image-20230128175321683](/Users/jamison/Library/Application Support/typora-user-images/image-20230128175321683.png)

例子：

![image-20230128175356120](/Users/jamison/Library/Application Support/typora-user-images/image-20230128175356120.png)

### 3.2 案例：转账业务追加日志

![image-20230128175411452](/Users/jamison/Library/Application Support/typora-user-images/image-20230128175411452.png)

尝试：

![image-20230128175514470](/Users/jamison/Library/Application Support/typora-user-images/image-20230128175514470.png)

实际上在不使用事务传播行为的情况下，这是不可行的，他们三个事务还是属于一个事务管理员：

![image-20230128175637256](/Users/jamison/Library/Application Support/typora-user-images/image-20230128175637256.png)

这里需要使用事务传播行为来解决问问题，见下节

### 3.3 事务传播行为

**事务传播行为是事务协调员对事务管理员所携带事务的处理态度**

![image-20230128175738243](/Users/jamison/Library/Application Support/typora-user-images/image-20230128175738243.png)

![image-20230128175933231](/Users/jamison/Library/Application Support/typora-user-images/image-20230128175933231.png)

![image-20230128175949703](/Users/jamison/Library/Application Support/typora-user-images/image-20230128175949703.png)

