package UI.component;

import DAO.DetailOfServiceDAO;
import DAO.ServiceDAO;
import Entity.DetailsOfService;
import Entity.Service;
import Entity.Staff;
import UI.CustomUI.Custom;
import UI.component.Dialog.DatePicker;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class StatisticService_UI extends JPanel implements ActionListener , ItemListener {
    private JComboBox<String> comboBoxLocTheo;
    private JButton btnLamMoi,btnThongKe;
    private DefaultTableModel modelTableDV;
    private JTable tableDV;
    private DatePicker pickerDenNgay,pickerTuNgay;
    private JLabel backgroundLabel,timeLabel;
    private DetailOfServiceDAO detailOfServiceDAO;
    private ServiceDAO serviceDAO;
    private DecimalFormat df = new DecimalFormat("#,###.##");
    public static Staff staffLogin = null;

    public StatisticService_UI(Staff staff){
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        detailOfServiceDAO = new DetailOfServiceDAO();
        serviceDAO = new ServiceDAO();

        //phan viet code
        JLabel headerLabel = new JLabel("THỐNG KÊ DỊCH VỤ");
        headerLabel.setBounds(570, 10, 1175, 40);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        Component add = add(headerLabel);

        JPanel timeNow = new JPanel();
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

        JPanel panel1 =  new JPanel();
        panel1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "THÔNG TIN THỐNG KÊ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panel1.setBounds(10, 70, 1245, 670);
        panel1.setOpaque(false);
        add(panel1);

        panel1.setLayout(null);

        //        Từ ngày
        JLabel labelTuNgay = new JLabel("Từ ngày:");
        labelTuNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTuNgay.setBounds(150, 100, 120, 30);
        labelTuNgay.setForeground(Color.WHITE);
        add(labelTuNgay);

        pickerTuNgay = new DatePicker(150);
        pickerTuNgay.setOpaque(false);
        pickerTuNgay.setBounds(230, 100, 300, 30);
        add(pickerTuNgay);
//      Đến ngày
        JLabel lblDenNgay = new JLabel("Đến ngày: ");
        lblDenNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDenNgay.setBounds(450, 100, 120, 30);
        lblDenNgay.setForeground(Color.WHITE);
        add(lblDenNgay);

        pickerDenNgay = new DatePicker(150);
        pickerDenNgay.setOpaque(false);
        pickerDenNgay.setBounds(530, 100, 300, 30);
        add(pickerDenNgay);

//      Lọc theo
        JLabel labelLocTheo = new JLabel("Lọc theo:");
        labelLocTheo.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLocTheo.setBounds(750, 100, 150, 30);
        labelLocTheo.setForeground(Color.WHITE);
        add(labelLocTheo);

        comboBoxLocTheo = new JComboBox<String>();
        comboBoxLocTheo.addItem("Tùy chỉnh");
        comboBoxLocTheo.addItem("7 ngày gần nhất");
        comboBoxLocTheo.addItem("1 tháng gần nhất");
        comboBoxLocTheo.addItem("3 tháng gần nhất");
        comboBoxLocTheo.addItem("6 tháng gần nhất");
        comboBoxLocTheo.addItem("1 năm gần nhất");
        comboBoxLocTheo.setBounds(830,100,200,30);
        Custom.setCustomComboBox(comboBoxLocTheo);
        add(comboBoxLocTheo);

        //        btn thống kê
        btnThongKe = new JButton("Thống kê");
        btnThongKe.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThongKe);
        btnThongKe.setBounds(430, 160, 150, 30);
        add(btnThongKe);

        //        btn làm mới
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnLamMoi);
        btnLamMoi.setBounds(630, 160, 150, 30);
        add(btnLamMoi);


//      danh sách dịch vụ
        JPanel panelDSDV = new JPanel();
        panelDSDV.setLayout(null);
        panelDSDV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH DỊCH VỤ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        panelDSDV.setBounds(5, 130, 1235, 530);
        panelDSDV.setOpaque(false);

        String[] colsDV = { "STT", "Mã dịch vụ", "Tên dịch vụ","Số lượng bán được" ,"Doanh số"};
        modelTableDV = new DefaultTableModel(colsDV, 0) ;
        JScrollPane scrollPaneDV;

        tableDV = new JTable(modelTableDV);
        tableDV.setFont(new Font("Arial", Font.BOLD, 14));
        tableDV.setBackground(new Color(255, 255, 255, 0));
        tableDV.setForeground(new Color(255, 255, 255));
        tableDV.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableDV.getTableHeader().setForeground(Color.BLUE);
//        tableLDV.getTableHeader().setBackground(new Color(255, 255, 255));
        Custom.getInstance().setCustomTable(tableDV);

        panelDSDV.add(scrollPaneDV = new JScrollPane(tableDV,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrollPaneDV.setBounds(10,20,1220,500);
        scrollPaneDV.setOpaque(false);
        scrollPaneDV.getViewport().setOpaque(false);
        scrollPaneDV.getViewport().setBackground(Color.WHITE);
        panel1.add(panelDSDV);
        //
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);

        btnThongKe.addActionListener(this);
        btnLamMoi.addActionListener(this);

        comboBoxLocTheo.addItemListener(this);
    }
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        timeLabel.setText(time);
    }

    private void docDuLieuVaoTable(ArrayList<DetailsOfService> listService) {
        int i=1;
        ArrayList<Service> list = (ArrayList<Service>) serviceDAO.getAllDichVu();

        HashMap<String, Integer> totalSales = new HashMap<>();
        ArrayList<Object[]> dataToAdd = new ArrayList<>();

        for (DetailsOfService CTDV : listService) {
            String serviceId = CTDV.getMaDichVu().getMaDichVu();
            int quantity =  CTDV.getSoLuong();
            if (totalSales.containsKey(serviceId)) {
                // Nếu mã dịch vụ đã tồn tại trong HashMap, cộng dồn số lượng bán
                int currentQuantity = totalSales.get(serviceId);
                totalSales.put(serviceId, currentQuantity + quantity);
            } else {
                // Nếu mã dịch vụ chưa tồn tại trong HashMap, thêm vào với số lượng bán hiện tại
                totalSales.put(serviceId, quantity);
            }
        }

        for (String serviceId : totalSales.keySet()){
            int totalQuantity = totalSales.get(serviceId);
            String s1 = "";
            double gia = 0;
            for (Service s : list){
                if (serviceId.equals(s.getMaDichVu())){
                    s1=s.getTenDichVu();
                    gia = totalQuantity*s.getGiaBan();
                }
            }
            dataToAdd.add(new Object[] { i, serviceId, s1, totalQuantity, df.format(gia) });
            i++;
        }

        // Sắp xếp danh sách tạm thời theo thứ tự giảm dần của giá

        dataToAdd.sort((o1, o2) -> Double.compare(Double.valueOf(o2[4].toString().replace(",", "")), Double.valueOf(o1[4].toString().replace(",", ""))));
        // Tăng stt
        for (int j = 0; j < dataToAdd.size(); j++) {
            dataToAdd.get(j)[0] = j + 1;
        }

        modelTableDV.getDataVector().removeAllElements();
        tableDV.removeAll();
        for (Object[] rowData : dataToAdd) {
            modelTableDV.addRow(rowData);
        }
    }

    public boolean validData() throws ParseException {
        Date tuNgay = pickerTuNgay.getFullDate();
        Date denNgay = pickerDenNgay.getFullDate();
        if (denNgay.before(tuNgay)) {
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThongKe)) {
            modelTableDV.getDataVector().removeAllElements();
            try {
                Date tuNgay = pickerTuNgay.getFullDate();
                Date denNgay = pickerDenNgay.addOneDay();
                ArrayList<DetailsOfService> listDV = detailOfServiceDAO.getListCTDVByDate(tuNgay, denNgay);
//                System.out.printf(String.valueOf(listDV.size())+"ádasdasd");
                if (validData()) {
                    if (tuNgay.before(denNgay)) {
                        if (listDV == null || listDV.isEmpty() || listDV.size() <= 0)
                            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin thông kê");
                        else
                            docDuLieuVaoTable(listDV);
                    }
                } else if (denNgay.before(tuNgay)) {
                    pickerDenNgay.setValueToDay();
                    JOptionPane.showMessageDialog(this, "Ngày kết thúc phải >= ngày bắt đầu", "Cảnh báo",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        } else if (o.equals(btnLamMoi)){
            modelTableDV.getDataVector().removeAllElements();
            tableDV.removeAll();
            comboBoxLocTheo.setSelectedIndex(0);
            pickerTuNgay.setValueToDay();
            pickerDenNgay.setValueToDay();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object o = e.getSource();
        if (o.equals(comboBoxLocTheo)) {
            String search = comboBoxLocTheo.getSelectedItem().toString();
            switch (search) {
                case "7 ngày gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTableDV.getDataVector().removeAllElements();
                    tableDV.removeAll();
                    Date tuNgay = pickerTuNgay.setDatesFromToday(Calendar.DAY_OF_MONTH, -6);
                    Date denNgay = null;
                    denNgay = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<DetailsOfService> listDV = detailOfServiceDAO.getListCTDVByDate(tuNgay, denNgay);
                    docDuLieuVaoTable(listDV);
                    break;
                case "1 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTableDV.getDataVector().removeAllElements();
                    tableDV.removeAll();
                    Date tuNgay1 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -1);
                    Date denNgay1 = null;
                    denNgay1 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<DetailsOfService> listDV1 = detailOfServiceDAO.getListCTDVByDate(tuNgay1, denNgay1);
                    docDuLieuVaoTable(listDV1);
                    break;
                case "3 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTableDV.getDataVector().removeAllElements();
                    tableDV.removeAll();
                    Date tuNgay2 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -2);
                    Date denNgay2 = null;
                    denNgay2 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<DetailsOfService> listDV2 = detailOfServiceDAO.getListCTDVByDate(tuNgay2, denNgay2);
                    docDuLieuVaoTable(listDV2);
                    break;
                case "6 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTableDV.getDataVector().removeAllElements();
                    tableDV.removeAll();
                    Date tuNgay3 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -5);
                    Date denNgay3 = null;
                    denNgay3 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<DetailsOfService> listDV3 = detailOfServiceDAO.getListCTDVByDate(tuNgay3, denNgay3);
                    docDuLieuVaoTable(listDV3);
                    break;
                case "1 năm gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTableDV.getDataVector().removeAllElements();
                    tableDV.removeAll();
                    Date tuNgay4 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -11);
                    Date denNgay4 = null;
                    denNgay4 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<DetailsOfService> listDV4 = detailOfServiceDAO.getListCTDVByDate(tuNgay4, denNgay4);
                    docDuLieuVaoTable(listDV4);

                    break;
                case "Tùy chỉnh":
                    pickerTuNgay.setActive(true);
                    pickerDenNgay.setActive(true);
                    pickerTuNgay.setValueToDay();
                    break;
            }
        }
    }
}
