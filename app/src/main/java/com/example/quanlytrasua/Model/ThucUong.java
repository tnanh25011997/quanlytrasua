package com.example.quanlytrasua.Model;

public class ThucUong {
    private int id;
    private String tenThucUong;
    private long gia;
    private int maLoai;
    private String anh;
    private int count=0;

    public ThucUong() {
    }

    public ThucUong(int id, String tenThucUong, long gia, int maLoai, String anh, int count) {
        this.id = id;
        this.tenThucUong = tenThucUong;
        this.gia = gia;
        this.maLoai = maLoai;
        this.anh = anh;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenThucUong() {
        return tenThucUong;
    }

    public void setTenThucUong(String tenThucUong) {
        this.tenThucUong = tenThucUong;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
