/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.impl.BinhGasDAO;
import com.tuandat.cuahanggas.model.ChiTietXuatHang;
import com.tuandat.cuahanggas.model.ChiTietXuatHangTableModel;
import com.tuandat.cuahanggas.utils.DBConnection;
import com.tuandat.cuahanggas.utils.Session;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author duck
 */
public class frmHoaDonXuat extends javax.swing.JFrame {

    private boolean addedSuccessfully = false;
    private static final Logger logger = Logger.getLogger(frmHoaDonXuat.class.getName());
    private boolean _vuaThemBinhGas = false;
    private BinhGasDAO binhGasDAO;
    private ChiTietXuatHangTableModel modelChiTietXuatHang;
    private LocalDate selectedNgayXuat; // Đổi tên biến
    private DefaultTableModel modelKetQuaTimKiemBinhGas;
    private boolean isSavedSuccessfully = false;

    public boolean isSavedSuccessfully() { // Getter
        return isSavedSuccessfully;
    }

    public void setSavedSuccessfully(boolean isSavedSuccessfully) { // Setter
        this.isSavedSuccessfully = isSavedSuccessfully;
    }

    /**
     * Creates new form frmHoaDonXuat
     */
    public frmHoaDonXuat() {
        initComponents();
        initChiTietXuatHangTable(); // Đổi tên phương thức
        frmHoaDonXuat_Load(); // Đổi tên phương thức
        if (Session.IsLoggedIn()) { // Kiểm tra xem người dùng đã đăng nhập chưa
            txtMaNhanVien.setText(Session.MaNhanVien);
            txtMaNhanVien.setEditable(false); // Có thể đặt không cho chỉnh sửa để tránh nhầm lẫn
        } else {
            // Xử lý trường hợp người dùng chưa đăng nhập
            JOptionPane.showMessageDialog(this, "Bạn cần đăng nhập để tạo hóa đơn xuất.", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
            this.dispose(); // Đóng form nếu chưa đăng nhập
        }
    }

    private void initChiTietXuatHangTable() {
        modelChiTietXuatHang = new ChiTietXuatHangTableModel(); // Khởi tạo ChiTietXuatHangTableModel
        dgvChiTietXuatHang.setModel(modelChiTietXuatHang); // Đổi tên dgv
        modelChiTietXuatHang.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // Kiểm tra nếu sự kiện là thay đổi dữ liệu (UPDATE)
                // hoặc chèn/xóa hàng (INSERT/DELETE)
                if (e.getType() == TableModelEvent.UPDATE
                        || e.getType() == TableModelEvent.INSERT
                        || e.getType() == TableModelEvent.DELETE) {
                    capNhatTongTien(); // Gọi phương thức cập nhật tổng tiền
                }
            }
        });
    }

    private void frmHoaDonXuat_Load() { // Đổi tên phương thức
        selectedNgayXuat = LocalDate.now();
        dtpNgayXuat.setDate(java.sql.Date.valueOf(selectedNgayXuat));
        System.out.println("DEBUG: Trong frmHoaDonXuat_Load. MaNhanVien: " + com.tuandat.cuahanggas.utils.Session.MaNhanVien);
        if (Session.IsLoggedIn()) {
            txtMaNhanVien.setText(Session.MaNhanVien);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên đăng nhập. Vui lòng đăng nhập lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaNhanVien.setText("Lỗi NV");
        }
        generateNewMaXuatHang(); // Đổi tên phương thức
        loadTenKhachHangComboBox(); // Đổi tên phương thức và chức năng
        capNhatTongTien();
        cboTenKhachHang.addActionListener(new java.awt.event.ActionListener() { // Đổi tên cbo
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String selectedTenKhachHang = (String) cboTenKhachHang.getSelectedItem();
                displayKhachHangInfo(selectedTenKhachHang); // Đổi tên phương thức
            }
        });
        txtTimKiemBinhGas.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                txtTimKiemBinhGas_TextChanged();
            }

            public void removeUpdate(DocumentEvent e) {
                txtTimKiemBinhGas_TextChanged();
            }

            public void insertUpdate(DocumentEvent e) {
                txtTimKiemBinhGas_TextChanged();
            }
        });

        dgvKetQuaTimKiemBinhGas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                dgvKetQuaTimKiemBinhGas_CellClick(evt);
            }
        });
        modelKetQuaTimKiemBinhGas = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Bình Gas", "Tên Bình Gas", "Loại Bình Gas", "Số lượng", "Giá Bán"} // Đổi "Giá Nhập" thành "Giá Bán"
        );
        dgvKetQuaTimKiemBinhGas.setModel(modelKetQuaTimKiemBinhGas);

        // Ẩn cột "Giá Vốn Trung Bình" không cần thiết cho hóa đơn xuất trong bảng tìm kiếm
        // Không cần ẩn cột "Giá Bán"
        int giaBanColumnIndexInView = dgvKetQuaTimKiemBinhGas.getColumnModel().getColumnIndex("Giá Bán");
        if (giaBanColumnIndexInView != -1) {
            // Có thể giữ cột này hiển thị hoặc ẩn tùy theo yêu cầu của bạn
            // Ví dụ: giữ hiển thị
        }
    }

   private void displayKhachHangInfo(String tenKhachHang) { // Đổi tên phương thức và logic
    if (tenKhachHang == null || tenKhachHang.isEmpty() || tenKhachHang.equals("--- Chọn Khách Hàng ---")) {
        txtMaKhachHang.setText(""); // Đổi tên txt
        txtSdt.setText(""); // Giả sử bạn có txtSdtKhachHang để hiển thị SĐT Khách hàng
        return;
    }

    // Đã sửa: Thay đổi 'TenKhachHang' thành 'HoTen' trong mệnh đề WHERE
    String sql = "SELECT MaKhachHang, SDT FROM KhachHang WHERE HoTen = ?";
    try (Connection con = DBConnection.openConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

        pstmt.setString(1, tenKhachHang);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            txtMaKhachHang.setText(rs.getString("MaKhachHang"));
            txtSdt.setText(rs.getString("SDT"));
        } else {
            txtMaKhachHang.setText("");
            txtSdt.setText("");
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin cho khách hàng: " + tenKhachHang, "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin khách hàng: " + ex.getMessage(), "Lỗi DB", JOptionPane.ERROR_MESSAGE);
    }
}

    private void generateNewMaXuatHang() { // Đổi tên phương thức và tiền tố mã
        try (Connection conn = DBConnection.openConnection()) {
            String query = "SELECT TOP 1 MaXuatHang FROM XuatHang WHERE ISNUMERIC(SUBSTRING(MaXuatHang, 3, LEN(MaXuatHang))) = 1 ORDER BY CAST(SUBSTRING(MaXuatHang, 3, LEN(MaXuatHang)) AS INT) DESC";
            String lastMaXuatHang = null;

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                if (rs.next()) {
                    lastMaXuatHang = rs.getString("MaXuatHang");
                }
            }

            String newMaXuatHang = "XH001"; // Đổi tiền tố
            if (lastMaXuatHang != null && lastMaXuatHang.startsWith("XH")) { // Đổi tiền tố
                try {
                    int lastNumber = Integer.parseInt(lastMaXuatHang.substring(2));
                    newMaXuatHang = String.format("XH%03d", ++lastNumber); // Đổi tiền tố
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Định dạng mã hóa đơn xuất không đúng. Gán lại XH001.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                }
            }
            txtMaHoaDon.setText(newMaXuatHang);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi tạo mã hóa đơn xuất mới", ex);
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo mã hóa đơn xuất mới", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaHoaDon.setText("LỖI");
        }
    }

    private void loadTenKhachHangComboBox() { // Đổi tên phương thức và chức năng
        try (Connection conn = DBConnection.openConnection()) {
            String query = "SELECT HoTen FROM KhachHang ORDER BY HoTen ASC";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                Vector<String> items = new Vector<>();
                items.add("--- Chọn Khách Hàng ---");
                while (rs.next()) {
                    items.add(rs.getString("HoTen"));
                }
                cboTenKhachHang.setModel(new DefaultComboBoxModel<>(items));
                cboTenKhachHang.setSelectedIndex(0);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi tải danh sách khách hàng", ex);
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách khách hàng", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cboTenKhachHang_SelectedIndexChanged() { // Đổi tên phương thức
        Object selectedItem = cboTenKhachHang.getSelectedItem();
        if (selectedItem == null || selectedItem.equals("--- Chọn Khách Hàng ---")) {
            txtMaKhachHang.setText("");
            txtSdt.setText("");
            return;
        }

        String tenKH = selectedItem.toString();
        try (Connection conn = DBConnection.openConnection()) {
            String query = "SELECT MaKhachHang, SDT FROM KhachHang WHERE HoTen = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, tenKH);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        txtMaKhachHang.setText(rs.getString("MaKhachHang"));
                        txtSdt.setText(rs.getString("SDT"));
                    } else {
                        txtMaKhachHang.setText("");
                        txtSdt.setText("");
                    }
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi lấy thông tin khách hàng", ex);
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin khách hàng", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

   private void txtTimKiemBinhGas_TextChanged() {
    try (Connection conn = DBConnection.openConnection()) {
        String keyword = txtTimKiemBinhGas.getText().trim();
        String query = "SELECT MaBinhGas, TenBinhGas, LoaiBinh, SoLuong, GiaVonTrungBinh FROM BinhGas WHERE MaBinhGas LIKE ? OR TenBinhGas LIKE ?";

        // Để debug, bạn có thể in câu query và keyword ra console
        logger.log(Level.INFO, "Executing query: " + query + " with keyword: " + keyword);

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                modelKetQuaTimKiemBinhGas.setRowCount(0); // Xóa dữ liệu cũ
                while (rs.next()) {
                    Vector<Object> rowData = new Vector<>();
                    rowData.add(rs.getString("MaBinhGas"));
                    rowData.add(rs.getString("TenBinhGas"));
                    rowData.add(rs.getString("LoaiBinh"));
                    rowData.add(rs.getInt("SoLuong")); // Tồn kho
                    rowData.add(rs.getObject("GiaVonTrungBinh")); // Thêm cột Giá Bán Lẻ
                    modelKetQuaTimKiemBinhGas.addRow(rowData);
                }
            }
        }
    } catch (SQLException ex) {
        // Cập nhật dòng này để hiển thị thông báo lỗi chi tiết hơn
        logger.log(Level.SEVERE, "Lỗi khi tìm kiếm bình gas: " + ex.getMessage(), ex);
        JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm bình gas: " + ex.getMessage(), "Lỗi DB", JOptionPane.ERROR_MESSAGE);
    }
}

    private void dgvKetQuaTimKiemBinhGas_CellClick(MouseEvent evt) {
        int row = dgvKetQuaTimKiemBinhGas.rowAtPoint(evt.getPoint());
        if (row < 0 || row >= dgvKetQuaTimKiemBinhGas.getRowCount()) {
            return;
        }

        try {
            String maBinhGas = modelKetQuaTimKiemBinhGas.getValueAt(row, 0).toString();
            String tenBinhGas = modelKetQuaTimKiemBinhGas.getValueAt(row, 1).toString();
            int soLuongTonKho = (int) modelKetQuaTimKiemBinhGas.getValueAt(row, 3); // Lấy số lượng tồn kho

            int giaBanDeXuat = 0;
            Object giaBanObj = modelKetQuaTimKiemBinhGas.getValueAt(row, 4); // Lấy giá bán lẻ
            if (giaBanObj != null) {
                try {
                    giaBanDeXuat = Integer.parseInt(giaBanObj.toString());
                } catch (NumberFormatException e) {
                    logger.log(Level.WARNING, "Không thể chuyển đổi Giá Bán Lẻ: " + giaBanObj, e);
                }
            }

            int soLuongXuat = 0;
            while (true) {
                String input = JOptionPane.showInputDialog(this, String.format("Nhập số lượng xuất cho: %s (Tồn kho: %d)", tenBinhGas, soLuongTonKho));
                if (input == null) { // Người dùng nhấn Cancel
                    return;
                }

                input = input.trim();
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Số lượng không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                try {
                    soLuongXuat = Integer.parseInt(input);
                    if (soLuongXuat > 0 && soLuongXuat <= soLuongTonKho) { // Số lượng xuất phải dương và không vượt quá tồn kho
                        break;
                    } else if (soLuongXuat > soLuongTonKho) {
                        JOptionPane.showMessageDialog(this, "Số lượng xuất không được lớn hơn số lượng tồn kho (" + soLuongTonKho + ").", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên dương cho số lượng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên hợp lệ cho số lượng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            int donGiaBan = 0;
            while (true) {
                String input = (String) JOptionPane.showInputDialog(
                        this,
                        String.format("Nhập đơn giá bán cho: %s (Giá bán đề xuất: %,d VNĐ)", tenBinhGas, giaBanDeXuat),
                        "Nhập Đơn Giá Bán",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        String.valueOf(giaBanDeXuat) // Giá trị mặc định
                );

                if (input == null) { // Người dùng nhấn Cancel
                    return;
                }

                input = input.trim();
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Đơn giá không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                try {
                    donGiaBan = Integer.parseInt(input);
                    if (donGiaBan >= 0) { // Đơn giá phải không âm
                        break;
                    } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên không âm cho đơn giá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên hợp lệ cho đơn giá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Tạo và thêm dòng mới
            ChiTietXuatHang newItem = new ChiTietXuatHang(txtMaHoaDon.getText(), maBinhGas, tenBinhGas, soLuongXuat, donGiaBan, ""); // Đổi sang ChiTietXuatHang
            modelChiTietXuatHang.addRow(newItem); // Dùng model ChiTietXuatHang

            capNhatTongTien();

            // Chọn dòng mới và scroll đến đó
            dgvChiTietXuatHang.clearSelection();
            int lastRow = modelChiTietXuatHang.getRowCount() - 1;
            if (lastRow >= 0) {
                dgvChiTietXuatHang.setRowSelectionInterval(lastRow, lastRow);
                dgvChiTietXuatHang.scrollRectToVisible(dgvChiTietXuatHang.getCellRect(lastRow, 0, true));
            }
        } finally {
            dgvKetQuaTimKiemBinhGas.clearSelection();
        }
    }

    private void capNhatTongTien() {
        int tongTien = 0;
        for (ChiTietXuatHang item : modelChiTietXuatHang.getData()) { // Dùng ChiTietXuatHang
            tongTien += item.getThanhTien(); // getThanhTien() = soLuong * donGia
        }
        txtTongTien.setText(String.format("%,d VNĐ", tongTien));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiemBinhGas = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgvKetQuaTimKiemBinhGas = new javax.swing.JTable();
        btnThanhToan = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        dtpNgayXuat = new com.toedter.calendar.JDateChooser();
        btnXoaChiTiet = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        dgvChiTietXuatHang = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMaKhachHang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        cboTenKhachHang = new javax.swing.JComboBox<>();
        btmThemKhachHang = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setName("pnlHeader"); // NOI18N

        jLabel1.setText("Hóa đơn xuất");

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

        txtTimKiemBinhGas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemBinhGasActionPerformed(evt);
            }
        });

        dgvKetQuaTimKiemBinhGas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(dgvKetQuaTimKiemBinhGas);

        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel5.setName("pnlLoaiBinh"); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(114, 58));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Mã hóa đơn");
        jLabel8.setName("txtLoaiBinh"); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Ngày xuất");
        jLabel9.setName("txtLoaiBinh"); // NOI18N

        txtMaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNhanVienActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Mã nhân viên");
        jLabel4.setName("txtLoaiBinh"); // NOI18N

        txtMaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaNhanVien)
                    .addComponent(txtMaHoaDon)
                    .addComponent(dtpNgayXuat, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(dtpNgayXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        btnXoaChiTiet.setText("Xóa chi tiết");
        btnXoaChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaChiTietActionPerformed(evt);
            }
        });

        dgvChiTietXuatHang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(dgvChiTietXuatHang);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Tổng");
        jLabel5.setName("txtLoaiBinh"); // NOI18N

        txtTongTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setName("pnlLoaiBinh"); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(114, 58));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Tên khách hàng");
        jLabel6.setName("txtLoaiBinh"); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Số điện thoại");
        jLabel7.setName("txtLoaiBinh"); // NOI18N

        txtMaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKhachHangActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Mã khách hàng");
        jLabel3.setName("txtLoaiBinh"); // NOI18N

        txtSdt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSdtActionPerformed(evt);
            }
        });

        cboTenKhachHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btmThemKhachHang.setText("Thêm");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btmThemKhachHang)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSdt)
                            .addComponent(cboTenKhachHang, 0, 135, Short.MAX_VALUE)
                            .addComponent(txtMaKhachHang))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btmThemKhachHang)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)))
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btnXoaChiTiet)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTimKiemBinhGas, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThanhToan)
                        .addGap(33, 33, 33))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThanhToan))
                    .addComponent(txtTimKiemBinhGas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoaChiTiet)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNhanVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNhanVienActionPerformed

    private void txtMaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaHoaDonActionPerformed

    private void txtTongTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTienActionPerformed

    private void txtMaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKhachHangActionPerformed

    private void txtSdtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSdtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSdtActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        String maXuatHang = txtMaHoaDon.getText().trim();
        String maKhachHang = txtMaKhachHang.getText().trim(); // Đổi tên txt
        String maNhanVien = txtMaNhanVien.getText().trim();

        if (maXuatHang.isEmpty() || maKhachHang.isEmpty() || maNhanVien.isEmpty() || modelChiTietXuatHang.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin hóa đơn và thêm chi tiết xuất hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        java.sql.Date ngayXuat = null; // Đổi tên biến
        try {
            ngayXuat = new java.sql.Date(dtpNgayXuat.getDate().getTime());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày xuất.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            conn = DBConnection.openConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Thêm mới bản ghi vào bảng XuatHang
            String sqlInsertXuatHang = "INSERT INTO XuatHang (MaXuatHang, MaKhachHang, MaNhanVien, NgayXuat) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlInsertXuatHang)) {
                ps.setString(1, maXuatHang);
                ps.setString(2, maKhachHang);
                ps.setString(3, maNhanVien);
                ps.setDate(4, ngayXuat);
                ps.executeUpdate();
            }

            // 2. Thêm mới các bản ghi vào bảng ChiTietXuatHang và cập nhật bảng BinhGas
            String sqlInsertChiTiet = "INSERT INTO ChiTietXuatHang (MaXuatHang, MaBinhGas, SoLuongXuat, DonGiaXuat) VALUES (?, ?, ?, ?)";
            String sqlUpdateBinhGas = "UPDATE BinhGas SET SoLuong = SoLuong - ? WHERE MaBinhGas = ?"; // Trừ đi số lượng xuất
            String sqlCheckSoLuongTon = "SELECT SoLuong FROM BinhGas WHERE MaBinhGas = ?";

            for (ChiTietXuatHang item : modelChiTietXuatHang.getData()) {
                // Kiểm tra số lượng tồn kho trước khi xuất
                int soLuongTonKho = 0;
                try (PreparedStatement psCheck = conn.prepareStatement(sqlCheckSoLuongTon)) {
                    psCheck.setString(1, item.getMaBinhGas());
                    try (ResultSet rs = psCheck.executeQuery()) {
                        if (rs.next()) {
                            soLuongTonKho = rs.getInt("SoLuong");
                        }
                    }
                }

                if (item.getSoLuongXuat() > soLuongTonKho) {
                    conn.rollback(); // Hoàn tác nếu không đủ số lượng
                    JOptionPane.showMessageDialog(this, "Không đủ số lượng bình gas " + item.getTenBinhGas() + " để xuất. Tồn kho: " + soLuongTonKho, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return; // Thoát khỏi phương thức
                }

                // Chèn chi tiết xuất hàng
                try (PreparedStatement ps = conn.prepareStatement(sqlInsertChiTiet)) {
                    ps.setString(1, item.getMaXuatHang());
                    ps.setString(2, item.getMaBinhGas());
                    ps.setInt(3, item.getSoLuongXuat());
                    ps.setDouble(4, item.getDonGiaXuat());
                    ps.executeUpdate();
                }

                // Cập nhật số lượng bình gas (trừ đi)
                try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateBinhGas)) {
                    psUpdate.setInt(1, item.getSoLuongXuat());
                    psUpdate.setString(2, item.getMaBinhGas());
                    psUpdate.executeUpdate();
                }
            }

            conn.commit(); // Hoàn tất transaction
            JOptionPane.showMessageDialog(this, "Lưu hóa đơn xuất thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            this.setSavedSuccessfully(true); // Set the flag to true
            this.dispose(); // Close the form

        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback transaction nếu có lỗi
                }
            } catch (SQLException rollbackEx) {
                System.err.println("Lỗi khi rollback transaction: " + rollbackEx.getMessage());
            }
            System.err.println("Lỗi khi lưu hóa đơn xuất: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn xuất: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Đặt lại auto-commit
                    conn.close();
                } catch (SQLException closeEx) {
                    System.err.println("Lỗi khi đóng kết nối: " + closeEx.getMessage());
                }
            }
        }    // TODO add your handling code here:
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnXoaChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaChiTietActionPerformed
        int selectedRow = dgvChiTietXuatHang.getSelectedRow(); // Đổi tên dgv

        // 1. Kiểm tra xem có dòng nào được chọn không
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return; // Thoát nếu không có dòng nào được chọn
        }

        // 2. Xác nhận từ người dùng trước khi xóa
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa chi tiết này khỏi hóa đơn?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        // 3. Xử lý khi người dùng xác nhận xóa
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                ChiTietXuatHangTableModel customModel = (ChiTietXuatHangTableModel) dgvChiTietXuatHang.getModel(); // Đổi tên model
                customModel.removeRow(selectedRow); // SẼ KÍCH HOẠT TABLEMODELEVENT.DELETE

                JOptionPane.showMessageDialog(this, "Đã xóa chi tiết thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa chi tiết: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace(); // In stack trace để debug
            }
        }

    }//GEN-LAST:event_btnXoaChiTietActionPerformed

    private void txtTimKiemBinhGasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemBinhGasActionPerformed
        txtTimKiemBinhGas_TextChanged();        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemBinhGasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btmThemKhachHang;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnXoaChiTiet;
    private javax.swing.JComboBox<String> cboTenKhachHang;
    private javax.swing.JTable dgvChiTietXuatHang;
    private javax.swing.JTable dgvKetQuaTimKiemBinhGas;
    private com.toedter.calendar.JDateChooser dtpNgayXuat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaKhachHang;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTimKiemBinhGas;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables

    boolean isAddedSuccessfully() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
