package UI.component;

import ConnectDB.ConnectDB;
import DAO.CustomerDAO;
import Entity.Customer;
import UI.CustomUI.Custom;
import UI.component.Dialog.DatePicker;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class Customer_UI extends JPanel implements ActionListener, MouseListener {
    private JTable tblKH;
    private DefaultTableModel modelTableKH;
    private JLabel backgroundLabel, timeLabel, maKHLabel, tenKHLabel, gioitinhKHLabel, sdtKHLabel, ngaySinhLabel, cmndLabel;
    private JTextField txtMaKH, txtTenKH, txtSDTKH, txtCMNDKH, txtHienThiLoi;
    private JComboBox cboGioiTinh;
    private JPanel timeNow, pnlCusList, pnlCusControl, pnlCusListRight;
    private DatePicker dpNgaySinh;
    private DefaultTableModel tableModel;
    private JButton btnThem, btnXoa, btnSua, btnLamMoi, btnXemHet;
    private CustomerDAO CustomerDAO;
    private Date date;

    public Customer_UI() {
        CustomerDAO = new CustomerDAO();
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLayout(null);
        setBounds(0, 0, 1175, 770);

        JLabel headerLabel = new JLabel("QUẢN LÝ KHÁCH HÀNG");
        headerLabel.setBounds(470, 10, 1175, 40);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        add(headerLabel);

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

        maKHLabel = new JLabel("Mã Khách Hàng: ");
        maKHLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        maKHLabel.setBounds(30, 20, 120, 30);
        maKHLabel.setForeground(Color.WHITE);
        pnlCusControl.add(maKHLabel);
        txtMaKH = new JTextField();
        txtMaKH.setBounds(145, 20, 350, 30);
//        txtMaKH.setEditable(false);
        pnlCusControl.add(txtMaKH);

        tenKHLabel = new JLabel("Tên Khách Hàng: ");
        tenKHLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        tenKHLabel.setBounds(30, 70, 120, 30);
        tenKHLabel.setForeground(Color.WHITE);
        pnlCusControl.add(tenKHLabel);
        txtTenKH = new JTextField();
        txtTenKH.setBounds(145, 70, 350, 30);
        pnlCusControl.add(txtTenKH);

        sdtKHLabel = new JLabel("SDT: ");
        sdtKHLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        sdtKHLabel.setBounds(550, 20, 50, 30);
        sdtKHLabel.setForeground(Color.WHITE);
        pnlCusControl.add(sdtKHLabel);

        txtSDTKH = new JTextField();
        txtSDTKH.setBounds(665, 20, 165, 30);
        pnlCusControl.add(txtSDTKH);

        ngaySinhLabel = new JLabel("Ngày Sinh: ");
        ngaySinhLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        ngaySinhLabel.setBounds(550, 70, 100, 30);
        ngaySinhLabel.setForeground(Color.WHITE);
        pnlCusControl.add(ngaySinhLabel);

        dpNgaySinh = new DatePicker(205);
        dpNgaySinh.setBounds(665, 70, 165, 30);
        pnlCusControl.add(dpNgaySinh);

        gioitinhKHLabel = new JLabel("Giới Tính: ");
        gioitinhKHLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gioitinhKHLabel.setBounds(550, 120, 70, 30);
        gioitinhKHLabel.setForeground(Color.WHITE);
        pnlCusControl.add(gioitinhKHLabel);

        cboGioiTinh = new JComboBox<String>();
        cboGioiTinh.addItem("Nam");
        cboGioiTinh.addItem("Nữ");
        cboGioiTinh.setBounds(665, 120, 165, 30);
        Custom.setCustomComboBox(cboGioiTinh);
        pnlCusControl.add(cboGioiTinh);

        cmndLabel = new JLabel("CCCD: ");
        cmndLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        cmndLabel.setBounds(30, 120, 60, 30);
        cmndLabel.setForeground(Color.WHITE);
        pnlCusControl.add(cmndLabel);

        txtCMNDKH = new JTextField();
        txtCMNDKH.setBounds(145, 120, 350, 30);
        pnlCusControl.add(txtCMNDKH);

        txtHienThiLoi = new JTextField();
        txtHienThiLoi.setFont(new Font("Arial", Font.BOLD, 13));
        txtHienThiLoi.setForeground(Color.RED);
        txtHienThiLoi.setBounds(145, 170, 465, 30);
        txtHienThiLoi.setOpaque(false);
        txtHienThiLoi.setEditable(false);
        pnlCusControl.add(txtHienThiLoi);

        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 15));
        btnThem.setBounds(650, 170, 100, 30);
        Custom.setCustomBtn(btnThem);
        pnlCusControl.add(btnThem);

        btnLamMoi = new JButton("Làm Mới");
        Custom.setCustomBtn(btnLamMoi);
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 15));
        btnLamMoi.setBounds(930, 170, 100, 30);
        pnlCusControl.add(btnLamMoi);

        btnSua = new JButton("Sửa");
        Custom.setCustomBtn(btnSua);
        btnSua.setFont(new Font("Arial", Font.BOLD, 15));
        btnSua.setBounds(790, 170, 100, 30);
        pnlCusControl.add(btnSua);


        JPanel panelDSKH = new JPanel();
        panelDSKH.setLayout(null);
        panelDSKH.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH KHÁCH HÀNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSKH.setBounds(30, 310, 1100, 320);
        panelDSKH.setOpaque(false);

        String[] colsKH = {"STT", "Mã KH", "Tên KH", "SDT", "CCCD", "Giới Tính", "Ngày Sinh"};
        modelTableKH = new DefaultTableModel(colsKH, 0);
        JScrollPane scrollPaneKH;

        tblKH = new JTable(modelTableKH);
        tblKH.setFont(new Font("Arial", Font.BOLD, 14));
        tblKH.setBackground(new Color(255, 255, 255, 0));
        tblKH.setForeground(new Color(255, 255, 255));
        tblKH.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblKH.getTableHeader().setForeground(Color.BLUE);
        Custom.getInstance().setCustomTable(tblKH);
        tblKH.addMouseListener(this);

        panelDSKH.add(scrollPaneKH = new JScrollPane(tblKH, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneKH.setBounds(10, 20, 1090, 330);
        scrollPaneKH.setOpaque(false);
        scrollPaneKH.getViewport().setOpaque(false);
        scrollPaneKH.getViewport().setBackground(Color.WHITE);
        pnlCusList.add(panelDSKH);

        loadKH();
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnLamMoi.addActionListener(this);

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

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
        return sdf.format(date);
    }

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


    private void showMessage(JTextField txt, String message) {
        txt.requestFocus();
        txtHienThiLoi.setText(message);
    }

    private boolean validData() {
        String ten = txtTenKH.getText().trim();
        String sdt = txtSDTKH.getText().trim();
        String cccd = txtCMNDKH.getText().trim();
        String ngaysinh = dpNgaySinh.getValue().toString(); // Lấy chuỗi ngày tháng từ DatePicker
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date ngaySinhDate = null; // Chuyển chuỗi ngày thành đối tượng Date
        try {
            ngaySinhDate = dateFormat.parse(ngaysinh);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // Lấy ngày tháng năm hiện tại
        LocalDate currentDate = LocalDate.now();

        // Giảm 18 năm
        currentDate = currentDate.minusYears(18);

        Date eighteenYearsAgo = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // So sánh ngày nhập vào với ngày tháng năm hiện tại đã giảm đi 18 năm
        if (!(ngaySinhDate.before(eighteenYearsAgo))) {
            txtHienThiLoi.setText("Khách hàng phải lớn hơn 18 tuổi");
            return false;
        }
        if (!((ten.length()) > 0 && ten.matches("[\\p{L},\\s]+"))) {
            showMessage(txtTenKH, "Tên khách hàng không được chứa kí tự đặc biệt và số");
            return false;
        }
        if (!((sdt.length()) > 0 && sdt.matches("^0[0-9]{9}$"))) {
            showMessage(txtSDTKH, "Số điện thoại phải gồm 10 số");

            return false;
        }
        if (!((cccd.length()) > 0 && cccd.matches("^0[0-9]{11}$"))) {
            showMessage(txtCMNDKH, "CCCD phải gồm 12 số");
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnLamMoi)) {
            reFresh();
            modelTableKH.getDataVector().removeAllElements();
            loadKH();
        } else if (o.equals(btnThem)) {
            if (txtTenKH.getText().equals("") || txtSDTKH.getText().equals("")
                    || txtCMNDKH.getText().equals("")
            ) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin khách hàng");
            } else if (validData()) {
                java.util.List<Customer> list = CustomerDAO.getAllKhachHang();
                int i = 1;
                for (Customer customer : list) {
                    i++;
                }
                String MaKH = "";
                if (i < 10) {
                    MaKH = "KH000" + i;
                } else {
                    MaKH = "KH00" + i;
                }
                String tenKH = txtTenKH.getText().trim();
                String sdt = txtSDTKH.getText().trim();
                String gioiTinh = cboGioiTinh.getSelectedItem().toString();
                boolean gt = true;
                String cccd = txtCMNDKH.getText().trim();
                if (gioiTinh == "Nam") {
                    gt = true;
                } else {
                    gt = false;
                }
                Date ngaysinh = null;
                try {
                    ngaysinh = dpNgaySinh.getFullDate();

                } catch (ParseException e3) {
                    e3.printStackTrace();
                }
                Customer kh = new Customer(MaKH, tenKH, sdt, cccd, gt, ngaysinh);
                try {
                    if (CustomerDAO.insert(kh)) {
                        String date = formatDate(kh.getNgaySinh());
                        modelTableKH.addRow(new Object[]{kh.getMaKhachHang(), kh.getTenKhachHang(), kh.getSoDienThoai(),
                                kh.getCCCD(), gt, date});
                        modelTableKH.fireTableDataChanged();
                        modelTableKH.getDataVector().removeAllElements();
                        loadKH();
                        JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công");
                        reFresh();
                    }
                } catch (SQLException e2) {
                    JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại");
                }
            }

        } else if (o.equals(btnSua)) {
            if (txtTenKH.getText().equals("") || txtSDTKH.getText().equals("")
                    || txtCMNDKH.getText().equals("")
            ) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin khách hàng");
            } else if (validData()) {
                String maKH = txtMaKH.getText().trim();
                String tenKH = txtTenKH.getText().trim();
                String sdt = txtSDTKH.getText().trim();
                String gioiTinh = cboGioiTinh.getSelectedItem().toString();
                boolean gt = true;
                String cccd = txtCMNDKH.getText().trim();
                if (gioiTinh == "Nam") {
                    gt = true;
                } else {
                    gt = false;
                }
                Date ngaysinh = null;
                try {
                    ngaysinh = dpNgaySinh.getFullDate();

                } catch (ParseException e3) {
                    e3.printStackTrace();
                }
                Customer kh = new Customer(maKH, tenKH, sdt, cccd, gt, ngaysinh);
                String date = formatDate(kh.getNgaySinh());
                int row = tblKH.getSelectedRow();
                boolean result = CustomerDAO.update(kh);
                if (row > 0) {
                    if (result == true) {
                        modelTableKH.setValueAt(kh.getMaKhachHang(), row, 1);
                        modelTableKH.setValueAt(kh.getTenKhachHang(), row, 2);
                        modelTableKH.setValueAt(kh.getSoDienThoai(), row, 3);
                        modelTableKH.setValueAt(kh.getCCCD(), row, 4);
                        modelTableKH.setValueAt(gt, row, 5);
                        modelTableKH.setValueAt(date, row, 6);
                        modelTableKH.fireTableDataChanged();
                        modelTableKH.getDataVector().removeAllElements();
                        loadKH();
                        JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                        reFresh();
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi: Cập nhật thất bại");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng để sửa");
                }

            }
        }
    }

    private void reFresh() {
        txtMaKH.setText("");
        txtTenKH.setText("");
        cboGioiTinh.setSelectedIndex(0);
        txtCMNDKH.setText("");
        dpNgaySinh.setValueToDay();
        txtHienThiLoi.setText("");
        txtSDTKH.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblKH.getSelectedRow();
        txtMaKH.setText(modelTableKH.getValueAt(row, 1).toString());
        txtTenKH.setText(modelTableKH.getValueAt(row, 2).toString());
        txtSDTKH.setText(modelTableKH.getValueAt(row, 3).toString());
        txtCMNDKH.setText(modelTableKH.getValueAt(row, 4).toString());
        cboGioiTinh.setSelectedItem(modelTableKH.getValueAt(row, 5).toString());
        try {
            dpNgaySinh.setValue(modelTableKH.getValueAt(row, 6).toString());
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
