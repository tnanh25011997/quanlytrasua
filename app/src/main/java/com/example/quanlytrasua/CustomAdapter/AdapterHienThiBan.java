package com.example.quanlytrasua.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.LayoutInflaterFactory;

import com.example.quanlytrasua.DTO.BanDTO;
import com.example.quanlytrasua.R;

import java.util.List;

public class AdapterHienThiBan extends BaseAdapter implements View.OnClickListener{

    Context context;
    int layout;
    List<BanDTO> banDTOList;
    ViewHolderBan viewHolderBan;

    public AdapterHienThiBan(Context context, int layout, List<BanDTO> banDTOList){
        this.context = context;
        this.layout = layout;
        this.banDTOList = banDTOList;
    }
    @Override
    public int getCount() {
        return banDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return banDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return banDTOList.get(i).getMaBan();
    }



    public class ViewHolderBan{
        ImageView ivBan, ivThanhToan, ivThemNuoc, ivAn;
        TextView txtTenBan;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderBan = new ViewHolderBan();
            v = inflater.inflate(R.layout.custom_layout_hienthiban,viewGroup,false);
            viewHolderBan.ivBan = v.findViewById(R.id.ivBan);
            viewHolderBan.ivThemNuoc = v.findViewById(R.id.ivThemNuoc);
            viewHolderBan.ivThanhToan = v.findViewById(R.id.ivThanhToan);
            viewHolderBan.ivAn = v.findViewById(R.id.ivAn);
            viewHolderBan.txtTenBan = v.findViewById(R.id.txtTenBan);

            v.setTag(viewHolderBan);


        }
        else {
            viewHolderBan = (ViewHolderBan) v.getTag();
        }
        viewHolderBan.ivBan.setOnClickListener(this);
        BanDTO banDTO = banDTOList.get(i);
        viewHolderBan.txtTenBan.setText(banDTO.getTenBan());

        return v;
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        viewHolderBan = (ViewHolderBan) ((View)view.getParent()).getTag();
        switch (id){
            case R.id.ivBan:

                viewHolderBan.ivThemNuoc.setVisibility(View.VISIBLE);
                break;
        }
    }
}
