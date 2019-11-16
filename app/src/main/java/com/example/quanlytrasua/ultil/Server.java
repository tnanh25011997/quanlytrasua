package com.example.quanlytrasua.ultil;

public class Server {

        public static String localhost ="192.168.0.101:8080";
    //public static String localhost = "172.16.3.69";
    public static String DuongDanAnh = "http://"+localhost+"/trasua/image/";
    public static String DuongDanBan = "http://"+localhost+"/trasua/mvc/BanController/getListBan/";
    public static String DuongDanLogin = "http://"+localhost+"/trasua/mvc/UserController/checkLogin/";
    public static String DuongDanThucUong = "http://"+localhost+"/trasua/mvc/ThucUongController/getAllList/";
    public static String DuongDanLoaiThucUong = "http://"+localhost+"/trasua/mvc/LoaiController/getListLoai/";
    public static String DuongDanCapNhatBan = "http://"+localhost+"/trasua/mvc/BanController/updateBan/";
    public static String DuongDanThemVaoHoaDon = "http://"+localhost+"/trasua/mvc/HoaDonController/insertHoaDon/";
    public static String DuongDanThemVaoCTHoaDon = "http://"+localhost+"/trasua/mvc/ChiTietHoaDonController/insertChiTiet/";
    public static String DuongDanThemVaoGetMax = "http://"+localhost+"/trasua/mvc/HoaDonController/getMaxId/";
}
