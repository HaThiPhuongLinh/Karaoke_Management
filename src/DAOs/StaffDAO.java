package DAOs;

import ConnectDB.ConnectDB;
import Entity.Account;
import Entity.Room;
import Entity.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    private static StaffDAO instance = new StaffDAO();

    public static StaffDAO getInstance() {
        return instance;
    }

    public ArrayList<Staff> getAllStaff(){
        ArrayList<Staff> dsStaff = new ArrayList<Staff>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT nv.maNhanVien, nv.tenNhanVien, nv.soDienThoai, nv.CCCD, nv.gioiTinh, nv.ngaySinh, " +
                    "nv.diaChi, nv.chucVu, nv.trangThai, " +
                    "nv.taiKhoan, " +
                    "tk.taiKhoan, tk.matKhau, tk.tinhTrang " +
                    "FROM dbo.TaiKhoan tk, dbo.NhanVien nv " +
                    "WHERE tk.taiKhoan = nv.taiKhoan ";

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Staff nhanVien = new Staff(rs);
                dsStaff.add(nhanVien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsStaff;
    }

    public Staff getStaffByUsername(String username) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String query = "SELECT TOP 1 nv.maNhanVien, nv.tenNhanVien, nv.soDienThoai, nv.CCCD, nv.gioiTinh,  nv.ngaySinh, " +
                "nv.diaChi, nv.chucVu, nv.trangThai, " +
                "nv.taiKhoan, " +
                "tk.taiKhoan, tk.matKhau, tk.tinhTrang " +
                "FROM dbo.TaiKhoan tk, dbo.NhanVien nv " +
                "WHERE tk.taiKhoan = nv.taiKhoan " +
                "AND tk.taiKhoan = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Staff staff = new Staff(rs);
                    return staff;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Staff> getListNhanVienByName(String name) {
        ArrayList<Staff> staffList = new ArrayList<Staff>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String query =  "SELECT * FROM NhanVien where tenNhanVien like ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + name + "%");

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Staff staff = new Staff(rs);
                    staffList.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }
    public List<Staff> getListNhanVienBySDT(String sdt) {
        List<Staff> sdtlistt = new ArrayList<Staff>();
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien where soDienThoai like ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + sdt + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Staff c = new Staff(rs);
                sdtlistt.add(c);
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
        return sdtlistt;
    }
    public List<Staff> getListNhanVienByCCCD(String cccd) {
        List<Staff> cccdlistt = new ArrayList<Staff>();
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien where CCCD like ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + cccd + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Staff c = new Staff(rs);
                cccdlistt.add(c);
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
        return cccdlistt;
    }
}
