package com.example.quanlytrasua.CustomAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlytrasua.BillActivity;
import com.example.quanlytrasua.Model.ThucUong;
import com.example.quanlytrasua.R;

import java.util.ArrayList;

public class AdapterHienThiBill extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<ThucUong> arrThucUong;


    public AdapterHienThiBill(Context context, int layout, ArrayList<ThucUong> arrThucUong) {
        this.context = context;
        this.layout = layout;
        this.arrThucUong = arrThucUong;
    }

    @Override
    public int getCount() {
        return arrThucUong.size();
    }

    @Override
    public Object getItem(int i) {
        return arrThucUong.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolderBill{
        TextView tvName;
        TextView tvPrice;
        ImageView btnUp;
        ImageView btnDown;
        TextView tvCount;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderBill viewHolderBill;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderBill = new ViewHolderBill();
            view = inflater.inflate(R.layout.activity_custom_hien_thi_bill,null);

            viewHolderBill.tvName = view.findViewById(R.id.tvNameOdFoodBill);
            viewHolderBill.tvCount = view.findViewById(R.id.tvCountBill);
            viewHolderBill.tvPrice = view.findViewById(R.id.tvPriceOfFoodBill);
            viewHolderBill.btnDown = view.findViewById(R.id.btnDownBill);
            viewHolderBill.btnUp = view.findViewById(R.id.btnUpBill);
            view.setTag(viewHolderBill);
        }
        else{
            viewHolderBill = (ViewHolderBill) view.getTag();
        }
        ThucUong thucUong = arrThucUong.get(i);
        System.out.println(thucUong.getCount());
        Log.d("Hello", thucUong.getCount()+"");
        viewHolderBill.tvName.setText(thucUong.getTenThucUong());
        viewHolderBill.tvPrice.setText(thucUong.getGia()+"");
        viewHolderBill.tvCount.setText(thucUong.getCount()+"");

        viewHolderBill.btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = thucUong.getCount();
                i = i + 1;
                viewHolderBill.tvCount.setText(i+"");
                thucUong.setCount(i);
            }
        });
        viewHolderBill.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = thucUong.getCount();
                i=i-1;
                viewHolderBill.tvCount.setText(i+"");
                thucUong.setCount(i);
            }
        });
        return view;
    }
}
