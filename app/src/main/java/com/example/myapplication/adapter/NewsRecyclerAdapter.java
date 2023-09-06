package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;
import com.example.myapplication.activity.NewsDetailsActivity;
import com.example.myapplication.fragment.NewsListFragment;
import com.example.myapplication.model.NewsItem;

import org.w3c.dom.Text;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsOverviewViewHolder>{

    private List<NewsItem>  newsList;

    public NewsRecyclerAdapter(List<NewsItem> newsList) {
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, NewsDetailsActivity.class);

                intent.putExtra("news_title", news.getTitle());
                intent.putExtra("news_publisher", news.getPublisher());
                intent.putExtra("news_date", news.getDate());
                intent.putExtra("news_content", news.getContent());

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
        notifyDataSetChanged();
    }
}
