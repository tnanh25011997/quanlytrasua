package com.example.quanlytrasua.ultil;

public class Server {

//    public static String localhost ="192.168.1.4";
    public static String localhost = "192.168.1.16:8080";
    public static String DuongDanAnh = "http://"+localhost+"/trasua/image/";
    public static String DuongDanBan = "http://"+localhost+"/trasua/mvc/BanController/getListBan/";
    public static String DuongDanLogin = "http://"+localhost+"/trasua/mvc/UserController/checkLogin/";
    public static String DuongDanThucUong = "http://"+localhost+"/trasua/mvc/ThucUongController/getAllList/";
    public static String DuongDanLoaiThucUong = "http://"+localhost+"/trasua/mvc/LoaiController/getListLoai/";
    public static String DuongDanGetThucUongByLoai = "http://"+localhost+"/trasua/mvc/ThucUongController/getListThucUongByLoai/";
}
