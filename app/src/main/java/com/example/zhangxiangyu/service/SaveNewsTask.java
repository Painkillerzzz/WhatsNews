package com.example.zhangxiangyu.service;

import android.os.AsyncTask;
import com.example.zhangxiangyu.model.NewsItem;
import com.example.zhangxiangyu.model.UserData;
import com.example.zhangxiangyu.model.UserDataManager;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class SaveNewsTask extends AsyncTask<NewsItem, Void, Void> {

    @Override
    protected Void doInBackground(NewsItem... newsItems) {
        if (newsItems != null && newsItems.length > 0) {
            NewsItem newsItem = newsItems[0];
            // 检查数据库中是否已存在相同 NewsId 的记录
            boolean newsExists = Select.from(NewsItem.class)
                    .where(Condition.prop("news_id").eq(newsItem.getNewsId()))
                    .count() > 0;

            if (!newsExists) {
                // 如果数据库中不存在相同 NewsId 的记录，则存储新闻
                newsItem.save();
            }

            String newsId = newsItem.getNewsId();
            UserDataManager userDataManager = UserDataManager.getInstance();
            UserData userData = userDataManager.getUser();

            if (userData != null) {
                if (userDataManager.getUserReadNewsIds().contains(newsId)) {
                    userDataManager.getUserReadNewsIds().remove(newsId);
                }
                userDataManager.getUserReadNewsIds().add(newsId);
                userData.setUserReadNewsIds(userDataManager.getUserReadNewsIds());
                userData.save();
            }
        }
        return null;
    }
}
