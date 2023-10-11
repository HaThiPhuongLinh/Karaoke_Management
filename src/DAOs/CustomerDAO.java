package DAOs;
import Entity.Customer;


import java.sql.*;
import java.util.List;
import ConnectDB.ConnectDB;
import java.util.ArrayList;

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

    public List<Customer> getListKhachHangByName(String name) {
        List<Customer> namelist = new ArrayList<Customer>();
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang where tenKhachHang like ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer c = new Customer(rs);
                namelist.add(c);
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
        return namelist;
    }

}
