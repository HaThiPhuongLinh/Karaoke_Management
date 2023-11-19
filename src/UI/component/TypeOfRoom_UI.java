package UI.component;

import ConnectDB.ConnectDB;
import DAO.TypeOfRoomDAO;
import Entity.Staff;
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

/**
 * Giao diện quản lý loại phòng
 * Người tham gia thiết kế: Hà Thị Phương Linh, Nguyễn Đình Dương
 * Ngày tạo: 12/10/2023
 * Lần cập nhật cuối: 14/11/2023
 * Nội dung cập nhật: fix chức năng xóa loại phòng
 */
public class TypeOfRoom_UI extends JPanel implements ActionListener, MouseListener {
    public static Staff staffLogin = null;
    private JTextField txtID, txtCapacity, txtError, txtName;
    private JButton btnAdd, btnFix, btnReFresh, btnDelete;
    private JTable tblTypeOfRoom;
    private DefaultTableModel modelTblTypeOfRoom;
    private JLabel lblBackGround, lblTime;
    private JPanel pnlTime, pnlTPList, pnlTPControl;
    private TypeOfRoomDAO typeOfRoomDAO;

    public TypeOfRoom_UI(Staff staff) {
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);
        typeOfRoomDAO = new TypeOfRoomDAO();
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel headerLabel = new JLabel("QUẢN LÝ LOẠI PHÒNG");
        headerLabel.setBounds(520, 10, 1175, 40);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);

        pnlTime = new JPanel();
        pnlTime.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
                TitledBorder.LEADING, TitledBorder.TOP));
        pnlTime.setBounds(12, 10, 300, 50);
        pnlTime.setOpaque(false);
        add(pnlTime);

        lblTime = new JLabel();
        lblTime.setFont(new Font("Arial", Font.BOLD, 33));
        lblTime.setForeground(Color.WHITE);
        pnlTime.add(lblTime);
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
        pnlTPList.setBounds(10, 70, 1240, 670);
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
        labelMaLPhong.setBounds(380, 10, 150, 30);
        labelMaLPhong.setForeground(Color.WHITE);
        pnlTPControl.add(labelMaLPhong);

        txtID = new JTextField();
        txtID.setBounds(495, 10, 311, 30);
        txtID.setColumns(10);
        pnlTPControl.add(txtID);

        JLabel labelTenLoaiPhong = new JLabel("Tên loại phòng:");
        labelTenLoaiPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTenLoaiPhong.setBounds(380, 60, 150, 30);
        labelTenLoaiPhong.setForeground(Color.WHITE);
        pnlTPControl.add(labelTenLoaiPhong);

        txtName = new JTextField();
        txtName.setBounds(495, 60, 311, 30);
        txtName.setColumns(10);
        pnlTPControl.add(txtName);

        JLabel labelSucChua = new JLabel("Sức chứa :");
        labelSucChua.setFont(new Font("Arial", Font.PLAIN, 14));
        labelSucChua.setBounds(380, 110, 150, 30);
        labelSucChua.setForeground(Color.WHITE);
        pnlTPControl.add(labelSucChua);

        txtCapacity = new JTextField();
        txtCapacity.setBounds(495, 110, 311, 30);
        txtCapacity.setColumns(10);
        pnlTPControl.add(txtCapacity);

        txtError = new JTextField();
        txtError.setFont(new Font("Arial", Font.BOLD, 13));
        txtError.setForeground(Color.RED);
        txtError.setBounds(380, 160, 446, 30);
        txtError.setColumns(10);
        pnlTPControl.add(txtError);

        btnAdd = new JButton("Thêm");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnAdd);
        btnAdd.setBounds(390, 210, 100, 30);
        pnlTPControl.add(btnAdd);

        btnDelete = new JButton("Xóa");
        btnDelete.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnDelete);
        btnDelete.setBounds(500, 210, 100, 30);
        pnlTPControl.add(btnDelete);

        btnFix = new JButton("Sửa");
        btnFix.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnFix);
        btnFix.setBounds(610, 210, 100, 30);
        pnlTPControl.add(btnFix);

        btnReFresh = new JButton("Làm mới");
        btnReFresh.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnReFresh);
        btnReFresh.setBounds(720, 210, 100, 30);
        pnlTPControl.add(btnReFresh);

        JPanel panelDSLP = new JPanel();
        panelDSLP.setLayout(null);
        panelDSLP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH LOẠI PHÒNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSLP.setBounds(30, 290, 1220, 370);
        panelDSLP.setOpaque(false);

        String[] colsLP = {"STT", "Mã Loại Phòng", "Tên Loại Phòng", "Sức Chứa"};
        modelTblTypeOfRoom = new DefaultTableModel(colsLP, 0);
        JScrollPane scrollPaneNV;

        tblTypeOfRoom = new JTable(modelTblTypeOfRoom);
        tblTypeOfRoom.setFont(new Font("Arial", Font.BOLD, 14));
        tblTypeOfRoom.setBackground(new Color(255, 255, 255, 0));
        tblTypeOfRoom.setForeground(new Color(255, 255, 255));
        tblTypeOfRoom.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblTypeOfRoom.getTableHeader().setForeground(Color.BLUE);

        Custom.getInstance().setCustomTable(tblTypeOfRoom);

        panelDSLP.add(scrollPaneNV = new JScrollPane(tblTypeOfRoom, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneNV.setBounds(10, 20, 1210, 380);
        scrollPaneNV.setOpaque(false);
        scrollPaneNV.getViewport().setOpaque(false);
        scrollPaneNV.getViewport().setBackground(Color.WHITE);
        pnlTPList.add(panelDSLP);
        loadLP();
        btnAdd.addActionListener(this);
        btnReFresh.addActionListener(this);
        btnFix.addActionListener(this);
        btnDelete.addActionListener(this);
        tblTypeOfRoom.addMouseListener(this);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        lblBackGround = new JLabel(backgroundImage);
        lblBackGround.setBounds(0, 0, getWidth(), getHeight());
        add(lblBackGround);
    }

    /**
     * Cập nhật thời gian thực cho lblTime
     */
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        lblTime.setText(time);
    }

    /**
     * Load danh sách loại phòng lên bảng
     */
    public void loadLP() {
        int i = 1;
        for (TypeOfRoom room : typeOfRoomDAO.getAllLoaiPhong()) {
            Object[] rowData = {i, room.getMaLoaiPhong(), room.getTenLoaiPhong(), room.getSucChua()};
            modelTblTypeOfRoom.addRow(rowData);
            i++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnAdd)) {
            if (txtName.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin loại phòng");
            } else if (validData()) {
                String malp = txtID.getText();
                String tenlp = txtName.getText().trim();
                int succhua = Integer.parseInt(txtCapacity.getText());

                TypeOfRoom type = new TypeOfRoom(malp, tenlp, succhua);
                if (typeOfRoomDAO.insert(type)) {
                    modelTblTypeOfRoom.getDataVector().removeAllElements();
                    loadLP();
                    JOptionPane.showMessageDialog(this, "Thêm loại phòng thành công");
                }
            }
        } else if (o.equals(btnDelete)) {
            int row = tblTypeOfRoom.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng cần xóa");
            } else {
                String MaLP = txtID.getText().trim();
                String tenLp = txtName.getText().trim();
                int succhua = Integer.parseInt(txtCapacity.getText().trim());
                TypeOfRoom type = new TypeOfRoom(MaLP, tenLp, succhua);
                int ans = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Cảnh báo",
                        JOptionPane.YES_NO_OPTION);
                if (ans == JOptionPane.YES_OPTION) {
                    if (typeOfRoomDAO.checkIfTypeOfRoomIsReferenced(type.getMaLoaiPhong())) {
                        JOptionPane.showMessageDialog(this, "Loại phòng đang được sử dụng! Không được phép xóa");
                    } else {
                        if (typeOfRoomDAO.delete(type.getMaLoaiPhong())) {
                            txtID.setText("");
                            txtName.setText("");
                            txtError.setText("");
                            modelTblTypeOfRoom.removeRow(row);
                            JOptionPane.showMessageDialog(this, "Xóa thành công");
                            modelTblTypeOfRoom.getDataVector().removeAllElements();
                            loadLP();
                        } else {
                            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi xóa. Vui lòng thử lại sau.");
                        }
                    }
                }
            }
        } else if (o.equals(btnFix)) {
            int row = tblTypeOfRoom.getSelectedRow();
            if (row >= 0) {
                if (validData()) {
                    String MaLP = txtID.getText().trim();
                    String tenLp = txtName.getText().trim();
                    int succhua = Integer.parseInt(txtCapacity.getText().trim());
                    TypeOfRoom type = new TypeOfRoom(MaLP, tenLp, succhua);

                    if (typeOfRoomDAO.update(type)) {
                        tblTypeOfRoom.setValueAt(txtName.getText(), row, 2);
                        tblTypeOfRoom.setValueAt(txtCapacity.getText(), row, 3);
                        JOptionPane.showMessageDialog(this, "Sửa thành công");
                    } else {
                        JOptionPane.showMessageDialog(this, "Không được phép sửa mã");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng cần sửa");
            }
        } else if (o.equals(btnReFresh)) {
            txtID.setText("");
            txtName.setText("");
            txtError.setText("");
            txtCapacity.setText("");
            modelTblTypeOfRoom.getDataVector().removeAllElements();
            loadLP();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(tblTypeOfRoom)) {
            int row = tblTypeOfRoom.getSelectedRow();
            txtID.setText(modelTblTypeOfRoom.getValueAt(row, 1).toString());
            txtName.setText(modelTblTypeOfRoom.getValueAt(row, 2).toString());
            txtCapacity.setText(modelTblTypeOfRoom.getValueAt(row, 3).toString());

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

    /**
     * Hiển thị lỗi
     *
     * @param txt:     JTextField lỗi
     * @param message: thông báo lỗi
     */
    private void showMessage(JTextField txt, String message) {
        txt.requestFocus();
        txtError.setText(message);
    }

    /**
     * Kiểm tra dữ liệu hợp lệ
     *
     * @return (@ Code boolean): true/false
     */
    private boolean validData() {
        String ten = txtName.getText().trim();
        String succhua = txtCapacity.getText().trim();
        if (!((ten.length() > 0) && ten.matches("^[A-Za-z0-9À-ỹ ]+"))) {
            showMessage(txtName, "Error: Tên loại phòng không được chứa số và kí tự đặc biệt");
            return false;
        }
        if (!((succhua.length() > 0) && succhua.matches("^[1-9]\\d*"))) {
            showMessage(txtCapacity, "Error: Sức chứa  phải là số lớn hơn 0");
            return false;
        }
        return true;
    }
}
