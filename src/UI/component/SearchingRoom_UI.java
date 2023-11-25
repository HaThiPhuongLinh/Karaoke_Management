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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Giao diện tìm kiếm phòng
 * Người tham gia thiết kế: Hà Thị Phương Linh, Nguyễn Đình Dương
 * Ngày tạo: 12/10/2023
 * Lần cập nhật cuối: 12/11/2023
 * Nội dung cập nhật: cập nhật định dạng combobox
 */
public class SearchingRoom_UI extends JPanel implements ActionListener, MouseListener {
    public static Staff staffLogin = null;
    private JTable tblRoom;
    private DefaultTableModel modelTblRoom;
    private JLabel lblBackGround, lblTime, lblSearchType, lblSearchPrice;
    private JComboBox cmbFindName, cmbFindPrice;
    private JPanel pnlTime, pnlCusList, pnlCusControl;
    private JButton btnRefresh;
    private RoomDAO RoomDAO;
    private DecimalFormat df = new DecimalFormat("#,###.##/giờ");

    public SearchingRoom_UI(Staff staff) {
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);
        RoomDAO = new RoomDAO();
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel headerLabel = new JLabel("TÌM KIẾM PHÒNG");
        headerLabel.setBounds(520, 10, 1175, 40);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        Component add = add(headerLabel);

        pnlTime = new JPanel();
        pnlTime.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
                TitledBorder.LEADING, TitledBorder.TOP));
        pnlTime.setBounds(12, 10, 300, 50);
        pnlTime.setOpaque(false);
        add(pnlTime);

        lblTime = new JLabel();
        lblTime.setFont(new Font("Arial", Font.BOLD, 33));
        lblTime.setForeground(Color.WHITE);
        pnlTime.add(lblTime);
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();

        pnlCusList = new JPanel();
        pnlCusList.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "PHÒNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlCusList.setBounds(10, 70, 1240, 670);
        pnlCusList.setOpaque(false);
        add(pnlCusList);
        pnlCusList.setLayout(new BorderLayout(0, 0));

        pnlCusControl = new JPanel();
        pnlCusControl.setOpaque(false);
        pnlCusControl.setBackground(Color.WHITE);
        pnlCusList.add(pnlCusControl, BorderLayout.NORTH);
        pnlCusControl.setLayout(null);
        pnlCusControl.setPreferredSize(new Dimension(1100, 100));

        lblSearchType = new JLabel("Tìm Theo Tên Loại Phòng: ");
        lblSearchType.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSearchType.setBounds(80, 25, 200, 30);
        lblSearchType.setForeground(Color.WHITE);
        pnlCusControl.add(lblSearchType);

        cmbFindName = new JComboBox();
        cmbFindName.setBounds(265, 25, 280, 30);
        cmbFindName.addItem("Tất Cả");
        Custom.setCustomComboBox(cmbFindName);
        pnlCusControl.add(cmbFindName);

        btnRefresh = new JButton("Làm mới");
        btnRefresh.setBounds(1030, 25, 100, 30);
        Custom.setCustomBtn(btnRefresh);
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 14));
        pnlCusControl.add(btnRefresh);

        lblSearchPrice = new JLabel("Tìm Theo Giá: ");
        lblSearchPrice.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSearchPrice.setBounds(590, 25, 120, 30);
        lblSearchPrice.setForeground(Color.WHITE);
        pnlCusControl.add(lblSearchPrice);

        cmbFindPrice = new JComboBox();
        cmbFindPrice.addItem("Tất cả");
        cmbFindPrice.addItem("150.000 - 200.000");
        cmbFindPrice.addItem("200.000 - 300.000");
        cmbFindPrice.addItem("300.000 - 400.000");
        cmbFindPrice.setBounds(695, 25, 280, 30);
        Custom.setCustomComboBox(cmbFindPrice);
        pnlCusControl.add(cmbFindPrice);

        JPanel panelDSKH = new JPanel();
        panelDSKH.setLayout(null);
        panelDSKH.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH PHÒNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSKH.setBounds(30, 20, 1220, 520);
        panelDSKH.setOpaque(false);

        String[] colsKH = {"STT", "Mã Phòng", "Loại Phòng", "Vị Trí", "Tình Trạng", "Giá Tiền"};
        modelTblRoom = new DefaultTableModel(colsKH, 0);
        JScrollPane scrollPaneP;

        tblRoom = new JTable(modelTblRoom);
        tblRoom.setFont(new Font("Arial", Font.BOLD, 14));
        tblRoom.setBackground(new Color(255, 255, 255, 0));
        tblRoom.setForeground(new Color(255, 255, 255));
        tblRoom.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblRoom.getTableHeader().setForeground(Color.BLUE);
        Custom.getInstance().setCustomTable(tblRoom);

        panelDSKH.add(scrollPaneP = new JScrollPane(tblRoom, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneP.setBounds(10, 20, 1210, 520);
        scrollPaneP.setOpaque(false);
        scrollPaneP.getViewport().setOpaque(false);
        scrollPaneP.getViewport().setBackground(Color.WHITE);
        pnlCusList.add(panelDSKH);
        loadP();
        loadCboLoaiPhong();
        btnRefresh.addActionListener(this);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        lblBackGround = new JLabel(backgroundImage);
        lblBackGround.setBounds(0, 0, getWidth(), getHeight());
        add(lblBackGround);

        cmbFindName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoaiPhong = (String) cmbFindName.getSelectedItem();
                ;
                cmbFindPrice.setSelectedIndex(0);
                modelTblRoom.setRowCount(0);
                int i = 1;
                for (Room room : RoomDAO.getRoomList()) {
                    if (selectedLoaiPhong.equalsIgnoreCase("Tất cả") ||
                            selectedLoaiPhong.equalsIgnoreCase(room.getLoaiPhong().getTenLoaiPhong())) {
                        Object[] rowData = {i, room.getMaPhong(), room.getLoaiPhong().getTenLoaiPhong(), room.getViTri(), room.getTinhTrang(), df.format(room.getGiaPhong())};
                        modelTblRoom.addRow(rowData);
                        i++;
                    }
                }
            }
        });

        cmbFindPrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelTblRoom.setRowCount(0);
                int i = 1;
                for (Room room : RoomDAO.getRoomList()) {
                    if (cmbFindPrice.getSelectedIndex() == 0) {
                        Object[] rowData = {i, room.getMaPhong(), room.getLoaiPhong().getTenLoaiPhong(), room.getViTri(), room.getTinhTrang(), df.format(room.getGiaPhong())};
                        modelTblRoom.addRow(rowData);
                        i++;
                    } else if (cmbFindPrice.getSelectedIndex() == 1) {
                        if (room.getGiaPhong() >= 150000 && room.getGiaPhong() <= 200000) {
                            Object[] rowData = {i, room.getMaPhong(), room.getLoaiPhong().getTenLoaiPhong(), room.getViTri(), room.getTinhTrang(), df.format(room.getGiaPhong())};
                            modelTblRoom.addRow(rowData);
                            i++;
                        }
                    } else if (cmbFindPrice.getSelectedIndex() == 2) {
                        if (room.getGiaPhong() > 200000 && room.getGiaPhong() <= 300000) {
                            Object[] rowData = {i, room.getMaPhong(), room.getLoaiPhong().getTenLoaiPhong(), room.getViTri(), room.getTinhTrang(), df.format(room.getGiaPhong())};
                            modelTblRoom.addRow(rowData);
                            i++;
                        }
                    } else if (cmbFindPrice.getSelectedIndex() == 3) {
                        if (room.getGiaPhong() > 300000 && room.getGiaPhong() <= 400000) {
                            Object[] rowData = {i, room.getMaPhong(), room.getLoaiPhong().getTenLoaiPhong(), room.getViTri(), room.getTinhTrang(), df.format(room.getGiaPhong())};
                            modelTblRoom.addRow(rowData);
                            i++;
                        }
                    }
                }
            }
        });
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
     * Load danh sách loại phòng lên cboTimTheoTen
     */
    private void loadCboLoaiPhong() {
        java.util.List<TypeOfRoom> dataList = TypeOfRoomDAO.getAllLoaiPhong();
        for (TypeOfRoom typeOfRoom : dataList) {
            cmbFindName.addItem(typeOfRoom.getTenLoaiPhong());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnRefresh)) {
            cmbFindName.setSelectedIndex(0);
            cmbFindPrice.setSelectedIndex(0);
            modelTblRoom.getDataVector().removeAllElements();
            loadP();
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

