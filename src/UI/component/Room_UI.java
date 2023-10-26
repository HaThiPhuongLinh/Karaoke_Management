package UI.component;

import javax.swing.*;

import ConnectDB.ConnectDB;
import DAO.RoomDAO;
import DAO.TypeOfRoomDAO;
import Entity.Room;
import Entity.TypeOfRoom;
import UI.CustomUI.Custom;

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
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.sql.SQLException ;

public class Room_UI extends JPanel implements ActionListener, MouseListener {
    private  JTextField txtBaoLoi;
    private  JButton btnThêmP;
    private  JButton btnXoaP;
    private  JButton btnSuaP;
    private  JTextField txtMaPhong;
    private JTextField txtVitri;
    private  JComboBox<String> cboLocTheoLP;
    private  JTextField txtGiaP;
    private  JButton btnlamMoiP;
    private  JComboBox<String> cboTinhTrang;
    private  JComboBox<String> cboLoaiPhong;
    private JTable tblPhong;
    private DefaultTableModel modelTableP;
    private DecimalFormat df = new DecimalFormat("#,###.##");


    private JPanel pnlRoomControl, pnlRoomList, timeNow;
    private JLabel backgroundLabel, timeLabel;
    private RoomDAO RoomDAO;
    private TypeOfRoomDAO typeOfRoomDAO;

    public Room_UI() {
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

        timeNow = new JPanel();
        timeNow.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
                TitledBorder.LEADING, TitledBorder.TOP));
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
        modelTableP = new DefaultTableModel(colsP, 0);
        JScrollPane scrollPaneP;

        tblPhong = new JTable(modelTableP);
        tblPhong.setFont(new Font("Arial", Font.BOLD, 14));
        tblPhong.setBackground(new Color(255, 255, 255, 0));
        tblPhong.setForeground(new Color(255, 255, 255));
        tblPhong.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblPhong.getTableHeader().setForeground(Color.BLUE);
        tblPhong.addMouseListener(this);

        Custom.getInstance().setCustomTable(tblPhong);

        panelDSP.add(scrollPaneP = new JScrollPane(tblPhong, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneP.setBounds(10, 20, 1210, 380);
        scrollPaneP.setOpaque(false);
        scrollPaneP.getViewport().setOpaque(false);
        scrollPaneP.getViewport().setBackground(Color.WHITE);
        pnlRoomList.add(panelDSP);

        //        Mã phòng
        JLabel labelMaPhong = new JLabel("Mã Phòng:");
        labelMaPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        labelMaPhong.setBounds(80, 20, 120, 30);
        labelMaPhong.setForeground(Color.WHITE);
        pnlRoomControl.add(labelMaPhong);

        txtMaPhong = new JTextField();
        txtMaPhong.setBounds(195, 20, 311, 30);
        txtMaPhong.setColumns(10);
        pnlRoomControl.add(txtMaPhong);

//      Vị trí
        JLabel labelViTri = new JLabel("Vị Trí:");
        labelViTri.setFont(new Font("Arial", Font.PLAIN, 14));
        labelViTri.setBounds(80, 70, 120, 30);
        labelViTri.setForeground(Color.WHITE);
        pnlRoomControl.add(labelViTri);

        txtVitri = new JTextField();
        txtVitri.setBounds(195, 70, 311, 30);
        txtVitri.setColumns(10);
        pnlRoomControl.add(txtVitri);

        //      Loại phòng
        JLabel labelLoaiPhong = new JLabel("Loại phòng:");
        labelLoaiPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLoaiPhong.setBounds(80, 120, 120, 30);
        labelLoaiPhong.setForeground(Color.WHITE);
        pnlRoomControl.add(labelLoaiPhong);

        cboLoaiPhong = new JComboBox<String>();
        cboLoaiPhong.addItem("Tất cả");
        cboLoaiPhong.setBounds(195, 120, 311, 30);
        Custom.setCustomComboBox(cboLoaiPhong);
        pnlRoomControl.add(cboLoaiPhong);

        //tinh trang
        JLabel labelTinhTrang = new JLabel("Tình Trạng:");
        labelTinhTrang.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTinhTrang.setBounds(80, 170, 120, 30);
        labelTinhTrang.setForeground(Color.WHITE);
        pnlRoomControl.add(labelTinhTrang);

        cboTinhTrang = new JComboBox<String>();
        cboTinhTrang.addItem("Trống");
        cboTinhTrang.addItem("Chờ");
        cboTinhTrang.addItem("Đang sử dụng");
        cboTinhTrang.addItem("Tạm");
        cboTinhTrang.setBounds(195, 170, 311, 30);
        Custom.setCustomComboBox(cboTinhTrang);
        pnlRoomControl.add(cboTinhTrang);


        JLabel labelGiaP = new JLabel("Giá Phòng:");
        labelGiaP.setFont(new Font("Arial", Font.PLAIN, 14));
        labelGiaP.setBounds(600, 20, 150, 30);
        labelGiaP.setForeground(Color.WHITE);
        pnlRoomControl.add(labelGiaP);

        txtGiaP = new JTextField();
        txtGiaP.setBounds(715, 20, 311, 30);
        txtGiaP.setColumns(6);
        pnlRoomControl.add(txtGiaP);

        txtBaoLoi = new JTextField();
        txtBaoLoi.setFont(new Font("Arial",Font.BOLD,13));
        txtBaoLoi.setForeground(Color.RED);
        txtBaoLoi.setBounds(600, 120, 426, 30);
        txtBaoLoi.setColumns(6);
        pnlRoomControl.add(txtBaoLoi);

        //        btn thêm
        btnThêmP = new JButton("Thêm");
        btnThêmP.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThêmP);
        btnThêmP.setBounds(600, 170, 100, 30);
        pnlRoomControl.add(btnThêmP);

        //        btn Xóa
        btnXoaP = new JButton("Xóa");
        btnXoaP.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnXoaP);
        btnXoaP.setBounds(740, 170, 100, 30);
        pnlRoomControl.add(btnXoaP);

        //        btn sửa
        btnSuaP = new JButton("Sửa");
        btnSuaP.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnSuaP);
        btnSuaP.setBounds(880, 170, 100, 30);
        pnlRoomControl.add(btnSuaP);

        //        btn làm mới
        btnlamMoiP = new JButton("Làm mới");
        btnlamMoiP.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnlamMoiP);
        btnlamMoiP.setBounds(1020, 170, 100, 30);
        pnlRoomControl.add(btnlamMoiP);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);
        btnSuaP.addActionListener(this);
        btnlamMoiP.addActionListener(this);
        btnThêmP.addActionListener(this);
        btnXoaP.addActionListener(this);
        loadP();
        loadCboLoaiPhong();
    }
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        timeLabel.setText(time);
    }

    public void loadP() {
        int i = 1;
        for (Room room : RoomDAO.getRoomList()) {
            Object[] rowData = {i, room.getMaPhong(), room.getLoaiPhong().getTenLoaiPhong(), room.getViTri(), room.getTinhTrang(), df.format(room.getGiaPhong())};
            modelTableP.addRow(rowData);
            i++;
        }
    }
    private void loadCboLoaiPhong() {
        java.util.List<TypeOfRoom> dataList = typeOfRoomDAO.getAllLoaiPhong();
        for (TypeOfRoom typeOfRoom : dataList) {
            cboLoaiPhong.addItem(typeOfRoom.getTenLoaiPhong());
        }
    }
    public String laymaP(){
        String MaP = RoomDAO.generateNextRoomId();
        return MaP;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThêmP)) {
            if (txtVitri.getText().equals("") || txtGiaP.getText().equals("") ||   cboLoaiPhong.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin phòng");
            } else if (validData()) {
                String ma = laymaP();
                String tenLDV = cboLoaiPhong.getSelectedItem().toString().trim();
                ArrayList<TypeOfRoom> typeOfRooms = typeOfRoomDAO.getTypeOfRoomByName(tenLDV);
                String s = "";
                for (TypeOfRoom typeOfRoom : typeOfRooms) {
                    s += typeOfRoom.getMaLoaiPhong();
                }
                String vitri = txtVitri.getText().trim();
                String tinhTrang = cboTinhTrang.getSelectedItem().toString();
                int giaBan = Integer.parseInt(txtGiaP.getText().trim());
                Room room = new Room(ma, new TypeOfRoom(s), vitri, tinhTrang, giaBan);
                try {
                    if (RoomDAO.insert(room)) {
                        modelTableP.getDataVector().removeAllElements();
                        loadP();
                        JOptionPane.showMessageDialog(this, "Thêm phòng thành công");
                        reFresh();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }


        } else if (o.equals(btnlamMoiP)) {
           reFresh();
            modelTableP.getDataVector().removeAllElements();
            loadP();
        } else if (o.equals(btnXoaP)) {
            int row = tblPhong.getSelectedRow();
            try {
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng cần xóa");
                } else {
                    String tenLP = cboLoaiPhong.getSelectedItem().toString().trim();
                    ArrayList<TypeOfRoom> typeOfRooms = typeOfRoomDAO.getTypeOfRoomByName(tenLP);
                    String s = "";
                    for (TypeOfRoom typeOfRoom : typeOfRooms) {
                        s += typeOfRoom.getMaLoaiPhong();
                    }
                    String map = txtMaPhong.getText().trim();
                    String vitri = txtVitri.getText().trim();
                    double gia = Double.parseDouble(txtGiaP.getText().toString());
                    String tinhtrang = cboTinhTrang.getSelectedItem().toString().trim();

                    Room room = new Room(map, new TypeOfRoom(s), vitri, tinhtrang, gia);

                    int ans = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Cảnh báo",
                            JOptionPane.YES_NO_OPTION);
                    if (ans == JOptionPane.YES_OPTION) {
                        RoomDAO.delete(room);
                        modelTableP.removeRow(row);
                        modelTableP.getDataVector().removeAllElements();
                        loadP();
                        JOptionPane.showMessageDialog(this, "Xóa thành công");
                        reFresh();
                    }
                }
            } catch (Exception e3) {
                JOptionPane.showMessageDialog(this, "Xóa không thành công");
            }
        } else if (o.equals(btnSuaP)) {
            int row = tblPhong.getSelectedRow();
            if (row > 0) {
                if (validData()) {
                    String tenLP = cboLoaiPhong.getSelectedItem().toString().trim();
                    ArrayList<TypeOfRoom> typeOfRooms = typeOfRoomDAO.getTypeOfRoomByName(tenLP);
                    String s = "";
                    for (TypeOfRoom typeOfRoom : typeOfRooms) {
                        s += typeOfRoom.getMaLoaiPhong();
                    }
                    String map = txtMaPhong.getText().trim();
                    String vitri = txtVitri.getText().trim();
                    double gia = Double.parseDouble(txtGiaP.getText().toString());
                    String tinhtrang = cboTinhTrang.getSelectedItem().toString().trim();
                    Room room = new Room(map, new TypeOfRoom(s), vitri, tinhtrang, gia);

                    boolean result = RoomDAO.update(room);
                    if (result == true) {
                        String priceStr = df.format(room.getGiaPhong());
                        tblPhong.setValueAt(cboLoaiPhong.getSelectedItem().toString(), row, 2);
                        tblPhong.setValueAt(txtVitri.getText(), row, 3);
                        tblPhong.setValueAt(cboTinhTrang.getSelectedItem().toString(), row, 4);
                        tblPhong.setValueAt(priceStr, row, 5);
                        JOptionPane.showMessageDialog(this, "Sửa thành công");
                        reFresh();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng cần sửa");
            }
        }
    }

    private void reFresh(){
        txtMaPhong.setText("");
        cboLoaiPhong.setSelectedIndex(0);
        txtGiaP.setText("");
        txtVitri.setText("");
        cboTinhTrang.setSelectedIndex(0);
        txtBaoLoi.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tblPhong.getSelectedRow();
        txtMaPhong.setText(modelTableP.getValueAt(row, 1).toString());
        cboLoaiPhong.setSelectedItem(modelTableP.getValueAt(row, 2).toString());
        cboTinhTrang.setSelectedItem(modelTableP.getValueAt(row, 4).toString());
        txtGiaP.setText(modelTableP.getValueAt(row, 5).toString().trim().replace(",", ""));
        txtVitri.setText(modelTableP.getValueAt(row, 3).toString());
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
    private void showMessage(JTextField txt, String message) {
        txt.requestFocus();
        txtBaoLoi.setText(message);
    }
    private boolean validData() {
        String vitri = txtVitri.getText().trim();
        String giaBan = txtGiaP.getText().trim();
        if (!((vitri.length()) > 0 && vitri.matches("^[A-Za-zÀ-ỹ0-9 ]+"))) {
            showMessage(txtVitri,"Vị trí không được chứa số và kí tự đặc biệt");
            return false;
        }
        if (!((giaBan.length()) > 0 && giaBan.matches("^[1-9]\\d*"))) {
            showMessage(txtGiaP,"Giá phải lớn hơn 0");
            return false;
        }
        return true;
    }
}
