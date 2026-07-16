package com.xu.user.content;

public class UserContext {
    // 本地线程存储登录用户ID
    private static final ThreadLocal<Long> USER_ID_HOLDER = new ThreadLocal<>();

    // 存入用户ID
    public static void setLoginUserId(Long userId) {
        USER_ID_HOLDER.set(userId);
    }

    // 获取当前登录用户ID
    public static Long getLoginUserId() {
        return USER_ID_HOLDER.get();
    }

    public static void clear() {
        USER_ID_HOLDER.remove();
    }
}