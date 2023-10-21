package UI.component;

import ConnectDB.ConnectDB;
import DAO.TypeOfRoomDAO;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class TypeOfRoom_UI extends JPanel implements ActionListener, MouseListener {
    private  JTextField txtBaoLoi;
    private  JTextField txtTenLoaiPhong;
    private  JTextField txtMaLoaiPhong, txtSucChua;
    private  JButton btnlamMoi;
    private  JButton btnXoa;
    private  JButton btnThem,btnSua;
    private  JTable tableLP;

    private  DefaultTableModel modelTableLP;
    private JLabel backgroundLabel, timeLabel ;
    private JPanel timeNow, pnlTPList, pnlTPControl;

    private TypeOfRoomDAO TypeOfRoomDAO;

    public TypeOfRoom_UI() {
        setLayout(null);
        setBounds(0, 0, 1175, 770);
        TypeOfRoomDAO = new TypeOfRoomDAO();
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel headerLabel = new JLabel("QUẢN LÝ LOẠI PHÒNG");
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

        pnlTPList = new JPanel();
        pnlTPList.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Loại Phòng",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlTPList.setBounds(10, 70, 1120, 620);
        pnlTPList.setOpaque(false);
        add(pnlTPList);
        pnlTPList.setLayout(new BorderLayout(0, 0));

        pnlTPControl = new JPanel();
        pnlTPControl.setOpaque(false);
        pnlTPControl.setBackground(Color.WHITE);
        pnlTPList.add(pnlTPControl, BorderLayout.NORTH);
        pnlTPControl.setLayout(null);
        pnlTPControl.setPreferredSize(new Dimension(1100, 250));

        JLabel labelMaLPhong = new JLabel("Mã loại Phòng:");
        labelMaLPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        labelMaLPhong.setBounds(330, 10, 150, 30);
        labelMaLPhong.setForeground(Color.WHITE);
        pnlTPControl.add(labelMaLPhong);

        txtMaLoaiPhong = new JTextField();
        txtMaLoaiPhong.setBounds(465, 10, 311, 30);
        txtMaLoaiPhong.setColumns(10);
        pnlTPControl.add(txtMaLoaiPhong);

//      Tên loại dịch vụ
        JLabel labelTenLoaiPhong = new JLabel("Tên loại phòng:");
        labelTenLoaiPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTenLoaiPhong.setBounds(330, 60, 150, 30);
        labelTenLoaiPhong.setForeground(Color.WHITE);
        pnlTPControl.add(labelTenLoaiPhong);

        txtTenLoaiPhong = new JTextField();
        txtTenLoaiPhong.setBounds(465, 60, 311, 30);
        txtTenLoaiPhong.setColumns(10);
        pnlTPControl.add(txtTenLoaiPhong);

        JLabel labelSucChua = new JLabel("Sức chứa :");
        labelSucChua.setFont(new Font("Arial", Font.PLAIN, 14));
        labelSucChua.setBounds(330, 110, 150, 30);
        labelSucChua.setForeground(Color.WHITE);
        pnlTPControl.add(labelSucChua);

        txtSucChua = new JTextField();
        txtSucChua.setBounds(465, 110, 311, 30);
        txtSucChua.setColumns(10);
        pnlTPControl.add(txtSucChua);




        txtBaoLoi = new JTextField();
        txtBaoLoi.setFont(new Font("Arial",Font.BOLD,13));
        txtBaoLoi.setForeground(Color.RED);
        txtBaoLoi.setBounds(330, 160, 446, 30);
        txtBaoLoi.setColumns(10);
        pnlTPControl.add(txtBaoLoi);






        //        btn thêm
        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThem);
        btnThem.setBounds(340, 210, 100, 30);
        pnlTPControl.add(btnThem);

        //        btn Xóa
        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnXoa);
        btnXoa.setBounds(450, 210, 100, 30);
        pnlTPControl.add(btnXoa);

        //        btn sửa
        btnSua = new JButton("Sửa");
        btnSua.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnSua);
        btnSua.setBounds(560, 210, 100, 30);
        pnlTPControl.add(btnSua);

        //        btn làm mới
        btnlamMoi = new JButton("Làm mới");
        btnlamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnlamMoi);
        btnlamMoi.setBounds(670, 210, 100, 30);
        pnlTPControl.add(btnlamMoi);


        JPanel panelDSLP = new JPanel();
        panelDSLP.setLayout(null);
        panelDSLP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH LOẠI PHÒNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSLP.setBounds(30, 290, 1100, 320);
        panelDSLP.setOpaque(false);

        String[] colsLP = {"STT", "Mã Loại Phòng", "Tên Loại Phòng", "Sức Chứa"};
        modelTableLP = new DefaultTableModel(colsLP, 0);
        JScrollPane scrollPaneNV;

        tableLP = new JTable(modelTableLP);
        tableLP.setFont(new Font("Arial", Font.BOLD, 14));
        tableLP.setBackground(new Color(255, 255, 255, 0));
        tableLP.setForeground(new Color(255, 255, 255));
        tableLP.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableLP.getTableHeader().setForeground(Color.BLUE);

        Custom.getInstance().setCustomTable(tableLP);

        panelDSLP.add(scrollPaneNV = new JScrollPane(tableLP, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneNV.setBounds(10, 20, 1090, 330);
        scrollPaneNV.setOpaque(false);
        scrollPaneNV.getViewport().setOpaque(false);
        scrollPaneNV.getViewport().setBackground(Color.WHITE);
        pnlTPList.add(panelDSLP);
        loadLP();
        btnThem.addActionListener(this);
        btnlamMoi.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        tableLP.addMouseListener(this);

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

    public void loadLP(){
        int i=1;
        for (TypeOfRoom room : TypeOfRoomDAO.getAllLoaiPhong()) {
            Object[] rowData = { i,room.getMaLoaiPhong(),room.getTenLoaiPhong(),room.getSucChua()};
            modelTableLP.addRow(rowData);
            i++;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        Object o = e.getSource();
        if(o.equals(btnThem)){
            if (txtTenLoaiPhong.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Bạn phải nhập thông tin loại phòng");
            } else
            if (validData()){
                String malp = txtMaLoaiPhong.getText();
                String tenlp = txtTenLoaiPhong.getText().trim();
                int succhua =Integer.parseInt(txtSucChua.getText());

                TypeOfRoom type = new TypeOfRoom(malp,tenlp,succhua);
                if (TypeOfRoomDAO.insert(type)) {
                    modelTableLP.getDataVector().removeAllElements();
                    loadLP();
                    JOptionPane.showMessageDialog(this,"Thêm loại phòng thành công");
                }
            }
        }else if(o.equals(btnXoa)){
            int row = tableLP.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng cần xóa");
            } else {
                String MaLP = txtMaLoaiPhong.getText().trim();
                String tenLp = txtTenLoaiPhong.getText().trim();
                int succhua =Integer.parseInt(txtSucChua.getText().trim());
                TypeOfRoom type = new TypeOfRoom(MaLP,tenLp,succhua);
                int ans = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Cảnh báo",
                        JOptionPane.YES_NO_OPTION);
                if (ans == JOptionPane.YES_OPTION) {
                    TypeOfRoomDAO.delete(type.getMaLoaiPhong());
                    txtMaLoaiPhong.setText("");
                    txtTenLoaiPhong.setText("");
                    txtBaoLoi.setText("");
                    modelTableLP.removeRow(row);
                    JOptionPane.showMessageDialog(this,"Xóa thành công");
                    modelTableLP.getDataVector().removeAllElements();
                    loadLP();
                }
            }
        }else if (o.equals(btnSua)){
            int row = tableLP.getSelectedRow();
            if (row >= 0) {
                if (validData()) {
                    String MaLP = txtMaLoaiPhong.getText().trim();
                    String tenLp = txtTenLoaiPhong.getText().trim();
                    int succhua =Integer.parseInt(txtSucChua.getText().trim());
                    TypeOfRoom type = new TypeOfRoom(MaLP,tenLp,succhua);
                    if (TypeOfRoomDAO.update(type)) {
                        tableLP.setValueAt(txtTenLoaiPhong.getText(), row, 2);
                        tableLP.setValueAt(txtSucChua.getText(), row, 3);
                        JOptionPane.showMessageDialog(this,"Sửa thành công");
                    }
                }
            }else {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng cần sửa");
            }
        }else if(o.equals(btnlamMoi)){
            txtMaLoaiPhong.setText("");
            txtTenLoaiPhong.setText("");
            txtBaoLoi.setText("");
            txtSucChua.setText("");
            modelTableLP.getDataVector().removeAllElements();
            loadLP();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(tableLP)) {
            int row = tableLP.getSelectedRow();
            txtMaLoaiPhong.setText(modelTableLP.getValueAt(row,1).toString());
            txtTenLoaiPhong.setText(modelTableLP.getValueAt(row,2).toString());
            txtSucChua.setText(modelTableLP.getValueAt(row,3).toString());

        }

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
        String ten = txtTenLoaiPhong.getText().trim();
        String succhua= txtSucChua.getText().trim();
        if (!((ten.length() > 0) && ten.matches("^[A-Za-z0-9À-ỹ ]+"))) {
            showMessage(txtTenLoaiPhong,"Error: Tên loại phòng không được chứa số và kí tự đặc biệt");
            return false;
        }
        if(!((succhua.length() > 0) && succhua.matches("^[1-9]\\d*"))){
            showMessage(txtSucChua,"Error: Sức chứa  phải là số lớn hơn 0");
            return false;
        }


        return true;
    }
}
