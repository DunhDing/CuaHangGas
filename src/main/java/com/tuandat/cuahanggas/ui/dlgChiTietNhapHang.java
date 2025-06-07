/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.impl.ChiTietNhapHangDAO;
import com.tuandat.cuahanggas.model.BinhGas;
import com.tuandat.cuahanggas.model.ChiTietNhapHang;
import com.tuandat.cuahanggas.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author duck
 */
public class dlgChiTietNhapHang extends javax.swing.JDialog {

      private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(dlgChiTietNhapHang.class.getName());
    private String maNhapHangHienTai;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Creates new form dlgChiTietNhapHang
     */
    public dlgChiTietNhapHang(java.awt.Frame parent, boolean modal) {
        initComponents();
        resetForm();
    }

   private void resetForm() {
        txtMaNhapHang.setText("");
        txtMaNhaCungCap.setText("");
        txtMaNhanVien.setText("");
        txtNgayNhap.setText("");
        txtGhiChu.setText("");
        dgvNhapHang.setModel(new DefaultTableModel());
    }
   private void loadChiTietNhapHang() {
    if (maNhapHangHienTai == null || maNhapHangHienTai.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Mã nhập hàng chưa được thiết lập!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    Connection conn = DBConnection.openConnection();
    if (conn == null) {
        JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    PreparedStatement ps1 = null;
    ResultSet rs1 = null;
    PreparedStatement ps2 = null;
    ResultSet rs2 = null;

    try {
        // 1. Truy vấn thông tin phiếu nhập
        String sqlNhapHang = "SELECT NgayNhap, MaNhanVien, MaNhaCungCap, GhiChu FROM NhapHang WHERE MaNhapHang = ?";
        ps1 = conn.prepareStatement(sqlNhapHang);
        ps1.setString(1, maNhapHangHienTai);
        rs1 = ps1.executeQuery();

        if (rs1.next()) {
            txtMaNhapHang.setText(maNhapHangHienTai);
            txtNgayNhap.setText(sdf.format(rs1.getDate("NgayNhap")));
            txtMaNhanVien.setText(rs1.getString("MaNhanVien"));
            txtMaNhaCungCap.setText(rs1.getString("MaNhaCungCap"));
            txtGhiChu.setText(rs1.getString("GhiChu"));
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Truy vấn chi tiết nhập hàng
        String sqlChiTiet = """
            SELECT ct.MaBinhGas, bg.TenBinhGas, ct.SoLuongNhap, ct.DonGiaNhap,
                   (ct.SoLuongNhap * ct.DonGiaNhap) AS ThanhTien
            FROM ChiTietNhapHang ct
            JOIN BinhGas bg ON ct.MaBinhGas = bg.MaBinhGas
            WHERE ct.MaNhapHang = ?
        """;
        ps2 = conn.prepareStatement(sqlChiTiet);
        ps2.setString(1, maNhapHangHienTai);
        rs2 = ps2.executeQuery();

        Vector<String> columnNames = new Vector<>(Arrays.asList(
            "Mã Bình Gas", "Tên Bình Gas", "Số Lượng Nhập", "Đơn Giá Nhập", "Thành Tiền"
        ));
        Vector<Vector<Object>> data = new Vector<>();

        while (rs2.next()) {
            Vector<Object> row = new Vector<>();
            row.add(rs2.getString("MaBinhGas"));
            row.add(rs2.getString("TenBinhGas"));
            row.add(rs2.getInt("SoLuongNhap"));
            row.add(rs2.getInt("DonGiaNhap"));
            row.add(rs2.getInt("ThanhTien"));
            data.add(row);
        }

        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có chi tiết nhập hàng nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // không cho sửa dữ liệu trong bảng
            }
        };

        dgvNhapHang.setModel(model);

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Lỗi khi truy vấn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (rs1 != null) rs1.close();
            if (ps1 != null) ps1.close();
            if (rs2 != null) rs2.close();
            if (ps2 != null) ps2.close();
        } catch (SQLException e) {
            // Ghi log hoặc bỏ qua nếu cần
        }
    }
}
 private void luuThongTinNhapHang() {
        String maNV = txtMaNhanVien.getText().trim();
        String maNCC = txtMaNhaCungCap.getText().trim();
        String ngay = txtNgayNhap.getText().trim();
        String ghiChu = txtGhiChu.getText().trim();

        if (maNV.isEmpty() || maNCC.isEmpty() || ngay.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ các trường bắt buộc.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            java.util.Date utilDate = new SimpleDateFormat("dd/MM/yyyy").parse(ngay);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            Connection conn = DBConnection.openConnection();
            String update = "UPDATE NhapHang SET NgayNhap=?, MaNhanVien=?, MaNhaCungCap=?, GhiChu=? WHERE MaNhapHang=?";
            PreparedStatement ps = conn.prepareStatement(update);
            ps.setDate(1, sqlDate);
            ps.setString(2, maNV);
            ps.setString(3, maNCC);
            ps.setString(4, ghiChu);
            ps.setString(5, maNhapHangHienTai);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadChiTietNhapHang();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu nhập để cập nhật.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Sai định dạng ngày. Dùng dd/MM/yyyy", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtMaNhaCungCap = new javax.swing.JTextField();
        txtMaNhanVien = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnLuu = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMaNhapHang = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgvNhapHang = new javax.swing.JTable();
        txtNgayNhap = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtMaNhaCungCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNhaCungCapActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Mã nhập hàng");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Ngày nhập");

        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Mã nhà cung cấp");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Mã nhân viên");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Ghi chú");

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
                            .addComponent(txtMaNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(txtMaNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtMaNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void txtMaNhaCungCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNhaCungCapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNhaCungCapActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
 luuThongTinNhapHang();        // TODO add your handling code here:
    }//GEN-LAST:event_btnLuuActionPerformed
// public static void main(String args[]) {
//       try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(dlgChiTietNhapHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(dlgChiTietNhapHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(dlgChiTietNhapHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(dlgChiTietNhapHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(() -> {
//            // SỬA DÒNG NÀY ĐỂ TRUYỀN THAM SỐ CHO CONSTRUCTOR
//            dlgChiTietNhapHang form = new dlgChiTietNhapHang(null, false); // <--- Đã sửa ở đây
//            form.setMaNhapHang("NH001"); // truyền mã nhập hàng cần hiển thị
//            form.setVisible(true);
//        });
//    }

    void setMaNhapHang(String maNhapHang) {
    this.maNhapHangHienTai = maNhapHang;

    if (maNhapHang == null || maNhapHang.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Mã nhập hàng chưa được thiết lập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    loadChiTietNhapHang();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLuu;
    private javax.swing.JTable dgvNhapHang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtMaNhaCungCap;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtMaNhapHang;
    private javax.swing.JTextField txtNgayNhap;
    // End of variables declaration//GEN-END:variables

}
