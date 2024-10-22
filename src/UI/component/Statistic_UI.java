package UI.component;

import DAO.BillDAO;
import DAO.DetailOfServiceDAO;
import Entity.Bill;
import Entity.DetailsOfService;
import Entity.Staff;
import UI.CustomUI.Custom;
import UI.component.Dialog.DatePicker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.Timer;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Sử dụng để thống kê doanh thu theo thời gian được chọn
 * <p>
 * Người tham gia thiết kế: Nguyễn Quang Duy
 * </p>
 * ngày tạo: 12/10/2023
 * <p>
 * Lần cập nhật cuối: 13/11/2023
 * </p>
 * Nội dung cập nhật: cập nhật cách tính tổng tiền (tt= tt - ((tt+vat))*15%))
 */
public class Statistic_UI extends JPanel implements ActionListener, ItemListener {
    public static Staff staffLogin = null;
    private static DetailOfServiceDAO detailOfServiceDAO;
    private JTextField txtTongDoanhThu;
    private ChartPanel pnlChare;
    private JButton btnLamMoi, btnThongKe;
    private JComboBox<String> cmbLocTheo;
    private DatePicker pickerTuNgay, pickerDenNgay;
    private JLabel lblBackGround, lblTime;
    private BillDAO billDAO;
    private DecimalFormat df = new DecimalFormat("#,###.## VND");
    private DefaultCategoryDataset dataset;

    public Statistic_UI(Staff staff) {
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        billDAO = new BillDAO();
        detailOfServiceDAO = new DetailOfServiceDAO();

        JLabel lblThongKeDoanhThu = new JLabel("THỐNG KÊ DOANH THU");
        lblThongKeDoanhThu.setBounds(570, 10, 1175, 40);
        lblThongKeDoanhThu.setFont(new Font("Arial", Font.BOLD, 25));
        lblThongKeDoanhThu.setForeground(Color.WHITE);
        Component add = add(lblThongKeDoanhThu);

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

        JPanel pnlThongTinThongke = new JPanel();
        pnlThongTinThongke.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "THÔNG TIN THỐNG KÊ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlThongTinThongke.setBounds(10, 70, 1245, 670);
        pnlThongTinThongke.setOpaque(false);
        add(pnlThongTinThongke);

        pnlThongTinThongke.setLayout(null);

        JLabel lblTuNgay = new JLabel("Từ ngày:");
        lblTuNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTuNgay.setBounds(150, 100, 120, 30);
        lblTuNgay.setForeground(Color.WHITE);
        add(lblTuNgay);

        pickerTuNgay = new DatePicker(150);
        pickerTuNgay.setOpaque(false);
        pickerTuNgay.setBounds(230, 100, 300, 30);
        add(pickerTuNgay);

        JLabel lblDenNgay = new JLabel("Đến ngày: ");
        lblDenNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDenNgay.setBounds(450, 100, 120, 30);
        lblDenNgay.setForeground(Color.WHITE);
        add(lblDenNgay);

        pickerDenNgay = new DatePicker(150);
        pickerDenNgay.setOpaque(false);
        pickerDenNgay.setBounds(530, 100, 300, 30);
        add(pickerDenNgay);

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
        cmbLocTheo.setBounds(830, 100, 200, 30);
        Custom.setCustomComboBox(cmbLocTheo);
        add(cmbLocTheo);

        btnThongKe = new JButton("Thống kê");
        btnThongKe.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThongKe);
        btnThongKe.setBounds(430, 160, 150, 30);
        add(btnThongKe);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnLamMoi);
        btnLamMoi.setBounds(630, 160, 150, 30);
        add(btnLamMoi);

        JPanel pnlDoanhThu = new JPanel();
        pnlDoanhThu.setLayout(null);
        pnlDoanhThu.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DOANH THU",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlDoanhThu.setBounds(5, 130, 1235, 530);
        pnlDoanhThu.setOpaque(false);

        JFreeChart chart = ChartFactory.createBarChart("BIỂU ĐỒ DOANH THU", "Ngày", "VND", null,
                PlotOrientation.VERTICAL, false, false, false);
        chart.setBackgroundPaint(Color.WHITE);

        pnlChare = new ChartPanel(chart);
        pnlChare.setBounds(10, 20, 950, 500);
        pnlChare.setOpaque(false);
        pnlDoanhThu.add(pnlChare);

        JLabel lblTongDoanhThu = new JLabel("Tổng doanh thu:");
        lblTongDoanhThu.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTongDoanhThu.setBounds(980, 50, 150, 30);
        lblTongDoanhThu.setForeground(Color.WHITE);
        pnlDoanhThu.add(lblTongDoanhThu);

        txtTongDoanhThu = new JTextField();
        txtTongDoanhThu.setBounds(980, 80, 200, 30);
        txtTongDoanhThu.setColumns(3);
        txtTongDoanhThu.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlDoanhThu.add(txtTongDoanhThu);

        pnlThongTinThongke.add(pnlDoanhThu);
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        lblBackGround = new JLabel(backgroundImage);
        lblBackGround.setBounds(0, 0, getWidth(), getHeight());
        add(lblBackGround);

        btnThongKe.addActionListener(this);
        btnLamMoi.addActionListener(this);
        cmbLocTheo.addItemListener(this);
    }

    /**
     * Lấy danh sách thông kê doanh thu theo ngày
     */
    public static ArrayList<Object[]> calculateTotalByDate(ArrayList<Bill> listBill) {
        Map<String, Double> totalByDate = new HashMap<>();
        ArrayList<DetailsOfService> list1 = detailOfServiceDAO.getAllDetailsOfService();

        for (Bill b : listBill) {
            Date ngay = b.getThoiGianRa();
            System.out.printf("----------------" + ngay + "----------------");

            String dateTimeString = String.valueOf(ngay);
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

            String formattedDate = null;
            try {
                Date date = inputFormat.parse(dateTimeString);
                formattedDate = outputFormat.format(date);
                System.out.println(formattedDate);
            } catch (Exception e) {
                e.printStackTrace();
            }

            double gia = 0;
            for (DetailsOfService details : list1) {
                if (b.getMaHoaDon().equals(details.getMaHoaDon().getMaHoaDon())) {
                    gia += details.getSoLuong() * details.getGiaBan();
                }
            }
            double quantity = b.tinhTienPhong() + gia;


            if (b.getKhuyenMai().trim().equalsIgnoreCase("KM")) {
                double totalBillWithVAT = quantity * 1.08; // Tính tổng bill kèm VAT
                quantity = totalBillWithVAT - (totalBillWithVAT * 0.15);
            } else {
                quantity += quantity * 8 / 100;
            }

            if (totalByDate.containsKey(formattedDate)) {
                // Nếu ngày đã tồn tại trong HashMap, cộng dồn tổng tiền
                double existingTotal = totalByDate.get(formattedDate);
                totalByDate.put(formattedDate, existingTotal + quantity);
            } else {
                // Nếu ngày chưa tồn tại trong HashMap, thêm mới với tổng tiền
                totalByDate.put(formattedDate, quantity);
            }
        }

        // Chuyển HashMap thành ArrayList<Object>
        ArrayList<Object[]> resultList = new ArrayList<>();
        for (Map.Entry<String, Double> entry : totalByDate.entrySet()) {
            Object[] item = new Object[]{entry.getKey(), entry.getValue()};
            System.out.printf(entry.getKey() + "\n");
            System.out.printf(entry.getValue() + "\n");
            resultList.add(item);
        }
        return resultList;
    }

    /**
     * Lấy danh sách thông kê doanh thu theo năm
     *
     * @param listBill: danh sách hóa đơn
     */
    public static ArrayList<Object[]> calculateTotalByYear(ArrayList<Bill> listBill) {
        Map<String, Double> totalByDate = new HashMap<>();
        ArrayList<DetailsOfService> list1 = detailOfServiceDAO.getAllDetailsOfService();


        for (Bill b : listBill) {
            Date ngay = b.getThoiGianRa();
            System.out.printf("----------------" + ngay + "----------------");

            String dateTimeString = String.valueOf(ngay);

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy");

            String formattedDate = null;
            try {
                Date date = inputFormat.parse(dateTimeString);
                formattedDate = outputFormat.format(date);
                System.out.println("Ngày định dạng lại: " + formattedDate);
            } catch (Exception e) {
                e.printStackTrace();
            }

            double gia = 0;
            for (DetailsOfService details : list1) {
                if (b.getMaHoaDon().equals(details.getMaHoaDon().getMaHoaDon())) {
                    gia += details.getSoLuong() * details.getGiaBan();
                }
            }
            double quantity = b.tinhTienPhong() + gia;

            if (b.getKhuyenMai().trim().equalsIgnoreCase("KM")) {
                double totalBillWithVAT = quantity * 1.08; // Tính tổng bill kèm VAT
                quantity = totalBillWithVAT - (totalBillWithVAT * 0.15);
            } else {
                quantity += quantity * 8 / 100;
            }

            if (totalByDate.containsKey(formattedDate)) {
                // Nếu năm đã tồn tại trong HashMap, cộng dồn tổng tiền
                double existingTotal = totalByDate.get(formattedDate);
                totalByDate.put(formattedDate, existingTotal + quantity);
            } else {
                // Nếu năm chưa tồn tại trong HashMap, thêm mới với tổng tiền
                totalByDate.put(formattedDate, quantity);
            }
        }

        // Chuyển HashMap thành ArrayList<Object>
        ArrayList<Object[]> resultList = new ArrayList<>();
        for (Map.Entry<String, Double> entry : totalByDate.entrySet()) {
            Object[] item = new Object[]{entry.getKey(), entry.getValue()};
            System.out.printf(entry.getKey() + "\n");
            System.out.printf(entry.getValue() + "\n");
            resultList.add(item);
        }
        return resultList;
    }

    /**
     * Lấy danh sách thông kê doanh thu theo tháng
     */
    public static ArrayList<Object[]> calculateTotalByMonth(ArrayList<Bill> listBill) {
        Map<String, Double> totalByDate = new HashMap<>();
        ArrayList<DetailsOfService> list1 = detailOfServiceDAO.getAllDetailsOfService();


        for (Bill b : listBill) {
            Date ngay = b.getThoiGianRa();
            System.out.printf("----------------" + ngay + "----------------");

            String dateTimeString = String.valueOf(ngay);
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            SimpleDateFormat outputFormat = new SimpleDateFormat("MM-yyyy");

            String formattedDate = null;
            try {
                Date date = inputFormat.parse(dateTimeString);
                formattedDate = outputFormat.format(date);
                System.out.println("Ngày định dạng lại: " + formattedDate);
            } catch (Exception e) {
                e.printStackTrace();
            }

            double gia = 0;
            for (DetailsOfService details : list1) {
                if (b.getMaHoaDon().equals(details.getMaHoaDon().getMaHoaDon())) {
                    gia += details.getSoLuong() * details.getGiaBan();
                }
            }
            double quantity = b.tinhTienPhong() + gia;

            if (b.getKhuyenMai().trim().equalsIgnoreCase("KM")) {
                double totalBillWithVAT = quantity * 1.08; // Tính tổng bill kèm VAT
                quantity = totalBillWithVAT - (totalBillWithVAT * 0.15);
            } else {
                quantity += quantity * 8 / 100;
            }

            if (totalByDate.containsKey(formattedDate)) {
                // Nếu tháng đã tồn tại trong HashMap, cộng dồn tổng tiền
                double existingTotal = totalByDate.get(formattedDate);
                totalByDate.put(formattedDate, existingTotal + quantity);
            } else {
                // Nếu tháng chưa tồn tại trong HashMap, thêm mới với tổng tiền
                totalByDate.put(formattedDate, quantity);
            }
        }

        // Chuyển HashMap thành ArrayList<Object>
        ArrayList<Object[]> resultList = new ArrayList<>();
        for (Map.Entry<String, Double> entry : totalByDate.entrySet()) {

            Object[] item = new Object[]{entry.getKey(), entry.getValue()};
            System.out.printf(entry.getKey() + "\n");
            System.out.printf(entry.getValue() + "\n");
            resultList.add(item);
        }
        return resultList;
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
    public void itemStateChanged(ItemEvent e) {
        Object o = e.getSource();
        if (o.equals(cmbLocTheo)) {
            String search = cmbLocTheo.getSelectedItem().toString();
            switch (search) {
                case "7 ngày gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    Date tuNgay = pickerTuNgay.setDatesFromToday(Calendar.DAY_OF_MONTH, -6);
                    Date denNgay = null;
                    denNgay = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill = billDAO.getListBillByDate(tuNgay, denNgay);
                    try {
                        statistical(listBill);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "1 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    Date tuNgay1 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -1);
                    Date denNgay1 = null;
                    denNgay1 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill1 = billDAO.getListBillByDate(tuNgay1, denNgay1);
                    try {
                        statistical(listBill1);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "3 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    Date tuNgay2 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -2);
                    Date denNgay2 = null;
                    denNgay2 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill2 = billDAO.getListBillByDate(tuNgay2, denNgay2);
                    try {
                        statistical(listBill2);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "6 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    Date tuNgay3 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -5);
                    Date denNgay3 = null;
                    denNgay3 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill3 = billDAO.getListBillByDate(tuNgay3, denNgay3);
                    try {
                        statistical(listBill3);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "1 năm gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    Date tuNgay4 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -13);
                    Date denNgay4 = null;
                    denNgay4 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill4 = billDAO.getListBillByDate(tuNgay4, denNgay4);
                    try {
                        statistical(listBill4);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    pickerTuNgay.setDatesFromToday(Calendar.MONTH, -12);
                    break;
                case "Tùy chỉnh":
                    pickerTuNgay.setActive(true);
                    pickerDenNgay.setActive(true);
                    pickerTuNgay.setValueToDay();
                    pickerDenNgay.setValueToDay();
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThongKe)) {
            Date tuNgay = null;
            try {
                tuNgay = pickerTuNgay.getFullDate();
                Date denNgay = pickerDenNgay.addOneDay();
                ArrayList<Bill> listBill = billDAO.getListBillByDate(tuNgay, denNgay);
                statistical(listBill);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

        } else if (o.equals(btnLamMoi)) {
            dataset.clear();
            txtTongDoanhThu.setText("");
            cmbLocTheo.setSelectedIndex(0);
            pickerTuNgay.setValueToDay();
            pickerDenNgay.setValueToDay();
        }
    }

    /**
     * Hiển thị biểu đồ thống kê
     *
     * @param fromDate       {@code Date} ngày bắt đầu
     * @param toDate         {@code Date} ngày kết thúc
     * @param dayOfMonth     {@code int} số ngày trong tháng
     * @param dayOfYear      {@code int} số ngày trong năm
     * @param format         {@code String} định dạng ngày
     * @param totalPriceList {@code List<Object[]>} danh sách tổng tiền và ngày
     */
    public void showStatistical(Date fromDate, Date toDate, int dayOfMonth, int dayOfYear, String format,
                                ArrayList<Object[]> totalPriceList) {
        pnlChare.removeAll();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(toDate);
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);

        long difference = toDate.getTime() - fromDate.getTime();
        int times = (int) TimeUnit.MILLISECONDS.toDays(difference);

        if (times > dayOfMonth && times <= dayOfYear) {
            calendar.setTime(fromDate);
            day1 = calendar.get(Calendar.MONTH) + 1;
            calendar.setTime(toDate);
            day2 = calendar.get(Calendar.MONTH) + 1;
            times = (day2 - day1) < 0 ? 1 : day2 - day1;
        } else if (times > dayOfYear) {
            calendar.setTime(fromDate);
            day1 = calendar.get(Calendar.YEAR);
            calendar.setTime(toDate);
            day2 = calendar.get(Calendar.YEAR);
            times = day2 - day1;
        }

        SimpleDateFormat sdfFullDate = new SimpleDateFormat("dd/MM/yyyy");
        String title = "";
        if (times == 1 && !format.equals("yyyy")) {
            title = "NGÀY " + sdfFullDate.format(fromDate);
        } else {
            title = "TỪ NGÀY " + sdfFullDate.format(fromDate) + " ĐẾN NGÀY " + sdfFullDate.format(toDate);
        }

        String timeUnit = "Ngày";
        if (format.equals("mm")) {
            timeUnit = "Tháng";
        } else if (format.equals("yyyy")) {
            timeUnit = "Năm";
        }

        dataset = new DefaultCategoryDataset();
        int size = totalPriceList.size();
        Double total = 0.0;

        calendar.setTime(fromDate);
        int oldMonth = calendar.get(Calendar.MONTH) + 1;
        for (int i = 0; i <= times; i++) {
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);

            String timeStr = "";
            String dayStr = day < 10 ? "0" + day : day + "";
            String monthStr = month < 10 ? "0" + month : month + "";
            String yearStr = year + "";
            String fullDayStr = "";

            if (format.equals("mm")) {
                timeStr = monthStr;
                fullDayStr = monthStr + "-" + yearStr;
                calendar.add(Calendar.MONTH, 1);
            } else if (format.equals("dd")) {
                timeStr = dayStr;
                if (oldMonth != month)
                    timeStr += "'";
                fullDayStr = dayStr + "-" + monthStr + "-" + yearStr;
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            } else {
                timeStr = yearStr;
                fullDayStr = yearStr;
                calendar.add(Calendar.YEAR, 1);
            }

            Double totalPrice = 0.0;

            for (int k = 0; k < size; k++) {
                System.out.printf(totalPriceList.get(k)[0] + "\n");
                System.out.printf(totalPriceList.get(k)[1] + "\n");
                if (fullDayStr.trim().equals(totalPriceList.get(k)[0])) {
                    System.out.printf(fullDayStr + "----------------");
                    totalPrice = (Double) totalPriceList.get(k)[1];
                    if (totalPrice == null) {
                        totalPrice = 0.0;
                    }
                    total += totalPrice;
                    break;
                }
            }

            if (totalPrice < 0.0 || totalPrice == null) {
                totalPrice = 0.0;
            }
            System.out.println("Ngày: " + fullDayStr + ", Doanh thu: " + totalPrice);
            dataset.addValue(totalPrice, "VND", timeStr);
        }
        txtTongDoanhThu.setText(df.format(total));

        JFreeChart chart = ChartFactory.createBarChart("BIỂU ĐỒ DOANH THU " + title, timeUnit, "VND", dataset,
                PlotOrientation.VERTICAL, false, false, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        pnlChare.setChart(chart);
    }

    /**
     * Thống kê doanh thu
     */
    private void statistical(ArrayList<Bill> listBill) throws ParseException {
        Date fromDate = pickerTuNgay.getFullDate();
        Date toDate = pickerDenNgay.getFullDate();

        int TotalBill = 0;
        TotalBill = billDAO.getTotalLineOfBillList(fromDate, toDate);

        System.out.printf(TotalBill + "");

        ArrayList<Object[]> totalPriceList = new ArrayList<>();
        long difference = toDate.getTime() - fromDate.getTime();
        int days = (int) TimeUnit.MILLISECONDS.toDays(difference);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = 30;
        int dayOfYear = 365;
        int year = calendar.get(Calendar.YEAR);
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                dayOfMonth = 31;
                break;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    dayOfMonth = 29;
                    dayOfYear = 366;
                } else {
                    dayOfMonth = 28;
                }
                break;
        }
        if (days < 0) {
            String message = "Ngày kết thúc thống kê phải lớn hơn hoặc bằng ngày bắt đầu thống kê";
            JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else if (days >= 0 && days <= dayOfMonth) {
            totalPriceList = calculateTotalByDate(listBill);
            showStatistical(fromDate, toDate, dayOfMonth, dayOfYear, "dd", totalPriceList);
        } else if (days > dayOfMonth && days <= dayOfYear) {
            totalPriceList = calculateTotalByMonth(listBill);
            showStatistical(fromDate, toDate, dayOfMonth, dayOfYear, "mm", totalPriceList);
        } else if (days > dayOfYear) {
            totalPriceList = calculateTotalByYear(listBill);
            showStatistical(fromDate, toDate, dayOfMonth, dayOfYear, "yyyy", totalPriceList);
        }
    }

}
