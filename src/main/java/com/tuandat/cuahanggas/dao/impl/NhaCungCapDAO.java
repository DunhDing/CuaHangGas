// File: com/tuandat/cuahanggas/dao/impl/NhaCungCapDAO.java
package com.tuandat.cuahanggas.dao.impl;

import com.tuandat.cuahanggas.dao.AbstractDAO; // Đảm bảo import này đúng
import com.tuandat.cuahanggas.model.NhaCungCap; // Đảm bảo import này đúng cho entity của bạn
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.List; // Thêm import này nếu bạn muốn các phương thức findByColumn trả về List

public class NhaCungCapDAO extends AbstractDAO<NhaCungCap> {

    public NhaCungCapDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() {
        return "NhaCungCap";
    }

    @Override
    protected NhaCungCap mapResultSetToEntity(ResultSet rs) throws SQLException {
        NhaCungCap nhaCungCap = new NhaCungCap();
        nhaCungCap.setMaNhaCungCap(rs.getString("MaNhaCungCap"));
        nhaCungCap.setTenNhaCungCap(rs.getString("TenNhaCungCap"));
        nhaCungCap.setSdt(rs.getString("SDT"));
        nhaCungCap.setEmail(rs.getString("Email"));
        nhaCungCap.setDiaChi(rs.getString("DiaChi"));
        nhaCungCap.setGhiChu(rs.getString("GhiChu"));
        return nhaCungCap;
    }

    @Override
    protected Object getEntityId(NhaCungCap entity) {
        return entity.getMaNhaCungCap();
    }

    @Override
    protected String getIdColumnName() {
        return "MaNhaCungCap";
    }

    @Override
    protected Map<String, Object> getInsertValues(NhaCungCap entity) {
        Map<String, Object> values = new HashMap<>();
        values.put("MaNhaCungCap", entity.getMaNhaCungCap());
        values.put("TenNhaCungCap", entity.getTenNhaCungCap());
        values.put("SDT", entity.getSdt());
        values.put("Email", entity.getEmail());
        values.put("DiaChi", entity.getDiaChi());
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }

    @Override
    protected Map<String, Object> getUpdateValues(NhaCungCap entity) {
        Map<String, Object> values = new HashMap<>();
        // MaNhaCungCap là khóa chính, thường không được cập nhật trực tiếp.
        // Các trường có thể cập nhật:
        values.put("TenNhaCungCap", entity.getTenNhaCungCap());
        values.put("SDT", entity.getSdt());
        values.put("Email", entity.getEmail());
        values.put("DiaChi", entity.getDiaChi());
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }

    // Các phương thức tìm kiếm cụ thể có thể hữu ích
//    public NhaCungCap findByTenNhaCungCap(String tenNhaCungCap) {
//        // Vì tên nhà cung cấp không UNIQUE, phương thức này sẽ trả về đối tượng đầu tiên tìm thấy.
//        // Nếu bạn muốn tất cả các nhà cung cấp có cùng tên, hãy tạo một phương thức trả về List.
//        List<NhaCungCap> results = findByColumn("TenNhaCungCap", tenNhaCungCap);
//        return results.isEmpty() ? null : results.get(0);
//    }
//
//    public NhaCungCap findBySdt(String sdt) {
//        // SDT là UNIQUE, nên đây là một phương thức tìm kiếm chính xác
//        List<NhaCungCap> results = findByColumn("SDT", sdt);
//        return results.isEmpty() ? null : results.get(0);
//    }
//
//    public NhaCungCap findByEmail(String email) {
//        // Email là UNIQUE, nên đây là một phương thức tìm kiếm chính xác
//        List<NhaCungCap> results = findByColumn("Email", email);
//        return results.isEmpty() ? null : results.get(0);
//    }
}