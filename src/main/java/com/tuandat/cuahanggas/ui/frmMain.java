package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.impl.BinhGasDAO;
import com.tuandat.cuahanggas.dao.impl.KhachHangDAO;
import com.tuandat.cuahanggas.dao.impl.NhanVienDAO;
import com.tuandat.cuahanggas.dao.impl.TaiKhoanNguoiDungDAO;
import com.tuandat.cuahanggas.model.TaiKhoanNguoiDung;
import com.tuandat.cuahanggas.utils.DBConnection;
import com.tuandat.cuahanggas.utils.Session;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class frmMain extends javax.swing.JFrame {

    private BinhGasDAO binhGasDAO;
    private KhachHangDAO khachHangDAO;
    private TaiKhoanNguoiDungDAO taiKhoanDAO;
    private NhanVienDAO nhanVienDAO;
    private boolean isLogout = false;

    public frmMain(BinhGasDAO binhGasDAO, TaiKhoanNguoiDungDAO taiKhoanDAO, NhanVienDAO nv, KhachHangDAO kh) {
        initComponents();
        this.taiKhoanDAO = taiKhoanDAO;
        this.binhGasDAO = binhGasDAO;
        this.nhanVienDAO = nv;
        this.khachHangDAO = kh;

        ImageIcon iconLogo = new ImageIcon(getClass().getResource("/logo.png"));
        Image imgLogo = iconLogo.getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(imgLogo));

        ImageIcon iconTongQuan = new ImageIcon(getClass().getResource("/tongquan.png"));
        Image imgTongQuan = iconTongQuan.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btnTongQuan.setIcon(new ImageIcon(imgTongQuan));

        ImageIcon iconHangHoa = new ImageIcon(getClass().getResource("/hanghoa.png"));
        Image imgHangHoa = iconHangHoa.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btnHangHoa.setIcon(new ImageIcon(imgHangHoa));

        ImageIcon iconGiaoDich = new ImageIcon(getClass().getResource("/giaodich.png"));
        Image imgGiaoDich = iconGiaoDich.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btnGiaoDich.setIcon(new ImageIcon(imgGiaoDich));

        ImageIcon iconDoiTac = new ImageIcon(getClass().getResource("/doitac.png"));
        Image imgDoiTac = iconDoiTac.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btnDoiTac.setIcon(new ImageIcon(imgDoiTac));

        ImageIcon iconNhanVien = new ImageIcon(getClass().getResource("/nhanvien.png"));
        Image imgNhanVien = iconNhanVien.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btnNhanVien.setIcon(new ImageIcon(imgNhanVien));

        ImageIcon iconBaoCao = new ImageIcon(getClass().getResource("/baocao.png"));
        Image imgBaoCao = iconBaoCao.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btnBaoCao.setIcon(new ImageIcon(imgBaoCao));

        ImageIcon iconTaiKhoan = new ImageIcon(getClass().getResource("/taikhoan.png"));
        Image imgTaiKhoan = iconTaiKhoan.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btnTaiKhoan.setIcon(new ImageIcon(imgTaiKhoan));

        btnTaiKhoan.setBackground(Color.WHITE);
        btnTaiKhoan.setForeground(Color.BLACK);
        btnTaiKhoan.setOpaque(true);
        btnTaiKhoan.setContentAreaFilled(true);

        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    public boolean isLogout() {
        return isLogout;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popMenuTaiKhoan = new javax.swing.JPopupMenu();
        menuQuanLyTaiKhoan = new javax.swing.JMenuItem();
        menuTaiKhoan = new javax.swing.JMenuItem();
        menuDangXuat = new javax.swing.JMenuItem();
        popMenuGiaoDich = new javax.swing.JPopupMenu();
        menuXuatHang = new javax.swing.JMenuItem();
        menuNhapHang = new javax.swing.JMenuItem();
        popMenuDoiTac = new javax.swing.JPopupMenu();
        menuKhachHang = new javax.swing.JMenuItem();
        menuNhaCungCap = new javax.swing.JMenuItem();
        pnlHeader = new javax.swing.JPanel();
        pnlHeaderContent = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblTenCuaHang = new javax.swing.JLabel();
        btnTaiKhoan = new javax.swing.JButton();
        pnlNavigation = new javax.swing.JPanel();
        btnHangHoa = new javax.swing.JButton();
        btnTongQuan = new javax.swing.JButton();
        btnGiaoDich = new javax.swing.JButton();
        btnDoiTac = new javax.swing.JButton();
        btnNhanVien = new javax.swing.JButton();
        btnBaoCao = new javax.swing.JButton();
        pnlMain = new javax.swing.JPanel();

        menuQuanLyTaiKhoan.setText("Quản lý tài khoản ");
        menuQuanLyTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuQuanLyTaiKhoanActionPerformed(evt);
            }
        });
        popMenuTaiKhoan.add(menuQuanLyTaiKhoan);

        menuTaiKhoan.setText("Tài khoản");
        menuTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTaiKhoanActionPerformed(evt);
            }
        });
        popMenuTaiKhoan.add(menuTaiKhoan);

        menuDangXuat.setText("Đăng xuất");
        menuDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDangXuatActionPerformed(evt);
            }
        });
        popMenuTaiKhoan.add(menuDangXuat);

        menuXuatHang.setText("Xuất hàng");
        popMenuGiaoDich.add(menuXuatHang);

        menuNhapHang.setText("Nhập hàng");
        popMenuGiaoDich.add(menuNhapHang);

        menuKhachHang.setText("Khách hàng");
        popMenuDoiTac.add(menuKhachHang);

        menuNhaCungCap.setText("Nhà cung cấp");
        popMenuDoiTac.add(menuNhaCungCap);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlHeader.setPreferredSize(new java.awt.Dimension(983, 120));
        pnlHeader.setLayout(new javax.swing.BoxLayout(pnlHeader, javax.swing.BoxLayout.Y_AXIS));

        pnlHeaderContent.setBackground(new java.awt.Color(255, 255, 255));
        pnlHeaderContent.setMinimumSize(new java.awt.Dimension(100, 70));
        pnlHeaderContent.setPreferredSize(new java.awt.Dimension(995, 70));

        lblLogo.setText("logo");

        lblTenCuaHang.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTenCuaHang.setForeground(new java.awt.Color(29, 43, 100));
        lblTenCuaHang.setText("CỬA HÀNG GAS TUẤN ĐẠT");

        btnTaiKhoan.setBorderPainted(false);
        btnTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiKhoanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHeaderContentLayout = new javax.swing.GroupLayout(pnlHeaderContent);
        pnlHeaderContent.setLayout(pnlHeaderContentLayout);
        pnlHeaderContentLayout.setHorizontalGroup(
            pnlHeaderContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderContentLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblTenCuaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 921, Short.MAX_VALUE)
                .addComponent(btnTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        pnlHeaderContentLayout.setVerticalGroup(
            pnlHeaderContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderContentLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderContentLayout.createSequentialGroup()
                .addComponent(lblTenCuaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlHeaderContentLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlHeader.add(pnlHeaderContent);

        pnlNavigation.setBackground(new java.awt.Color(0, 102, 204));

        btnHangHoa.setBackground(new java.awt.Color(0, 102, 204));
        btnHangHoa.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnHangHoa.setForeground(new java.awt.Color(255, 255, 255));
        btnHangHoa.setText("Hàng hóa");
        btnHangHoa.setBorderPainted(false);

        btnTongQuan.setBackground(new java.awt.Color(0, 102, 204));
        btnTongQuan.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnTongQuan.setForeground(new java.awt.Color(255, 255, 255));
        btnTongQuan.setText("Tổng quan");
        btnTongQuan.setAlignmentY(0.0F);
        btnTongQuan.setBorderPainted(false);

        btnGiaoDich.setBackground(new java.awt.Color(0, 102, 204));
        btnGiaoDich.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnGiaoDich.setForeground(new java.awt.Color(255, 255, 255));
        btnGiaoDich.setText("Giao dịch");
        btnGiaoDich.setBorderPainted(false);
        btnGiaoDich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGiaoDichActionPerformed(evt);
            }
        });

        btnDoiTac.setBackground(new java.awt.Color(0, 102, 204));
        btnDoiTac.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnDoiTac.setForeground(new java.awt.Color(255, 255, 255));
        btnDoiTac.setText("Đối tác");
        btnDoiTac.setBorderPainted(false);
        btnDoiTac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiTacActionPerformed(evt);
            }
        });

        btnNhanVien.setBackground(new java.awt.Color(0, 102, 204));
        btnNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        btnNhanVien.setText("Nhân viên");
        btnNhanVien.setBorderPainted(false);

        btnBaoCao.setBackground(new java.awt.Color(0, 102, 204));
        btnBaoCao.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnBaoCao.setForeground(new java.awt.Color(255, 255, 255));
        btnBaoCao.setText("Báo cáo");
        btnBaoCao.setBorderPainted(false);

        javax.swing.GroupLayout pnlNavigationLayout = new javax.swing.GroupLayout(pnlNavigation);
        pnlNavigation.setLayout(pnlNavigationLayout);
        pnlNavigationLayout.setHorizontalGroup(
            pnlNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavigationLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnTongQuan, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGiaoDich, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDoiTac, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(549, Short.MAX_VALUE))
        );
        pnlNavigationLayout.setVerticalGroup(
            pnlNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnHangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnTongQuan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnGiaoDich, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnDoiTac, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlHeader.add(pnlNavigation);
        pnlNavigation.getAccessibleContext().setAccessibleParent(pnlHeader);

        getContentPane().add(pnlHeader, java.awt.BorderLayout.NORTH);

        pnlMain.setBackground(java.awt.SystemColor.control);
        pnlMain.setName("pnlMain"); // NOI18N
        pnlMain.setLayout(new java.awt.BorderLayout());
        getContentPane().add(pnlMain, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGiaoDichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGiaoDichActionPerformed
        popMenuGiaoDich.show(btnGiaoDich, 0, btnGiaoDich.getHeight());
    }//GEN-LAST:event_btnGiaoDichActionPerformed

    private void btnDoiTacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiTacActionPerformed
        popMenuDoiTac.show(btnDoiTac, 0, btnDoiTac.getHeight());
    }//GEN-LAST:event_btnDoiTacActionPerformed

    private void btnTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiKhoanActionPerformed
        popMenuTaiKhoan.show(btnTaiKhoan, 0, btnTaiKhoan.getHeight());
    }//GEN-LAST:event_btnTaiKhoanActionPerformed

    private void menuDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDangXuatActionPerformed
        isLogout = true;
        this.dispose();

    }//GEN-LAST:event_menuDangXuatActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn thoát không?",
                "Xác nhận thoát",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void menuQuanLyTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuQuanLyTaiKhoanActionPerformed
        ucQuanLyTaiKhoan panelTaiKhoan = new ucQuanLyTaiKhoan(taiKhoanDAO, nhanVienDAO);
        showPanel(panelTaiKhoan);
        panelTaiKhoan.loadData();
        panelTaiKhoan.setVisible(true);
    }//GEN-LAST:event_menuQuanLyTaiKhoanActionPerformed

    private void menuTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTaiKhoanActionPerformed
        TaiKhoanNguoiDung currentUser = Session.getCurrentUser();

        // Kiểm tra nếu người dùng chưa đăng nhập
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập trước!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mở dialog chi tiết tài khoản với thông tin người dùng hiện tại
        dlgChiTietTaiKhoan dialog = new dlgChiTietTaiKhoan(this, currentUser, true, taiKhoanDAO, nhanVienDAO);
        dialog.loadData();
        dialog.setLocationRelativeTo(null);  // Đặt vị trí dialog ở trung tâm
        dialog.setVisible(true);
    }//GEN-LAST:event_menuTaiKhoanActionPerformed

    private void showPanel(JPanel panel) {
        pnlMain.removeAll();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.add(panel, BorderLayout.CENTER);
        pnlMain.revalidate();
        pnlMain.repaint();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBaoCao;
    private javax.swing.JButton btnDoiTac;
    private javax.swing.JButton btnGiaoDich;
    private javax.swing.JButton btnHangHoa;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnTaiKhoan;
    private javax.swing.JButton btnTongQuan;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblTenCuaHang;
    private javax.swing.JMenuItem menuDangXuat;
    private javax.swing.JMenuItem menuKhachHang;
    private javax.swing.JMenuItem menuNhaCungCap;
    private javax.swing.JMenuItem menuNhapHang;
    private javax.swing.JMenuItem menuQuanLyTaiKhoan;
    private javax.swing.JMenuItem menuTaiKhoan;
    private javax.swing.JMenuItem menuXuatHang;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlHeaderContent;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlNavigation;
    private javax.swing.JPopupMenu popMenuDoiTac;
    private javax.swing.JPopupMenu popMenuGiaoDich;
    private javax.swing.JPopupMenu popMenuTaiKhoan;
    // End of variables declaration//GEN-END:variables

}
