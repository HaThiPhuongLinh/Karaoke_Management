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
                        new TypeOfRoom(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDouble(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLoaiPhong;
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
