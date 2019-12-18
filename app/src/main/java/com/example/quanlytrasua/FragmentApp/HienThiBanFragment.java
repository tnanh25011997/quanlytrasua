package com.example.quanlytrasua.FragmentApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlytrasua.CustomAdapter.AdapterHienThiBan;
import com.example.quanlytrasua.DanhSachBanActivity;
import com.example.quanlytrasua.HoaDonActivity;
import com.example.quanlytrasua.Model.BanDTO;
import com.example.quanlytrasua.R;
import com.example.quanlytrasua.ThucUongActivity;
import com.example.quanlytrasua.ultil.Server;
import com.github.nkzawa.emitter.Emitter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class HienThiBanFragment extends Fragment {

    public static boolean CHECK_BAN = false;

    GridView gvHienThiBan;
    List<BanDTO> banDTOList;
    AdapterHienThiBan adapterHienThiBan;
    int id = 0;
    String tenBan = "";
    int tinhTrang = 0;
    DatabaseReference mData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthiban,container,false);
        gvHienThiBan = view.findViewById(R.id.gvHienThiBan);


        mData = FirebaseDatabase.getInstance().getReference();
        banDTOList = new ArrayList<BanDTO>();
        adapterHienThiBan = new AdapterHienThiBan(getActivity(), R.layout.custom_layout_hienthiban, banDTOList);
        gvHienThiBan.setAdapter(adapterHienThiBan);
        adapterHienThiBan.notifyDataSetChanged();
        GetDuLieuBan();

        gvHienThiBan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (adapterView.getId())
                {
                    case R.id.gvHienThiBan:
                        BanDTO table = banDTOList.get(i);
                        int check = table.getTinhTrang();
                        int maBan = table.getMaBan();
                        if (check == 0)
                        {

                            HienThiBanFragment.CHECK_BAN = false;
                            Intent intent = new Intent(getActivity(), ThucUongActivity.class);
                            intent.putExtra("table",maBan+"");
                            startActivity(intent);


                        }
                        else if (check == 1)
                        {
                            HienThiBanFragment.CHECK_BAN = true;
                            Intent intent = new Intent(getActivity(), HoaDonActivity.class);
                            intent.putExtra("table",maBan+"");
                            startActivity(intent);

                        }
                        break;
                }
            }
        });

        return view;


    }

    private void GetDuLieuBan() {


        mData.child("Ban").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BanDTO ban = dataSnapshot.getValue(BanDTO.class);
                //ban.setMaBan(dataSnapshot.getKey());
                banDTOList.add(ban);
                adapterHienThiBan.notifyDataSetChanged();
        }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BanDTO ban = dataSnapshot.getValue(BanDTO.class);
                for(BanDTO b : banDTOList){
                    if(ban.getMaBan()==b.getMaBan()){
                        b.setTenBan(ban.getTenBan());
                        b.setTinhTrang(ban.getTinhTrang());
                    }
                }
                adapterHienThiBan.notifyDataSetChanged();
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
