package DAOs;
import java.sql.*;
import java.util.List;
import ConnectDB.ConnectDB;
import Entity.Room;
import Entity.TypeOfRoom;
import Entity.TypeOfService;
import Entity.Service;
import java.util.ArrayList;

public class ServiceDAO {
    public List<Service> getAllDichVu() {
        List<Service> dsDichVu = new ArrayList<Service>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "Select * from DichVu";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsDichVu.add(
                        new Service(rs.getString(1), rs.getString(2), new TypeOfService(rs.getString(3)), rs.getString(4),rs.getInt(5), rs.getDouble(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsDichVu;
    }

    public ArrayList<Service> getServiceByName(String name) {
        ArrayList<Service> lst = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT * FROM DichVu where tenDichVu like ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lst.add(
                        new Service(rs.getString(1), rs.getString(2), new TypeOfService(rs.getString(3)), rs.getString(4),rs.getInt(5), rs.getDouble(6)));
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

        return lst;
    }
}
