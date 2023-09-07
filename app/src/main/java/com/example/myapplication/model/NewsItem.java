package com.example.myapplication.model;

import java.util.List;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

public class NewsItem extends SugarRecord {
    private String newsId;
    private String title;
    private String category;
    private List<String> keywords;
    private String publisher;
    private  String date;
    public String image;
    public String video;
    private String content;
    private boolean isRead;
    private boolean isLiked;
    private boolean isCommented;

    public NewsItem() {
        this.newsId = "0000000";
        this.title = "家人们谁懂啊";
        this.category = "科技";
        this.keywords = null;
        this.publisher = "下头男";
        this.date = "真的是无语死了";
        this.image = "dsds";
        this.video = "dsds";
        this.content = "假如如一间铁屋子，是绝无窗户而万难破毀的，里面有许多熟睡的人们，不久都要闷死了，然而是从昏睡入死灭，并不感到就死的悲哀。现在你大嚷起来，惊起了较为清醒的几个人，使这不幸的少数者来受无可挽救的临终的苦楚，你倒以为对得起他们么？";
        this.isRead = false;
        this.isLiked =  false;
        this.isCommented = false;
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

    public String getNewsId() { return newsId; }

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

    public boolean getStateRead() { return isRead; }

    public boolean getStateLiked() { return isLiked; }

    public boolean getStateCommented() { return isCommented; }

}
