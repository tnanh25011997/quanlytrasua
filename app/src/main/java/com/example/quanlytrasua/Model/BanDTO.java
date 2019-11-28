package com.example.quanlytrasua.Model;

public class BanDTO {
    int maBan;
    String tenBan;
    int tinhTrang;

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public BanDTO(int maBan, String tenBan, int tinhTrang) {
        this.maBan = maBan;
        this.tenBan = tenBan;
        this.tinhTrang = tinhTrang;
    }

    public BanDTO() {
    }
}
