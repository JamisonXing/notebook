# 特别篇 前行课程

## 1 基础语法和Web框架起步

### 1.1 包声明

![截图](0d1d6c9527b2ab98cdace75bc274d99d.png)

<br/>

	为什么有匿名引用的机制

![截图](2da0f5cf07fee837a1fdbccd0660ece1.png)

<br/>

### 1.2 string和基础类型

小技巧：长字符串可以使用反引号，会识别换行不需要转义字符

```golang
func useOfBackticks() 
	fmt.Print(`hello
自动换行`)
/*
   hello
   自动换行
*/
}
```

<br/>

string长度：

![截图](f07d1c4086aade3609910a4e5f27a2ed.png)

<br/>

<br/>

<br/>

# 一、开营第一课 如何学习与职业发展

一个好的开发工程师一定对sre有着很深的理解：

![截图](b1e1561ad65f2df934262ed9ce06bf55.png)

构建go语言知识体系：

![截图](108ffbe7aeb48c10bd2cdb98c479bbcc.png)

```
优秀的开源项目：gin ngix go-zero kratos k8s redis....
```

<br/>

信息获取：

![截图](547bc6c93fcb75430d576c14611c0300.png)

<br/>

工具：

![截图](b3a0794e2a2de7fea491e1c7dd459536.png)

<br/>

## 1. 缓存

内容大纲：1. 工作中常被问到cache和db的数据一致性问题。2. 可用性相关，比如缓存的性能，和出现集群故障，热点事件，缓存穿透如何对下游系统进行保护 3. 整理的缓存的最佳实践

### 1.1 缓存技巧

<br/>

![截图](022b678408e46ab0948ae92fc3477eeb.png)

<br/>

![截图](46511cf433e61bc39090cb32e334228a.png)

<br/>

![截图](946b456a031c7903ecbe68343a7b4ab4.png)

<br/>

# 二、微服务

## 2.1 微服务概览 巨shi架构(单体架构)到微服务架构的演进

1. 单体架构：

![截图](33d3cfe8240d6ddd38c8736a63becb20.png)

![截图](0ec1827a310480017dadd4bcd9cdafef.png)

<br/>

2. 微服务架构

- 微服务起源

![截图](30d05dcc65953c85a102079a4c28cdd6.png)

<br/>

![截图](8587fbb9e1eaa63866fe8a9b720f8d92.png)

- 微服务定义

![截图](0fe95421af8e6ef619647a4720abe7c1.png)

<br/>

![截图](3ba72facc123b3f89a95f9dbb30b7710.png)

<br/>

- 微服务的缺点

![截图](dbee410941ef145217f11aab5caff046.png)

<br/>

![截图](1f3502252d53bb877723e9e5da8c672e.png)

<br/>

- 实现微服务——组件服务化

![截图](4d69e8d4293978da5c2ecf9b13536305.png)

<br/>

![截图](336d44ce4ea938949c0adfb616398e47.png)

<br/>

- 微服务如何去组织

![截图](bd87427a2bd3c77d31e284146f67428b.png)

<br/>

- 去中心化

![截图](e9901a324b0ecb4719bafaed813c02c8.png)

<br/>

- 基础设施自动化

![截图](50ec2fc175f0dc85cb9b47c6bf960c84.png)

<br/>

- 可用性 & 兼容性设计

![截图](0b3ffa6b7d2d82e2b7f25b45baad93c2.png)

<br/>

![截图](9771fca37ee33b30afb30983f212c939.png)

<br/>

## 2.2 微服务设计

重要角色：api gateway，bff层，micro service、、、

### 2.2.1 微服务重要角色和演进过程

- Api Gateway

初代 微服务全部对外

![截图](acfda20f4e556657ce2ec71965f074ab.png)

<br/>

二代 bff对外，对内grpc，加了一层

![截图](7659ad5b8b10854bd65f8013e42983f6.png)

<br/>

三代 拆分bff

![截图](5bb9fc79c95fe1fe215729f63ce9d89d.png)

<br/>

四代 加了一层，引入api gateway，踢掉web server

**envy是网关框架，可以学习**，，java体系主要使用zuul，go体系主要有kong、ingress、envy

![截图](ebc8a2483dc54de5bdfc89039ee93bce.png)

<br/>

### 2.2.2 Microservice微服务划分

- 业务职能和上下文划分

![截图](24d8e6025b5c842f2f5e0e55603cde40.png)

<br/>

- CQRS划分

![截图](de21dc5adb349cbac8182ebf9f3aff2e.png)

<br/>

![截图](524e7d9929d045a5796391eec4bd6654.png)

<br/>

### 2.2.3 Microservice安全

<br/>

![截图](2aaea0db72a33cc21fc99739ccb86c6d.png)

<br/>

<br/>

## 2.3 gRPC&服务发现

服务与服务之间通讯的核心就是RPC框架（rpc偏向同步通讯）和消息队列（偏向异步通讯）

<br/>

### 2.3.1 gRPC

![截图](6fa6d2ef06cdb9d3955e659ffe647990.png)

<br/>

![截图](dd4c37c01cfd41ded349837d5f4fc7d6.png)

<br/>

![截图](c8ad21f6ce9b7da9f24699e69693eff5.png)

<br/>

### 2.3.2 服务发现

- 两种模式

1. 客户端发现模式 没有集中式的负载均衡

![截图](e4dc7ec47231e6e928f13ae6255cfc8c.png)

<br/>

2. 服务端发现模式 集中式的负载均衡

![截图](c60206aeb2ae3b63d8695026319e65da.png)

<br/>

3. 对比两种模式

![截图](bcabf12a0551f639c611e725092e3fb8.png)

<br/>

![截图](54a1568ac0ebc9df0f9909e0c9a1873a.png)

<br/>

- 服务发现

<br/>

![截图](1f64253738e55b97cb6b4c316783214b.png)

<br/>

![截图](7a569f6ba2f883e6144b5e4160144385.png)

<br/>

![截图](ea9761de4bdbf78f5620922ac50d7cdb.png)

<br/>

<br/>

## 2.4 多集群&多租户
