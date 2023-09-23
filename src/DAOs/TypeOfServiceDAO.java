package DAOs;
import java.sql.SQLException;
import java.util.List;
import ConnectDB.ConnectDB;
import Entity.Room;
import Entity.TypeOfRoom;
import Entity.TypeOfService;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
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

}
