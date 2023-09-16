package com.example.zhangxiangyu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhangxiangyu.R;
import com.example.zhangxiangyu.adapter.CategorySelectionAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Arrays;

public class CategorySelectionFragment extends BottomSheetDialogFragment implements CategorySelectionAdapter.OnButtonClickListener {

    @Override
    public void onButtonClick(String title,  boolean wanted, int absolutePosition) {
        if (wanted) {
            selectedAdapter.delete(title);
            unselectedAdapter.add(title);
        } else {
            unselectedAdapter.delete(title);
            selectedAdapter.add(title);
        }
        currentSelection[absolutePosition] = !currentSelection[absolutePosition];
    }

    // 回调接口，用于通知选择结果
    public interface OnCategorySelectionListener {
        void onCategorySelection(boolean[] selectedCategories);
    }

    RecyclerView categorySelectedList;
    RecyclerView categoryUnselectedList;
    TextView selectedTitle;
    TextView unselectedTitle;
    CategorySelectionAdapter selectedAdapter;
    CategorySelectionAdapter unselectedAdapter;

    private boolean[] initialSelection; // 初始选中状态
    private boolean[] reversedCurrentSelection =  new boolean[11];
    private boolean[] currentSelection; // 当前选中状态
    private OnCategorySelectionListener selectionListener;

    public CategorySelectionFragment(boolean[] initialSelection) {
        this.initialSelection = initialSelection;
        for (int i = 0; i < initialSelection.length; i++){
            reversedCurrentSelection[i] = !initialSelection[i];
        }
        this.currentSelection = Arrays.copyOf(initialSelection, initialSelection.length);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 确保宿主 Fragment 实现了回调接口
        if (getParentFragment() instanceof OnCategorySelectionListener) {
            selectionListener = (OnCategorySelectionListener) getParentFragment();
        } else {
            throw new RuntimeException(getParentFragment().toString()
                    + " must implement OnCategorySelectionListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet1, container, false);

        selectedTitle = view.findViewById(R.id.selectedTitle);
        unselectedTitle = view.findViewById(R.id.unselectedTitle);
        categorySelectedList = view.findViewById(R.id.categorySelected);
        categorySelectedList.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        categoryUnselectedList = view.findViewById(R.id.categoryUnselected);
        categoryUnselectedList.setLayoutManager(new GridLayoutManager(requireContext(), 3));

        selectedAdapter = new CategorySelectionAdapter(currentSelection, true);
        selectedAdapter.setListener(this);
        unselectedAdapter = new CategorySelectionAdapter(reversedCurrentSelection, false);
        unselectedAdapter.setListener(this);

        categorySelectedList.setAdapter(selectedAdapter);

        categoryUnselectedList.setAdapter(unselectedAdapter);

        Button buttonCancel = view.findViewById(R.id.buttonCancel);
        Button buttonConfirm = view.findViewById(R.id.buttonConfirm);

        // 取消按钮点击事件
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 传回初始选中状态
                selectionListener.onCategorySelection(initialSelection);
                dismiss(); // 关闭底部表单
            }
        });

        // 确认按钮点击事件
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 传回当前选中状态
                selectionListener.onCategorySelection(currentSelection);
                dismiss(); // 关闭底部表单
            }
        });

        return view;
    }

}
