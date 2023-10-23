package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailsOfService {
    private Bill maHoaDon;
    private Service maDichVu;
//    private ServiceForm serviceForm;
    private int soLuong;
    private double giaBan;

    public DetailsOfService(Bill maHoaDon, Service maDichVu, int soLuong, double giaBan) {
        this.maHoaDon = maHoaDon;
        this.maDichVu = maDichVu;
//        this.serviceForm = serviceForm;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
    }
//    public DetailsOfService(Bill maHoaDon, Service maDichVu, ServiceForm phieuDichVu, int soLuong, double giaBan) {
//        this.maHoaDon = maHoaDon;
//        this.maDichVu = maDichVu;
//        this.serviceForm = serviceForm;
//        this.soLuong = soLuong;
//        this.giaBan = giaBan;
//    }

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

//    public ServiceForm getServiceForm() {
//        return serviceForm;
//    }
//
//    public void setServiceForm(ServiceForm serviceForm) {
//        this.serviceForm = serviceForm;
//    }

    public DetailsOfService(ResultSet rs) throws SQLException {
//        Account account = new Account();
//        Staff staff = new Staff(rs.getString("maNhanVien"));
//        Customer custom = new Customer(rs.getString("maKhachHang"));
//        TypeOfRoom typeOfRoom = new TypeOfRoom(rs.getString("maLoaiPhong"),rs.getString("tenLoaiPhong"),rs.getInt("sucChua"));
//        Room room = new Room(rs.getString("maPhong"));
        Bill bill = new Bill(rs.getString("maHoaDon"));
//        TypeOfService typeOfService = new TypeOfService(rs.getString("maLoaiDichVu"),rs.getString("tenoaiDichVu"));
        Service s = new Service(rs.getString("maDichVu"));
//        ServiceForm serviceForm = new ServiceForm(rs.getString("maPhieuDichVu"),rs.getTimestamp("thoiGianDat"),staff,custom);

        this.maHoaDon = bill;
        this.maDichVu = s;
        this.giaBan = rs.getDouble("giaBan");
        this.soLuong =rs.getInt("soLuong");
    }
}
