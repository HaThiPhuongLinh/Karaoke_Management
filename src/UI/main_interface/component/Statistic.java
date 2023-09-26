package UI.main_interface.component;

import UI.CustomUI.Custom;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Statistic extends JPanel {
    private JLabel backgroundLabel;

    public Statistic(){
        setLayout(null);
        setBounds(0, 0, 1175, 770);

        //phan viet code
        JLabel labelHeader = new JLabel("THỐNG KÊ");
        labelHeader.setBounds(520, 10, 1175, 40);
        labelHeader.setFont(new Font("Arial", Font.BOLD, 25));
        labelHeader.setForeground(Color.WHITE);
        add(labelHeader);

        //        Từ ngày
        JLabel labelTuNgay = new JLabel("Từ ngày:");
        labelTuNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTuNgay.setBounds(30, 50, 120, 30);
        labelTuNgay.setForeground(Color.WHITE);
        add(labelTuNgay);

        DatePicker pickerTuNgay = new DatePicker(150);
        pickerTuNgay.setOpaque(false);
        pickerTuNgay.setBounds(130, 50, 250, 30);
        add(pickerTuNgay);
//      Đến ngày
        JLabel lblDenNgay = new JLabel("Đến ngày: ");
        lblDenNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDenNgay.setBounds(300, 50, 120, 30);
        lblDenNgay.setForeground(Color.WHITE);
        add(lblDenNgay);

        DatePicker pickerDenNgay = new DatePicker(150);
        pickerDenNgay.setOpaque(false);
        pickerDenNgay.setBounds(400, 50, 250, 30);
        add(pickerDenNgay);

//      Lọc theo
        JLabel labelLocTheo = new JLabel("Lọc theo:");
        labelLocTheo.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLocTheo.setBounds(600, 50, 150, 30);
        labelLocTheo.setForeground(Color.WHITE);
        add(labelLocTheo);

        JComboBox<String> comboBoxLocTheo = new JComboBox<String>();
        comboBoxLocTheo.addItem("Tùy chỉnh");
        comboBoxLocTheo.setBounds(700,50,150,30);
        Custom.setCustomComboBox(comboBoxLocTheo);
        add(comboBoxLocTheo);

        //        btn thống kê
        JButton btnThongKe = new JButton("Thống kê");
        btnThongKe.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThongKe);
        btnThongKe.setBounds(930, 50, 150, 30);
        add(btnThongKe);

//      Thống kê khách hàng
        JPanel panelTKKH = new JPanel();
        panelTKKH.setLayout(null);
        panelTKKH.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "THỐNG KÊ KHÁCH HÀNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelTKKH.setBounds(30, 100, 520, 250);
        panelTKKH.setOpaque(false);

        String[] colsTKKH = { "STT", "Mã KH", "Tên KH","Số lần sử dụng DV" };
        DefaultTableModel modelTableTKKH = new DefaultTableModel(colsTKKH, 0) ;
        JScrollPane scrollPaneTKKH;

        JTable tableTKKH = new JTable(modelTableTKKH);
        tableTKKH.setFont(new Font("Arial", Font.BOLD, 14));
        tableTKKH.setBackground(new Color(255, 255, 255, 0));
        tableTKKH.setForeground(new Color(255, 255, 255));
        tableTKKH.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableTKKH.getTableHeader().setForeground(Color.BLUE);
//        tableLDV.getTableHeader().setBackground(new Color(255, 255, 255));

        panelTKKH.add(scrollPaneTKKH = new JScrollPane(tableTKKH,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneTKKH.setBounds(10,20,500,220);
        scrollPaneTKKH.setOpaque(false);
        scrollPaneTKKH.getViewport().setOpaque(false);
        scrollPaneTKKH.getViewport().setBackground(Color.WHITE);
        add(panelTKKH);


//      Thống kê dịch vụ
        JPanel panelTKDV = new JPanel();
        panelTKDV.setLayout(null);
        panelTKDV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "THỐNG KÊ DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelTKDV.setBounds(600, 100, 520, 250);
        panelTKDV.setOpaque(false);

        String[] colsTKDV = { "STT", "Mã DV", "Tên DV","DV dùng nhiều nhất" };
        DefaultTableModel modelTableTKDV = new DefaultTableModel(colsTKDV, 0) ;
        JScrollPane scrollPaneTKDV;

        JTable tableTKDV = new JTable(modelTableTKDV);
        tableTKDV.setFont(new Font("Arial", Font.BOLD, 14));
        tableTKDV.setBackground(new Color(255, 255, 255, 0));
        tableTKDV.setForeground(new Color(255, 255, 255));
        tableTKDV.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableTKDV.getTableHeader().setForeground(Color.BLUE);
//        tableLDV.getTableHeader().setBackground(new Color(255, 255, 255));

        panelTKDV.add(scrollPaneTKDV = new JScrollPane(tableTKDV,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneTKDV.setBounds(10,20,500,220);
        scrollPaneTKDV.setOpaque(false);
        scrollPaneTKDV.getViewport().setOpaque(false);
        scrollPaneTKDV.getViewport().setBackground(Color.WHITE);
        add(panelTKDV);

        JFreeChart chart = ChartFactory.createBarChart("BIỂU ĐỒ DOANH THU", "Ngày", "VND", null,
                PlotOrientation.VERTICAL, false, false, false);
        // chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.setBackgroundPaint(Color.WHITE);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(30, 380, 800, 290);
        chartPanel.setOpaque(false);
        add(chartPanel);

//        Tổng doanh thu
        JLabel labelTongDoanhThu = new JLabel("Tổng doanh thu:");
        labelTongDoanhThu.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTongDoanhThu.setBounds(850, 400, 150, 30);
        labelTongDoanhThu.setForeground(Color.WHITE);
        add(labelTongDoanhThu);

        JTextField textFieldTongDoanhThu = new JTextField();
        textFieldTongDoanhThu.setBounds(850, 430, 150, 30);
        textFieldTongDoanhThu.setColumns(3);
        add(textFieldTongDoanhThu);

        JLabel labelVND = new JLabel("VND");
        labelVND.setFont(new Font("Arial", Font.PLAIN, 14));
        labelVND.setBounds(1010, 430, 150, 30);
        labelVND.setForeground(Color.WHITE);
        add(labelVND);

        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);
    }
}
