package com.example.myapplication;

import com.example.myapplication.model.NewsItem;
import com.example.myapplication.model.UserData;
import com.orm.SugarApp;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.orm.SugarRecord;

public class WhatsNews extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();

        SugarContext.init(this);
    }
}
