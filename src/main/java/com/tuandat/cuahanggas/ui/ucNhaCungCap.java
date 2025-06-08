/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.impl.NhaCungCapDAO;
import com.tuandat.cuahanggas.dao.impl.NhapHangDAO;
import com.tuandat.cuahanggas.model.ExcelExporter;
import com.tuandat.cuahanggas.model.NhaCungCap;
import com.tuandat.cuahanggas.utils.MyToys;
import java.awt.Image;
import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
public class ucNhaCungCap extends javax.swing.JPanel {

    private NhaCungCapDAO nhaCungCapDAO;
    private NhaCungCap selectedNhaCungCap = null;
    private static NhapHangDAO nhapHangDAO;
    List<NhaCungCap> danhSachCaps = null;
    private static final Logger logger = Logger.getLogger(ucNhaCungCap.class.getName());

    /**
     * Creates new form ucHangHoa
     *
     * @param binhGasDAO
     */
    public ucNhaCungCap(NhaCungCapDAO nhaCungCapDAO, NhapHangDAO nhapHangDAO) {
        this.nhaCungCapDAO = nhaCungCapDAO;
        this.nhapHangDAO = nhapHangDAO;
        try {
            initComponents();
//        ImageIcon icon = new ImageIcon("Logo.png"); // Đường dẫn đến ảnh
//        JLabel label = new JLabel("Text", icon, JLabel.LEFT);

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
            danhSachCaps = nhaCungCapDAO.getAll(); // Lấy tất cả bình gas từ DB
            System.out.println("Da lay duoc du lieu NCC tu CSDL.");
        } catch (Exception e) {
            System.err.println("Loi khi lay du lieu Binh Gas tu CSDL: " + e.getMessage());
            e.printStackTrace();
            return; // Dừng nếu không thể lấy dữ liệu
        }

        tbleHangHoa.setModel(MyToys.listToTableModel(danhSachCaps));
         String[] columnNames = {"Mã Nhà Cung Cấp", "Tên Nhà Cung Cấp", "Số Điện Thoại", "Email", "Địa Chỉ", "Ghi Chú"};
    for (int i = 0; i < columnNames.length; i++) {
        if (i < tbleHangHoa.getColumnModel().getColumnCount()) {
            tbleHangHoa.getColumnModel().getColumn(i).setHeaderValue(columnNames[i]);
        }
    }
        selectedNhaCungCap = null;
    }

    private void filterTable() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();

        List<NhaCungCap> allData = nhaCungCapDAO.getAll();

        List<NhaCungCap> filtered = allData.stream()
                .filter(b -> b.getMaNhaCungCap().toLowerCase().contains(keyword)
                || b.getTenNhaCungCap().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        fillTable(filtered);
    }


    private void fillTable(List<NhaCungCap> list) {
        DefaultTableModel model = (DefaultTableModel) tbleHangHoa.getModel();
        model.setRowCount(0); // clear old data

        for (NhaCungCap b : list) {
            model.addRow(new Object[]{
                b.getMaNhaCungCap(),
                b.getTenNhaCungCap(),
                b.getDiaChi(),
                b.getEmail(),
                b.getGhiChu(),
                b.getSdt(),
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtTimKiem = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnChiTiet = new javax.swing.JButton();
        btnXuatFile = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        scpHangHoa = new javax.swing.JScrollPane();
        tbleHangHoa = new javax.swing.JTable();
        lblQuanLyTaiKhoan = new javax.swing.JLabel();

        setRequestFocusEnabled(false);

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

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

        btnChiTiet.setBackground(new java.awt.Color(102, 102, 102));
        btnChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChiTiet.setForeground(new java.awt.Color(255, 255, 255));
        btnChiTiet.setText("Chi Tiết");
        btnChiTiet.setName("btnChiTiet"); // NOI18N
        btnChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChiTietMouseClicked(evt);
            }
        });

        btnXuatFile.setBackground(new java.awt.Color(0, 176, 80));
        btnXuatFile.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXuatFile.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatFile.setText("Xuất File");
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
        lblQuanLyTaiKhoan.setText("Nhà cung cấp");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblQuanLyTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(btnChiTiet)
                        .addGap(18, 18, 18)
                        .addComponent(btnXuatFile)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa))
                    .addComponent(scpHangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 1164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQuanLyTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(scpHangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        frmTest f = new frmTest();
        dlgChiTietNhaCungCap d = new dlgChiTietNhaCungCap(f, null, true, nhaCungCapDAO);
        d.loadData();
        d.setVisible(true);

        loadData();
    }//GEN-LAST:event_btnThemMouseClicked

    private void btnChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChiTietMouseClicked
        if (selectedNhaCungCap == null) {
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 NCC đối tượng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        frmTest f = new frmTest();
        dlgChiTietNhaCungCap d = new dlgChiTietNhaCungCap(f, this.selectedNhaCungCap, true, nhaCungCapDAO);
        d.loadData();
        d.setVisible(true);

        loadData();
    }//GEN-LAST:event_btnChiTietMouseClicked

    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked
        if (selectedNhaCungCap == null) {
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 NCC đối tượng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean isUsed = MyToys.isForeignKeyInUse(
                nhapHangDAO.getAll(),
                ct -> ct.getMaNhaCungCap().equals(selectedNhaCungCap.getMaNhaCungCap())
        );
        if (isUsed) {
            JOptionPane.showMessageDialog(null, "Ma NCC đang được sử dụng là khóa ngoại.");
            return;
        }
        if (JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa NCC này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {
            nhaCungCapDAO.delete(selectedNhaCungCap.getMaNhaCungCap());
            JOptionPane.showMessageDialog(null, "Xoa NCC thành công!");
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
        fileChooser.setSelectedFile(new File("DanhSachNCC_" + System.currentTimeMillis() + ".xlsx"));

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
                        "Danh Sách Nhà Cung cấp", // Tên sheet trong Excel
                        "DANH SÁCH NHÀ CUNG CẤP" // Tiêu đề chính của báo cáo
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

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

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
                selectedNhaCungCap = null;

                if (selectedRow >= 0) {
                    int modelRow = tbleHangHoa.convertRowIndexToModel(selectedRow);
                    selectedNhaCungCap = danhSachCaps.get(modelRow);
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChiTiet;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JLabel lblQuanLyTaiKhoan;
    private javax.swing.JScrollPane scpHangHoa;
    private javax.swing.JTable tbleHangHoa;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
