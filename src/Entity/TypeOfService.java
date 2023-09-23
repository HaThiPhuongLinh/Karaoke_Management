package Entity;

import java.util.Objects;

public class TypeOfService {
    private String maLoaiDichVu, tenLoaiDichVu;

    public String getMaLoaiDichVu() {
        return maLoaiDichVu;
    }

    public String getTenLoaiDichVu() {
        return tenLoaiDichVu;
    }

    public void setMaLoaiDichVu(String maLoaiDichVu) {
        if(!maLoaiDichVu.trim().equals("")) {
            this.maLoaiDichVu = maLoaiDichVu;
        }else {
            this.maLoaiDichVu = "Un-known";
        }
    }

    public void setTenLoaiDichVu(String tenLoaiDichVu) {
        if(!tenLoaiDichVu.trim().equals("")) {
            this.tenLoaiDichVu = tenLoaiDichVu;
        }else {
            this.tenLoaiDichVu = "Un-known";
        }
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
                "maLDV='" + maLoaiDichVu + '\'' +
                ", tenLDV='" + tenLoaiDichVu + '\'' +
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

    public TypeOfService(String maLoaiDichVu) {
        this.maLoaiDichVu = maLoaiDichVu;
    }
}
