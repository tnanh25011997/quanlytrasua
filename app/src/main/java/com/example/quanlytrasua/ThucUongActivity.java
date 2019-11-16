package com.example.quanlytrasua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlytrasua.CustomAdapter.AdapterHienThiThucUong;
import com.example.quanlytrasua.Model.BanDTO;
import com.example.quanlytrasua.Model.LoaiThucUong;
import com.example.quanlytrasua.Model.ThucUong;
import com.example.quanlytrasua.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThucUongActivity extends AppCompatActivity {
    ListView lvHienThiThucUong;
    ArrayList<ThucUong> listThucUong;
    ArrayAdapter<LoaiThucUong> dsLoaiThucUong;
    AdapterHienThiThucUong adapterHienThiThucUong;
    Toolbar toolbar;
    Spinner spinner;
    ImageButton btnBack;
    TextView goToBill;

    private  int idLoai;
    private String tenLoaiThucUong;

    private int id;
    private String tenThucUong;
    private long gia;
    private int maLoai;
    private String anh;
    private int count=0;
    private String tenLoai;
    private ArrayList<ThucUong> arrListThucUong;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuc_uong);
        AddControl();
        AddEvent();

    }

    private void AddEvent() {
        spinner = findViewById(R.id.spinner);
        dsLoaiThucUong = new ArrayAdapter<>(
                ThucUongActivity.this,
                android.R.layout.simple_spinner_item
        );
        dsLoaiThucUong.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(dsLoaiThucUong);
        getDuLieuLoaiThucUong();
        getDuLieuThucUong();
        listThucUong = new ArrayList<ThucUong>();
        adapterHienThiThucUong = new AdapterHienThiThucUong(this, R.layout.custom_layout_hienthithucuong, listThucUong);
        lvHienThiThucUong.setAdapter(adapterHienThiThucUong);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listThucUong.clear();
                getListThucUongByLoai(dsLoaiThucUong.getItem(i).getId()+"");
                adapterHienThiThucUong.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        goToBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ThucUong> arrayListBill = new ArrayList<>();
                for(ThucUong tu : listThucUong){
                    if(tu.getCount() >0){
                        arrayListBill.add(tu);
                    }
                }
                if(BillActivity.CHECK_START_MENU){
                    Intent resultInten = new Intent();
                    resultInten.putExtra("result",arrayListBill);
                    setResult(RESULT_OK,resultInten);
                    finish();
                }else{
                    Intent intent = new Intent(ThucUongActivity.this,BillActivity.class);
                    intent.putExtra("listBill",arrayListBill);
                    startActivity(intent);
                }


            }
        });

    }

    private void getDuLieuLoaiThucUong() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanLoaiThucUong, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response !=null){
                    for(int i=0; i<response.length(); i++){

                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idLoai = jsonObject.getInt("id");
                            tenLoaiThucUong = jsonObject.getString("tenLoai");
                            dsLoaiThucUong.addAll(new LoaiThucUong(idLoai,tenLoaiThucUong));
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
                            tenLoai = jsonObject.getString("tenLoai");
                            count = 0;
                            listThucUong.add(new ThucUong(id, tenThucUong,gia,maLoai,anh,count, tenLoai));

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
    private void getListThucUongByLoai(String idLoai){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanGetThucUongByLoai+idLoai, new Response.Listener<JSONArray>() {
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
                            tenLoai = jsonObject.getString("tenLoai");
                            count = 0;


                            listThucUong.add(new ThucUong(id, tenThucUong,gia,maLoai,anh,count, tenLoai));

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
        //spinner = findViewById(R.id.spinner);
        btnBack = findViewById(R.id.btnBackMenu);
        goToBill = findViewById(R.id.btnGoToBill);

    }

}