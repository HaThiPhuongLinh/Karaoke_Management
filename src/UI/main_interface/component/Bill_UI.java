package UI.main_interface.component;

import UI.CustomUI.Custom;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Bill_UI extends JPanel {
    private JLabel backgroundLabel;

    public Bill_UI(){
        setLayout(null);
        setBounds(0, 0, 1175, 770);

        //phan viet code
        JLabel labelHeader = new JLabel("LẬP HOÁ ĐƠN");
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
        JLabel labelKM = new JLabel("Mã Khuyến Mãi:");
        labelKM.setFont(new Font("Arial", Font.PLAIN, 14));
        labelKM.setBounds(20, 60, 150, 30);
        labelKM.setForeground(Color.WHITE);
        panelFull.add(labelKM);




        JTextField textFieldKM = new JTextField();
        textFieldKM.setBounds(130, 60, 207, 30);
        textFieldKM.setColumns(6);
        panelFull.add(textFieldKM);

        //        btn tìm kiếm
        JLabel labelTK = new JLabel("Tìm Theo Mã Phòng:");
        labelTK.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTK.setBounds(660, 20, 150, 30);
        labelTK.setForeground(Color.WHITE);
        panelFull.add(labelTK);




        JTextField textFieldTK = new JTextField();
        textFieldTK.setBounds(820, 20, 150, 30);
        textFieldTK.setColumns(6);
        panelFull.add(textFieldTK);
        JButton btnTim = new JButton("Tìm");
        btnTim.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnTim);
        btnTim.setBounds(990, 20, 100, 30);
        panelFull.add(btnTim);



        //
        JButton btnLap = new JButton("Lập Hóa Đơn");
        btnLap.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnLap);
        btnLap.setBounds(100, 230, 200, 50);
        panelFull.add(btnLap);



        //      danh sách phong dang su dung
        JPanel panelDSHD = new JPanel();
        panelDSHD.setLayout(null);
        panelDSHD.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Danh Sách Dịch Vụ Được Sử Dụng",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSHD.setBounds(30, 320, 1065, 320);
        panelDSHD.setOpaque(false);

        String[] colsHD = { "STT", "Mã Dịch Vụ", "Tên Dịch Vụ","Số Lượng Đặt","Giá Dịch Vụ","Tổng Tiền" };
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
        panelCTHD.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Danh Sách Các Phòng Đang Được Dùng",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelCTHD.setBounds(380, 55, 714, 250);
        panelCTHD.setOpaque(false);

        String[] colsCTHD = { "STT", "Mã Phòng", "Loại Phòng","Giờ Vào","Giá Phòng" };
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
