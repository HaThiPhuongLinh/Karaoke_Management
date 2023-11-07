package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Lớp chi tiết dịch vụ
 * <p>
 * Người tham gia thiết kế: Nguyễn Quang Duy
 * <p>
 * Ngày tạo: 07/10/2023
 * <p>
 * Lần cập nhật cuối: 07/11/2023
 * <p>
 * Nội dung cập nhật: thêm javadoc
 */

public class DetailsOfService {
    private Bill maHoaDon;
    private Service maDichVu;
    private int soLuong;
    private double giaBan;

    /**
     * Tạo 1 {@code DetailsOfService} với các tham số sau:
     * @param maHoaDon mã hóa đơn
     * @param maDichVu mã dịch vụ
     * @param soLuong số lượng
     * @param giaBan giá bán
     */
    public DetailsOfService(Bill maHoaDon, Service maDichVu, int soLuong, double giaBan) {
        this.maHoaDon = maHoaDon;
        this.maDichVu = maDichVu;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
    }

    /**
     * Tạo 1 {@code DetailsOfService} từ kết quả truy vấn nhận được từ cơ sở dữ liệu
     * @param rs kết quả truy vấn
     * @throws SQLException nếu có lỗi trong quá trình lấy dữ liệu
     */
    public DetailsOfService(ResultSet rs) throws SQLException {
        this(new Bill(rs),  new Service(rs), rs.getInt("soLuong"), rs.getDouble("giaBan"));
    }
    /**
     * Lấy mã hóa đơn
     * @return mã hóa đơn được tìm thấy
     */
    public Bill getMaHoaDon() {
        return maHoaDon;
    }
    /**
     * cập nhật mã hóa đơn
     * @param maHoaDon mã loại dịch vụ
     */
    public void setMaHoaDon(Bill maHoaDon) {
        this.maHoaDon = maHoaDon;
    }
    /**
     * Lấy mã dịch vụ
     * @return mã dịch vụ được tìm thấy
     */
    public Service getMaDichVu() {
        return maDichVu;
    }
    /**
     * cập nhật mã dịch vụ
     * @param maDichVu mã dịch vụ
     */
    public void setMaDichVu(Service maDichVu) {
        this.maDichVu = maDichVu;
    }
    /**
     * Lấy giá bán
     * @return giá bán được tìm thấy
     */
    public double getGiaBan() {
        return giaBan;
    }
    /**
     * cập nhật giá bán
     * @param giaBan giá bán
     */
    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }
    /**
     * Lấy số lượng
     * @return số lượng được tìm thấy
     */
    public int getSoLuong() {
        return soLuong;
    }
    /**
     * cập nhật số lượng
     * @param soLuong số lượng
     */
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    /**
     * tính tiền dịch vụ
     * @return tiền dịch vụ
     */
    public Double tinhTienDichVu() {
        return giaBan * soLuong;
    }

}
