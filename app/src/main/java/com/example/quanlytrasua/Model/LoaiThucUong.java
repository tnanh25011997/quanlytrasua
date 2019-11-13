package com.example.quanlytrasua.Model;

import androidx.annotation.NonNull;

public class LoaiThucUong {
    private int id;
    private String tenLoai;


    public LoaiThucUong(int id, String tenLoai) {
        this.id = id;
        this.tenLoai = tenLoai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    @NonNull
    @Override
    public String toString() {
        return this.tenLoai;
    }
}
