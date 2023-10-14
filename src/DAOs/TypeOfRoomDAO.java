package DAOs;

import java.sql.*;
import java.util.List;

import ConnectDB.ConnectDB;
import Entity.Room;
import Entity.TypeOfRoom;

import java.util.ArrayList;

public class TypeOfRoomDAO {
    public List<TypeOfRoom> getAllLoaiPhong() {
        List<TypeOfRoom> dsLoaiPhong = new ArrayList<TypeOfRoom>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "Select * from LoaiPhong";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsLoaiPhong.add(
                        new TypeOfRoom(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLoaiPhong;
    }


    public TypeOfRoom getRoomTypeByID(String roomID) {
        TypeOfRoom room = null;
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM LoaiPhong where maLoaiPhong = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, roomID);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                return null;
            room = new TypeOfRoom(rs);
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

    public String getRoomTypeNameByRoomID(String roomID) {
        String name = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT tenLoaiPhong FROM LoaiPhong WHERE maLoaiPhong = (SELECT maLoaiPhong FROM Phong WHERE maPhong = ?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, roomID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                name = rs.getString("tenLoaiPhong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
    }


}
