package com.example.quanlytrasua.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlytrasua.Model.ThucUong;
import com.example.quanlytrasua.R;


import java.util.ArrayList;

public class AdapterHienThiHoaDon extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<ThucUong> arrItem;

    public AdapterHienThiHoaDon(Context context, int layout, ArrayList<ThucUong> arrItem) {
        this.context = context;
        this.layout = layout;
        this.arrItem = arrItem;
    }
    private class ViewHolder{

        TextView tvTen;
        TextView tvGia;
        ImageView btnUp;
        ImageView btnDown;
        TextView tvCount;

    }

    @Override
    public int getCount() {
        return arrItem.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewRow = view;
        if (viewRow == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewRow = inflater.inflate(layout,viewGroup,false);
            AdapterHienThiHoaDon.ViewHolder viewHolder = new AdapterHienThiHoaDon.ViewHolder();

            viewHolder.tvTen = viewRow.findViewById(R.id.tvTenThucUongBill);
            viewHolder.tvGia = viewRow.findViewById(R.id.tvGiaThucUongBill);
            viewHolder.btnUp = viewRow.findViewById(R.id.btnUpBill);
            viewHolder.btnDown = viewRow.findViewById(R.id.btnDownBill);
            viewHolder.tvCount = viewRow.findViewById(R.id.tvCountBill);

            viewRow.setTag(viewHolder);
        }

        final ThucUong thucUong = arrItem.get(i);
        final AdapterHienThiHoaDon.ViewHolder viewHolder = (AdapterHienThiHoaDon.ViewHolder) viewRow.getTag();

        viewHolder.tvTen.setText(thucUong.getTenThucUong());
        viewHolder.tvGia.setText(thucUong.getGia()+"");
        viewHolder.tvCount.setText(thucUong.getCount()+"");
        viewHolder.btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = thucUong.getCount();
                i = i + 1;
                viewHolder.tvCount.setText(String.valueOf(i));
                thucUong.setCount(i);
            }
        });

        viewHolder.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = thucUong.getCount();
                if (i == 0)
                {
                    //viewHolder.tvCount.setText(i);
                    thucUong.setCount(i);
                }
                else
                {
                    i = i - 1;
                    viewHolder.tvCount.setText(String.valueOf(i));
                    thucUong.setCount(i);
                }
            }
        });
        return viewRow;
    }
}
