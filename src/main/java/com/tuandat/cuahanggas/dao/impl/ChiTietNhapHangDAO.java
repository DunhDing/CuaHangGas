/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandat.cuahanggas.dao.impl;



import com.tuandat.cuahanggas.dao.AbstractDAO;
import com.tuandat.cuahanggas.model.ChiTietNhapHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Đảm bảo bạn đã có AbstractDAO.java trong package tương ứng
// import com.tuandat.cuahanggas.dao.AbstractDAO; // Nếu AbstractDAO ở package cha

public class ChiTietNhapHangDAO extends AbstractDAO<ChiTietNhapHang> {

    public ChiTietNhapHangDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() {
        return "ChiTietNhapHang";
    }

    @Override
    protected ChiTietNhapHang mapResultSetToEntity(ResultSet rs) throws SQLException {
        ChiTietNhapHang chiTiet = new ChiTietNhapHang();
        chiTiet.setMaNhapHang(rs.getString("MaNhapHang"));
        chiTiet.setMaBinhGas(rs.getString("MaBinhGas"));
        chiTiet.setSoLuongNhap(rs.getInt("SoLuongNhap"));
        chiTiet.setDonGiaNhap(rs.getInt("DonGiaNhap"));
        chiTiet.setGhiChu(rs.getString("GhiChu"));
        return chiTiet;
    }

    @Override
    protected Object getEntityId(ChiTietNhapHang entity) {
        // Đối với khóa chính tổng hợp, chúng ta có thể trả về một chuỗi kết hợp
        // Điều này chỉ là một cách để thỏa mãn interface của AbstractDAO cho các phương thức
        // như 'insert' và 'getAll' mà không cần thay đổi AbstractDAO.
        // Tuy nhiên, nó không thể được sử dụng trực tiếp cho findById(Object id) hoặc delete(Object id)
        // của AbstractDAO.
        return entity.getMaNhapHang() + "_" + entity.getMaBinhGas();
    }

    @Override
    protected String getIdColumnName() {
        // Tương tự như getEntityId, đây là một cách để thỏa mãn AbstractDAO.
        // Nó không thực sự có ý nghĩa cho khóa tổng hợp.
        // Chúng ta sẽ triển khai các phương thức findById và delete riêng.
        return "MaNhapHang_MaBinhGas_CompositeKey"; // Tên cột giả
    }

    @Override
    protected Map<String, Object> getInsertValues(ChiTietNhapHang entity) {
        Map<String, Object> values = new HashMap<>();
        values.put("MaNhapHang", entity.getMaNhapHang());
        values.put("MaBinhGas", entity.getMaBinhGas());
        values.put("SoLuongNhap", entity.getSoLuongNhap());
        values.put("DonGiaNhap", entity.getDonGiaNhap());
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }

    @Override
    protected Map<String, Object> getUpdateValues(ChiTietNhapHang entity) {
        Map<String, Object> values = new HashMap<>();
        // Không bao gồm khóa chính tổng hợp ở đây, chúng sẽ được dùng trong WHERE clause
        values.put("SoLuongNhap", entity.getSoLuongNhap());
        values.put("DonGiaNhap", entity.getDonGiaNhap());
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }

    // --- Phương thức đặc biệt cho khóa chính tổng hợp ---

    /**
     * Tìm ChiTietNhapHang theo khóa chính tổng hợp.
     * @param maNhapHang Mã nhập hàng
     * @param maBinhGas Mã bình gas
     * @return Đối tượng ChiTietNhapHang hoặc null nếu không tìm thấy
     */
    public ChiTietNhapHang findById(String maNhapHang, String maBinhGas) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE MaNhapHang = ? AND MaBinhGas = ?";
        System.out.println("SQL FindById Composite: " + sql);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNhapHang);
            stmt.setString(2, maBinhGas);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm ChiTietNhapHang theo khóa tổng hợp (" + maNhapHang + ", " + maBinhGas + "): " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Xóa ChiTietNhapHang theo khóa chính tổng hợp.
     * @param maNhapHang Mã nhập hàng
     * @param maBinhGas Mã bình gas
     * @return true nếu xóa thành công, false nếu ngược lại
     */
    public boolean delete(String maNhapHang, String maBinhGas) {
        String sql = "DELETE FROM " + getTableName() + " WHERE MaNhapHang = ? AND MaBinhGas = ?";
        System.out.println("SQL Delete Composite: " + sql);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNhapHang);
            stmt.setString(2, maBinhGas);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa ChiTietNhapHang theo khóa tổng hợp (" + maNhapHang + ", " + maBinhGas + "): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(ChiTietNhapHang entity) {
        Map<String, Object> values = getUpdateValues(entity);
        StringBuilder sql = new StringBuilder("UPDATE ").append(getTableName()).append(" SET ");

        List<String> setClauses = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            setClauses.add(entry.getKey() + " = ?");
            params.add(entry.getValue());
        }
        sql.append(String.join(", ", setClauses));
        
        // Thêm WHERE clause cho khóa chính tổng hợp
        sql.append(" WHERE MaNhapHang = ? AND MaBinhGas = ?");
        params.add(entity.getMaNhapHang());
        params.add(entity.getMaBinhGas());

        System.out.println("SQL Update Composite: " + sql.toString());

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int i = 1;
            for (Object param : params) {
                stmt.setObject(i++, param);
            }
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật ChiTietNhapHang: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
