package com.jamison.jucTest.threadLocal;

class RequestHandler {
    public void handlerRequest() {
        User currentUser = UserContext.getUser();
        System.out.println("Handling request for user: " + currentUser.getUsername());
        //其他处理逻辑
    }
}