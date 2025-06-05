package com.tuandat.cuahanggas;

import com.tuandat.cuahanggas.dao.impl.BinhGasDAO;
import com.tuandat.cuahanggas.dao.impl.KhachHangDAO;
import com.tuandat.cuahanggas.dao.impl.NhanVienDAO;
import com.tuandat.cuahanggas.dao.impl.TaiKhoanNguoiDungDAO;
import com.tuandat.cuahanggas.model.NhanVien;
import com.tuandat.cuahanggas.model.TaiKhoanNguoiDung;
import com.tuandat.cuahanggas.ui.frmLogin;
import com.tuandat.cuahanggas.ui.frmMain;
import com.tuandat.cuahanggas.utils.DBConnection;
import java.sql.Connection;
import javax.swing.JOptionPane;

public class CuaHangGas {
    private static Connection appConnection;
    private static BinhGasDAO binhGasDAO;
    private static KhachHangDAO khachHangDAO;
    private static TaiKhoanNguoiDungDAO taiKhoanDAO;
    private static NhanVienDAO nhanVienDAO;

    public static void main(String[] args) {
        // Bước 1: Mở kết nối CSDL MỘT LẦN duy nhất khi ứng dụng khởi động
        appConnection = DBConnection.openConnection();

        if (appConnection == null) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối cơ sở dữ liệu. Ứng dụng sẽ thoát.",
                                            "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Thoát ứng dụng nếu không có kết nối
        }
        
        // Bước 2: Khởi tạo các DAO MỘT LẦN duy nhất, truyền kết nối đã mở
        binhGasDAO = new BinhGasDAO(appConnection);
        khachHangDAO = new KhachHangDAO(appConnection);
        taiKhoanDAO = new TaiKhoanNguoiDungDAO(appConnection);
        nhanVienDAO = new NhanVienDAO(appConnection);
        
        // ... (Khởi tạo các DAO khác tương tự: nhanVienDAO, taiKhoanDAO, v.v.)

        // Thêm Shutdown Hook để đóng kết nối khi JVM thoát
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Dong ket noi CSDL khi ung dung thoat.");
            DBConnection.closeConnection();
        }));
        java.awt.EventQueue.invokeLater(() -> {
            new frmMain(binhGasDAO, taiKhoanDAO, nhanVienDAO, khachHangDAO).setVisible(true);
        });
//        while (true) {
//            frmLogin login = new frmLogin(null, true);
//            login.setLocationRelativeTo(null);
//            login.setVisible(true);
//
//            if (login.isLoginSuccess()) {
//                frmMain main = new frmMain(binhGasDAO, taiKhoanDAO);
//                main.setLocationRelativeTo(null);
//                main.setVisible(true);
//
//                while (main.isDisplayable()) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                if (main.isLogout()) {
//                    continue;
//                } else {
//                    break;
//                }
//            } else {
//                break;
//            }
//        }
//
//        System.exit(0);
    }
}
