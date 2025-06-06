// File: com/tuandat/cuahanggas/dao/impl/ChiTietXuatHangDAO.java
package com.tuandat.cuahanggas.dao.impl;

import com.tuandat.cuahanggas.dao.AbstractDAO;
import com.tuandat.cuahanggas.model.ChiTietXuatHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChiTietXuatHangDAOV2 extends AbstractDAO<ChiTietXuatHang> {

    public ChiTietXuatHangDAOV2(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() {
        return "ChiTietXuatHang";
    }

    @Override
    protected ChiTietXuatHang mapResultSetToEntity(ResultSet rs) throws SQLException {
        ChiTietXuatHang chiTiet = new ChiTietXuatHang();
        chiTiet.setMaXuatHang(rs.getString("MaXuatHang"));
        chiTiet.setMaBinhGas(rs.getString("MaBinhGas"));
        chiTiet.setSoLuongXuat(rs.getInt("SoLuongXuat"));
        chiTiet.setDonGiaXuat(rs.getInt("DonGiaXuat"));
        chiTiet.setGhiChu(rs.getString("GhiChu"));
        return chiTiet;
    }

    @Override
    protected Object getEntityId(ChiTietXuatHang entity) {
        // Chỉ để thỏa mãn AbstractDAO. Không dùng trực tiếp cho khóa tổng hợp.
        return entity.getMaXuatHang() + ":" + entity.getMaBinhGas();
    }

    @Override
    protected String getIdColumnName() {
        // Chỉ để thỏa mãn AbstractDAO. Không dùng trực tiếp cho khóa tổng hợp.
        return "CompositeKey";
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

    // --- Các phương thức OVERRIDE và THÊM MỚI cho khóa chính tổng hợp ---

    @Override
    public boolean update(ChiTietXuatHang entity) {
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
        sql.append(" WHERE MaXuatHang = ? AND MaBinhGas = ?");
        params.add(entity.getMaXuatHang()); // Tham số cho MaXuatHang
        params.add(entity.getMaBinhGas());  // Tham số cho MaBinhGas

        System.out.println("SQL Update ChiTietXuatHang (Composite): " + sql.toString());

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int i = 1;
            for (Object param : params) {
                stmt.setObject(i++, param);
            }
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật ChiTietXuatHang: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Tìm ChiTietXuatHang theo khóa chính tổng hợp.
     * @param maXuatHang Mã xuất hàng
     * @param maBinhGas Mã bình gas
     * @return Đối tượng ChiTietXuatHang hoặc null nếu không tìm thấy
     */
    public ChiTietXuatHang findById(String maXuatHang, String maBinhGas) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE MaXuatHang = ? AND MaBinhGas = ?";
        System.out.println("SQL FindById ChiTietXuatHang (Composite): " + sql);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maXuatHang);
            stmt.setString(2, maBinhGas);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm ChiTietXuatHang theo khóa tổng hợp (" + maXuatHang + ", " + maBinhGas + "): " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Xóa ChiTietXuatHang theo khóa chính tổng hợp.
     * @param maXuatHang Mã xuất hàng
     * @param maBinhGas Mã bình gas
     * @return true nếu xóa thành công, false nếu ngược lại
     */
    public boolean delete(String maXuatHang, String maBinhGas) {
        String sql = "DELETE FROM " + getTableName() + " WHERE MaXuatHang = ? AND MaBinhGas = ?";
        System.out.println("SQL Delete ChiTietXuatHang (Composite): " + sql);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maXuatHang);
            stmt.setString(2, maBinhGas);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa ChiTietXuatHang theo khóa tổng hợp (" + maXuatHang + ", " + maBinhGas + "): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy tất cả ChiTietXuatHang cho một MaXuatHang cụ thể.
     * @param maXuatHang Mã xuất hàng cần tìm
     * @return Danh sách các ChiTietXuatHang
     */
    public List<ChiTietXuatHang> findByMaXuatHang(String maXuatHang) {
        List<ChiTietXuatHang> list = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE MaXuatHang = ?";
        System.out.println("SQL FindByMaXuatHang: " + sql);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maXuatHang);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm ChiTietXuatHang theo MaXuatHang (" + maXuatHang + "): " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy tất cả ChiTietXuatHang cho một MaBinhGas cụ thể.
     * @param maBinhGas Mã bình gas cần tìm
     * @return Danh sách các ChiTietXuatHang
     */
    public List<ChiTietXuatHang> findByMaBinhGas(String maBinhGas) {
        List<ChiTietXuatHang> list = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE MaBinhGas = ?";
        System.out.println("SQL FindByMaBinhGas: " + sql);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maBinhGas);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm ChiTietXuatHang theo MaBinhGas (" + maBinhGas + "): " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected Map<String, Object> getUpdateValues(ChiTietXuatHang entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}