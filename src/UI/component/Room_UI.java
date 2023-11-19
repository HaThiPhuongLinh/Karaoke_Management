package UI.component;

import ConnectDB.ConnectDB;
import DAO.RoomDAO;
import DAO.TypeOfRoomDAO;
import Entity.Room;
import Entity.Staff;
import Entity.TypeOfRoom;
import UI.CustomUI.Custom;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Giao diện quản lý phòng
 * Người tham gia thiết kế: Hà Thị Phương Linh, Nguyễn Đình Dương
 * Ngày tạo: 17/09/2023
 * Lần cập nhật cuối: 12/11/2023
 * Nội dung cập nhật: cập nhật giá trị combobox loại phòng
 */
public class Room_UI extends JPanel implements ActionListener, MouseListener {
    public static Staff staffLogin = null;
    private JButton btnAdd, btnDelete, btnFix, btnRefresh;
    private JTextField txtRoomID, txtLocation, txtPrice, txtError;
    private JComboBox<String> cmbTinhTrang;
    private JComboBox<String> cmbLoaiPhong;
    private JTable tblRoom;
    private DefaultTableModel modelTblRoom;
    private DecimalFormat df = new DecimalFormat("#,###.##/giờ");
    private JPanel pnlRoomControl, pnlRoomList, plnTime;
    private JLabel lblBackGround, lblTime;
    private RoomDAO RoomDAO;
    private TypeOfRoomDAO typeOfRoomDAO;

    public Room_UI(Staff staff) {
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);
        RoomDAO = new RoomDAO();
        typeOfRoomDAO = new TypeOfRoomDAO();
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel headerLabel = new JLabel("QUẢN LÝ PHÒNG");
        headerLabel.setBounds(520, 10, 1175, 40);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        add(headerLabel);

        plnTime = new JPanel();
        plnTime.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
                TitledBorder.LEADING, TitledBorder.TOP));
        plnTime.setBounds(12, 10, 300, 50);
        plnTime.setOpaque(false);
        add(plnTime);

        lblTime = new JLabel();
        lblTime.setFont(new Font("Arial", Font.BOLD, 33));
        lblTime.setForeground(Color.WHITE);
        plnTime.add(lblTime);
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();

        pnlRoomList = new JPanel();
        pnlRoomList.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "PHÒNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlRoomList.setBounds(10, 70, 1240, 670);
        pnlRoomList.setOpaque(false);
        add(pnlRoomList);
        pnlRoomList.setLayout(new BorderLayout(0, 0));

        pnlRoomControl = new JPanel();
        pnlRoomControl.setOpaque(false);
        pnlRoomControl.setBackground(Color.WHITE);
        pnlRoomList.add(pnlRoomControl, BorderLayout.NORTH);
        pnlRoomControl.setLayout(null);
        pnlRoomControl.setPreferredSize(new Dimension(1100, 230));

        JPanel panelDSP = new JPanel();
        panelDSP.setLayout(null);
        panelDSP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH PHÒNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSP.setBounds(30, 310, 1220, 370);
        panelDSP.setOpaque(false);

        String[] colsP = {"STT", "Mã Phòng", "Loại Phòng", "Vị Trí", "Tình Trạng", "Giá Phòng"};
        modelTblRoom = new DefaultTableModel(colsP, 0);
        JScrollPane scrollPaneP;

        tblRoom = new JTable(modelTblRoom);
        tblRoom.setFont(new Font("Arial", Font.BOLD, 14));
        tblRoom.setBackground(new Color(255, 255, 255, 0));
        tblRoom.setForeground(new Color(255, 255, 255));
        tblRoom.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblRoom.getTableHeader().setForeground(Color.BLUE);
        tblRoom.addMouseListener(this);

        Custom.getInstance().setCustomTable(tblRoom);

        panelDSP.add(scrollPaneP = new JScrollPane(tblRoom, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneP.setBounds(10, 20, 1210, 380);
        scrollPaneP.setOpaque(false);
        scrollPaneP.getViewport().setOpaque(false);
        scrollPaneP.getViewport().setBackground(Color.WHITE);
        pnlRoomList.add(panelDSP);

        JLabel labelMaPhong = new JLabel("Mã Phòng:");
        labelMaPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        labelMaPhong.setBounds(80, 20, 120, 30);
        labelMaPhong.setForeground(Color.WHITE);
        pnlRoomControl.add(labelMaPhong);

        txtRoomID = new JTextField();
        txtRoomID.setBounds(195, 20, 311, 30);
        txtRoomID.setColumns(10);
        pnlRoomControl.add(txtRoomID);

        JLabel labelViTri = new JLabel("Vị Trí:");
        labelViTri.setFont(new Font("Arial", Font.PLAIN, 14));
        labelViTri.setBounds(80, 70, 120, 30);
        labelViTri.setForeground(Color.WHITE);
        pnlRoomControl.add(labelViTri);

        txtLocation = new JTextField();
        txtLocation.setBounds(195, 70, 311, 30);
        txtLocation.setColumns(10);
        pnlRoomControl.add(txtLocation);

        JLabel labelLoaiPhong = new JLabel("Loại phòng:");
        labelLoaiPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLoaiPhong.setBounds(80, 120, 120, 30);
        labelLoaiPhong.setForeground(Color.WHITE);
        pnlRoomControl.add(labelLoaiPhong);

        cmbLoaiPhong = new JComboBox<String>();
        cmbLoaiPhong.addItem("");
        cmbLoaiPhong.setBounds(195, 120, 311, 30);
        Custom.setCustomComboBox(cmbLoaiPhong);
        pnlRoomControl.add(cmbLoaiPhong);

        JLabel labelTinhTrang = new JLabel("Tình Trạng:");
        labelTinhTrang.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTinhTrang.setBounds(80, 170, 120, 30);
        labelTinhTrang.setForeground(Color.WHITE);
        pnlRoomControl.add(labelTinhTrang);

        cmbTinhTrang = new JComboBox<String>();
        cmbTinhTrang.addItem("Trống");
        cmbTinhTrang.addItem("Chờ");
        cmbTinhTrang.addItem("Đang sử dụng");
        cmbTinhTrang.setBounds(195, 170, 311, 30);
        Custom.setCustomComboBox(cmbTinhTrang);
        pnlRoomControl.add(cmbTinhTrang);

        JLabel labelGiaP = new JLabel("Giá Phòng:");
        labelGiaP.setFont(new Font("Arial", Font.PLAIN, 14));
        labelGiaP.setBounds(600, 20, 150, 30);
        labelGiaP.setForeground(Color.WHITE);
        pnlRoomControl.add(labelGiaP);

        txtPrice = new JTextField();
        txtPrice.setBounds(715, 20, 311, 30);
        txtPrice.setColumns(6);
        pnlRoomControl.add(txtPrice);

        txtError = new JTextField();
        txtError.setFont(new Font("Arial", Font.BOLD, 13));
        txtError.setForeground(Color.RED);
        txtError.setBounds(600, 120, 426, 30);
        txtError.setColumns(6);
        pnlRoomControl.add(txtError);

        btnAdd = new JButton("Thêm");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnAdd);
        btnAdd.setBounds(600, 170, 100, 30);
        pnlRoomControl.add(btnAdd);

        btnDelete = new JButton("Xóa");
        btnDelete.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnDelete);
        btnDelete.setBounds(740, 170, 100, 30);
        pnlRoomControl.add(btnDelete);

        btnFix = new JButton("Sửa");
        btnFix.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnFix);
        btnFix.setBounds(880, 170, 100, 30);
        pnlRoomControl.add(btnFix);

        btnRefresh = new JButton("Làm mới");
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnRefresh);
        btnRefresh.setBounds(1020, 170, 100, 30);
        pnlRoomControl.add(btnRefresh);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        lblBackGround = new JLabel(backgroundImage);
        lblBackGround.setBounds(0, 0, getWidth(), getHeight());
        add(lblBackGround);
        btnFix.addActionListener(this);
        btnRefresh.addActionListener(this);
        btnAdd.addActionListener(this);
        btnDelete.addActionListener(this);
        ArrayList<Room> roomList = RoomDAO.getRoomList();

        loadP();
        loadCboLoaiPhong();
    }

    /**
     * Cập nhật thời gian thực cho lblTime
     */
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        lblTime.setText(time);
    }

    /**
     * Load danh sách phòng lên bảng
     */
    public void loadP() {
        int i = 1;
        for (Room room : RoomDAO.getRoomList()) {
            if (room.getTinhTrang().equals("Trong")) {
                RoomDAO.updateRoomStatus(room.getMaPhong(), "Trống");
            }
            if (room.getTinhTrang().equals("Cho")) {
                RoomDAO.updateRoomStatus(room.getMaPhong(), "Chờ");
            }
            Object[] rowData = {i, room.getMaPhong(), room.getLoaiPhong().getTenLoaiPhong(), room.getViTri(), room.getTinhTrang(), df.format(room.getGiaPhong())};
            modelTblRoom.addRow(rowData);
            i++;
        }
    }

    /**
     * Load danh sách loại phòng lên cboLoaiPhong
     */
    private void loadCboLoaiPhong() {
        java.util.List<TypeOfRoom> dataList = typeOfRoomDAO.getAllLoaiPhong();
        for (TypeOfRoom typeOfRoom : dataList) {
            cmbLoaiPhong.addItem(typeOfRoom.getTenLoaiPhong());
        }
    }

    /**
     * Phát sinh mã phòng tự động
     *
     * @return String
     */
    public String laymaP() {
        String MaP = RoomDAO.generateNextRoomId();
        return MaP;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnAdd)) {
            if (txtLocation.getText().equals("") || txtPrice.getText().equals("") || cmbLoaiPhong.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin phòng");
            } else if (validData()) {
                String ma = laymaP();
                String tenLDV = cmbLoaiPhong.getSelectedItem().toString().trim();
                ArrayList<TypeOfRoom> typeOfRooms = typeOfRoomDAO.getTypeOfRoomByName(tenLDV);
                String s = "";
                for (TypeOfRoom typeOfRoom : typeOfRooms) {
                    s += typeOfRoom.getMaLoaiPhong();
                }
                String vitri = txtLocation.getText().trim();
                String tinhTrang = cmbTinhTrang.getSelectedItem().toString();
                int giaBan = Integer.parseInt(txtPrice.getText().trim());
                Room room = new Room(ma, new TypeOfRoom(s), tinhTrang, vitri, giaBan);
                try {
                    if (RoomDAO.insert(room)) {
                        modelTblRoom.getDataVector().removeAllElements();
                        loadP();
                        JOptionPane.showMessageDialog(this, "Thêm phòng thành công");
                        reFresh();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (o.equals(btnRefresh)) {
            reFresh();
            modelTblRoom.getDataVector().removeAllElements();
            loadP();
        } else if (o.equals(btnDelete)) {
            int row = tblRoom.getSelectedRow();
            try {
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng cần xóa");
                } else {
                    String tenLP = cmbLoaiPhong.getSelectedItem().toString().trim();
                    ArrayList<TypeOfRoom> typeOfRooms = typeOfRoomDAO.getTypeOfRoomByName(tenLP);
                    String s = "";
                    for (TypeOfRoom typeOfRoom : typeOfRooms) {
                        s += typeOfRoom.getMaLoaiPhong();
                    }
                    String map = txtRoomID.getText().trim();
                    String vitri = txtLocation.getText().trim();
                    double gia = Double.parseDouble(txtPrice.getText().toString());
                    String tinhtrang = cmbTinhTrang.getSelectedItem().toString().trim();
                    if(tinhtrang.equalsIgnoreCase("Đang sử dụng") || tinhtrang.equalsIgnoreCase("Chờ") ){
                        JOptionPane.showMessageDialog(this, "Phòng đang được sử dụng. Không được phép xóa");
                    } else {

                        Room room = new Room(map, new TypeOfRoom(s), tinhtrang, vitri, gia);

                        int ans = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Cảnh báo",
                                JOptionPane.YES_NO_OPTION);
                        if (ans == JOptionPane.YES_OPTION) {
                            RoomDAO.delete(room);
                            modelTblRoom.removeRow(row);
                            modelTblRoom.getDataVector().removeAllElements();
                            loadP();
                            JOptionPane.showMessageDialog(this, "Xóa thành công");
                            reFresh();
                        }
                    }
                }
            } catch (Exception e3) {
                JOptionPane.showMessageDialog(this, "Xóa không thành công");
            }
        } else if (o.equals(btnFix)) {
            int row = tblRoom.getSelectedRow();
            if (row > 0) {
                if (validData()) {
                    String tenLP = cmbLoaiPhong.getSelectedItem().toString().trim();
                    ArrayList<TypeOfRoom> typeOfRooms = typeOfRoomDAO.getTypeOfRoomByName(tenLP);
                    String s = "";
                    for (TypeOfRoom typeOfRoom : typeOfRooms) {
                        s += typeOfRoom.getMaLoaiPhong();
                    }
                    String map = txtRoomID.getText().trim();
                    String vitri = txtLocation.getText().trim();
                    double gia = Double.parseDouble(txtPrice.getText().toString());
                    String tinhtrang = cmbTinhTrang.getSelectedItem().toString().trim();
                    Room room = new Room(map, new TypeOfRoom(s), tinhtrang, vitri, gia);

                    boolean result = RoomDAO.update(room);
                    if (result == true) {
                        String priceStr = df.format(room.getGiaPhong());
                        tblRoom.setValueAt(cmbLoaiPhong.getSelectedItem().toString(), row, 2);
                        tblRoom.setValueAt(txtLocation.getText(), row, 3);
                        tblRoom.setValueAt(cmbTinhTrang.getSelectedItem().toString(), row, 4);
                        tblRoom.setValueAt(priceStr, row, 5);
                        JOptionPane.showMessageDialog(this, "Sửa thành công");
                        reFresh();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng cần sửa");
            }
        }
    }

    /**
     * Xóa trắng các textfield và set index combobox về 0
     */
    private void reFresh() {
        txtRoomID.setText("");
        cmbLoaiPhong.setSelectedIndex(0);
        txtPrice.setText("");
        txtLocation.setText("");
        cmbTinhTrang.setSelectedIndex(0);
        txtError.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblRoom.getSelectedRow();
        txtRoomID.setText(modelTblRoom.getValueAt(row, 1).toString());
        cmbLoaiPhong.setSelectedItem(modelTblRoom.getValueAt(row, 2).toString());
        cmbTinhTrang.setSelectedItem(modelTblRoom.getValueAt(row, 4).toString());
        txtPrice.setText(modelTblRoom.getValueAt(row, 5).toString().trim().replace(",", "").replace("/giờ", ""));
        txtLocation.setText(modelTblRoom.getValueAt(row, 3).toString());
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

    /**
     * Hiển thị lỗi
     *
     * @param txt:     JTextField lỗi
     * @param message: thông báo lỗi
     */
    private void showMessage(JTextField txt, String message) {
        txt.requestFocus();
        txtError.setText(message);
    }

    /**
     * Kiểm tra dữ liệu hợp lệ
     *
     * @return (@ Code boolean): true/false
     */
    private boolean validData() {
        String vitri = txtLocation.getText().trim();
        String giaBan = txtPrice.getText().trim();
        if (!((vitri.length()) > 0 && vitri.matches("^[A-Za-zÀ-ỹ0-9 ]+"))) {
            showMessage(txtLocation, "Vị trí không được chứa kí tự đặc biệt");
            return false;
        }
        if (!((giaBan.length()) > 0 && giaBan.matches("^[1-9]\\d*"))) {
            showMessage(txtPrice, "Giá phải lớn hơn 0");
            return false;
        }
        return true;
    }
}
