package com.example.myapplication.service;

import com.example.myapplication.model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

public class NewsApiClient extends AsyncTask<Void, Void, List<NewsItem>> {

    private NewsApiCallback callback;

    private int size;
    private int page;
    private String startDate = "2020-08-20";
    private String endDate =  "2021-09-02";
    private String words = "八部门";
    private String categories;

    public NewsApiClient(NewsApiCallback callback) {
        this.callback = callback;
    }

    public NewsApiClient(NewsApiCallback callback, int size, int page) {
        this.callback = callback;
        this.size = size;
        this.page = page;
    }

    public NewsApiClient(NewsApiCallback callback, int size, int page, String startDate, String endDate, String words, String categories) {
        this.callback = callback;
        this.size = size;
        this.page = page;
        this.startDate = startDate;
        this.endDate = endDate;
        this.words = words;
        this.categories = categories;
    }

    @Override
    protected List<NewsItem> doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api2.newsminer.net/svc/news/queryNewsList?";

        if (size > 0) {
            url += "size=" + size;
        } else {
            url += "size=15&page=1";
        }
        if (page > 0) {
            url += "&page=" + page;
        }
        if (startDate != null) {
            url += "&startDate=" + startDate;
        }
        if (endDate != null) {
            url += "&endDate=" + endDate;
        }
        if (words != null) {
            url += "&words=" + words;
        }
        if (categories != null) {
            url += "&categories=" + categories;
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response);
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                // 解析JSON数据并返回新闻项列表
                return parseNews(jsonData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            throw new RuntimeException();
        }

        return null;
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

    public interface NewsApiCallback {
        void onNewsDataReceived(List<NewsItem> newsItemList);
    }

    private List<NewsItem> parseNews(String jsonData) {
        List<NewsItem> newsItemList = new ArrayList<>();

        try {
            JSONObject jsonNews = new JSONObject(jsonData);
            JSONArray jsonNewsArray = jsonNews.getJSONArray("data");

            for (int i = 0; i < jsonNewsArray.length(); i++) {
                JSONObject jsonNewsItem = jsonNewsArray.getJSONObject(i);

                String newsID = jsonNewsItem.getString("newsID");
                String title = jsonNewsItem.getString("title");
                String publisher = jsonNewsItem.getString("publisher");
                String date = jsonNewsItem.getString("publishTime");
                String video = parseHttpUrl(jsonNewsItem.getString("video"));
                String image = parseHttpUrl(parseImageUrl(jsonNewsItem.getString("image")));
                String category = jsonNewsItem.getString("category");
                String content = jsonNewsItem.getString("content");

                JSONArray keywordsArray = jsonNewsItem.getJSONArray("keywords");
                List<String> keywordsList = new ArrayList<>();

                for (int j = 0; j < keywordsArray.length(); j++) {
                    JSONObject keywordObject = keywordsArray.getJSONObject(j);
                    String word = keywordObject.getString("word");
                    keywordsList.add(word);
                }

                NewsItem newsItem = new NewsItem(newsID, title, category, keywordsList, publisher, date, video, image, content);
                newsItemList.add(newsItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsItemList;
    }

    private String parseHttpUrl(@NonNull String inputUrl) {
        String httpsUrl = inputUrl.replaceAll("http://", "https://");

        if (inputUrl.startsWith("https://")) {
            httpsUrl = inputUrl;
        }

        return httpsUrl;
    }

    private  String parseImageUrl(String inputString) {
        String regex = "\\[([^\\]]+)\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputString);

        ArrayList<String> urls = new ArrayList<>();

        // 查找匹配的 URL
        while (matcher.find()) {
            String urlGroup = matcher.group(1); // 获取包含在方括号内的内容
            String[] urlArray = urlGroup.split(","); // 使用逗号分割 URL

            // 去除空格并添加到结果列表
            for (String url : urlArray) {
                urls.add(url.trim());
            }
        }
        if  (!urls.isEmpty()) {
            return urls.get(0);
        }
        return "";
    }

}
