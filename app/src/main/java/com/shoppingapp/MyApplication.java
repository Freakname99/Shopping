package com.shoppingapp;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.shoppingapp.activities.MainActivity;
import com.shoppingapp.db.DbManager;

public class MyApplication extends Application {

    private  String userName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    private  String passWord;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
