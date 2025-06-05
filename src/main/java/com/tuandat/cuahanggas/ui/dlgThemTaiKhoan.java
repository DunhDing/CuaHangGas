package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.impl.TaiKhoanNguoiDungDAO;
import com.tuandat.cuahanggas.utils.DBConnection;
import java.awt.Image;
import javax.swing.*;
import java.sql.*;
import java.util.Map;

public class dlgThemTaiKhoan extends javax.swing.JDialog {

    private Connection conn;
    private TaiKhoanNguoiDungDAO taiKhoanDAO;
    private Map<String, String> vaiTrosMap;

    public dlgThemTaiKhoan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        conn = DBConnection.openConnection();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối CSDL! Vui lòng kiểm tra lại cấu hình.", "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.taiKhoanDAO = new TaiKhoanNguoiDungDAO(conn);
        
        initComponents();
        
        loadVaiTroToComboBox();
        
        ImageIcon iconLuu = new ImageIcon(getClass().getResource("/luu.png"));
        Image imgLuu = iconLuu.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        btnLuu.setIcon(new ImageIcon(imgLuu));
    }

    private void loadVaiTroToComboBox() {
        try {
            // Lấy danh sách vai trò từ database (bao gồm tên và mã vai trò)
            //vaiTrosMap = taiKhoanDAO.getDanhSachVaiTro(); // Trả về Map <TenVaiTro, MaVaiTro>

            // Kiểm tra nếu danh sách vai trò rỗng
            if (vaiTrosMap == null || vaiTrosMap.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có vai trò nào trong hệ thống! Vui lòng thêm vai trò trước.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Xóa tất cả các item hiện tại trong ComboBox
            cboVaiTro.removeAllItems();

            // Thêm các vai trò từ database vào ComboBox
            for (String tenVaiTro : vaiTrosMap.keySet()) {
                cboVaiTro.addItem(tenVaiTro);
            }
            // Đặt mục đầu tiên được chọn nếu có
            if (cboVaiTro.getItemCount() > 0) {
                cboVaiTro.setSelectedIndex(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải vai trò: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean kiemTraTrungTenDangNhap(String tenDangNhap) {
        String query = "SELECT COUNT(*) FROM TaiKhoanNguoiDung WHERE TenDangNhap = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tenDangNhap);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // Tên đăng nhập đã tồn tại
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi chi tiết ra console để debug
            JOptionPane.showMessageDialog(this, "Lỗi kiểm tra trùng tên đăng nhập: " + e.getMessage(), "Lỗi SQL", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private boolean kiemTraMaNhanVien(String maNhanVien) {
        maNhanVien = maNhanVien.trim();

        // Nếu mã nhân viên để trống, nó sẽ được lưu là NULL, và không cần kiểm tra tồn tại
        if (maNhanVien.isEmpty()) {
            return true; // Trả về true để cho phép lưu NULL
        }

        try {
            // 1. Kiểm tra sự tồn tại của MaNhanVien trong bảng NhanVien
            String queryNhanVien = "SELECT COUNT(*) FROM NhanVien WHERE MaNhanVien = ?";
            try (PreparedStatement stmtNhanVien = conn.prepareStatement(queryNhanVien)) {
                stmtNhanVien.setString(1, maNhanVien);
                try (ResultSet rsNhanVien = stmtNhanVien.executeQuery()) {
                    if (rsNhanVien.next() && rsNhanVien.getInt(1) == 0) {
                        JOptionPane.showMessageDialog(this, "Mã nhân viên không tồn tại trong hệ thống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }

            // 2. Kiểm tra xem MaNhanVien này đã có tài khoản chưa (tránh 1 nhân viên có nhiều tài khoản)
            String queryTaiKhoan = "SELECT COUNT(*) FROM TaiKhoanNguoiDung WHERE MaNhanVien = ?";
            try (PreparedStatement stmtTaiKhoan = conn.prepareStatement(queryTaiKhoan)) {
                stmtTaiKhoan.setString(1, maNhanVien);
                try (ResultSet rsTaiKhoan = stmtTaiKhoan.executeQuery()) {
                    if (rsTaiKhoan.next() && rsTaiKhoan.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(this, "Nhân viên này đã có tài khoản rồi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }
            return true; // Mã nhân viên hợp lệ và chưa có tài khoản
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kiểm tra mã nhân viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private String getLastMaTaiKhoan() {
        String maTaiKhoanCuoi = null;
        // SỬA CÂU LỆNH SQL TẠI ĐÂY: Dùng TOP 1 thay vì LIMIT 1 cho SQL Server
        String query = "SELECT TOP 1 MaTaiKhoan FROM TaiKhoanNguoiDung ORDER BY MaTaiKhoan DESC";

        try (PreparedStatement stmt = this.conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                maTaiKhoanCuoi = rs.getString("MaTaiKhoan");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Giữ lại để debug
            // Nếu có lỗi SQL ở đây, nó sẽ được in ra console.
            // Bạn có thể thêm JOptionPane.showMessageDialog nếu muốn thông báo lỗi người dùng
            // JOptionPane.showMessageDialog(this, "Lỗi khi lấy mã tài khoản cuối cùng: " + e.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        }
        return maTaiKhoanCuoi;
    }

    private String taoMaTaiKhoan() {
        String maTaiKhoanCuoi = getLastMaTaiKhoan();

        if (maTaiKhoanCuoi == null || maTaiKhoanCuoi.isEmpty()) {
            return "TK01"; // Bắt đầu từ TK01 nếu chưa có tài khoản nào
        }

        // Kiểm tra định dạng mã tài khoản cuối cùng để tránh lỗi NumberFormatException
        if (maTaiKhoanCuoi.length() < 3 || !maTaiKhoanCuoi.substring(0, 2).equalsIgnoreCase("TK")) {
            System.err.println("Cảnh báo: Mã tài khoản cuối cùng trong DB không đúng định dạng 'TKxx': " + maTaiKhoanCuoi);
            // Có thể reset lại hoặc đưa ra một cách xử lý khác tùy theo yêu cầu
            return "TK01"; // Nếu mã cuối không đúng định dạng, mặc định bắt đầu lại từ TK01
        }

        String prefix = maTaiKhoanCuoi.substring(0, 2);
        String suffix = maTaiKhoanCuoi.substring(2);

        int nextNumber;
        try {
            nextNumber = Integer.parseInt(suffix) + 1;
        } catch (NumberFormatException e) {
            System.err.println("Lỗi: Không thể chuyển đổi phần số của mã tài khoản cuối cùng thành số: " + suffix);
            e.printStackTrace();
            return "TK01"; // Xử lý lỗi: nếu không parse được, mặc định bắt đầu từ TK01
        }

        // Đảm bảo số có 2 chữ số (ví dụ 01, 02)
        String newSuffix = String.format("%02d", nextNumber);

        // Xử lý trường hợp vượt quá TK99 (sẽ thành TK100, TK101, ...)
        if (nextNumber > 99) {
            return prefix + String.valueOf(nextNumber);
        }
        return prefix + newSuffix;
    }

    private boolean luuTaiKhoan(String maTaiKhoan, String tenDangNhap, String matKhau, String maNhanVien, String vaiTro, String ghiChu) {
        try {
            String maVaiTro = vaiTrosMap.get(vaiTro);

            if (maVaiTro == null) {
                JOptionPane.showMessageDialog(this, "Không thể tìm thấy mã vai trò tương ứng. Vui lòng chọn vai trò hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            String query = "INSERT INTO TaiKhoanNguoiDung (MaTaiKhoan, TenDangNhap, MatKhau, MaNhanVien, MaVaiTro, GhiChu) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, maTaiKhoan);
                stmt.setString(2, tenDangNhap);
                stmt.setString(3, matKhau);

                // Xử lý MaNhanVien: nếu rỗng thì lưu NULL
                if (maNhanVien.isEmpty()) {
                    stmt.setNull(4, java.sql.Types.NVARCHAR);
                } else {
                    stmt.setString(4, maNhanVien);
                }

                stmt.setString(5, maVaiTro);

                // Xử lý GhiChu: nếu rỗng thì lưu NULL
                if (ghiChu.isEmpty()) {
                    stmt.setNull(6, java.sql.Types.NVARCHAR);
                } else {
                    stmt.setString(6, ghiChu);
                }

                int result = stmt.executeUpdate();
                return result > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi lưu tài khoản. Chi tiết: " + e.getMessage(), "Lỗi Lưu Dữ Liệu", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel12 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtNLMK = new javax.swing.JPasswordField();
        btnLuu = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cboVaiTro = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTenDangNhap = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        txtGhiChu = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new java.awt.Dimension(1008, 486));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Mã nhân viên:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Nhập lại mật khẩu:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(txtNLMK, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNLMK, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        btnLuu.setBackground(new java.awt.Color(0, 176, 80));
        btnLuu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLuu.setForeground(new java.awt.Color(255, 255, 255));
        btnLuu.setText("Lưu");
        btnLuu.setName("btnLuu"); // NOI18N
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Vai trò:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tên đăng nhập:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Ghi chú:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Mật khẩu:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(93, 93, 93))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        String tenDangNhap = txtTenDangNhap.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword()).trim();
        String nlmk = new String(txtNLMK.getPassword()).trim();
        String maNhanVien = txtMaNhanVien.getText().trim(); // Lấy mã nhân viên, có thể rỗng
        String vaiTro = (String) cboVaiTro.getSelectedItem();
        String ghiChu = txtGhiChu.getText().trim();

        // --- Bắt đầu kiểm tra dữ liệu đầu vào ---
        if (tenDangNhap.isEmpty() || matKhau.isEmpty() || nlmk.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ Tên đăng nhập, Mật khẩu và Nhập lại mật khẩu.", "Lỗi Thiếu Thông Tin", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!matKhau.equals(nlmk)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu và Nhập lại mật khẩu không khớp!", "Lỗi Xác Nhận Mật Khẩu", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (kiemTraTrungTenDangNhap(tenDangNhap)) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.", "Lỗi Trùng Lặp", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra mã nhân viên chỉ khi nó không rỗng.
        // Nếu maNhanVien rỗng, kiemTraMaNhanVien() sẽ trả về true và không cần kiểm tra thêm.
        if (!maNhanVien.isEmpty() && !kiemTraMaNhanVien(maNhanVien)) {
            // Lỗi đã được hiển thị bên trong kiemTraMaNhanVien(), chỉ cần return
            return;
        }

        if (vaiTro == null || vaiTro.isEmpty() || "Tất cả".equals(vaiTro)) { // Giả sử "Tất cả" là một lựa chọn không hợp lệ
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một vai trò hợp lệ.", "Lỗi Chọn Vai Trò", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- Hết kiểm tra dữ liệu đầu vào ---
        // Tạo mã tài khoản tự động
        String maTaiKhoan = taoMaTaiKhoan();
        if (maTaiKhoan == null || maTaiKhoan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không thể tạo mã tài khoản tự động. Vui lòng thử lại hoặc kiểm tra dữ liệu.", "Lỗi Tạo Mã", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lưu tài khoản vào cơ sở dữ liệu
        if (luuTaiKhoan(maTaiKhoan, tenDangNhap, matKhau, maNhanVien, vaiTro, ghiChu)) {
            JOptionPane.showMessageDialog(this, "Tài khoản đã được thêm thành công.", "Thành Công", JOptionPane.INFORMATION_MESSAGE);
            // Có thể thêm logic reset form hoặc đóng form tại đây
            // resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi lưu tài khoản.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnLuuActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLuu;
    private javax.swing.JComboBox<String> cboVaiTro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JPasswordField txtNLMK;
    private javax.swing.JTextField txtTenDangNhap;
    // End of variables declaration//GEN-END:variables
}
