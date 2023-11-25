package DAO;

import ConnectDB.ConnectDB;
import Entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Người tham gia thiết kế: Hà Thị Phương Linh
 * <p>
 * Ngày tạo: 10/09/2023
 * <p>
 * Lần cập nhật cuối: 19/11/2023
 * <p>
 * Nội dung cập nhật: cập nhật lịch sử code
 */
public class AccountDAO {
    private static AccountDAO instance = new AccountDAO();

    /**
     * Tạo thể hiện hiện tại cho AccountDAO
     */
    public static AccountDAO getInstance() {
        return instance;
    }

    /**
     * Kiểm tra đăng nhập thông qua tình trạng của tài khoản
     *
     * @param user: truyền vào taiKhoan và matKhau
     */
    public int checkLogin(Account user) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String query = "SELECT * FROM TaiKhoan WHERE taiKhoan = ? AND matKhau = ? ";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, user.getTaiKhoan());
            preparedStatement.setString(2, user.getMatKhau());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int tinhTrang = resultSet.getInt("tinhTrang");
                    return tinhTrang;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
