package Entity;
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
        this.maKhachHang = maKhachHang;
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

    public Date getNgaySinh() {
        return ngaySinh;
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

    public Customer(String maKhachHang, String tenKhachHang, String soDienThoai, String CCCD, boolean gioiTinh, Date ngaySinh) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.CCCD = CCCD;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
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
