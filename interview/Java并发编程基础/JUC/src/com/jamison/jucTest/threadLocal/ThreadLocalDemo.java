package com.jamison.jucTest.threadLocal;

//当用户在Web应用中发起请求时，可以使用ThreadLocal来存储当前请求的上下文信息，方便各个组件或方法在处理请求时访问该上下文信息。以下是一个简单的示例：

public class ThreadLocalDemo {
    public static void main(String[] args) {
        WebApplication webApplication = new WebApplication();
        webApplication.processRequest();
    }
}
