package DAOs;

import ConnectDB.ConnectDB;
import Entity.Bill;
import Entity.Customer;
import Entity.Room;
import Entity.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class BillDAO {
    private static BillDAO instance = new BillDAO();

    public static BillDAO getInstance() {
        return instance;
    }
    public ArrayList<Bill> getAllStaff(){
        ArrayList<Bill> dsStaff = new ArrayList<Bill>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "Select * from dbo.HoaDon";
            Statement statement = con.createStatement();
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet
            ResultSet rs = statement.executeQuery(sql);
            // Duyệt trên kết quả trả về
            while (rs.next()) {
                String maHoaDon = rs.getString(1);
                Staff maNhanVien = new Staff(rs.getString(2));
                Customer maKhachHang = new Customer(rs.getString(3));
                Room maPhong = new Room(rs.getString(4));
                double giaPhong = rs.getDouble(5);
                Timestamp ngayGioDat = rs.getTimestamp(6);
                Timestamp ngayGioTra = rs.getTimestamp(7);
                int tinhTrang = rs.getInt(8);
                String khuyenMai = rs.getString(9);

                Bill bill = new Bill(maHoaDon,maNhanVien,maKhachHang,maPhong,giaPhong,ngayGioDat,ngayGioTra,tinhTrang,khuyenMai);

                dsStaff.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsStaff;
    }
}
