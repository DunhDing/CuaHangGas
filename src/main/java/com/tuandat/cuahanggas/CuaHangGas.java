package com.tuandat.cuahanggas;

import com.tuandat.cuahanggas.ui.frmMain;
import com.tuandat.cuahanggas.ui.frmLogin;
import javax.swing.SwingUtilities;

public class CuaHangGas {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frmLogin login = new frmLogin(null, true); 
            login.setVisible(true); 
      
            if (login.isLoginSuccess()) {
                frmMain main = new frmMain();
                main.setVisible(true);
            } else {
                System.exit(0); 
            }
        });
    }
}
