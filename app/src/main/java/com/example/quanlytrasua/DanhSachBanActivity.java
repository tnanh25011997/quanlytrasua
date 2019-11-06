package com.example.quanlytrasua;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.quanlytrasua.CustomAdapter.AdapterHienThiBan;
import com.example.quanlytrasua.DAO.BanDAO;
import com.example.quanlytrasua.DTO.BanDTO;

import java.util.List;

public class DanhSachBanActivity extends AppCompatActivity {

    GridView gvHienThiBan;
    List<BanDTO> banDTOList;
    BanDAO banDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_danhsachban);
        gvHienThiBan = findViewById(R.id.gvHienThiBan);
        banDAO = new BanDAO();
        banDTOList = banDAO.getListBan();

        AdapterHienThiBan adapterHienThiBan = new AdapterHienThiBan(DanhSachBanActivity.this,R.layout.custom_layout_hienthiban, banDTOList);
        gvHienThiBan.setAdapter(adapterHienThiBan);
        adapterHienThiBan.notifyDataSetChanged();

    }
}
