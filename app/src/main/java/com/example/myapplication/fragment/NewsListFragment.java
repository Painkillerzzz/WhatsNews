package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NewsRecyclerAdapter;
import com.example.myapplication.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class NewsListFragment extends Fragment {
    RecyclerView recyclerView;
    NewsRecyclerAdapter adapter;
    List<NewsItem> newsItemList =  new ArrayList<>();

    public NewsListFragment() {
        newsItemList.add(new NewsItem());
        newsItemList.add(new NewsItem());
        newsItemList.add(new NewsItem());
        newsItemList.add(new NewsItem());
        newsItemList.add(new NewsItem());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        setAdapter();
        return view;
    }

    public void setNewsItemList(List<NewsItem> newsItemList) {
        this.newsItemList = newsItemList;
        if (adapter != null) {
            adapter.setNewsItemList(newsItemList);
        }
    }

    private void setAdapter() {
        adapter = new NewsRecyclerAdapter(newsItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
    }
}