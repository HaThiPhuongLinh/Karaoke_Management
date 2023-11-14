package Entity;

import DAO.RoomDAO;
import DAO.TypeOfRoomDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Entity: Phiếu đặt phòng
 * Người thiết kế: Hà Thị Phương Linh
 */
public class Room {
    private String maPhong;
    private TypeOfRoom loaiPhong;
    private String tinhTrang;
    private String viTri;
    private double giaPhong;

    public Room(ResultSet rs) throws SQLException {
        this(rs.getString("maPhong"), new TypeOfRoom(rs.getString(2)), rs.getString("tinhTrang"), rs.getString("viTri"), rs.getDouble("giaPhong"));
    }
    public Room() {
        this.maPhong = "";
        this.loaiPhong = new TypeOfRoom();
        this.tinhTrang = "";
        this.viTri = "";
    }

    public Room(String maPhong) {
        RoomDAO roomDAO = new RoomDAO();
        Room r = roomDAO.getRoomByRoomId(maPhong);
        this.maPhong = r.getMaPhong();
        this.loaiPhong = r.getLoaiPhong();
        this.tinhTrang = r.getTinhTrang();
        this.viTri = r.getViTri();
        this.giaPhong = r.getGiaPhong();
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public TypeOfRoom getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(TypeOfRoom loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
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
                ", loaiPhong=" + loaiPhong +
                ", viTri='" + viTri + '\'' +
                ", tinhTrang='" + tinhTrang + '\'' +
                ", giaPhong=" + giaPhong +
                '}';
    }

    public Room(String maPhong, TypeOfRoom loaiPhong, String tinhTrang, String viTri, double giaPhong) {
        this.maPhong = maPhong;
        this.loaiPhong = loaiPhong;
        this.tinhTrang = tinhTrang;
        this.viTri = viTri;
        this.giaPhong = giaPhong;
    }

    public double getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(double giaPhong) {
        this.giaPhong = giaPhong;
    }
}
