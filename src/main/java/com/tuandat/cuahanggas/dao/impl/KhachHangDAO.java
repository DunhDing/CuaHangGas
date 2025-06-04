/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandat.cuahanggas.dao.impl;

import com.tuandat.cuahanggas.dao.AbstractDAO;
import com.tuandat.cuahanggas.model.KhachHang;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.sql.Date; // Quan trọng: Sử dụng java.sql.Date khi làm việc với ResultSet cho cột DATE

public class KhachHangDAO extends AbstractDAO<KhachHang> {

    public KhachHangDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() {
        return "KhachHang";
    }

    @Override
    protected KhachHang mapResultSetToEntity(ResultSet rs) throws SQLException {
        KhachHang khachHang = new KhachHang();
        khachHang.setMaKhachHang(rs.getString("MaKhachHang"));
        khachHang.setHoTen(rs.getString("HoTen"));
        khachHang.setGioiTinh(rs.getString("GioiTinh"));
        // Chuyển đổi java.sql.Date sang java.util.Date
        Date sqlDate = rs.getDate("NgaySinh");
        if (sqlDate != null) {
            khachHang.setNgaySinh(new java.util.Date(sqlDate.getTime()));
        } else {
            khachHang.setNgaySinh(null); // Hoặc giá trị mặc định nếu cần
        }
        khachHang.setSdt(rs.getString("SDT"));
        khachHang.setEmail(rs.getString("Email"));
        khachHang.setDiaChi(rs.getString("DiaChi"));
        khachHang.setGhiChu(rs.getString("GhiChu"));
        return khachHang;
    }

    @Override
    protected Object getEntityId(KhachHang entity) {
        return entity.getMaKhachHang();
    }

    @Override
    protected String getIdColumnName() {
        return "MaKhachHang"; // Tên cột khóa chính trong DB
    }

    @Override
    protected Map<String, Object> getInsertValues(KhachHang entity) {
        Map<String, Object> values = new HashMap<>();
        values.put("MaKhachHang", entity.getMaKhachHang());
        values.put("HoTen", entity.getHoTen());
        values.put("GioiTinh", entity.getGioiTinh());
        // Chuyển đổi java.util.Date sang java.sql.Date để chèn vào DB
        values.put("NgaySinh", entity.getNgaySinh() != null ? new Date(entity.getNgaySinh().getTime()) : null);
        values.put("SDT", entity.getSdt());
        values.put("Email", entity.getEmail());
        values.put("DiaChi", entity.getDiaChi());
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }

    @Override
    protected Map<String, Object> getUpdateValues(KhachHang entity) {
        Map<String, Object> values = new HashMap<>();
        // Không bao gồm khóa chính ở đây nếu nó không được phép cập nhật
        values.put("HoTen", entity.getHoTen());
        values.put("GioiTinh", entity.getGioiTinh());
        values.put("NgaySinh", entity.getNgaySinh() != null ? new Date(entity.getNgaySinh().getTime()) : null);
        values.put("SDT", entity.getSdt());
        values.put("Email", entity.getEmail());
        values.put("DiaChi", entity.getDiaChi());
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }
}
