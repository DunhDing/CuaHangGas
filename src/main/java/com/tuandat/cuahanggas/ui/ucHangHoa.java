/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.impl.BinhGasDAO;
import com.tuandat.cuahanggas.model.BinhGas;
import com.tuandat.cuahanggas.utils.MyToys;
import java.awt.Image;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class ucHangHoa extends javax.swing.JPanel {

    private final BinhGasDAO binhGasDAO;
    private BinhGas selectedBinhGas = null;
    List<BinhGas> danhSachBinhGas = null;

    /**
     * Creates new form ucHangHoa
     *
     * @param binhGasDAO
     */
    public ucHangHoa(BinhGasDAO binhGasDAO) {
        this.binhGasDAO = binhGasDAO;

        initComponents();
//        ImageIcon icon = new ImageIcon("Logo.png"); // Đường dẫn đến ảnh
//        JLabel label = new JLabel("Text", icon, JLabel.LEFT);

        ImageIcon iconThem = new ImageIcon(getClass().getResource("/plus-solid.png"));
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

        // --- Đổ dữ liệu vào tbleHangHoa ---
//        String[] columnNames = {"Mã Bình Gas", "Tên Bình Gas", "Loại Bình", "Loại Van", "Số Lượng", "Giá Vốn TB", "Ghi Chú"};
//        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false; // Không cho phép sửa trực tiếp trên bảng
//            }
//        };
//
//        for (BinhGas bg : danhSachBinhGas) {
//            Vector<Object> row = new Vector<>();
//            row.add(bg.getMaBinhGas());
//            row.add(bg.getTenBinhGas());
//            row.add(bg.getLoaiBinh());
//            row.add(bg.getLoaiVan());
//            row.add(bg.getSoLuong());
//            row.add(bg.getGiaVonTrungBinh());
//            row.add(bg.getGhiChu());
//            tableModel.addRow(row);
//        }
//        tbleHangHoa.setModel(tableModel);
//        System.out.println("Du lieu Binh Gas đã được tai vao bang.");
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHangHoa = new javax.swing.JPanel();
        lblHangHoa = new javax.swing.JLabel();
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

        setRequestFocusEnabled(false);

        pnlHangHoa.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlHangHoa.setName("pnlHeader"); // NOI18N

        lblHangHoa.setText("Hàng Hóa");

        javax.swing.GroupLayout pnlHangHoaLayout = new javax.swing.GroupLayout(pnlHangHoa);
        pnlHangHoa.setLayout(pnlHangHoaLayout);
        pnlHangHoaLayout.setHorizontalGroup(
            pnlHangHoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHangHoaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHangHoa)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        pnlHangHoaLayout.setVerticalGroup(
            pnlHangHoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHangHoaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblHangHoa)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pnlLoaiBinh.setBackground(new java.awt.Color(255, 255, 255));
        pnlLoaiBinh.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlLoaiBinh.setName("pnlLoaiBinh"); // NOI18N

        lblLoaiBinh.setText("Loại Bình");
        lblLoaiBinh.setName("txtLoaiBinh"); // NOI18N

        cboLoaiBinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLoaiBinh.setName("cboLoaiBinh"); // NOI18N

        javax.swing.GroupLayout pnlLoaiBinhLayout = new javax.swing.GroupLayout(pnlLoaiBinh);
        pnlLoaiBinh.setLayout(pnlLoaiBinhLayout);
        pnlLoaiBinhLayout.setHorizontalGroup(
            pnlLoaiBinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiBinhLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlLoaiBinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboLoaiBinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLoaiBinh))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlLoaiBinhLayout.setVerticalGroup(
            pnlLoaiBinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiBinhLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLoaiBinh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboLoaiBinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlLoaiVan.setBackground(new java.awt.Color(255, 255, 255));
        pnlLoaiVan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlLoaiVan.setName("pnlLoaiVan"); // NOI18N

        lbLoaiVan.setText("Loại Van");
        lbLoaiVan.setName("txtLoaiVan"); // NOI18N

        cboLoaiVan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLoaiVan.setName("cboLoaiVan"); // NOI18N

        javax.swing.GroupLayout pnlLoaiVanLayout = new javax.swing.GroupLayout(pnlLoaiVan);
        pnlLoaiVan.setLayout(pnlLoaiVanLayout);
        pnlLoaiVanLayout.setHorizontalGroup(
            pnlLoaiVanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiVanLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlLoaiVanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboLoaiVan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbLoaiVan))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        pnlLoaiVanLayout.setVerticalGroup(
            pnlLoaiVanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiVanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbLoaiVan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboLoaiVan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtTimKiem.setText("tìm kiếm");

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

        btnXoa.setBackground(new java.awt.Color(237, 28, 36));
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlHangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlLoaiBinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLoaiVan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChiTiet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXuatFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa)
                        .addGap(96, 96, 96))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scpHangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(92, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlHangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlLoaiBinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(pnlLoaiVan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scpHangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
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
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 đối tượng" , "Lỗi", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 đối tượng" , "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn thoát không?",
                "Xác nhận thoát",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {
            binhGasDAO.delete(selectedBinhGas.getMaBinhGas());
            JOptionPane.showMessageDialog(null, "Xoa binh gas thành công!");
            loadData();
        }
    }//GEN-LAST:event_btnXoaMouseClicked

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
    private javax.swing.JLabel lblHangHoa;
    private javax.swing.JLabel lblLoaiBinh;
    private javax.swing.JPanel pnlHangHoa;
    private javax.swing.JPanel pnlLoaiBinh;
    private javax.swing.JPanel pnlLoaiVan;
    private javax.swing.JScrollPane scpHangHoa;
    private javax.swing.JTable tbleHangHoa;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
