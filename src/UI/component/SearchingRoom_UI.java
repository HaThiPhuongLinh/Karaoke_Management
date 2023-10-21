package UI.component;

import ConnectDB.ConnectDB;
import DAO.RoomDAO;
import DAO.TypeOfRoomDAO;
import Entity.*;
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
import java.util.ArrayList;
import java.util.Date;

public class SearchingRoom_UI extends JPanel implements ActionListener, MouseListener {
    private  JTable tableP;
    private  DefaultTableModel modelTableP;
    private JLabel backgroundLabel, timeLabel, search1Label, search2Label, search3Label;
    private JTextField  txtSearch2, txtSearch3;
    private JComboBox cboTimTheoTen;
    private JPanel timeNow, pnlCusList, pnlCusControl, pnlCusListRight;
    private DefaultTableModel tableModel;
    private JButton btnTim,btnLammOi;
    private RoomDAO RoomDAO;
    private TypeOfRoomDAO typeOfRoomDAO;
    private DecimalFormat df = new DecimalFormat("#,###.##");

    public SearchingRoom_UI() {
        setLayout(null);
        setBounds(0, 0, 1175, 770);
        typeOfRoomDAO = new TypeOfRoomDAO();
        RoomDAO = new RoomDAO();
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //phan viet code
        JLabel headerLabel = new JLabel("TÌM KIẾM PHÒNG");
        headerLabel.setBounds(470, 10, 1175, 40);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        Component add = add(headerLabel);

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

        pnlCusList = new JPanel();
        pnlCusList.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "PHÒNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlCusList.setBounds(10, 70, 1120, 620);
        pnlCusList.setOpaque(false);
        add(pnlCusList);
        pnlCusList.setLayout(new BorderLayout(0, 0));


        pnlCusControl = new JPanel();
        pnlCusControl.setOpaque(false);
        pnlCusControl.setBackground(Color.WHITE);
        pnlCusList.add(pnlCusControl, BorderLayout.NORTH);
        pnlCusControl.setLayout(null);
        pnlCusControl.setPreferredSize(new Dimension(1100, 100));

        search1Label = new JLabel("Tìm Theo Tên Loại Phòng: ");

        search1Label.setFont(new Font("Arial", Font.PLAIN, 14));
        search1Label.setBounds(30, 25, 200, 30);
        search1Label.setForeground(Color.WHITE);
        pnlCusControl.add(search1Label);

        cboTimTheoTen = new JComboBox();
        cboTimTheoTen.setBounds(215, 25, 280, 30);
        cboTimTheoTen.addItem("Tất Cả");
        pnlCusControl.add(cboTimTheoTen);

        btnTim = new JButton("Tìm kiếm");
        btnTim.setBounds(980, 25, 100, 30);
        Custom.setCustomBtn(btnTim);
        btnTim.setFont(new Font("Arial", Font.BOLD, 14));
        pnlCusControl.add(btnTim);
        btnLammOi = new JButton("Làm mới");
        btnLammOi.setBounds(980, 60, 100, 30);
        Custom.setCustomBtn(btnLammOi);
        btnLammOi.setFont(new Font("Arial", Font.BOLD, 14));
        pnlCusControl.add(btnLammOi);

        search2Label = new JLabel("Tìm Theo Giá: ");
        search2Label.setFont(new Font("Arial", Font.PLAIN, 14));
        search2Label.setBounds(540, 25, 120, 30);
        search2Label.setForeground(Color.WHITE);
        pnlCusControl.add(search2Label);

        txtSearch2 = new JTextField();
        txtSearch2.setBounds(645, 25, 280, 30);
        pnlCusControl.add(txtSearch2);

        JPanel panelDSKH = new JPanel();
        panelDSKH.setLayout(null);
        panelDSKH.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH PHÒNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSKH.setBounds(30, 20, 1100, 470);
        panelDSKH.setOpaque(false);

        String[] colsKH = {"STT", "Mã Phòng", "Loại Phòng", "Tình Trạng", "Vị Trí","Giá Tiền"};
        modelTableP = new DefaultTableModel(colsKH, 0);
        JScrollPane scrollPaneP;

        tableP = new JTable(modelTableP);
        tableP.setFont(new Font("Arial", Font.BOLD, 14));
        tableP.setBackground(new Color(255, 255, 255, 0));
        tableP.setForeground(new Color(255, 255, 255));
        tableP.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableP.getTableHeader().setForeground(Color.BLUE);
        Custom.getInstance().setCustomTable(tableP);

        panelDSKH.add(scrollPaneP = new JScrollPane(tableP, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneP.setBounds(10, 20, 1090, 470);
        scrollPaneP.setOpaque(false);
        scrollPaneP.getViewport().setOpaque(false);
        scrollPaneP.getViewport().setBackground(Color.WHITE);
        pnlCusList.add(panelDSKH);
        loadP();
        loadCboLoaiPhong();
        btnLammOi.addActionListener(this);
        btnTim.addActionListener(this);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);
//Tim theo ten loại phong
        cboTimTheoTen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoaiPhong = (String) cboTimTheoTen.getSelectedItem();
                txtSearch2.setText("");

                modelTableP.setRowCount(0);
                int i=1;
                for (Room room : RoomDAO.getRoomList()) {
                    if (selectedLoaiPhong.equalsIgnoreCase("Tất cả") ||
                            selectedLoaiPhong.equalsIgnoreCase(room.getLoaiPhong().getTenLoaiPhong())) {
                        Object[] rowData = {i,room.getMaPhong(),room.getLoaiPhong().getTenLoaiPhong(),room.getViTri(),room.getTinhTrang(),df.format(room.getGiaPhong())};
                        modelTableP.addRow(rowData);
                        i++;
                    }
                }
            }
        });
    }
    //Cap nhat thoi gian thuc
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        timeLabel.setText(time);
    }
    //Load danh sach phong len bang
    public void loadP(){
        int i=1;
        for (Room room : RoomDAO.getRoomList()) {
            Object[] rowData = { i,room.getMaPhong(),room.getLoaiPhong().getTenLoaiPhong(),room.getViTri(),room.getTinhTrang(),df.format(room.getGiaPhong())};
            modelTableP.addRow(rowData);
            i++;

        }
    }
    //Load danh sach loai phong len combobox
    private void loadCboLoaiPhong() {
        java.util.List<TypeOfRoom> dataList = TypeOfRoomDAO.getAllLoaiPhong();

        for (TypeOfRoom typeOfRoom : dataList) {
            cboTimTheoTen.addItem(typeOfRoom.getTenLoaiPhong());
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnTim)) {
            if ( txtSearch2.getText().trim().equals("") ) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin tìm kiếm");
            } else if (!txtSearch2.getText().trim().equals("")) {
                modelTableP.getDataVector().removeAllElements();
                int gia = Integer.parseInt(txtSearch2.getText());
                ArrayList<Room> services3 = RoomDAO.getPhongTheoGia(gia);
                int i = 1;
                if (services3.size() != 0) {
                    for (Room room : services3) {
                        modelTableP.addRow(new Object[]{i, room.getMaPhong(), room.getLoaiPhong().getTenLoaiPhong(), room.getViTri(), room.getTinhTrang(), df.format(room.getGiaPhong())});
                        i++;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy phòng");
                    txtSearch2.selectAll();
                    txtSearch2.requestFocus();
                }
            }

        }else if(o.equals(btnLammOi)){
            txtSearch2.setText("");
            cboTimTheoTen.setSelectedIndex(0);
            modelTableP.getDataVector().removeAllElements();
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

