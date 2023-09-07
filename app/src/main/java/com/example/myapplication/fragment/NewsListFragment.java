package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.service.NewsApiClient;
import com.example.myapplication.adapter.NewsRecyclerAdapter;
import com.example.myapplication.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class NewsListFragment extends Fragment implements NewsApiClient.NewsApiCallback{
    RecyclerView recyclerView;
    NewsRecyclerAdapter adapter;
    NewsApiClient newsApiClient;
    boolean needUpdate = true;
    View view;

    List<NewsItem> newsItemList =  new ArrayList<>();

    public NewsListFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if  (needUpdate){
            view = inflater.inflate(R.layout.fragment_news_list, container, false);
            recyclerView = view.findViewById(R.id.recyclerView);
            adapter = new NewsRecyclerAdapter(getContext(), newsItemList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(adapter);

            newsApiClient = new NewsApiClient(this);
            newsApiClient.execute();
            needUpdate = false;
        }
        return view;
    }

    @Override
    public void onNewsDataReceived(List<NewsItem> newsItemList) {
        if  (newsItemList != null) {
            Toast.makeText(getContext(), "News received", Toast.LENGTH_SHORT).show();
            this.newsItemList = newsItemList;
            adapter.setNewsItemList(this.newsItemList);
            adapter.notifyDataSetChanged();
        }
    }

    public void onNewsDataError(String errorMessage) {
        //TODO: handle error
    }

}