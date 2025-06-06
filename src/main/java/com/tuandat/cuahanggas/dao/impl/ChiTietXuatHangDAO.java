package com.tuandat.cuahanggas.dao.impl; // Đảm bảo gói này phù hợp với cấu trúc project của bạn

import com.tuandat.cuahanggas.model.ChiTietXuatHang;
import com.tuandat.cuahanggas.utils.DBConnection; // Vẫn cần để mở kết nối nếu phương thức DAO không nhận Connection

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChiTietXuatHangDAO {

    private static final Logger logger = Logger.getLogger(ChiTietXuatHangDAO.class.getName());

    // --- Phương thức không nhận Connection (tự quản lý kết nối) ---
    // Sử dụng khi bạn chỉ muốn thực hiện một thao tác độc lập, không nằm trong transaction lớn.
    // KHÔNG KHUYẾN KHÍCH SỬ DỤNG CHO CÁC THAO TÁC LIÊN QUAN ĐẾN LƯU HÓA ĐƠN
    // vì nó sẽ mở/đóng kết nối riêng cho mỗi lần gọi.
    public boolean addChiTietXuatHang(ChiTietXuatHang chiTietXuatHang) {
        try (Connection conn = DBConnection.openConnection()) {
            return addChiTietXuatHang(chiTietXuatHang, conn); // Gọi phiên bản có Connection
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi mở kết nối để thêm chi tiết xuất hàng (standalone): " + chiTietXuatHang.getMaBinhGas(), ex);
            return false;
        }
    }

    public List<ChiTietXuatHang> getChiTietXuatHangByMaXuatHang(String maXuatHang) {
        try (Connection conn = DBConnection.openConnection()) {
            return getChiTietXuatHangByMaXuatHang(maXuatHang, conn); // Gọi phiên bản có Connection
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi mở kết nối để lấy chi tiết xuất hàng (standalone): " + maXuatHang, ex);
            return new ArrayList<>();
        }
    }
    public boolean addChiTietXuatHang(ChiTietXuatHang chiTietXuatHang, Connection conn) throws SQLException {
        String sql = "INSERT INTO ChiTietXuatHang (MaXuatHang, MaBinhGas, SoLuongXuat, DonGiaXuat, GhiChu) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) { // KHÔNG ĐÓNG 'conn' Ở ĐÂY
            pstmt.setString(1, chiTietXuatHang.getMaXuatHang());
            pstmt.setString(2, chiTietXuatHang.getMaBinhGas());
            pstmt.setInt(3, chiTietXuatHang.getSoLuongXuat());
            pstmt.setDouble(4, chiTietXuatHang.getDonGiaXuat());
            pstmt.setString(5, chiTietXuatHang.getGhiChu()); // Thêm cột GhiChu nếu có trong DB

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi thêm chi tiết xuất hàng vào database: " + chiTietXuatHang.getMaBinhGas(), ex);
            throw ex; // Ném lỗi để lớp gọi có thể bắt và rollback transaction
        }
    }
    public List<ChiTietXuatHang> getChiTietXuatHangByMaXuatHang(String maXuatHang, Connection conn) throws SQLException {
        List<ChiTietXuatHang> list = new ArrayList<>();
        String sql = "SELECT c.MaXuatHang, c.MaBinhGas, bg.TenBinhGas, c.SoLuongXuat, c.DonGiaXuat, c.GhiChu " +
                     "FROM ChiTietXuatHang c JOIN BinhGas bg ON c.MaBinhGas = bg.MaBinhGas " +
                     "WHERE c.MaXuatHang = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            pstmt.setString(1, maXuatHang);
            while (rs.next()) {
                String maXH = rs.getString("MaXuatHang");
                String maBG = rs.getString("MaBinhGas");
                String tenBG = rs.getString("TenBinhGas");
                int soLuong = rs.getInt("SoLuongXuat");
                double donGia = rs.getDouble("DonGiaXuat");
                String ghiChu = rs.getString("GhiChu");

                ChiTietXuatHang chiTiet = new ChiTietXuatHang(maXH, maBG, tenBG, soLuong, donGia, ghiChu);
                list.add(chiTiet);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi lấy chi tiết xuất hàng theo mã hóa đơn từ database: " + maXuatHang, ex);
            throw ex; // Ném lỗi để lớp gọi có thể bắt và xử lý
        }
        return list;
    }
    public boolean updateChiTietXuatHang(ChiTietXuatHang chiTietXuatHang, Connection conn) throws SQLException {
        String sql = "UPDATE ChiTietXuatHang SET SoLuongXuat = ?, DonGiaXuat = ?, GhiChu = ? WHERE MaXuatHang = ? AND MaBinhGas = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, chiTietXuatHang.getSoLuongXuat());
            pstmt.setDouble(2, chiTietXuatHang.getDonGiaXuat());
            pstmt.setString(3, chiTietXuatHang.getGhiChu());
            pstmt.setString(4, chiTietXuatHang.getMaXuatHang());
            pstmt.setString(5, chiTietXuatHang.getMaBinhGas());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi cập nhật chi tiết xuất hàng trong database: " + chiTietXuatHang.getMaBinhGas(), ex);
            throw ex; // Ném lỗi để lớp gọi có thể bắt và rollback transaction
        }
    }
    public boolean updateGhiChu(String maXuatHang, String maBinhGas, String ghiChuMoi, Connection conn) throws SQLException {
        String sql = "UPDATE ChiTietXuatHang SET GhiChu = ? WHERE MaXuatHang = ? AND MaBinhGas = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ghiChuMoi);
            pstmt.setString(2, maXuatHang);
            pstmt.setString(3, maBinhGas);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi cập nhật ghi chú chi tiết xuất hàng: MaXuatHang=" + maXuatHang + ", MaBinhGas=" + maBinhGas, ex);
            throw ex;
        }
    }

    public boolean deleteChiTietXuatHang(String maXuatHang, String maBinhGas, Connection conn) throws SQLException {
        String sql = "DELETE FROM ChiTietXuatHang WHERE MaXuatHang = ? AND MaBinhGas = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maXuatHang);
            pstmt.setString(2, maBinhGas);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi xóa chi tiết xuất hàng từ database: MaXuatHang=" + maXuatHang + ", MaBinhGas=" + maBinhGas, ex);
            throw ex;
        }
    }
    public boolean deleteAllChiTietOfXuatHang(String maXuatHang, Connection conn) throws SQLException {
        String sql = "DELETE FROM ChiTietXuatHang WHERE MaXuatHang = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maXuatHang);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Có thể trả về true ngay cả khi không có chi tiết nào để xóa (rowsAffected = 0)

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi xóa tất cả chi tiết của hóa đơn xuất từ database: " + maXuatHang, ex);
            throw ex;
        }
    }
}