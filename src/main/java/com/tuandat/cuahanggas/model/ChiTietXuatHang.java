package com.tuandat.cuahanggas.model; // Đảm bảo gói này phù hợp với cấu trúc project của bạn

public class ChiTietXuatHang {

    private String maXuatHang;
    private String maBinhGas;
    private String tenBinhGas; // Tên bình gas để hiển thị trên bảng, không cần lấy từ DB mỗi lần
    private int soLuongXuat;
    private double donGiaXuat;
    private String ghiChu; // Có thể có hoặc không, tùy theo nghiệp vụ của bạn

    public ChiTietXuatHang() {
    }
    /**
     * Constructor để tạo một đối tượng ChiTietXuatHang mới.
     *
     * @param maXuatHang Mã hóa đơn xuất mà chi tiết này thuộc về.
     * @param maBinhGas Mã của bình gas được xuất.
     * @param tenBinhGas Tên của bình gas (để hiển thị tiện lợi).
     * @param soLuongXuat Số lượng bình gas được xuất.
     * @param donGiaXuat Đơn giá xuất của một bình gas.
     * @param ghiChu Ghi chú thêm cho chi tiết xuất hàng (có thể rỗng).
     */
    public ChiTietXuatHang(String maXuatHang, String maBinhGas, String tenBinhGas, int soLuongXuat, double donGiaXuat, String ghiChu) {
        this.maXuatHang = maXuatHang;
        this.maBinhGas = maBinhGas;
        this.tenBinhGas = tenBinhGas;
        this.soLuongXuat = soLuongXuat;
        this.donGiaXuat = donGiaXuat;
        this.ghiChu = ghiChu;
    }

    // --- Getters và Setters ---

    public String getMaXuatHang() {
        return maXuatHang;
    }

    public void setMaXuatHang(String maXuatHang) {
        this.maXuatHang = maXuatHang;
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

    public int getSoLuongXuat() {
        return soLuongXuat;
    }

    public void setSoLuongXuat(int soLuongXuat) {
        this.soLuongXuat = soLuongXuat;
    }

    public double getDonGiaXuat() {
        return donGiaXuat;
    }

    public void setDonGiaXuat(double donGiaXuat) {
        this.donGiaXuat = donGiaXuat;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    /**
     * Phương thức tính toán thành tiền cho dòng chi tiết này.
     *
     * @return Tổng tiền (Số lượng xuất * Đơn giá xuất).
     */
    public double getThanhTien() {
        return soLuongXuat * donGiaXuat;
    }

    /**
     * Ghi đè phương thức toString() để dễ dàng in thông tin đối tượng (hữu ích cho debug).
     */
    @Override
    public String toString() {
        return "ChiTietXuatHang{" +
               "maXuatHang='" + maXuatHang + '\'' +
               ", maBinhGas='" + maBinhGas + '\'' +
               ", tenBinhGas='" + tenBinhGas + '\'' +
               ", soLuongXuat=" + soLuongXuat +
               ", donGiaXuat=" + donGiaXuat +
               ", thanhTien=" + getThanhTien() +
               ", ghiChu='" + ghiChu + '\'' +
               '}';
    }
}