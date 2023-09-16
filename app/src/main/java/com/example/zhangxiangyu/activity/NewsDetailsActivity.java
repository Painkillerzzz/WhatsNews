package com.example.zhangxiangyu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.zhangxiangyu.R;
import com.example.zhangxiangyu.model.UserData;
import com.example.zhangxiangyu.model.UserDataManager;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

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
    private boolean liked = false;

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
                Intent intent = new Intent(NewsDetailsActivity.this, NavigationActivity.class);
                startActivity(intent);
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
                liked = true;
            } else {
                likeButton.setImageResource(R.drawable.icon_favorite_border);
                liked = false;
            }
        }
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userData != null){
                    if (liked) {
                        likeButton.setImageResource(R.drawable.icon_favorite_border);
                        while (userDataManager.getUserLikedNewsIds().contains(newsId)) {
                            userDataManager.getUserLikedNewsIds().remove(newsId);
                        }
                    } else {
                        likeButton.setImageResource(R.drawable.icon_favorite);
                        userDataManager.getUserLikedNewsIds().add(newsId);
                    }
                    liked = !liked;
                    userData.setUserLikedNewsIds(userDataManager.getUserLikedNewsIds());
                    userData.save();
                } else {
                    Toast.makeText(NewsDetailsActivity.this, "Please login!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        shareButton = findViewById(R.id.share_btn);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewsDetailsActivity.this, "Sharing function unavailable! ", Toast.LENGTH_SHORT).show();
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this news");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, news.getNewsLink());
//                startActivity(Intent.createChooser(shareIntent, "Share using"));
            }
        });

        commentButton = findViewById(R.id.comment_btn);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewsDetailsActivity.this, "Commenting function unavailable! ", Toast.LENGTH_SHORT).show();
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
