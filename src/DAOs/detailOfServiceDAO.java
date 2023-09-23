package DAOs;

import ConnectDB.ConnectDB;
import Entity.Bill;
import Entity.Service;
import Entity.detailOfService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class detailOfServiceDAO {
    private static detailOfServiceDAO instance = new detailOfServiceDAO();

    public static detailOfServiceDAO getInstance() {
        return instance;
    }
    public ArrayList<detailOfService> getAllStaff(){
        ArrayList<detailOfService> dsDOS = new ArrayList<detailOfService>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "Select * from dbo.CTDichVu";
            Statement statement = con.createStatement();
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet
            ResultSet rs = statement.executeQuery(sql);
            // Duyệt trên kết quả trả về
            while (rs.next()) {
                Bill maHD = new Bill(rs.getString(1));
                Service maDV = new Service(rs.getString(2));
                int soLuongDat = rs.getInt(3);
                double donGia = rs.getDouble(4);

                detailOfService dos = new detailOfService(maHD,maDV,soLuongDat,donGia);

                dsDOS.add(dos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsDOS;
    }
}
