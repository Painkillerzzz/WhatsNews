package com.example.zhangxiangyu;

import com.orm.SugarApp;
import com.orm.SugarContext;

public class WhatsNews extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();

        SugarContext.init(this);
    }
}
