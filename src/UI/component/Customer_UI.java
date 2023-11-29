package UI.component;

import ConnectDB.ConnectDB;
import DAO.CustomerDAO;
import Entity.Customer;
import Entity.Staff;
import UI.CustomUI.Custom;
import UI.component.Dialog.DatePicker;
import UI.component.Dialog.Main;

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

/**
 * Giao diện dùng để quản lý khách hàng
 * Người thiết kế: Nguyễn Đình Dương
 * Ngày tạo:9/10/2023
 * Lần cập nhật cuối : 29/11/2023
 * Nội dung cập nhật : sửa public cho biến txtSDTKH
 */
public class Customer_UI extends JPanel implements ActionListener, MouseListener {
    public static Staff staffLogin = null;
    private JTable tblKH;
    private DefaultTableModel modelTableKH;
    private JLabel lblBackground, lblTime, lblMaKH, lblTenKH, lblGioitinhKH, lblSdtKH, lblNgaySinh, lblCmnd;
    public JTextField txtMaKH, txtTenKH, txtCMNDKH, txtHienThiLoi, txtSDTKH;
    private JComboBox cmbGioiTinh;
    private JPanel timeNow, pnlCusList, pnlCusControl;
    private DatePicker dpNgaySinh;
    private JButton btnThem, btnSua, btnLamMoi;
    private CustomerDAO CustomerDAO;

    public Customer_UI(Staff staff) {
        this.staffLogin = staff;
        CustomerDAO = new CustomerDAO();
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        JLabel headerLabel = new JLabel("QUẢN LÝ KHÁCH HÀNG");
        headerLabel.setBounds(520, 10, 1175, 40);
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

        lblMaKH = new JLabel("Mã Khách Hàng: ");
        lblMaKH.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMaKH.setBounds(80, 20, 120, 30);
        lblMaKH.setForeground(Color.WHITE);
        pnlCusControl.add(lblMaKH);
        txtMaKH = new JTextField();
        txtMaKH.setBounds(195, 20, 350, 30);
        txtMaKH.setEditable(false);
        pnlCusControl.add(txtMaKH);

        lblTenKH = new JLabel("Tên Khách Hàng: ");
        lblTenKH.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTenKH.setBounds(80, 70, 120, 30);
        lblTenKH.setForeground(Color.WHITE);
        pnlCusControl.add(lblTenKH);
        txtTenKH = new JTextField();
        txtTenKH.setBounds(195, 70, 350, 30);
        pnlCusControl.add(txtTenKH);

        lblSdtKH = new JLabel("SDT: ");
        lblSdtKH.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSdtKH.setBounds(700, 20, 50, 30);
        lblSdtKH.setForeground(Color.WHITE);
        pnlCusControl.add(lblSdtKH);

        txtSDTKH = new JTextField();
        txtSDTKH.setBounds(815, 20, 165, 30);
        pnlCusControl.add(txtSDTKH);

        lblNgaySinh = new JLabel("Ngày Sinh: ");
        lblNgaySinh.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNgaySinh.setBounds(700, 70, 100, 30);
        lblNgaySinh.setForeground(Color.WHITE);
        pnlCusControl.add(lblNgaySinh);

        dpNgaySinh = new DatePicker(205);
        dpNgaySinh.setBounds(815, 70, 165, 30);
        pnlCusControl.add(dpNgaySinh);

        lblGioitinhKH = new JLabel("Giới Tính: ");
        lblGioitinhKH.setFont(new Font("Arial", Font.PLAIN, 14));
        lblGioitinhKH.setBounds(700, 120, 70, 30);
        lblGioitinhKH.setForeground(Color.WHITE);
        pnlCusControl.add(lblGioitinhKH);

        cmbGioiTinh = new JComboBox<String>();
        cmbGioiTinh.addItem("Nam");
        cmbGioiTinh.addItem("Nữ");
        cmbGioiTinh.setBounds(815, 120, 165, 30);
        Custom.setCustomComboBox(cmbGioiTinh);
        pnlCusControl.add(cmbGioiTinh);

        lblCmnd = new JLabel("CCCD: ");
        lblCmnd.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCmnd.setBounds(80, 120, 60, 30);
        lblCmnd.setForeground(Color.WHITE);
        pnlCusControl.add(lblCmnd);

        txtCMNDKH = new JTextField();
        txtCMNDKH.setBounds(195, 120, 350, 30);
        pnlCusControl.add(txtCMNDKH);

        txtHienThiLoi = new JTextField();
        txtHienThiLoi.setFont(new Font("Arial", Font.BOLD, 13));
        txtHienThiLoi.setForeground(Color.RED);
        txtHienThiLoi.setBounds(195, 170, 465, 30);
        txtHienThiLoi.setOpaque(false);
        txtHienThiLoi.setEditable(false);
        pnlCusControl.add(txtHienThiLoi);

        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 15));
        btnThem.setBounds(700, 170, 100, 30);
        Custom.setCustomBtn(btnThem);
        pnlCusControl.add(btnThem);

        btnLamMoi = new JButton("Làm Mới");
        Custom.setCustomBtn(btnLamMoi);
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 15));
        btnLamMoi.setBounds(980, 170, 100, 30);
        pnlCusControl.add(btnLamMoi);

        btnSua = new JButton("Sửa");
        Custom.setCustomBtn(btnSua);
        btnSua.setFont(new Font("Arial", Font.BOLD, 15));
        btnSua.setBounds(840, 170, 100, 30);
        pnlCusControl.add(btnSua);

        JPanel panelDSKH = new JPanel();
        panelDSKH.setLayout(null);
        panelDSKH.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH KHÁCH HÀNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSKH.setBounds(30, 310, 1220, 370);
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
        scrollPaneKH.setBounds(10, 20, 1210, 380);
        scrollPaneKH.setOpaque(false);
        scrollPaneKH.getViewport().setOpaque(false);
        scrollPaneKH.getViewport().setBackground(Color.WHITE);
        pnlCusList.add(panelDSKH);

        loadKH();
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnLamMoi.addActionListener(this);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        lblBackground = new JLabel(backgroundImage);
        lblBackground.setBounds(0, 0, getWidth(), getHeight());
        add(lblBackground);
    }

    /**
     * Gán sdt cho txtSDTKH
     * @param sdt: SDT cần gán
     */
    public void setSDT(String sdt) {
        txtSDTKH.setText(sdt);
    }

    /**
     * Gán tên KH cho txtTenKH
     * @param tenKH: tên KH cần gán
     */
    public void setTenKH(String tenKH) {
        txtTenKH.setText(tenKH);
    }

    /**
     * Gán cccd cho txtCMNDKH
     * @param cccd: cccd cần gán
     */
    public void setCMNDKH(String cccd) {
        txtCMNDKH.setText(cccd);
    }

    /**
     * Gán gioiTinh cho cmbGioiTinh
     * @param gioiTinh: gioiTinh cần gán
     */
    public void setGioiTinh(String gioiTinh) {
        if(gioiTinh.equalsIgnoreCase("Nam")){
            cmbGioiTinh.setSelectedIndex(0);
        } else {
            cmbGioiTinh.setSelectedIndex(1);
        }
    }

    /**
     * Gán ngày sinh cho dpNgaySinh từ một chuỗi
     * @param ngaySinhStr: chuỗi ngày sinh cần gán
     */
    public void setNgaySinh(String ngaySinhStr) throws ParseException {
        if (ngaySinhStr != null && !ngaySinhStr.trim().isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date ngaySinh = sdf.parse(ngaySinhStr);
            dpNgaySinh.setValue(java.sql.Date.valueOf(ngaySinh.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        }
    }


    /**
     * Thiết lập thời gian thực cho lblTime
     */
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        lblTime.setText(time);
    }

    /**
     * hàm sử dụng định dạng "HH:mm:ss" để biểu diễn thời gian (giờ, phút và giây) của đối tượng date
     *
     * @param date : ngày cần định dạng
     * @return {@code String}: ngày cần định dạng
     */
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
        return sdf.format(date);
    }

    /**
     * Load danh sách tất cả khách hàng lên bảng
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

    /**
     * Hiển thị lỗi
     *
     * @param txt:        Nơi hiển thị
     * @param message:Lỗi cần hiển thị
     */
    private void showMessage(JTextField txt, String message) {
        txt.requestFocus();
        txtHienThiLoi.setText(message);
    }

    /**
     * ReGex dữ liệu được nhập vào
     *
     * @return {@code boolean} :True or False
     */
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
        if (cccd.length() > 0 && !cccd.matches("^0[0-9]{11}$")) {
            showMessage(txtCMNDKH, "CCCD phải gồm 12 số và bắt đầu bằng 0");
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
                String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
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
            ) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin khách hàng");
            } else if (validData()) {
                String maKH = txtMaKH.getText().trim();
                String tenKH = txtTenKH.getText().trim();
                String sdt = txtSDTKH.getText().trim();
                String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
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

    /**
     * Làm mới dữ liệu nhập
     */
    private void reFresh() {
        txtMaKH.setText("");
        txtTenKH.setText("");
        cmbGioiTinh.setSelectedIndex(0);
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
        cmbGioiTinh.setSelectedItem(modelTableKH.getValueAt(row, 5).toString());
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
