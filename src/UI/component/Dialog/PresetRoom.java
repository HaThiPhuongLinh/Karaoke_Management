package UI.component.Dialog;

import ConnectDB.ConnectDB;
import DAO.CustomerDAO;
import DAO.ReservationFormDAO;
import DAO.RoomDAO;
import Entity.Customer;
import Entity.ReservationForm;
import Entity.Room;
import Entity.Staff;
import UI.component.Customer_UI;
import UI.component.KaraokeBooking_UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

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
    private JRadioButton radToday, radTomorrow;
    private ButtonGroup bgTime;
    private JComboBox<String> cmbHour, cmbMin;
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
        radToday = new JRadioButton("Hôm nay");
        radToday.setFont(new Font("Dialog", Font.BOLD, 14));
        radToday.setBounds(160, 180, 110, 20);
        radTomorrow = new JRadioButton("Ngày mai");
        radTomorrow.setFont(new Font("Dialog", Font.BOLD, 14));
        radTomorrow.setBounds(280, 180, 140, 20);
        bgTime = new ButtonGroup();
        bgTime.add(radToday);
        bgTime.add(radTomorrow);

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
        add(radToday);
        add(radTomorrow);
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
    }

    public static PresetRoom getInstance() {
        if (instance == null)
            instance = new PresetRoom();
        return instance;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PresetRoom presetRoom = new PresetRoom();
            presetRoom.setVisible(true);
        });
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
                    int choice =  JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm khách hàng", "Thông báo", JOptionPane.YES_NO_OPTION);

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
                boolean isRadTodaySelected = radToday.isSelected();
                boolean isRadTomorrowSelected = radTomorrow.isSelected();

                if (!isRadTodaySelected && !isRadTomorrowSelected) {
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

                LocalDateTime currentDateTime = LocalDateTime.now();
                LocalDate selectedDate = isRadTodaySelected ? LocalDate.now() : LocalDate.now().plusDays(1);
                LocalTime selectedTime = LocalTime.of(hour, minute);
                selectedDateTime = LocalDateTime.of(selectedDate, selectedTime);

                if (selectedDateTime.isBefore(currentDateTime)) {
                    JOptionPane.showMessageDialog(this, "Thời gian đặt không hợp lệ");
                    return;
                }

                String roomID = lblRoomID2.getText().trim();
                Room room = roomDAO.getRoomByRoomId(roomID);
                if (room != null) {
                    if (room.getTinhTrang().equalsIgnoreCase("Đang sử dụng")) {
                        LocalDateTime minValidTime = currentDateTime.plusHours(2);

                        ReservationForm existingForm = reservationFormDAO.getFormByRoomID(roomID);
                        if (existingForm != null) {
                            LocalDateTime previousBookingTime = existingForm.getThoiGianDat().toLocalDateTime();
                            minValidTime = previousBookingTime.plusHours(4);
                        }

                        if (selectedDateTime.isBefore(minValidTime)) {
                            JOptionPane.showMessageDialog(this, "Phòng đang sử dụng hoặc đã có phiếu đặt. Vui lòng đặt sau giờ hợp lệ.");
                            return;
                        }
                    } else {
                        ReservationForm existingForm = reservationFormDAO.getFormByRoomID(roomID);
                        if (existingForm != null) {
                            LocalDateTime previousBookingTime = existingForm.getThoiGianDat().toLocalDateTime();
                            LocalDateTime minValidTime = previousBookingTime.plusHours(4);
                            if (selectedDateTime.isBefore(minValidTime)) {
                                JOptionPane.showMessageDialog(this, "Phòng đã có phiếu đặt. Vui lòng đặt sau 4 tiếng kể từ thời gian đặt trước đó.");
                                return;
                            }
                        }
                    }
                }

                createReservationForm();
            }
        }

    }

    /**
     * Tự phát sinh mã phiếu đặt phòng
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

        ReservationForm form = new ReservationForm(formID, startTime, staffLogin, c, room);
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
     * @param roomID
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
