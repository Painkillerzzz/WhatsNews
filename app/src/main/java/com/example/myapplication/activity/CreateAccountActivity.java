package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.UserData;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText passwordTwiceEditText;
    private Button confirmButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // 初始化视图组件
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordTwiceEditText = findViewById(R.id.passwordTwiceEditText);
        confirmButton = findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入的用户名和密码
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String passwordTwice = passwordTwiceEditText.getText().toString();

                if (password.equals(passwordTwice)) {
                    if (username.length() > 0 && password.length() > 0){
                        // 密码输入一致，创建用户数据并保存到数据库
                        if  (UserData.find(UserData.class, "USER_NAME = ?", username).isEmpty()){
                            UserData userData = new UserData(username, password);
                            userData.save();

                            startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            usernameEditText.setError("Username already exists");
                        }
                    } else if (username.length() == 0){
                        usernameEditText.setError("Username cannot be empty");
                    } else {
                        passwordEditText.setError("Password cannot be empty");
                    }
                } else {
                    // 密码输入不一致，显示错误消息
                    passwordTwiceEditText.setError("Passwords do not match");
                }
            }
        });
    }
}
