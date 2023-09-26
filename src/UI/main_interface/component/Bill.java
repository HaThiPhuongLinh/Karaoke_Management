package UI.main_interface.component;

import UI.CustomUI.Custom;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Bill extends JPanel {
    private JLabel backgroundLabel;

    public Bill(){
        setLayout(null);
        setBounds(0, 0, 1175, 770);

        //phan viet code
        JLabel labelHeader = new JLabel("QUẢN LÍ HÓA ĐƠN");
        labelHeader.setBounds(520, 10, 1175, 40);
        labelHeader.setFont(new Font("Arial", Font.BOLD, 25));
        labelHeader.setForeground(Color.WHITE);
        add(labelHeader);

        JPanel panelFull = new JPanel();
        panelFull.setLayout(null);
        panelFull.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
                TitledBorder.LEADING, TitledBorder.TOP));
        panelFull.setBounds(10,50,1125,635);
        panelFull.setOpaque(false);
        add(panelFull);

//        Từ ngày
        JLabel labelTuNgay = new JLabel("Từ ngày:");
        labelTuNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTuNgay.setBounds(30, 30, 150, 30);
        labelTuNgay.setForeground(Color.WHITE);
        panelFull.add(labelTuNgay);

        DatePicker pickerTuNgay = new DatePicker(250);
        pickerTuNgay.setOpaque(false);
        pickerTuNgay.setBounds(130, 30, 250, 30);
        panelFull.add(pickerTuNgay);
//      Đến ngày
        JLabel lblDenNgay = new JLabel("Đến ngày: ");
        lblDenNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDenNgay.setBounds(30, 80, 150, 30);
        lblDenNgay.setForeground(Color.WHITE);
        panelFull.add(lblDenNgay);

        DatePicker pickerDenNgay = new DatePicker(250);
        pickerDenNgay.setOpaque(false);
        pickerDenNgay.setBounds(130, 80, 250, 30);
        panelFull.add(pickerDenNgay);

//      Lọc theo
        JLabel labelLocTheo = new JLabel("Lọc theo:");
        labelLocTheo.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLocTheo.setBounds(30, 130, 150, 30);
        labelLocTheo.setForeground(Color.WHITE);
        panelFull.add(labelLocTheo);

        JComboBox<String> comboBoxLocTheo = new JComboBox<String>();
        comboBoxLocTheo.addItem("Tất cả");
        comboBoxLocTheo.setBounds(130,130,208,30);
        Custom.setCustomComboBox(comboBoxLocTheo);
        panelFull.add(comboBoxLocTheo);

        //      Từ khóa
        JLabel labelTuKhoa = new JLabel("Từ khóa:");
        labelTuKhoa.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTuKhoa.setBounds(30, 180, 150, 30);
        labelTuKhoa.setForeground(Color.WHITE);
        panelFull.add(labelTuKhoa);

        JTextField textFieldTuKhoa = new JTextField();
        textFieldTuKhoa.setBounds(130, 180, 207, 30);
        textFieldTuKhoa.setColumns(6);
        panelFull.add(textFieldTuKhoa);

        //        btn tìm kiếm
        JButton btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnTimKiem);
        btnTimKiem.setBounds(30, 230, 150, 30);
        panelFull.add(btnTimKiem);

        //        btn xuat hoa don
        JButton btnXuatHoaDon = new JButton("Xuất hóa đơn");
        btnXuatHoaDon.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnXuatHoaDon);
        btnXuatHoaDon.setBounds(188, 230, 150, 30);
        panelFull.add(btnXuatHoaDon);

        //      danh sách hóa đơn
        JPanel panelDSHD = new JPanel();
        panelDSHD.setLayout(null);
        panelDSHD.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH HÓA ĐƠN",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSHD.setBounds(30, 290, 1065, 320);
        panelDSHD.setOpaque(false);

        String[] colsHD = { "STT", "Mã HD", "Ngày đặt","Ngày trả","Mã phòng","Tên khách hàng","Tên nhân viên","Tổng tiền" };
        DefaultTableModel modelTableHD = new DefaultTableModel(colsHD, 0) ;
        JScrollPane scrollPaneHD;

        JTable tableHD = new JTable(modelTableHD);
        tableHD.setFont(new Font("Arial", Font.BOLD, 14));
        tableHD.setBackground(new Color(255, 255, 255, 0));
        tableHD.setForeground(new Color(255, 255, 255));
        tableHD.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableHD.getTableHeader().setForeground(Color.BLUE);
//        tableLDV.getTableHeader().setBackground(new Color(255, 255, 255));

        panelDSHD.add(scrollPaneHD = new JScrollPane(tableHD,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneHD.setBounds(10,20,1045,290);
        scrollPaneHD.setOpaque(false);
        scrollPaneHD.getViewport().setOpaque(false);
        scrollPaneHD.getViewport().setBackground(Color.WHITE);
        panelFull.add(panelDSHD);

        //      danh sách Chi tiết hóa đơn
        JPanel panelCTHD = new JPanel();
        panelCTHD.setLayout(null);
        panelCTHD.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "CHI TIẾT HÓA ĐƠN",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelCTHD.setBounds(380, 25, 714, 250);
        panelCTHD.setOpaque(false);

        String[] colsCTHD = { "STT", "Tên DV", "Số lượng đặt","Giá tiền","Thành tiền" };
        DefaultTableModel modelTableLDV = new DefaultTableModel(colsCTHD, 0) ;
        JScrollPane scrollPaneCTHD;

        JTable tableCTHD = new JTable(modelTableLDV);
        tableCTHD.setFont(new Font("Arial", Font.BOLD, 14));
        tableCTHD.setBackground(new Color(255, 255, 255, 0));
        tableCTHD.setForeground(new Color(255, 255, 255));
        tableCTHD.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableCTHD.getTableHeader().setForeground(Color.BLUE);
//        tableLDV.getTableHeader().setBackground(new Color(255, 255, 255));

        panelCTHD.add(scrollPaneCTHD = new JScrollPane(tableCTHD,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneCTHD.setBounds(10,20,694,220);
        scrollPaneCTHD.setOpaque(false);
        scrollPaneCTHD.getViewport().setOpaque(false);
        scrollPaneCTHD.getViewport().setBackground(Color.WHITE);
        panelFull.add(panelCTHD);

        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);
    }
}
