# 实习日报

## 2023.3.6

工作内容：增删改查

### 1. 一般用POST来查询，不怎么使用GET（一个老哥讲的）

1. get是从服务器上获取数据（只能发送ASCII字符），post是向服务器传送数据（可以发送多种数据类型的数据）。

2. get传送的数据量较小（是有url长度限制的），post传送的数据量较大，一般被默认为不受限制。

3. get安全性非常低，post安全性较高。但是执行效率却比Post方法好（也就是post要比get慢）。

4. post通常用于修改和写入操作，get一般用于搜索排序和筛选等操作

5. 在进行文件上传时只能使用post而不能是get

### 2. @RequestBody @RequestBody @PathVariable注解

@RequestParam 和 @RequestBody 都是从 HttpServletRequest request 中取参的，而 @PathVariable 是映射 URI 请求参数中的占位符到目标方法的参数中的，接下来一一举例说明。

希望大家能了解：前端在不明确指出 Content-Type 时，默认为 application/x-www-form-urlencoded 格式，@RequestParam 可以获取 application/x-www-form-urlencoded 以及 application/json 这两种类型的参数，但是 @RequestBody 是用来获取非 application/x-www-form-urlencoded 类型的数据，比如 application/json、application/xml 等。

- @RequestParam

  ![image-20230306232927989](/Users/jamison/Library/Application Support/typora-user-images/image-20230306232927989.png)

- @PathVariable

  ![image-20230306233606178](/Users/jamison/Library/Application Support/typora-user-images/image-20230306233606178.png)

  ![image-20230306233632500](/Users/jamison/Library/Application Support/typora-user-images/image-20230306233632500.png)

- @RequestBody

  ![image-20230306234051535](/Users/jamison/Library/Application Support/typora-user-images/image-20230306234051535.png)
  
  ![image-20230306234116163](/Users/jamison/Library/Application Support/typora-user-images/image-20230306234116163.png)
  
  ![image-20230306234128549](/Users/jamison/Library/Application Support/typora-user-images/image-20230306234128549.png)
  
- 总结
  
  ![image-20230306234411529](/Users/jamison/Library/Application Support/typora-user-images/image-20230306234411529.png)
  
  ![image-20230306234428505](/Users/jamison/Library/Application Support/typora-user-images/image-20230306234428505.png)

### 3. parameterType 和 **resultType**

一个入参，一个返回参数