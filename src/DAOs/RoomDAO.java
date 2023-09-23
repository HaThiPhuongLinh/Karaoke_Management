package DAOs;

import Entity.Room;

import java.sql.SQLException;
import java.util.List;
import ConnectDB.ConnectDB;
import Entity.TypeOfRoom;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
public class RoomDAO {

    public List<Room> getAllPhong() {
        List<Room> dsPhong = new ArrayList<Room>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "Select * from Phong";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsPhong.add(
                        new Room(rs.getString(1), new TypeOfRoom(rs.getString(2)), rs.getInt(3),rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhong;
    }
}
