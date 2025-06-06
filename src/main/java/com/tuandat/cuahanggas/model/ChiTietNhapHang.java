package com.tuandat.cuahanggas.model;

// Không cần import java.math.BigDecimal nữa nếu chỉ dùng int

/**
 * Lớp ChiTietNhapHang đại diện cho một mặt hàng trong hóa đơn nhập.
 *
 * @author duck
 */
public class ChiTietNhapHang {

    private String maNhapHang; // Part of composite primary key, and foreign key
    private String maBinhGas;  // Part of composite primary key, and foreign key
    private String tenBinhGas; // Tên bình gas (đã thêm)
    private int soLuongNhap;
    private int donGiaNhap;    // ĐÃ THAY ĐỔI TỪ BigDecimal SANG int
    private String ghiChu;

    // Constructor rỗng
    public ChiTietNhapHang() {
    }

    /**
     * Constructor đầy đủ cho ChiTietNhapHang.
     *
     * @param maNhapHang Mã hóa đơn nhập.
     * @param maBinhGas Mã bình gas.
     * @param tenBinhGas Tên bình gas. // Đã thêm tham số này
     * @param soLuongNhap Số lượng bình gas nhập.
     * @param donGiaNhap Đơn giá nhập của bình gas. // Đã thay đổi kiểu tham số
     * @param ghiChu Ghi chú cho chi tiết nhập hàng.
     */
    public ChiTietNhapHang(String maNhapHang, String maBinhGas, String tenBinhGas, int soLuongNhap,
                           int donGiaNhap, String ghiChu) { // Đã thay đổi kiểu tham số
        this.maNhapHang = maNhapHang;
        this.maBinhGas = maBinhGas;
        this.tenBinhGas = tenBinhGas; // Gán giá trị tên bình gas
        this.soLuongNhap = soLuongNhap;
        this.donGiaNhap = donGiaNhap;
        this.ghiChu = ghiChu;
    }

    // Getters và Setters
    public String getMaNhapHang() {
        return maNhapHang;
    }

    public void setMaNhapHang(String maNhapHang) {
        this.maNhapHang = maNhapHang;
    }

    public String getMaBinhGas() {
        return maBinhGas;
    }

    public void setMaBinhGas(String maBinhGas) {
        this.maBinhGas = maBinhGas;
    }

    public String getTenBinhGas() {
        return tenBinhGas;
    }

    public void setTenBinhGas(String tenBinhGas) {
        this.tenBinhGas = tenBinhGas;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public int getDonGiaNhap() { // Đã thay đổi kiểu trả về
        return donGiaNhap;
    }

    public void setDonGiaNhap(int donGiaNhap) { // Đã thay đổi kiểu tham số
        this.donGiaNhap = donGiaNhap;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    /**
     * Tính toán thành tiền cho chi tiết nhập hàng này.
     * @return Thành tiền dưới dạng int.
     */
    public int getThanhTien() {
        // Kiểm tra nếu donGiaNhap hoặc soLuongNhap không hợp lệ (ví dụ: âm)
        // Mặc dù int không thể null, bạn vẫn có thể kiểm tra giá trị âm
        if (donGiaNhap < 0 || soLuongNhap < 0) {
            return 0; // Trả về 0 hoặc ném ngoại lệ nếu dữ liệu không hợp lệ
        }
        return donGiaNhap * soLuongNhap; // THAY ĐỔI TÍNH TOÁN SANG int
    }

    @Override
    public String toString() {
        return "ChiTietNhapHang{" +
               "maNhapHang='" + maNhapHang + '\'' +
               ", maBinhGas='" + maBinhGas + '\'' +
               ", tenBinhGas='" + tenBinhGas + '\'' + // Thêm tenBinhGas vào toString
               ", soLuongNhap=" + soLuongNhap +
               ", donGiaNhap=" + donGiaNhap +
               ", ghiChu='" + ghiChu + '\'' +
               '}';
    }

    // Quan trọng: implement equals và hashCode cho khóa chính tổng hợp
    // Điều này giúp so sánh hai đối tượng ChiTietNhapHang dựa trên khóa chính của chúng
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChiTietNhapHang that = (ChiTietNhapHang) o;

        // Đảm bảo so sánh không có null nếu các trường có thể null (ở đây thì không)
        if (!maNhapHang.equals(that.maNhapHang)) return false;
        return maBinhGas.equals(that.maBinhGas);
    }

    @Override
    public int hashCode() {
        int result = maNhapHang.hashCode();
        result = 31 * result + maBinhGas.hashCode();
        return result;
    }
}