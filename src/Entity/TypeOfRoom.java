package Entity;

import java.util.Objects;

public class TypeOfRoom {
    private String maLoaiPhong;
    private String tenLoaiPhong;
    private int sucChua;
    private double giaTien;

    public String getMaLoaiPhong() {
        return maLoaiPhong;
    }

    public void setMaLoaiPhong(String maLoaiPhong) {
        if(!maLoaiPhong.trim().equals("")) {
            this.maLoaiPhong = maLoaiPhong;
        }else {
            this.maLoaiPhong = "Un-known";
        }
    }

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public void setTenLoaiPhong(String tenLoaiPhong) {
        if(tenLoaiPhong.trim().equals("")) {
            this.tenLoaiPhong = tenLoaiPhong;
        }else {
            this.tenLoaiPhong = "Un-known";
        }
    }

    public int getSucChua() {
        return sucChua;
    }

    public void setSucChua(int sucChua) {
        if(sucChua<=0) {
            this.sucChua=1;
        }
        else
            this.sucChua=sucChua;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        if(giaTien<0) {
            this.giaTien=0;
        }
        else
            this.giaTien=giaTien;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeOfRoom that)) return false;
        return Objects.equals(getMaLoaiPhong(), that.getMaLoaiPhong());
    }

    @Override
    public String toString() {
        return "TypeOfRoom{" +
                "maLP='" + maLoaiPhong + '\'' +
                ", tenLP='" + tenLoaiPhong + '\'' +
                ", sucChua=" + sucChua +
                ", giaTien=" + giaTien +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaLoaiPhong());
    }

    public TypeOfRoom(String maLoaiPhong, String tenLoaiPhong, int sucChua, double giaTien) {
        setMaLoaiPhong(maLoaiPhong);
        setTenLoaiPhong(tenLoaiPhong);
        setSucChua(sucChua);
        setGiaTien(giaTien);
    }

    public TypeOfRoom(String maLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
    }
}
