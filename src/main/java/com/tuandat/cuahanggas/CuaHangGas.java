package com.tuandat.cuahanggas;

import com.tuandat.cuahanggas.ui.frmLogin;
import com.tuandat.cuahanggas.ui.frmMain;

public class CuaHangGas {

    public static void main(String[] args) {
        while (true) {
            frmLogin login = new frmLogin(null, true);
            login.setLocationRelativeTo(null);
            login.setVisible(true);

            if (login.isLoginSuccess()) {
                frmMain main = new frmMain();
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
