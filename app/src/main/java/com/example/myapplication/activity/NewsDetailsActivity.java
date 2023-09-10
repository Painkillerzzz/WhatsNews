package com.example.myapplication.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class NewsDetailsActivity extends AppCompatActivity{

    SimpleExoPlayer player;

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
            String videoUrl = intent.getStringExtra("news_video");
            boolean isLiked = intent.getBooleanExtra("news_state_liked", false);
            boolean isCommented = intent.getBooleanExtra("news_state_commented", false);

            TextView titleTextView = findViewById(R.id.text_title_news_details);
            TextView publisherTextView = findViewById(R.id.text_publisher_news_details);
            TextView dateTextView = findViewById(R.id.text_date_news_details);
            TextView contentTextView = findViewById(R.id.text_content_news_details);
            ImageView imageView = findViewById(R.id.image_news_details);
            PlayerView playerView = findViewById(R.id.video_news_details);

            titleTextView.setText(title);
            publisherTextView.setText(publisher);
            dateTextView.setText(date);
            contentTextView.setText(content);

            if (imageUrl.isEmpty()) {
                imageView.setVisibility(View.GONE);
            }
            else {
                System.out.println(imageUrl);
                Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.placeholder_image)
                        .fallback(R.drawable.error_image)
                        .error(R.drawable.error_image)
                        .into(imageView);
            }

            if (videoUrl.isEmpty()) {
                playerView.setVisibility(View.GONE);
            }
            else {
                player = new SimpleExoPlayer.Builder(this).build();

                MediaItem mediaItem = MediaItem.fromUri(videoUrl);

                player.setMediaItem(mediaItem);

                player.prepare();

                playerView.setPlayer(player);
            }
        }

        ImageView backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView likeButton = findViewById(R.id.like_btn);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeButton.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.icon_favorite).getConstantState()) {
                    likeButton.setImageDrawable(getResources().getDrawable(R.drawable.icon_favorite_border));
                    //TODO: add to database
                }
                else {
                    likeButton.setImageDrawable(getResources().getDrawable(R.drawable.icon_favorite));
                    //TODO: remove from database
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }

    }


}
