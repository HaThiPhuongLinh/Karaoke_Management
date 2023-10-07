package Entity;

public class DetailsOfBill {
    private Bill maHoaDon;
    private Room maPhong;
    private int thoiGianSuDung;
    private int giaPhong;

    public DetailsOfBill(Bill maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(int giaPhong) {
        this.giaPhong = giaPhong;
    }

    public DetailsOfBill(Bill maHoaDon, Room maPhong, int thoiGianSuDung, int giaPhong) {
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

    public int getThoiGianSuDung() {
        return thoiGianSuDung;
    }

    public void setThoiGianSuDung(int thoiGianSuDung) {
        this.thoiGianSuDung = thoiGianSuDung;
    }
}
