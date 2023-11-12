package UI.component;

import ConnectDB.ConnectDB;
import DAO.CustomerDAO;
import Entity.Customer;
import Entity.Staff;
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
/**
 * Giao diện dùng để tìm kiếm khách hàng
 * Người thiết kế Nguyễn Đình Dương
 * Ngày tạo:7/10/2023
 * Lần cập nhật cuối : 18/10/2023
 * Nội dung cập nhật : Sửa tính năng tìm theo SDT
 */
public class SearchingCustomer_UI extends JPanel implements ActionListener {
    private  JButton btnlamMoi;
    private DefaultTableModel modelTableKH;
    private JLabel lblBackground, lblTime, lblSearchbyName, lblSearchbyNumber, lblSearchbyCCCD;
    private JTextField txtSearchbyName, txtSearchbyNumber, txtSearchbyCCCD;
    private JPanel timeNow, pnlCusList, pnlCusControl;
    private JButton btnTim;
    private CustomerDAO CustomerDAO;
    public static Staff staffLogin = null;

    public SearchingCustomer_UI(Staff staff) {
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);
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

        lblTime = new JLabel();
        lblTime.setFont(new Font("Arial", Font.BOLD, 33));
        lblTime.setForeground(Color.WHITE);
        timeNow.add(lblTime);
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
        pnlCusList.setBounds(10, 70, 1240, 670);
        pnlCusList.setOpaque(false);
        add(pnlCusList);
        pnlCusList.setLayout(new BorderLayout(0, 0));


        pnlCusControl = new JPanel();
        pnlCusControl.setOpaque(false);
        pnlCusControl.setBackground(Color.WHITE);
        pnlCusList.add(pnlCusControl, BorderLayout.NORTH);
        pnlCusControl.setLayout(null);
        pnlCusControl.setPreferredSize(new Dimension(1100, 230));

        lblSearchbyName = new JLabel("Tìm Theo Tên: ");

        lblSearchbyName.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSearchbyName.setBounds(380, 25, 120, 30);
        lblSearchbyName.setForeground(Color.WHITE);
        pnlCusControl.add(lblSearchbyName);

        txtSearchbyName = new JTextField();
        txtSearchbyName.setBounds(515, 25, 280, 30);
        pnlCusControl.add(txtSearchbyName);

        btnTim = new JButton("Tìm kiếm");
        btnTim.setBounds(695, 195, 100, 30);
        btnTim.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnTim);
        pnlCusControl.add(btnTim);

        btnlamMoi = new JButton("Làm mới");
        btnlamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnlamMoi);
        btnlamMoi.setBounds(585, 195, 100, 30);
        pnlCusControl.add(btnlamMoi);


        lblSearchbyNumber = new JLabel("Tìm Theo SDT: ");
        lblSearchbyNumber.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSearchbyNumber.setBounds(380, 85, 120, 30);
        lblSearchbyNumber.setForeground(Color.WHITE);
        pnlCusControl.add(lblSearchbyNumber);

        txtSearchbyNumber = new JTextField();
        txtSearchbyNumber.setBounds(515, 85, 280, 30);
        pnlCusControl.add(txtSearchbyNumber);


        lblSearchbyCCCD = new JLabel("Tìm Theo CCCD: ");

        lblSearchbyCCCD.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSearchbyCCCD.setBounds(380, 145, 120, 30);
        lblSearchbyCCCD.setForeground(Color.WHITE);
        pnlCusControl.add(lblSearchbyCCCD);

        txtSearchbyCCCD = new JTextField();

        txtSearchbyCCCD.setBounds(515, 145, 280, 30);
        pnlCusControl.add(txtSearchbyCCCD);

        JPanel panelDSKH = new JPanel();
        panelDSKH.setLayout(null);
        panelDSKH.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH KHÁCH HÀNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSKH.setBounds(30, 310, 1220, 370);
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
        scrollPaneKH.setBounds(10, 20, 1210, 380);
        scrollPaneKH.setOpaque(false);
        scrollPaneKH.getViewport().setOpaque(false);
        scrollPaneKH.getViewport().setBackground(Color.WHITE);
        pnlCusList.add(panelDSKH);
        loadKH();
        btnTim.addActionListener(this);
        btnlamMoi.addActionListener(this);


        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        lblBackground = new JLabel(backgroundImage);
        lblBackground.setBounds(0, 0, getWidth(), getHeight());
        add(lblBackground);
    }

    /**
     * Thiết lập thời gian hiện tại cho lblTime
     */
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        lblTime.setText(time);
    }
    /**
     * hàm sử dụng định dạng "HH:mm:ss" để biểu diễn thời gian (giờ, phút và giây) của đối tượng date
     * @param date : ngày cần định dạng
     * @return {@code String}: ngày cần định dạng
     */
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
        return sdf.format(date);
    }

    /**
     * Đọc dữ liệu tất cả khách hàng lên bảng
     */
    public void loadKH() {
        int i = 1;
        String gt = "";
        for (Customer customer : CustomerDAO.getAllKhachHang()) {
            String date = formatDate(customer.getNgaySinh());
            if (customer.isGioiTinh() == true) {
                gt = "Nam";
            } else {
                gt = "Nữ";
            }
            Object[] rowData = {i, customer.getMaKhachHang(), customer.getTenKhachHang(), customer.getSoDienThoai(), customer.getCCCD(), gt, date};
            modelTableKH.addRow(rowData);
            i++;

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnTim)) {

            String txtTenKH = txtSearchbyName.getText();
            ArrayList<Customer> cus1 = (ArrayList<Customer>) CustomerDAO.getListKhachHangByName(txtTenKH);

            String txtSDT = txtSearchbyNumber.getText();
            ArrayList<Customer> cus2 = (ArrayList<Customer>) CustomerDAO.getListKhachHangBySDT(txtSDT);

            String txtcccd = txtSearchbyCCCD.getText();
            ArrayList<Customer> cus3 = (ArrayList<Customer>) CustomerDAO.getListKhachHangByCCCD(txtcccd);

            if (txtSearchbyName.getText().trim().equals("") && txtSearchbyNumber.getText().trim().equals("") && txtSearchbyCCCD.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin tìm kiếm");
            } else if (!txtSearchbyName.getText().trim().equals("")) {
                modelTableKH.getDataVector().removeAllElements();
                int i = 1;
                String gt = "";
                if (cus1.size() != 0) {
                    for (Customer customer : cus1) {
                        String date = formatDate(customer.getNgaySinh());
                        if (customer.isGioiTinh() == true) {
                            gt = "Nam";
                        } else {
                            gt = "Nữ";
                        }
                        modelTableKH.addRow(new Object[]{i, customer.getMaKhachHang(), customer.getTenKhachHang(), customer.getSoDienThoai(), customer.getCCCD(), gt, date});
                        i++;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng");
                    txtSearchbyName.selectAll();
                    txtSearchbyName.requestFocus();
                }
            } else if (!txtSearchbyNumber.getText().trim().equals("")) {
                modelTableKH.getDataVector().removeAllElements();
                int i = 1;
                String gt ="";
                if (cus2.size() != 0) {
                    for (Customer customer : cus2) {
                        String date = formatDate(customer.getNgaySinh());
                        if (customer.isGioiTinh() == true) {
                            gt = "Nam";
                        } else {
                            gt = "Nữ";
                        }
                        modelTableKH.addRow(new Object[]{i, customer.getMaKhachHang(), customer.getTenKhachHang(), customer.getSoDienThoai(), customer.getCCCD(), gt, date});
                        i++;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng");
                    txtSearchbyNumber.selectAll();
                    txtSearchbyNumber.requestFocus();
                }
            } else if (!txtSearchbyCCCD.getText().trim().equals("")) {
                modelTableKH.getDataVector().removeAllElements();
                int i = 1;
                String gt ="";
                if (cus3.size() != 0) {
                    for (Customer customer : cus3) {
                        String date = formatDate(customer.getNgaySinh());
                        if (customer.isGioiTinh() == true) {
                            gt = "Nam";
                        } else {
                            gt = "Nữ";
                        }
                        modelTableKH.addRow(new Object[]{i, customer.getMaKhachHang(), customer.getTenKhachHang(), customer.getSoDienThoai(), customer.getCCCD(),gt, date});
                        i++;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng");
                    txtSearchbyCCCD.selectAll();
                    txtSearchbyCCCD.requestFocus();
                }
            }
        }else if(o.equals(btnlamMoi)){
            txtSearchbyNumber.setText("");
            txtSearchbyName.setText("");
            txtSearchbyCCCD.setText("");


            modelTableKH.getDataVector().removeAllElements();
            loadKH();
        }
    }
}
