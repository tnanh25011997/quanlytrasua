package com.example.quanlytrasua.ultil;

public class Server {

//    public static String localhost ="192.168.1.4";
    public static String localhost = "192.168.1.19:8080";
    public static String DuongDanAnh = "http://"+localhost+"/quanlytrasua/image/";
    public static String DuongDanBan = "http://"+localhost+"/quanlytrasua/mvc/BanController/getListBan/";
    public static String DuongDanLogin = "http://"+localhost+"/quanlytrasua/mvc/UserController/checkLogin/";
    public static String DuongDanThucUong = "http://"+localhost+"/quanlytrasua/mvc/ThucUongController/getAllList/";
    public static String DuongDanLoaiThucUong = "http://"+localhost+"/quanlytrasua/mvc/LoaiController/getListLoai/";
    public static String DuongDanGetThucUongByLoai = "http://"+localhost+"/quanlytrasua/mvc/ThucUongController/getListThucUongByLoai/";
    public static String DuongDanAddBill = "http://"+localhost+"/quanlytrasua/mvc/HoaDonController/insertHoaDon";
    public static String DuongDanAddBillDetail = "http://"+localhost+"/quanlytrasua/mvc/ChiTietHoaDonController/insertChiTiet";
    public static String DuongDanUpdateTinhTrangBan = "http://"+localhost+"/quanlytrasua/mvc/BanController/updateBan/";
    public static String DuongDangGetDataBan = "http://"+localhost+"/quanlytrasua/mvc/ChiTietHoaDonController/getDetailBill/";
    public static String DuongDangDeleteChiTiet = "http://"+localhost+"/quanlytrasua/mvc/ChiTietHoaDonController/deleteChiTietHoaDon/";
    public static String DuongDangUpdateHoaDon = "http://"+localhost+"/quanlytrasua/mvc/HoaDonController/updateHoaDon/";
}
