package UI.component;

import DAO.BillDAO;
import Entity.Bill;
import UI.CustomUI.Custom;
import UI.component.Dialog.DatePicker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Statistic_UI extends JPanel implements  ActionListener, ItemListener {
    private ChartPanel chartPanel;
    private JButton btnLamMoi, btnThongKe;
    private JComboBox<String> comboBoxLocTheo;
    private DatePicker pickerTuNgay,pickerDenNgay;
    private JLabel backgroundLabel,timeLabel;
    private BillDAO billDAO;

    public Statistic_UI(){
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        billDAO = new BillDAO();
        //phan viet code

        JLabel headerLabel = new JLabel("THỐNG KÊ DOANH THU");
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
        JLabel labelTuNgay = new JLabel("Từ ngày:");
        labelTuNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTuNgay.setBounds(150, 100, 120, 30);
        labelTuNgay.setForeground(Color.WHITE);
        add(labelTuNgay);

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
        JLabel labelLocTheo = new JLabel("Lọc theo:");
        labelLocTheo.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLocTheo.setBounds(750, 100, 150, 30);
        labelLocTheo.setForeground(Color.WHITE);
        add(labelLocTheo);

        comboBoxLocTheo = new JComboBox<String>();
        comboBoxLocTheo.addItem("Tùy chỉnh");
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


        JPanel panelDoanhThu = new JPanel();
        panelDoanhThu.setLayout(null);
        panelDoanhThu.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DOANH THU",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDoanhThu.setBounds(5, 130, 1235, 530);
        panelDoanhThu.setOpaque(false);

        JFreeChart chart = ChartFactory.createBarChart("BIỂU ĐỒ DOANH THU", "Ngày", "VND", null,
                PlotOrientation.VERTICAL, false, false, false);
        // chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.setBackgroundPaint(Color.WHITE);

        chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(10, 20, 950, 500);
        chartPanel.setOpaque(false);
        panelDoanhThu.add(chartPanel);

//        Tổng doanh thu
        JLabel labelTongDoanhThu = new JLabel("Tổng doanh thu:");
        labelTongDoanhThu.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTongDoanhThu.setBounds(980, 50, 150, 30);
        labelTongDoanhThu.setForeground(Color.WHITE);
        panelDoanhThu.add(labelTongDoanhThu);

        JTextField textFieldTongDoanhThu = new JTextField();
        textFieldTongDoanhThu.setBounds(980, 80, 150, 30);
        textFieldTongDoanhThu.setColumns(3);
        panelDoanhThu.add(textFieldTongDoanhThu);

        JLabel labelVND = new JLabel("VND");
        labelVND.setFont(new Font("Arial", Font.PLAIN, 14));
        labelVND.setBounds(1140, 80, 150, 30);
        labelVND.setForeground(Color.WHITE);
        panelDoanhThu.add(labelVND);

        panel1.add(panelDoanhThu);
        //
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

    @Override
    public void itemStateChanged(ItemEvent e) {
//        Object o = e.getSource();
//        if (o.equals(comboBoxLocTheo)) {
//            String search = comboBoxLocTheo.getSelectedItem().toString();
//            switch (search) {
//                case "7 ngày gần nhất":
//                    pickerTuNgay.setActive(false);
//                    pickerDenNgay.setActive(false);
//                    modelTableDV.getDataVector().removeAllElements();
//                    tableDV.removeAll();
//                    Date tuNgay = pickerTuNgay.setDatesFromToday(Calendar.DAY_OF_MONTH, -6);
//                    Date denNgay = null;
//                    try {
//                        denNgay = pickerDenNgay.getValueToDay();
//                    } catch (ParseException ex) {
//                        ex.printStackTrace();
//                    }
//                    ArrayList<Bill> listBill = billDAO.getListBillByDate(tuNgay, denNgay);
//                    docDuLieuVaoTable(listBill);
//                    break;
//                case "1 tháng gần nhất":
//                    pickerTuNgay.setActive(false);
//                    pickerDenNgay.setActive(false);
//                    modelTableDV.getDataVector().removeAllElements();
//                    tableDV.removeAll();
//                    Date tuNgay1 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -1);
//                    Date denNgay1 = null;
//                    try {
//                        denNgay1 = pickerDenNgay.getValueToDay();
//                    } catch (ParseException ex) {
//                        ex.printStackTrace();
//                    }
//                    ArrayList<Bill> listBill1 = billDAO.getListBillByDate(tuNgay1, denNgay1);
//                    docDuLieuVaoTable(listBill1);
//                    break;
//                case "3 tháng gần nhất":
//                    pickerTuNgay.setActive(false);
//                    pickerDenNgay.setActive(false);
//                    modelTableDV.getDataVector().removeAllElements();
//                    tableDV.removeAll();
//                    Date tuNgay2 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -2);
//                    Date denNgay2 = null;
//                    try {
//                        denNgay2 = pickerDenNgay.getValueToDay();
//                    } catch (ParseException ex) {
//                        ex.printStackTrace();
//                    }
//                    ArrayList<Bill> listBill2 = billDAO.getListBillByDate(tuNgay2, denNgay2);
//                    docDuLieuVaoTable(listBill2);
//                    break;
//                case "6 tháng gần nhất":
//                    pickerTuNgay.setActive(false);
//                    pickerDenNgay.setActive(false);
//                    modelTableDV.getDataVector().removeAllElements();
//                    tableDV.removeAll();
//                    Date tuNgay3 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -5);
//                    Date denNgay3 = null;
//                    try {
//                        denNgay3 = pickerDenNgay.getValueToDay();
//                    } catch (ParseException ex) {
//                        ex.printStackTrace();
//                    }
//                    ArrayList<Bill> listBill3 = billDAO.getListBillByDate(tuNgay3, denNgay3);
//                    docDuLieuVaoTable(listBill3);
//                    break;
//                case "1 năm gần nhất":
//                    pickerTuNgay.setActive(false);
//                    pickerDenNgay.setActive(false);
//                    modelTableDV.getDataVector().removeAllElements();
//                    tableDV.removeAll();
//                    Date tuNgay4 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -11);
//                    Date denNgay4 = null;
//                    try {
//                        denNgay4 = pickerDenNgay.getValueToDay();
//                    } catch (ParseException ex) {
//                        ex.printStackTrace();
//                    }
//                    ArrayList<Bill> listBill4 = billDAO.getListBillByDate(tuNgay4, denNgay4);
//                    docDuLieuVaoTable(listBill4);
//
//                    break;
//                case "Tùy chỉnh":
//                    pickerTuNgay.setActive(true);
//                    pickerDenNgay.setActive(true);
//                    pickerTuNgay.setValueToDay();
//                    break;
//            }
//        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThongKe)){

        }else if (o.equals(btnLamMoi)){

        }
    }

    public void showStatistical(Date fromDate, Date toDate, int dayOfMonth, int dayOfYear, String format,
                                ArrayList<Object[]> totalPriceList) {
        chartPanel.removeAll();
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

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int size = totalPriceList.size();
        Double total = 0.0;
        int totalPriceListIndex = 0;

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
            if (size > totalPriceListIndex && size != 0) {
                if (totalPriceList.get(totalPriceListIndex)[0].equals(fullDayStr)) {
                    totalPrice = (Double) totalPriceList.get(totalPriceListIndex)[1];
                    if (totalPrice == null) {
                        totalPrice = 0.0;
                    }
                    total += totalPrice;
                    totalPriceListIndex++;
                }
            }

            if (totalPrice < 0.0 || totalPrice == null) {
                totalPrice = 0.0;
            }
            dataset.addValue(totalPrice, "VND", timeStr);
        }
//        txtTotalPrice.setText(df.format(total));

        JFreeChart chart = ChartFactory.createBarChart("BIỂU ĐỒ DOANH THU " + title, timeUnit, "VND", dataset,
                PlotOrientation.VERTICAL, false, false, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chartPanel.setChart(chart);
    }

    private void statistical(){
        Date fromDate = pickerTuNgay.getValueSqlDate();
        Date toDate = pickerDenNgay.getValueSqlDate();

        int TotalBill = 0;
        TotalBill = billDAO.getTotalLineOfBillList(fromDate, toDate);
//        txtTotalBill.setText(df.format(TotalBill));

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
//        if (days < 0) {
//            String message = "Ngày kết thúc thống kê phải lớn hơn hoặc bằng ngày bắt đầu thống kê";
//            JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
//        } else if (days >= 0 && days <= dayOfMonth) {
//            totalPriceList = billDAO.getTotalPriceBillListByDate(fromDate, toDate,
//                    DAY);
//            showStatistical(fromDate, toDate, dayOfMonth, dayOfYear, "dd", totalPriceList);
//        } else if (days > dayOfMonth && days <= dayOfYear) {
//            totalPriceList = billDAO.getTotalPriceBillListByDate(fromDate, toDate,
//                    MONTH);
//            showStatistical(fromDate, toDate, dayOfMonth, dayOfYear, "mm", totalPriceList);
//        } else if (days > dayOfYear) {
//            totalPriceList = billDAO.getTotalPriceBillListByDate(fromDate, toDate,
//                    YEAR);
//            showStatistical(fromDate, toDate, dayOfMonth, dayOfYear, "yyyy", totalPriceList);
//        }
    }

}
