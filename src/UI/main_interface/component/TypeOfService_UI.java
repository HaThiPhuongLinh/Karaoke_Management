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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TypeOfService_UI extends JPanel  implements ActionListener, MouseListener {
    private JTable tableDV;
    private JTextField textFieldTenLoaiDichVu,textFieldMaLoaiDichVu,textFieldThongBao;
    private JButton btnThem,btnXoa,btnSua,btnLamMoi;
    private DefaultTableModel modelTableDV;
    private JLabel backgroundLabel,timeLabel;
    private TypeOfServiceDAO typeOfServiceDAO;

    public TypeOfService_UI(){
        setLayout(null);
        setBounds(0, 0, 1175, 770);

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        typeOfServiceDAO = new TypeOfServiceDAO();

        //phan viet code
        JLabel headerLabel = new JLabel("QUẢN LÝ LOẠI DỊCH VỤ");
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
        JLabel labelMaDichVu = new JLabel("Mã loại dịch vụ:");
        labelMaDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        labelMaDichVu.setBounds(350, 50, 150, 30);
        labelMaDichVu.setForeground(Color.WHITE);
        panel1.add(labelMaDichVu);

        textFieldMaLoaiDichVu = new JTextField();
        textFieldMaLoaiDichVu.setBounds(460, 50, 270, 30);
        textFieldMaLoaiDichVu.setColumns(10);
        panel1.add(textFieldMaLoaiDichVu);

//      Tên dịch vụ
        JLabel labelTenDichVu = new JLabel("Tên loại dịch vụ:");
        labelTenDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTenDichVu.setBounds(350, 100, 150, 30);
        labelTenDichVu.setForeground(Color.WHITE);
        panel1.add(labelTenDichVu);

        textFieldTenLoaiDichVu = new JTextField();
        textFieldTenLoaiDichVu.setBounds(460, 100, 270, 30);
        textFieldTenLoaiDichVu.setColumns(10);
        panel1.add(textFieldTenLoaiDichVu);



        //Thông báo
        textFieldThongBao = new JTextField();
        textFieldThongBao.setBounds(20, 160, 270 , 30);
        textFieldThongBao.setColumns(10);
        panel1.add(textFieldThongBao);

//        btn thêm
        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThem);
        btnThem.setBounds(320, 160, 100, 30);
        panel1.add(btnThem);

        //        btn Xóa
        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnXoa);
        btnXoa.setBounds(440, 160, 100, 30);
        panel1.add(btnXoa);

        //        btn sửa
        btnSua = new JButton("Sửa");
        btnSua.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnSua);
        btnSua.setBounds(560, 160, 100, 30);
        panel1.add(btnSua);

        //        btn làm mới
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnLamMoi);
        btnLamMoi.setBounds(680, 160, 100, 30);
        panel1.add(btnLamMoi);

//      danh sách dịch vụ
        JPanel panelDSDV = new JPanel();
        panelDSDV.setLayout(null);
        panelDSDV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSDV.setBounds(5, 210, 1110, 405);
        panelDSDV.setOpaque(false);

        String[] colsDV = { "STT", "Mã loại dịch vụ", "Tên loại dịch vụ" };
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

        btnThem.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);

        tableDV.addMouseListener(this);


        loadTypeOfService();
    }

    private void loadTypeOfService(){
        java.util.List<TypeOfService> list = typeOfServiceDAO.getAllLoaiDichVu();
        int i=1;
        for (TypeOfService typeOfService : list){
            modelTableDV.addRow(new Object[]{i,typeOfService.getMaLoaiDichVu(),typeOfService.getTenLoaiDichVu()});
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
        if(o.equals(btnThem)){
            if (textFieldTenLoaiDichVu.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Bạn phải nhập thông tin đầy đủ thông tin loại dịch vụ");
            }else
            if (validData()){
                java.util.List<TypeOfService> list = typeOfServiceDAO.getAllLoaiDichVu();
                int i=1;
                for (TypeOfService service : list){
                    i++;
                }
                String MaLDV = "";
                if (i<10){
                    MaLDV = "LDV0"+i;
                }else{
                    MaLDV = "LDV"+i;
                }
                String maldv = textFieldMaLoaiDichVu.getText();
                String tenldv = textFieldTenLoaiDichVu.getText().trim();

                TypeOfService type = new TypeOfService(MaLDV,tenldv);
                if (typeOfServiceDAO.insert(type)) {
                    modelTableDV.getDataVector().removeAllElements();
                    loadTypeOfService();
                    textFieldThongBao.setText("Thêm loại dịch vụ thành công!!!");
                }
            }
        }else if(o.equals(btnXoa)){
            int row = tableDV.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chon dong can xoa");
            } else {

                String MaDV = textFieldMaLoaiDichVu.getText().trim();
                String tenDv = textFieldTenLoaiDichVu.getText().trim();

                TypeOfService type = new TypeOfService(MaDV,tenDv);

                int ans = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá dòng đã chọn ?", "Cảnh báo",
                        JOptionPane.YES_NO_OPTION);
                if (ans == JOptionPane.YES_OPTION) {

                    typeOfServiceDAO.delete(type.getMaLoaiDichVu());

                    textFieldMaLoaiDichVu.setText("");
                    textFieldTenLoaiDichVu.setText("");

                    textFieldThongBao.setText("");

                    modelTableDV.removeRow(row);
                    textFieldThongBao.setText("Xóa thành công!!!");
                    modelTableDV.getDataVector().removeAllElements();
                    loadTypeOfService();
                }
            }
        }else if (o.equals(btnSua)){
            int row = tableDV.getSelectedRow();
            if (row >= 0) {
                if (validData()) {

                    String MaDV = textFieldMaLoaiDichVu.getText().trim();
                    String tenDv = textFieldTenLoaiDichVu.getText().trim();

                    TypeOfService type = new TypeOfService(MaDV,tenDv);

                    if (typeOfServiceDAO.update(type)) {

                        tableDV.setValueAt(textFieldTenLoaiDichVu.getText(), row, 2);

                        textFieldThongBao.setText("Sửa thành công");
                    }
                }
            }else {
                JOptionPane.showMessageDialog(this, "Chọn dòng cần sửa");
            }
        }else if(o.equals(btnLamMoi)){
            textFieldMaLoaiDichVu.setText("");
            textFieldTenLoaiDichVu.setText("");
            textFieldThongBao.setText("");
            modelTableDV.getDataVector().removeAllElements();
            loadTypeOfService();
        }
    }

    public TypeOfService getDataInFormTypeOfService() {
        String maLDV = textFieldMaLoaiDichVu.getText().trim();
        String tenLDV = textFieldTenLoaiDichVu.getText().trim();
        TypeOfService ldv = new TypeOfService(maLDV,tenLDV);
        return ldv;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(tableDV)) {
            int row = tableDV.getSelectedRow();
            textFieldMaLoaiDichVu.setText(modelTableDV.getValueAt(row,1).toString());
            textFieldTenLoaiDichVu.setText(modelTableDV.getValueAt(row,2).toString());

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
    private boolean validData() {
//        String ma = txtMaKH.getText().trim();
        String ten = textFieldTenLoaiDichVu.getText().trim();
        if (!((ten.length()) > 0 && ten.matches("^[A-Za-zaAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ ]+"))) {
            JOptionPane.showMessageDialog(textFieldTenLoaiDichVu,"Error: Tên loại dịch vụ không được chứa số và kí tự đặc biệt");
            textFieldTenLoaiDichVu.selectAll();
            textFieldTenLoaiDichVu.requestFocus();
            return false;
        }

        return true;
    }
}
