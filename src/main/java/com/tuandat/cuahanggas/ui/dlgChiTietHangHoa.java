/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.impl.BinhGasDAO;
import com.tuandat.cuahanggas.model.BinhGas;
import com.tuandat.cuahanggas.utils.MyToys;
import java.awt.Image;
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
public class dlgChiTietHangHoa extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(dlgChiTietHangHoa.class.getName());
    public BinhGas selectedABinhGas = null;
    private final BinhGasDAO binhGasDAO;

    /**
     * Creates new form dlgChiTietHangHoa
     *
     * @param parent
     * @param binhGas
     * @param modal
     * @param binhGasDAO
     */
    public dlgChiTietHangHoa(java.awt.Frame parent, BinhGas binhGas, boolean modal, BinhGasDAO binhGasDAO) {
        super(parent, "Chi Tiết Hàng Hóa", true);
        this.selectedABinhGas = binhGas;
        this.binhGasDAO = binhGasDAO;
        initComponents();
        setLocationRelativeTo(parent);
        txtMaBinhGa.setEnabled(false);

        ImageIcon iconLuu = new ImageIcon(getClass().getResource("/luu.png"));
        Image imgLuu = iconLuu.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btnLuu.setIcon(new ImageIcon(imgLuu));
        MyToys.setNumericFilter(txtSoLuong);
        MyToys.setNumericFilter(txtSoVonTB);
    }

    public void loadData() {
        List<BinhGas> danhSachBinhGas = null;
        try {
            danhSachBinhGas = binhGasDAO.getAll(); // Lấy tất cả bình gas từ DB
            System.out.println("Da lay duoc du lieu Binh Gas tu CSDL.");
        } catch (Exception e) {
            System.err.println("Loi khi lay du lieu Binh Gas tu CSDL: " + e.getMessage());
            e.printStackTrace();
            return; // Dừng nếu không thể lấy dữ liệu
        }

        // --- Đổ dữ liệu vào cboLoaiBinh và cboLoaiVan bằng Lambda ---
        // Sử dụng Set để đảm bảo các giá trị là duy nhất
        Set<String> distinctLoaiBinhs = danhSachBinhGas.stream()
                .map(BinhGas::getLoaiBinh)
                .filter(loai -> loai != null && !loai.trim().isEmpty()) // Lọc bỏ null hoặc rỗng
                .collect(Collectors.toSet());

        Set<String> distinctLoaiVans = danhSachBinhGas.stream()
                .map(BinhGas::getLoaiVan)
                .filter(van -> van != null && !van.trim().isEmpty()) // Lọc bỏ null hoặc rỗng
                .collect(Collectors.toSet());
        // Fill cboLoaiBinh
        DefaultComboBoxModel<String> loaiBinhModel = new DefaultComboBoxModel<>();
        // Thêm các giá trị duy nhất từ Set vào model
        distinctLoaiBinhs.forEach(loaiBinhModel::addElement);
        cboLoaiBinh.setModel(loaiBinhModel);
        System.out.println("Du lieu Loai Binh da duoc tai vao ComboBox.");

        // Fill cboLoaiVan
        DefaultComboBoxModel<String> loaiVanModel = new DefaultComboBoxModel<>();
        // Thêm các giá trị duy nhất từ Set vào model
        distinctLoaiVans.forEach(loaiVanModel::addElement);
        cboLoaiVan.setModel(loaiVanModel);
        System.out.println("Du lieu Loai Van da duoc tai vao ComboBox.");

        //PHẦN KHÁC BIỆT
        if (selectedABinhGas != null) {
            //update
            txtMaBinhGa.setText(selectedABinhGas.getMaBinhGas());
            txtTenBinhGas.setText(selectedABinhGas.getTenBinhGas());
            txtSoLuong.setText(Integer.toString(selectedABinhGas.getSoLuong()));
            txtSoVonTB.setText(Integer.toString(selectedABinhGas.getGiaVonTrungBinh()));
            txtGhiChu.setText(selectedABinhGas.getGhiChu());
            cboLoaiBinh.setSelectedItem(selectedABinhGas.getLoaiBinh());
            cboLoaiVan.setSelectedItem(selectedABinhGas.getLoaiVan());
            
        } else {
            //add
            List<String> distinctMaBinhGas = danhSachBinhGas.stream()
                    .map(BinhGas::getMaBinhGas) // Chọn thuộc tính ID
                    .collect(Collectors.toList()); // Chuyển thành List

            //lấy mã tự động
            txtMaBinhGa.setText(MyToys.generateNextIdFromStrings(distinctMaBinhGas, "BG"));
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

        pnlMaBinhGa = new javax.swing.JPanel();
        lblMaBinhGa = new java.awt.Label();
        txtMaBinhGa = new javax.swing.JTextField();
        pnlTenBinhGas = new javax.swing.JPanel();
        lblTenBinhGas = new java.awt.Label();
        txtTenBinhGas = new javax.swing.JTextField();
        pnlLoaiBinh = new javax.swing.JPanel();
        lblLoaiBinh = new java.awt.Label();
        cboLoaiBinh = new javax.swing.JComboBox<>();
        pnlLoaiVan = new javax.swing.JPanel();
        lblLoaiVan = new java.awt.Label();
        cboLoaiVan = new javax.swing.JComboBox<>();
        pnlSoLuong = new javax.swing.JPanel();
        lblSoLuong = new java.awt.Label();
        txtSoLuong = new javax.swing.JTextField();
        pnlSoVonTB = new javax.swing.JPanel();
        lblSoVonTB = new java.awt.Label();
        txtSoVonTB = new javax.swing.JTextField();
        pnlGhiChu = new javax.swing.JPanel();
        lblGhiChu = new java.awt.Label();
        txtGhiChu = new javax.swing.JTextField();
        btnLuu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlMaBinhGa.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlMaBinhGa.setName("pnlMaBinhGas"); // NOI18N

        lblMaBinhGa.setText("Mã Bình Gas");

        txtMaBinhGa.setName("txtMaBinhGas"); // NOI18N

        javax.swing.GroupLayout pnlMaBinhGaLayout = new javax.swing.GroupLayout(pnlMaBinhGa);
        pnlMaBinhGa.setLayout(pnlMaBinhGaLayout);
        pnlMaBinhGaLayout.setHorizontalGroup(
            pnlMaBinhGaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMaBinhGaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMaBinhGa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtMaBinhGa, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlMaBinhGaLayout.setVerticalGroup(
            pnlMaBinhGaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMaBinhGaLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(pnlMaBinhGaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMaBinhGa, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaBinhGa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pnlTenBinhGas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlTenBinhGas.setName("pnlTenBinhGas"); // NOI18N

        lblTenBinhGas.setText("Tên Bình Gas");

        txtTenBinhGas.setName("txtTenBinhGas"); // NOI18N

        javax.swing.GroupLayout pnlTenBinhGasLayout = new javax.swing.GroupLayout(pnlTenBinhGas);
        pnlTenBinhGas.setLayout(pnlTenBinhGasLayout);
        pnlTenBinhGasLayout.setHorizontalGroup(
            pnlTenBinhGasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTenBinhGasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTenBinhGas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(txtTenBinhGas, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTenBinhGasLayout.setVerticalGroup(
            pnlTenBinhGasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTenBinhGasLayout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(pnlTenBinhGasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTenBinhGas, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTenBinhGas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pnlLoaiBinh.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlLoaiBinh.setName("pnlLoaiBinh"); // NOI18N

        lblLoaiBinh.setText("Loại Bình");

        cboLoaiBinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlLoaiBinhLayout = new javax.swing.GroupLayout(pnlLoaiBinh);
        pnlLoaiBinh.setLayout(pnlLoaiBinhLayout);
        pnlLoaiBinhLayout.setHorizontalGroup(
            pnlLoaiBinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiBinhLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLoaiBinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboLoaiBinh, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlLoaiBinhLayout.setVerticalGroup(
            pnlLoaiBinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoaiBinhLayout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(pnlLoaiBinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboLoaiBinh, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLoaiBinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pnlLoaiVan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlLoaiVan.setName("pnlLoaiVan"); // NOI18N

        lblLoaiVan.setText("Loại Van");

        cboLoaiVan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlLoaiVanLayout = new javax.swing.GroupLayout(pnlLoaiVan);
        pnlLoaiVan.setLayout(pnlLoaiVanLayout);
        pnlLoaiVanLayout.setHorizontalGroup(
            pnlLoaiVanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiVanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLoaiVan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboLoaiVan, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlLoaiVanLayout.setVerticalGroup(
            pnlLoaiVanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoaiVanLayout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(pnlLoaiVanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboLoaiVan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLoaiVan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pnlSoLuong.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlSoLuong.setName("pnlSoluong"); // NOI18N

        lblSoLuong.setText("Số Lượng");

        txtSoLuong.setName("txtSoLuong"); // NOI18N
        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnlSoLuongLayout = new javax.swing.GroupLayout(pnlSoLuong);
        pnlSoLuong.setLayout(pnlSoLuongLayout);
        pnlSoLuongLayout.setHorizontalGroup(
            pnlSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSoLuongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlSoLuongLayout.setVerticalGroup(
            pnlSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSoLuongLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(pnlSoLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        pnlSoVonTB.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlSoVonTB.setName("pnlSoVonTB"); // NOI18N

        lblSoVonTB.setText("Số Vốn TB");

        txtSoVonTB.setName("txtSoVonTB"); // NOI18N

        javax.swing.GroupLayout pnlSoVonTBLayout = new javax.swing.GroupLayout(pnlSoVonTB);
        pnlSoVonTB.setLayout(pnlSoVonTBLayout);
        pnlSoVonTBLayout.setHorizontalGroup(
            pnlSoVonTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSoVonTBLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSoVonTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(txtSoVonTB, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlSoVonTBLayout.setVerticalGroup(
            pnlSoVonTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSoVonTBLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(pnlSoVonTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSoVonTB, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSoVonTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        pnlGhiChu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlGhiChu.setName("pnlGhiChu"); // NOI18N

        lblGhiChu.setText("Ghi Chú");

        txtGhiChu.setName("txtGhiChu"); // NOI18N

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
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlLoaiVan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLoaiBinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTenBinhGas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMaBinhGa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlSoVonTB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlGhiChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlSoLuong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(44, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(119, 119, 119))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pnlMaBinhGa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pnlSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(pnlTenBinhGas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pnlSoVonTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(pnlLoaiBinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(pnlLoaiVan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(91, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLuuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLuuMouseClicked

        if (!MyToys.isValidInput(txtTenBinhGas, txtSoLuong, txtSoVonTB, txtGhiChu)) {
            return;
        }
        try {
            // Lấy dữ liệu từ form
            String maBinhGas = txtMaBinhGa.getText();
            String tenBinhGas = txtTenBinhGas.getText();
            String loaiBinh = cboLoaiBinh.getSelectedItem().toString();
            String loaiVan = cboLoaiVan.getSelectedItem().toString();
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            int giaVonTrungBinh = Integer.parseInt(txtSoVonTB.getText());
            String ghiChu = txtGhiChu.getText();

            // Tạo đối tượng BinhGas
            BinhGas binhGas = new BinhGas(maBinhGas, tenBinhGas, loaiBinh, loaiVan, soLuong, giaVonTrungBinh, ghiChu);
            if (selectedABinhGas != null)
                binhGasDAO.update(binhGas);
            else
                binhGasDAO.insert(binhGas);
            JOptionPane.showMessageDialog(null, "Thêm mới bình gas thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu bình gas: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        this.dispose();
    }//GEN-LAST:event_btnLuuMouseClicked

    private void txtSoLuongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyPressed
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume(); // chặn ký tự không hợp lệ hoặc vượt quá 10 số
        }
    }//GEN-LAST:event_txtSoLuongKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLuu;
    private javax.swing.JComboBox<String> cboLoaiBinh;
    private javax.swing.JComboBox<String> cboLoaiVan;
    private java.awt.Label lblGhiChu;
    private java.awt.Label lblLoaiBinh;
    private java.awt.Label lblLoaiVan;
    private java.awt.Label lblMaBinhGa;
    private java.awt.Label lblSoLuong;
    private java.awt.Label lblSoVonTB;
    private java.awt.Label lblTenBinhGas;
    private javax.swing.JPanel pnlGhiChu;
    private javax.swing.JPanel pnlLoaiBinh;
    private javax.swing.JPanel pnlLoaiVan;
    private javax.swing.JPanel pnlMaBinhGa;
    private javax.swing.JPanel pnlSoLuong;
    private javax.swing.JPanel pnlSoVonTB;
    private javax.swing.JPanel pnlTenBinhGas;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtMaBinhGa;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoVonTB;
    private javax.swing.JTextField txtTenBinhGas;
    // End of variables declaration//GEN-END:variables
}
