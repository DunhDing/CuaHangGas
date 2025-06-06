/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.impl.BinhGasDAO;
import com.tuandat.cuahanggas.dao.impl.ChiTietXuatHangDAO;
import com.tuandat.cuahanggas.model.BinhGas;
import com.tuandat.cuahanggas.model.ChiTietXuatHang;
import com.tuandat.cuahanggas.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author duck
 */
public class dlgChiTietXuatHang extends javax.swing.JDialog {

    private static final Logger logger = Logger.getLogger(dlgChiTietXuatHang.class.getName());
    private String maXuatHangHienTai;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public dlgChiTietXuatHang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        resetForm();

    }

    private void resetForm() {
        txtMaXuatHang.setText("");
        txtMaKhachHang.setText("");
        txtMaNhanVien.setText("");
        txtNgayXuat.setText("");
        txtGhiChu.setText("");
        ((DefaultTableModel) dgvXuatHang.getModel()).setRowCount(0); // Xóa dữ liệu bảng
    }

    // Phương thức để thiết lập mã hóa đơn xuất và tải dữ liệu
    public void setMaXuatHang(String maXuatHang) {
        this.maXuatHangHienTai = maXuatHang;
        loadChiTietXuatHang();
    }

    private void loadChiTietXuatHang() {
        if (maXuatHangHienTai == null || maXuatHangHienTai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã xuất hàng chưa được thiết lập!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = DBConnection.openConnection();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Lấy thông tin phiếu xuất
            PreparedStatement ps1 = conn.prepareStatement(
                    "SELECT NgayXuat, MaNhanVien, MaKhachHang, GhiChu FROM XuatHang WHERE MaXuatHang = ?"
            );
            ps1.setString(1, maXuatHangHienTai);
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                txtMaXuatHang.setText(maXuatHangHienTai);
                txtNgayXuat.setText(sdf.format(rs1.getDate("NgayXuat")));
                txtMaNhanVien.setText(rs1.getString("MaNhanVien"));
                txtMaKhachHang.setText(rs1.getString("MaKhachHang"));
                txtGhiChu.setText(rs1.getString("GhiChu"));
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lấy chi tiết xuất hàng
            PreparedStatement ps2 = conn.prepareStatement(
                    "SELECT MaBinhGas, SoLuongXuat, DonGiaXuat, GhiChu FROM ChiTietXuatHang WHERE MaXuatHang = ?"
            );
            ps2.setString(1, maXuatHangHienTai);
            ResultSet rs2 = ps2.executeQuery();

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"Mã Bình", "Tên Bình", "Số lượng", "Đơn giá", "Thành tiền", "Ghi chú"}, 0
            );

            BinhGasDAO binhGasDAO = new BinhGasDAO(conn);

            while (rs2.next()) {
                String maBinh = rs2.getString("MaBinhGas");
                int soLuong = rs2.getInt("SoLuongXuat");
                int donGia = rs2.getInt("DonGiaXuat");
                String ghiChu = rs2.getString("GhiChu");
                String tenBinh = "Không rõ";

                try {
                    BinhGas binhGas = binhGasDAO.findById(maBinh);
                    if (binhGas != null) {
                        tenBinh = binhGas.getTenBinhGas();
                    }
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Lỗi khi tìm tên bình gas", e);
                }

                int thanhTien = soLuong * donGia;

                model.addRow(new Object[]{
                    maBinh,
                    tenBinh,
                    soLuong,
                    donGia,
                    thanhTien,
                    ghiChu
                });
            }

            dgvXuatHang.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            logger.log(Level.SEVERE, "Lỗi khi load chi tiết xuất hàng", ex);
        }
    }

     private void luuThongTinXuatHang() {
        // 1. Lấy dữ liệu từ các trường nhập liệu
        String maXuatHang = txtMaXuatHang.getText().trim();
        String maKhachHang = txtMaKhachHang.getText().trim();
        String maNhanVien = txtMaNhanVien.getText().trim();
        String ngayXuatStr = txtNgayXuat.getText().trim();
        String ghiChuHoaDon = txtGhiChu.getText().trim();

        // 2. Kiểm tra dữ liệu đầu vào cơ bản
        if (maXuatHang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã xuất hàng không được để trống.", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (maKhachHang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng không được để trống.", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (maNhanVien.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không được để trống.", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (ngayXuatStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ngày xuất không được để trống.", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra định dạng ngày
        java.sql.Date ngayXuatSql = null;
        try {
            Date parsedDate = sdf.parse(ngayXuatStr);
            ngayXuatSql = new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Ngày xuất không đúng định dạng (dd/MM/yyyy).", "Lỗi định dạng", JOptionPane.WARNING_MESSAGE);
            logger.log(Level.WARNING, "Lỗi định dạng ngày xuất: " + ngayXuatStr, e);
            return;
        }

        Connection conn = null; // Khai báo Connection ở ngoài try để có thể sử dụng trong finally
        try {
            // 3. Mở kết nối Database
            conn = DBConnection.openConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu. Vui lòng kiểm tra cài đặt kết nối.", "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.SEVERE, "Không thể mở kết nối DB trong luuThongTinXuatHang.");
                return;
            }

            // Bắt đầu Transaction để đảm bảo tính toàn vẹn dữ liệu
            conn.setAutoCommit(false);

            // 4. Cập nhật thông tin phiếu xuất chính (Bảng XuatHang)
            // Lấy thông tin về phiếu xuất từ các trường JTextField và cập nhật vào bảng XuatHang
            // Nếu bạn có một XuatHangDAO riêng, hãy sử dụng nó. Nếu không, viết SQL trực tiếp.

            String sqlUpdateXuatHang = "UPDATE XuatHang SET MaKhachHang = ?, MaNhanVien = ?, NgayXuat = ?, GhiChu = ? WHERE MaXuatHang = ?";
            try (PreparedStatement psXuatHang = conn.prepareStatement(sqlUpdateXuatHang)) {
                psXuatHang.setString(1, maKhachHang);
                psXuatHang.setString(2, maNhanVien);
                psXuatHang.setDate(3, ngayXuatSql);
                psXuatHang.setString(4, ghiChuHoaDon);
                psXuatHang.setString(5, maXuatHang);

                int rowsAffectedXuatHang = psXuatHang.executeUpdate();
                if (rowsAffectedXuatHang == 0) {
                    // Nếu không có hàng nào được cập nhật, có thể mã xuất hàng không tồn tại
                    JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu xuất có mã '" + maXuatHang + "' để cập nhật.", "Lỗi cập nhật", JOptionPane.WARNING_MESSAGE);
                    conn.rollback(); // Rollback toàn bộ transaction
                    return;
                }
                logger.log(Level.INFO, "Cập nhật XuatHang.RowsAffected: " + rowsAffectedXuatHang);
            }

            // 5. Cập nhật hoặc thêm mới chi tiết xuất hàng (Bảng ChiTietXuatHang)
            // Bạn cần xóa tất cả chi tiết cũ của phiếu xuất này và thêm lại toàn bộ từ JTable
            // Hoặc, nếu chỉ cập nhật 'GhiChu' như code cũ của bạn, thì giữ nguyên cách đó.
            // Phương án an toàn hơn: Xóa tất cả chi tiết cũ và insert lại các dòng mới từ JTable.

            // Cách 1: Chỉ cập nhật GhiChu (giữ nguyên logic bạn đang có)
            ChiTietXuatHangDAO chiTietXuatHangDAO = new ChiTietXuatHangDAO(conn); // Truyền connection vào DAO

            DefaultTableModel model = (DefaultTableModel) dgvXuatHang.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String maBinhGas = (String) model.getValueAt(i, 0);
                // Giả sử các cột khác như SoLuong, DonGia cũng có thể thay đổi và bạn muốn lưu
                // Bạn cần kiểm tra null cho các giá trị này nếu người dùng có thể để trống
                int soLuong = (Integer) model.getValueAt(i, 2); // Cột số lượng
                int donGia = (Integer) model.getValueAt(i, 3); // Cột đơn giá
                String ghiChuChiTiet = (String) model.getValueAt(i, 5); // Cột ghi chú

                if (maBinhGas == null || maBinhGas.trim().isEmpty()) {
                    logger.log(Level.WARNING, "Bỏ qua hàng chi tiết xuất không có Mã Bình Gas ở hàng: " + i);
                    continue; // Bỏ qua hàng không có mã bình gas
                }

                // Cập nhật từng chi tiết
                // Đảm bảo updateChiTietXuatHang() của bạn có thể xử lý việc này
                // Ví dụ: updateChiTietXuatHang(maXuatHang, maBinhGas, soLuong, donGia, ghiChuChiTiet, conn);
                // Hoặc updateGhiChu như bạn đã có
                boolean updated = chiTietXuatHangDAO.updateGhiChu(maXuatHang, maBinhGas, ghiChuChiTiet, conn);
                if (!updated) {
                    logger.log(Level.WARNING, "Không thể cập nhật ghi chú cho chi tiết xuất: MaXuatHang=" + maXuatHang + ", MaBinhGas=" + maBinhGas);
                    // Tùy thuộc vào yêu cầu: có thể rollback hoặc chỉ cảnh báo
                }
            }
            
            // 6. Hoàn tất giao dịch
            conn.commit(); // Commit tất cả các thay đổi vào database
            JOptionPane.showMessageDialog(this, "Lưu thông tin xuất hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            // 7. Cập nhật lại JTable sau khi lưu thành công
            loadChiTietXuatHang();

        } catch (SQLException ex) {
            // 8. Xử lý lỗi SQL và Rollback
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback tất cả các thay đổi nếu có lỗi
                    logger.log(Level.WARNING, "Rollback transaction do lỗi SQL.");
                }
            } catch (SQLException rbEx) {
                logger.log(Level.SEVERE, "Lỗi khi rollback transaction: " + rbEx.getMessage(), rbEx);
            }
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu thông tin xuất hàng vào cơ sở dữ liệu: " + ex.getMessage(), "Lỗi Database", JOptionPane.ERROR_MESSAGE);
            logger.log(Level.SEVERE, "Lỗi SQL khi lưu chi tiết xuất hàng", ex);

        } catch (Exception ex) {
            // 9. Xử lý các lỗi khác không phải SQL
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback cả với các lỗi không phải SQL nếu đang trong transaction
                    logger.log(Level.WARNING, "Rollback transaction do lỗi khác.");
                }
            } catch (SQLException rbEx) {
                logger.log(Level.SEVERE, "Lỗi khi rollback transaction (non-SQL error): " + rbEx.getMessage(), rbEx);
            }
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi không mong muốn khi lưu dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            logger.log(Level.SEVERE, "Lỗi không mong muốn khi lưu chi tiết xuất hàng", ex);

        } finally {
            // 10. Đảm bảo đóng kết nối và đặt lại autoCommit
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // Đặt lại autoCommit về true cho các thao tác tiếp theo
                    conn.close(); // Đóng kết nối để giải phóng tài nguyên
                }
            } catch (SQLException closeEx) {
                logger.log(Level.SEVERE, "Lỗi khi đóng kết nối hoặc đặt lại autoCommit: " + closeEx.getMessage(), closeEx);
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

        jLabel5 = new javax.swing.JLabel();
        txtMaXuatHang = new javax.swing.JTextField();
        txtNgayXuat = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgvXuatHang = new javax.swing.JTable();
        txtMaKhachHang = new javax.swing.JTextField();
        txtMaNhanVien = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnLuu = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Ghi chú");

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

        txtMaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKhachHangActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Mã xuất hàng");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Ngày xuất");

        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Mã khách hàng");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Mã nhân viên");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLuu))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaXuatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMaXuatHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNgayXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKhachHangActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        luuThongTinXuatHang();
    }//GEN-LAST:event_btnLuuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Test Frame");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setLocationRelativeTo(null); // Đặt frame ở giữa màn hình
                // frame.setVisible(true); // Có thể ẩn frame này nếu bạn chỉ muốn test dialog

                // Tạo đối tượng dlgChiTietXuatHang
                dlgChiTietXuatHang dialog = new dlgChiTietXuatHang(frame, true); // true cho modal

                // --- Thiết lập mã xuất hàng để test ---
                // Bạn cần cung cấp một mã xuất hàng có sẵn trong database của bạn để dialog có thể load dữ liệu
                String testMaXuatHang = "XH001"; // Thay thế bằng một mã xuất hàng thực tế trong DB của bạn

                // Gọi phương thức setMaXuatHang để dialog tải dữ liệu
                dialog.setMaXuatHang(testMaXuatHang);

                // Hiển thị dialog
                dialog.setVisible(true);

                // Khi dialog đóng, bạn có thể thực hiện một số hành động ở đây nếu cần
                System.out.println("Dialog đã đóng.");
                // frame.dispose(); // Nếu bạn không muốn giữ lại frame test
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLuu;
    private javax.swing.JTable dgvXuatHang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtMaKhachHang;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtMaXuatHang;
    private javax.swing.JTextField txtNgayXuat;
    // End of variables declaration//GEN-END:variables
}
