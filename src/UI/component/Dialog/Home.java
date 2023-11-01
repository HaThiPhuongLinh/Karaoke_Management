package UI.component.Dialog;

import javax.swing.*;

public class Home extends JPanel {
    private JLabel backgroundLabel;
    public Home(){
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/hp2.jpg"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 1475, 770);
        add(backgroundLabel);
    }
}
