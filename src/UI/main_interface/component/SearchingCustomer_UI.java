package UI.main_interface.component;

import ConnectDB.ConnectDB;
import DAOs.CustomerDAO;
import Entity.Customer;
import Entity.Service;
import Entity.TypeOfService;
import UI.CustomUI.Custom;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchingCustomer_UI extends JPanel implements ActionListener {
    private  JButton btnlamMoi;
    private DefaultTableModel modelTableKH;
    private JLabel backgroundLabel, timeLabel, search1Label, search2Label, search3Label;
    private JTextField txtSearch1, txtSearch2, txtSearch3;
    private JPanel timeNow, pnlCusList, pnlCusControl, pnlCusListRight;
    private DefaultTableModel tableModel;
    private JButton btnTim;
    private CustomerDAO CustomerDAO;

    public SearchingCustomer_UI() {
        setLayout(null);
        setBounds(0, 0, 1175, 770);
        CustomerDAO = new CustomerDAO();
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //phan viet code
        JLabel headerLabel = new JLabel("TÌM KIẾM KHÁCH HÀNG");
        headerLabel.setBounds(470, 10, 1175, 40);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        Component add = add(headerLabel);

        timeNow = new JPanel();
        timeNow.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
                TitledBorder.LEADING, TitledBorder.TOP));
        timeNow.setBounds(12, 10, 300, 50);
        timeNow.setOpaque(false);
        add(timeNow);

        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.BOLD, 33));
        timeLabel.setForeground(Color.WHITE);
        timeNow.add(timeLabel);
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();

        pnlCusList = new JPanel();
        pnlCusList.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Khách Hàng",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlCusList.setBounds(10, 70, 1120, 620);
        pnlCusList.setOpaque(false);
        add(pnlCusList);
        pnlCusList.setLayout(new BorderLayout(0, 0));


        pnlCusControl = new JPanel();
        pnlCusControl.setOpaque(false);
        pnlCusControl.setBackground(Color.WHITE);
        pnlCusList.add(pnlCusControl, BorderLayout.NORTH);
        pnlCusControl.setLayout(null);
        pnlCusControl.setPreferredSize(new Dimension(1100, 230));

        search1Label = new JLabel("Tìm Theo Tên: ");

        search1Label.setFont(new Font("Arial", Font.PLAIN, 14));
        search1Label.setBounds(330, 25, 120, 30);
        search1Label.setForeground(Color.WHITE);
        pnlCusControl.add(search1Label);

        txtSearch1 = new JTextField();
        txtSearch1.setBounds(465, 25, 280, 30);
        pnlCusControl.add(txtSearch1);

        btnTim = new JButton("Tìm kiếm");
        btnTim.setBounds(645, 195, 100, 30);
        btnTim.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnTim);
        pnlCusControl.add(btnTim);

        btnlamMoi = new JButton("Làm mới");
        btnlamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnlamMoi);
        btnlamMoi.setBounds(535, 195, 100, 30);
        pnlCusControl.add(btnlamMoi);


        search2Label = new JLabel("Tìm Theo SDT: ");
        search2Label.setFont(new Font("Arial", Font.PLAIN, 14));
        search2Label.setBounds(330, 85, 120, 30);
        search2Label.setForeground(Color.WHITE);
        pnlCusControl.add(search2Label);

        txtSearch2 = new JTextField();
        txtSearch2.setBounds(465, 85, 280, 30);
        pnlCusControl.add(txtSearch2);


        search3Label = new JLabel("Tìm Theo CCCD: ");

        search3Label.setFont(new Font("Arial", Font.PLAIN, 14));
        search3Label.setBounds(330, 145, 120, 30);
        search3Label.setForeground(Color.WHITE);
        pnlCusControl.add(search3Label);

        txtSearch3 = new JTextField();

        txtSearch3.setBounds(465, 145, 280, 30);
        pnlCusControl.add(txtSearch3);

        JPanel panelDSKH = new JPanel();
        panelDSKH.setLayout(null);
        panelDSKH.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH KHÁCH HÀNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSKH.setBounds(30, 310, 1100, 320);
        panelDSKH.setOpaque(false);

        String[] colsKH = {"STT", "Mã KH", "Tên KH", "SDT", "CCCD", "Giới Tính", "Ngày Sinh"};
        modelTableKH = new DefaultTableModel(colsKH, 0);
        JScrollPane scrollPaneKH;

        JTable tableKH = new JTable(modelTableKH);
        tableKH.setFont(new Font("Arial", Font.BOLD, 14));
        tableKH.setBackground(new Color(255, 255, 255, 0));
        tableKH.setForeground(new Color(255, 255, 255));
        tableKH.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableKH.getTableHeader().setForeground(Color.BLUE);
        Custom.getInstance().setCustomTable(tableKH);

        panelDSKH.add(scrollPaneKH = new JScrollPane(tableKH, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneKH.setBounds(10, 20, 1090, 330);
        scrollPaneKH.setOpaque(false);
        scrollPaneKH.getViewport().setOpaque(false);
        scrollPaneKH.getViewport().setBackground(Color.WHITE);
        pnlCusList.add(panelDSKH);
        loadKH();
        btnTim.addActionListener(this);
        btnlamMoi.addActionListener(this);


        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);
    }

    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        timeLabel.setText(time);
    }

    public void loadKH() {
        int i = 1;
        String gt = "";
        for (Customer customer : CustomerDAO.getAllKhachHang()) {


            if (customer.isGioiTinh() == true) {

                gt = "Nam";

            } else {
                gt = "Nữ";
            }
            Object[] rowData = {i, customer.getMaKhachHang(), customer.getTenKhachHang(), customer.getSoDienThoai(), customer.getCCCD(), gt, customer.getNgaySinh()};
            modelTableKH.addRow(rowData);
            i++;

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnTim)) {

            String txtTenKH = txtSearch1.getText();
            ArrayList<Customer> cus1 = (ArrayList<Customer>) CustomerDAO.getListKhachHangByName(txtTenKH);

            String txtSDT = txtSearch2.getText();
            ArrayList<Customer> cus2 = (ArrayList<Customer>) CustomerDAO.getListKhachHangBySDT(txtSDT);

            String txtcccd = txtSearch3.getText();
            ArrayList<Customer> cus3 = (ArrayList<Customer>) CustomerDAO.getListKhachHangByCCCD(txtcccd);

//            String txtTenLDV = textFieldTenLoaiDichVu.getText();
//            ArrayList<TypeOfService> typeOfServices = typeOfServiceDAO.getTypeOfServiceByName(txtTenLDV);
            if (txtSearch1.getText().trim().equals("") && txtSearch2.getText().trim().equals("") && txtSearch3.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin tìm kiếm");
            } else if (!txtSearch1.getText().trim().equals("")) {
                modelTableKH.getDataVector().removeAllElements();
                int i = 1;
                if (cus1.size() != 0) {
                    for (Customer customer : cus1) {
                        modelTableKH.addRow(new Object[]{i, customer.getMaKhachHang(), customer.getTenKhachHang(), customer.getSoDienThoai(), customer.getCCCD(), customer.isGioiTinh(), customer.getNgaySinh()});
                        i++;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng");
                    txtSearch1.selectAll();
                    txtSearch1.requestFocus();
                }
            } else if (!txtSearch2.getText().trim().equals("")) {
                modelTableKH.getDataVector().removeAllElements();
                int i = 1;
                if (cus2.size() != 0) {
                    for (Customer customer : cus2) {
                        modelTableKH.addRow(new Object[]{i, customer.getMaKhachHang(), customer.getTenKhachHang(), customer.getSoDienThoai(), customer.getCCCD(), customer.isGioiTinh(), customer.getNgaySinh()});
                        i++;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng");
                    txtSearch2.selectAll();
                    txtSearch2.requestFocus();
                }
            } else if (!txtSearch3.getText().trim().equals("")) {
                modelTableKH.getDataVector().removeAllElements();
                int i = 1;
                if (cus3.size() != 0) {
                    for (Customer customer : cus3) {
                        modelTableKH.addRow(new Object[]{i, customer.getMaKhachHang(), customer.getTenKhachHang(), customer.getSoDienThoai(), customer.getCCCD(), customer.isGioiTinh(), customer.getNgaySinh()});
                        i++;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng");
                    txtSearch3.selectAll();
                    txtSearch3.requestFocus();
                }
            }
        }else if(o.equals(btnlamMoi)){
            txtSearch2.setText("");
            txtSearch1.setText("");
            txtSearch3.setText("");


            modelTableKH.getDataVector().removeAllElements();
            loadKH();
        }
    }
}
