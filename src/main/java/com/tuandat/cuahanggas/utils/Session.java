package com.tuandat.cuahanggas.utils;

import com.tuandat.cuahanggas.model.TaiKhoanNguoiDung;

public class Session {

    private static TaiKhoanNguoiDung currentUser; // Lưu trữ toàn bộ đối tượng người dùng
    public static String MaNhanVien; // Mã nhân viên của người dùng đã đăng nhập
    public static String TenNhanVien; // Tên nhân viên của người dùng đã đăng nhập
    private static boolean isLoggedIn = false; // Trạng thái đăng nhập

    // Phương thức để thiết lập đối tượng người dùng hiện tại
    public static void setCurrentUser(TaiKhoanNguoiDung user) {
        currentUser = user;
    }

    // Phương thức để lấy đối tượng người dùng hiện tại
    public static TaiKhoanNguoiDung getCurrentUser() {
        return currentUser;
    }

    /**
     * Phương thức kiểm tra trạng thái đăng nhập.
     * Trả về true nếu người dùng đã đăng nhập và thông tin cần thiết không rỗng.
     */
    public static boolean IsLoggedIn() {
        // Đảm bảo cả cờ isLoggedIn và thông tin người dùng (MaNhanVien) đều hợp lệ
        return isLoggedIn && currentUser != null && MaNhanVien != null && !MaNhanVien.trim().isEmpty();
    }

    /**
     * Thiết lập trạng thái đăng nhập.
     * @param status true để đánh dấu đã đăng nhập, false để đánh dấu đã đăng xuất.
     */
    public static void setLoggedIn(boolean status) {
        isLoggedIn = status;
    }

    public static void login(TaiKhoanNguoiDung user) {
        if (user != null) {
            Session.currentUser = user;
            Session.MaNhanVien = user.getMaNhanVien();
            Session.TenNhanVien = user.getTenNhanVien(); 
            Session.setLoggedIn(true);
        } else {
            // Xử lý trường hợp đối tượng user là null (có thể reset phiên)
            logout();
        }
    }

    public static void logout() {
        currentUser = null;
        MaNhanVien = null;
        TenNhanVien = null;
        isLoggedIn = false;
    }
}