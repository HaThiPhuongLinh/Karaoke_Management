package Entity;

import DAOs.TypeOfRoomDAO;
import DAOs.TypeOfServiceDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class TypeOfService {
    private String maLoaiDichVu, tenLoaiDichVu;

    public TypeOfService(ResultSet rs) throws SQLException {
        this(rs.getString(1), rs.getString(2));
    }

    public TypeOfService(String maLoaiDichVu) {
        TypeOfServiceDAO typeOfServiceDAO = new TypeOfServiceDAO();
        TypeOfService tr = typeOfServiceDAO.getServiceTypeByID(maLoaiDichVu);
        this.maLoaiDichVu=tr.getMaLoaiDichVu();
        this.tenLoaiDichVu=tr.getTenLoaiDichVu();
    }


    public String getMaLoaiDichVu() {
        return maLoaiDichVu;
    }

    public void setMaLoaiDichVu(String maLoaiDichVu) {
        this.maLoaiDichVu = maLoaiDichVu;
    }

    public String getTenLoaiDichVu() {
        return tenLoaiDichVu;
    }

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

    public TypeOfService(String maLoaiDichVu, String tenLoaiDichVu) {
        setMaLoaiDichVu(maLoaiDichVu);
        setTenLoaiDichVu(tenLoaiDichVu);
    }

}
