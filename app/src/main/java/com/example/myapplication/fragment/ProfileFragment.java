package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.activity.LoginActivity;
import com.example.myapplication.activity.NavigationActivity;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    public interface OnButtonClickListener {
        void onButtonClick();
    }

    private Button button;

    private OnButtonClickListener buttonClickListener;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(); // 触发按钮点击事件
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    private void onButtonClicked() {
        if (buttonClickListener != null) {
            buttonClickListener.onButtonClick();
        }
    }

}
