package UI.main_interface.component;

import ConnectDB.ConnectDB;
import DAOs.CustomerDAO;
import DAOs.ReservationFormDAO;
import DAOs.RoomDAO;
import DAOs.TypeOfRoomDAO;
import Entity.*;
import UI.CustomUI.Custom;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * KaraokeBooking (Giao diện quản lý đặt phòng)
 * Author: Hà Thị Phương Linh
 */
public class KaraokeBooking_UI extends JPanel implements ActionListener, MouseListener {
    public static JTextField txtCustomer;
    KaraokeBooking_UI main = this;
    private DefaultTableModel tableModel;
    private JPanel pnlShowRoom, pnlRoomList, timeNow, pnlRoomControl, pnlShowCustomer, pnlShowDetails;
    private JLabel backgroundLabel, timeLabel, roomLabel, statusLabel, customerLabel, room2Label, typeRoomLabel, locationLabel, nameLabel, startLabel, receiveLabel;
    private JTextField txtRoom, txtLocation, txtName, txtStart, txtReceive, txtTypeRoom;
    private JScrollPane scrShowRoom, scrService;
    private JButton btnSwitchRoom, btnBookRoom, btnPresetRoom, btnCancelRoom, btnReceiveRoom, btnChooseCustomer, btnForm;
    private JButton[] btnRoomList;
    private int heightTable = 140;
    private int location = -1;
    private JTable tblService;
    private TypeOfRoomDAO typeOfRoomDAO;
    private RoomDAO roomDAO;
    private JComboBox<String> cboRoomType, cboStatus;
    private ReservationFormDAO reservationFormDAO;
    private Staff staffLogin = null;
    private CustomerDAO customerDAO;
    private Room selectedRoom = null;

    public KaraokeBooking_UI(Staff staff) {
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1175, 770);

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        typeOfRoomDAO = new TypeOfRoomDAO();
        customerDAO = new CustomerDAO();
        reservationFormDAO = new ReservationFormDAO();
        roomDAO = new RoomDAO();

        timeNow = new JPanel();
        timeNow.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP));
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

        pnlRoomList = new JPanel();
        pnlRoomList.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Phòng", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlRoomList.setBounds(10, 150, 670, 540);
        pnlRoomList.setOpaque(false);
        add(pnlRoomList);
        pnlRoomList.setLayout(new BorderLayout(0, 0));

        pnlRoomControl = new JPanel();
        pnlRoomControl.setOpaque(false);
        pnlRoomControl.setBackground(Color.WHITE);
        pnlRoomList.add(pnlRoomControl, BorderLayout.NORTH);
        pnlRoomControl.setLayout(null);
        pnlRoomControl.setPreferredSize(new Dimension(330, 55));

        roomLabel = new JLabel("Loại phòng: ");
        roomLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        roomLabel.setBounds(20, 17, 85, 20);
        roomLabel.setForeground(Color.WHITE);
        pnlRoomControl.add(roomLabel);

        cboRoomType = new JComboBox<String>();
        loadCboRoomType();
        cboRoomType.setBounds(106, 14, 140, 28);
        Custom.setCustomComboBox(cboRoomType);
        pnlRoomControl.add(cboRoomType);

        btnSwitchRoom = new JButton("Đổi phòng");
        btnSwitchRoom.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnSwitchRoom);
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/transfer_16.png"));
        btnSwitchRoom.setIcon(icon);
        btnSwitchRoom.setBounds(260, 12, 135, 30);
        pnlRoomControl.add(btnSwitchRoom);

        statusLabel = new JLabel("Trạng thái: ");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setBounds(420, 17, 85, 20);
        statusLabel.setForeground(Color.WHITE);
        pnlRoomControl.add(statusLabel);

        cboStatus = new JComboBox<String>();
        cboStatus.addItem("Tất cả");
        cboStatus.addItem("Trống");
        cboStatus.addItem("Đang sử dụng");
        cboStatus.addItem("Chờ");
        cboStatus.addItem("Tạm");
        cboStatus.setBounds(500, 14, 140, 28);
        Custom.setCustomComboBox(cboStatus);
        pnlRoomControl.add(cboStatus);

        pnlShowRoom = new JPanel();
        pnlShowRoom.setOpaque(false);
        pnlShowRoom.setBackground(Color.WHITE);
        FlowLayout flShowRoom = new FlowLayout(FlowLayout.LEFT);
        flShowRoom.setHgap(6);
        flShowRoom.setVgap(6);
        pnlShowRoom.setLayout(flShowRoom);
        pnlShowRoom.setPreferredSize(new Dimension(310, 140));

        scrShowRoom = new JScrollPane(pnlShowRoom, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrShowRoom.setOpaque(false);
        scrShowRoom.getViewport().setOpaque(false);
        scrShowRoom.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        scrShowRoom.setBackground(Color.WHITE);
        scrShowRoom.getVerticalScrollBar().setUnitIncrement(10);
        pnlRoomList.add(scrShowRoom, BorderLayout.CENTER);

        pnlShowCustomer = new JPanel();
        pnlShowCustomer.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlShowCustomer.setBounds(330, 10, 805, 130);
        pnlShowCustomer.setOpaque(false);
        pnlShowCustomer.setLayout(null);
        add(pnlShowCustomer);

        customerLabel = new JLabel("Khách hàng: ");
        customerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        customerLabel.setBounds(20, 30, 85, 20);
        customerLabel.setForeground(Color.WHITE);
        pnlShowCustomer.add(customerLabel);

        txtCustomer = new JTextField();
        txtCustomer.setBounds(120, 27, 220, 30);
        txtCustomer.setFont(new Font("Dialog", Font.PLAIN, 14));
        pnlShowCustomer.add(txtCustomer);

        btnChooseCustomer = new JButton();
        btnChooseCustomer.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnChooseCustomer);
        btnChooseCustomer.setIcon(new ImageIcon(getClass().getResource("/UI/main_interface/icon/blueAdd_16.png")));
        btnChooseCustomer.setBounds(340, 27, 40, 30);
        pnlShowCustomer.add(btnChooseCustomer);

        btnForm = new JButton("Phiếu đặt phòng");
        btnForm.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnForm);
        btnForm.setBounds(120, 80, 155, 30);
        pnlShowCustomer.add(btnForm);

        btnBookRoom = new JButton("Đặt phòng");
        btnBookRoom.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnBookRoom);
        btnBookRoom.setBounds(440, 30, 155, 30);
        pnlShowCustomer.add(btnBookRoom);

        btnPresetRoom = new JButton("Đặt phòng chờ");
        btnPresetRoom.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnPresetRoom);
        btnPresetRoom.setBounds(440, 75, 155, 30);
        pnlShowCustomer.add(btnPresetRoom);

        btnReceiveRoom = new JButton("Nhận phòng");
        btnReceiveRoom.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnReceiveRoom);
        btnReceiveRoom.setBounds(610, 30, 155, 30);
        pnlShowCustomer.add(btnReceiveRoom);

        btnCancelRoom = new JButton("Hủy phòng");
        btnCancelRoom.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnCancelRoom);
        btnCancelRoom.setBounds(610, 75, 155, 30);
        pnlShowCustomer.add(btnCancelRoom);

        pnlShowDetails = new JPanel();
        pnlShowDetails.setLayout(null);
        pnlShowDetails.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Chi tiết", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlShowDetails.setBounds(690, 150, 450, 540);
        pnlShowDetails.setOpaque(false);
        add(pnlShowDetails);

        room2Label = new JLabel("Mã phòng: ");
        room2Label.setFont(new Font("Arial", Font.PLAIN, 14));
        room2Label.setBounds(40, 37, 100, 20);
        room2Label.setForeground(Color.WHITE);
        pnlShowDetails.add(room2Label);

        txtRoom = new JTextField();
        txtRoom.setBounds(140, 33, 220, 26);
        pnlShowDetails.add(txtRoom);

        typeRoomLabel = new JLabel("Loại phòng: ");
        typeRoomLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        typeRoomLabel.setBounds(40, 77, 100, 20);
        typeRoomLabel.setForeground(Color.WHITE);
        pnlShowDetails.add(typeRoomLabel);

        txtTypeRoom = new JTextField();
        txtTypeRoom.setBounds(140, 73, 220, 26);
        pnlShowDetails.add(txtTypeRoom);

        locationLabel = new JLabel("Vị trí: ");
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        locationLabel.setBounds(40, 117, 100, 20);
        locationLabel.setForeground(Color.WHITE);
        pnlShowDetails.add(locationLabel);

        txtLocation = new JTextField();
        txtLocation.setBounds(140, 113, 220, 26);
        pnlShowDetails.add(txtLocation);

        nameLabel = new JLabel("Tên KH: ");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLabel.setBounds(40, 157, 100, 20);
        nameLabel.setForeground(Color.WHITE);
        pnlShowDetails.add(nameLabel);

        txtName = new JTextField();
        txtName.setBounds(140, 153, 220, 26);
        pnlShowDetails.add(txtName);

        startLabel = new JLabel("Giờ đặt: ");
        startLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        startLabel.setBounds(40, 197, 100, 20);
        startLabel.setForeground(Color.WHITE);
        pnlShowDetails.add(startLabel);

        txtStart = new JTextField();
        txtStart.setBounds(140, 193, 220, 26);
        pnlShowDetails.add(txtStart);

        receiveLabel = new JLabel("Giờ nhận: ");
        receiveLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        receiveLabel.setBounds(40, 237, 100, 20);
        receiveLabel.setForeground(Color.WHITE);
        pnlShowDetails.add(receiveLabel);

        txtReceive = new JTextField();
        txtReceive.setBounds(140, 233, 220, 26);
        pnlShowDetails.add(txtReceive);

        String[] colsService = {"Tên DV", "Số lượng", "Giá bán",};
        DefaultTableModel modelService = new DefaultTableModel(colsService, 0);

        tblService = new JTable(modelService);
        tblService.setFont(new Font("Arial", Font.BOLD, 14));
        tblService.setBackground(new Color(255, 255, 255, 0));
        tblService.setForeground(new Color(255, 255, 255));
        tblService.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblService.getTableHeader().setForeground(Color.BLUE);

        pnlShowDetails.add(scrService = new JScrollPane(tblService, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        scrService.setBounds(10, 325, 430, 210);
        scrService.setOpaque(false);
        scrService.getViewport().setOpaque(false);
        scrService.getViewport().setBackground(Color.WHITE);

        btnChooseCustomer.addActionListener(this);
        btnBookRoom.addActionListener(this);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);

        ArrayList<Room> roomList = roomDAO.getRoomList();
        LoadRoomList(roomList);

        cboRoomType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomTypeName = cboRoomType.getSelectedItem().toString();
                loadRoomListByRoomTypeName(roomTypeName);
            }
        });

        cboStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String status = cboStatus.getSelectedItem().toString();
                loadRoomListByRoomStatus(status);
            }
        });

    }

    private void loadCboRoomType() {
        java.util.List<TypeOfRoom> dataList = typeOfRoomDAO.getAllLoaiPhong();
        cboRoomType.addItem("Tất cả");
        for (TypeOfRoom roomType : dataList) {
            cboRoomType.addItem(roomType.getTenLoaiPhong());
        }
    }

    /**
     * Load thông tin phòng lên btnRoomList
     *
     * @param roomID1 {@code ArrayList<Room>}: ID phòng
     */
    private void loadRoom(String roomID1) {
        Room room = roomDAO.getRoomByRoomId(roomID1);
        if (room == null) room = new Room();
        String statusP = room.getTinhTrang();
        String roomID = room.getMaPhong();
        String btnName = "<html><p style='text-align: center;'> " + roomID + " </p></br><p style='text-align: center;'> " + statusP + " </p></html>";
        int index = 0;
        for (int i = 0; i < btnRoomList.length; i++) {
            if (btnRoomList[i].getText().contains(roomID)) {
                index = i;
            } else if (btnRoomList[i].getText().equals("")) {
                index = i;
                break;
            }
        }
        btnRoomList[index].setText(btnName);
        btnRoomList[index].setForeground(Color.WHITE);
        btnRoomList[index].setFont(new Font("Dialog", Font.PLAIN, 14));
        btnRoomList[index].setVerticalTextPosition(SwingConstants.BOTTOM);
        btnRoomList[index].setHorizontalTextPosition(SwingConstants.CENTER);
        btnRoomList[index].setPreferredSize(new Dimension(120, 120));
        btnRoomList[index].setIcon(new ImageIcon(getClass().getResource("/images/room.png")));
        btnRoomList[index].setCursor(new Cursor(Cursor.HAND_CURSOR));
        switch (statusP) {
            case "Trống":
                btnRoomList[index].setBackground(Color.decode("#5F009D"));
                btnBookRoom.setEnabled(true);
                btnChooseCustomer.setEnabled(true);
                btnSwitchRoom.setEnabled(false);
                break;
            case "Chờ":
                btnRoomList[index].setBackground(Color.decode("#0D9EA1"));
                btnBookRoom.setEnabled(true);
                btnChooseCustomer.setEnabled(true);
                btnSwitchRoom.setEnabled(false);
                break;
            case "Tạm":
                btnRoomList[index].setBackground(Color.decode("#FAA0AA"));
                btnBookRoom.setEnabled(true);
                btnChooseCustomer.setEnabled(true);
                btnSwitchRoom.setEnabled(false);
                break;
            default:
                btnRoomList[index].setBackground(Color.decode("#008000"));
                btnBookRoom.setEnabled(false);
                btnChooseCustomer.setEnabled(true);
                btnSwitchRoom.setEnabled(true);
                break;
        }
        pnlShowRoom.revalidate();
        pnlShowRoom.repaint();
    }

    /**
     * Hiển thị danh sách phòng được truyền vào
     *
     * @param listRoom {@code ArrayList<Room>}: danh sách phòng cần hiển thị
     */
    private void LoadRoomList(ArrayList<Room> listRoom) {
        pnlShowRoom.removeAll();
        pnlShowRoom.revalidate();
        pnlShowRoom.repaint();
        Border lineRed = new LineBorder(Color.RED, 2);
        Border lineGray = new LineBorder(Color.GRAY, 1);
        int sizeListRoom = listRoom.size();
        btnRoomList = new JButton[sizeListRoom];

        for (int i = 0; i < sizeListRoom; i++) {
            int selection = i;
            String roomID = listRoom.get(i).getMaPhong();
            String location2 = listRoom.get(i).getViTri();
            btnRoomList[selection] = new JButton("");
            loadRoom(roomID);
            btnRoomList[selection].setBorder(lineGray);
            if ((i + 1) % 5 == 0) {
                heightTable += 130;
                pnlShowRoom.setPreferredSize(new Dimension(90, heightTable));
            }
            String roomName = txtRoom.getText();
            if (roomID.equalsIgnoreCase(roomName)) {
                location = i;
            }
            btnRoomList[selection].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (location != -1) {
                        btnRoomList[location].setBorder(lineGray);
                    }

                    String roomTypeName = typeOfRoomDAO.getRoomTypeNameByRoomID(roomID);
                    if (roomTypeName.equals("") || roomTypeName.isEmpty() || roomTypeName == null) {
                        roomTypeName = "Tất cả";
                    }
                    loadCboRoom(roomTypeName);
                    location = selection;
                    btnRoomList[selection].setBorder(lineRed);
                    txtRoom.setText(roomID);
                    txtLocation.setText(location2);
                    Room roomActiveE = roomDAO.getRoomByRoomId(roomID);
                    if (roomActiveE == null)
                        roomActiveE = new Room();
                    txtTypeRoom.setText(roomActiveE.getLoaiPhong().getTenLoaiPhong());

                    if (roomActiveE.getTinhTrang().equalsIgnoreCase("Đang sử dụng")) {
                        ReservationForm reservationForm = reservationFormDAO.getReservationFormByRoomId(roomID);
                        if (reservationForm != null) {
                            txtName.setText(reservationForm.getMaKhachHang().getTenKhachHang());
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                            txtStart.setText(sdf.format(reservationForm.getThoiGianDat()));
                            txtReceive.setText(sdf.format(reservationForm.getThoiGianNhanPhong()));
                        }
                    }
                }
            });

            btnRoomList[selection].addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    btnRoomList[selection].setBackground(Color.decode("#bbdefc"));
                    btnRoomList[selection].setForeground(Color.decode("#1a66e3"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    Room roomActiveE = roomDAO.getRoomByRoomId(roomID);
                    if (roomActiveE == null) roomActiveE = new Room();
                    String status = roomActiveE.getTinhTrang();
                    switch (status) {
                        case "Trống":
                            btnRoomList[selection].setBackground(Color.decode("#5F009D"));
                            break;
                        case "Chờ":
                            btnRoomList[selection].setBackground(Color.decode("#0D9EA1"));
                            break;
                        case "Tạm":
                            btnRoomList[selection].setBackground(Color.decode("#FAA0AA"));
                            break;
                        default:
                            btnRoomList[selection].setBackground(Color.decode("#008000"));
                            break;
                    }
                    btnRoomList[selection].setForeground(Color.WHITE);
                }
            });
            pnlShowRoom.add(btnRoomList[selection]);
        }
        if (location != -1 && location < btnRoomList.length) {
            btnRoomList[location].setBorder(lineRed);
            btnRoomList[location].doClick();
        }
    }

    /**
     * Hiển thị danh sách phòng dựa trên tên loại phòng
     *
     * @param roomTypeName {@code String}: tên loại phòng
     */
    private void loadRoomListByRoomTypeName(String roomTypeName) {
        ArrayList<Room> dataList = new ArrayList<Room>();
        if (roomTypeName.equalsIgnoreCase("Tất cả"))
            dataList = roomDAO.getRoomList();
        else {
            location = -1;
            dataList = roomDAO.getRoomsByType(roomTypeName);
        }
        LoadRoomList(dataList);
    }

    /**
     * Hiển thị danh sách phòng khi biết loại phòng trên comboBox Phòng
     *
     * @param roomTypeName {@code String}: loại tên phòng
     */
    private void loadCboRoom(String roomTypeName) {
        ArrayList<Room> roomList = new ArrayList<Room>();
        if (roomTypeName.equalsIgnoreCase("Tất cả")) {
            roomList = roomDAO.getListAvailableRoom();
        } else {
            roomList = roomDAO.getListAvailableRoomByRoomTypeName(roomTypeName);
        }
    }

    /**
     * Hiển thị danh sách phòng dựa trên trạng thái
     *
     * @param statusRoom {@code String}: trạng thái
     */
    private void loadRoomListByRoomStatus(String statusRoom) {
        ArrayList<Room> dataList = new ArrayList<Room>();
        if (statusRoom.equalsIgnoreCase("Tất cả"))
            dataList = roomDAO.getRoomList();
        else {
            location = -1;
            dataList = roomDAO.getRoomsByStatus(statusRoom);
        }
        LoadRoomList(dataList);
    }

    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        timeLabel.setText(time);
    }

    private String generateID() {
        String formID = reservationFormDAO.generateNextFormId();
        return formID;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnChooseCustomer)) {
            ChooseCustomer c = new ChooseCustomer(main);
            c.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            c.setVisible(true);
        }
        if (o.equals(btnBookRoom)) {
            if (location == -1) {
                JOptionPane.showMessageDialog(this, "Chưa chọn phòng để đặt");
            } else if (txtCustomer.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa chọn khách hàng cho thuê");
            }

            String roomID = txtRoom.getText().trim();
            Room room = roomDAO.getRoomByRoomId(roomID);
            if (room == null) {
                room = new Room();
            }
            if (room.getTinhTrang().equalsIgnoreCase("Đang sử dụng")) {
                JOptionPane.showMessageDialog(this, "Phòng đang có khách. Vui lòng chọn phòng khác");
            } else {
                String customerName = txtCustomer.getText().trim();
                String customerID = customerDAO.getIdByTenKhachHang(customerName);
                Customer c = customerDAO.getKhachHangById(customerID);
                if (c == null) {
                    c = new Customer();
                }
                String formID = generateID();
                long millis = System.currentTimeMillis();
                Timestamp startTime = new Timestamp(millis);
                Timestamp receiveTime = new Timestamp(millis);

                ReservationForm form = new ReservationForm(formID, startTime, receiveTime, staffLogin, c, room);
                boolean resultForm = reservationFormDAO.addReservationForm(form);

                if (resultForm) {
                    JOptionPane.showMessageDialog(this, "Cho thuê phòng thành công");
                   roomDAO.updateRoomStatus(roomID,"Đang sử dụng");
                   loadRoom(roomID);
                   txtCustomer.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Cho thuê phòng thất bại");
                }
            }
        }
    }

    private void bookRoomNow() {

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

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}