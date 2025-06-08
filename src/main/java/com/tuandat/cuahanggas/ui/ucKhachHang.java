/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.impl.KhachHangDAO;
import com.tuandat.cuahanggas.dao.impl.XuatHangDAO;
import com.tuandat.cuahanggas.model.ExcelExporter;
import com.tuandat.cuahanggas.model.KhachHang;
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

public class ucKhachHang extends javax.swing.JPanel {

    private KhachHangDAO khachHangDAO;
    private KhachHang selectedKhachHang = null;
    private static XuatHangDAO xuatHangDAO;
    List<KhachHang> danhSachKhachHang = null;
    private static final Logger logger = Logger.getLogger(ucKhachHang.class.getName());

    public ucKhachHang(KhachHangDAO khachHangDAO, XuatHangDAO xuatHangDAO) {
        this.khachHangDAO = khachHangDAO;
        this.xuatHangDAO = xuatHangDAO;

        initComponents();
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

    private void setupSelectionListener() {
        tbleKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int selectedRow = tbleKhachHang.getSelectedRow();
                selectedKhachHang = null;

                if (selectedRow >= 0) {
                    int modelRow = tbleKhachHang.convertRowIndexToModel(selectedRow);
                    selectedKhachHang = danhSachKhachHang.get(modelRow);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        lblQuanLyTaiKhoan = new javax.swing.JLabel();
        lblTimKiem = new javax.swing.JLabel();

        pnlGioiTinh.setBackground(new java.awt.Color(255, 255, 255));
        pnlGioiTinh.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlGioiTinh.setName("pnlGioiTinh"); // NOI18N

        lblGioiTinh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblGioiTinh.setText("Giới Tính");
        lblGioiTinh.setName("txtLoaiBinh"); // NOI18N

        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboGioiTinh.setName("cboGioiTinh"); // NOI18N

        javax.swing.GroupLayout pnlGioiTinhLayout = new javax.swing.GroupLayout(pnlGioiTinh);
        pnlGioiTinh.setLayout(pnlGioiTinhLayout);
        pnlGioiTinhLayout.setHorizontalGroup(
            pnlGioiTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGioiTinhLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnlGioiTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGioiTinh)
                    .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlGioiTinhLayout.setVerticalGroup(
            pnlGioiTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGioiTinhLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(lblGioiTinh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
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
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.setName("btnThem"); // NOI18N
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemMouseClicked(evt);
            }
        });
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
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

        lblQuanLyTaiKhoan.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblQuanLyTaiKhoan.setText("Khách hàng");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblQuanLyTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                    .addComponent(pnlGioiTinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
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
                        .addComponent(btnXoa))
                    .addComponent(scpKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 1168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(pnlGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblQuanLyTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)))
                        .addComponent(scpKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public void loadData() {

        try {
            danhSachKhachHang = khachHangDAO.getAll(); // Lấy tất cả bình gas từ DB
            System.out.println("Da lay duoc du lieu Binh Gas tu CSDL.");
        } catch (Exception e) {
            System.err.println("Loi khi lay du lieu Binh Gas tu CSDL: " + e.getMessage());
            e.printStackTrace();
            return; // Dừng nếu không thể lấy dữ liệu
        }

        tbleKhachHang.setModel(MyToys.listToTableModel(danhSachKhachHang));
        String[] columnNames = {"Mã Khách Hàng", "Tên Khách Hàng", "Giới tính", "Ngày sinh", "Số Điện Thoại", "Gmail", "Địa Chỉ", "Ghi Chú"}; // Adjust as per your KhachHang fields

        // Iterate through the table's column model and set the header values
        for (int i = 0; i < columnNames.length; i++) {
            // Ensure we don't go out of bounds if MyToys.listToTableModel returns fewer columns
            if (i < tbleKhachHang.getColumnModel().getColumnCount()) {
                tbleKhachHang.getColumnModel().getColumn(i).setHeaderValue(columnNames[i]);
            }
        }
        Set<String> distinctGioiTinh = danhSachKhachHang.stream()
                .map(KhachHang::getGioiTinh)
                .filter(loai -> loai != null && !loai.trim().isEmpty()) // Lọc bỏ null hoặc rỗng
                .collect(Collectors.toSet());

        // Fill cboLoaiVan
        DefaultComboBoxModel<String> loaiVanModel = new DefaultComboBoxModel<>();
        loaiVanModel.addElement("Tất cả"); // Thêm tùy chọn "Tất cả"
        // Thêm các giá trị duy nhất từ Set vào model
        distinctGioiTinh.forEach(loaiVanModel::addElement);
        cboGioiTinh.setModel(loaiVanModel);
        System.out.println("Du lieu Loai Van da duoc tai vao ComboBox.");

        selectedKhachHang = null;
    }

    private void filterTable() {
        String selectedLoaiVan = (String) cboGioiTinh.getSelectedItem();
        String keyword = txtTimKiem.getText().trim().toLowerCase();

        List<KhachHang> allData = khachHangDAO.getAll();

        List<KhachHang> filtered = allData.stream()
                .filter(b -> "Tất cả".equals(selectedLoaiVan) || b.getGioiTinh().equals(selectedLoaiVan))
                .filter(b -> b.getMaKhachHang().toLowerCase().contains(keyword)
                || b.getHoTen().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        fillTable(filtered);
    }

    private void fillTable(List<KhachHang> list) {
        DefaultTableModel model = (DefaultTableModel) tbleKhachHang.getModel();
        model.setRowCount(0); // clear old data

        for (KhachHang b : list) {
            model.addRow(new Object[]{
                b.getMaKhachHang(),
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


    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        frmTest f = new frmTest();
        dlgChiTietKhachHang d = new dlgChiTietKhachHang(f, null, true, khachHangDAO);
        d.loadData();
        d.setVisible(true);

        loadData();
    }//GEN-LAST:event_btnThemMouseClicked

    private void btnChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChiTietMouseClicked
        if (selectedKhachHang == null) {
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 đối tượng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        frmTest f = new frmTest();
        dlgChiTietKhachHang d = new dlgChiTietKhachHang(f, this.selectedKhachHang, true, khachHangDAO);
        d.loadData();
        d.setVisible(true);

        loadData();
    }//GEN-LAST:event_btnChiTietMouseClicked

    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked
        if (selectedKhachHang == null) {
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 đối tượng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean isUsed = MyToys.isForeignKeyInUse(
                xuatHangDAO.getAll(),
                ct -> ct.getMaKhachHang().equals(selectedKhachHang.getMaKhachHang())
        );
        if (isUsed) {
            JOptionPane.showMessageDialog(null, "Ma Khách hàng này đang được sử dụng là khóa ngoại.");
            return;
        }
        if (JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa khách hàng này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {
            khachHangDAO.delete(selectedKhachHang.getMaKhachHang());
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
        fileChooser.setSelectedFile(new File("DanhSachKH_" + System.currentTimeMillis() + ".xlsx"));

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
                        "Danh Sách Khách Hàng", // Tên sheet trong Excel
                        "DANH SÁCH KHÁCH HÀNG" // Tiêu đề chính của báo cáo
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

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChiTiet;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblQuanLyTaiKhoan;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JPanel pnlGioiTinh;
    private javax.swing.JScrollPane scpKhachHang;
    private javax.swing.JTable tbleKhachHang;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
