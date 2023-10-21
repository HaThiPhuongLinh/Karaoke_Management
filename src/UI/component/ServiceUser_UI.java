package UI.component;

import javax.swing.*;

public class ServiceUser_UI extends JPanel {
    private JLabel backgroundLabel;

    public ServiceUser_UI(){
        setLayout(null);
        setBounds(0, 0, 1175, 770);

        //phan viet code



        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);
    }
}
