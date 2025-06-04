package com.tuandat.cuahanggas.model;

public class TaiKhoanNguoiDung {
    private String maTaiKhoan;
    private String tenDangNhap;
    private String matKhau;
    private String maVaiTro;
    private String maNhanVien;
    private String ghiChu;

    public TaiKhoanNguoiDung() {
    }

    public TaiKhoanNguoiDung(String maTaiKhoan, String tenDangNhap, String matKhau, String maVaiTro, String maNhanVien, String ghiChu) {
        this.maTaiKhoan = maTaiKhoan;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.maVaiTro = maVaiTro;
        this.maNhanVien = maNhanVien;
        this.ghiChu = ghiChu;
    }

    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getMaVaiTro() {
        return maVaiTro;
    }

    public void setMaVaiTro(String maVaiTro) {
        this.maVaiTro = maVaiTro;
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
}

