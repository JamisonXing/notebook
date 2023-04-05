# Redis八股文--B站微软程序员

【【微软程序员】Redis入门】 https://www.bilibili.com/video/BV1aU4y1Z71c/?p=4&share_source=copy_web&vd_source=72c4cacd7237c3e04c39153a62aed182

## 一、基本数据类型

基本数据类型指的是值的数据类型，键都为字符串

### 1. 字符串

![image-20230405124832077](/Users/jamison/notebook/interview/redis/assets/image-20230405124832077.png)

- 为什么Redis不采用c语言的字符串直接做具体实现呢？
  - O(n)复杂度获取长度
  - 没有较好的扩容机制
  - 特殊字符无法处理

![image-20230405125020248](/Users/jamison/notebook/interview/redis/assets/image-20230405125020248.png)

左图3.0之前的，右图是之后的

Redis字符串又称为SDS（Simple Dynamic String）

- 左图
  - len是实际使用的长度，free是未使用的长度，len + free是实际申请的长度（空间）
  - buf就是存储字符串的结构了
- 右图
  - alloc是分配的长度，剩余的长度=alloc - len
  - flags是啥后面讲

![image-20230405125643826](/Users/jamison/notebook/interview/redis/assets/image-20230405125643826.png)

因为有的字符串长一点有的短一点不同的flags对应不同长度的sds，为了节省空间

- 总结
  - Redis字符串本质上是C语言的字符数组，加上了一点别的标识属性的结构体而已。
  - 这样做的优点
    - 字符串长度获取时间复杂度变从O(n)变为O(1)
    - 减少字符串扩容引起的数据搬运次数
    - 可以存储更加复杂的二进制数据

### 2. 链表

Redis底层链表的实现是双向链表

```c++
typedef struct listNode {
	struct listNode *prev;
	struct listNode *next;
	void * value;
} listNode;

typedef struct list {
  listNode * head; //链表头节点
  
  listNode * tail; //链表尾节点
  
  unsigned long len; //链表长度
  
  void *(*dup) (void *ptr); //节点复制函数
  
  void *(free) (void *ptr); //节点值释放函数
  
  void(*match) (void *ptr, void *key); //节点值对比函数
}
```

常见API函数：

![image-20230405131531583](/Users/jamison/notebook/interview/redis/assets/image-20230405131531583.png)

### 3. 哈希表

哈希表是一种存储数据的结构。

在哈希表中，键和值是一一对应的关系。哈希表可以通过键，在O(1)的时间复杂度的情况下获得对应的值。

由于C语言自己没有内置哈希表这一数据结构，因此Redis自己实现了Hash表。

- Redis采用拉链法作为哈希表的实现。

![image-20230405132041065](/Users/jamison/notebook/interview/redis/assets/image-20230405132041065.png)

- Redis中哈希表的数据结构

  - dicht 单纯表示一个哈希表

  - ![image-20230405133332240](/Users/jamison/notebook/interview/redis/assets/image-20230405133332240.png)

    - ```c++
      typedef struct dictht {
        dictEntry **table; //哈希表数组（哈希表项集合）
        unsigned long size; //Hash表的大小
        unsigned long sizemask; //哈希表掩码。类似于子网掩码，计算下标
        unsigned long used; //Hash表已使用的大小	 
      } dictht;
      ```

      负载因子 = used / size，需要保证负载因子在一个合理范围之内，不要太小白白占用空间，不要太大降低查询效率，后面讲调整原则。

  - dictEntry 哈希表的一项，可以看作就是一个键值对

  - ![image-20230405133318755](/Users/jamison/notebook/interview/redis/assets/image-20230405133318755.png)

    - ```c++
      typedef struct dictEntry {
        void *key;
        union {
          void *val;
          uint64_t u64;
          int64_t s64;
          double d;
        } v;
        struct dictEntry *next;
      } dictEntry;
      ```

  - dict Redis给外层调用的哈希表结构，包括两个dictht，也可以说封装了dicht

    - ![image-20230405133600346](/Users/jamison/notebook/interview/redis/assets/image-20230405133600346.png)

    - ```c++
      typedef struct dict {
        dictType *type;
        void *privdata;
        dictht ht[2];
        int rehashidx;
      } dict;
      ```

    - 负载因子 = 散列表内元素个数 / 散列表的长度

      负载因子值在合理范围内，程序需要对哈希表进行扩展和收缩。

      由于空间变大或者缩小，之前的键在老表的存储位置，在新表中不一定一样了，需要重新计算。这个重新计算，并把老表元素转移到新表元素的过程就叫做rehash。

    - ht[0]存放的老表，ht[1]存放的是新表

      步骤：

      - 分配空间给ht[1]。分配空间有ht[0]的具体参数决定。
      - 将ht[0]存储的键值对，重新计算hash值和索引值，并复制到ht[1]的对应位置中。
      - 当复制完成后，释放ht[0]所占空间，并将ht[0]指向ht[1]目前的地址。
      - ht[1]指向空表。

- 关于负载因子

  - ![image-20230405135400498](/Users/jamison/notebook/interview/redis/assets/image-20230405135400498.png)
  - 如果Redis没有执行后台备份，当负载因子大于等于1就执行（反正CPU闲着也是闲着）
  - 如果Redis在执行那个后台备份，当负载因子大于等于5就执行。（CPU在干备份了，咋对于实在挤的表改一改，等CPU闲下来，再把稍微偏挤的rehash）

### 4. 集合

- 普通集合

  - 就是对hash表的封装

- 整数集合

  - 里面只有整数，且是有序的

  - ![image-20230405140327168](/Users/jamison/notebook/interview/redis/assets/image-20230405140327168.png)

  - ```c++
    typedef struct inset {
      uint32_t encoding; //编码方式，包含int16_t, int32_t, int64_t
    	uint32_t length; //集合长度
      int8_t contents[]; //元素数组
    }
    ```

  - content是递增的数组，只能通过二分法查找元素，时间复杂度为O(logN)了，高于Hash查找，但是节省空间，这是Redis时间换空间的策略。

  - intset修改查找操作怎么做呢？

    - 修改：由于intset占用一段连续内存，所以每次修改数据需要重新申请空间，增加就是扩容，删除就是缩容
    - 查找，intset一段空间有序，因此可以执行二分查找算法

### 5. 有序集合

Zet实现方式之一是跳表，再讲之前需要讲一下跳表

普通链表，查找O(n)

![image-20230405145430006](/Users/jamison/notebook/interview/redis/assets/image-20230405145430006.png)

跳表降低了时间复杂度

![image-20230405145454668](/Users/jamison/notebook/interview/redis/assets/image-20230405145454668.png)

ZSet数据结构

![image-20230405145510893](/Users/jamison/notebook/interview/redis/assets/image-20230405145510893.png)

![image-20230405151925735](/Users/jamison/notebook/interview/redis/assets/image-20230405151925735.png)

```c++
typedef struct zskiplistNode {
  sds ele; //元素，在热词情景中，就变成了一段文字
  
  double score; //权重值，热词场景下就是热度
  
  struct zskiplistNode *backward;//指向后面的指针
  
	struct skiplistLevel {
    struct zskiplistNode *forward;
    unsigned long span;
  } level[];
  //节点的level数组，x.level[i].span
  //表示节点x在第i层到其下一个节点需跳过的节点数，两个相邻节点span为1
} zskiplistNode;

typedef struct zskiplist {
  struct zskiplistNode *header, *tail;
  unsigned long length;
  int level;
} zskiplist;
```

有意思的是level[]，比如上图三个索引的2（就是第一个节点）就是使用level[]来区分的，不是真正创建了三个节点。

- 跳表有什么问题吗？更新元素的时候需要重新创建索引，那有什么解决方法吗
  - ![image-20230405151429641](/Users/jamison/notebook/interview/redis/assets/image-20230405151429641.png)
  - 以上方法，对查询效率有影响，但是最终差不了太多

- 业务场景：利用Redis跳表实现微博热搜Top K

- ZSet常见API

  ![image-20230405152154348](/Users/jamison/notebook/interview/redis/assets/image-20230405152154348.png)

## 二、持久化

### 1. RDB

- Redis持久化简述
  - Redis是内存型数据库
  - 优点：内存读取速度快
  - 缺点：数据易失性，断电后内存数据消失
  - 解决方法：RDB(Redis Data Base)、AOP(Append Only File)
- RDB
  - 是一种全量备份
  - 把目前Redis内存中的数据，生成一个快照文件(RDB文件)，保存在硬盘中，如果发生事故，Redis可以通过RDB文件，进行文件读取，并将数据重新载入内存中
- RDB文件示意图
  - ![image-20230405153513270](/Users/jamison/notebook/interview/redis/assets/image-20230405153513270.png)
  - Redis、RDB版本号、数据、RDB文件结束标识码、RDB文件校验和
- 各个基础数据类型在RDB中的结构
  - ![image-20230405154150989](/Users/jamison/notebook/interview/redis/assets/image-20230405154150989.png)
- RDB触发条件
  - 手动触发
    - save: 执行该命令后，主线程执行rdbSave函数，服务器进程阻塞，即不能处理任何其他请求
    - bgsave: 本质上这个命令和save差不多，区别在于这个命令会去fork一个子进程，去执行rdbSave函数，因此主线程还是可以执行新请求的
  - 自动触发
    - 配置文件中写入save  m n，代表m秒内发生n次变化就执行bgsave 

### 2. AOF

AOF: 记录之后所有对Redis数据进行修改的操作

如果发生事故，Redis可以通过AOF文件，将文件中的数据修改命令全部执行一遍，以此来恢复数据

- AOF重写和恢复

  - ![image-20230405161559312](/Users/jamison/notebook/interview/redis/assets/image-20230405161559312.png)
  - 重写生成新的AOF文件的过程中，不会参考老的AOF文件，而是直接根据当前Redis数据生成

- AOF触发条件
  - 手动触发：bgrewriteaof
  - 自动触发：配置文件中设置appendonly yes开启，写入策略如下
    - Always：及同步写回，在每个写命令执行完成后，直接将命令落入磁盘文件（数据基本保证可靠性，但是影响Redis性能）
    - Everysec：即每秒写回，对于每个命令执行完成后，该命令被写入文件的内存缓冲区，每过1秒，Redis就会把该缓冲区的命令写到磁盘的AOF文件中（出了问题最多丢失一秒内的数据，性能影响较小）
    - NO：意思不是不执行AOF，而是将操作命令全部只写到Redis缓存区，至于在何时将缓存落盘，交给操作系统决定（出了问题，数据丢失情况不可控，性能影响最小）
    - 默认策略是Everysec
- Redis恢复和重写流程图
  - ![image-20230405162608031](/Users/jamison/notebook/interview/redis/assets/image-20230405162608031.png)
  - 恢复会创建一个伪客户端执行命令

## 三、缓存

### 1. 缓存淘汰（感觉没有细化）

- ### FIFO先进先出

  - ![image-20230405192650692](/Users/jamison/notebook/interview/redis/assets/image-20230405192650692.png)

- ### LRU最近最少使用

  - ![image-20230405192703286](/Users/jamison/notebook/interview/redis/assets/image-20230405192703286.png)
  - 双向链表 + 哈希表，双向链表用来存放值，哈希用来方便查找值，弥补链表查询效率为O(n)的缺点
  - ![image-20230405193521663](/Users/jamison/notebook/interview/redis/assets/image-20230405193521663.png)
  - 被访问到了就调整到最前面的位置

- ### LFU最不经常使用

  - ![image-20230405193952194](/Users/jamison/notebook/interview/redis/assets/image-20230405193952194.png)

### 2. 过期删除

- 主动删除
  - ![image-20230405194457116](/Users/jamison/notebook/interview/redis/assets/image-20230405194457116.png)
  - 到期就删除，对Redis性能影响较大
- 惰性删除
  - ![image-20230405194554538](/Users/jamison/notebook/interview/redis/assets/image-20230405194554538.png)
  - 访问该值的时候会查看是否过期，如果没有过期就返回否则则删除
  - 优点：服务器运算资源占用小
  - 缺点：可能导致一些数据长期霸占内存，不被删除的情况
- Redis删除策略：定期删除
  - ![image-20230405194836444](/Users/jamison/notebook/interview/redis/assets/image-20230405194836444.png)
  - 每隔一段时间主动删除过期的，其他时间点惰性删除

### 3. 缓存一致

- Cache Aside

  - ![image-20230405200818461](/Users/jamison/notebook/interview/redis/assets/image-20230405200818461.png)

  - 它的核心思想是，当缓存数据有更新值了，它采用的不是更新缓存数据，而是删除缓存数据

  - 两种读写流程

    - ![image-20230405200952488](/Users/jamison/notebook/interview/redis/assets/image-20230405200952488.png)

      当前情况下，会读到缓存的旧数据。为了解决这个问题，可以采用延迟双删

      ![image-20230405201148251](/Users/jamison/notebook/interview/redis/assets/image-20230405201148251.png)

    - ![image-20230405201243307](/Users/jamison/notebook/interview/redis/assets/image-20230405201243307.png)

      这种情况比较少见，原因：更新频繁的数据就不应该使用Redis缓存，第二查询数据库正常情况下是要比更新快的，出现这种情况可能是网络出现波动等原因。

- Read/Write

  - ![image-20230405201628255](/Users/jamison/notebook/interview/redis/assets/image-20230405201628255.png)
  - 右图是Cache Aside
  - Read/Write Through模式核心点在于把缓存作为数据读取的主要方式，即避免缓存击穿

- Write Bebind

  - ![image-20230405201822434](/Users/jamison/notebook/interview/redis/assets/image-20230405201822434.png)

  - 方法一：使用mq异步更新缓存

    方法二：如canal模拟主从复制，异步更新缓存

![image-20230405202052313](/Users/jamison/notebook/interview/redis/assets/image-20230405202052313.png)

### 4. 缓存击穿

![image-20230405202347832](/Users/jamison/notebook/interview/redis/assets/image-20230405202347832.png)

- 定义：查询某个数据，结果缓存中不存在，就会饶过缓存查询数据库
- 一般缓存会设置数据过期时间，所以缓存击穿的情况比较常见
- 带来的问题
  - ![image-20230405202540045](/Users/jamison/notebook/interview/redis/assets/image-20230405202540045.png)
  - 如果查询量特别大，MySQL顶不住就会宕机
- 如何解决呢？
  - ![image-20230405202653768](/Users/jamison/notebook/interview/redis/assets/image-20230405202653768.png)
  - 从MySQL角度出发：减少击穿后的直接流量，如直接加锁
  - 从Redis角度出发：
    - 设置热点数据永不过期
    - 热点数据后台起一个线程，重新刷新过期时间，把数据回填到缓存层

### 5. 缓存穿透

![image-20230405203608060](/Users/jamison/notebook/interview/redis/assets/image-20230405203608060.png)

- 定义：查询了一个缓存和数据库中都不存在的数据
- 一般解决方法
  - 拦截非法查询请求
  - 缓存空对象，直接返回空对象
  - 布隆过滤器
    - ![image-20230405204114213](/Users/jamison/notebook/interview/redis/assets/image-20230405204114213.png)

### 6. 缓存雪崩

![image-20230405204824714](/Users/jamison/notebook/interview/redis/assets/image-20230405204824714.png)

- 定义：一大批被缓存的数据同时失效，此时对于这一批的数据请求就全部打到数据库上了，导致数据库宕机
- 跟缓存击穿相似，缓存击穿是单点，缓存雪崩是多点
- 如何解决：
  - 从MySQL角度：减少并发量，如加锁
    - ![image-20230405205059866](/Users/jamison/notebook/interview/redis/assets/image-20230405205059866.png)
  - 从Redis角度：
    - 设置热点数据永不过期
    - 分析失效时间，尽量让失效时间点分散
    - 缓存预热，即在上线前，更急当天的情况分析，将热点数据直接加载到缓存系统

## 四、集群

### 1. 主从复制

 ![image-20230405212103960](/Users/jamison/notebook/interview/redis/assets/image-20230405212103960.png)

流程：

![image-20230405212137301](/Users/jamison/notebook/interview/redis/assets/image-20230405212137301.png)

1. 当主从库都上线后，他们不急着进行复制过程，首先需要进行握手，进行信息验证

![image-20230405212234658](/Users/jamison/notebook/interview/redis/assets/image-20230405212234658.png)

2. 当握手完成后，从库需要向主库发送PSYN命令，即同步命令，开启数据同步过程，并发送主库ID，复制的进度偏移量offset（为什么要发主库id呢，防止断线后主库变更）。

![image-20230405212458941](/Users/jamison/notebook/interview/redis/assets/image-20230405212458941.png)

3. 主库会根据从库发送的信息，进行逻辑判断，并告诉从库，是进行全量复制/断线后重复制。
4. 如果是全量复制
   - ![image-20230405212707057](/Users/jamison/notebook/interview/redis/assets/image-20230405212707057.png)
   - 主库执行BGSAVE生成RDB文件，并将文件生成过程中的数据命令放进开辟的缓冲区中
   - RDB文件产生后，主库发送给从库，从库通过RDB恢复数据
5. 命令传播阶段
   - ![image-20230405213149339](/Users/jamison/notebook/interview/redis/assets/image-20230405213149339.png)
   - 主库状态被修改了（如期间更新了数据）为了同步状态，主库会把数据变更命令发给从库，从库收到后执行命令。
6. 断线后重复制
   - ![image-20230405213229786](/Users/jamison/notebook/interview/redis/assets/image-20230405213229786.png)
   - 断线后重复值：断线重连后，此过程依赖服务器运行ID，复制偏移量，复制挤压缓冲区
     - 服务器运行ID：唯一确定主库的身份
     - 复制偏移量：代表主节点传输了的字节数
     - 复制挤压缓冲区：是一个FIFO队列，存储了最近主节点的数据修改命令

### 2. 哨兵机制

![image-20230405213834729](/Users/jamison/notebook/interview/redis/assets/image-20230405213834729.png)

- 哨兵机制：对主从库进行监控，如果主库下线，哨兵组进行投票，从从库中挑选出新的主库
- 哨兵服务器是一个不提供数据服务的Redis服务器

![image-20230405214041025](/Users/jamison/notebook/interview/redis/assets/image-20230405214041025.png)

心跳检测，若设置时间内哨兵没有收到某机器的心跳，就默认这个机器断连了

![image-20230405214227787](/Users/jamison/notebook/interview/redis/assets/image-20230405214227787.png)

哨兵自己发现主库连接不上了，就标注主库为主观下线，并通知其他哨兵，超过半数的哨兵连接不上主库后，哨兵就将其标注为客观下线，并挑选一个从库当主库。

![image-20230405214456603](/Users/jamison/notebook/interview/redis/assets/image-20230405214456603.png)

选举结束后，哨兵向slaveof no one此时从库就会变成主库，同时向其他slave发送主库的IP端口号。

![image-20230405214617502](/Users/jamison/notebook/interview/redis/assets/image-20230405214617502.png)

主库重新上线后，临时主库就会下线

### 3. Cluster

![image-20230405215930444](/Users/jamison/notebook/interview/redis/assets/image-20230405215930444.png)

分布式数据库解决方案，数据切分给多台机器存储。

![image-20230405220014331](/Users/jamison/notebook/interview/redis/assets/image-20230405220014331.png)

分区策略：采用虚拟槽，所有键通过CRC16校验函数，对16384取模，决定数据分配到哪个槽位。

每个Redis的cluster节点负责一部分槽slot的数据，并且集群节点也可以使用主从复制模式。

- 查询策略
  - ![image-20230405220254310](/Users/jamison/notebook/interview/redis/assets/image-20230405220254310.png)
  - 每个节点都包含集群中其他节点的元信息
    - 包括各个节点存储的槽数据
    - 各节点的master和slave状态
    - 各个节点是否存活
    - 。。。。
  - 元信息的传播
    - ![image-20230405220454782](/Users/jamison/notebook/interview/redis/assets/image-20230405220454782.png)
    - 采用gossip协议
      - 每隔一段时间执行一次
      - 像病毒传播一样类似于泛洪
- cluster扩缩容
  - ![image-20230405220732738](/Users/jamison/notebook/interview/redis/assets/image-20230405220732738.png)
  - 用户查询时正在扩缩容怎么办见上图
