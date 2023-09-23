package DAOs;

import ConnectDB.ConnectDB;
import Entity.Bill;
import Entity.Service;
import Entity.DetailOfService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DetailOfServiceDAO {
    private static DetailOfServiceDAO instance = new DetailOfServiceDAO();

    public static DetailOfServiceDAO getInstance() {
        return instance;
    }
    public ArrayList<DetailOfService> getAllStaff(){
        ArrayList<DetailOfService> dsDOS = new ArrayList<DetailOfService>();
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
                int soLuong = rs.getInt(3);
                double donGia = rs.getDouble(4);

                DetailOfService dos = new DetailOfService(maHoaDon,maDichVu,soLuong,donGia);

                dsDOS.add(dos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsDOS;
    }
}
