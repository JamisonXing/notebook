# 1. Static{}静态代码块的使用

static{}静态代码块在类的加载时执行，并且只执行一次，且在main方法之前执行。一个类中可以有多个静态代码块，执行顺序自上而下。一个静态代码块只能访问该静态代码块之前的静态变量。

static{}静态代码块在类初次被加载的时候被执行**且只会被执行一次**，**所以可以起到优化程序性能的作用**。

静态代码块的语法：

```java
//静态代码块
static
{
    //Java程序
}
```



# 2. 实例语句块的使用

除了static{}静态代码之外，还有一种语句块叫做**实例语句块**。

顾名思义，就是在实例化对象时执行，并不是在类加载时执行。

实例语句块的具体执行时间是在实例化对象时的**构造函数之前**执行的。

实例语句块的语法：

```java
//实例语句块
{
    //Java程序
}
```




# 3. java程序初始化顺序

在java语言中，当时实例化对象时，对象所在类的所有成员首先要进行初始化，只有当所有类成员完成初始化后，才会调用对象所在的构造函数创建对象。

**Java程序的初始化一般遵循一下三个原则：**

1. 静态对象(变量)、非静态对象(变量)
2. 父类优于子类进行初始化。
3. 按照成员变量定义的顺序进行初始化。

java程序的初始化工作可以在许多不同的代码块中完成，例如：静态代码块、实例语句块、构造函数等。

## 普通类的执行顺序

普通类的执行顺序为：静态代码块->实例语句块->构造函数。

【**示例**】普通类的执行顺序

(1)创建普通类

```java
package com.jamison.initalizationOrder;

/**
 * 普通类的执行顺序为：静态代码块 → 实例语句块 → 构造函数。
 * @author jamison
 */
public class MyClass {
    //静态代码块
    static {
        System.out.println("静态代码块");
    }

    //实例语句块
    {
        System.out.println("实例语句块");
    }

    /**
     * 构造函数
     */
    public MyClass() {
        System.out.println("构造函数");
    }
}
```



(2)执行方法

```java
package com.jamison.initalizationOrder;

/**
 * @author jamison
 */
public class TestMyClass {
    public static void main(String[] args) {
        MyClass myClass = new MyClass();
    }
}
/**
 * result:
 * 静态代码块
 * 实例语句块
 * 构造函数
 */
```





## 继承类的执行顺序

继承类的执行顺序为：父类静态代码块->子类静态代码块->父类实例语句块->父类构造函数->子类实例语句块->子类构造函数。

【实例】继承类的执行顺序

(1)创建父类

```java
package com.jamison.initalizationOrder;

/**
 * @author jamison
 */
public class Parent {
    //静态代码块
    static {
        System.out.println("父类静态代码块");
    }

    //实例语句块
    {
        System.out.println("父类实例语句块");
    }

    /**
     * 构造函数
     */
    public Parent() {
        System.out.println("父类构造函数");
    }
}
```



(2)创建子类

```java
package com.jamison.initalizationOrder;

/**
 * @author jamison
 */
public class Child extends Parent{
    //静态代码块
    static {
        System.out.println("子类静态代码块");
    }

    //实例语句块
    {
        System.out.println("子类实例语句块");
    }

    /**
     * 构造函数
     */
    public Child() {
        System.out.println("子类构造函数");
    }
}
```



(3)执行方法

```java
package com.jamison.initalizationOrder;

/**
 * 继承类的执行顺序为：父类静态代码块 → 子类静态代码块 → 父类实例语句块 → 父类构造函数 → 子类实例语句块 → 子类构造函数。
 * @author jamison
 */
public class TestParent {
    public static void main(String[] args) {
        Child child = new Child();
    }
}

/**
 * result:
 * 父类静态代码块
 * 子类静态代码块
 * 父类实例语句块
 * 父类构造函数
 * 子类实例语句块
 * 子类构造函数
 */
```



## 成员变量初始化的优先级

这里需要注意的是，（静态）非静态成员域在定义时初始化和（静态）非静态块中初始化的优先级是平级的，也就是说按照从上到下初始化，最后一次初始化为最终值（不包括非静态的成员域在构造器中初始化）。所以在（静态）非静态代码块中初始化的域甚至能在该域声明的上方，**因为分配存储空间在初始化之前就完成了**。

【示例】（静态）非静态中成员变量初始化的优先级。

```java
package com.jamison.initalizationOrder;

/**
 *这里需要注意的是，（静态）非静态成员域在定义时初始化和（静态）非静态块中初始化的优先级是平级的，也就是说按照从上到下初始化，最后一次初始化为最终的值（不包括非静态的成员域在构造器中初始化）。所以在（静态）非静态块中初始化的域甚至能在该域声明的上方，因为分配存储空间在初始化之前就完成了。
 * @author jamison
 */
public class TestStatic {
    static {
        a = 1;
    }
    static  int a = 3;
    static {
        b = 5;
    }
    static  int b = 7;

    public static void main(String[] args) {
        System.out.println("初始化变量后的a,b值");
        System.out.println(a);
        System.out.println(b);
    }
    /**
     * 初始化变量后的a,b值
     * 3
     * 7
     */
}
```





---
title:  Java 代理模式详解
category: Java
tag:
  - Java基础
---

# 4. 代理模式

代理模式是一种比较好理解的设计模式。简单来说就是 **我们使用代理对象来代替对真实对象(real object)的访问，这样就可以在不修改原目标对象的前提下，提供额外的功能操作，扩展目标对象的功能。**

**代理模式的主要作用是扩展目标对象的功能，比如说在目标对象的某个方法执行前后你可以增加一些自定义的操作。**

举个例子：新娘找来了自己的姨妈来代替自己处理新郎的提问，新娘收到的提问都是经过姨妈处理过滤之后的。姨妈在这里就可以看作是代理你的代理对象，代理的行为（方法）是接收和回复新郎的提问。

![Understanding the Proxy Design Pattern | by Mithun Sasidharan | Medium](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/2020-8/1*DjWCgTFm-xqbhbNQVsaWQw.png)

<p style="text-align:right;font-size:13px;color:gray">https://medium.com/@mithunsasidharan/understanding-the-proxy-design-pattern-5e63fe38052a</p>

代理模式有静态代理和动态代理两种实现方式，我们 先来看一下静态代理模式的实现。

## 静态代理

**静态代理中，我们对目标对象的每个方法的增强都是手动完成的（_后面会具体演示代码_），非常不灵活（_比如接口一旦新增加方法，目标对象和代理对象都要进行修改_）且麻烦(_需要对每个目标类都单独写一个代理类_）。** 实际应用场景非常非常少，日常开发几乎看不到使用静态代理的场景。

上面我们是从实现和应用角度来说的静态代理，从 JVM 层面来说， **静态代理在编译时就将接口、实现类、代理类这些都变成了一个个实际的 class 文件。**

静态代理实现步骤:

1. 定义一个接口及其实现类；
2. 创建一个代理类同样实现这个接口
3. 将目标对象注入进代理类，然后在代理类的对应方法调用目标类中的对应方法。这样的话，我们就可以通过代理类屏蔽对目标对象的访问，并且可以在目标方法执行前后做一些自己想做的事情。

下面通过代码展示！

**1.定义发送短信的接口**

```java
public interface SmsService {
    String send(String message);
}
```

**2.实现发送短信的接口**

```java
public class SmsServiceImpl implements SmsService {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
```

**3.创建代理类并同样实现发送短信的接口**

```java
public class SmsProxy implements SmsService {

    private final SmsService smsService;

    public SmsProxy(SmsService smsService) {
        this.smsService = smsService;
    }

    @Override
    public String send(String message) {
        //调用方法之前，我们可以添加自己的操作
        System.out.println("before method send()");
        smsService.send(message);
        //调用方法之后，我们同样可以添加自己的操作
        System.out.println("after method send()");
        return null;
    }
}
```

**4.实际使用**

```java
public class Main {
    public static void main(String[] args) {
        SmsService smsService = new SmsServiceImpl();
        SmsProxy smsProxy = new SmsProxy(smsService);
        smsProxy.send("java");
    }
}
```

运行上述代码之后，控制台打印出：

```bash
before method send()
send message:java
after method send()
```

可以输出结果看出，我们已经增加了 `SmsServiceImpl` 的`send()`方法。

## 动态代理

相比于静态代理来说，动态代理更加灵活。我们不需要针对每个目标类都单独创建一个代理类，并且也不需要我们必须实现接口，我们可以直接代理实现类( _CGLIB 动态代理机制_)。

**从 JVM 角度来说，动态代理是在运行时动态生成类字节码，并加载到 JVM 中的。**

说到动态代理，Spring AOP、RPC 框架应该是两个不得不提的，它们的实现都依赖了动态代理。

**动态代理在我们日常开发中使用的相对较少，但是在框架中的几乎是必用的一门技术。学会了动态代理之后，对于我们理解和学习各种框架的原理也非常有帮助。**

就 Java 来说，动态代理的实现方式有很多种，比如 **JDK 动态代理**、**CGLIB 动态代理**等等。

[guide-rpc-framework](https://github.com/Snailclimb/guide-rpc-framework) 使用的是 JDK 动态代理，我们先来看看 JDK 动态代理的使用。

另外，虽然 [guide-rpc-framework](https://github.com/Snailclimb/guide-rpc-framework) 没有用到 **CGLIB 动态代理** ，我们这里还是简单介绍一下其使用以及和**JDK 动态代理**的对比。

### JDK 动态代理机制

#### 1. 介绍

**在 Java 动态代理机制中 `InvocationHandler` 接口和 `Proxy` 类是核心。**

`Proxy` 类中使用频率最高的方法是：`newProxyInstance()` ，这个方法主要用来生成一个代理对象。

```java
    public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h)
        throws IllegalArgumentException
    {
        ......
    }
```

这个方法一共有 3 个参数：

1. **loader** :类加载器，用于加载代理对象。
2. **interfaces** : 被代理类实现的一些接口；
3. **h** : 实现了 `InvocationHandler` 接口的对象；

要实现动态代理的话，还必须需要实现`InvocationHandler` 来自定义处理逻辑。 当我们的动态代理对象调用一个方法时，这个方法的调用就会被转发到实现`InvocationHandler` 接口类的 `invoke` 方法来调用。

```java
public interface InvocationHandler {

    /**
     * 当你使用代理对象调用方法的时候实际会调用到这个方法
     */
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable;
}
```

`invoke()` 方法有下面三个参数：

1. **proxy** :动态生成的代理类
2. **method** : 与代理类对象调用的方法相对应
3. **args** : 当前 method 方法的参数

也就是说：**你通过`Proxy` 类的 `newProxyInstance()` 创建的代理对象在调用方法的时候，实际会调用到实现`InvocationHandler` 接口的类的 `invoke()`方法。** 你可以在 `invoke()` 方法中自定义处理逻辑，比如在方法执行前后做什么事情。

#### 2. JDK 动态代理类使用步骤

1. 定义一个接口及其实现类；
2. 自定义 `InvocationHandler` 并重写`invoke`方法，在 `invoke` 方法中我们会调用原生方法（被代理类的方法）并自定义一些处理逻辑；
3. 通过 `Proxy.newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)` 方法创建代理对象；

#### 3. 代码示例

这样说可能会有点空洞和难以理解，我上个例子，大家感受一下吧！

**1.定义发送短信的接口**

```java
public interface SmsService {
    String send(String message);
}
```

**2.实现发送短信的接口**

```java
public class SmsServiceImpl implements SmsService {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
```

**3.定义一个 JDK 动态代理类**

```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author shuang.kou
 * @createTime 2020年05月11日 11:23:00
 */
public class DebugInvocationHandler implements InvocationHandler {
    /**
     * 代理类中的真实对象
     */
    private final Object target;

    public DebugInvocationHandler(Object target) {
        this.target = target;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        //调用方法之前，我们可以添加自己的操作
        System.out.println("before method " + method.getName());
        Object result = method.invoke(target, args);
        //调用方法之后，我们同样可以添加自己的操作
        System.out.println("after method " + method.getName());
        return result;
    }
}

```

`invoke()` 方法: 当我们的动态代理对象调用原生方法的时候，最终实际上调用到的是 `invoke()` 方法，然后 `invoke()` 方法代替我们去调用了被代理对象的原生方法。

**4.获取代理对象的工厂类**

```java
public class JdkProxyFactory {
    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(), // 目标类的类加载
                target.getClass().getInterfaces(),  // 代理需要实现的接口，可指定多个
                new DebugInvocationHandler(target)   // 代理对象对应的自定义 InvocationHandler
        );
    }
}
```

`getProxy()` ：主要通过`Proxy.newProxyInstance（）`方法获取某个类的代理对象

**5.实际使用**

```java
SmsService smsService = (SmsService) JdkProxyFactory.getProxy(new SmsServiceImpl());
smsService.send("java");
```

运行上述代码之后，控制台打印出：

```
before method send
send message:java
after method send
```

### CGLIB 动态代理机制

#### 1. 介绍

**JDK 动态代理有一个最致命的问题是其只能代理实现了接口的类。**

**为了解决这个问题，我们可以用 CGLIB 动态代理机制来避免。**

[CGLIB](https://github.com/cglib/cglib)(_Code Generation Library_)是一个基于[ASM](http://www.baeldung.com/java-asm)的字节码生成库，它允许我们在运行时对字节码进行修改和动态生成。CGLIB 通过继承方式实现代理。很多知名的开源框架都使用到了[CGLIB](https://github.com/cglib/cglib)， 例如 Spring 中的 AOP 模块中：如果目标对象实现了接口，则默认采用 JDK 动态代理，否则采用 CGLIB 动态代理。

**在 CGLIB 动态代理机制中 `MethodInterceptor` 接口和 `Enhancer` 类是核心。**

你需要自定义 `MethodInterceptor` 并重写 `intercept` 方法，`intercept` 用于拦截增强被代理类的方法。

```java
public interface MethodInterceptor
extends Callback{
    // 拦截被代理类中的方法
    public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args,MethodProxy proxy) throws Throwable;
}

```

1. **obj** : 被代理的对象（需要增强的对象）
2. **method** : 被拦截的方法（需要增强的方法）
3. **args** : 方法入参
4. **proxy** : 用于调用原始方法

你可以通过 `Enhancer`类来动态获取被代理类，当代理类调用方法的时候，实际调用的是 `MethodInterceptor` 中的 `intercept` 方法。

#### 2. CGLIB 动态代理类使用步骤

1. 定义一个类；
2. 自定义 `MethodInterceptor` 并重写 `intercept` 方法，`intercept` 用于拦截增强被代理类的方法，和 JDK 动态代理中的 `invoke` 方法类似；
3. 通过 `Enhancer` 类的 `create()`创建代理类；

#### 3. 代码示例

不同于 JDK 动态代理不需要额外的依赖。[CGLIB](https://github.com/cglib/cglib)(_Code Generation Library_) 实际是属于一个开源项目，如果你要使用它的话，需要手动添加相关依赖。

```xml
<dependency>
  <groupId>cglib</groupId>
  <artifactId>cglib</artifactId>
  <version>3.3.0</version>
</dependency>
```

**1.实现一个使用阿里云发送短信的类**

```java
package github.javaguide.dynamicProxy.cglibDynamicProxy;

public class AliSmsService {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
```

**2.自定义 `MethodInterceptor`（方法拦截器）**

```java
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 自定义MethodInterceptor
 */
public class DebugMethodInterceptor implements MethodInterceptor {


    /**
     * @param o           被代理的对象（需要增强的对象）
     * @param method      被拦截的方法（需要增强的方法）
     * @param args        方法入参
     * @param methodProxy 用于调用原始方法
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        //调用方法之前，我们可以添加自己的操作
        System.out.println("before method " + method.getName());
        Object object = methodProxy.invokeSuper(o, args);
        //调用方法之后，我们同样可以添加自己的操作
        System.out.println("after method " + method.getName());
        return object;
    }

}
```

**3.获取代理类**

```java
import net.sf.cglib.proxy.Enhancer;

public class CglibProxyFactory {

    public static Object getProxy(Class<?> clazz) {
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(clazz);
        // 设置方法拦截器
        enhancer.setCallback(new DebugMethodInterceptor());
        // 创建代理类
        return enhancer.create();
    }
}
```

**4.实际使用**

```java
AliSmsService aliSmsService = (AliSmsService) CglibProxyFactory.getProxy(AliSmsService.class);
aliSmsService.send("java");
```

运行上述代码之后，控制台打印出：

```bash
before method send
send message:java
after method send
```

### JDK 动态代理和 CGLIB 动态代理对比

1. **JDK 动态代理只能代理实现了接口的类或者直接代理接口，而 CGLIB 可以代理未实现任何接口的类。** 另外， CGLIB 动态代理是通过生成一个被代理类的子类来拦截被代理类的方法调用，因此不能代理声明为 final 类型的类和方法。
2. 就二者的效率来说，大部分情况都是 JDK 动态代理更优秀，随着 JDK 版本的升级，这个优势更加明显。

## 静态代理和动态代理的对比

1. **灵活性** ：动态代理更加灵活，不需要必须实现接口，可以直接代理实现类，并且可以不需要针对每个目标类都创建一个代理类。另外，静态代理中，接口一旦新增加方法，目标对象和代理对象都要进行修改，这是非常麻烦的！
2. **JVM 层面** ：静态代理在编译时就将接口、实现类、代理类这些都变成了一个个实际的 class 文件。而动态代理是在运行时动态生成类字节码，并加载到 JVM 中的。

## 总结

这篇文章中主要介绍了代理模式的两种实现：静态代理以及动态代理。涵盖了静态代理和动态代理实战、静态代理和动态代理的区别、JDK 动态代理和 Cglib 动态代理区别等内容。

文中涉及到的所有源码，你可以在这里找到：[https://github.com/Snailclimb/guide-rpc-framework-learning/tree/master/src/main/java/github/javaguide/proxy](https://github.com/Snailclimb/guide-rpc-framework-learning/tree/master/src/main/java/github/javaguide/proxy) 。



---
title: BigDecimal 详解
category: Java
tag:
  - Java基础
---

《阿里巴巴 Java 开发手册》中提到：“为了避免精度丢失，可以使用 `BigDecimal` 来进行浮点数的运算”。

浮点数的运算竟然还会有精度丢失的风险吗？确实会！

示例代码：

```java
float a = 2.0f - 1.9f;
float b = 1.8f - 1.7f;
System.out.println(a);// 0.100000024
System.out.println(b);// 0.099999905
System.out.println(a == b);// false
```

**为什么浮点数 `float` 或 `double` 运算的时候会有精度丢失的风险呢？**

这个和计算机保存浮点数的机制有很大关系。我们知道计算机是二进制的，而且计算机在表示一个数字时，宽度是有限的，无限循环的小数存储在计算机时，只能被截断，所以就会导致小数精度发生损失的情况。这也就是解释了为什么浮点数没有办法用二进制精确表示。

就比如说十进制下的 0.2 就没办法精确转换成二进制小数：

```java
// 0.2 转换为二进制数的过程为，不断乘以 2，直到不存在小数为止，
// 在这个计算过程中，得到的整数部分从上到下排列就是二进制的结果。
0.2 * 2 = 0.4 -> 0
0.4 * 2 = 0.8 -> 0
0.8 * 2 = 1.6 -> 1
0.6 * 2 = 1.2 -> 1
0.2 * 2 = 0.4 -> 0（发生循环）
...
```

关于浮点数的更多内容，建议看一下[计算机系统基础（四）浮点数](http://kaito-kidd.com/2018/08/08/computer-system-float-point/)这篇文章。

# 5. BigDecimal

`BigDecimal` 可以实现对浮点数的运算，不会造成精度丢失。

通常情况下，大部分需要浮点数精确运算结果的业务场景（比如涉及到钱的场景）都是通过 `BigDecimal` 来做的。

《阿里巴巴 Java 开发手册》中提到：**浮点数之间的等值判断，基本数据类型不能用 == 来比较，包装数据类型不能用 equals 来判断。**

![](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/javaguide/image-20211213101646884.png)

具体原因我们在上面已经详细介绍了，这里就不多提了。

想要解决浮点数运算精度丢失这个问题，可以直接使用 `BigDecimal` 来定义浮点数的值，然后再进行浮点数的运算操作即可。

```java
BigDecimal a = new BigDecimal("1.0");
BigDecimal b = new BigDecimal("0.9");
BigDecimal c = new BigDecimal("0.8");

BigDecimal x = a.subtract(b);
BigDecimal y = b.subtract(c);

System.out.println(x.compareTo(y));// 0
```

## BigDecimal 常见方法

### 创建

我们在使用 `BigDecimal` 时，为了防止精度丢失，推荐使用它的`BigDecimal(String val)`构造方法或者 `BigDecimal.valueOf(double val)` 静态方法来创建对象。

《阿里巴巴 Java 开发手册》对这部分内容也有提到，如下图所示。

![](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/javaguide/image-20211213102222601.png)

### 加减乘除

`add` 方法用于将两个 `BigDecimal` 对象相加，`subtract` 方法用于将两个 `BigDecimal` 对象相减。`multiply` 方法用于将两个 `BigDecimal` 对象相乘，`divide` 方法用于将两个 `BigDecimal` 对象相除。

```java
BigDecimal a = new BigDecimal("1.0");
BigDecimal b = new BigDecimal("0.9");
System.out.println(a.add(b));// 1.9
System.out.println(a.subtract(b));// 0.1
System.out.println(a.multiply(b));// 0.90
System.out.println(a.divide(b));// 无法除尽，抛出 ArithmeticException 异常
System.out.println(a.divide(b, 2, RoundingMode.HALF_UP));// 1.11
```

这里需要注意的是，在我们使用 `divide` 方法的时候尽量使用 3 个参数版本，并且`RoundingMode` 不要选择 `UNNECESSARY`，否则很可能会遇到 `ArithmeticException`（无法除尽出现无限循环小数的时候），其中 `scale` 表示要保留几位小数，`roundingMode` 代表保留规则。

```java
public BigDecimal divide(BigDecimal divisor, int scale, RoundingMode roundingMode) {
    return divide(divisor, scale, roundingMode.oldMode);
}
```

保留规则非常多，这里列举几种:

```java
public enum RoundingMode {
   // 2.5 -> 3 , 1.6 -> 2
   // -1.6 -> -2 , -2.5 -> -3
			 UP(BigDecimal.ROUND_UP),
   // 2.5 -> 2 , 1.6 -> 1
   // -1.6 -> -1 , -2.5 -> -2
			 DOWN(BigDecimal.ROUND_DOWN),
			 // 2.5 -> 3 , 1.6 -> 2
   // -1.6 -> -1 , -2.5 -> -2
			 CEILING(BigDecimal.ROUND_CEILING),
			 // 2.5 -> 2 , 1.6 -> 1
   // -1.6 -> -2 , -2.5 -> -3
			 FLOOR(BigDecimal.ROUND_FLOOR),
   	// 2.5 -> 3 , 1.6 -> 2
   // -1.6 -> -2 , -2.5 -> -3
			 HALF_UP(BigDecimal.ROUND_HALF_UP),
   //......
}
```

### 大小比较

`a.compareTo(b)` : 返回 -1 表示 `a` 小于 `b`，0 表示 `a` 等于 `b` ， 1 表示 `a` 大于 `b`。

```java
BigDecimal a = new BigDecimal("1.0");
BigDecimal b = new BigDecimal("0.9");
System.out.println(a.compareTo(b));// 1
```

### 保留几位小数

通过 `setScale`方法设置保留几位小数以及保留规则。保留规则有挺多种，不需要记，IDEA 会提示。

```java
BigDecimal m = new BigDecimal("1.255433");
BigDecimal n = m.setScale(3,RoundingMode.HALF_DOWN);
System.out.println(n);// 1.255
```

## BigDecimal 等值比较问题

《阿里巴巴 Java 开发手册》中提到：

![](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/github/javaguide/java/basis/image-20220714161315993.png)

`BigDecimal` 使用 `equals()` 方法进行等值比较出现问题的代码示例：

```java
BigDecimal a = new BigDecimal("1");
BigDecimal b = new BigDecimal("1.0");
System.out.println(a.equals(b));//false
```

这是因为 `equals()` 方法不仅仅会比较值的大小（value）还会比较精度（scale），而 `compareTo()` 方法比较的时候会忽略精度。

1.0 的 scale 是 1，1 的 scale 是 0，因此 `a.equals(b)` 的结果是 false。

[**当时不明白的参数**](http://t.csdn.cn/xlkSL)

![](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/github/javaguide/java/basis/image-20220714164706390.png)

`compareTo()` 方法可以比较两个 `BigDecimal` 的值，如果相等就返回 0，如果第 1 个数比第 2 个数大则返回 1，反之返回-1。

```java
BigDecimal a = new BigDecimal("1");
BigDecimal b = new BigDecimal("1.0");
System.out.println(a.compareTo(b));//0
```

## BigDecimal 工具类分享

网上有一个使用人数比较多的 `BigDecimal` 工具类，提供了多个静态方法来简化 `BigDecimal` 的操作。

我对其进行了简单改进，分享一下源码：

```java
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 简化BigDecimal计算的小工具类
 */
public class BigDecimalUtil {

    /**
     * 默认除法运算精度
     */
    private static final int DEF_DIV_SCALE = 10;

    private BigDecimalUtil() {
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double subtract(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double multiply(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double divide(double v1, double v2) {
        return divide(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double divide(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = BigDecimal.valueOf(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 提供精确的类型转换(Float)
     *
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public static float convertToFloat(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.floatValue();
    }

    /**
     * 提供精确的类型转换(Int)不进行四舍五入
     *
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public static int convertsToInt(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.intValue();
    }

    /**
     * 提供精确的类型转换(Long)
     *
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public static long convertsToLong(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.longValue();
    }

    /**
     * 返回两个数中大的一个值
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中大的一个值
     */
    public static double returnMax(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.max(b2).doubleValue();
    }

    /**
     * 返回两个数中小的一个值
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中小的一个值
     */
    public static double returnMin(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.min(b2).doubleValue();
    }

    /**
     * 精确对比两个数字
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
     */
    public static int compareTo(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.compareTo(b2);
    }

}
```

## 总结

浮点数没有办法用二进制精确表示，因此存在精度丢失的风险。

不过，Java 提供了`BigDecimal` 来操作浮点数。`BigDecimal` 的实现利用到了 `BigInteger` （用来操作大整数）, 所不同的是 `BigDecimal` 加入了小数位的概念。



# 6. Static关键字

再类中，用static声明的成员变量为静态成员变量，也称为类变量。类变量的生命周期与类相同，在整个应用程序执行周期期间都有效。

**这里要强调一下：**

- static修饰的成员变量和方法，从属于类
- 普通变量和方法从属于对象
- 静态方法不能调用非静态成员，编译会报错

**Static关键字的用途**

一句话描述就是：方便在没有创建对象的情况下进行调用(方法/变量)。

显然，被static修饰的方法和变量不需要依赖于对象来进行访问，只要类被加载了，就可以通过类名去访问。

static可以修饰类的成员方法，类的成员变量，另外也可以边写static代码块来优化程序性能

**static方法**

static方法也成为静态方法，由于静态方法不依赖于任何对象就可以直接访问，因此**对于静态方法来说是没有this的**，因为不依附于任何对象，既然没对象就谈不上this了，并且由于此特性，在静态方法中不能访问非静态成员变量和非静态方法，因为非静态成员变量和非静态方法都需要依赖于具体的对象才能够被调用。

> 虽然在静态方法中不能访问非静态成员方法和非静态成员变量，但是在非静态成员方法中是可以访问静态成员方法和静态成员变量。

【代码示例】

![image-20221211145216382](/Users/jamison/Library/Application Support/typora-user-images/image-20221211145216382.png)

从以上代码看出：

- test2()中调用非静态成员变量address，编译失败。这是因为编译期间并没有对象生成，address变量根本不存在。
- test2()调用非静态方法test1()失败，编译失败，这是因为，编译器无法预知在非静态成员方法test1()中是否访问了非静态成员变量，所以也禁止在静态方法中调用非静态成员方法
- 非静态成员方法test1()访问静态成员方法test2()/b变量name是没有限制的

所以，如果想在不创建对象的情况下调用某个方法，就可以将这个方法设置为static。最常见的静态方法就是main方法，这就是为什么main方法是静态方法就一目了然了，因为程序在执行main方法的时候没有创建任何对象，只有通过类名来访问。

> **特别说明：**static方法是属于类的，非实例对象，在JVM加载类时，就已经存在于内存中了，不会被虚拟机GC回收掉，这样内存负荷会很大，但是非static方法会在运行完毕后被虚拟机GC掉，减轻内存压力	

**static变量**

static变量也称为静态变量，静态变量和非静态变量的区别：

- 静态变量被所有对象共享，在内存中只有一个副本，在类初次加载的时候才会被初始化
- 非静态变量是对象所拥有的，在创建对象的时候初始化，存在多个副本，各个对象拥有的副本互不影响

> static成员变量的初始化顺序按照定义的顺序初始化

**static块**

**对比构造方法用于对象的初始化**。静态初始化块，用于类的初始化操作。

在静态初始化块中不能直接访问非static成员。

**static块的作用**

静态初始块的作用就是**提升程序性能**。

为什么说静态初始化块能提升程序性能，**代码示例如下：**

```java
public class Person {
    private Date birthday;
    public Person(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 判断这个人是不是2000-2004年间出生的
     * @return
     */
    boolean isBronBoomer() {
        Date startDate = Date.valueOf("2000");
        Date endDate = Date.valueOf("2004");
        return birthday.compareTo(startDate) >= 0 && birthday.compareTo(endDate) < 0;
    }
}
```

isBornBoomer是用来这个人是否是2000-2004年间出生的，而每次isBornBoomer被调用的时候，都会生成startDate和birthDate两个对象，造成了空间浪费，如果改成这样效率会更好：

```java
public class Person {
    private Date birthday;
    private static Date startDate, endDate;
    static {
        Date startDate = Date.valueOf("2000");
        Date endDate = Date.valueOf("2004");
    }

    public Person(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 判断这个人是不是2000-2004年间出生的
     * @return
     */
    boolean isBronBoomer() {
        return birthday.compareTo(startDate) >= 0 && birthday.compareTo(endDate) < 0;
    }
}
```

因此，很多时候会将一些只需要进行一次的初始化操作都放在static代码块中进行

> 静态初始化块可以置于类中的任何地方，类中可以有多个静态初始化块。
>
> 在类被初次加载时，会按照静态初始化块的顺序来执行每个块，并且只会执行一次。

**static关键字的误区**

1. static关键字会改变类中成员的访问权限吗？

   有些初学的朋友会将java中的static与C/C++中的static关键字的功能混淆了。在这里只需要记住一点：与C/C++中的static不同，Java中的static关键字不会影响到变量或者方法的作用域。在Java中能够影响到访问权限的只有private, public, protected（包括包访问权限）这几个关键字。看下面的例子就明白了：

   ```java
   public class Person {
       private String name = "jamison";
       private static String addr = "China";
   }
   ```

   ![示例](/Users/jamison/Library/Application Support/typora-user-images/image-20221211154807598.png)

**说明static关键字不能改变变量和方法的访问权限**

2. 能用this访问静态成员变量吗？

   ```java
   public class Main {
       static int value = 33;
   
       public static void main(String[] args) {
           new Main().printValue();
       }
   
       void printValue() {
           int value = 4;
           System.out.println(this.value);
       }
   }
   ```

   这段代码输出结果为：33

   this代表什么？this代表当前对象，那么通过new Main()来调用printValue的话，当前对象就是通过new Main()生成的对象。而static变量是被对象所享有的，因此在printValue中的this.value的值毫无疑问是33。在printValue是局部变量根本不可能与this关联，所以输出结果是33。在这里永远记住一点：静态变量虽然独立于对象，但是不代表不可以通过对象去访问，所有的静态方法和静态变量都可以通过对象访问（只要访问权限足够）。

3. static能作用于局部变量吗？

   static是不允许用来修饰局部变量。不要问为什么，这是Java语法的规定。

**static常见笔试面试题目**

1. 下面这段代码输出的结果是什么？

   ```java
   public class Test extends Base{
   
       static{
           System.out.println("test static");
       }
   
       public Test(){
           System.out.println("test constructor");
       }
   
       public static void main(String[] args) {
           new Test();
       }
   }
   
   class Base{
   
       static{
           System.out.println("base static");
       }
   
       public Base(){
           System.out.println("base constructor");
       }
   }
   ```

   输出结果为：

   ```java
   base static
   test static
   base constructor
   test constructor
   ```

   分析这段代码的执行过程：

   - 找到main方法入口，main方法是程序入口，但在执行main方法之前，要先加载Test类
   - 加载Test类的时候，发现Test类继承Base类，于是先去加载Base类
   - 加载Base类的时候，发现Base类有static块，而是先执行static块，输出base static结果
   - Base类加载完成后，再去加载Test类，发现Test类也有static块，而是执行Test类中的static块，输出test static结果
   - Base类和Test类加载完成后，然后执行main方法中的`new Test()`，调用子类构造器之前会先调用父类构造器
   - 调用父类构造器，输出base constructor结果
   - 然后再调用子类构造器，输出test constructor结果

2. 这段代码输出的结果是什么？

   ```java
   public class Test {
       Person person = new Person("Test");
       static{
           System.out.println("test static");
       }
   
       public Test() {
           System.out.println("test constructor");
       }
   
       public static void main(String[] args) {
           new MyClass();
       }
   }
   
   class Person{
       static{
           System.out.println("person static");
       }
       public Person(String str) {
           System.out.println("person "+str);
       }
   }
   
   
   class MyClass extends Test {
       Person person = new Person("MyClass");
       static{
           System.out.println("myclass static");
       }
   
       public MyClass() {
           System.out.println("myclass constructor");
       }
   }
   ```

   输出结果：

   ```
   test static
   myclass static
   person static
   person Test
   test constructor
   person MyClass
   myclass constructor
   ```

   为什么输出结果是这样的？我们来分析下这段代码的执行过程：

   - 找到main方法入口，main方法是程序入口，但在执行main方法之前，要先加载Test类
   - 加载Test类的时候，发现Test类有static块，而是先执行static块，输出test static结果
   - 然后执行`new MyClass()`,执行此代码之前，先加载MyClass类，发现MyClass类继承Test类，而是要先加载Test类，Test类之前已加载
   - 加载MyClass类，发现MyClass类有static块，而是先执行static块，输出myclass static结果
   - **然后调用MyClass类的构造器生成对象，在生成对象前，需要先初始化父类Test的成员变量，而是执行`Person person = new Person("Test")`代码，发现Person类没有加载**
   - 加载Person类，发现Person类有static块，而是先执行static块，输出person static结果
   - 接着执行Person构造器，输出person Test结果
   - 然后调用父类Test构造器，输出test constructor结果，这样就完成了父类Test的初始化了
   - 再初始化MyClass类成员变量，执行Person构造器，输出person MyClass结果
   - 最后调用MyClass类构造器，输出myclass constructor结果，这样就完成了MyClass类的初始化了

3. 这段代码的输出结果是什么?

   ```java
   public class Test {
   
       static{
           System.out.println("test static 1");
       }
       public static void main(String[] args) {
   
       }
   
       static{
           System.out.println("test static 2");
       }
   }
   ```

   输出结果:

   ```
   test static 1
   test static 2
   ```

   虽然在main方法中没有任何语句，但是还是会输出，原因上面已经讲述过了。另外，static块可以出现类中的任何地方（只要不是方法内部，记住，任何方法内部都不行），并且执行是按照static块的顺序执行的。



# 7. Volatile关键字

http://t.csdn.cn/EL8hh

## 简介

volatile是Java提供的一种轻量级同步机制。Java语言包含两种内在的同步机制：同步块（或方法）和volatile变量，相当于synchronized（synchronized通常称为重量级锁），**volatile更轻量级**，**因为他不会引起线程上下文切换和调度**。但是volatile变量的同步性较差（有时候它更简单并且开销更低），而且使用也容易出错。

## 并发编程的三个基本概念

### 原子性

> **定义**：即一个操作或者多个操作，要么全部执行并且执行的过程不会被任何因素打断，要么就不执行。

​	原子性是拒绝多线程操作的，不论是多核还是单核，具有原子性的量，同一时刻只能有一个线程来对他进行操作。简而言之，在整个操作过程中不会被线程调度器中断的操作，都可认为是原子性。例如a=1是原子操作，但是a++和a+=1就不是原子操作。**Java中的原子性操作包括：**

1. 基本类型的读取和赋值操作，**且赋值必须是值赋值给变量**，变量之间的赋值操作不是原子操作。
2. <font color='red'>所有引用reference的赋值操作  </font>
3. java.concurrent.Atomic.*包中的一切操作

### 可见性

> **定义**：指当多个线程访问同一个变量时，一个线程修改了这个变量的值，其他线程能够**立即**看得到修改的值。

​	在多线程环境下，一个线程对共享变量的操作对其他线程是不可见的。**Java提供了volatile来抱保证可见性**，

当一个变量被volatile修饰后，**表示着线程的本地内存无效**，**当一个线程修改共享变量后他会被立即更新到主内存中，其他线程读取共享变量时，会直接从主内存中读取**。当然，**synchronized和lock都可以保证可见性。**synchronized和lock能保证同一时刻只有一个线程获取锁然后执行同步代码，**并且在释放锁之前会将对变量的修改刷新到主存中**，因此可以保证可见性。

### 有序性

> **定义**：即程序执行的顺序按照代码的顺序执行。

​	Java内存模型的有序性可以总结为：如果在本线程内观察，所有操作都是有序的；如果在一个线程观察另一个线程，所有操作都是无序的。前半句是指“线程内表现为串行语义”，后半句是指“指令重排序<font color='red'>（可以去了解下内存屏障Memory Barrier）</font>”现象和“<font color='red'>工作内存主内存同步延迟现象</font>”。

​	在Java内存模型中，为了效率是允许编译器和处理器对指令进行重排序，当然重排序不会影响单线程的运行结果，<font color='red'>但是对多线程会有影响。</font>Java提供volatile来保证一定的有序性。最著名的例子就是<font color='red'>单例模式中的DCL（双重检查锁）</font>。另外，可以通过synchronized和<font color='red'>Lock</font>来保证有序性，synchronized和lock保证每个时刻是有一个线程执行同步代码，相当于让线程顺序执行同步代码，自然就保证了有序性。

## 锁的互斥性和可见性

锁提供了两种主要特性：互斥（mutual exclusion）和可见性（visibility）。

1. 互斥即一次只能让一个线程持有某个特定的锁，一次就只有一个线程能够使用该共享数据。

2. 可见性要复杂一点，**他必须确保释放锁之前对共享数据做出的更改对于随后获得该锁的另一个线程是可见的。**

   也即当一条线程修改了共享变量的值，新值对于其他线程来说是可以立即得知的。如果没有同步机制提供的这种可见性保证，线程看到的共享变量可能是修改前的值或不一致的值，这将引发许多严重问题。要使得volatile变量提供理想的线程安全，必须同时满足下面两个条件：、

   - 对变量的写操作不依赖当前值。
   - 该变量没有包含在其他变量的<font color='red'>不变式</font>中。

​	实际上，这些条件表明，可以被写入volatile变量的有效值，独立于任何程序的状态，包括变量的当前状态。事实上就是保证操作是原子性操作，才能保证使用volatile关键字的程序在并发时能够挣却执行。

## Java内存模型JMM以及共享变量的可见性

JMM决定一个线程对共享变量的写入何时对另一个线程可见，**JMM定义了线程和主内存之间的抽象关系：共享变量存储在在主内存（Main Memory）中，每个线程都有一个私有的本地内存（Local Memory），本地内存保存了被该线程使用到的主内存的副本拷贝，线程对变量的所有操作都必须在工作内存中执行，而不能直读写内存中的变量。**

![JMM](/Users/jamison/Library/Application Support/typora-user-images/image-20221213173543418.png)

​	对于普通的共享变量来说，线程A将其修改为某个值保存在线程A的本地内存中，此时还未同步到主内存中去；而线程B已经缓存了该变量的旧值，所以就导致了，共享变量的值不一致。解决这种共享变量在多线程模型中不可见的问题，较粗暴地方式自然就是加锁，但是此处使用synchronized或者Lock就太重量级了，比较合理的方式其实就是volatile。

​	需要注意的是，**JMM是个抽象的内存模型，所以所谓的本地内存，主内存都是抽象概念，并不一定就真实的对应CPU缓存和物理内存。**

## volatile变量的特性

### 1. 保证可见性，不保证原子性

- 当写一个volatile变量时，JMM会把该线程本地内存中的变量强制刷新到主内存中去
- 这个写操作会导致其他线程中的volatile缓存无效

### 2. 禁止指令重排

重排序是指编译器和处理器为了优化程序性能而对指令序列进行排序的一种手段。重排序需要遵守一定的规则：

- 重排序操作不会对存在数据依赖关系的操作进行重排序。比如：a=1;b=a;这个指令序列，由于第二个操作依赖于第一个操作，所以在编译时和处理器运行时这两个操作不会被重排序。

- 重排序是为了优化性能，但是不管怎么排序，单线程下的性能不能被改变，比如：a=1;b=2;c=a+b;这三个操作，第一步（a=1）由于不存在数据依赖关系，所以可能会发生重排序，但是c=a+b这个操作是不会被重排序的，因为需要保证最终的结果一定是c=a+b=3。

  ​	重排序在单线程下一定能保证结果的正确性，但是在多线程环境下，发生重排序，可能会影响结果，下例中的1和2由于不存在数据依赖关系，则有可能被重排序，先执行status=true再执行a=2。而此时线程B会顺利到达4处，而线程A中a=2这个操作还未被执行，所以b=a+1的结果也有可能依然等于2。

  ```java
  public class TestVolatile{int a = 1;boolean status = false;//状态切换为truepublic void changeStatus{a = 2;   //1status = true;  //2}//若状态为true，则为runningpublic void run(){if(status){   //3int b = a + 1;  //4System.out.println(b);}}}
  ```

  ​	使用volatile关键字修饰共享变量可以禁止这种重排序。若用volatile修饰共享变量，在编译时，会在指令序列中插入内存屏障来禁止特定类型的处理器重排序，volatile禁止指令重排序也有一定规则：

  - 当程序执行到volatile变量的读操作或者写操作时，在其前面的操作的更改肯定全部已经进行，且结果已经对后面的操作可见；在其后面的操作肯定还没有进行；

  - 在进行指令优化时，不能将对volatile变量访问的语句放在其后面执行，也不能把volatile变量后面的语句放到其前面执行。

    ​	即执行到volatile变量时，其前面的所有语句都执行完，后面所有语句都未执行。且前面语句的结果对volatile变量及其后面语句可见。



## volatile不适合复合操作

 例如，inc++不是一个原子性操作，可以由读取、加、赋值3步组成，所以结果并不能达到30000。.

![image-20221213182701312](/Users/jamison/Library/Application Support/typora-user-images/image-20221213182701312.png)

解决方法：

1. synchronized

   ![image-20221213182813591](/Users/jamison/Library/Application Support/typora-user-images/image-20221213182813591.png)

2. Lock

   ![image-20221213182851788](/Users/jamison/Library/Application Support/typora-user-images/image-20221213182851788.png)

3. 采用Java并发包中的原子操作类，原子操作类是通过<font color='red'>CAS循环</font>的方式来保证其原子性的

   ![image-20221213183047105](/Users/jamison/Library/Application Support/typora-user-images/image-20221213183047105.png)

## volatile原理

​	volatile可以保证线程可见性且提供了一定的有序性，但是无法保证原子性。在JVM底层volatile是采用“内存屏障”来实现的。观察加入volatile关键字和没有加入volatile关键字时所生成的汇编代码发现，加入volatile关键字时，**会多出一个lock前缀指令，lock前缀指令实际上相当于一个内存屏障（也成内存栅栏）**，内存屏障会提供3个功能：

1. 它确保指令重排序时不会把其后面的指令排到内存屏障之前的位置，也不会把前面的指令排到内存屏障的后面；即在执行到内存屏障这句指令时，在它前面的操作已经全部完成；
2. 它会强制将对缓存的修改操作立即写入主存；
3. 如果是写操作，它会导致其他CPU中对应的缓存行无效。

## 单例模式的双重锁为什么要加volatile

```java
public class TestInstance{
  private volatile static TestInstance instance;
  public static TestInstance getInstance(){
    //1
    if(instance == null){
    	//2
      synchronized(TestInstance.class){
    		//3
        if(instance == null){
   			//4
          instance = new TestInstance();
    		//5
        }
      }
    }
    return instance;
    //6
  }
}
```

 需要volatile关键字的原因是，在并发情况下，如果没有volatile关键字，在第5行会出现问题。instance = new TestInstance();可以分解为3行伪代码

```
a. memory = allocate() //分配内存
b. ctorInstanc(memory) //初始化对象
c. instance = memory //设置instance指向刚分配的地址
```

上面的代码在编译运行时，可能会出现重排序从a-b-c排序为a-c-b。在多线程的情况下会出现以下问题。当线程A在执行第5行代码时，B线程进来执行到第2行代码。假设此时A执行的过程中发生了指令重排序，即先执行了a和c，没有执行b。那么由于A线程执行了c导致instance指向了一段地址，所以B线程判断instance不为null，会直接跳到第6行并返回一个未初始化的对象。



---
title:  Java 魔法类 Unsafe 详解
category: Java
tag:
  - Java基础
---

> 本文整理完善自下面这两篇优秀的文章：
>
> - [Java魔法类：Unsafe 应用解析 - 美团技术团队 -2019](https://tech.meituan.com/2019/02/14/talk-about-java-magic-class-unsafe.html)
> - [Java 双刃剑之 Unsafe 类详解 - 码农参上 - 2021](https://xie.infoq.cn/article/8b6ed4195e475bfb32dacc5cb)

阅读过 JUC 源码的同学，一定会发现很多并发工具类都调用了一个叫做 `Unsafe` 的类。

那这个类主要是用来干什么的呢？有什么使用场景呢？这篇文章就带你搞清楚！

# 8. 魔法类Unsafe详解

<font color='red'>这部分我看的比较蒙</font>

## Unsafe 介绍

`Unsafe` 是位于 `sun.misc` 包下的一个类，主要提供一些用于执行低级别、不安全操作的方法，如直接访问系统内存资源、自主管理内存资源等，这些方法在提升 Java 运行效率、增强 Java 语言底层资源操作能力方面起到了很大的作用。但由于 `Unsafe` 类使 Java 语言拥有了类似 C 语言指针一样操作内存空间的能力，这无疑也增加了程序发生相关指针问题的风险。在程序中过度、不正确使用 `Unsafe` 类会使得程序出错的概率变大，使得 Java 这种安全的语言变得不再“安全”，因此对 `Unsafe` 的使用一定要慎重。

另外，`Unsafe` 提供的这些功能的实现需要依赖本地方法（Native Method）。你可以将本地方法看作是 Java 中使用其他编程语言编写的方法。本地方法使用 **`native`** 关键字修饰，Java 代码中只是声明方法头，具体的实现则交给 **本地代码**。

![](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/github/javaguide/java/basis/unsafe/image-20220717115231125.png)

**为什么要使用本地方法呢？**

1. 需要用到 Java 中不具备的依赖于操作系统的特性，Java 在实现跨平台的同时要实现对底层的控制，需要借助其他语言发挥作用。
2. 对于其他语言已经完成的一些现成功能，可以使用 Java 直接调用。
3. 程序对时间敏感或对性能要求非常高时，有必要使用更加底层的语言，例如 C/C++甚至是汇编。

在 JUC 包的很多并发工具类在实现并发机制时，都调用了本地方法，通过它们打破了 Java 运行时的界限，能够接触到操作系统底层的某些功能。对于同一本地方法，不同的操作系统可能会通过不同的方式来实现，但是对于使用者来说是透明的，最终都会得到相同的结果。

## Unsafe 创建

`sun.misc.Unsafe` 部分源码如下：

```java
public final class Unsafe {
  // 单例对象
  private static final Unsafe theUnsafe;
  ......
  private Unsafe() {
  }
  @CallerSensitive
  public static Unsafe getUnsafe() {
    Class var0 = Reflection.getCallerClass();
    // 仅在引导类加载器`BootstrapClassLoader`加载时才合法
    if(!VM.isSystemDomainLoader(var0.getClassLoader())) {
      throw new SecurityException("Unsafe");
    } else {
      return theUnsafe;
    }
  }
}
```

`Unsafe` 类为一单例实现，提供静态方法 `getUnsafe` 获取 `Unsafe`实例。这个看上去貌似可以用来获取 `Unsafe` 实例。但是，当我们直接调用这个静态方法的时候，会抛出 `SecurityException` 异常：

```bash
Exception in thread "main" java.lang.SecurityException: Unsafe
 at sun.misc.Unsafe.getUnsafe(Unsafe.java:90)
 at com.cn.test.GetUnsafeTest.main(GetUnsafeTest.java:12)
```

**为什么 `public static` 方法无法被直接调用呢？**

这是因为在`getUnsafe`方法中，会对调用者的`classLoader`进行检查，判断当前类是否由`Bootstrap classLoader`加载，如果不是的话那么就会抛出一个`SecurityException`异常。也就是说，只有启动类加载器加载的类才能够调用 Unsafe 类中的方法，来防止这些方法在不可信的代码中被调用。

**为什么要对 Unsafe 类进行这么谨慎的使用限制呢?**

`Unsafe` 提供的功能过于底层（如直接访问系统内存资源、自主管理内存资源等），安全隐患也比较大，使用不当的话，很容易出现很严重的问题。

**如若想使用 `Unsafe` 这个类的话，应该如何获取其实例呢？**

这里介绍两个可行的方案。

1、利用反射获得 Unsafe 类中已经实例化完成的单例对象 `theUnsafe` 。

```java
private static Unsafe reflectGetUnsafe() {
    try {
      Field field = Unsafe.class.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      return (Unsafe) field.get(null);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
}
```

2、从`getUnsafe`方法的使用限制条件出发，通过 Java 命令行命令`-Xbootclasspath/a`把调用 Unsafe 相关方法的类 A 所在 jar 包路径追加到默认的 bootstrap 路径中，使得 A 被引导类加载器加载，从而通过`Unsafe.getUnsafe`方法安全的获取 Unsafe 实例。

```bash
java -Xbootclasspath/a: ${path}   // 其中path为调用Unsafe相关方法的类所在jar包路径
```

## Unsafe 功能

概括的来说，`Unsafe` 类实现功能可以被分为下面 8 类：

1. 内存操作
2. 内存屏障
3. 对象操作
4. 数据操作
5. CAS 操作
6. 线程调度
7. Class 操作
8. 系统信息

### 内存操作

#### 介绍

如果你是一个写过 C 或者 C++ 的程序员，一定对内存操作不会陌生，而在 Java 中是不允许直接对内存进行操作的，对象内存的分配和回收都是由 JVM 自己实现的。但是在 `Unsafe` 中，提供的下列接口可以直接进行内存操作：

```java
//分配新的本地空间
public native long allocateMemory(long bytes);
//重新调整内存空间的大小
public native long reallocateMemory(long address, long bytes);
//将内存设置为指定值
public native void setMemory(Object o, long offset, long bytes, byte value);
//内存拷贝
public native void copyMemory(Object srcBase, long srcOffset,Object destBase, long destOffset,long bytes);
//清除内存
public native void freeMemory(long address);
```

使用下面的代码进行测试：

```java
private void memoryTest() {
    int size = 4;
    long addr = unsafe.allocateMemory(size);
    long addr3 = unsafe.reallocateMemory(addr, size * 2);
    System.out.println("addr: "+addr);
    System.out.println("addr3: "+addr3);
    try {
        unsafe.setMemory(null,addr ,size,(byte)1);
        for (int i = 0; i < 2; i++) {
            unsafe.copyMemory(null,addr,null,addr3+size*i,4);
        }
        System.out.println(unsafe.getInt(addr));
        System.out.println(unsafe.getLong(addr3));
    }finally {
        unsafe.freeMemory(addr);
        unsafe.freeMemory(addr3);
    }
}
```

先看结果输出：

```
addr: 2433733895744
addr3: 2433733894944
16843009
72340172838076673
```

分析一下运行结果，首先使用`allocateMemory`方法申请 4 字节长度的内存空间，调用`setMemory`方法向每个字节写入内容为`byte`类型的 1，当使用 Unsafe 调用`getInt`方法时，因为一个`int`型变量占 4 个字节，会一次性读取 4 个字节，组成一个`int`的值，对应的十进制结果为 16843009。

你可以通过下图理解这个过程：

![](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/github/javaguide/java/basis/unsafe/image-20220717144344005.png)

在代码中调用`reallocateMemory`方法重新分配了一块 8 字节长度的内存空间，通过比较`addr`和`addr3`可以看到和之前申请的内存地址是不同的。在代码中的第二个 for 循环里，调用`copyMemory`方法进行了两次内存的拷贝，每次拷贝内存地址`addr`开始的 4 个字节，分别拷贝到以`addr3`和`addr3+4`开始的内存空间上：

![](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/github/javaguide/java/basis/unsafe/image-20220717144354582.png)

拷贝完成后，使用`getLong`方法一次性读取 8 个字节，得到`long`类型的值为 72340172838076673。

需要注意，通过这种方式分配的内存属于 堆外内存 ，是无法进行垃圾回收的，需要我们把这些内存当做一种资源去手动调用`freeMemory`方法进行释放，否则会产生内存泄漏。通用的操作内存方式是在`try`中执行对内存的操作，最终在`finally`块中进行内存的释放。

**为什么要使用堆外内存？**

- 对垃圾回收停顿的改善。由于堆外内存是直接受操作系统管理而不是 JVM，所以当我们使用堆外内存时，即可保持较小的堆内内存规模。从而在 GC 时减少回收停顿对于应用的影响。
- 提升程序 I/O 操作的性能。通常在 I/O 通信过程中，会存在堆内内存到堆外内存的数据拷贝操作，对于需要频繁进行内存间数据拷贝且生命周期较短的暂存数据，都建议存储到堆外内存。

#### 典型应用

`DirectByteBuffer` 是 Java 用于实现堆外内存的一个重要类，通常用在通信过程中做缓冲池，如在 Netty、MINA 等 NIO 框架中应用广泛。`DirectByteBuffer` 对于堆外内存的创建、使用、销毁等逻辑均由 Unsafe 提供的堆外内存 API 来实现。

下图为 `DirectByteBuffer` 构造函数，创建 `DirectByteBuffer` 的时候，通过 `Unsafe.allocateMemory` 分配内存、`Unsafe.setMemory` 进行内存初始化，而后构建 `Cleaner` 对象用于跟踪 `DirectByteBuffer` 对象的垃圾回收，以实现当 `DirectByteBuffer` 被垃圾回收时，分配的堆外内存一起被释放。

```java
DirectByteBuffer(int cap) {                   // package-private

    super(-1, 0, cap, cap);
    boolean pa = VM.isDirectMemoryPageAligned();
    int ps = Bits.pageSize();
    long size = Math.max(1L, (long)cap + (pa ? ps : 0));
    Bits.reserveMemory(size, cap);

    long base = 0;
    try {
        // 分配内存并返回基地址
        base = unsafe.allocateMemory(size);
    } catch (OutOfMemoryError x) {
        Bits.unreserveMemory(size, cap);
        throw x;
    }
    // 内存初始化
    unsafe.setMemory(base, size, (byte) 0);
    if (pa && (base % ps != 0)) {
        // Round up to page boundary
        address = base + ps - (base & (ps - 1));
    } else {
        address = base;
    }
    // 跟踪 DirectByteBuffer 对象的垃圾回收，以实现堆外内存释放
    cleaner = Cleaner.create(this, new Deallocator(base, size, cap));
    att = null;
}
```

### 内存屏障

#### 介绍

在介绍内存屏障前，需要知道编译器和 CPU 会在保证程序输出结果一致的情况下，会对代码进行重排序，从指令优化角度提升性能。而指令重排序可能会带来一个不好的结果，导致 CPU 的高速缓存和内存中数据的不一致，而内存屏障（`Memory Barrier`）就是通过阻止屏障两边的指令重排序从而避免编译器和硬件的不正确优化情况。

在硬件层面上，内存屏障是 CPU 为了防止代码进行重排序而提供的指令，不同的硬件平台上实现内存屏障的方法可能并不相同。在 Java8 中，引入了 3 个内存屏障的函数，它屏蔽了操作系统底层的差异，允许在代码中定义、并统一由 JVM 来生成内存屏障指令，来实现内存屏障的功能。

`Unsafe` 中提供了下面三个内存屏障相关方法：

```java
//内存屏障，禁止load操作重排序。屏障前的load操作不能被重排序到屏障后，屏障后的load操作不能被重排序到屏障前
public native void loadFence();
//内存屏障，禁止store操作重排序。屏障前的store操作不能被重排序到屏障后，屏障后的store操作不能被重排序到屏障前
public native void storeFence();
//内存屏障，禁止load、store操作重排序
public native void fullFence();
```

内存屏障可以看做对内存随机访问的操作中的一个同步点，使得此点之前的所有读写操作都执行后才可以开始执行此点之后的操作。以`loadFence`方法为例，它会禁止读操作重排序，保证在这个屏障之前的所有读操作都已经完成，并且将缓存数据设为无效，重新从主存中进行加载。

看到这估计很多小伙伴们会想到`volatile`关键字了，如果在字段上添加了`volatile`关键字，就能够实现字段在多线程下的可见性。基于读内存屏障，我们也能实现相同的功能。下面定义一个线程方法，在线程中去修改`flag`标志位，注意这里的`flag`是没有被`volatile`修饰的：

```java
@Getter
class ChangeThread implements Runnable{
    /**volatile**/ boolean flag=false;
    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("subThread change flag to:" + flag);
        flag = true;
    }
}
```

在主线程的`while`循环中，加入内存屏障，测试是否能够感知到`flag`的修改变化：

```java
public static void main(String[] args){
    ChangeThread changeThread = new ChangeThread();
    new Thread(changeThread).start();
    while (true) {
        boolean flag = changeThread.isFlag();
        unsafe.loadFence(); //加入读内存屏障
        if (flag){
            System.out.println("detected flag changed");
            break;
        }
    }
    System.out.println("main thread end");
}
```

运行结果：

```
subThread change flag to:false
detected flag changed
main thread end
```

而如果删掉上面代码中的`loadFence`方法，那么主线程将无法感知到`flag`发生的变化，会一直在`while`中循环。可以用图来表示上面的过程：

![](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/github/javaguide/java/basis/unsafe/image-20220717144703446.png)

了解 Java 内存模型（`JMM`）的小伙伴们应该清楚，运行中的线程不是直接读取主内存中的变量的，只能操作自己工作内存中的变量，然后同步到主内存中，并且线程的工作内存是不能共享的。上面的图中的流程就是子线程借助于主内存，将修改后的结果同步给了主线程，进而修改主线程中的工作空间，跳出循环。

#### 典型应用

在 Java 8 中引入了一种锁的新机制——`StampedLock`，它可以看成是读写锁的一个改进版本。`StampedLock` 提供了一种乐观读锁的实现，这种乐观读锁类似于无锁的操作，完全不会阻塞写线程获取写锁，从而缓解读多写少时写线程“饥饿”现象。由于 `StampedLock` 提供的乐观读锁不阻塞写线程获取读锁，当线程共享变量从主内存 load 到线程工作内存时，会存在数据不一致问题。

为了解决这个问题，`StampedLock` 的 `validate` 方法会通过 `Unsafe` 的 `loadFence` 方法加入一个 `load` 内存屏障。

```java
public boolean validate(long stamp) {
   U.loadFence();
   return (stamp & SBITS) == (state & SBITS);
}
```

### 对象操作

#### 介绍

**对象属性**

对象成员属性的内存偏移量获取，以及字段属性值的修改，在上面的例子中我们已经测试过了。除了前面的`putInt`、`getInt`方法外，Unsafe 提供了全部 8 种基础数据类型以及`Object`的`put`和`get`方法，并且所有的`put`方法都可以越过访问权限，直接修改内存中的数据。阅读 openJDK 源码中的注释发现，基础数据类型和`Object`的读写稍有不同，基础数据类型是直接操作的属性值（`value`），而`Object`的操作则是基于引用值（`reference value`）。下面是`Object`的读写方法：

```java
//在对象的指定偏移地址获取一个对象引用
public native Object getObject(Object o, long offset);
//在对象指定偏移地址写入一个对象引用
public native void putObject(Object o, long offset, Object x);
```

除了对象属性的普通读写外，`Unsafe` 还提供了 **volatile 读写**和**有序写入**方法。`volatile`读写方法的覆盖范围与普通读写相同，包含了全部基础数据类型和`Object`类型，以`int`类型为例：

```java
//在对象的指定偏移地址处读取一个int值，支持volatile load语义
public native int getIntVolatile(Object o, long offset);
//在对象指定偏移地址处写入一个int，支持volatile store语义
public native void putIntVolatile(Object o, long offset, int x);
```

相对于普通读写来说，`volatile`读写具有更高的成本，因为它需要保证可见性和有序性。在执行`get`操作时，会强制从主存中获取属性值，在使用`put`方法设置属性值时，会强制将值更新到主存中，从而保证这些变更对其他线程是可见的。

有序写入的方法有以下三个：

```java
public native void putOrderedObject(Object o, long offset, Object x);
public native void putOrderedInt(Object o, long offset, int x);
public native void putOrderedLong(Object o, long offset, long x);
```

有序写入的成本相对`volatile`较低，因为它只保证写入时的有序性，而不保证可见性，也就是一个线程写入的值不能保证其他线程立即可见。为了解决这里的差异性，需要对内存屏障的知识点再进一步进行补充，首先需要了解两个指令的概念：

- `Load`：将主内存中的数据拷贝到处理器的缓存中
- `Store`：将处理器缓存的数据刷新到主内存中

顺序写入与`volatile`写入的差别在于，在顺序写时加入的内存屏障类型为`StoreStore`类型，而在`volatile`写入时加入的内存屏障是`StoreLoad`类型，如下图所示：

![](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/github/javaguide/java/basis/unsafe/image-20220717144834132.png)

在有序写入方法中，使用的是`StoreStore`屏障，该屏障确保`Store1`立刻刷新数据到内存，这一操作先于`Store2`以及后续的存储指令操作。而在`volatile`写入中，使用的是`StoreLoad`屏障，该屏障确保`Store1`立刻刷新数据到内存，这一操作先于`Load2`及后续的装载指令，并且，`StoreLoad`屏障会使该屏障之前的所有内存访问指令，包括存储指令和访问指令全部完成之后，才执行该屏障之后的内存访问指令。

综上所述，在上面的三类写入方法中，在写入效率方面，按照`put`、`putOrder`、`putVolatile`的顺序效率逐渐降低。

**对象实例化**

使用 `Unsafe` 的 `allocateInstance` 方法，允许我们使用非常规的方式进行对象的实例化，首先定义一个实体类，并且在构造函数中对其成员变量进行赋值操作：

```java
@Data
public class A {
    private int b;
    public A(){
        this.b =1;
    }
}
```

分别基于构造函数、反射以及 `Unsafe` 方法的不同方式创建对象进行比较：

```java
public void objTest() throws Exception{
    A a1=new A();
    System.out.println(a1.getB());
    A a2 = A.class.newInstance();
    System.out.println(a2.getB());
    A a3= (A) unsafe.allocateInstance(A.class);
    System.out.println(a3.getB());
}
```

打印结果分别为 1、1、0，说明通过`allocateInstance`方法创建对象过程中，不会调用类的构造方法。使用这种方式创建对象时，只用到了`Class`对象，所以说如果想要跳过对象的初始化阶段或者跳过构造器的安全检查，就可以使用这种方法。在上面的例子中，如果将 A 类的构造函数改为`private`类型，将无法通过构造函数和反射创建对象，但`allocateInstance`方法仍然有效。

#### 典型应用

- **常规对象实例化方式**：我们通常所用到的创建对象的方式，从本质上来讲，都是通过 new 机制来实现对象的创建。但是，new 机制有个特点就是当类只提供有参的构造函数且无显示声明无参构造函数时，则必须使用有参构造函数进行对象构造，而使用有参构造函数时，必须传递相应个数的参数才能完成对象实例化。
- **非常规的实例化方式**：而 Unsafe 中提供 allocateInstance 方法，仅通过 Class 对象就可以创建此类的实例对象，而且不需要调用其构造函数、初始化代码、JVM 安全检查等。它抑制修饰符检测，也就是即使构造器是 private 修饰的也能通过此方法实例化，只需提类对象即可创建相应的对象。由于这种特性，allocateInstance 在 java.lang.invoke、Objenesis（提供绕过类构造器的对象生成方式）、Gson（反序列化时用到）中都有相应的应用。

### 数组操作

#### 介绍

`arrayBaseOffset` 与 `arrayIndexScale` 这两个方法配合起来使用，即可定位数组中每个元素在内存中的位置。

```java
//返回数组中第一个元素的偏移地址
public native int arrayBaseOffset(Class<?> arrayClass);
//返回数组中一个元素占用的大小
public native int arrayIndexScale(Class<?> arrayClass);
```

#### 典型应用

这两个与数据操作相关的方法，在 `java.util.concurrent.atomic` 包下的 `AtomicIntegerArray`（可以实现对 `Integer` 数组中每个元素的原子性操作）中有典型的应用，如下图 `AtomicIntegerArray` 源码所示，通过 `Unsafe` 的 `arrayBaseOffset` 、`arrayIndexScale` 分别获取数组首元素的偏移地址 `base` 及单个元素大小因子 `scale` 。后续相关原子性操作，均依赖于这两个值进行数组中元素的定位，如下图二所示的 `getAndAdd` 方法即通过 `checkedByteOffset` 方法获取某数组元素的偏移地址，而后通过 CAS 实现原子性操作。

![](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/github/javaguide/java/basis/unsafe/image-20220717144927257.png)

### CAS 操作

#### 介绍

这部分主要为 CAS 相关操作的方法。

```java
/**
	*  CAS
  * @param o         包含要修改field的对象
  * @param offset    对象中某field的偏移量
  * @param expected  期望值
  * @param update    更新值
  * @return          true | false
  */
public final native boolean compareAndSwapObject(Object o, long offset,  Object expected, Object update);

public final native boolean compareAndSwapInt(Object o, long offset, int expected,int update);

public final native boolean compareAndSwapLong(Object o, long offset, long expected, long update);
```

**什么是 CAS?** CAS 即比较并替换（Compare And Swap)，是实现并发算法时常用到的一种技术。CAS 操作包含三个操作数——内存位置、预期原值及新值。执行 CAS 操作的时候，将内存位置的值与预期原值比较，如果相匹配，那么处理器会自动将该位置值更新为新值，否则，处理器不做任何操作。我们都知道，CAS 是一条 CPU 的原子指令（cmpxchg 指令），不会造成所谓的数据不一致问题，`Unsafe` 提供的 CAS 方法（如 `compareAndSwapXXX`）底层实现即为 CPU 指令 `cmpxchg` 。

#### 典型应用

在 JUC 包的并发工具类中大量地使用了 CAS 操作，像在前面介绍`synchronized`和`AQS`的文章中也多次提到了 CAS，其作为乐观锁在并发工具类中广泛发挥了作用。在 `Unsafe` 类中，提供了`compareAndSwapObject`、`compareAndSwapInt`、`compareAndSwapLong`方法来实现的对`Object`、`int`、`long`类型的 CAS 操作。以`compareAndSwapInt`方法为例：

```java
public final native boolean compareAndSwapInt(Object o, long offset,int expected,int x);
```

参数中`o`为需要更新的对象，`offset`是对象`o`中整形字段的偏移量，如果这个字段的值与`expected`相同，则将字段的值设为`x`这个新值，并且此更新是不可被中断的，也就是一个原子操作。下面是一个使用`compareAndSwapInt`的例子：

```java
private volatile int a;
public static void main(String[] args){
    CasTest casTest=new CasTest();
    new Thread(()->{
        for (int i = 1; i < 5; i++) {
            casTest.increment(i);
            System.out.print(casTest.a+" ");
        }
    }).start();
    new Thread(()->{
        for (int i = 5 ; i <10 ; i++) {
            casTest.increment(i);
            System.out.print(casTest.a+" ");
        }
    }).start();
}

private void increment(int x){
    while (true){
        try {
            long fieldOffset = unsafe.objectFieldOffset(CasTest.class.getDeclaredField("a"));
            if (unsafe.compareAndSwapInt(this,fieldOffset,x-1,x))
                break;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
```

运行代码会依次输出：

```
1 2 3 4 5 6 7 8 9
```

在上面的例子中，使用两个线程去修改`int`型属性`a`的值，并且只有在`a`的值等于传入的参数`x`减一时，才会将`a`的值变为`x`，也就是实现对`a`的加一的操作。流程如下所示：

![](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/github/javaguide/java/basis/unsafe/image-20220717144939826.png)

需要注意的是，在调用`compareAndSwapInt`方法后，会直接返回`true`或`false`的修改结果，因此需要我们在代码中手动添加自旋的逻辑。在`AtomicInteger`类的设计中，也是采用了将`compareAndSwapInt`的结果作为循环条件，直至修改成功才退出死循环的方式来实现的原子性的自增操作。

### 线程调度

#### 介绍

`Unsafe` 类中提供了`park`、`unpark`、`monitorEnter`、`monitorExit`、`tryMonitorEnter`方法进行线程调度。

```java
//取消阻塞线程
public native void unpark(Object thread);
//阻塞线程
public native void park(boolean isAbsolute, long time);
//获得对象锁（可重入锁）
@Deprecated
public native void monitorEnter(Object o);
//释放对象锁
@Deprecated
public native void monitorExit(Object o);
//尝试获取对象锁
@Deprecated
public native boolean tryMonitorEnter(Object o);
```

方法 `park`、`unpark` 即可实现线程的挂起与恢复，将一个线程进行挂起是通过 `park` 方法实现的，调用 `park` 方法后，线程将一直阻塞直到超时或者中断等条件出现；`unpark` 可以终止一个挂起的线程，使其恢复正常。

此外，`Unsafe` 源码中`monitor`相关的三个方法已经被标记为`deprecated`，不建议被使用：

```java
//获得对象锁
@Deprecated
public native void monitorEnter(Object var1);
//释放对象锁
@Deprecated
public native void monitorExit(Object var1);
//尝试获得对象锁
@Deprecated
public native boolean tryMonitorEnter(Object var1);
```

`monitorEnter`方法用于获得对象锁，`monitorExit`用于释放对象锁，如果对一个没有被`monitorEnter`加锁的对象执行此方法，会抛出`IllegalMonitorStateException`异常。`tryMonitorEnter`方法尝试获取对象锁，如果成功则返回`true`，反之返回`false`。

#### 典型应用

Java 锁和同步器框架的核心类 `AbstractQueuedSynchronizer` (AQS)，就是通过调用`LockSupport.park()`和`LockSupport.unpark()`实现线程的阻塞和唤醒的，而 `LockSupport` 的 `park` 、`unpark` 方法实际是调用 `Unsafe` 的 `park` 、`unpark` 方式实现的。

```java
public static void park(Object blocker) {
    Thread t = Thread.currentThread();
    setBlocker(t, blocker);
    UNSAFE.park(false, 0L);
    setBlocker(t, null);
}
public static void unpark(Thread thread) {
    if (thread != null)
        UNSAFE.unpark(thread);
}
```

`LockSupport` 的`park`方法调用了 `Unsafe` 的`park`方法来阻塞当前线程，此方法将线程阻塞后就不会继续往后执行，直到有其他线程调用`unpark`方法唤醒当前线程。下面的例子对 `Unsafe` 的这两个方法进行测试：

```java
public static void main(String[] args) {
    Thread mainThread = Thread.currentThread();
    new Thread(()->{
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("subThread try to unpark mainThread");
            unsafe.unpark(mainThread);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }).start();

    System.out.println("park main mainThread");
    unsafe.park(false,0L);
    System.out.println("unpark mainThread success");
}
```

程序输出为：

```
park main mainThread
subThread try to unpark mainThread
unpark mainThread success
```

程序运行的流程也比较容易看懂，子线程开始运行后先进行睡眠，确保主线程能够调用`park`方法阻塞自己，子线程在睡眠 5 秒后，调用`unpark`方法唤醒主线程，使主线程能继续向下执行。整个流程如下图所示：

![](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/github/javaguide/java/basis/unsafe/image-20220717144950116.png)

### Class 操作

#### 介绍

`Unsafe` 对`Class`的相关操作主要包括类加载和静态变量的操作方法。

**静态属性读取相关的方法**

```java
//获取静态属性的偏移量
public native long staticFieldOffset(Field f);
//获取静态属性的对象指针
public native Object staticFieldBase(Field f);
//判断类是否需要实例化（用于获取类的静态属性前进行检测）
public native boolean shouldBeInitialized(Class<?> c);
```

创建一个包含静态属性的类，进行测试：

```java
@Data
public class User {
    public static String name="Hydra";
    int age;
}
private void staticTest() throws Exception {
    User user=new User();
    System.out.println(unsafe.shouldBeInitialized(User.class));
    Field sexField = User.class.getDeclaredField("name");
    long fieldOffset = unsafe.staticFieldOffset(sexField);
    Object fieldBase = unsafe.staticFieldBase(sexField);
    Object object = unsafe.getObject(fieldBase, fieldOffset);
    System.out.println(object);
}
```

运行结果：

```
falseHydra
```

在 `Unsafe` 的对象操作中，我们学习了通过`objectFieldOffset`方法获取对象属性偏移量并基于它对变量的值进行存取，但是它不适用于类中的静态属性，这时候就需要使用`staticFieldOffset`方法。在上面的代码中，只有在获取`Field`对象的过程中依赖到了`Class`，而获取静态变量的属性时不再依赖于`Class`。

在上面的代码中首先创建一个`User`对象，这是因为如果一个类没有被实例化，那么它的静态属性也不会被初始化，最后获取的字段属性将是`null`。所以在获取静态属性前，需要调用`shouldBeInitialized`方法，判断在获取前是否需要初始化这个类。如果删除创建 User 对象的语句，运行结果会变为：

```
truenull
```

**使用`defineClass`方法允许程序在运行时动态地创建一个类**

```java
public native Class<?> defineClass(String name, byte[] b, int off, int len, ClassLoader loader,ProtectionDomain protectionDomain);
```

在实际使用过程中，可以只传入字节数组、起始字节的下标以及读取的字节长度，默认情况下，类加载器（`ClassLoader`）和保护域（`ProtectionDomain`）来源于调用此方法的实例。下面的例子中实现了反编译生成后的 class 文件的功能：

```java
private static void defineTest() {
    String fileName="F:\\workspace\\unsafe-test\\target\\classes\\com\\cn\\model\\User.class";
    File file = new File(fileName);
    try(FileInputStream fis = new FileInputStream(file)) {
        byte[] content=new byte[(int)file.length()];
        fis.read(content);
        Class clazz = unsafe.defineClass(null, content, 0, content.length, null, null);
        Object o = clazz.newInstance();
        Object age = clazz.getMethod("getAge").invoke(o, null);
        System.out.println(age);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

在上面的代码中，首先读取了一个`class`文件并通过文件流将它转化为字节数组，之后使用`defineClass`方法动态的创建了一个类，并在后续完成了它的实例化工作，流程如下图所示，并且通过这种方式创建的类，会跳过 JVM 的所有安全检查。

![](https://guide-blog-images.oss-cn-shenzhen.aliyuncs.com/github/javaguide/java/basis/unsafe/image-20220717145000710.png)

除了`defineClass`方法外，Unsafe 还提供了一个`defineAnonymousClass`方法：

```java
public native Class<?> defineAnonymousClass(Class<?> hostClass, byte[] data, Object[] cpPatches);
```

使用该方法可以用来动态的创建一个匿名类，在`Lambda`表达式中就是使用 ASM 动态生成字节码，然后利用该方法定义实现相应的函数式接口的匿名类。在 JDK 15 发布的新特性中，在隐藏类（`Hidden classes`）一条中，指出将在未来的版本中弃用 `Unsafe` 的`defineAnonymousClass`方法。

#### 典型应用

Lambda 表达式实现需要依赖 `Unsafe` 的 `defineAnonymousClass` 方法定义实现相应的函数式接口的匿名类。

### 系统信息

#### 介绍

这部分包含两个获取系统相关信息的方法。

```java
//返回系统指针的大小。返回值为4（32位系统）或 8（64位系统）。
public native int addressSize();
//内存页的大小，此值为2的幂次方。
public native int pageSize();
```

#### 典型应用

这两个方法的应用场景比较少，在`java.nio.Bits`类中，在使用`pageCount`计算所需的内存页的数量时，调用了`pageSize`方法获取内存页的大小。另外，在使用`copySwapMemory`方法拷贝内存时，调用了`addressSize`方法，检测 32 位系统的情况。

## 总结

在本文中，我们首先介绍了 `Unsafe` 的基本概念、工作原理，并在此基础上，对它的 API 进行了说明与实践。相信大家通过这一过程，能够发现 `Unsafe` 在某些场景下，确实能够为我们提供编程中的便利。但是回到开头的话题，在使用这些便利时，确实存在着一些安全上的隐患，在我看来，一项技术具有不安全因素并不可怕，可怕的是它在使用过程中被滥用。尽管之前有传言说会在 Java9 中移除 `Unsafe` 类，不过它还是照样已经存活到了 Java16。按照存在即合理的逻辑，只要使用得当，它还是能给我们带来不少的帮助，因此最后还是建议大家，在使用 `Unsafe` 的过程中一定要做到使用谨慎使用、避免滥用。

