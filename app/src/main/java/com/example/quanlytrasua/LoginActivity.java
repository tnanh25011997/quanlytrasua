package com.example.quanlytrasua;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText txtTenDangNhap,txtMatKhau;
    private Button btnLogin;
    private static String URL_LOGIN="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtTenDangNhap = findViewById(R.id.txtTenDangNhap);
        txtMatKhau = findViewById(R.id.txtMatKhau);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenDangNhap = txtTenDangNhap.getText().toString();
                String matKhau = txtMatKhau.getText().toString();
                if(!tenDangNhap.isEmpty()|| !matKhau.isEmpty()){
                    Login(tenDangNhap, matKhau);
                }
                else{
                    txtTenDangNhap.setError("Vui lòng nhập tên đăng nhập");
                    txtMatKhau.setError("Vui lòng nhập mật khẩu");
                }
            }
        });
    }

    private void Login(String tenDangNhap, String matKhau) {



    }
}
