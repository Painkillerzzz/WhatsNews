package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

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
                // TODO: Implement this method

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String usernameInput = usernameEditText.getText().toString();
                String passwordInput = passwordEditText.getText().toString();
                if  (usernameInput.equals("") || passwordInput.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
                } else if  (usernameInput.equals("admin") && passwordInput.equals("admin")) {
                    Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                    intent.putExtra("loginState", true);
                    intent.putExtra("userName", usernameInput);
                    startActivity(intent);
                    finish();
                    Toast.makeText(LoginActivity.this, "Logged in as " + usernameInput, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
