package UI.main_interface.component;

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

public class Room extends JPanel {

    private JPanel pnlShowRoom, pnlRoomList, timeNow, pnlRoomControl, pnlShowCustomer, pnlShowDetails;
    private JLabel backgroundLabel, timeLabel, roomLabel, statusLabel, customerLabel;

    public Room(){
        setLayout(null);
        setBounds(0, 0, 1175, 770);

        //phan viet code
        JPanel panel1 =  new JPanel();
        panel1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "QUẢN LÍ LOẠI PHÒNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panel1.setBounds(10, 10, 1120, 330);
        panel1.setOpaque(false);
        add(panel1);

        panel1.setLayout(null);

//        Mã loại dịch vụ
        JLabel labelMaLPhong = new JLabel("Mã loại Phòng:");
        labelMaLPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        labelMaLPhong.setBounds(20, 40, 150, 30);
        labelMaLPhong.setForeground(Color.WHITE);
        panel1.add(labelMaLPhong);

        JTextField textFieldMaLoaiPhong = new JTextField();
        textFieldMaLoaiPhong.setBounds(140, 40, 311, 30);
        textFieldMaLoaiPhong.setColumns(10);
        panel1.add(textFieldMaLoaiPhong);

//      Tên loại dịch vụ
        JLabel labelTenLoaiPhong = new JLabel("Tên loại phòng:");
        labelTenLoaiPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTenLoaiPhong.setBounds(20, 90, 150, 30);
        labelTenLoaiPhong.setForeground(Color.WHITE);
        panel1.add(labelTenLoaiPhong);

        JTextField textFieldTenLoaiPhong = new JTextField();
        textFieldTenLoaiPhong.setBounds(140, 90, 311, 30);
        textFieldTenLoaiPhong.setColumns(10);
        panel1.add(textFieldTenLoaiPhong);

        JLabel labelSucChua = new JLabel("Sức chứa :");
        labelSucChua.setFont(new Font("Arial", Font.PLAIN, 14));
        labelSucChua.setBounds(20, 140, 150, 30);
        labelSucChua.setForeground(Color.WHITE);
        panel1.add(labelSucChua);

        JTextField textFieldSucChua = new JTextField();
        textFieldSucChua.setBounds(140, 140, 311, 30);
        textFieldSucChua.setColumns(10);
        panel1.add(textFieldSucChua);


        JLabel labelGiaTien = new JLabel("Giá Tiền :");
        labelGiaTien.setFont(new Font("Arial", Font.PLAIN, 14));
        labelGiaTien.setBounds(20, 190, 150, 30);
        labelGiaTien.setForeground(Color.WHITE);
        panel1.add(labelGiaTien);

        JTextField textFieldGiaTien = new JTextField();
        textFieldGiaTien.setBounds(140, 190, 311, 30);
        textFieldGiaTien.setColumns(10);
        panel1.add(textFieldGiaTien);




//        btn tìm kiếm
        JButton btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnTimKiem);
        btnTimKiem.setBounds(350, 190, 100, 30);
        panel1.add(btnTimKiem);

        //        btn thêm
        JButton btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThem);
        btnThem.setBounds(20, 260, 100, 30);
        panel1.add(btnThem);

        //        btn Xóa
        JButton btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnXoa);
        btnXoa.setBounds(130, 260, 100, 30);
        panel1.add(btnXoa);

        //        btn sửa
        JButton btnSua = new JButton("Sửa");
        btnSua.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnSua);
        btnSua.setBounds(240, 260, 100, 30);
        panel1.add(btnSua);

        //        btn làm mới
        JButton btnlamMoi = new JButton("Làm mới");
        btnlamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnlamMoi);
        btnlamMoi.setBounds(350, 260, 100, 30);
        panel1.add(btnlamMoi);

//      danh sách loại phòng
        JPanel panelDSLP = new JPanel();
        panelDSLP.setLayout(null);
        panelDSLP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH LOẠI PHÒNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSLP.setBounds(460, 20, 640, 295);
        panelDSLP.setOpaque(false);

        String[] cols = {"STT", "Mã loại phòng", "Tên loại phòng ","Sức chứa","Giá tiền" };
        DefaultTableModel modelTableLP = new DefaultTableModel(cols, 0) ;
        JScrollPane scrollPaneLP;

        JTable tableLP = new JTable(modelTableLP);
        tableLP.setFont(new Font("Arial", Font.BOLD, 14));
        tableLP.setBackground(new Color(255, 255, 255, 0));
        tableLP.setForeground(new Color(255, 255, 255));
        tableLP.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableLP.getTableHeader().setForeground(Color.BLUE);
//        tableLDV.getTableHeader().setBackground(new Color(255, 255, 255));

        panelDSLP.add(scrollPaneLP = new JScrollPane(tableLP,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneLP.setBounds(10,20,620,260);
        scrollPaneLP.setOpaque(false);
        scrollPaneLP.getViewport().setOpaque(false);
        scrollPaneLP.getViewport().setBackground(Color.WHITE);
        panel1.add(panelDSLP);

        /////////////////////////////////////////////////////////////////////////

        JPanel panel2 =  new JPanel();
        panel2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "QUẢN LÍ PHÒNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panel2.setBounds(10, 350, 1120, 340);
        panel2.setOpaque(false);
        add(panel2);

        panel2.setLayout(null);

        //        Mã phòng
        JLabel labelMaPhong = new JLabel("Mã Phòng:");
        labelMaPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        labelMaPhong.setBounds(20, 20, 150, 30);
        labelMaPhong.setForeground(Color.WHITE);
        panel2.add(labelMaPhong);

        JTextField textFieldMaPhong = new JTextField();
        textFieldMaPhong.setBounds(140, 20, 311, 30);
        textFieldMaPhong.setColumns(10);
        panel2.add(textFieldMaPhong);

//      Vị trí
        JLabel labelViTri = new JLabel("Vị Trí:");
        labelViTri.setFont(new Font("Arial", Font.PLAIN, 14));
        labelViTri.setBounds(20, 60, 150, 30);
        labelViTri.setForeground(Color.WHITE);
        panel2.add(labelViTri);

        JTextField textFieldVitri = new JTextField();
        textFieldVitri.setBounds(140, 60, 311, 30);
        textFieldVitri.setColumns(10);
        panel2.add(textFieldVitri);


        //      Loại phòng
        JLabel labelLoaiPhong = new JLabel("Loại phòng:");
        labelLoaiPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLoaiPhong.setBounds(20, 100, 150, 30);
        labelLoaiPhong.setForeground(Color.WHITE);
        panel2.add(labelLoaiPhong);

        JComboBox<String> comboBoxLoaiPhong = new JComboBox<String>();
        comboBoxLoaiPhong.addItem("Tất cả");
        comboBoxLoaiPhong.setBounds(140,100,311,30);
        Custom.setCustomComboBox(comboBoxLoaiPhong);
        panel2.add(comboBoxLoaiPhong);

        //tinh trang
        JLabel labelTinhTrang= new JLabel("Tình Trạng:");
        labelTinhTrang.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTinhTrang.setBounds(20, 140, 150, 30);
        labelTinhTrang.setForeground(Color.WHITE);
        panel2.add(labelTinhTrang);

        JComboBox<String> comboBoxTinhTrang = new JComboBox<String>();
        comboBoxTinhTrang .addItem("Tất cả");
        comboBoxTinhTrang .setBounds(140,140,311,30);
        Custom.setCustomComboBox(comboBoxTinhTrang );
        panel2.add(comboBoxTinhTrang );

        //      Lọc theo
        JLabel labelLocTheoLP = new JLabel("Lọc theo:");
        labelLocTheoLP.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLocTheoLP.setBounds(20, 180, 150, 30);
        labelLocTheoLP.setForeground(Color.WHITE);
        panel2.add(labelLocTheoLP);

        JComboBox<String> comboBoxLocTheoLP = new JComboBox<String>();
        comboBoxLocTheoLP.addItem("Tất cả");
        comboBoxLocTheoLP.addItem("Tình Trạng");
        comboBoxLocTheoLP.addItem("Loại Phòng");
        comboBoxLocTheoLP.setBounds(140,180,311,30);
        Custom.setCustomComboBox(comboBoxLocTheoLP);
        panel2.add(comboBoxLocTheoLP);

        //      Từ khóa
        JLabel labelTuKhoaDV = new JLabel("Từ khóa:");
        labelTuKhoaDV.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTuKhoaDV.setBounds(20, 220, 150, 30);
        labelTuKhoaDV.setForeground(Color.WHITE);
        panel2.add(labelTuKhoaDV);

        JTextField textFieldTuKhoaDV = new JTextField();
        textFieldTuKhoaDV.setBounds(140, 220, 200, 30);
        textFieldTuKhoaDV.setColumns(6);
        panel2.add(textFieldTuKhoaDV);

//        btn tìm kiếm
        JButton btnTimKiemDV = new JButton("Tìm kiếm");
        btnTimKiemDV.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnTimKiemDV);
        btnTimKiemDV.setBounds(350, 220, 100, 30);
        panel2.add(btnTimKiemDV);

        //        btn thêm
        JButton btnThêmDV = new JButton("Thêm");
        btnThêmDV.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThêmDV);
        btnThêmDV.setBounds(20, 280, 100, 30);
        panel2.add(btnThêmDV);

        //        btn Xóa
        JButton btnXoaDV = new JButton("Xóa");
        btnXoaDV.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnXoaDV);
        btnXoaDV.setBounds(130, 280, 100, 30);
        panel2.add(btnXoaDV);

        //        btn sửa
        JButton btnSuaDV = new JButton("Sửa");
        btnSuaDV.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnSuaDV);
        btnSuaDV.setBounds(240, 280, 100, 30);
        panel2.add(btnSuaDV);

        //        btn làm mới
        JButton btnlamMoiDV = new JButton("Làm mới");
        btnlamMoiDV.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnlamMoiDV);
        btnlamMoiDV.setBounds(350, 280, 100, 30);
        panel2.add(btnlamMoiDV);

//      danh sách phòng
        JPanel panelDSP = new JPanel();
        panelDSP.setLayout(null);
        panelDSP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH PHÒNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSP.setBounds(460, 20, 640, 305);
        panelDSP.setOpaque(false);

        String[] colsP = { "STT", "Mã phòng", "Vị trí","Trạng thái","Loại Phòng" };
        DefaultTableModel modelTableP = new DefaultTableModel(colsP, 0) ;
        JScrollPane scrollPaneP;

        JTable tableP = new JTable(modelTableP);
        tableP.setFont(new Font("Arial", Font.BOLD, 14));
        tableP.setBackground(new Color(255, 255, 255, 0));
        tableP.setForeground(new Color(255, 255, 255));
        tableP.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableP.getTableHeader().setForeground(Color.BLUE);
//        tableLDV.getTableHeader().setBackground(new Color(255, 255, 255));

        panelDSP.add(scrollPaneP = new JScrollPane(tableP,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneP.setBounds(10,20,620,270);
        scrollPaneP.setOpaque(false);
        scrollPaneP.getViewport().setOpaque(false);
        scrollPaneP.getViewport().setBackground(Color.WHITE);
        panel2.add(panelDSP);


        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);
    }

}
