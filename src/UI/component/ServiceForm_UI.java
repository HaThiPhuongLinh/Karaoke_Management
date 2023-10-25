package UI.component;

import ConnectDB.ConnectDB;
import DAO.RoomDAO;
import DAO.ServiceDAO;
import DAO.TypeOfRoomDAO;
import DAO.TypeOfServiceDAO;
import Entity.*;
import UI.CustomUI.Custom;

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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ServiceForm_UI extends JPanel implements ActionListener, MouseListener {
    private DefaultTableModel tableModel, serviceModel;
    private JPanel pnlShowRoom, pnlRoomList, pnlServiceList, pnlShowService, pnlServiceControl,  pnlServiceDetail, timeNow, pnlRoomControl, pnlSelect;
    private JLabel backgroundLabel, timeLabel, searchLabel, searchServiceLable, tOSLabel, quantityLabel, nameLable, stockLabel, sumLabel;
    private JTextField txtFind, txtFindService, txtName, txtStock, txtSum;
    private JScrollPane scrShowRoom, scrShowService, scrShowServiceDetail;
    private JSpinner txtQuantity;
    private JButton btnFindRoom, btnFindService, btnUse;
    private JButton[] btnRoomList;
    private int heightTable = 140;
    private int location = -1;
    private JTable tblService, tblSC;
    private JComboBox<String> cboService;
    private TypeOfRoomDAO typeOfRoomDAO;
    private TypeOfServiceDAO typeOfServiceDAO;
    private ServiceDAO serviceDAO;
    private RoomDAO roomDAO;
    private Map<String, TypeOfService> maDichVuToLoaiDichVu;
    private ArrayList<Service> lstService;
    private DecimalFormat df = new DecimalFormat("#,###.##");

    public ServiceForm_UI() {
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        typeOfRoomDAO = new TypeOfRoomDAO();
        typeOfServiceDAO= new TypeOfServiceDAO();
        serviceDAO = new ServiceDAO();
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

        //Room
        pnlRoomList = new JPanel();
        pnlRoomList.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Phòng", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlRoomList.setBounds(10, 70, 500, 360);
        pnlRoomList.setOpaque(false);
        add(pnlRoomList);
        pnlRoomList.setLayout(new BorderLayout(0, 0));

        pnlRoomControl = new JPanel();
        pnlRoomControl.setOpaque(false);
        pnlRoomControl.setBackground(Color.WHITE);
        pnlRoomList.add(pnlRoomControl, BorderLayout.NORTH);
        pnlRoomControl.setLayout(null);
        pnlRoomControl.setPreferredSize(new Dimension(330, 55));

        searchLabel = new JLabel("Mã phòng: ");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        searchLabel.setBounds(20, 17, 85, 20);
        searchLabel.setForeground(Color.WHITE);
        pnlRoomControl.add(searchLabel);

        txtFind = new JTextField();
        txtFind.setBounds(106, 14, 170, 28);
        pnlRoomControl.add(txtFind);

        btnFindRoom = new JButton("Tìm");
        btnFindRoom.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnFindRoom);
        btnFindRoom.setBounds(290, 13, 105, 30);
        pnlRoomControl.add(btnFindRoom);

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


        //Service
        pnlServiceList = new JPanel();
        pnlServiceList.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Dịch vụ", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlServiceList.setBounds(520, 10, 620, 420);
        pnlServiceList.setOpaque(false);
        add(pnlServiceList);
        pnlServiceList.setLayout(new BorderLayout(0, 0));

        pnlServiceControl = new JPanel();
        pnlServiceControl.setOpaque(false);
        pnlServiceControl.setBackground(Color.WHITE);
        pnlServiceList.add(pnlServiceControl, BorderLayout.NORTH);
        pnlServiceControl.setLayout(null);
        pnlServiceControl.setPreferredSize(new Dimension(330, 95));

        tOSLabel = new JLabel("Loại dịch vụ: ");
        tOSLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        tOSLabel.setBounds(20, 17, 85, 20);
        tOSLabel.setForeground(Color.WHITE);
        pnlServiceControl.add(tOSLabel);

        cboService = new JComboBox<String>();
        cboService.setBounds(126, 14, 170, 28);
        Custom.setCustomComboBox(cboService);
        pnlServiceControl.add(cboService);

        searchServiceLable = new JLabel("Tên dịch vụ: ");
        searchServiceLable.setFont(new Font("Arial", Font.PLAIN, 14));
        searchServiceLable.setBounds(20, 57, 85, 20);
        searchServiceLable.setForeground(Color.WHITE);
        pnlServiceControl.add(searchServiceLable);

        txtFindService = new JTextField();
        txtFindService.setBounds(126, 54, 170, 28);
        pnlServiceControl.add(txtFindService);

        btnFindService = new JButton("Tìm");
        btnFindService.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnFindService);
        btnFindService.setBounds(330, 54, 105, 30);
        pnlServiceControl.add(btnFindService);

        pnlShowService = new JPanel();
        pnlShowService.setOpaque(false);
        pnlShowService.setBackground(Color.WHITE);
        pnlShowService.setPreferredSize(new Dimension(310, 140));

        String[] colsService2 = {"Mã dịch vụ", "Tên dịch vụ", "Đơn vị tính", "Số lượng tồn", "Giá bán"};
        serviceModel = new DefaultTableModel(colsService2, 0);

        tblSC = new JTable(serviceModel);
        Custom.getInstance().setCustomTable(tblSC);

        tblSC.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                int row= tblSC.getSelectedRow();
                txtName.setText(serviceModel.getValueAt(row, 1).toString());
                txtStock.setText(serviceModel.getValueAt(row, 3).toString());

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

        for (Service dv : serviceDAO.getAllDichVu()) {
            Object[] rowData = { dv.getMaDichVu(), dv.getTenDichVu(), dv.getDonViTinh(), dv.getSoLuongTon(), df.format(dv.getGiaBan())};
            serviceModel.addRow(rowData);
        }

        pnlServiceList.add(scrShowService = new JScrollPane(tblSC, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        //scrShowService.setOpaque(false);
        scrShowService.getViewport().setOpaque(false);
        //scrShowService.getViewport().setBackground(Color.WHITE);

        pnlServiceDetail = new JPanel();
        pnlServiceDetail.setLayout(null);
        pnlServiceDetail.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlServiceDetail.setBounds(13, 440, 825, 250);
        pnlServiceDetail.setOpaque(false);
        add(pnlServiceDetail);

        String[] colsService = {"Mã PDV","Mã dịch vụ", "Tên dịch vụ", "Loại dịch vụ", "Đơn vị tính", "Số lượng", "Giá bán"};
        DefaultTableModel modelService = new DefaultTableModel(colsService, 0);

        tblService = new JTable(modelService);
        tblService.setFont(new Font("Arial", Font.BOLD, 14));
        tblService.setBackground(new Color(255, 255, 255, 0));
        tblService.setForeground(new Color(255, 255, 255));
        tblService.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblService.getTableHeader().setForeground(Color.BLUE);


        pnlServiceDetail.add(scrShowServiceDetail = new JScrollPane(tblService, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        scrShowServiceDetail.setBounds(4, 10, 815, 240);
        scrShowServiceDetail.setOpaque(false);
        scrShowServiceDetail.getViewport().setOpaque(false);
        scrShowServiceDetail.getViewport().setBackground(Color.WHITE);

        pnlSelect = new JPanel();
        pnlSelect.setLayout(null);
        pnlSelect.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlSelect.setBounds(850, 440, 290, 250);
        pnlSelect.setOpaque(false);
        add(pnlSelect);

        nameLable = new JLabel("Dịch vụ: ");
        nameLable.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLable.setBounds(10, 17, 85, 20);
        nameLable.setForeground(Color.WHITE);
        pnlSelect.add(nameLable);

        txtName = new JTextField();
        txtName.setBounds(90, 13, 185, 28);
        pnlSelect.add(txtName);

        quantityLabel = new JLabel("Số lượng: ");
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        quantityLabel.setBounds(10, 58, 85, 20);
        quantityLabel.setForeground(Color.WHITE);
        pnlSelect.add(quantityLabel);

        txtQuantity = new JSpinner();
        txtQuantity.setBounds(90, 56, 185, 28);
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) txtQuantity.getEditor();
        editor.getTextField().setHorizontalAlignment(JTextField.LEFT);
        pnlSelect.add(txtQuantity);

        stockLabel = new JLabel("SL tồn: ");
        stockLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        stockLabel.setBounds(10, 99, 85, 20);
        stockLabel.setForeground(Color.WHITE);
        pnlSelect.add(stockLabel);

        txtStock = new JTextField();
        txtStock.setBounds(90, 98, 185, 28);
        pnlSelect.add(txtStock);

        sumLabel = new JLabel("Tổng tiền: ");
        sumLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        sumLabel.setBounds(10, 139, 85, 20);
        sumLabel.setForeground(Color.WHITE);
        pnlSelect.add(sumLabel);

        txtSum = new JTextField();
        txtSum.setBounds(90, 138, 185, 28);
        pnlSelect.add(txtSum);

        btnUse = new JButton("Đặt dịch vụ");
        btnUse.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnUse);
        btnUse.setBounds(100, 190, 155, 30);
        pnlSelect.add(btnUse);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);

        ArrayList<Room> roomList = roomDAO.getRoomList();
        loadRoomList();
        reSizeColumnTableService();
        loadCboService();
        btnFindService.addActionListener(this);

        txtFindService.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnFindService.doClick();
                }
            }
        });

        cboService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoaiDichVu = (String) cboService.getSelectedItem();
                serviceModel.setRowCount(0);
                for (Service dv : serviceDAO.getAllDichVu()) {
                    if (selectedLoaiDichVu.equalsIgnoreCase("Tất cả") ||
                            selectedLoaiDichVu.equalsIgnoreCase(dv.getMaLoaiDichVu().getTenLoaiDichVu())) {
                        Object[] rowData = { dv.getMaDichVu(), dv.getTenDichVu(), dv.getDonViTinh(), dv.getSoLuongTon(), df.format(dv.getGiaBan())};
                        serviceModel.addRow(rowData);
                    }
                }
            }
        });
    }


    private void loadCboService() {
     java.util.List<TypeOfService> dataList = typeOfServiceDAO.getAllLoaiDichVu();
        cboService.addItem("Tất cả");
        for (TypeOfService serviceType : dataList) {
            cboService.addItem(serviceType.getTenLoaiDichVu());
        }
    }

    private void loadRoom(String roomID1) {
        Room room = roomDAO.getRoomByRoomId(roomID1);
        if (room == null) room = new Room();
        String statusP = room.getTinhTrang();
        String roomID = room.getMaPhong();
        String btnName = "<html><p style='text-align: center;'> " + roomID + " </p></br><p style='text-align: center;'> " + statusP + " </p></html>";
        int index = 0;
        for (int i = 0; i < btnRoomList.length; i++) {
            if (btnRoomList[i].getText().contains(roomID)){
                index = i;
            }
            else if (btnRoomList[i].getText().equals("")) {
                index = i;
                break;
            }
        }
        btnRoomList[index].setText(btnName);
        btnRoomList[index].setForeground(Color.WHITE);
        btnRoomList[index].setFont(new Font("Dialog", Font.BOLD, 12));
        btnRoomList[index].setVerticalTextPosition(SwingConstants.BOTTOM);
        btnRoomList[index].setHorizontalTextPosition(SwingConstants.CENTER);
        btnRoomList[index].setPreferredSize(new Dimension(110, 110));
        btnRoomList[index].setIcon(new ImageIcon(getClass().getResource("/images/room.png")));
        btnRoomList[index].setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRoomList[index].setBackground(Color.decode("#008000"));
        pnlShowRoom.revalidate();
        pnlShowRoom.repaint();
    }

    /**
     * Hiển thị danh sách phòng được truyền vào
     *
     * @param  {@code ArrayList<Room>}: danh sách phòng cần hiển thị
     */
    private void loadRoomList() {
        pnlShowRoom.removeAll();
        pnlShowRoom.revalidate();
        pnlShowRoom.repaint();
        Border lineRed = new LineBorder(Color.RED, 2);
        Border lineGray = new LineBorder(Color.GRAY, 1);

        // Lấy danh sách các phòng có trạng thái "Đang sử dụng"
        ArrayList<Room> roomsInUse = roomDAO.getRoomsByStatus("Đang sử dụng");

        int sizeListRoom = roomsInUse.size();
        btnRoomList = new JButton[sizeListRoom];

        for (int i = 0; i < sizeListRoom; i++) {
            int selection = i;
            String roomID = roomsInUse.get(i).getMaPhong();
            String status = roomsInUse.get(i).getTinhTrang();
            String typeRoom = roomsInUse.get(i).getLoaiPhong().getTenLoaiPhong();
            String location2 = roomsInUse.get(i).getViTri();

            btnRoomList[selection] = new JButton("");
            loadRoom(roomsInUse.get(i).getMaPhong());
            btnRoomList[selection].setBorder(lineGray);
            if ((i + 1) % 4 == 0) {
                heightTable += 100;
                pnlShowRoom.setPreferredSize(new Dimension(0, heightTable));
            }
            btnRoomList[selection].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (location != -1) {
                        btnRoomList[location].setBorder(lineGray);
                    }
                    String roomTypeName = typeOfRoomDAO.getRoomTypeNameByRoomID(roomID);
                    cboService.setSelectedItem(roomTypeName);

                    String roomStatus = roomDAO.getSatusRoomByID(roomID);

                    location = selection;
                    btnRoomList[selection].setBorder(lineRed);
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
                    String roomStatus = roomActiveE.getTinhTrang();
                    btnRoomList[selection].setBackground(Color.decode("#008000"));
                    btnRoomList[selection].setForeground(Color.WHITE);
                }
            });
            pnlShowRoom.add(btnRoomList[selection]);
        }
    }

    /**
     * Thay đổi kích thước các cột trong bảng dịch vụ
     */
    private void reSizeColumnTableService() {
        TableColumnModel tcm = tblSC.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(30);
        tcm.getColumn(1).setPreferredWidth(120);
        tcm.getColumn(2).setPreferredWidth(30);
        tcm.getColumn(3).setPreferredWidth(40);
        tcm.getColumn(4).setPreferredWidth(40);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        tcm.getColumn(0).setCellRenderer(leftRenderer);
        tcm.getColumn(3).setCellRenderer(rightRenderer);
        tcm.getColumn(4).setCellRenderer(rightRenderer);
    }


    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnFindService)) {
            if (txtFindService.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Chưa nhập tên dịch vụ");
            } else {
                String serviceName = txtFindService.getText().trim();
                serviceModel.getDataVector().removeAllElements();
                serviceModel.fireTableDataChanged();
                lstService = serviceDAO.getServiceByName(serviceName);
                if (lstService == null || lstService.size() <= 0) {
                    serviceModel.getDataVector().removeAllElements();
                } else {
                    for (Service s : lstService) {
                        serviceModel.addRow(new Object[] { s.getMaDichVu(), s.getTenDichVu(), s.getDonViTinh(), s.getSoLuongTon(), df.format(s.getGiaBan()) });
                    }
                }
            }
        }
    }


    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        timeLabel.setText(time);
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
