// File: com/tuandat/cuahanggas/dao/impl/NhapHangDAO.java
package com.tuandat.cuahanggas.dao.impl;

import com.tuandat.cuahanggas.dao.AbstractDAO; // Ensure this import is correct
import com.tuandat.cuahanggas.model.NhapHang;    // Ensure this import is correct for your entity
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NhapHangDAO extends AbstractDAO<NhapHang> {

    public NhapHangDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() {
        return "NhapHang";
    }

    @Override
    protected NhapHang mapResultSetToEntity(ResultSet rs) throws SQLException {
        NhapHang nhapHang = new NhapHang();
        nhapHang.setMaNhapHang(rs.getString("MaNhapHang"));
        
        // Convert SQL Date to Java Date
        java.sql.Date sqlDate = rs.getDate("NgayNhap");
        if (sqlDate != null) {
            nhapHang.setNgayNhap(new Date(sqlDate.getTime()));
        }
        
        nhapHang.setMaNhaCungCap(rs.getString("MaNhaCungCap"));
        nhapHang.setMaNhanVien(rs.getString("MaNhanVien"));
        nhapHang.setGhiChu(rs.getString("GhiChu"));
        return nhapHang;
    }

    @Override
    protected Object getEntityId(NhapHang entity) {
        return entity.getMaNhapHang();
    }

    @Override
    protected String getIdColumnName() {
        return "MaNhapHang";
    }

    @Override
    protected Map<String, Object> getInsertValues(NhapHang entity) {
        Map<String, Object> values = new HashMap<>();
        values.put("MaNhapHang", entity.getMaNhapHang());
        
        // Convert Java Date to SQL Date for insertion
        if (entity.getNgayNhap() != null) {
            values.put("NgayNhap", new java.sql.Date(entity.getNgayNhap().getTime()));
        } else {
            values.put("NgayNhap", null); // Or handle as per your business logic
        }
        
        values.put("MaNhaCungCap", entity.getMaNhaCungCap());
        values.put("MaNhanVien", entity.getMaNhanVien());
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }

    @Override
    protected Map<String, Object> getUpdateValues(NhapHang entity) {
        Map<String, Object> values = new HashMap<>();
        // Note: MaNhapHang is the primary key, typically not updated directly.
        // Updateable fields:
        if (entity.getNgayNhap() != null) {
            values.put("NgayNhap", new java.sql.Date(entity.getNgayNhap().getTime()));
        } else {
            values.put("NgayNhap", null);
        }
        values.put("MaNhaCungCap", entity.getMaNhaCungCap());
        values.put("MaNhanVien", entity.getMaNhanVien());
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }

    // You can add specific queries here if needed, e.g., find imports by supplier or employee
//    public List<NhapHang> findByMaNhaCungCap(String maNhaCungCap) {
//        return findByColumn("MaNhaCungCap", maNhaCungCap);
//    }
//
//    public List<NhapHang> findByMaNhanVien(String maNhanVien) {
//        return findByColumn("MaNhanVien", maNhanVien);
//    }

    public List<NhapHang> findByDateRange(Date startDate, Date endDate) {
        List<NhapHang> list = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE NgayNhap BETWEEN ? AND ?";
        System.out.println("SQL findByDateRange (NhapHang): " + sql);
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(startDate.getTime()));
            stmt.setDate(2, new java.sql.Date(endDate.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding NhapHang by date range: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}