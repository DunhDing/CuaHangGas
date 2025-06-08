/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandat.cuahanggas.ui;

import com.toedter.calendar.JDateChooser;
import com.tuandat.cuahanggas.dao.impl.ChiTietNhapHangDAO;
import com.tuandat.cuahanggas.model.ChiTietNhapHang;
import com.tuandat.cuahanggas.model.ExcelExporter;
import com.tuandat.cuahanggas.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.tuandat.cuahanggas.utils.TableHelper;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author duck
 */
public class pnlNhapHang extends javax.swing.JPanel {

    private static final Logger logger = Logger.getLogger(pnlXuatHang.class.getName());
    private Connection conn;

    /**
     * Creates new form pnlNhapHang
     */
    public pnlNhapHang(Connection c) {
        this.conn = c;
        initComponents();
        ImageIcon iconThem = new ImageIcon(getClass().getResource("/them.png"));
        Image imgThem = iconThem.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btnThem.setIcon(new ImageIcon(imgThem));

        ImageIcon iconChiTiet = new ImageIcon(getClass().getResource("/info.png"));
        Image imgChiTiet = iconChiTiet.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btnChiTiet.setIcon(new ImageIcon(imgChiTiet));

        ImageIcon iconXuatFile = new ImageIcon(getClass().getResource("/excel.png"));
        Image imgXuatFile = iconXuatFile.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btnXuat.setIcon(new ImageIcon(imgXuatFile));

        ImageIcon iconXoa = new ImageIcon(getClass().getResource("/trash-solid.png"));
        Image imgXoa = iconXoa.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btnXoa.setIcon(new ImageIcon(imgXoa));
        
        ImageIcon iconTimKiem = new ImageIcon(getClass().getResource("/timkiem.png"));
        Image imgTimKiem = iconTimKiem.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        lblTimKiem.setIcon(new ImageIcon(imgTimKiem));
        setupComponents();
        loadComboBoxData();
        timKiemNhapHang();
    }

    private void setupComponents() {
        cboLoaiLocNgay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            "Không lọc", "Theo ngày", "Theo tháng", "Theo năm"
        }));

        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                timKiemNhapHang();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                timKiemNhapHang();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                timKiemNhapHang();
            }
        });

        // Thiết lập ngày mặc định cho dtpNgayNhap là ngày hiện tại
        dtpNgayNhap.setDate(new Date());

        // Lắng nghe sự kiện click đúp vào bảng để mở chi tiết
        dgvNhapHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    btnChiTietActionPerformed(null); // Gọi hành động xem chi tiết khi click đúp
                }
            }
        });
    }

    private void loadComboBoxData() {
        //Connection conn = null; // Khai báo biến conn
        Statement stmt = null; // Khai báo biến stmt
        ResultSet rsNCC = null; // Khai báo biến rsNCC
        ResultSet rsNV = null; // Khai báo biến rsNV

        try {
            //conn = DBConnection.openConnection(); // Chỉ lấy kết nối, không đóng ở đây
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Không thể kết nối CSDL để tải dữ liệu combobox. Vui lòng kiểm tra kết nối.", "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
                return;
            }
            stmt = conn.createStatement();

            rsNCC = stmt.executeQuery("SELECT DISTINCT MaNhaCungCap FROM NhapHang ORDER BY MaNhaCungCap ASC");
            cboMaNhaCungCap.removeAllItems();
            cboMaNhaCungCap.addItem("Tất cả");
            while (rsNCC.next()) {
                cboMaNhaCungCap.addItem(rsNCC.getString("MaNhaCungCap"));
            }

            rsNV = stmt.executeQuery("SELECT DISTINCT MaNhanVien FROM NhanVien ORDER BY MaNhanVien ASC");
            cboMaNhanVien.removeAllItems();
            cboMaNhanVien.addItem("Tất cả");
            while (rsNV.next()) {
                cboMaNhanVien.addItem(rsNV.getString("MaNhanVien"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // In ra lỗi để debug
        } finally {
            // Đóng ResultSet và Statement ở đây
            try {
                if (rsNCC != null) {
                    rsNCC.close();
                }
                if (rsNV != null) {
                    rsNV.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                // KHÔNG ĐÓNG 'conn' Ở ĐÂY, vì nó là kết nối tĩnh của DBConnection
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void loadData() {
        System.out.println("pnlNhapHang: loadData() - Bắt đầu.");
        DefaultTableModel model = (DefaultTableModel) dgvNhapHang.getModel();
        model.setRowCount(0);

        //Connection conn = DBConnection.openConnection(); // Lấy kết nối
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối CSDL để tải dữ liệu chi tiết. Vui lòng kiểm tra kết nối.", "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
            System.err.println("pnlNhapHang: loadData() - Kết nối CSDL null.");
            return; // Thoát nếu không có kết nối
        }

        ChiTietNhapHangDAO chiTietNhapHangDAO = new ChiTietNhapHangDAO(conn); // Truyền kết nối vào DAO
        List<ChiTietNhapHang> danhSachChiTietNhap = new ArrayList<>();
        try {
            danhSachChiTietNhap = chiTietNhapHangDAO.getAll();
            System.out.println("pnlNhapHang: loadData() - Đã lấy được " + danhSachChiTietNhap.size() + " ChiTietNhapHang từ DAO.");

            // Kiểm tra xem danh sách có rỗng không
            if (danhSachChiTietNhap.isEmpty()) {
                System.out.println("pnlNhapHang: loadData() - Danh sách ChiTietNhapHang trống.");
                // Có thể hiển thị thông báo "Không có dữ liệu" trên bảng nếu muốn
            } else {
                model.setColumnIdentifiers(new Object[]{"Mã NH", "Mã Gas", "Số Lượng", "Đơn Giá", "Tổng Tiền"}); // Tên các cột
                for (ChiTietNhapHang ctnh : danhSachChiTietNhap) {
                    model.addRow(new Object[]{
                        ctnh.getMaNhapHang(),
                        ctnh.getMaBinhGas(),
                        ctnh.getSoLuongNhap(),
                        ctnh.getDonGiaNhap(),
                        ctnh.getThanhTien() // Tính tổng tiền
                    });
                }
                System.out.println("pnlNhapHang: loadData() - Dữ liệu ChiTietNhapHang đã được tải vào bảng.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu chi tiết nhập hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.err.println("pnlNhapHang: loadData() - Lỗi khi tải dữ liệu chi tiết nhập hàng: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // KHÔNG ĐÓNG KẾT NỐI Ở ĐÂY, vì nó là kết nối tĩnh của DBConnection
        }
        System.out.println("pnlNhapHang: loadData() - Kết thúc.");
    }

    /**
     * @param args the command line arguments
     */
    private void timKiemNhapHang() {
        System.out.println("pnlNhapHang: timKiemNhapHang() - Bắt đầu.");
        String tuKhoa = txtTimKiem.getText().trim();
        String maNCC = (String) cboMaNhaCungCap.getSelectedItem();
        String maNV = (String) cboMaNhanVien.getSelectedItem();
        String locTheo = (String) cboLoaiLocNgay.getSelectedItem();
        Date ngayNhap = dtpNgayNhap.getDate();

        StringBuilder query = new StringBuilder(
                "SELECT nh.MaNhapHang, nh.NgayNhap, nh.MaNhaCungCap, ncc.TenNhaCungCap, "
                + "nh.MaNhanVien, nv.HoTen AS TenNhanVien, "
                + "ISNULL(SUM(ctnh.SoLuongNhap * ctnh.DonGiaNhap), 0) AS TongTienHoaDonNhap, nh.GhiChu "
                + "FROM NhapHang nh "
                + "LEFT JOIN ChiTietNhapHang ctnh ON nh.MaNhapHang = ctnh.MaNhapHang "
                + "INNER JOIN NhanVien nv ON nh.MaNhanVien = nv.MaNhanVien "
                + "LEFT JOIN NhaCungCap ncc ON nh.MaNhaCungCap = ncc.MaNhaCungCap "
                + "WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (!tuKhoa.isEmpty()) {
            query.append("AND (nh.MaNhapHang LIKE ? OR nh.MaNhaCungCap LIKE ? OR nh.MaNhanVien LIKE ? OR nv.HoTen LIKE ? OR ncc.TenNhaCungCap LIKE ? OR nh.GhiChu LIKE ?) ");
            for (int i = 0; i < 6; i++) {
                params.add("%" + tuKhoa + "%");
            }
        }

        if (!"Tất cả".equals(maNCC)) {
            query.append("AND nh.MaNhaCungCap = ? ");
            params.add(maNCC);
        }

        if (!"Tất cả".equals(maNV)) {
            query.append("AND nh.MaNhanVien = ? ");
            params.add(maNV);
        }

        if (!"Không lọc".equals(locTheo) && ngayNhap != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(ngayNhap);

            switch (locTheo) {
                case "Theo ngày":
                    query.append("AND CONVERT(DATE, nh.NgayNhap) = ? ");
                    params.add(new java.sql.Date(ngayNhap.getTime()));
                    break;
                case "Theo tháng":
                    query.append("AND MONTH(nh.NgayNhap) = ? AND YEAR(nh.NgayNhap) = ? ");
                    params.add(cal.get(Calendar.MONTH) + 1);
                    params.add(cal.get(Calendar.YEAR));
                    break;
                case "Theo năm":
                    query.append("AND YEAR(nh.NgayNhap) = ? ");
                    params.add(cal.get(Calendar.YEAR));
                    break;
            }
        }

        query.append("GROUP BY nh.MaNhapHang, nh.NgayNhap, nh.MaNhaCungCap, ncc.TenNhaCungCap, nh.MaNhanVien, nv.HoTen, nh.GhiChu "
                + "ORDER BY nh.NgayNhap DESC, nh.MaNhapHang ASC");
        System.out.println("DEBUG SQL Query Final: " + query.toString());
        System.out.println("DEBUG SQL Params Final: " + params.toString());
        //Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //conn = DBConnection.openConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Không thể kết nối CSDL để tìm kiếm. Vui lòng kiểm tra kết nối.", "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
                System.err.println("pnlNhapHang: timKiemNhapHang() - Kết nối CSDL null.");
                return;
            }
            ps = conn.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            rs = ps.executeQuery();
            LinkedHashMap<String, String> columnHeaderMapping = new LinkedHashMap<>();
            columnHeaderMapping.put("MaNhapHang", "Mã Nhập Hàng");
            columnHeaderMapping.put("NgayNhap", "Ngày Nhập");
            columnHeaderMapping.put("MaNhaCungCap", "Mã NCC");
            columnHeaderMapping.put("TenNhaCungCap", "Tên Nhà Cung Cấp"); // Nếu bạn join với bảng NCC và có alias
            columnHeaderMapping.put("MaNhanVien", "Mã Nhân Viên");
            columnHeaderMapping.put("TenNhanVien", "Tên Nhân Viên"); // Tên alias từ SQL
            columnHeaderMapping.put("TongTienHoaDonNhap", "Tổng Tiền Hóa Đơn"); // Tên alias từ SQL
            columnHeaderMapping.put("GhiChu", "Ghi Chú");

            dgvNhapHang.setModel(TableHelper.resultSetToTableModel(rs, columnHeaderMapping));
            System.out.println("pnlNhapHang: timKiemNhapHang() - Đã thực thi truy vấn. Số hàng trả về: " + dgvNhapHang.getRowCount()); // In ra số hàng
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.err.println("pnlNhapHang: timKiemNhapHang() - Lỗi SQLException: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("pnlNhapHang: timKiemNhapHang() - Kết thúc.");
    }

    private void deleteSelectedNhapHang() {
        int selectedRow = dgvNhapHang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần xóa.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maNhapHang = (String) dgvNhapHang.getValueAt(selectedRow, 0); // Mã nhập hàng ở cột đầu tiên

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa phiếu nhập " + maNhapHang + " này và tất cả chi tiết liên quan?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            //Connection conn = null;
            PreparedStatement psDeleteChiTiet = null;
            PreparedStatement psDeleteNhapHang = null;

            try {
                //conn = DBConnection.openConnection();
                conn.setAutoCommit(false); // Bắt đầu transaction

                // Xóa chi tiết nhập hàng trước
                String sqlDeleteChiTiet = "DELETE FROM ChiTietNhapHang WHERE MaNhapHang = ?";
                psDeleteChiTiet = conn.prepareStatement(sqlDeleteChiTiet);
                psDeleteChiTiet.setString(1, maNhapHang);
                psDeleteChiTiet.executeUpdate();

                // Xóa phiếu nhập hàng
                String sqlDeleteNhapHang = "DELETE FROM NhapHang WHERE MaNhapHang = ?";
                psDeleteNhapHang = conn.prepareStatement(sqlDeleteNhapHang);
                psDeleteNhapHang.setString(1, maNhapHang);
                int rowsAffected = psDeleteNhapHang.executeUpdate();

                if (rowsAffected > 0) {
                    conn.commit(); // Commit transaction nếu thành công
                    JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    timKiemNhapHang(); // Tải lại dữ liệu
                } else {
                    conn.rollback(); // Rollback nếu không có hàng nào bị ảnh hưởng (phiếu nhập không tồn tại)
                    JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu nhập để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }

            } catch (SQLException e) {
                try {
                    if (conn != null) {
                        conn.rollback(); // Rollback nếu có lỗi
                    }
                } catch (SQLException ex) {

                }
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa phiếu nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (psDeleteChiTiet != null) {
                        psDeleteChiTiet.close();
                    }
                    if (psDeleteNhapHang != null) {
                        psDeleteNhapHang.close();
                    }
                    if (conn != null) {
                        conn.setAutoCommit(true); // Đặt lại auto-commit
                    }
                } catch (SQLException e) {
                    // Ghi lại ngoại lệ nếu cần
                }
            }
        }
    }

    private void exportNhapToExcel() {
        // Thay dgvXuatHang bằng JTable của nhập hàng, ví dụ: dgvNhapHang
        DefaultTableModel model = (DefaultTableModel) dgvNhapHang.getModel();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu nhập hàng để xuất ra Excel.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel Hóa đơn Nhập"); // Đổi tiêu đề hộp thoại
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        // Đổi tên file gợi ý ban đầu
        fileChooser.setSelectedFile(new File("DanhSachHoaDonNhap_" + System.currentTimeMillis() + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }

            try {
                // Gọi phương thức xuất nhập hàng từ class ExcelExporter
                ExcelExporter.exportHoaDonXuatToExcel( // Đổi sang exportHoaDonNhapToExcel
                        dgvNhapHang, // JTable chứa dữ liệu nhập hàng
                        fileToSave.getAbsolutePath(), // Đường dẫn file sẽ lưu
                        "Danh Sách Hóa Đơn Nhập", // Tên sheet trong Excel
                        "DANH SÁCH HÓA ĐƠN NHẬP HÀNG" // Tiêu đề chính của báo cáo
                );
                JOptionPane.showMessageDialog(this, "Xuất file Excel Hóa đơn Nhập thành công!\n" + fileToSave.getAbsolutePath(), "Thành công", JOptionPane.INFORMATION_MESSAGE); // Đổi thông báo
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel Hóa đơn Nhập: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); // Đổi thông báo lỗi
                logger.log(Level.SEVERE, "Lỗi khi xuất file Excel Hóa đơn Nhập", ex); // Đổi thông báo log
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        dtpNgayNhap = new com.toedter.calendar.JDateChooser();
        cboLoaiLocNgay = new javax.swing.JComboBox<>();
        btnXuat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cboMaNhanVien = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgvNhapHang = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cboMaNhaCungCap = new javax.swing.JComboBox<>();
        btnChiTiet = new javax.swing.JButton();
        lblQuanLyTaiKhoan = new javax.swing.JLabel();
        lblTimKiem = new javax.swing.JLabel();

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel5.setName("pnlLoaiBinh"); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Ngày nhập");
        jLabel4.setName("txtLoaiBinh"); // NOI18N

        dtpNgayNhap.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtpNgayNhapPropertyChange(evt);
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
                    .addComponent(jLabel4)
                    .addComponent(dtpNgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                    .addComponent(cboLoaiLocNgay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dtpNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboLoaiLocNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnXuat.setBackground(new java.awt.Color(0, 176, 80));
        btnXuat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXuat.setForeground(new java.awt.Color(255, 255, 255));
        btnXuat.setText("Xuất file");
        btnXuat.setName("btnXuatFile"); // NOI18N
        btnXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(237, 28, 36));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.setName("btnXoa"); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setName("pnlLoaiBinh"); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(114, 58));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
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
                    .addComponent(cboMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        dgvNhapHang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(dgvNhapHang);

        btnThem.setBackground(new java.awt.Color(0, 176, 80));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
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

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Mã nhà cung cấp");
        jLabel3.setName("txtLoaiVan"); // NOI18N

        cboMaNhaCungCap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMaNhaCungCap.setName("cboMaNCC"); // NOI18N
        cboMaNhaCungCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaNhaCungCapActionPerformed(evt);
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
                    .addComponent(cboMaNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboMaNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        btnChiTiet.setBackground(new java.awt.Color(153, 153, 153));
        btnChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChiTiet.setForeground(new java.awt.Color(255, 255, 255));
        btnChiTiet.setText("Chi tiết");
        btnChiTiet.setName("btnChiTiet"); // NOI18N
        btnChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiTietActionPerformed(evt);
            }
        });

        lblQuanLyTaiKhoan.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblQuanLyTaiKhoan.setText("Nhập hàng");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(lblQuanLyTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChiTiet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXuat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa)))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblQuanLyTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboMaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaNhanVienActionPerformed
        timKiemNhapHang();
    }//GEN-LAST:event_cboMaNhanVienActionPerformed

    private void cboMaNhaCungCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaNhaCungCapActionPerformed
        timKiemNhapHang();        // TODO add your handling code here:
    }//GEN-LAST:event_cboMaNhaCungCapActionPerformed

    private void cboLoaiLocNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiLocNgayActionPerformed
        dtpNgayNhap.setEnabled(cboLoaiLocNgay.getSelectedIndex() > 0);
        timKiemNhapHang();        // TODO add your handling code here:
    }//GEN-LAST:event_cboLoaiLocNgayActionPerformed

    private void dtpNgayNhapPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtpNgayNhapPropertyChange
        if ("date".equals(evt.getPropertyName())) {
            timKiemNhapHang();
        }     // TODO add your handling code here:
    }//GEN-LAST:event_dtpNgayNhapPropertyChange

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        frmHoaDonNhap hoaDonNhapForm = new frmHoaDonNhap(conn); // Khởi tạo form
        hoaDonNhapForm.setVisible(true); // Hiển thị form
        hoaDonNhapForm.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                // Sự kiện này được gọi khi form frmHoaDonNhap đóng
                if (hoaDonNhapForm.isSavedSuccessfully()) {
                    timKiemNhapHang(); // Gọi phương thức tải lại dữ liệu trên pnlNhapHang
                }
                hoaDonNhapForm.dispose(); // Đảm bảo form đã được đóng hoàn toàn
            }
        });

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiTietActionPerformed
        int selectedRow = dgvNhapHang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để xem chi tiết.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maNhapHang = (String) dgvNhapHang.getValueAt(selectedRow, 0); // Giả sử Mã Nhập Hàng ở cột 0

        // Mở dialog chi tiết nhập hàng và truyền mã nhập hàng
        dlgChiTietNhapHang chiTietDialog = new dlgChiTietNhapHang(null, true); // Thay 'null' bằng JFrame cha thực tế
        chiTietDialog.setMaNhapHang(maNhapHang);
        chiTietDialog.setVisible(true);

        // Sau khi dialog đóng, có thể cần tải lại dữ liệu nếu có sự thay đổi
        timKiemNhapHang();
    }//GEN-LAST:event_btnChiTietActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        deleteSelectedNhapHang();        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatActionPerformed
        exportNhapToExcel();  // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChiTiet;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuat;
    private javax.swing.JComboBox<String> cboLoaiLocNgay;
    private javax.swing.JComboBox<String> cboMaNhaCungCap;
    private javax.swing.JComboBox<String> cboMaNhanVien;
    private javax.swing.JTable dgvNhapHang;
    private com.toedter.calendar.JDateChooser dtpNgayNhap;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblQuanLyTaiKhoan;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
