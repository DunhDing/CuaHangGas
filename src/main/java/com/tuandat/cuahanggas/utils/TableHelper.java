package com.tuandat.cuahanggas.utils;

import com.tuandat.cuahanggas.model.NhanVien;
import com.tuandat.cuahanggas.model.TaiKhoanNguoiDung;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

public class TableHelper {
    private static final Logger logger = Logger.getLogger(TableHelper.class.getName());
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
    
    public static DefaultTableModel resultSetToTableModel(ResultSet rs, LinkedHashMap<String, String> columnHeaderMapping) {
        DefaultTableModel model = null;
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Lấy tên cột hiển thị theo thứ tự từ mapping
            List<String> displayColumnNames = new ArrayList<>(columnHeaderMapping.values());
            Vector<String> columnNamesVector = new Vector<>(displayColumnNames);

            // Tạo DefaultTableModel với các tên cột đã ánh xạ
            model = new DefaultTableModel(columnNamesVector, 0);

            // Duyệt qua từng hàng của ResultSet và thêm vào model
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (Map.Entry<String, String> entry : columnHeaderMapping.entrySet()) {
                    String dbColumnName = entry.getKey(); // Tên cột trong DB
                    try {
                        Object value = rs.getObject(dbColumnName);

                        // Xử lý đặc biệt cho kiểu ngày tháng hoặc các kiểu khác nếu cần
                        if (value instanceof java.sql.Date) {
                            row.add(((java.sql.Date) value).toLocalDate()); // Chuyển đổi sang LocalDate
                        } else if (value instanceof java.sql.Timestamp) {
                            row.add(((java.sql.Timestamp) value).toLocalDateTime()); // Chuyển đổi sang LocalDateTime
                        } else {
                            row.add(value);
                        }
                    } catch (SQLException e) {
                        // Nếu cột không tồn tại trong ResultSet, thêm giá trị rỗng hoặc null
                        logger.log(Level.WARNING, "Cột '" + dbColumnName + "' không tìm thấy hoặc có lỗi khi đọc từ ResultSet. Thêm giá trị null.", e);
                        row.add(null);
                    }
                }
                model.addRow(row);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Lỗi khi chuyển đổi ResultSet sang TableModel: " + e.getMessage(), e);
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
