package com.tuandat.cuahanggas.ui;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class frmMain extends javax.swing.JFrame {

    public frmMain() {
         initComponents();
        URL imageUrl = getClass().getResource("/Logo.png");
if (imageUrl != null) {
    ImageIcon originalIcon = new ImageIcon(imageUrl);
    
    // Lấy kích thước của lblLogo
    int labelWidth = lblLogo.getWidth();
    int labelHeight = lblLogo.getHeight();

    // Nếu kích thước chưa có (label chưa hiển thị), dùng kích thước mặc định
    if (labelWidth == 0 || labelHeight == 0) {
        labelWidth = 52;  // kích thước theo GroupLayout bạn đã đặt
        labelHeight = 50;
    }

    // Scale ảnh
    Image scaledImage = originalIcon.getImage().getScaledInstance(
        labelWidth,
        labelHeight,
        Image.SCALE_SMOOTH
    );

    ImageIcon scaledIcon = new ImageIcon(scaledImage);
    lblLogo.setIcon(scaledIcon);
    lblLogo.setText("");
} else {
    System.err.println("❌ Không tìm thấy ảnh logo!");
    }
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        pnlHeaderContent = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblTenCuaHang = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        pnlNavigation = new javax.swing.JPanel();
        pnlMain = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlHeader.setPreferredSize(new java.awt.Dimension(983, 120));
        pnlHeader.setLayout(new javax.swing.BoxLayout(pnlHeader, javax.swing.BoxLayout.Y_AXIS));

        pnlHeaderContent.setBackground(new java.awt.Color(255, 255, 255));
        pnlHeaderContent.setMinimumSize(new java.awt.Dimension(100, 70));
        pnlHeaderContent.setPreferredSize(new java.awt.Dimension(995, 70));

        lblLogo.setText("logo");

        lblTenCuaHang.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTenCuaHang.setForeground(new java.awt.Color(29, 43, 100));
        lblTenCuaHang.setText("CỬA HÀNG GAS TUẤN ĐẠT");

        jButton1.setText("jButton1");

        javax.swing.GroupLayout pnlHeaderContentLayout = new javax.swing.GroupLayout(pnlHeaderContent);
        pnlHeaderContent.setLayout(pnlHeaderContentLayout);
        pnlHeaderContentLayout.setHorizontalGroup(
            pnlHeaderContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderContentLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTenCuaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 488, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(29, 29, 29))
        );
        pnlHeaderContentLayout.setVerticalGroup(
            pnlHeaderContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                .addComponent(jButton1))
            .addComponent(lblTenCuaHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlHeader.add(pnlHeaderContent);

        pnlNavigation.setBackground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout pnlNavigationLayout = new javax.swing.GroupLayout(pnlNavigation);
        pnlNavigation.setLayout(pnlNavigationLayout);
        pnlNavigationLayout.setHorizontalGroup(
            pnlNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1032, Short.MAX_VALUE)
        );
        pnlNavigationLayout.setVerticalGroup(
            pnlNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        pnlHeader.add(pnlNavigation);
        pnlNavigation.getAccessibleContext().setAccessibleParent(pnlHeader);

        getContentPane().add(pnlHeader, java.awt.BorderLayout.NORTH);

        pnlMain.setBackground(new java.awt.Color(204, 204, 204));
        pnlMain.setName("pnlMain"); // NOI18N

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1032, Short.MAX_VALUE)
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        getContentPane().add(pnlMain, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblTenCuaHang;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlHeaderContent;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlNavigation;
    // End of variables declaration//GEN-END:variables
}
