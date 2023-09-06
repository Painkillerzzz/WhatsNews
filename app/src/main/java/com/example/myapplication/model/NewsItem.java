package com.example.myapplication.model;

import java.util.List;

public class NewsItem {
    private String newsId;
    private String title;
    private String category;
    private List<String> keywords;
    private String publisher;
    private  String date;
    public String image;
    public String video;
    private String content;

    public NewsItem() {
        this.newsId = "ddsds";
        this.title = "家人们谁懂啊";
        this.category = "dsds";
        this.keywords = null;
        this.publisher = "下头男";
        this.date = "真的是无语死了";
        this.image = "dsds";
        this.video = "dsds";
        this.content = "dsds";
    }

    public NewsItem(String newsId, String title, String category, List<String> keywords, String publisher, String date, String video, String image, String content) {
        this.newsId = newsId;
        this.title = title;
        this.category = category;
        this.keywords = keywords;
        this.publisher = publisher;
        this.date = date;
        this.video = video;
        this.image = image;
        this.content = content;
    }

    public String getNewsID() { return newsId; }

    public String getTitle() { return title; }

    public String getCategory() { return category; }

    public List<String> getKeywords() { return keywords; }

    public String getPublisher() { return publisher; }

    public String getDate() { return date; }

    public String getImage() {
        return image;
    }

    public String getVideo() {
        return video;
    }

    public String getContent() { return content; }

}
