package UI.component.Dialog;

import DAO.RoomDAO;
import Entity.Room;
import Entity.Staff;
import UI.LoginUI;
import UI.component.*;
import menu.swing.RoundPanel;
import menu.Header;
import menu.Menu;
import menu.MenuAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Giao diện chính (main) gồm header, menu và body
 * Người tham gia thiết kế: Hà Thị Phương Linh
 * Ngày tạo: 10/09/2023
 * Lần cập nhật cuối: 05/11/2023
 * Nội dung cập nhật: thêm menu listBill_UI
 */
public class Main extends JFrame {
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static Main instance;
    public static JPanel body;
    private JButton btnMinimize;
    private JButton btnExit;
    private Header header2;
    private Menu menu;
    private RoundPanel plnRound;
    private static Staff staffLogin = null;
    private RoomDAO roomDAO = new RoomDAO();

    /**
     * Khởi tạo Main
     * @param staff: thông tin nhân viên đăng nhập
     */
    public Main(Staff staff) {
        initComponents();
        staffLogin = staff;

        if (staffLogin != null) {
            menu.setHeader(staffLogin.getTenNhanVien());
        }
        PresetRoom presetRoom = PresetRoom.getInstance();
        presetRoom.setM(this);
        menu.setLogoutAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               dispose();
                LoginUI login = new LoginUI();
                login.setVisible(true);
            }
        });

        setBackground(new Color(0, 0, 0, 0));
        menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {
            if (index == 0) {
                if (subIndex == 1) {
                    showForm(new SearchingCustomer_UI(staffLogin));
                } else if (subIndex == 2) {
                    showForm(new Customer_UI(staffLogin));
                }
            } else if (index == 1) {
                if (subIndex == 1) {
                    showForm(new SearchingStaff_UI(staffLogin));
                } else if (subIndex == 2) {
                    if(staffLogin.getChucVu().equalsIgnoreCase("Quản lý")){
                    showForm(new Staff_UI(staffLogin));}
                    else {
                        JOptionPane.showMessageDialog(this,"Không có quyền truy cập");
                    }
                }
            } else if (index == 2) {
                if (subIndex == 1) {
                    showForm(new SearchingRoom_UI(staffLogin));
                } else if (subIndex == 2) {
                    if(staffLogin.getChucVu().equalsIgnoreCase("Quản lý")){
                        showForm(new Room_UI(staffLogin));}
                    else {
                        JOptionPane.showMessageDialog(this,"Không có quyền truy cập");
                    }
                } else if (subIndex == 3) {
                    if(staffLogin.getChucVu().equalsIgnoreCase("Quản lý")){
                        showForm(new TypeOfRoom_UI(staffLogin));}
                    else {
                        JOptionPane.showMessageDialog(this,"Không có quyền truy cập");
                    }
                } else if (subIndex == 4) {
                    showForm(new KaraokeBooking_UI(staffLogin));
                }
            } else if (index == 3) {
                if (subIndex == 1) {
                    showForm(new SearchingService_UI(staffLogin));
                } else if (subIndex == 2) {
                    if(staffLogin.getChucVu().equalsIgnoreCase("Quản lý")){
                        showForm(new Service_UI(staffLogin));}
                    else {
                        JOptionPane.showMessageDialog(this,"Không có quyền truy cập");
                    }
                } else if (subIndex == 3) {
                    if(staffLogin.getChucVu().equalsIgnoreCase("Quản lý")){
                        showForm(new TypeOfService_UI(staffLogin));}
                    else {
                        JOptionPane.showMessageDialog(this,"Không có quyền truy cập");
                    }
                } else if (subIndex == 4) {
                    showForm(new ServiceForm_UI(staffLogin));
                }
            } else if (index == 4) {
                if (subIndex == 1) {
                    showForm(new Bill_UI(staffLogin));
                }
                if (subIndex == 2) {
                    showForm(new ListBill_UI(staffLogin));
                }
            } else if (index == 5) {
                if (subIndex == 1) {
                    if(staffLogin.getChucVu().equalsIgnoreCase("Quản lý")){
                        showForm(new Statistic_UI(staffLogin));}
                    else {
                        JOptionPane.showMessageDialog(this,"Không có quyền truy cập");
                    }
                } else if (subIndex == 2) {
                    if(staffLogin.getChucVu().equalsIgnoreCase("Quản lý")){
                        showForm(new StatisticCustomer_UI(staffLogin));}
                    else {
                        JOptionPane.showMessageDialog(this,"Không có quyền truy cập");
                    }
                } else if (subIndex == 3) {
                    if(staffLogin.getChucVu().equalsIgnoreCase("Quản lý")){
                        showForm(new StatisticService_UI(staffLogin));}
                    else {
                        JOptionPane.showMessageDialog(this,"Không có quyền truy cập");
                    }
                }
            } else {
                action.cancel();
            }
        });
        showForm(new Home_UI());
    }

    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
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
        //</editor-fold>

        /* Create and display the form */
//        SwingUtilities.invokeLater(() -> {
//            new Main(staffLogin).setVisible(true);
//        });
    }

    /**
     * Hiển thị component lên body
     * @param com: component
     */
    public void showForm(Component com) {
        body.removeAll();
        body.add(com);
        body.revalidate();
        body.repaint();
    }

    /**
     * Tạo thể hiện hiện tại cho Main
     * @param staffLogin: thông tin nhân viên đăng nhập
     * @return
     */
    public static Main getInstance(Staff staffLogin) {
        if (instance == null)
            instance = new Main(staffLogin);
        return instance;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents

    /**
     * Khởi tạo các components từ header, menu và body
     */
    private void initComponents() {
        plnRound = new RoundPanel();
        header2 = new Header();
        menu = new Menu();
        body = new JPanel();
        body.setBackground(Color.WHITE);


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        plnRound.setBackground(new Color(25, 25, 25));

        body.setOpaque(false);
        body.setLayout(new BorderLayout());

        btnMinimize = new JButton("–");
        btnMinimize.setFont(new Font("Arial", Font.PLAIN, 15));
        btnMinimize.setBounds(1440, 12, 50, 30);
        btnMinimize.setFocusPainted(false);
        btnMinimize.setBorderPainted(false);
        btnMinimize.setContentAreaFilled(false);
        btnMinimize.setOpaque(false);
        btnMinimize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        btnMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMinimize.setForeground(Color.WHITE);
                btnMinimize.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMinimize.setForeground(Color.BLACK);
                btnMinimize.setCursor(Cursor.getDefaultCursor());
            }
        });

        header2.add(btnMinimize);

        btnExit = new JButton("X");
        btnExit.setFont(new Font("Arial", Font.PLAIN, 15));
        btnExit.setBounds(1480, 12, 50, 30);
        btnExit.setFocusPainted(false);
        btnExit.setBorderPainted(false);
        btnExit.setContentAreaFilled(false);
        btnExit.setOpaque(false);

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExit.setForeground(Color.WHITE);
                btnExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExit.setForeground(Color.BLACK);
                btnExit.setCursor(Cursor.getDefaultCursor());
            }
        });

        for (Room room : roomDAO.getRoomList()) {
            if (room.getTinhTrang().equals("Trong")) {
                roomDAO.updateRoomStatus(room.getMaPhong(), "Trống");
            }
            if (room.getTinhTrang().equals("Cho")) {
                roomDAO.updateRoomStatus(room.getMaPhong(), "Chờ");
            }
        }
        header2.add(btnExit);
        GroupLayout roundPanel1Layout = new GroupLayout(plnRound);
        plnRound.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(roundPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(header2, GroupLayout.DEFAULT_SIZE, 1534, Short.MAX_VALUE).addGroup(roundPanel1Layout.createSequentialGroup().addGap(10, 10, 10).addComponent(menu, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE).addGap(10, 10, 10).addComponent(body, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(10, 10, 10)));
        roundPanel1Layout.setVerticalGroup(roundPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(roundPanel1Layout.createSequentialGroup().addComponent(header2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(10, 10, 10).addGroup(roundPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(menu, GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE).addComponent(body, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGap(10, 10, 10)));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(plnRound, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(plnRound, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    // End of variables declaration//GEN-END:variables
}
