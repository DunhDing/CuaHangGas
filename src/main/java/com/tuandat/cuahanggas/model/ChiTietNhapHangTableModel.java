package com.tuandat.cuahanggas.model; // Hoặc package khác

import com.tuandat.cuahanggas.model.ChiTietNhapHang;
import javax.swing.table.AbstractTableModel;
// import java.math.BigDecimal; // KHÔNG CẦN NỮA
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane; // Dùng cho thông báo lỗi trong setValueAt

public class ChiTietNhapHangTableModel extends AbstractTableModel {

    private List<ChiTietNhapHang> data;
    private String[] columnNames = {"Mã Bình Gas", "Tên Bình Gas", "Số Lượng Nhập", "Đơn Giá Nhập", "Thành Tiền"};
    private String[] internalColumnNames = {"MaBinhGas", "TenBinhGas", "SoLuong", "DonGia", "ThanhTien"};


    public ChiTietNhapHangTableModel() {
        this.data = new ArrayList<>();
    }

    public List<ChiTietNhapHang> getData() {
        return data;
    }

    public ChiTietNhapHang getItemAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < data.size()) {
            return data.get(rowIndex);
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    // Phương thức để lấy tên cột nội bộ (không hiển thị)
    public String getInternalColumnName(int columnIndex) {
        return internalColumnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (internalColumnNames[columnIndex]) {
            case "SoLuong":
                return Integer.class;
            case "DonGia":
            case "ThanhTien":
                return Integer.class; // ĐÃ THAY ĐỔI TỪ BigDecimal.class SANG Integer.class
            default:
                return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Chỉ cho phép chỉnh sửa cột "SoLuong" và "DonGia"
        return internalColumnNames[columnIndex].equals("SoLuong") || internalColumnNames[columnIndex].equals("DonGia");
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ChiTietNhapHang item = data.get(rowIndex);
        switch (internalColumnNames[columnIndex]) {
            case "MaBinhGas":
                return item.getMaBinhGas();
            case "TenBinhGas":
                return item.getTenBinhGas();
            case "SoLuong":
                return item.getSoLuongNhap();
            case "DonGia":
                return item.getDonGiaNhap();
            case "ThanhTien":
                return item.getThanhTien(); // Dùng getter mới để tính toán (đã trả về int)
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= data.size()) return;

        ChiTietNhapHang item = data.get(rowIndex);
        String columnName = internalColumnNames[columnIndex];

        try {
            if (columnName.equals("SoLuong")) {
                int soLuong = Integer.parseInt(aValue.toString());
                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên dương.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                } else {
                    item.setSoLuongNhap(soLuong);
                    fireTableCellUpdated(rowIndex, columnIndex); // Cập nhật cột số lượng
                    fireTableCellUpdated(rowIndex, findColumn("ThanhTien")); // Cập nhật cột Thành Tiền
                }
            } else if (columnName.equals("DonGia")) {
                int donGia = Integer.parseInt(aValue.toString()); // ĐÃ THAY ĐỔI TỪ BigDecimal SANG int
                if (donGia < 0) { // ĐÃ THAY ĐỔI LOGIC KIỂM TRA SANG int
                    JOptionPane.showMessageDialog(null, "Đơn giá không thể là số âm.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                } else {
                    item.setDonGiaNhap(donGia); // Set int
                    fireTableCellUpdated(rowIndex, columnIndex);
                    fireTableCellUpdated(rowIndex, findColumn("ThanhTien"));
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Giá trị nhập vào không hợp lệ. Vui lòng nhập một số nguyên.", "Lỗi", JOptionPane.WARNING_MESSAGE); // THAY ĐỔI THÔNG BÁO LỖI
        }
    }

    /**
     * Thêm một ChiTietNhapHang mới vào model.
     * Nếu MaBinhGas đã tồn tại, sẽ cập nhật số lượng và đơn giá.
     * @param newItem Đối tượng ChiTietNhapHang cần thêm/cập nhật.
     */
    public void addRow(ChiTietNhapHang newItem) {
        int existingIndex = -1;
        for (int i = 0; i < data.size(); i++) {
            // So sánh dựa trên MaBinhGas và MaNhapHang (nếu có khóa chính tổng hợp)
            // Hiện tại chỉ so sánh MaBinhGas theo code của bạn.
            // Nếu bạn muốn so sánh cả MaNhapHang, cần thêm điều kiện
            if (data.get(i).getMaBinhGas().equals(newItem.getMaBinhGas())) {
                existingIndex = i;
                break;
            }
        }

        if (existingIndex != -1) {
            // Cập nhật số lượng và đơn giá nếu đã tồn tại
            ChiTietNhapHang existingItem = data.get(existingIndex);
            existingItem.setSoLuongNhap(newItem.getSoLuongNhap());
            existingItem.setDonGiaNhap(newItem.getDonGiaNhap());
            fireTableRowsUpdated(existingIndex, existingIndex); // Cập nhật hàng đó
        } else {
            data.add(newItem);
            fireTableRowsInserted(data.size() - 1, data.size() - 1); // Thêm hàng mới
        }
    }

    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < data.size()) {
            data.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }
    
    public void clearData() {
        data.clear();
        fireTableDataChanged();
    }

    // Helper method to find column index by internal name
    public int findColumn(String internalColumnName) {
        for (int i = 0; i < internalColumnNames.length; i++) {
            if (internalColumnNames[i].equals(internalColumnName)) {
                return i;
            }
        }
        return -1; // Not found
    }
}