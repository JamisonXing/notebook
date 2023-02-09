# Java基础面试题

1. java是什么类型的语言

   

3: Java是什么类型的语言?<font color='yellow'>很多人都答错了<font>

视频资料笔记：

![image-20230208085203307](/Users/jamison/Library/Application Support/typora-user-images/image-20230208085203307.png)

jit:

![image-20230208085220545](/Users/jamison/Library/Application Support/typora-user-images/image-20230208085220545.png)

GraalVM:

相当于JIT plus

![image-20230208085237131](/Users/jamison/Library/Application Support/typora-user-images/image-20230208085237131.png)

```
一、你可以说它是编译型的：因为所有的Java代码都是要编译的，.java不经过编译就什么用都没有。 
二、你可以说它是解释型的：因为java代码编译后不能直接运行，它是解释运行在JVM上的，所以它是解释运行的，那也就算是解释的了。 
三、但是，现在的JVM为了效率，都有一些JIT优化。它又会把.class的二进制代码编译为本地的代码直接运行，所以，又是编译的。

定义： 
（1）编译型语言：把做好的源程序全部编译成二进制代码的可运行程序。然后，可直接运行这个程序。 
（2）解释型语言：把做好的源程序翻译一句，然后执行一句，直至结束！ 
区别： 
（1）编译型语言，执行速度快、效率高；依靠编译器、跨平台性差些。 
（2）解释型语言，执行速度慢、效率低；依靠解释器、跨平台性好。

java是解释型的语言，因为虽然java也需要编译，编译成.class文件，但是并不是机器可以识别的语言，而是字节码，最终还是需要 jvm的解释，才能在各个平台执行，这同时也是java跨平台的原因。所以可是说java即是编译型的，也是解释型，但是假如非要归类的话，从概念上的定义，恐怕java应该归到解释型的语言中。 
附： 
编译型的语言包括：C、C++、Delphi、Pascal、Fortran 
解释型的语言包括：Java、Basic、javascript、python

```

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/752/1645524673000/fe3475290e8c4ab08fd74ebe837d272a.png)

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/752/1645524673000/fe3475290e8c4ab08fd74ebe837d272a.png)

GraalVM 即时编译   graal  aot  jit c1 c2

2. 简单说说java对象如何拷贝

   http://t.csdn.cn/UXjOb

   ```
   一、浅拷贝clone（）
   
   如果对象中的所有数据域都是数值或者基本类型，使用clone（）即可满足需求，如：
   
   Person p = new Person();
   
   Person p1 = p.clone();
   
   这样p和p1分别指向不同的对象。
   
   二、深度拷贝
   
   如果在对象中包含子对象的引用，拷贝的结果是使得两个域引用同一个对象，默认的拷贝是浅拷贝，没有拷贝包含在对象中的内部对象。
   
   如果子对象是不可变的，如String，这没有什么问题；如果对象是可变的，必须重新定义clone方法；
   
   三、序列化可克隆（深拷贝）
   
   四、BeanUtils.copyProperties()
   ```

3. 伪代码实现下深拷贝

4. 什么是Object，有哪些常见的方法，怎么创建对象？

   - 顶级父类

   - 常见方法

     ![image-20230209114040705](/Users/jamison/Library/Application Support/typora-user-images/image-20230209114040705.png)

   - 怎样创建对象

        1、使用new关键字；
        2、通过反射创建对象
        		使用Class类的newInstance方法，可调用无参的构造函数创建对象；http://t.csdn.cn/AREKU
        		使用Constructor类的newInstance方法；

     ![image-20230209115444811](/Users/jamison/Library/Application Support/typora-user-images/image-20230209115444811.png)

        3、使用clone方法；http://t.csdn.cn/6qYlr
        4、使用反序列化。http://t.csdn.cn/CCqhO

5. 多态，面向接口编程？聊聊你的认知

   ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/752/1645524673000/e443a4ecd0cf4abe9a71b0cb3e535ab8.png)

   - 制定标准

   - 提高扩展性

   - 让你设计一个接口，你会考虑到哪些点？（了解）http://t.csdn.cn/dE86H
   
   - 接口和抽象类的区别？
   
     简单来说**接口用于抽象事物的特性，抽象类用于代码复用http://t.csdn.cn/jQDHN**，或者**接口是对动作的抽象，抽象类是对根源的抽象。http://t.csdn.cn/xHXb8**
   
     

