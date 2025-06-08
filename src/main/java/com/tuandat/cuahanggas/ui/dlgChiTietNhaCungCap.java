/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.impl.NhaCungCapDAO;
import com.tuandat.cuahanggas.model.NhaCungCap;
import com.tuandat.cuahanggas.utils.MyToys;
import java.awt.Image;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class dlgChiTietNhaCungCap extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(dlgChiTietNhaCungCap.class.getName());
    public NhaCungCap selectedANCC = null;
    private final NhaCungCapDAO nhaCungCapDAO;

    /**
     * Creates new form dlgChiTietKhachHang
     *
     * @param parent
     * @param modal
     */
    public dlgChiTietNhaCungCap(java.awt.Frame parent, NhaCungCap selectedANCC, boolean modal, NhaCungCapDAO nhaCungCapDAO) {
        super(parent, "Chi Tiết Khách Hàng", true);
        this.selectedANCC = selectedANCC;
        this.nhaCungCapDAO = nhaCungCapDAO;
        initComponents();

        setLocationRelativeTo(parent);
        txtMaKhachHang.setEnabled(false);
        ImageIcon iconLuu = new ImageIcon(getClass().getResource("/luu.png"));
        Image imgLuu = iconLuu.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btnLuu.setIcon(new ImageIcon(imgLuu));
        
        MyToys.setNumericFilter(txtSoDienThoai);
    }

    public void loadData() {
        List<NhaCungCap> danhKhachHangs = null;
        try {
            danhKhachHangs = nhaCungCapDAO.getAll(); // Lấy tất cả bình gas từ DB
            System.out.println("Da lay duoc du lieu Binh Gas tu CSDL.");
        } catch (Exception e) {
            System.err.println("Loi khi lay du lieu Binh Gas tu CSDL: " + e.getMessage());
            e.printStackTrace();
            return; // Dừng nếu không thể lấy dữ liệu
        }

        //PHẦN KHÁC BIỆT
        if (selectedANCC != null) {
            //update
            txtMaKhachHang.setText(selectedANCC.getMaNhaCungCap());
            txtTenKhachHang.setText(selectedANCC.getTenNhaCungCap());
            txtGhiChu.setText(selectedANCC.getGhiChu());
            txtSoDienThoai.setText(selectedANCC.getSdt());
            txtEmail.setText(selectedANCC.getEmail());
            txtDiaChi.setText(selectedANCC.getDiaChi());

        } else {
            //add
            List<String> distinctMaNCC = danhKhachHangs.stream()
                    .map(NhaCungCap::getMaNhaCungCap) // Chọn thuộc tính ID
                    .collect(Collectors.toList()); // Chuyển thành List

            //lấy mã tự động
            txtMaKhachHang.setText(MyToys.generateNextIdFromStrings(distinctMaNCC, "NCC"));
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMaKhachHang = new javax.swing.JPanel();
        lblMaKhachHang = new java.awt.Label();
        txtMaKhachHang = new javax.swing.JTextField();
        pnlTenKhachHang = new javax.swing.JPanel();
        lblTenKhachHang = new java.awt.Label();
        txtTenKhachHang = new javax.swing.JTextField();
        pnlEmail = new javax.swing.JPanel();
        lblEmail = new java.awt.Label();
        txtEmail = new javax.swing.JTextField();
        pnlSoDienThoai = new javax.swing.JPanel();
        lblSoDienThoai = new java.awt.Label();
        txtSoDienThoai = new javax.swing.JTextField();
        pnlGhiChu = new javax.swing.JPanel();
        lblGhiChu = new java.awt.Label();
        txtGhiChu = new javax.swing.JTextField();
        pnlDiaChi = new javax.swing.JPanel();
        lblDiaChi = new java.awt.Label();
        txtDiaChi = new javax.swing.JTextField();
        btnLuu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlMaKhachHang.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlMaKhachHang.setName("pnlMaBinhGas"); // NOI18N

        lblMaKhachHang.setText("Mã Nhà Cung Cấp");

        txtMaKhachHang.setName("txtMaBinhGas"); // NOI18N

        javax.swing.GroupLayout pnlMaKhachHangLayout = new javax.swing.GroupLayout(pnlMaKhachHang);
        pnlMaKhachHang.setLayout(pnlMaKhachHangLayout);
        pnlMaKhachHangLayout.setHorizontalGroup(
            pnlMaKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMaKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlMaKhachHangLayout.setVerticalGroup(
            pnlMaKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMaKhachHangLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(pnlMaKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pnlTenKhachHang.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlTenKhachHang.setName("pnlMaBinhGas"); // NOI18N

        lblTenKhachHang.setText("Tên Nhà Cung Cấp");

        txtTenKhachHang.setName("txtMaBinhGas"); // NOI18N

        javax.swing.GroupLayout pnlTenKhachHangLayout = new javax.swing.GroupLayout(pnlTenKhachHang);
        pnlTenKhachHang.setLayout(pnlTenKhachHangLayout);
        pnlTenKhachHangLayout.setHorizontalGroup(
            pnlTenKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTenKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTenKhachHangLayout.setVerticalGroup(
            pnlTenKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTenKhachHangLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(pnlTenKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pnlEmail.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlEmail.setName("pnlMaBinhGas"); // NOI18N

        lblEmail.setText("Email");

        txtEmail.setName("txtMaBinhGas"); // NOI18N

        javax.swing.GroupLayout pnlEmailLayout = new javax.swing.GroupLayout(pnlEmail);
        pnlEmail.setLayout(pnlEmailLayout);
        pnlEmailLayout.setHorizontalGroup(
            pnlEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEmailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlEmailLayout.setVerticalGroup(
            pnlEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEmailLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(pnlEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pnlSoDienThoai.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlSoDienThoai.setName("pnlMaBinhGas"); // NOI18N

        lblSoDienThoai.setText("Số Điện Thoại");

        txtSoDienThoai.setName("txtMaBinhGas"); // NOI18N

        javax.swing.GroupLayout pnlSoDienThoaiLayout = new javax.swing.GroupLayout(pnlSoDienThoai);
        pnlSoDienThoai.setLayout(pnlSoDienThoaiLayout);
        pnlSoDienThoaiLayout.setHorizontalGroup(
            pnlSoDienThoaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSoDienThoaiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlSoDienThoaiLayout.setVerticalGroup(
            pnlSoDienThoaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSoDienThoaiLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(pnlSoDienThoaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pnlGhiChu.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlGhiChu.setName("pnlMaBinhGas"); // NOI18N

        lblGhiChu.setText("Ghi Chú");

        txtGhiChu.setName("txtMaBinhGas"); // NOI18N

        javax.swing.GroupLayout pnlGhiChuLayout = new javax.swing.GroupLayout(pnlGhiChu);
        pnlGhiChu.setLayout(pnlGhiChuLayout);
        pnlGhiChuLayout.setHorizontalGroup(
            pnlGhiChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGhiChuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlGhiChuLayout.setVerticalGroup(
            pnlGhiChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGhiChuLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(pnlGhiChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pnlDiaChi.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlDiaChi.setName("pnlMaBinhGas"); // NOI18N

        lblDiaChi.setText("Địa Chỉ");

        txtDiaChi.setName("txtMaBinhGas"); // NOI18N

        javax.swing.GroupLayout pnlDiaChiLayout = new javax.swing.GroupLayout(pnlDiaChi);
        pnlDiaChi.setLayout(pnlDiaChiLayout);
        pnlDiaChiLayout.setHorizontalGroup(
            pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDiaChiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlDiaChiLayout.setVerticalGroup(
            pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDiaChiLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        btnLuu.setBackground(new java.awt.Color(0, 176, 80));
        btnLuu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLuu.setForeground(new java.awt.Color(255, 255, 255));
        btnLuu.setText("Lưu");
        btnLuu.setName("btnLuu"); // NOI18N
        btnLuu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLuuMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTenKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMaKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlGhiChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(107, 107, 107)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(pnlEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlSoDienThoai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlDiaChi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(111, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlMaKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlSoDienThoai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlTenKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addComponent(pnlDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlGhiChu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(141, 141, 141)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLuuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLuuMouseClicked
        if (!MyToys.isValidInput(txtTenKhachHang, txtSoDienThoai, txtEmail, txtDiaChi, txtGhiChu)) {
            return;
        }
        if (!MyToys.validatePhone(txtSoDienThoai)) {
            return; // Dừng lại nếu không hợp lệ
        }
        try {
            // Lấy dữ liệu từ form
            String ma = txtMaKhachHang.getText();
            String ten = txtTenKhachHang.getText();
            String sdt = txtSoDienThoai.getText();
            String email = txtEmail.getText();
            String diaChi = txtDiaChi.getText();
            String ghiChu = txtGhiChu.getText();

            // Tạo đối tượng BinhGas
            NhaCungCap kh = new NhaCungCap(ma, ten, sdt, email, diaChi, ghiChu);
            if (selectedANCC != null) {
                nhaCungCapDAO.update(kh);
            } else {
                nhaCungCapDAO.insert(kh);
            }
            JOptionPane.showMessageDialog(null, "Lưu khách hàng thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu khách hàng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        this.dispose();
    }//GEN-LAST:event_btnLuuMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLuu;
    private java.awt.Label lblDiaChi;
    private java.awt.Label lblEmail;
    private java.awt.Label lblGhiChu;
    private java.awt.Label lblMaKhachHang;
    private java.awt.Label lblSoDienThoai;
    private java.awt.Label lblTenKhachHang;
    private javax.swing.JPanel pnlDiaChi;
    private javax.swing.JPanel pnlEmail;
    private javax.swing.JPanel pnlGhiChu;
    private javax.swing.JPanel pnlMaKhachHang;
    private javax.swing.JPanel pnlSoDienThoai;
    private javax.swing.JPanel pnlTenKhachHang;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtMaKhachHang;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenKhachHang;
    // End of variables declaration//GEN-END:variables
}
