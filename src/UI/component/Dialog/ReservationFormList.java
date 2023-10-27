package UI.component.Dialog;

import ConnectDB.ConnectDB;
import DAO.BillDAO;
import DAO.CustomerDAO;
import DAO.ReservationFormDAO;
import DAO.RoomDAO;
import Entity.*;
import UI.CustomUI.Custom;
import UI.component.KaraokeBooking_UI;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReservationFormList extends JFrame implements ActionListener, MouseListener {
    private JTable table;
    JButton btnFind, btnReceive, btnALL, btnCancel;
    JTextField txtTim;
    private DefaultTableModel modelTable;
    private CustomerDAO customerDAO;
    private static KaraokeBooking_UI main;
    ArrayList<ReservationForm> lstForms;
    private ReservationFormDAO reservationFormDAO;
    private static ReservationFormList instance;
    private BillDAO billDAO ;
    private RoomDAO roomDAO = new RoomDAO();

    public static ReservationFormList getInstance() {
        if (instance == null)
            instance = new ReservationFormList();
        return instance;
    }
    public void setKaraokeBookingUI(KaraokeBooking_UI main) {
        this.main = main;
    }

        public ReservationFormList() {
            setSize(900, 550);
            setLocationRelativeTo(null);
            setResizable(false);
            this.main = main;
            reservationFormDAO = new ReservationFormDAO();
            billDAO = new BillDAO();
            roomDAO = new RoomDAO();

            try {
                ConnectDB.getInstance().connect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            gui();
        }

        private String formatDate(Date date) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            return sdf.format(date);
        }

        private void gui() {
            JPanel panel = new JPanel();
            add(panel, BorderLayout.CENTER);
            panel.setLayout(null);

            JPanel searchPanel = new JPanel();
            searchPanel.setBounds(110, 20, 650, 70);
            searchPanel.setLayout(null);
            JLabel nhapten = new JLabel("Tên khách hàng: ");
            nhapten.setBounds(80, 32, 180, 20);
            searchPanel.add(nhapten);
            txtTim = new JTextField();
            txtTim.setBounds(180, 29, 250, 28);
            searchPanel.add(txtTim);
            btnFind = new JButton("Tìm");
            btnFind.setBounds(450, 26, 55, 30);
            searchPanel.add(btnFind);


            btnALL = new JButton("Refresh");
            btnALL.setBounds(510, 26, 75, 30);
            searchPanel.add(btnALL);
            searchPanel.setBorder(new TitledBorder(
                    new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                    "Tìm khách hàng", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
            panel.add(searchPanel);
            Custom.setCustomBtn(btnALL);
            JPanel pnTable = new JPanel();
            pnTable.setBounds(20, 120, 850, 340);
            panel.add(pnTable);
            pnTable.setLayout(new BorderLayout(0, 0));
            String[] cols = { "Mã phiếu đặt", "Tên khách hàng", "Thời gian đặt", "Mã phòng", "Loại phòng" };

            modelTable = new DefaultTableModel(cols, 0);
            table = new JTable(modelTable);
            JScrollPane scpTable = new JScrollPane(table);
            pnTable.add(scpTable, BorderLayout.CENTER);
            Custom.setCustomTable(table);

            btnReceive = new JButton("Nhận phòng");
            btnReceive.setBounds(300, 470, 130, 30);
            btnReceive.setBackground(Color.decode("#008000"));
            btnReceive.setForeground(Color.WHITE);
            panel.add(btnReceive);

            btnCancel = new JButton("Hủy phòng");
            btnCancel.setBounds(460, 470, 130, 30);
            btnCancel.setBackground(Color.decode("#FF3E20"));
            btnCancel.setForeground(Color.WHITE);
            panel.add(btnCancel);

            Custom.setCustomBtn(btnFind);

            btnALL.setFont(new Font("Arial", Font.BOLD, 14));
            btnFind.setFont(new Font("Arial", Font.BOLD, 14));
            btnReceive.setFont(new Font("Arial", Font.BOLD, 14));
            btnCancel.setFont(new Font("Arial", Font.BOLD, 14));

            customerDAO = new CustomerDAO();
            table.setRowHeight(30);

            for (ReservationForm r : reservationFormDAO.getAllForm()) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String date = sdf.format(r.getThoiGianDat());
                Object[] rowData = { r.getMaPhieuDat(),r.getMaKhachHang().getTenKhachHang(),date,r.getMaPhong().getMaPhong(), r.getMaPhong().getLoaiPhong().getTenLoaiPhong()};
                modelTable.addRow(rowData);
            }

            btnFind.addActionListener(this);
            btnReceive.addActionListener(this);
            btnALL.addActionListener(this);
            btnCancel.addActionListener(this);
        }

        public static void main(String[] args) {
            new UI.component.Dialog.ReservationFormList().setVisible(true);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object o = e.getSource();
            if (o.equals(btnFind)) {
                if (txtTim.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "Chưa nhập tên khách hàng");
                } else {
                    String tenKH = txtTim.getText().trim();
                    modelTable.getDataVector().removeAllElements();
                    modelTable.fireTableDataChanged();
                    lstForms = customerDAO.findReservationFormsByCustomerName(tenKH);
                    if (lstForms == null || lstForms.size() <= 0) {
                        modelTable.getDataVector().removeAllElements();
                    } else {
                        for (ReservationForm r : lstForms) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            String date = sdf.format(r.getThoiGianDat());
                            Object[] rowData = { r.getMaPhieuDat(),r.getMaKhachHang().getTenKhachHang(),date,r.getMaPhong().getMaPhong(), r.getMaPhong().getLoaiPhong().getTenLoaiPhong()};
                            modelTable.addRow(rowData);
                        }

                    }
                }
            }
            if (o.equals(btnReceive)) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Chưa chọn phiếu đặt");
                } else {
                    int maPhongColumn = 3;
                    String roomID = table.getValueAt(row, maPhongColumn).toString();

                    Room room = roomDAO.getRoomByRoomId(roomID);
                    if (room == null) {
                        room = new Room();
                    }

                    if (room.getTinhTrang().equalsIgnoreCase("Chờ")) {
                        int khachHangColumn = 1;
                        String customerName = table.getValueAt(row, khachHangColumn).toString();
                        String customerID = customerDAO.getIdByTenKhachHang(customerName);
                        Customer c = customerDAO.getKhachHangById(customerID);
                        if (c == null) {
                            c = new Customer();
                        }
                        Staff staffLogin = KaraokeBooking_UI.getInstance().staffLogin;
                        long millis = System.currentTimeMillis();
                        Timestamp startTime = new Timestamp(millis);
                        Timestamp receiveTime = null;
                        int tinhTrang = 0;
                        String khuyenMai = "";

                        String billID = generateBillID();
                        Bill bill = new Bill(billID, staffLogin, c, room, startTime, receiveTime, tinhTrang, khuyenMai);
                        boolean resultBill = billDAO.addBill(bill);

                        if (resultBill) {
                            int maPhieuColumn = 0;
                            String reservationFormID = table.getValueAt(row, maPhieuColumn).toString();;
                            boolean deleteResult = reservationFormDAO.deleteReservationForm(reservationFormID);

                            roomDAO.updateRoomStatus(roomID, "Đang sử dụng");
                            ArrayList<Room> yourListOfRooms = roomDAO.getRoomList();
                            main.LoadRoomList(yourListOfRooms);
                            JOptionPane.showMessageDialog(this, "Nhận phòng thành công");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Cho thuê phòng thất bại");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Phòng đang được sử dụng");
                    }
                }
            }

            if (o.equals(btnCancel)) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Chưa chọn phiếu đặt");
                } else {
                    int maPhongColumn = 3;
                    String roomID = table.getValueAt(row, maPhongColumn).toString();

                    Room room = roomDAO.getRoomByRoomId(roomID);
                    if (room == null) {
                        room = new Room();
                    }
                    int maPhieuColumn = 0;
                    String reservationFormID = table.getValueAt(row, maPhieuColumn).toString();;
                    boolean deleteResult = reservationFormDAO.deleteReservationForm(reservationFormID);
                    if(deleteResult){
                        JOptionPane.showMessageDialog(this, "Hủy đặt phòng thành công");
                        modelTable.setRowCount(0);
                        for (ReservationForm r : reservationFormDAO.getAllForm()) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            String date = sdf.format(r.getThoiGianDat());
                            Object[] rowData = { r.getMaPhieuDat(),r.getMaKhachHang().getTenKhachHang(),date,r.getMaPhong().getMaPhong(), r.getMaPhong().getLoaiPhong().getTenLoaiPhong()};
                            modelTable.addRow(rowData);
                        }
                        roomDAO.updateRoomStatus(roomID, "Trống");
                        ArrayList<Room> yourListOfRooms = roomDAO.getRoomList();
                        main.LoadRoomList(yourListOfRooms);
                    } else {
                        JOptionPane.showMessageDialog(this, "Hủy đặt phòng thất bại");
                    }
                    txtTim.setText("");
                }
            }

            if (o.equals(btnALL)) {
                modelTable.setRowCount(0);
                int row = table.getSelectedRow();
                for (ReservationForm r : reservationFormDAO.getAllForm()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String date = sdf.format(r.getThoiGianDat());
                    Object[] rowData = { r.getMaPhieuDat(),r.getMaKhachHang().getTenKhachHang(),date,r.getMaPhong().getMaPhong(), r.getMaPhong().getLoaiPhong().getTenLoaiPhong()};
                    modelTable.addRow(rowData);
                }
                txtTim.setText("");
            }
        }
    private String generateBillID() {
        String billID = billDAO.generateNextBillId();
        return billID;
    }
    }

