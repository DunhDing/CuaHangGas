package com.tuandat.cuahanggas.ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class frmMain extends javax.swing.JFrame {

    public frmMain() {
        initComponents();
//        setExtendedState(MAXIMIZED_BOTH);
//        setLocationRelativeTo(null);

        setTitle("Main Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        pnlMain.setLayout(new BorderLayout()); // Đảm bảo bố cục

        // Gọi JPanel Form vào pnlMain
        ucHangHoa childPanel = new ucHangHoa();
        pnlMain.add(childPanel, BorderLayout.CENTER);

        revalidate();
        repaint(); // Cập nhật giao diện

        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        pnlHeaderContent = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        pnlNavigation = new javax.swing.JPanel();
        pnlMain = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlHeader.setPreferredSize(new java.awt.Dimension(983, 120));
        pnlHeader.setLayout(new javax.swing.BoxLayout(pnlHeader, javax.swing.BoxLayout.Y_AXIS));

        pnlHeaderContent.setBackground(new java.awt.Color(255, 255, 255));
        pnlHeaderContent.setMinimumSize(new java.awt.Dimension(100, 70));
        pnlHeaderContent.setPreferredSize(new java.awt.Dimension(995, 70));

        lblLogo.setText("logo");

        pnlNavigation.setBackground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout pnlNavigationLayout = new javax.swing.GroupLayout(pnlNavigation);
        pnlNavigation.setLayout(pnlNavigationLayout);
        pnlNavigationLayout.setHorizontalGroup(
            pnlNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlNavigationLayout.setVerticalGroup(
            pnlNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 58, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlHeaderContentLayout = new javax.swing.GroupLayout(pnlHeaderContent);
        pnlHeaderContent.setLayout(pnlHeaderContentLayout);
        pnlHeaderContentLayout.setHorizontalGroup(
            pnlHeaderContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderContentLayout.createSequentialGroup()
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(952, Short.MAX_VALUE))
            .addComponent(pnlNavigation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlHeaderContentLayout.setVerticalGroup(
            pnlHeaderContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderContentLayout.createSequentialGroup()
                .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNavigation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlNavigation.getAccessibleContext().setAccessibleParent(pnlHeader);

        pnlHeader.add(pnlHeaderContent);

        getContentPane().add(pnlHeader, java.awt.BorderLayout.NORTH);

        pnlMain.setBackground(new java.awt.Color(255, 51, 51));
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmChiTietHangHoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmChiTietHangHoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmChiTietHangHoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmChiTietHangHoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new frmMain().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblLogo;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlHeaderContent;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlNavigation;
    // End of variables declaration//GEN-END:variables
}
