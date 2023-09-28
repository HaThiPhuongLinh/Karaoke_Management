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
                        new Room(rs.getString(1), new TypeOfRoom(rs.getString(2)), rs.getString(3), rs.getString(4)));
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
}
