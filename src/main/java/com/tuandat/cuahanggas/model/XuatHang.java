// File: com/tuandat/cuahanggas/model/XuatHang.java
package com.tuandat.cuahanggas.model;

import java.util.Date; // For DATE type in SQL

public class XuatHang {
    private String maXuatHang;
    private Date ngayXuat;
    private String maKhachHang; // Foreign Key
    private String maNhanVien;  // Foreign Key
    private String ghiChu;

    // Default constructor
    public XuatHang() {
    }

    // Constructor with all fields
    public XuatHang(String maXuatHang, Date ngayXuat, String maKhachHang, String maNhanVien, String ghiChu) {
        this.maXuatHang = maXuatHang;
        this.ngayXuat = ngayXuat;
        this.maKhachHang = maKhachHang;
        this.maNhanVien = maNhanVien;
        this.ghiChu = ghiChu;
    }

    // --- Getters and Setters ---
    public String getMaXuatHang() {
        return maXuatHang;
    }

    public void setMaXuatHang(String maXuatHang) {
        this.maXuatHang = maXuatHang;
    }

    public Date getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(Date ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
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

    public void voidsetGhiChu(String ghiChu) { // Corrected method name: setGhiChu
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "XuatHang{" +
               "maXuatHang='" + maXuatHang + '\'' +
               ", ngayXuat=" + ngayXuat +
               ", maKhachHang='" + maKhachHang + '\'' +
               ", maNhanVien='" + maNhanVien + '\'' +
               ", ghiChu='" + ghiChu + '\'' +
               '}';
    }
}