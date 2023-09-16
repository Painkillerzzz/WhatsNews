package com.example.zhangxiangyu.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.zhangxiangyu.R;
import com.example.zhangxiangyu.service.NewsApiClient;
import com.example.zhangxiangyu.adapter.NewsRecyclerAdapter;
import com.example.zhangxiangyu.model.NewsItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewsListFragment extends Fragment implements NewsApiClient.NewsApiCallback {
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    NewsRecyclerAdapter adapter;
    NewsApiClient newsApiClient;

    ProgressBar progressBar;
    boolean needUpdate = true;

    String startDate = "2020-01-01";
    String endDate = LocalDate.now().toString();
    String category = "";
    String keyword = "";

    private int requestPage = 1;
    View view;

    List<NewsItem> newsItemList =  new ArrayList<>();

    public NewsListFragment() {
    }

    public NewsListFragment(String keyword, String category, String startDate, String endDate) {
        this.keyword = keyword;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if  (needUpdate){
            view = inflater.inflate(R.layout.fragment_news_list, container, false);

            swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

            recyclerView = view.findViewById(R.id.recyclerView);
            progressBar = view.findViewById(R.id.progressBar);

            if  (adapter == null) {
                adapter = new NewsRecyclerAdapter(getContext(), newsItemList);
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                recyclerView.setAdapter(adapter);
            }

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    // 检查是否已经滚动到了列表底部
                    if (!recyclerView.canScrollVertically(1)) {
                        if (newsApiClient.getStatus() == AsyncTask.Status.FINISHED) {
                            progressBar.setVisibility(View.VISIBLE);
                            requestPage = requestPage + 1;
                            newsApiClient = new NewsApiClient(NewsListFragment.this, 15, requestPage, startDate, endDate, keyword, category);
                            newsApiClient.execute();
                        }
                    }
                }
            });


            newsApiClient = new NewsApiClient(NewsListFragment.this, 15, requestPage, startDate, endDate, keyword, category);

            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (newsApiClient.getStatus() == AsyncTask.Status.FINISHED){
                        requestPage = 1;
                        newsApiClient =  new NewsApiClient(NewsListFragment.this,  15, requestPage, startDate, endDate, keyword, category);
                        newsApiClient.execute();
                    }
                    else {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
            newsApiClient.execute();
            needUpdate = false;
        }
        return view;
    }

    @Override
    public void onNewsDataReceived(List<NewsItem> fetchedNewsItemList) {
        progressBar.setVisibility(View.INVISIBLE);
        if (fetchedNewsItemList != null) {
            if (newsItemList.isEmpty()) {
                // 如果当前列表为空，则说明是首次加载
                if (getContext() != null) {
//                    Toast.makeText(requireContext(), "News received", Toast.LENGTH_SHORT).show();
                }
                newsItemList = fetchedNewsItemList;
                adapter.setNewsItemList(this.newsItemList);
            } else {
                // 如果当前列表不为空，说明是下拉刷新
                 if (getContext() != null) {
//                     Toast.makeText(requireContext(), "News updated", Toast.LENGTH_SHORT).show();
                 }
                // 清空原有数据，然后添加新数据
                if (recyclerView.canScrollVertically(1)){
                    newsItemList.clear();
                }
                newsItemList.addAll(fetchedNewsItemList);
                swipeRefreshLayout.setRefreshing(false); // 停止刷新动画
            }
            adapter.notifyDataSetChanged(); // 更新 RecyclerView
        } else {
            if (getContext() != null) {
                Toast.makeText(requireContext(), "No news received", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onNewsDataError(String errorMessage) {
        // 处理错误
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false); // 停止刷新动画
    }

    public void updateNewsList(String newKeyword, String newStartDate, String newEndDate) {
        // 更新成员变量
        this.keyword = newKeyword;
        this.startDate = newStartDate;
        this.endDate = newEndDate;
        newsItemList.clear();

        // 重置分页信息
        requestPage = 1;
        newsApiClient = new NewsApiClient(this, 15, requestPage, startDate, endDate, keyword, category);
        newsApiClient.execute();

        // 清空原有数据
        adapter.notifyDataSetChanged();
    }

}