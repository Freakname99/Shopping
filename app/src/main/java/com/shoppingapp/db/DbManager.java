package com.shoppingapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.shoppingapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DbManager {
    private final int BUFFER_SIZE = 400000;
    private String dbName;
    private int resId;
    public static final String PACKAGE_NAME = "com.shoppingapp";//包名
    public static final String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() +
            "/" + PACKAGE_NAME;   //数据库的绝对路径( /data/data/com.*.*(package name))
    private SQLiteDatabase db;
    private Context context;

    public DbManager(Context context) {
        this.context = context;

    }

    //对外提供的打开数据库接口
    public void openDataBase(String dbName,int resId) {
        this.dbName = dbName;
        this.resId = resId;
        this.db = this.openDataBase(DB_PATH + "/" + dbName+".db",dbName,resId);
    }

    //获取打开后的数据库
    public SQLiteDatabase getDb() {
        return this.db;
    }

    // 本地打开数据方法
    private SQLiteDatabase openDataBase(String filePath, String dbName,int resId) {
        try {
            File file = new File(filePath);
            if (!file.exists()) { //判断文件是否存在
                //通过输入流和输出流，把数据库拷贝到"filePath"下
                InputStream is = context.getResources().openRawResource(resId);//获取输入流，使用R.raw.test资源
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[BUFFER_SIZE];
                int readCount;
                while ((readCount = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, readCount);
                }
                fos.close();
                is.close();
            }
//打开数据库
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(filePath, null);
            return db;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //关闭数据库
    public void closeDataBase() {
        if (this.db != null) db.close();
    }
}
