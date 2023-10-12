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

public class SearchingStaff_UI extends JPanel {

    private JLabel backgroundLabel, timeLabel, search1Label, search2Label, search3Label, search4Label;
    private JPanel timeNow, pnlStaffList, pnlStaffControl, panelDSNV;
    private DefaultTableModel tableModelNV;
    private JComboBox<String> cboTinhTrang;
    private JCheckBox cb;
    private JTextField txtSearch1, txtSearch2, txtSearch3;
    private JButton btnTim;

    public SearchingStaff_UI() {
        setLayout(null);
        setBounds(0, 0, 1175, 770);

        JLabel headerLabel = new JLabel("TÌM KIẾM NHÂN VIÊN");
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

        pnlStaffList = new JPanel();
        pnlStaffList.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Nhân Viên",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlStaffList.setBounds(10, 70, 1120, 620);
        pnlStaffList.setOpaque(false);
        add(pnlStaffList);
        pnlStaffList.setLayout(new BorderLayout(0, 0));

        pnlStaffControl = new JPanel();
        pnlStaffControl.setOpaque(false);
        pnlStaffControl.setBackground(Color.WHITE);
        pnlStaffList.add(pnlStaffControl, BorderLayout.NORTH);
        pnlStaffControl.setLayout(null);
        pnlStaffControl.setPreferredSize(new Dimension(1100, 250));

        search1Label = new JLabel("Tìm Theo Tên: ");
        search1Label.setFont(new Font("Arial", Font.PLAIN, 14));
        search1Label.setBounds(330, 15, 120, 30);
        search1Label.setForeground(Color.WHITE);
        pnlStaffControl.add(search1Label);

        txtSearch1 = new JTextField();
        txtSearch1.setBounds(465, 15, 280, 30);
        pnlStaffControl.add(txtSearch1);

        btnTim = new JButton("Tìm");
        btnTim.setBounds(645, 205, 100, 30);
        Custom.setCustomBtn(btnTim);
        btnTim.setFont(new Font("Arial", Font.PLAIN, 14));
        pnlStaffControl.add(btnTim);

        search2Label = new JLabel("Tìm Theo SDT: ");
        search2Label.setFont(new Font("Arial", Font.PLAIN, 14));
        search2Label.setBounds(330, 65, 120, 30);
        search2Label.setForeground(Color.WHITE);
        pnlStaffControl.add(search2Label);

        txtSearch2 = new JTextField();
        txtSearch2.setBounds(465, 65, 280, 30);
        pnlStaffControl.add(txtSearch2);

        search3Label = new JLabel("Tìm Theo CCCD: ");
        search3Label.setFont(new Font("Arial", Font.PLAIN, 14));
            search3Label.setBounds(330, 115, 120, 30);
        search3Label.setForeground(Color.WHITE);
        pnlStaffControl.add(search3Label);

        txtSearch3 = new JTextField();
        txtSearch3.setBounds(465, 115, 280, 30);
        pnlStaffControl.add(txtSearch3);

        search4Label = new JLabel("Tình Trạng: ");
        search4Label.setFont(new Font("Arial", Font.PLAIN, 14));
        search4Label.setBounds(330, 165, 120, 30);
        search4Label.setForeground(Color.WHITE);
        pnlStaffControl.add(search4Label);

        cboTinhTrang = new JComboBox<>();
        cboTinhTrang.addItem("Đang làm");
        cboTinhTrang.addItem("Đã nghỉ");
        cboTinhTrang.setBounds(465, 165, 280, 30);
        pnlStaffControl.add(cboTinhTrang);

        cb = new JCheckBox();
        cb.setBounds(760, 174, 20, 20);
        pnlStaffControl.add(cb);

        txtSearch1 = new JTextField();
        txtSearch1.setBounds(465, 15, 280, 30);
        pnlStaffControl.add(txtSearch1);


        JPanel panelDSNV = new JPanel();
        panelDSNV.setLayout(null);
        panelDSNV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH NHÂN VIÊN",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSNV.setBounds(30, 290, 1100, 320);
        panelDSNV.setOpaque(false);

        String[] colsNV = {"STT", "Mã NV", "Tên NV", "SDT", "CCCD", "Giới Tính", "Ngày Sinh", "Chức Vụ", "Tình Trạng", "Tài Khoản","Địa Chỉ"};
        DefaultTableModel modelTableNV = new DefaultTableModel(colsNV, 0);
        JScrollPane scrollPaneNV;

        JTable tableNV = new JTable(modelTableNV);
        tableNV.setFont(new Font("Arial", Font.BOLD, 14));
        tableNV.setBackground(new Color(255, 255, 255, 0));
        tableNV.setForeground(new Color(255, 255, 255));
        tableNV.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableNV.getTableHeader().setForeground(Color.BLUE);

        panelDSNV.add(scrollPaneNV = new JScrollPane(tableNV, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneNV.setBounds(10, 20, 1090, 330);
        scrollPaneNV.setOpaque(false);
        scrollPaneNV.getViewport().setOpaque(false);
        scrollPaneNV.getViewport().setBackground(Color.WHITE);
        pnlStaffList.add(panelDSNV);

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
