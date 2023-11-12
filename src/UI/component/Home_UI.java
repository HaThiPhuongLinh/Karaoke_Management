package UI.component;

import javax.swing.*;

/**
 * Giao diện hiển thị background chính
 * Người tham gia thiết kế: Hà Thị Phương Linh
 * Ngày tạo: 01/11/2023
 * Lần cập nhật cuối: 01/11/2023
 * Nội dung cập nhật: Thêm hp2.jpg
 */
public class Home_UI extends JPanel {
    private JLabel lblBackGround;
    public Home_UI(){
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/hp2.jpg"));
        lblBackGround = new JLabel(backgroundImage);
        lblBackGround.setBounds(0, 0, 1475, 770);
        add(lblBackGround);
    }
}
