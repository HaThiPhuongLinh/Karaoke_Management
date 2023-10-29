package UI.CustomUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import javax.swing.border.EmptyBorder;
public class Custom {
    private static Custom instance = new Custom();
    private Font fontBold = new Font("Dialog", Font.BOLD, 14);
    private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
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
    public void setCustomLabel(JLabel lbl) {
        lbl.setFont(fontNormal);
        lbl.setForeground(Color.WHITE);
    }
    public void setCustomTableBill(JTable tbl) {
        tbl.setFont(fontNormal);
        tbl.setBackground(new Color(255, 255, 255, 0));
        tbl.setForeground(new Color(255, 255, 255));
        tbl.getTableHeader().setFont(fontBold);
        tbl.getTableHeader().setForeground(Color.decode("#9B17EB"));
        tbl.getTableHeader().setBackground(new Color(255, 255, 255));
    }
    public void setCustomLabelBill(JLabel lbl) {
        lbl.setBackground(Color.WHITE);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Dialog", Font.PLAIN, 16));
    }
    public void setCustomTextFieldBill(JTextField txt) {
        txt.setEditable(false);
        txt.setForeground(Color.WHITE);
        txt.setBorder(new EmptyBorder(0, 0, 0, 0));
        txt.setOpaque(false);
        txt.setFont(fontNormal);
    }
    public static void setCustomTable(JTable tbl) {
        tbl.setFont(new Font("Arial", Font.PLAIN, 14));
        tbl.setForeground(Color.WHITE);
        tbl.setBackground(Color.decode("#5F009D"));
        tbl.setSelectionBackground(Color.decode("#232D31"));
        tbl.setRowHeight(27);
        tbl.setShowGrid(true);
        tbl.setGridColor(Color.WHITE);
        tbl.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tbl.getTableHeader().setForeground(Color.BLUE);
    }


}
