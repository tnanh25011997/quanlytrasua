package com.example.quanlytrasua;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlytrasua.CustomAdapter.AdapterHienThiBill;
import com.example.quanlytrasua.Model.ThucUong;

import java.util.ArrayList;

import javax.xml.transform.Result;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillActivity extends AppCompatActivity {
    AdapterHienThiBill adapterHienThiBill;
    public static boolean CHECK_START_MENU = false;
    public static int CODE_CHECK = 1;
    private ArrayList<ThucUong> arrayListBill;
    @BindView(R.id.lvItemBill)
    ListView lvBill;
    @BindView(R.id.tvTableBill)
    TextView tvBan;
    @BindView(R.id.tvTimeBill)
    TextView tvThoiGian;
    @BindView(R.id.btnAddItem)
    ImageView imvAdd;
    @BindView(R.id.btnPushBill)
    ImageView imvRefesh;
    @BindView(R.id.btnPrintBill)
    ImageView imvSave;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_CHECK){
            if(resultCode == RESULT_OK){
                ArrayList<ThucUong> arr = new ArrayList<>();
                arr = (ArrayList<ThucUong>) data.getSerializableExtra("result");
                int len = arr.size();
                int len2 = arrayListBill.size();
                for(int i=0;i<len;i++){
                    for(int j=0;j<len2;j++){
                        if(arr.get(i).getId()==arrayListBill.get(j).getId()){
                            arrayListBill.get(j).setCount(arr.get(i).getCount()+arrayListBill.get(j).getCount());
                            break;
                        }else{
                            arrayListBill.add(arr.get(i));
                            break;
                        }
                    }
                }
                adapterHienThiBill.notifyDataSetChanged();
            }
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        ButterKnife.bind(this);

        addEvent();
    }

    private void addEvent() {
        Intent intent = getIntent();
        arrayListBill = new ArrayList<>();
        arrayListBill = (ArrayList<ThucUong>) intent.getSerializableExtra("listBill");
        adapterHienThiBill = new AdapterHienThiBill(this,R.layout.activity_custom_hien_thi_bill,arrayListBill);
        lvBill.setAdapter(adapterHienThiBill);
        CHECK_START_MENU = true;
        imvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BillActivity.this,ThucUongActivity.class);
                startActivityForResult(intent,CODE_CHECK);
            }
        });
    }


}
