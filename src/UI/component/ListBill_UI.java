package UI.component;

import ConnectDB.ConnectDB;
import DAO.*;
import Entity.Room;
import Entity.Staff;
import Entity.Bill;
import Entity.DetailsOfService;
import UI.CustomUI.Custom;
import UI.component.Dialog.DatePicker;
import UI.component.Dialog.InfoBill;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Giao diện dùng để thống kê hóa đơn đã thanh toán
 * Người thiết kế Nguyễn Đình Dương
 * Ngày tạo:5/11/2023
 * Lần cập nhật cuối : 06/11/2023
 * Nội dung cập nhật : Sửa tổng tiền dịch vụ
 */
public class ListBill_UI extends JPanel implements ActionListener,MouseListener,ItemListener {
    public static Staff staffLogin = null;
    private JComboBox<String> cmbLocTheo;
    private JTable tblHD;
    private DefaultTableModel modelTblHD;
    private JButton btnThongKe, btnLamMoi;
    private JLabel lblDenNgay, lblTuNgay;
    private JLabel lblBackground, lblTime;
    private DatePicker dpDenNgay, dpTuNgay;
    private BillDAO billDAO;
    private DetailOfServiceDAO serviceDetailDAO = DetailOfServiceDAO.getInstance();
    private Bill bills = new Bill();
    private RoomDAO roomDAO;
    private  DetailOfServiceDAO ctdv_dao;

    private DecimalFormat df = new DecimalFormat("#,###.## VND");
    public ListBill_UI(Staff staff) {
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);
        billDAO = new BillDAO();
        java.util.List<DetailsOfService> serviceOrders = new ArrayList<>();
        serviceOrders = serviceDetailDAO.getDetailsOfServiceForBill(bills.getMaHoaDon());
        bills.setLstDetails(serviceOrders);
        roomDAO =new RoomDAO();
        ctdv_dao = new DetailOfServiceDAO();

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

        JPanel panel1 =  new JPanel();
        panel1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "THÔNG TIN THỐNG KÊ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panel1.setBounds(10, 70, 1245, 670);
        panel1.setOpaque(false);
        add(panel1);

        panel1.setLayout(null);

        //        Từ ngày
        lblTuNgay = new JLabel("Từ ngày:");
        lblTuNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTuNgay.setBounds(150, 100, 120, 30);
        lblTuNgay.setForeground(Color.WHITE);
        add(lblTuNgay);

        dpTuNgay = new DatePicker(150);
        dpTuNgay.setOpaque(false);
        dpTuNgay.setBounds(230, 100, 300, 30);
        add(dpTuNgay);
//      Đến ngày
        lblDenNgay = new JLabel("Đến ngày: ");
        lblDenNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDenNgay.setBounds(450, 100, 120, 30);
        lblDenNgay.setForeground(Color.WHITE);
        add(lblDenNgay);

        dpDenNgay = new DatePicker(150);
        dpDenNgay.setOpaque(false);
        dpDenNgay.setBounds(530, 100, 300, 30);
        add(dpDenNgay);

//      Lọc theo
        JLabel labelLocTheo = new JLabel("Lọc theo:");
        labelLocTheo.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLocTheo.setBounds(750, 100, 150, 30);
        labelLocTheo.setForeground(Color.WHITE);
        add(labelLocTheo);

        cmbLocTheo = new JComboBox<String>();
        cmbLocTheo.addItem("Tùy chỉnh");
        cmbLocTheo.addItem("7 ngày gần nhất");
        cmbLocTheo.addItem("1 tháng gần nhất");
        cmbLocTheo.addItem("3 tháng gần nhất");
        cmbLocTheo.addItem("6 tháng gần nhất");
        cmbLocTheo.addItem("1 năm gần nhất");
        cmbLocTheo.setBounds(830,100,200,30);
        Custom.setCustomComboBox(cmbLocTheo);
        add(cmbLocTheo);

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

        String[] colsDV = { "Mã HD", "Tên Nhân Viên", "Tên Khách Hàng","Mã Phòng","Tiền Phòng","Tiền Dich Vụ","Tổng Tiền" };
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
        lblBackground = new JLabel(backgroundImage);
        lblBackground.setBounds(0, 0, getWidth(), getHeight());
        add(lblBackground);

        btnThongKe.addActionListener(this);
        btnLamMoi.addActionListener(this);

        cmbLocTheo.addItemListener(this);
        loadHD();
        tblHD.addMouseListener(this);

        //phan viet code
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * gán thời gian hiện tại cho lblTime
     */
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        lblTime.setText(time);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThongKe)) {
            modelTblHD.getDataVector().removeAllElements();
            try {
                Date tuNgay = dpTuNgay.getFullDate();
                Date denNgay = dpDenNgay.addOneDay();
                ArrayList<Bill> listBill = billDAO.getListBillByDate(tuNgay, denNgay);
                if (validData()) {
                    if (tuNgay.before(denNgay)) {
                        if (listBill == null || listBill.isEmpty() || listBill.size() <= 0) {
                            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin thông kê");
                        } else {
                            reloadHD(listBill);
                        }
                    }
                } else if (denNgay.before(tuNgay)) {
                    dpDenNgay.setValueToDay();
                    JOptionPane.showMessageDialog(this, "Ngày kết thúc phải >= ngày bắt đầu", "Cảnh báo",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        } else if (o.equals(btnLamMoi)) {
            modelTblHD.getDataVector().removeAllElements();
            tblHD.removeAll();
            cmbLocTheo.setSelectedIndex(0);
            dpTuNgay.setValueToDay();
            dpDenNgay.setValueToDay();
        }
    }

    /**
     * Load lại danh sách hóa đơn đã thanh toán
     * @param listBill:Danh sách hóa đơn cần load
     */
    public void reloadHD(ArrayList<Bill> listBill) {

        for (Bill bill :listBill) {

            if(bill.getTinhTrangHD()==1) {
                java.util.List<DetailsOfService> serviceOrders = new ArrayList<>();
                serviceOrders = serviceDetailDAO.getDetailsOfServiceForBill(bill.getMaHoaDon());
                bill.setLstDetails(serviceOrders);
                double totalPriceService = bill.tinhTongTienDichVu();

                double totalPriceRoom = bill.tinhTienPhong();

                double km = 0;

                Calendar calendarNgaySinhKhachHang = Calendar.getInstance();
                calendarNgaySinhKhachHang.setTime(bill.getMaKH().getNgaySinh());

                Calendar calendarNgayDat = Calendar.getInstance();
                calendarNgayDat.setTime(bill.getNgayGioDat());

                int ngaySinhKhachHangValue = calendarNgaySinhKhachHang.get(Calendar.DAY_OF_MONTH);
                int thangSinhKhachHangValue = calendarNgaySinhKhachHang.get(Calendar.MONTH) + 1;

                int ngayDatValue = calendarNgayDat.get(Calendar.DAY_OF_MONTH);
                int thangDatValue = calendarNgayDat.get(Calendar.MONTH) + 1;

                double vat = (totalPriceService + totalPriceRoom) * 0.08;
                if (ngayDatValue == ngaySinhKhachHangValue && thangDatValue == thangSinhKhachHangValue) {
                    km = (totalPriceService + totalPriceRoom + vat) * 0.15;
                    boolean b = billDAO.updateKM(bill.getMaHoaDon());
                } else {
                    km = 0;
                }

                double totalPrice = bill.getTongTienHD() + vat - km;
                Object[] rowData = {bill.getMaHoaDon(),bill.getMaNhanVien().getTenNhanVien(),bill.getMaKH().getTenKhachHang(), bill.getMaPhong().getMaPhong(), df.format(totalPriceRoom),df.format(totalPriceService),df.format(totalPrice)};
                modelTblHD.addRow(rowData);
            }
        }
    }
    /**
     * Load  danh sách hóa đơn đã thanh toán
     *
     */
    public void loadHD() {
        for (Bill bill :billDAO.getAllBill2()) {
            if(bill.getTinhTrangHD()==1) {
                java.util.List<DetailsOfService> serviceOrders = new ArrayList<>();
                serviceOrders = serviceDetailDAO.getDetailsOfServiceForBill(bill.getMaHoaDon());
                bill.setLstDetails(serviceOrders);

                double totalPriceService = bill.tinhTongTienDichVu();

                double totalPriceRoom = bill.tinhTienPhong();

                double km = 0;

                Calendar calendarNgaySinhKhachHang = Calendar.getInstance();
                calendarNgaySinhKhachHang.setTime(bill.getMaKH().getNgaySinh());

                Calendar calendarNgayDat = Calendar.getInstance();
                calendarNgayDat.setTime(bill.getNgayGioDat());

                int ngaySinhKhachHangValue = calendarNgaySinhKhachHang.get(Calendar.DAY_OF_MONTH);
                int thangSinhKhachHangValue = calendarNgaySinhKhachHang.get(Calendar.MONTH) + 1;

                int ngayDatValue = calendarNgayDat.get(Calendar.DAY_OF_MONTH);
                int thangDatValue = calendarNgayDat.get(Calendar.MONTH) + 1;

                double vat = (totalPriceService + totalPriceRoom) * 0.08;
                if (ngayDatValue == ngaySinhKhachHangValue && thangDatValue == thangSinhKhachHangValue) {
                    km = (totalPriceService + totalPriceRoom + vat) * 0.15;
                    boolean b = billDAO.updateKM(bill.getMaHoaDon());
                } else {
                    km = 0.0;
                }
                double totalPrice = bill.getTongTienHD() + vat - km;
                Object[] rowData = {bill.getMaHoaDon(),bill.getMaNhanVien().getTenNhanVien(),bill.getMaKH().getTenKhachHang(), bill.getMaPhong().getMaPhong(), df.format(totalPriceRoom),df.format(totalPriceService),df.format(totalPrice)};
                modelTblHD.addRow(rowData);

            }
        }
    }

    /**
     * Hàm để regex dữ liệu nhập vào
     * @return {@code boolean}:Dữ liệu nhập đúng hoặc sai
     * @throws ParseException
     */
    public boolean validData() throws ParseException {
        Date tuNgay = dpTuNgay.getFullDate();
        Date denNgay = dpDenNgay.getFullDate();
        if (denNgay.before(tuNgay)) {
            return false;
        }
        return true;
    }

    /**
     * Lọc hóa đơn đã thanh toán theo các mốc thời gian khác nhau
     * @param e the event to be processed
     */
    public void itemStateChanged(ItemEvent e) {
        Object o = e.getSource();
        if (o.equals(cmbLocTheo)) {
            String search = cmbLocTheo.getSelectedItem().toString();
            switch (search) {
                case "7 ngày gần nhất":
                    dpTuNgay.setActive(false);
                    dpDenNgay.setActive(false);
                    modelTblHD.getDataVector().removeAllElements();
                    tblHD.removeAll();
                    Date tuNgay = dpTuNgay.setDatesFromToday(Calendar.DAY_OF_MONTH, -6);
                    Date denNgay = null;
                    denNgay = dpDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill = billDAO.getListBillByDate(tuNgay, denNgay);
                    reloadHD(listBill);
                    break;
                case "1 tháng gần nhất":
                    dpTuNgay.setActive(false);
                    dpDenNgay.setActive(false);
                    modelTblHD.getDataVector().removeAllElements();
                    tblHD.removeAll();
                    Date tuNgay1 = dpTuNgay.setDatesFromToday(Calendar.MONTH, -1);
                    Date denNgay1 = null;
                    denNgay1 = dpDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill1 = billDAO.getListBillByDate(tuNgay1, denNgay1);
                    reloadHD(listBill1);
                    break;
                case "3 tháng gần nhất":
                    dpTuNgay.setActive(false);
                    dpDenNgay.setActive(false);
                    modelTblHD.getDataVector().removeAllElements();
                    tblHD.removeAll();
                    Date tuNgay2 = dpTuNgay.setDatesFromToday(Calendar.MONTH, -2);
                    Date denNgay2 = null;
                    denNgay2 = dpDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill2 = billDAO.getListBillByDate(tuNgay2, denNgay2);
                    reloadHD(listBill2);
                    break;
                case "6 tháng gần nhất":
                    dpTuNgay.setActive(false);
                    dpDenNgay.setActive(false);
                    modelTblHD.getDataVector().removeAllElements();
                    tblHD.removeAll();
                    Date tuNgay3 = dpTuNgay.setDatesFromToday(Calendar.MONTH, -5);
                    Date denNgay3 = null;
                    denNgay3 = dpDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill3 = billDAO.getListBillByDate(tuNgay3, denNgay3);
                    reloadHD(listBill3);
                    break;
                case "1 năm gần nhất":
                    dpTuNgay.setActive(false);
                    dpDenNgay.setActive(false);
                    modelTblHD.getDataVector().removeAllElements();
                    tblHD.removeAll();
                    Date tuNgay4 = dpTuNgay.setDatesFromToday(Calendar.MONTH, -11);
                    Date denNgay4 = null;
                    denNgay4 = dpDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill4 = billDAO.getListBillByDate(tuNgay4, denNgay4);
                    reloadHD(listBill4);
                    break;
                case "Tùy chỉnh":
                    dpTuNgay.setActive(true);
                    dpDenNgay.setActive(true);
                    dpTuNgay.setValueToDay();
                    break;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if(o.equals(tblHD)) {
            int row = tblHD.getSelectedRow();
            System.out.println("Đã nhấp chuột");
            if (row != -1) {
                String maHoaDon = modelTblHD.getValueAt(row, 0).toString();
                System.out.println("MaHoaDon được chọn: " + maHoaDon);
                Bill bill = billDAO.getBillByBillID(maHoaDon);
                String maPhong = modelTblHD.getValueAt(row, 3).toString();
//                Room room = roomDAO.getRoomByRoomId(maPhong);
//                if (room == null)
//                    room = new Room();
//                bill.setMaPhong(room);
                String billId = bill.getMaHoaDon();
                ArrayList<DetailsOfService> billInfoList = ctdv_dao.getDetailsOfServiceForBill(billId);
                bill.setLstDetails(billInfoList);
                long millis = System.currentTimeMillis();
               Timestamp ngayTra =bill.getNgayGioTra();
                bill.setNgayGioTra(ngayTra);
                Double totalPriceBill = bill.getTongTienHD();

                InfoBill winPayment = new InfoBill(bill);
                winPayment.setModal(true);
                winPayment.setVisible(true);
                System.out.println("Dialog InfoBill được hiển thị");
            }
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
