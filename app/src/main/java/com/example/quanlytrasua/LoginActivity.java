package com.example.quanlytrasua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.quanlytrasua.ultil.Server;
import com.example.quanlytrasua.ultil.SessionManager;
//import com.example.quanlytrasua.ultil.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

//import io.socket.client.IO;
//import io.socket.client.Socket;
//import io.socket.emitter.Emitter;

public class LoginActivity extends AppCompatActivity {

    private EditText txtTenDangNhap,txtMatKhau;
    private Button btnLogin;

    SessionManager sessionManager;
    //Socket mSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


//        try {
//            mSocket = IO.socket(Server.PORT);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
        //initSocket();
        //mSocket.on("SERVER_SEND_RESULT", onRetrieveLoginData);

        sessionManager = new SessionManager(this);
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
                    //mSocket.emit("CLIENT_SEND_REQUEST_LOGIN", tenDangNhap+","+matKhau);
                }
                else{
                    txtTenDangNhap.setError("Vui lòng nhập tên đăng nhập");
                    txtMatKhau.setError("Vui lòng nhập mật khẩu");
                }

            }
        });
    }
//    private Emitter.Listener onRetrieveLoginData = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    JSONArray data = (JSONArray) args[0];
//                    if (data.length() == 0){
//                        Toast.makeText(LoginActivity.this,"Sai tài khoản mật khẩu", Toast.LENGTH_LONG).show();
//                    }
//                    else{
//                        Intent intent = new Intent(LoginActivity.this, DanhSachBanActivity.class);
//                        //Toast.makeText(LoginActivity.this,data.length()+"", Toast.LENGTH_LONG).show();
//                        startActivity(intent);
//                        finish();
//
//                    }
//                }
//            });
//        }
//    };
//    private void initSocket() {
//        mSocket.connect();
//    }

    private void Login(final String tenDangNhap, final String matKhau) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = (String) jsonObject.get("success");
                            JSONArray jsonArray =  jsonObject.getJSONArray("data");

                            if(success.equals("1")){
                                for(int i = 0; i< jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String name = object.getString("fullname");
                                    String tenDangNhap = object.getString("username");

                                    sessionManager.CreateSession(name, tenDangNhap);
                                    Intent intent = new Intent(LoginActivity.this, DanhSachBanActivity.class);
                                    intent.putExtra("fullname",name);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                            else {
                                Toast.makeText(LoginActivity.this,"Sai tài khoản mật khẩu", Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this,"Sai Tên Tài Khoản Hoặc Mật Khẩu", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,"ERROR2", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("TenDangNhap", tenDangNhap);
                params.put("MatKhau", matKhau);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
