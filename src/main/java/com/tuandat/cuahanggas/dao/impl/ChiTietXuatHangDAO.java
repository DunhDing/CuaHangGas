package com.tuandat.cuahanggas.dao.impl;

import com.tuandat.cuahanggas.dao.AbstractDAO;
import com.tuandat.cuahanggas.model.ChiTietXuatHang;
import java.sql.Connection; 
import java.sql.*;
import java.util.*;

public class ChiTietXuatHangDAO extends AbstractDAO<ChiTietXuatHang> {

    public ChiTietXuatHangDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() {
        return "ChiTietXuatHang";
    }

    @Override
    protected ChiTietXuatHang mapResultSetToEntity(ResultSet rs) throws SQLException {
        ChiTietXuatHang ct = new ChiTietXuatHang();
        ct.setMaXuatHang(rs.getString("MaXuatHang"));
        ct.setMaBinhGas(rs.getString("MaBinhGas"));
        ct.setSoLuongXuat(rs.getInt("SoLuongXuat"));
        ct.setDonGiaXuat(rs.getInt("DonGiaXuat"));
        ct.setGhiChu(rs.getString("GhiChu"));
        return ct;
    }

    @Override
    protected Object getEntityId(ChiTietXuatHang entity) {
        // Trả về ID duy nhất nếu có, ở đây dùng kết hợp MaXuatHang + MaBinhGas là khóa chính
        return Arrays.asList(entity.getMaXuatHang(), entity.getMaBinhGas());
    }

    @Override
    protected String getIdColumnName() {
        return "MaXuatHang"; // Chỉ dùng khi có một khóa chính
    }

    @Override
    protected Map<String, Object> getInsertValues(ChiTietXuatHang entity) {
        Map<String, Object> values = new HashMap<>();
        values.put("MaXuatHang", entity.getMaXuatHang());
        values.put("MaBinhGas", entity.getMaBinhGas());
        values.put("SoLuongXuat", entity.getSoLuongXuat());
        values.put("DonGiaXuat", entity.getDonGiaXuat());
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }

    @Override
    protected Map<String, Object> getUpdateValues(ChiTietXuatHang entity) {
        Map<String, Object> values = new HashMap<>();
        values.put("SoLuongXuat", entity.getSoLuongXuat());
        values.put("DonGiaXuat", entity.getDonGiaXuat());
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }

    public List<ChiTietXuatHang> getByMaXuatHang(String maXuatHang) throws SQLException {
        List<ChiTietXuatHang> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietXuatHang WHERE MaXuatHang = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maXuatHang);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToEntity(rs));
                }
            }
        }
        return list;
    }

   public boolean updateGhiChu(String maXuatHang, String maBinhGas, String ghiChuMoi, Connection conn) throws SQLException {
    String sql = "UPDATE ChiTietXuatHang SET GhiChu = ? WHERE MaXuatHang = ? AND MaBinhGas = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) { // Sử dụng conn được truyền vào
        ps.setString(1, ghiChuMoi);
        ps.setString(2, maXuatHang);
        ps.setString(3, maBinhGas);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;
    }
}
    public ChiTietXuatHang findById(String maXuatHang, String maBinhGas) throws SQLException {
        String sql = "SELECT * FROM ChiTietXuatHang WHERE MaXuatHang = ? AND MaBinhGas = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maXuatHang);
            ps.setString(2, maBinhGas);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        }
        return null;
    }
}
