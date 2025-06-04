
package com.tuandat.cuahanggas.model;


public class BinhGas {
    private String maBinhGas;
    private String tenBinhGas;
    private String loaiBinh;
    private String loaiVan;
    private int soLuong;
    private int giaVonTrungBinh;
    private String ghiChu;

    // Constructor rỗng
    public BinhGas() {
    }

    // Constructor đầy đủ
    public BinhGas(String maBinhGas, String tenBinhGas, String loaiBinh, String loaiVan,
                   int soLuong, int giaVonTrungBinh, String ghiChu) {
        this.maBinhGas = maBinhGas;
        this.tenBinhGas = tenBinhGas;
        this.loaiBinh = loaiBinh;
        this.loaiVan = loaiVan;
        this.soLuong = soLuong;
        this.giaVonTrungBinh = giaVonTrungBinh;
        this.ghiChu = ghiChu;
    }

    // Getters và Setters cho tất cả các thuộc tính
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

    public String getLoaiBinh() {
        return loaiBinh;
    }

    public void setLoaiBinh(String loaiBinh) {
        this.loaiBinh = loaiBinh;
    }

    public String getLoaiVan() {
        return loaiVan;
    }

    public void setLoaiVan(String loaiVan) {
        this.loaiVan = loaiVan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaVonTrungBinh() {
        return giaVonTrungBinh;
    }

    public void setGiaVonTrungBinh(int giaVonTrungBinh) {
        this.giaVonTrungBinh = giaVonTrungBinh;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
