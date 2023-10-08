package UI.main_interface.component;

import ConnectDB.ConnectDB;
import DAOs.RoomDAO;
import DAOs.TypeOfRoomDAO;
import UI.CustomUI.Custom;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceForm_UI extends JPanel {
    private DefaultTableModel tableModel;
    private JPanel pnlShowRoom, pnlRoomList, pnlServiceList, pnlShowService, pnlServiceControl,  pnlServiceDetail, timeNow, pnlRoomControl;
    private JLabel backgroundLabel, timeLabel, searchLabel, searchServiceLable, tOSLabel;
    private JTextField txtFind, txtFindService;
    private JScrollPane scrShowRoom, scrShowService, scrShowServiceDetail;
    private JButton btnFindRoom, btnFindService;
    private JButton[] btnRoomList;
    private int heightTable = 140;
    private int location = -1;
    private JComboBox<String> cboRoomType;
    private TypeOfRoomDAO typeOfRoomDAO;
    private RoomDAO roomDAO;

    public ServiceForm_UI() {
        setLayout(null);
        setBounds(0, 0, 1175, 770);

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        typeOfRoomDAO = new TypeOfRoomDAO();
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
        pnlRoomList.setBounds(10, 70, 500, 400);
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
        pnlServiceList.setBounds(530, 10, 610, 460);
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

        cboRoomType = new JComboBox<String>();
        cboRoomType.addItem("Tất cả");
        cboRoomType.setBounds(126, 14, 170, 28);
        Custom.setCustomComboBox(cboRoomType);
        pnlServiceControl.add(cboRoomType);

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
        FlowLayout flShowRoom2 = new FlowLayout(FlowLayout.LEFT);
        flShowRoom2.setHgap(6);
        flShowRoom2.setVgap(6);
        pnlShowService.setLayout(flShowRoom2);
        pnlShowService.setPreferredSize(new Dimension(310, 140));


        scrShowService = new JScrollPane(pnlShowService, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrShowService.setOpaque(false);
        scrShowService.getViewport().setOpaque(false);
        scrShowService.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        scrShowService.setBackground(Color.WHITE);
        scrShowService.getVerticalScrollBar().setUnitIncrement(10);
        pnlServiceList.add(scrShowService, BorderLayout.CENTER);


        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);
    }

    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        timeLabel.setText(time);
    }

}
