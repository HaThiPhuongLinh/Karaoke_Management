package UI.component;

import DAO.BillDAO;
import DAO.DetailOfServiceDAO;
import DAO.ServiceDAO;
import Entity.Bill;
import Entity.DetailsOfService;
import Entity.Service;
import Entity.Staff;
import UI.CustomUI.Custom;
import UI.component.Dialog.DatePicker;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
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
 * Sử dụng để thống kê dịch vụ có doanh thu nhiều nhất
 * <p>
 *     Người tham gia thiết kế: Nguyễn Quang Duy
 * </p>
 * ngày tạo: 12/10/2023
 * <p>
 *     Lần cập nhật cuối: 6/11/2023
 * </p>
 * Nội dung cập nhật: thêm javadoc
 */

public class StatisticService_UI extends JPanel implements ActionListener , ItemListener {
    private JComboBox<String> cmbLocTheo;
    private JButton btnLamMoi,btnThongKe;
    private DefaultTableModel modelTblDichVu;
    private JTable tblDichVu;
    private DatePicker pickerDenNgay,pickerTuNgay;
    private JLabel lblBackGround, lblTime;
    private DetailOfServiceDAO detailOfServiceDAO;
    private ServiceDAO serviceDAO;
    private BillDAO billDAO;
    private DecimalFormat df = new DecimalFormat("#,###.## VND");
    public static Staff staffLogin = null;

    public StatisticService_UI(Staff staff){
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        detailOfServiceDAO = new DetailOfServiceDAO();
        serviceDAO = new ServiceDAO();
        billDAO = new BillDAO();

        //phan viet code
        JLabel lblThongKeDichVu = new JLabel("THỐNG KÊ DỊCH VỤ");
        lblThongKeDichVu.setBounds(570, 10, 1175, 40);
        lblThongKeDichVu.setFont(new Font("Arial", Font.BOLD, 25));
        lblThongKeDichVu.setForeground(Color.WHITE);
        Component add = add(lblThongKeDichVu);

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
        JLabel lblTuNgay = new JLabel("Từ ngày:");
        lblTuNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTuNgay.setBounds(150, 100, 120, 30);
        lblTuNgay.setForeground(Color.WHITE);
        add(lblTuNgay);

        pickerTuNgay = new DatePicker(150);
        pickerTuNgay.setOpaque(false);
        pickerTuNgay.setBounds(230, 100, 300, 30);
        add(pickerTuNgay);
//      Đến ngày
        JLabel lblDenNgay = new JLabel("Đến ngày: ");
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


//      danh sách dịch vụ
        JPanel pnlDSDV = new JPanel();
        pnlDSDV.setLayout(null);
        pnlDSDV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlDSDV.setBounds(5, 130, 1235, 530);
        pnlDSDV.setOpaque(false);

        String[] colsDV = { "STT", "Mã dịch vụ", "Tên dịch vụ","Số lượng bán được" ,"Doanh số"};
        modelTblDichVu = new DefaultTableModel(colsDV, 0) ;
        JScrollPane scrDichVu;

        tblDichVu = new JTable(modelTblDichVu);
        tblDichVu.setFont(new Font("Arial", Font.BOLD, 14));
        tblDichVu.setBackground(new Color(255, 255, 255, 0));
        tblDichVu.setForeground(new Color(255, 255, 255));
        tblDichVu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblDichVu.getTableHeader().setForeground(Color.BLUE);
        Custom.getInstance().setCustomTable(tblDichVu);

        pnlDSDV.add(scrDichVu = new JScrollPane(tblDichVu,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrDichVu.setBounds(10,20,1220,500);
        scrDichVu.setOpaque(false);
        scrDichVu.getViewport().setOpaque(false);
        scrDichVu.getViewport().setBackground(Color.WHITE);
        pnlThongTinThongKe.add(pnlDSDV);
        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        lblBackGround = new JLabel(backgroundImage);
        lblBackGround.setBounds(0, 0, getWidth(), getHeight());
        add(lblBackGround);

        btnThongKe.addActionListener(this);
        btnLamMoi.addActionListener(this);

        cmbLocTheo.addItemListener(this);

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
    /**
     * Đưa dữ liệu thống kê vào table
     * @param listService
     */
    private void docDuLieuVaoTable(ArrayList<DetailsOfService> listService) {
        int i=1;
        ArrayList<Service> list = (ArrayList<Service>) serviceDAO.getAllDichVu();

        HashMap<String, Integer> totalSales = new HashMap<>();
        ArrayList<Object[]> dataToAdd = new ArrayList<>();

        for (DetailsOfService CTDV : listService) {
            String serviceId = CTDV.getMaDichVu().getMaDichVu();
            int quantity =  CTDV.getSoLuong();
            if (totalSales.containsKey(serviceId)) {
                // Nếu mã dịch vụ đã tồn tại trong HashMap, cộng dồn số lượng bán
                int currentQuantity = totalSales.get(serviceId);
                totalSales.put(serviceId, currentQuantity + quantity);
            } else {
                // Nếu mã dịch vụ chưa tồn tại trong HashMap, thêm vào với số lượng bán hiện tại
                totalSales.put(serviceId, quantity);
            }
        }

        for (String serviceId : totalSales.keySet()){
            int totalQuantity = totalSales.get(serviceId);
            String s1 = "";
            double gia = 0;
            for (Service s : list){
                if (serviceId.equals(s.getMaDichVu())){
                    s1=s.getTenDichVu();
                    gia = totalQuantity*s.getGiaBan();
                }
            }
            dataToAdd.add(new Object[] { i, serviceId, s1, totalQuantity, df.format(gia) });
            i++;
        }

        // Sắp xếp danh sách tạm thời theo thứ tự giảm dần của giá

        dataToAdd.sort((o1, o2) -> Double.compare(Double.valueOf(o2[4].toString().replace(",", "").replace(" VND", "")), Double.valueOf(o1[4].toString().replace(",", "").replace(" VND", ""))));
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
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThongKe)) {
            modelTblDichVu.getDataVector().removeAllElements();
            try {
                Date tuNgay = pickerTuNgay.getFullDate();
                Date denNgay = pickerDenNgay.addOneDay();
                ArrayList<DetailsOfService> listDV = detailOfServiceDAO.getListCTDVByDate(tuNgay, denNgay);
//                System.out.printf(String.valueOf(listDV.size())+"ádasdasd");
                if (validData()) {
                    if (tuNgay.before(denNgay)) {
                        if (listDV == null || listDV.isEmpty() || listDV.size() <= 0)
                            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin thông kê");
                        else
                            docDuLieuVaoTable(listDV);
                    }
                } else if (denNgay.before(tuNgay)) {
                    pickerDenNgay.setValueToDay();
                    JOptionPane.showMessageDialog(this, "Ngày kết thúc phải >= ngày bắt đầu", "Cảnh báo",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        } else if (o.equals(btnLamMoi)){
            modelTblDichVu.getDataVector().removeAllElements();
            tblDichVu.removeAll();
            cmbLocTheo.setSelectedIndex(0);
            pickerTuNgay.setValueToDay();
            pickerDenNgay.setValueToDay();
        }
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
                    ArrayList<DetailsOfService> listDV = detailOfServiceDAO.getListCTDVByDate(tuNgay, denNgay);
                    docDuLieuVaoTable(listDV);
                    break;
                case "1 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblDichVu.getDataVector().removeAllElements();
                    tblDichVu.removeAll();
                    Date tuNgay1 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -1);
                    Date denNgay1 = null;
                    denNgay1 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<DetailsOfService> listDV1 = detailOfServiceDAO.getListCTDVByDate(tuNgay1, denNgay1);
                    docDuLieuVaoTable(listDV1);
                    break;
                case "3 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblDichVu.getDataVector().removeAllElements();
                    tblDichVu.removeAll();
                    Date tuNgay2 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -2);
                    Date denNgay2 = null;
                    denNgay2 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<DetailsOfService> listDV2 = detailOfServiceDAO.getListCTDVByDate(tuNgay2, denNgay2);
                    docDuLieuVaoTable(listDV2);
                    break;
                case "6 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblDichVu.getDataVector().removeAllElements();
                    tblDichVu.removeAll();
                    Date tuNgay3 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -5);
                    Date denNgay3 = null;
                    denNgay3 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<DetailsOfService> listDV3 = detailOfServiceDAO.getListCTDVByDate(tuNgay3, denNgay3);
                    docDuLieuVaoTable(listDV3);
                    break;
                case "1 năm gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblDichVu.getDataVector().removeAllElements();
                    tblDichVu.removeAll();
                    Date tuNgay4 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -11);
                    Date denNgay4 = null;
                    denNgay4 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<DetailsOfService> listDV4 = detailOfServiceDAO.getListCTDVByDate(tuNgay4, denNgay4);
                    docDuLieuVaoTable(listDV4);

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

        String[] colsDV = { "STT","Mã HĐ" ,"Mã DV", "Tên DV","Ngày lập","Số lượng","Giá dịch vụ" ,"Tổng tiền"};
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
        tblDichVu1.getColumnModel().getColumn(6).setPreferredWidth(100);
        tblDichVu1.getColumnModel().getColumn(7).setPreferredWidth(100);

        panel.add(scrKhachHang = new JScrollPane(tblDichVu1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrKhachHang.setBounds(10,20,1280,480);

        String madv = modelTblDichVu.getValueAt(selectedRow,1)+"";

        ArrayList<Bill> listBill = billDAO.getAllBill();
        ArrayList<DetailsOfService> listDetail = detailOfServiceDAO.getBillByServiceID(madv);
        System.out.printf(listDetail.size()+"");

        int i=1;
        double gia=0;
        for (DetailsOfService details: listDetail){
            String ngayThangDinhDang="";
            for (Bill bill:listBill){
                if (details.getMaHoaDon().getMaHoaDon().trim().equalsIgnoreCase(bill.getMaHoaDon())){
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
                    ngayThangDinhDang = sdfOutput.format(date);
                }
            }
            gia+=details.getSoLuong()*details.getGiaBan();
            modelTblDichVu1.addRow(new Object[]{i,details.getMaHoaDon().getMaHoaDon(),details.getMaDichVu().getMaDichVu(),details.getMaDichVu().getTenDichVu(),ngayThangDinhDang,details.getSoLuong(),df.format(details.getGiaBan()),df.format(details.getSoLuong()*details.getGiaBan())});
            i++;
        }
        modelTblDichVu1.addRow(new Object[]{"","","","","","","Tổng tiền:",df.format(gia)});

        // Hiển thị panel mới
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
