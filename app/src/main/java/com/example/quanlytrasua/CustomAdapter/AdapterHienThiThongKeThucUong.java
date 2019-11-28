package com.example.quanlytrasua.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlytrasua.Model.ThucUong;
import com.example.quanlytrasua.R;

import java.util.ArrayList;

public class AdapterHienThiThongKeThucUong extends BaseAdapter {
    private Context context;
    private  int layout;
    private ArrayList<ThucUong> listThucUong;

    public AdapterHienThiThongKeThucUong(Context context, int layout, ArrayList<ThucUong> listThucUong) {
        this.context = context;
        this.layout = layout;
        this.listThucUong = listThucUong;
    }
    private class ViewHolder{
        TextView tvTenThucUong;
        TextView tvGia;
        TextView tvSoLuong;
    }

    @Override
    public int getCount() {
        return listThucUong.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewRow = view;
        if (viewRow == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewRow = inflater.inflate(layout,viewGroup,false);
            AdapterHienThiThongKeThucUong.ViewHolder viewHolder = new AdapterHienThiThongKeThucUong.ViewHolder();

            viewHolder.tvTenThucUong = viewRow.findViewById(R.id.tvTenThucUongHD);
            viewHolder.tvGia = viewRow.findViewById(R.id.tvGiaThucUongHD);
            viewHolder.tvSoLuong = viewRow.findViewById(R.id.tvSoLuongThucUongHD);

            viewRow.setTag(viewHolder);
        }

        final ThucUong thucUong = listThucUong.get(i);
        final AdapterHienThiThongKeThucUong.ViewHolder viewHolder = (AdapterHienThiThongKeThucUong.ViewHolder) viewRow.getTag();
        viewHolder.tvTenThucUong.setText( thucUong.getTenThucUong());
        viewHolder.tvGia.setText("Đ.giá: "+thucUong.getGia()+"đ");
        viewHolder.tvSoLuong.setText("SL: "+thucUong.getCount()+"");

        return viewRow;
    }
}
