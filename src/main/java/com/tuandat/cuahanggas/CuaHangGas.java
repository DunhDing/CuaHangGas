package com.tuandat.cuahanggas;

import com.tuandat.cuahanggas.ui.frmMain;

public class CuaHangGas {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new frmMain().setVisible(true);
        });
    }
}
