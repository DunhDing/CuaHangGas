package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.impl.NhanVienDAO;
import com.tuandat.cuahanggas.dao.impl.TaiKhoanNguoiDungDAO;
import com.tuandat.cuahanggas.model.NhanVien;
import com.tuandat.cuahanggas.model.TaiKhoanNguoiDung;
import com.tuandat.cuahanggas.utils.MyToys;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ucQuanLyTaiKhoan extends javax.swing.JPanel {

    private TaiKhoanNguoiDungDAO taiKhoanDAO;
    private NhanVienDAO nhanVienDAO;
    List<TaiKhoanNguoiDung> list = null;
    public TaiKhoanNguoiDung selectedTaiKhoan = null;
    private Map<String, String> vaiTroMap = new HashMap<>();
    private List<Map<String, String>> dsTaiKhoan = new ArrayList<>();

    public ucQuanLyTaiKhoan(TaiKhoanNguoiDungDAO taiKhoanDAO, NhanVienDAO nv) {
        initComponents();
        this.taiKhoanDAO = taiKhoanDAO;
        this.nhanVienDAO = nv;

        ImageIcon iconTimKiem = new ImageIcon(getClass().getResource("/timkiem.png"));
        Image imgTimKiem = iconTimKiem.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        lblTimKiem.setIcon(new ImageIcon(imgTimKiem));

        ImageIcon iconThem = new ImageIcon(getClass().getResource("/them.png"));
        Image imgThem = iconThem.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        btnThem.setIcon(new ImageIcon(imgThem));

        ImageIcon iconChiTiet = new ImageIcon(getClass().getResource("/info.png"));
        Image imgChiTiet = iconChiTiet.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        btnChiTiet.setIcon(new ImageIcon(imgChiTiet));

        ImageIcon iconXoa = new ImageIcon(getClass().getResource("/xoa.png"));
        Image imgXoa = iconXoa.getImage().getScaledInstance(20, 25, Image.SCALE_SMOOTH);
        btnXoa.setIcon(new ImageIcon(imgXoa));

        //hienThiDuLieuTaiKhoan();
        loadVaiTroToComboBox();
        loadData();
        setupSelectionListener();
    }

    public void loadData() {

        dsTaiKhoan = taiKhoanDAO.getTaiKhoanVoiTenLienKet();

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Mã Tài Khoản", "Tên Đăng Nhập", "Mật Khẩu", "Vai Trò", "Nhân Viên", "Ghi Chú"}, 0);

        for (Map<String, String> row : dsTaiKhoan) {
            model.addRow(new Object[]{
                row.get("MaTaiKhoan"),
                row.get("TenDangNhap"),
                row.get("MatKhau"),
                row.get("TenVaiTro"),
                row.get("TenNhanVien"),
                row.get("GhiChu")
            });
        }

        tblQuanLyTaiKhoan.setModel(model);
    }

    //sự kiện chọn trong Jtable
    private void setupSelectionListener() {
        tblQuanLyTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int selectedRow = tblQuanLyTaiKhoan.getSelectedRow();
                selectedTaiKhoan = null;

                if (selectedRow >= 0) {
                    int modelRow = tblQuanLyTaiKhoan.convertRowIndexToModel(selectedRow);

                    Map<String, String> mapSelected = dsTaiKhoan.get(modelRow);

                    TaiKhoanNguoiDung tk = new TaiKhoanNguoiDung();
                    tk.setMaTaiKhoan(mapSelected.get("MaTaiKhoan"));
                    tk.setTenDangNhap(mapSelected.get("TenDangNhap"));
                    tk.setMatKhau(mapSelected.get("MatKhau"));
                    // Nếu class TaiKhoanNguoiDung có thuộc tính vai trò hoặc nhân viên,
                    // bạn có thể set thêm thông tin ở đây nếu muốn.
                    // Ví dụ:
                    // tk.setVaiTro(mapSelected.get("TenVaiTro"));
                    // tk.setNhanVien(mapSelected.get("TenNhanVien"));

                    selectedTaiKhoan = tk;

                    System.out.println("Đã chọn tài khoản: " + selectedTaiKhoan.getTenDangNhap());
                }
            }
        });
    }

    private void loadVaiTroToComboBox() {
        vaiTroMap = taiKhoanDAO.getDanhSachVaiTro();
        cboVaiTro.removeAllItems();
        cboVaiTro.addItem("Tất cả");
        for (String tenVaiTro : vaiTroMap.keySet()) {
            cboVaiTro.addItem(tenVaiTro);
        }
    }

    private void timKiemTaiKhoan() {
        String keyword = txtTimKiem.getText().trim();
        String tenVaiTro = (String) cboVaiTro.getSelectedItem();

        List<Map<String, String>> ketQua = taiKhoanDAO.timKiemTaiKhoan(keyword, tenVaiTro);
        hienThiKetQuaTimKiem(ketQua);
    }

    private void hienThiKetQuaTimKiem(List<Map<String, String>> ketQua) {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Mã tài khoản", "Tên đăng nhập", "Mật khẩu", "Vai trò", "Nhân viên", "Ghi chú"}, 0);

        for (Map<String, String> row : ketQua) {
            model.addRow(new Object[]{
                row.get("MaTaiKhoan"),
                row.get("TenDangNhap"),
                row.get("MatKhau"),
                row.get("TenVaiTro"),
                row.get("TenNhanVien"),
                row.get("GhiChu")
            });
        }

        tblQuanLyTaiKhoan.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblQuanLyTaiKhoan = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboVaiTro = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblQuanLyTaiKhoan = new javax.swing.JTable();
        btnChiTiet = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        lblTimKiem = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1513, 502));

        lblQuanLyTaiKhoan.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblQuanLyTaiKhoan.setText("Quản lý tài khoản");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Vai trò");

        cboVaiTro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboVaiTroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(cboVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        tblQuanLyTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQuanLyTaiKhoan.setGridColor(new java.awt.Color(153, 153, 255));
        jScrollPane1.setViewportView(tblQuanLyTaiKhoan);

        btnChiTiet.setBackground(java.awt.Color.gray);
        btnChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChiTiet.setForeground(new java.awt.Color(255, 255, 255));
        btnChiTiet.setText("Chi tiết");
        btnChiTiet.setName("btnChiTiet"); // NOI18N
        btnChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiTietActionPerformed(evt);
            }
        });

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

        btnXoa.setBackground(new java.awt.Color(237, 28, 36));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.setName("btnThem"); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQuanLyTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(23, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblQuanLyTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            timKiemTaiKhoan();
        }
    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void cboVaiTroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboVaiTroActionPerformed
        timKiemTaiKhoan();
    }//GEN-LAST:event_cboVaiTroActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        frmMain a = new frmMain(null, null, null, null, null, null, null, null, null);
        dlgChiTietTaiKhoan cttk = new dlgChiTietTaiKhoan(a, null, true, taiKhoanDAO, nhanVienDAO);
        cttk.loadData();
        cttk.setLocationRelativeTo(null);
        cttk.setVisible(true);

        loadData();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiTietActionPerformed
        int selectedRow = tblQuanLyTaiKhoan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một tài khoản để xem chi tiết.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy mã tài khoản từ dòng được chọn
        String maTaiKhoan = tblQuanLyTaiKhoan.getValueAt(selectedRow, 0).toString();

        // Lấy đối tượng tài khoản từ DAO
        TaiKhoanNguoiDung tk = taiKhoanDAO.findById(maTaiKhoan);
        if (tk == null) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy dữ liệu tài khoản.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mở dialog chi tiết tài khoản
        dlgChiTietTaiKhoan cttk = new dlgChiTietTaiKhoan(null, tk, true, taiKhoanDAO, nhanVienDAO);
        cttk.loadData();
        cttk.setLocationRelativeTo(null);
        cttk.setVisible(true);

        // Sau khi đóng dialog, cập nhật lại dữ liệu bảng
        loadData();
    }//GEN-LAST:event_btnChiTietActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if (selectedTaiKhoan == null) {
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 đối tượng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn thoát không?",
                "Xác nhận thoát",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {
            taiKhoanDAO.delete(selectedTaiKhoan.getMaTaiKhoan());
            JOptionPane.showMessageDialog(null, "Xoa tai khoan thành công!");
            loadData();
        }
    }//GEN-LAST:event_btnXoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChiTiet;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboVaiTro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblQuanLyTaiKhoan;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JTable tblQuanLyTaiKhoan;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
