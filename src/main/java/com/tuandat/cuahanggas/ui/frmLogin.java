package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.impl.TaiKhoanNguoiDungDAO;
import com.tuandat.cuahanggas.model.TaiKhoanNguoiDung;
import com.tuandat.cuahanggas.utils.DBConnection;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class frmLogin extends javax.swing.JDialog {

    private boolean loginSuccess = false;
    private TaiKhoanNguoiDungDAO taiKhoanDAO;

    public frmLogin(java.awt.Frame parent, boolean modal, TaiKhoanNguoiDungDAO taiKhoanNguoiDungDAO) {
        super(parent, modal);
        this.taiKhoanDAO = taiKhoanNguoiDungDAO;
        setModalityType(ModalityType.APPLICATION_MODAL);
        initComponents();
        setLocationRelativeTo(parent);
        
        getContentPane().setBackground(Color.WHITE);
                
        ImageIcon iconLogo = new ImageIcon(getClass().getResource("/logo.png"));
        Image imgLogo = iconLogo.getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(imgLogo));
        
        ImageIcon iconTenDangNhap = new ImageIcon(getClass().getResource("/tendangnhap.png"));
        Image imgTenDangNhap = iconTenDangNhap.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        lblTenDangNhap.setIcon(new ImageIcon(imgTenDangNhap));

        ImageIcon iconMatKhau = new ImageIcon(getClass().getResource("/matkhau.png"));
        Image imgMatKhau = iconMatKhau.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        lblMatKhau.setIcon(new ImageIcon(imgMatKhau));
        
        setLocationRelativeTo(parent);
    }

    public frmLogin() {
        this(null, true, null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLogo = new javax.swing.JLabel();
        lblTenCuaHang = new javax.swing.JLabel();
        txtTenDangNhap = new javax.swing.JTextField();
        btnThoat = new javax.swing.JButton();
        btnDangNhap = new javax.swing.JButton();
        txtMatKhau = new javax.swing.JPasswordField();
        lblTenDangNhap = new javax.swing.JLabel();
        lblMatKhau = new javax.swing.JLabel();

        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(650, 400));
        setSize(new java.awt.Dimension(650, 400));

        lblLogo.setText("logo");

        lblTenCuaHang.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTenCuaHang.setForeground(new java.awt.Color(29, 43, 100));
        lblTenCuaHang.setText("CỬA HÀNG GAS TUẤN ĐẠT");

        txtTenDangNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenDangNhap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTenDangNhapKeyPressed(evt);
            }
        });

        btnThoat.setBackground(new java.awt.Color(237, 28, 36));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThoat.setForeground(new java.awt.Color(255, 255, 255));
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        btnDangNhap.setBackground(new java.awt.Color(0, 102, 204));
        btnDangNhap.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDangNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnDangNhap.setText("Đăng nhập");
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhapActionPerformed(evt);
            }
        });

        txtMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMatKhau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMatKhauKeyPressed(evt);
            }
        });

        lblTenDangNhap.setBackground(new java.awt.Color(255, 255, 255));

        lblMatKhau.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(161, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(144, 144, 144))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTenDangNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                    .addComponent(txtMatKhau))
                                .addGap(63, 63, 63))
                            .addComponent(lblTenCuaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88)
                        .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(138, 138, 138))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenCuaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//    private boolean checkLogin(String username, String password) {
//        Connection conn = null;
//        PreparedStatement pst = null;
//        ResultSet rs = null;
//
//        try {
//            conn = DBConnection.openConnection();
//
//            String sql = "SELECT * FROM TaiKhoanNguoiDung WHERE TenDangNhap = ? AND MatKhau = ?";
//            pst = conn.prepareStatement(sql);
//            pst.setString(1, username);
//            pst.setString(2, password);
//
//            rs = pst.executeQuery();
//
//            return rs.next();
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Lỗi khi kết nối: " + e.getMessage());
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pst != null) {
//                    pst.close();
//                }
//                DBConnection.closeConnection();
//            } catch (SQLException e) {
//                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
//            }
//        }
//        return false;
//    }
//
    
    
    private boolean checkLogin(String username, String password, List<TaiKhoanNguoiDung> allAccounts) {
    return allAccounts.stream()
            .anyMatch(acc -> acc.getTenDangNhap().equals(username) && acc.getMatKhau().equals(password));
}

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhapActionPerformed
        String username = txtTenDangNhap.getText().trim();
    String password = new String(txtMatKhau.getPassword()).trim();

    if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập và mật khẩu!");
        return;
    }

    List<TaiKhoanNguoiDung> allAccounts = taiKhoanDAO.getAll();
    if (checkLogin(username, password, allAccounts)) {
        loginSuccess = true;
        dispose();
    } else {
        JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng!");
    }
    }//GEN-LAST:event_btnDangNhapActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn thoát không?",
                "Xác nhận thoát",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);    }//GEN-LAST:event_btnThoatActionPerformed
    }
    private void txtTenDangNhapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenDangNhapKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnDangNhap.doClick();
        }
    }//GEN-LAST:event_txtTenDangNhapKeyPressed

    private void txtMatKhauKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMatKhauKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnDangNhap.doClick();
        }
    }//GEN-LAST:event_txtMatKhauKeyPressed

    public boolean isLoginSuccess() {
        return loginSuccess;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JLabel lblTenCuaHang;
    private javax.swing.JLabel lblTenDangNhap;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtTenDangNhap;
    // End of variables declaration//GEN-END:variables
}
