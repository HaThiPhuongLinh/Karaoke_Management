package UI.component;

import DAO.*;
import Entity.Bill;
import Entity.Customer;
import Entity.DetailsOfService;
import Entity.Staff;
import UI.CustomUI.Custom;
import UI.component.Dialog.DatePicker;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Sử dụng để thống kê khách hàng
 * <p>
 * Người tham gia thiết kế: Nguyễn Quang Duy, Hà Thị Phương Linh
 * </p>
 * ngày tạo: 12/10/2023
 * <p>
 * Lần cập nhật cuối: 29/11/2023
 * </p>
 * Nội dung cập nhật: cập nhật chức năng xuất báo cáo (Excel)
 */
public class StatisticCustomer_UI extends JPanel implements ActionListener, ItemListener {
    private final String WORKING_DIR = System.getProperty("user.dir");
    private String path = WORKING_DIR + "/KRManagement/excel/";
    public static Staff staffLogin = null;
    private JComboBox<String> cmbLocTheo;
    private JTable tblDichVu;
    private DefaultTableModel modelTblDichVu;
    private JButton btnThongKe, btnLamMoi,btnXuatExcel;
    private JLabel lblDenNgay, lblTuNgay;
    private JLabel lblBackGround, lblTime;
    private DatePicker pickerDenNgay, pickerTuNgay;
    private BillDAO billDAO;
    private CustomerDAO customerDAO;
    private ServiceDAO serviceDAO;
    private DetailOfServiceDAO detailOfServiceDAO;
    private DecimalFormat df = new DecimalFormat("#,###.## VND");

    public StatisticCustomer_UI(Staff staff) {
        this.staffLogin = staff;
        setLayout(null);
        setBounds(0, 0, 1475, 770);

        billDAO = new BillDAO();
        customerDAO = new CustomerDAO();
        serviceDAO = new ServiceDAO();
        detailOfServiceDAO = new DetailOfServiceDAO();

        JLabel lblThongKeKhachHang = new JLabel("THỐNG KÊ KHÁCH HÀNG");
        lblThongKeKhachHang.setBounds(570, 10, 1175, 40);
        lblThongKeKhachHang.setFont(new Font("Arial", Font.BOLD, 25));
        lblThongKeKhachHang.setForeground(Color.WHITE);
        Component add = add(lblThongKeKhachHang);

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

        JPanel pnlThongTinThongKe = new JPanel();
        pnlThongTinThongKe.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "THÔNG TIN THỐNG KÊ",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlThongTinThongKe.setBounds(10, 70, 1245, 670);
        pnlThongTinThongKe.setOpaque(false);
        add(pnlThongTinThongKe);

        pnlThongTinThongKe.setLayout(null);

        lblTuNgay = new JLabel("Từ ngày:");
        lblTuNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTuNgay.setBounds(150, 100, 120, 30);
        lblTuNgay.setForeground(Color.WHITE);
        add(lblTuNgay);

        pickerTuNgay = new DatePicker(150);
        pickerTuNgay.setOpaque(false);
        pickerTuNgay.setBounds(230, 100, 300, 30);
        add(pickerTuNgay);

        lblDenNgay = new JLabel("Đến ngày: ");
        lblDenNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDenNgay.setBounds(450, 100, 120, 30);
        lblDenNgay.setForeground(Color.WHITE);
        add(lblDenNgay);

        pickerDenNgay = new DatePicker(150);
        pickerDenNgay.setOpaque(false);
        pickerDenNgay.setBounds(530, 100, 300, 30);
        add(pickerDenNgay);

        JLabel lblLocTheo = new JLabel("Lọc theo:");
        lblLocTheo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblLocTheo.setBounds(750, 100, 150, 30);
        lblLocTheo.setForeground(Color.WHITE);
        add(lblLocTheo);

        cmbLocTheo = new JComboBox<String>();
        cmbLocTheo.addItem("Tùy chỉnh");
        cmbLocTheo.addItem("7 ngày gần nhất");
        cmbLocTheo.addItem("1 tháng gần nhất");
        cmbLocTheo.addItem("3 tháng gần nhất");
        cmbLocTheo.addItem("6 tháng gần nhất");
        cmbLocTheo.addItem("1 năm gần nhất");
        cmbLocTheo.setBounds(830, 100, 200, 30);
        Custom.setCustomComboBox(cmbLocTheo);
        add(cmbLocTheo);

        btnThongKe = new JButton("Thống kê");
        btnThongKe.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnThongKe);
        btnThongKe.setBounds(300, 160, 120, 30);
        add(btnThongKe);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnLamMoi);
        btnLamMoi.setBounds(500, 160, 120, 30);
        add(btnLamMoi);

        btnXuatExcel = new JButton("Xuất excel");
        btnXuatExcel.setFont(new Font("Arial", Font.BOLD, 14));
        Custom.setCustomBtn(btnXuatExcel);
        btnXuatExcel.setBounds(700, 160, 120, 30);
        add(btnXuatExcel);

        JPanel pnlDSDV = new JPanel();
        pnlDSDV.setLayout(null);
        pnlDSDV.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH KHÁCH HÀNG",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        pnlDSDV.setBounds(5, 130, 1235, 530);
        pnlDSDV.setOpaque(false);

        String[] colsDV = {"STT", "Mã khách hàng", "Tên khách hàng", "Tổng doanh số"};
        modelTblDichVu = new DefaultTableModel(colsDV, 0);
        JScrollPane scrKhachHang;

        tblDichVu = new JTable(modelTblDichVu);
        tblDichVu.setFont(new Font("Arial", Font.BOLD, 14));
        tblDichVu.setBackground(new Color(255, 255, 255, 0));
        tblDichVu.setForeground(new Color(255, 255, 255));
        tblDichVu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblDichVu.getTableHeader().setForeground(Color.BLUE);
        Custom.getInstance().setCustomTable(tblDichVu);

        pnlDSDV.add(scrKhachHang = new JScrollPane(tblDichVu, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrKhachHang.setBounds(10, 20, 1220, 500);
        scrKhachHang.setOpaque(false);
        scrKhachHang.getViewport().setOpaque(false);
        scrKhachHang.getViewport().setBackground(Color.WHITE);
        pnlThongTinThongKe.add(pnlDSDV);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/background.png"));
        lblBackGround = new JLabel(backgroundImage);
        lblBackGround.setBounds(0, 0, getWidth(), getHeight());
        add(lblBackGround);

        btnThongKe.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnXuatExcel.addActionListener(this);

        cmbLocTheo.addItemListener(this);

        tblDichVu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xử lý sự kiện khi click chuột trên dòng
                int selectedRow = tblDichVu.rowAtPoint(e.getPoint());
                // Kiểm tra nếu chọn dòng hợp lệ
                if (selectedRow >= 0) {
                    try {
                        Date tuNgay = pickerTuNgay.getFullDate();
                        Date denNgay = pickerDenNgay.addOneDay();

                        displayNewPanel(selectedRow, cmbLocTheo.getSelectedItem().toString(), tuNgay, denNgay);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

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
        if (o.equals(btnThongKe)) {
            modelTblDichVu.getDataVector().removeAllElements();
            try {
                Date tuNgay = pickerTuNgay.getFullDate();
                Date denNgay = pickerDenNgay.addOneDay();
                ArrayList<Bill> listBill = billDAO.getListBillByDate(tuNgay, denNgay);
                if (validData()) {
                    if (tuNgay.before(denNgay)) {
                        if (listBill == null || listBill.isEmpty() || listBill.size() <= 0) {
                            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin thông kê");
                        } else {
                            docDuLieuVaoTable(listBill);
                        }
                    }
                } else if (denNgay.before(tuNgay)) {
                    pickerDenNgay.setValueToDay();
                    JOptionPane.showMessageDialog(this, "Ngày kết thúc phải >= ngày bắt đầu", "Cảnh báo",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        } else if (o.equals(btnLamMoi)) {
            modelTblDichVu.getDataVector().removeAllElements();
            tblDichVu.removeAll();
            cmbLocTheo.setSelectedIndex(0);
            pickerTuNgay.setValueToDay();
            pickerDenNgay.setValueToDay();
        }else if (o.equals(btnXuatExcel)) {
                Date tuNgay = null;
                try {
                    tuNgay = pickerTuNgay.getFullDate();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                Date denNgay = null;
                try {
                    denNgay = pickerDenNgay.addOneDay();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                ArrayList<Bill> listBill = billDAO.getListBillByDate(tuNgay, denNgay);

            if (listBill.size()==0){
                JOptionPane.showMessageDialog(null,"Vui lòng thống kê trước khi xuất báo cáo!!!");
            }else {
                int confirmation = JOptionPane.showConfirmDialog(null, "Bạn có muốn xuất báo cáo không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    try {
                        XSSFWorkbook workbook = new XSSFWorkbook();
                        XSSFSheet spreadsheet = workbook.createSheet("Khách hàng");
                        XSSFRow row = null;
                        Cell cell = null;

                        row = spreadsheet.createRow((short) 2);
                        row.setHeight((short) 500);
                        cell = row.createCell(0, CellType.STRING);
                        cell.setCellValue("DANH SÁCH HÓA ĐƠN (Từ ngày " + tuNgay + " đến ngày " + denNgay + ")");
/////////////////////////
                        CellStyle headerCellStyle = workbook.createCellStyle();
                        XSSFFont headerFont = workbook.createFont();
                        headerFont.setFontHeightInPoints((short) 14); // Size 14
                        headerCellStyle.setFont(headerFont);
                        cell.setCellStyle(headerCellStyle);
//////////////////////////
                        row = spreadsheet.createRow((short) 3);
                        row.setHeight((short) 500);
                        cell = row.createCell(0, CellType.STRING);
                        cell.setCellValue("STT");
                        cell.setCellStyle(headerCellStyle);
                        spreadsheet.setColumnWidth(0, 3000);

                        cell = row.createCell(1, CellType.STRING);
                        cell.setCellValue("Mã hóa đơn");
                        cell.setCellStyle(headerCellStyle);
                        spreadsheet.setColumnWidth(1, 4000);

                        cell = row.createCell(2, CellType.STRING);
                        cell.setCellValue("Mã KH/Mã DV");
                        cell.setCellStyle(headerCellStyle);
                        spreadsheet.setColumnWidth(2, 4500);

                        cell = row.createCell(3, CellType.STRING);
                        cell.setCellValue("Tên KH/Tên DV");
                        cell.setCellStyle(headerCellStyle);
                        spreadsheet.setColumnWidth(3, 5000);

                        cell = row.createCell(4, CellType.STRING);
                        cell.setCellValue("Ngày lập");
                        cell.setCellStyle(headerCellStyle);
                        spreadsheet.setColumnWidth(4, 4000);

                        cell = row.createCell(5, CellType.STRING);
                        cell.setCellValue("Giờ vào");
                        cell.setCellStyle(headerCellStyle);
                        spreadsheet.setColumnWidth(5, 4000);

                        cell = row.createCell(6, CellType.STRING);
                        cell.setCellValue("Giờ ra");
                        cell.setCellStyle(headerCellStyle);
                        spreadsheet.setColumnWidth(6, 4000);

                        cell = row.createCell(7, CellType.STRING);
                        cell.setCellValue("Số giờ/ Số lượng");
                        cell.setCellStyle(headerCellStyle);
                        spreadsheet.setColumnWidth(7, 5000);

                        cell = row.createCell(8, CellType.STRING);
                        cell.setCellValue("Giá Phòng(Giờ)/Giá Dịch vụ");
                        cell.setCellStyle(headerCellStyle);
                        spreadsheet.setColumnWidth(8, 8000);

                        cell = row.createCell(9, CellType.STRING);
                        cell.setCellValue("Tổng tiền");
                        cell.setCellStyle(headerCellStyle);
                        spreadsheet.setColumnWidth(9, 5000);

                        int count = 0;
                        int k = 0;
                        double tongDoanhThu = 0;
                        for (int i = 0; i < listBill.size(); i++) {
                            Bill bill = listBill.get(i);

                            int l = 0;

                            CellStyle rowStyle = workbook.createCellStyle();
                            XSSFFont rowFont = workbook.createFont();

                            // Thiết lập font size cho Font
                            rowFont.setFontHeightInPoints((short) 12); // Size 12

                            // Áp dụng font cho CellStyle
                            rowStyle.setFont(rowFont);

                            String ngayThang = String.valueOf(bill.getThoiGianRa());
                            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                            Date date = null;
                            try {
                                date = sdfInput.parse(ngayThang);
                            } catch (ParseException e2) {
                                e2.printStackTrace();
                            }

                            // Định dạng lại đối tượng Date thành chuỗi mới
                            SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MM-yyyy");
                            String ngayThangDinhDang = sdfOutput.format(date);

                            SimpleDateFormat sdfOutput2 = new SimpleDateFormat("HH:mm:ss");
                            String gioPhutGiayTra = sdfOutput2.format(date);
                            //==============================
                            String gioDat = String.valueOf(bill.getThoiGianVao());
                            SimpleDateFormat sdfInput1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                            Date date1 = null;
                            try {
                                date1 = sdfInput1.parse(gioDat);
                            } catch (ParseException e2) {
                                e2.printStackTrace();
                            }


                            SimpleDateFormat sdfOutput1 = new SimpleDateFormat("HH:mm:ss");
                            String gioPhutGiayDat = sdfOutput1.format(date1);

                            row = spreadsheet.createRow((short) 4 + i + count + k + l);
                            row.setHeight((short) 400);

                            Cell cell2 = null;
                            cell2 = row.createCell(0);
                            cell2.setCellValue(i + 1);
                            cell2.setCellStyle(rowStyle);

                            cell2 = row.createCell(1);
                            cell2.setCellValue(bill.getMaHoaDon());
                            cell2.setCellStyle(rowStyle);

                            cell2 = row.createCell(2);
                            cell2.setCellValue(bill.getMaKH().getMaKhachHang());
                            cell2.setCellStyle(rowStyle);

                            cell2 = row.createCell(3);
                            cell2.setCellValue(bill.getMaKH().getTenKhachHang());
                            cell2.setCellStyle(rowStyle);

                            cell2 = row.createCell(4);
                            cell2.setCellValue(ngayThangDinhDang);
                            cell2.setCellStyle(rowStyle);

                            cell2 = row.createCell(5);
                            cell2.setCellValue(gioPhutGiayDat);
                            cell2.setCellStyle(rowStyle);

                            cell2 = row.createCell(6);
                            cell2.setCellValue(gioPhutGiayTra);
                            cell2.setCellStyle(rowStyle);

                            cell2 = row.createCell(7);
                            cell2.setCellValue(bill.tinhThoiGianSuDung());
                            cell2.setCellStyle(rowStyle);

                            cell2 = row.createCell(8);
                            cell2.setCellValue(df.format(bill.getMaPhong().getGiaPhong()));
                            cell2.setCellStyle(rowStyle);

                            cell2 = row.createCell(9);
                            cell2.setCellValue(df.format(bill.tinhTienPhong()));
                            cell2.setCellStyle(rowStyle);

                            ArrayList<DetailsOfService> details = detailOfServiceDAO.getDetailsOfServiceForBill(bill.getMaHoaDon());
                            double gia = 0;
                            int j = 0;
                            for (DetailsOfService detailsOfService : details) {

                                Cell cell3 = null;

                                row = spreadsheet.createRow((short) 5 + i + j + k + count);
                                row.setHeight((short) 400);
                                row.createCell(0).setCellValue("");
                                row.createCell(1).setCellValue("");

                                cell3 = row.createCell(2);
                                cell3.setCellValue(detailsOfService.getMaDichVu().getMaDichVu());
                                cell3.setCellStyle(rowStyle);

                                cell3 = row.createCell(3);
                                cell3.setCellValue(detailsOfService.getMaDichVu().getTenDichVu());
                                cell3.setCellStyle(rowStyle);

                                cell3 = row.createCell(4);
                                cell3.setCellValue(ngayThangDinhDang);
                                cell3.setCellStyle(rowStyle);

                                cell3 = row.createCell(5);
                                cell3.setCellValue(gioPhutGiayDat);
                                cell3.setCellStyle(rowStyle);

                                cell3 = row.createCell(6);
                                cell3.setCellValue(gioPhutGiayTra);
                                cell3.setCellStyle(rowStyle);

                                cell3 = row.createCell(7);
                                cell3.setCellValue(detailsOfService.getSoLuong());
                                cell3.setCellStyle(rowStyle);

                                cell3 = row.createCell(8);
                                cell3.setCellValue(df.format(detailsOfService.getGiaBan()));
                                cell3.setCellStyle(rowStyle);

                                cell3 = row.createCell(9);
                                cell3.setCellValue(df.format((detailsOfService.getGiaBan() * detailsOfService.getSoLuong())));
                                cell3.setCellStyle(rowStyle);

                                gia += detailsOfService.getGiaBan() * detailsOfService.getSoLuong();
                                j++;
                            }
                            count += j;
                            double quantity = bill.tinhTienPhong() + gia;
                            if (bill.getKhuyenMai().trim().equalsIgnoreCase("KM")) {
                                double totalBillWithVAT = quantity * 1.08; // Tính tổng bill kèm VAT
                                quantity = totalBillWithVAT - (totalBillWithVAT * 0.15);
                            } else {
                                quantity += quantity * 8 / 100;
                            }

                            Cell cell4 = null;

                            row = spreadsheet.createRow((short) 5 + i + k + count);
                            row.setHeight((short) 400);
                            row.createCell(0).setCellValue("");
                            row.createCell(1).setCellValue("");
                            row.createCell(2).setCellValue("");
                            row.createCell(3).setCellValue("");
                            row.createCell(4).setCellValue("");
                            row.createCell(5).setCellValue("");
                            row.createCell(6).setCellValue("");
                            row.createCell(7).setCellValue("");

                            cell4 = row.createCell(8);
                            cell4.setCellValue("Tổng tiền (VAT 8%):");
                            cell4.setCellStyle(rowStyle);

                            cell4 = row.createCell(9);
                            cell4.setCellValue(df.format(quantity));
                            cell4.setCellStyle(rowStyle);

                            k += 2;
                            l++;
                            tongDoanhThu += quantity;
                        }

                        row = spreadsheet.createRow((short) 5 + listBill.size() + k + count);

                        Cell cell5 = null;
                        cell5 = row.createCell(8);
                        cell5.setCellValue("TỔNG DOANH THU:");
                        cell5.setCellStyle(headerCellStyle);

                        cell5 = row.createCell(9);
                        cell5.setCellValue(df.format(tongDoanhThu));
                        cell5.setCellStyle(headerCellStyle);

//                        // Tạo tên file duy nhất dựa trên thời gian
//                        String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
//                        String fileName = "Báo cáo khách hàng ngày " + timeStamp + ".xlsx";
//                        if (!path.matches("^.+[\\\\/]$")) {
//                            path += "/";
//                        }
//                        File folder = new File(path);
//                        if (!folder.exists())
//                            folder.mkdir();
//
//                        String filePath = path + fileName;
//
//                        FileOutputStream out = new FileOutputStream(filePath);


                        // Tạo tên file duy nhất dựa trên thời gian
                        String timeStamp = new SimpleDateFormat("dd-MM-yyyy 'lúc' HH'giờ'mm'phút'ss'giây'").format(new Date());
                        String fileName = "E:\\16\\KaraokeManagement\\excel\\KhachHang"+"\\Báo cáo ngày " + timeStamp + ".xlsx";

                        FileOutputStream out = new FileOutputStream(fileName);


                        JOptionPane.showMessageDialog(null, "Xuất file thành công. File được lưu ở tệp excel");
                        workbook.write(out);
                        out.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Đưa dữ liệu thống kê vào table
     */
    private void docDuLieuVaoTable(ArrayList<Bill> listBill) {
        int i = 1;
        java.util.List<Customer> list = customerDAO.getAllKhachHang();
        ArrayList<DetailsOfService> list1 = (ArrayList<DetailsOfService>) detailOfServiceDAO.getAllDetailsOfService();
        HashMap<String, Double> totalSales = new HashMap<>();
        ArrayList<Object[]> dataToAdd = new ArrayList<>();

        for (Bill bill : listBill) {
            String customerID = bill.getMaKH().getMaKhachHang();
            double gia = 0;
            for (DetailsOfService details : list1) {
                if (bill.getMaHoaDon().equals(details.getMaHoaDon().getMaHoaDon())) {
                    gia += details.getSoLuong() * details.getGiaBan();
                }
            }

            double roomPrice = bill.tinhTienPhong();
            double totalServicePrice = gia;

            double totalQuantity;
            if (bill.getKhuyenMai().trim().equalsIgnoreCase("KM")) {
                // Nếu hóa đơn có mã khuyến mãi "KM", không tính VAT
                totalQuantity = ((roomPrice + totalServicePrice) * 1.08) - (((roomPrice + totalServicePrice) * 1.08) * 0.15);
            } else {
                // Nếu hóa đơn không có mã khuyến mãi hoặc khuyến mãi khác "", tính VAT 8%
                totalQuantity = (roomPrice + totalServicePrice) * 1.08;
            }

            if (totalSales.containsKey(customerID)) {
                // Nếu mã khách hàng đã tồn tại trong HashMap, cộng dồn số tiền
                double currentQuantity = totalSales.get(customerID);
                totalSales.put(customerID, (currentQuantity + totalQuantity));
            } else {
                // Nếu mã khách hàng chưa tồn tại trong HashMap, thêm vào với số tiền hiện tại
                totalSales.put(customerID, totalQuantity);
            }
        }

        for (String customerID : totalSales.keySet()) {
            double totalQuantity = totalSales.get(customerID);
            String s1 = "";
            for (Customer kh : list) {
                if (customerID.equals(kh.getMaKhachHang())) {
                    s1 = kh.getTenKhachHang();
                }
            }
            dataToAdd.add(new Object[]{i, customerID, s1, df.format(totalQuantity)});

        }

        // Sắp xếp danh sách tạm thời theo thứ tự giảm dần của doanh thu
        dataToAdd.sort((o1, o2) -> Double.compare(Double.valueOf(o2[3].toString().replace(",", "").replace(" VND", "")), Double.valueOf(o1[3].toString().replace(",", "").replace(" VND", ""))));

        // Tăng stt
        for (int j = 0; j < dataToAdd.size(); j++) {
            dataToAdd.get(j)[0] = j + 1;
        }

        modelTblDichVu.getDataVector().removeAllElements();
        tblDichVu.removeAll();
        for (Object[] rowData : dataToAdd) {
            modelTblDichVu.addRow(rowData);
        }
    }

    /**
     * kiểm tra ngày bắt đầu và ngày kết thúc
     *
     * @return fasle nếu ngày kết thúc < ngày bắt đầu và ngược lại thì true
     */
    public boolean validData() throws ParseException {
        Date tuNgay = pickerTuNgay.getFullDate();
        Date denNgay = pickerDenNgay.getFullDate();
        if (denNgay.before(tuNgay)) {
            return false;
        }
        return true;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object o = e.getSource();
        if (o.equals(cmbLocTheo)) {
            String search = cmbLocTheo.getSelectedItem().toString();
            switch (search) {
                case "7 ngày gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblDichVu.getDataVector().removeAllElements();
                    tblDichVu.removeAll();
                    Date tuNgay = pickerTuNgay.setDatesFromToday(Calendar.DAY_OF_MONTH, -6);
                    Date denNgay = null;
                    denNgay = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill = billDAO.getListBillByDate(tuNgay, denNgay);
                    docDuLieuVaoTable(listBill);
                    break;
                case "1 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblDichVu.getDataVector().removeAllElements();
                    tblDichVu.removeAll();
                    Date tuNgay1 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -1);
                    Date denNgay1 = null;
                    denNgay1 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill1 = billDAO.getListBillByDate(tuNgay1, denNgay1);
                    docDuLieuVaoTable(listBill1);
                    break;
                case "3 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblDichVu.getDataVector().removeAllElements();
                    tblDichVu.removeAll();
                    Date tuNgay2 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -2);
                    Date denNgay2 = null;
                    denNgay2 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill2 = billDAO.getListBillByDate(tuNgay2, denNgay2);
                    docDuLieuVaoTable(listBill2);
                    break;
                case "6 tháng gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblDichVu.getDataVector().removeAllElements();
                    tblDichVu.removeAll();
                    Date tuNgay3 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -5);
                    Date denNgay3 = null;
                    denNgay3 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill3 = billDAO.getListBillByDate(tuNgay3, denNgay3);
                    docDuLieuVaoTable(listBill3);
                    break;
                case "1 năm gần nhất":
                    pickerTuNgay.setActive(false);
                    pickerDenNgay.setActive(false);
                    modelTblDichVu.getDataVector().removeAllElements();
                    tblDichVu.removeAll();
                    Date tuNgay4 = pickerTuNgay.setDatesFromToday(Calendar.MONTH, -11);
                    Date denNgay4 = null;
                    denNgay4 = pickerDenNgay.getCurrentDatePlusOneDay();
                    ArrayList<Bill> listBill4 = billDAO.getListBillByDate(tuNgay4, denNgay4);
                    docDuLieuVaoTable(listBill4);
                    break;
                case "Tùy chỉnh":
                    pickerTuNgay.setActive(true);
                    pickerDenNgay.setActive(true);
                    pickerTuNgay.setValueToDay();
                    break;
            }
        }
    }

    /**
     * Hiển thị panel mới (chứa hóa đơn của dòng được click)
     *
     * @param selectedRow:    dòng được chọn
     * @param selectedOption: giá trị được chọn trong combobox
     * @param fromDate:       ngày bắt đầu
     * @param toDate:         ngày kết thúc
     */
    private void displayNewPanel(int selectedRow, String selectedOption, Date fromDate, Date toDate) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1300, 500));
        panel.setLayout(null);
        panel.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DANH SÁCH HÓA ĐƠN",
                TitledBorder.LEADING, TitledBorder.TOP));

        String[] colsDV = {"STT", "Mã HĐ", "Mã KH/Mã DV", "Tên KH/Tên DV", "Ngày lập", "Giờ vào", "Giờ ra", "Số giờ/Số lượng", "Giá phòng(Giờ)/Giá dịch vụ", "Tổng tiền"};
        DefaultTableModel modelTblDichVu1 = new DefaultTableModel(colsDV, 0);
        JScrollPane scrKhachHang;

        JTable tblDichVu1 = new JTable(modelTblDichVu1);
        tblDichVu1.setFont(new Font("Arial", Font.BOLD, 14));
        tblDichVu1.setBackground(new Color(255, 255, 255, 0));
        tblDichVu1.setForeground(new Color(255, 255, 255));
        tblDichVu1.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblDichVu1.getTableHeader().setForeground(Color.BLUE);
        Custom.getInstance().setCustomTable(tblDichVu1);

        tblDichVu1.getColumnModel().getColumn(0).setPreferredWidth(10);
        tblDichVu1.getColumnModel().getColumn(1).setPreferredWidth(30);
        tblDichVu1.getColumnModel().getColumn(2).setPreferredWidth(30);
        tblDichVu1.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblDichVu1.getColumnModel().getColumn(4).setPreferredWidth(30);
        tblDichVu1.getColumnModel().getColumn(5).setPreferredWidth(10);
        tblDichVu1.getColumnModel().getColumn(6).setPreferredWidth(10);
        tblDichVu1.getColumnModel().getColumn(7).setPreferredWidth(50);
        tblDichVu1.getColumnModel().getColumn(8).setPreferredWidth(150);
        tblDichVu1.getColumnModel().getColumn(9).setPreferredWidth(70);

        panel.add(scrKhachHang = new JScrollPane(tblDichVu1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        scrKhachHang.setBounds(10, 20, 1280, 470);

        String makh = modelTblDichVu.getValueAt(selectedRow, 1) + "";

        ArrayList<Bill> listBill;
        if ("7 ngày".equals(selectedOption)) {
            listBill = billDAO.getBillByCustomerIDAndDateRange(makh,
                    Date.from(fromDate.toInstant().minus(7, ChronoUnit.DAYS)), toDate);
        } else if ("1 tháng".equals(selectedOption)) {
            listBill = billDAO.getBillByCustomerIDAndDateRange(makh,
                    Date.from(fromDate.toInstant().minus(1, ChronoUnit.MONTHS)), toDate);
        } else if ("3 tháng".equals(selectedOption)) {
            listBill = billDAO.getBillByCustomerIDAndDateRange(makh,
                    Date.from(fromDate.toInstant().minus(3, ChronoUnit.MONTHS)), toDate);
        } else if ("6 tháng".equals(selectedOption)) {
            listBill = billDAO.getBillByCustomerIDAndDateRange(makh,
                    Date.from(fromDate.toInstant().minus(6, ChronoUnit.MONTHS)), toDate);
        } else if ("1 năm".equals(selectedOption)) {
            listBill = billDAO.getBillByCustomerIDAndDateRange(makh,
                    Date.from(fromDate.toInstant().minus(1, ChronoUnit.YEARS)), toDate);
        } else {
            listBill = billDAO.getBillByCustomerIDAndDateRange(makh, fromDate, toDate);
        }

        System.out.printf(listBill.size() + "");

        int i = 1;
        for (Bill bill : listBill) {
            String ngayThang = String.valueOf(bill.getThoiGianRa());
            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date = null;
            try {
                date = sdfInput.parse(ngayThang);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Định dạng lại đối tượng Date thành chuỗi mới
            SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MM-yyyy");
            String ngayThangDinhDang = sdfOutput.format(date);

            SimpleDateFormat sdfOutput2 = new SimpleDateFormat("HH:mm:ss");
            String gioPhutGiayTra = sdfOutput2.format(date);
            //==============================
            String gioDat = String.valueOf(bill.getThoiGianVao());
            SimpleDateFormat sdfInput1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date1 = null;
            try {
                date1 = sdfInput1.parse(gioDat);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat sdfOutput1 = new SimpleDateFormat("HH:mm:ss");
            String gioPhutGiayDat = sdfOutput1.format(date1);

            modelTblDichVu1.addRow(new Object[]{i, bill.getMaHoaDon(), bill.getMaKH().getMaKhachHang(), bill.getMaKH().getTenKhachHang(), ngayThangDinhDang, gioPhutGiayDat, gioPhutGiayTra, bill.tinhThoiGianSuDung(), df.format(bill.getMaPhong().getGiaPhong()), df.format(bill.tinhTienPhong())});
            ArrayList<DetailsOfService> details = detailOfServiceDAO.getDetailsOfServiceForBill(bill.getMaHoaDon());
            double gia = 0;
            for (DetailsOfService detailsOfService : details) {
                modelTblDichVu1.addRow(new Object[]{"", "", detailsOfService.getMaDichVu().getMaDichVu(), detailsOfService.getMaDichVu().getTenDichVu(), ngayThangDinhDang, gioPhutGiayDat, gioPhutGiayTra, detailsOfService.getSoLuong(), df.format(detailsOfService.getGiaBan()), df.format((detailsOfService.getGiaBan() * detailsOfService.getSoLuong()))});
                gia += detailsOfService.getGiaBan() * detailsOfService.getSoLuong();
            }
            double quantity = bill.tinhTienPhong() + gia;
            if (bill.getKhuyenMai().trim().equalsIgnoreCase("KM")) {
                double totalBillWithVAT = quantity * 1.08; // Tính tổng bill kèm VAT
                quantity = totalBillWithVAT - (totalBillWithVAT * 0.15);
            } else {
                quantity += quantity * 8 / 100;
            }
            modelTblDichVu1.addRow(new Object[]{"", "", "", "", "", "", "", "", "Tổng tiền (VAT 8%):", df.format(quantity)});
            modelTblDichVu1.addRow(new Object[]{});

            i++;
        }

        // Hiển thị panel mới
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}