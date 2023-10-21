package UI.component;

import ConnectDB.ConnectDB;
import DAO.ServiceDAO;
import DAO.TypeOfServiceDAO;
import Entity.Service;
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

public class Service_UI extends JPanel implements ActionListener, MouseListener{
    private JTable tableDV;
    private JComboBox<String> comboBoxLDV;
    private JTextField textFieldDonViTinh;
    private JButton btnThem,btnXoa,btnSua,btnlamMoi;
    private JTextField textFieldMaDichVu,textFieldTenDichVu,textFieldThongBao;
    private JTextField textFieldGiaBan, textFieldSLT;
    private DefaultTableModel modelTableDV;
    private JLabel backgroundLabel,timeLabel;
    private ServiceDAO serviceDAO;
    private TypeOfServiceDAO typeOfServiceDAO;
    private DecimalFormat df = new DecimalFormat("#,###.##");

    public Service_UI(){
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
        JLabel headerLabel = new JLabel("QUẢN LÝ DỊCH VỤ");
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
        textFieldMaDichVu.setBounds(160, 50, 170, 30);
        textFieldMaDichVu.setColumns(10);
        panel1.add(textFieldMaDichVu);

//      Tên dịch vụ
        JLabel labelTenDichVu = new JLabel("Tên dịch vụ:");
        labelTenDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTenDichVu.setBounds(60, 100, 150, 30);
        labelTenDichVu.setForeground(Color.WHITE);
        panel1.add(labelTenDichVu);

        textFieldTenDichVu = new JTextField();
        textFieldTenDichVu.setBounds(160, 100, 170, 30);
        textFieldTenDichVu.setColumns(10);
        panel1.add(textFieldTenDichVu);
        //      Loại dịch vụ
        JLabel labelLDV = new JLabel("Loại dịch vụ:");
        labelLDV.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLDV.setBounds(420, 50, 150, 30);
        labelLDV.setForeground(Color.WHITE);
        panel1.add(labelLDV);

        comboBoxLDV = new JComboBox<String>();
        comboBoxLDV.addItem(" ");
        comboBoxLDV.setBounds(520, 50, 170, 30);
        Custom.setCustomComboBox(comboBoxLDV);
        panel1.add(comboBoxLDV);

        //      Đơn vị tính
        JLabel labelDonViTinh = new JLabel("Đơn vị tính:");
        labelDonViTinh.setFont(new Font("Arial", Font.PLAIN, 14));
        labelDonViTinh.setBounds(420, 100, 150, 30);
        labelDonViTinh.setForeground(Color.WHITE);
        panel1.add(labelDonViTinh);

        textFieldDonViTinh = new JTextField();
        textFieldDonViTinh.setBounds(520, 100, 170, 30);
        textFieldDonViTinh.setColumns(10);
        panel1.add(textFieldDonViTinh);

        //      Số lượng tồn
        JLabel labelSoLuongTon = new JLabel("Số lượng tồn:");
        labelSoLuongTon.setFont(new Font("Arial", Font.PLAIN, 14));
        labelSoLuongTon.setBounds(780, 50, 150, 30);
        labelSoLuongTon.setForeground(Color.WHITE);
        panel1.add(labelSoLuongTon);

        textFieldSLT = new JTextField();
        textFieldSLT.setBounds(880,50,170,30);
//        textFieldSLT.setColumns(10);
        panel1.add(textFieldSLT);

        //      Giá bán
        JLabel labelGiaBan = new JLabel("Giá bán:");
        labelGiaBan.setFont(new Font("Arial", Font.PLAIN, 14));
        labelGiaBan.setBounds(780, 100, 150 , 30);
        labelGiaBan.setForeground(Color.WHITE);
        panel1.add(labelGiaBan);

        textFieldGiaBan = new JTextField();
        textFieldGiaBan.setBounds(880,100,170,30);
//        textFieldGiaBan.setColumns(10);
        panel1.add(textFieldGiaBan);

        //Thông báo
        textFieldThongBao = new JTextField();
        textFieldThongBao.setFont(new Font("Arial",Font.BOLD,13));
        textFieldThongBao.setForeground(Color.RED);
        textFieldThongBao.setBounds(30, 160, 450 , 30);
        textFieldThongBao.setColumns(10);
        panel1.add(textFieldThongBao);

//        btn thêm
        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThem);
        btnThem.setBounds(500, 160, 100, 30);
        panel1.add(btnThem);

        //        btn Xóa
        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnXoa);
        btnXoa.setBounds(650, 160, 100, 30);
        panel1.add(btnXoa);

        //        btn sửa
        btnSua = new JButton("Sửa");
        btnSua.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnSua);
        btnSua.setBounds(800, 160, 100, 30);
        panel1.add(btnSua);

        //        btn làm mới
        btnlamMoi = new JButton("Làm mới");
        btnlamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnlamMoi);
        btnlamMoi.setBounds(950, 160, 100, 30);
        panel1.add(btnlamMoi);

//      danh sách dịch vụ
        JPanel panelDSDV = new JPanel();
        panelDSDV.setLayout(null);
        panelDSDV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSDV.setBounds(5, 210, 1110, 405);
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
        scrollPaneDV.setBounds(10,20,1090,375);
        scrollPaneDV.setOpaque(false);
        scrollPaneDV.getViewport().setOpaque(false);
        scrollPaneDV.getViewport().setBackground(Color.WHITE);
        panel1.add(panelDSDV);
        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);

        loadService();
        loadCboService();
        comboBoxLDV.addActionListener(this);
        tableDV.addMouseListener(this);

        btnThem.addActionListener(this);
        btnlamMoi.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);

        textFieldMaDichVu.setEditable(false);
        reSizeColumnTableService();

    }
    private void reSizeColumnTableService(){
        TableColumnModel tblModel = tableDV.getColumnModel();

        tblModel.getColumn(0).setPreferredWidth(20);
        tblModel.getColumn(1).setPreferredWidth(40);
        tblModel.getColumn(2).setPreferredWidth(130);
        tblModel.getColumn(3).setPreferredWidth(100);
    }

    private void loadService(){
        java.util.List<Service> list = serviceDAO.getAllDichVu();
        int i=1;
        for (Service service : list){
            modelTableDV.addRow(new Object[]{i,service.getMaDichVu(),service.getTenDichVu(),service.getMaLoaiDichVu().getTenLoaiDichVu(),service.getDonViTinh(),service.getSoLuongTon(),df.format(service.getGiaBan())});
            i++;
        }
    }
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        timeLabel.setText(time);
    }

    private void loadCboService() {
        java.util.List<TypeOfService> dataList = typeOfServiceDAO.getAllLoaiDichVu();
        for (TypeOfService serviceType : dataList) {
            comboBoxLDV.addItem(serviceType.getTenLoaiDichVu());
        }
    }

    private void showMessage(JTextField txt, String message) {
        txt.requestFocus();
        textFieldThongBao.setText(message);
    }

    private boolean validData() {
        String ten = textFieldTenDichVu.getText().trim();
        String donViTinh = textFieldDonViTinh.getText().trim();
        String soLuongTon = textFieldSLT.getText().toString();
        String giaBan = textFieldGiaBan.getText().toString();

        if (!((ten.length() > 0) && ten.matches("^[A-Za-z0-9À-ỹ ]+"))) {
            showMessage(textFieldTenDichVu,"Tên dịch vụ không được chứa kí tự đặc biệt");
            return false;
        }
        if (!((donViTinh.length()) > 0 && donViTinh.matches("^[A-Za-zÀ-ỹ ]+"))) {
            showMessage(textFieldDonViTinh,"ĐVT không được chứa số và kí tự đặc biệt");
            return false;
        }
        if (!((soLuongTon.length()) > 0 && soLuongTon.matches("^[1-9]\\d*"))) {
            showMessage(textFieldSLT,"Số lượng tồn phải lớn hơn 0");
            return false;
        }
        if (!((giaBan.length()) > 0 && giaBan.matches("^[1-9]\\d*"))) {
            showMessage(textFieldGiaBan,"Giá bán phải lớn hơn 0");
            return false;
        }
        return true;
    }

    public String laymaLDV(){
        String tenLDV = comboBoxLDV.getSelectedItem().toString().trim();
        String maLDV = typeOfServiceDAO.getServiceCodeByName(tenLDV);
        return maLDV;
    }

    public String laymaDV(){
        String MaDV = serviceDAO.generateNextServiceId();
        return MaDV;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThem)) {
            if (textFieldTenDichVu.getText().equals("") || textFieldSLT.getText().equals("") || textFieldDonViTinh.getText().equals("") || textFieldGiaBan.getText().equals("") || comboBoxLDV.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin dịch vụ");
            } else {
                if (validData()) {
                    String MaDV = laymaDV();
                    String maLDV = laymaLDV();
                    String tenDv = textFieldTenDichVu.getText().trim();
                    String donViTinh = textFieldDonViTinh.getText().trim();
                    int soLuongTon = Integer.parseInt(textFieldSLT.getText().toString());
                    double giaBan = Double.parseDouble(textFieldGiaBan.getText().toString());

                    Service service = new Service(MaDV, tenDv, new TypeOfService(maLDV), donViTinh, soLuongTon, giaBan);
                    if (serviceDAO.insert(service)) {
                        modelTableDV.getDataVector().removeAllElements();
                        loadService();
                        lamMoi();
                        JOptionPane.showMessageDialog(this,"Thêm dịch vụ thành công");
                    }
                }

            }
        } else if (o.equals(btnlamMoi)) {
            lamMoi();
            modelTableDV.getDataVector().removeAllElements();
            loadService();
        } else if (o.equals(btnXoa)) {
            int row = tableDV.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng cần xóa");
            } else {
                String maLDV = laymaLDV();
                String MaDV = textFieldMaDichVu.getText().trim();
                String tenDv = textFieldTenDichVu.getText().trim();
                String donViTinh = textFieldDonViTinh.getText().trim();
                int soLuongTon = Integer.parseInt(textFieldSLT.getText().toString());
                double giaBan = Double.parseDouble(textFieldGiaBan.getText().toString());

                Service service = new Service(MaDV, tenDv, new TypeOfService(maLDV), donViTinh, soLuongTon, giaBan);
                int ans = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Cảnh báo",
                        JOptionPane.YES_NO_OPTION);
                if (ans == JOptionPane.YES_OPTION) {
                    serviceDAO.delete(service.getMaDichVu());
                    lamMoi();
                    modelTableDV.removeRow(row);
                    modelTableDV.getDataVector().removeAllElements();
                    loadService();
                    JOptionPane.showMessageDialog(this,"Xóa thành công");
                }
            }
        } else if (o.equals(btnSua)) {
            int row = tableDV.getSelectedRow();
            if (row > 0) {
                if (validData()) {
                    String tenLDV = comboBoxLDV.getSelectedItem().toString().trim();
                    ArrayList<TypeOfService> typeOfServices = typeOfServiceDAO.getTypeOfServiceByName(tenLDV);
                    String s = "";
                    for (TypeOfService service : typeOfServices) {
                        s += service.getMaLoaiDichVu();
                    }

                    String MaDV = textFieldMaDichVu.getText().trim();
                    String tenDv = textFieldTenDichVu.getText().trim();
                    String donViTinh = textFieldDonViTinh.getText().trim();
                    int soLuongTon = Integer.parseInt(textFieldSLT.getText().toString());
                    double giaBan = Double.parseDouble(textFieldGiaBan.getText().toString());

                    Service service = new Service(MaDV, tenDv, new TypeOfService(s), donViTinh, soLuongTon, giaBan);

                    if (serviceDAO.update(service)) {
                        String quantityStr = df.format(service.getSoLuongTon());
                        String priceStr = df.format(service.getGiaBan());
                        tableDV.setValueAt(textFieldTenDichVu.getText(), row, 2);
                        tableDV.setValueAt(comboBoxLDV.getSelectedItem(), row, 3);
                        tableDV.setValueAt(textFieldDonViTinh.getText(), row, 4);
                        tableDV.setValueAt(quantityStr, row, 5);
                        tableDV.setValueAt(priceStr, row, 6);
                        lamMoi();
                        JOptionPane.showMessageDialog(this,"Sửa thành công");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng cần sửa");
            }
        }
    }

    private void lamMoi(){
        textFieldMaDichVu.setText("");
        textFieldTenDichVu.setText("");
        comboBoxLDV.setSelectedIndex(0);
        textFieldDonViTinh.setText("");
        textFieldSLT.setText("");
        textFieldGiaBan.setText("");
        textFieldThongBao.setText("");

    }
    @Override
    public void mouseClicked (MouseEvent e){
        Object o = e.getSource();
        if (o.equals(tableDV)) {
            int row = tableDV.getSelectedRow();
            textFieldMaDichVu.setText(tableDV.getValueAt(row, 1).toString());
            textFieldTenDichVu.setText(tableDV.getValueAt(row, 2).toString());
            comboBoxLDV.setSelectedItem(tableDV.getValueAt(row, 3));
            textFieldDonViTinh.setText(tableDV.getValueAt(row, 4).toString());
            String quantityStr = tableDV.getValueAt(row, 5).toString().trim().replace(",", "");
            textFieldSLT.setText(String.valueOf(Integer.parseInt(quantityStr)));
            String priceStr = tableDV.getValueAt(row, 6).toString().trim().replace(",", "");
            textFieldGiaBan.setText(String.valueOf(Integer.parseInt(priceStr)));

        }
    }

    @Override
    public void mousePressed (MouseEvent e){

    }

    @Override
    public void mouseReleased (MouseEvent e){

    }

    @Override
    public void mouseEntered (MouseEvent e){

    }

    @Override
    public void mouseExited (MouseEvent e){

    }

}
