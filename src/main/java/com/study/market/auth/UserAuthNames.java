package com.study.market.auth;

public class UserAuthNames {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_GUEST = "ROLE_GUEST";

    public static String combine(String... names) { //admin이면서 user일 수도 있으니까~~
        return String.join(",", names);
    }
}