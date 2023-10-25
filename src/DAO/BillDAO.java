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
    public boolean addBill(Bill bill) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "INSERT INTO HoaDon (maHoaDon, maNhanVien, maKhachHang, maPhong, ngayGioDat, ngayGioTra, tinhTrangHD, khuyenMai) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            statement = con.prepareStatement(sql);
            statement.setString(1, bill.getMaHoaDon());
            statement.setString(2, bill.getMaNhanVien().getMaNhanVien());
            statement.setString(3, bill.getMaKH().getMaKhachHang());
            statement.setString(4, bill.getMaPhong().getMaPhong());
            statement.setTimestamp(5, bill.getNgayGioDat());
            statement.setTimestamp(6, bill.getNgayGioTra());
            statement.setInt(7, bill.getTinhTrangHD());
            statement.setString(8, bill.getKhuyenMai());

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Bill getBillByRoomID(String roomID) {
        Bill bill = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getInstance().getConnection();
            String query = "SELECT * FROM HoaDon WHERE maPhong = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, roomID);

            rs = stmt.executeQuery();

            if (rs.next()) {
                String maHoaDon = rs.getString("maHoaDon");
                Staff maNhanVien = new Staff(rs.getString("maNhanVien"));
                Customer maKhachHang = new Customer(rs.getString("maKhachHang"));
                Timestamp ngayGioDat = rs.getTimestamp("ngayGioDat");
                Timestamp ngayGioTra = rs.getTimestamp("ngayGioTra");
                int tinhTrang = rs.getInt("tinhTrangHD");
                String khuyenMai = rs.getString("khuyenMai");

                bill = new Bill(maHoaDon, maNhanVien, maKhachHang, new Room(roomID), ngayGioDat, ngayGioTra, tinhTrang, khuyenMai);
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

        return bill;
    }



    public String generateNextBillId() {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String nextId = null;
        try {
            String sql = "SELECT TOP 1 maHoaDon FROM HoaDon ORDER BY maHoaDon DESC";
            statement = con.prepareStatement(sql);
            rs = statement.executeQuery();

            if (rs.next()) {
                String lastServiceId = rs.getString("maHoaDon");
                String numericPart = lastServiceId.substring(4); // Lấy phần số từ mã dịch vụ cuối cùng
                int counter = Integer.parseInt(numericPart) + 1; // Tăng giá trị số lên 1
                nextId = "HD" + String.format("%07d", counter); // Định dạng lại giá trị số thành chuỗi 3 chữ số, sau đó ghép với tiền tố "HD"
            } else {
                nextId = "HD0000001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextId;
    }

}
