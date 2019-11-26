package com.example.quanlytrasua.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlytrasua.Model.ThongKe;
import com.example.quanlytrasua.R;

import java.util.ArrayList;

public class AdapterHienThiThongKe extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<ThongKe> arrThongKe;

    public AdapterHienThiThongKe(Context context, int layout, ArrayList<ThongKe> arrThongKe) {
        this.context = context;
        this.layout = layout;
        this.arrThongKe = arrThongKe;
    }

    private class ViewHolder{
        TextView tvIDHoaDon,tvIDBan,tvNgay,tvTongTien;

    }
    @Override
    public int getCount() {
        return arrThongKe.size();
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
        if(viewRow == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewRow = inflater.inflate(layout,viewGroup,false);
            AdapterHienThiThongKe.ViewHolder viewHolder = new AdapterHienThiThongKe.ViewHolder();

            viewHolder.tvIDBan = viewRow.findViewById(R.id.tvTableHD);
            viewHolder.tvIDHoaDon= viewRow.findViewById(R.id.tvIdHoaDon);
            viewHolder.tvNgay = viewRow.findViewById(R.id.tvDateHD);
            viewHolder.tvTongTien = viewRow.findViewById(R.id.tvPriceHD);

            viewRow.setTag(viewHolder);

        }
        final ThongKe thongKe = arrThongKe.get(i);
        final AdapterHienThiThongKe.ViewHolder viewHolder = (AdapterHienThiThongKe.ViewHolder) viewRow.getTag();
        viewHolder.tvIDHoaDon.setText("Mã hóa đơn: "+ thongKe.getIdHoaDon());
        viewHolder.tvIDBan.setText("Bàn số: "+thongKe.getIdBan()+"");
        viewHolder.tvNgay.setText("Ngày: "+thongKe.getNgay()+"");
        viewHolder.tvTongTien.setText("Tổng tiền: "+thongKe.getTongTien()+" VNĐ");
        return viewRow;
    }
}
