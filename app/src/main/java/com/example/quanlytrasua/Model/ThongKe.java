package com.example.quanlytrasua.Model;

import java.util.ArrayList;

public class ThongKe {
    private int idHoaDon;
    private int idBan;
    private String tongTien;
    private String ngay;
    private ArrayList<ThucUong> listThucUong;

    public ThongKe(int idHoaDon, int idBan, String tongTien, String ngay) {
        this.idHoaDon = idHoaDon;
        this.idBan = idBan;
        this.tongTien = tongTien;
        this.ngay = ngay;

    }

    public int getIdHoaDon() {
        return idHoaDon;
    }

    public int getIdBan() {
        return idBan;
    }

    public String getTongTien() {
        return tongTien;
    }

    public String getNgay() {
        return ngay;
    }

    public ArrayList<ThucUong> getListThucUong() {
        return listThucUong;
    }
}
