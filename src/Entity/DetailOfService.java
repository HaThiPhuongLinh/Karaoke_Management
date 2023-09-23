package Entity;

public class DetailOfService {
    private Bill maHoaDon;
    private Service maDichVu;
    private int soLuong;
    private double donGia;

    public DetailOfService(){

    }

    public DetailOfService(Bill maHoaDon, Service maDichVu, int soLuong, double donGia){
        this.maHoaDon = maHoaDon;
        this.maDichVu = maDichVu;
        this.soLuong = soLuong;
        this.donGia=donGia;
    }

    public Bill getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(Bill maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public Service getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(Service maDichVu) {
        this.maDichVu = maDichVu;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
