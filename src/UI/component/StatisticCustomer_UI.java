package UI.component;

import DAO.BillDAO;
import DAO.CustomerDAO;
import DAO.DetailOfServiceDAO;
import DAO.ServiceDAO;
import Entity.*;
import UI.CustomUI.Custom;
import UI.component.Dialog.DatePicker;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
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
import java.util.HashMap;

/**
 * Sử dụng để thống kê khách hàng
 * <p>
 * Người tham gia thiết kế: Nguyễn Quang Duy
 * </p>
 * ngày tạo: 12/10/2023
 * <p>
 * Lần cập nhật cuối: 13/11/2023
 * </p>
 * Nội dung cập nhật: cập nhật cách tính tổng tiền (tt= tt - ((tt+vat))*15%))
 */

public class StatisticCustomer_UI extends JPanel implements ActionListener, ItemListener {
    private JComboBox<String> cmbLocTheo;
    private JTable tblDichVu;
    private DefaultTableModel modelTblDichVu;
    private JButton btnThongKe, btnLamMoi;
    private JLabel lblDenNgay, lblTuNgay;
    private JLabel lblBackGround, lblTime;
    private DatePicker pickerDenNgay,pickerTuNgay;
    private BillDAO billDAO;
    private CustomerDAO customerDAO;
    private ServiceDAO serviceDAO;
    private DetailOfServiceDAO detailOfServiceDAO;
    private DecimalFormat df = new DecimalFormat("#,###.## VND");
    public static Staff staffLogin = null;

    public StatisticCustomer_UI(Staff staff){
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        billDAO = new BillDAO();
        customerDAO = new CustomerDAO();
        serviceDAO = new ServiceDAO();
        detailOfServiceDAO = new DetailOfServiceDAO();

        //phan viet code
        JLabel lblThongKeKhachHang = new JLabel("THỐNG KÊ KHÁCH HÀNG");
        lblThongKeKhachHang.setBounds(570, 10, 1175, 40);
        lblThongKeKhachHang.setFont(new Font("Arial", Font.BOLD, 25));
        lblThongKeKhachHang.setForeground(Color.WHITE);
        Component add = add(lblThongKeKhachHang);

        JPanel pnlTimeNow = new JPanel();
        pnlTimeNow.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
                TitledBorder.LEADING, TitledBorder.TOP));
        pnlTimeNow.setBounds(12, 10, 300, 50);
        pnlTimeNow.setOpaque(false);
        add(pnlTimeNow);

        lblTime = new JLabel();
        lblTime.setFont(new Font("Arial", Font.BOLD, 33));
        lblTime.setForeground(Color.WHITE);
        pnlTimeNow.add(lblTime);
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();

        JPanel pnlThongTinThongKe =  new JPanel();
        pnlThongTinThongKe.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "THÔNG TIN THỐNG KÊ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlThongTinThongKe.setBounds(10, 70, 1245, 670);
        pnlThongTinThongKe.setOpaque(false);
        add(pnlThongTinThongKe);

        pnlThongTinThongKe.setLayout(null);

        //        Từ ngày
        lblTuNgay = new JLabel("Từ ngày:");
        lblTuNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTuNgay.setBounds(150, 100, 120, 30);
        lblTuNgay.setForeground(Color.WHITE);
        add(lblTuNgay);

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
        JLabel lblLocTheo = new JLabel("Lọc theo:");
        lblLocTheo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblLocTheo.setBounds(750, 100, 150, 30);
        lblLocTheo.setForeground(Color.WHITE);
        add(lblLocTheo);

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
        JPanel pnlDSDV = new JPanel();
        pnlDSDV.setLayout(null);
        pnlDSDV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH KHÁCH HÀNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlDSDV.setBounds(5, 130, 1235, 530);
        pnlDSDV.setOpaque(false);

        String[] colsDV = { "STT", "Mã khách hàng", "Tên khách hàng","Tổng doanh số" };
        modelTblDichVu = new DefaultTableModel(colsDV, 0) ;
        JScrollPane scrKhachHang;

        tblDichVu = new JTable(modelTblDichVu);
        tblDichVu.setFont(new Font("Arial", Font.BOLD, 14));
        tblDichVu.setBackground(new Color(255, 255, 255, 0));
        tblDichVu.setForeground(new Color(255, 255, 255));
        tblDichVu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblDichVu.getTableHeader().setForeground(Color.BLUE);
        Custom.getInstance().setCustomTable(tblDichVu);

        pnlDSDV.add(scrKhachHang = new JScrollPane(tblDichVu,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrKhachHang.setBounds(10,20,1220,500);
        scrKhachHang.setOpaque(false);
        scrKhachHang.getViewport().setOpaque(false);
        scrKhachHang.getViewport().setBackground(Color.WHITE);
        pnlThongTinThongKe.add(pnlDSDV);
        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        lblBackGround = new JLabel(backgroundImage);
        lblBackGround.setBounds(0, 0, getWidth(), getHeight());
        add(lblBackGround);

        btnThongKe.addActionListener(this);
        btnLamMoi.addActionListener(this);

        cmbLocTheo.addItemListener(this);


//        tblDichVu.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                // Xử lý sự kiện khi dòng đang được chọn thay đổi
//                if (!e.getValueIsAdjusting()) {
//                    int selectedRow = tblDichVu.getSelectedRow();
//                    // Hiển thị panel mới dựa trên dòng đã chọn
//                    displayNewPanel(selectedRow);
//                }
//            }
//        });
        tblDichVu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xử lý sự kiện khi click chuột trên dòng
                int selectedRow = tblDichVu.rowAtPoint(e.getPoint());
                // Kiểm tra nếu chọn dòng hợp lệ
                if (selectedRow >= 0) {
                    displayNewPanel(selectedRow);
                }
            }
        });
    }
    /**
     * Gán thời gian hiện tại cho label lblTime
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
            modelTblDichVu.getDataVector().removeAllElements();
            try {
                Date tuNgay = pickerTuNgay.getFullDate();
                Date denNgay = pickerDenNgay.addOneDay();
                ArrayList<Bill> listBill = billDAO.getListBillByDate(tuNgay, denNgay);
                if (validData()) {
                    if (tuNgay.before(denNgay)) {
                        if (listBill == null || listBill.isEmpty() || listBill.size() <= 0) {
                            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin thông kê");
                        } else {
                            docDuLieuVaoTable(listBill);
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
            modelTblDichVu.getDataVector().removeAllElements();
            tblDichVu.removeAll();
            cmbLocTheo.setSelectedIndex(0);
            pickerTuNgay.setValueToDay();
            pickerDenNgay.setValueToDay();
        }
    }

    /**
     * Đưa dữ liệu thống kê vào table
     * @param listBill
     */
    private void docDuLieuVaoTable(ArrayList<Bill> listBill) {
        int i = 1;
        java.util.List<Customer> list = customerDAO.getAllKhachHang();
        ArrayList<DetailsOfService> list1 = (ArrayList<DetailsOfService>) detailOfServiceDAO.getAllDetailsOfService();
        HashMap<String, Double> totalSales = new HashMap<>();
        ArrayList<Object[]> dataToAdd = new ArrayList<>();

        for (Bill bill : listBill) {
            String customerID = bill.getMaKH().getMaKhachHang();
            double gia = 0;
            for (DetailsOfService details : list1) {
                if (bill.getMaHoaDon().equals(details.getMaHoaDon().getMaHoaDon())) {
                    gia += details.getSoLuong() * details.getGiaBan();
                }
            }

            double roomPrice = bill.getMaPhong().getGiaPhong() * bill.tinhGioThue();
            double totalServicePrice = gia;

            double totalQuantity;
            if (bill.getKhuyenMai().trim().equalsIgnoreCase("KM")) {
                // Nếu hóa đơn có mã khuyến mãi "KM", không tính VAT
                totalQuantity = ((roomPrice + totalServicePrice) * 1.08) - (((roomPrice + totalServicePrice) * 1.08) * 0.15);
            } else {
                // Nếu hóa đơn không có mã khuyến mãi hoặc khuyến mãi khác "", tính VAT 8%
                totalQuantity = (roomPrice + totalServicePrice) * 1.08;
            }

            if (totalSales.containsKey(customerID)) {
                // Nếu mã khách hàng đã tồn tại trong HashMap, cộng dồn số tiền
                double currentQuantity = totalSales.get(customerID);
                totalSales.put(customerID, (currentQuantity + totalQuantity));
            } else {
                // Nếu mã khách hàng chưa tồn tại trong HashMap, thêm vào với số tiền hiện tại
                totalSales.put(customerID, totalQuantity);
            }
        }

        for (String customerID : totalSales.keySet()) {
            double totalQuantity = totalSales.get(customerID);
            String s1 = "";
            for (Customer kh : list) {
                if (customerID.equals(kh.getMaKhachHang())) {
                    s1 = kh.getTenKhachHang();
                }
            }
            dataToAdd.add(new Object[] { i, customerID, s1, df.format(totalQuantity) });

        }

        // Sắp xếp danh sách tạm thời theo thứ tự giảm dần của doanh thu
        dataToAdd.sort((o1, o2) -> Double.compare(Double.valueOf(o2[3].toString().replace(",", "").replace(" VND", "")), Double.valueOf(o1[3].toString().replace(",", "").replace(" VND", ""))));

        // Tăng stt
        for (int j = 0; j < dataToAdd.size(); j++) {
            dataToAdd.get(j)[0] = j + 1;
        }

        modelTblDichVu.getDataVector().removeAllElements();
        tblDichVu.removeAll();
        for (Object[] rowData : dataToAdd) {
            modelTblDichVu.addRow(rowData);
        }
    }

    /**
     * kiểm tra ngày bắt đầu và ngày kết thúc
     * @return fasle nếu ngày kết thúc < ngày bắt đầu và ngược lại thì true
     * @throws ParseException
     */
    public boolean validData() throws ParseException {
        Date tuNgay = pickerTuNgay.getFullDate();
        Date denNgay = pickerDenNgay.getFullDate();
        if (denNgay.before(tuNgay)) {
            return false;
        }
        return true;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object o = e.getSource();
        if (o.equals(cmbLocTheo)) {
            String search = cmbLocTheo.getSelectedItem().toString();
            switch (search) {
                case "7 ngày gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblDichVu.getDataVector().removeAllElements();
                    tblDichVu.removeAll();
                    Date tuNgay = pickerTuNgay.setDatesFromToday(Calendar.DAY_OF_MONTH, -6);
                    Date denNgay = null;
                    denNgay = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill = billDAO.getListBillByDate(tuNgay, denNgay);
                    docDuLieuVaoTable(listBill);
                    break;
                case "1 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblDichVu.getDataVector().removeAllElements();
                    tblDichVu.removeAll();
                    Date tuNgay1 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -1);
                    Date denNgay1 = null;
                    denNgay1 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill1 = billDAO.getListBillByDate(tuNgay1, denNgay1);
                    docDuLieuVaoTable(listBill1);
                    break;
                case "3 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblDichVu.getDataVector().removeAllElements();
                    tblDichVu.removeAll();
                    Date tuNgay2 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -2);
                    Date denNgay2 = null;
                    denNgay2 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill2 = billDAO.getListBillByDate(tuNgay2, denNgay2);
                    docDuLieuVaoTable(listBill2);
                    break;
                case "6 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblDichVu.getDataVector().removeAllElements();
                    tblDichVu.removeAll();
                    Date tuNgay3 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -5);
                    Date denNgay3 = null;
                    denNgay3 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill3 = billDAO.getListBillByDate(tuNgay3, denNgay3);
                    docDuLieuVaoTable(listBill3);
                    break;
                case "1 năm gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblDichVu.getDataVector().removeAllElements();
                    tblDichVu.removeAll();
                    Date tuNgay4 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -11);
                    Date denNgay4 = null;
                    denNgay4 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill4 = billDAO.getListBillByDate(tuNgay4, denNgay4);
                    docDuLieuVaoTable(listBill4);
                    break;
                case "Tùy chỉnh":
                    pickerTuNgay.setActive(true);
                    pickerDenNgay.setActive(true);
                    pickerTuNgay.setValueToDay();
                    break;
            }
        }
    }

    private void displayNewPanel(int selectedRow) {
        // Tạo panel mới
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1300, 500));
        panel.setLayout(null);
        panel.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH HÓA ĐƠN",
                TitledBorder.LEADING, TitledBorder.TOP));
//        panel.setBounds(12, 10, 300, 50);
        // Tùy chỉnh panel theo nhu cầu của bạn
        // Ví dụ: Thêm các thành phần và đặt thuộc tính

        String[] colsDV = { "STT","Mã HĐ" ,"Mã KH/Mã DV", "Tên KH/Tên DV","Ngày lập","Giờ vào","Giờ ra","Số giờ/Số lượng","Giá phòng(Giờ)/Giá dịch vụ" ,"Tổng tiền"};
        DefaultTableModel modelTblDichVu1 = new DefaultTableModel(colsDV, 0) ;
        JScrollPane scrKhachHang;

        JTable tblDichVu1 = new JTable(modelTblDichVu1);
        tblDichVu1.setFont(new Font("Arial", Font.BOLD, 14));
        tblDichVu1.setBackground(new Color(255, 255, 255, 0));
        tblDichVu1.setForeground(new Color(255, 255, 255));
        tblDichVu1.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblDichVu1.getTableHeader().setForeground(Color.BLUE);
        Custom.getInstance().setCustomTable(tblDichVu1);

        tblDichVu1.getColumnModel().getColumn(0).setPreferredWidth(10);
        tblDichVu1.getColumnModel().getColumn(1).setPreferredWidth(30);
        tblDichVu1.getColumnModel().getColumn(2).setPreferredWidth(30);
        tblDichVu1.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblDichVu1.getColumnModel().getColumn(4).setPreferredWidth(30);
        tblDichVu1.getColumnModel().getColumn(5).setPreferredWidth(10);
        tblDichVu1.getColumnModel().getColumn(6).setPreferredWidth(10);
        tblDichVu1.getColumnModel().getColumn(7).setPreferredWidth(50);
        tblDichVu1.getColumnModel().getColumn(8).setPreferredWidth(150);
        tblDichVu1.getColumnModel().getColumn(9).setPreferredWidth(40);

        panel.add(scrKhachHang = new JScrollPane(tblDichVu1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrKhachHang.setBounds(10,20,1280,480);
//        scrKhachHang.setOpaque(false);
//        scrKhachHang.getViewport().setOpaque(false);
//        scrKhachHang.getViewport().setBackground(Color.WHITE);
//        pnlThongTinThongKe.add(panel);

//        JLabel label = new JLabel("Dòng đã chọn: " + selectedRow);
//        panel.add(label);

        String makh = modelTblDichVu.getValueAt(selectedRow,1)+"";

        ArrayList<Bill> listBill = billDAO.getBillByCustomerID(makh);
        System.out.printf(listBill.size()+"");

        int i=1;
        for (Bill bill: listBill){
            String ngayThang = String.valueOf(bill.getNgayGioTra());
            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date = null;
            try {
                date = sdfInput.parse(ngayThang);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Định dạng lại đối tượng Date thành chuỗi mới
            SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MM-yyyy");
            String ngayThangDinhDang = sdfOutput.format(date);

            SimpleDateFormat sdfOutput2 = new SimpleDateFormat("HH:mm:ss");
            String gioPhutGiayTra = sdfOutput2.format(date);
//==============================
            String gioDat = String.valueOf(bill.getNgayGioDat());
            SimpleDateFormat sdfInput1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date1 = null;
            try {
                date1 = sdfInput1.parse(gioDat);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat sdfOutput1 = new SimpleDateFormat("HH:mm:ss");
            String gioPhutGiayDat = sdfOutput1.format(date1);

            modelTblDichVu1.addRow(new Object[]{i,bill.getMaHoaDon(),bill.getMaKH().getMaKhachHang(),bill.getMaKH().getTenKhachHang(),ngayThangDinhDang,gioPhutGiayDat,gioPhutGiayTra,bill.tinhGioThue(),df.format(bill.getMaPhong().getGiaPhong()),df.format(bill.tinhTienPhong())});
            ArrayList<DetailsOfService> details = detailOfServiceDAO.getDetailsOfServiceForBill(bill.getMaHoaDon());
            double gia=0;
            for (DetailsOfService detailsOfService:details){
                modelTblDichVu1.addRow(new Object[]{"","",detailsOfService.getMaDichVu().getMaDichVu(),detailsOfService.getMaDichVu().getTenDichVu(),ngayThangDinhDang,gioPhutGiayDat,gioPhutGiayTra,detailsOfService.getSoLuong(),df.format(detailsOfService.getGiaBan()),df.format((detailsOfService.getGiaBan()*detailsOfService.getSoLuong()))});
                gia += detailsOfService.getGiaBan()*detailsOfService.getSoLuong();
            }
            double quantity = bill.getMaPhong().getGiaPhong() * bill.tinhGioThue() + gia;
            if (bill.getKhuyenMai().trim().equalsIgnoreCase("KM")){
                double totalBillWithVAT = quantity * 1.08; // Tính tổng bill kèm VAT
                quantity = totalBillWithVAT - (totalBillWithVAT * 0.15);
            }else{
                quantity += quantity*8/100;
            }
            modelTblDichVu1.addRow(new Object[]{"","","","","","","","","Tổng tiền (VAT 8%):",df.format(quantity)});

            i++;
        }

        // Hiển thị panel mới
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}