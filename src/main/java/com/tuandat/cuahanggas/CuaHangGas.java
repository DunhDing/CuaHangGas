package com.tuandat.cuahanggas;

import com.tuandat.cuahanggas.ui.frmMain;
import javax.swing.SwingUtilities;

public class CuaHangGas {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frmMain form = new frmMain(); 
            form.setVisible(true); 
        });
    }
}
