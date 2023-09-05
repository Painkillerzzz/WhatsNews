package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.fragment.CategoryFragment;
import com.example.myapplication.fragment.NewsListFragment;
import com.example.myapplication.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NavigationActivity extends AppCompatActivity implements ProfileFragment.OnButtonClickListener{

    private Button button;
    private int selectedItem = R.id.menu_newest;

    BottomNavigationView bottomNavigationView;
    NewsListFragment newsListFragment;
    CategoryFragment categoryFragment;
    ProfileFragment profileFragment;

    @Override  
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        newsListFragment = new NewsListFragment();
        categoryFragment = new CategoryFragment();
        profileFragment = new ProfileFragment();
        profileFragment.setOnButtonClickListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_newest) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, newsListFragment).commit();
                } else if (item.getItemId() == R.id.menu_category) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, categoryFragment).commit();
                } else if (item.getItemId() == R.id.menu_profile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, profileFragment).commit();
                }
                selectedItem = item.getItemId();
                return true;
            }
        });

        if (savedInstanceState != null) {
            selectedItem = savedInstanceState.getInt("selectedItem");
        }
        bottomNavigationView.setSelectedItemId(selectedItem);
    }

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putInt("selectedItem", selectedItem);
//        super.onSaveInstanceState(outState);
//    }

    @Override
    public void onButtonClick() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
