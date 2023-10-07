package Entity;

import java.sql.Timestamp;
import java.util.Date;

public class Bill {
    private String maHoaDon;
    private Staff maNhanVien;
    private Customer maKhachHang;
    private Room maPhong;
    private double giaPhong;
    private Timestamp ngayGioDat;
    private Timestamp ngayGioTra;
    private int tinhTrangHD;
    private String khuyenMai;

    public Bill(){

    }

    public Bill(String maHoaDon){
        this.maHoaDon =maHoaDon;
    }

    public Bill(String maHoaDon, Staff maNhanVien, Customer maKH, Room maPhong, double giaPhong, Timestamp ngayGioDat, Timestamp ngayGioTra, int tinhTrangHD, String khuyenMai){
        this.maHoaDon= maHoaDon;
        this.maNhanVien=maNhanVien;
        this.maKhachHang=maKH;
        this.maPhong=maPhong;
        this.giaPhong=giaPhong;
        this.ngayGioDat=ngayGioDat;
        this.ngayGioTra=ngayGioTra;
        this.tinhTrangHD = tinhTrangHD;
        this.khuyenMai = khuyenMai;
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

    public double getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(double giaPhong) {
        this.giaPhong = giaPhong;
    }

    public Date getNgayGioDat() {
        return ngayGioDat;
    }

    public void setNgayGioDat(Timestamp ngayGioDat) {
        this.ngayGioDat = ngayGioDat;
    }

    public Date getNgayGioTra() {
        return ngayGioTra;
    }

    public void setNgayGioTra(Timestamp ngayGioTra) {
        this.ngayGioTra = ngayGioTra;
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


}
