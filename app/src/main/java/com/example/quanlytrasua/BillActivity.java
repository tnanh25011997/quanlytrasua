package com.example.quanlytrasua;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlytrasua.CustomAdapter.AdapterHienThiBill;
import com.example.quanlytrasua.Model.LoaiThucUong;
import com.example.quanlytrasua.Model.ThucUong;
import com.example.quanlytrasua.ultil.Server;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillActivity extends AppCompatActivity {
    private WebSocketClient mWebSocketClient;
    AdapterHienThiBill adapterHienThiBill;
    public static boolean CHECK_START_MENU = false;
    public static int CODE_CHECK = 1;
    String maban;
    String maHoaDon;
    String ngayTao;
    long thanhtien;
    long tong =0;
    private ArrayList<ThucUong> arrayListBill;
    @BindView(R.id.lvItemBill)
    ListView lvBill;
    @BindView(R.id.tvTableBill)
    TextView tvBan;
    @BindView(R.id.tvTimeBill)
    TextView tvThoiGian;
    @BindView(R.id.btnAddItem)
    ImageView imvAdd;
    @BindView(R.id.btnPushBill)
    ImageView imvRefesh;
    @BindView(R.id.btnPrintBill)
    ImageView imvSave;
    @BindView(R.id.tvTotalBill)
    TextView txtTotal;
    @BindView(R.id.btnCheckout)
    ImageView imvCheckout;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODE_CHECK){
            if(resultCode == RESULT_OK){
                ArrayList<ThucUong> arr = new ArrayList<>();
                arr = (ArrayList<ThucUong>) data.getSerializableExtra("result");
                int len = arr.size();
                int kt=0;
                int len2 = arrayListBill.size();
                if(arrayListBill.size()==0){
                    for(int i=0;i<len;i++){
                        arrayListBill.add(arr.get(i));
                    }
                }else{
                    for(int i=0;i<len;i++){

                        for(int j=0;j<len2;j++){
                            if(arr.get(i).getId()==arrayListBill.get(j).getId()){
                                kt=1;
                                arrayListBill.get(j).setCount(arr.get(i).getCount()+arrayListBill.get(j).getCount());
                                break;
                            }
                        }
                        if(kt==0){
                            arrayListBill.add(arr.get(i));
                        }
                        kt=0;
                    }
                }
                //Toast.makeText(BillActivity.this,arr.size()+" tuan",Toast.LENGTH_SHORT).show();
                adapterHienThiBill.notifyDataSetChanged();
            }
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        maban = intent.getStringExtra("table");
        if(CHECK_START_MENU == true){
            System.out.println("============> Da co");
            getDataBanDaCo();
            eventButtonBill();
            CHECK_START_MENU = false;

        }
        else{
            System.out.println("==============> chua co");
            addEvent();
            getData();
        }

    }

    private void getData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CHECK_START_MENU = false;
    }

    private void addEvent() {
        Intent intent = getIntent();

        tvBan.setText("Bàn số "+intent.getStringExtra("table"));
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyy");
        Date date = new Date();
        tvThoiGian.setText(formatter.format(date));
        arrayListBill = new ArrayList<>();
        arrayListBill = (ArrayList<ThucUong>) intent.getSerializableExtra("listBill");

        adapterHienThiBill = new AdapterHienThiBill(this,R.layout.activity_custom_hien_thi_bill,arrayListBill);
        lvBill.setAdapter(adapterHienThiBill);
        CHECK_START_MENU = true;

        eventButton();
    }
    private void eventButton(){
        imvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BillActivity.this,ThucUongActivity.class);
                startActivityForResult(intent,CODE_CHECK);
            }
        });
        imvRefesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<arrayListBill.size();i++){
                    if(arrayListBill.get(i).getCount() <1){
                        arrayListBill.remove(i);
                    }
                }
                for(int i=0;i<arrayListBill.size();i++){
                    tong+=arrayListBill.get(i).getGia()*arrayListBill.get(i).getCount();
                }
                txtTotal.setText(tong+"");
                adapterHienThiBill.notifyDataSetChanged();
            }
        });
        imvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder aBuilder = new AlertDialog.Builder(BillActivity.this);
                aBuilder.setTitle("Xác nhận");
                aBuilder.setMessage("Lưu hóa đơn bàn");
                aBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            saveBill();
                            updateTinhTrangBan(1);
                            Thread.sleep(500);
                            saveBillDetail();
                            Intent intent = new Intent(BillActivity.this,DanhSachBanActivity.class);
                            startActivity(intent);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                aBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                aBuilder.show();
            }
        });
        imvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTinhTrangBan(0);
                Intent intent = new Intent(BillActivity.this,DanhSachBanActivity.class);
                startActivity(intent);
            }
        });
    }
    private void eventButtonBill(){
        imvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BillActivity.this,ThucUongActivity.class);
                startActivityForResult(intent,CODE_CHECK);
            }
        });
        imvRefesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<arrayListBill.size();i++){
                    if(arrayListBill.get(i).getCount() <1){
                        arrayListBill.remove(i);
                    }
                }
                tong = 0;
                for(int i=0;i<arrayListBill.size();i++){
                    tong+=arrayListBill.get(i).getGia()*arrayListBill.get(i).getCount();
                }
                txtTotal.setText(tong+"");
                adapterHienThiBill.notifyDataSetChanged();
            }
        });
        imvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder aBuilder = new AlertDialog.Builder(BillActivity.this);
                aBuilder.setTitle("Xác nhận");
                aBuilder.setMessage("Lưu hóa đơn bàn");
                aBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteChiTiet();

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        updateThanhTien();
                        updaetBillDetail();
                        Intent intent = new Intent(BillActivity.this,DanhSachBanActivity.class);
                        startActivity(intent);
                    }
                });
                aBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                aBuilder.show();
            }
        });
        imvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTinhTrangBan(0);
                updateTinhTrangHoaDon();
                Intent intent = new Intent(BillActivity.this,DanhSachBanActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateTinhTrangHoaDon() {
    }

    private void updaetBillDetail() {
        for(final ThucUong tu : arrayListBill) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanAddBillDetail,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.trim().equals("success")){
                                Toast.makeText(BillActivity.this, "Update chi tiết thành công",Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(BillActivity.this, "Loi",Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("mathucuong",String.valueOf(tu.getId()));
                    params.put("soluong",String.valueOf(tu.getCount()));
                    params.put("mahoadon",String.valueOf(maHoaDon));
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private void saveBill(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanAddBill,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(BillActivity.this, R.string.addsuccess +"",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BillActivity.this, "Loi",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("maban",String.valueOf(maban));
                params.put("thanhtien",String.valueOf(tong));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void saveBillDetail(){
        System.out.println(arrayListBill.size()+"==========>");
        for(final ThucUong tu : arrayListBill) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanAddBillDetail,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.trim().equals("success")){
                                Toast.makeText(BillActivity.this, "Thêm chi tiết thành công",Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(BillActivity.this, "Loi",Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("mathucuong",String.valueOf(tu.getId()));
                    params.put("soluong",String.valueOf(tu.getCount()));
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
    private void updateTinhTrangBan(int tt){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanUpdateTinhTrangBan+maban,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(BillActivity.this, "update thanh cong",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BillActivity.this, "Loi",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("tinhtrang",String.valueOf(tt));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void getDataBanDaCo(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDangGetDataBan+maban,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            arrayListBill = new ArrayList<>();
                            System.out.println(Server.DuongDangGetDataBan+maban);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray =  jsonObject.getJSONArray("item");
                            JSONArray jsonArrayHoaDon =  jsonObject.getJSONArray("hoadon");
                            JSONObject hoaDon = jsonArrayHoaDon.getJSONObject(0);
                            maHoaDon = hoaDon.getString("id");
                            maban = hoaDon.getString("maban");
                            ngayTao = hoaDon.getString("ngaytao");
                            thanhtien = hoaDon.getLong("thanhtien");

                            if(jsonArray.length() > 0){

                                int len = jsonArray.length();
                                for (int i=0;i<len;i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    int id = object.getInt("mathucuong");
                                    String tenThucUong = object.getString("tenthucuong");
                                    long gia = object.getLong("gia");
                                    int maLoai = object.getInt("maloai");
                                    String anh = object.getString("anh");
                                    int soluong = object.getInt("soluong");

                                    arrayListBill.add(new ThucUong(id,tenThucUong,gia,maLoai,anh,soluong,""));
                                }
                                tvThoiGian.setText(ngayTao);
                                txtTotal.setText(thanhtien+"");
                                tvBan.setText("Bàn số : "+maban);
                                System.out.println(arrayListBill.size()+"===========>");
                                adapterHienThiBill = new AdapterHienThiBill(BillActivity.this,R.layout.activity_custom_hien_thi_bill,arrayListBill);
                                lvBill.setAdapter(adapterHienThiBill);

                            }else{
                                System.out.println("ko co data");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void deleteChiTiet(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Server.DuongDangDeleteChiTiet + maHoaDon,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String t = response;
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void updateThanhTien(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDangUpdateHoaDon+maHoaDon,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(BillActivity.this, "update thanh cong",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BillActivity.this, "Loi",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("maban",String.valueOf(maban));
                params.put("thanhtien",String.valueOf(tong));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }





}
