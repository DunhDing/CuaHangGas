/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.impl.NhanVienDAO;
import com.tuandat.cuahanggas.dao.impl.NhapHangDAO;
import com.tuandat.cuahanggas.dao.impl.XuatHangDAO;
import com.tuandat.cuahanggas.model.ExcelExporter;
import com.tuandat.cuahanggas.model.NhanVien;
import com.tuandat.cuahanggas.utils.MyToys;
import java.awt.Image;
import java.io.File;
import java.util.List;
import java.util.Set;
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
public class ucNhanVien extends javax.swing.JPanel {
    
    private static final Logger logger = Logger.getLogger(ucNhanVien.class.getName());

    private NhanVienDAO nhanVienDAO;
    private NhanVien selectedNhanVien = null;
    private static NhapHangDAO nhapHangDAO;
    private static XuatHangDAO xuatHangDAO;
    List<NhanVien> danhSachNhanVien = null;

    public ucNhanVien(NhanVienDAO nhanVienDAO, NhapHangDAO nhapHangDAO, XuatHangDAO xuatHangDAO) {
        this.nhanVienDAO = nhanVienDAO;
        this.nhapHangDAO = nhapHangDAO;
        this.xuatHangDAO = xuatHangDAO;
        initComponents();

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
        setupListeners();
    }

    private void setupListeners() {
        cboGioiTinh.addActionListener(e -> filterTable());
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
    }

    public void loadData() {

        try {
            danhSachNhanVien = nhanVienDAO.getAll(); // Lấy tất cả bình gas từ DB
            System.out.println("Da lay duoc du lieu Nhan vien tu CSDL.");
        } catch (Exception e) {
            System.err.println("Loi khi lay du lieu Nhan Vien tu CSDL: " + e.getMessage());
            e.printStackTrace();
            return; // Dừng nếu không thể lấy dữ liệu
        }

        tbleKhachHang.setModel(MyToys.listToTableModel(danhSachNhanVien));
        Set<String> distinctGioiTinh = danhSachNhanVien.stream()
                .map(NhanVien::getGioiTinh)
                .filter(loai -> loai != null && !loai.trim().isEmpty()) // Lọc bỏ null hoặc rỗng
                .collect(Collectors.toSet());

        // Fill cboLoaiVan
        DefaultComboBoxModel<String> loaiVanModel = new DefaultComboBoxModel<>();
        loaiVanModel.addElement("Tất cả"); // Thêm tùy chọn "Tất cả"
        // Thêm các giá trị duy nhất từ Set vào model
        distinctGioiTinh.forEach(loaiVanModel::addElement);
        cboGioiTinh.setModel(loaiVanModel);
        System.out.println("Du lieu Gioi Tinh da duoc tai vao ComboBox.");

        selectedNhanVien = null;
    }

    private void filterTable() {
        try {
            String selectedGioiTinh = (String) cboGioiTinh.getSelectedItem();
            String keyword = txtTimKiem.getText().trim().toLowerCase();

            List<NhanVien> allData = danhSachNhanVien;

            List<NhanVien> filtered = allData.stream()
                    .filter(b -> "Tất cả".equals(selectedGioiTinh) || selectedGioiTinh.equals(b.getGioiTinh()))
                    .filter(b -> b.getMaNhanVien().toLowerCase().contains(keyword)
                    || b.getHoTen().toLowerCase().contains(keyword))
                    .collect(Collectors.toList());

            fillTable(filtered);
        } catch (Exception ex) {
            ex.printStackTrace(); // Hoặc log lỗi
        }
    }

    private void fillTable(List<NhanVien> list) {
        DefaultTableModel model = (DefaultTableModel) tbleKhachHang.getModel();
        model.setRowCount(0); // clear old data

        for (NhanVien b : list) {
            model.addRow(new Object[]{
                b.getMaNhanVien(),
                b.getHoTen(),
                b.getDiaChi(),
                b.getEmail(),
                b.getGhiChu(),
                b.getGioiTinh(),
                b.getNgaySinh(),
                b.getSdt()
            });
        }
    }

    private void setupSelectionListener() {
        tbleKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int selectedRow = tbleKhachHang.getSelectedRow();
                selectedNhanVien = null;

                if (selectedRow >= 0) {
                    int modelRow = tbleKhachHang.convertRowIndexToModel(selectedRow);
                    selectedNhanVien = danhSachNhanVien.get(modelRow);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlKhachHang = new javax.swing.JPanel();
        lblKhachHang = new javax.swing.JLabel();
        pnlGioiTinh = new javax.swing.JPanel();
        lblGioiTinh = new javax.swing.JLabel();
        cboGioiTinh = new javax.swing.JComboBox<>();
        txtTimKiem = new javax.swing.JTextField();
        scpKhachHang = new javax.swing.JScrollPane();
        tbleKhachHang = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnChiTiet = new javax.swing.JButton();
        btnXuatFile = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();

        pnlKhachHang.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlKhachHang.setName("pnlHeader"); // NOI18N

        lblKhachHang.setText("Nhân Viên");

        javax.swing.GroupLayout pnlKhachHangLayout = new javax.swing.GroupLayout(pnlKhachHang);
        pnlKhachHang.setLayout(pnlKhachHangLayout);
        pnlKhachHangLayout.setHorizontalGroup(
            pnlKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblKhachHang)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        pnlKhachHangLayout.setVerticalGroup(
            pnlKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKhachHangLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblKhachHang)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pnlGioiTinh.setBackground(new java.awt.Color(255, 255, 255));
        pnlGioiTinh.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlGioiTinh.setName("pnlGioiTinh"); // NOI18N

        lblGioiTinh.setText("Giới Tính");
        lblGioiTinh.setName("txtLoaiBinh"); // NOI18N

        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboGioiTinh.setName("cboGioiTinh"); // NOI18N

        javax.swing.GroupLayout pnlGioiTinhLayout = new javax.swing.GroupLayout(pnlGioiTinh);
        pnlGioiTinh.setLayout(pnlGioiTinhLayout);
        pnlGioiTinhLayout.setHorizontalGroup(
            pnlGioiTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGioiTinhLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlGioiTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGioiTinh))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        pnlGioiTinhLayout.setVerticalGroup(
            pnlGioiTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGioiTinhLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblGioiTinh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbleKhachHang.setModel(new javax.swing.table.DefaultTableModel(
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
        scpKhachHang.setViewportView(tbleKhachHang);

        btnThem.setBackground(new java.awt.Color(0, 176, 80));
        btnThem.setText("Thêm");
        btnThem.setName("btnThem"); // NOI18N
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemMouseClicked(evt);
            }
        });

        btnChiTiet.setBackground(new java.awt.Color(102, 102, 102));
        btnChiTiet.setText("Chi Tiết");
        btnChiTiet.setName("btnChiTiet"); // NOI18N
        btnChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChiTietMouseClicked(evt);
            }
        });

        btnXuatFile.setBackground(new java.awt.Color(0, 176, 80));
        btnXuatFile.setText("Xuất File");
        btnXuatFile.setName("btnXuatFile"); // NOI18N
        btnXuatFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXuatFileMouseClicked(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(237, 28, 36));
        btnXoa.setText("Xóa");
        btnXoa.setName("btnXoa"); // NOI18N
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(scpKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 1258, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(btnChiTiet)
                        .addGap(18, 18, 18)
                        .addComponent(btnXuatFile)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa)
                        .addGap(51, 51, 51))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(pnlGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 278, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scpKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        frmTest f = new frmTest();
        dlgChiTietNhanVien d = new dlgChiTietNhanVien(f, null, true, nhanVienDAO);
        d.loadData();
        d.setVisible(true);

        loadData();
    }//GEN-LAST:event_btnThemMouseClicked

    private void btnChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChiTietMouseClicked
        if (selectedNhanVien == null) {
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 đối tượng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        frmTest f = new frmTest();
        dlgChiTietNhanVien d = new dlgChiTietNhanVien(f, this.selectedNhanVien, true, nhanVienDAO);
        d.loadData();
        d.setVisible(true);

        loadData();
    }//GEN-LAST:event_btnChiTietMouseClicked

    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked
        if (selectedNhanVien == null) {
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 nhan vien", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean isUsed = MyToys.isForeignKeyInUse(
                nhapHangDAO.getAll(),
                ct -> ct.getMaNhanVien().equals(selectedNhanVien.getMaNhanVien())
        );
        boolean isUsed1 = MyToys.isForeignKeyInUse(
                xuatHangDAO.getAll(),
                ct -> ct.getMaNhanVien().equals(selectedNhanVien.getMaNhanVien())
        );
        if (isUsed == true || isUsed1 == true) {
            JOptionPane.showMessageDialog(null, "Ma Nhan Vien này đang được sử dụng là khóa ngoại.");
            return;
        }
        if (JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa nhan vien này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {
            nhanVienDAO.delete(selectedNhanVien.getMaNhanVien());
            JOptionPane.showMessageDialog(null, "Xoa khách hàng thành công!");
            loadData();
        }
    }//GEN-LAST:event_btnXoaMouseClicked

    private void exportNhapToExcel() {
        // Thay dgvXuatHang bằng JTable của nhập hàng, ví dụ: dgvNhapHang
        DefaultTableModel model = (DefaultTableModel) tbleKhachHang.getModel();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu nhập hàng để xuất ra Excel.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel Hóa đơn Nhập"); // Đổi tiêu đề hộp thoại
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        // Đổi tên file gợi ý ban đầu
        fileChooser.setSelectedFile(new File("DanhSachNV_" + System.currentTimeMillis() + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }

            try {
                // Gọi phương thức xuất nhập hàng từ class ExcelExporter
                ExcelExporter.exportHoaDonXuatToExcel( // Đổi sang exportHoaDonNhapToExcel
                        tbleKhachHang, // JTable chứa dữ liệu nhập hàng
                        fileToSave.getAbsolutePath(), // Đường dẫn file sẽ lưu
                        "Danh Sách Nhân Viên", // Tên sheet trong Excel
                        "DANH SÁCH NHÂN VIÊN" // Tiêu đề chính của báo cáo
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChiTiet;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblKhachHang;
    private javax.swing.JPanel pnlGioiTinh;
    private javax.swing.JPanel pnlKhachHang;
    private javax.swing.JScrollPane scpKhachHang;
    private javax.swing.JTable tbleKhachHang;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
