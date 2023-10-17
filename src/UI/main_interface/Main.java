package UI.main_interface;

import Entity.Staff;
import UI.main_interface.component.*;
import UI.main_interface.swing.RoundPanel;
import menu.Menu;
import menu.MenuAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static Main instance;
    public static JPanel body;
    private JButton minimizeButton;
    private JButton exitButton;
    private Header header2;
    private Menu menu21;
    private RoundPanel roundPanel1;
    private static Staff staffLogin = null;

    public Main(Staff staff) {
        initComponents();
        staffLogin = staff;

        if (staffLogin != null) {
            menu21.setHeader(staffLogin.getTenNhanVien());
        }

        setBackground(new Color(0, 0, 0, 0));
        menu21.addMenuEvent((int index, int subIndex, MenuAction action) -> {
            if (index == 0) {
                if (subIndex == 1) {
                    showForm(new SearchingCustomer_UI());
                } else if (subIndex == 2) {
                    showForm(new Customer_UI());
                }
            } else if (index == 1) {
                if (subIndex == 1) {
                    showForm(new SearchingStaff_UI());
                } else if (subIndex == 2) {
                    showForm(new Staff_UI());
                }
            } else if (index == 2) {
                if (subIndex == 1) {
                    showForm(new SearchingRoom());
                } else if (subIndex == 2) {
                    showForm(new Room_UI());

                } else if (subIndex == 3) {
                    showForm(new TypeOfRoom_UI());

                } else if (subIndex == 4) {
                    showForm(new KaraokeBooking_UI());
                }
            } else if (index == 3) {
                if (subIndex == 1) {
                    showForm(new SearchingService_UI());
                } else if (subIndex == 2) {
                    showForm(new Service_UI());
                } else if (subIndex == 3) {
                    showForm(new TypeOfService_UI());
                } else if (subIndex == 4) {
                    showForm(new ServiceForm_UI());
                }
            } else if (index == 4) {
                if (subIndex == 1) {
                    showForm(new Bill_UI());
                }
            } else if (index == 5) {
                if (subIndex == 1) {
                    showForm(new Statistic_UI());
                } else if (subIndex == 2) {
                    showForm(new StatisticCustomer_UI());
                } else if (subIndex == 3) {
                    showForm(new StatisticService_UI());
                }
            } else {
                action.cancel();
            }
        });
        showForm(new KaraokeBooking_UI());
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
        SwingUtilities.invokeLater(() -> {
            new Main(staffLogin).setVisible(true);
        });
    }

    private void showForm(Component com) {
        body.removeAll();
        body.add(com);
        body.revalidate();
        body.repaint();
    }
    public static Main getInstance(Staff staffLogin) {
        if (instance == null)
            instance = new Main(staffLogin);
        return instance;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        roundPanel1 = new RoundPanel();
        header2 = new Header();
        menu21 = new Menu();
        body = new JPanel();
        body.setBackground(Color.WHITE);


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        roundPanel1.setBackground(new Color(25, 25, 25));

        body.setOpaque(false);
        body.setLayout(new BorderLayout());

        minimizeButton = new JButton("–");
        minimizeButton.setFont(new Font("Arial", Font.PLAIN, 15));
        minimizeButton.setBounds(1300, 12, 50, 30);
        minimizeButton.setFocusPainted(false);
        minimizeButton.setBorderPainted(false);
        minimizeButton.setContentAreaFilled(false);
        minimizeButton.setOpaque(false);
        minimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        minimizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                minimizeButton.setForeground(Color.WHITE);
                minimizeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                minimizeButton.setForeground(Color.BLACK);
                minimizeButton.setCursor(Cursor.getDefaultCursor());
            }
        });

        header2.add(minimizeButton);

        exitButton = new JButton("X");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 15));
        exitButton.setBounds(1350, 12, 50, 30);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setOpaque(false);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButton.setForeground(Color.WHITE);
                exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButton.setForeground(Color.BLACK);
                exitButton.setCursor(Cursor.getDefaultCursor());
            }
        });
        header2.add(exitButton);
        GroupLayout roundPanel1Layout = new GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(roundPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(header2, GroupLayout.DEFAULT_SIZE, 1400, Short.MAX_VALUE).addGroup(roundPanel1Layout.createSequentialGroup().addGap(10, 10, 10).addComponent(menu21, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE).addGap(10, 10, 10).addComponent(body, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(10, 10, 10)));
        roundPanel1Layout.setVerticalGroup(roundPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(roundPanel1Layout.createSequentialGroup().addComponent(header2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(10, 10, 10).addGroup(roundPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(menu21, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE).addComponent(body, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGap(10, 10, 10)));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(roundPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(roundPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    // End of variables declaration//GEN-END:variables
}
