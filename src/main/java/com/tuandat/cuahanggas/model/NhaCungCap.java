// File: com/tuandat/cuahanggas/model/NhaCungCap.java
package com.tuandat.cuahanggas.model;

public class NhaCungCap {
    private String maNhaCungCap;
    private String tenNhaCungCap;
    private String sdt;
    private String email;
    private String diaChi;
    private String ghiChu;

    // Constructor mặc định (cần thiết cho một số framework hoặc khi tạo đối tượng rỗng)
    public NhaCungCap() {
    }

    // Constructor đầy đủ tham số
    public NhaCungCap(String maNhaCungCap, String tenNhaCungCap, String sdt, String email, String diaChi, String ghiChu) {
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
        this.ghiChu = ghiChu;
    }

    // --- Getters và Setters ---
    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "NhaCungCap{" +
               "maNhaCungCap='" + maNhaCungCap + '\'' +
               ", tenNhaCungCap='" + tenNhaCungCap + '\'' +
               ", sdt='" + sdt + '\'' +
               ", email='" + email + '\'' +
               ", diaChi='" + diaChi + '\'' +
               ", ghiChu='" + ghiChu + '\'' +
               '}';
    }
}