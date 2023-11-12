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
    private JLabel backgroundLabel, timeLabel, search1Label, search2Label;
    private JComboBox cboTimTheoTen, cboTimTheoGia;
    private JPanel timeNow, pnlCusList, pnlCusControl;
    private JButton btnLammOi;
    private RoomDAO RoomDAO;
    private DecimalFormat df = new DecimalFormat("#,###.##/giờ");
    public static Staff staffLogin = null;

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

        //phan viet code
        JLabel headerLabel = new JLabel("TÌM KIẾM PHÒNG");
        headerLabel.setBounds(520, 10, 1175, 40);
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

        search1Label = new JLabel("Tìm Theo Tên Loại Phòng: ");

        search1Label.setFont(new Font("Arial", Font.PLAIN, 14));
        search1Label.setBounds(80, 25, 200, 30);
        search1Label.setForeground(Color.WHITE);
        pnlCusControl.add(search1Label);

        cboTimTheoTen = new JComboBox();
        cboTimTheoTen.setBounds(265, 25, 280, 30);
        cboTimTheoTen.addItem("Tất Cả");
        pnlCusControl.add(cboTimTheoTen);

        btnLammOi = new JButton("Làm mới");
        btnLammOi.setBounds(1030, 25, 100, 30);
        Custom.setCustomBtn(btnLammOi);
        btnLammOi.setFont(new Font("Arial", Font.BOLD, 14));
        pnlCusControl.add(btnLammOi);

        search2Label = new JLabel("Tìm Theo Giá: ");
        search2Label.setFont(new Font("Arial", Font.PLAIN, 14));
        search2Label.setBounds(590, 25, 120, 30);
        search2Label.setForeground(Color.WHITE);
        pnlCusControl.add(search2Label);

        cboTimTheoGia = new JComboBox();
        cboTimTheoGia.addItem("Tất cả");
        cboTimTheoGia.addItem("150.000 - 200.000");
        cboTimTheoGia.addItem("200.000 - 300.000");
        cboTimTheoGia.addItem("300.000 - 400.000");
        cboTimTheoGia.setBounds(695, 25, 280, 30);
        pnlCusControl.add(cboTimTheoGia);

        JPanel panelDSKH = new JPanel();
        panelDSKH.setLayout(null);
        panelDSKH.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH PHÒNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSKH.setBounds(30, 20, 1220, 520);
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
        scrollPaneP.setBounds(10, 20, 1210, 520);
        scrollPaneP.setOpaque(false);
        scrollPaneP.getViewport().setOpaque(false);
        scrollPaneP.getViewport().setBackground(Color.WHITE);
        pnlCusList.add(panelDSKH);
        loadP();
        loadCboLoaiPhong();
        btnLammOi.addActionListener(this);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);

        cboTimTheoTen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoaiPhong = (String) cboTimTheoTen.getSelectedItem();;
                cboTimTheoGia.setSelectedIndex(0);
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


        cboTimTheoGia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelTableP.setRowCount(0);
                int i=1;
                for (Room room : RoomDAO.getRoomList()) {
                    if (cboTimTheoGia.getSelectedIndex()==0){
                        Object[] rowData = {i,room.getMaPhong(),room.getLoaiPhong().getTenLoaiPhong(),room.getViTri(),room.getTinhTrang(),df.format(room.getGiaPhong())};
                        modelTableP.addRow(rowData);
                        i++;
                    }else if (cboTimTheoGia.getSelectedIndex()==1){
                        if (room.getGiaPhong()>=150000 && room.getGiaPhong()<=200000){
                            Object[] rowData = {i,room.getMaPhong(),room.getLoaiPhong().getTenLoaiPhong(),room.getViTri(),room.getTinhTrang(),df.format(room.getGiaPhong())};
                            modelTableP.addRow(rowData);
                            i++;
                        }
                    }else if (cboTimTheoGia.getSelectedIndex()==2){
                        if (room.getGiaPhong()>200000 && room.getGiaPhong()<=300000){
                            Object[] rowData = {i,room.getMaPhong(),room.getLoaiPhong().getTenLoaiPhong(),room.getViTri(),room.getTinhTrang(),df.format(room.getGiaPhong())};
                            modelTableP.addRow(rowData);
                            i++;
                        }
                    }else if (cboTimTheoGia.getSelectedIndex()==3){
                        if (room.getGiaPhong()>300000 && room.getGiaPhong()<=400000){
                            Object[] rowData = {i,room.getMaPhong(),room.getLoaiPhong().getTenLoaiPhong(),room.getViTri(),room.getTinhTrang(),df.format(room.getGiaPhong())};
                            modelTableP.addRow(rowData);
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
        timeLabel.setText(time);
    }

    /**
     * Load danh sách phòng lên bảng
     */
    public void loadP(){
        int i=1;
        for (Room room : RoomDAO.getRoomList()) {
            Object[] rowData = { i,room.getMaPhong(),room.getLoaiPhong().getTenLoaiPhong(),room.getViTri(),room.getTinhTrang(),df.format(room.getGiaPhong())};
            modelTableP.addRow(rowData);
            i++;

        }
    }
    /**
     * Load danh sách loại phòng lên cboTimTheoTen
     */
    private void loadCboLoaiPhong() {
        java.util.List<TypeOfRoom> dataList = TypeOfRoomDAO.getAllLoaiPhong();
        for (TypeOfRoom typeOfRoom : dataList) {
            cboTimTheoTen.addItem(typeOfRoom.getTenLoaiPhong());
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o.equals(btnLammOi)){
            cboTimTheoTen.setSelectedIndex(0);
            cboTimTheoGia.setSelectedIndex(0);
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

