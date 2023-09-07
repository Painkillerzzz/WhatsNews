package com.example.myapplication.service;

import android.content.Context;

import com.example.myapplication.model.NewsItem;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class NewsDataSource {

    public NewsDataSource(Context context) {
        // 构造函数
    }

    // 不再需要 open 和 close 方法

    public boolean isNewsIDExists(String newsID) {
        // 使用Sugar ORM查询是否存在指定的 newsID
        List<NewsItem> results = Select.from(NewsItem.class)
                .where(Condition.prop("news_id").eq(newsID))
                .list();

        return results.size() > 0;
    }
}
