package com.example.quanlytrasua;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlytrasua.CustomAdapter.AdapterHienThiHoaDon;
import com.example.quanlytrasua.FragmentApp.HienThiBanFragment;
import com.example.quanlytrasua.Model.ModelMax;
import com.example.quanlytrasua.Model.ThucUong;
import com.example.quanlytrasua.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HoaDonActivity extends AppCompatActivity {

    public static boolean CHECK_START_MENU = false;
    ArrayList<ThucUong> listThucUongchecked;
    private TextView tvTable;
    private TextView tvTime;

    private ListView lvHoaDon;
    private AdapterHienThiHoaDon adapterHienThiHoaDon;
    private ArrayList<ThucUong> listThucUong;
    private String table;
    private String thoigian;

    private TextView tvTotalBill;
    private ImageView imgThemBill;
    private ImageView imgDayBill;
    private ImageView imgInBill;
    private ImageView imgRefresh;
    private int idBanchecking;
    long tongTien = 0;
    int maHoaDONNN = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        AddControl();
        AddEvent();

    }


    private void AddControl() {
        lvHoaDon = findViewById(R.id.lvThucUongBill);
        tvTable = findViewById(R.id.tvTableBill);
        imgThemBill = findViewById(R.id.btnThemBill);
        imgInBill = findViewById(R.id.btnInBill);
        imgDayBill = findViewById(R.id.btnDayBill);
        tvTotalBill = findViewById(R.id.tvTongBill);
        tvTime = findViewById(R.id.tvTimeBill);
        imgRefresh = findViewById(R.id.btnRefresh);
    }

    @Override
    protected void onStop() {
        tongTien = 0;
        super.onStop();
    }


    private void AddEvent() {

        if(HienThiBanFragment.CHECK_TABLE == false){
            LayDuLieuBanTrong();
            TaoViewBanTrong();
            getTongBill();
        }
        else{
            listThucUong = new ArrayList<ThucUong>();
            adapterHienThiHoaDon = new AdapterHienThiHoaDon(HoaDonActivity.this, R.layout.custom_layout_hienthihoadon, listThucUong);
            lvHoaDon.setAdapter(adapterHienThiHoaDon);
            LayDuLieuBanCoNguoi();
            TaoViewBanCoNguoi();

        }
        imgThemBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CHECK_START_MENU = true;
                Intent intent = new Intent(HoaDonActivity.this,ThucUongActivity.class);
                startActivityForResult(intent,111);
            }
        });
        imgDayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogLuuHoaDon();
            }
        });
        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = listThucUong.size() - 1; i>=0; i--)
                {
                    if(listThucUong.get(i).getCount()==0)
                        listThucUong.remove(i);
                }
                getTongBill();
                Toast.makeText(HoaDonActivity.this, "a"+maHoaDONNN,Toast.LENGTH_LONG).show();
//
//                startActivity(getIntent());
//                finish();
                adapterHienThiHoaDon.notifyDataSetChanged();
            }
        });
    }



    private void showDialogLuuHoaDon() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonActivity.this);
        builder.setTitle("XÁC NHẬN");
        builder.setMessage("Lưu hóa đơn?");
        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                LuuHoaDon();
                Intent intent = new Intent(HoaDonActivity.this, DanhSachBanActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void LuuHoaDon() {
        try {
            XoaHetChiTietCu();
            ThemVaoBangHoaDon();
            Thread.sleep(500);
            CapNhatTinhTrangBan(idBanchecking, 1);
            ThemVaoBangChiTietHoaDon();
            //getMaxHoaDon();
        }catch (Exception e){

        }


    }

    private void XoaHetChiTietCu() {
    }

    //    private void getMaxHoaDon(){
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.DuongDanMaxHoaDon, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        for(int i=0; i< response.length(); i++){
//                            try {
//                                JSONObject jsonObject = response.getJSONObject(i);
//                                int a = jsonObject.getInt("max");
//                                ThemVaoBangChiTietHoaDon(a);
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        );
//
//        requestQueue.add(jsonArrayRequest);
//
//
//
//    }
    private void ThemVaoBangChiTietHoaDon() {
        for(final ThucUong thucUong : listThucUong) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanThemVaoCTHoaDon,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("success")) {

                                Toast.makeText(HoaDonActivity.this, "Đã thêm chi hóa đơn", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(HoaDonActivity.this, "Lỗi cập nhật chi tiết hóa đơn", Toast.LENGTH_LONG).show();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("mathucuong", String.valueOf(thucUong.getId()));
                    params.put("soluong", String.valueOf(thucUong.getCount()));
                    //params.put("mahoadon",String.valueOf(maHoaDon));

                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }


    private void ThemVaoBangHoaDon() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanThemVaoHoaDon,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){


                        }
                        else{
                            Toast.makeText(HoaDonActivity.this, "Lỗi cập nhật hóa đơn",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("maban", String.valueOf(idBanchecking));
                params.put("thanhtien", String.valueOf(tongTien));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void CapNhatTinhTrangBan(int idBan, final int tinhTrang) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanCapNhatBan+idBan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){

                        }
                        else{
                            Toast.makeText(HoaDonActivity.this, "Lỗi cập nhật bàn",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HoaDonActivity.this, "Lỗi server",Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tinhTrang", String.valueOf(tinhTrang));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void LayDuLieuBanCoNguoi() {
        Intent intent = getIntent();
        table = intent.getStringExtra("table");


    }

    private void TaoViewBanTrong() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String[] str = timeStamp.split("_");
        String[] str2 = str[0].split("");
        String year = str2[1]+str2[2]+str2[3]+str2[4];
        String month = str2[5]+str2[6];
        String day = str2[7]+str2[8];

        String[] str3 = str[1].split("");
        String hour = str3[1]+str3[2];
        String minute = str3[3]+str3[4];
        String sec = str3[5]+str3[6];
        tvTime.setText("Thời gian: "+day+"/"+month+"/"+year+"   "+hour+":"+minute+":"+sec);

        adapterHienThiHoaDon = new AdapterHienThiHoaDon(HoaDonActivity.this, R.layout.custom_layout_hienthihoadon, listThucUong);
        lvHoaDon.setAdapter(adapterHienThiHoaDon);
        adapterHienThiHoaDon.notifyDataSetChanged();


    }
    private void TaoViewBanCoNguoi() {

        tvTable.setText("Bàn "+table);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanGetDataBan + table, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response !=null){
                    for(int i=0; i<response.length(); i++){

                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int id_thucuong = jsonObject.getInt("id_thucuong");
                            String tenThucUong = jsonObject.getString("tenthucuong");
                            long gia = jsonObject.getLong("gia");
                            int maLoai = jsonObject.getInt("maloai");
                            String anh = jsonObject.getString("anh");
                            String tenLoai = jsonObject.getString("tenloai");
                            int count = jsonObject.getInt("soluong");;
                            listThucUong.add(new ThucUong(id_thucuong, tenThucUong,gia,maLoai,anh,count ,tenLoai));
                            tongTien = jsonObject.getLong("thanhtien");
                            thoigian = jsonObject.getString("ngaytao");
                            maHoaDONNN = jsonObject.getInt("id_hoadon");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    tvTime.setText("Thời gian: "+thoigian);
                    tvTotalBill.setText("Tổng tiền: "+getTien(tongTien));

                    adapterHienThiHoaDon.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);


    }

    private void LayDuLieuBanTrong() {
        listThucUong = new ArrayList<>();
        Intent intent = getIntent();
        listThucUong = (ArrayList<ThucUong>) intent.getSerializableExtra("list");
        String soBan= intent.getStringExtra("table");
        idBanchecking = Integer.parseInt(intent.getStringExtra("table").trim());
        tvTable.setText("Bàn Số: "+soBan);
    }

    private void getTongBill(){
        tongTien = 0;
        for (int i=0; i<listThucUong.size(); i++){
            tongTien += listThucUong.get(i).getGia()*listThucUong.get(i).getCount();
        }
        tvTotalBill.setText("Tổng tiền: "+getTien(tongTien));
    }
    private String getTien(long x){
        String str = x+"";
        String[] s1 = str.split("");
        String money = "";
        int count = 0;
        int c = 0;
        for (int i = s1.length - 1; i>=0; i--)
        {
            count++;
            c++;
            if (count == 3)
            {
                if (s1[i].equals(""))
                {
                    money = s1[i] +money;
                    count = 0;
                }
                else if (c < s1.length-1){
                    money = "," + s1[i] +money;
                    count = 0;
                }
                else{
                    money = s1[i] +money;
                    count = 0;
                }

            }
            else {
                money = s1[i] + money;
            }
        }

        return money+" VND";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 111) {
            if(resultCode == Activity.RESULT_OK){
                ArrayList<ThucUong> listThucUongThem = new ArrayList<>();
                listThucUongThem = (ArrayList<ThucUong>) data.getSerializableExtra("result");
                String str = "";
                for (ThucUong item : listThucUong)
                {
                    str += item.getTenThucUong();
                }
                Log.e("--------------",str);
                for (int j = 0; j<listThucUongThem.size(); j++)
                {
                    if (!str.contains(listThucUongThem.get(j).getTenThucUong()))
                    {
                        listThucUong.add(listThucUongThem.get(j));
                    }
                    else {
                        for (int k=0; k<listThucUong.size(); k++)
                        {
                            if (listThucUong.get(k).getTenThucUong().equals(listThucUongThem.get(j).getTenThucUong()))
                            {
                                listThucUong.get(k).setCount(listThucUong.get(k).getCount()+listThucUongThem.get(j).getCount());
                            }
                        }
                    }

                }
                for(int i = listThucUong.size() - 1; i>=0; i--)
                {
                    if(listThucUong.get(i).getCount()==0)
                        listThucUong.remove(i);
                }
                CHECK_START_MENU = false;
                getTongBill();
                adapterHienThiHoaDon.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

}
