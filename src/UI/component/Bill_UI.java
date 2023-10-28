package UI.component;

import ConnectDB.ConnectDB;
import DAO.BillDAO;
import DAO.ReservationFormDAO;
import DAO.DetailOfServiceDAO;
import DAO.RoomDAO;
import Entity.Bill;
import Entity.DetailsOfService;
import UI.CustomUI.Custom;
import Entity.ReservationForm;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bill_UI extends JPanel implements ActionListener, MouseListener {
    private  JTable tableHD;
    private  JTable tblPDP;
    private  DefaultTableModel modelTableHD;
    private  JTextField txtTK;
    private  JButton btnLap;
    private  JButton btnTim;
    private  DefaultTableModel modelTablePDP;
    private JLabel backgroundLabel;
    private ReservationFormDAO reservationFormDAO;
    private RoomDAO roomDAO;
    private DecimalFormat df = new DecimalFormat("#,###.##");
    private Bill bill;
    private BillDAO billDAO;

    private  DetailOfServiceDAO ctdv_dao;
    private ArrayList<DetailsOfService> dsCTDV;

    public Bill_UI(){
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        //phan viet code
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        billDAO = new BillDAO();
        reservationFormDAO = new ReservationFormDAO();
        roomDAO =new RoomDAO();
        ctdv_dao = new DetailOfServiceDAO();
        JLabel labelHeader = new JLabel("LẬP HOÁ ĐƠN");
        labelHeader.setBounds(520, 10, 1175, 40);
        labelHeader.setFont(new Font("Arial", Font.BOLD, 25));
        labelHeader.setForeground(Color.WHITE);
        add(labelHeader);

        JPanel panelFull = new JPanel();
        panelFull.setLayout(null);
        panelFull.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
                TitledBorder.LEADING, TitledBorder.TOP));
        panelFull.setBounds(10,50,1245,690);
        panelFull.setOpaque(false);
        add(panelFull);

//        Từ ngày
//        JLabel labelKM = new JLabel("Mã Khuyến Mãi:");
//        labelKM.setFont(new Font("Arial", Font.PLAIN, 14));
//        labelKM.setBounds(20, 60, 150, 30);
//        labelKM.setForeground(Color.WHITE);
//        panelFull.add(labelKM);
//
//        JTextField textFieldKM = new JTextField();
//        textFieldKM.setBounds(130, 60, 207, 30);
//        textFieldKM.setColumns(6);
//        panelFull.add(textFieldKM);

        //        btn tìm kiếm
        JLabel labelTK = new JLabel("Tìm Theo Mã Phòng:");
        labelTK.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTK.setBounds(710, 20, 150, 30);
        labelTK.setForeground(Color.WHITE);
        panelFull.add(labelTK);

        txtTK = new JTextField();
        txtTK.setBounds(870, 20, 150, 30);
        txtTK.setColumns(6);
        panelFull.add(txtTK);
        btnTim = new JButton("Tìm");
        btnTim.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnTim);
        btnTim.setBounds(1040, 20, 100, 30);
        panelFull.add(btnTim);

        btnLap = new JButton("Lập Hóa Đơn");
        btnLap.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnLap);
        btnLap.setBounds(100, 230, 200, 50);
        panelFull.add(btnLap);

        JPanel panelDSHD = new JPanel();
        panelDSHD.setLayout(null);
        panelDSHD.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Danh Sách Dịch Vụ Được Sử Dụng",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSHD.setBounds(30, 320, 1185, 370);
        panelDSHD.setOpaque(false);

        String[] colsHD = { "STT", "Mã Dịch Vụ", "Tên Dịch Vụ","Số Lượng Đặt","Giá Dịch Vụ","Tổng Tiền" };
         modelTableHD = new DefaultTableModel(colsHD, 0) ;
        JScrollPane scrollPaneHD;

         tableHD = new JTable(modelTableHD);
        tableHD.setFont(new Font("Arial", Font.BOLD, 14));
        tableHD.setBackground(new Color(255, 255, 255, 0));
        tableHD.setForeground(new Color(255, 255, 255));
        tableHD.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableHD.getTableHeader().setForeground(Color.BLUE);
//        tableLDV.getTableHeader().setBackground(new Color(255, 255, 255));
        Custom.getInstance().setCustomTable(tableHD);

        panelDSHD.add(scrollPaneHD = new JScrollPane(tableHD,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneHD.setBounds(10,20,1165,340);
        scrollPaneHD.setOpaque(false);
        scrollPaneHD.getViewport().setOpaque(false);
        scrollPaneHD.getViewport().setBackground(Color.WHITE);
        panelFull.add(panelDSHD);

        //      danh sách Chi tiết hóa đơn
        JPanel panelCTHD = new JPanel();
        panelCTHD.setLayout(null);
        panelCTHD.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Danh Sách Các Phòng Đang Được Dùng",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelCTHD.setBounds(420, 55, 789, 250);
        panelCTHD.setOpaque(false);

        String[] colsCTHD = { "STT","Mã Phòng", "Loại Phòng","Tên KH","Giờ Vào","Giá Phòng" };
        modelTablePDP = new DefaultTableModel(colsCTHD, 0) ;
        JScrollPane scrollPaneCTHD;

       tblPDP = new JTable(modelTablePDP);
        tblPDP.setFont(new Font("Arial", Font.BOLD, 14));
        tblPDP.setBackground(new Color(255, 255, 255, 0));
        tblPDP.setForeground(new Color(255, 255, 255));
        tblPDP.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblPDP.getTableHeader().setForeground(Color.BLUE);
        Custom.getInstance().setCustomTable(tblPDP);

        panelCTHD.add(scrollPaneCTHD = new JScrollPane(tblPDP,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneCTHD.setBounds(10,20,771,220);
        scrollPaneCTHD.setOpaque(false);
        scrollPaneCTHD.getViewport().setOpaque(false);
        scrollPaneCTHD.getViewport().setBackground(Color.WHITE);
        panelFull.add(panelCTHD);
        btnTim.addActionListener(this);
        loadHD();
        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);
        tblPDP.addMouseListener(this);
    }
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }
    public void loadCTDV(){
        modelTableHD.setRowCount(0);
        int i = 1;
        for (DetailsOfService ctdv : dsCTDV) {
            Double tongtien = ctdv.getGiaBan()*ctdv.getSoLuong();
            modelTableHD.addRow(new Object[] { i,ctdv.getMaDichVu().getMaDichVu(), ctdv.getMaDichVu().getTenDichVu(), ctdv.getSoLuong(),
                    df.format(ctdv.getMaDichVu().getGiaBan()), df.format(tongtien) });
            i++;
        }
    }

    public void loadHD() {
        int i = 1;

        for (Bill bill :billDAO.getAllBill()) {
            String date = formatDate(bill.getNgayGioDat());
if(bill.getTinhTrangHD()==0) {
    Object[] rowData = {i, bill.getMaPhong().getMaPhong(), bill.getMaPhong().getLoaiPhong().getTenLoaiPhong(),bill.getMaKH().getTenKhachHang(), date, df.format(bill.getMaPhong().getGiaPhong())};
    modelTablePDP.addRow(rowData);
}
            i++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
                          
//        Object o = e.getSource();
//        if(o.equals(btnTim)) {
//
//            if (txtTK.getText().trim().equals("")) {
//                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin tìm kiếm");
//                modelTablePDP.getDataVector().removeAllElements();
//                loadHD();
//            }
//            else if (!txtTK.getText().trim().equals("")) {
//                modelTablePDP.getDataVector().removeAllElements();
//
//                String txtMaP = txtTK.getText();
//                ReservationForm rsvf = reservationFormDAO.getFormByRoomID(txtMaP);
//
//
//                    String date = formatDate(rsvf.getThoiGianDat());
//
//                    Object[] rowData = {1, rsvf.getMaPhong().getMaPhong(), rsvf.getMaPhong().getLoaiPhong().getTenLoaiPhong(), date, df.format(rsvf.getMaPhong().getGiaPhong())};
//                    modelTablePDP.addRow(rowData);
//
//
//            }
//            else {
//                    JOptionPane.showMessageDialog(this, "Không có phòng đang được sử dụng khớp với thông tin tìm kiếm");
//                    txtTK.selectAll();
//                    txtTK.requestFocus();
//                }
//
//        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if(o.equals(tblPDP)){
            int row = tblPDP.getSelectedRow();
            String maPhong = modelTablePDP.getValueAt(row,1).toString();
            dsCTDV = ctdv_dao.getDetailsOfServiceListByRoomId(maPhong);
            loadCTDV();

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
