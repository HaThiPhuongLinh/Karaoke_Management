package DAOs;

import Entity.Account;

import ConnectDB.ConnectDB;
import Entity.Staff;

import java.sql.*;
import java.util.ArrayList;

public class AccountDAO {
    private static AccountDAO instance = new AccountDAO();
    public static AccountDAO getInstance() {
        return instance;
    }


    public Account getAccountByTaiKhoan(String taiKhoan) {
        Account account = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();

        String sql = "SELECT * FROM TaiKhoan WHERE taiKhoan = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, taiKhoan);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    account = new Account(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public String getAccountByAccount(String taiKhoan) {
        String account = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();

        String sql = "SELECT * FROM TaiKhoan WHERE taiKhoan = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, taiKhoan);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    account = resultSet.getString("taiKhoan");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

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
