package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class ProfileLoggedInFragment extends Fragment {

    private String userName;

    TextView userNameTextView;

    public ProfileLoggedInFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if  (getArguments() != null) {
            userName = getArguments().getString("userName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_logged_in, container, false);
        userNameTextView = view.findViewById(R.id.profile_name);
        userNameTextView.setText(userName);
        return view;
    }

    public void setUserNameTextView(String userName) {
        userNameTextView.setText(userName);
    }
}
