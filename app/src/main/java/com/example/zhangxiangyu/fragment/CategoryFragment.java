package com.example.zhangxiangyu.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.zhangxiangyu.R;
import com.example.zhangxiangyu.adapter.MyPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CategoryFragment extends Fragment implements CategorySelectionFragment.OnCategorySelectionListener{
    private EditText searchText;
    private Button startDateButton;
    private String startDate;
    private String startDateFormatted;
    private DatePickerDialog startDatePickerDialog;
    private Button endDateButton;
    private String endDate;
    private String endDateFormatted;
    private DatePickerDialog endDatePickerDialog;
    private Button searchButton;
    private ImageView selectCategoryButton;
    private boolean[] categorySelected;
    private String keyword;
    private boolean needUpdate = true;

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    MyPagerAdapter pagerAdapter;
    List<Fragment> fragmentList= new ArrayList<>();

    public CategoryFragment() {
        startDate = "2019-01-01";
        endDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
        categorySelected = new boolean[11];
        keyword = "";
        Arrays.fill(categorySelected, true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);

        viewPager2 = view.findViewById(R.id.view_pager);
        viewPager2.setOffscreenPageLimit(3);
        tabLayout = view.findViewById(R.id.tab_layout);

        pagerAdapter = new MyPagerAdapter(getActivity(), categorySelected, keyword, startDate, endDate);
        viewPager2.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    tab.setText(pagerAdapter.getTabTitle(position));
                }).attach();

        searchText = view.findViewById(R.id.searchText);

        startDateButton = view.findViewById(R.id.startDateButton);
        startDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startDatePickerDialog.show();
                }
            });

        endDateButton = view.findViewById(R.id.endDateButton);
        endDateButton.setText(getTodayDate());

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDatePickerDialog.show();
            }
        });

        if (needUpdate) {
            initDatePicker();
            needUpdate = false;
        } else {
            startDateButton.setText(startDateFormatted);
            endDateButton.setText(endDateFormatted);
        }

        searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = searchText.getText().toString();
                pagerAdapter = new MyPagerAdapter(getActivity(), categorySelected, keyword, startDate, endDate);
                viewPager2.setAdapter(pagerAdapter);
            }
        });

        selectCategoryButton =  view.findViewById(R.id.icon_select);
        selectCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorySelectionFragment categorySelectionFragment = new CategorySelectionFragment(categorySelected);
                categorySelectionFragment.show(getChildFragmentManager(), "categorySelection");
            }
        });

        return view;
    }

    protected void initDatePicker(){
        DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // 获取用户选择的日期
                month = month + 1;
                startDateFormatted = monthString(month) + " " + String.format("%02d", dayOfMonth) + " " + year;
                // 设置日期
                startDateButton.setText(startDateFormatted);
                startDate = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", dayOfMonth);
            }
        };

        DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // 获取用户选择的日期
                month = month + 1;
                endDateFormatted = monthString(month) + " " + String.format("%02d", dayOfMonth) + " " + year;
                // 设置日期
                endDateButton.setText(endDateFormatted);
                endDate = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", dayOfMonth);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        startDateFormatted = monthString(1) + " " + 1 + " " + 2019;
        endDateFormatted = monthString(month + 1) + " " + day + " " + year;

        int style = AlertDialog.THEME_HOLO_LIGHT;
        startDatePickerDialog = new DatePickerDialog(requireContext(), style, startDateSetListener, 2019, 0, 1);
        endDatePickerDialog = new DatePickerDialog(requireContext(), style, endDateSetListener, year, month, day);
    }

    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return monthString(month + 1) + " " + day + " " + year;
    }

    private String monthString(int month) {
        String[] monthStrings = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        if (month >= 1 && month <= 12) {
            return monthStrings[month - 1];
        } else {
            return "JAN";
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 在这里可以为视图控件设置初始值或处理其他逻辑
    }

    @Override
    public void onCategorySelection(boolean[] selectedCategories) {
        categorySelected = selectedCategories;
        pagerAdapter = new MyPagerAdapter(getActivity(), categorySelected, keyword, startDate, endDate);
        viewPager2.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    tab.setText(pagerAdapter.getTabTitle(position));
                }).attach();
    }
}
