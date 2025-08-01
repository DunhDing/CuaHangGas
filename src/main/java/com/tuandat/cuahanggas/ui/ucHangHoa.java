/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.impl.BinhGasDAO;
import com.tuandat.cuahanggas.dao.impl.ChiTietNhapHangDAO;
import com.tuandat.cuahanggas.dao.impl.ChiTietXuatHangDAO;
import com.tuandat.cuahanggas.dao.impl.ChiTietXuatHangDAOV2;
import com.tuandat.cuahanggas.model.BinhGas;
import com.tuandat.cuahanggas.model.ExcelExporter;
import com.tuandat.cuahanggas.utils.MyToys;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Image;
import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class ucHangHoa extends javax.swing.JPanel {

    private BinhGasDAO binhGasDAO;
    private BinhGas selectedBinhGas = null;
    private static ChiTietNhapHangDAO chiTietNhapHangDAO;
    private static ChiTietXuatHangDAOV2 chiTietXuatHangDAO;
    List<BinhGas> danhSachBinhGas = null;
    private static final Logger logger = Logger.getLogger(ucHangHoa.class.getName());

    /**
     * Creates new form ucHangHoa
     *
     * @param binhGasDAO
     */
    public ucHangHoa(BinhGasDAO binhGasDAO, ChiTietNhapHangDAO chiTietNhapHangDAO, ChiTietXuatHangDAOV2 chiTietXuatHangDAO) {
        this.binhGasDAO = binhGasDAO;
        this.chiTietNhapHangDAO = chiTietNhapHangDAO;
        this.chiTietXuatHangDAO = chiTietXuatHangDAO;
        try {
            initComponents();
//        ImageIcon icon = new ImageIcon("Logo.png"); // Đường dẫn đến ảnh
//        JLabel label = new JLabel("Text", icon, JLabel.LEFT);
            ImageIcon iconTimKiem = new ImageIcon(getClass().getResource("/timkiem.png"));
            Image imgTimKiem = iconTimKiem.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            lblTimKiem.setIcon(new ImageIcon(imgTimKiem));
            
            ImageIcon iconThem = new ImageIcon(getClass().getResource("/them.png"));
            Image imgThem = iconThem.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            btnThem.setIcon(new ImageIcon(imgThem));

            ImageIcon iconChiTiet = new ImageIcon(getClass().getResource("/info.png"));
            Image imgChiTiet = iconChiTiet.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            btnChiTiet.setIcon(new ImageIcon(imgChiTiet));

            ImageIcon iconXuatFile = new ImageIcon(getClass().getResource("/excel.png"));
            Image imgXuatFile = iconXuatFile.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            btnXuatFile.setIcon(new ImageIcon(imgXuatFile));

            ImageIcon iconXoa = new ImageIcon(getClass().getResource("/trash-solid.png"));
            Image imgXoa = iconXoa.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            btnXoa.setIcon(new ImageIcon(imgXoa));

            setupSelectionListener();
            cboLoaiBinh.addActionListener(e -> filterTable());
            cboLoaiVan.addActionListener(e -> filterTable());
            txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    filterTable();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    filterTable();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    filterTable();
                }
            });

        } catch (Exception e) {
            System.err.println("ERROR: Lỗi nghiêm trọng trong constructor của ucHangHoa:");
            e.printStackTrace(); // In toàn bộ stack trace của lỗi
            // Tùy chọn: hiển thị JOptionPane cho người dùng
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi khởi tạo panel Hàng hóa: " + e.getMessage(),
                    "Lỗi Khởi Tạo", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadData() {

        try {
            danhSachBinhGas = binhGasDAO.getAll(); // Lấy tất cả bình gas từ DB
            System.out.println("Da lay duoc du lieu Binh Gas tu CSDL.");
        } catch (Exception e) {
            System.err.println("Loi khi lay du lieu Binh Gas tu CSDL: " + e.getMessage());
            e.printStackTrace();
            return; // Dừng nếu không thể lấy dữ liệu
        }

        tbleHangHoa.setModel(MyToys.listToTableModel(danhSachBinhGas));
        String[] columnNames = {"Mã Bình Gas", "Tên Bình Gas", "Loại Bình", "Loại Van", "Số Lượng", "Giá Vốn TB", "Ghi Chú"};
        for (int i = 0; i < columnNames.length; i++) {
            // Ensure we don't go out of bounds if MyToys.listToTableModel returns fewer columns than expected
            if (i < tbleHangHoa.getColumnModel().getColumnCount()) {
                tbleHangHoa.getColumnModel().getColumn(i).setHeaderValue(columnNames[i]);
            }
        }
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
        loaiBinhModel.addElement("Tất cả"); // Thêm tùy chọn "Tất cả"
        // Thêm các giá trị duy nhất từ Set vào model
        distinctLoaiBinhs.forEach(loaiBinhModel::addElement);
        cboLoaiBinh.setModel(loaiBinhModel);
        System.out.println("Du lieu Loai Binh da duoc tai vao ComboBox.");

        // Fill cboLoaiVan
        DefaultComboBoxModel<String> loaiVanModel = new DefaultComboBoxModel<>();
        loaiVanModel.addElement("Tất cả"); // Thêm tùy chọn "Tất cả"
        // Thêm các giá trị duy nhất từ Set vào model
        distinctLoaiVans.forEach(loaiVanModel::addElement);
        cboLoaiVan.setModel(loaiVanModel);
        System.out.println("Du lieu Loai Van da duoc tai vao ComboBox.");

        selectedBinhGas = null;
    }

    private void filterTable() {
        String selectedLoaiVan = (String) cboLoaiVan.getSelectedItem();
        String selectedLoaiBinh = (String) cboLoaiBinh.getSelectedItem();
        String keyword = txtTimKiem.getText().trim().toLowerCase();

        List<BinhGas> allData = binhGasDAO.getAll();

        List<BinhGas> filtered = allData.stream()
                .filter(b -> "Tất cả".equals(selectedLoaiVan) || b.getLoaiVan().equals(selectedLoaiVan))
                .filter(b -> "Tất cả".equals(selectedLoaiBinh) || b.getLoaiBinh().equals(selectedLoaiBinh))
                .filter(b -> b.getMaBinhGas().toLowerCase().contains(keyword)
                || b.getTenBinhGas().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        fillTable(filtered);
    }

    private void fillTable(List<BinhGas> list) {
        DefaultTableModel model = (DefaultTableModel) tbleHangHoa.getModel();
        model.setRowCount(0); // clear old data

        for (BinhGas b : list) {
            model.addRow(new Object[]{
                b.getMaBinhGas(),
                b.getTenBinhGas(),
                b.getLoaiVan(),
                b.getLoaiBinh(),
                b.getSoLuong(),
                b.getGiaVonTrungBinh(),
                b.getGhiChu()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLoaiBinh = new javax.swing.JPanel();
        lblLoaiBinh = new javax.swing.JLabel();
        cboLoaiBinh = new javax.swing.JComboBox<>();
        pnlLoaiVan = new javax.swing.JPanel();
        lbLoaiVan = new javax.swing.JLabel();
        cboLoaiVan = new javax.swing.JComboBox<>();
        txtTimKiem = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnChiTiet = new javax.swing.JButton();
        btnXuatFile = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        scpHangHoa = new javax.swing.JScrollPane();
        tbleHangHoa = new javax.swing.JTable();
        lblQuanLyTaiKhoan = new javax.swing.JLabel();
        lblTimKiem = new javax.swing.JLabel();

        setForeground(new java.awt.Color(255, 255, 255));
        setRequestFocusEnabled(false);

        pnlLoaiBinh.setBackground(new java.awt.Color(255, 255, 255));
        pnlLoaiBinh.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlLoaiBinh.setName("pnlLoaiBinh"); // NOI18N

        lblLoaiBinh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLoaiBinh.setText("Loại bình");
        lblLoaiBinh.setName("txtLoaiBinh"); // NOI18N

        cboLoaiBinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLoaiBinh.setName("cboLoaiBinh"); // NOI18N

        javax.swing.GroupLayout pnlLoaiBinhLayout = new javax.swing.GroupLayout(pnlLoaiBinh);
        pnlLoaiBinh.setLayout(pnlLoaiBinhLayout);
        pnlLoaiBinhLayout.setHorizontalGroup(
            pnlLoaiBinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiBinhLayout.createSequentialGroup()
                .addGroup(pnlLoaiBinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLoaiBinhLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblLoaiBinh))
                    .addGroup(pnlLoaiBinhLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(cboLoaiBinh, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlLoaiBinhLayout.setVerticalGroup(
            pnlLoaiBinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoaiBinhLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(lblLoaiBinh)
                .addGap(18, 18, 18)
                .addComponent(cboLoaiBinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        pnlLoaiVan.setBackground(new java.awt.Color(255, 255, 255));
        pnlLoaiVan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlLoaiVan.setName("pnlLoaiVan"); // NOI18N

        lbLoaiVan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbLoaiVan.setText("Loại van");
        lbLoaiVan.setName("txtLoaiVan"); // NOI18N

        cboLoaiVan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLoaiVan.setName("cboLoaiVan"); // NOI18N

        javax.swing.GroupLayout pnlLoaiVanLayout = new javax.swing.GroupLayout(pnlLoaiVan);
        pnlLoaiVan.setLayout(pnlLoaiVanLayout);
        pnlLoaiVanLayout.setHorizontalGroup(
            pnlLoaiVanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiVanLayout.createSequentialGroup()
                .addGroup(pnlLoaiVanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLoaiVanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbLoaiVan))
                    .addGroup(pnlLoaiVanLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(cboLoaiVan, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        pnlLoaiVanLayout.setVerticalGroup(
            pnlLoaiVanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiVanLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(lbLoaiVan)
                .addGap(18, 18, 18)
                .addComponent(cboLoaiVan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        btnThem.setBackground(new java.awt.Color(0, 176, 80));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.setName("btnThem"); // NOI18N
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemMouseClicked(evt);
            }
        });

        btnChiTiet.setBackground(java.awt.Color.gray);
        btnChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChiTiet.setForeground(new java.awt.Color(255, 255, 255));
        btnChiTiet.setText("Chi tiết");
        btnChiTiet.setName("btnChiTiet"); // NOI18N
        btnChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChiTietMouseClicked(evt);
            }
        });
        btnChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiTietActionPerformed(evt);
            }
        });

        btnXuatFile.setBackground(new java.awt.Color(0, 176, 80));
        btnXuatFile.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXuatFile.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatFile.setText("Xuất file");
        btnXuatFile.setName("btnXuatFile"); // NOI18N
        btnXuatFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXuatFileMouseClicked(evt);
            }
        });
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(237, 28, 36));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.setName("btnXoa"); // NOI18N
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaMouseClicked(evt);
            }
        });

        tbleHangHoa.setModel(new javax.swing.table.DefaultTableModel(
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
        scpHangHoa.setViewportView(tbleHangHoa);

        lblQuanLyTaiKhoan.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblQuanLyTaiKhoan.setText("Hàng hóa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblQuanLyTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLoaiVan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLoaiBinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scpHangHoa, javax.swing.GroupLayout.DEFAULT_SIZE, 1230, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(btnChiTiet)
                        .addGap(18, 18, 18)
                        .addComponent(btnXuatFile)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa)
                        .addGap(35, 35, 35))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblQuanLyTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scpHangHoa, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(pnlLoaiBinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(pnlLoaiVan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        frmTest f = new frmTest();
        dlgChiTietHangHoa d = new dlgChiTietHangHoa(f, null, true, binhGasDAO);
        d.loadData();
        d.setVisible(true);

        loadData();
    }//GEN-LAST:event_btnThemMouseClicked

    private void btnChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChiTietMouseClicked
        if (selectedBinhGas == null) {
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 đối tượng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        frmTest f = new frmTest();
        dlgChiTietHangHoa d = new dlgChiTietHangHoa(f, this.selectedBinhGas, true, binhGasDAO);
        d.loadData();
        d.setVisible(true);

        loadData();
    }//GEN-LAST:event_btnChiTietMouseClicked

    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked
        if (selectedBinhGas == null) {
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 đối tượng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean isUsed = MyToys.isForeignKeyInUse(
                chiTietNhapHangDAO.getAll(),
                ct -> ct.getMaBinhGas().equals(selectedBinhGas.getMaBinhGas())
        );
        boolean isUsed1 = MyToys.isForeignKeyInUse(
                chiTietXuatHangDAO.getAll(),
                ct -> ct.getMaBinhGas().equals(selectedBinhGas.getMaBinhGas())
        );
        if (isUsed == true || isUsed1 == true) {
            JOptionPane.showMessageDialog(null, "Ma BinhGas đang được sử dụng là khóa ngoại.");
            return;
        }
        if (JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa bình gas này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {
            binhGasDAO.delete(selectedBinhGas.getMaBinhGas());
            JOptionPane.showMessageDialog(null, "Xoa binh gas thành công!");
            loadData();
        }
    }//GEN-LAST:event_btnXoaMouseClicked

    private void exportNhapToExcel() {
        // Thay dgvXuatHang bằng JTable của nhập hàng, ví dụ: dgvNhapHang
        DefaultTableModel model = (DefaultTableModel) tbleHangHoa.getModel();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu nhập hàng để xuất ra Excel.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel Hóa đơn Nhập"); // Đổi tiêu đề hộp thoại
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        // Đổi tên file gợi ý ban đầu
        fileChooser.setSelectedFile(new File("DanhSachHH_" + System.currentTimeMillis() + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }

            try {
                // Gọi phương thức xuất nhập hàng từ class ExcelExporter
                ExcelExporter.exportHoaDonXuatToExcel( // Đổi sang exportHoaDonNhapToExcel
                        tbleHangHoa, // JTable chứa dữ liệu nhập hàng
                        fileToSave.getAbsolutePath(), // Đường dẫn file sẽ lưu
                        "Danh Sách Binh Gas", // Tên sheet trong Excel
                        "DANH SÁCH BÌNH GAS" // Tiêu đề chính của báo cáo
                );
                JOptionPane.showMessageDialog(this, "Xuất file Excel Hóa đơn Nhập thành công!\n" + fileToSave.getAbsolutePath(), "Thành công", JOptionPane.INFORMATION_MESSAGE); // Đổi thông báo
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel Hóa đơn Nhập: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); // Đổi thông báo lỗi
                logger.log(Level.SEVERE, "Lỗi khi xuất file Excel Hóa đơn Nhập", ex); // Đổi thông báo log
            }
        }
    }
    private void btnXuatFileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXuatFileMouseClicked
        exportNhapToExcel();
    }//GEN-LAST:event_btnXuatFileMouseClicked

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatFileActionPerformed

    private void btnChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiTietActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnChiTietActionPerformed

    //VIẾT SỰ KIỆN SELECTION_CHANGED TRONG JTABLE
//    private void setupSelectionListener() {
//        ListSelectionModel selectionModel = tbleHangHoa.getSelectionModel();
//        selectionModel.addListSelectionListener(e -> {
//            if (!e.getValueIsAdjusting()) {
//                int selectedRow = tbleHangHoa.getSelectedRow();
//                selectedBinhGas = null;
//
//                if (selectedRow >= 0) {
//                    // Nếu bạn dùng danh sách bookList để đổ dữ liệu:
//                    selectedBinhGas = binhGasDAO.getAll().get(tbleHangHoa.convertRowIndexToModel(selectedRow));
//                    System.out.println("Da Chon sach: " + selectedBinhGas.getTenBinhGas());
//                }
//            }
//        });
//    }
    private void setupSelectionListener() {
        tbleHangHoa.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int selectedRow = tbleHangHoa.getSelectedRow();
                selectedBinhGas = null;

                if (selectedRow >= 0) {
                    int modelRow = tbleHangHoa.convertRowIndexToModel(selectedRow);
                    selectedBinhGas = danhSachBinhGas.get(modelRow);
                    System.out.println("Đã chọn bình gas: " + selectedBinhGas.getTenBinhGas());
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChiTiet;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JComboBox<String> cboLoaiBinh;
    private javax.swing.JComboBox<String> cboLoaiVan;
    private javax.swing.JLabel lbLoaiVan;
    private javax.swing.JLabel lblLoaiBinh;
    private javax.swing.JLabel lblQuanLyTaiKhoan;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JPanel pnlLoaiBinh;
    private javax.swing.JPanel pnlLoaiVan;
    private javax.swing.JScrollPane scpHangHoa;
    private javax.swing.JTable tbleHangHoa;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
