package Entity;
import java.util.Date;
import java.util.Objects;

public class Room {
    private String maPhong;
    private TypeOfRoom maLoaiPhong;
    private int tinhTrang;
    private String viTri;

    public Room(){

    }

    public Room(String maPhong){
        this.maPhong=maPhong;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        if(maPhong.trim().equals("")) {
            this.maPhong = maPhong;
        }else {
            this.maPhong = "Un-known";
        }
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        if(viTri.trim().equals("")) {
            this.viTri = viTri;
        }else {
            this.viTri = "Un-known";
        }
    }

    public TypeOfRoom getMaLoaiPhong() {
        return maLoaiPhong;
    }

    public void setMaLoaiPhong(TypeOfRoom maLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return Objects.equals(getMaPhong(), room.getMaPhong());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaPhong());
    }

    @Override
    public String toString() {
        return "Room{" +
                "maPhong='" + maPhong + '\'' +
                ", tinhTrang=" + tinhTrang +
                ", viTri='" + viTri + '\'' +
                ", maLP=" + maLoaiPhong +
                '}';
    }

    public Room(String maPhong, TypeOfRoom maLoaiPhong, int tinhTrang, String viTri) {
        setMaPhong(maPhong);
        this.tinhTrang = tinhTrang;
        setViTri(viTri);
        this.maLoaiPhong = maLoaiPhong;
    }
}
