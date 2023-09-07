package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

public class NewsDetailsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        // 获取从 RecyclerView 传递的新闻数据
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("news_title");
            String publisher = intent.getStringExtra("news_publisher");
            String date = intent.getStringExtra("news_date");
            String content = intent.getStringExtra("news_content");
            String imageUrl = intent.getStringExtra("news_picture");
            boolean isLiked = intent.getBooleanExtra("news_state_liked", false);
            boolean isCommented = intent.getBooleanExtra("news_state_commented", false);

            // 在这里使用传递的数据来初始化 NewsDetailsActivity 的视图
            TextView titleTextView = findViewById(R.id.text_title_news_details);
            TextView publisherTextView = findViewById(R.id.text_publisher_news_details);
            TextView dateTextView = findViewById(R.id.text_date_news_details);
            TextView contentTextView = findViewById(R.id.text_content_news_details);
            ImageView imageView = findViewById(R.id.image_news_details);

            // 设置视图中的文本
            titleTextView.setText(title);
            publisherTextView.setText(publisher);
            dateTextView.setText(date);
            contentTextView.setText(content);

            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .fallback(R.drawable.error_image)
                    .error(R.drawable.error_image)
                    .into(imageView);
        }

        ImageView backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
