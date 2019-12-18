package com.example.quanlytrasua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlytrasua.Model.BanDTO;
import com.example.quanlytrasua.Model.HoaDon2;
import com.example.quanlytrasua.Model.LoaiThucUong;
import com.example.quanlytrasua.Model.ThucUong;
import com.example.quanlytrasua.Model.User;
import com.example.quanlytrasua.Model.chitiethoadon;
import com.example.quanlytrasua.ultil.Server;
import com.example.quanlytrasua.ultil.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.example.quanlytrasua.ultil.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class LoginActivity extends AppCompatActivity {

    private EditText txtTenDangNhap,txtMatKhau;
    private Button btnLogin;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //mDatabase.child("chitiethoadon").push().setValue(new chitiethoadon("1","-Lul8nHmGAHEmN-NrKqU","1",2));
        mAuth = FirebaseAuth.getInstance();

        sessionManager = new SessionManager(this);
        txtTenDangNhap = findViewById(R.id.txtTenDangNhap);
        txtMatKhau = findViewById(R.id.txtMatKhau);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenDangNhap = txtTenDangNhap.getText().toString();
                String matKhau = txtMatKhau.getText().toString();
                if(!tenDangNhap.isEmpty()&& !matKhau.isEmpty()){
                    Login(tenDangNhap, matKhau);
                }
                else{
                    txtTenDangNhap.setError("Vui lòng nhập tên đăng nhập");
                    txtMatKhau.setError("Vui lòng nhập mật khẩu");
                }

            }
        });
    }

    private void Login(final String tenDangNhap, final String matKhau) {


        mAuth.signInWithEmailAndPassword(tenDangNhap, matKhau)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sessionManager.CreateSession(tenDangNhap);
                            Intent intent = new Intent(LoginActivity.this, DanhSachBanActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng Nhập Thất Bại.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
