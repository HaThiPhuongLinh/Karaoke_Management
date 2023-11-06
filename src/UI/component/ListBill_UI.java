package UI.component;

import ConnectDB.ConnectDB;
import DAO.*;
import Entity.Staff;
import ConnectDB.ConnectDB;
import Entity.Room;
import Entity.Bill;
import Entity.DetailsOfService;
import Entity.Staff;
import UI.CustomUI.Custom;
import UI.component.Dialog.DatePicker;
import UI.component.Dialog.DialogBill;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;
import javax.swing.*;

public class ListBill_UI extends JPanel implements ActionListener,MouseListener,ItemListener {
    public static Staff staffLogin = null;
    private JComboBox<String> comboBoxLocTheo;
    private JTable tblHD;
    private DefaultTableModel modelTblHD;
    private JButton btnThongKe, btnLamMoi;
    private JLabel lblDenNgay,labelTuNgay;
    private JLabel backgroundLabel,timeLabel;
    private DatePicker pickerDenNgay,pickerTuNgay;
    private BillDAO billDAO;
    private DetailOfServiceDAO serviceDetailDAO = DetailOfServiceDAO.getInstance();
    private Bill bills = new Bill();

    private DecimalFormat df = new DecimalFormat("#,###.##");
    public ListBill_UI(Staff staff) {
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);
        billDAO = new BillDAO();
        java.util.List<DetailsOfService> serviceOrders = new ArrayList<>();
        serviceOrders = serviceDetailDAO.getDetailsOfServiceForBill(bills.getMaHoaDon());
        bills.setLstDetails(serviceOrders);
        //phan viet code
        JLabel headerLabel = new JLabel("THỐNG KÊ HOÁ ĐƠN");
        headerLabel.setBounds(570, 10, 1175, 40);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        Component add = add(headerLabel);

        JPanel timeNow = new JPanel();
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

        JPanel panel1 =  new JPanel();
        panel1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "THÔNG TIN THỐNG KÊ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panel1.setBounds(10, 70, 1245, 670);
        panel1.setOpaque(false);
        add(panel1);

        panel1.setLayout(null);

        //        Từ ngày
        labelTuNgay = new JLabel("Từ ngày:");
        labelTuNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTuNgay.setBounds(150, 100, 120, 30);
        labelTuNgay.setForeground(Color.WHITE);
        add(labelTuNgay);

        pickerTuNgay = new DatePicker(150);
        pickerTuNgay.setOpaque(false);
        pickerTuNgay.setBounds(230, 100, 300, 30);
        add(pickerTuNgay);
//      Đến ngày
        lblDenNgay = new JLabel("Đến ngày: ");
        lblDenNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDenNgay.setBounds(450, 100, 120, 30);
        lblDenNgay.setForeground(Color.WHITE);
        add(lblDenNgay);

        pickerDenNgay = new DatePicker(150);
        pickerDenNgay.setOpaque(false);
        pickerDenNgay.setBounds(530, 100, 300, 30);
        add(pickerDenNgay);

//      Lọc theo
        JLabel labelLocTheo = new JLabel("Lọc theo:");
        labelLocTheo.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLocTheo.setBounds(750, 100, 150, 30);
        labelLocTheo.setForeground(Color.WHITE);
        add(labelLocTheo);

        comboBoxLocTheo = new JComboBox<String>();
        comboBoxLocTheo.addItem("Tùy chỉnh");
        comboBoxLocTheo.addItem("7 ngày gần nhất");
        comboBoxLocTheo.addItem("1 tháng gần nhất");
        comboBoxLocTheo.addItem("3 tháng gần nhất");
        comboBoxLocTheo.addItem("6 tháng gần nhất");
        comboBoxLocTheo.addItem("1 năm gần nhất");
        comboBoxLocTheo.setBounds(830,100,200,30);
        Custom.setCustomComboBox(comboBoxLocTheo);
        add(comboBoxLocTheo);

        //        btn thống kê
        btnThongKe = new JButton("Thống kê");
        btnThongKe.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThongKe);
        btnThongKe.setBounds(430, 160, 150, 30);
        add(btnThongKe);

        //        btn làm mới
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnLamMoi);
        btnLamMoi.setBounds(630, 160, 150, 30);
        add(btnLamMoi);


//      danh sách khách hàng
        JPanel panelDSDV = new JPanel();
        panelDSDV.setLayout(null);
        panelDSDV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH KHÁCH HÀNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSDV.setBounds(5, 130, 1235, 530);
        panelDSDV.setOpaque(false);

        String[] colsDV = { "Mã HD", "Tên Nhân Viên", "Tên Hhách Hàng","Mã Phòng","Tiền Phòng","Tiền Dich Vụ","Tổng Tiền" };
        modelTblHD = new DefaultTableModel(colsDV, 0) ;
        JScrollPane scrollPaneDV;

        tblHD = new JTable(modelTblHD);
        tblHD.setFont(new Font("Arial", Font.BOLD, 14));
        tblHD.setBackground(new Color(255, 255, 255, 0));
        tblHD.setForeground(new Color(255, 255, 255));
        tblHD.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblHD.getTableHeader().setForeground(Color.BLUE);
//        tableLDV.getTableHeader().setBackground(new Color(255, 255, 255));
        Custom.getInstance().setCustomTable(tblHD);

        panelDSDV.add(scrollPaneDV = new JScrollPane(tblHD,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneDV.setBounds(10,20,1220,500);
        scrollPaneDV.setOpaque(false);
        scrollPaneDV.getViewport().setOpaque(false);
        scrollPaneDV.getViewport().setBackground(Color.WHITE);
        panel1.add(panelDSDV);
        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);

        btnThongKe.addActionListener(this);
        btnLamMoi.addActionListener(this);

        comboBoxLocTheo.addItemListener(this);
        loadHD();

        //phan viet code
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        timeLabel.setText(time);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThongKe)) {
            modelTblHD.getDataVector().removeAllElements();
            try {
                Date tuNgay = pickerTuNgay.getFullDate();
                Date denNgay = pickerDenNgay.addOneDay();
                ArrayList<Bill> listBill = billDAO.getListBillByDate(tuNgay, denNgay);
                System.out.printf(String.valueOf(listBill.size()) + "ádasdasd");
                if (validData()) {
                    if (tuNgay.before(denNgay)) {
                        if (listBill == null || listBill.isEmpty() || listBill.size() <= 0) {
                            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin thông kê");
                        } else {
                            reloadHD(listBill);
                        }
                    }
                } else if (denNgay.before(tuNgay)) {
                    pickerDenNgay.setValueToDay();
                    JOptionPane.showMessageDialog(this, "Ngày kết thúc phải >= ngày bắt đầu", "Cảnh báo",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        } else if (o.equals(btnLamMoi)) {
            modelTblHD.getDataVector().removeAllElements();
            tblHD.removeAll();
            comboBoxLocTheo.setSelectedIndex(0);
            pickerTuNgay.setValueToDay();
            pickerDenNgay.setValueToDay();
        }
    }
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }
    public void reloadHD(ArrayList<Bill> listBill) {

        for (Bill bill :listBill) {

            if(bill.getTinhTrangHD()==1) {
                java.util.List<DetailsOfService> serviceOrders = new ArrayList<>();
                serviceOrders = serviceDetailDAO.getDetailsOfServiceForBill(bill.getMaHoaDon());
                bill.setLstDetails(serviceOrders);
                double totalPriceService = bill.tinhTongTienDichVu();

                double totalPriceRoom = bill.tinhTienPhong();

                double vat = 0;
                Date ngayHienTai = new Date();

// Chuyển ngày hiện tại và ngày sinh của khách hàng thành lớp Calendar
                Calendar calendarHienTai = Calendar.getInstance();
                calendarHienTai.setTime(ngayHienTai);

                Calendar calendarNgaySinhKhachHang = Calendar.getInstance();
                calendarNgaySinhKhachHang.setTime(bill.getMaKH().getNgaySinh());

// Lấy ngày và tháng của ngày hiện tại
                int ngayHienTaiValue = calendarHienTai.get(Calendar.DAY_OF_MONTH);
                int thangHienTaiValue = calendarHienTai.get(Calendar.MONTH);

// Lấy ngày và tháng từ ngày sinh của khách hàng
                int ngaySinhKhachHangValue = calendarNgaySinhKhachHang.get(Calendar.DAY_OF_MONTH);
                int thangSinhKhachHangValue = calendarNgaySinhKhachHang.get(Calendar.MONTH);
                if (ngayHienTaiValue == ngaySinhKhachHangValue && thangHienTaiValue == thangSinhKhachHangValue) {
                    vat = 0.0;
                    boolean b = billDAO.updateKM(bill.getMaHoaDon());
                } else {
                    vat = (totalPriceService + totalPriceRoom) * 0.08;
                }

                double totalPrice = bill.getTongTienHD() +vat;
                Object[] rowData = {bill.getMaHoaDon(),bill.getMaNhanVien().getTenNhanVien(),bill.getMaKH().getTenKhachHang(), bill.getMaPhong().getMaPhong(), df.format(totalPriceRoom),df.format(totalPriceService),df.format(totalPrice)};
                modelTblHD.addRow(rowData);


            }
        }
    }

    public void loadHD() {

        for (Bill bill :billDAO.getAllBill2()) {

            if(bill.getTinhTrangHD()==1) {
                java.util.List<DetailsOfService> serviceOrders = new ArrayList<>();
                serviceOrders = serviceDetailDAO.getDetailsOfServiceForBill(bill.getMaHoaDon());
                bill.setLstDetails(serviceOrders);

                double totalPriceService = bill.tinhTongTienDichVu();

                double totalPriceRoom = bill.tinhTienPhong();

                double vat = 0;
                Date ngayHienTai = new Date();

// Chuyển ngày hiện tại và ngày sinh của khách hàng thành lớp Calendar
                Calendar calendarHienTai = Calendar.getInstance();
                calendarHienTai.setTime(ngayHienTai);

                Calendar calendarNgaySinhKhachHang = Calendar.getInstance();
                calendarNgaySinhKhachHang.setTime(bill.getMaKH().getNgaySinh());

// Lấy ngày và tháng của ngày hiện tại
                int ngayHienTaiValue = calendarHienTai.get(Calendar.DAY_OF_MONTH);
                int thangHienTaiValue = calendarHienTai.get(Calendar.MONTH);

// Lấy ngày và tháng từ ngày sinh của khách hàng
                int ngaySinhKhachHangValue = calendarNgaySinhKhachHang.get(Calendar.DAY_OF_MONTH);
                int thangSinhKhachHangValue = calendarNgaySinhKhachHang.get(Calendar.MONTH);
                if (ngayHienTaiValue == ngaySinhKhachHangValue && thangHienTaiValue == thangSinhKhachHangValue) {
                    vat = 0.0;
                    boolean b = billDAO.updateKM(bill.getMaHoaDon());
                } else {
                    vat = (totalPriceService + totalPriceRoom) * 0.08;
                }

                double totalPrice = bill.getTongTienHD() +vat;
                Object[] rowData = {bill.getMaHoaDon(),bill.getMaNhanVien().getTenNhanVien(),bill.getMaKH().getTenKhachHang(), bill.getMaPhong().getMaPhong(), df.format(totalPriceRoom),df.format(totalPriceService),df.format(totalPrice)};
                modelTblHD.addRow(rowData);

            }
        }
    }

    public boolean validData() throws ParseException {
        Date tuNgay = pickerTuNgay.getFullDate();
        Date denNgay = pickerDenNgay.getFullDate();
        if (denNgay.before(tuNgay)) {
            return false;
        }
        return true;
    }
    public void itemStateChanged(ItemEvent e) {
        Object o = e.getSource();
        if (o.equals(comboBoxLocTheo)) {
            String search = comboBoxLocTheo.getSelectedItem().toString();
            switch (search) {
                case "7 ngày gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblHD.getDataVector().removeAllElements();
                    tblHD.removeAll();
                    Date tuNgay = pickerTuNgay.setDatesFromToday(Calendar.DAY_OF_MONTH, -6);
                    Date denNgay = null;
                    denNgay = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill = billDAO.getListBillByDate(tuNgay, denNgay);
                    reloadHD(listBill);
                    break;
                case "1 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblHD.getDataVector().removeAllElements();
                    tblHD.removeAll();
                    Date tuNgay1 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -1);
                    Date denNgay1 = null;
                    denNgay1 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill1 = billDAO.getListBillByDate(tuNgay1, denNgay1);
                    reloadHD(listBill1);
                    break;
                case "3 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblHD.getDataVector().removeAllElements();
                    tblHD.removeAll();
                    Date tuNgay2 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -2);
                    Date denNgay2 = null;
                    denNgay2 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill2 = billDAO.getListBillByDate(tuNgay2, denNgay2);
                    reloadHD(listBill2);
                    break;
                case "6 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblHD.getDataVector().removeAllElements();
                    tblHD.removeAll();
                    Date tuNgay3 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -5);
                    Date denNgay3 = null;
                    denNgay3 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill3 = billDAO.getListBillByDate(tuNgay3, denNgay3);
                    reloadHD(listBill3);
                    break;
                case "1 năm gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblHD.getDataVector().removeAllElements();
                    tblHD.removeAll();
                    Date tuNgay4 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -11);
                    Date denNgay4 = null;
                    denNgay4 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill4 = billDAO.getListBillByDate(tuNgay4, denNgay4);
                    reloadHD(listBill4);
                    break;
                case "Tùy chỉnh":
                    pickerTuNgay.setActive(true);
                    pickerDenNgay.setActive(true);
                    pickerTuNgay.setValueToDay();
                    break;
            }
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
