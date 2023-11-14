package Entity;

import DAO.ReservationFormDAO;
import DAO.RoomDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Entity: Phiếu đặt phòng
 * Người thiết kế: Hà Thị Phương Linh
 */
public class ReservationForm {
    private String maPhieuDat;
    private Timestamp thoiGianGoi;
    private Timestamp thoiGianDat;
    private Staff maNhanVien;
    private Customer maKhachHang;
    private Room maPhong;
    private int trangThai;

    public ReservationForm() {
    }
//
//    public ReservationForm(String maPhieuDat, Timestamp thoiGianDat, Staff maNhanVien, Customer maKhachHang, Room maPhong) {
//        this.maPhieuDat = maPhieuDat;
//        this.thoiGianDat = thoiGianDat;
//        this.maNhanVien = maNhanVien;
//        this.maKhachHang = maKhachHang;
//        this.maPhong = maPhong;
//    }

    public ReservationForm(String maPhieuDat, Timestamp thoiGianGoi, Timestamp thoiGianDat, Staff maNhanVien, Customer maKhachHang, Room maPhong,  int trangThai) {
        this.maPhieuDat = maPhieuDat;
        this.thoiGianGoi = thoiGianGoi;
        this.thoiGianDat = thoiGianDat;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.maPhong = maPhong;
        this.trangThai = trangThai;
    }

    public ReservationForm(String maPhieuDat) {
        ReservationFormDAO reservationFormDAO = new ReservationFormDAO();
        ReservationForm r = reservationFormDAO.getReservationFormByFormId(maPhieuDat);
        this.maPhieuDat = r.getMaPhieuDat();
        this.thoiGianDat = r.getThoiGianDat();
        this.maNhanVien = r.getMaNhanVien();
        this.maKhachHang = r.getMaKhachHang();
        this.maPhong = r.getMaPhong();
        this.thoiGianGoi = r.getThoiGianGoi();
        this.trangThai = r.getTrangThai();
    }

    public Timestamp getThoiGianGoi() {
        return thoiGianGoi;
    }

    public void setThoiGianGoi(Timestamp thoiGianGoi) {
        this.thoiGianGoi = thoiGianGoi;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
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
                ", thoiGianGoi=" + thoiGianGoi +
                ", thoiGianDat=" + thoiGianDat +
                ", maNhanVien=" + maNhanVien +
                ", maKhachHang=" + maKhachHang +
                ", maPhong=" + maPhong +
                ", trangThai=" + trangThai +
                '}';
    }
}
