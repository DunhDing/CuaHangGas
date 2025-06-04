/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tuandat.cuahanggas.dao;

import java.util.List;

public interface IGenericDAO<T> {
    boolean insert(T entity);
    boolean update(T entity); // Thay đổi để cập nhật dựa trên ID có trong entity
    boolean delete(Object id); // Xóa theo ID
    T findById(Object id);
    List<T> getAll();
}
