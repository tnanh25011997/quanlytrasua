package com.example.quanlytrasua;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlytrasua.CustomAdapter.AdapterHienThiHoaDon;
import com.example.quanlytrasua.FragmentApp.HienThiBanFragment;
import com.example.quanlytrasua.Model.ThucUong;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HoaDonActivity extends AppCompatActivity {

    public static boolean CHECK_START_MENU = false;
    ArrayList<ThucUong> listThucUongchecked;
    private TextView tvTable;
    private TextView tvTime;

    private ListView lvHoaDon;
    private AdapterHienThiHoaDon adapterHienThiHoaDon;
    private ArrayList<ThucUong> listThucUong;
    private String table;
    private String time;

    private TextView tvTotalBill;
    private ImageView imgThemBill;
    private ImageView imgDayBill;
    private ImageView imgInBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        AddControl();
        AddEvent();



    }


    private void AddControl() {
        lvHoaDon = findViewById(R.id.lvThucUongBill);
        tvTable = findViewById(R.id.tvTableBill);
        imgThemBill = findViewById(R.id.btnThemBill);
        imgInBill = findViewById(R.id.btnInBill);
        imgDayBill = findViewById(R.id.btnDayBill);
        tvTotalBill = findViewById(R.id.tvTongBill);
        tvTime = findViewById(R.id.tvTimeBill);
    }

    private void AddEvent() {

        if(HienThiBanFragment.CHECK_TABLE == false){
            getDataCheckFalse();
            initViewsCheckFalse();
        }
        else{
            getDataCheckTrue();
        }
        imgThemBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CHECK_START_MENU = true;
                Intent intent = new Intent(HoaDonActivity.this,ThucUongActivity.class);
                startActivityForResult(intent,111);
            }
        });
        imgDayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDayBill();
            }
        });
    }

    private void showDialogDayBill() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonActivity.this);
        builder.setTitle("XÁC NHẬN");
        builder.setMessage("Đẩy hóa đơn?");
        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getDataCheckTrue() {
        Intent intent = getIntent();
        table = intent.getStringExtra("table");
    }

    private void initViewsCheckFalse() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String[] str = timeStamp.split("_");
        String[] str2 = str[0].split("");
        String year = str2[1]+str2[2]+str2[3]+str2[4];
        String month = str2[5]+str2[6];
        String day = str2[7]+str2[8];

        String[] str3 = str[1].split("");
        String hour = str3[1]+str3[2];
        String minute = str3[3]+str3[4];
        String sec = str3[5]+str3[6];
        tvTime.setText("Thời gian: "+day+"/"+month+"/"+year+"_"+hour+":"+minute+":"+sec);

        adapterHienThiHoaDon = new AdapterHienThiHoaDon(HoaDonActivity.this, R.layout.custom_layout_hienthihoadon, listThucUong);
        lvHoaDon.setAdapter(adapterHienThiHoaDon);
        adapterHienThiHoaDon.notifyDataSetChanged();


    }

    private void getDataCheckFalse() {
        listThucUong = new ArrayList<>();
        Intent intent = getIntent();
        listThucUong = (ArrayList<ThucUong>) intent.getSerializableExtra("list");
        String soBan= intent.getStringExtra("table");
        tvTable.setText("Bàn Số: "+soBan);
    }
}
