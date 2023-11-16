package UI;
import ConnectDB.ConnectDB;
import javax.swing.*;
import javax.swing.border.Border;
import DAO.AccountDAO;
import DAO.StaffDAO;
import Entity.Account;
import Entity.Staff;
import UI.component.Dialog.Main;
import menu.Menu;

import java.awt.*;
import java.awt.event.*;

/**
 * Giao diện đăng nhập
 * Người tham gia thiết kế: Hà Thị Phương Linh
 * Ngày tạo: 10/09/2023
 * Lần cập nhật cuối: 17/10/2023
 * Nội dung cập nhật: phân quyền nhân viên khi đăng nhập
 */
public class LoginUI extends JFrame{
    private JTextField txtUserName;
    private JPasswordField txtPassWord;
    private JLabel lblErrorMessage;
    private final Border borderBottomFocus = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#b775d5"));
    private final Border borderBottomFocusDark = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#321a3d"));

    private AccountDAO logInDAO = new AccountDAO();
    private StaffDAO staffDAO = new StaffDAO();
    public static Menu menu;

    public LoginUI() {
        setTitle("Login Form");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        staffDAO = new StaffDAO();
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon karaokeImage = new ImageIcon(getClass().getResource("/images/bgLogIn.png"));
                g.drawImage(karaokeImage.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        leftPanel.setPreferredSize(new Dimension(375, getHeight()));

        JPanel rightPanel = new JPanel(null);
        rightPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(50, 80, 300, 35);
        rightPanel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(70, 140, 280, 15);
        rightPanel.add(usernameLabel);
        txtUserName = new JTextField();
        txtUserName.setText("maithaivu");
        txtUserName.setBounds(70, 160, 280, 30);
        txtUserName.setBorder(borderBottomFocus);
        rightPanel.add(txtUserName);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(70, 210, 280, 15);
        rightPanel.add(passwordLabel);
        txtPassWord = new JPasswordField();
        txtPassWord.setText("1");
        txtPassWord.setBounds(70, 230, 280, 30);
        txtPassWord.setBorder(borderBottomFocus);
        rightPanel.add(txtPassWord);

        lblErrorMessage = new JLabel("");
        lblErrorMessage.setForeground(Color.RED);
        lblErrorMessage.setBounds(70, 270, 280, 15);
        rightPanel.add(lblErrorMessage);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBackground(Color.decode("#6d4b7c"));
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBounds(70, 300, 280, 30);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.decode("#6d4b7c"), 0));
        loginButton.setFocusPainted(false);
        rightPanel.add(loginButton);

        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        txtUserName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtUserName.setBorder(borderBottomFocusDark);
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtUserName.setBorder(borderBottomFocus);
            }
        });

        txtUserName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });
        txtPassWord.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtPassWord.setBorder(borderBottomFocusDark);
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtPassWord.setBorder(borderBottomFocus);
            }
        });

        txtPassWord.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    txtPassWord.setText("");
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = txtUserName.getText();
                String enteredPassword = new String(txtPassWord.getPassword());
                Account user = new Account(enteredUsername,enteredPassword,null);

                int tinhTrang = logInDAO.checkLogin(user);
                if (tinhTrang == 1) {
                    Staff staff = staffDAO.getStaffByUsername(enteredUsername);
                    try {
                        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                            if ("Nimbus".equals(info.getName())) {
                                UIManager.setLookAndFeel(info.getClassName());
                                break;
                            }
                        }
                    } catch (ClassNotFoundException ex) {
                        java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                    dispose();
                    SwingUtilities.invokeLater( () -> {
                        new Main(staff).setVisible(true);
                    });
                } else if (tinhTrang == 0) {
                    lblErrorMessage.setForeground(Color.RED);
                    lblErrorMessage.setText("Tài khoản đã bị vô hiệu hóa.");
                } else if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                    lblErrorMessage.setForeground(Color.RED);
                    lblErrorMessage.setText("Nhập đủ tài khoản và mật khẩu.");
                } else {
                    lblErrorMessage.setForeground(Color.RED);
                    lblErrorMessage.setText("Mật khẩu hoặc tài khoản không đúng.");
                }
            }
        });



        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                loginButton.setBackground(Color.BLACK);
                loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent evt) {
                loginButton.setBackground(Color.decode("#6d4b7c"));
                loginButton.setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { new LoginUI().setVisible(true);
        });
    }
}