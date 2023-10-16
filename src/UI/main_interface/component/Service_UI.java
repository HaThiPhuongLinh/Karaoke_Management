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
    private JSpinner textFieldGiaBan, textFieldSLT;
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

        textFieldSLT = new JSpinner();
        textFieldSLT.setBounds(880,50,170,30);
//        textFieldSLT.setColumns(10);
        panel1.add(textFieldSLT);

        //      Giá bán
        JLabel labelGiaBan = new JLabel("Giá bán:");
        labelGiaBan.setFont(new Font("Arial", Font.PLAIN, 14));
        labelGiaBan.setBounds(780, 100, 150 , 30);
        labelGiaBan.setForeground(Color.WHITE);
        panel1.add(labelGiaBan);

        textFieldGiaBan = new JSpinner();
        textFieldGiaBan.setBounds(880,100,170,30);
//        textFieldGiaBan.setColumns(10);
        panel1.add(textFieldGiaBan);

        //Thông báo
        textFieldThongBao = new JTextField();
        textFieldThongBao.setBounds(30, 160, 200 , 30);
        textFieldThongBao.setColumns(10);
        panel1.add(textFieldThongBao);

//        btn thêm
        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThem);
        btnThem.setBounds(280, 160, 100, 30);
        panel1.add(btnThem);

        //        btn Xóa
        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnXoa);
        btnXoa.setBounds(430, 160, 100, 30);
        panel1.add(btnXoa);

        //        btn sửa
        btnSua = new JButton("Sửa");
        btnSua.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnSua);
        btnSua.setBounds(580, 160, 100, 30);
        panel1.add(btnSua);

        //        btn làm mới
        btnlamMoi = new JButton("Làm mới");
        btnlamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnlamMoi);
        btnlamMoi.setBounds(730, 160, 100, 30);
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

    private void reSizeColumnTableService() {
        TableColumnModel tcm = tableDV.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(20);
        tcm.getColumn(1).setPreferredWidth(40);
        tcm.getColumn(2).setPreferredWidth(130);
        tcm.getColumn(3).setPreferredWidth(100);

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
    private boolean validData() {
//        String ma = txtMaKH.getText().trim();
        String ten = textFieldTenDichVu.getText().trim();
        String donViTinh = textFieldDonViTinh.getText().trim();
        String soLuongTon = textFieldSLT.getValue().toString();
        String giaBan = textFieldGiaBan.getValue().toString();

//        if (!((ma.length()) > 0 && ma.matches("[A-Z]\\d{3}"))) {
//            showMessage("Error: Mã nhân viên phải theo định dạng: [A-Z]\\\\d{3}", txtMaKH);
//            return false;
//        }
        if (!((ten.length()) > 0 && ten.matches("^[A-Za-zaAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ ]+"))) {
            JOptionPane.showMessageDialog(textFieldTenDichVu,"Error: Tên dịch vụ không được chứa số và kí tự đặc biệt");
            textFieldTenDichVu.selectAll();
            textFieldTenDichVu.requestFocus();
            return false;
        }
        if (!((donViTinh.length()) > 0 && donViTinh.matches("^[A-Za-zaAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ ]+"))) {
            JOptionPane.showMessageDialog(textFieldDonViTinh,"Error: Đơn vị tính không được chứa số và kí tự đặc biệt");
            textFieldDonViTinh.selectAll();
            textFieldDonViTinh.requestFocus();
            return false;
        }
        if (!((soLuongTon.length()) > 0 && soLuongTon.matches("^[1-9]\\d*"))) {
            JOptionPane.showMessageDialog(textFieldSLT,"Error: Số lượng tồn phải là số lớn hơn 0");
            return false;
        }
        if (!((giaBan.length()) > 0 && giaBan.matches("^[1-9]\\d*"))) {
            JOptionPane.showMessageDialog(textFieldGiaBan,"Error: Giá bán phải là số lớn hơn 0");
            return false;
        }
        return true;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThem)){
            if (textFieldTenDichVu.getText().equals("") || textFieldSLT.getValue().equals("") || textFieldDonViTinh.getText().equals("")|| textFieldGiaBan.getValue().equals("")|| comboBoxLDV.getSelectedIndex()==0){
                JOptionPane.showMessageDialog(this,"Bạn phải nhập thông tin đầy đủ thông tin dịch vụ");
            }else
            if (validData()){
                String tenLDV = comboBoxLDV.getSelectedItem().toString().trim();
                ArrayList<TypeOfService> typeOfServices = typeOfServiceDAO.getTypeOfServiceByName(tenLDV);
                String s="";
                for (TypeOfService service : typeOfServices){
                    s+=service.getMaLoaiDichVu();
                }

                java.util.List<Service> list = serviceDAO.getAllDichVu();
                int i=1;
                for (Service service : list){
                    i++;
                }
                String MaDV = "";
                if (i<10){
                    MaDV = "DV00"+i;
                }else{
                    MaDV = "DV0"+i;
                }
                String ma = textFieldMaDichVu.getText();
                String tenDv = textFieldTenDichVu.getText().trim();
                String donViTinh = textFieldDonViTinh.getText().trim();
                int soLuongTon = Integer.parseInt(textFieldSLT.getValue().toString());
                double giaBan = Double.parseDouble(textFieldGiaBan.getValue().toString());
//                String giaBan = df.format(textFieldGiaBan.getText());

                Service service = new Service(MaDV,tenDv,new TypeOfService(s),donViTinh,soLuongTon,giaBan);
                if (serviceDAO.insert(service)) {
                    modelTableDV.getDataVector().removeAllElements();
                    loadService();
                    textFieldThongBao.setText("Thêm dịch vụ thành công!!!");
                }
            }

        }else if (o.equals(btnlamMoi)){
            textFieldMaDichVu.setText("");
            textFieldTenDichVu.setText("");
            comboBoxLDV.setSelectedIndex(0);
            textFieldDonViTinh.setText("");
            textFieldSLT.setValue((int)0);
            textFieldGiaBan.setValue((double)0);
            textFieldThongBao.setText("");

            modelTableDV.getDataVector().removeAllElements();
            loadService();
        }else if(o.equals(btnXoa)){
            int row = tableDV.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chon dong can xoa");
            } else {
                String tenLDV = comboBoxLDV.getSelectedItem().toString().trim();
                ArrayList<TypeOfService> typeOfServices = typeOfServiceDAO.getTypeOfServiceByName(tenLDV);
                String s="";
                for (TypeOfService service : typeOfServices){
                    s+=service.getMaLoaiDichVu();
                }
                String MaDV = textFieldMaDichVu.getText().trim();
                String tenDv = textFieldTenDichVu.getText().trim();
                String donViTinh = textFieldDonViTinh.getText().trim();
                int soLuongTon = Integer.parseInt(textFieldSLT.getValue().toString());
                double giaBan = Double.parseDouble(textFieldGiaBan.getValue().toString());

                Service service = new Service(MaDV,tenDv,new TypeOfService(s),donViTinh,soLuongTon,giaBan);
                int ans = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá dòng đã chọn ?", "Cảnh báo",
                        JOptionPane.YES_NO_OPTION);
                if (ans == JOptionPane.YES_OPTION) {

                    serviceDAO.delete(service.getMaDichVu());

                    textFieldMaDichVu.setText("");
                    textFieldTenDichVu.setText("");
                    comboBoxLDV.setSelectedIndex(0);
                    textFieldDonViTinh.setText("");
                    textFieldSLT.setValue((int)0);
                    textFieldGiaBan.setValue((double)0);
                    textFieldThongBao.setText("");

                    modelTableDV.removeRow(row);

                    textFieldThongBao.setText("Xóa thành công!!!");
                    modelTableDV.getDataVector().removeAllElements();
                    loadService();
                }
            }
        }else if (o.equals(btnSua)){
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
                    int soLuongTon = Integer.parseInt(textFieldSLT.getValue().toString());
                    double giaBan = Double.parseDouble(textFieldGiaBan.getValue().toString());

                    Service service = new Service(MaDV, tenDv, new TypeOfService(s), donViTinh, soLuongTon, giaBan);


                    if (serviceDAO.update(service)) {
                        tableDV.setValueAt(textFieldTenDichVu.getText(), row, 2);
                        tableDV.setValueAt(comboBoxLDV.getSelectedItem(), row, 3);
                        tableDV.setValueAt(textFieldDonViTinh.getText(), row, 4);
                        tableDV.setValueAt(textFieldSLT.getValue(), row, 5);
                        tableDV.setValueAt(textFieldGiaBan.getValue(), row, 6);

                        textFieldThongBao.setText("Sửa thành công");
                    }
                }
            }else {
                JOptionPane.showMessageDialog(this, "Chọn dòng cần sửa");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(tableDV)) {
            int row = tableDV.getSelectedRow();
            textFieldMaDichVu.setText(tableDV.getValueAt(row,1).toString());
            textFieldTenDichVu.setText(tableDV.getValueAt(row,2).toString());
            comboBoxLDV.setSelectedItem(tableDV.getValueAt(row,3));
            textFieldDonViTinh.setText(tableDV.getValueAt(row,4).toString());
            String quantityStr = tableDV.getValueAt(row, 5).toString().trim().replace(",", "");
            textFieldSLT.setValue(Integer.parseInt(quantityStr));
            String priceStr = tableDV.getValueAt(row, 6).toString().trim().replace(",", "");
            textFieldGiaBan.setValue(Integer.parseInt(priceStr));

        }
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
