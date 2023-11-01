package UI.component;

import ConnectDB.ConnectDB;
import DAO.ServiceDAO;
import DAO.TypeOfServiceDAO;
import Entity.Service;
import Entity.Staff;
import Entity.TypeOfService;
import UI.CustomUI.Custom;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchingService_UI extends JPanel implements ActionListener, MouseListener {
    private JComboBox<String> comboBoxLDV,comboBoxGiaban;
    private JTable tableDV;
    private JTextField textFieldMaDichVu,textFieldTenDichVu,textFieldTenLoaiDichVu,textFieldGiaBan;
    private JButton btnlamMoi;
    private JButton btnTimKiem;
    private JLabel backgroundLabel,timeLabel;
    private ServiceDAO serviceDAO;
    private TypeOfServiceDAO typeOfServiceDAO;
    private DefaultTableModel modelTableDV;
    private DecimalFormat df = new DecimalFormat("#,###.##");
    public static Staff staffLogin = null;

    public SearchingService_UI(Staff staff){
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        serviceDAO = new ServiceDAO();
        typeOfServiceDAO = new TypeOfServiceDAO();

        //phan viet code
        JLabel headerLabel = new JLabel("TÌM KIẾM DỊCH VỤ");
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
        panel1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panel1.setBounds(10, 70, 1245, 670);
        panel1.setOpaque(false);
        add(panel1);

        panel1.setLayout(null);

//      Tên dịch vụ
        JLabel labelTenDichVu = new JLabel("Tên dịch vụ:");
        labelTenDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTenDichVu.setBounds(150, 50, 150, 30);
        labelTenDichVu.setForeground(Color.WHITE);
        panel1.add(labelTenDichVu);

        textFieldTenDichVu = new JTextField();
        textFieldTenDichVu.setBounds(270, 50, 250, 30);
        textFieldTenDichVu.setColumns(10);
        panel1.add(textFieldTenDichVu);

        //      Loại dịch vụ
        JLabel labelLDV = new JLabel("Loại dịch vụ:");
        labelLDV.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLDV.setBounds(150, 100, 150, 30);
        labelLDV.setForeground(Color.WHITE);
        panel1.add(labelLDV);

        comboBoxLDV = new JComboBox<String>();
//        comboBoxLDV.addItem(" ");
        comboBoxLDV.setBounds(270, 100, 250, 30);
        Custom.setCustomComboBox(comboBoxLDV);
        panel1.add(comboBoxLDV);

        //      Giá bán
        JLabel labelGiaBan = new JLabel("Giá bán:");
        labelGiaBan.setFont(new Font("Arial", Font.PLAIN, 14));
        labelGiaBan.setBounds(680, 50, 150, 30);
        labelGiaBan.setForeground(Color.WHITE);
        panel1.add(labelGiaBan);

        comboBoxGiaban = new JComboBox<String>();
        comboBoxGiaban.addItem("Tất cả");
        comboBoxGiaban.addItem("10.000 - 50.000");
        comboBoxGiaban.addItem("50.000 - 100.000");
        comboBoxGiaban.addItem("100.000 - 500.000");
        comboBoxGiaban.setBounds(800, 50, 250, 30);
        Custom.setCustomComboBox(comboBoxGiaban);
        panel1.add(comboBoxGiaban);

//        textFieldGiaBan = new JTextField();
//        textFieldGiaBan.setBounds(800, 50, 250, 30);
//        textFieldGiaBan.setColumns(10);
//        panel1.add(textFieldGiaBan);

//        btn tìm kiếm
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnTimKiem);
        btnTimKiem.setBounds(800, 100, 100, 30);
        panel1.add(btnTimKiem);

        //        btn làm mới
        btnlamMoi = new JButton("Làm mới");
        btnlamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnlamMoi);
        btnlamMoi.setBounds(950, 100, 100, 30);
        panel1.add(btnlamMoi);

//      danh sách dịch vụ
        JPanel panelDSDV = new JPanel();
        panelDSDV.setLayout(null);
        panelDSDV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSDV.setBounds(5, 160, 1235, 505);
        panelDSDV.setOpaque(false);

        String[] colsDV = { "STT", "Mã dịch vụ", "Tên dịch vụ","Tên loại dịch vụ","Đơn vị tính","Số lượng tồn","Giá bán"};
        modelTableDV = new DefaultTableModel(colsDV, 0) ;
        JScrollPane scrollPaneDV;

        tableDV = new JTable(modelTableDV);
        tableDV.setFont(new Font("Arial", Font.BOLD, 14));
        tableDV.setBackground(new Color(255, 255, 255, 0));
        tableDV.setForeground(new Color(255, 255, 255));
        tableDV.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableDV.getTableHeader().setForeground(Color.BLUE);
//        tableLDV.getTableHeader().setBackground(new Color(255, 255, 255));
        Custom.getInstance().setCustomTable(tableDV);

        panelDSDV.add(scrollPaneDV = new JScrollPane(tableDV,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneDV.setBounds(10,20,1220,480);
        scrollPaneDV.setOpaque(false);
        scrollPaneDV.getViewport().setOpaque(false);
        scrollPaneDV.getViewport().setBackground(Color.WHITE);
        panel1.add(panelDSDV);
        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);

        btnTimKiem.addActionListener(this);
        btnlamMoi.addActionListener(this);

        loadSearchingService();
        loadCboService();
        reSizeColumnTableService();

        comboBoxLDV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoaiDichVu = (String) comboBoxLDV.getSelectedItem();
                textFieldTenDichVu.setText("");
//                textFieldGiaBan.setText("");
                comboBoxGiaban.setSelectedIndex(0);
                modelTableDV.setRowCount(0);
                int i=1;
                for (Service dv : serviceDAO.getAllDichVu()) {
                    if (selectedLoaiDichVu.equalsIgnoreCase("Tất cả") ||
                            selectedLoaiDichVu.equalsIgnoreCase(dv.getMaLoaiDichVu().getTenLoaiDichVu())) {
                        Object[] rowData = {i, dv.getMaDichVu(), dv.getTenDichVu(),dv.getMaLoaiDichVu().getTenLoaiDichVu(), dv.getDonViTinh(), dv.getSoLuongTon(), df.format(dv.getGiaBan())};
                        modelTableDV.addRow(rowData);
                    }
                    i++;
                }
            }
        });

        comboBoxGiaban.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                String selectedLoaiDichVu = (String) comboBoxLDV.getSelectedItem();
                textFieldTenDichVu.setText("");
//                textFieldGiaBan.setText("");
//                comboBoxLDV.setSelectedIndex(0);
                modelTableDV.setRowCount(0);
                int i=1;
                for (Service dv : serviceDAO.getAllDichVu()) {
                    if (comboBoxGiaban.getSelectedIndex()==0){
                        Object[] rowData = {i, dv.getMaDichVu(), dv.getTenDichVu(),dv.getMaLoaiDichVu().getTenLoaiDichVu(), dv.getDonViTinh(), dv.getSoLuongTon(), df.format(dv.getGiaBan())};
                        modelTableDV.addRow(rowData);

                    }else if (comboBoxGiaban.getSelectedIndex()==1){
                        if (dv.getGiaBan()>=10000 && dv.getGiaBan()<=50000){
                            Object[] rowData = {i, dv.getMaDichVu(), dv.getTenDichVu(),dv.getMaLoaiDichVu().getTenLoaiDichVu(), dv.getDonViTinh(), dv.getSoLuongTon(), df.format(dv.getGiaBan())};
                            modelTableDV.addRow(rowData);
                        }
                    }else if (comboBoxGiaban.getSelectedIndex()==2){
                        if (dv.getGiaBan()>=50000 && dv.getGiaBan()<=100000){
                            Object[] rowData = {i, dv.getMaDichVu(), dv.getTenDichVu(),dv.getMaLoaiDichVu().getTenLoaiDichVu(), dv.getDonViTinh(), dv.getSoLuongTon(), df.format(dv.getGiaBan())};
                            modelTableDV.addRow(rowData);
                        }
                    }else if (comboBoxGiaban.getSelectedIndex()==3){
                        if (dv.getGiaBan()>=100000 && dv.getGiaBan()<=500000){
                            Object[] rowData = {i, dv.getMaDichVu(), dv.getTenDichVu(),dv.getMaLoaiDichVu().getTenLoaiDichVu(), dv.getDonViTinh(), dv.getSoLuongTon(), df.format(dv.getGiaBan())};
                            modelTableDV.addRow(rowData);
                        }
                    }
                    i++;
                }
            }
        });
    }

    private void reSizeColumnTableService(){
        TableColumnModel tblModel = tableDV.getColumnModel();

        tblModel.getColumn(0).setPreferredWidth(20);
        tblModel.getColumn(1).setPreferredWidth(40);
        tblModel.getColumn(2).setPreferredWidth(130);
        tblModel.getColumn(3).setPreferredWidth(100);
    }

    private void loadSearchingService(){
        java.util.List<Service> list = serviceDAO.getAllDichVu();
        int i=1;
        for (Service service : list){
            modelTableDV.addRow(new Object[]{i,service.getMaDichVu(),service.getTenDichVu(),service.getMaLoaiDichVu().getTenLoaiDichVu(),service.getDonViTinh(),service.getSoLuongTon(),df.format(service.getGiaBan())});
            i++;
        }
    }

    private void loadCboService() {
        java.util.List<TypeOfService> dataList = typeOfServiceDAO.getAllLoaiDichVu();
        comboBoxLDV.addItem("Tất cả");
        for (TypeOfService serviceType : dataList) {
            comboBoxLDV.addItem(serviceType.getTenLoaiDichVu());
        }
    }
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        timeLabel.setText(time);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnTimKiem)) {
            String txtTenDV = textFieldTenDichVu.getText();
            ArrayList<Service> services2 = serviceDAO.getServiceByName(txtTenDV);
//            if (!textFieldGiaBan.getText().trim().equals("") && !textFieldTenDichVu.getText().trim().equals("")) {
//                modelTableDV.getDataVector().removeAllElements();
//                int i = 1;
//                double gia = Double.parseDouble(textFieldGiaBan.getText());
//                ArrayList<Service> services3 = serviceDAO.getDichVuTheoGia(gia);
//                if (services3.size() != 0 && services2.size() != 0) {
//                    for (Service service : services3) {
//                        for (Service s : services2) {
//                            if (s.getGiaBan() == service.getGiaBan()) {
//                                modelTableDV.addRow(new Object[]{i, s.getMaDichVu(), s.getTenDichVu(), s.getMaLoaiDichVu().getTenLoaiDichVu(), s.getDonViTinh(), s.getSoLuongTon(), df.format(s.getGiaBan())});
//                                i++;
//                            }
//                        }
//                        break;
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(this, "KHÔNG TÌM THẤY!!!");
//                }
//            } else {
                if (!textFieldTenDichVu.getText().trim().equals("")) {
                    modelTableDV.getDataVector().removeAllElements();
                    int i = 1;
                    if (services2.size() != 0) {
                        for (Service service : services2) {
                            modelTableDV.addRow(new Object[]{i, service.getMaDichVu(), service.getTenDichVu(), service.getMaLoaiDichVu().getTenLoaiDichVu(), service.getDonViTinh(), service.getSoLuongTon(), df.format(service.getGiaBan())});
                            i++;
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "KHÔNG TÌM THẤY!!!");
                        textFieldTenDichVu.selectAll();
                        textFieldTenDichVu.requestFocus();
                    }
//                }
//                else if (!textFieldGiaBan.getText().trim().equals("")) {
//                    modelTableDV.getDataVector().removeAllElements();
//                    double gia = Double.parseDouble(textFieldGiaBan.getText());
//                    ArrayList<Service> services3 = serviceDAO.getDichVuTheoGia(gia);
//                    int i = 1;
//                    if (services3.size() != 0) {
//                        for (Service service : services3) {
//                            modelTableDV.addRow(new Object[]{i, service.getMaDichVu(), service.getTenDichVu(), service.getMaLoaiDichVu().getTenLoaiDichVu(), service.getDonViTinh(), service.getSoLuongTon(), df.format(service.getGiaBan())});
//                            i++;
//                        }
//                    } else {
//                        JOptionPane.showMessageDialog(this, "KHÔNG TÌM THẤY!!!");
//                        textFieldGiaBan.selectAll();
//                        textFieldGiaBan.requestFocus();
//
//                    }
//                }
            }

        }else if(o.equals(btnlamMoi)){
            textFieldTenDichVu.setText("");
//            textFieldGiaBan.setText("");
            comboBoxGiaban.setSelectedIndex(0);
            comboBoxLDV.setSelectedIndex(0);
            modelTableDV.getDataVector().removeAllElements();
            loadSearchingService();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
