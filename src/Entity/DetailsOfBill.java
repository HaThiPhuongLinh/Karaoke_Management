package Entity;

public class DetailsOfBill {
    private Bill maHoaDon;
    private Room maPhong;
    private int thoiGianSuDung;

    public DetailsOfBill(Bill maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public DetailsOfBill(Bill maHoaDon, Room maPhong, int thoiGianSuDung) {
        this.maHoaDon = maHoaDon;
        this.maPhong = maPhong;
        this.thoiGianSuDung = thoiGianSuDung;
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
