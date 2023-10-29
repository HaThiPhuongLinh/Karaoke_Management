package UI.component.Dialog;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Lớp này dùng để chuyển đổi thời gian sang các định dạng khác nhau:
 * <ul>
 * <li>{@code java.sql.Date} sang {@code java.util.Date}</li>
 * <li>{@code java.sql.Date} sang {@code String}</li>
 * <li>{@code java.sql.Timestamp} sang {@code String}</li>
 * </ul>
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 13/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public class ConvertTime {
    private static ConvertTime instance = new ConvertTime();

    /**
     * Sử dụng kiến trúc singleton để tạo ra đối tượng {@code ConvertTime} duy nhất
     *
     * @return {@code ConvertTime}: đối tượng ConvertTime
     */
    public static ConvertTime getInstance() {
        if (instance == null)
            instance = new ConvertTime();
        return instance;
    }

    /**
     * Chuyển đổi ngày ở kiểu dữ liệu {@code java.sql.Date} sang ngày ở kiểu dữ liệu
     * java.util.Date
     *
     * @param date {@code java.sql.Date}
     * @return java.util.Date
     */
    public java.util.Date convertSqlDateToUtilDate(Date date) {
        java.util.Date utilDate = new java.util.Date(date.getTime());
        return utilDate;
    }

    /**
     * chuyển đổi ngày ở kiểu dữ liệu {@code java.util.Date} sang ngày ở kiểu dạng
     * chuỗi
     *
     * @param date       {@code java.sql.Date}: ngày cần chuyển đổi ở kiểu dữ liệu
     * @param formatTime {@code String}: định dạng cần format. Ví dụ:
     *                   {@code dd/MM/yyyy}, {@code hh:mm:ss},
     *                   {@code dd/MM/yyyy hh:mm:ss}, ...
     * @return {@code String}: chuỗi ngày giờ đã định dạng
     */
    public String convertTimeToString(Date date, String formatTime) {
        java.util.Date utilDate = new java.util.Date(date.getTime());
        DateFormat df = new SimpleDateFormat(formatTime);
        return df.format(utilDate);
    }

    /**
     * chuyển đổi ngày ở kiểu dữ liệu {@code java.sql.Timestamp} sang ngày ở kiểu
     * dạng chuỗi
     *
     * @param date       {@code java.sql.Timestamp}: ngày cần chuyển đổi ở kiểu dữ
     *                   liệu
     * @param formatTime {@code String}: định dạng cần format. Ví dụ:
     *                   {@code dd/MM/yyyy}, {@code hh:mm:ss},
     *                   {@code dd/MM/yyyy hh:mm:ss}, ...
     * @return {@code String}: chuỗi ngày giờ đã định dạng
     */
    public String convertTimeToString(Timestamp date, String formatTime) {
        java.util.Date utilDate = new java.util.Date(date.getTime());
        DateFormat df = new SimpleDateFormat(formatTime);
        return df.format(utilDate);
    }
}

