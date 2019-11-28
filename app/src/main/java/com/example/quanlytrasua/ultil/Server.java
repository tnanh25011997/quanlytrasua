package com.example.quanlytrasua.ultil;

public class Server {
    public static String localhost ="192.168.1.7";
    public static String PORT = "http://192.168.1.7:3000/";
    //public static String PORT = "http://172.16.1.121:3000/";
    //public static String localhost = "172.16.1.121";
    public static String DuongDanAnh = "http://"+localhost+"/quanlytrasua/image/";
    public static String DuongDanBan = "http://"+localhost+"/quanlytrasua/mvc/BanController/getListBan/";
    public static String DuongDanLogin = "http://"+localhost+"/quanlytrasua/mvc/UserController/checkLogin/";
    public static String DuongDanThucUong = "http://"+localhost+"/quanlytrasua/mvc/ThucUongController/getAllList/";
    public static String DuongDanLoaiThucUong = "http://"+localhost+"/quanlytrasua/mvc/LoaiController/getListLoai/";
    public static String DuongDanCapNhatBan = "http://"+localhost+"/quanlytrasua/mvc/BanController/updateBan/";
    public static String DuongDanThemVaoHoaDon = "http://"+localhost+"/quanlytrasua/mvc/HoaDonController/insertHoaDon/";
    public static String DuongDanThemVaoCTHoaDon = "http://"+localhost+"/quanlytrasua/mvc/ChiTietHoaDonController/insertChiTiet/";
    public static String DuongDanGetDataBan = "http://"+localhost+"/quanlytrasua/mvc/DatabanController/getDataBan/";
    public static String DuongDanXoaCTHD = "http://"+localhost+"/quanlytrasua/mvc/ChiTietHoaDonController/deleteChiTiet/";
    public static String DuongDanCapNhatTrangThaiHoaDon = "http://"+localhost+"/quanlytrasua/mvc/HoaDonController/updateTinhTrang/";
    public static String DuongDanThongKe = "http://"+localhost+"/quanlytrasua/mvc/HoaDonController/getListHoaDon/";
    public static String DuongDanThongKeThucUong = "http://"+localhost+"/quanlytrasua/mvc/DataThongKeController/getDataHoaDon/";
}
