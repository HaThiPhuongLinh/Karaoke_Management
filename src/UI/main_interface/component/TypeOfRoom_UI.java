package UI.main_interface.component;

import ConnectDB.ConnectDB;
import DAOs.StaffDAO;
import DAOs.TypeOfRoomDAO;
import Entity.Room;
import Entity.TypeOfRoom;
import UI.CustomUI.Custom;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TypeOfRoom_UI extends JPanel{
    private  JTable tableLP;
   
    private  DefaultTableModel modelTableLP;
    private JLabel backgroundLabel, timeLabel ;
    private JPanel timeNow, pnlTPList, pnlTPControl;
    
    private TypeOfRoomDAO TypeOfRoomDAO;

    public TypeOfRoom_UI() {
        setLayout(null);
        setBounds(0, 0, 1175, 770);
        TypeOfRoomDAO = new TypeOfRoomDAO();
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel headerLabel = new JLabel("QUẢN LÝ LOẠI PHÒNG");
        headerLabel.setBounds(470, 10, 1175, 40);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        Component add = add(headerLabel);

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

        pnlTPList = new JPanel();
        pnlTPList.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Loại Phòng",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlTPList.setBounds(10, 70, 1120, 620);
        pnlTPList.setOpaque(false);
        add(pnlTPList);
        pnlTPList.setLayout(new BorderLayout(0, 0));

        pnlTPControl = new JPanel();
        pnlTPControl.setOpaque(false);
        pnlTPControl.setBackground(Color.WHITE);
        pnlTPList.add(pnlTPControl, BorderLayout.NORTH);
        pnlTPControl.setLayout(null);
        pnlTPControl.setPreferredSize(new Dimension(1100, 250));

        JLabel labelMaLPhong = new JLabel("Mã loại Phòng:");
        labelMaLPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        labelMaLPhong.setBounds(330, 10, 150, 30);
        labelMaLPhong.setForeground(Color.WHITE);
        pnlTPControl.add(labelMaLPhong);

        JTextField textFieldMaLoaiPhong = new JTextField();
        textFieldMaLoaiPhong.setBounds(465, 10, 311, 30);
        textFieldMaLoaiPhong.setColumns(10);
        pnlTPControl.add(textFieldMaLoaiPhong);

//      Tên loại dịch vụ
        JLabel labelTenLoaiPhong = new JLabel("Tên loại phòng:");
        labelTenLoaiPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTenLoaiPhong.setBounds(330, 60, 150, 30);
        labelTenLoaiPhong.setForeground(Color.WHITE);
        pnlTPControl.add(labelTenLoaiPhong);

        JTextField textFieldTenLoaiPhong = new JTextField();
        textFieldTenLoaiPhong.setBounds(465, 60, 311, 30);
        textFieldTenLoaiPhong.setColumns(10);
        pnlTPControl.add(textFieldTenLoaiPhong);

        JLabel labelSucChua = new JLabel("Sức chứa :");
        labelSucChua.setFont(new Font("Arial", Font.PLAIN, 14));
        labelSucChua.setBounds(330, 110, 150, 30);
        labelSucChua.setForeground(Color.WHITE);
        pnlTPControl.add(labelSucChua);

        JTextField textFieldSucChua = new JTextField();
        textFieldSucChua.setBounds(465, 110, 311, 30);
        textFieldSucChua.setColumns(10);
        pnlTPControl.add(textFieldSucChua);




        JTextField textFieldBaoLoi = new JTextField();
        textFieldBaoLoi.setBounds(465, 160, 311, 30);
        textFieldBaoLoi.setColumns(10);
        pnlTPControl.add(textFieldBaoLoi);






        //        btn thêm
        JButton btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThem);
        btnThem.setBounds(340, 210, 100, 30);
        pnlTPControl.add(btnThem);

        //        btn Xóa
        JButton btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnXoa);
        btnXoa.setBounds(450, 210, 100, 30);
        pnlTPControl.add(btnXoa);

        //        btn sửa
        JButton btnSua = new JButton("Sửa");
        btnSua.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnSua);
        btnSua.setBounds(560, 210, 100, 30);
        pnlTPControl.add(btnSua);

        //        btn làm mới
        JButton btnlamMoi = new JButton("Làm mới");
        btnlamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnlamMoi);
        btnlamMoi.setBounds(670, 210, 100, 30);
        pnlTPControl.add(btnlamMoi);


        JPanel panelDSLP = new JPanel();
        panelDSLP.setLayout(null);
        panelDSLP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH LOẠI PHÒNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSLP.setBounds(30, 290, 1100, 320);
        panelDSLP.setOpaque(false);

        String[] colsLP = {"STT", "Mã Loại Phòng", "Tên Loại Phòng", "Sức Chứa"};
         modelTableLP = new DefaultTableModel(colsLP, 0);
        JScrollPane scrollPaneNV;

         tableLP = new JTable(modelTableLP);
        tableLP.setFont(new Font("Arial", Font.BOLD, 14));
        tableLP.setBackground(new Color(255, 255, 255, 0));
        tableLP.setForeground(new Color(255, 255, 255));
        tableLP.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableLP.getTableHeader().setForeground(Color.BLUE);

        Custom.getInstance().setCustomTable(tableLP);

        panelDSLP.add(scrollPaneNV = new JScrollPane(tableLP, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneNV.setBounds(10, 20, 1090, 330);
        scrollPaneNV.setOpaque(false);
        scrollPaneNV.getViewport().setOpaque(false);
        scrollPaneNV.getViewport().setBackground(Color.WHITE);
        pnlTPList.add(panelDSLP);
        loadLP();

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

    public void loadLP(){
        int i=1;

        for (TypeOfRoom room : TypeOfRoomDAO.getAllLoaiPhong()) {


            Object[] rowData = { i,room.getMaLoaiPhong(),room.getTenLoaiPhong(),room.getSucChua()};
            modelTableLP.addRow(rowData);
            i++;

        }
    }


}
