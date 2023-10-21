package DAO;

import ConnectDB.ConnectDB;
import Entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public static boolean update(Customer kh) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            String sql = "update dbo.KhachHang" + " set tenKhachHang = ?, soDienThoai=?, CCCD = ?, gioiTinh = ?,ngaySinh=?"
                    + " where maKhachHang = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, kh.getTenKhachHang());
            statement.setString(2, kh.getSoDienThoai());
            statement.setString(3, kh.getCCCD());
            statement.setBoolean(4, kh.isGioiTinh());
            statement.setDate(5, kh.getNgaySinh());
            statement.setString(6, kh.getMaKhachHang());
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

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
                        new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getDate(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }

    public String getIdByTenKhachHang(String tenKhachHang) {
        String id = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT maKhachHang FROM KhachHang WHERE tenKhachHang = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, tenKhachHang);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return id;
    }


    public Customer getKhachHangById(String id) {
        Customer customer = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = new Customer(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return customer;
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

    public List<Customer> getListKhachHangBySDT(String sdt) {
        List<Customer> sdtlist = new ArrayList<Customer>();
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang where soDienThoai like ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + sdt + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer c = new Customer(rs);
                sdtlist.add(c);
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
        return sdtlist;
    }

    public List<Customer> getListKhachHangByCCCD(String cccd) {
        List<Customer> cccdlist = new ArrayList<Customer>();
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang where CCCD like ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + cccd + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer c = new Customer(rs);
                cccdlist.add(c);
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
        return cccdlist;
    }

    public boolean insert(Customer kh) throws SQLException {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            String sql = "insert into dbo.KhachHang (maKhachHang, tenKhachHang, soDienThoai, CCCD, gioiTinh,ngaySinh)" + "values (?,?,?,?,?,?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, kh.getMaKhachHang());
            statement.setString(2, kh.getTenKhachHang());
            statement.setString(3, kh.getSoDienThoai());
            statement.setString(4, kh.getCCCD());
            statement.setBoolean(5, kh.isGioiTinh());
            statement.setDate(6, kh.getNgaySinh());
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e2) {
                // TODO: handle exception
                e2.printStackTrace();
            }
        }
        return n > 0;
    }

    public boolean delete(Customer kh) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            String sql = "delete from dbo.KhachHang" + " where maKhachHang = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, kh.getMaKhachHang());
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;


    }

}
