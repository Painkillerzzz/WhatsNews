package com.example.zhangxiangyu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhangxiangyu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategorySelectionAdapter extends RecyclerView.Adapter<CategorySelectionAdapter.ButtonViewHolder> {
    private boolean wanted;
    private boolean[] visible; // 存储按钮是否被选中的数组
    private final String[] buttonTexts = {"全部", "娱乐", "军事", "教育", "文化", "健康", "财经", "体育", "汽车", "科技", "社会"}; // 存储按钮文本的数组
    private List<String> categoryList =  new ArrayList<>();

    private OnButtonClickListener listener;
    Map<String, Integer> categoryMap;

    // 构造函数，传入按钮文本数组
    public CategorySelectionAdapter(boolean[] visible, boolean wanted) {
        this.visible = visible;
        this.wanted = wanted;
        for (int i = 0; i < buttonTexts.length; i++) {
            if  (visible[i]) {
                categoryList.add(buttonTexts[i]);
            }
        }

        categoryMap = new HashMap<>();
        categoryMap.put("全部", 0);
        categoryMap.put("娱乐", 1);
        categoryMap.put("军事", 2);
        categoryMap.put("教育", 3);
        categoryMap.put("文化", 4);
        categoryMap.put("健康", 5);
        categoryMap.put("财经", 6);
        categoryMap.put("体育", 7);
        categoryMap.put("汽车", 8);
        categoryMap.put("科技", 9);
        categoryMap.put("社会", 10);
    }

    // 创建 ViewHolder
    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_button_item, parent, false);
        return new ButtonViewHolder(view);
    }

    // 绑定数据到 ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        String buttonText = categoryList.get(position);
        holder.button.setText(buttonText);
    }

    public void setListener(OnButtonClickListener listener) {
        this.listener = listener;
    }

    // 获取按钮的数量
    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void add(String title){
        int index = 0;
        for(String category : categoryList){
            if(categoryMap.get(title) < categoryMap.get(category)){
                categoryList.add(index, title);
                notifyItemInserted(index);
                return;
            }
            index++;
        }
        categoryList.add(title);
        notifyItemInserted(index);
    }

    public void delete(String title){
        int index = 0;
        for(String category : categoryList){
            if(category.equals(title)){
                categoryList.remove(title);
                notifyItemRemoved(index);
                return;
            }
            index++;
        }
    }

    // 自定义 ViewHolder 类
    public class ButtonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public Button button;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button); // 这里的 R.id.button 是按钮视图的 ID
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null){
                listener.onButtonClick(button.getText().toString(), wanted, categoryMap.get(button.getText().toString()));
            }
        }
    }

    public interface OnButtonClickListener {
        void onButtonClick(String title, boolean wanted, int absolutePosition);
    }
}
