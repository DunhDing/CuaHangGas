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

    public static void logout() {
        currentUser = null;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
