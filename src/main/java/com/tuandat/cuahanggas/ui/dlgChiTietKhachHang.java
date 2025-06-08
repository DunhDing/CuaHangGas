/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.impl.KhachHangDAO;
import com.tuandat.cuahanggas.model.KhachHang;
import com.tuandat.cuahanggas.utils.MyToys;
import java.awt.Image;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class dlgChiTietKhachHang extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(dlgChiTietKhachHang.class.getName());
    public KhachHang selectedAKhachHang = null;
    private final KhachHangDAO khachHangDAO;

    /**
     * Creates new form dlgChiTietKhachHang
     *
     * @param parent
     * @param khachHang
     * @param modal
     * @param khachHangDAO
     */
    public dlgChiTietKhachHang(java.awt.Frame parent, KhachHang khachHang, boolean modal, KhachHangDAO khachHangDAO) {
        super(parent, "Chi Tiết Khách Hàng", true);
        this.selectedAKhachHang = khachHang;
        this.khachHangDAO = khachHangDAO;
        initComponents();

        setLocationRelativeTo(parent);
        txtMaKhachHang.setEnabled(false);
        ImageIcon iconLuu = new ImageIcon(getClass().getResource("/luu.png"));
        Image imgLuu = iconLuu.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btnLuu.setIcon(new ImageIcon(imgLuu));
        MyToys.setNumericFilter(txtSoDienThoai);
    }

    public void loadData() {
        List<KhachHang> danhKhachHangs = null;
        try {
            danhKhachHangs = khachHangDAO.getAll(); // Lấy tất cả bình gas từ DB
            System.out.println("Da lay duoc du lieu Binh Gas tu CSDL.");
        } catch (Exception e) {
            System.err.println("Loi khi lay du lieu Binh Gas tu CSDL: " + e.getMessage());
            e.printStackTrace();
            return; // Dừng nếu không thể lấy dữ liệu
        }

        // --- Đổ dữ liệu vào cboLoaiBinh và cboLoaiVan bằng Lambda ---
        // Sử dụng Set để đảm bảo các giá trị là duy nhất
        Set<String> distinctGioiTinh = danhKhachHangs.stream()
                .map(KhachHang::getGioiTinh)
                .filter(loai -> loai != null && !loai.trim().isEmpty()) // Lọc bỏ null hoặc rỗng
                .collect(Collectors.toSet());

        // Fill cboLoaiVan
        DefaultComboBoxModel<String> loaiVanModel = new DefaultComboBoxModel<>();
        // Thêm các giá trị duy nhất từ Set vào model
        distinctGioiTinh.forEach(loaiVanModel::addElement);
        cboGioiTinh.setModel(loaiVanModel);

        //PHẦN KHÁC BIỆT
        if (selectedAKhachHang != null) {
            //update
            txtMaKhachHang.setText(selectedAKhachHang.getMaKhachHang());
            txtTenKhachHang.setText(selectedAKhachHang.getHoTen());
            dtpNgaySinh.setDate(selectedAKhachHang.getNgaySinh());
            txtGhiChu.setText(selectedAKhachHang.getGhiChu());
            txtSoDienThoai.setText(selectedAKhachHang.getSdt());
            txtEmail.setText(selectedAKhachHang.getEmail());
            txtDiaChi.setText(selectedAKhachHang.getDiaChi());
            cboGioiTinh.setSelectedItem(selectedAKhachHang.getGioiTinh());

        } else {
            //add
            List<String> distinctMaKhachHang = danhKhachHangs.stream()
                    .map(KhachHang::getMaKhachHang) // Chọn thuộc tính ID
                    .collect(Collectors.toList()); // Chuyển thành List

            //lấy mã tự động
            txtMaKhachHang.setText(MyToys.generateNextIdFromStrings(distinctMaKhachHang, "KH"));
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlNgaySinh = new javax.swing.JPanel();
        lblNgaySinh = new java.awt.Label();
        dtpNgaySinh = new com.toedter.calendar.JDateChooser();
        pnlMaKhachHang = new javax.swing.JPanel();
        lblMaKhachHang = new java.awt.Label();
        txtMaKhachHang = new javax.swing.JTextField();
        pnlTenKhachHang = new javax.swing.JPanel();
        lblTenKhachHang = new java.awt.Label();
        txtTenKhachHang = new javax.swing.JTextField();
        pnlGioiTinh = new javax.swing.JPanel();
        lblGioiTinh = new java.awt.Label();
        cboGioiTinh = new javax.swing.JComboBox<>();
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

        pnlNgaySinh.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlNgaySinh.setName("pnlMaBinhGas"); // NOI18N

        lblNgaySinh.setText("Ngày Sinh");

        javax.swing.GroupLayout pnlNgaySinhLayout = new javax.swing.GroupLayout(pnlNgaySinh);
        pnlNgaySinh.setLayout(pnlNgaySinhLayout);
        pnlNgaySinhLayout.setHorizontalGroup(
            pnlNgaySinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNgaySinhLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dtpNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlNgaySinhLayout.setVerticalGroup(
            pnlNgaySinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNgaySinhLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(pnlNgaySinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dtpNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pnlMaKhachHang.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlMaKhachHang.setName("pnlMaBinhGas"); // NOI18N

        lblMaKhachHang.setText("Mã Khách Hàng");

        txtMaKhachHang.setName("txtMaBinhGas"); // NOI18N

        javax.swing.GroupLayout pnlMaKhachHangLayout = new javax.swing.GroupLayout(pnlMaKhachHang);
        pnlMaKhachHang.setLayout(pnlMaKhachHangLayout);
        pnlMaKhachHangLayout.setHorizontalGroup(
            pnlMaKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMaKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        lblTenKhachHang.setText("Tên Khách Hàng");

        txtTenKhachHang.setName("txtMaBinhGas"); // NOI18N

        javax.swing.GroupLayout pnlTenKhachHangLayout = new javax.swing.GroupLayout(pnlTenKhachHang);
        pnlTenKhachHang.setLayout(pnlTenKhachHangLayout);
        pnlTenKhachHangLayout.setHorizontalGroup(
            pnlTenKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTenKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
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

        pnlGioiTinh.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlGioiTinh.setName("pnlMaBinhGas"); // NOI18N

        lblGioiTinh.setText("Giới Tính");

        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlGioiTinhLayout = new javax.swing.GroupLayout(pnlGioiTinh);
        pnlGioiTinh.setLayout(pnlGioiTinhLayout);
        pnlGioiTinhLayout.setHorizontalGroup(
            pnlGioiTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGioiTinhLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlGioiTinhLayout.setVerticalGroup(
            pnlGioiTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGioiTinhLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(pnlGioiTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMaKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlGioiTinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(pnlGhiChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(pnlMaKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlSoDienThoai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTenKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlGioiTinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlDiaChi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlNgaySinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlGhiChu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLuuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLuuMouseClicked
        if (!MyToys.isValidInput(txtTenKhachHang, txtSoDienThoai, txtEmail, txtDiaChi, txtGhiChu)) {
            return;
        }
        if (dtpNgaySinh.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày!");
            return;
        }
        if (!MyToys.validatePhone(txtSoDienThoai)) {
            return; // Dừng lại nếu không hợp lệ
        }
        try {
            // Lấy dữ liệu từ form
            String ma = txtMaKhachHang.getText();
            String ten = txtTenKhachHang.getText();
            String gioiTinh = cboGioiTinh.getSelectedItem().toString();
            Date ngaySinh = dtpNgaySinh.getDate();
            String sdt = txtSoDienThoai.getText();
            String email = txtEmail.getText();
            String diaChi = txtDiaChi.getText();
            String ghiChu = txtGhiChu.getText();

            // Tạo đối tượng BinhGas
            KhachHang kh = new KhachHang(ma, ten, gioiTinh, ngaySinh, sdt, email, diaChi, ghiChu);
            if (selectedAKhachHang != null) {
                khachHangDAO.update(kh);
            } else {
                khachHangDAO.insert(kh);
            }
            JOptionPane.showMessageDialog(null, "Lưu khách hàng thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu khách hàng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        this.dispose();
    }//GEN-LAST:event_btnLuuMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLuu;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private com.toedter.calendar.JDateChooser dtpNgaySinh;
    private java.awt.Label lblDiaChi;
    private java.awt.Label lblEmail;
    private java.awt.Label lblGhiChu;
    private java.awt.Label lblGioiTinh;
    private java.awt.Label lblMaKhachHang;
    private java.awt.Label lblNgaySinh;
    private java.awt.Label lblSoDienThoai;
    private java.awt.Label lblTenKhachHang;
    private javax.swing.JPanel pnlDiaChi;
    private javax.swing.JPanel pnlEmail;
    private javax.swing.JPanel pnlGhiChu;
    private javax.swing.JPanel pnlGioiTinh;
    private javax.swing.JPanel pnlMaKhachHang;
    private javax.swing.JPanel pnlNgaySinh;
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
