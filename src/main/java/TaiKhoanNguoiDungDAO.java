package com.tuandat.cuahanggas.dao.impl;

import com.tuandat.cuahanggas.dao.AbstractDAO;
import com.tuandat.cuahanggas.model.TaiKhoanNguoiDung;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
}