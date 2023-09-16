package com.example.zhangxiangyu.model;

import java.util.List;

import com.example.zhangxiangyu.fragment.Comment;
import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;


@Table(name = "NewsItem")
public class NewsItem extends SugarRecord {
    @Column(name = "news_id", unique = true)
    private String newsId;

    @Column(name = "title")
    private String title;

    @Column(name = "category")
    private String category;

    @Column(name = "keywords")
    private List<String> keywords;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "date")
    private  String date;

    @Column(name = "imageUrl")
    public String image;

    @Column(name = "videoUrl")
    public String video;

    @Column(name = "content")
    private String content;

    @Column(name = "comments")
    private List<Comment> comments;


    public NewsItem() {}

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
        this.comments = null;
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

    public void setComments(List<Comment> comments) { this.comments = comments; }

}
