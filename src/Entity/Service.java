package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Service {
    private String maDichVu,tenDichVu;
    private TypeOfService maLoaiDichVu;
    private String donViTinh;
    private int soLuongTon;
    private double giaBan;

    public Service(){

    }

    public Service(ResultSet rs) throws SQLException {
        this(rs.getString("maDichVu"), rs.getString("tenDichVu"), new TypeOfService(rs.getString(3)), rs.getString(4), rs.getInt(5),rs.getDouble(6));
    }

    public Service(String maDichVu){
        this.maDichVu=maDichVu;
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public TypeOfService getMaLoaiDichVu() {
        return maLoaiDichVu;
    }

    public void setMaLoaiDichVu(TypeOfService maLoaiDichVu) {
        this.maLoaiDichVu = maLoaiDichVu;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public double getGiaBan() {
        return giaBan;
    }

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
