package com.example.quanlytrasua.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlytrasua.FragmentApp.HienThiBanFragment;
import com.example.quanlytrasua.Model.BanDTO;
import com.example.quanlytrasua.R;

import java.util.List;

public class AdapterHienThiBan extends BaseAdapter {

    Context context;
    int layout;
    List<BanDTO> banDTOList;

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
        //return null;
        return banDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        //return 0;
        return i;
    }



    public class ViewHolderBan{
        ImageView ivBan;
        TextView txtTenBan;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderBan viewHolderBan;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderBan = new ViewHolderBan();
            view = inflater.inflate(R.layout.custom_layout_hienthiban,null);

            viewHolderBan.ivBan = view.findViewById(R.id.ivBan);
            viewHolderBan.txtTenBan = view.findViewById(R.id.txtTenBan);


            view.setTag(viewHolderBan);
        }
        else {
            viewHolderBan = (ViewHolderBan) view.getTag();
        }
        BanDTO banDTO = banDTOList.get(i);
        viewHolderBan.txtTenBan.setText(banDTO.getTenBan());
        if(banDTO.getTinhTrang() == 1){

        }
        else{
            viewHolderBan.ivBan.setImageResource(R.drawable.table_trong);
        }
        return view;
    }

}
