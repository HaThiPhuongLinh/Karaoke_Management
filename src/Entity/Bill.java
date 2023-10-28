package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bill {
    private String maHoaDon;
    private Staff maNhanVien;
    private Customer maKhachHang; 
    private Room maPhong;
    private Timestamp ngayGioDat;
    private Timestamp ngayGioTra;
    private int tinhTrangHD;
    private String khuyenMai;


    private List<DetailsOfService> lstDetails;

    public Bill(String maHoaDon){
        this.maHoaDon =maHoaDon;
        this.lstDetails = new ArrayList<DetailsOfService>();
    }

    public Bill(ResultSet rs) throws SQLException {
        this(rs.getString("maHoaDon"), rs.getTimestamp("ngayGioDat"), rs.getTimestamp("ngayGioTra"),
                rs.getInt("tinhTrangHD"), rs.getString("khuyenMai"));
    }

    public Bill(String maHoaDon, Staff maNhanVien, Customer maKH, Room maPhong, Timestamp ngayGioDat, Timestamp ngayGioTra, int tinhTrangHD, String khuyenMai){
        this.maHoaDon= maHoaDon;
        this.maNhanVien=maNhanVien;
        this.maKhachHang=maKH;
        this.maPhong=maPhong;
        this.ngayGioDat=ngayGioDat;
        this.ngayGioTra=ngayGioTra;
        this.tinhTrangHD = tinhTrangHD;
        this.khuyenMai = khuyenMai;
    }

    public Bill(String maHoaDon, Timestamp ngayGioDat, Timestamp ngayGioTra, int tinhTrangHD, String khuyenMai) {
        this.maHoaDon = maHoaDon;
        this.ngayGioDat = ngayGioDat;
        this.ngayGioTra = ngayGioTra;
        this.tinhTrangHD = tinhTrangHD;
        this.khuyenMai = khuyenMai;

        this.lstDetails = new ArrayList<DetailsOfService>();
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

    public Timestamp getNgayGioDat() {
        return ngayGioDat;
    }

    public void setNgayGioDat(Timestamp ngayGioDat) {
        this.ngayGioDat = ngayGioDat;
    }

    public Timestamp getNgayGioTra() {
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
