package com.example.tpandroid.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Context;

public class PreferencesHelper  {

    public static void Save(Context context, String file,String key,String value){
        SharedPreferences preferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
        return;
    }

        public static String LoadValue(Context context,String file,String key){
        SharedPreferences preferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return preferences.getString(key,"key invalida");

    }
}
