package com.example.hd_api;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button btnlogin,btnproduct;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnlogin = findViewById(R.id.btngologin);
        btnproduct = findViewById(R.id.btngogetallproduct);
        btnlogin.setOnClickListener(click -> {
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
        });
        btnproduct.setOnClickListener(click-> {

            //Lấy token khi đăng nhập thành công

            SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
            String accessToken = preferences.getString("access_token", null);
            //
            if (accessToken != null && !accessToken.isEmpty()) {
                Intent intent = new Intent(this,GetAllProduct.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Phải đăng nhập trước", Toast.LENGTH_SHORT).show();
            }
        });



    }

}