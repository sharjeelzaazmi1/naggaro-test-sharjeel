package com.naggaro.javatest.constants;


/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */

public class NaggaroConstants {

    private NaggaroConstants()
    {

    }

    public static final String ADMIN_USER_NAME="admin";
    public static final String ADMIN_PASS ="admin";
    public static final String USER_NAME="user";
    public static final String USER_PASS ="user";

    //2023-04-26 18:50:43
    public static final String DATE_TIME_FORMATE="yyyy-MM-dd hh:mm:ss";

    public static final long STATMENT_DEFAULT_TIME_IN_MONTHS = 3;

    public static final long TOKEN_EXPIRY_TIME_IN_MINUTES = 5;


    public static final String INVALID_TOKEN = "Invalid Token";
    public static final String USER_ALREADY_LOGGEDIN = "User Already Login";
    public static final String INVALID_TOKEN_OR_EXPIRED = "token invalid or expired";
    public static final String LOGOUT_TEXT = "Logout Successfully";
    public static final String USER_NOT_FOUND =  "user not found";
    public static final String ACCESS_DENIED = "Access Denied!";
}
