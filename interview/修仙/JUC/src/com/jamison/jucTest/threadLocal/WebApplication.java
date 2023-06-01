package com.jamison.jucTest.threadLocal;

class WebApplication {
    public void processRequest() {
        //模拟用户登录，并将当前用户存储到UserContext
        User user = new User("jamison");
        UserContext.setUser(user);

        //处理请求
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.handlerRequest();

        //清除UserContext中的用户信息
        UserContext.clear();
    }
}