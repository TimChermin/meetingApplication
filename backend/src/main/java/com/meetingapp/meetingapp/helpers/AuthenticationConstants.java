package com.meetingapp.meetingapp.helpers;

public class AuthenticationConstants {
    public static final String SECRET = "Sup3RS3Cr3t@uth3NT1C@Ti0nK3Y";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer=";
    public static final String HEADER_STRING = "Authorization";

    public static final String PROP_FILE_NAME = "meetingapp.properties";
    public static final String USERNAME_FIELD_NAME = "meetingapp.adminUsername";
    public static final String PASSWORD_FIELD_NAME = "meetingapp.adminPassword";
    public static final String USERNAME_EXCHANGE = "meetingapp.usernameExchange";
    public static final String PASSWORD_EXCHANGE = "meetingapp.passwordExchange";
}

