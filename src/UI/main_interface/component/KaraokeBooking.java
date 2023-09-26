package UI.main_interface.component;

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

/**
 *
 */
public class KaraokeBooking extends JPanel {
    private DefaultTableModel tableModel;
    private JPanel pnlShowRoom, pnlRoomList, timeNow, pnlRoomControl, pnlShowCustomer, pnlShowDetails;
    private JLabel backgroundLabel, timeLabel, roomLabel, statusLabel, customerLabel;
    private JTextField txtCustomer;
    private JScrollPane scrShowRoom;
    private JButton btnSwitchRoom, btnBookRoom, btnPresetRoom, btnCancelRoom, btnReceiveRoom, btnChooseCustomer;
    private JComboBox<String> cboRoomType, cboStatus;

    public KaraokeBooking() {
        setLayout(null);
        setBounds(0, 0, 1175, 770);

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
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Phòng",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlRoomList.setBounds(10, 150, 750, 540);
        pnlRoomList.setOpaque(false);
        add(pnlRoomList);
        pnlRoomList.setLayout(new BorderLayout(0, 0));

        pnlRoomControl = new JPanel();
        pnlRoomControl.setOpaque(false);
        pnlRoomControl.setBackground(Color.WHITE);
        pnlRoomList.add(pnlRoomControl, BorderLayout.NORTH);
        pnlRoomControl.setLayout(null);
        pnlRoomControl.setPreferredSize(new Dimension(330, 55));

        roomLabel = new JLabel("Loại phòng: ");
        roomLabel.setFont( new Font("Arial", Font.PLAIN, 14));
        roomLabel.setBounds(20, 17, 85, 20);
        roomLabel.setForeground(Color.WHITE);
        pnlRoomControl.add(roomLabel);

        cboRoomType = new JComboBox<String>();
        cboRoomType.addItem("Thường");
        cboRoomType.addItem("VIP");
        cboRoomType.setBounds(106, 14, 140, 28);
        Custom.setCustomComboBox(cboRoomType);
        pnlRoomControl.add(cboRoomType);

        btnSwitchRoom = new JButton("Đổi phòng");
        btnSwitchRoom.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnSwitchRoom);
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/transfer_16.png"));
        btnSwitchRoom.setIcon(icon);
        btnSwitchRoom.setBounds(260, 12, 125, 30);
        pnlRoomControl.add(btnSwitchRoom);

        statusLabel = new JLabel("Trạng thái: ");
        statusLabel.setFont( new Font("Arial", Font.PLAIN, 14));
        statusLabel.setBounds(450, 17, 85, 20);
        statusLabel.setForeground(Color.WHITE);
        pnlRoomControl.add(statusLabel);

        cboStatus = new JComboBox<String>();
        cboStatus.addItem("ALL");
        cboStatus.addItem("Trống");
        cboStatus.addItem("Đang sử dụng");
        cboStatus.addItem("Chờ");
        cboStatus.setBounds(550, 14, 140, 28);
        Custom.setCustomComboBox(cboStatus);
        pnlRoomControl.add(cboStatus);

        scrShowRoom = new JScrollPane(pnlShowRoom, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrShowRoom.setOpaque(false);
        scrShowRoom.getViewport().setOpaque(false);
        scrShowRoom.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        scrShowRoom.setBackground(Color.WHITE);
        scrShowRoom.getVerticalScrollBar().setUnitIncrement(10);
        pnlRoomList.add(scrShowRoom, BorderLayout.CENTER);

        pnlShowCustomer = new JPanel();
        pnlShowCustomer.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlShowCustomer.setBounds(330,10,805,130);
        pnlShowCustomer.setOpaque(false);
        pnlShowCustomer.setLayout(null);
        add(pnlShowCustomer);

        customerLabel = new JLabel("Khách hàng: ");
        customerLabel.setFont( new Font("Arial", Font.PLAIN, 14));
        customerLabel.setBounds(20, 30, 85, 20);
        customerLabel.setForeground(Color.WHITE);
        pnlShowCustomer.add(customerLabel);

        txtCustomer = new JTextField();
        txtCustomer.setBounds(120, 27, 250, 30);
        pnlShowCustomer.add(txtCustomer);

        btnChooseCustomer = new JButton("Chọn khách hàng");
        btnChooseCustomer.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnChooseCustomer);
        btnChooseCustomer.setBounds(120, 80, 175, 30);
        pnlShowCustomer.add(btnChooseCustomer);

        btnBookRoom = new JButton("Đặt phòng");
        btnBookRoom.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnBookRoom);
        btnBookRoom.setBounds(440, 30, 155, 30);
        pnlShowCustomer.add(btnBookRoom);

        btnPresetRoom = new JButton("Đặt phòng chờ");
        btnPresetRoom.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnPresetRoom);
        btnPresetRoom.setBounds(440, 75, 155, 30);
        pnlShowCustomer.add(btnPresetRoom);

        btnReceiveRoom = new JButton("Nhận phòng");
        btnReceiveRoom.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnReceiveRoom);
        btnReceiveRoom.setBounds(610, 30, 155, 30);
        pnlShowCustomer.add(btnReceiveRoom);

        btnCancelRoom = new JButton("Hủy phòng");
        btnCancelRoom.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnCancelRoom);
        btnCancelRoom.setBounds(610, 75, 155, 30);
        pnlShowCustomer.add(btnCancelRoom);

        pnlShowDetails = new JPanel();
        pnlShowDetails.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Chi tiết",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlShowDetails.setBounds(770,150,370,540);
        pnlShowDetails.setOpaque(false);
        add(pnlShowDetails);

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

