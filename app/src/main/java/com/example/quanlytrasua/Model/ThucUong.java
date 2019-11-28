package com.example.quanlytrasua.Model;

import java.io.Serializable;

public class ThucUong implements Serializable {
    private String id;
    private String tenThucUong;
    private long gia;
    private int maLoai;
    private String anh;
    private int count=0;
    private String tenLoai;

    public ThucUong() {
    }

    public ThucUong(String tenThucUong, long gia, int count) {
        this.tenThucUong = tenThucUong;
        this.gia = gia;
        this.count = count;
    }

    public ThucUong(String id_thucuong, String tenthucuong, long gia, int maloai, String anh, int soluong, String tenloai) {
        this.id = id_thucuong;
        this.tenThucUong = tenthucuong;
        this.gia = gia;
        this.maLoai = maloai;
        this.anh = anh;
        this.count = soluong;
        this.tenLoai = tenloai;
    }

    public ThucUong(String id, String tenThucUong, long gia, int maLoai, String anh, String tenLoai) {
        this.id = id;
        this.tenThucUong = tenThucUong;
        this.gia = gia;
        this.maLoai = maLoai;
        this.anh = anh;
        this.count = 0;
        this.tenLoai = tenLoai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
