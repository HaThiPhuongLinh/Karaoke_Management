package UI.component;

import ConnectDB.ConnectDB;
import Entity.Room;
import DAO.BillDAO;
import DAO.ReservationFormDAO;
import DAO.DetailOfServiceDAO;
import DAO.RoomDAO;
import Entity.Bill;
import Entity.DetailsOfService;
import UI.CustomUI.Custom;
import Entity.ReservationForm;
import UI.component.Dialog.DialogBill;

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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

public class Bill_UI extends JPanel implements ActionListener, MouseListener {
    private  JTable tableHD;
    private  JTable tblPDP;
    private  DefaultTableModel modelTableHD;
    private  JTextField txtTK, txtKH;
    private  JButton btnLap;
    private  JButton btnTim, btnRefresh;
    private  DefaultTableModel modelTablePDP;
    private JLabel backgroundLabel;
    private ReservationFormDAO reservationFormDAO;
    private RoomDAO roomDAO;
    private DecimalFormat df = new DecimalFormat("#,###.##");
    private Bill bill;
    private BillDAO billDAO;

    private  DetailOfServiceDAO ctdv_dao;
    private ArrayList<DetailsOfService> dsCTDV;
    private Bill rsvf;
    private static KaraokeBooking_UI main;
    private Bill rsvf2;

    public Bill_UI(){
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        //phan viet code
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.main = main;
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

        //        btn tìm kiếm
        JLabel labelTK = new JLabel("Tìm Theo Mã Phòng:");
        labelTK.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTK.setBounds(310, 20, 150, 30);
        labelTK.setForeground(Color.WHITE);
        panelFull.add(labelTK);

        txtTK = new JTextField();
        txtTK.setBounds(500, 20, 220, 30);
        txtTK.setColumns(6);
        panelFull.add(txtTK);

        JLabel labelKH = new JLabel("Tìm Theo Tên Khách Hàng:");
        labelKH.setFont(new Font("Arial", Font.PLAIN, 14));
        labelKH.setBounds(310, 70, 180, 30);
        labelKH.setForeground(Color.WHITE);
        panelFull.add(labelKH);

        txtKH = new JTextField();
        txtKH.setBounds(500, 70, 220, 30);
        txtKH.setColumns(6);
        panelFull.add(txtKH);

        btnTim = new JButton("Tìm");
        btnTim.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnTim);
        btnTim.setBounds(750, 70, 100, 30);
        panelFull.add(btnTim);

        btnRefresh = new JButton("Làm mới");
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnRefresh);
        btnRefresh.setBounds(880, 70, 100, 30);
        panelFull.add(btnRefresh);

        btnLap = new JButton("Lập Hóa Đơn");
        btnLap.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnLap);
        btnLap.setBounds(1070, 331, 150, 32);
        panelFull.add(btnLap);

        JPanel panelDSHD = new JPanel();
        panelDSHD.setLayout(null);
        panelDSHD.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Danh Sách Dịch Vụ Được Sử Dụng",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSHD.setBounds(30, 380, 1185, 300);
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
        scrollPaneHD.setBounds(10,20,1165,260);
        scrollPaneHD.setOpaque(false);
        scrollPaneHD.getViewport().setOpaque(false);
        scrollPaneHD.getViewport().setBackground(Color.WHITE);
        panelFull.add(panelDSHD);

        //      danh sách Chi tiết hóa đơn
        JPanel panelCTHD = new JPanel();
        panelCTHD.setLayout(null);
        panelCTHD.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Danh Sách Các Phòng Đang Sử Dụng",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelCTHD.setBounds(30, 115, 1020, 250);
        panelCTHD.setOpaque(false);

        String[] colsCTHD = { "STT","Mã Phòng", "Loại Phòng","Tên Khách Hàng","Giờ Vào","Giá Phòng" };
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
        scrollPaneCTHD.setBounds(10,20,1000,220);
        scrollPaneCTHD.setOpaque(false);
        scrollPaneCTHD.getViewport().setOpaque(false);
        scrollPaneCTHD.getViewport().setBackground(Color.WHITE);
        panelFull.add(panelCTHD);
        btnTim.addActionListener(this);
        loadHD();
        reSizeColumnTable();
        reSizeColumnTable2();
        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);
        tblPDP.addMouseListener(this);
        btnLap.addActionListener(this);
    }

    private void reSizeColumnTable() {
        TableColumnModel tcm = tblPDP.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(30);
        tcm.getColumn(1).setPreferredWidth(30);
        tcm.getColumn(2).setPreferredWidth(80);
        tcm.getColumn(3).setPreferredWidth(150);
        tcm.getColumn(4).setPreferredWidth(40);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        tcm.getColumn(4).setCellRenderer(rightRenderer);
        tcm.getColumn(5).setCellRenderer(rightRenderer);
    }

    private void reSizeColumnTable2() {
        TableColumnModel tcm = tableHD.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(30);
        tcm.getColumn(1).setPreferredWidth(30);
        tcm.getColumn(2).setPreferredWidth(150);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        tcm.getColumn(3).setCellRenderer(rightRenderer);
        tcm.getColumn(4).setCellRenderer(rightRenderer);
        tcm.getColumn(5).setCellRenderer(rightRenderer);
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
    public void setKaraokeBookingUI(KaraokeBooking_UI main) {
        this.main = main;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o.equals(btnTim)) {
            if (txtTK.getText().trim().equals("")&&txtKH.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin tìm kiếm");
                modelTablePDP.getDataVector().removeAllElements();
                loadHD();
            }
            else if (!txtTK.getText().trim().equals("")) {
                modelTablePDP.getDataVector().removeAllElements();

                String txtMaP = txtTK.getText();
                rsvf =billDAO.getBillByRoomID(txtMaP);
                if(rsvf!=null) {


                    String date = formatDate(rsvf.getNgayGioDat());

                    Object[] rowData = {1, rsvf.getMaPhong().getMaPhong(), rsvf.getMaPhong().getLoaiPhong().getTenLoaiPhong(), rsvf.getMaKH().getTenKhachHang(), date, df.format(rsvf.getMaPhong().getGiaPhong())};
                    modelTablePDP.addRow(rowData);
                }else {
                    JOptionPane.showMessageDialog(this, "Không có phòng đang được sử dụng khớp với thông tin tìm kiếm");
                    txtTK.selectAll();
                    txtTK.requestFocus();
                }



            }else if(!txtKH.getText().trim().equals("")){
                modelTablePDP.getDataVector().removeAllElements();

                String txtTen = txtKH.getText();
                rsvf2 =billDAO.getBillByCustomerName(txtTen);
                if(rsvf2 !=null) {
                    String date2 = formatDate(rsvf2.getNgayGioDat());
                    Object[] rowData = {1, rsvf2.getMaPhong().getMaPhong(), rsvf2.getMaPhong().getLoaiPhong().getTenLoaiPhong(), rsvf2.getMaKH().getTenKhachHang(), date2, df.format(rsvf2.getMaPhong().getGiaPhong())};
                    modelTablePDP.addRow(rowData);
                }else{
                    JOptionPane.showMessageDialog(this, "Không có phòng đang được sử dụng khớp với thông tin tìm kiếm");
                    txtKH.selectAll();
                    txtKH.requestFocus();
                }
            }


        }else if(o.equals(btnLap)){
            int row = tblPDP.getSelectedRow();
            String maPhong = modelTablePDP.getValueAt(row,1).toString();

            Bill bill = billDAO.getBillByRoomID(maPhong);
            if (bill != null) {
                Room room = roomDAO.getRoomByRoomId(maPhong);
                if (room == null)
                    room = new Room();
                bill.setMaPhong(room);
                String billId = bill.getMaHoaDon();
                ArrayList<DetailsOfService> billInfoList = ctdv_dao.getDetailsOfServiceForBill(billId);
                bill.setLstDetails(billInfoList);
                long millis = System.currentTimeMillis();
                Timestamp endTime = new Timestamp(millis);
                bill.setNgayGioTra(endTime);
                Double totalPriceBill = bill.getTongTienHD();

                DialogBill winPayment = new DialogBill(bill);
                winPayment.setModal(true);
                winPayment.setVisible(true);
                Boolean isPaid = winPayment.getPaid();
                if (isPaid) {
//                    ArrayList<Room> yourListOfRooms = roomDAO.getRoomList();
////                    main.LoadRoomList(yourListOfRooms);
                } else {
                    bill.setNgayGioTra(null);
                }
            }
        }
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