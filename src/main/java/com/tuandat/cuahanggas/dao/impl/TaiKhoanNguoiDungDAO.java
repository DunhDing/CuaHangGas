package com.tuandat.cuahanggas.dao.impl;

import com.tuandat.cuahanggas.dao.AbstractDAO;
import com.tuandat.cuahanggas.model.TaiKhoanNguoiDung;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaiKhoanNguoiDungDAO extends AbstractDAO<TaiKhoanNguoiDung> {

    public TaiKhoanNguoiDungDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() {
        return "TaiKhoanNguoiDung";
    }

    @Override
    protected TaiKhoanNguoiDung mapResultSetToEntity(ResultSet rs) throws SQLException {
        TaiKhoanNguoiDung taiKhoan = new TaiKhoanNguoiDung();
        taiKhoan.setMaTaiKhoan(rs.getString("MaTaiKhoan"));
        taiKhoan.setTenDangNhap(rs.getString("TenDangNhap"));
        taiKhoan.setMatKhau(rs.getString("MatKhau"));
        taiKhoan.setMaVaiTro(rs.getString("MaVaiTro"));
        taiKhoan.setMaNhanVien(rs.getString("MaNhanVien")); // Có thể là null
        taiKhoan.setGhiChu(rs.getString("GhiChu"));
        return taiKhoan;
    }

    @Override
    protected Object getEntityId(TaiKhoanNguoiDung entity) {
        return entity.getMaTaiKhoan();
    }

    @Override
    protected String getIdColumnName() {
        return "MaTaiKhoan"; // Tên cột khóa chính trong DB

    }

    @Override
    protected Map<String, Object> getInsertValues(TaiKhoanNguoiDung entity) {
        Map<String, Object> values = new HashMap<>();
        values.put("MaTaiKhoan", entity.getMaTaiKhoan());
        values.put("TenDangNhap", entity.getTenDangNhap());
        values.put("MatKhau", entity.getMatKhau());
        values.put("MaVaiTro", entity.getMaVaiTro());
        values.put("MaNhanVien", entity.getMaNhanVien());
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }

    @Override
    protected Map<String, Object> getUpdateValues(TaiKhoanNguoiDung entity) {
        Map<String, Object> values = new HashMap<>();
        values.put("TenDangNhap", entity.getTenDangNhap());
        values.put("MatKhau", entity.getMatKhau());
        values.put("MaVaiTro", entity.getMaVaiTro());
        values.put("MaNhanVien", entity.getMaNhanVien()); // Có thể là null
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }

    public List<Map<String, String>> getTaiKhoanVoiTenLienKet() {
        List<Map<String, String>> list = new ArrayList<>();
        String sql = """
    SELECT tk.MaTaiKhoan, tk.TenDangNhap, tk.MatKhau,
           vt.TenVaiTro, nv.HoTen AS TenNhanVien, tk.GhiChu
    FROM TaiKhoanNguoiDung tk
    JOIN VaiTroTaiKhoan vt ON tk.MaVaiTro = vt.MaVaiTro
    LEFT JOIN NhanVien nv ON tk.MaNhanVien = nv.MaNhanVien
""";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("MaTaiKhoan", rs.getString("MaTaiKhoan"));
                row.put("TenDangNhap", rs.getString("TenDangNhap"));
                row.put("MatKhau", rs.getString("MatKhau"));
                row.put("TenVaiTro", rs.getString("TenVaiTro"));
                row.put("TenNhanVien", rs.getString("TenNhanVien"));
                row.put("GhiChu", rs.getString("GhiChu"));
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<String> getDanhSachTenVaiTro() {
        List<String> ds = new ArrayList<>();
        String sql = "SELECT TenVaiTro FROM VaiTroTaiKhoan";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ds.add(rs.getString("TenVaiTro"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public Map<String, String> getDanhSachVaiTro() {
        Map<String, String> vaiTros = new HashMap<>();
        String sql = "SELECT MaVaiTro, TenVaiTro FROM VaiTroTaiKhoan";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String maVaiTro = rs.getString("MaVaiTro");
                String tenVaiTro = rs.getString("TenVaiTro");
                vaiTros.put(tenVaiTro, maVaiTro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vaiTros;
    }

    public List<Map<String, String>> timKiemTaiKhoan(String keyword, String tenVaiTro) {
        List<Map<String, String>> list = new ArrayList<>();

        String sql = """
        SELECT tk.MaTaiKhoan, tk.TenDangNhap, tk.MatKhau,
               vt.TenVaiTro, nv.HoTen AS TenNhanVien, tk.GhiChu
        FROM TaiKhoanNguoiDung tk
        JOIN VaiTroTaiKhoan vt ON tk.MaVaiTro = vt.MaVaiTro
        LEFT JOIN NhanVien nv ON tk.MaNhanVien = nv.MaNhanVien
        WHERE (tk.MaTaiKhoan LIKE ? OR tk.TenDangNhap LIKE ?)
    """;

        // Nếu không phải "Tất cả"
        if (tenVaiTro != null && !tenVaiTro.equalsIgnoreCase("Tất cả")) {
            sql += " AND vt.TenVaiTro = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String param = "%" + keyword + "%";
            stmt.setString(1, param);
            stmt.setString(2, param);

            if (tenVaiTro != null && !tenVaiTro.equalsIgnoreCase("Tất cả")) {
                stmt.setString(3, tenVaiTro);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("MaTaiKhoan", rs.getString("MaTaiKhoan"));
                    row.put("TenDangNhap", rs.getString("TenDangNhap"));
                    row.put("MatKhau", rs.getString("MatKhau"));
                    row.put("TenVaiTro", rs.getString("TenVaiTro"));
                    row.put("TenNhanVien", rs.getString("TenNhanVien"));
                    row.put("GhiChu", rs.getString("GhiChu"));
                    list.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public TaiKhoanNguoiDung getUserByUsername(String username) {
        String query = "SELECT * FROM TaiKhoanNguoiDung WHERE TenDangNhap = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Tạo đối tượng TaiKhoanNguoiDung từ kết quả truy vấn
                TaiKhoanNguoiDung user = new TaiKhoanNguoiDung();
                user.setMaTaiKhoan(rs.getString("MaTaiKhoan"));
                user.setTenDangNhap(rs.getString("TenDangNhap"));
                user.setMatKhau(rs.getString("MatKhau"));
                user.setGhiChu(rs.getString("GhiChu"));
                user.setMaVaiTro(rs.getString("MaVaiTro"));
                user.setMaNhanVien(rs.getString("MaNhanVien"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
}
