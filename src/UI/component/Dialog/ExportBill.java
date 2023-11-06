package UI.component.Dialog;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import UI.CustomUI.Custom;
import Entity.*;
/**
 * Sử dụng xuất hóa đơn dạng pdf
 * Người tham gia thiết kế: Nguyen Dinh Duong
 * Ngày tạo: 27/10/2023
 * Lần cập nhật cuối: 06/11/2021
 * Nội dung cập nhật: sửa lỗi hiển thị khi xuất file pdf
 */
public class ExportBill {


    private URL fontPath = ExportBill.class.getResource(Custom.pathFont);

    private BaseFont baseFontMedium;
    private BaseFont baseFontLight;
    private BaseFont baseFontLightItalic;
    private int pdfAlignLeft = Element.ALIGN_LEFT;
    private int pdfAlignCenter = Element.ALIGN_CENTER;
    private int pdfAlignRight = Element.ALIGN_RIGHT;

    private DecimalFormat df = new DecimalFormat("#,###.##");
    private String formatTime = "HH:mm:ss dd/MM/yyyy";
    private String karaokeName = "ROSIE KARAOKE";
    private String address = "12 Nguyen Van Bao, Phuong 4, Go Vap, Thanh pho Ho Chi Minh";
    private String phoneNumber = "0828012868";

    private static ExportBill instance = new ExportBill();

    public static ExportBill getInstance() {
        if (instance == null)
            instance = new ExportBill();
        return instance;
    }
    /**
     * Chuyển số giờ thuê thành chuỗi dạng {@code x giờ y phút}
     *
     * @param hours {@code double}: số giờ thuê
     * @return {@code String}: chuỗi dạng {@code x giờ y phút}
     */

    private String convertRentalTime(double hours) {
        int minutes = (int) (hours % 1 * 60);
        int hoursInt = (int) hours;
        String minutesStr = minutes < 10 ? "0" + minutes : minutes + "";
        String hoursStr = hoursInt < 10 ? "0" + hoursInt : hoursInt + "";
        String time = String.format("%s gio %s phút", hoursStr, minutesStr);
        return time;
    }



    /**
     * tạo khoản trắng trên file pdf
     *
     * @return {@code Paragraph}: dòng khoản trắng
     */
    private Paragraph skipRowPdf() {
        Font skipFont = new Font(baseFontLight, 5);
        Chunk chunkSkip = new Chunk(" ", skipFont);
        Paragraph pSkip = new Paragraph(chunkSkip);
        return pSkip;
    }

    /**
     * tạo 1 dòng thông tin hóa đơn
     *
     * @param label       {@code String}: tên của dòng
     * @param value       {@code String}: giá trị của dòng
     * @param numberOfTab {@code int}: số tab cần thêm
     * @return {@code Paragraph}: dòng thông tin hóa đơn
     */
    private Paragraph createRowBillInfoPdf(String label, String value, int numberOfTab) {
        Font font = new Font(baseFontLight, 15);

        Chunk chunk = new Chunk(label, font);
        Paragraph paragraph = new Paragraph(chunk);
        for (int i = 0; i < numberOfTab; i++) {
            paragraph.add(Chunk.TABBING);
        }
        paragraph.add(value);
        return paragraph;
    }

    /**
     * Tạo 1 ô chứa thông tin dịch vụ đã đặt
     *
     * @param value             {@code String}: giá trị của dòng
     * @param borderWidthBottom {@code int}: độ rộng của viền dưới
     * @param paddingBottom     {@code int}: khoảng cách dưới so với chữ
     * @param font              {@code Font}: font chữ
     * @param align             {@code int}: căn lề của dòng
     *                          <ul>
     *                          <li>Căn trái thì truyền vào {@code 0} hoặc dùng biến
     *                          {@code pdfAlignLeft}</li>
     *                          <li>Căn giữa thì truyền vào {@code 1} hoặc dùng biến
     *                          {@code pdfAlignCenter}</li>
     *                          <li>Căn phải thì truyền vào {@code 2} hoặc dùng biến
     *                          {@code pdfAlignRight}</li>
     *                          </ul>
     * @return {@code PdfPCell}: ô thông tin dịch vụ đã đặt
     */
    private PdfPCell createCellServiceOrder(String value, int borderWidthBottom, int paddingBottom, Font font,
                                            int align) {
        PdfPCell cell = new PdfPCell(new Phrase(value, font));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidthBottom(borderWidthBottom);
        cell.setPaddingBottom(paddingBottom);
        cell.setHorizontalAlignment(align);
        return cell;
    }

    /**
     * Tạo phần header cho hóa đơn trên file pdf
     *
     * @param doc {@code Document}: document xử lý thông tin hóa đơn
     */
    private void showHeaderPdf(Document doc) {
        try {
            // karaoke name
            Font fontKaraokeName = new Font(baseFontMedium, 20);
            Chunk chunkKaraokeName = new Chunk(karaokeName, fontKaraokeName);
            Paragraph pKaraokeName = new Paragraph(chunkKaraokeName);
            pKaraokeName.setAlignment(pdfAlignCenter);
            doc.add(pKaraokeName);

            // address
            Font font = new Font(baseFontLight, 15);
            Chunk chunkAddress = new Chunk(address, font);
            Paragraph pAddress = new Paragraph(chunkAddress);
            pAddress.setAlignment(pdfAlignCenter);
            doc.add(pAddress);

            // phone number
            Chunk chunkPhoneNumber = new Chunk(phoneNumber, font);
            Paragraph pPhoneNumber = new Paragraph(chunkPhoneNumber);
            pPhoneNumber.setAlignment(pdfAlignCenter);
            doc.add(pPhoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tạo phần thông tin hóa đơn trên file pdf
     *
     * @param doc {@code Document}: document xử lý thông tin hóa đơn
     */
    private void showBillInfoPdf(Bill bill, Document doc) {
        try {
            // skip
            Paragraph pSkip = skipRowPdf();
            doc.add(pSkip);

            // bill name
            Font fontBillName = new Font(baseFontMedium, 20);
            Chunk chunkBillName = new Chunk("HOA DON TINH TIEN", fontBillName);
            Paragraph pBillName = new Paragraph(chunkBillName);
            pBillName.setAlignment(pdfAlignCenter);
            doc.add(pBillName);

            // skip
            doc.add(pSkip);

            // bill id
            Paragraph pBillId = createRowBillInfoPdf("Ma hoa don: ", bill.getMaHoaDon(), 5);
            doc.add(pBillId);

            // staff Name
            Staff staff = bill.getMaNhanVien();
            Paragraph pStaffName = createRowBillInfoPdf("Thu ngan: ", staff.getTenNhanVien(), 6);
            doc.add(pStaffName);

            // customer Name
            Customer customer = bill.getMaKH();
            Paragraph pCustomerName = createRowBillInfoPdf("Ten khach hang: ", customer.getTenKhachHang(), 4);
            doc.add(pCustomerName);

            // room id
            Paragraph pRoomId = createRowBillInfoPdf("So phong: ", bill.getMaPhong().getMaPhong(), 6);
            doc.add(pRoomId);

            // room type name
            TypeOfRoom roomType = bill.getMaPhong().getLoaiPhong();
            Paragraph pRoomTypeName = createRowBillInfoPdf("Loai phong: ", roomType.getTenLoaiPhong(), 5);
            doc.add(pRoomTypeName);

            // room type price
            String roomTypePrice = df.format(bill.getMaPhong().getGiaPhong()) + "d/gio";
            Paragraph pRoomTypePrice = createRowBillInfoPdf("Gia phong: ", roomTypePrice, 5);
            doc.add(pRoomTypePrice);

            // start Time
            String startTime = ConvertTime.getInstance().convertTimeToString(bill.getNgayGioDat(), formatTime);
            Paragraph pStartTime = createRowBillInfoPdf("Gio bat dau: ", startTime, 5);
            doc.add(pStartTime);

            // end Time
            String endTime = ConvertTime.getInstance().convertTimeToString(bill.getNgayGioTra(), formatTime);
            Paragraph pEndTime = createRowBillInfoPdf("Gio ket thuc: ", endTime, 5);
            doc.add(pEndTime);

            // used Time
            String usedTime = convertRentalTime(bill.tinhGioThue());
            Paragraph pUsedTime = createRowBillInfoPdf("Thoi gian su dung: ", usedTime, 4);
            doc.add(pUsedTime);

            // skip
            doc.add(pSkip);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị thông tin các dịch vụ đã đặt
     *

     * @param doc  {@code Document}: document xử lý thông tin hóa đơn
     */
    private void showServiceOrderPdf(List<DetailsOfService> serviceOrders, Document doc) {
        int serviceOrdersLastIndex = serviceOrders.size();
        Font fontHeader = new Font(baseFontMedium, 15);
        Font fontContent = new Font(baseFontLight, 15);
        int borderWidthHeader = 2;
        int borderWidthContent = 0;
        int paddingBottom = 8;
        try {
            // skip
            Paragraph pSkip = skipRowPdf();
            doc.add(pSkip);

            // table Service Order
            PdfPTable table = new PdfPTable(4);
            table.setLockedWidth(false);
            float[] columnWidth = { 290f, 60f, 125f, 125f };
            table.setWidthPercentage(columnWidth, PageSize.A4);

            PdfPCell cell1 = createCellServiceOrder("Ten", borderWidthHeader, paddingBottom, fontHeader, pdfAlignLeft);
            table.addCell(cell1);

            PdfPCell cell2 = createCellServiceOrder("SL", borderWidthHeader, paddingBottom, fontHeader, pdfAlignRight);
            table.addCell(cell2);

            PdfPCell cell3 = createCellServiceOrder("Don Gia", borderWidthHeader, paddingBottom, fontHeader,
                    pdfAlignRight);
            table.addCell(cell3);

            PdfPCell cell4 = createCellServiceOrder("T.Tien", borderWidthHeader, paddingBottom, fontHeader,
                    pdfAlignRight);
            table.addCell(cell4);

            for (int i = 0; i < serviceOrdersLastIndex; i++) {
                DetailsOfService serviceOrder = serviceOrders.get(i);
                Service service = serviceOrder.getMaDichVu();
                if (i == serviceOrdersLastIndex - 1) {
                    borderWidthContent = 2;
                }
                cell1 = createCellServiceOrder(service.getTenDichVu(), borderWidthContent, paddingBottom, fontContent,
                        pdfAlignLeft);
                table.addCell(cell1);

                String quantityOrder = df.format(serviceOrder.getSoLuong());
                cell2 = createCellServiceOrder(quantityOrder, borderWidthContent, paddingBottom, fontContent,
                        pdfAlignRight);
                table.addCell(cell2);

                String price = df.format(serviceOrder.getGiaBan());
                cell3 = createCellServiceOrder(price, borderWidthContent, paddingBottom, fontContent, pdfAlignRight);
                table.addCell(cell3);

                String totalPrice = df.format(serviceOrder.tinhTienDichVu());
                cell4 = createCellServiceOrder(totalPrice, borderWidthContent, paddingBottom, fontContent,
                        pdfAlignRight);
                table.addCell(cell4);
            }
            doc.add(table);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị thông tin thanh toán hóa đơn
     *
     * @param bill {@code HoaDon}: hóa đơn cần hiển thị thông tin thanh toán
     * @param doc  {@code Document}: document xử lý thông tin hóa đơn
     */
    private void showTotalPricePdf(Bill bill, Document doc) {
        Font fontContent = new Font(baseFontLight, 15);
        int borderWidthContent = 0;
        int paddingBottom = 6;

        Double totalPriceService = bill.tinhTongTienDichVu();
        Double totalPriceRoom = bill.tinhTienPhong();
        double vat = 0;
        Date ngayHienTai = new Date();

// Chuyển ngày hiện tại và ngày sinh của khách hàng thành lớp Calendar
        Calendar calendarHienTai = Calendar.getInstance();
        calendarHienTai.setTime(ngayHienTai);

        Calendar calendarNgaySinhKhachHang = Calendar.getInstance();
        calendarNgaySinhKhachHang.setTime(bill.getMaKH().getNgaySinh());

// Lấy ngày và tháng của ngày hiện tại
        int ngayHienTaiValue = calendarHienTai.get(Calendar.DAY_OF_MONTH);
        int thangHienTaiValue = calendarHienTai.get(Calendar.MONTH);

// Lấy ngày và tháng từ ngày sinh của khách hàng
        int ngaySinhKhachHangValue = calendarNgaySinhKhachHang.get(Calendar.DAY_OF_MONTH);
        int thangSinhKhachHangValue = calendarNgaySinhKhachHang.get(Calendar.MONTH);
        if (ngayHienTaiValue == ngaySinhKhachHangValue && thangHienTaiValue == thangSinhKhachHangValue) {
            vat = 0.0;

        } else {
            vat = (totalPriceService + totalPriceRoom) * 0.08;
        }
        Double totalPriceBill = bill.getTongTienHD() +vat;
        String labels[] = { "Tong tien dich vu:", "Tong tien phong:", "VAT(8%):", "Tong cong:" };
        String values[] = { df.format(totalPriceService), df.format(totalPriceRoom), df.format(vat),
                df.format(totalPriceBill) };
        try {
            // skip
            Paragraph pSkip = skipRowPdf();
            doc.add(pSkip);

            // bill id
            PdfPTable table = new PdfPTable(2);
            table.setLockedWidth(false);
            float[] columnWidth = { 300f, 300f };
            table.setWidthPercentage(columnWidth, PageSize.A4);

            int lastIndex = labels.length;
            for (int i = 0; i < lastIndex; i++) {
                if (i == lastIndex - 1) {
                    fontContent = new Font(baseFontMedium, 17);
                }
                PdfPCell cell1 = createCellServiceOrder(labels[i], borderWidthContent, paddingBottom, fontContent,
                        pdfAlignLeft);
                table.addCell(cell1);

                PdfPCell cell2 = createCellServiceOrder(values[i], borderWidthContent, paddingBottom, fontContent,
                        pdfAlignRight);
                table.addCell(cell2);
            }
            doc.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tạo chân trang hóa đơn
     *
     * @param doc {@code Document}: document xử lý chân trang hóa đơn
     */
    private void showFooterPdf(Document doc) {
        Font font = new Font(baseFontLightItalic, 15);
        // skip
        String message = "Quy khach vui lòng kiem tra lai hoa don truoc khi thanh toan. "
                + "\nXin cam on va hen gap lai quy khach";
        try {
            Paragraph pSkip = skipRowPdf();
            doc.add(pSkip);

            Chunk chunk = new Chunk(message, font);
            Paragraph pSeeYouAgain = new Paragraph(chunk);
            pSeeYouAgain.setAlignment(pdfAlignCenter);
            doc.add(pSeeYouAgain);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Xuất file pdf hóa đơn dựa trong mã hóa đơn và đường dẫn đến nơi xuất file
     *

     * @param path   {@code String}: đường dẫn đến file pdf
     */
    public boolean exportBillToPdf(Bill bill, String path) {
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();
        String fileName = bill.getMaHoaDon() + ".pdf";
        if (!path.matches("^.+[\\\\/]$")) {
            path += "/";
        }

        String filePath = path + fileName;
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {


            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            showHeaderPdf(document);
            showBillInfoPdf(bill, document);
            showServiceOrderPdf(bill.getLstDetails(), document);
            showTotalPricePdf(bill, document);
            showFooterPdf(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
