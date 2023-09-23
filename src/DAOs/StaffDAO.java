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
                String CCCD = rs.getString(3);
                boolean gioiTinh = rs.getBoolean(4);
                Date ngaySinh = rs.getDate(5);
                String soDienThoai = rs.getString(6);
                String chucVu = rs.getString(7);
                String trangThai = rs.getString(8);
                String taiKhoan = rs.getString(9);

                Staff nhanVien = new Staff(maNhanVien,tenNhanVien,CCCD,gioiTinh,ngaySinh,soDienThoai,chucVu,trangThai,taiKhoan);

                dsStaff.add(nhanVien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsStaff;
    }
}
