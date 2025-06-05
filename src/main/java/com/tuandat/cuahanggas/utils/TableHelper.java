package com.tuandat.cuahanggas.utils;

import com.tuandat.cuahanggas.model.NhanVien;
import com.tuandat.cuahanggas.model.TaiKhoanNguoiDung;
import java.sql.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

public class TableHelper {
    public static DefaultTableModel resultSetToTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Tạo tên cột
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }

        // Tạo dữ liệu cho bảng
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = rs.getObject(i);
            }
            model.addRow(rowData);
        }

        return model;
    }
    
    public static List<NhanVien> getNhanViensChuaCoTaiKhoan(
            List<NhanVien> danhSachNhanVien,
            List<TaiKhoanNguoiDung> danhSachTaiKhoan) {

        // Lấy danh sách mã nhân viên đã có trong bảng tài khoản
        Set<String> maNhanViensDaCoTK = danhSachTaiKhoan.stream()
                .map(TaiKhoanNguoiDung::getMaNhanVien)
                .collect(Collectors.toSet());

        // Lọc ra những nhân viên chưa có tài khoản
        return danhSachNhanVien.stream()
                .filter(nv -> !maNhanViensDaCoTK.contains(nv.getMaNhanVien()))
                .collect(Collectors.toList());
    }
}
