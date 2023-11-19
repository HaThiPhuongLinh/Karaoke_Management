package UI.component.Dialog;

import ConnectDB.ConnectDB;
import DAO.CustomerDAO;
import DAO.ReservationFormDAO;
import DAO.RoomDAO;
import Entity.Customer;
import Entity.ReservationForm;
import Entity.Room;
import Entity.Staff;
import UI.CustomUI.Custom;
import UI.component.Customer_UI;
import UI.component.KaraokeBooking_UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Giao diện cho thuê phòng
 * Người tham gia thiết kế: Hà Thị Phương Linh
 * Ngày tạo: 24/10/2023
 * Lần cập nhật cuối: 01/11/2023
 * Nội dung cập nhật: Sửa tính năng check số điện thoại khách
 */
public class PresetRoom extends JFrame implements ActionListener, MouseListener {
    private static PresetRoom instance = new PresetRoom();
    public JLabel lblTitle, lblRoomID, lblRoomID2, lblPhone, lblCName, lblCName2, lblRDay, lblTDay, lblMin, lblHour;
    public JTextField txtPhone;
    public LocalDateTime selectedDateTime;
    private JPanel pnlTop;
    private JButton btnCheck, btnCancel, btnBook;
    private JComboBox<String> cmbHour, cmbMin, cmbDate;
    private CustomerDAO customerDAO;
    private KaraokeBooking_UI main;
    private Main m;
    private Staff staffLogin = null;
    private ReservationFormDAO reservationFormDAO;
    private RoomDAO roomDAO;

    public PresetRoom() {
        setSize(460, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setLayout(null);

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        customerDAO = new CustomerDAO();
        reservationFormDAO = new ReservationFormDAO();
        roomDAO = new RoomDAO();

        pnlTop = new JPanel();
        pnlTop.setBackground(Color.decode("#5F009D"));
        pnlTop.setBounds(0, 0, 500, 50);
        lblTitle = new JLabel("ĐẶT PHÒNG CHỜ");
        lblTitle.setFont(new Font("Dialog", Font.BOLD, 25));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(10, 10, 480, 60);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pnlTop.add(lblTitle);
        add(pnlTop);

        lblRoomID = new JLabel("Phòng số:");
        lblRoomID.setFont(new Font("Dialog", Font.BOLD, 14));
        lblRoomID.setBounds(10, 60, 80, 20);
        lblRoomID2 = new JLabel();
        lblRoomID2.setBounds(100, 60, 100, 20);

        lblPhone = new JLabel("Số điện thoại khách:");
        lblPhone.setFont(new Font("Dialog", Font.BOLD, 14));
        lblPhone.setBounds(10, 100, 160, 20);
        txtPhone = new JTextField();
        txtPhone.setFont(new Font("Dialog", Font.BOLD, 14));
        txtPhone.setBounds(170, 98, 140, 25);
        btnCheck = new JButton("Kiểm tra");
        btnCheck.setBounds(330, 97, 100, 25);

        lblCName = new JLabel("Tên khách:");
        lblCName.setFont(new Font("Dialog", Font.BOLD, 14));
        lblCName.setBounds(10, 140, 80, 20);
        lblCName2 = new JLabel("");
        lblCName2.setFont(new Font("Dialog", Font.BOLD, 14));
        lblCName2.setBounds(120, 140, 170, 20);

        lblRDay = new JLabel("Ngày nhận phòng:");
        lblRDay.setFont(new Font("Dialog", Font.BOLD, 14));
        lblRDay.setBounds(10, 180, 130, 20);
        cmbDate = new JComboBox<>();
        cmbDate.setBounds(170, 180, 195, 25);
        Custom.setCustomComboBox(cmbDate);
        add(cmbDate);

        lblTDay = new JLabel("Giờ nhận phòng:");
        lblTDay.setFont(new Font("Dialog", Font.BOLD, 14));
        lblTDay.setBounds(10, 220, 120, 20);
        cmbHour = new JComboBox<>(new String[]{"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"});
        cmbHour.setBounds(150, 220, 60, 25);
        cmbHour.setFont(new Font("Dialog", Font.BOLD, 15));
        lblHour = new JLabel("giờ");
        lblHour.setFont(new Font("Dialog", Font.BOLD, 14));
        lblHour.setBounds(210, 220, 40, 20);
        cmbMin = new JComboBox<>(new String[]{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"});
        cmbMin.setBounds(260, 220, 60, 25);
        cmbMin.setFont(new Font("Dialog", Font.BOLD, 15));
        lblMin = new JLabel("phút");
        lblMin.setFont(new Font("Dialog", Font.BOLD, 14));
        lblMin.setBounds(330, 220, 40, 20);

        btnCancel = new JButton("Hủy");
        btnCancel.setBounds(130, 260, 100, 30);
        btnCancel.setBackground(Color.decode("#FF3E20"));
        btnCancel.setForeground(Color.WHITE);

        btnBook = new JButton("Đặt phòng");
        btnBook.setBounds(250, 260, 100, 30);
        btnBook.setBackground(Color.decode("#008000"));
        btnBook.setForeground(Color.WHITE);

        add(lblRoomID);
        add(lblRoomID2);
        add(lblPhone);
        add(txtPhone);
        add(btnCheck);
        add(lblCName);
        add(lblCName2);
        add(lblRDay);
        add(lblTDay);
        add(cmbHour);
        add(lblHour);
        add(cmbMin);
        add(lblMin);
        add(btnCancel);
        add(btnBook);

        btnCancel.addActionListener(this);
        btnCancel.addMouseListener(this);
        btnBook.addActionListener(this);
        btnBook.addMouseListener(this);
        btnCheck.addActionListener(this);
        btnCheck.addMouseListener(this);

        txtPhone.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnCheck.doClick();
                }
            }
        });

        updateDateComboBox();
    }

    public static PresetRoom getInstance() {
        if (instance == null)
            instance = new PresetRoom();
        return instance;
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            PresetRoom presetRoom = new PresetRoom();
//            presetRoom.setVisible(true);
//        });
//    }

    private void updateDateComboBox() {
        LocalDate currentDate = LocalDate.now();
        ArrayList<String> dateList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", new Locale("vi"));

        // Thêm các ngày từ ngày hiện tại đến ngày hiện tại + 1 tuần
        for (int i = 1; i < 7; i++) {
            String formattedDate = currentDate.plusDays(i).format(formatter);
            dateList.add(formattedDate);
        }

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dateList.toArray(new String[0]));
        cmbDate.setModel(model);
    }

    public void setKaraokeBookingUI(KaraokeBooking_UI main) {
        this.main = main;
    }

    public void setM(Main m) {
        this.m = m;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnCancel)) {
            dispose();
        }
        if (o.equals(btnCheck)) {
            String phone = txtPhone.getText();
            if (!phone.equalsIgnoreCase("")) {
                Customer c = customerDAO.getKhachHangBySDT(phone);
                if (c != null) {
                    lblCName2.setText(c.getTenKhachHang());
                } else {
                    JOptionPane.showMessageDialog(this, "Chưa có khách hàng trong hệ thống");
                    int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm khách hàng", "Thông báo", JOptionPane.YES_NO_OPTION);

                    if (choice == JOptionPane.YES_OPTION) {
                        dispose();
                        m.showForm(new Customer_UI(staffLogin));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chưa nhập số điện thoại khách hàng");
            }
        }
        if (o.equals(btnBook)) {
            if (txtPhone.getText().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Chưa nhập số điện thoại khách hàng");
            } else {
                String selectedDateString = (String) cmbDate.getSelectedItem();
                if (selectedDateString == null) {
                    JOptionPane.showMessageDialog(this, "Chưa chọn ngày nhận phòng");
                    return;
                }

                String selectedHour = (String) cmbHour.getSelectedItem();
                String selectedMinute = (String) cmbMin.getSelectedItem();

                if (lblCName2.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(this, "Chưa điền tên khách hàng");
                    return;
                }

                if (selectedHour == null || selectedMinute == null) {
                    JOptionPane.showMessageDialog(this, "Chưa chọn thời gian nhận phòng");
                    return;
                }
                int hour = Integer.parseInt(selectedHour);
                int minute = Integer.parseInt(selectedMinute);

                LocalDate selectedDate = LocalDate.parse(selectedDateString, DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", new Locale("vi")));

                LocalDateTime currentDateTime = LocalDateTime.now();
                LocalTime selectedTime = LocalTime.of(hour, minute);
                selectedDateTime = LocalDateTime.of(selectedDate, selectedTime);

                if (selectedDateTime.isBefore(currentDateTime)) {
                    JOptionPane.showMessageDialog(this, "Thời gian đặt không hợp lệ");
                    return;
                }

                String roomID = lblRoomID2.getText().trim();
                Room room = roomDAO.getRoomByRoomId(roomID);
                if (room != null) {
                    Map<LocalDate, ArrayList<ReservationForm>> reservationsByDate = new HashMap<>();
                    ArrayList<ReservationForm> reservations = reservationFormDAO.getReservationsByRoomID(roomID);
                    for (ReservationForm reservation : reservations) {
                        LocalDateTime reservationTime = reservation.getThoiGianDat().toLocalDateTime();
                        LocalDate reservationDate = reservationTime.toLocalDate();

                        reservationsByDate.computeIfAbsent(reservationDate, k -> new ArrayList<>()).add(reservation);
                    }

                    // Kiểm tra xem ngày đã có phiếu đặt chưa
                    LocalDate selectedDate2 = selectedDateTime.toLocalDate();
                    ArrayList<ReservationForm> reservationsOnSelectedDate = reservationsByDate.get(selectedDate2);
                    if (reservationsOnSelectedDate != null && !reservationsOnSelectedDate.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Phòng đã có phiếu đặt vào ngày đã chọn. Vui lòng chọn ngày khác.");
                        return;
                    }
                    createReservationForm();
                }
            }
        }
    }

    /**
     * Tự phát sinh mã phiếu đặt phòng
     *
     * @return String
     */
    private String generateID() {
        String formID = reservationFormDAO.generateNextFormId();
        return formID;
    }

    /**
     * Tạo phiếu đặt phòng
     */
    private void createReservationForm() {
        String formID = generateID();
        Timestamp startTime = Timestamp.valueOf(selectedDateTime);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Staff staffLogin = KaraokeBooking_UI.getInstance().staffLogin;

        String customerName = lblCName2.getText().trim();
        String customerID = customerDAO.getIdByTenKhachHang(customerName);
        Customer c = customerDAO.getKhachHangById(customerID);
        if (c == null) {
            c = new Customer();
        }

        String roomID = lblRoomID2.getText().trim();
        Room room = roomDAO.getRoomByRoomId(roomID);
        if (room == null) {
            room = new Room();
        }

        ReservationForm form = new ReservationForm(formID, currentTime, startTime, staffLogin, c, room, 2);
        boolean resultForm = reservationFormDAO.addReservationForm(form);

        if (resultForm) {
            if (room.getTinhTrang().equalsIgnoreCase("Trống")) {
                roomDAO.updateRoomStatus(roomID, "Chờ");
            }
            JOptionPane.showMessageDialog(this, "Cho thuê phòng thành công");
            ArrayList<Room> yourListOfRooms = roomDAO.getRoomList();
            main.LoadRoomList(yourListOfRooms);
        } else {
            JOptionPane.showMessageDialog(this, "Cho thuê phòng thất bại");
        }
        dispose();
    }

    /**
     * Gán mã phiếu đặt xong cho label
     */
    public void setRoomID(String roomID) {
        lblRoomID2.setText(roomID);
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
        Object o = e.getSource();
        if (o.equals(btnCancel)) {
            btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (o.equals(btnBook)) {
            btnBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (o.equals(btnCheck)) {
            btnCheck.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
