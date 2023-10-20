package DAOs;

import ConnectDB.ConnectDB;
import Entity.Bill;
import Entity.Service;
import Entity.DetailsOfService;

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
                int soLuong = rs.getInt(4);
                double donGia = rs.getDouble(5);

                DetailsOfService dos = new DetailsOfService(maHoaDon,maDichVu,soLuong,donGia);

                dsDOS.add(dos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsDOS;
    }
}
