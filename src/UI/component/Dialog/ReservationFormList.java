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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Giao diện hiển thị phiếu đặt phòng
 * Người tham gia thiết kế: Hà Thị Phương Linh
 * Ngày tạo: 25/10/2023
 * Lần cập nhật cuối: 01/11/2023
 * Nội dung cập nhật: Cập nhật tính năng nhận phòng
 */
public class ReservationFormList extends JFrame implements ActionListener, MouseListener {
    private static KaraokeBooking_UI main;
    private static ReservationFormList instance;
    private JButton btnFind, btnReceive, btnALL, btnCancel;
    private JTextField txtTim;
    private ArrayList<ReservationForm> lstForms;
    private JTable tblReservations;
    private DefaultTableModel modelTblReservations;
    private CustomerDAO customerDAO;
    private ReservationFormDAO reservationFormDAO;
    private BillDAO billDAO;
    private RoomDAO roomDAO = new RoomDAO();

    public ReservationFormList() {
        setSize(1050, 550);
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

    public static ReservationFormList getInstance() {
        if (instance == null)
            instance = new ReservationFormList();
        return instance;
    }

    public static void main(String[] args) {
        new UI.component.Dialog.ReservationFormList().setVisible(true);
    }

    public void setKaraokeBookingUI(KaraokeBooking_UI main) {
        this.main = main;
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
        searchPanel.setBounds(110, 20, 775, 70);
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
        pnTable.setBounds(20, 120, 995, 340);
        panel.add(pnTable);
        pnTable.setLayout(new BorderLayout(0, 0));
        String[] cols = {"Mã phiếu", "Tên khách hàng","Thời gian gọi", "Thời gian đặt", "Mã phòng", "Loại phòng", "Trạng thái"};

        modelTblReservations = new DefaultTableModel(cols, 0);
        tblReservations = new JTable(modelTblReservations);
        JScrollPane scpTable = new JScrollPane(tblReservations);
        pnTable.add(scpTable, BorderLayout.CENTER);
        Custom.setCustomTable(tblReservations);

        btnReceive = new JButton("Nhận phòng");
        btnReceive.setBounds(370, 470, 130, 30);
        btnReceive.setBackground(Color.decode("#008000"));
        btnReceive.setForeground(Color.WHITE);
        panel.add(btnReceive);

        btnCancel = new JButton("Hủy phòng");
        btnCancel.setBounds(560, 470, 130, 30);
        btnCancel.setBackground(Color.decode("#FF3E20"));
        btnCancel.setForeground(Color.WHITE);
        panel.add(btnCancel);

        Custom.setCustomBtn(btnFind);

        btnALL.setFont(new Font("Arial", Font.BOLD, 14));
        btnFind.setFont(new Font("Arial", Font.BOLD, 14));
        btnReceive.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));

        customerDAO = new CustomerDAO();
        tblReservations.setRowHeight(30);

        for (ReservationForm r : reservationFormDAO.getAllForm()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String date = sdf.format(r.getThoiGianDat());
            String date2 = sdf.format(r.getThoiGianGoi());
            String trangThaiLabel = getTrangThaiLabel(r.getTrangThai());
            Object[] rowData = {r.getMaPhieuDat(), r.getMaKhachHang().getTenKhachHang(), date2, date, r.getMaPhong().getMaPhong(), r.getMaPhong().getLoaiPhong().getTenLoaiPhong(), trangThaiLabel};
            modelTblReservations.addRow(rowData);
        }

        tblReservations.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblReservations.getSelectedRow();

                if (selectedRow != -1) {
                    int trangThaiColumn = 6;
                    String trangThaiValue = (String) tblReservations.getValueAt(selectedRow, trangThaiColumn);

                    if (trangThaiValue.trim().equalsIgnoreCase("Đã hủy") || trangThaiValue.trim().equalsIgnoreCase("Đã nhận")) {
                        btnReceive.setEnabled(false);
                        btnCancel.setEnabled(false);
                    } else {
                        btnReceive.setEnabled(true);
                        btnCancel.setEnabled(true);
                    }
                }
            }
        });


        btnFind.addActionListener(this);
        btnReceive.addActionListener(this);
        btnALL.addActionListener(this);
        btnCancel.addActionListener(this);

        reSizeColumnTable();
    }

    private void reSizeColumnTable() {
        TableColumnModel tcm = tblReservations.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(40);
        tcm.getColumn(1).setPreferredWidth(140);
        tcm.getColumn(2).setPreferredWidth(100);
        tcm.getColumn(3).setPreferredWidth(100);
        tcm.getColumn(4).setPreferredWidth(40);
        tcm.getColumn(5).setPreferredWidth(40);
        tcm.getColumn(6).setPreferredWidth(50);
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
        Object o = e.getSource();
        if (o.equals(btnCancel)) {
            btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (o.equals(btnReceive)) {
            btnReceive.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (o.equals(btnFind)) {
            btnFind.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (o.equals(btnALL)) {
            btnALL.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
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
                modelTblReservations.getDataVector().removeAllElements();
                modelTblReservations.fireTableDataChanged();
                lstForms = customerDAO.findReservationFormsByCustomerName(tenKH);
                if (lstForms == null || lstForms.size() <= 0) {
                    modelTblReservations.getDataVector().removeAllElements();
                } else {
                    for (ReservationForm r : lstForms) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String date = sdf.format(r.getThoiGianDat());
                        String date2 = sdf.format(r.getThoiGianGoi());
                        String trangThaiLabel = getTrangThaiLabel(r.getTrangThai());
                        Object[] rowData = {r.getMaPhieuDat(), r.getMaKhachHang().getTenKhachHang(), date2, date, r.getMaPhong().getMaPhong(), r.getMaPhong().getLoaiPhong().getTenLoaiPhong(), trangThaiLabel};
                        modelTblReservations.addRow(rowData);
                    }

                }
            }
        }
        if (o.equals(btnReceive)) {
            int row = tblReservations.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chưa chọn phiếu đặt");
            } else {
                int maPhongColumn = 4;
                String roomID = tblReservations.getValueAt(row, maPhongColumn).toString();

                Room room = roomDAO.getRoomByRoomId(roomID);
                if (room == null) {
                    room = new Room();
                }

                if (room.getTinhTrang().equalsIgnoreCase("Chờ")) {
                    ArrayList<ReservationForm> reservations = reservationFormDAO.getActiveReservationsForRoom(roomID);

                    if (!reservations.isEmpty()) {
                        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

                        int maPhieuColumn = 0;
                        String reservationFormID = tblReservations.getValueAt(row, maPhieuColumn).toString();
                        ReservationForm selectedReservation = null;
                        for (ReservationForm reservation : reservations) {
                            if (reservation.getMaPhieuDat().equals(reservationFormID)) {
                                selectedReservation = reservation;
                                break;
                            }
                        }
                        Timestamp selectedReservationTime = selectedReservation.getThoiGianDat();

                        String message = "Phòng đã được đặt vào các thời điểm sau:\n";
                        SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm:ss dd/MM/yyyy");

                        for (int i = 0; i < reservations.size(); i++) {
                            ReservationForm reservation = reservations.get(i);
                            Timestamp reservationTime = reservation.getThoiGianDat();
                            if (reservationTime.before(selectedReservationTime)) {
                                String timeDiffMessage = sdf.format(reservationTime);
                                message += "- " + timeDiffMessage + "\n";
                            }
                        }
                        if (message.equals("Phòng đã được đặt vào các thời điểm sau:\n")) {
                            long timeDifference = (selectedReservationTime.getTime() - currentTime.getTime());

                            int minutes = (int) ((timeDifference / (1000 * 60)) % 60);
                            int hours = (int) (timeDifference / (1000 * 60 * 60));

                            message = "Còn " + hours + " giờ " + minutes + " phút mới đến giờ đặt. Bạn có muốn nhận phòng ngay bây giờ không?";
                            int choice = JOptionPane.showConfirmDialog(this, message, "Thông báo", JOptionPane.YES_NO_OPTION);

                            if (choice == JOptionPane.YES_OPTION) {
                                int khachHangColumn = 1;
                                String customerName = tblReservations.getValueAt(row, khachHangColumn).toString();
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
                                //int maPhieuColumn = 0;

                                String billID = generateBillID();
                                Bill bill = new Bill(billID, staffLogin, c, room, startTime, receiveTime, tinhTrang, khuyenMai);
                                boolean resultBill = billDAO.addBill(bill);

                                if (resultBill) {
                                    if (room.getTinhTrang().equalsIgnoreCase("Chờ")) {
                                        roomDAO.updateRoomStatus(roomID, "Đang sử dụng");
                                    }
                                    String reservationFormID2 = tblReservations.getValueAt(tblReservations.getSelectedRow(), maPhieuColumn).toString();
                                    //boolean deleteResult = reservationFormDAO.deleteReservationForm(reservationFormID);
                                    boolean updateResult = reservationFormDAO.updateReservationStatus(reservationFormID2,  1);
                                    ArrayList<Room> yourListOfRooms = roomDAO.getRoomList();
                                    main.LoadRoomList(yourListOfRooms);
                                    JOptionPane.showMessageDialog(this, "Nhận phòng thành công");
                                    dispose();
                                } else {
                                    JOptionPane.showMessageDialog(this, "Nhận phòng thất bại");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, message);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Phòng đang được sử dụng");
                }
            }
        }
        if (o.equals(btnCancel)) {
            int row = tblReservations.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chưa chọn phiếu đặt");
            } else {
                int maPhongColumn = 4;
                String roomID = tblReservations.getValueAt(row, maPhongColumn).toString();

                Room room = roomDAO.getRoomByRoomId(roomID);
                if (room == null) {
                    room = new Room();
                }
                int maPhieuColumn = 0;
                String reservationFormID = tblReservations.getValueAt(row, maPhieuColumn).toString();

                // Cập nhật trạng thái thành 0
                boolean updateResult = reservationFormDAO.updateReservationStatus(reservationFormID,  0);

                if (updateResult) {
                    JOptionPane.showMessageDialog(this, "Hủy đặt phòng thành công");
                    ArrayList<ReservationForm> reservations = reservationFormDAO.getReservationsByRoomID(roomID);

                    if (!reservationFormDAO.hasActiveReservationsForRoom(roomID)) {
                        // Nếu không còn phiếu đặt nào có tình trạng khác 0, cập nhật tình trạng phòng thành "Trống"
                        roomDAO.updateRoomStatus(roomID, "Trống");
                    }
                    modelTblReservations.setRowCount(0);

                    for (ReservationForm r : reservationFormDAO.getAllForm()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String date = sdf.format(r.getThoiGianDat());
                        String date2 = sdf.format(r.getThoiGianGoi());
                        String trangThaiLabel = getTrangThaiLabel(r.getTrangThai());
                        Object[] rowData = {r.getMaPhieuDat(), r.getMaKhachHang().getTenKhachHang(), date2, date, r.getMaPhong().getMaPhong(), r.getMaPhong().getLoaiPhong().getTenLoaiPhong(), trangThaiLabel};
                        modelTblReservations.addRow(rowData);
                    }

                    ArrayList<Room> yourListOfRooms = roomDAO.getRoomList();
                    main.LoadRoomList(yourListOfRooms);
                } else {
                    JOptionPane.showMessageDialog(this, "Hủy đặt phòng thất bại");
                }
                txtTim.setText("");
            }
        }

        if (o.equals(btnALL)) {
            modelTblReservations.setRowCount(0);
            int row = tblReservations.getSelectedRow();
            for (ReservationForm r : reservationFormDAO.getAllForm()) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String date = sdf.format(r.getThoiGianDat());
                String date2 = sdf.format(r.getThoiGianGoi());
                String trangThaiLabel = getTrangThaiLabel(r.getTrangThai());
                Object[] rowData = {r.getMaPhieuDat(), r.getMaKhachHang().getTenKhachHang(), date2, date, r.getMaPhong().getMaPhong(), r.getMaPhong().getLoaiPhong().getTenLoaiPhong(), trangThaiLabel};
                modelTblReservations.addRow(rowData);
            }
            txtTim.setText("");
        }
    }

    /**
     * Lấy nhãn trạng thái theo int
     * @param trangThai: (0 - Đã hủy, 1 - Đã nhận, 2 - Đang chờ)
     * @return
     */
    private String getTrangThaiLabel(int trangThai) {
        switch (trangThai) {
            case 0:
                return "Đã hủy";
            case 1:
                return "Đã nhận";
            case 2:
                return "Đang chờ";
            default:
                return "";
        }
    }

    /**
     * Tự phát sinh mã hóa đơn
     * @return String
     */
    private String generateBillID() {
        String billID = billDAO.generateNextBillId();
        return billID;
    }
}
