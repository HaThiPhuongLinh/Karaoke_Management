package Entity;

import java.util.Objects;

public class Service {
    private String maDichVu,tenDichVu;
    private TypeOfService maLoaiDichVu;
    private String donViTinh;
    private int soLuongTon;
    private double giaBan;

    public Service(){

    }

    public Service(String maDichVu){
        this.maDichVu=maDichVu;
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        if(!maDichVu.trim().equals("")) {
            this.maDichVu = maDichVu;
        }else {
            this.maDichVu = "Un-known";
        }
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        if(!tenDichVu.trim().equals("")) {
            this.tenDichVu = tenDichVu;
        }else {
            this.tenDichVu = "Un-known";
        }
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        if(giaBan<0) {
            this.giaBan=0;
        }
        else
            this.giaBan=giaBan;
    }


    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        if(soLuongTon<0) {
            this.soLuongTon=0;
        }
        else
            this.soLuongTon=soLuongTon;
    }


    public TypeOfService getMaLoaiDichVu() {
        return maLoaiDichVu;
    }

    public void setMaLoaiDichVu(TypeOfService maLoaiDichVu) {
        this.maLoaiDichVu = maLoaiDichVu;
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
