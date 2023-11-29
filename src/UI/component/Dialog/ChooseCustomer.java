package UI.component.Dialog;

import DAO.CustomerDAO;
import Entity.Customer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import ConnectDB.ConnectDB;
import UI.CustomUI.Custom;
import UI.component.KaraokeBooking_UI;

/**
 * Giao diện chọn khách hàng
 * Người tham gia thiết kế: Hà Thị Phương Linh
 * Ngày tạo: 11/10/2023
 * Lần cập nhật cuối: 22/11/2023
 * Nội dung cập nhật: Sửa tính năng tìm khách hàng (btnFind)
 */
public class ChooseCustomer extends JFrame implements ActionListener, MouseListener {
    private JTable tblCustomer;
    private JButton btnFind, btnChoose, btnALL;
    private JTextField txtTim;
    private DefaultTableModel modelTblCustomer;
    private CustomerDAO customerDAO;
    private static KaraokeBooking_UI main;
    private List<Customer> lstCustomer;

    public ChooseCustomer(KaraokeBooking_UI main) {
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setAlwaysOnTop(true);
        this.main = main;

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        gui();
    }

    /**
     * Format ngày theo dạng dd-MM-yyyy
     * @param date: ngày kiểu Date
     * @return (@code String)
     */
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
        return sdf.format(date);
    }

    /**
     * Khởi tạo giao diện
     */
    private void gui() {
        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(110, 20, 650, 70);
        searchPanel.setLayout(null);
        JLabel nhapten = new JLabel("Tên khách hàng: ");
        nhapten.setBounds(80, 32, 180, 20);
        searchPanel.add(nhapten);
        txtTim = new JTextField();
        txtTim.setBounds(180, 29, 250, 28);
        searchPanel.add(txtTim);
        btnFind = new JButton("Tìm");
        btnFind.setBounds(450, 26, 55, 30);
        searchPanel.add(btnFind);


        btnALL = new JButton("Refresh");
        btnALL.setBounds(510, 26, 75, 30);
        searchPanel.add(btnALL);
        searchPanel.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                "Tìm khách hàng", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.add(searchPanel);
        Custom.setCustomBtn(btnALL);
        JPanel pnTable = new JPanel();
        pnTable.setBounds(20, 120, 850, 340);
        panel.add(pnTable);
        pnTable.setLayout(new BorderLayout(0, 0));
        String[] cols = { "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Ngày Sinh" };

        modelTblCustomer = new DefaultTableModel(cols, 0);
        tblCustomer = new JTable(modelTblCustomer);
        JScrollPane scpTable = new JScrollPane(tblCustomer);
        pnTable.add(scpTable, BorderLayout.CENTER);
        Custom.setCustomTable(tblCustomer);

        btnChoose = new JButton("Chọn");
        btnChoose.setBounds(380, 470, 100, 30);
        panel.add(btnChoose);

        Custom.setCustomBtn(btnFind);
        Custom.setCustomBtn(btnChoose);

        btnALL.setFont(new Font("Arial", Font.BOLD, 14));
        btnFind.setFont(new Font("Arial", Font.BOLD, 14));
        btnChoose.setFont(new Font("Arial", Font.BOLD, 14));

        customerDAO = new CustomerDAO();
        tblCustomer.setRowHeight(30);

        for (Customer c : customerDAO.getAllKhachHang()) {
            String date = formatDate(c.getNgaySinh());
            Object[] rowData = { c.getMaKhachHang(), c.getTenKhachHang(), c.getSoDienThoai(), date};
            modelTblCustomer.addRow(rowData);
        }

        btnFind.addActionListener(this);
        btnChoose.addActionListener(this);
        btnALL.addActionListener(this);
    }

//    public static void main(String[] args) {
//        new ChooseCustomer(main).setVisible(true);
//    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnFind)) {
            if (txtTim.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Chưa nhập tên khách hàng");
            } else {
                String tenKH = txtTim.getText().trim();
                modelTblCustomer.getDataVector().removeAllElements();
                modelTblCustomer.fireTableDataChanged();
                lstCustomer = customerDAO.getListKhachHangByName(tenKH);
                if (lstCustomer == null || lstCustomer.size() <= 0) {
                    modelTblCustomer.getDataVector().removeAllElements();
                } else {
                    for (Customer c : lstCustomer) {
                        String date = formatDate(c.getNgaySinh());
                        modelTblCustomer.addRow(new Object[] { c.getMaKhachHang(), c.getTenKhachHang(), c.getSoDienThoai(), date});
                    }

                }
            }

        }
        if (o.equals(btnChoose)) {
            int row = tblCustomer.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chưa chọn khách hàng");
            }
            if (row != -1) {
                String name = tblCustomer.getModel().getValueAt(row, 1).toString();
                main.txtCustomer.setText(name);
                dispose();
            }
        }

        if (o.equals(btnALL)) {
            modelTblCustomer.setRowCount(0);
            int row = tblCustomer.getSelectedRow();
            for (Customer c : customerDAO.getAllKhachHang()) {
                String date = formatDate(c.getNgaySinh());
                Object[] rowData = { c.getMaKhachHang(), c.getTenKhachHang(), c.getSoDienThoai(), date};
                modelTblCustomer.addRow(rowData);
            }
            txtTim.setText("");
        }
    }
}
