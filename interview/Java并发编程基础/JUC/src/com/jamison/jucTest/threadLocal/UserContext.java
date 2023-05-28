package com.jamison.jucTest.threadLocal;

class UserContext {
    private static ThreadLocal<User> userContext = new ThreadLocal<>();

    public static void setUser(User user) {
        userContext.set(user);
    }

    public static User getUser() {
        return userContext.get();
    }

    public static void clear() {
        userContext.remove();
    }
}