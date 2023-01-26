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