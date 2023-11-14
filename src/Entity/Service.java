package Entity;

import DAO.RoomDAO;
import DAO.ServiceDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Entity: Dịch vụ
 * Người thiết kế: Nguyễn Quang Duy
 */

public class Service {
    private String maDichVu,tenDichVu;
    private TypeOfService maLoaiDichVu;
    private String donViTinh;
    private int soLuongTon;
    private double giaBan;
    /**
     * Tạo 1 {@code Service} không tham số
     */
    public Service(){

    }

    /**
     * Tạo 1 {@code Service} từ kết quả truy vấn nhận được từ cơ sở dữ liệu
     * @param rs kết quả truy vấn
     * @throws SQLException nếu có lỗi trong quá trình lấy dữ liệu
     */
    public Service(ResultSet rs) throws SQLException {
        this(rs.getString(1), rs.getString(2), new TypeOfService(rs.getString(3)), rs.getString(4), rs.getInt(5),rs.getDouble(6));
    }

    /**
     * Tạo 1 {@code LoaiDichVu} với tham số
     * @param maDV mã dịch vụ
     */
    public Service(String maDV) {
        ServiceDAO serviceDAO = new ServiceDAO();
        Service s = serviceDAO.getDichVuByMaDichVu(maDV);
        this.maDichVu = maDV;
        this.tenDichVu = s.getTenDichVu();
        this.maLoaiDichVu = s.getMaLoaiDichVu();
        this.donViTinh = s.getDonViTinh();
        this.soLuongTon = s.getSoLuongTon();
        this.giaBan = s.getGiaBan();
    }

    /**
     * Lấy mã dịch vụ
     * @return mã dịch vụ được tìm thấy
     */
    public String getMaDichVu() {
        return maDichVu;
    }

    /**
     * cập nhật mã dịch vụ
     * @param maDichVu mã dịch vụ
     */
    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    /**
     * Lấy tên dịch vụ
     * @return tên dịch vụ được tìm thấy
     */
    public String getTenDichVu() {
        return tenDichVu;
    }

    /**
     * Cập nhật tên dịch vụ
     * @param tenDichVu tên dịch vụ
     */
    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }
    /**
     * Lấy mã loại dịch vụ
     * @return mã loại dịch vụ được tìm thấy
     */
    public TypeOfService getMaLoaiDichVu() {
        return maLoaiDichVu;
    }
    /**
     * Cập nhật mã loại dịch vụ
     * @param maLoaiDichVu mã loại dịch vụ
     */
    public void setMaLoaiDichVu(TypeOfService maLoaiDichVu) {
        this.maLoaiDichVu = maLoaiDichVu;
    }
    /**
     * Lấy đơn vị tính
     * @return đơn vị tính được tìm thấy
     */
    public String getDonViTinh() {
        return donViTinh;
    }
    /**
     * Cập nhật đơn vị tính
     * @param donViTinh đơn vị tính
     */
    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }
    /**
     * Lấy số lượng tồn
     * @return số lượng tồn được tìm thấy
     */
    public int getSoLuongTon() {
        return soLuongTon;
    }
    /**
     * Cập nhật số lượng tồn
     * @param soLuongTon số lượng tồn
     */
    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }
    /**
     * Lấy giá bán
     * @return giá bán được tìm thấy
     */
    public double getGiaBan() {
        return giaBan;
    }
    /**
     * Cập nhật giá bán
     * @param giaBan giá bán
     */
    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service service)) return false;
        return Objects.equals(getMaDichVu(), service.getMaDichVu());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaDichVu());
    }

    /**
     * Tạo 1 {@code LoaiDichVu} với các tham số sau:
     * @param maDichVu mã dịch vụ
     * @param tenDichVu tên dịch vụ
     * @param maLoaiDichVu mã loại dịch vụ
     * @param donViTinh đơn vị tính
     * @param soLuongTon số lượng tồn
     * @param giaBan giá bán
     */
    public Service(String maDichVu, String tenDichVu, TypeOfService maLoaiDichVu, String donViTinh, int soLuongTon, double giaBan) {
        this.maDichVu = maDichVu;
        this.tenDichVu = tenDichVu;
        this.maLoaiDichVu = maLoaiDichVu;
        this.donViTinh = donViTinh;
        this.soLuongTon = soLuongTon;
        this.giaBan = giaBan;
    }

    @Override
    public String toString() {
        return "Service{" +
                "maDichVu='" + maDichVu + '\'' +
                ", tenDichVu='" + tenDichVu + '\'' +
                ", maLDV=" + maLoaiDichVu +
                ", donViTinh='" + donViTinh + '\'' +
                ", soLuongTon=" + soLuongTon +
                ", giaBan=" + giaBan +
                '}';
    }
}
