package com.example.quanlytrasua.Model;

public class chitiethoadon {
    private String id;
    private String maHoaDon;
    private String maThucUong;
    private int soLuong;

    public chitiethoadon() {
    }

    public chitiethoadon(String id, String maHoaDon, String maThucUong, int soLuong) {
        this.id = id;
        this.maHoaDon = maHoaDon;
        this.maThucUong = maThucUong;
        this.soLuong = soLuong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaThucUong() {
        return maThucUong;
    }

    public void setMaThucUong(String maThucUong) {
        this.maThucUong = maThucUong;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
