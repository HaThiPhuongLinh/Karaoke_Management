package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class Staff {
    private String maNhanVien;
    private String tenNhanVien;
    private String soDienThoai;
    private String CCCD;
    private boolean gioiTinh;
    private Date ngaySinh;
    private String diaChi;
    private String chucVu;
    private String trangThai;
    private Account taiKhoan;

    public Staff(ResultSet rs) throws SQLException {
        this(rs.getString("maNhanVien"), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5),rs.getDate("ngaySinh"), rs.getString(7), rs.getString(8), rs.getString(9), new Account(rs));
    }
    public Staff(String maNhanVien){
        this.maNhanVien = maNhanVien;
    }

    public Staff(String maNhanVien, String tenNhanVien, String soDienThoai, String CCCD, boolean gioiTinh, Date ngaySinh, String diaChi, String chucVu, String trangThai, Account taiKhoan) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.soDienThoai = soDienThoai;
        this.CCCD = CCCD;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.chucVu = chucVu;
        this.trangThai = trangThai;
        this.taiKhoan = taiKhoan;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Account getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(Account taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "maNhanVien='" + maNhanVien + '\'' +
                ", tenNhanVien='" + tenNhanVien + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", CCCD='" + CCCD + '\'' +
                ", gioiTinh=" + gioiTinh +
                ", ngaySinh=" + ngaySinh +
                ", diaChi='" + diaChi + '\'' +
                ", chucVu='" + chucVu + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", taiKhoan='" + taiKhoan + '\'' +
                '}';
    }
}
