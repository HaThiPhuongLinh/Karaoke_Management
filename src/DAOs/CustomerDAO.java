package DAOs;
import Entity.Customer;


import java.sql.SQLException;
import java.util.List;
import ConnectDB.ConnectDB;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
public class CustomerDAO {
    public List<Customer> getAllKhachHang() {
        List<Customer> dsKhachHang = new ArrayList<Customer>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "Select * from KhachHang";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsKhachHang.add(
                        new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5),rs.getDate(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }
}
