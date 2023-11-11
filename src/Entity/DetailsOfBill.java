package Entity;

public class DetailsOfBill {
    private Bill maHoaDon;
    private Room maPhong;
    private String thoiGianSuDung;
    private double giaPhong;

    public DetailsOfBill(Bill maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public double getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(double giaPhong) {
        this.giaPhong = giaPhong;
    }

    public DetailsOfBill(Bill maHoaDon, Room maPhong, String thoiGianSuDung, double giaPhong) {
        this.maHoaDon = maHoaDon;
        this.maPhong = maPhong;
        this.thoiGianSuDung = thoiGianSuDung;
        this.giaPhong = giaPhong;
    }

    public Bill getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(Bill maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public Room getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(Room maPhong) {
        this.maPhong = maPhong;
    }

    public String getThoiGianSuDung() {
        return thoiGianSuDung;
    }

    public void setThoiGianSuDung(String thoiGianSuDung) {
        this.thoiGianSuDung = thoiGianSuDung;
    }
}
