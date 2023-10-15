package DAOs;

import ConnectDB.ConnectDB;
import Entity.Account;
import Entity.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

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

            String sql = "Select * from dbo.NhanVien";
            Statement statement = con.createStatement();
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet
            ResultSet rs = statement.executeQuery(sql);
            // Duyệt trên kết quả trả về
            while (rs.next()) {
                String maNhanVien = rs.getString(1);
                String tenNhanVien = rs.getString(2);
                String soDienThoai = rs.getString(3);
                String CCCD = rs.getString(4);
                boolean gioiTinh = rs.getBoolean(5);
                Date ngaySinh = rs.getDate(6);
                String diachi =rs.getString(7);
                String chucVu = rs.getString(8);
                String trangThai = rs.getString(9);
                String taiKhoan = rs.getString(10);


                Staff nhanVien = new Staff(maNhanVien,tenNhanVien,CCCD,soDienThoai,gioiTinh,ngaySinh,diachi,chucVu,trangThai,taiKhoan);

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

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Staff staff = new Staff();
                    staff.setMaNhanVien(resultSet.getString(1));
                    staff.setTenNhanVien(resultSet.getString(2));
                    staff.setSoDienThoai(resultSet.getString(3));
                    staff.setCCCD(resultSet.getString(4));
                    staff.setGioiTinh(resultSet.getBoolean(5));
                    staff.setNgaySinh(resultSet.getDate(6));
                    staff.setChucVu(resultSet.getString(7));
                    staff.setTrangThai(resultSet.getString(8));
                    staff.setTaiKhoan(resultSet.getString(9));
                    return staff;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
