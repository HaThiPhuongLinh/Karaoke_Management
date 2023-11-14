package UI.component;

import ConnectDB.ConnectDB;
import DAO.ServiceDAO;
import DAO.TypeOfServiceDAO;
import Entity.Service;
import Entity.Staff;
import Entity.TypeOfService;
import UI.CustomUI.Custom;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Sử dụng để quản lí dịch vụ
 * <p>
 *     Người tham gia thiết kế: Nguyễn Quang Duy
 * </p>
 * ngày tạo: 12/10/2023
 * <p>
 *     Lần cập nhật cuối: 6/11/2023
 * </p>
 * Nội dung cập nhật: thêm javadoc
 */

public class Service_UI extends JPanel implements ActionListener, MouseListener{
    private JTable tblDichVu;
    private JComboBox<String> cmbLoaiDichVu;
    private JTextField txtDonViTinh;
    private JButton btnThem,btnXoa,btnSua, btnLamMoi;
    private JTextField txtMaDichVu, txtTenDichVu, txtThongBao;
    private JTextField txtGiaBan, txtSoLuongTon;
    private DefaultTableModel modelTblDichVu;
    private JLabel lblBackGround, lblTime;
    private ServiceDAO serviceDAO;
    private TypeOfServiceDAO typeOfServiceDAO;
    private DecimalFormat df = new DecimalFormat("#,###.## VND");
    public static Staff staffLogin = null;

    public Service_UI(Staff staff){
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        serviceDAO = new ServiceDAO();
        typeOfServiceDAO = new TypeOfServiceDAO();

        //phan viet code
        JLabel lblLabel = new JLabel("QUẢN LÝ DỊCH VỤ");
        lblLabel.setBounds(570, 10, 1175, 40);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 25));
        lblLabel.setForeground(Color.WHITE);
        Component add = add(lblLabel);

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

        JPanel pnlDichVu =  new JPanel();
        pnlDichVu.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlDichVu.setBounds(10, 70, 1245, 670);
        pnlDichVu.setOpaque(false);
        add(pnlDichVu);

        pnlDichVu.setLayout(null);

        //        Mã dịch vụ
        JLabel lblMaDichVu = new JLabel("Mã dịch vụ:");
        lblMaDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMaDichVu.setBounds(90, 50, 150, 30);
        lblMaDichVu.setForeground(Color.WHITE);
        pnlDichVu.add(lblMaDichVu);

        txtMaDichVu = new JTextField();
        txtMaDichVu.setBounds(190, 50, 170, 30);
        txtMaDichVu.setColumns(10);
        pnlDichVu.add(txtMaDichVu);

//      Tên dịch vụ
        JLabel lblTenDichVu = new JLabel("Tên dịch vụ:");
        lblTenDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTenDichVu.setBounds(90, 100, 150, 30);
        lblTenDichVu.setForeground(Color.WHITE);
        pnlDichVu.add(lblTenDichVu);

        txtTenDichVu = new JTextField();
        txtTenDichVu.setBounds(190, 100, 170, 30);
        txtTenDichVu.setColumns(10);
        pnlDichVu.add(txtTenDichVu);
        //      Loại dịch vụ
        JLabel lblLoaiDichVu = new JLabel("Loại dịch vụ:");
        lblLoaiDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        lblLoaiDichVu.setBounds(450, 50, 150, 30);
        lblLoaiDichVu.setForeground(Color.WHITE);
        pnlDichVu.add(lblLoaiDichVu);

        cmbLoaiDichVu = new JComboBox<String>();
        cmbLoaiDichVu.addItem(" ");
        cmbLoaiDichVu.setBounds(550, 50, 170, 30);
        Custom.setCustomComboBox(cmbLoaiDichVu);
        pnlDichVu.add(cmbLoaiDichVu);

        //      Đơn vị tính
        JLabel lblDonViTinh = new JLabel("Đơn vị tính:");
        lblDonViTinh.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDonViTinh.setBounds(450, 100, 150, 30);
        lblDonViTinh.setForeground(Color.WHITE);
        pnlDichVu.add(lblDonViTinh);

        txtDonViTinh = new JTextField();
        txtDonViTinh.setBounds(550, 100, 170, 30);
        txtDonViTinh.setColumns(10);
        pnlDichVu.add(txtDonViTinh);

        //      Số lượng tồn
        JLabel lblSoLuongTon = new JLabel("Số lượng tồn:");
        lblSoLuongTon.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSoLuongTon.setBounds(830, 50, 150, 30);
        lblSoLuongTon.setForeground(Color.WHITE);
        pnlDichVu.add(lblSoLuongTon);

        txtSoLuongTon = new JTextField();
        txtSoLuongTon.setBounds(950,50,170,30);
        pnlDichVu.add(txtSoLuongTon);

        //      Giá bán
        JLabel lblGiaBan = new JLabel("Giá bán:");
        lblGiaBan.setFont(new Font("Arial", Font.PLAIN, 14));
        lblGiaBan.setBounds(830, 100, 150 , 30);
        lblGiaBan.setForeground(Color.WHITE);
        pnlDichVu.add(lblGiaBan);

        txtGiaBan = new JTextField();
        txtGiaBan.setBounds(950,100,170,30);
        pnlDichVu.add(txtGiaBan);

        //Thông báo
        txtThongBao = new JTextField();
        txtThongBao.setFont(new Font("Arial",Font.BOLD,13));
        txtThongBao.setForeground(Color.RED);
        txtThongBao.setBounds(30, 160, 450 , 30);
        txtThongBao.setColumns(10);
        pnlDichVu.add(txtThongBao);

//        btn thêm
        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThem);
        btnThem.setBounds(550, 160, 100, 30);
        pnlDichVu.add(btnThem);

        //        btn Xóa
        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnXoa);
        btnXoa.setBounds(700, 160, 100, 30);
        pnlDichVu.add(btnXoa);

        //        btn sửa
        btnSua = new JButton("Sửa");
        btnSua.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnSua);
        btnSua.setBounds(850, 160, 100, 30);
        pnlDichVu.add(btnSua);

        //        btn làm mới
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnLamMoi);
        btnLamMoi.setBounds(1000, 160, 100, 30);
        pnlDichVu.add(btnLamMoi);

//      danh sách dịch vụ
        JPanel pnlDSDV = new JPanel();
        pnlDSDV.setLayout(null);
        pnlDSDV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlDSDV.setBounds(5, 210, 1235, 455);
        pnlDSDV.setOpaque(false);

        String[] colsDV = { "STT", "Mã dịch vụ", "Tên dịch vụ","Tên loại dịch vụ","Đơn vị tính","Số lượng tồn","Giá bán"};
        modelTblDichVu = new DefaultTableModel(colsDV, 0) ;
        JScrollPane scrDichVu;

        tblDichVu = new JTable(modelTblDichVu);
        tblDichVu.setFont(new Font("Arial", Font.BOLD, 14));
        tblDichVu.setBackground(new Color(255, 255, 255, 0));
        tblDichVu.setForeground(new Color(255, 255, 255));
        tblDichVu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblDichVu.getTableHeader().setForeground(Color.BLUE);
        Custom.getInstance().setCustomTable(tblDichVu);

        pnlDSDV.add(scrDichVu = new JScrollPane(tblDichVu,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrDichVu.setBounds(10,20,1220,425);
        scrDichVu.setOpaque(false);
        scrDichVu.getViewport().setOpaque(false);
        scrDichVu.getViewport().setBackground(Color.WHITE);
        pnlDichVu.add(pnlDSDV);
        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        lblBackGround = new JLabel(backgroundImage);
        lblBackGround.setBounds(0, 0, getWidth(), getHeight());
        add(lblBackGround);

        loadService();
        loadCboService();
        cmbLoaiDichVu.addActionListener(this);
        tblDichVu.addMouseListener(this);

        btnThem.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);

        txtMaDichVu.setEditable(false);
        reSizeColumnTableService();

    }

    /**
     * Thay đổi kích thước cột
     */
    private void reSizeColumnTableService(){
        TableColumnModel tblModel = tblDichVu.getColumnModel();
        tblModel.getColumn(0).setPreferredWidth(20);
        tblModel.getColumn(1).setPreferredWidth(40);
        tblModel.getColumn(2).setPreferredWidth(130);
        tblModel.getColumn(3).setPreferredWidth(100);
    }

    /**
     * Load thông tin dịch vụ lên tblDichVu
     */
    private void loadService(){
        java.util.List<Service> list = serviceDAO.getAllDichVu();
        int i=1;
        for (Service service : list){
            modelTblDichVu.addRow(new Object[]{i,service.getMaDichVu(),service.getTenDichVu(),service.getMaLoaiDichVu().getTenLoaiDichVu(),service.getDonViTinh(),service.getSoLuongTon(),df.format(service.getGiaBan())});
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
     * Load các loại dịch vụ vào cmbLoaiDichVu
     */

    private void loadCboService() {
        java.util.List<TypeOfService> dataList = typeOfServiceDAO.getAllLoaiDichVu();
        for (TypeOfService serviceType : dataList) {
            cmbLoaiDichVu.addItem(serviceType.getTenLoaiDichVu());
        }
    }

    /**
     * Hiển thị thông báo khi kiểm tra thông tin trong form
     * @param txtTenDichVu
     * @param message
     */
    private void showMessage(JTextField txtTenDichVu, String message) {
        txtTenDichVu.requestFocus();
        txtThongBao.setText(message);
    }

    /**
     * Kiểm tra thông tin trong form
     * @return {@code boolean}: kết quả trả về của quá trình kiểm tra thông tin
     *          <ul>
     *              <li>Nếu hợp lệ thì trả về {@code true}</li>
     *              <li>Nếu không hợp lệ thì trả về {@code false}</li>
     *          </ul>
     */
    private boolean validData() {
        String ten = txtTenDichVu.getText().trim();
        String donViTinh = txtDonViTinh.getText().trim();
        String soLuongTon = txtSoLuongTon.getText();
        String giaBan = txtGiaBan.getText();

        if (!((ten.length() > 0) && ten.matches("^[A-Za-z0-9À-ỹ ]+"))) {
            showMessage(txtTenDichVu,"Tên dịch vụ không được chứa kí tự đặc biệt");
            return false;
        }
        if (!((donViTinh.length()) > 0 && donViTinh.matches("^[A-Za-zÀ-ỹ ]+"))) {
            showMessage(txtDonViTinh,"Đơn vị tính không được chứa số và kí tự đặc biệt");
            return false;
        }
        if (!((soLuongTon.length()) > 0 && soLuongTon.matches("^[0-9]\\d*"))) {
            showMessage(txtSoLuongTon,"Số lượng tồn phải là số và lớn hơn hoặc bằng 0");
            return false;
        }
        if (!((giaBan.length()) > 0 && giaBan.matches("^[1-9]\\d*"))) {
            showMessage(txtGiaBan,"Giá bán phải lớn hơn 0");
            return false;
        }
        return true;
    }

    /**
     * Lấy mã loại dịch vụ
     * @return mã loại dịch vụ
     */
    public String getMaLoaiDichVu(){
        String tenLDV = cmbLoaiDichVu.getSelectedItem().toString().trim();
        String maLDV = typeOfServiceDAO.getServiceCodeByName(tenLDV);
        return maLDV;
    }
    /**
     * Lấy mã dịch vụ
     * @return mã dịch vụ
     */
    public String getMaDichVu(){
        String MaDV = serviceDAO.generateNextServiceId();
        return MaDV;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThem)) {
            if (txtTenDichVu.getText().equals("") || txtSoLuongTon.getText().equals("") || txtDonViTinh.getText().equals("") || txtGiaBan.getText().equals("") || cmbLoaiDichVu.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập thông tin dịch vụ");
            } else {
                if (validData()) {
                    String MaDV = getMaDichVu();
                    String maLDV = getMaLoaiDichVu();
                    String tenDv = txtTenDichVu.getText().trim();
                    String donViTinh = txtDonViTinh.getText().trim();
                    int soLuongTon = Integer.parseInt(txtSoLuongTon.getText().toString());
                    double giaBan = Double.parseDouble(txtGiaBan.getText().toString());

                    Service service = new Service(MaDV, tenDv, new TypeOfService(maLDV), donViTinh, soLuongTon, giaBan);
                    if (serviceDAO.insert(service)) {
                        modelTblDichVu.getDataVector().removeAllElements();
                        loadService();
                        reset();
                        JOptionPane.showMessageDialog(this,"Thêm dịch vụ thành công");
                    }
                }

            }
        } else if (o.equals(btnLamMoi)) {
            reset();
            modelTblDichVu.getDataVector().removeAllElements();
            loadService();
        } else if (o.equals(btnXoa)) {
            int row = tblDichVu.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng cần xóa");
            } else {
                String maLDV = getMaLoaiDichVu();
                String MaDV = txtMaDichVu.getText().trim();
                String tenDv = txtTenDichVu.getText().trim();
                String donViTinh = txtDonViTinh.getText().trim();
                int soLuongTon = Integer.parseInt(txtSoLuongTon.getText());
                double giaBan = Double.parseDouble(txtGiaBan.getText());

                Service service = new Service(MaDV, tenDv, new TypeOfService(maLDV), donViTinh, soLuongTon, giaBan);
                int ans = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Cảnh báo",
                        JOptionPane.YES_NO_OPTION);
                if (ans == JOptionPane.YES_OPTION) {
                    serviceDAO.delete(service.getMaDichVu());
                    reset();
                    modelTblDichVu.removeRow(row);
                    modelTblDichVu.getDataVector().removeAllElements();
                    loadService();
                    JOptionPane.showMessageDialog(this,"Xóa thành công");
                }
            }
        } else if (o.equals(btnSua)) {
            int row = tblDichVu.getSelectedRow();
            if (row > 0) {
                if (validData()) {
                    String tenLDV = cmbLoaiDichVu.getSelectedItem().toString().trim();
                    ArrayList<TypeOfService> typeOfServices = typeOfServiceDAO.getTypeOfServiceByName(tenLDV);
                    String s = "";
                    for (TypeOfService service : typeOfServices) {
                        s += service.getMaLoaiDichVu();
                    }

                    String MaDV = txtMaDichVu.getText().trim();
                    String tenDv = txtTenDichVu.getText().trim();
                    String donViTinh = txtDonViTinh.getText().trim();
                    int soLuongTon = Integer.parseInt(txtSoLuongTon.getText());
                    double giaBan = Double.parseDouble(txtGiaBan.getText());

                    Service service = new Service(MaDV, tenDv, new TypeOfService(s), donViTinh, soLuongTon, giaBan);

                    if (serviceDAO.update(service)) {
                        String quantityStr = df.format(service.getSoLuongTon());
                        String priceStr = df.format(service.getGiaBan());
                        tblDichVu.setValueAt(txtTenDichVu.getText(), row, 2);
                        tblDichVu.setValueAt(cmbLoaiDichVu.getSelectedItem(), row, 3);
                        tblDichVu.setValueAt(txtDonViTinh.getText(), row, 4);
                        tblDichVu.setValueAt(quantityStr, row, 5);
                        tblDichVu.setValueAt(priceStr, row, 6);
                        reset();
                        JOptionPane.showMessageDialog(this,"Sửa thành công");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng cần sửa");
            }
        }
    }

    /**
     * làm mới các thông tin trong form
     */
    private void reset(){
        txtMaDichVu.setText("");
        txtTenDichVu.setText("");
        cmbLoaiDichVu.setSelectedIndex(0);
        txtDonViTinh.setText("");
        txtSoLuongTon.setText("");
        txtGiaBan.setText("");
        txtThongBao.setText("");

    }

    @Override
    public void mouseClicked (MouseEvent e){
        Object o = e.getSource();
        if (o.equals(tblDichVu)) {
            int row = tblDichVu.getSelectedRow();
            txtMaDichVu.setText(tblDichVu.getValueAt(row, 1).toString());
            txtTenDichVu.setText(tblDichVu.getValueAt(row, 2).toString());
            cmbLoaiDichVu.setSelectedItem(tblDichVu.getValueAt(row, 3));
            txtDonViTinh.setText(tblDichVu.getValueAt(row, 4).toString());
            String quantityStr = tblDichVu.getValueAt(row, 5).toString().trim().replace(",", "");
            txtSoLuongTon.setText(String.valueOf(Integer.parseInt(quantityStr)));
            String priceStr = tblDichVu.getValueAt(row, 6).toString().trim().replace(",", "").replace(" VND", "");
            txtGiaBan.setText(String.valueOf(Integer.parseInt(priceStr)));
        }
    }

    @Override
    public void mousePressed (MouseEvent e){

    }

    @Override
    public void mouseReleased (MouseEvent e){

    }

    @Override
    public void mouseEntered (MouseEvent e){

    }

    @Override
    public void mouseExited (MouseEvent e){

    }

}
