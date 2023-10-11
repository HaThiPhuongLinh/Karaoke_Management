package DAOs;
import java.sql.*;
import java.util.List;
import ConnectDB.ConnectDB;
import Entity.Room;
import Entity.TypeOfRoom;
import Entity.TypeOfService;

import java.util.ArrayList;

public class TypeOfServiceDAO {
    public List<TypeOfService> getAllLoaiDichVu() {
        List<TypeOfService> dsLoaiDichVu = new ArrayList<TypeOfService>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "Select * from LoaiDichVu";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsLoaiDichVu.add(
                        new TypeOfService(rs.getString(1), rs.getString(2)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLoaiDichVu;
    }

    public TypeOfService getServiceTypeByID(String typeID) {
        TypeOfService tr = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM LoaiDichVu WHERE maLoaiDichVu = ?";

            stmt = con.prepareStatement(sql);
            stmt.setString(1, typeID);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next())
                return null;
            tr = new TypeOfService(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tr;
    }

}
