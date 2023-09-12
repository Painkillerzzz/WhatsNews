package com.example.myapplication.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NewsRecyclerAdapter;
import com.example.myapplication.model.NewsItem;
import com.example.myapplication.service.NewsApiClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CategoryFragment extends Fragment implements NewsApiClient.NewsApiCallback {
    private NewsApiClient newsApiClient;
    private EditText searchText;
    private Button startDateButton;
    private String startDate =  "2020-01-01";
    private DatePickerDialog startDatePickerDialog;
    private Button endDateButton;
    private String endDate =  "2021-09-02";
    private DatePickerDialog endDatePickerDialog;
    private RadioGroup categoryRadioGroup;
    private String category = "娱乐";
    RecyclerView recyclerView;
    NewsRecyclerAdapter  adapter;
    List<NewsItem> newsItemList =  new ArrayList<>();
    View root;
    private boolean needUpdate = true;

    public CategoryFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (needUpdate){
            root = inflater.inflate(R.layout.fragment_category, container, false);

            searchText = root.findViewById(R.id.searchText);
            recyclerView = root.findViewById(R.id.recyclerView);
            adapter = new NewsRecyclerAdapter(getContext(), newsItemList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(adapter);

            searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        newsApiClient = new NewsApiClient(CategoryFragment.this, 15, 1, startDate, endDate, searchText.getText().toString(), category);
                        newsApiClient.execute();
                        return true; // 返回 true 表示事件已被处理
                    }
                    return false; // 返回 false 表示事件未被处理
                }
            });

            startDateButton = root.findViewById(R.id.startDateButton);
            startDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startDatePickerDialog.show();
                }
            });
            endDateButton = root.findViewById(R.id.endDateButton);

            initDatePicker();

            endDateButton = root.findViewById(R.id.endDateButton);
            endDateButton.setText(getTodayDate());

            endDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    endDatePickerDialog.show();
                }
            });

            categoryRadioGroup = root.findViewById(R.id.categoryRadioGroup);
            categoryRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.allRadioButton) {
                        category = null;
                    } else if (checkedId == R.id.entertainmentRadioButton) {
                        category = "娱乐";
                    } else if (checkedId == R.id.militaryRadioButton) {
                        category = "军事";
                    } else if (checkedId == R.id.educationRadioButton) {
                        category = "教育";
                    } else if (checkedId == R.id.cultureRadioButton) {
                        category = "文化";
                    } else if (checkedId == R.id.healthRadioButton) {
                        category = "健康";
                    } else if (checkedId == R.id.financeRadioButton) {
                        category = "财经";
                    } else if (checkedId == R.id.sportsRadioButton) {
                        category = "体育";
                    } else if (checkedId == R.id.vihecleRadioButton) {
                        category = "汽车";
                    } else if (checkedId == R.id.technologyRadioButton) {
                        category = "科技";
                    } else if (checkedId == R.id.societyRadioButton) {
                        category = "社会";
                    }
                }
            });
            categoryRadioGroup.check(R.id.entertainmentRadioButton);

            initDatePicker();
            needUpdate = false;
        }
        return root;
    }

    protected void initDatePicker(){
        DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // 获取用户选择的日期
                month = month + 1;
                String date = monthString(month) + " " + dayOfMonth + " " + year;
                // 设置日期
                startDateButton.setText(date);
                startDate = year + "-" + month + "-" + dayOfMonth;
            }
        };

        DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // 获取用户选择的日期
                month = month + 1;
                String date = monthString(month) + " " + dayOfMonth + " " + year;
                // 设置日期
                endDateButton.setText(date);
                endDate = year + "-" + monthString(month) + "-" + dayOfMonth;
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

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
        if (month == 1)
            return "JAN";
        else if (month == 2)
            return "FEB";
        else if (month == 3)
            return "MAR";
        else if (month == 4)
            return "APR";
        else if (month == 5)
            return "MAY";
        else if (month == 6)
            return "JUN";
        else if (month == 7)
            return "JUL";
        else if (month == 8)
            return "AUG";
        else if (month == 9)
            return "SEP";
        else if (month == 10)
            return "OCT";
        else if (month == 11)
            return "NOV";
        else if (month == 12)
            return "DEC";
        else
            return "JAN";
    }

    protected void initRadioGroup(){
        categoryRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 在这里可以为视图控件设置初始值或处理其他逻辑
    }

    @Override
    public void onNewsDataReceived(List<NewsItem> fetchedNewsItemList) {
        // 在这里为视图控件设置初始值或处理其他逻辑
        adapter.setNewsItemList(fetchedNewsItemList);
        adapter.notifyDataSetChanged();
//        if (fetchedNewsItemList != null) {
//            if (newsItemList.isEmpty()) {
//                // 如果当前列表为空，则说明是首次加载
//                Toast.makeText(getContext(), "News received", Toast.LENGTH_SHORT).show();
//                newsItemList = fetchedNewsItemList;
//                adapter.setNewsItemList(this.newsItemList);
//            } else {
//                // 如果当前列表不为空，说明是下拉刷新
//                Toast.makeText(getContext(), "News updated", Toast.LENGTH_SHORT).show();
//                // 清空原有数据，然后添加新数据
//                if (recyclerView.canScrollVertically(1)){
//                    newsItemList.clear();
//                }
//                newsItemList.addAll(fetchedNewsItemList);
//                swipeRefreshLayout.setRefreshing(false); // 停止刷新动画
//            }
//            adapter.notifyDataSetChanged(); // 更新 RecyclerView
//        } else {
//            Toast.makeText(getContext(), "No news received", Toast.LENGTH_SHORT).show();
//        }
    }
}
