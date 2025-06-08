package com.tuandat.cuahanggas.ui;

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

public class ucTongQuan extends javax.swing.JPanel {

    private Connection conn;

    public ucTongQuan(Connection conn) {
        this.conn = conn;
        initComponents();
        showChart();
    }

    private void showChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            // Sửa lại câu truy vấn để chỉ lấy doanh số trong ngày hôm nay
            String sql = "SELECT bg.MaBinhGas, bg.TenBinhGas, bg.LoaiBinh, bg.LoaiVan, "
                    + "SUM(ctxh.SoLuongXuat) AS SoLuongXuat, "
                    + "MAX(xh.NgayXuat) AS NgayCuoi "
                    + "FROM ChiTietXuatHang ctxh "
                    + "JOIN BinhGas bg ON ctxh.MaBinhGas = bg.MaBinhGas "
                    + "JOIN XuatHang xh ON ctxh.MaXuatHang = xh.MaXuatHang "
                    + "WHERE CONVERT(DATE, xh.NgayXuat) = CONVERT(DATE, GETDATE()) " // Chỉ lấy dữ liệu của ngày hôm nay
                    + "GROUP BY bg.MaBinhGas, bg.TenBinhGas, bg.LoaiBinh, bg.LoaiVan "
                    + "ORDER BY SoLuongXuat DESC"; // Sắp xếp theo doanh số xuất

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả và thêm vào dataset
            while (rs.next()) {
                String tenBinhGas = rs.getString("TenBinhGas");
                String loaiBinh = rs.getString("LoaiBinh");
                String loaiVan = rs.getString("LoaiVan");
                int soLuongXuat = rs.getInt("SoLuongXuat");

                // Tạo một chuỗi để hiển thị thêm thông tin loại bình và loại van
                String key = tenBinhGas + " (" + loaiBinh + ", " + loaiVan + ")";

                // Thêm dữ liệu vào dataset, hiển thị Số Lượng xuất
                dataset.addValue(soLuongXuat, "Số Lượng", key);
            }

            // Tạo biểu đồ từ dữ liệu cơ sở dữ liệu
            JFreeChart chart = ChartFactory.createBarChart(
                    "Doanh số bình gas hôm nay", // Tiêu đề biểu đồ
                    "Tên bình gas (Loại)", // Tiêu đề trục X
                    "Số lượng xuất", // Tiêu đề trục Y
                    dataset // Dữ liệu biểu đồ
            );
            CategoryPlot plot = chart.getCategoryPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setMaximumBarWidth(0.1); // Đặt độ rộng tối đa của cột thành 10%
            renderer.setSeriesPaint(0, new Color(0,102,204));
            // Thêm biểu đồ vào panel
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(800, 400));
            this.setLayout(new java.awt.BorderLayout());
            this.add(chartPanel, java.awt.BorderLayout.CENTER);

        } catch (SQLException e) {
            e.printStackTrace();
            // Nếu có lỗi, hiển thị thông báo lỗi
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu từ cơ sở dữ liệu!", "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 990, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
