package UI.component;

import ConnectDB.ConnectDB;
import DAO.StaffDAO;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * Giao diện dùng để tìm kiếm nhân viên
 * Người thiết kế Nguyễn Đình Dương
 * Ngày tạo:7/10/2023
 * Lần cập nhật cuối : 18/10/2023
 * Nội dung cập nhật : Sửa tính năng tìm theo SDT
 */
public class SearchingStaff_UI extends JPanel implements ActionListener, MouseListener {

    private  JButton btnLamMoi;
    private JTable tblNV;
    private DefaultTableModel modelTableNV;
    private JLabel lblBackground, lblTime, lblSearchbyName, lblSearchbyNumber, lblSearchbyCCCD, lblSearchStatus;
    private JPanel timeNow, pnlStaffList, pnlStaffControl;
    private DefaultTableModel tableModelNV;
    private JComboBox<String> cboTinhTrang;

    private JTextField txtSearchbyName, txtSearchbyNumber, txtSearchbyCCCD;
    private JButton btnTim;
    private StaffDAO StaffDAO;
    public static Staff staffLogin = null;

    public SearchingStaff_UI(Staff staff) {
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);
        StaffDAO = new StaffDAO();
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel headerLabel = new JLabel("TÌM KIẾM NHÂN VIÊN");
        headerLabel.setBounds(470, 10, 1175, 40);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        Component add = add(headerLabel);

        timeNow = new JPanel();
        timeNow.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP));
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

        pnlStaffList = new JPanel();
        pnlStaffList.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Nhân Viên", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlStaffList.setBounds(10, 70, 1240, 670);
        pnlStaffList.setOpaque(false);
        add(pnlStaffList);
        pnlStaffList.setLayout(new BorderLayout(0, 0));

        pnlStaffControl = new JPanel();
        pnlStaffControl.setOpaque(false);
        pnlStaffControl.setBackground(Color.WHITE);
        pnlStaffList.add(pnlStaffControl, BorderLayout.NORTH);
        pnlStaffControl.setLayout(null);
        pnlStaffControl.setPreferredSize(new Dimension(1100, 250));

        lblSearchbyName = new JLabel("Tìm Theo Tên: ");
        lblSearchbyName.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSearchbyName.setBounds(380, 15, 120, 30);
        lblSearchbyName.setForeground(Color.WHITE);
        pnlStaffControl.add(lblSearchbyName);

        txtSearchbyName = new JTextField();
        txtSearchbyName.setBounds(515, 15, 280, 30);
        pnlStaffControl.add(txtSearchbyName);

        btnTim = new JButton("Tìm kiếm");
        btnTim.setBounds(695, 205, 100, 30);
        Custom.setCustomBtn(btnTim);
        btnTim.setFont(new Font("Arial", Font.BOLD, 14));
        pnlStaffControl.add(btnTim);
        btnLamMoi = new JButton("Làm Mới");
        btnLamMoi.setBounds(585, 205, 100, 30);
        Custom.setCustomBtn(btnLamMoi);
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        pnlStaffControl.add(btnLamMoi);

        lblSearchbyNumber = new JLabel("Tìm Theo SDT: ");
        lblSearchbyNumber.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSearchbyNumber.setBounds(380, 65, 120, 30);
        lblSearchbyNumber.setForeground(Color.WHITE);
        pnlStaffControl.add(lblSearchbyNumber);

        txtSearchbyNumber = new JTextField();
        txtSearchbyNumber.setBounds(515, 65, 280, 30);
        pnlStaffControl.add(txtSearchbyNumber);

        lblSearchbyCCCD = new JLabel("Tìm Theo CCCD: ");
        lblSearchbyCCCD.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSearchbyCCCD.setBounds(380, 115, 120, 30);
        lblSearchbyCCCD.setForeground(Color.WHITE);
        pnlStaffControl.add(lblSearchbyCCCD);

        txtSearchbyCCCD = new JTextField();
        txtSearchbyCCCD.setBounds(515, 115, 280, 30);
        pnlStaffControl.add(txtSearchbyCCCD);

        lblSearchStatus = new JLabel("Tình Trạng: ");
        lblSearchStatus.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSearchStatus.setBounds(380, 165, 120, 30);
        lblSearchStatus.setForeground(Color.WHITE);
        pnlStaffControl.add(lblSearchStatus);

        cboTinhTrang = new JComboBox<>();
        cboTinhTrang.addItem("Tất cả");
        cboTinhTrang.addItem("Đang làm");
        cboTinhTrang.addItem("Đã nghỉ");
        cboTinhTrang.setBounds(515, 165, 280, 30);
        pnlStaffControl.add(cboTinhTrang);

        JPanel panelDSNV = new JPanel();
        panelDSNV.setLayout(null);
        panelDSNV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH NHÂN VIÊN", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSNV.setBounds(30, 290, 1220, 370);
        panelDSNV.setOpaque(false);

        String[] colsNV = {"Mã NV", "Tên NV", "SDT", "CCCD", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "Chức Vụ", "Tình Trạng", "Tài Khoản"};
        modelTableNV = new DefaultTableModel(colsNV, 0);
        JScrollPane scrollPaneNV;

        tblNV = new JTable(modelTableNV);
        tblNV.setFont(new Font("Arial", Font.BOLD, 14));
        tblNV.setBackground(new Color(255, 255, 255, 0));
        tblNV.setForeground(new Color(255, 255, 255));
        tblNV.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblNV.getTableHeader().setForeground(Color.BLUE);
        Custom.getInstance().setCustomTable(tblNV);

        panelDSNV.add(scrollPaneNV = new JScrollPane(tblNV, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        scrollPaneNV.setBounds(10, 20, 1210, 360);
        scrollPaneNV.setOpaque(false);
        scrollPaneNV.getViewport().setOpaque(false);
        scrollPaneNV.getViewport().setBackground(Color.WHITE);
        pnlStaffList.add(panelDSNV);
        loadNV();

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        lblBackground = new JLabel(backgroundImage);
        lblBackground.setBounds(0, 0, getWidth(), getHeight());
        add(lblBackground);
        btnTim.addActionListener(this);
        btnLamMoi.addActionListener(this);

        cboTinhTrang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedNhanVien = (String) cboTinhTrang.getSelectedItem();
                modelTableNV.setRowCount(0);
                String gt ="";
                for (Staff staff : StaffDAO.getAllStaff()) {
                    String date = formatDate(staff.getNgaySinh());
                    if (staff.isGioiTinh() == true) {
                        gt = "Nam";
                    } else {
                        gt = "Nữ";
                    }
                    if (selectedNhanVien.equalsIgnoreCase("Tất cả") || selectedNhanVien.equalsIgnoreCase(staff.getTrangThai())) {
                        Object[] rowData = { staff.getMaNhanVien(), staff.getTenNhanVien(), staff.getSoDienThoai(),
                                staff.getCCCD(), gt, date, staff.getDiaChi(),
                                staff.getChucVu(), staff.getTrangThai(), staff.getTaiKhoan().getTaiKhoan()};
                        modelTableNV.addRow(rowData);
                    }
                }
            }
        });

        reSizeColumnTableStaff();
    }
    //Cap nhat thoi gian thuc
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        lblTime.setText(time);
    }
    //Custom size cua bang
    private void reSizeColumnTableStaff() {
        TableColumnModel tcm = tblNV.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(50);
        tcm.getColumn(1).setPreferredWidth(120);
        tcm.getColumn(2).setPreferredWidth(80);
        tcm.getColumn(3).setPreferredWidth(100);
        tcm.getColumn(4).setPreferredWidth(50);
        tcm.getColumn(8).setPreferredWidth(70);
        tcm.getColumn(9).setPreferredWidth(120);
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
    //Load danh sach nhan vien len bang
    public void loadNV() {
        String gt = "";
        for (Staff staff : StaffDAO.getAllStaff()) {
            String date = formatDate(staff.getNgaySinh());
            if (staff.isGioiTinh() == true) {
                gt = "Nam";
            } else {
                gt = "Nữ";
            }
            Object[] rowData = {staff.getMaNhanVien(), staff.getTenNhanVien(), staff.getSoDienThoai(), staff.getCCCD(), gt, date ,staff.getDiaChi(), staff.getChucVu(), staff.getTrangThai(), staff.getTaiKhoan().getTaiKhoan()};
            modelTableNV.addRow(rowData);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnTim)) {
            String txtTenKH = txtSearchbyName.getText();
            ArrayList<Staff> cus1 = (ArrayList<Staff>) StaffDAO.getListNhanVienByName(txtTenKH);

            String txtSDT = txtSearchbyNumber.getText();
            ArrayList<Staff> cus2 = (ArrayList<Staff>) StaffDAO.getListNhanVienBySDT(txtSDT);

            String txtcccd = txtSearchbyCCCD.getText();
            ArrayList<Staff> cus3 = (ArrayList<Staff>) StaffDAO.getListNhanVienByCCCD(txtcccd);

            if (txtSearchbyName.getText().trim().equals("") && txtSearchbyNumber.getText().trim().equals("") && txtSearchbyCCCD.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin tìm kiếm");
            } else if (!txtSearchbyName.getText().trim().equals("")) {
                modelTableNV.getDataVector().removeAllElements();
                int i = 1;
                String gt ="";
                if (cus1.size() != 0) {
                    for (Staff staff : cus1) {
                        String date = formatDate(staff.getNgaySinh());
                        if (staff.isGioiTinh() == true) {
                            gt = "Nam";
                        } else {
                            gt = "Nữ";
                        }
                        modelTableNV.addRow(new Object[]{staff.getMaNhanVien(), staff.getTenNhanVien(), staff.getSoDienThoai(),
                                staff.getCCCD(), gt, date, staff.getDiaChi(),
                                staff.getChucVu(), staff.getTrangThai(), staff.getTaiKhoan().getTaiKhoan()});
                        i++;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên");
                    txtSearchbyName.selectAll();
                    txtSearchbyName.requestFocus();
                }
            } else if (!txtSearchbyNumber.getText().trim().equals("")) {
                modelTableNV.getDataVector().removeAllElements();
                int i = 1;
                String gt ="";
                if (cus2.size() != 0) {
                    for (Staff staff : cus2) {
                        String date = formatDate(staff.getNgaySinh());
                        if (staff.isGioiTinh() == true) {
                            gt = "Nam";
                        } else {
                            gt = "Nữ";
                        }
                        modelTableNV.addRow(new Object[]{staff.getMaNhanVien(), staff.getTenNhanVien(), staff.getSoDienThoai(),
                                staff.getCCCD(), gt, date, staff.getDiaChi(),
                                staff.getChucVu(), staff.getTrangThai(), staff.getTaiKhoan().getTaiKhoan()});
                        i++;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên");
                    txtSearchbyNumber.selectAll();
                    txtSearchbyNumber.requestFocus();
                }
            } else if (!txtSearchbyCCCD.getText().trim().equals("")) {
                modelTableNV.getDataVector().removeAllElements();
                int i = 1;
                String gt ="";
                if (cus3.size() != 0) {
                    for (Staff staff: cus3) {
                        String date = formatDate(staff.getNgaySinh());
                        if (staff.isGioiTinh() == true) {
                            gt = "Nam";
                        } else {
                            gt = "Nữ";
                        }
                        modelTableNV.addRow(new Object[]{staff.getMaNhanVien(), staff.getTenNhanVien(), staff.getSoDienThoai(),
                                staff.getCCCD(), gt, date, staff.getDiaChi(),
                                staff.getChucVu(), staff.getTrangThai(), staff.getTaiKhoan().getTaiKhoan()});
                        i++;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên");
                    txtSearchbyCCCD.selectAll();
                    txtSearchbyCCCD.requestFocus();
                }
            }

        } else if (o.equals(btnLamMoi)) {
            txtSearchbyName.setText("");
            txtSearchbyNumber.setText("");
            txtSearchbyCCCD.setText("");
            modelTableNV.setRowCount(0);
            loadNV();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
