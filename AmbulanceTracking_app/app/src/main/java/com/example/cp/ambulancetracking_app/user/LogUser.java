package com.example.cp.ambulancetracking_app.user;

public class LogUser {

    public static String userId,username,bkst;

    public static String getBkst() {
        return bkst;
    }

    public static void setBkst(String bkst) {
        LogUser.bkst = bkst;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        LogUser.userId = userId;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        LogUser.username = username;
    }
}
