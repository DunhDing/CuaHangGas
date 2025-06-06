/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandat.cuahanggas.ui;

import com.toedter.calendar.JDateChooser;
import com.tuandat.cuahanggas.dao.impl.ChiTietXuatHangDAO; // Đã đổi từ NhapHangDAO sang XuatHangDAO
import com.tuandat.cuahanggas.model.ChiTietXuatHang; // Đã đổi từ ChiTietNhapHang sang ChiTietXuatHang
import com.tuandat.cuahanggas.model.ExcelExporter;
import com.tuandat.cuahanggas.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.tuandat.cuahanggas.utils.TableHelper;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream; // Có thể giữ nếu bạn có chức năng xuất file
import java.util.LinkedHashMap;
import java.util.Vector; // Có thể giữ nếu bạn có chức năng xuất file
import javax.swing.JFileChooser; // Có thể giữ nếu bạn có chức năng xuất file
import javax.swing.JFrame; // Có thể giữ nếu bạn có chức năng xuất file
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter; // Có thể giữ nếu bạn có chức năng xuất file
import java.util.logging.Level; // Thêm import cho Logger
import java.util.logging.Logger; // Thêm import cho Logger

/**
 *
 * @author duck
 */
public class pnlXuatHang extends javax.swing.JPanel {

    private static final Logger logger = Logger.getLogger(pnlXuatHang.class.getName());

    public pnlXuatHang() {
        initComponents();
        setupComponents();
        loadComboBoxData();
        timKiemXuatHang(); // Đã đổi từ timKiemNhapHang
    }

    private void setupComponents() {
        cboLoaiLocNgay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            "Không lọc", "Theo ngày", "Theo tháng", "Theo năm"
        }));

        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                timKiemXuatHang();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                timKiemXuatHang();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                timKiemXuatHang();
            }
        });

        dtpNgayXuat.setDate(new Date());

        // Lắng nghe sự kiện click đúp vào bảng để mở chi tiết
        dgvXuatHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    btnChiTietActionPerformed(null); // Gọi hành động xem chi tiết khi click đúp
                }
            }
        });
    }

    private void loadComboBoxData() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rsKH = null; // Đã đổi từ rsNCC
        ResultSet rsNV = null;

        try {
            conn = DBConnection.openConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Không thể kết nối CSDL để tải dữ liệu combobox. Vui lòng kiểm tra kết nối.", "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.SEVERE, "loadComboBoxData() - Không thể kết nối CSDL."); // Sử dụng logger
                return;
            }
            stmt = conn.createStatement();

            // Tải dữ liệu cho cboMaKhachHang (thay vì cboMaNhaCungCap)
            rsKH = stmt.executeQuery("SELECT DISTINCT MaKhachHang FROM KhachHang ORDER BY MaKhachHang ASC"); // Đổi bảng
            cboMaKhachHang.removeAllItems(); // Đã đổi cbo
            cboMaKhachHang.addItem("Tất cả"); // Đã đổi cbo
            while (rsKH.next()) {
                cboMaKhachHang.addItem(rsKH.getString("MaKhachHang")); // Đã đổi cbo
            }

            // Tải dữ liệu cho cboMaNhanVien (giữ nguyên)
            rsNV = stmt.executeQuery("SELECT DISTINCT MaNhanVien FROM NhanVien ORDER BY MaNhanVien ASC");
            cboMaNhanVien.removeAllItems();
            cboMaNhanVien.addItem("Tất cả");
            while (rsNV.next()) {
                cboMaNhanVien.addItem(rsNV.getString("MaNhanVien"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu combobox: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            logger.log(Level.SEVERE, "Lỗi tải dữ liệu combobox: " + e.getMessage(), e); // Sử dụng logger
        } finally {
            try {
                if (rsKH != null) { // Đã đổi rs
                    rsKH.close();
                }
                if (rsNV != null) {
                    rsNV.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Lỗi đóng tài nguyên trong loadComboBoxData(): " + ex.getMessage(), ex); // Sử dụng logger
            }
        }
    }

    private void timKiemXuatHang() { // Đã đổi tên hàm
        logger.log(Level.INFO, "timKiemXuatHang() - Bắt đầu.");
        String tuKhoa = txtTimKiem.getText().trim();
        // Đảm bảo giá trị lấy từ combobox là "Tất cả" hoặc mã thực tế
        String maKH = (cboMaKhachHang.getSelectedItem() != null && cboMaKhachHang.getSelectedItem() instanceof String)
                ? (String) cboMaKhachHang.getSelectedItem() : "Tất cả";
        String maNV = (cboMaNhanVien.getSelectedItem() != null && cboMaNhanVien.getSelectedItem() instanceof String)
                ? (String) cboMaNhanVien.getSelectedItem() : "Tất cả";
        String locTheo = (String) cboLoaiLocNgay.getSelectedItem();
        Date ngayXuat = dtpNgayXuat.getDate();

        // 1. Khởi tạo câu truy vấn cơ bản (SELECT, FROM, JOIN)
        StringBuilder query = new StringBuilder(
                "SELECT xh.MaXuatHang, xh.NgayXuat, xh.MaKhachHang, kh.HoTen AS TenKhachHang, " // THÊM kh.HoTen AS TenKhachHang
                + "xh.MaNhanVien, nv.HoTen AS TenNhanVien, "
                + "ISNULL(SUM(ctxh.SoLuongXuat * ctxh.DonGiaXuat), 0) AS TongTienHoaDonXuat, xh.GhiChu "
                + "FROM XuatHang xh "
                + "LEFT JOIN ChiTietXuatHang ctxh ON xh.MaXuatHang = ctxh.MaXuatHang "
                + "INNER JOIN NhanVien nv ON xh.MaNhanVien = nv.MaNhanVien "
                + "LEFT JOIN KhachHang kh ON xh.MaKhachHang = kh.MaKhachHang " // Đảm bảo JOIN với KhachHang
                + "WHERE 1=1 "); // Luôn bắt đầu với WHERE 1=1 để dễ dàng thêm AND

        List<Object> params = new ArrayList<>();

        // 2. Thêm điều kiện tìm kiếm theo từ khóa
        if (!tuKhoa.isEmpty()) {
            query.append("AND (xh.MaXuatHang LIKE ? OR xh.MaKhachHang LIKE ? OR xh.MaNhanVien LIKE ? OR nv.HoTen LIKE ? OR kh.HoTen LIKE ? OR xh.GhiChu LIKE ?) "); // THÊM kh.HoTen
            for (int i = 0; i < 6; i++) { // Phải là 6 vì có 6 điều kiện LIKE
                params.add("%" + tuKhoa + "%");
            }
        }

        // 3. Thêm điều kiện lọc theo Mã Khách Hàng
        if (!"Tất cả".equals(maKH)) {
            query.append("AND xh.MaKhachHang = ? ");
            params.add(maKH);
        }

        // 4. Thêm điều kiện lọc theo Mã Nhân Viên
        if (!"Tất cả".equals(maNV)) {
            query.append("AND xh.MaNhanVien = ? ");
            params.add(maNV);
        }

        // 5. Thêm điều kiện lọc theo ngày xuất
        if (!"Không lọc".equals(locTheo) && ngayXuat != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(ngayXuat);

            switch (locTheo) {
                case "Theo ngày":
                    query.append("AND CONVERT(DATE, xh.NgayXuat) = ? ");
                    params.add(new java.sql.Date(ngayXuat.getTime()));
                    break;
                case "Theo tháng":
                    query.append("AND MONTH(xh.NgayXuat) = ? AND YEAR(xh.NgayXuat) = ? ");
                    params.add(cal.get(Calendar.MONTH) + 1);
                    params.add(cal.get(Calendar.YEAR));
                    break;
                case "Theo năm":
                    query.append("AND YEAR(xh.NgayXuat) = ? ");
                    params.add(cal.get(Calendar.YEAR));
                    break;
            }
        }

        // 6. Thêm GROUP BY và ORDER BY CỐ ĐỊNH ở cuối cùng (CHỈ MỘT LẦN)
        query.append(" GROUP BY xh.MaXuatHang, xh.NgayXuat, xh.MaKhachHang, kh.HoTen, xh.MaNhanVien, nv.HoTen, xh.GhiChu "); // THÊM kh.HoTen vào GROUP BY
        query.append(" ORDER BY xh.NgayXuat DESC, xh.MaXuatHang ASC");

        logger.log(Level.INFO, "DEBUG SQL Query Final (timKiemXuatHang): " + query.toString());
        logger.log(Level.INFO, "DEBUG SQL Params Final (timKiemXuatHang): " + params.toString());
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.openConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Không thể kết nối CSDL để tìm kiếm. Vui lòng kiểm tra kết nối.", "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.SEVERE, "timKiemXuatHang() - Kết nối CSDL null.");
                return;
            }
            ps = conn.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            rs = ps.executeQuery();

            LinkedHashMap<String, String> columnMapping = new LinkedHashMap<>();
            columnMapping.put("MaXuatHang", "Mã Xuất Hàng");
            columnMapping.put("NgayXuat", "Ngày Xuất");
            columnMapping.put("MaKhachHang", "Mã Khách Hàng");
            columnMapping.put("TenKhachHang", "Tên Khách Hàng"); // Giả sử cột này tồn tại và được join đúng
            columnMapping.put("MaNhanVien", "Mã Nhân Viên");
            columnMapping.put("TenNhanVien", "Tên Nhân Viên");
            columnMapping.put("TongTienHoaDonXuat", "Tổng Tiền Hóa Đơn");
            columnMapping.put("GhiChu", "Ghi Chú");
            dgvXuatHang.setModel(TableHelper.resultSetToTableModel(rs, columnMapping));
            logger.log(Level.INFO, "timKiemXuatHang() - Đã thực thi truy vấn. Số hàng trả về: " + dgvXuatHang.getRowCount());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm phiếu xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            logger.log(Level.SEVERE, "timKiemXuatHang() - Lỗi SQLException: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                // KHÔNG ĐÓNG 'conn' Ở ĐÂY, vì nó là kết nối tĩnh của DBConnection
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Lỗi đóng tài nguyên trong timKiemXuatHang(): " + ex.getMessage(), ex);
            }
        }
        logger.log(Level.INFO, "timKiemXuatHang() - Kết thúc.");
    }

    private void deleteSelectedXuatHang() { // Đã đổi tên hàm
        int selectedRow = dgvXuatHang.getSelectedRow(); // Đã đổi dgv
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu xuất cần xóa.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maXuatHang = (String) dgvXuatHang.getValueAt(selectedRow, 0); // Mã xuất hàng ở cột đầu tiên

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa phiếu xuất " + maXuatHang + " này và tất cả chi tiết liên quan?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Connection conn = null;
            PreparedStatement psDeleteChiTiet = null;
            PreparedStatement psDeleteXuatHang = null; // Đã đổi tên

            try {
                conn = DBConnection.openConnection();
                if (conn == null) {
                    JOptionPane.showMessageDialog(this, "Không thể kết nối CSDL để xóa. Vui lòng kiểm tra kết nối.", "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                conn.setAutoCommit(false); // Bắt đầu transaction

                // Xóa chi tiết xuất hàng trước
                String sqlDeleteChiTiet = "DELETE FROM ChiTietXuatHang WHERE MaXuatHang = ?"; // Đổi bảng
                psDeleteChiTiet = conn.prepareStatement(sqlDeleteChiTiet);
                psDeleteChiTiet.setString(1, maXuatHang);
                psDeleteChiTiet.executeUpdate();
                logger.log(Level.INFO, "Đã xóa chi tiết xuất hàng cho MaXuatHang: " + maXuatHang);

                // Xóa phiếu xuất hàng
                String sqlDeleteXuatHang = "DELETE FROM XuatHang WHERE MaXuatHang = ?"; // Đổi bảng
                psDeleteXuatHang = conn.prepareStatement(sqlDeleteXuatHang);
                psDeleteXuatHang.setString(1, maXuatHang);
                int rowsAffected = psDeleteXuatHang.executeUpdate();

                if (rowsAffected > 0) {
                    conn.commit(); // Commit transaction nếu thành công
                    JOptionPane.showMessageDialog(this, "Xóa phiếu xuất thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    timKiemXuatHang(); // Tải lại dữ liệu sau khi xóa thành công
                } else {
                    conn.rollback(); // Rollback nếu không có hàng nào bị ảnh hưởng (phiếu xuất không tồn tại)
                    JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu xuất để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }

            } catch (SQLException e) {
                try {
                    if (conn != null) {
                        conn.rollback(); // Rollback nếu có lỗi
                        logger.log(Level.WARNING, "Rollback transaction do lỗi SQL khi xóa phiếu xuất.", e);
                    }
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, "Lỗi khi rollback sau lỗi xóa phiếu xuất: " + ex.getMessage(), ex);
                }
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa phiếu xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.SEVERE, "Lỗi SQL khi xóa phiếu xuất", e);
            } finally {
                try {
                    if (psDeleteChiTiet != null) {
                        psDeleteChiTiet.close();
                    }
                    if (psDeleteXuatHang != null) {
                        psDeleteXuatHang.close();
                    }
                    if (conn != null) {
                        conn.setAutoCommit(true); // Đặt lại auto-commit
                        // KHÔNG ĐÓNG 'conn' Ở ĐÂY, vì nó là kết nối tĩnh của DBConnection
                    }
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Lỗi đóng tài nguyên trong deleteSelectedXuatHang(): " + e.getMessage(), e);
                }
            }
        }
    }

    private void exportToExcel() {
        DefaultTableModel model = (DefaultTableModel) dgvXuatHang.getModel();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất ra Excel.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop")); // Mở mặc định ở Desktop
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File("DanhSachHoaDonXuat_" + System.currentTimeMillis() + ".xlsx")); // Tên file gợi ý ban đầu

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }

            try {
                // Gọi phương thức tĩnh từ class ExcelExporter
                ExcelExporter.exportHoaDonXuatToExcel(
                        dgvXuatHang, // JTable chứa dữ liệu
                        fileToSave.getAbsolutePath(), // Đường dẫn file sẽ lưu
                        "Danh Sách Hóa Đơn Xuất", // Tên sheet trong Excel
                        "DANH SÁCH HÓA ĐƠN XUẤT HÀNG" // Tiêu đề chính của báo cáo
                );
                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!\n" + fileToSave.getAbsolutePath(), "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) { // Bắt Exception chung từ phương thức export
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.SEVERE, "Lỗi khi xuất file Excel", ex);
            }
        }
    }

    /**
     *
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnXuat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnChiTiet = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cboMaNhanVien = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgvXuatHang = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        dtpNgayXuat = new com.toedter.calendar.JDateChooser();
        cboLoaiLocNgay = new javax.swing.JComboBox<>();
        txtTimKiem = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cboMaKhachHang = new javax.swing.JComboBox<>();

        btnXuat.setText("Xuất File");
        btnXuat.setName("btnXuatFile"); // NOI18N
        btnXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.setName("btnXoa"); // NOI18N

        btnChiTiet.setText("Chi Tiết");
        btnChiTiet.setName("btnChiTiet"); // NOI18N
        btnChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiTietActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setName("pnlLoaiBinh"); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(114, 58));

        jLabel2.setText("Mã nhân viên");
        jLabel2.setName("txtLoaiBinh"); // NOI18N

        cboMaNhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMaNhanVien.setName("cboMaNhanVien"); // NOI18N
        cboMaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(cboMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setName("pnlHeader"); // NOI18N

        jLabel1.setText("Xuất hàng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        dgvXuatHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(dgvXuatHang);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel5.setName("pnlLoaiBinh"); // NOI18N

        jLabel4.setText("Ngày xuất");
        jLabel4.setName("txtLoaiBinh"); // NOI18N

        dtpNgayXuat.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtpNgayXuatPropertyChange(evt);
            }
        });

        cboLoaiLocNgay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLoaiLocNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiLocNgayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dtpNgayXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addComponent(cboLoaiLocNgay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dtpNgayXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboLoaiLocNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");
        btnThem.setName("btnThem"); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setName("pnlLoaiVan"); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(114, 58));

        jLabel3.setText("Mã khách hàng");
        jLabel3.setName("txtLoaiVan"); // NOI18N

        cboMaKhachHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMaKhachHang.setName("cboMaNCC"); // NOI18N
        cboMaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(cboMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(108, 108, 108)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(162, 162, 162)
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChiTiet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXuat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(61, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiTietActionPerformed
        int selectedRow = dgvXuatHang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu xuất để xem chi tiết.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maXuatHang = (String) dgvXuatHang.getValueAt(selectedRow, 0); // Giả sử Mã Xuất Hàng ở cột 0

        // Mở dialog chi tiết xuất hàng và truyền mã xuất hàng
        // Bạn cần đảm bảo đã tạo lớp dlgChiTietXuatHang tương tự dlgChiTietNhapHang
        dlgChiTietXuatHang chiTietDialog = new dlgChiTietXuatHang(null, true); // Thay 'null' bằng JFrame cha thực tế
        chiTietDialog.setMaXuatHang(maXuatHang);
        chiTietDialog.setVisible(true);

        // Sau khi dialog đóng, có thể cần tải lại dữ liệu nếu có sự thay đổi
        timKiemXuatHang();        // TODO add your handling code here:
    }//GEN-LAST:event_btnChiTietActionPerformed

    private void cboMaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaNhanVienActionPerformed
        timKiemXuatHang();
    }//GEN-LAST:event_cboMaNhanVienActionPerformed

    private void dtpNgayXuatPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtpNgayXuatPropertyChange
        if ("date".equals(evt.getPropertyName())) {
            timKiemXuatHang(); // Gọi lại tìm kiếm khi thay đổi ngày
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_dtpNgayXuatPropertyChange

    private void cboLoaiLocNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiLocNgayActionPerformed
        dtpNgayXuat.setEnabled(cboLoaiLocNgay.getSelectedIndex() > 0); // Bật/tắt date picker tùy chọn lọc
        timKiemXuatHang();
    }//GEN-LAST:event_cboLoaiLocNgayActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        frmHoaDonXuat themXuatHangDialog = new frmHoaDonXuat();

        // Thêm WindowListener để lắng nghe sự kiện đóng cửa sổ
        themXuatHangDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (themXuatHangDialog.isSavedSuccessfully()) {
                    timKiemXuatHang();
                    JOptionPane.showMessageDialog(null, "Danh sách hóa đơn đã được cập nhật.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        themXuatHangDialog.setVisible(true);
    }//GEN-LAST:event_btnThemActionPerformed

    private void cboMaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaKhachHangActionPerformed
        timKiemXuatHang();        // TODO add your handling code here:
    }//GEN-LAST:event_cboMaKhachHangActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        timKiemXuatHang();         // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatActionPerformed
        exportToExcel();        // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChiTiet;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuat;
    private javax.swing.JComboBox<String> cboLoaiLocNgay;
    private javax.swing.JComboBox<String> cboMaKhachHang;
    private javax.swing.JComboBox<String> cboMaNhanVien;
    private javax.swing.JTable dgvXuatHang;
    private com.toedter.calendar.JDateChooser dtpNgayXuat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
