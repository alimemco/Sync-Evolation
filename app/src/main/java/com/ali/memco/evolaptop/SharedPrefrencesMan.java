package com.ali.memco.evolation;

import android.content.Context;
import android.content.SharedPreferences;

import com.ali.memco.evolation.dataModel.User;

public class SharedPrefrencesMan {

    private SharedPreferences sharedPreferences;
    private static final String USER_SHARED_PREF_NAME="user_sharedPefrences";

    private static final String KEY_FIRST_NAME="first_name";
    private static final String KEY_LAST_NAME="last_name";
    private static final String KEY_JAVA_EXPRET="java_expert";
    private static final String KEY_HTML_EXPRET="html_expert";
    private static final String KEY_CSS_EXPRET="css_expert";
    private static final String KEY_GENDER="gender";

    public SharedPrefrencesMan(Context context){
        sharedPreferences = context.getSharedPreferences(USER_SHARED_PREF_NAME,Context.MODE_PRIVATE);
    }

    public void saveUser(User user){
        if (user!=null){
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(KEY_FIRST_NAME, user.getFirstName());
            edit.putString(KEY_LAST_NAME, user.getLastName());
            edit.putBoolean(KEY_JAVA_EXPRET,user.isJavaExpert());
            edit.putBoolean(KEY_HTML_EXPRET,user.isHtmlExpert());
            edit.putBoolean(KEY_CSS_EXPRET,user.isCssExpert());
            edit.putInt(KEY_GENDER,user.getGender());
            edit.apply();

        }
    }

    public User getUser(){
        User user = new User();

        user.setFirstName(sharedPreferences.getString(KEY_FIRST_NAME,""));
        user.setLastName(sharedPreferences.getString(KEY_LAST_NAME,""));
        user.setJavaExpert(sharedPreferences.getBoolean(KEY_JAVA_EXPRET,false));
        user.setHtmlExpert(sharedPreferences.getBoolean(KEY_HTML_EXPRET,false));
        user.setCssExpert(sharedPreferences.getBoolean(KEY_CSS_EXPRET,false));
        user.setGender((byte)sharedPreferences.getInt(KEY_GENDER,User.MALE));

        return user;
    }




}
