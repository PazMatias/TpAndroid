package com.example.tpandroid.helpers;

public class TokenSingleton {

    private static TokenSingleton mTokenSingleton;
    public String token;
    public String token_refresh;

    private TokenSingleton(String token, String token_refresh){
        this.token = token;
        this.token_refresh = token_refresh;

    }

    public static synchronized TokenSingleton getInstance(String token, String token_refresh){
        if (mTokenSingleton == null){
            mTokenSingleton = new TokenSingleton(token,token_refresh);
        }

        return mTokenSingleton;
    }

    public static TokenSingleton getTokenSingleton(){
        return mTokenSingleton;
    }

    public void setToken(String token){
        this.token = token;
    }

    public void setTokenRefresh(String token_refresh){
        this.token = token_refresh;
    }

}
