package UI.component.Dialog;

import ConnectDB.ConnectDB;
import DAO.BillDAO;
import DAO.RoomDAO;
import Entity.Bill;
import Entity.Room;
import UI.CustomUI.Custom;
import UI.component.KaraokeBooking_UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SwitchRoom extends JFrame implements ActionListener {
    public JLabel lblTitle, lblRoomID, lblRoomID2, lblArrow, lblRoomID3, lblRoomID4, lblCName2, lblRDay, lblTDay, lblMin, lblHour;
    private JTextField txtRoom;
    private JButton btnFind, btnReturn, btnRefresh, btnSwitch;
    private JPanel topPanel;
    private JTable tblRoom;
    private DefaultTableModel modelTblRoom;
    private RoomDAO roomDAO = new RoomDAO();
    private BillDAO billDAO = new BillDAO();
    private DecimalFormat df = new DecimalFormat("#,###.##");
    private KaraokeBooking_UI main;
    private String roomIdOld = ""; // Giá trị ban đầu của lblRoomID2
    private String roomIdNew = ""; // Giá trị ban đầu của lblRoomID2

    public SwitchRoom() {
        setSize(740, 460);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setUndecorated(true);

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        topPanel = new JPanel();
        topPanel.setBackground(Color.decode("#5F009D"));
        topPanel.setBounds(0, 0, 760, 50);
        lblTitle = new JLabel("ĐỔI PHÒNG");
        lblTitle.setFont(new Font("Dialog", Font.BOLD, 25));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(10, 10, 760, 60);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(lblTitle);
        add(topPanel);

        lblRoomID = new JLabel("Phòng hiện tại:");
        lblRoomID.setFont(new Font("Dialog", Font.PLAIN, 15));
        lblRoomID.setBounds(10, 60, 120, 20);
        lblRoomID2 = new JLabel(roomIdOld); // Gán giá trị ban đầu
        lblRoomID2.setFont(new Font("Dialog", Font.BOLD, 15));
        lblRoomID2.setBounds(120, 60, 100, 20);
        add(lblRoomID);
        add(lblRoomID2);

        lblArrow = new JLabel("=>");
        lblArrow.setFont(new Font("Dialog", Font.BOLD, 17));
        lblArrow.setBounds(170, 60, 30, 20);
        add(lblArrow);

        lblRoomID3 = new JLabel(""); // Giá trị ban đầu là trống
        lblRoomID3.setFont(new Font("Dialog", Font.BOLD, 15));
        lblRoomID3.setBounds(200, 60, 100, 20);
        add(lblRoomID3);

        lblRoomID4 = new JLabel("Phòng số:");
        lblRoomID4.setFont(new Font("Dialog", Font.BOLD, 15));
        lblRoomID4.setBounds(350, 60, 100, 20);
        add(lblRoomID4);

        txtRoom = new JTextField();
        txtRoom.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtRoom.setBounds(450, 58, 130, 30);
        add(txtRoom);

        btnFind = new JButton("Tìm");
        btnFind.setBackground(Color.decode("#3C8DBC"));
        btnFind.setForeground(Color.WHITE);
        btnFind.setBounds(600, 61, 70, 27);
        add(btnFind);

        JPanel pnTable = new JPanel();
        pnTable.setBounds(20, 100, 700, 320);
        add(pnTable);
        pnTable.setLayout(new BorderLayout(0, 0));
        String[] cols = {"Mã phòng", "Trạng thái", "Loại phòng", "Giá phòng"};

        modelTblRoom = new DefaultTableModel(cols, 0);
        tblRoom = new JTable(modelTblRoom);
        JScrollPane scpTable = new JScrollPane(tblRoom);
        pnTable.add(scpTable, BorderLayout.CENTER);

        btnReturn = new JButton("Quay lại");
        btnReturn.setBackground(Color.decode("#FF3E20"));
        btnReturn.setForeground(Color.WHITE);
        btnReturn.setBounds(20, 425, 100, 27);
        add(btnReturn);

        btnRefresh = new JButton("Làm mới");
        btnRefresh.setBackground(Color.decode("#005B86"));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setBounds(140, 425, 100, 27);
        add(btnRefresh);

        btnSwitch = new JButton("Chuyển");
        btnSwitch.setBackground(Color.decode("#008000"));
        btnSwitch.setForeground(Color.WHITE);
        btnSwitch.setBounds(620, 425, 100, 27);
        add(btnSwitch);

        tblRoom.setRowHeight(30);

        for (Room r : roomDAO.getRoomList()) {
            if (r.getTinhTrang().trim().equalsIgnoreCase("Trống") || r.getTinhTrang().trim().equalsIgnoreCase("Chờ")) {
                Object[] rowData = {r.getMaPhong(), r.getTinhTrang(), r.getLoaiPhong().getTenLoaiPhong(), df.format(r.getGiaPhong())};
                modelTblRoom.addRow(rowData);
            }
        }

        tblRoom.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblRoom.getSelectedRow();
                int roomIDColumn = 0;
                roomIdNew = tblRoom.getValueAt(row, roomIDColumn).toString();
                lblRoomID3.setText(roomIdNew);
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

        });

        btnReturn.addActionListener(this);
        btnSwitch.addActionListener(this);
        btnFind.addActionListener(this);
        btnRefresh.addActionListener(this);
    }

    public static void main(String[] args) {
        new SwitchRoom().setVisible(true);
    }

    public void setKaraokeBookingUI(KaraokeBooking_UI main) {
        this.main = main;
        roomIdOld = main.txtRoom.getText(); // Gán giá trị ban đầu của lblRoomID2
        lblRoomID2.setText(roomIdOld); // Cập nhật lblRoomID2
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnReturn)) {
            dispose();
        }
        if (o.equals(btnFind)) {
            if (txtRoom.getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Chưa nhập mã phòng");
            } else {
                modelTblRoom.setRowCount(0);
                modelTblRoom.getDataVector().removeAllElements();
                String roomID = txtRoom.getText().trim();
                Room r = roomDAO.getRoomByRoomId(roomID);
                if (r != null) {
                    Object[] rowData = {r.getMaPhong(), r.getTinhTrang(), r.getLoaiPhong().getTenLoaiPhong(), df.format(r.getGiaPhong())};
                    modelTblRoom.addRow(rowData);
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy");
                }
            }
        }
        if (o.equals(btnRefresh)) {
            modelTblRoom.setRowCount(0);
            modelTblRoom.getDataVector().removeAllElements();
            for (Room r : roomDAO.getRoomList()) {
                if (r.getTinhTrang().trim().equalsIgnoreCase("Trống") || r.getTinhTrang().trim().equalsIgnoreCase("Chờ")) {
                    Object[] rowData = {r.getMaPhong(), r.getTinhTrang(), r.getLoaiPhong().getTenLoaiPhong(), df.format(r.getGiaPhong())};
                    modelTblRoom.addRow(rowData);
                }
            }
            txtRoom.setText("");
            lblRoomID3.setText("");
        }
        if (o.equals(btnSwitch)) {
            int row = tblRoom.getSelectedRow();
            int roomIDColumn = 0;
            roomIdOld = main.txtRoom.getText();
            roomIdNew = tblRoom.getValueAt(row, roomIDColumn).toString();
            String message = String.format("Bạn có chắc chắn muốn chuyển từ phòng %s qua phòng %s", roomIdOld, roomIdNew);
            int select = JOptionPane.showConfirmDialog(this, message, "Xác nhận chuyển phòng",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (select == JOptionPane.OK_OPTION) {
                Bill bill = billDAO.getBillByRoomID(roomIdOld);
                String billID = bill.getMaHoaDon();
                if (billID.equals("")) {
                    JOptionPane.showConfirmDialog(this, message, "Phòng này chưa được cho thuê nên không thể chuyển",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                } else {
                    String billId = bill.getMaHoaDon();
                    boolean result = roomDAO.switchRoom(billId, roomIdOld, roomIdNew);
                    if (result) {
                        ArrayList<Room> yourListOfRooms = roomDAO.getRoomList();
                        for (Room room : roomDAO.getRoomList()) {
                            if (room.getTinhTrang().equals("Trong")) {
                                roomDAO.updateRoomStatus(room.getMaPhong(), "Trống");
                            }
                            if (room.getTinhTrang().equals("Cho")) {
                                roomDAO.updateRoomStatus(room.getMaPhong(), "Chờ");
                            }
                            if (room.getTinhTrang().equals("Dang su dung")) {
                                roomDAO.updateRoomStatus(room.getMaPhong(), "Đang sử dụng");
                            }
                        }
                        main.LoadRoomList(yourListOfRooms);
                        JOptionPane.showMessageDialog(this, "Chuyển phòng thành công");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Chuyển phòng thất bại");
                    }
                }
            }
        }
    }
}
