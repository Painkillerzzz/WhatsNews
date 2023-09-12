package com.example.myapplication.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activity.NavigationActivity;
import com.example.myapplication.adapter.NewsRecyclerAdapter;
import com.example.myapplication.model.NewsItem;
import com.example.myapplication.model.UserData;
import com.example.myapplication.model.UserDataManager;
import com.example.myapplication.service.FindNewsTask;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class ProfileLoggedInFragment extends Fragment implements FindNewsTask.NewsDatabaseCallback{

    private String userName;
    private Uri imageUri;

    TextView userNameTextView;
    ShapeableImageView profileImageView;
    UserDataManager userDataManager;
    Button likedButton;
    Button commentedButton;
    Button readButton;
    ImageView logoutButton;
    RecyclerView recyclerView;
    NewsRecyclerAdapter adapter;
    List<NewsItem> newsItemList =  new ArrayList<>();

    private FindNewsTask findNewsTask;

    public ProfileLoggedInFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDataManager = UserDataManager.getInstance();
        userName = userDataManager.getUserName();
        if  (userDataManager.getUserProfilePicture() != null) {
            imageUri = Uri.parse(userDataManager.getUserProfilePicture());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_logged_in, container, false);
        userNameTextView = view.findViewById(R.id.profile_name);
        userNameTextView.setText(userName);

        profileImageView = view.findViewById(R.id.imageButton);
        if (imageUri != null){
            Glide.with(this).load(imageUri)
                    .placeholder(R.drawable.icon_profile_picture)
                    .fallback(R.drawable.icon_profile_picture)
                    .error(R.drawable.icon_profile_picture)
                    .into(profileImageView);
        }
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在此处启动相册选择图片的操作
                setProfilePicture();
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new NewsRecyclerAdapter(getContext(), newsItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        findNewsTask = new FindNewsTask(this);

        likedButton = view.findViewById(R.id.like_history_button);
        likedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findNewsTask != null && findNewsTask.getStatus() == AsyncTask.Status.RUNNING) {
                    findNewsTask.cancel(true);
                }

                List<String> likedNewsIds = userDataManager.getUserLikedNewsIds();

                if (likedNewsIds != null && !likedNewsIds.isEmpty()) {
                    // 调用异步任务来查找用户阅读过的新闻
                    findNewsTask = new FindNewsTask(ProfileLoggedInFragment.this);
                    findNewsTask.execute(likedNewsIds);
                } else {
                    Toast.makeText(getContext(), "No liked news available.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        readButton = view.findViewById(R.id.read_history_button);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findNewsTask != null && findNewsTask.getStatus() == AsyncTask.Status.RUNNING) {
                    findNewsTask.cancel(true);
                }

                List<String> readNewsIds = userDataManager.getUserReadNewsIds();

                if (readNewsIds != null && !readNewsIds.isEmpty()) {
                    // 调用异步任务来查找用户阅读过的新闻
                    findNewsTask = new FindNewsTask(ProfileLoggedInFragment.this);
                    findNewsTask.execute(readNewsIds);
                } else {
                    Toast.makeText(getContext(), "No read news available.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDataManager.resetToGuest();
                ProfileFragment profileFragment = new ProfileFragment();
                getActivity().finish();
                startActivity(new Intent(getActivity(), NavigationActivity.class));
            }
        });

        return view;
    }

    public void setUserName(String userName) {
        userNameTextView.setText(userName);
    }

    private static final int PICK_IMAGE_REQUEST = 1;

    private void setProfilePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            // 用户已成功选择图片
            Uri selectedImageUri = data.getData();
            userDataManager.setUserProfilePicture(selectedImageUri.toString());
            UserData.updateProfilePicture(userName, selectedImageUri.toString());
            Glide.with(this).load(selectedImageUri).into(profileImageView);
        }
    }

    @Override
    public void onNewsDataReceived(List<NewsItem> newsItemList) {
        if (newsItemList != null) {
            adapter.setNewsItemList(newsItemList);
            adapter.notifyDataSetChanged();
        }
    }
}
