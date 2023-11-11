package ConnectDB;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Kết nối Database
 * Người thiết kế: Hà Thị Phương Linh
 * Ngày tạo: 09/10/2023
 * Lần cập nhật cuối : 09/11/2023
 */
public class ConnectDB {
    public static Connection con = null;
    private static ConnectDB instance = new ConnectDB();

    public static ConnectDB getInstance() {
        return instance;
    }

    /**
     * Connect tới database "KRManagement" với user = "sa", password = "123456" bằng sqljdbc42
     * @throws SQLException
     */
    public static void connect () throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databasename= KRManagement";
        String user = "sa";
        String password = "123456";
        con = DriverManager.getConnection(url, user, password);
    }

    /**
     * Truyền kết nối Driver
     * @return
     */
    public static Connection getConnection() {
        return con;
    }
}
