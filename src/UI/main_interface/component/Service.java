package UI.main_interface.component;

import UI.CustomUI.Custom;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Service extends JPanel {
    private JLabel backgroundLabel;

    public Service(){
        setLayout(null);
        setBounds(0, 0, 1175, 770);

        //phan viet code
        JPanel panel1 =  new JPanel();
        panel1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "QUẢN LÍ LOẠI DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panel1.setBounds(10, 10, 1120, 330);
        panel1.setOpaque(false);
        add(panel1);

        panel1.setLayout(null);

//        Mã loại dịch vụ
        JLabel labelMaLoaiDichVu = new JLabel("Mã loại dịch vụ:");
        labelMaLoaiDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        labelMaLoaiDichVu.setBounds(20, 40, 150, 30);
        labelMaLoaiDichVu.setForeground(Color.WHITE);
        panel1.add(labelMaLoaiDichVu);

        JTextField textFieldMaLoaiDichVu = new JTextField();
        textFieldMaLoaiDichVu.setBounds(140, 40, 311, 30);
        textFieldMaLoaiDichVu.setColumns(10);
        panel1.add(textFieldMaLoaiDichVu);

//      Tên loại dịch vụ
        JLabel labelTenLoaiDichVu = new JLabel("Tên loại dịch vụ:");
        labelTenLoaiDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTenLoaiDichVu.setBounds(20, 90, 150, 30);
        labelTenLoaiDichVu.setForeground(Color.WHITE);
        panel1.add(labelTenLoaiDichVu);

        JTextField textFieldTenLoaiDichVu = new JTextField();
        textFieldTenLoaiDichVu.setBounds(140, 90, 311, 30);
        textFieldTenLoaiDichVu.setColumns(10);
        panel1.add(textFieldTenLoaiDichVu);

//      Lọc theo
        JLabel labelLocTheo = new JLabel("Lọc theo:");
        labelLocTheo.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLocTheo.setBounds(20, 140, 150, 30);
        labelLocTheo.setForeground(Color.WHITE);
        panel1.add(labelLocTheo);

        JComboBox<String> comboBoxLocTheo = new JComboBox<String>();
        comboBoxLocTheo.addItem("Tất cả");
        comboBoxLocTheo.setBounds(140,140,311,30);
        Custom.setCustomComboBox(comboBoxLocTheo);
        panel1.add(comboBoxLocTheo);

        //      Từ khóa
        JLabel labelTuKhoa = new JLabel("Từ khóa:");
        labelTuKhoa.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTuKhoa.setBounds(20, 190, 150, 30);
        labelTuKhoa.setForeground(Color.WHITE);
        panel1.add(labelTuKhoa);

        JTextField textFieldTuKhoa = new JTextField();
        textFieldTuKhoa.setBounds(140, 190, 200, 30);
        textFieldTuKhoa.setColumns(6);
        panel1.add(textFieldTuKhoa);

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

//      danh sách loại dịch vụ
        JPanel panelDSLDV = new JPanel();
        panelDSLDV.setLayout(null);
        panelDSLDV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH LOẠI DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSLDV.setBounds(460, 20, 640, 295);
        panelDSLDV.setOpaque(false);

        String[] cols = { "STT", "Mã loại dịch vụ", "Tên loại dịch vụ " };
        DefaultTableModel modelTableLDV = new DefaultTableModel(cols, 0) ;
        JScrollPane scrollPaneLDV;

        JTable tableLDV = new JTable(modelTableLDV);
        tableLDV.setFont(new Font("Arial", Font.BOLD, 14));
        tableLDV.setBackground(new Color(255, 255, 255, 0));
        tableLDV.setForeground(new Color(255, 255, 255));
        tableLDV.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableLDV.getTableHeader().setForeground(Color.BLUE);
//        tableLDV.getTableHeader().setBackground(new Color(255, 255, 255));

        panelDSLDV.add(scrollPaneLDV = new JScrollPane(tableLDV,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneLDV.setBounds(10,20,620,260);
        scrollPaneLDV.setOpaque(false);
        scrollPaneLDV.getViewport().setOpaque(false);
        scrollPaneLDV.getViewport().setBackground(Color.WHITE);
        panel1.add(panelDSLDV);

        /////////////////////////////////////////////////////////////////////////

        JPanel panel2 =  new JPanel();
        panel2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "QUẢN LÍ DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panel2.setBounds(10, 350, 1120, 340);
        panel2.setOpaque(false);
        add(panel2);

        panel2.setLayout(null);

        //        Mã dịch vụ
        JLabel labelMaDichVu = new JLabel("Mã dịch vụ:");
        labelMaDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        labelMaDichVu.setBounds(20, 20, 150, 30);
        labelMaDichVu.setForeground(Color.WHITE);
        panel2.add(labelMaDichVu);

        JTextField textFieldMaDichVu = new JTextField();
        textFieldMaDichVu.setBounds(140, 20, 311, 30);
        textFieldMaDichVu.setColumns(10);
        panel2.add(textFieldMaDichVu);

//      Tên dịch vụ
        JLabel labelTenDichVu = new JLabel("Tên dịch vụ:");
        labelTenDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTenDichVu.setBounds(20, 60, 150, 30);
        labelTenDichVu.setForeground(Color.WHITE);
        panel2.add(labelTenDichVu);

        JTextField textFieldTenDichVu = new JTextField();
        textFieldTenDichVu.setBounds(140, 60, 311, 30);
        textFieldTenDichVu.setColumns(10);
        panel2.add(textFieldTenDichVu);

        //      Giá bán
        JLabel labelGiaBan = new JLabel("Giá bán:");
        labelGiaBan.setFont(new Font("Arial", Font.PLAIN, 14));
        labelGiaBan.setBounds(20, 100, 150, 30);
        labelGiaBan.setForeground(Color.WHITE);
        panel2.add(labelGiaBan);

        JTextField textFieldGiaBan = new JTextField();
        textFieldGiaBan.setBounds(140, 100, 311, 30);
        textFieldGiaBan.setColumns(10);
        panel2.add(textFieldGiaBan);

        //      Số lượng tồn
        JLabel labelSoLuongTon = new JLabel("Số lượng tồn:");
        labelSoLuongTon.setFont(new Font("Arial", Font.PLAIN, 14));
        labelSoLuongTon.setBounds(20, 140, 150, 30);
        labelSoLuongTon.setForeground(Color.WHITE);
        panel2.add(labelSoLuongTon);

        JTextField textFieldSoLuongTon = new JTextField();
        textFieldSoLuongTon.setBounds(140, 140, 311, 30);
        textFieldSoLuongTon.setColumns(10);
        panel2.add(textFieldSoLuongTon);

        //      Loại dịch vụ
        JLabel labelLoaiDichVu = new JLabel("Loại dịch vụ:");
        labelLoaiDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLoaiDichVu.setBounds(20, 220, 150, 30);
        labelLoaiDichVu.setForeground(Color.WHITE);
        panel2.add(labelLoaiDichVu);

        JComboBox<String> comboBoxLoaiDichVu = new JComboBox<String>();
        comboBoxLoaiDichVu.addItem("Tất cả");
        comboBoxLoaiDichVu.setBounds(140,220,311,30);
        Custom.setCustomComboBox(comboBoxLoaiDichVu);
        panel2.add(comboBoxLoaiDichVu);

        //      Lọc theo
        JLabel labelLocTheoDV = new JLabel("Lọc theo:");
        labelLocTheoDV.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLocTheoDV.setBounds(20, 180, 150, 30);
        labelLocTheoDV.setForeground(Color.WHITE);
        panel2.add(labelLocTheoDV);

        JComboBox<String> comboBoxLocTheoDV = new JComboBox<String>();
        comboBoxLocTheoDV.addItem("Tất cả");
        comboBoxLocTheoDV.setBounds(140,180,311,30);
        Custom.setCustomComboBox(comboBoxLocTheoDV);
        panel2.add(comboBoxLocTheoDV);

        //      Từ khóa
        JLabel labelTuKhoaDV = new JLabel("Từ khóa:");
        labelTuKhoaDV.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTuKhoaDV.setBounds(20, 260, 150, 30);
        labelTuKhoaDV.setForeground(Color.WHITE);
        panel2.add(labelTuKhoaDV);

        JTextField textFieldTuKhoaDV = new JTextField();
        textFieldTuKhoaDV.setBounds(140, 260, 200, 30);
        textFieldTuKhoaDV.setColumns(6);
        panel2.add(textFieldTuKhoaDV);

//        btn tìm kiếm
        JButton btnTimKiemDV = new JButton("Tìm kiếm");
        btnTimKiemDV.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnTimKiemDV);
        btnTimKiemDV.setBounds(350, 260, 100, 30);
        panel2.add(btnTimKiemDV);

        //        btn thêm
        JButton btnThêmDV = new JButton("Thêm");
        btnThêmDV.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThêmDV);
        btnThêmDV.setBounds(20, 300, 100, 30);
        panel2.add(btnThêmDV);

        //        btn Xóa
        JButton btnXoaDV = new JButton("Xóa");
        btnXoaDV.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnXoaDV);
        btnXoaDV.setBounds(130, 300, 100, 30);
        panel2.add(btnXoaDV);

        //        btn sửa
        JButton btnSuaDV = new JButton("Sửa");
        btnSuaDV.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnSuaDV);
        btnSuaDV.setBounds(240, 300, 100, 30);
        panel2.add(btnSuaDV);

        //        btn làm mới
        JButton btnlamMoiDV = new JButton("Làm mới");
        btnlamMoiDV.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnlamMoiDV);
        btnlamMoiDV.setBounds(350, 300, 100, 30);
        panel2.add(btnlamMoiDV);

//      danh sách dịch vụ
        JPanel panelDSDV = new JPanel();
        panelDSDV.setLayout(null);
        panelDSDV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSDV.setBounds(460, 20, 640, 305);
        panelDSDV.setOpaque(false);

        String[] colsDV = { "STT", "Mã DV", "Tên DV","Giá bán","Số lượng","Loại DV" };
        DefaultTableModel modelTableDV = new DefaultTableModel(colsDV, 0) ;
        JScrollPane scrollPaneDV;

        JTable tableDV = new JTable(modelTableDV);
        tableDV.setFont(new Font("Arial", Font.BOLD, 14));
        tableDV.setBackground(new Color(255, 255, 255, 0));
        tableDV.setForeground(new Color(255, 255, 255));
        tableDV.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableDV.getTableHeader().setForeground(Color.BLUE);
//        tableLDV.getTableHeader().setBackground(new Color(255, 255, 255));

        panelDSDV.add(scrollPaneDV = new JScrollPane(tableDV,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneDV.setBounds(10,20,620,270);
        scrollPaneDV.setOpaque(false);
        scrollPaneDV.getViewport().setOpaque(false);
        scrollPaneDV.getViewport().setBackground(Color.WHITE);
        panel2.add(panelDSDV);
        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);
    }
}
