package UI.main_interface.component;

import javax.swing.*;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
public class Customer extends JPanel {

    private JLabel backgroundLabel,timeLabel,maKHLabel,tenKHLabel,gioitinhKHLabel,sdtKHLabel,ngaySinhLabel,cmndLabel,search1Label,search2Label,search3Label;
    private JTextField txtMaKH,txtTenKH,txtSDTKH,txtCMNDKH,txtSearch1,txtSearch2,txtSearch3;
    private JComboBox cboGioiTinh;
    private  JPanel timeNow,pnlCusList,pnlCusControl,pnlCusListRight;
    private DatePicker dpNgaySinh;
    private ImageIcon iconThem = new ImageIcon(new ImageIcon("src/images/add-icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon iconXoa = new ImageIcon(new ImageIcon("src/images/delete-icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon iconSua = new ImageIcon(new ImageIcon("src/images/update-icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    Icon iconLamMoi = new ImageIcon(new ImageIcon("src/images/refesh-icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon iconTim = new ImageIcon(new ImageIcon("src/images/search-icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon iconXemTatCa = new ImageIcon(new ImageIcon("src/images/see_all-icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private DefaultTableModel tableModel;
    private JButton btnThem ,btnXoa,btnSua,btnTim1,btnTim2,btnTim3,btnLamMoi,btnXemHet;

    public Customer(){
        setLayout(null);
        setBounds(0, 0, 1175, 770);

        //phan viet code
        JLabel headerLabel = new JLabel("QUẢN LÝ KHÁCH HÀNG");
        headerLabel.setBounds(470, 10, 1175, 40);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        Component add =add(headerLabel);

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
        pnlCusControl.setPreferredSize(new Dimension(1100, 250));

        maKHLabel = new JLabel("Mã Khách Hàng: ");
        maKHLabel.setFont( new Font("Arial", Font.PLAIN, 14));
        maKHLabel.setBounds(30, 20, 120, 30);
        maKHLabel.setForeground(Color.WHITE);
        pnlCusControl.add(maKHLabel);
        txtMaKH =new JTextField();
        txtMaKH.setBounds(145,20,350,30);
        txtMaKH.setEditable(false);
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
        gioitinhKHLabel.setBounds(315, 120, 70, 30);
        gioitinhKHLabel.setForeground(Color.WHITE);
        pnlCusControl.add(gioitinhKHLabel);

        cboGioiTinh = new JComboBox<String>();
        cboGioiTinh.addItem("Nam");
        cboGioiTinh.addItem("Nữ");
        cboGioiTinh.setBounds(390, 120, 105, 30);
        Custom.setCustomComboBox(cboGioiTinh);
        pnlCusControl.add(cboGioiTinh);

        sdtKHLabel = new JLabel("SDT: ");
        sdtKHLabel.setFont( new Font("Arial", Font.PLAIN, 14));
        sdtKHLabel.setBounds(30, 120, 50, 30);
        sdtKHLabel.setForeground(Color.WHITE);
        pnlCusControl.add(sdtKHLabel);

        txtSDTKH =new JTextField();
        txtSDTKH.setBounds(145,120,165,30);
        pnlCusControl.add(txtSDTKH);

        ngaySinhLabel = new JLabel("Ngày Sinh: ");
        ngaySinhLabel.setFont( new Font("Arial", Font.PLAIN, 14));
        ngaySinhLabel.setBounds(30, 170, 100, 30);
        ngaySinhLabel.setForeground(Color.WHITE);
        pnlCusControl.add(ngaySinhLabel);

        dpNgaySinh = new DatePicker(205);
        dpNgaySinh.setBounds(145, 170, 165, 30);
        pnlCusControl.add(dpNgaySinh);

        cmndLabel = new JLabel("CMND: ");
        cmndLabel.setFont( new Font("Arial", Font.PLAIN, 14));
        cmndLabel.setBounds(30, 220, 60, 30);
        cmndLabel.setForeground(Color.WHITE);
        pnlCusControl.add(cmndLabel);

        txtCMNDKH =new JTextField();
        txtCMNDKH.setBounds(145,220,350,30);
        pnlCusControl.add(txtCMNDKH);

        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.PLAIN, 15));
        btnThem.setBounds(550, 20, 100, 30);
        btnThem.setIcon(iconThem);
        Custom.setCustomBtn(btnThem);
        pnlCusControl.add(btnThem);

        btnLamMoi = new JButton("Làm Mới");
        Custom.setCustomBtn(btnLamMoi);
        btnLamMoi.setFont(new Font("Arial", Font.PLAIN, 15));
        btnLamMoi.setBounds(690, 20, 100, 30);
        btnLamMoi.setIcon(iconLamMoi);
        pnlCusControl.add(btnLamMoi);

        btnSua = new JButton("Sửa");
        Custom.setCustomBtn(btnSua);
        btnSua.setFont(new Font("Arial", Font.PLAIN, 15));
        btnSua.setBounds(830, 20, 100, 30);
        btnSua.setIcon(iconSua);
        pnlCusControl.add(btnSua);

        btnXemHet = new JButton("Xem Hết");
        Custom.setCustomBtn(btnXemHet);
        btnXemHet.setFont(new Font("Arial", Font.PLAIN, 15));
        btnXemHet.setBounds(970, 20, 100, 30);
        btnXemHet.setIcon(iconXemTatCa);
        pnlCusControl.add(btnXemHet);

        search1Label = new JLabel("Tìm Theo Tên: ");

        search1Label.setFont( new Font("Arial", Font.PLAIN, 14));
        search1Label.setBounds(550, 85, 120, 30);
        search1Label.setForeground(Color.WHITE);
        pnlCusControl.add(search1Label);

        txtSearch1 =new JTextField();
        txtSearch1.setBounds(685,85,280,30);
        pnlCusControl.add(txtSearch1);

        btnTim1 = new JButton("Tìm");
        btnTim1.setBounds(970, 85, 100, 30);
        btnTim1.setIcon(iconTim);
        Custom.setCustomBtn(btnTim1);
        btnTim1.setFont( new Font("Arial", Font.PLAIN, 14));
        pnlCusControl.add(btnTim1);

        //-
        search2Label = new JLabel("Tìm Theo SDT: ");
        search2Label.setFont( new Font("Arial", Font.PLAIN, 14));
        search2Label.setBounds(550, 155, 120, 30);
        search2Label.setForeground(Color.WHITE);
        pnlCusControl.add(search2Label);

        txtSearch2 =new JTextField();
        txtSearch2.setBounds(685,155,280,30);
        pnlCusControl.add(txtSearch2);

        btnTim2 = new JButton("Tìm");
        Custom.setCustomBtn(btnTim2);
        btnTim2.setBounds(970, 155, 100, 30);
        btnTim2.setIcon(iconTim);
        btnTim2.setFont( new Font("Arial", Font.PLAIN, 14));
        pnlCusControl.add(btnTim2);
        //=-
        search3Label = new JLabel("Tìm Theo CMND: ");

        search3Label.setFont( new Font("Arial", Font.PLAIN, 14));
        search3Label.setBounds(550, 220, 120, 30);
        search3Label.setForeground(Color.WHITE);
        pnlCusControl.add(search3Label);

        txtSearch3 =new JTextField();

        txtSearch3.setBounds(685,220,280,30);
        pnlCusControl.add(txtSearch3);

        btnTim3 = new JButton("Tìm");
        Custom.setCustomBtn(btnTim3);
        btnTim3.setBounds(970, 220, 100, 30);
        btnTim3.setFont( new Font("Arial", Font.PLAIN, 14));
        btnTim3.setIcon(iconTim);
        pnlCusControl.add(btnTim3);
        //table KH
        JPanel panelDSKH = new JPanel();
        panelDSKH.setLayout(null);
        panelDSKH.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH KHÁCH HÀNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSKH.setBounds(30, 310, 1100, 320);
        panelDSKH.setOpaque(false);

        String[] colsKH = { "STT", "Mã KH", "Tên KH","SDT","CCCD","Giới Tính","Ngày Sinh" };
        DefaultTableModel modelTableKH = new DefaultTableModel(colsKH, 0) ;
        JScrollPane scrollPaneKH;

        JTable tableKH = new JTable(modelTableKH);
        tableKH.setFont(new Font("Arial", Font.BOLD, 14));
        tableKH.setBackground(new Color(255, 255, 255, 0));
        tableKH.setForeground(new Color(255, 255, 255));
        tableKH.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableKH.getTableHeader().setForeground(Color.BLUE);
//        tableLDV.getTableHeader().setBackground(new Color(255, 255, 255));

        panelDSKH.add(scrollPaneKH = new JScrollPane(tableKH,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneKH.setBounds(10,20,1090,330);
        scrollPaneKH.setOpaque(false);
        scrollPaneKH.getViewport().setOpaque(false);
        scrollPaneKH.getViewport().setBackground(Color.WHITE);
        pnlCusList.add(panelDSKH);





        //
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
