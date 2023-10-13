package DAOs;

import ConnectDB.ConnectDB;
import Entity.Staff;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
