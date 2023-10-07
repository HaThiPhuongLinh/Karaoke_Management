package Entity;

public class DetailsOfService {
    private Bill maHoaDon;
    private Service maDichVu;
    private ServiceForm serviceForm;
    private int soLuong;
    private double giaBan;

    public DetailsOfService(){

    }

    public DetailsOfService(Bill maHoaDon, Service maDichVu, ServiceForm serviceForm, int soLuong, double giaBan) {
        this.maHoaDon = maHoaDon;
        this.maDichVu = maDichVu;
        this.serviceForm = serviceForm;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
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

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public ServiceForm getServiceForm() {
        return serviceForm;
    }

    public void setServiceForm(ServiceForm serviceForm) {
        this.serviceForm = serviceForm;
    }
}
