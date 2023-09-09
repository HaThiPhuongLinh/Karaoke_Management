package UI.main_interface.component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DatPhong extends JPanel {
    private JTextField textField;
    private DefaultTableModel tableModel;
    public DatPhong() {
        setLayout(null);
        setBackground(Color.BLACK);
        setBounds(0,0,1175,770);
        JLabel headerLabel = new JLabel("Đặt Phòng DEMO");
        headerLabel.setBounds(480, 10, 1175, 40);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        add(headerLabel);
    }

}
