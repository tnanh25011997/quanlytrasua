package com.example.quanlytrasua.Model;

import java.util.Date;

public class HoaDon2 {
    private String id;
    private int maBan;
    private String ngayTao;
    private int tinhTrang;
    private long thanhTien;

    public HoaDon2() {
    }

    public HoaDon2(String id, int maBan, String ngayTao, int tinhTrang, long thanhTien) {
        this.id = id;
        this.maBan = maBan;
        this.ngayTao = ngayTao;
        this.tinhTrang = tinhTrang;
        this.thanhTien = thanhTien;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public long getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(long thanhTien) {
        this.thanhTien = thanhTien;
    }
}
