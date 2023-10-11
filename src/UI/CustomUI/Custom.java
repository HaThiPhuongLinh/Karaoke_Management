package UI.CustomUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class Custom {
    private static Custom instance = new Custom();

    public static Custom getInstance() {
        if (instance == null)
            instance = new Custom();
        return instance;
    }
    public static void setCustomBtn(JButton btn) {
        btn.setBackground(Color.decode("#d0e1fd"));
        btn.setForeground(Color.decode("#1a66e3"));
        btn.setBorder(new LineBorder(Color.BLUE, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    public static void setCustomComboBox(JComboBox<?> cbo) {
        cbo.setFont(new Font("Arial", Font.PLAIN, 15));
    }

    public static void setCustomTable(JTable tbl) {
        tbl.setFont(new Font("Arial", Font.PLAIN, 13));
        tbl.setForeground(Color.WHITE);
        tbl.setBackground(new Color(0, 41, 169));
        tbl.setSelectionBackground(Color.decode("#6666FF"));
        tbl.setRowHeight(25);
        tbl.setShowGrid(true);
        tbl.setGridColor(Color.WHITE);
        tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


}
