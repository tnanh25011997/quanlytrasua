package com.example.quanlytrasua.FragmentApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlytrasua.BillActivity;
import com.example.quanlytrasua.CustomAdapter.AdapterHienThiBan;
import com.example.quanlytrasua.HoaDonActivity;
import com.example.quanlytrasua.Model.BanDTO;
import com.example.quanlytrasua.R;
import com.example.quanlytrasua.ThucUongActivity;
import com.example.quanlytrasua.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HienThiBanFragment extends Fragment {
    GridView gvHienThiBan;
    List<BanDTO> banDTOList;
    AdapterHienThiBan adapterHienThiBan;
    int id = 0;
    String tenBan = "";
    int tinhTrang = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthiban,container,false);
        gvHienThiBan = view.findViewById(R.id.gvHienThiBan);
        banDTOList = new ArrayList<BanDTO>();
        adapterHienThiBan = new AdapterHienThiBan(getActivity(), R.layout.custom_layout_hienthiban, banDTOList);
        gvHienThiBan.setAdapter(adapterHienThiBan);
        GetDuLieuBan();
        gvHienThiBan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (adapterView.getId())
                {
                    case R.id.gvHienThiBan:
                        BanDTO table = banDTOList.get(i);
                        int check = table.getTinhTrang();
                        int maBan = table.getMaBan();
                        if (check == 0)
                        {
                            Intent intent = new Intent(getActivity(), ThucUongActivity.class);
                            intent.putExtra("table",maBan+"");
                            startActivity(intent);
                        }
                        else if (check == 1)
                        {
                            Intent intent = new Intent(getActivity(), BillActivity.class);
                            intent.putExtra("table",maBan+"");
                            BillActivity.CHECK_START_MENU = true;
                            startActivity(intent);
                        }
                        break;
                }
            }
        });
        return view;


    }



    private void GetDuLieuBan() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanBan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response !=null){
                    for(int i=0; i<response.length(); i++){

                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("ID");
                            tenBan = jsonObject.getString("TenBan");
                            tinhTrang = jsonObject.getInt("TinhTrang");
                            banDTOList.add(new BanDTO(id,tenBan, tinhTrang));
                            adapterHienThiBan.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
