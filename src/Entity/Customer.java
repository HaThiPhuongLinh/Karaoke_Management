package Entity;
import DAOs.CustomerDAO;
import DAOs.StaffDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public class Customer {
    private String maKhachHang, tenKhachHang;
    private String soDienThoai;
    private String CCCD;
    private boolean gioiTinh;
    private Date ngaySinh;

    public Customer(){

    }

    public Customer(String maKhachHang){
        CustomerDAO customerDAO = new CustomerDAO();
        Customer c = customerDAO.getKhachHangById(maKhachHang);
        this.maKhachHang = c.getMaKhachHang();
        this.tenKhachHang = c.getTenKhachHang();
        this.soDienThoai = c.getSoDienThoai();
        this.CCCD = c.getCCCD();
        this.gioiTinh = c.isGioiTinh();
        this.ngaySinh = c.getNgaySinh();
    }

    public Customer(ResultSet rs) throws SQLException {
        this(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5),rs.getDate(6));
    }

    public Customer(String maKhachHang, String tenKhachHang, String soDienThoai, String CCCD, boolean gioiTinh, Date ngaySinh) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.CCCD = CCCD;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public String getCCCD() {
        return CCCD;
    }

    public java.sql.Date getNgaySinh() {
        return (java.sql.Date) ngaySinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setMaKhachHang(String maKhachHang) {
        if(!maKhachHang.trim().equals("")) {
            this.maKhachHang = maKhachHang;
        }else {
            this.maKhachHang = "Un-known";
        }
    }

    public void setTenKhachHang(String tenKhachHang) {
        if(!tenKhachHang.trim().equals("")) {
            this.tenKhachHang = tenKhachHang;
        }else {
            this.tenKhachHang = "Un-known";
        }
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setCCCD(String CCCD) {
        if(!CCCD.trim().equals("")) {
            this.CCCD = CCCD;
        }else {
            this.CCCD = "Un-known";
        }
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(getMaKhachHang(), customer.getMaKhachHang());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaKhachHang());
    }


    @Override
    public String toString() {
        return "Customer{" +
                "maKhachHang='" + maKhachHang + '\'' +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", CCCD='" + CCCD + '\'' +
                ", gioiTinh=" + gioiTinh +
                ", ngaySinh=" + ngaySinh +
                '}';
    }
}
