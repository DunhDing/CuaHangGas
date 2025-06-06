package com.tuandat.cuahanggas.model; // Đảm bảo đúng package của bạn

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.AreaReference; // Cần thiết cho XSSFTable
import org.apache.poi.ss.util.CellReference; // Cần thiết cho XSSFTable
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFTable; // Cần thiết cho XSSFTable

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExcelExporter {

    private static final Logger logger = Logger.getLogger(ExcelExporter.class.getName());

    /**
     * Xuất dữ liệu từ JTable ra file Excel với định dạng hóa đơn xuất hàng.
     *
     * @param table JTable chứa dữ liệu cần xuất.
     * @param filePath Đường dẫn đầy đủ của file Excel sẽ được tạo.
     * @param sheetName Tên của sheet trong Excel.
     * @param title Tiêu đề chính của báo cáo.
     * @throws IOException Nếu có lỗi xảy ra trong quá trình ghi file.
     */
    public static void exportHoaDonXuatToExcel(JTable table, String filePath, String sheetName, String title) throws IOException {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Sử dụng try-with-resources để đảm bảo Workbook và FileOutputStream được đóng tự động
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream outputStream = new FileOutputStream(filePath)) {

            Sheet sheet = workbook.createSheet(sheetName);

            // --- 1. Tạo Cell Styles ---
            // Style cho tiêu đề chính
            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 20);
            titleFont.setFontName("Times New Roman");
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            // Style cho tiêu đề cột (Header)
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Style cho dữ liệu căn giữa
            CellStyle centerAlignStyle = workbook.createCellStyle();
            centerAlignStyle.setAlignment(HorizontalAlignment.CENTER);
            centerAlignStyle.setBorderBottom(BorderStyle.THIN);
            centerAlignStyle.setBorderTop(BorderStyle.THIN);
            centerAlignStyle.setBorderLeft(BorderStyle.THIN);
            centerAlignStyle.setBorderRight(BorderStyle.THIN);

            // Style cho dữ liệu ngày tháng
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(workbook.createDataFormat().getFormat("dd/MM/yyyy"));
            dateStyle.setAlignment(HorizontalAlignment.CENTER);
            dateStyle.setBorderBottom(BorderStyle.THIN);
            dateStyle.setBorderTop(BorderStyle.THIN);
            dateStyle.setBorderLeft(BorderStyle.THIN);
            dateStyle.setBorderRight(BorderStyle.THIN);

            // Style cho dữ liệu số (tiền)
            CellStyle currencyStyle = workbook.createCellStyle();
            // Định dạng số có dấu phẩy phân cách hàng nghìn
            currencyStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
            currencyStyle.setAlignment(HorizontalAlignment.RIGHT);
            currencyStyle.setBorderBottom(BorderStyle.THIN);
            currencyStyle.setBorderTop(BorderStyle.THIN);
            currencyStyle.setBorderLeft(BorderStyle.THIN);
            currencyStyle.setBorderRight(BorderStyle.THIN);

            // Style cho dữ liệu căn trái
            CellStyle leftAlignStyle = workbook.createCellStyle();
            leftAlignStyle.setAlignment(HorizontalAlignment.LEFT);
            leftAlignStyle.setBorderBottom(BorderStyle.THIN);
            leftAlignStyle.setBorderTop(BorderStyle.THIN);
            leftAlignStyle.setBorderLeft(BorderStyle.THIN);
            leftAlignStyle.setBorderRight(BorderStyle.THIN);

            // --- 2. Tiêu đề chính (Merged Cells) ---
            int titleRowIndex = 0;
            Row titleRow = sheet.createRow(titleRowIndex);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(title);
            titleCell.setCellStyle(titleStyle);

            // Hợp nhất các ô cho tiêu đề chính (từ cột 0 đến cột cuối cùng của dữ liệu)
            int numberOfDataColumns = model.getColumnCount();
            sheet.addMergedRegion(new CellRangeAddress(
                    titleRowIndex,
                    titleRowIndex,
                    0,
                    numberOfDataColumns - 1
            ));

            // --- 3. Bỏ qua 1 hàng trống (để tạo khoảng cách) ---
            int emptyRowIndex = titleRowIndex + 1;

            // --- 4. Tiêu đề cột (Header Row) ---
            int headerRowIndex = emptyRowIndex + 1;
            Row headerExcelRow = sheet.createRow(headerRowIndex);

            // Định nghĩa tiêu đề cột và độ rộng cột
            String[] columnHeaders = {
                "Mã Xuất Hàng", "Ngày Xuất", "Mã Khách Hàng", "Tên Khách Hàng",
                "Mã Nhân Viên", "Tên Nhân Viên", "Tổng Tiền Hóa Đơn", "Ghi Chú"
            };
            int[] columnWidths = {18, 15, 20, 25, 18, 25, 25, 40}; // Đơn vị là 'character width' * 256

            // Thiết lập độ rộng cột
            for (int i = 0; i < columnWidths.length; i++) {
                sheet.setColumnWidth(i, columnWidths[i] * 256);
            }

            // Ghi tiêu đề cột
            for (int col = 0; col < columnHeaders.length; col++) {
                Cell cell = headerExcelRow.createCell(col);
                cell.setCellValue(columnHeaders[col]);
                cell.setCellStyle(headerStyle);
            }

            // --- 5. Ghi dữ liệu từ JTable vào Excel ---
            int dataStartRowIndex = headerRowIndex + 1;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            for (int row = 0; row < model.getRowCount(); row++) {
                Row excelRow = sheet.createRow(dataStartRowIndex + row);
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object value = model.getValueAt(row, col);
                    Cell cell = excelRow.createCell(col);

                    // Áp dụng style và xử lý dữ liệu theo loại cột
                    switch (col) {
                        case 0: // Mã Xuất Hàng
                        case 2: // Mã Khách Hàng
                        case 4: // Mã Nhân Viên
                            cell.setCellValue(value != null ? value.toString() : "");
                            cell.setCellStyle(centerAlignStyle);
                            break;
                        case 1: // Ngày Xuất (Giả định JTable trả về Date hoặc String định dạng "dd/MM/yyyy")
                            if (value instanceof Date) {
                                cell.setCellValue((Date) value);
                                cell.setCellStyle(dateStyle);
                            } else {
                                try {
                                    String dateStr = value != null ? value.toString() : "";
                                    if (!dateStr.isEmpty()) {
                                        cell.setCellValue(dateFormat.parse(dateStr));
                                        cell.setCellStyle(dateStyle);
                                    } else {
                                        cell.setCellValue("");
                                        cell.setCellStyle(centerAlignStyle);
                                    }
                                } catch (java.text.ParseException e) {
                                    // Nếu parse lỗi, ghi dạng chuỗi
                                    cell.setCellValue(value != null ? value.toString() : "");
                                    cell.setCellStyle(centerAlignStyle);
                                }
                            }
                            break;
                        case 3: // Tên Khách Hàng
                        case 5: // Tên Nhân Viên
                            cell.setCellValue(value != null ? value.toString() : "");
                            cell.setCellStyle(leftAlignStyle);
                            break;
                        case 6: // Tổng Tiền Hóa Đơn (Giả định JTable trả về Number hoặc String có thể parse sang Double)
                            if (value instanceof Number) {
                                cell.setCellValue(((Number) value).doubleValue());
                                cell.setCellStyle(currencyStyle);
                            } else {
                                try {
                                    cell.setCellValue(Double.parseDouble(value.toString()));
                                    cell.setCellStyle(currencyStyle);
                                } catch (NumberFormatException e) {
                                    // Nếu parse lỗi, ghi dạng chuỗi và căn trái
                                    cell.setCellValue(value != null ? value.toString() : "");
                                    cell.setCellStyle(leftAlignStyle);
                                }
                            }
                            break;
                        case 7: // Ghi Chú
                            cell.setCellValue(value != null ? value.toString() : "");
                            cell.setCellStyle(leftAlignStyle);
                            break;
                        default: // Các cột khác (nếu có)
                            cell.setCellValue(value != null ? value.toString() : "");
                            cell.setCellStyle(leftAlignStyle);
                            break;
                    }
                }
            }

            // --- 6. Tùy chọn: Tạo Excel Table (Chức năng này đã gây lỗi "cannot find symbol" trước đó) ---
            // Để sử dụng XSSFTable, bạn cần đảm bảo các dependency sau đã được tải thành công:
            // poi-ooxml (chứa XSSFWorkbook), poi-ooxml-schemas (chứa các schema XML cần thiết), và xmlbeans
            // (xmlbeans là phụ thuộc của poi-ooxml-schemas và thường được Maven tự động tải).
            try {
                // Kiểm tra xem có dữ liệu hay không trước khi tạo bảng
                if (model.getRowCount() > 0) {
                    int firstRow = headerRowIndex;
                    int lastRow = dataStartRowIndex + model.getRowCount() - 1;
                    int firstCol = 0;
                    int lastCol = columnHeaders.length - 1;

                    AreaReference tableArea = new AreaReference(
                            new CellReference(firstRow, firstCol),
                            new CellReference(lastRow, lastCol),
                            workbook.getSpreadsheetVersion()
                    );
            
                    // tableObj.getCTTable().addNewTableStyleInfo().setName("TableStyleMedium6"); // Tùy chọn: áp dụng một style có sẵn (có thể không hoạt động với mọi phiên bản POI)

                    // Lưu ý: Nếu vẫn gặp lỗi "cannot find symbol" cho AreaReference hoặc CellReference,
                    // hãy kiểm tra lại kỹ việc tải các dependency của POI bằng Maven.
                } else {
                    logger.log(Level.INFO, "Không có dữ liệu để tạo Excel Table.");
                }
            } catch (NoClassDefFoundError | NoSuchMethodError e) {
                // Bắt các lỗi liên quan đến thiếu lớp hoặc phương thức (thường do thiếu dependency)
                logger.log(Level.WARNING, "Không thể tạo Excel Table do thiếu dependency hoặc lỗi phiên bản: " + e.getMessage(), e);
            } catch (Exception e) {
                logger.log(Level.WARNING, "Lỗi khi tạo Excel Table: " + e.getMessage(), e);
            }

            // --- 7. Ghi Workbook ra File ---
            workbook.write(outputStream);
            logger.log(Level.INFO, "Đã xuất file Excel thành công: " + filePath);

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Lỗi IO khi xuất file Excel vào " + filePath, ex);
            throw ex; // Ném lại ngoại lệ để caller xử lý (ví dụ: hiển thị JOptionPane)
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Lỗi không xác định khi xuất file Excel vào " + filePath, ex);
            throw new IOException("Lỗi khi xuất file Excel: " + ex.getMessage(), ex);
        }
    }

    // Bạn có thể thêm các phương thức export khác ở đây (ví dụ: cho Hóa đơn nhập, báo cáo khác)
    // public static void exportHoaDonNhapToExcel(...) { ... }
}