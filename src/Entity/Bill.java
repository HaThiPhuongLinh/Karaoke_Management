package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Entity: Hóa đơn
 * Người thiết kế: Nguyễn Đình Dương
 */
public class Bill {
    private String maHoaDon;
    private Staff maNhanVien;
    private Customer maKhachHang;
    private Room maPhong;
    private Timestamp thoiGianVao;
    private Timestamp thoiGianRa;
    private int tinhTrangHD;
    private String khuyenMai;


    private List<DetailsOfService> lstDetails;


    public Bill(String maHoaDon){
        this.maHoaDon =maHoaDon;
        this.lstDetails = new ArrayList<DetailsOfService>();
    }

    public Bill(ResultSet rs) throws SQLException {
        this(rs.getString("maHoaDon"), rs.getTimestamp("thoiGianVao"), rs.getTimestamp("thoiGianRa"),
                rs.getInt("tinhTrangHD"), rs.getString("khuyenMai"));
    }

    public Bill(String maHoaDon, Staff maNhanVien, Customer maKH, Room maPhong, Timestamp thoiGianVao, Timestamp thoiGianRa, int tinhTrangHD, String khuyenMai){
        this.maHoaDon= maHoaDon;
        this.maNhanVien=maNhanVien;
        this.maKhachHang=maKH;
        this.maPhong=maPhong;
        this.thoiGianVao = thoiGianVao;
        this.thoiGianRa = thoiGianRa;
        this.tinhTrangHD = tinhTrangHD;
        this.khuyenMai = khuyenMai;
    }

    public Bill(String maHoaDon, Staff maNhanVien, Customer maKhachHang, Room maPhong, Timestamp thoiGianVao, Timestamp thoiGianRa, int tinhTrangHD, String khuyenMai, List<DetailsOfService> lstDetails) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.maPhong = maPhong;
        this.thoiGianVao = thoiGianVao;
        this.thoiGianRa = thoiGianRa;
        this.tinhTrangHD = tinhTrangHD;
        this.khuyenMai = khuyenMai;
        this.lstDetails = lstDetails;
    }

    public Bill(List<DetailsOfService> lstDetails) {
        this.lstDetails = lstDetails;
    }

    public Bill(String maHoaDon, Timestamp thoiGianVao, Timestamp thoiGianRa, int tinhTrangHD, String khuyenMai) {
        this.maHoaDon = maHoaDon;
        this.thoiGianVao = thoiGianVao;
        this.thoiGianRa = thoiGianRa;
        this.tinhTrangHD = tinhTrangHD;
        this.khuyenMai = khuyenMai;

        this.lstDetails = new ArrayList<DetailsOfService>();
    }

    public Bill() {

    }

    public List<DetailsOfService> getLstDetails() {
        return lstDetails;
    }

    public void setLstDetails(List<DetailsOfService> lstDetails) {
        this.lstDetails = lstDetails;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public Staff getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(Staff maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public Customer getMaKH() {
        return maKhachHang;
    }

    public void setMaKH(Customer maKH) {
        this.maKhachHang = maKH;
    }

    public Room getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(Room maPhong) {
        this.maPhong = maPhong;
    }

    public Timestamp getThoiGianVao() {
        return thoiGianVao;
    }

    public void setThoiGianVao(Timestamp thoiGianVao) {
        this.thoiGianVao = thoiGianVao;
    }

    public Timestamp getThoiGianRa() {
        return thoiGianRa;
    }

    public void setThoiGianRa(Timestamp thoiGianRa) {
        this.thoiGianRa = thoiGianRa;
    }

    public int getTinhTrangHD() {
        return tinhTrangHD;
    }

    public void setTinhTrangHD(int tinhTrangHD) {
        this.tinhTrangHD = tinhTrangHD;
    }

    public String getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(String khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", maNhanVien=" + maNhanVien +
                ", maKhachHang=" + maKhachHang +
                ", maPhong=" + maPhong +
                ", thoiGianVao=" + thoiGianVao +
                ", thoiGianRa=" + thoiGianRa +
                ", tinhTrangHD=" + tinhTrangHD +
                ", khuyenMai='" + khuyenMai + '\'' +
                ", lstDetails=" + lstDetails +
                '}';
    }

    /**
     * Hàm tính giờ thuê phòng dựa trên thời gian bắt đầu thuê (ngày giờ đặt) và thời gian kết thúc thuê (ngày giờ trả)
     * @return {@code Double}: Thời gian sử dụng phòng
     */
    public Double tinhGioThue() {
        int soPhut = 0;
        if (thoiGianRa != null && thoiGianVao != null) {
            long difference = thoiGianRa.getTime() - thoiGianVao.getTime();
            soPhut = (int) TimeUnit.MILLISECONDS.toMinutes(difference);
        }
        return soPhut * 1.0 / 60;
    }

    /**
     * Hàm tính thời gian sử dụng phòng trả về dạng chuỗi
     * @return {@code String}: Thời gian sử dụng phòng
     */
    public String tinhThoiGianSuDung() {
        int soPhut = 0;
        if (thoiGianRa != null && thoiGianVao != null) {
            long difference = thoiGianRa.getTime() - thoiGianVao.getTime();
            soPhut = (int) TimeUnit.MILLISECONDS.toMinutes(difference);
        }

        int gio = soPhut / 60;
        int phut = soPhut % 60;

        // Nếu số phút nhỏ hơn hoặc bằng 60, hiển thị theo số phút.
        if (soPhut <= 60) {
            return soPhut + " phút";
        } else {
            // Ngược lại, hiển thị theo giờ và phút.
            return gio + " giờ " + phut + " phút";
        }
    }

    /**
     * Tính tổng tiền dịch vụ
     * @return {@code Double}: tiền dịch vụ đã đặt
     */
    public Double tinhTongTienDichVu() {
        if (lstDetails == null) {
            return 0.0;
        }
        else {
            Double tongTienDV = 0.0;
            for (DetailsOfService item : lstDetails) {
                tongTienDV += item.tinhTienDichVu();
            }
            return tongTienDV;
        }
    }
    /**
     * Tính tiền thuê phòng
     *
     * @return {@code Double}: tiền phòng đã thuê
     */
    public Double tinhTienPhong() {
        Double soGio = tinhGioThue();
        if (soGio < 1.0) {
            soGio = 1.0;
        }
        return soGio * getMaPhong().getGiaPhong();
    }
    public Double getTongTienHD() {
        return tinhTienPhong()+tinhTongTienDichVu();
    }


}
