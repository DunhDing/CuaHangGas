package com.tuandat.cuahanggas.utils;

import com.tuandat.cuahanggas.model.TaiKhoanNguoiDung;

public class Session {

    private static TaiKhoanNguoiDung currentUser;

    public static void setCurrentUser(TaiKhoanNguoiDung user) {
        currentUser = user;
    }

    public static TaiKhoanNguoiDung getCurrentUser() {
        return currentUser;
    }
    public static String MaNhanVien;
    public static String TenNhanVien; // Có thể thêm các thông tin khác nếu cần
    private static boolean isLoggedIn = false;

 
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
