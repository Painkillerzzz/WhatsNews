package com.example.zhangxiangyu.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.zhangxiangyu.fragment.NewsListFragment;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentStateAdapter {

    private List<Fragment> FragmentList = new ArrayList<>();
    private List<String> FragmentTitleList = new ArrayList<>();
    private final boolean[] fragmentVisibility;
    private final String[] tabTitleArray = {"全部", "娱乐", "军事", "教育", "文化", "健康", "财经", "体育", "汽车", "科技", "社会"};
    private final String[] categoryIndexArray = {"", "娱乐", "军事", "教育", "文化", "健康", "财经", "体育", "汽车", "科技", "社会"};

    public MyPagerAdapter(FragmentActivity fragmentActivity, boolean[] fragmentVisibility, String keyword, String startDate, String endDate) {
        super(fragmentActivity);
        this.fragmentVisibility = fragmentVisibility;

        for (int i = 0; i < 11; i++) {
            if (fragmentVisibility[i]) {
                addFragment(new NewsListFragment(keyword, categoryIndexArray[i], startDate, endDate), tabTitleArray[i]);
            }
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return FragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return FragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        FragmentList.add(fragment);
        FragmentTitleList.add(title);
    }

    public void setFragmentList(List<Fragment> mFragmentList) {
        this.FragmentList = mFragmentList;
    }

    public String getTabTitle(int position) {
        // 获取指定位置的 TabLayout 标签
        return FragmentTitleList.get(position); 
    }
}
