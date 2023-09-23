package Entity;

import UI.main_interface.component.Customer;

import java.util.Date;

public class Bill {
    private String maHoaDon;
    private Staff maNhanVien;
    private Customer maKhachHang;
    private Room maPhong;
    private double giaPhong;
    private Date ngayGioDat;
    private Date ngayGioTra;
    private int tinhTrangHD;
    private String khuyenMai;
    private double tongTienHD;

    public Bill(){

    }

    public Bill(String maHoaDon){
        this.maHoaDon =maHoaDon;
    }

    public Bill(String maHoaDon, Staff maNhanVien, Customer maKH, Room maPhong, double giaPhong, Date ngayGioDat, Date ngayGioTra, int tinhTrangHD, String khuyenMai, double tongTienHD){
        this.maHoaDon= maHoaDon;
        this.maNhanVien=maNhanVien;
        this.maKhachHang=maKH;
        this.maPhong=maPhong;
        this.giaPhong=giaPhong;
        this.ngayGioDat=ngayGioDat;
        this.ngayGioTra=ngayGioTra;
        this.tinhTrangHD = tinhTrangHD;
        this.khuyenMai = khuyenMai;
        this.tongTienHD = tongTienHD;
    }

    public Bill(String maHoaDon, Staff maNhanVien, Entity.Customer maKH, Room maPhong, double giaPhong, Date ngayGioDat, Date ngayGioTra, int tinhTrang, String maKM, double tinhTongTien) {
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

    public void setNgayGioDat(Date ngayGioDat) {
        this.ngayGioDat = ngayGioDat;
    }

    public Date getNgayGioTra() {
        return ngayGioTra;
    }

    public void setNgayGioTra(Date ngayGioTra) {
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

    public double getTongTienHD() {
        return tongTienHD;
    }

    public void setTongTienHD(double tongTienHD) {
        this.tongTienHD = tongTienHD;
    }
}
