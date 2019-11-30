package com.example.quanlytrasua.CustomAdapter;

import android.content.Context;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quanlytrasua.Model.ThucUong;
import com.example.quanlytrasua.R;
import com.example.quanlytrasua.ultil.Server;

import java.util.ArrayList;

public class AdapterHienThiThucUong extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<ThucUong> arrItem;

    public AdapterHienThiThucUong(Context context, int layout, ArrayList<ThucUong> arrItem) {
        this.context = context;
        this.layout = layout;
        this.arrItem = arrItem;
    }

    private class ViewHolder{
        ImageView imgThucUong;
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
        return arrItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewRow = view;
        final ViewHolder viewHolder;
        if (viewRow == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewRow = inflater.inflate(layout,viewGroup,false);
            //AdapterHienThiThucUong.ViewHolder viewHolder = new AdapterHienThiThucUong.ViewHolder();
            viewHolder = new ViewHolder();

            viewHolder.imgThucUong = viewRow.findViewById(R.id.imgThucUong);
            viewHolder.tvTen = viewRow.findViewById(R.id.tvTenThucUong);
            viewHolder.tvGia = viewRow.findViewById(R.id.tvGiaThucUong);
            viewHolder.btnUp = viewRow.findViewById(R.id.btnUp);
            viewHolder.btnDown = viewRow.findViewById(R.id.btnDown);
            viewHolder.tvCount = viewRow.findViewById(R.id.tvCount);

            viewRow.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)viewRow.getTag();
        }
        final ThucUong thucUong = arrItem.get(i);
        //final AdapterHienThiThucUong.ViewHolder viewHolder = (AdapterHienThiThucUong.ViewHolder) viewRow.getTag();
        Glide.with(context).load(Server.DuongDanAnh+thucUong.getAnh()).into(viewHolder.imgThucUong);
        viewHolder.tvTen.setText(thucUong.getTenThucUong());
        viewHolder.tvGia.setText(thucUong.getGia()+" VNĐ");
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
