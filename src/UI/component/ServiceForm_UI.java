package UI.component;

import ConnectDB.ConnectDB;
import DAO.*;
import Entity.*;
import UI.CustomUI.Custom;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private DefaultTableModel serviceModel, modelService;
    private JPanel pnlShowRoom, pnlRoomList, pnlServiceList, pnlShowService, pnlServiceControl,  pnlServiceDetail, timeNow, pnlRoomControl, pnlSelect;
    private JLabel backgroundLabel, timeLabel, searchLabel, searchServiceLable, tOSLabel, quantityLabel, nameLable, stockLabel, sumLabel, returnLabel;
    private JTextField txtFind, txtFindService, txtName, txtStock, txtSum;
    private JScrollPane scrShowRoom, scrShowService, scrShowServiceDetail;
    private JSpinner txtQuantity, txtReturn;
    private JButton btnFindRoom, btnFindService, btnUse, btnReturn, btnRefresh1, btnRefresh2;
    private JButton[] btnRoomList;
    private int heightTable = 140;
    private int location = -1;
    private JTable tblService, tblSC;
    private JComboBox<String> cboService;
    private TypeOfRoomDAO typeOfRoomDAO;
    private TypeOfServiceDAO typeOfServiceDAO;
    private ServiceDAO serviceDAO;
    private RoomDAO roomDAO;
    private BillDAO billDAO;
    private boolean isDoubleClick = false;
    private int selectedServiceIndex = -1;
    private int selectedServiceOrderIndex = -1;
    private ArrayList<Service> lstService = new ArrayList<Service>();
    private ArrayList<Service> serviceList = new ArrayList<Service>();
    private DetailsOfService detailsOfService;
    private DetailOfServiceDAO detailOfServiceDAO;
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
        billDAO = new BillDAO();
        detailOfServiceDAO = new DetailOfServiceDAO();

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
        txtFind.setBounds(106, 14, 140, 28);
        pnlRoomControl.add(txtFind);

        btnFindRoom = new JButton("Tìm");
        btnFindRoom.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnFindRoom);
        btnFindRoom.setBounds(260, 13, 90, 30);
        pnlRoomControl.add(btnFindRoom);

        btnRefresh1 = new JButton("Làm mới");
        btnRefresh1.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnRefresh1);
        btnRefresh1.setBounds(360, 13, 105, 30);
        pnlRoomControl.add(btnRefresh1);

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
        pnlServiceList.setBounds(520, 10, 730, 420);
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

        btnRefresh2 = new JButton("Làm mới");
        btnRefresh2.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnRefresh2);
        btnRefresh2.setBounds(460, 54, 105, 30);
        pnlServiceControl.add(btnRefresh2);

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
                selectedServiceIndex = row;

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
        scrShowService.getViewport().setOpaque(false);

        pnlServiceDetail = new JPanel();
        pnlServiceDetail.setLayout(null);
        pnlServiceDetail.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlServiceDetail.setBounds(13, 440, 710, 295);
        pnlServiceDetail.setOpaque(false);
        add(pnlServiceDetail);

        String[] colsService = {"STT","Tên dịch vụ", "Đơn vị tính", "Số lượng", "Giá bán", "Thành tiền"};
        modelService = new DefaultTableModel(colsService, 0);

        tblService = new JTable(modelService);
        Custom.getInstance().setCustomTable(tblService);

        pnlServiceDetail.add(scrShowServiceDetail = new JScrollPane(tblService, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        scrShowServiceDetail.setBounds(4, 10, 700, 280);
        scrShowServiceDetail.setOpaque(false);
        scrShowServiceDetail.getViewport().setOpaque(false);
        scrShowServiceDetail.getViewport().setBackground(Color.WHITE);

        pnlSelect = new JPanel();
        pnlSelect.setLayout(null);
        pnlSelect.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlSelect.setBounds(730, 440, 520, 290);
        pnlSelect.setOpaque(false);
        add(pnlSelect);

        nameLable = new JLabel("Dịch vụ: ");
        nameLable.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLable.setBounds(10, 17, 85, 20);
        nameLable.setForeground(Color.WHITE);
        pnlSelect.add(nameLable);

        txtName = new JTextField();
        txtName.setBounds(90, 13, 170, 28);
        txtName.setFont(new Font("Arial", Font.PLAIN, 14));
        pnlSelect.add(txtName);

        quantityLabel = new JLabel("Số lượng: ");
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        quantityLabel.setBounds(10, 63, 85, 20);
        quantityLabel.setForeground(Color.WHITE);
        pnlSelect.add(quantityLabel);

        txtQuantity = new JSpinner();
        txtQuantity.setBounds(90, 61, 170, 28);
        txtQuantity.setFont(new Font("Arial", Font.PLAIN, 14));
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) txtQuantity.getEditor();
        editor.getTextField().setHorizontalAlignment(JTextField.LEFT);
        pnlSelect.add(txtQuantity);

        stockLabel = new JLabel("SL tồn: ");
        stockLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        stockLabel.setBounds(10, 114, 85, 20);
        stockLabel.setForeground(Color.WHITE);
        pnlSelect.add(stockLabel);

        txtStock = new JTextField();
        txtStock.setBounds(90, 113, 170, 28);
        txtStock.setFont(new Font("Arial", Font.PLAIN, 14));
        pnlSelect.add(txtStock);

        sumLabel = new JLabel("Tổng tiền: ");
        sumLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        sumLabel.setBounds(10, 163, 85, 20);
        sumLabel.setForeground(Color.WHITE);
        pnlSelect.add(sumLabel);

        txtSum = new JTextField();
        txtSum.setBounds(90, 164, 170, 28);
        txtSum.setFont(new Font("Arial", Font.PLAIN, 14));
        pnlSelect.add(txtSum);

        btnUse = new JButton("Đặt dịch vụ");
        btnUse.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnUse);
        btnUse.setBounds(100, 230, 145, 30);
        pnlSelect.add(btnUse);

        returnLabel = new JLabel("SL trả: ");
        returnLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        returnLabel.setBounds(280, 17, 85, 20);
        returnLabel.setForeground(Color.WHITE);
        pnlSelect.add(returnLabel);

        txtReturn = new JSpinner();
        txtReturn.setBounds(340, 13, 150, 28);
        txtReturn.setFont(new Font("Arial", Font.PLAIN, 14));
        JSpinner.DefaultEditor editor2 = (JSpinner.DefaultEditor) txtReturn.getEditor();
        editor2.getTextField().setHorizontalAlignment(JTextField.LEFT);
        pnlSelect.add(txtReturn);

        btnReturn = new JButton("Trả hàng");
        btnReturn.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnReturn);
        btnReturn.setBounds(350, 55, 110, 30);
        pnlSelect.add(btnReturn);


        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);

        // Lấy danh sách các phòng có trạng thái "Đang sử dụng"
        ArrayList<Room> roomsInUse = roomDAO.getRoomsByStatus("Đang sử dụng");
        loadRoomList(roomsInUse);
        reSizeColumnTableService();
        reSizeColumnTableService2();
        loadCboService();
        btnFindService.addActionListener(this);
        btnFindRoom.addActionListener(this);
        btnRefresh1.addActionListener(this);
        btnRefresh2.addActionListener(this);
        btnUse.addActionListener(this);

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

        txtQuantity.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int row = tblSC.getSelectedRow();
                if (row != -1) {
                    int quantity = (int) txtQuantity.getValue();
                    int soLuongTon = Integer.parseInt(serviceModel.getValueAt(row, 3).toString().replace(",", "")); // Chuyển đổi số lượng từ chuỗi thành số
                    if (quantity > soLuongTon) {
                        txtQuantity.setValue(soLuongTon);
                    } else if(quantity < 0) {
                        txtQuantity.setValue(0);
                    } else {
                        double giaBan = Double.parseDouble(serviceModel.getValueAt(row, 4).toString().replace(",", "")); // Chuyển đổi giá từ chuỗi thành số
                        double sum = giaBan * quantity;
                        txtSum.setText(df.format(sum));
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
    private void loadRoomList(ArrayList<Room> listRoom) {
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

            btnRoomList[selection] = new JButton("");
            loadRoom(listRoom.get(i).getMaPhong());
            btnRoomList[selection].setBorder(lineGray);
            if ((i + 1) % 4 == 0) {
                heightTable += 110;
                pnlShowRoom.setPreferredSize(new Dimension(0, heightTable));
            }
            btnRoomList[selection].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (location != -1) {
                        btnRoomList[location].setBorder(lineGray);
                    }
                    txtFind.setText(roomID);
                    location = selection;
                    btnRoomList[selection].setBorder(lineRed);
                    showBillInfo(roomID);
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

    private void reSizeColumnTableService2() {
        TableColumnModel tcm = tblService.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(10);
        tcm.getColumn(1).setPreferredWidth(140);
        tcm.getColumn(2).setPreferredWidth(30);
        tcm.getColumn(3).setPreferredWidth(20);
        tcm.getColumn(4).setPreferredWidth(40);
        tcm.getColumn(5).setPreferredWidth(40);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        tcm.getColumn(0).setCellRenderer(leftRenderer);
        tcm.getColumn(3).setCellRenderer(rightRenderer);
        tcm.getColumn(4).setCellRenderer(rightRenderer);
        tcm.getColumn(5).setCellRenderer(rightRenderer);
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
        if (o.equals(btnFindRoom)) {
            String roomID = txtFind.getText().trim();
            if (roomID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa nhập mã phòng");
            } else {
                ArrayList roomInUse = roomDAO.getRoomsByRoomIdAndStatus(roomID, "Đang sử dụng");
                if (roomInUse.size() == 0) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy");
                } else {
                    loadRoomListByRoomID(roomID);
                }
            }
        }
        if (o.equals(btnRefresh1)) {
            txtFind.setText("");
            ArrayList<Room> roomsInUse = roomDAO.getRoomsByStatus("Đang sử dụng");
            loadRoomList(roomsInUse);
        }
        if (o.equals(btnRefresh2)) {
            txtFindService.setText("");
            txtName.setText("");
            txtStock.setText("");
            txtSum.setText("");
            txtQuantity.setValue(0);
            for (Service dv : serviceDAO.getAllDichVu()) {
                Object[] rowData = { dv.getMaDichVu(), dv.getTenDichVu(), dv.getDonViTinh(), dv.getSoLuongTon(), df.format(dv.getGiaBan())};
                serviceModel.addRow(rowData);
            }
            cboService.setSelectedIndex(0);
        }
        if (o.equals(btnUse)) {
            if (txtName.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "Bạn cần phải chọn dịch vụ");
            } else {
                int orderQuantity = (int) txtQuantity.getValue();
                if (isDoubleClick) {
                    orderQuantity = 1;
                    isDoubleClick = false;
                }
                String quantityInStockStr = txtStock.getText().replace(",", "").trim();
                int quantityInStock = Integer.parseInt(quantityInStockStr);
                String message = "";

                // Số lượng trong kho bé hơn không
                if (quantityInStock <= 0) {
                    message = "Dịch vụ đã hết";
                    JOptionPane.showMessageDialog(this, message, "Cảnh bảo", JOptionPane.ERROR_MESSAGE);
                } else {
                    String typeMessage = "Thêm";
                    Service service = new Service();
                    if (selectedServiceIndex != -1) {
                        service = serviceList.get(selectedServiceIndex);
                    } else if (selectedServiceOrderIndex != -1) {
                        service = lstService.get(selectedServiceOrderIndex);
                    }

                    String roomID = txtFind.getText().trim();
                    Bill bill = billDAO.getBillByRoomID(roomID);
                    boolean result = false;
                    message = typeMessage + " dịch vụ thất bại";
                    boolean isUpdate = false;
                    if (!bill.getMaHoaDon().equalsIgnoreCase("")) {
                        DetailsOfService serviceDetail = detailOfServiceDAO.getDetailsOfServiceByBillIdAndServiceId(bill.getMaHoaDon(),
                                service.getMaDichVu());
                        // nếu ctDichVu không tồn tại thì thêm mới
                        // nếu ctDichVu đã tồn tại thì cập nhật
                        // Thêm mới
                        int newOrderQuantity = 0;
                        if (serviceDetail == null) {
                            isUpdate = false;
                            double servicePrice = service.getGiaBan();
                            serviceDetail = new DetailsOfService(bill, service, orderQuantity, servicePrice);
                            result = detailOfServiceDAO.updateServiceDetail(serviceDetail, orderQuantity,
                                    bill.getMaHoaDon());
                            // cập nhật

                        } else {
                            int newQuantity = quantityInStock - orderQuantity;
                            newOrderQuantity = serviceDetail.getSoLuong() + orderQuantity;
                            if (serviceDetail.getSoLuong() > 0 && newQuantity >= 0) {
                                isUpdate = true;
                                serviceDetail.setSoLuong(newOrderQuantity);
                                result = detailOfServiceDAO.updateServiceDetail(serviceDetail, orderQuantity,
                                        bill.getMaHoaDon());
                            } else {
                                message = "Số lượng đặt phải nhỏ hơn số lượng hiện có. Vui lòng nhập lại";
                            }
                        }
                        // kiểm tra kết quả thêm, cập nhật
                        if (result) {
                            if (isUpdate) {
                                int lastIndex = lstService.size() - 1;
                                for (int i = 0; i < lastIndex; i++) {
                                    Service serviceOrder = lstService.get(i);
                                    if (service.getMaDichVu().equals(serviceOrder.getMaDichVu())) {
                                        serviceOrder.setSoLuongTon(newOrderQuantity);
                                        break;
                                    }
                                }
                            } else {
                                lstService.add(service);
                            }
                            int newQuantity = quantityInStock - orderQuantity;
                            showBillInfo(roomID);
                            txtQuantity.setValue(1);
                            txtStock.setText(String.valueOf(newQuantity));
                        } else {
                            JOptionPane.showMessageDialog(this, message);
                        }
                    } else {
                        message = "Hóa đơn không tồn tại";
                        JOptionPane.showMessageDialog(this, message);
                    }
                }

            }}
    }

    private Bill getBillForCurrentRoom() {
        String roomID = txtFind.getText().trim();
        for (Bill bill : billDAO.getAllBill()) {
            if (bill.getMaPhong().getMaPhong().equals(roomID)) {
                return bill;
            }
        }
        return null;
    }

    private void loadRoomListByRoomID(String roomID) {
        ArrayList<Room> dataList = new ArrayList<Room>();
        if (txtFind.getText().equalsIgnoreCase(roomID)){
            location = -1;
            dataList = roomDAO.getRoomsByRoomIdAndStatus(roomID, "Đang sử dụng");}

        loadRoomList(dataList);
    }

    private void showBillInfo(String maPhong) {
        ArrayList<DetailsOfService> dataList = detailOfServiceDAO.getDetailsOfServiceListByRoomId(maPhong);
        int i = 1;
        modelService.getDataVector().removeAllElements();
        modelService.fireTableDataChanged();
        Double totalPrice = 0.0;
        lstService.clear();
        for (DetailsOfService item : dataList) {
            Service service = item.getMaDichVu();
            lstService.add(service);
            // hiển thị lại phòng đã chọn lúc đầu
            if (selectedServiceOrderIndex <= -1) {
                if (selectedServiceOrderIndex == i) {
                    tblService.getSelectionModel().addSelectionInterval(i - 1, i - 1);
                }
            }
            String stt = df.format(i++);
            String totalPriceStr = df.format(item.getSoLuong()*item.getGiaBan());
            String priceStr = df.format(item.getGiaBan());
            String quantityStr = df.format(item.getSoLuong());
            modelService.addRow(new Object[] { stt, addSpaceToString(service.getTenDichVu()),addSpaceToString(service.getDonViTinh()),
                    addSpaceToString(quantityStr), addSpaceToString(priceStr), addSpaceToString(totalPriceStr) });
        }
    }

    private String addSpaceToString(String str) {
        return " " + str + " ";
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
