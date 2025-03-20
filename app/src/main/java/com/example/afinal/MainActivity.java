package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText TextUser, TextPass;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextUser = findViewById(R.id.TextUser);
        TextPass = findViewById(R.id.TextPass);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (checkUser() && checkPass()) {
                    startActivity(new Intent(MainActivity.this, MenuActivity.class));
                    finish();
//                }
            }
        });
    }

    private boolean checkUser() {
        String userStr = TextUser.getText().toString();
        if (!TextUtils.isEmpty(userStr)) {
            if (userStr.toUpperCase().equals("ADMIN")) {
                return true;
            } else {
                TextUser.setError("ชื่อผู้ใช้ผิด");
                return false;
            }
        } else {
            TextUser.setError("กรุณาใส่ชื่อผู้ใช้");
            return false;
        }
    }

    private boolean checkPass() {
        String passStr = TextPass.getText().toString();
        if (!TextUtils.isEmpty(passStr)) {
            if (passStr.equals("123456")) {
                return true;
            } else {
                TextPass.setError("รหัสผ่านผิด");
                return false;
            }
        } else {
            TextPass.setError("กรุณาใส่รหัสผ่าน");
            return false;
        }
    }
}