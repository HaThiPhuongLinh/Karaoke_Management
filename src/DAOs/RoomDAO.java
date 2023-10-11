package DAOs;

import Entity.Room;

import java.sql.*;
import java.util.List;

import ConnectDB.ConnectDB;
import Entity.TypeOfRoom;

import java.util.ArrayList;

public class RoomDAO {

    public ArrayList<Room> getAllPhong() {
        ArrayList<Room> dsPhong = new ArrayList<Room>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        Statement statement = null;
        try {
            String sql = "Select * from Phong";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsPhong.add(
                        new Room(rs.getString(1), new TypeOfRoom(rs.getString(2)), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dsPhong;
    }

    /**
     * Lầy danh sách phòng dựa trên ID phòng
     *
     * @param roomID: ID phòng
     * @return {@code Room}: phòng
     */
    public Room getRoomByID(String roomID) {
        Room room = null;
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM Phong where maPhong = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, roomID);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                return null;
            room = new Room(rs);
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return room;
    }

    /**
     * Lầy trạng thái phòng dựa trên ID phòng
     *
     * @param roomID: ID phòng
     * @return {@code String}: trạng thái phòng
     */
    public String getSatusRoomByID(String roomID) {
        String status = "";
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT tinhTrang FROM Phong where maPhong = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, roomID);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                return null;
            status = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status;

    }

    public ArrayList<Room> getRoomsByStatus(String status) {
        ArrayList<Room> rooms = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT * FROM Phong WHERE tinhTrang = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, status);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rooms.add(new Room(rs.getString(1), new TypeOfRoom(rs.getString(2)), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rooms;
    }

    public ArrayList<Room> getRoomsByType(String roomType) {
        ArrayList<Room> rooms = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT p.maPhong, p.tinhTrang, p.maLoaiPhong, p.viTri, p.giaPhong, lp.maLoaiPhong, lp.sucChua, lp.tenLoaiPhong FROM dbo.Phong p INNER JOIN dbo.LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong WHERE lp.tenLoaiPhong = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, roomType);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rooms.add(new Room(rs.getString(1), new TypeOfRoom(rs.getString(2)), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rooms;
    }


}
