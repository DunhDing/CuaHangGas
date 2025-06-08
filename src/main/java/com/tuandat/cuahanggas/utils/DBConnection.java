package com.tuandat.cuahanggas.utils;

import com.tuandat.cuahanggas.model.TaiKhoanNguoiDung;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=CuaHangGas;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "KIEN2005";
    private static Connection connection;

    public static Connection openConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("Lỗi khi kết nối cơ sở dữ liệu: " + e.getMessage());
            return null;
        }
    }
public TaiKhoanNguoiDung getByUsernameAndPassword(String username, String password) {
    String sql = "SELECT * FROM TaiKhoanNguoiDung WHERE TenDangNhap = ? AND MatKhau = ?";
    try (Connection conn = DBConnection.openConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            TaiKhoanNguoiDung acc = new TaiKhoanNguoiDung();
            acc.setMaNhanVien(rs.getString("MaNhanVien"));
            acc.setTenDangNhap(rs.getString("TenDangNhap"));
            acc.setMatKhau(rs.getString("MatKhau"));
            return acc;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
        }
    }
}
