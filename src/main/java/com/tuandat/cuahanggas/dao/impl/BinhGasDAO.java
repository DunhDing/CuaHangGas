/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandat.cuahanggas.dao.impl;

import com.tuandat.cuahanggas.dao.AbstractDAO;
import com.tuandat.cuahanggas.model.BinhGas;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BinhGasDAO extends AbstractDAO<BinhGas> {

    public BinhGasDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() {
        return "BinhGas";
    }

    @Override
    protected BinhGas mapResultSetToEntity(ResultSet rs) throws SQLException {
        BinhGas binhGas = new BinhGas();
        binhGas.setMaBinhGas(rs.getString("MaBinhGas"));
        binhGas.setTenBinhGas(rs.getString("TenBinhGas"));
        binhGas.setLoaiBinh(rs.getString("LoaiBinh"));
        binhGas.setLoaiVan(rs.getString("LoaiVan"));
        binhGas.setSoLuong(rs.getInt("SoLuong"));
        binhGas.setGiaVonTrungBinh(rs.getInt("GiaVonTrungBinh"));
        binhGas.setGhiChu(rs.getString("GhiChu"));
        return binhGas;
    }

    @Override
    protected Object getEntityId(BinhGas entity) {
        return entity.getMaBinhGas();
    }

    @Override
    protected String getIdColumnName() {
        return "MaBinhGas"; // Tên cột khóa chính trong DB
    }

    @Override
    protected Map<String, Object> getInsertValues(BinhGas entity) {
        Map<String, Object> values = new HashMap<>();
        values.put("MaBinhGas", entity.getMaBinhGas());
        values.put("TenBinhGas", entity.getTenBinhGas());
        values.put("LoaiBinh", entity.getLoaiBinh());
        values.put("LoaiVan", entity.getLoaiVan());
        values.put("SoLuong", entity.getSoLuong());
        values.put("GiaVonTrungBinh", entity.getGiaVonTrungBinh());
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }

    @Override
    protected Map<String, Object> getUpdateValues(BinhGas entity) {
        Map<String, Object> values = new HashMap<>();
        // Không bao gồm khóa chính ở đây nếu nó không được phép cập nhật
        values.put("TenBinhGas", entity.getTenBinhGas());
        values.put("LoaiBinh", entity.getLoaiBinh());
        values.put("LoaiVan", entity.getLoaiVan());
        values.put("SoLuong", entity.getSoLuong());
        values.put("GiaVonTrungBinh", entity.getGiaVonTrungBinh());
        values.put("GhiChu", entity.getGhiChu());
        return values;
    }
}
