# MyBatis

## 一、MyBatis简介

### 1. 什么是MyBatis

![image-20230119123637293](/Users/jamison/Library/Application Support/typora-user-images/image-20230119123637293.png)

### 2. 持久层

- 负责讲数据到保存到数据库的那一层代码
- JavaEE三层架构：表现层、业务层、持久层

### 3. 框架

![image-20230119123800251](/Users/jamison/Library/Application Support/typora-user-images/image-20230119123800251.png)

### 4. MaBatis简化了JDBC

![image-20230119123927680](/Users/jamison/Library/Application Support/typora-user-images/image-20230119123927680.png)

## 二、MyBatis快速入门

### 1. 查询user表中的所有数据

https://mybatis.org/mybatis-3/zh/getting-started.html

![image-20230119124403464](/Users/jamison/Library/Application Support/typora-user-images/image-20230119124403464.png)

![image-20230119140646557](/Users/jamison/Library/Application Support/typora-user-images/image-20230119140646557.png)

Pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.jamison</groupId>
    <artifactId>mybatis-demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.5</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.30</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13</version>
            <scope>test</scope>
        </dependency>

        <!-- 添加slf4j日志api -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.20</version>
        </dependency>
        <!-- 添加logback-classic依赖 -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
        </dependency>
        <!-- 添加logback-core依赖 -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
            <version>1.2.3</version>
        </dependency>

    </dependencies>

</project>
```

Mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql:///mybatis?userSSL=false"/>
                <property name="username" value="root"/>
                <property name="password" value="xxj753896"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <!--加载sql映射文件-->
        <mapper resource="UserMapper.xml"/>
    </mappers>
</configuration>
```

UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--
    namespace:命名空间，多个Mapper文件的时候便于区分
-->
<mapper namespace="test">
    <select id="selectAll" resultType="com.jamison.pojo.User">
        select * from tb_user;
    </select>
</mapper>
```

User.java

```java
package com.jamison.pojo;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String gender;
    private String addr;

    @Override
    public String toString() {
        return "com.jamison.pojo.User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}

```

MybatisDemo.java

```java
package com.jamison;

import com.jamison.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author jamison
 */
public class MybatisDemo {
    public static void main(String[] args) throws IOException {
        //1. 加载mybatis核心配置文件，获取SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2. 获取获取SqlSession对象，用它来执行sql
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //3. 执行sql
        List<User> users = sqlSession.selectList("test.selectAll");

        System.out.println(users);

        //4. 释放资源
        sqlSession.close();
    }
}
```

### 2. 解决SQL映射文件的警告提示

不过我做的时候没出现这个提示。

![image-20230119141358088](/Users/jamison/Library/Application Support/typora-user-images/image-20230119141358088.png)

### 3. Mapper代理开发

![image-20230119161847370](/Users/jamison/Library/Application Support/typora-user-images/image-20230119161847370.png)

**注意：**

1. 为了满足Mapper接口和SQL映射文件放置在同一目录下，在resource下定义包时，要用 / 隔开不用' . ',但是ide还是会显示'.'，还要注意mybatis-config.xml的mapper也要改一下。

   ![image-20230119162931747](/Users/jamison/Library/Application Support/typora-user-images/image-20230119162931747.png)

2. 全限定名

   ![image-20230119163329633](/Users/jamison/Library/Application Support/typora-user-images/image-20230119163329633.png)

3. 定义方法

   ![image-20230119163405887](/Users/jamison/Library/Application Support/typora-user-images/image-20230119163405887.png)

4. 编码

   ```java
   package com.jamison;
   
   import com.jamison.mapper.UserMapper;
   import com.jamison.pojo.User;
   import org.apache.ibatis.io.Resources;
   import org.apache.ibatis.session.SqlSession;
   import org.apache.ibatis.session.SqlSessionFactory;
   import org.apache.ibatis.session.SqlSessionFactoryBuilder;
   
   import java.io.IOException;
   import java.io.InputStream;
   import java.util.List;
   
   /**
    * @author jamison
    */
   public class MybatisDemo2 {
       public static void main(String[] args) throws IOException {
           //1. 加载mybatis核心配置文件，获取SqlSessionFactory
           String resource = "mybatis-config.xml";
           InputStream inputStream = Resources.getResourceAsStream(resource);
           SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
   
           //2. 获取获取SqlSession对象，用它来执行sql
           SqlSession sqlSession = sqlSessionFactory.openSession();
   
           //3. 执行sql
           //List<User> users = sqlSession.selectList("test.selectAll");
           //3.1 mapper代理方式执行
           UserMapper mapper = sqlSession.getMapper(UserMapper.class);
           List<User> users = mapper.selectAll();
           System.out.println(users);
   
           //4. 释放资源
           sqlSession.close();
       }
   }
   ```

5. 包扫描方式加载映射文件

   ![image-20230119164532273](/Users/jamison/Library/Application Support/typora-user-images/image-20230119164532273.png)

### 4. Mybatis核心配置文件

https://mybatis.org/mybatis-3/zh/configuration.html

![image-20230119175114776](/Users/jamison/Library/Application Support/typora-user-images/image-20230119175114776.png)

上面这个不仅是目录也是顺序，配置各个标签的时候要遵守前后顺序。

## 三、Mybatis查询

### 1. 查询-查询所有

![image-20230119180942884](/Users/jamison/Library/Application Support/typora-user-images/image-20230119180942884.png)

![image-20230119181023482](/Users/jamison/Library/Application Support/typora-user-images/image-20230119181023482.png)

1. 编写实体类和接口

   ![image-20230119181133409](/Users/jamison/Library/Application Support/typora-user-images/image-20230119181133409.png)

   ![image-20230119181057707](/Users/jamison/Library/Application Support/typora-user-images/image-20230119181057707.png)

2. SQL映射文件

   ![image-20230119181217719](/Users/jamison/Library/Application Support/typora-user-images/image-20230119181217719.png)

3. 执行测试方法

   ```java
   public class MybatisTest {
       public static void main(String[] args) throws IOException {
           //1. 加载mybatis核心配置文件，获取SqlSessionFactory
           String resource = "mybatis-config.xml";
           InputStream inputStream = Resources.getResourceAsStream(resource);
           SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
   
           //2. 获取获取SqlSession对象，用它来执行sql
           SqlSession sqlSession = sqlSessionFactory.openSession();
   
           //3 mapper代理方式执行
           BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
           List<Brand> brands = mapper.selectAll();
           System.out.println(brands);
   
           //4. 释放资源
           sqlSession.close();
       }
   }
   ```

![image-20230119181436095](/Users/jamison/Library/Application Support/typora-user-images/image-20230119181436095.png)

**这里出现了点小问题，brandName和companyName没查出来，后面会讲。**

### 2. 结果映射

出现以上问题的原因是数据库表的字段名称和实体类的属性不一样，不能自动封装数据

两种解决方案：

1. 起别名

   ```xml
   <mapper namespace="com.jamison.mapper.BrandMapper">
       <!--
       数据库表的字段名称和实体类的属性不一样，不能自动封装数据
           *起别名：对不一样的列名起别名，让别名和实体类属性名相同
               *缺点：每次查询都要定义一次别名
       -->
   
       <!--1. 起别名-->
       <select id="selectAll" resultType="brand">
           select id, brand_name as brandName, company_name as companyName, ordered, description, status
           from tb_brand;
       </select>
   </mapper>
   ```

可以用sql片段优化一下：

```xml
    数据库表的字段名称和实体类的属性不一样，不能自动封装数据
        *起别名：对不一样的列名起别名，让别名和实体类属性名相同
            *缺点：每次查询都要定义一次别名
                *sql片段
    <sql id="brand_column">
        id, brand_name as brandName, company_name as companyName, ordered, description, status
    </sql>
    <!--1. 起别名-->
    <select id="selectAll" resultType="brand">
        select
            <include refid="brand_column"/>
        from tb_brand;
    </select>
</mapper>
```

2. resultMap

   ```xml
   <!--        
   *resultMap:
               1. 定义<resultMap>标签
               2. 在<select>使用resultMap属性替换resultType属性
   -->
       <resultMap id="brandResultMap" type="brand">
           <!--
               id: 完成主键字段的映射
                   column:表的列名
                   property:实体类的属性名
               result: 完成一般字段的映射
                   column:表的列名
                   property:实体类的属性名
           -->
           <result column="brand_name" property="brandName"/>
           <result column="company_name" property="companyName"/>
       </resultMap>
       <select id="selectAll" resultMap="brandResultMap">
           select *
           from tb_brand;
       </select>
   ```