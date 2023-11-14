package Entity;

import DAO.TypeOfServiceDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Entity: Loại dịch vụ
 * Người thiết kế: Nguyễn Quang Duy
 */

public class TypeOfService {
    private String maLoaiDichVu, tenLoaiDichVu;
    /**
     * Tạo 1 {@code TypeOfService} từ kết quả truy vấn nhận được từ cơ sở dữ liệu
     * @param rs kết quả truy vấn
     * @throws SQLException nếu có lỗi trong quá trình lấy dữ liệu
     */
    public TypeOfService(ResultSet rs) throws SQLException {
        this(rs.getString(1), rs.getString(2));
    }

    /**
     * Tạo 1 {@code TypeOfService} với các tham số sau
     * @param maLoaiDichVu mã loại dịch vụ
     */
    public TypeOfService(String maLoaiDichVu) {
        TypeOfServiceDAO typeOfServiceDAO = new TypeOfServiceDAO();
        TypeOfService tr = typeOfServiceDAO.getServiceTypeByID(maLoaiDichVu);
        this.maLoaiDichVu=tr.getMaLoaiDichVu();
        this.tenLoaiDichVu=tr.getTenLoaiDichVu();
    }

    /**
     * Lấy mã loại dịch vụ
     * @return mã loại dịch vụ được tìm thấy
     */
    public String getMaLoaiDichVu() {
        return maLoaiDichVu;
    }
    /**
     * cập nhật mã loại dịch vụ
     * @param maLoaiDichVu mã loại dịch vụ
     */
    public void setMaLoaiDichVu(String maLoaiDichVu) {
        this.maLoaiDichVu = maLoaiDichVu;
    }
    /**
     * Lấy tên loại dịch vụ
     * @return tên loại dịch vụ được tìm thấy
     */
    public String getTenLoaiDichVu() {
        return tenLoaiDichVu;
    }
    /**
     * cập nhật tên loại dịch vụ
     * @param tenLoaiDichVu mã dịch vụ
     */
    public void setTenLoaiDichVu(String tenLoaiDichVu) {
        this.tenLoaiDichVu = tenLoaiDichVu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeOfService that)) return false;
        return Objects.equals(getMaLoaiDichVu(), that.getMaLoaiDichVu());
    }

    @Override
    public String toString() {
        return "TypeOfService{" +
                "maLoaiDichVu='" + maLoaiDichVu + '\'' +
                ", tenLoaiDichVu='" + tenLoaiDichVu + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaLoaiDichVu());
    }

    /**
     * Tạo 1 {@code TypeOfService} với các tham số sau
     * @param maLoaiDichVu mã loại dịch vụ
     * @param tenLoaiDichVu tên loại dịch vụ
     */
    public TypeOfService(String maLoaiDichVu, String tenLoaiDichVu) {
        setMaLoaiDichVu(maLoaiDichVu);
        setTenLoaiDichVu(tenLoaiDichVu);
    }

}
