package DAO;

import Entity.Account;

import ConnectDB.ConnectDB;

import java.sql.*;

public class AccountDAO {
    private static AccountDAO instance = new AccountDAO();
    public static AccountDAO getInstance() {
        return instance;
    }

    /**
     * Kiểm tra đăng nhập thông qua tình trạng của tài khoản
     * @param user: truyền vào taiKhoan và matKhau
     * @return
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
