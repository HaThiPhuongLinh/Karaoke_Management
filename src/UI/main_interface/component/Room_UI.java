package UI.main_interface.component;

import javax.swing.*;
import UI.CustomUI.Custom;

import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Room_UI extends JPanel {

       private JPanel pnlRoomControl, pnlRoomList, timeNow;
       private JLabel backgroundLabel, timeLabel;





    public Room_UI(){
            setLayout(null);
            setBounds(0, 0, 1175, 770);

            JLabel headerLabel = new JLabel("QUẢN LÝ KHÁCH HÀNG");
            headerLabel.setBounds(470, 10, 1175, 40);
            headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
            headerLabel.setForeground(Color.WHITE);
            add(headerLabel);

            timeNow = new JPanel();
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

            pnlRoomList = new JPanel();
        pnlRoomList.setBorder(new TitledBorder(
                    new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Phòng",
                    TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlRoomList.setBounds(10, 70, 1120, 620);
            pnlRoomList.setOpaque(false);
            add(pnlRoomList);
        pnlRoomList.setLayout(new BorderLayout(0, 0));


        pnlRoomControl = new JPanel();
        pnlRoomControl.setOpaque(false);
        pnlRoomControl.setBackground(Color.WHITE);
        pnlRoomList.add(pnlRoomControl, BorderLayout.NORTH);
        pnlRoomControl.setLayout(null);
        pnlRoomControl.setPreferredSize(new Dimension(1100, 230));




            JPanel panelDSP = new JPanel();
        panelDSP.setLayout(null);
        panelDSP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH KHÁCH HÀNG",
                    TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSP.setBounds(30, 310, 1100, 320);
        panelDSP.setOpaque(false);

            String[] colsP = { "STT", "Mã Phòng","Mã Loại Phòng","Vị Trí","Tình Trạng" };
            DefaultTableModel modelTableP = new DefaultTableModel(colsP, 0) ;
            JScrollPane scrollPaneP;

            JTable tableP = new JTable(modelTableP);
        tableP.setFont(new Font("Arial", Font.BOLD, 14));
        tableP.setBackground(new Color(255, 255, 255, 0));
        tableP.setForeground(new Color(255, 255, 255));
        tableP.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableP.getTableHeader().setForeground(Color.BLUE);


        panelDSP.add(scrollPaneP = new JScrollPane(tableP,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                    BorderLayout.CENTER);
            scrollPaneP.setBounds(10,20,1090,330);
            scrollPaneP.setOpaque(false);
            scrollPaneP.getViewport().setOpaque(false);
            scrollPaneP.getViewport().setBackground(Color.WHITE);
        pnlRoomList.add(panelDSP);






        //        Mã phòng
        JLabel labelMaPhong = new JLabel("Mã Phòng:");
        labelMaPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        labelMaPhong.setBounds(30, 20, 120, 30);
        labelMaPhong.setForeground(Color.WHITE);
        pnlRoomControl.add(labelMaPhong);

        JTextField textFieldMaPhong = new JTextField();
        textFieldMaPhong.setBounds(145, 20, 311, 30);
        textFieldMaPhong.setColumns(10);
        pnlRoomControl.add(textFieldMaPhong);

//      Vị trí
        JLabel labelViTri = new JLabel("Vị Trí:");
        labelViTri.setFont(new Font("Arial", Font.PLAIN, 14));
        labelViTri.setBounds(30, 70, 120, 30);
        labelViTri.setForeground(Color.WHITE);
        pnlRoomControl.add(labelViTri);

        JTextField textFieldVitri = new JTextField();
        textFieldVitri.setBounds(145, 70, 311, 30);
        textFieldVitri.setColumns(10);
        pnlRoomControl.add(textFieldVitri);


        //      Loại phòng
        JLabel labelLoaiPhong = new JLabel("Loại phòng:");
        labelLoaiPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLoaiPhong.setBounds(30, 120, 120, 30);
        labelLoaiPhong.setForeground(Color.WHITE);
        pnlRoomControl.add(labelLoaiPhong);

        JComboBox<String> comboBoxLoaiPhong = new JComboBox<String>();
        comboBoxLoaiPhong.addItem("Tất cả");
        comboBoxLoaiPhong.setBounds(145,120,311,30);
        Custom.setCustomComboBox(comboBoxLoaiPhong);
        pnlRoomControl.add(comboBoxLoaiPhong);

        //tinh trang
        JLabel labelTinhTrang= new JLabel("Tình Trạng:");
        labelTinhTrang.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTinhTrang.setBounds(30, 170, 120, 30);
        labelTinhTrang.setForeground(Color.WHITE);
        pnlRoomControl.add(labelTinhTrang);

        JComboBox<String> comboBoxTinhTrang = new JComboBox<String>();
        comboBoxTinhTrang .addItem("Tất cả");
        comboBoxTinhTrang .setBounds(145,170,311,30);
        Custom.setCustomComboBox(comboBoxTinhTrang );
        pnlRoomControl.add(comboBoxTinhTrang );

        //      Lọc theo
        JLabel labelLocTheoLP = new JLabel("Lọc theo:");
        labelLocTheoLP.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLocTheoLP.setBounds(550, 20, 150, 30);
        labelLocTheoLP.setForeground(Color.WHITE);
        pnlRoomControl.add(labelLocTheoLP);

        JComboBox<String> comboBoxLocTheoLP = new JComboBox<String>();
        comboBoxLocTheoLP.addItem("Tất cả");
        comboBoxLocTheoLP.addItem("Tình Trạng");
        comboBoxLocTheoLP.addItem("Loại Phòng");
        comboBoxLocTheoLP.setBounds(665,20,311,30);
        Custom.setCustomComboBox(comboBoxLocTheoLP);
        pnlRoomControl.add(comboBoxLocTheoLP);

        //      Từ khóa
        JLabel labelTuKhoaDV = new JLabel("Từ khóa:");
        labelTuKhoaDV.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTuKhoaDV.setBounds(550, 70, 150, 30);
        labelTuKhoaDV.setForeground(Color.WHITE);
        pnlRoomControl.add(labelTuKhoaDV);

        JTextField textFieldTuKhoaDV = new JTextField();
        textFieldTuKhoaDV.setBounds(665, 70, 200, 30);
        textFieldTuKhoaDV.setColumns(6);
        pnlRoomControl.add(textFieldTuKhoaDV);

//        btn tìm kiếm
        JButton btnTimKiemP = new JButton("Tìm kiếm");
        btnTimKiemP.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnTimKiemP);
        btnTimKiemP.setBounds(875, 70, 100, 30);
        pnlRoomControl.add(btnTimKiemP);

        //        btn thêm
        JButton btnThêmP = new JButton("Thêm");
        btnThêmP.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThêmP);
        btnThêmP.setBounds(550, 170, 100, 30);
        pnlRoomControl.add(btnThêmP);

        //        btn Xóa
        JButton btnXoaP = new JButton("Xóa");
        btnXoaP.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnXoaP);
        btnXoaP.setBounds(690, 170, 100, 30);
        pnlRoomControl.add(btnXoaP);

        //        btn sửa
        JButton btnSuaP = new JButton("Sửa");
        btnSuaP.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnSuaP);
        btnSuaP.setBounds(830, 170, 100, 30);
        pnlRoomControl.add(btnSuaP);

        //        btn làm mới
        JButton btnlamMoiP = new JButton("Làm mới");
        btnlamMoiP.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnlamMoiP);
        btnlamMoiP.setBounds(970, 170, 100, 30);
        pnlRoomControl.add(btnlamMoiP);


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

}
