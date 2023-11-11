package DAO;

import ConnectDB.ConnectDB;
import Entity.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Entity.DetailsOfBill;
/**
 * Người tham gia thiết kế: Nguyễn Đình Dương
 * <p>
 * Ngày tạo: 01/11/2023
 * <p>
 * Lần cập nhật cuối: 06/11/2023
 * <p>
 * Nội dung cập nhật: cập nhật lịch sử code
 */
public class DetailOfBillDAO {
    /**
     * Thêm chi tiết hóa đơn
     * @param ro:Chi tiết hóa đơn cần thêm
     * @return {@code boolean} :Được hoặc không
     * @throws SQLException
     */
    public boolean insert(DetailsOfBill ro) throws SQLException {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            String sql = "insert into dbo.ChiTietHoaDon (maHoaDon, maPhong, thoiGianSuDung, giaPhong)" + "values (?,?,?,?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, ro.getMaHoaDon().getMaHoaDon());
            statement.setString(2, ro.getMaPhong().getMaPhong());
            statement.setString(3, ro.getThoiGianSuDung());

            statement.setDouble(4,ro.getGiaPhong());

            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                statement.close();
            } catch (SQLException e2) {
                // TODO: handle exception
                e2.printStackTrace();
            }
        }
        return n > 0;
    }
}
