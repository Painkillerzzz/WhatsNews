package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activity.NewsDetailsActivity;
import com.example.myapplication.model.NewsItem;
import com.example.myapplication.service.SaveNewsTask;

import org.w3c.dom.Text;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsOverviewViewHolder>{
    private Context context;
    private List<NewsItem>  newsList;

    public NewsRecyclerAdapter(Context context, List<NewsItem> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsOverviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_ov, parent, false);
        return new NewsOverviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsOverviewViewHolder holder, int position) {
        NewsItem news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.publisher.setText(news.getPublisher());
        holder.date.setText(news.getDate());

        Glide.with(context)
                .load(news.getImage())
                .placeholder(R.drawable.placeholder_image)
                .fallback(R.drawable.error_image)
                .error(R.drawable.error_image)
                .into(holder.picture);

        if (news.getImage().isEmpty()) {
            holder.picture.setVisibility(View.GONE);
        }
        else {
            Glide.with(context)
                    .load(news.getImage())
                    .placeholder(R.drawable.placeholder_image)
                    .fallback(R.drawable.error_image)
                    .error(R.drawable.error_image)
                    .into(holder.picture);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.title.setTextColor(ContextCompat.getColor(view.getContext(), R.color.gray));
                holder.publisher.setTextColor(ContextCompat.getColor(view.getContext(), R.color.gray));
                holder.date.setTextColor(ContextCompat.getColor(view.getContext(), R.color.gray));

                Context context = view.getContext();
                Intent intent = new Intent(context, NewsDetailsActivity.class);

                intent.putExtra("news_title", news.getTitle());
                intent.putExtra("news_publisher", news.getPublisher());
                intent.putExtra("news_date", news.getDate());
                intent.putExtra("news_content", news.getContent());
                intent.putExtra("news_picture", news.getImage());
                intent.putExtra("news_video", news.getVideo());
                intent.putExtra("news_id", news.getId());
                intent.putExtra("news_state_liked", news.getStateLiked());
                intent.putExtra("news_state_comment", news.getStateCommented());

                SaveNewsTask saveNewsTask = new SaveNewsTask();
                saveNewsTask.execute(news);

                context.startActivity(intent);
            }
        });
    }

    class NewsOverviewViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView publisher;
        TextView date;
        ImageView picture;

        public NewsOverviewViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title_news_ov);
            publisher = itemView.findViewById(R.id.text_publisher_news_ov);
            date = itemView.findViewById(R.id.text_date_news_ov);
            picture = itemView.findViewById(R.id.ic_news_ov);
        }
    }
    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setNewsItemList(List<NewsItem> newsList) {
        this.newsList = newsList;
    }
}
