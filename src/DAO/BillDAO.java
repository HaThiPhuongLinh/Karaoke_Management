package DAO;

import ConnectDB.ConnectDB;
import Entity.*;

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

    public ArrayList<Bill> getListBillByDate(Date tuNgay, Date denNgay){
        ArrayList<Bill> dsStaff = new ArrayList<Bill>();
        ConnectDB.getInstance();
        PreparedStatement statement = null;
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "SELECT * FROM dbo.HoaDon WHERE ngayGioTra >= ? AND ngayGioTra <= ?";
//            String sql = "SELECT ct.* FROM ChiTietDichVu ct JOIN HoaDon hd ON ct.maHoaDon = hd.maHoaDon WHERE hd.ngayGioTra >= ? AND hd.ngayGioTra <= ?";
            statement = con.prepareStatement(sql);

            statement.setDate(1, (java.sql.Date) tuNgay);
            statement.setDate(2, (java.sql.Date) denNgay);

            ResultSet rs = statement.executeQuery();
            while(rs.next()) {

                String maHoaDon = rs.getString(1);
                Staff maNhanVien = new Staff(rs.getString(2));
                Customer maKhachHang = new Customer(rs.getString(3));
                Room maPhong = new Room(rs.getString(4));
//                double giaPhong = rs.getDouble(5);

                Timestamp ngayGioDat = rs.getTimestamp(5);
                Timestamp ngayGioTra = rs.getTimestamp(6);
                int tinhTrang = rs.getInt(7);
                String khuyenMai = rs.getString(8);

                Bill bill = new Bill(maHoaDon,maNhanVien,maKhachHang,maPhong,ngayGioDat,ngayGioTra,tinhTrang,khuyenMai);

                dsStaff.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsStaff;
    }
}
