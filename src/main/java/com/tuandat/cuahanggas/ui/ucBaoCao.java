/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandat.cuahanggas.ui;

import com.tuandat.cuahanggas.dao.DAO;
import com.tuandat.cuahanggas.model.ExcelExporter;
import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class ucBaoCao extends javax.swing.JPanel {

    private Connection conn;
    private static final Logger logger = Logger.getLogger(ucHangHoa.class.getName());

    public ucBaoCao(Connection conn) {
        this.conn = conn;
        initComponents();

        cboDoiTuong.addActionListener(e -> filterForDoiTuong());

    }

    public void loadData() {
        cboDoiTuong.removeAllItems();
        cboDoiTuong.addItem("Chọn...");
        cboDoiTuong.addItem("Bình gas");
        cboDoiTuong.addItem("Nhà Cung Cấp");
        cboDoiTuong.addItem("Nhân Viên");
        cboDoiTuong.addItem("Khách Hàng");

        cboThoiGian.removeAllItems();
        cboThoiGian.addItem("Không lọc");
        cboThoiGian.addItem("Theo ngày");
        cboThoiGian.addItem("Theo tháng");
        cboThoiGian.addItem("Theo năm");

        cboDanhMucTheoBinhGas.setEnabled(false);
        btnXuatFile.setEnabled(false);
        btnXuatBaoCao.setEnabled(false);

        dcrTGBD.setDate(new Date());
        dcrTGKT.setDate(new Date());
    }

    private void filterForDoiTuong() {
        //btnXuatBaoCao.setEnabled(true);
        if (cboDoiTuong.getSelectedIndex() == 1) {
            cboDanhMucTheoBinhGas.setEnabled(true);
            cboDanhMucTheoBinhGas.removeAllItems();
            cboDanhMucTheoBinhGas.addItem("Doanh số Bình Gas");
            cboDanhMucTheoBinhGas.addItem("Doanh thu & Lợi nhuận");
            cboDanhMucTheoBinhGas.addItem("Giá trị kho");
            cboDanhMucTheoBinhGas.addItem("Số lượng & Giá trị nhập hàng");

        } else {
            cboDanhMucTheoBinhGas.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHangHoa = new javax.swing.JPanel();
        lblHangHoa = new javax.swing.JLabel();
        pnlDanhMucBaoCao = new javax.swing.JPanel();
        lblLoaiBinh = new javax.swing.JLabel();
        cboDoiTuong = new javax.swing.JComboBox<>();
        cboDanhMucTheoBinhGas = new javax.swing.JComboBox<>();
        btnXuatBaoCao = new javax.swing.JButton();
        btnXuatFile = new javax.swing.JButton();
        scpHangHoa = new javax.swing.JScrollPane();
        tbleBaoCao = new javax.swing.JTable();
        pnlThoiGian = new javax.swing.JPanel();
        dcrTGBD = new com.toedter.calendar.JDateChooser();
        dcrTGKT = new com.toedter.calendar.JDateChooser();
        cboThoiGian = new javax.swing.JComboBox<>();
        lblThoiGian = new javax.swing.JLabel();
        lblTu = new javax.swing.JLabel();
        lblDen = new javax.swing.JLabel();

        pnlHangHoa.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlHangHoa.setName("pnlHeader"); // NOI18N

        lblHangHoa.setText("Báo Cáo");

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

        pnlDanhMucBaoCao.setBackground(new java.awt.Color(255, 255, 255));
        pnlDanhMucBaoCao.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlDanhMucBaoCao.setName("pnlDanhMucBaoCao"); // NOI18N

        lblLoaiBinh.setText("Danh Mục Báo Cáo");
        lblLoaiBinh.setName("txtLoaiBinh"); // NOI18N

        cboDoiTuong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboDoiTuong.setName("cboDoiTuong"); // NOI18N

        cboDanhMucTheoBinhGas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboDanhMucTheoBinhGas.setName("cboLoaiBinh"); // NOI18N

        javax.swing.GroupLayout pnlDanhMucBaoCaoLayout = new javax.swing.GroupLayout(pnlDanhMucBaoCao);
        pnlDanhMucBaoCao.setLayout(pnlDanhMucBaoCaoLayout);
        pnlDanhMucBaoCaoLayout.setHorizontalGroup(
            pnlDanhMucBaoCaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhMucBaoCaoLayout.createSequentialGroup()
                .addGroup(pnlDanhMucBaoCaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDanhMucBaoCaoLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlDanhMucBaoCaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboDoiTuong, 0, 150, Short.MAX_VALUE)
                            .addComponent(cboDanhMucTheoBinhGas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlDanhMucBaoCaoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblLoaiBinh)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDanhMucBaoCaoLayout.setVerticalGroup(
            pnlDanhMucBaoCaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhMucBaoCaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLoaiBinh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboDoiTuong, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboDanhMucTheoBinhGas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        btnXuatBaoCao.setBackground(new java.awt.Color(0, 255, 0));
        btnXuatBaoCao.setText("Xuất Báo Cáo");
        btnXuatBaoCao.setName("btnXuatBaoCao"); // NOI18N
        btnXuatBaoCao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXuatBaoCaoMouseClicked(evt);
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

        tbleBaoCao.setModel(new javax.swing.table.DefaultTableModel(
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
        scpHangHoa.setViewportView(tbleBaoCao);

        pnlThoiGian.setBackground(new java.awt.Color(255, 255, 255));

        cboThoiGian.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboThoiGian.setName("cboLoaiBinh"); // NOI18N

        lblThoiGian.setText("Thời Gian");
        lblThoiGian.setName("txtLoaiBinh"); // NOI18N

        lblTu.setText("Từ");
        lblTu.setName("txtLoaiBinh"); // NOI18N

        lblDen.setText("Đến");
        lblDen.setName("txtLoaiBinh"); // NOI18N

        javax.swing.GroupLayout pnlThoiGianLayout = new javax.swing.GroupLayout(pnlThoiGian);
        pnlThoiGian.setLayout(pnlThoiGianLayout);
        pnlThoiGianLayout.setHorizontalGroup(
            pnlThoiGianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThoiGianLayout.createSequentialGroup()
                .addGroup(pnlThoiGianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThoiGianLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(pnlThoiGianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTu)
                            .addGroup(pnlThoiGianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cboThoiGian, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dcrTGKT, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                                .addComponent(dcrTGBD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblDen)))
                    .addGroup(pnlThoiGianLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblThoiGian)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        pnlThoiGianLayout.setVerticalGroup(
            pnlThoiGianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThoiGianLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblThoiGian)
                .addGap(18, 18, 18)
                .addComponent(lblTu)
                .addGap(2, 2, 2)
                .addComponent(dcrTGBD, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcrTGKT, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlDanhMucBaoCao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlHangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlThoiGian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(scpHangHoa, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(247, 247, 247)
                        .addComponent(btnXuatBaoCao)
                        .addGap(28, 28, 28)
                        .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlHangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnXuatBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpHangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlDanhMucBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnXuatBaoCaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXuatBaoCaoMouseClicked
        btnXuatFile.setEnabled(false);

        boolean canShowButtons = false;

        if (cboDoiTuong.getSelectedIndex() != 0) {
            if (cboDoiTuong.getSelectedItem().toString().equals("Bình gas")) {
                canShowButtons = cboDanhMucTheoBinhGas.isVisible() && cboDanhMucTheoBinhGas.getSelectedItem() != null;
            } else {
                canShowButtons = true;
            }
        }

        if (!canShowButtons) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ mục báo cáo!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Kết nối Database
        String sql = "";

        // --- Lọc thời gian xuất hàng ---
        Date fromDate = dcrTGBD.getDate();
        Date toDate = dcrTGKT.getDate();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String filterSql = "";
        String filterSqlNhap = "";

        if (cboThoiGian.getSelectedItem() != null) {
            String selectedFilter = cboThoiGian.getSelectedItem().toString();
            Calendar cal = Calendar.getInstance();
            switch (selectedFilter) {
                case "Theo ngày":
                    filterSql = "AND CAST(xh.NgayXuat AS DATE) BETWEEN '" + sdf.format(fromDate) + "' AND '" + sdf.format(toDate) + "'";
                    filterSqlNhap = "AND CAST(nh.NgayNhap AS DATE) BETWEEN '" + sdf.format(fromDate) + "' AND '" + sdf.format(toDate) + "'";
                    break;
                case "Theo tháng":

                    cal.setTime(fromDate);
                    int fromYM = cal.get(Calendar.YEAR) * 100 + cal.get(Calendar.MONTH) + 1;

                    cal.setTime(toDate);
                    int toYM = cal.get(Calendar.YEAR) * 100 + cal.get(Calendar.MONTH) + 1;

                    filterSql = "AND (YEAR(xh.NgayXuat) * 100 + MONTH(xh.NgayXuat)) BETWEEN " + fromYM + " AND " + toYM;
                    filterSqlNhap = "AND (YEAR(nh.NgayNhap) * 100 + MONTH(nh.NgayNhap)) BETWEEN " + fromYM + " AND " + toYM;
                    break;
                case "Theo năm":
                    cal.setTime(fromDate);
                    int fromYear = cal.get(Calendar.YEAR);

                    cal.setTime(toDate);
                    int toYear = cal.get(Calendar.YEAR);

                    filterSql = "AND YEAR(xh.NgayXuat) BETWEEN " + fromYear + " AND " + toYear;
                    filterSqlNhap = "AND YEAR(nh.NgayNhap) BETWEEN " + fromYear + " AND " + toYear;
                    break;
            }
        }
        // --- Xử lý truy vấn theo lựa chọn ---
        String selectedReport = cboDoiTuong.getSelectedItem().toString();
        String selectedDetail = cboDanhMucTheoBinhGas.getSelectedItem().toString();//cboDoiTuong.getSelectedIndex() != 1 ? cboDanhMucTheoBinhGas.getSelectedItem().toString() : "";

        if (selectedReport.equals("Bình gas")) {
            if (cboDanhMucTheoBinhGas.isVisible() && !selectedDetail.isEmpty()) {
                switch (selectedDetail) {
                    case "Doanh số Bình Gas":
                        sql = "SELECT bg.MaBinhGas, bg.TenBinhGas, bg.LoaiBinh, bg.LoaiVan, "
                                + "SUM(ctxh.SoLuongXuat) AS SoLuongXuat, "
                                + "MAX(xh.NgayXuat) AS NgayCuoi "
                                + "FROM ChiTietXuatHang ctxh "
                                + "JOIN BinhGas bg ON ctxh.MaBinhGas = bg.MaBinhGas "
                                + "JOIN XuatHang xh ON ctxh.MaXuatHang = xh.MaXuatHang "
                                + "WHERE 1=1 " + filterSql
                                + "GROUP BY bg.MaBinhGas, bg.TenBinhGas, bg.LoaiBinh, bg.LoaiVan "
                                + "ORDER BY SoLuongXuat DESC";
                        break;

                    case "Doanh thu & Lợi nhuận":
                        sql = "SELECT bg.MaBinhGas, bg.TenBinhGas, bg.LoaiBinh, bg.LoaiVan, "
                                + "SUM(ctxh.SoLuongXuat) AS SoLuongXuat, "
                                + "AVG(ctxh.DonGiaXuat) AS DonGiaXuatTB, "
                                + "SUM(ctxh.SoLuongXuat * ctxh.DonGiaXuat) AS DoanhThu, "
                                + "bg.GiaVonTrungBinh, "
                                + "SUM(ctxh.SoLuongXuat * bg.GiaVonTrungBinh) AS TongGiaVon, "
                                + "SUM(ctxh.SoLuongXuat * (ctxh.DonGiaXuat - bg.GiaVonTrungBinh)) AS LoiNhuan "
                                + "FROM ChiTietXuatHang ctxh "
                                + "JOIN BinhGas bg ON ctxh.MaBinhGas = bg.MaBinhGas "
                                + "JOIN XuatHang xh ON ctxh.MaXuatHang = xh.MaXuatHang "
                                + "WHERE 1=1 " + filterSql
                                + "GROUP BY bg.MaBinhGas, bg.TenBinhGas, bg.LoaiBinh, bg.LoaiVan, bg.GiaVonTrungBinh "
                                + "ORDER BY LoiNhuan DESC";
                        break;

                    case "Số lượng & Giá trị nhập hàng":
                        sql = "SELECT bg.MaBinhGas, bg.TenBinhGas, bg.LoaiBinh, bg.LoaiVan, "
                                + "SUM(ctnh.SoLuongNhap) AS SoLuongNhap, "
                                + "AVG(ctnh.DonGiaNhap) AS DonGiaNhapTB, "
                                + "SUM(ctnh.SoLuongNhap * ctnh.DonGiaNhap) AS GiaTriNhap "
                                + "FROM ChiTietNhapHang ctnh "
                                + "JOIN BinhGas bg ON ctnh.MaBinhGas = bg.MaBinhGas "
                                + "JOIN NhapHang nh ON ctnh.MaNhapHang = nh.MaNhapHang "
                                + "WHERE 1=1 " + filterSqlNhap
                                + "GROUP BY bg.MaBinhGas, bg.TenBinhGas, bg.LoaiBinh, bg.LoaiVan "
                                + "ORDER BY GiaTriNhap DESC";
                        break;

                    case "Giá trị kho":
                        sql = "SELECT MaBinhGas, TenBinhGas, LoaiBinh, LoaiVan, SoLuong, GiaVonTrungBinh, "
                                + "SoLuong * GiaVonTrungBinh AS GiaTriKho, GhiChu FROM BinhGas";
                        break;

                    default:
                        sql = "SELECT * FROM BinhGas";
                        break;
                }
            } else {
                sql = "SELECT * FROM BinhGas";
            }
        } else if (selectedReport.equals("Nhà Cung Cấp")) {
            sql = "SELECT ncc.MaNhaCungCap, ncc.TenNhaCungCap, "
                    + "COUNT(nh.MaNhapHang) AS SoLanNhap, "
                    + "SUM(ctnh.SoLuongNhap * ctnh.DonGiaNhap) AS TongGiaTriNhap, "
                    + "MAX(nh.NgayNhap) AS NgayNhap "
                    + "FROM NhaCungCap ncc "
                    + "JOIN NhapHang nh ON nh.MaNhaCungCap = ncc.MaNhaCungCap "
                    + "JOIN ChiTietNhapHang ctnh ON nh.MaNhapHang = ctnh.MaNhapHang "
                    + "WHERE 1=1 " + filterSqlNhap
                    + "GROUP BY ncc.MaNhaCungCap, ncc.TenNhaCungCap "
                    + "ORDER BY TongGiaTriNhap DESC";
        } else if (selectedReport.equals("Nhân Viên")) {
            sql = "SELECT nv.MaNhanVien, nv.HoTen, "
                    + "COUNT(xh.MaXuatHang) AS SoDonXuat, "
                    + "SUM(ctxh.SoLuongXuat * ctxh.DonGiaXuat) AS DoanhThuBanDuoc, "
                    + "MAX(xh.NgayXuat) AS NgayXuat "
                    + "FROM NhanVien nv "
                    + "JOIN XuatHang xh ON nv.MaNhanVien = xh.MaNhanVien "
                    + "JOIN ChiTietXuatHang ctxh ON xh.MaXuatHang = ctxh.MaXuatHang "
                    + "WHERE 1=1 " + filterSql
                    + "GROUP BY nv.MaNhanVien, nv.HoTen "
                    + "ORDER BY DoanhThuBanDuoc DESC";
        } else if (selectedReport.equals("Khách Hàng")) {
            sql = "SELECT kh.MaKhachHang, kh.HoTen, "
                    + "COUNT(xh.MaXuatHang) AS SoLanMua, "
                    + "SUM(ctxh.SoLuongXuat * ctxh.DonGiaXuat) AS TongGiaTriMua, "
                    + "MAX(xh.NgayXuat) AS NgayXuat "
                    + "FROM KhachHang kh "
                    + "JOIN XuatHang xh ON xh.MaKhachHang = kh.MaKhachHang "
                    + "JOIN ChiTietXuatHang ctxh ON xh.MaXuatHang = ctxh.MaXuatHang "
                    + "WHERE 1=1 " + filterSql
                    + "GROUP BY kh.MaKhachHang, kh.HoTen "
                    + "ORDER BY TongGiaTriMua DESC";
        } else {
            sql = "SELECT * FROM BinhGas";
        }
        // Load dữ liệu vào DefaultTableModel từ truy vấn SQL
        DefaultTableModel model = DAO.loadDataToTable(sql, conn);
        tbleBaoCao.setModel(model); // Gán model cho JTable

// Tự động hiển thị tiêu đề cột
        tbleBaoCao.getTableHeader().setVisible(true);

// Tự động giãn cột theo nội dung
        tbleBaoCao.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

// Bật/tắt nút in/xuất theo dữ liệu
        boolean hasData = model.getRowCount() > 0;
        btnXuatFile.setEnabled(hasData);

// Định dạng cột tiền tệ
        for (int i = 0; i < tbleBaoCao.getColumnCount(); i++) {
            String columnName = tbleBaoCao.getColumnName(i);

            if (columnName.contains("Giá") || columnName.contains("Tiền")
                    || columnName.contains("Trị") || columnName.contains("Doanh")
                    || columnName.contains("Lợi")) {

                tbleBaoCao.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                    {
                        setHorizontalAlignment(SwingConstants.RIGHT);
                    }
                });
            }
        }

// Đặt lại tên cột tiếng Việt
        Map<String, String> columnNamesMap = new HashMap<>();
        columnNamesMap.put("MaBinhGas", "Mã Bình Gas");
        columnNamesMap.put("TenBinhGas", "Tên Bình Gas");
        columnNamesMap.put("LoaiBinh", "Loại Bình");
        columnNamesMap.put("LoaiVan", "Loại Van");
        columnNamesMap.put("SoLuongXuat", "Số Lượng Xuất");
        columnNamesMap.put("SoLuongNhap", "Số Lượng Nhập");
        columnNamesMap.put("DonGiaXuatTB", "Đơn Giá Xuất TB");
        columnNamesMap.put("DonGiaNhapTB", "Đơn Giá Nhập TB");
        columnNamesMap.put("DoanhThu", "Doanh Thu");
        columnNamesMap.put("TongGiaVon", "Tổng Giá Vốn");
        columnNamesMap.put("LoiNhuan", "Lợi Nhuận");
        columnNamesMap.put("GiaTriNhap", "Giá Trị Nhập");
        columnNamesMap.put("GiaTriKho", "Giá Trị Kho");
        columnNamesMap.put("SoLuong", "Số Lượng");
        columnNamesMap.put("GiaVonTrungBinh", "Giá Vốn TB");
        columnNamesMap.put("GhiChu", "Ghi Chú");
        columnNamesMap.put("NgayCuoi", "Ngày Cuối");
        columnNamesMap.put("MaNhaCungCap", "Mã Nhà Cung Cấp");
        columnNamesMap.put("TenNhaCungCap", "Tên Nhà Cung Cấp");
        columnNamesMap.put("SoLanNhap", "Số Lần Nhập");
        columnNamesMap.put("TongGiaTriNhap", "Tổng Giá Trị Nhập");
        columnNamesMap.put("MaNhanVien", "Mã Nhân Viên");
        columnNamesMap.put("HoTen", "Họ Tên");
        columnNamesMap.put("SoDonXuat", "Số Đơn Xuất");
        columnNamesMap.put("DoanhThuBanDuoc", "Doanh Thu Bán Được");
        columnNamesMap.put("MaKhachHang", "Mã Khách Hàng");
        columnNamesMap.put("SoLanMua", "Số Lần Mua");
        columnNamesMap.put("TongGiaTriMua", "Tổng Giá Trị Mua");
        columnNamesMap.put("NgayNhap", "Ngày Nhập");

// Xử lý key "NgayXuat" với điều kiện
        columnNamesMap.put("NgayXuat", selectedReport.equals("Khách Hàng") ? "Ngày Mua" : "Ngày Xuất");

// Cập nhật tiêu đề cột trong JTable
        for (int i = 0; i < tbleBaoCao.getColumnCount(); i++) {
            String columnName = tbleBaoCao.getColumnName(i);
            if (columnNamesMap.containsKey(columnName)) {
                tbleBaoCao.getColumnModel().getColumn(i).setHeaderValue(columnNamesMap.get(columnName));
            }
        }

    }//GEN-LAST:event_btnXuatBaoCaoMouseClicked

    private void exportNhapToExcel() {
        // Thay dgvXuatHang bằng JTable của nhập hàng, ví dụ: dgvNhapHang
        DefaultTableModel model = (DefaultTableModel) tbleBaoCao.getModel();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu nhập hàng để xuất ra Excel.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel báo cáo"); // Đổi tiêu đề hộp thoại
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
                        tbleBaoCao, // JTable chứa dữ liệu nhập hàng
                        fileToSave.getAbsolutePath(), // Đường dẫn file sẽ lưu
                        "Danh Sách Bao Cao" + cboDoiTuong.getSelectedItem().toString(), // Tên sheet trong Excel
                        "DANH SÁCH BÁO CÁO" // Tiêu đề chính của báo cáo
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
    private javax.swing.JButton btnXuatBaoCao;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JComboBox<String> cboDanhMucTheoBinhGas;
    private javax.swing.JComboBox<String> cboDoiTuong;
    private javax.swing.JComboBox<String> cboThoiGian;
    private com.toedter.calendar.JDateChooser dcrTGBD;
    private com.toedter.calendar.JDateChooser dcrTGKT;
    private javax.swing.JLabel lblDen;
    private javax.swing.JLabel lblHangHoa;
    private javax.swing.JLabel lblLoaiBinh;
    private javax.swing.JLabel lblThoiGian;
    private javax.swing.JLabel lblTu;
    private javax.swing.JPanel pnlDanhMucBaoCao;
    private javax.swing.JPanel pnlHangHoa;
    private javax.swing.JPanel pnlThoiGian;
    private javax.swing.JScrollPane scpHangHoa;
    private javax.swing.JTable tbleBaoCao;
    // End of variables declaration//GEN-END:variables
}
