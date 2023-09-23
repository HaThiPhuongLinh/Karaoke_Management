package DAOs;

import ConnectDB.ConnectDB;
import Entity.Customer;
import Entity.ReservationForm;
import Entity.Room;
import Entity.Staff;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReservationFormDAO {
    public List<ReservationForm> getAllForm() {
        List<ReservationForm> listForm = new ArrayList<ReservationForm>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "Select * from PhieuDatPhong";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                listForm.add(
                        new ReservationForm(rs.getString(1), rs.getTimestamp(2), rs.getTimestamp(3), new Staff(rs.getString(4)), new Customer(rs.getString(5)),new Room(rs.getString(6))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listForm;
    }
}
