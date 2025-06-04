/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandat.cuahanggas.model;

/**
 *
 * @author PC
 */
import java.util.Date; // Sử dụng java.util.Date cho NgaySinh

public class KhachHang {
    private String maKhachHang;
    private String hoTen;
    private String gioiTinh; // Nam hoặc Nữ
    private Date ngaySinh;
    private String sdt;
    private String email;
    private String diaChi;
    private String ghiChu;

    // Constructor rỗng
    public KhachHang() {
    }

    // Constructor đầy đủ
    public KhachHang(String maKhachHang, String hoTen, String gioiTinh, Date ngaySinh,
                     String sdt, String email, String diaChi, String ghiChu) {
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
        this.ghiChu = ghiChu;
    }

    // Getters và Setters cho tất cả các thuộc tính
    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
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
}
