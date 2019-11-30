package com.example.quanlytrasua;

import androidx.annotation.NonNull;
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
import com.example.quanlytrasua.Model.BanDTO;
import com.example.quanlytrasua.Model.HoaDon2;
import com.example.quanlytrasua.Model.ThucUong;
import com.example.quanlytrasua.Model.chitiethoadon;
import com.example.quanlytrasua.ultil.Server;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HoaDonActivity extends AppCompatActivity {

    public static boolean CHECK_START_MENU = false;
    DatabaseReference mDatabase;

    ArrayList<ThucUong> listThucUongchecked;
    private TextView tvTable;
    private TextView tvTime;

    private ListView lvHoaDon;
    private AdapterHienThiHoaDon adapterHienThiHoaDon;
    private ArrayList<ThucUong> listThucUong;
    private int idBanchecking; //lay idban trong
    private String table; //lay idban co nguoi

    private String thoigian;
    private String timeCheckIn;
    private String timeCheckOut;

    private TextView tvTotalBill;
    private ImageView imgThemBill;
    private ImageView imgDayBill;
    private ImageView imgInBill;
    private ImageView imgRefresh;

    long tongTien = 0;
    String maHOADONCHECK = "";
    ArrayList<chitiethoadon> listCT = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
        adapterHienThiHoaDon.notifyDataSetChanged();
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
                Toast.makeText(HoaDonActivity.this, "Refresh bàn có mã hóa đơn là "+maHOADONCHECK+" trong CSDL",Toast.LENGTH_LONG).show();
                adapterHienThiHoaDon.notifyDataSetChanged();
            }
        });
        imgInBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogThanhToan();
            }
        });
    }

    private void showDialogThanhToan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonActivity.this);
        builder.setTitle("XÁC NHẬN");
        builder.setMessage("Xác nhận thanh toán?");
        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ThanhToan();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void ThanhToan() {
        if(maHOADONCHECK.equals("")){
            Toast.makeText(HoaDonActivity.this, "Chưa có Hóa đơn nào được lưu!", Toast.LENGTH_LONG).show();
        }else{
            CapNhatTinhTrangBan(Integer.parseInt(table),0);
            UpdateTinhTrangHoaDon(maHOADONCHECK);
            Intent intent = new Intent(HoaDonActivity.this, DanhSachBanActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

    }

    private void UpdateTinhTrangHoaDon(final String maHoaDonCheck) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanCapNhatTrangThaiHoaDon,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(HoaDonActivity.this, "Đã thanh toán hóa đơn có mã "+maHoaDonCheck,Toast.LENGTH_LONG).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("idHoaDon",String.valueOf(maHoaDonCheck));
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
        mDatabase.child("hoadon").child(maHoaDonCheck).setValue(new HoaDon2(maHoaDonCheck,Integer.parseInt(table), getNgay(),1,tongTien));
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
            //XoaHetChiTietCu(maHOADONCHECK);

            ThemVaoBangHoaDon(maHOADONCHECK);

            CapNhatTinhTrangBan(idBanchecking, 1);
            //ThemVaoBangChiTietHoaDon(maHOADONCHECK);
        }catch (Exception e){

        }


    }



    private void ThemVaoBangHoaDon(final String maHoaDonCheck) {
        DatabaseReference chitietref = FirebaseDatabase.getInstance().getReference("chitiethoadon");
        chitietref.keepSynced(true);
        getTongBill();
          if(maHoaDonCheck.equals("")){
              final String mGroupId = mDatabase.push().getKey();
              //String id, int maBan, String ngayTao, int tinhTrang, long thanhTien
              mDatabase.child("hoadon").child(mGroupId).setValue(new HoaDon2(mGroupId,idBanchecking, getNgay(),0,tongTien));
                  for (ThucUong thucUong : listThucUong){
                      //String id, String maHoaDon, String maThucUong, int soLuong
                      String key = mDatabase.push().getKey();
                      mDatabase.child("chitiethoadon").child(key).setValue(new chitiethoadon(key,mGroupId,thucUong.getId(),thucUong.getCount()));
                  }
          }else{


              mDatabase.child("hoadon").child(maHoaDonCheck).setValue(new HoaDon2(maHoaDonCheck,Integer.parseInt(table), getNgay(),0,tongTien));
              final ArrayList<chitiethoadon> tam = new ArrayList<>();
              int t=0;
              int kt=0;
              for(ThucUong thucUong : listThucUong){
                  for(chitiethoadon ct : listCT){
                      if(thucUong.getId().equals(ct.getMaThucUong())) {
                          tam.remove(ct);
                          t++;
                          chitietref.child(ct.getId()).setValue(new chitiethoadon(ct.getId(), ct.getMaHoaDon(), thucUong.getId(), thucUong.getCount()));
                      }
                      else{
                          if(kt==0){
                              tam.add(ct);
                          }
                      }

                  }

                  if(t==0){
                      String key = mDatabase.push().getKey();
                      chitietref.child(key).setValue(new chitiethoadon(key,maHoaDonCheck,thucUong.getId(),thucUong.getCount()));
                  }

                  t=0;
                  kt++;
              }
              for (chitiethoadon ct : tam){
                  chitietref.child(ct.getId()).removeValue();
              }

          }


    }

    private void CapNhatTinhTrangBan(final int idBan, final int tinhTrang) {
            mDatabase.child("Ban").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    BanDTO ban = dataSnapshot.getValue(BanDTO.class);
                    if(ban.getMaBan()== idBan ){
                        mDatabase.child("Ban").child(dataSnapshot.getKey()).setValue(new BanDTO(idBan,ban.getTenBan(),tinhTrang));
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
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

//        tvTable.setText("Bàn "+table);
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanGetDataBan + table, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if(response !=null){
//                    for(int i=0; i<response.length(); i++){
//
//                        try {
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            String id_thucuong = jsonObject.getString("id_thucuong");
//                            String tenThucUong = jsonObject.getString("tenthucuong");
//                            long gia = jsonObject.getLong("gia");
//                            int maLoai = jsonObject.getInt("maloai");
//                            String anh = jsonObject.getString("anh");
//                            String tenLoai = jsonObject.getString("tenloai");
//                            int count = jsonObject.getInt("soluong");;
//                            listThucUong.add(new ThucUong(id_thucuong, tenThucUong,gia,maLoai,anh,count ,tenLoai));
//                            tongTien = jsonObject.getLong("thanhtien");
//                            thoigian = jsonObject.getString("ngaytao");
//                            maHOADONCHECK = jsonObject.getInt("id_hoadon");
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
//                    String[] str = timeStamp.split("_");
//                    String[] str2 = str[0].split("");
//                    String year = str2[1]+str2[2]+str2[3]+str2[4];
//                    String month = str2[5]+str2[6];
//                    String day = str2[7]+str2[8];
//
//                    String[] str3 = str[1].split("");
//                    String hour = str3[1]+str3[2];
//                    String minute = str3[3]+str3[4];
//                    String sec = str3[5]+str3[6];
//                    tvTime.setText("Thời gian: "+day+"/"+month+"/"+year+"   "+hour+":"+minute+":"+sec);
//                    //tvTime.setText("Thời gian: "+thoigian);
//                    tvTotalBill.setText("Tổng tiền: "+getTien(tongTien));
//
//                    adapterHienThiHoaDon.notifyDataSetChanged();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
        tvTable.setText("Bàn số: "+table);
        final DatabaseReference ctref = FirebaseDatabase.getInstance().getReference("chitiethoadon");
          mDatabase.child("hoadon").addChildEventListener(new ChildEventListener() {
              @Override
              public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                  final HoaDon2 hd = dataSnapshot.getValue(HoaDon2.class);
                  hd.setId(dataSnapshot.getKey());
                  if(table.equals(String.valueOf(hd.getMaBan()))&& hd.getTinhTrang()==0){
                      maHOADONCHECK = dataSnapshot.getKey();
                      tongTien = hd.getThanhTien();
                      thoigian = hd.getNgayTao();
                      tvTime.setText("Thời gian: "+thoigian);
                      tvTotalBill.setText("Tổng tiền: "+ getTien(tongTien));
                  }
              }

              @Override
              public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

              }

              @Override
              public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

              }

              @Override
              public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });
          mDatabase.child("chitiethoadon").addChildEventListener(new ChildEventListener() {
              @Override
              public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                  final chitiethoadon ct = dataSnapshot.getValue(chitiethoadon.class);
                  ct.setId(dataSnapshot.getKey());
                  if(ct.getMaHoaDon().equals(maHOADONCHECK)){
                      listCT.add(ct);
                      mDatabase.child("thucuong").addChildEventListener(new ChildEventListener() {
                          @Override
                          public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                              ThucUong thucUong = dataSnapshot.getValue(ThucUong.class);
                              thucUong.setId(dataSnapshot.getKey());
                              thucUong.setCount(ct.getSoLuong());
                              if(thucUong.getId().equals(ct.getMaThucUong())){
                                  listThucUong.add(thucUong);
                              }
                              adapterHienThiHoaDon.notifyDataSetChanged();
                          }

                          @Override
                          public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                          }

                          @Override
                          public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                          }

                          @Override
                          public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                          }

                          @Override
                          public void onCancelled(@NonNull DatabaseError databaseError) {

                          }
                      });
                  }
              }

              @Override
              public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

              }

              @Override
              public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

              }

              @Override
              public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });


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
    private String getNgay()
    {
        String tg;
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
        tg = day+"/"+month+"/"+year+"   "+hour+":"+minute+":"+sec;
        return tg;
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
