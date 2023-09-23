package Entity;
import java.util.Date;
import java.util.Objects;
//Test
public class Customer {
    private String maKH, hoTen;
    private boolean gioiTinh;
    private String cmnd;
    private Date ngaySinh;
    private String soDienThoai;

    public Customer(){

    }

    public Customer(String maKH){
        this.maKH=maKH;
    }

    public String getMaKH() {
        return maKH;
    }

    public String getHoTen() {
        return hoTen;
    }



    public String getCmnd() {
        return cmnd;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setMaKH(String maKH) {
        if(!maKH.trim().equals("")) {
            this.maKH = maKH;
        }else {
            this.maKH = "Un-known";
        }
    }

    public void setHoTen(String hoTen) {
        if(!hoTen.trim().equals("")) {
            this.hoTen = hoTen;
        }else {
            this.hoTen = "Un-known";
        }
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setCmnd(String cmnd) {
        if(!cmnd.trim().equals("")) {
            this.cmnd = cmnd;
        }else {
            this.cmnd = "Un-known";
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
        return Objects.equals(getMaKH(), customer.getMaKH());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaKH());
    }

    public Customer(String maKH, String hoTen, boolean gioiTinh, String cmnd, Date ngaySinh, String soDienThoai) {
        setMaKH(maKH);
        setHoTen(hoTen);
        setGioiTinh(gioiTinh);
        setCmnd(cmnd);
        setNgaySinh(ngaySinh);
        setSoDienThoai(soDienThoai);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "maKH='" + maKH + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", cmnd='" + cmnd + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", soDienThoai='" + soDienThoai + '\'' +
                '}';
    }
}
