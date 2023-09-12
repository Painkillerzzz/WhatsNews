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
import com.example.myapplication.model.NewsItem;
import com.example.myapplication.model.UserData;
import com.example.myapplication.model.UserDataManager;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

public class NewsDetailsActivity extends AppCompatActivity{
    String newsId;

    SimpleExoPlayer player;
    TextView titleTextView;
    TextView publisherTextView;
    TextView dateTextView;
    TextView contentTextView;
    ImageView imageView;
    PlayerView playerView;

    ImageView likeButton;
    ImageView shareButton;
    ImageView commentButton;
    ImageView backButton;
    UserData userData;

    UserDataManager userDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        userDataManager = UserDataManager.getInstance();

        Intent intent = getIntent();
        if (intent != null) {
            newsId = intent.getStringExtra("news_id_not_null");
            String title = intent.getStringExtra("news_title");
            String publisher = intent.getStringExtra("news_publisher");
            String date = intent.getStringExtra("news_date");
            String content = intent.getStringExtra("news_content");
            String imageUrl = intent.getStringExtra("news_picture");
            String videoUrl = intent.getStringExtra("news_video");

            titleTextView = findViewById(R.id.text_title_news_details);
            publisherTextView = findViewById(R.id.text_publisher_news_details);
            dateTextView = findViewById(R.id.text_date_news_details);
            contentTextView = findViewById(R.id.text_content_news_details);
            imageView = findViewById(R.id.image_news_details);
            playerView = findViewById(R.id.video_news_details);

            titleTextView.setText(title);
            publisherTextView.setText(publisher);
            dateTextView.setText(date);
            contentTextView.setText(content);

            if (imageUrl.isEmpty()) {
                imageView.setVisibility(View.GONE);
            }
            else {
                Glide.with(this)
                        .load(imageUrl)
//                        .placeholder(R.drawable.placeholder_image)
//                        .fallback(R.drawable.error_image)
//                        .error(R.drawable.error_image)
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

        backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        likeButton = findViewById(R.id.like_btn);
        likeButton.setImageResource(R.drawable.icon_favorite_border);

        userDataManager = UserDataManager.getInstance();


        if (userDataManager.getUser() != null) {
            userData = userDataManager.getUser();
            if (userDataManager.getUserLikedNewsIds().contains(newsId)) {
                likeButton.setImageResource(R.drawable.icon_favorite);
            } else {
                likeButton.setImageResource(R.drawable.icon_favorite_border);
            }
        }
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeButton.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.icon_favorite).getConstantState()) {
                    likeButton.setImageDrawable(getResources().getDrawable(R.drawable.icon_favorite_border));
                    if (userData != null) {
                        while (userDataManager.getUserLikedNewsIds().contains(newsId)) {
                            userDataManager.getUserLikedNewsIds().remove(newsId);
                        }
                        userData.setUserLikedNewsIds(userDataManager.getUserLikedNewsIds());
                        userData.save();
                    }
                }
                else {
                    likeButton.setImageDrawable(getResources().getDrawable(R.drawable.icon_favorite));
                    if (userData != null) {
                        userDataManager.getUserLikedNewsIds().add(newsId);
                        userData.setUserLikedNewsIds(userDataManager.getUserLikedNewsIds());
                        userData.save();
                    }
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
