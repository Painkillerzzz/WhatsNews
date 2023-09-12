package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.UserData;
import com.example.myapplication.model.UserDataManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private Button createAccountButton;
    private Button loginButton;
    private EditText usernameEditText;
    private EditText passwordEditText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createAccountButton = findViewById(R.id.createAccountButton);
        loginButton = findViewById(R.id.loginButton);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String usernameInput = usernameEditText.getText().toString();
                String passwordInput = passwordEditText.getText().toString();

                if (usernameInput.equals("") || passwordInput.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
                } else {
                    // 在数据库中查找匹配的用户
                    List<UserData> users = UserData.find(UserData.class, "user_name = ?", usernameInput);

                    if (users.size() > 0) {
                        UserData user = users.get(0);
                        String hashedPassword = hashPassword(passwordInput); // 对输入密码进行哈希

                        if (user.getHashedUserPassword().equals(hashedPassword)) {
                            // 用户名和密码匹配，成功登录
                            UserDataManager userDataManager = UserDataManager.getInstance();
                            userDataManager.setUserName(usernameInput);
                            userDataManager.setUserProfilePicture(user.getUserProfilePicture());
                            userDataManager.setUserReadNewsIds(user.getUserReadNewsIds());
                            userDataManager.setUserLikedNewsIds(user.getUserLikedNewsIds());
                            userDataManager.setUserCommentedNewsIds(user.getUserCommentedNewsIds());

                            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                            intent.putExtra("loginState", true);
                            startActivity(intent);
                            finish();
                            Toast.makeText(LoginActivity.this, "Logged in as " + usernameInput, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());

            // 将哈希字节数组转换为十六进制字符串
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexStringBuilder.append('0');
                }
                hexStringBuilder.append(hex);
            }

            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // 处理异常
            return null;
        }
    }
}
