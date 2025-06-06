/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandat.cuahanggas.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

public class MyToys {

    //hàm thêm khóa tự động
    public static String generateNextIdFromStrings(List<String> ids, String prefix) {
        int maxNumber = 0;

        for (String id : ids) {
            if (id.startsWith(prefix) && id.length() == prefix.length() + 3) {
                try {
                    int num = Integer.parseInt(id.substring(prefix.length(), prefix.length() + 3));
                    if (num > maxNumber) {
                        maxNumber = num;
                    }
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu không phải số hợp lệ
                }
            }
        }

        int nextNumber = maxNumber + 1;
        return String.format("%s%03d", prefix, nextNumber); // Format 3 chữ số: 001, 045
    }

    //hàm kiểm tra trống cảu JText
    public static boolean isValidInput(javax.swing.JTextField... fields) {
        for (javax.swing.JTextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(
                        null,
                        "Please fill in the information",
                        "Lack of data",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );
                field.requestFocus();
                return false;
            }
        }
        return true;
    }

    //hàm load comboBox nhận vào List trả ra model để gán
    public static DefaultTableModel listToTableModel(List<?> list) {
        if (list == null || list.isEmpty()) {
            return new DefaultTableModel(); // Trả về bảng rỗng nếu danh sách trống
        }

        // Lấy danh sách trường (field) từ class của phần tử đầu tiên
        Class<?> clazz = list.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();

        // Tạo tên cột
        String[] columnNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            columnNames[i] = fields[i].getName(); // Dùng tên field làm tên cột
        }

        // Tạo dữ liệu cho bảng
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Object obj : list) {
            Object[] rowData = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true); // Cho phép truy cập field private
                    rowData[i] = fields[i].get(obj); // Lấy giá trị của field từ object
                } catch (IllegalAccessException e) {
                    rowData[i] = null; // Nếu có lỗi, đặt giá trị null
                }
            }
            model.addRow(rowData);
        }

        return model;
    }

    public static <T> boolean isForeignKeyInUse(List<T> childList, Predicate<T> predicate) {
        return childList.stream().anyMatch(predicate);
    }

//    List<ChiTietNhapHang> list = chiTietNhapHangService.getAll();
//
//    boolean isUsed = CabinetUtil.isForeignKeyInUse(
//            list,
//            ct -> ct.getMaBinhGas().equals("BG01")
//    );
//
//
//if (isUsed) {
//    System.out.println("MaBinhGas đang được sử dụng.");
//} else {
//    System.out.println("Có thể xóa MaBinhGas này.");
//}
    public static boolean validatePhone(JTextField txtPhone) {
        String rawPhone = txtPhone.getText();

        if (rawPhone.length() != 10) {
            JOptionPane.showMessageDialog(null,
                    "Số điện thoại phải đủ 10 chữ số!",
                    "Lỗi",
                    JOptionPane.WARNING_MESSAGE);
            txtPhone.requestFocus();
            return false;
        }

        String prefix = rawPhone.substring(0, 2);
        if (!prefix.equals("03") && !prefix.equals("08") && !prefix.equals("09")) {
            JOptionPane.showMessageDialog(null,
                    "Số điện thoại phải bắt đầu bằng 03, 08 hoặc 09!",
                    "Lỗi",
                    JOptionPane.WARNING_MESSAGE);
            txtPhone.requestFocus();
            return false;
        }

        // Kiểm tra toàn bộ là số
        if (!rawPhone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null,
                    "Số điện thoại chỉ được chứa chữ số!",
                    "Lỗi",
                    JOptionPane.WARNING_MESSAGE);
            txtPhone.requestFocus();
            return false;
        }

        return true;
    }
    
    public static void setNumericFilter(JTextField textField) {
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

}
