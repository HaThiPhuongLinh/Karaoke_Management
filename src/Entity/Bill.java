package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
//    private List<DetailsOfBill> lstCTHD;

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

    public Double tinhGioThue() {
        int soPhut = 0;
        if (ngayGioTra != null && ngayGioDat != null) {
            long difference = ngayGioTra.getTime() - ngayGioDat.getTime();
            soPhut = (int) TimeUnit.MILLISECONDS.toMinutes(difference);
        }
        if(soPhut <= 60) {
            soPhut = 60;
        }
        soPhut = (int) soPhut / 15;
        return soPhut * 1.0 / 4;
    }
    public String tinhThoiGianSuDung() {
        int soPhut = 0;
        if (ngayGioTra != null && ngayGioDat != null) {
            long difference = ngayGioTra.getTime() - ngayGioDat.getTime();
            soPhut = (int) TimeUnit.MILLISECONDS.toMinutes(difference);
        }

        if (soPhut <= 60) {
            soPhut = 60;
        }

        int gio = soPhut / 60;
        int phut = soPhut % 60;

        return gio + " giờ " + phut + " phút";
    }
    public Double tinhTongTienDichVu() {
        Double tongTienDV = 0.0;
        for (DetailsOfService item : lstDetails) {
            tongTienDV += item.tinhTienDichVu();
        }
        return tongTienDV;
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
