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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.quanlytrasua.CustomAdapter.AdapterHienThiThongKe;
import com.example.quanlytrasua.CustomAdapter.AdapterHienThiThongKeThucUong;
import com.example.quanlytrasua.Model.HoaDon2;
import com.example.quanlytrasua.Model.ThucUong;
import com.example.quanlytrasua.Model.chitiethoadon;
import com.example.quanlytrasua.R;
import com.example.quanlytrasua.ultil.Server;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;


public class ThongKeFragment extends Fragment {
    DatabaseReference mDatabase;
    int idBan;
    String idHoaDon;
    String ngay;
    long tongTien;
    ListView lvHoaDon;
    ArrayList<HoaDon2> listThongKe;

    ArrayList<ThucUong> listThucUong;
    AdapterHienThiThongKe adapterHienThiThongKe;
    AdapterHienThiThongKeThucUong adapterHienThiThongKeThucUong;
    private Dialog dialogListItem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithongke,container,false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lvHoaDon = view.findViewById(R.id.lvHoaDon);
        listThongKe = new ArrayList<HoaDon2>();
        listThucUong = new ArrayList<ThucUong>();
        adapterHienThiThongKe = new AdapterHienThiThongKe(getActivity(), R.layout.custom_layout_hienthithongke, listThongKe);
        lvHoaDon.setAdapter(adapterHienThiThongKe);
        adapterHienThiThongKe.notifyDataSetChanged();
        GetDuLieuThongKe();
        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                initDialog(listThongKe.get(i).getId());

            }
        });
        return view;
    }

    private void initDialog(String midHoaDon) {
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

    private void getThucUongTheoHoaDon(final String midHoaDon) {
        listThucUong.clear();
        mDatabase.child("chitiethoadon").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final chitiethoadon ct = dataSnapshot.getValue(chitiethoadon.class);
                ct.setId(dataSnapshot.getKey());
                if(ct.getMaHoaDon().equals(midHoaDon)){
                    mDatabase.child("thucuong").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            ThucUong thucUong = dataSnapshot.getValue(ThucUong.class);
                            thucUong.setId(dataSnapshot.getKey());
                            thucUong.setCount(ct.getSoLuong());
                            if(thucUong.getId().equals(ct.getMaThucUong())){
                                listThucUong.add(new ThucUong(thucUong.getTenThucUong(),thucUong.getGia(),thucUong.getCount()));
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
        adapterHienThiThongKeThucUong.notifyDataSetChanged();
    }

    private void GetDuLieuThongKe() {

        mDatabase.child("hoadon").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                HoaDon2 hd = dataSnapshot.getValue(HoaDon2.class);
                hd.setId(dataSnapshot.getKey());
                if(hd.getTinhTrang() == 1){
                    idHoaDon = hd.getId();
                    idBan = hd.getMaBan();
                    ngay = hd.getNgayTao();
                    tongTien = hd.getThanhTien();
                    //String id, int maBan, String ngayTao, long thanhTien
                    listThongKe.add(new HoaDon2(idHoaDon,idBan,ngay,tongTien));
                    adapterHienThiThongKe.notifyDataSetChanged();
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
}
