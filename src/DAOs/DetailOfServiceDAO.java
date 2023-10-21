package DAOs;

import ConnectDB.ConnectDB;
import Entity.Bill;
import Entity.Service;
import Entity.DetailsOfService;
import Entity.ServiceForm;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DetailOfServiceDAO {
    private static DetailOfServiceDAO instance = new DetailOfServiceDAO();

    public static DetailOfServiceDAO getInstance() {
        return instance;
    }
    public ArrayList<DetailsOfService> getAllStaff(){
        ArrayList<DetailsOfService> dsDOS = new ArrayList<DetailsOfService>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "Select * from dbo.CTDichVu";
            Statement statement = con.createStatement();
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet
            ResultSet rs = statement.executeQuery(sql);
            // Duyệt trên kết quả trả về
            while (rs.next()) {
                Bill maHoaDon = new Bill(rs.getString(1));
                Service maDichVu = new Service(rs.getString(2));
                ServiceForm phieuDichVu = new ServiceForm(rs.getString(3));
                int soLuong = rs.getInt(4);
                double donGia = rs.getDouble(5);

                DetailsOfService dos = new DetailsOfService(maHoaDon,maDichVu,phieuDichVu,soLuong,donGia);

                dsDOS.add(dos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsDOS;
    }
    public ArrayList<DetailsOfService> getListCTDVByDate(Date tuNgay, Date denNgay){
        ArrayList<DetailsOfService> dataList = new ArrayList<DetailsOfService>();
        ConnectDB.getInstance();
        PreparedStatement statement = null;
        Connection con = ConnectDB.getConnection();
        try {
//            String sql = "SELECT * FROM dbo.HoaDon WHERE ngayGioTra >= ? AND ngayGioTra <= ?";
            String sql = "SELECT ct.* FROM ChiTietDichVu ct JOIN HoaDon hd ON ct.maHoaDon = hd.maHoaDon WHERE hd.ngayGioTra >= ? AND hd.ngayGioTra <= ?";
            statement = con.prepareStatement(sql);

            statement.setDate(1, (java.sql.Date) tuNgay);
            statement.setDate(2, (java.sql.Date) denNgay);

            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
//                DetailsOfService chiTietDichVu = new DetailsOfService(rs);
                Bill maHoaDon = new Bill(rs.getString(1));
                Service maDichVu = new Service(rs.getString(2));
//                ServiceForm phieuDichVu = new ServiceForm(rs.getString(3));
                int soLuong = rs.getInt(3);
                double donGia = rs.getDouble(4);

                DetailsOfService dos = new DetailsOfService(maHoaDon,maDichVu,soLuong,donGia);

                dataList.add(dos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
