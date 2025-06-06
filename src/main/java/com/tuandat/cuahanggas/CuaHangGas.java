package com.tuandat.cuahanggas;

import com.tuandat.cuahanggas.dao.impl.BinhGasDAO;
import com.tuandat.cuahanggas.dao.impl.KhachHangDAO;
import com.tuandat.cuahanggas.dao.impl.NhanVienDAO;
import com.tuandat.cuahanggas.dao.impl.TaiKhoanNguoiDungDAO;
import com.tuandat.cuahanggas.ui.frmLogin;
import com.tuandat.cuahanggas.ui.frmMain;
import com.tuandat.cuahanggas.utils.DBConnection;
import javax.swing.JOptionPane;
import java.sql.Connection;

public class CuaHangGas {

    private static Connection appConnection;
    private static BinhGasDAO binhGasDAO;
    private static KhachHangDAO khachHangDAO;
    private static TaiKhoanNguoiDungDAO taiKhoanDAO;
    private static NhanVienDAO nhanVienDAO;

    public static void main(String[] args) {
        // Mở kết nối với cơ sở dữ liệu
        appConnection = DBConnection.openConnection();

        // Kiểm tra kết nối, nếu không thành công thì thông báo lỗi và thoát ứng dụng
        if (appConnection == null) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối cơ sở dữ liệu. Ứng dụng sẽ thoát.",
                    "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Khởi tạo các DAO chỉ một lần duy nhất
        binhGasDAO = new BinhGasDAO(appConnection);
        khachHangDAO = new KhachHangDAO(appConnection);
        taiKhoanDAO = new TaiKhoanNguoiDungDAO(appConnection);
        nhanVienDAO = new NhanVienDAO(appConnection);

        // Thêm Shutdown Hook để đóng kết nối khi JVM thoát
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Đóng kết nối CSDL khi ứng dụng thoát.");
            DBConnection.closeConnection();
        }));
//        java.awt.EventQueue.invokeLater(() -> {
//            new frmMain(binhGasDAO, taiKhoanDAO, nhanVienDAO, khachHangDAO).setVisible(true);
//        });
        while (true) {
            frmLogin login = new frmLogin(null, true, taiKhoanDAO);
            login.setLocationRelativeTo(null);
            login.setVisible(true);

            if (login.isLoginSuccess()) {
                frmMain main = new frmMain(binhGasDAO, taiKhoanDAO, nhanVienDAO, khachHangDAO);
                main.setLocationRelativeTo(null);
                main.setVisible(true);

                while (main.isDisplayable()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (main.isLogout()) {
                    continue;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        System.exit(0);
    }
}

