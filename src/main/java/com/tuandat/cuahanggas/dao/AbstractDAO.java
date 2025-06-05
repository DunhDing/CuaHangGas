package com.tuandat.cuahanggas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractDAO<T> implements IGenericDAO<T> {
    protected Connection conn;

    public AbstractDAO(Connection conn) {
        this.conn = conn;
    }

    // Các phương thức abstract cần được triển khai bởi lớp con
    protected abstract String getTableName();
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;
    protected abstract Object getEntityId(T entity); // Để lấy ID của entity cho update/delete
    protected abstract String getIdColumnName(); // Tên cột ID trong DB
    protected abstract Map<String, Object> getInsertValues(T entity);
    protected abstract Map<String, Object> getUpdateValues(T entity);

    @Override
    public boolean insert(T entity) {
        Map<String, Object> values = getInsertValues(entity);
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(getTableName()).append(" (");
        sql.append(String.join(", ", values.keySet()));
        sql.append(") VALUES (");
        sql.append(values.keySet().stream().map(k -> "?").collect(Collectors.joining(", ")));
        sql.append(")");

        System.out.println("SQL Insert: " + sql.toString()); // Debug SQL

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int i = 1;
            for (Object value : values.values()) {
                stmt.setObject(i++, value);
            }
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm " + getTableName() + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(T entity) {
        Map<String, Object> values = getUpdateValues(entity);
        StringBuilder sql = new StringBuilder("UPDATE ").append(getTableName()).append(" SET ");

        List<String> setClauses = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            setClauses.add(entry.getKey() + " = ?");
            params.add(entry.getValue());
        }
        sql.append(String.join(", ", setClauses));
        sql.append(" WHERE ").append(getIdColumnName()).append(" = ?");
        params.add(getEntityId(entity)); // Giá trị ID để xác định bản ghi cần cập nhật

        System.out.println("SQL Update: " + sql.toString()); // Debug SQL

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int i = 1;
            for (Object param : params) {
                stmt.setObject(i++, param);
            }
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật " + getTableName() + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Object id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName() + " = ?";
        System.out.println("SQL Delete: " + sql); // Debug SQL

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa từ " + getTableName() + " với ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public T findById(Object id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " = ?";
        System.out.println("SQL FindById: " + sql); // Debug SQL

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm " + getTableName() + " theo ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        System.out.println("SQL GetAll: " + sql); // Debug SQL

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy tất cả từ " + getTableName() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return entities;
    }
}
