/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandat.cuahanggas.dao.impl;

import com.tuandat.cuahanggas.dao.AbstractDAO;
import com.tuandat.cuahanggas.model.NhanVien;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.sql.Date; // Quan trọng: Sử dụng java.sql.Date khi làm việc với ResultSet cho cột DATE

public class NhanVienDAO extends AbstractDAO<NhanVien> {

    public NhanVienDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() {
        return "NhanVien";
    }

    @Override
    protected NhanVien mapResultSetToEntity(ResultSet rs) throws SQLException {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNhanVien(rs.getString("MaNhanVien"));
        nhanVien.setHoTen(rs.getString("HoTen"));
        nhanVien.setGioiTinh(rs.getString("GioiTinh"));
        // Chuyển đổi java.sql.Date sang java.util.Date
        Date sqlDate = rs.getDate("NgaySinh");
        if (sqlDate != null) {
            nhanVien.setNgaySinh(new java.util.Date(sqlDate.getTime()));
        } else {
            nhanVien.setNgaySinh(null); // Hoặc giá trị mặc định nếu cần
        }
        nhanVien.setSdt(rs.getString("SDT"));
        nhanVien.setEmail(rs.getString("Email"));
        nhanVien.setDiaChi(rs.getString("DiaChi"));
        nhanVien.setGhiChu(rs.getString("GhiChu"));
        return nhanVien;
    }

    @Override
    protected Object getEntityId(NhanVien entity) {
        return entity.getMaNhanVien();
    }

    @Override
    protected String getIdColumnName() {
        return "MaNhanVien"; // Tên cột khóa chính trong DB
    }

    @Override
    protected Map<String, Object> getInsertValues(NhanVien entity) {
        Map<String, Object> values = new HashMap<>();
        values.put("MaNhanVien", entity.getMaNhanVien());
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
    protected Map<String, Object> getUpdateValues(NhanVien entity) {
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
