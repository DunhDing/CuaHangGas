// File: com/tuandat/cuahanggas/dao/impl/XuatHangDAO.java
package com.tuandat.cuahanggas.dao.impl;

import com.tuandat.cuahanggas.dao.AbstractDAO; // Ensure this import is correct
import com.tuandat.cuahanggas.model.XuatHang;    // Ensure this import is correct for your entity
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XuatHangDAO extends AbstractDAO<XuatHang> {

    public XuatHangDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() {
        return "XuatHang";
    }

    @Override
    protected XuatHang mapResultSetToEntity(ResultSet rs) throws SQLException {
        XuatHang xuatHang = new XuatHang();
        xuatHang.setMaXuatHang(rs.getString("MaXuatHang"));
        
        // Convert SQL Date to Java Date
        java.sql.Date sqlDate = rs.getDate("NgayXuat");
        if (sqlDate != null) {
            xuatHang.setNgayXuat(new Date(sqlDate.getTime()));
        }
        
        xuatHang.setMaKhachHang(rs.getString("MaKhachHang"));
        xuatHang.setMaNhanVien(rs.getString("MaNhanVien"));
        xuatHang.voidsetGhiChu(rs.getString("GhiChu")); // Corrected method name: setGhiChu
        return xuatHang;
    }

    @Override
    protected Object getEntityId(XuatHang entity) {
        return entity.getMaXuatHang();
    }

    @Override
    protected String getIdColumnName() {
        return "MaXuatHang";
    }

    @Override
    protected Map<String, Object> getInsertValues(XuatHang entity) {
        Map<String, Object> values = new HashMap<>();
        values.put("MaXuatHang", entity.getMaXuatHang());
        
        // Convert Java Date to SQL Date for insertion
        if (entity.getNgayXuat() != null) {
            values.put("NgayXuat", new java.sql.Date(entity.getNgayXuat().getTime()));
        } else {
            values.put("NgayXuat", null); // Or handle as per your business logic (e.g., throw error)
        }
        
        values.put("MaKhachHang", entity.getMaKhachHang());
        values.put("MaNhanVien", entity.getMaNhanVien());
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }

    @Override
    protected Map<String, Object> getUpdateValues(XuatHang entity) {
        Map<String, Object> values = new HashMap<>();
        // Note: MaXuatHang is the primary key, typically not updated directly.
        // Updateable fields:
        if (entity.getNgayXuat() != null) {
            values.put("NgayXuat", new java.sql.Date(entity.getNgayXuat().getTime()));
        } else {
            values.put("NgayXuat", null);
        }
        values.put("MaKhachHang", entity.getMaKhachHang());
        values.put("MaNhanVien", entity.getMaNhanVien());
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }

    // You can add specific queries here if needed, e.g., find exports by customer or employee
//    public List<XuatHang> findByMaKhachHang(String maKhachHang) {
//        return findByColumn("MaKhachHang", maKhachHang);
//    }
//
//    public List<XuatHang> findByMaNhanVien(String maNhanVien) {
//        return findByColumn("MaNhanVien", maNhanVien);
//    }

    public List<XuatHang> findByDateRange(Date startDate, Date endDate) {
        List<XuatHang> list = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE NgayXuat BETWEEN ? AND ?";
        System.out.println("SQL findByDateRange: " + sql);
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(startDate.getTime()));
            stmt.setDate(2, new java.sql.Date(endDate.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding XuatHang by date range: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}