package com.example.quanlytrasua.Model;

import java.util.ArrayList;

public class HoaDon {
    private String idBill;
    private int table;
    private String total;
    private ArrayList<ThucUong> arrayList;
    private String time;

    public HoaDon(String idBill, int table, String total, ArrayList<ThucUong> arrayList, String time) {
        this.idBill = idBill;
        this.table = table;
        this.total = total;
        this.arrayList = arrayList;
        this.time = time;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<ThucUong> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ThucUong> arrayList) {
        this.arrayList = arrayList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
