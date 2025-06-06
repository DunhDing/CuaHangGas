package com.tuandat.cuahanggas.utils;

/**
 * Lớp Session tĩnh để lưu trữ thông tin người dùng đang đăng nhập.
 */
public class Session {

    public static String MaNhanVien;
    public static String TenNhanVien; // Có thể thêm các thông tin khác nếu cần
    private static boolean isLoggedIn = false;

    /**
     * Kiểm tra xem người dùng đã đăng nhập hay chưa.
     * @return true nếu đã đăng nhập và MaNhanVien không rỗng, false nếu ngược lại.
     */
    public static boolean IsLoggedIn() {
        return isLoggedIn && MaNhanVien != null && !MaNhanVien.trim().isEmpty();
    }

    /**
     * Thiết lập trạng thái đăng nhập.
     * @param status true để đánh dấu đã đăng nhập, false để đánh dấu đã đăng xuất.
     */
    public static void setLoggedIn(boolean status) {
        isLoggedIn = status;
    }

    /**
     * Phương thức để thiết lập thông tin người dùng sau khi đăng nhập thành công.
     * @param maNhanVien Mã nhân viên.
     * @param tenNhanVien Tên nhân viên.
     */
    public static void login(String maNhanVien, String tenNhanVien) {
        Session.MaNhanVien = maNhanVien;
        Session.TenNhanVien = tenNhanVien;
        Session.setLoggedIn(true);
    }

    /**
     * Phương thức để xóa thông tin người dùng khi đăng xuất.
     */
    public static void logout() {
        Session.MaNhanVien = null;
        Session.TenNhanVien = null;
        Session.setLoggedIn(false);
    }
}