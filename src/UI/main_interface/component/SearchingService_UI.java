package UI.main_interface.component;

import ConnectDB.ConnectDB;
import DAOs.ServiceDAO;
import DAOs.TypeOfServiceDAO;
import Entity.Service;
import Entity.TypeOfService;
import UI.CustomUI.Custom;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchingService_UI extends JPanel implements ActionListener, MouseListener {
    private JTextField textFieldMaDichVu,textFieldTenDichVu,textFieldTenLoaiDichVu,textFieldGiaBan;
    private JButton btnlamMoi;
    private JButton btnTimKiem;
    private JLabel backgroundLabel,timeLabel;
    private ServiceDAO serviceDAO;
    private TypeOfServiceDAO typeOfServiceDAO;
    private DefaultTableModel modelTableDV;
    private JTable tableDV;
    public SearchingService_UI(){
        setLayout(null);
        setBounds(0, 0, 1175, 770);

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        serviceDAO = new ServiceDAO();
        typeOfServiceDAO = new TypeOfServiceDAO();

        //phan viet code
        JLabel headerLabel = new JLabel("TÌM KIẾM DỊCH VỤ");
        headerLabel.setBounds(470, 10, 1175, 40);
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
        panel1.setBounds(10, 70, 1120, 620);
        panel1.setOpaque(false);
        add(panel1);

        panel1.setLayout(null);

        //        Mã dịch vụ
        JLabel labelMaDichVu = new JLabel("Mã dịch vụ:");
        labelMaDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        labelMaDichVu.setBounds(60, 50, 150, 30);
        labelMaDichVu.setForeground(Color.WHITE);
        panel1.add(labelMaDichVu);

        textFieldMaDichVu = new JTextField();
        textFieldMaDichVu.setBounds(160, 50, 250, 30);
        textFieldMaDichVu.setColumns(10);
        panel1.add(textFieldMaDichVu);

//      Tên dịch vụ
        JLabel labelTenDichVu = new JLabel("Tên dịch vụ:");
        labelTenDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTenDichVu.setBounds(60, 100, 150, 30);
        labelTenDichVu.setForeground(Color.WHITE);
        panel1.add(labelTenDichVu);

        textFieldTenDichVu = new JTextField();
        textFieldTenDichVu.setBounds(160, 100, 250, 30);
        textFieldTenDichVu.setColumns(10);
        panel1.add(textFieldTenDichVu);

        //      Tên loại dịch vụ
        JLabel labelTenLoaiDichVu = new JLabel("Tên loại dịch vụ:");
        labelTenLoaiDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTenLoaiDichVu.setBounds(480, 50, 150, 30);
        labelTenLoaiDichVu.setForeground(Color.WHITE);
        panel1.add(labelTenLoaiDichVu);

        textFieldTenLoaiDichVu = new JTextField();
        textFieldTenLoaiDichVu.setBounds(610, 50, 250, 30);
        textFieldTenLoaiDichVu.setColumns(10);
        panel1.add(textFieldTenLoaiDichVu);

        //      Giá bán
        JLabel labelGiaBan = new JLabel("Giá bán:");
        labelGiaBan.setFont(new Font("Arial", Font.PLAIN, 14));
        labelGiaBan.setBounds(480, 100, 150, 30);
        labelGiaBan.setForeground(Color.WHITE);
        panel1.add(labelGiaBan);

        textFieldGiaBan = new JTextField();
        textFieldGiaBan.setBounds(610, 100, 250, 30);
        textFieldGiaBan.setColumns(10);
        panel1.add(textFieldGiaBan);

//        btn tìm kiếm
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnTimKiem);
        btnTimKiem.setBounds(930, 50, 100, 30);
        panel1.add(btnTimKiem);

        //        btn làm mới
        btnlamMoi = new JButton("Làm mới");
        btnlamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnlamMoi);
        btnlamMoi.setBounds(930, 100, 100, 30);
        panel1.add(btnlamMoi);

//      danh sách dịch vụ
        JPanel panelDSDV = new JPanel();
        panelDSDV.setLayout(null);
        panelDSDV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSDV.setBounds(5, 160, 1110, 450);
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
        scrollPaneDV.setBounds(10,20,1090,420);
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
        reSizeColumnTableService();
    }

    private void loadSearchingService(){
        java.util.List<Service> list = serviceDAO.getAllDichVu();
        int i=1;
        for (Service service : list){
            modelTableDV.addRow(new Object[]{i,service.getMaDichVu(),service.getTenDichVu(),service.getMaLoaiDichVu().getTenLoaiDichVu(),service.getDonViTinh(),service.getSoLuongTon(),service.getGiaBan()});
            i++;
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
        if (o.equals(btnTimKiem)){

            String txtMaDV = textFieldMaDichVu.getText();
            ArrayList<Service> services1 = serviceDAO.getDichVuTheoMa(txtMaDV);

            String txtTenDV = textFieldTenDichVu.getText();
            ArrayList<Service> services2 = serviceDAO.getServiceByName(txtTenDV);

            String txtTenLDV = textFieldTenLoaiDichVu.getText();
            ArrayList<TypeOfService> typeOfServices = typeOfServiceDAO.getTypeOfServiceByName(txtTenLDV);

            double giaBan = Double.parseDouble(textFieldGiaBan.getText());
            ArrayList<Service> services3 = serviceDAO.getDichVuTheoGia(giaBan);

            if (!textFieldMaDichVu.getText().trim().equals("")){
                modelTableDV.getDataVector().removeAllElements();
                int i=1;
                for (Service service : services1){
                    modelTableDV.addRow(new Object[]{i,service.getMaDichVu(),service.getTenDichVu(),service.getMaLoaiDichVu().getTenLoaiDichVu(),service.getDonViTinh(),service.getSoLuongTon(),service.getGiaBan()});
                    i++;
                }
            }else if (!textFieldTenDichVu.getText().trim().equals("")){
                modelTableDV.getDataVector().removeAllElements();
                int i=1;
                for (Service service : services2){
                    modelTableDV.addRow(new Object[]{i,service.getMaDichVu(),service.getTenDichVu(),service.getMaLoaiDichVu().getTenLoaiDichVu(),service.getDonViTinh(),service.getSoLuongTon(),service.getGiaBan()});
                    i++;
                }
            }else
//                if (!textFieldTenLoaiDichVu.getText().trim().equals("")){
//                modelTableDV.getDataVector().removeAllElements();
//                int i=1;
//                for (TypeOfService service : typeOfServices){
//                    modelTableDV.addRow(new Object[]{i,service.getMaDichVu(),service.getTenDichVu(),service.getMaLoaiDichVu().getTenLoaiDichVu(),service.getDonViTinh(),service.getSoLuongTon(),service.getGiaBan()});
//                    i++;
//                }
//            }else
                if (!textFieldGiaBan.getText().trim().equals("")){
                    modelTableDV.getDataVector().removeAllElements();
                    int i=1;
                    for (Service service : services3){
                        modelTableDV.addRow(new Object[]{i,service.getMaDichVu(),service.getTenDichVu(),service.getMaLoaiDichVu().getTenLoaiDichVu(),service.getDonViTinh(),service.getSoLuongTon(),service.getGiaBan()});
                        i++;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "KHÔNG TÌM THẤY!!!");
                }
        }else if(o.equals(btnlamMoi)){
            textFieldMaDichVu.setText("");
            textFieldTenDichVu.setText("");
            textFieldTenLoaiDichVu.setText("");
            textFieldGiaBan.setText("");

            modelTableDV.getDataVector().removeAllElements();
            loadSearchingService();
        }
    }

    private void reSizeColumnTableService() {
        TableColumnModel tcm = tableDV.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(20);
        tcm.getColumn(1).setPreferredWidth(40);
        tcm.getColumn(2).setPreferredWidth(130);
        tcm.getColumn(3).setPreferredWidth(100);

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
