package com.example.quanlytrasua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlytrasua.CustomAdapter.AdapterHienThiThucUong;
import com.example.quanlytrasua.Model.BanDTO;
import com.example.quanlytrasua.Model.ThucUong;
import com.example.quanlytrasua.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThucUongActivity extends AppCompatActivity {
    ListView lvHienThiThucUong;
    ArrayList<ThucUong> listThucUong;
    AdapterHienThiThucUong adapterHienThiThucUong;
    Toolbar toolbar;
    Spinner spinner;
    ImageButton btnBack;
    TextView goToBill;

    private int id;
    private String tenThucUong;
    private long gia;
    private int maLoai;
    private String anh;
    private int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuc_uong);
//        Intent intent = getIntent();
//        test = findViewById(R.id.thucuong);
//        test.setText(intent.getStringExtra("table"));
        AddControl();
        AddEvent();

    }

    private void AddEvent() {
        listThucUong = new ArrayList<ThucUong>();

        adapterHienThiThucUong = new AdapterHienThiThucUong(this, R.layout.custom_layout_hienthithucuong, listThucUong);
        lvHienThiThucUong.setAdapter(adapterHienThiThucUong);
        getDuLieuThucUong();

    }

    private void getDuLieuThucUong() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanThucUong, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response !=null){
                    for(int i=0; i<response.length(); i++){

                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenThucUong = jsonObject.getString("tenThucUong");
                            gia = jsonObject.getLong("gia");
                            maLoai = jsonObject.getInt("maLoai");
                            anh = jsonObject.getString("anh");
                            count = 0;
                            listThucUong.add(new ThucUong(id, tenThucUong,gia,maLoai,anh,count));

                            adapterHienThiThucUong.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void AddControl() {
        lvHienThiThucUong = findViewById(R.id.lvMenu);
        toolbar = findViewById(R.id.toolbarMenu);
        spinner = findViewById(R.id.spinner);
        btnBack = findViewById(R.id.btnBackMenu);
        goToBill = findViewById(R.id.btnGoToBill);

    }
}
