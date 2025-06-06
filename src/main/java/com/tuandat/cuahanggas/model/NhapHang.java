// File: com/tuandat/cuahanggas/model/NhapHang.java
package com.tuandat.cuahanggas.model;

import java.util.Date; // For SQL DATE type

public class NhapHang {
    private String maNhapHang;
    private Date ngayNhap;
    private String maNhaCungCap; // Foreign Key
    private String maNhanVien;  // Foreign Key
    private String ghiChu;

    // Default constructor
    public NhapHang() {
    }

    // Constructor with all fields
    public NhapHang(String maNhapHang, Date ngayNhap, String maNhaCungCap, String maNhanVien, String ghiChu) {
        this.maNhapHang = maNhapHang;
        this.ngayNhap = ngayNhap;
        this.maNhaCungCap = maNhaCungCap;
        this.maNhanVien = maNhanVien;
        this.ghiChu = ghiChu;
    }

    // --- Getters and Setters ---
    public String getMaNhapHang() {
        return maNhapHang;
    }

    public void setMaNhapHang(String maNhapHang) {
        this.maNhapHang = maNhapHang;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "NhapHang{" +
               "maNhapHang='" + maNhapHang + '\'' +
               ", ngayNhap=" + ngayNhap +
               ", maNhaCungCap='" + maNhaCungCap + '\'' +
               ", maNhanVien='" + maNhanVien + '\'' +
               ", ghiChu='" + ghiChu + '\'' +
               '}';
    }
}