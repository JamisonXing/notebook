# 集合的使用

**主要内容**

- 集合总体结构介绍
- List实现类
- Set实现类
- Map实现类
- Iterator
- Collections

**学习目标**

![image-20230115211646248](/Users/jamison/Library/Application Support/typora-user-images/image-20230115211646248.png)

## 一、集合的介绍

### 1. 介绍

集合又称为**容器**。是Java中对数据结构（数据存储方式）的具体实现。

我们可以利用集合存放数据，也可以对集合进行新增，删除，修改，查看等操作。

集合中数据都是在**内存**中，当程序关闭或重启后集合中数据会丢失。所以集合是一种**临时**存储数据的容器。

### 2. JDK中集合结构图（常见面试题）

集合作为一种容器，可以存储多个元素，但是由于数据结构的不同，java提供了多种集合类。将集合类中共性的功能，不断向上抽取，最终形成了集合体系结构。

![image-20230116165932158](/Users/jamison/Library/Application Support/typora-user-images/image-20230116165932158.png)

![image-20230116170009024](/Users/jamison/Library/Application Support/typora-user-images/image-20230116170009024.png)

## 二、Collection接口

### 1. 介绍

List和Set接口的父接口，还有其他的实现类或子接口。

### 2. 继承关系

![image-20230116170226668](/Users/jamison/Library/Application Support/typora-user-images/image-20230116170226668.png)

## 三、List接口

### 1. 介绍

Collection接口的子接口。Collection中包含的内容List接口中可以继承。

List专门**存储有序，可重复数据**的接口。

### 2. 包含的API

![image-20230117215828086](/Users/jamison/Library/Application Support/typora-user-images/image-20230117215828086.png)

## 四、ArrayList

### 1. 介绍

实现了List接口，底层实现可变长度数组。存储有序、可重复数据，有下标。

### 2. 实例化

常用向上转型进行实例化。绝大多数集合都支持泛型，如果不写泛型默认是Object,使用集合时建议一定要指定泛型。

### 3. 内存结构图

![image-20230117223453301](/Users/jamison/Library/Application Support/typora-user-images/image-20230117223453301.png)

### 4. 举例

```java
package com.jamison.collection_test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jamison
 */
public class TestList {
    public static void main(String[] args) {
        //创建集合对象
        //ArrayList<Integer> list1 = new ArrayList<>();
        List<Integer> list = new ArrayList<>();

        //添加
        list.add(123);
        list.add(124);
        list.add(126);
        System.out.println(list);

        List<Integer> list1 = new ArrayList<>();
        list1.add(123);
        list1.add(124);
        list1.add(126);
        //将其他集合整体添加到指定集合中
        list.addAll(list1);
        System.out.println(list1);

        //修改
        list.set(0, 234);

        //获得元素
        Integer num = list.get(1);
        System.out.println(num);

        //删除
        //根据下标进行移除
        list.remove(2);
        //根据内容删除，只有Integer数据类型比较尴尬，不能直接写数字，分不清到底是index还是value，要new出来
        list.remove(new Integer(234));
        System.out.println(list);

        //清空集合内容
        //list.removeAll(list);
        list.clear();

        boolean empty = list.isEmpty();
        System.out.println(empty);

        //集合长度
        int size = list.size();
        System.out.println(size);

        //判断
        //根据内容返回数组下标，如果元素内容不存在返回-1
        int i = list.indexOf(126);
        System.out.println(i);
        //判断集合是否包含指定元素，返回true or false
        boolean IsContains = list.contains(126);
        System.out.println(IsContains);
    }
}
```

![image-20230117223528280](/Users/jamison/Library/Application Support/typora-user-images/image-20230117223528280.png)

```java
package com.jamison.collection_test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author jamison
 */
public class TestList2 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        System.out.println("----遍历方法1----");
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        System.out.println("----遍历方法2----");
        for(Integer num : list) {
            System.out.println(num);
        }

        System.out.println("----遍历方法3----");
        Iterator<Integer> it = list.iterator();
        while(it.hasNext()) {
            System.out.println(it.next() + " ");
        }
    }
}
```

```java
package com.jamison.collection_test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jamison
 */
public class TestList3 {
    public static void main(String[] args) {
        //集合中也可以保存集合
        List<List<Integer>> list = new ArrayList<>();

        List<Integer> subList01 = new ArrayList<>();
        subList01.add(12);
        subList01.add(13);
        subList01.add(14);
        List<Integer> subList02 = new ArrayList<>();
        subList02.add(22);
        subList02.add(23);
        subList02.add(24);

        list.add(subList01);
        list.add(subList02);
        System.out.println(list);

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                System.out.println(list.get(i).get(j));
            }
        }
    }
}
```

