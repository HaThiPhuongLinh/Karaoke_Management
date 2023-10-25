package UI.component;

import ConnectDB.ConnectDB;
import DAO.StaffDAO;
import Entity.*;
import UI.CustomUI.Custom;
import UI.component.Dialog.DatePicker;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Staff_UI extends JPanel implements  ActionListener, MouseListener {
    private  JTextField txtBaoLoi;
    private  JTextField txtDiaChi;
    private JTable tableNV;
    private DefaultTableModel modelTableNV;
    private JLabel backgroundLabel, ChucVuLabel, taiKhoanLabel, tinhTrangLabel, timeLabel, maNVLabel, tenNVLabel, gioitinhNVLabel, sdtNVLabel, ngaySinhLabel, cmndLabel, search1Label, search2Label, search3Label;
    private JTextField txtMaNV, txtTenNV, txtSDTNV, txtCMNDNV, txtTaiKhoan;
    private JComboBox cboGioiTinhNV, cboChucVu, cbotinhTrang;
    private JPanel timeNow, pnlStaffList, pnlStaffControl;
    private DatePicker dpNgaySinhNV;
    private JButton btnThem, btnSua, btnLamMoi;
    private StaffDAO StaffDAO;

    public Staff_UI() {
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        //phan viet code
        StaffDAO = new StaffDAO();
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JLabel headerLabel = new JLabel("QUẢN LÝ NHÂN VIÊN");
        headerLabel.setBounds(470, 10, 1175, 40);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        Component add = add(headerLabel);

        timeNow = new JPanel();
        timeNow.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP));
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

        pnlStaffList = new JPanel();
        pnlStaffList.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Nhân Viên", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlStaffList.setBounds(10, 70, 1120, 620);
        pnlStaffList.setOpaque(false);
        add(pnlStaffList);
        pnlStaffList.setLayout(new BorderLayout(0, 0));

        pnlStaffControl = new JPanel();
        pnlStaffControl.setOpaque(false);
        pnlStaffControl.setBackground(Color.WHITE);
        pnlStaffList.add(pnlStaffControl, BorderLayout.NORTH);
        pnlStaffControl.setLayout(null);
        pnlStaffControl.setPreferredSize(new Dimension(1100, 250));

        maNVLabel = new JLabel("Mã Nhân Viên: ");
        maNVLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        maNVLabel.setBounds(30, 10, 120, 30);
        maNVLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(maNVLabel);
        txtMaNV = new JTextField();
        txtMaNV.setBounds(145, 10, 350, 30);
        txtMaNV.setEditable(false);
        pnlStaffControl.add(txtMaNV);

        tenNVLabel = new JLabel("Tên Nhân Viên: ");
        tenNVLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        tenNVLabel.setBounds(30, 60, 120, 30);
        tenNVLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(tenNVLabel);

        txtTenNV = new JTextField();
        txtTenNV.setBounds(145, 60, 350, 30);
        pnlStaffControl.add(txtTenNV);

        gioitinhNVLabel = new JLabel("Giới Tính: ");
        gioitinhNVLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gioitinhNVLabel.setBounds(315, 110, 70, 30);
        gioitinhNVLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(gioitinhNVLabel);

        cboGioiTinhNV = new JComboBox<String>();
        cboGioiTinhNV.addItem("Nam");
        cboGioiTinhNV.addItem("Nữ");
        cboGioiTinhNV.setBounds(390, 110, 105, 30);
        Custom.setCustomComboBox(cboGioiTinhNV);
        pnlStaffControl.add(cboGioiTinhNV);

        sdtNVLabel = new JLabel("SDT: ");
        sdtNVLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        sdtNVLabel.setBounds(30, 110, 50, 30);
        sdtNVLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(sdtNVLabel);

        txtSDTNV = new JTextField();
        txtSDTNV.setBounds(145, 110, 165, 30);
        pnlStaffControl.add(txtSDTNV);

        ngaySinhLabel = new JLabel("Ngày Sinh: ");
        ngaySinhLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        ngaySinhLabel.setBounds(30, 160, 100, 30);
        ngaySinhLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(ngaySinhLabel);

        dpNgaySinhNV = new DatePicker(205);
        dpNgaySinhNV.setBounds(145, 160, 165, 30);
        pnlStaffControl.add(dpNgaySinhNV);

        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 15));
        Custom.setCustomBtn(btnThem);
        btnThem.setBounds(600, 210, 100, 30);
        pnlStaffControl.add(btnThem);

        btnSua = new JButton("Sửa");
        btnSua.setFont(new Font("Arial", Font.BOLD, 15));
        Custom.setCustomBtn(btnSua);
        btnSua.setBounds(750, 210, 100, 30);
        pnlStaffControl.add(btnSua);

        btnLamMoi = new JButton("Làm Mới");
        Custom.setCustomBtn(btnLamMoi);
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 15));
        btnLamMoi.setBounds(900, 210, 100, 30);
        pnlStaffControl.add(btnLamMoi);

        ChucVuLabel = new JLabel("Chức Vụ: ");
        ChucVuLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ChucVuLabel.setBounds(315, 160, 120, 30);
        ChucVuLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(ChucVuLabel);

        cboChucVu = new JComboBox<String>();
        cboChucVu.addItem("Nhân Viên");
        cboChucVu.addItem("Quản lý");
        cboChucVu.setBounds(390, 160, 105, 30);
        Custom.setCustomComboBox(cboChucVu);
        pnlStaffControl.add(cboChucVu);

        tinhTrangLabel = new JLabel("Tình Trạng: ");
        tinhTrangLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        tinhTrangLabel.setBounds(550, 60, 200, 30);
        tinhTrangLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(tinhTrangLabel);

        cbotinhTrang = new JComboBox<String>();
        cbotinhTrang.addItem("Đang làm");
        cbotinhTrang.addItem("Đã nghỉ");
        cbotinhTrang.setBounds(665, 60, 350, 30);
        Custom.setCustomComboBox(cbotinhTrang);
        pnlStaffControl.add(cbotinhTrang);

        taiKhoanLabel = new JLabel("Tài Khoản: ");
        taiKhoanLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        taiKhoanLabel.setBounds(550, 110, 200, 30);
        taiKhoanLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(taiKhoanLabel);

        txtTaiKhoan = new JTextField();
        txtTaiKhoan.setBounds(665, 110, 350, 30);
        pnlStaffControl.add(txtTaiKhoan);

        cmndLabel = new JLabel("CMND: ");
        cmndLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        cmndLabel.setBounds(550, 160, 200, 30);
        cmndLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(cmndLabel);

        txtCMNDNV = new JTextField();
        txtCMNDNV.setBounds(665, 160, 350, 30);
        pnlStaffControl.add(txtCMNDNV);

        txtBaoLoi = new JTextField();
        txtBaoLoi.setFont(new Font("Arial",Font.BOLD,13));
        txtBaoLoi.setForeground(Color.RED);
        txtBaoLoi.setBounds(145, 210, 400, 30);
        pnlStaffControl.add(txtBaoLoi);

        JLabel diaChiLabel = new JLabel("Địa Chỉ: ");
        diaChiLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        diaChiLabel.setBounds(550, 10, 350, 30);
        diaChiLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(diaChiLabel);

        txtDiaChi = new JTextField();
        txtDiaChi.setBounds(665, 10, 350, 30);
        pnlStaffControl.add(txtDiaChi);

        JPanel panelDSNV = new JPanel();
        panelDSNV.setLayout(null);
        panelDSNV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH NHÂN VIÊN", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSNV.setBounds(30, 290, 1100, 320);
        panelDSNV.setOpaque(false);

        String[] colsNV = {"Mã NV", "Tên NV", "SDT", "CCCD", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "Chức Vụ", "Tình Trạng", "Tài Khoản"};
        modelTableNV = new DefaultTableModel(colsNV, 0);
        JScrollPane scrollPaneNV;

        tableNV = new JTable(modelTableNV);
        tableNV.setFont(new Font("Arial", Font.BOLD, 14));
        tableNV.setBackground(new Color(255, 255, 255, 0));
        tableNV.setForeground(new Color(255, 255, 255));
        tableNV.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableNV.getTableHeader().setForeground(Color.BLUE);
        Custom.getInstance().setCustomTable(tableNV);
        panelDSNV.add(scrollPaneNV = new JScrollPane(tableNV, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        scrollPaneNV.setBounds(10, 20, 1090, 310);
        scrollPaneNV.setOpaque(false);
        scrollPaneNV.getViewport().setOpaque(false);
        scrollPaneNV.getViewport().setBackground(Color.WHITE);
        pnlStaffList.add(panelDSNV);
        loadNV();
        tableNV.addMouseListener(this);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);
        btnSua.addActionListener(this);

        btnThem.addActionListener(this);
        btnLamMoi.addActionListener(this);

        reSizeColumnTableStaff();
    }

    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        timeLabel.setText(time);
    }

    private void reSizeColumnTableStaff() {
        TableColumnModel tcm = tableNV.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(50);
        tcm.getColumn(1).setPreferredWidth(120);
        tcm.getColumn(2).setPreferredWidth(80);
        tcm.getColumn(3).setPreferredWidth(100);
        tcm.getColumn(4).setPreferredWidth(50);
        tcm.getColumn(8).setPreferredWidth(70);
        tcm.getColumn(9).setPreferredWidth(120);
    }
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
        return sdf.format(date);
    }
    public void loadNV() {
        String gt = "";
        for (Staff staff : StaffDAO.getAllStaff()) {
            String date = formatDate(staff.getNgaySinh());
            if (staff.isGioiTinh() == true) {
                gt = "Nam";
            } else {
                gt = "Nữ";
            }
            Object[] rowData = {staff.getMaNhanVien(), staff.getTenNhanVien(), staff.getSoDienThoai(), staff.getCCCD(), gt, date,staff.getDiaChi(), staff.getChucVu(), staff.getTrangThai(), staff.getTaiKhoan().getTaiKhoan()};
            modelTableNV.addRow(rowData);
        }
    }
    private void showMessage(JTextField txt, String message) {
        txt.requestFocus();
        txtBaoLoi.setText(message);
    }

    private boolean validData() {
        String ten = txtTenNV.getText().trim();
        String diachi = txtDiaChi.getText().trim();
        String taikhoan = txtTaiKhoan.getText().toString();
        String cccd = txtCMNDNV.getText().toString();
        String sdt = txtSDTNV.getText().toString();
        String ngaysinh = dpNgaySinhNV.getValue().toString(); // Lấy chuỗi ngày tháng từ DatePicker
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
            txtBaoLoi.setText("Nhân viên phải lớn hơn 18 tuổi");
            return false;
        }

        if (!((ten.length() > 0) && ten.matches("^[A-Za-zÀ-ỹ ]+"))) {
            showMessage(txtTenNV,"Tên nhân viên không được chứa kí tự đặc biệt và số");
            return false;
        }
        if (!((diachi.length()) > 0 && diachi.matches("^[A-Za-zÀ-ỹ0-9 ]+"))) {
            showMessage(txtDiaChi,"Địa chỉ không được chứa số và kí tự đặc biệt");
            return false;
        }
        if (!((taikhoan.length()) > 0 && taikhoan.matches("^[a-z1-9]*"))) {
            showMessage(txtTaiKhoan,"Tài khoản không được chứa kí tự đặc biệt");
            return false;
        }
        if (!((cccd.length()) > 0 && cccd.matches("^0[0-9]{11}$"))) {
            showMessage(txtCMNDNV,"CCCD phải gồm 12 số ");
            return false;
        }if (!((sdt.length()) > 0 && sdt.matches("^0[0-9]{9}$"))) {
            showMessage(txtSDTNV,"SDT phải gồm 10 số ");
            return false;
        }
        return true;
    }
    public String laymaNV(){
        String MaNV = StaffDAO.generateNextStaffId();
        return MaNV;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnLamMoi)) {
            reFresh();
            modelTableNV.getDataVector().removeAllElements();
            loadNV();
        } else if (o.equals(btnThem)) {
            if (txtTenNV.getText().equals("") || txtSDTNV.getText().equals("") || txtCMNDNV.getText().equals("") || txtDiaChi.getText().equals("") || txtTaiKhoan.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin nhân viên");
            } else if (validData()){
                String ma = laymaNV();
                String ten = txtTenNV.getText().trim();
                String sdt = txtSDTNV.getText().trim();
                String gioiTinh = cboGioiTinhNV.getSelectedItem().toString();
                boolean gt = true;
                String cccd = txtCMNDNV.getText().trim();
                String chucVu = cboChucVu.getSelectedItem().toString();
                String diachi = txtDiaChi.getText().trim();
                String trangthai = cbotinhTrang.getSelectedItem().toString();
                String taikhoan = txtTaiKhoan.getText().trim();
                if (gioiTinh == "Nam") {
                    gt = true;
                } else {
                    gt = false;
                }
                Date ngaysinh = null;
                try {
                    ngaysinh = dpNgaySinhNV.getFullDate();
                } catch (ParseException e3) {
                    e3.printStackTrace();
                }
                Staff kh = new Staff(ma, ten, sdt, cccd, gt, ngaysinh, diachi, chucVu, trangthai, new Account(taikhoan));

                if (StaffDAO.addStaff(kh)) {
                    String date = formatDate(kh.getNgaySinh());
                    modelTableNV.addRow(new Object[]{kh.getMaNhanVien(), kh.getTenNhanVien(), kh.getSoDienThoai(),
                            kh.getCCCD(), gt, date, kh.getDiaChi(), kh.getChucVu(), kh.getTrangThai(), kh.getTaiKhoan().getTaiKhoan()});
                    modelTableNV.fireTableDataChanged();
                    modelTableNV.getDataVector().removeAllElements();
                    loadNV();
                    JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công");
                    reFresh();
                }
            }
        } else if (o.equals(btnSua)) {
            int row = tableNV.getSelectedRow();
            if(row > 0){
                if (txtTenNV.getText().equals("") || txtSDTNV.getText().equals("") || txtCMNDNV.getText().equals("") || txtDiaChi.getText().equals("") || txtTaiKhoan.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin nhân viên");
                } else if (validData()) {
                    String ma = txtMaNV.getText().trim();
                    String ten = txtTenNV.getText().trim();
                    String sdt = txtSDTNV.getText().trim();
                    String gioiTinh = cboGioiTinhNV.getSelectedItem().toString();
                    boolean gt = true;
                    String chucVu = cboChucVu.getSelectedItem().toString();
                    String diachi = txtDiaChi.getText().trim();
                    String trangthai=cbotinhTrang.getSelectedItem().toString();
                    String cccd = txtCMNDNV.getText().trim();
                    String taikhoan =txtTaiKhoan.getText().trim();
                    if (gioiTinh == "Nam") {
                        gt = true;
                    } else {
                        gt = false;
                    }
                    Date ngaysinh = null;
                    try {
                        ngaysinh = dpNgaySinhNV.getFullDate();

                    } catch (ParseException e3) {
                        e3.printStackTrace();
                    }
                    Staff kh = new Staff(ma, ten, sdt, cccd, gt, ngaysinh ,diachi,chucVu,trangthai,new Account(taikhoan));
                    boolean result = StaffDAO.update(kh);
                    if (result == true) {
                        String date = formatDate(kh.getNgaySinh());
                        modelTableNV.setValueAt(kh.getMaNhanVien(), row, 0);
                        modelTableNV.setValueAt(kh.getTenNhanVien(), row, 1);
                        modelTableNV.setValueAt(kh.getSoDienThoai(), row, 2);
                        modelTableNV.setValueAt(gt, row, 3);
                        modelTableNV.setValueAt(kh.isGioiTinh(), row, 4);
                        modelTableNV.setValueAt(date, row, 5);
                        modelTableNV.setValueAt(kh.getDiaChi(), row, 6);
                        modelTableNV.setValueAt(kh.getChucVu(), row, 7);
                        modelTableNV.setValueAt(kh.getTrangThai(), row, 8);
                        modelTableNV.setValueAt(kh.getTaiKhoan().getTaiKhoan(), row, 9);
                        modelTableNV.fireTableDataChanged();
                        modelTableNV.getDataVector().removeAllElements();
                        loadNV();
                        JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                        reFresh();
                    }
                }} else {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng cần sửa");
            }
        }
    }

    private void reFresh(){
        txtMaNV.setText("");
        txtTenNV.setText("");
        cboGioiTinhNV.setSelectedIndex(0);
        txtCMNDNV.setText("");
        dpNgaySinhNV.setValueToDay();
        txtTaiKhoan.setText("");
        cbotinhTrang.setSelectedIndex(0);
        cboChucVu.setSelectedIndex(0);
        txtDiaChi.setText("");
        txtSDTNV.setText("");
        txtBaoLoi.setText("");
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tableNV.getSelectedRow();
        txtMaNV.setText(modelTableNV.getValueAt(row, 0).toString());
        txtTenNV.setText(modelTableNV.getValueAt(row, 1).toString());
        txtSDTNV.setText(modelTableNV.getValueAt(row, 2).toString());
        txtCMNDNV.setText(modelTableNV.getValueAt(row, 3).toString());
        cboGioiTinhNV.setSelectedItem(modelTableNV.getValueAt(row,4).toString());
        try {
            dpNgaySinhNV.setValue(modelTableNV.getValueAt(row, 5).toString());
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        txtDiaChi.setText(modelTableNV.getValueAt(row, 6).toString());
        cboChucVu.setSelectedItem(modelTableNV.getValueAt(row,7).toString());
        cbotinhTrang.setSelectedItem(modelTableNV.getValueAt(row,8).toString());
        txtTaiKhoan.setText(modelTableNV.getValueAt(row, 9).toString());



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
