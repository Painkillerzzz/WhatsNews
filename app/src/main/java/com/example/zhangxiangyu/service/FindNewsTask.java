package com.example.zhangxiangyu.service;

import android.os.AsyncTask;
import com.example.zhangxiangyu.model.NewsItem;
import com.orm.query.Condition;
import com.orm.query.Select;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindNewsTask extends AsyncTask<List<String>, Void, List<NewsItem>> {
    private FindNewsTask.NewsDatabaseCallback callback;

    public FindNewsTask(FindNewsTask.NewsDatabaseCallback callback) {
        this.callback = callback;
    }

    @Override
    protected List<NewsItem> doInBackground(List<String>... params) {
        List<NewsItem> result = new ArrayList<>();

        if (params != null && params.length > 0) {
            List<String> newsIdsToFind = params[0];

            // 反转 newsIdsToFind 列表
            Collections.reverse(newsIdsToFind);

            // 遍历 newsIdsToFind 列表，查找新闻并添加到结果列表中
            for (String newsId : newsIdsToFind) {
                // 在数据库中查找匹配的新闻
                List<NewsItem> newsItems = Select.from(NewsItem.class)
                        .where(Condition.prop("news_id").eq(newsId))
                        .list();

                if (!newsItems.isEmpty()) {
                    // 如果找到匹配的新闻，则将其添加到结果列表中
                    result.add(newsItems.get(0));
                }
            }
        }

        return result;
    }


    @Override
    protected void onPostExecute(List<NewsItem> newsItemList) {
        super.onPostExecute(newsItemList);
        // 数据拉取完成后，通过回调函数通知Fragment
        if (callback != null) {
            callback.onNewsDataReceived(newsItemList);
        }
    }

    // 其他方法不变...

    public interface NewsDatabaseCallback {
        void onNewsDataReceived(List<NewsItem> newsItemList);
    }
}