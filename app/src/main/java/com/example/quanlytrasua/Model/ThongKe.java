package com.example.quanlytrasua.Model;

import java.util.ArrayList;

public class ThongKe {
    private String idHoaDon;
    private String idBan;
    private String tongTien;
    private String ngay;
    private ArrayList<ThucUong> listThucUong;

    public ThongKe() {
    }

    public ThongKe(String idHoaDon, String idBan, String tongTien, String ngay) {
        this.idHoaDon = idHoaDon;
        this.idBan = idBan;
        this.tongTien = tongTien;
        this.ngay = ngay;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getIdBan() {
        return idBan;
    }

    public void setIdBan(String idBan) {
        this.idBan = idBan;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public ArrayList<ThucUong> getListThucUong() {
        return listThucUong;
    }

    public void setListThucUong(ArrayList<ThucUong> listThucUong) {
        this.listThucUong = listThucUong;
    }
}
