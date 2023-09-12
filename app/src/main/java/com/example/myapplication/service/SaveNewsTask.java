package com.example.myapplication.service;

import android.os.AsyncTask;
import com.example.myapplication.model.NewsItem;
import com.example.myapplication.model.UserData;
import com.example.myapplication.model.UserDataManager;
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
            String userName = userDataManager.getUserName();

            List<UserData> users = UserData.find(UserData.class, "USER_NAME = ?", userName);

            if (!users.isEmpty()) {
                UserData userData = users.get(0);
                if (!userDataManager.getUserReadNewsIds().contains(newsId)) {
                    userDataManager.getUserReadNewsIds().add(newsId);
                    userData.setUserReadNewsIds(userDataManager.getUserReadNewsIds());
                    userData.save();
                }
            }
        }
        return null;
    }
}
