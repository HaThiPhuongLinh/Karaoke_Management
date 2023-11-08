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
 * Sử dụng để tìm kiếm dịch vụ
 * <p>
 *     Người tham gia thiết kế: Nguyễn Quang Duy
 * </p>
 * ngày tạo: 12/10/2023
 * <p>
 *     Lần cập nhật cuối: 5/11/2023
 * </p>
 * Nội dung cập nhật: thêm javadoc
 */

public class SearchingService_UI extends JPanel implements ActionListener, MouseListener {
    private JComboBox<String> cmbLDV, cmbGiaBan;
    private JTable tblDichVu;
    private JTextField txtTenDichVu;
    private JButton btnlamMoi;
    private JButton btnTimKiem;
    private JLabel lblBackGround, lblTime;
    private ServiceDAO serviceDAO;
    private TypeOfServiceDAO typeOfServiceDAO;
    private DefaultTableModel modelTblDichVu;
    private DecimalFormat df = new DecimalFormat("#,###.## VND");
    public static Staff staffLogin = null;

    public SearchingService_UI(Staff staff){
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
        JLabel lblTimKiemDichVu = new JLabel("TÌM KIẾM DỊCH VỤ");
        lblTimKiemDichVu.setBounds(570, 10, 1175, 40);
        lblTimKiemDichVu.setFont(new Font("Arial", Font.BOLD, 25));
        lblTimKiemDichVu.setForeground(Color.WHITE);
        Component add = add(lblTimKiemDichVu);

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

//      Tên dịch vụ
        JLabel lblTenDichVu = new JLabel("Tên dịch vụ:");
        lblTenDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTenDichVu.setBounds(150, 50, 150, 30);
        lblTenDichVu.setForeground(Color.WHITE);
        pnlDichVu.add(lblTenDichVu);

        txtTenDichVu = new JTextField();
        txtTenDichVu.setBounds(270, 50, 250, 30);
        txtTenDichVu.setColumns(10);
        pnlDichVu.add(txtTenDichVu);

        //      Loại dịch vụ
        JLabel lblLoaiDichVu = new JLabel("Loại dịch vụ:");
        lblLoaiDichVu.setFont(new Font("Arial", Font.PLAIN, 14));
        lblLoaiDichVu.setBounds(150, 100, 150, 30);
        lblLoaiDichVu.setForeground(Color.WHITE);
        pnlDichVu.add(lblLoaiDichVu);

        cmbLDV = new JComboBox<String>();
        cmbLDV.setBounds(270, 100, 250, 30);
        Custom.setCustomComboBox(cmbLDV);
        pnlDichVu.add(cmbLDV);

        //      Giá bán
        JLabel lblGiaBan = new JLabel("Giá bán:");
        lblGiaBan.setFont(new Font("Arial", Font.PLAIN, 14));
        lblGiaBan.setBounds(680, 50, 150, 30);
        lblGiaBan.setForeground(Color.WHITE);
        pnlDichVu.add(lblGiaBan);

        cmbGiaBan = new JComboBox<String>();
        cmbGiaBan.addItem("Tất cả");
        cmbGiaBan.addItem("10.000 - 50.000");
        cmbGiaBan.addItem("50.000 - 100.000");
        cmbGiaBan.addItem("100.000 - 500.000");
        cmbGiaBan.setBounds(800, 50, 250, 30);
        Custom.setCustomComboBox(cmbGiaBan);
        pnlDichVu.add(cmbGiaBan);

//        btn tìm kiếm
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnTimKiem);
        btnTimKiem.setBounds(800, 100, 100, 30);
        pnlDichVu.add(btnTimKiem);

        //        btn làm mới
        btnlamMoi = new JButton("Làm mới");
        btnlamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnlamMoi);
        btnlamMoi.setBounds(950, 100, 100, 30);
        pnlDichVu.add(btnlamMoi);

//      danh sách dịch vụ
        JPanel pnlDSDV = new JPanel();
        pnlDSDV.setLayout(null);
        pnlDSDV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlDSDV.setBounds(5, 160, 1235, 505);
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
        scrDichVu.setBounds(10,20,1220,480);
        scrDichVu.setOpaque(false);
        scrDichVu.getViewport().setOpaque(false);
        scrDichVu.getViewport().setBackground(Color.WHITE);
        pnlDichVu.add(pnlDSDV);
        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        lblBackGround = new JLabel(backgroundImage);
        lblBackGround.setBounds(0, 0, getWidth(), getHeight());
        add(lblBackGround);

        btnTimKiem.addActionListener(this);
        btnlamMoi.addActionListener(this);

        loadSearchingService();
        loadCboService();
        reSizeColumnTableService();

        cmbLDV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoaiDichVu = (String) cmbLDV.getSelectedItem();
                txtTenDichVu.setText("");
                cmbGiaBan.setSelectedIndex(0);
                modelTblDichVu.setRowCount(0);
                int i=1;
                for (Service dv : serviceDAO.getAllDichVu()) {
                    if (selectedLoaiDichVu.equalsIgnoreCase("Tất cả") ||
                            selectedLoaiDichVu.equalsIgnoreCase(dv.getMaLoaiDichVu().getTenLoaiDichVu())) {
                        Object[] rowData = {i, dv.getMaDichVu(), dv.getTenDichVu(),dv.getMaLoaiDichVu().getTenLoaiDichVu(), dv.getDonViTinh(), dv.getSoLuongTon(), df.format(dv.getGiaBan())};
                        modelTblDichVu.addRow(rowData);
                        i++;
                    }
                }
            }
        });

        cmbGiaBan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtTenDichVu.setText("");

                modelTblDichVu.setRowCount(0);
                int i=1;
                for (Service dv : serviceDAO.getAllDichVu()) {
                    if (cmbGiaBan.getSelectedIndex()==0){
                        Object[] rowData = {i, dv.getMaDichVu(), dv.getTenDichVu(),dv.getMaLoaiDichVu().getTenLoaiDichVu(), dv.getDonViTinh(), dv.getSoLuongTon(), df.format(dv.getGiaBan())};
                        modelTblDichVu.addRow(rowData);
                        i++;
                    }else if (cmbGiaBan.getSelectedIndex()==1){
                        if (dv.getGiaBan()>=10000 && dv.getGiaBan()<=50000){
                            Object[] rowData = {i, dv.getMaDichVu(), dv.getTenDichVu(),dv.getMaLoaiDichVu().getTenLoaiDichVu(), dv.getDonViTinh(), dv.getSoLuongTon(), df.format(dv.getGiaBan())};
                            modelTblDichVu.addRow(rowData);
                            i++;
                        }
                    }else if (cmbGiaBan.getSelectedIndex()==2){
                        if (dv.getGiaBan()>=50000 && dv.getGiaBan()<=100000){
                            Object[] rowData = {i, dv.getMaDichVu(), dv.getTenDichVu(),dv.getMaLoaiDichVu().getTenLoaiDichVu(), dv.getDonViTinh(), dv.getSoLuongTon(), df.format(dv.getGiaBan())};
                            modelTblDichVu.addRow(rowData);
                            i++;
                        }
                    }else if (cmbGiaBan.getSelectedIndex()==3){
                        if (dv.getGiaBan()>=100000 && dv.getGiaBan()<=500000){
                            Object[] rowData = {i, dv.getMaDichVu(), dv.getTenDichVu(),dv.getMaLoaiDichVu().getTenLoaiDichVu(), dv.getDonViTinh(), dv.getSoLuongTon(), df.format(dv.getGiaBan())};
                            modelTblDichVu.addRow(rowData);
                            i++;
                        }
                    }
                }
            }
        });
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
     * load danh sách dịch vụ vào modelTblDichVu
     */
    private void loadSearchingService(){
        java.util.List<Service> list = serviceDAO.getAllDichVu();
        int i=1;
        for (Service service : list){
            modelTblDichVu.addRow(new Object[]{i,service.getMaDichVu(),service.getTenDichVu(),service.getMaLoaiDichVu().getTenLoaiDichVu(),service.getDonViTinh(),service.getSoLuongTon(),df.format(service.getGiaBan())});
            i++;
        }
    }
    /**
     * tải danh sách loại dịch vụ vào combobox
     */
    private void loadCboService() {
        java.util.List<TypeOfService> dataList = typeOfServiceDAO.getAllLoaiDichVu();
        cmbLDV.addItem("Tất cả");
        for (TypeOfService serviceType : dataList) {
            cmbLDV.addItem(serviceType.getTenLoaiDichVu());
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
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnTimKiem)) {
            String txtTenDV = txtTenDichVu.getText();
            ArrayList<Service> services2 = serviceDAO.getServiceByName(txtTenDV);
                if (!txtTenDichVu.getText().trim().equals("")) {
                    modelTblDichVu.getDataVector().removeAllElements();
                    int i = 1;
                    if (services2.size() != 0) {
                        for (Service service : services2) {
                            modelTblDichVu.addRow(new Object[]{i, service.getMaDichVu(), service.getTenDichVu(), service.getMaLoaiDichVu().getTenLoaiDichVu(), service.getDonViTinh(), service.getSoLuongTon(), df.format(service.getGiaBan())});
                            i++;
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "KHÔNG TÌM THẤY!!!");
                        txtTenDichVu.selectAll();
                        txtTenDichVu.requestFocus();
                    }
            }

        }else if(o.equals(btnlamMoi)){
            txtTenDichVu.setText("");
//            textFieldGiaBan.setText("");
            cmbGiaBan.setSelectedIndex(0);
            cmbLDV.setSelectedIndex(0);
            modelTblDichVu.getDataVector().removeAllElements();
            loadSearchingService();
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
