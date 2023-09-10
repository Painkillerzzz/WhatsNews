package com.example.myapplication;

import com.orm.SugarApp;
import com.orm.SugarContext;
import com.orm.SugarDb;

public class WhatsNews extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();

        // 删除数据库
        SugarDb sugarDb = new SugarDb(getApplicationContext());
        sugarDb.getDB().execSQL("DELETE FROM SQLITE_SEQUENCE");

        // 关闭数据库连接
        sugarDb.getDB().close();

        // 重新初始化SugarORM
        SugarContext.init(this);
    }
}
