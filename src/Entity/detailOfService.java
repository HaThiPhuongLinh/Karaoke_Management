package Entity;

public class detailOfService {
    private Bill maHoaDon;
    private Service maDichVu;
    private int soLuongDat;
    private double donGia;

    public detailOfService(){

    }

    public detailOfService(Bill maHoaDon, Service maDichVu, int soLuongDat, double donGia){
        this.maHoaDon = maHoaDon;
        this.maDichVu = maDichVu;
        this.soLuongDat=soLuongDat;
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

    public int getSoLuongDat() {
        return soLuongDat;
    }

    public void setSoLuongDat(int soLuongDat) {
        this.soLuongDat = soLuongDat;
    }
}
