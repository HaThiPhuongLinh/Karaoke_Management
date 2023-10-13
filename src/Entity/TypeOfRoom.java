package Entity;

import DAOs.RoomDAO;
import DAOs.TypeOfRoomDAO;
import DAOs.TypeOfServiceDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class TypeOfRoom {
    private String maLoaiPhong;
    private String tenLoaiPhong;
    private int sucChua;

    public TypeOfRoom(ResultSet rs) throws SQLException {
        this(rs.getString(1), rs.getString(2), rs.getInt(3));
    }

    public TypeOfRoom(String maLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
    }


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
        if(!tenLoaiPhong.trim().equals("")) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeOfRoom that)) return false;
        return Objects.equals(getMaLoaiPhong(), that.getMaLoaiPhong());
    }

    @Override
    public String toString() {
        return "TypeOfRoom{" +
                "maLoaiPhong='" + maLoaiPhong + '\'' +
                ", tenLoaiPhong='" + tenLoaiPhong + '\'' +
                ", sucChua=" + sucChua +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaLoaiPhong());
    }

    public TypeOfRoom(String maLoaiPhong, String tenLoaiPhong, int sucChua) {
        setMaLoaiPhong(maLoaiPhong);
        setTenLoaiPhong(tenLoaiPhong);
        setSucChua(sucChua);

    }



}
