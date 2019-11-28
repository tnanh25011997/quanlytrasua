package com.example.quanlytrasua.FragmentApp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlytrasua.CustomAdapter.AdapterHienThiThongKe;
import com.example.quanlytrasua.CustomAdapter.AdapterHienThiThongKeThucUong;
import com.example.quanlytrasua.Model.ThongKe;
import com.example.quanlytrasua.Model.ThucUong;
import com.example.quanlytrasua.R;
import com.example.quanlytrasua.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ThongKeFragment extends Fragment {
    int idHoaDon,idBan;
    String ngay,tongTien;
    ListView lvHoaDon;
    ArrayList<ThongKe> listThongKe;
    String tenThucUong;
    int soLuong;
    long gia;
    ArrayList<ThucUong> listThucUong;
    AdapterHienThiThongKe adapterHienThiThongKe;
    AdapterHienThiThongKeThucUong adapterHienThiThongKeThucUong;
    private Dialog dialogListItem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithongke,container,false);
        lvHoaDon = view.findViewById(R.id.lvHoaDon);
        listThongKe = new ArrayList<ThongKe>();
        listThucUong = new ArrayList<ThucUong>();
        adapterHienThiThongKe = new AdapterHienThiThongKe(getActivity(), R.layout.custom_layout_hienthithongke, listThongKe);
        lvHoaDon.setAdapter(adapterHienThiThongKe);
        adapterHienThiThongKe.notifyDataSetChanged();
        GetDuLieuThongKe();
        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                initDialog(listThongKe.get(i).getIdHoaDon());

            }
        });
        return view;
    }

    private void initDialog(int midHoaDon) {
        dialogListItem = new Dialog(getActivity());
        dialogListItem.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialogListItem.setContentView(R.layout.layout_hienthithongkethucuong);
        dialogListItem.setTitle("Chi tiết hóa đơn");
        ListView lvThucUong = dialogListItem.findViewById(R.id.lvHoaDonThucUong);
        adapterHienThiThongKeThucUong = new AdapterHienThiThongKeThucUong(getActivity(),R.layout.custom_layout_hienthithongkethucuong, listThucUong);
        lvThucUong.setAdapter(adapterHienThiThongKeThucUong);
        getThucUongTheoHoaDon(midHoaDon);
        adapterHienThiThongKeThucUong.notifyDataSetChanged();
        dialogListItem.show();

    }

    private void getThucUongTheoHoaDon(int midHoaDon) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanThongKeThucUong+midHoaDon, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response !=null){
                    listThucUong.clear();
                    for(int i=0; i<response.length(); i++){

                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            tenThucUong = jsonObject.getString("tenThucUong");
                            soLuong = jsonObject.getInt("soLuong");
                            gia = jsonObject.getLong("gia");
                            listThucUong.add(new ThucUong(tenThucUong,gia,soLuong));
                            //listThucUong.add(new ThucUong(1,"a",1200,1,"a",12,"alo"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapterHienThiThongKeThucUong.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuThongKe() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanThongKe, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response !=null){
                    for(int i=0; i<response.length(); i++){

                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idHoaDon = jsonObject.getInt("id");
                            idBan = jsonObject.getInt("maBan");
                            ngay = jsonObject.getString("ngayTao");
                            tongTien = jsonObject.getString("thanhTien");
                            listThongKe.add(new ThongKe(idHoaDon,idBan,tongTien,ngay));
                            adapterHienThiThongKe.notifyDataSetChanged();
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
}
