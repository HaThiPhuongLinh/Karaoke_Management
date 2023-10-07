package Entity;

import java.sql.Timestamp;

public class ServiceForm {
    private String maPhieuDichVu;
    private Timestamp thoiGianDat;
    private Staff staff;
    private Customer customer;

    public ServiceForm(String maPhieuDichVu, Timestamp thoiGianDat, Staff staff, Customer customer) {
        this.maPhieuDichVu = maPhieuDichVu;
        this.thoiGianDat = thoiGianDat;
        this.staff = staff;
        this.customer = customer;
    }

    public ServiceForm(String string) {
    }

    public String getMaPhieuDichVu() {
        return maPhieuDichVu;
    }

    public void setMaPhieuDichVu(String maPhieuDichVu) {
        this.maPhieuDichVu = maPhieuDichVu;
    }

    public Timestamp getThoiGianDat() {
        return thoiGianDat;
    }

    public void setThoiGianDat(Timestamp thoiGianDat) {
        this.thoiGianDat = thoiGianDat;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
