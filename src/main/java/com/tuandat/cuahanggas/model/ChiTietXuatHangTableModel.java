package com.tuandat.cuahanggas.model; // Đảm bảo gói này phù hợp với cấu trúc project của bạn

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane; // Cần thiết cho các thông báo lỗi khi nhập liệu

public class ChiTietXuatHangTableModel extends AbstractTableModel {

    private List<ChiTietXuatHang> data;
    // Tên các cột sẽ hiển thị trên JTable
    private final String[] columnNames = {"Mã Bình Gas", "Tên Bình Gas", "Số Lượng Xuất", "Đơn Giá Xuất", "Thành Tiền"};

    public ChiTietXuatHangTableModel() {
        this.data = new ArrayList<>();
    }

    /**
     * Trả về danh sách các đối tượng ChiTietXuatHang hiện có trong model.
     * @return List<ChiTietXuatHang>
     */
    public List<ChiTietXuatHang> getData() {
        return data;
    }

    /**
     * Thêm một dòng (ChiTietXuatHang) mới vào bảng.
     * @param item Đối tượng ChiTietXuatHang cần thêm.
     */
    public void addRow(ChiTietXuatHang item) {
        // Kiểm tra xem sản phẩm đã có trong danh sách chưa
        for (int i = 0; i < data.size(); i++) {
            ChiTietXuatHang existingItem = data.get(i);
            if (existingItem.getMaBinhGas().equals(item.getMaBinhGas())) {
                // Nếu sản phẩm đã có, cập nhật số lượng và đơn giá (nếu bạn muốn cho phép)
                // Hoặc đơn giản là tăng số lượng và giữ nguyên đơn giá cũ
                // Trong ví dụ này, tôi sẽ cộng dồn số lượng
                existingItem.setSoLuongXuat(existingItem.getSoLuongXuat() + item.getSoLuongXuat());
                // Cập nhật đơn giá nếu cần (ví dụ: lấy đơn giá mới nếu giá khác)
                // Hoặc bạn có thể giữ nguyên đơn giá cũ, tùy theo nghiệp vụ
                existingItem.setDonGiaXuat(item.getDonGiaXuat()); // Cập nhật đơn giá theo cái mới nhập
                fireTableRowsUpdated(i, i); // Thông báo JTable rằng dòng đó đã được cập nhật
                return; // Thoát khỏi phương thức sau khi cập nhật
            }
        }
        // Nếu sản phẩm chưa có, thêm mới
        data.add(item);
        // Thông báo cho JTable rằng một dòng mới đã được chèn vào cuối
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    /**
     * Xóa một dòng khỏi bảng dựa trên chỉ số dòng.
     * @param rowIndex Chỉ số dòng cần xóa.
     */
    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < data.size()) {
            data.remove(rowIndex);
            // Thông báo cho JTable rằng một dòng đã bị xóa
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    /**
     * Trả về số lượng dòng trong bảng.
     * @return Số lượng dòng.
     */
    @Override
    public int getRowCount() {
        return data.size();
    }

    /**
     * Trả về số lượng cột trong bảng.
     * @return Số lượng cột.
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Trả về tên của cột tại chỉ số được chỉ định.
     * @param column Chỉ số cột.
     * @return Tên cột.
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * Trả về giá trị của ô tại vị trí (rowIndex, columnIndex).
     * @param rowIndex Chỉ số dòng.
     * @param columnIndex Chỉ số cột.
     * @return Giá trị của ô.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ChiTietXuatHang item = data.get(rowIndex);
        switch (columnIndex) {
            case 0: return item.getMaBinhGas();
            case 1: return item.getTenBinhGas();
            case 2: return item.getSoLuongXuat();
            case 3: return item.getDonGiaXuat();
            case 4: return item.getThanhTien(); // Tính toán Thành Tiền
            default: return null;
        }
    }

    /**
     * Xác định xem một ô có thể chỉnh sửa được hay không.
     * @param rowIndex Chỉ số dòng.
     * @param columnIndex Chỉ số cột.
     * @return true nếu ô có thể chỉnh sửa, false nếu không.
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Cho phép chỉnh sửa cột "Số Lượng Xuất" (index 2) và "Đơn Giá Xuất" (index 3)
        return columnIndex == 2 || columnIndex == 3;
    }

    /**
     * Đặt giá trị cho một ô tại vị trí (aValue, rowIndex, columnIndex).
     * Phương thức này được gọi khi người dùng chỉnh sửa trực tiếp trên JTable.
     * @param aValue Giá trị mới được nhập.
     * @param rowIndex Chỉ số dòng.
     * @param columnIndex Chỉ số cột.
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ChiTietXuatHang item = data.get(rowIndex);
        try {
            if (columnIndex == 2) { // Cột "Số Lượng Xuất"
                int newSoLuong = Integer.parseInt(aValue.toString());
                if (newSoLuong > 0) {
                    item.setSoLuongXuat(newSoLuong);
                } else {
                    JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên dương.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                }
            } else if (columnIndex == 3) { // Cột "Đơn Giá Xuất"
                double newDonGia = Double.parseDouble(aValue.toString());
                // Cho phép đơn giá bằng 0 nếu bạn muốn
                if (newDonGia >= 0) {
                    item.setDonGiaXuat(newDonGia);
                } else {
                    JOptionPane.showMessageDialog(null, "Đơn giá phải là số không âm.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                }
            }
            // Thông báo rằng ô đã được cập nhật, JTable sẽ tự động gọi fireTableDataChanged
            // hoặc fireTableRowsUpdated nếu bạn đã thêm TableModelListener
            fireTableCellUpdated(rowIndex, columnIndex);
            // Quan trọng: Sau khi cập nhật một ô, bạn cần thông báo cho các listener biết rằng
            // dữ liệu đã thay đổi, đặc biệt nếu nó ảnh hưởng đến các cột tính toán (như Thành Tiền)
            // hoặc tổng cộng. Gọi fireTableDataChanged() để đảm bảo JTable và listener cập nhật lại.
            fireTableDataChanged();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số hợp lệ.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        }
    }
}