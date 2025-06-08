/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.DAO;
import com.tuandat.cuahanggas.dao.impl.BinhGasDAO;
import com.tuandat.cuahanggas.dao.impl.NhaCungCapDAO;
import com.tuandat.cuahanggas.utils.DBConnection;
import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import com.tuandat.cuahanggas.model.ChiTietNhapHang;
import com.tuandat.cuahanggas.model.ChiTietNhapHangTableModel;
import com.tuandat.cuahanggas.utils.Session;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
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
public class frmHoaDonNhap extends javax.swing.JFrame {

    private boolean addedSuccessfully = false;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmHoaDonNhap.class.getName());
    private boolean _vuaThemBinhGas = false;
    private BinhGasDAO binhGasDAO;
    private ChiTietNhapHangTableModel modelChiTietNhapHang;
    private LocalDate selectedNgayNhap;
    private DefaultTableModel modelKetQuaTimKiemBinhGas;
    private boolean isSavedSuccessfully = false;
    private Connection conn;
    private NhaCungCapDAO nhaCungCapDAO;

    public boolean isSavedSuccessfully() { // Getter
        return isSavedSuccessfully;
    }

    public void setSavedSuccessfully(boolean isSavedSuccessfully) { // Setter
        this.isSavedSuccessfully = isSavedSuccessfully;
    }

    /**
     * Creates new form frmHoaDonNhap
     */
    public frmHoaDonNhap(Connection con) {
        this.conn = con;
        initComponents();
        this.nhaCungCapDAO = new NhaCungCapDAO(conn);
        ImageIcon iconThem = new ImageIcon(getClass().getResource("/them.png"));
        Image imgThem = iconThem.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        btmThemKhachHang.setIcon(new ImageIcon(imgThem));

        ImageIcon iconXoa = new ImageIcon(getClass().getResource("/xoa.png"));
        Image imgXoa = iconXoa.getImage().getScaledInstance(20, 25, Image.SCALE_SMOOTH);
        btnXoaChiTiet.setIcon(new ImageIcon(imgXoa));
        initChiTietNhapHangTable();
        frmHoaDonNhap_Load();
        setExtendedState(MAXIMIZED_BOTH);
        if (Session.IsLoggedIn()) { // Kiểm tra xem người dùng đã đăng nhập chưa
            txtMaNhanVien.setText(Session.MaNhanVien);
            txtMaNhanVien.setEditable(false); // Có thể đặt không cho chỉnh sửa để tránh nhầm lẫn
        } else {
            // Xử lý trường hợp người dùng chưa đăng nhập, ví dụ:
            JOptionPane.showMessageDialog(this, "Bạn cần đăng nhập để tạo hóa đơn nhập.", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
            this.dispose(); // Đóng form nếu chưa đăng nhập
        }

    }

    private void initChiTietNhapHangTable() {
        modelChiTietNhapHang = new ChiTietNhapHangTableModel();
        dgvChiTietNhapHang.setModel(modelChiTietNhapHang);
        modelChiTietNhapHang.addTableModelListener(new TableModelListener() {
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

    private void frmHoaDonNhap_Load() {
        selectedNgayNhap = LocalDate.now();
        dtpNgayXuat.setDate(java.sql.Date.valueOf(selectedNgayNhap));
        System.out.println("DEBUG: Trong frmHoaDonNhap_Load. MaNhanVien: " + com.tuandat.cuahanggas.utils.Session.MaNhanVien);
        if (Session.IsLoggedIn()) {
            txtMaNhanVien.setText(Session.MaNhanVien);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên đăng nhập. Vui lòng đăng nhập lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaNhanVien.setText("Lỗi NV");
        }
        generateNewMaNhapHang();
        loadMaNhaCungCapComboBox();
        capNhatTongTien();
        cboTenNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String selectedTenNCC = (String) cboTenNCC.getSelectedItem();
                displayNhaCungCapInfo(selectedTenNCC);
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
                // Thêm "Giá Vốn Trung Bình" vào đây
                new String[]{"Mã Bình Gas", "Tên Bình Gas", "Loại Bình Gas", "Số lượng", "Giá Nhập", "Giá Vốn Trung Bình"}
        );
        dgvKetQuaTimKiemBinhGas.setModel(modelKetQuaTimKiemBinhGas);

        int giaVonColumnIndexInView = dgvKetQuaTimKiemBinhGas.getColumnModel().getColumnIndex("Giá Vốn Trung Bình");
        if (giaVonColumnIndexInView != -1) {
            dgvKetQuaTimKiemBinhGas.getColumnModel().getColumn(giaVonColumnIndexInView).setMinWidth(0);
            dgvKetQuaTimKiemBinhGas.getColumnModel().getColumn(giaVonColumnIndexInView).setMaxWidth(0);
            dgvKetQuaTimKiemBinhGas.getColumnModel().getColumn(giaVonColumnIndexInView).setWidth(0);
            dgvKetQuaTimKiemBinhGas.getColumnModel().getColumn(giaVonColumnIndexInView).setPreferredWidth(0);
        }
    }

    private void displayNhaCungCapInfo(String tenNhaCungCap) {
        if (tenNhaCungCap == null || tenNhaCungCap.isEmpty()) {
            txtMaNCC.setText("");
            txtSdt.setText(""); // Giả sử bạn có txtSdt để hiển thị SĐT NCC
            return;
        }

//        String sql = "SELECT MaNhaCungCap, SDT FROM NhaCungCap WHERE TenNhaCungCap = ?";
//        try (Connection con = DBConnection.openConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
//
//            pstmt.setString(1, tenNhaCungCap);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                txtMaNCC.setText(rs.getString("MaNhaCungCap"));
//                // Giả sử bạn có một JTextField tên là txtSdt cho số điện thoại nhà cung cấp
//                txtSdt.setText(rs.getString("SDT"));
//            } else {
//                txtMaNCC.setText("");
//                txtSdt.setText("");
//                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin cho nhà cung cấp: " + tenNhaCungCap, "Lỗi", JOptionPane.WARNING_MESSAGE);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin nhà cung cấp: " + ex.getMessage(), "Lỗi DB", JOptionPane.ERROR_MESSAGE);
//        }
        String sql = "SELECT MaNhaCungCap, SDT FROM NhaCungCap WHERE TenNhaCungCap = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tenNhaCungCap);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    txtMaNCC.setText(rs.getString("MaNhaCungCap"));
                    txtSdt.setText(rs.getString("SDT"));
                } else {
                    txtMaNCC.setText("");
                    txtSdt.setText("");
                   
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin nhà cung cấp: " + ex.getMessage(), "Lỗi DB", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateNewMaNhapHang() {
//        try (Connection conn = DBConnection.openConnection()) {
//            String query = "SELECT TOP 1 MaNhapHang FROM NhapHang WHERE ISNUMERIC(SUBSTRING(MaNhapHang, 3, LEN(MaNhapHang))) = 1 ORDER BY CAST(SUBSTRING(MaNhapHang, 3, LEN(MaNhapHang)) AS INT) DESC";
//            String lastMaNhapHang = null;
//
//            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
//                if (rs.next()) {
//                    lastMaNhapHang = rs.getString("MaNhapHang");
//                }
//            }
//
//            String newMaNhapHang = "NH001";
//            if (lastMaNhapHang != null && lastMaNhapHang.startsWith("NH")) {
//                try {
//                    int lastNumber = Integer.parseInt(lastMaNhapHang.substring(2));
//                    newMaNhapHang = String.format("NH%03d", ++lastNumber);
//                } catch (NumberFormatException e) {
//                    JOptionPane.showMessageDialog(this, "Định dạng mã hóa đơn nhập không đúng. Gán lại NH001.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
//                }
//            }
//            txtMaHoaDon.setText(newMaNhapHang);
//        } catch (SQLException ex) {
//            logger.log(Level.SEVERE, "Lỗi khi tạo mã hóa đơn nhập mới", ex);
//            JOptionPane.showMessageDialog(this, "Lỗi khi tạo mã hóa đơn nhập mới", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            txtMaHoaDon.setText("LỖI");
//        }
        String query = """
    SELECT TOP 1 MaNhapHang 
    FROM NhapHang 
    WHERE ISNUMERIC(SUBSTRING(MaNhapHang, 3, LEN(MaNhapHang))) = 1 
          AND LEFT(MaNhapHang, 2) = 'NH'
    ORDER BY CAST(SUBSTRING(MaNhapHang, 3, LEN(MaNhapHang)) AS INT) DESC
""";

        try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            String lastMa = null;
            if (rs.next()) {
                lastMa = rs.getString("MaNhapHang");
            }

            String newMa = "NH001";
            if (lastMa != null) {
                try {
                    int number = Integer.parseInt(lastMa.substring(2));
                    newMa = String.format("NH%03d", number + 1);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Mã không đúng định dạng, tạo mới là NH001", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                }
            }

            txtMaHoaDon.setText(newMa);

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi tạo mã nhập hàng mới", ex);
            JOptionPane.showMessageDialog(this, "Không thể tạo mã mới", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaHoaDon.setText("LỖI");
        }

    }

    private void loadMaNhaCungCapComboBox() {
//        try (Connection conn = DBConnection.openConnection()) {
//            String query = "SELECT TenNhaCungCap FROM NhaCungCap ORDER BY TenNhaCungCap ASC";
//            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
//                Vector<String> items = new Vector<>();
//                items.add("--- Chọn Nhà Cung Cấp ---");
//                while (rs.next()) {
//                    items.add(rs.getString("TenNhaCungCap"));
//                }
//                cboTenNCC.setModel(new DefaultComboBoxModel<>(items));
//                cboTenNCC.setSelectedIndex(0);
//            }
//        } catch (SQLException ex) {
//            logger.log(Level.SEVERE, "Lỗi khi tải danh sách nhà cung cấp", ex);
//            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách nhà cung cấp", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
        String query = "SELECT TenNhaCungCap FROM NhaCungCap ORDER BY TenNhaCungCap ASC";

        try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            Vector<String> items = new Vector<>();
            items.add("--- Chọn Nhà Cung Cấp ---");

            while (rs.next()) {
                String tenNCC = rs.getString("TenNhaCungCap");
                if (tenNCC != null && !tenNCC.trim().isEmpty()) {
                    items.add(tenNCC);
                }
            }

            cboTenNCC.setModel(new DefaultComboBoxModel<>(items));
            cboTenNCC.setSelectedIndex(0);

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi tải danh sách nhà cung cấp", ex);
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách nhà cung cấp", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void cboMaNCC_SelectedIndexChanged() {
//        Object selectedItem = cboTenNCC.getSelectedItem();
//        if (selectedItem == null || selectedItem.equals("--- Chọn Nhà Cung Cấp ---")) {
//            txtMaNCC.setText("");
//            txtSdt.setText("");
//            return;
//        }
//
//        String tenNCC = selectedItem.toString();
//        try (Connection conn = DBConnection.openConnection()) {
//            String query = "SELECT MaNhaCungCap, SDT FROM NhaCungCap WHERE TenNhaCungCap = ?";
//            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
//                pstmt.setString(1, tenNCC);
//                try (ResultSet rs = pstmt.executeQuery()) {
//                    if (rs.next()) {
//                        txtMaNCC.setText(rs.getString("MaNhaCungCap"));
//                        txtSdt.setText(rs.getString("SDT"));
//                    } else {
//                        txtMaNCC.setText("");
//                        txtSdt.setText("");
//                    }
//                }
//            }
//        } catch (SQLException ex) {
//            logger.log(Level.SEVERE, "Lỗi khi lấy thông tin nhà cung cấp", ex);
//            JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin nhà cung cấp", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
        String tenNCC = String.valueOf(cboTenNCC.getSelectedItem());

        if (tenNCC.equals("--- Chọn Nhà Cung Cấp ---") || tenNCC.isEmpty()) {
            txtMaNCC.setText("");
            txtSdt.setText("");
            return;
        }

        String sql = "SELECT MaNhaCungCap, SDT FROM NhaCungCap WHERE TenNhaCungCap = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) { // Sử dụng this.conn đã truyền vào từ constructor
            pstmt.setString(1, tenNCC);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    txtMaNCC.setText(rs.getString("MaNhaCungCap"));
                    txtSdt.setText(rs.getString("SDT"));
                } else {
                    txtMaNCC.setText("");
                    txtSdt.setText("");
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi lấy thông tin nhà cung cấp", ex);
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin nhà cung cấp", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void txtTimKiemBinhGas_TextChanged() {
//        try (Connection conn = DBConnection.openConnection()) {
//            String keyword = txtTimKiemBinhGas.getText().trim();
//            String query = "SELECT MaBinhGas, TenBinhGas, LoaiBinh, SoLuong, GiaVonTrungBinh FROM BinhGas WHERE MaBinhGas LIKE ? OR TenBinhGas LIKE ?";
//
//            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
//                pstmt.setString(1, "%" + keyword + "%");
//                pstmt.setString(2, "%" + keyword + "%");
//                try (ResultSet rs = pstmt.executeQuery()) {
//                    modelKetQuaTimKiemBinhGas.setRowCount(0); // Xóa dữ liệu cũ
//                    while (rs.next()) {
//                        Vector<Object> rowData = new Vector<>();
//                        // Thêm dữ liệu theo thứ tự cột đã định nghĩa trong modelKetQuaTimKiemBinhGas
//                        rowData.add(rs.getString("MaBinhGas"));
//                        rowData.add(rs.getString("TenBinhGas"));
//                        rowData.add(rs.getString("LoaiBinh"));
//                        rowData.add(rs.getInt("SoLuong")); // Tương ứng với cột "Tồn Kho"
//                        rowData.add(rs.getObject("GiaVonTrungBinh")); // Tương ứng với cột "Giá Vốn Trung Bình"
//                        modelKetQuaTimKiemBinhGas.addRow(rowData);
//                    }
//                }
//            }
//            int giaVonColumnIndex = -1;
//            for (int i = 0; i < modelKetQuaTimKiemBinhGas.getColumnCount(); i++) {
//                if (modelKetQuaTimKiemBinhGas.getColumnName(i).equalsIgnoreCase("GiaVonTrungBinh")) {
//                    giaVonColumnIndex = i;
//                    break;
//                }
//            }
//
//            if (giaVonColumnIndex != -1) {
//                dgvKetQuaTimKiemBinhGas.getColumnModel().getColumn(giaVonColumnIndex).setMinWidth(0);
//                dgvKetQuaTimKiemBinhGas.getColumnModel().getColumn(giaVonColumnIndex).setMaxWidth(0);
//                dgvKetQuaTimKiemBinhGas.getColumnModel().getColumn(giaVonColumnIndex).setWidth(0);
//                dgvKetQuaTimKiemBinhGas.getColumnModel().getColumn(giaVonColumnIndex).setPreferredWidth(0);
//            }
//        } catch (SQLException ex) {
//            logger.log(Level.SEVERE, "Lỗi khi tìm kiếm bình gas", ex);
//            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm bình gas", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
        String keyword = txtTimKiemBinhGas.getText().trim();
        String query = "SELECT MaBinhGas, TenBinhGas, LoaiBinh, SoLuong, GiaVonTrungBinh FROM BinhGas WHERE MaBinhGas LIKE ? OR TenBinhGas LIKE ?";

        try (PreparedStatement pstmt = this.conn.prepareStatement(query)) { // Dùng this.conn đã truyền từ constructor
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                modelKetQuaTimKiemBinhGas.setRowCount(0); // Xóa dữ liệu cũ
                while (rs.next()) {
                    Vector<Object> rowData = new Vector<>();
                    // Thêm dữ liệu theo thứ tự cột đã định nghĩa trong modelKetQuaTimKiemBinhGas
                    rowData.add(rs.getString("MaBinhGas"));
                    rowData.add(rs.getString("TenBinhGas"));
                    rowData.add(rs.getString("LoaiBinh"));
                    rowData.add(rs.getInt("SoLuong")); // Tồn Kho
                    rowData.add(rs.getObject("GiaVonTrungBinh")); // Giá Vốn Trung Bình
                    modelKetQuaTimKiemBinhGas.addRow(rowData);
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Lỗi khi tìm kiếm bình gas", ex);
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm bình gas", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ẩn cột "GiaVonTrungBinh" nếu tồn tại
        int giaVonColumnIndex = modelKetQuaTimKiemBinhGas.findColumn("GiaVonTrungBinh");
        if (giaVonColumnIndex != -1) {
            dgvKetQuaTimKiemBinhGas.getColumnModel().getColumn(giaVonColumnIndex).setMinWidth(0);
            dgvKetQuaTimKiemBinhGas.getColumnModel().getColumn(giaVonColumnIndex).setMaxWidth(0);
            dgvKetQuaTimKiemBinhGas.getColumnModel().getColumn(giaVonColumnIndex).setWidth(0);
            dgvKetQuaTimKiemBinhGas.getColumnModel().getColumn(giaVonColumnIndex).setPreferredWidth(0);
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

            int giaVonDeXuat = 0;
            int giaVonColumnIndex = -1;

            for (int i = 0; i < modelKetQuaTimKiemBinhGas.getColumnCount(); i++) {
                if (modelKetQuaTimKiemBinhGas.getColumnName(i).equalsIgnoreCase("GiaVonTrungBinh")) {
                    giaVonColumnIndex = i;
                    break;
                }
            }

            if (giaVonColumnIndex != -1) {
                Object giaVonObj = modelKetQuaTimKiemBinhGas.getValueAt(row, giaVonColumnIndex);
                if (giaVonObj != null) {
                    try {
                        giaVonDeXuat = Integer.parseInt(giaVonObj.toString());
                    } catch (NumberFormatException e) {
                        logger.log(Level.WARNING, "Không thể chuyển đổi Giá Vốn Trung Bình: " + giaVonObj, e);
                    }
                }
            }

            int soLuong = 0;
            while (true) {
                String input = JOptionPane.showInputDialog(this, String.format("Nhập số lượng nhập cho: %s", tenBinhGas));
                if (input == null) { // Người dùng nhấn Cancel
                    return;
                }

                input = input.trim();
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Số lượng không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                try {
                    soLuong = Integer.parseInt(input);
                    if (soLuong > 0) { // Số lượng phải dương
                        break;
                    } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên dương cho số lượng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên hợp lệ cho số lượng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            int donGia = 0;
            while (true) {
                String input = (String) JOptionPane.showInputDialog(
                        this,
                        String.format("Nhập đơn giá nhập cho: %s (Giá vốn đề xuất: %,d VNĐ)", tenBinhGas, giaVonDeXuat),
                        "Nhập Đơn Giá Nhập",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        String.valueOf(giaVonDeXuat) // Giá trị mặc định
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
                    donGia = Integer.parseInt(input);
                    // CHỈNH SỬA TẠI ĐÂY: Đảm bảo đơn giá không âm
                    if (donGia >= 0) { // Đơn giá phải không âm (có thể là 0 nếu bạn cho phép)
                        break;
                    } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên không âm cho đơn giá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên hợp lệ cho đơn giá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Tạo và thêm dòng mới
            ChiTietNhapHang newItem = new ChiTietNhapHang(txtMaHoaDon.getText(), maBinhGas, tenBinhGas, soLuong, donGia, "");
            modelChiTietNhapHang.addRow(newItem);

            capNhatTongTien();

            // Chọn dòng mới và scroll đến đó
            dgvChiTietNhapHang.clearSelection();
            int lastRow = modelChiTietNhapHang.getRowCount() - 1;
            if (lastRow >= 0) {
                dgvChiTietNhapHang.setRowSelectionInterval(lastRow, lastRow);
                dgvChiTietNhapHang.scrollRectToVisible(dgvChiTietNhapHang.getCellRect(lastRow, 0, true));
            }
        } finally {
            dgvKetQuaTimKiemBinhGas.clearSelection();
        }
    }

    private void capNhatTongTien() {
        int tongTien = 0;
        for (ChiTietNhapHang item : modelChiTietNhapHang.getData()) {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        dgvKetQuaTimKiemBinhGas = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        dgvChiTietNhapHang = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMaNCC = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        cboTenNCC = new javax.swing.JComboBox<>();
        btmThemKhachHang = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        dtpNgayXuat = new com.toedter.calendar.JDateChooser();
        txtTimKiemBinhGas = new javax.swing.JTextField();
        btnThanhToan = new javax.swing.JButton();
        btnXoaChiTiet = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        lblQuanLyTaiKhoan = new javax.swing.JLabel();

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

        dgvChiTietNhapHang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(dgvChiTietNhapHang);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setName("pnlLoaiBinh"); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(114, 58));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Tên nhà cung cấp");
        jLabel6.setName("txtLoaiBinh"); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Số điện thoại");
        jLabel7.setName("txtLoaiBinh"); // NOI18N

        txtMaNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNCCActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Mã nhà cung cấp");
        jLabel3.setName("txtLoaiBinh"); // NOI18N

        txtSdt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSdtActionPerformed(evt);
            }
        });

        cboTenNCC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTenNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenNCCActionPerformed(evt);
            }
        });

        btmThemKhachHang.setBackground(new java.awt.Color(0, 176, 80));
        btmThemKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btmThemKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        btmThemKhachHang.setText("Thêm");
        btmThemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btmThemKhachHangActionPerformed(evt);
            }
        });

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
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtMaNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cboTenNCC, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboTenNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btmThemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

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
                .addGap(18, 34, Short.MAX_VALUE)
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

        txtTimKiemBinhGas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemBinhGasActionPerformed(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(0, 176, 80));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnXoaChiTiet.setBackground(new java.awt.Color(237, 28, 36));
        btnXoaChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaChiTiet.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaChiTiet.setText("Xóa chi tiết");
        btnXoaChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaChiTietActionPerformed(evt);
            }
        });

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

        lblQuanLyTaiKhoan.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblQuanLyTaiKhoan.setText("Hóa đơn nhập");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(131, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(btnXoaChiTiet)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 956, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(txtTimKiemBinhGas, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(402, 402, 402)
                            .addComponent(btnThanhToan))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 956, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(125, 125, 125))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(132, 132, 132)
                    .addComponent(lblQuanLyTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1212, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiemBinhGas, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaChiTiet))
                .addContainerGap(86, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblQuanLyTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(716, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNCCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNCCActionPerformed

    private void txtSdtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSdtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSdtActionPerformed

    private void txtMaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNhanVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNhanVienActionPerformed

    private void txtMaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaHoaDonActionPerformed

    private void txtTongTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienActionPerformed
        capNhatTongTien();        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTienActionPerformed

    private void cboTenNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenNCCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTenNCCActionPerformed

    private void txtTimKiemBinhGasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemBinhGasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemBinhGasActionPerformed

    private void btnXoaChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaChiTietActionPerformed
        int selectedRow = dgvChiTietNhapHang.getSelectedRow();

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
                // Lấy model của dgvChiTietNhapHang
                // Vì bạn đã dùng ChiTietNhapHangTableModel, chúng ta sẽ cast về nó
                ChiTietNhapHangTableModel customModel = (ChiTietNhapHangTableModel) dgvChiTietNhapHang.getModel();

                // Gọi phương thức removeRow của ChiTietNhapHangTableModel
                // Phương thức này sẽ xóa dòng khỏi ArrayList nội bộ và thông báo cho JTable
                customModel.removeRow(selectedRow); // SẼ KÍCH HOẠT TABLEMODELEVENT.DELETE

                // Thông báo thành công
                JOptionPane.showMessageDialog(this, "Đã xóa chi tiết thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                // Ghi chú: capNhatTongTien() sẽ tự động được gọi nhờ TableModelListener
                // mà bạn đã thêm vào modelChiTietNhapHang trong initChiTietNhapHangTable().
                // Không cần gọi tường minh ở đây.
            } catch (Exception e) {
                // Xử lý lỗi nếu có
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa chi tiết: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace(); // In stack trace để debug
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaChiTietActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        String maNhapHang = txtMaHoaDon.getText().trim(); // Thêm .trim() để loại bỏ khoảng trắng
        String maNhaCungCap = txtMaNCC.getText().trim(); // Thêm .trim()
        String maNhanVien = txtMaNhanVien.getText().trim(); // Thêm .trim()

        // Kiểm tra các trường văn bản và số lượng dòng trong bảng chi tiết
        if (maNhapHang.isEmpty() || maNhaCungCap.isEmpty() || maNhanVien.isEmpty() || modelChiTietNhapHang.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin hóa đơn và thêm chi tiết nhập hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        java.sql.Date ngayNhap = null;
        try {
            ngayNhap = new java.sql.Date(dtpNgayXuat.getDate().getTime());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày nhập.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Connection conn = null;
        try {
            //conn = DBConnection.openConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Thêm mới bản ghi vào bảng NhapHang
            String sqlInsertNhapHang = "INSERT INTO NhapHang (MaNhapHang, MaNhaCungCap, MaNhanVien, NgayNhap) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlInsertNhapHang)) {
                ps.setString(1, maNhapHang);
                ps.setString(2, maNhaCungCap);
                ps.setString(3, maNhanVien);
                ps.setDate(4, ngayNhap);
                ps.executeUpdate();
            }

            // 2. Thêm mới các bản ghi vào bảng ChiTietNhapHang và cập nhật bảng BinhGas
            String sqlInsertChiTiet = "INSERT INTO ChiTietNhapHang (MaNhapHang, MaBinhGas, SoLuongNhap, DonGiaNhap) VALUES (?, ?, ?, ?)";
            String sqlUpdateBinhGas = "UPDATE BinhGas SET SoLuong = SoLuong + ?, GiaVonTrungBinh = ? WHERE MaBinhGas = ?";
            String sqlGetOldBinhGasInfo = "SELECT SoLuong, GiaVonTrungBinh FROM BinhGas WHERE MaBinhGas = ?";

            for (ChiTietNhapHang item : modelChiTietNhapHang.getData()) {
                // Lấy thông tin bình gas cũ
                int oldSoLuong = 0;
                double oldGiaVonTrungBinh = 0;
                try (PreparedStatement psGet = conn.prepareStatement(sqlGetOldBinhGasInfo)) {
                    psGet.setString(1, item.getMaBinhGas());
                    try (ResultSet rs = psGet.executeQuery()) {
                        if (rs.next()) {
                            oldSoLuong = rs.getInt("SoLuong");
                            oldGiaVonTrungBinh = rs.getDouble("GiaVonTrungBinh");
                        }
                    }
                }

                // Chèn chi tiết nhập hàng
                try (PreparedStatement ps = conn.prepareStatement(sqlInsertChiTiet)) {
                    ps.setString(1, item.getMaNhapHang());
                    ps.setString(2, item.getMaBinhGas());
                    ps.setInt(3, item.getSoLuongNhap());
                    ps.setDouble(4, item.getDonGiaNhap());
                    ps.executeUpdate();
                }

                // Cập nhật số lượng và tính lại giá vốn trung bình
                int newSoLuong = oldSoLuong + item.getSoLuongNhap();
                double newGiaVonTrungBinh = ((oldSoLuong * oldGiaVonTrungBinh) + (item.getSoLuongNhap() * item.getDonGiaNhap())) / newSoLuong;

                try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateBinhGas)) {
                    psUpdate.setInt(1, item.getSoLuongNhap()); // SoLuong in update is the quantity to add
                    psUpdate.setDouble(2, newGiaVonTrungBinh);
                    psUpdate.setString(3, item.getMaBinhGas());
                    psUpdate.executeUpdate();
                }
            }

            conn.commit(); // Hoàn tất transaction
            JOptionPane.showMessageDialog(this, "Lưu hóa đơn nhập thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            this.setSavedSuccessfully(true); // Set the flag to true
            this.dispose(); // Close the form

        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback transaction nếu có lỗi
                }
            } catch (SQLException rollbackEx) {
                // logger.log(Level.SEVERE, "Lỗi khi rollback transaction", rollbackEx); // Uncomment nếu có logger
                System.err.println("Lỗi khi rollback transaction: " + rollbackEx.getMessage());
            }
            // logger.log(Level.SEVERE, "Lỗi khi lưu hóa đơn nhập", ex); // Uncomment nếu có logger
            System.err.println("Lỗi khi lưu hóa đơn nhập: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn nhập: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.setAutoCommit(true); // Đặt lại auto-commit
//                    conn.close();
//                } catch (SQLException closeEx) {
//                    // logger.log(Level.SEVERE, "Lỗi khi đóng kết nối", closeEx); // Uncomment nếu có logger
//                    System.err.println("Lỗi khi đóng kết nối: " + closeEx.getMessage());
//                }
//            }
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btmThemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmThemKhachHangActionPerformed
        frmTest f = new frmTest();
        dlgChiTietNhaCungCap d = new dlgChiTietNhaCungCap(f, null, true, nhaCungCapDAO);
        d.loadData();
        d.setVisible(true);

        loadMaNhaCungCapComboBox();
    }//GEN-LAST:event_btmThemKhachHangActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
//            logger.log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//java.awt.EventQueue.invokeLater(() -> new frmHoaDonNhap().setVisible(true));
//        /* Create and display the form */   
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btmThemKhachHang;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnXoaChiTiet;
    private javax.swing.JComboBox<String> cboTenNCC;
    private javax.swing.JTable dgvChiTietNhapHang;
    private javax.swing.JTable dgvKetQuaTimKiemBinhGas;
    private com.toedter.calendar.JDateChooser dtpNgayXuat;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblQuanLyTaiKhoan;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaNCC;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTimKiemBinhGas;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables

    boolean isAddedSuccessfully() {
        return addedSuccessfully;
    }

    private void setAddedSuccessfully(boolean status) {
        this.addedSuccessfully = status;
    }
}
