package UI.component;

import ConnectDB.ConnectDB;
import DAO.TypeOfServiceDAO;
import Entity.Staff;
import Entity.TypeOfService;
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
 * Sử dụng để quản lý loại dịch vụ
 * <p>
 * Người tham gia thiết kế: Nguyễn Quang Duy
 * </p>
 * ngày tạo: 12/10/2023
 * <p>
 * Lần cập nhật cuối: 14/11/2023
 * </p>
 * Nội dung cập nhật: fix chức năng xóa loại dịch vụ
 */
public class TypeOfService_UI extends JPanel implements ActionListener, MouseListener {
    public static Staff staffLogin = null;
    private JTable tblDichVu;
    private JTextField txtTenLoaiDichVu, txtMaLoaiDichVu, txtThongBao;
    private JButton btnThem, btnXoa, btnSua, btnLamMoi;
    private DefaultTableModel modelTblDichVu;
    private JLabel lblBackGround, lblTime;
    private TypeOfServiceDAO typeOfServiceDAO;

    public TypeOfService_UI(Staff staff) {
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        typeOfServiceDAO = new TypeOfServiceDAO();

        JLabel lblQuanLyLDV = new JLabel("QUẢN LÝ LOẠI DỊCH VỤ");
        lblQuanLyLDV.setBounds(570, 10, 1175, 40);
        lblQuanLyLDV.setFont(new Font("Arial", Font.BOLD, 25));
        lblQuanLyLDV.setForeground(Color.WHITE);

        JPanel pnlTimeNow = new JPanel();
        pnlTimeNow.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
                TitledBorder.LEADING, TitledBorder.TOP));
        pnlTimeNow.setBounds(12, 10, 300, 50);
        pnlTimeNow.setOpaque(false);
        add(pnlTimeNow);

        lblTime = new JLabel();
        lblTime.setFont(new Font("Arial", Font.BOLD, 33));
        lblTime.setForeground(Color.WHITE);
        pnlTimeNow.add(lblTime);
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();

        JPanel pnlDichVu = new JPanel();
        pnlDichVu.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlDichVu.setBounds(10, 70, 1245, 670);
        pnlDichVu.setOpaque(false);
        add(pnlDichVu);

        pnlDichVu.setLayout(null);

        JLabel lblMaDichVu = new JLabel("Mã loại dịch vụ:");
        lblMaDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMaDichVu.setBounds(400, 50, 150, 30);
        lblMaDichVu.setForeground(Color.WHITE);
        pnlDichVu.add(lblMaDichVu);

        txtMaLoaiDichVu = new JTextField();
        txtMaLoaiDichVu.setBounds(510, 50, 270, 30);
        txtMaLoaiDichVu.setColumns(10);
        pnlDichVu.add(txtMaLoaiDichVu);

        JLabel lblTenDichVu = new JLabel("Tên loại dịch vụ:");
        lblTenDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTenDichVu.setBounds(400, 100, 150, 30);
        lblTenDichVu.setForeground(Color.WHITE);
        pnlDichVu.add(lblTenDichVu);

        txtTenLoaiDichVu = new JTextField();
        txtTenLoaiDichVu.setBounds(510, 100, 270, 30);
        txtTenLoaiDichVu.setColumns(10);
        pnlDichVu.add(txtTenLoaiDichVu);

        txtThongBao = new JTextField();
        txtThongBao.setFont(new Font("Arial", Font.BOLD, 13));
        txtThongBao.setForeground(Color.RED);
        txtThongBao.setBounds(20, 160, 370, 30);
        txtThongBao.setColumns(10);
        pnlDichVu.add(txtThongBao);

        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThem);
        btnThem.setBounds(450, 160, 100, 30);
        pnlDichVu.add(btnThem);

        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnXoa);
        btnXoa.setBounds(570, 160, 100, 30);
        pnlDichVu.add(btnXoa);

        btnSua = new JButton("Sửa");
        btnSua.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnSua);
        btnSua.setBounds(690, 160, 100, 30);
        pnlDichVu.add(btnSua);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnLamMoi);
        btnLamMoi.setBounds(810, 160, 100, 30);
        pnlDichVu.add(btnLamMoi);

        JPanel pnlDSDV = new JPanel();
        pnlDSDV.setLayout(null);
        pnlDSDV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlDSDV.setBounds(5, 210, 1235, 455);
        pnlDSDV.setOpaque(false);

        String[] colsDV = {"STT", "Mã loại dịch vụ", "Tên loại dịch vụ"};
        modelTblDichVu = new DefaultTableModel(colsDV, 0);
        JScrollPane scrDichVu;

        tblDichVu = new JTable(modelTblDichVu);
        tblDichVu.setFont(new Font("Arial", Font.BOLD, 14));
        tblDichVu.setBackground(new Color(255, 255, 255, 0));
        tblDichVu.setForeground(new Color(255, 255, 255));
        tblDichVu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblDichVu.getTableHeader().setForeground(Color.BLUE);
        Custom.getInstance().setCustomTable(tblDichVu);

        pnlDSDV.add(scrDichVu = new JScrollPane(tblDichVu, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrDichVu.setBounds(10, 20, 1220, 425);
        scrDichVu.setOpaque(false);
        scrDichVu.getViewport().setOpaque(false);
        scrDichVu.getViewport().setBackground(Color.WHITE);
        pnlDichVu.add(pnlDSDV);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        lblBackGround = new JLabel(backgroundImage);
        lblBackGround.setBounds(0, 0, getWidth(), getHeight());
        add(lblBackGround);

        btnThem.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        tblDichVu.addMouseListener(this);
        txtMaLoaiDichVu.setEditable(false);

        loadTypeOfService();
    }

    /**
     * Load thông tin dịch vụ lên tblDichVu
     */
    private void loadTypeOfService() {
        java.util.List<TypeOfService> list = typeOfServiceDAO.getAllLoaiDichVu();
        int i = 1;
        for (TypeOfService typeOfService : list) {
            modelTblDichVu.addRow(new Object[]{i, typeOfService.getMaLoaiDichVu(), typeOfService.getTenLoaiDichVu()});
            i++;
        }
    }

    /**
     * Gán thời gian hiện tại cho label lblTime
     */
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        lblTime.setText(time);
    }

    /**
     * Lấy mã loại dịch vụ
     *
     * @return maLDV
     */
    public String getMaLoaiDichVu() {
        String MaLDV = typeOfServiceDAO.generateNextTypeOfServiceId();
        return MaLDV;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThem)) {
            if (txtTenLoaiDichVu.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin loại dịch vụ");
            } else if (validData()) {
                String MaLDV = getMaLoaiDichVu();
                String tenldv = txtTenLoaiDichVu.getText().trim();
                TypeOfService type = new TypeOfService(MaLDV, tenldv);
                if (typeOfServiceDAO.insert(type)) {
                    modelTblDichVu.getDataVector().removeAllElements();
                    loadTypeOfService();
                    JOptionPane.showMessageDialog(this, "Thêm loại dịch vụ thành công");
                    txtMaLoaiDichVu.setText("");
                    txtTenLoaiDichVu.setText("");
                    txtThongBao.setText("");
                }
            }
        } else if (o.equals(btnXoa)) {
            int row = tblDichVu.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng cần xóa");
            } else {
                String MaDV = txtMaLoaiDichVu.getText().trim();
                String tenDv = txtTenLoaiDichVu.getText().trim();
                TypeOfService type = new TypeOfService(MaDV, tenDv);
                int ans = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Cảnh báo",
                        JOptionPane.YES_NO_OPTION);
                if (ans == JOptionPane.YES_OPTION) {
                    if (typeOfServiceDAO.checkIfTypeOfServiceIsReferenced(type.getMaLoaiDichVu())) {
                        JOptionPane.showMessageDialog(this, "Loại dịch vụ đang được sử dụng! Không được phép xóa");
                    } else {
                        if (typeOfServiceDAO.delete(type.getMaLoaiDichVu())) {
                            JOptionPane.showMessageDialog(this, "Xóa thành công");
                            modelTblDichVu.removeRow(row);
                            modelTblDichVu.getDataVector().removeAllElements();
                            loadTypeOfService();
                            txtMaLoaiDichVu.setText("");
                            txtTenLoaiDichVu.setText("");
                            txtThongBao.setText("");
                        } else {
                            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi xóa. Vui lòng thử lại sau.");
                        }
                    }
                }
            }
        } else if (o.equals(btnSua)) {
            int row = tblDichVu.getSelectedRow();
            if (row >= 0) {
                if (validData()) {
                    String MaDV = txtMaLoaiDichVu.getText().trim();
                    String tenDv = txtTenLoaiDichVu.getText().trim();
                    TypeOfService type = new TypeOfService(MaDV, tenDv);
                    if (typeOfServiceDAO.update(type)) {
                        tblDichVu.setValueAt(txtTenLoaiDichVu.getText(), row, 2);
                        JOptionPane.showMessageDialog(this, "Sửa thành công");
                        txtMaLoaiDichVu.setText("");
                        txtTenLoaiDichVu.setText("");
                        txtThongBao.setText("");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn phải dòng cần sửa");
            }
        } else if (o.equals(btnLamMoi)) {
            txtMaLoaiDichVu.setText("");
            txtTenLoaiDichVu.setText("");
            txtThongBao.setText("");
            modelTblDichVu.getDataVector().removeAllElements();
            loadTypeOfService();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(tblDichVu)) {
            int row = tblDichVu.getSelectedRow();
            txtMaLoaiDichVu.setText(modelTblDichVu.getValueAt(row, 1).toString());
            txtTenLoaiDichVu.setText(modelTblDichVu.getValueAt(row, 2).toString());
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
     * Hiển thị thông báo khi kiểm tra thông tin trong form
     */
    private void showMessage(JTextField txtTenDichVu, String message) {
        txtTenDichVu.requestFocus();
        txtThongBao.setText(message);
    }

    /**
     * Kiểm tra thông tin trong form
     *
     * @return {@code boolean}: kết quả trả về của quá trình kiểm tra thông tin
     * <ul>
     *     <li>Nếu hợp lệ thì trả về {@code true}</li>
     *     <li>Nếu không hợp lệ thì trả về {@code false}</li>
     * </ul>
     */
    private boolean validData() {
        String ten = txtTenLoaiDichVu.getText().trim();
        if (!((ten.length()) > 0 && ten.matches("^[A-Za-z0-9À-ỹ ]+"))) {
            showMessage(txtTenLoaiDichVu, "Tên loại dịch vụ không được chứa kí tự đặc biệt");
            return false;
        }
        return true;
    }
}
