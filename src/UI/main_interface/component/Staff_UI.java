package UI.main_interface.component;

import ConnectDB.ConnectDB;
import DAOs.CustomerDAO;
import DAOs.StaffDAO;
import Entity.Customer;
import Entity.Staff;
import UI.CustomUI.Custom;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Staff_UI extends JPanel {

    private JTable tableNV;
    private DefaultTableModel modelTableNV;
    private JLabel backgroundLabel, ChucVuLabel, taiKhoanLabel, tinhTrangLabel, timeLabel, maNVLabel, tenNVLabel, gioitinhNVLabel, sdtNVLabel, ngaySinhLabel, cmndLabel, search1Label, search2Label, search3Label;
    private JTextField txtMaNV, txtTenNV, txtSDTNV, txtCMNDNV, txtTaiKhoan, txtChucVu, txttinhTrang;
    private JComboBox cboGioiTinhNV, cboChucVu, cbotinhTrang;
    private JPanel timeNow, pnlStaffList, pnlStaffControl, panelDSNV;
    private DatePicker dpNgaySinhNV;
    private DefaultTableModel tableModelNV;
    private JButton btnThem, btnXoa, btnSua, btnTim1, btnTim2, btnTim3, btnLamMoi, btnXemHet;
    private StaffDAO StaffDAO;

    public Staff_UI() {
        setLayout(null);
        setBounds(0, 0, 1175, 770);

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
        maNVLabel.setBounds(30, 20, 120, 30);
        maNVLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(maNVLabel);
        txtMaNV = new JTextField();
        txtMaNV.setBounds(145, 20, 350, 30);
        txtMaNV.setEditable(false);
        pnlStaffControl.add(txtMaNV);

        tenNVLabel = new JLabel("Tên Nhân Viên: ");
        tenNVLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        tenNVLabel.setBounds(30, 70, 120, 30);
        tenNVLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(tenNVLabel);
        txtTenNV = new JTextField();
        txtTenNV.setBounds(145, 70, 350, 30);
        pnlStaffControl.add(txtTenNV);

        gioitinhNVLabel = new JLabel("Giới Tính: ");
        gioitinhNVLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gioitinhNVLabel.setBounds(315, 120, 70, 30);
        gioitinhNVLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(gioitinhNVLabel);

        cboGioiTinhNV = new JComboBox<String>();
        cboGioiTinhNV.addItem("Nam");
        cboGioiTinhNV.addItem("Nữ");
        cboGioiTinhNV.setBounds(390, 120, 105, 30);
        Custom.setCustomComboBox(cboGioiTinhNV);
        pnlStaffControl.add(cboGioiTinhNV);

        sdtNVLabel = new JLabel("SDT: ");
        sdtNVLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        sdtNVLabel.setBounds(30, 120, 50, 30);
        sdtNVLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(sdtNVLabel);

        txtSDTNV = new JTextField();
        txtSDTNV.setBounds(145, 120, 165, 30);
        pnlStaffControl.add(txtSDTNV);

        ngaySinhLabel = new JLabel("Ngày Sinh: ");
        ngaySinhLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        ngaySinhLabel.setBounds(30, 170, 100, 30);
        ngaySinhLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(ngaySinhLabel);

        dpNgaySinhNV = new DatePicker(205);
        dpNgaySinhNV.setBounds(145, 170, 165, 30);
        pnlStaffControl.add(dpNgaySinhNV);

        cmndLabel = new JLabel("CMND: ");
        cmndLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        cmndLabel.setBounds(30, 220, 60, 30);
        cmndLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(cmndLabel);

        txtCMNDNV = new JTextField();
        txtCMNDNV.setBounds(145, 220, 350, 30);
        pnlStaffControl.add(txtCMNDNV);

        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 15));
        Custom.setCustomBtn(btnThem);
        btnThem.setBounds(550, 220, 100, 30);
        pnlStaffControl.add(btnThem);

        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 15));
        btnXoa.setBounds(690, 220, 100, 30);
        Custom.setCustomBtn(btnXoa);
        pnlStaffControl.add(btnXoa);

        btnSua = new JButton("Sửa");
        btnSua.setFont(new Font("Arial", Font.BOLD, 15));
        Custom.setCustomBtn(btnSua);
        btnSua.setBounds(830, 220, 100, 30);
        pnlStaffControl.add(btnSua);

        btnLamMoi = new JButton("Làm Mới");
        Custom.setCustomBtn(btnLamMoi);
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 15));
        btnLamMoi.setBounds(970, 220, 100, 30);
        pnlStaffControl.add(btnLamMoi);

        ChucVuLabel = new JLabel("Chức Vụ: ");
        ChucVuLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ChucVuLabel.setBounds(315, 170, 120, 30);
        ChucVuLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(ChucVuLabel);


        cboChucVu = new JComboBox<String>();
        cboChucVu.addItem("Nhân Viên");
        cboChucVu.addItem("Quản lý");
        cboChucVu.setBounds(390, 170, 105, 30);
        Custom.setCustomComboBox(cboChucVu);
        pnlStaffControl.add(cboChucVu);

        tinhTrangLabel = new JLabel("Tình Trạng: ");
        tinhTrangLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        tinhTrangLabel.setBounds(550, 70, 120, 30);
        tinhTrangLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(tinhTrangLabel);

        cbotinhTrang = new JComboBox<String>();
        cbotinhTrang.addItem("Đang làm ");
        cbotinhTrang.addItem("Đã nghỉ");
        cbotinhTrang.setBounds(665, 70, 200, 30);
        Custom.setCustomComboBox(cbotinhTrang);
        pnlStaffControl.add(cbotinhTrang);

        taiKhoanLabel = new JLabel("Tài Khoản: ");
        taiKhoanLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        taiKhoanLabel.setBounds(550, 120, 120, 30);
        taiKhoanLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(taiKhoanLabel);

        txtTaiKhoan = new JTextField();
        txtTaiKhoan.setBounds(665, 120, 200, 30);
        pnlStaffControl.add(txtTaiKhoan);

        JTextField txtBaoLoi = new JTextField();
        txtBaoLoi.setBounds(665, 170, 200, 30);
        pnlStaffControl.add(txtBaoLoi);
        //-
        JLabel diaChiLabel = new JLabel("Địa Chỉ: ");
        diaChiLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        diaChiLabel.setBounds(550, 20, 120, 30);
        diaChiLabel.setForeground(Color.WHITE);
        pnlStaffControl.add(diaChiLabel);

        JTextField txtDiaChi = new JTextField();
        txtDiaChi.setBounds(665, 20, 200, 30);
        pnlStaffControl.add(txtDiaChi);

        JPanel panelDSNV = new JPanel();
        panelDSNV.setLayout(null);
        panelDSNV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH NHÂN VIÊN", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSNV.setBounds(30, 290, 1100, 320);
        panelDSNV.setOpaque(false);

        String[] colsNV = {"Mã NV", "Tên NV", "SDT", "CCCD", "Giới Tính", "Ngày Sinh", "Chức Vụ", "Tình Trạng", "Tài Khoản", "Địa Chỉ"};
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
        scrollPaneNV.setBounds(10, 20, 1090, 330);
        scrollPaneNV.setOpaque(false);
        scrollPaneNV.getViewport().setOpaque(false);
        scrollPaneNV.getViewport().setBackground(Color.WHITE);
        pnlStaffList.add(panelDSNV);
        loadNV();

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);

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
        tcm.getColumn(4).setPreferredWidth(60);
        tcm.getColumn(8).setPreferredWidth(100);
        tcm.getColumn(9).setPreferredWidth(90);
    }

    public void loadNV() {
        String gt = "";
        for (Staff staff : StaffDAO.getAllStaff()) {
            if (staff.isGioiTinh() == true) {
                gt = "Nam";
            } else {
                gt = "Nữ";
            }
            Object[] rowData = {staff.getMaNhanVien(), staff.getTenNhanVien(), staff.getCCCD(), staff.getSoDienThoai(), gt, staff.getNgaySinh(), staff.getChucVu(), staff.getTrangThai(), staff.getTaiKhoan(), staff.getDiaChi()};
            modelTableNV.addRow(rowData);
        }
    }
}
