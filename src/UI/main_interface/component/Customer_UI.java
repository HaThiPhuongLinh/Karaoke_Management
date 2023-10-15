package UI.main_interface.component;

import javax.swing.*;

import ConnectDB.ConnectDB;
import DAOs.CustomerDAO;
import Entity.Customer;
import Entity.Service;
import UI.CustomUI.Custom;

import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class Customer_UI extends JPanel implements ActionListener, MouseListener {

    private  JTable tableKH;
    private  DefaultTableModel modelTableKH;
    private JLabel backgroundLabel,timeLabel,maKHLabel,tenKHLabel,gioitinhKHLabel,sdtKHLabel,ngaySinhLabel,cmndLabel;
    private JTextField txtMaKH,txtTenKH,txtSDTKH,txtCMNDKH, txtHienThiLoi;
    private JComboBox cboGioiTinh;
    private  JPanel timeNow,pnlCusList,pnlCusControl,pnlCusListRight;
    private DatePicker dpNgaySinh;
    private DefaultTableModel tableModel;
    private JButton btnThem ,btnXoa,btnSua,btnLamMoi,btnXemHet;
    private CustomerDAO CustomerDAO ;
    private Date date;


    public Customer_UI(){
        CustomerDAO = new CustomerDAO();
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        pnlCusList = new JPanel();
        pnlCusList.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Khách Hàng",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlCusList.setBounds(10, 70, 1120, 620);
        pnlCusList.setOpaque(false);
        add(pnlCusList);
        pnlCusList.setLayout(new BorderLayout(0, 0));


        pnlCusControl = new JPanel();
        pnlCusControl.setOpaque(false);
        pnlCusControl.setBackground(Color.WHITE);
        pnlCusList.add(pnlCusControl, BorderLayout.NORTH);
        pnlCusControl.setLayout(null);
        pnlCusControl.setPreferredSize(new Dimension(1100, 230));

        maKHLabel = new JLabel("Mã Khách Hàng: ");
        maKHLabel.setFont( new Font("Arial", Font.PLAIN, 14));
        maKHLabel.setBounds(30, 20, 120, 30);
        maKHLabel.setForeground(Color.WHITE);
        pnlCusControl.add(maKHLabel);
        txtMaKH =new JTextField();
        txtMaKH.setBounds(145,20,350,30);
//        txtMaKH.setEditable(false);
        pnlCusControl.add(txtMaKH);

        tenKHLabel = new JLabel("Tên Khách Hàng: ");
        tenKHLabel.setFont( new Font("Arial", Font.PLAIN, 14));
        tenKHLabel.setBounds(30, 70, 120, 30);
        tenKHLabel.setForeground(Color.WHITE);
        pnlCusControl.add(tenKHLabel);
        txtTenKH =new JTextField();
        txtTenKH.setBounds(145,70,350,30);
        pnlCusControl.add(txtTenKH);

        gioitinhKHLabel = new JLabel("Giới Tính: ");
        gioitinhKHLabel.setFont( new Font("Arial", Font.PLAIN, 14));
        gioitinhKHLabel.setBounds(865, 20, 70, 30);
        gioitinhKHLabel.setForeground(Color.WHITE);
        pnlCusControl.add(gioitinhKHLabel);

        cboGioiTinh = new JComboBox<String>();
        cboGioiTinh.addItem("Nam");
        cboGioiTinh.addItem("Nữ");
        cboGioiTinh.setBounds(965, 20, 105, 30);
        Custom.setCustomComboBox(cboGioiTinh);
        pnlCusControl.add(cboGioiTinh);

        sdtKHLabel = new JLabel("SDT: ");
        sdtKHLabel.setFont( new Font("Arial", Font.PLAIN, 14));
        sdtKHLabel.setBounds(550, 20, 50, 30);
        sdtKHLabel.setForeground(Color.WHITE);
        pnlCusControl.add(sdtKHLabel);

        txtSDTKH =new JTextField();
        txtSDTKH.setBounds(665,20,165,30);
        pnlCusControl.add(txtSDTKH);

        ngaySinhLabel = new JLabel("Ngày Sinh: ");
        ngaySinhLabel.setFont( new Font("Arial", Font.PLAIN, 14));
        ngaySinhLabel.setBounds(550, 70, 100, 30);
        ngaySinhLabel.setForeground(Color.WHITE);
        pnlCusControl.add(ngaySinhLabel);

        dpNgaySinh = new DatePicker(205);
        dpNgaySinh.setBounds(665, 70, 165, 30);
        pnlCusControl.add(dpNgaySinh);

        cmndLabel = new JLabel("CCCD: ");
        cmndLabel.setFont( new Font("Arial", Font.PLAIN, 14));
        cmndLabel.setBounds(30, 120, 60, 30);
        cmndLabel.setForeground(Color.WHITE);
        pnlCusControl.add(cmndLabel);

        txtCMNDKH =new JTextField();
        txtCMNDKH.setBounds(145,120,350,30);
        pnlCusControl.add(txtCMNDKH);

        txtHienThiLoi =new JTextField();
        txtHienThiLoi.setBounds(145,170,350,30);
        txtHienThiLoi.setOpaque(false);
        txtHienThiLoi.setEditable(false);
        pnlCusControl.add(txtHienThiLoi);

        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 15));
        btnThem.setBounds(550, 170, 100, 30);
        Custom.setCustomBtn(btnThem);
        pnlCusControl.add(btnThem);

        btnLamMoi = new JButton("Làm Mới");
        Custom.setCustomBtn(btnLamMoi);
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 15));
        btnLamMoi.setBounds(690, 170, 100, 30);
        pnlCusControl.add(btnLamMoi);

        btnSua = new JButton("Sửa");
        Custom.setCustomBtn(btnSua);
        btnSua.setFont(new Font("Arial", Font.BOLD, 15));
        btnSua.setBounds(830, 170, 100, 30);
        pnlCusControl.add(btnSua);

        btnXemHet = new JButton("Xem Hết");
        Custom.setCustomBtn(btnXemHet);
        btnXemHet.setFont(new Font("Arial", Font.BOLD, 15));
        btnXemHet.setBounds(970, 170, 100, 30);
        pnlCusControl.add(btnXemHet);


        JPanel panelDSKH = new JPanel();
        panelDSKH.setLayout(null);
        panelDSKH.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH KHÁCH HÀNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSKH.setBounds(30, 310, 1100, 320);
        panelDSKH.setOpaque(false);

        String[] colsKH = { "STT", "Mã KH", "Tên KH","SDT","CCCD","Giới Tính","Ngày Sinh" };
         modelTableKH = new DefaultTableModel(colsKH, 0) ;
        JScrollPane scrollPaneKH;

         tableKH = new JTable(modelTableKH);
        tableKH.setFont(new Font("Arial", Font.BOLD, 14));
        tableKH.setBackground(new Color(255, 255, 255, 0));
        tableKH.setForeground(new Color(255, 255, 255));
        tableKH.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableKH.getTableHeader().setForeground(Color.BLUE);

        Custom.getInstance().setCustomTable(tableKH);

        tableKH.addMouseListener(this);



        panelDSKH.add(scrollPaneKH = new JScrollPane(tableKH,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneKH.setBounds(10,20,1090,330);
        scrollPaneKH.setOpaque(false);
        scrollPaneKH.getViewport().setOpaque(false);
        scrollPaneKH.getViewport().setBackground(Color.WHITE);
        pnlCusList.add(panelDSKH);

        loadKH();
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXemHet.addActionListener(this);
        btnLamMoi.addActionListener(this);



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
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
        return sdf.format(date);
    }
    public void loadKH(){
        int i=1;
        String gt ="";
        for (Customer customer : CustomerDAO.getAllKhachHang()) {


            if(customer.isGioiTinh()==true){

             gt="Nam" ;

            }
            else{
                gt="Nữ";
            }
            Object[] rowData = { i,customer.getMaKhachHang(), customer.getTenKhachHang(), customer.getSoDienThoai(), customer.getCCCD(), gt,customer.getNgaySinh()};
            modelTableKH.addRow(rowData);
            i++;

        }
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnLamMoi)) {
            txtMaKH.setText("");
            txtTenKH.setText("");
            cboGioiTinh.setSelectedIndex(0);
            txtCMNDKH.setText("");
            dpNgaySinh.setValueToDay();
            txtMaKH.requestFocus();
            txtMaKH.setText("KH");
            txtMaKH.setEditable(true);
        } else if (o.equals(btnThem)) {


            String maKH = txtMaKH.getText().trim();
            String tenKH = txtTenKH.getText().trim();
            String sdt = txtSDTKH.getText().trim();
            String gioiTinh = cboGioiTinh.getSelectedItem().toString();
            boolean gt = true;
            String cccd = txtCMNDKH.getText().trim();
            if (gioiTinh == "Nam") {
                gt = true;
            } else {
                gt = false;
            }


            Date ngaysinh = null;

            try {
                ngaysinh = dpNgaySinh.getValueToDay();

            } catch (ParseException e3) {
                e3.printStackTrace();
            }

            Customer kh = new Customer(maKH, tenKH, sdt, cccd, gt, ngaysinh);

            try {
                if (CustomerDAO.insert(kh)) {
                    String date = formatDate(kh.getNgaySinh());
                    modelTableKH.addRow(new Object[]{kh.getMaKhachHang(), kh.getTenKhachHang(), kh.getSoDienThoai(),
                            kh.getCCCD(), kh.isGioiTinh(), date});
                    JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công");
                    modelTableKH.fireTableDataChanged();
                    modelTableKH.getDataVector().removeAllElements();
                    loadKH();

                }


            } catch (SQLException e2) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại");
            }

        } else if (o.equals(btnSua)) {

            String maKH = txtMaKH.getText().trim();
            String tenKH = txtTenKH.getText().trim();
            String sdt = txtSDTKH.getText().trim();
            String gioiTinh = cboGioiTinh.getSelectedItem().toString();
            boolean gt = true;
            String cccd = txtCMNDKH.getText().trim();
            if (gioiTinh == "Nam") {
                gt = true;
            } else {
                gt = false;
            }


            Date ngaysinh = null;

            try {
                ngaysinh = dpNgaySinh.getValueToDay();

            } catch (ParseException e3) {
                e3.printStackTrace();
            }
            Customer kh = new Customer(maKH, tenKH, sdt, cccd, gt, ngaysinh);

            int row = tableKH.getSelectedRow();
            boolean result = CustomerDAO.update(kh);
            if (result == true) {
                String date = formatDate(kh.getNgaySinh());
                modelTableKH.setValueAt(kh.getMaKhachHang(), row, 1);
                modelTableKH.setValueAt(kh.getTenKhachHang(), row, 2);
                modelTableKH.setValueAt(kh.getSoDienThoai(), row, 3);
                modelTableKH.setValueAt(kh.getCCCD(), row, 4);
                modelTableKH.setValueAt(kh.isGioiTinh(), row, 5);
                modelTableKH.setValueAt(date, row, 6);
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                modelTableKH.fireTableDataChanged();
                modelTableKH.getDataVector().removeAllElements();
                loadKH();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi: Cập nhật thất bại");
            }

        }else if(o.equals(btnLamMoi)){
            txtMaKH.setText("");
            txtTenKH.setText("");
            txtCMNDKH.setText("");
            cboGioiTinh.setSelectedItem(0);
            txtSDTKH.setText("");
            dpNgaySinh.setValueToDay();

        }else if(o.equals(btnXemHet)){
            modelTableKH.getDataVector().removeAllElements();
            loadKH();
        }

    }


        @Override
        public void mouseClicked (MouseEvent e){
            int row = tableKH.getSelectedRow();
            txtMaKH.setText(modelTableKH.getValueAt(row, 1).toString());
            txtTenKH.setText(modelTableKH.getValueAt(row, 2).toString());
            txtSDTKH.setText(modelTableKH.getValueAt(row, 3).toString());
            txtCMNDKH.setText(modelTableKH.getValueAt(row, 4).toString());
//        dpNgaySinh.set(modelTableKH.getValueAt(row, 6).toString());
//        String ngaysinh = tableKH.getValueAt(row,6).toString().trim();
//        java.util.Date ns = Calendar.getInstance().getTime();
//        try {
//            ns =new SimpleDateFormat("dd-MM-yyyy").parse(ngaysinh);
//        } catch (ParseException ex) {
//            ex.printStackTrace();
//            ns =Calendar.getInstance().getTime();
//        }
//     dpNgaySinh.set(ns);
            cboGioiTinh.setSelectedItem(modelTableKH.getValueAt(row, 5).toString());

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
