package com.example.tccadoteumaarvore.utils;


import android.util.Base64;

public class Base64Custom {
    public static String encodeBase64(String str){
        return Base64.encodeToString(str.getBytes(), Base64.DEFAULT).replaceAll("[\\n\\r]", "");
    }
    public static String decodeBase64(String strBase64){
        return new String(Base64.decode(strBase64, Base64.DEFAULT));
    }
}
