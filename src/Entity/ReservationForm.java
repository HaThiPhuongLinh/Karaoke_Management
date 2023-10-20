package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

public class ReservationForm {
    private String maPhieuDat;
    private Timestamp thoiGianDat;
    private Timestamp thoiGianNhanPhong;
    private Staff maNhanVien;
    private Customer maKhachHang;
    private Room maPhong;

    public ReservationForm(ResultSet rs) throws SQLException {
        this(rs.getString(1), rs.getTimestamp(2), rs.getTimestamp(3), new Staff(rs), new Customer(rs), new Room(rs));
    }

    public ReservationForm(String maPhieuDat, Timestamp thoiGianDat, Timestamp thoiGianNhanPhong, Staff maNhanVien, Customer maKhachHang, Room maPhong) {
        this.maPhieuDat = maPhieuDat;
        this.thoiGianDat = thoiGianDat;
        this.thoiGianNhanPhong = thoiGianNhanPhong;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.maPhong = maPhong;
    }

    public String getMaPhieuDat() {
        return maPhieuDat;
    }

    public void setMaPhieuDat(String maPhieuDat) {
        this.maPhieuDat = maPhieuDat;
    }

    public Timestamp getThoiGianDat() {
        return thoiGianDat;
    }

    public void setThoiGianDat(Timestamp thoiGianDat) {
        this.thoiGianDat = thoiGianDat;
    }

    public Timestamp getThoiGianNhanPhong() {
        return thoiGianNhanPhong;
    }

    public void setThoiGianNhanPhong(Timestamp thoiGianNhanPhong) {
        this.thoiGianNhanPhong = thoiGianNhanPhong;
    }

    public Staff getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(Staff maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public Customer getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(Customer maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public Room getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(Room maPhong) {
        this.maPhong = maPhong;
    }

    @Override
    public String toString() {
        return "ReservationForm{" +
                "maPhieuDat='" + maPhieuDat + '\'' +
                ", thoiGianDat=" + thoiGianDat +
                ", thoiGianNhanPhong=" + thoiGianNhanPhong +
                ", maNhanVien=" + maNhanVien +
                ", maKhachHang=" + maKhachHang +
                ", maPhong=" + maPhong +
                '}';
    }
}
