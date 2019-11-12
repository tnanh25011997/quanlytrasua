package com.example.quanlytrasua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HoaDonActivity extends AppCompatActivity {

    TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
//        Intent intent = getIntent();
//        test = findViewById(R.id.hoadon);
//        test.setText(intent.getStringExtra("table"));
    }
}
