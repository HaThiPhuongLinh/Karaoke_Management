package UI.component;

import ConnectDB.ConnectDB;
import Entity.Staff;

import javax.swing.*;

public class ListBill_UI extends JPanel {
    public static Staff staffLogin = null;
    private JLabel backgroundLabel;
    public ListBill_UI(Staff staff) {
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        //phan viet code
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }


        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);
    }
}
