package UI.component;

import ConnectDB.ConnectDB;
import DAO.*;
import Entity.*;
import UI.CustomUI.Custom;
import UI.component.Dialog.ChooseCustomer;
import UI.component.Dialog.PresetRoom;
import UI.component.Dialog.ReservationFormList;
import UI.component.Dialog.SwitchRoom;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Giao diện quản lý đặt phòng
 * Người tham gia thiết kế: Hà Thị Phương Linh
 * Ngày tạo: 09/10/2023
 * Lần cập nhật cuối: 12/11/2023
 * Nội dung cập nhật: sửa định dạng VND
 */
public class KaraokeBooking_UI extends JPanel implements ActionListener, MouseListener {
    public static JTextField txtCustomer;
    public static Staff staffLogin = null;
    private static KaraokeBooking_UI instance = new KaraokeBooking_UI(staffLogin);
    public JTextField txtRoom;
    KaraokeBooking_UI main = this;
    private DefaultTableModel modelTblService;
    private JPanel pnlShowRoom, pnlRoomList, pnlTimeNow, pnlRoomControl, pnlShowCustomer, pnlShowDetails;
    private JLabel lblBackGround, lblTime, lblRoom, lblStatus, lblCustomer, lblRoom2, lblTypeRoom, lblLocation, lblName, lblStart;
    private JTextField txtLocation, txtName, txtStart, txtTypeRoom;
    private JScrollPane scrShowRoom, scrService;
    private JButton btnSwitchRoom, btnBookRoom, btnPresetRoom, btnChooseCustomer, btnForm;
    private JButton[] btnRoomList;
    private int heightTable = 140;
    private int location = -1;
    private JTable tblService;
    private TypeOfRoomDAO typeOfRoomDAO;
    private RoomDAO roomDAO;
    private JComboBox<String> cmbRoomType, cmbStatus;
    private ReservationFormDAO reservationFormDAO;
    private CustomerDAO customerDAO;
    private BillDAO billDAO;
    private DetailOfServiceDAO detailOfServiceDAO = new DetailOfServiceDAO();
    private ArrayList<Service> serviceOrderList = new ArrayList<Service>();
    private int selectedServiceOrderIndex = -1;
    private DecimalFormat df = new DecimalFormat("#,###.## VND");

    public KaraokeBooking_UI(Staff staff) {
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        typeOfRoomDAO = new TypeOfRoomDAO();
        customerDAO = new CustomerDAO();
        reservationFormDAO = new ReservationFormDAO();
        roomDAO = new RoomDAO();
        billDAO = new BillDAO();

        pnlTimeNow = new JPanel();
        pnlTimeNow.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP));
        pnlTimeNow.setBounds(12, 10, 300, 50);
        pnlTimeNow.setOpaque(false);
        add(pnlTimeNow);

        lblTime = new JLabel();
        lblTime.setFont(new Font("Arial", Font.BOLD, 33));
        lblTime.setForeground(Color.WHITE);
        pnlTimeNow.add(lblTime);

        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();

        pnlRoomList = new JPanel();
        pnlRoomList.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Phòng", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlRoomList.setBounds(10, 150, 780, 590);
        pnlRoomList.setOpaque(false);
        add(pnlRoomList);
        pnlRoomList.setLayout(new BorderLayout(0, 0));

        pnlRoomControl = new JPanel();
        pnlRoomControl.setOpaque(false);
        pnlRoomControl.setBackground(Color.WHITE);
        pnlRoomList.add(pnlRoomControl, BorderLayout.NORTH);
        pnlRoomControl.setLayout(null);
        pnlRoomControl.setPreferredSize(new Dimension(330, 55));

        lblRoom = new JLabel("Loại phòng: ");
        lblRoom.setFont(new Font("Arial", Font.PLAIN, 14));
        lblRoom.setBounds(20, 17, 85, 20);
        lblRoom.setForeground(Color.WHITE);
        pnlRoomControl.add(lblRoom);

        cmbRoomType = new JComboBox<String>();
        loadCboRoomType();
        cmbRoomType.setBounds(106, 14, 140, 28);
        Custom.setCustomComboBox(cmbRoomType);
        pnlRoomControl.add(cmbRoomType);

        btnSwitchRoom = new JButton("Đổi phòng");
        btnSwitchRoom.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnSwitchRoom);
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/transfer_16.png"));
        btnSwitchRoom.setIcon(icon);
        btnSwitchRoom.setBounds(260, 12, 135, 30);
        pnlRoomControl.add(btnSwitchRoom);

        lblStatus = new JLabel("Trạng thái: ");
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 14));
        lblStatus.setBounds(420, 17, 85, 20);
        lblStatus.setForeground(Color.WHITE);
        pnlRoomControl.add(lblStatus);

        cmbStatus = new JComboBox<String>();
        cmbStatus.addItem("Tất cả");
        cmbStatus.addItem("Trống");
        cmbStatus.addItem("Đang sử dụng");
        cmbStatus.addItem("Chờ");
        cmbStatus.setBounds(500, 14, 140, 28);
        Custom.setCustomComboBox(cmbStatus);
        pnlRoomControl.add(cmbStatus);

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
        pnlShowCustomer.setBounds(330, 10, 915, 130);
        pnlShowCustomer.setOpaque(false);
        pnlShowCustomer.setLayout(null);
        add(pnlShowCustomer);

        lblCustomer = new JLabel("Khách hàng: ");
        lblCustomer.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCustomer.setBounds(20, 30, 85, 20);
        lblCustomer.setForeground(Color.WHITE);
        pnlShowCustomer.add(lblCustomer);

        txtCustomer = new JTextField();
        txtCustomer.setBounds(120, 27, 220, 30);
        txtCustomer.setFont(new Font("Dialog", Font.PLAIN, 14));
        pnlShowCustomer.add(txtCustomer);

        btnChooseCustomer = new JButton();
        btnChooseCustomer.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnChooseCustomer);
        btnChooseCustomer.setIcon(new ImageIcon(getClass().getResource("/images//Icon/icon_menu/blueAdd_16.png")));
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
        btnBookRoom.setBounds(500, 30, 155, 30);
        pnlShowCustomer.add(btnBookRoom);

        btnPresetRoom = new JButton("Đặt phòng chờ");
        btnPresetRoom.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnPresetRoom);
        btnPresetRoom.setBounds(500, 75, 155, 30);
        pnlShowCustomer.add(btnPresetRoom);

        pnlShowDetails = new JPanel();
        pnlShowDetails.setLayout(null);
        pnlShowDetails.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Chi tiết", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlShowDetails.setBounds(800, 150, 450, 590);
        pnlShowDetails.setOpaque(false);
        add(pnlShowDetails);

        lblRoom2 = new JLabel("Mã phòng: ");
        lblRoom2.setFont(new Font("Arial", Font.PLAIN, 14));
        lblRoom2.setBounds(40, 37, 100, 20);
        lblRoom2.setForeground(Color.WHITE);
        pnlShowDetails.add(lblRoom2);

        txtRoom = new JTextField();
        txtRoom.setBounds(160, 33, 220, 26);
        pnlShowDetails.add(txtRoom);

        lblTypeRoom = new JLabel("Loại phòng: ");
        lblTypeRoom.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTypeRoom.setBounds(40, 87, 100, 20);
        lblTypeRoom.setForeground(Color.WHITE);
        pnlShowDetails.add(lblTypeRoom);

        txtTypeRoom = new JTextField();
        txtTypeRoom.setBounds(160, 83, 220, 26);
        pnlShowDetails.add(txtTypeRoom);

        lblLocation = new JLabel("Vị trí: ");
        lblLocation.setFont(new Font("Arial", Font.PLAIN, 14));
        lblLocation.setBounds(40, 137, 100, 20);
        lblLocation.setForeground(Color.WHITE);
        pnlShowDetails.add(lblLocation);

        txtLocation = new JTextField();
        txtLocation.setBounds(160, 133, 220, 26);
        pnlShowDetails.add(txtLocation);

        lblName = new JLabel("Tên khách hàng: ");
        lblName.setFont(new Font("Arial", Font.PLAIN, 14));
        lblName.setBounds(40, 187, 130, 20);
        lblName.setForeground(Color.WHITE);
        pnlShowDetails.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(160, 183, 220, 26);
        pnlShowDetails.add(txtName);

        lblStart = new JLabel("Giờ vào: ");
        lblStart.setFont(new Font("Arial", Font.PLAIN, 14));
        lblStart.setBounds(40, 237, 100, 20);
        lblStart.setForeground(Color.WHITE);
        pnlShowDetails.add(lblStart);

        txtStart = new JTextField();
        txtStart.setBounds(160, 233, 220, 26);
        pnlShowDetails.add(txtStart);

        String[] colsService = {"Tên DV", "Số lượng", "Giá bán",};
        modelTblService = new DefaultTableModel(colsService, 0);

        tblService = new JTable(modelTblService);
        Custom.setCustomTable(tblService);

        pnlShowDetails.add(scrService = new JScrollPane(tblService, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        scrService.setBounds(10, 325, 430, 260);
        scrService.setOpaque(false);
        scrService.getViewport().setOpaque(false);
        scrService.getViewport().setBackground(Color.WHITE);

        btnChooseCustomer.addActionListener(this);
        btnBookRoom.addActionListener(this);
        btnPresetRoom.addActionListener(this);
        btnForm.addActionListener(this);
        btnSwitchRoom.addActionListener(this);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        lblBackGround = new JLabel(backgroundImage);
        lblBackGround.setBounds(0, 0, getWidth(), getHeight());
        add(lblBackGround);

        ArrayList<Room> roomList = roomDAO.getRoomList();
        for (Room room : roomList) {
            if (room.getTinhTrang().equals("Trong")) {
                roomDAO.updateRoomStatus(room.getMaPhong(), "Trống");
            }
            if (room.getTinhTrang().equals("Cho")) {
                roomDAO.updateRoomStatus(room.getMaPhong(), "Chờ");
            }
        }
        LoadRoomList(roomList);
        reSizeColumnTableService();

        cmbRoomType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomTypeName = cmbRoomType.getSelectedItem().toString();
                loadRoomListByRoomTypeName(roomTypeName);
            }
        });

        cmbStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String status = cmbStatus.getSelectedItem().toString();
                loadRoomListByRoomStatus(status);
            }
        });

    }

    /**
     * Tạo thể hiện hiện tại cho class KaraokeBooking_UI
     */
    public static KaraokeBooking_UI getInstance() {
        if (instance == null)
            instance = new KaraokeBooking_UI(staffLogin);
        return instance;
    }

    /**
     * Chỉnh size của bảng tblService
     */
    private void reSizeColumnTableService() {
        TableColumnModel tcm = tblService.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(140);
        tcm.getColumn(1).setPreferredWidth(50);
        tcm.getColumn(2).setPreferredWidth(90);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        tcm.getColumn(0).setCellRenderer(leftRenderer);
        tcm.getColumn(1).setCellRenderer(rightRenderer);
        tcm.getColumn(2).setCellRenderer(rightRenderer);
    }

    /**
     * Tải dữ liệu tất cả loại phòng lên cboRoomType
     */
    private void loadCboRoomType() {
        java.util.List<TypeOfRoom> dataList = typeOfRoomDAO.getAllLoaiPhong();
        cmbRoomType.addItem("Tất cả");
        for (TypeOfRoom roomType : dataList) {
            cmbRoomType.addItem(roomType.getTenLoaiPhong());
        }
    }

    /**
     * Tải thông tin phòng lên btnRoomList bao gồm mã phòng và trạng thái phòng
     *
     * @param roomID1 {@code ArrayList<Room>}: mã phòng
     */
    public void loadRoom(String roomID1) {
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
        btnRoomList[index].setPreferredSize(new Dimension(140, 140));
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
     * Hiển thị danh sách phòng được truyền vào lên giao diện thông qua btnRoomList
     *
     * @param listRoom {@code ArrayList<Room>}: danh sách phòng cần hiển thị
     */
    public void LoadRoomList(ArrayList<Room> listRoom) {
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
                    showBillInfo(roomID);

                    Room roomActiveE = roomDAO.getRoomByRoomId(roomID);
                    if (roomActiveE == null)
                        roomActiveE = new Room();
                    txtTypeRoom.setText(roomActiveE.getLoaiPhong().getTenLoaiPhong());

                    if (roomActiveE.getTinhTrang().equalsIgnoreCase("Đang sử dụng")) {
                        btnSwitchRoom.setEnabled(true);
                        Bill bill = billDAO.getBillByRoomID(roomID);
                        if (bill != null) {
                            txtName.setText(bill.getMaKH().getTenKhachHang());
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                            txtStart.setText(sdf.format(bill.getThoiGianVao()));
                        }
                    }
                    if (roomActiveE.getTinhTrang().equalsIgnoreCase("Trống")) {
                        btnSwitchRoom.setEnabled(false);
                        txtName.setText("");
                        txtStart.setText("");
                    }
                    if (roomActiveE.getTinhTrang().equalsIgnoreCase("Chờ")) {
                        btnSwitchRoom.setEnabled(false);
                        ReservationForm reservationForm = reservationFormDAO.getReservationFormByRoomId(roomID);
                        if (reservationForm != null) {
                            txtName.setText(reservationForm.getMaKhachHang().getTenKhachHang());
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            txtStart.setText(sdf.format(reservationForm.getThoiGianDat()));
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
     * Hiển thị danh sách phòng dựa trên tên loại phòng trong cbo roomTypeName
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
     * Hiển thị danh sách phòng khi biết loại phòng trên comboBox roomTypeName
     *
     * @param roomTypeName {@code String}: tên loại phòng
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
     * @param statusRoom {@code String}: trạng thái phòng
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

    /**
     * Gán thời gian hiện tại cho label lblTime
     */
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        lblTime.setText(time);
    }

    /**
     * Sinh mã hóa đơn tự động
     */
    private String generateBillID() {
        String billID = billDAO.generateNextBillId();
        return billID;
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
            SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm:ss dd/MM/yyyy");
            if (location == -1) {
                JOptionPane.showMessageDialog(this, "Chưa chọn phòng để đặt");
            } else if (txtCustomer.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa chọn khách hàng cho thuê");
            } else {
                String roomID = txtRoom.getText().trim();
                Room room = roomDAO.getRoomByRoomId(roomID);
                if (room == null) {
                    room = new Room();
                }
                if (room.getTinhTrang().equalsIgnoreCase("Đang sử dụng")) {
                    JOptionPane.showMessageDialog(this, "Phòng đang sử dụng. Không được đặt.");
                } else {

                    // Lấy danh sách các phiếu đặt phòng cho phòng hiện tại và sắp xếp chúng theo thời gian đặt
                    ArrayList<ReservationForm> reservations = reservationFormDAO.getReservationsByRoomID(roomID);
                    reservations.sort(Comparator.comparing(ReservationForm::getThoiGianDat));

                    if (!reservations.isEmpty()) {
                        // Lấy phiếu đặt sớm nhất
                        ReservationForm earliestReservation = reservations.get(0);

                        long currentTimeMillis = System.currentTimeMillis();
                        long reservationTimeMillis = earliestReservation.getThoiGianDat().getTime();
                        long timeDifferenceHours = (reservationTimeMillis - currentTimeMillis) / (60 * 60 * 1000);

                        int choice = JOptionPane.showConfirmDialog(
                                this,
                                "Phòng đã có phiếu đặt vào lúc " + sdf.format(earliestReservation.getThoiGianDat()) + ". Bạn có muốn sử dụng ngay không?",
                                "Xác nhận",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (choice == JOptionPane.YES_OPTION) {
                            String customerName = txtCustomer.getText().trim();
                            String customerID = customerDAO.getIdByTenKhachHang(customerName);
                            Customer c = customerDAO.getKhachHangById(customerID);
                            if (c == null) {
                                c = new Customer();
                            }
                            Timestamp startTime = new Timestamp(currentTimeMillis);
                            Timestamp receiveTime = null;
                            int tinhTrang = 0;
                            String khuyenMai = "";

                            String billID = generateBillID();
                            Bill bill = new Bill(billID, staffLogin, c, room, startTime, receiveTime, tinhTrang, khuyenMai);
                            boolean resultBill = billDAO.addBill(bill);

                            if (resultBill) {
                                long thirtyMinutesMillis = 30 * 60 * 1000; // 30 phút trong mili giây
                                long timeToReturnMillis = reservationTimeMillis - thirtyMinutesMillis;

                                Timestamp timeToReturn = new Timestamp(timeToReturnMillis);
                                JOptionPane.showMessageDialog(
                                        this,
                                        "Cho thuê phòng thành công. Vui lòng trả phòng trước " + sdf.format(timeToReturn),
                                        "Thông báo",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                                roomDAO.updateRoomStatus(roomID, "Đang sử dụng");
                                ArrayList<Room> roomList = roomDAO.getRoomList();
                                LoadRoomList(roomList);
                                txtCustomer.setText("");
                            } else {
                                JOptionPane.showMessageDialog(this, "Cho thuê phòng thất bại");
                            }
                        }
                    } else {
                        // Không có phiếu đặt nào, cho phép đặt phòng
                        String customerName = txtCustomer.getText().trim();
                        String customerID = customerDAO.getIdByTenKhachHang(customerName);
                        Customer c = customerDAO.getKhachHangById(customerID);
                        if (c == null) {
                            c = new Customer();
                        }
                        long millis = System.currentTimeMillis();
                        Timestamp startTime = new Timestamp(millis);
                        Timestamp receiveTime = null;
                        int tinhTrang = 0;
                        String khuyenMai = "";

                        String billID = generateBillID();
                        Bill bill = new Bill(billID, staffLogin, c, room, startTime, receiveTime, tinhTrang, khuyenMai);
                        boolean resultBill = billDAO.addBill(bill);

                        if (resultBill) {
                            JOptionPane.showMessageDialog(this, "Cho thuê phòng thành công");
                            roomDAO.updateRoomStatus(roomID, "Đang sử dụng");
                            ArrayList<Room> roomList = roomDAO.getRoomList();
                            LoadRoomList(roomList);
                            txtCustomer.setText("");
                        } else {
                            JOptionPane.showMessageDialog(this, "Cho thuê phòng thất bại");
                        }
                    }
                }
            }
        }

        if (o.equals(btnPresetRoom)) {
            if (location == -1) {
                JOptionPane.showMessageDialog(this, "Chưa chọn phòng để đặt");
            } else {
                String roomID = txtRoom.getText().trim();
                Room room = roomDAO.getRoomByRoomId(roomID);
                if (room == null) {
                    room = new Room();
                }
                PresetRoom presetRoom = PresetRoom.getInstance();
                presetRoom.setKaraokeBookingUI(this);
                presetRoom.setVisible(true);
                presetRoom.setRoomID(roomID);
                ArrayList<Room> roomList = roomDAO.getRoomList();
                KaraokeBooking_UI.getInstance().LoadRoomList(roomList);
            }
        }
        if (o.equals(btnForm)) {
            ReservationFormList reservationFormList = new ReservationFormList();
            reservationFormList.setKaraokeBookingUI(this);
            reservationFormList.setVisible(true);
            ArrayList<Room> roomList = roomDAO.getRoomList();
            KaraokeBooking_UI.getInstance().LoadRoomList(roomList);
        }
        if (o.equals(btnSwitchRoom)) {
            SwitchRoom switchRoom = new SwitchRoom();
            switchRoom.setKaraokeBookingUI(this);
            switchRoom.setVisible(true);
        }
    }

    /**
     * Hiển thị thông tin chi tiết dịch vụ của phòng khi click vào phòng đang sử dụng
     *
     * @param maPhong: mã phòng
     */
    private void showBillInfo(String maPhong) {
        ArrayList<DetailsOfService> dataList = detailOfServiceDAO.getDetailsOfServiceListByRoomId(maPhong);
        int i = 1;
        modelTblService.getDataVector().removeAllElements();
        modelTblService.fireTableDataChanged();
        serviceOrderList.clear();
        for (DetailsOfService item : dataList) {
            Service service = item.getMaDichVu();
            serviceOrderList.add(service);
            // hiển thị lại phòng đã chọn lúc đầu
            if (selectedServiceOrderIndex <= -1) {
                if (selectedServiceOrderIndex == i) {
                    tblService.getSelectionModel().addSelectionInterval(i - 1, i - 1);
                }
            }
            String priceStr = df.format(item.getGiaBan());
            String quantityStr = String.valueOf(item.getSoLuong());
            modelTblService.addRow(new Object[]{service.getTenDichVu(),
                    quantityStr, priceStr});
        }
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