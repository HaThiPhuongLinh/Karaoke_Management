package DAO;

import ConnectDB.ConnectDB;
import Entity.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Người tham gia thiết kế: Nguyễn Đình Dương, Hà Thị Phương Linh
 * <p>
 * Ngày tạo: 23/09/2023
 * <p>
 * Lần cập nhật cuối: 12/11/2023
 * <p>
 * Nội dung cập nhật: cập nhật hàm switchRoom (đổi trạng thái)
 */
public class RoomDAO {

    /**
     * lấy danh sách thông tin tất cả phòng
     *
     * @return {@code ArrayList<Room>}: danh sách phòng
     */
    public ArrayList<Room> getRoomList() {
        ArrayList<Room> rooms = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT p.maPhong, p.maLoaiPhong, p.tinhTrang , p.viTri, p.giaPhong, lp.maLoaiPhong, lp.tenLoaiPhong, lp.sucChua  " +
                    "FROM dbo.Phong p, dbo.LoaiPhong lp " +
                    "WHERE p.maLoaiPhong = lp.maLoaiPhong";
            stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rooms.add(new Room(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rooms;
    }

    /**
     * Lầy danh sách phòng dựa trên ID phòng
     *
     * @param roomID: ID phòng
     * @return {@code Room}: phòng
     */
    public Room getRoomByRoomId(String roomID) {
        Room room = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT TOP 1 p.maPhong, p.maLoaiPhong, p.tinhTrang , p.viTri, p.giaPhong, lp.maLoaiPhong,lp.tenLoaiPhong, lp.sucChua " +
                    "FROM dbo.Phong p, dbo.LoaiPhong lp " +
                    "WHERE p.maLoaiPhong = lp.maLoaiPhong AND p.maPhong = ?";

            stmt = con.prepareStatement(sql);
            stmt.setString(1, roomID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                room = new Room(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return room;
    }

    /**
     * Lầy danh sách phòng dựa trên trạng thái phòng
     *
     * @param status: trạng thái phòng
     * @return {@code Room}: phòng
     */
    public ArrayList<Room> getRoomsByStatus(String status) {
        ArrayList<Room> rooms = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = " SELECT p.maPhong, p.maLoaiPhong, p.tinhTrang , p.viTri, p.giaPhong, lp.maLoaiPhong,lp.tenLoaiPhong, lp.sucChua FROM dbo.Phong p INNER JOIN dbo.LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong WHERE p.tinhTrang = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, status);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rooms.add(new Room(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rooms;
    }

    /**
     * Lầy danh sách phòng dựa trên tên loại phòng
     *
     * @param roomType: tên loại phòng
     * @return {@code Room}: phòng
     */
    public ArrayList<Room> getRoomsByType(String roomType) {
        ArrayList<Room> rooms = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT p.maPhong, p.maLoaiPhong, p.tinhTrang , p.viTri, p.giaPhong, lp.maLoaiPhong,lp.tenLoaiPhong, lp.sucChua FROM dbo.Phong p INNER JOIN dbo.LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong WHERE lp.tenLoaiPhong = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, roomType);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rooms.add(new Room(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rooms;
    }

    /**
     * Lầy danh sách phòng trống
     *
     * @return {@code ArrayList<Room>}: danh sách phòng trống
     */
    public ArrayList<Room> getListAvailableRoom() {
        ArrayList<Room> rooms = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT p.maPhong, p.tinhTrang, p.viTri, p.giaPhong lp.maLoaiPhong, lp.sucChua, lp.tenLoaiPhong" +
                    "FROM dbo.Phong p " +
                    "INNER JOIN dbo.LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
                    "WHERE p.tinhTrang = 'Trống'";
            stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rooms.add(new Room(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rooms;
    }

    /**
     * Lầy danh sách phòng trống dựa trên tên loại phòng
     *
     * @param roomTypeName: tên loại phòng
     * @return {@code ArrayList<Room>}: danh sách phòng trống
     */
    public ArrayList<Room> getListAvailableRoomByRoomTypeName(String roomTypeName) {
        ArrayList<Room> rooms = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT p.maPhong, p.tinhTrang, p.viTri, p.giaPhong, lp.maLoaiPhong, lp.sucChua, lp.tenLoaiPhong " +
                    "FROM dbo.Phong p " +
                    "INNER JOIN dbo.LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
                    "WHERE p.tinhTrang = 'Trống'";
            stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rooms.add(new Room(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rooms;
    }

    /**
     * Cập nhật trạng thái phòng dựa trên mã phòng
     *
     * @param roomId: mã phòng
     * @param status: trạng thái phòng
     * @return {@code boolean}: true/false
     */
    public boolean updateRoomStatus(String roomId, String status) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String updateSql = "UPDATE dbo.Phong SET tinhTrang = ? WHERE maPhong = ?";
            stmt = con.prepareStatement(updateSql);
            stmt.setString(1, status);
            stmt.setString(2, roomId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated != 1) {
                return false;
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Thêm phòng
     *
     * @param ro: phòng
     * @return {@code boolean}: true/false
     */
    public boolean insert(Room ro) throws SQLException {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            String sql = "insert into dbo.Phong (maPhong, maLoaiPhong, tinhTrang, viTri, giaPhong)" + "values (?,?,?,?,?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, ro.getMaPhong());
            statement.setString(2, ro.getLoaiPhong().getMaLoaiPhong());
            statement.setString(3, ro.getTinhTrang());
            statement.setString(4, ro.getViTri());
            statement.setDouble(5, ro.getGiaPhong());

            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e2) {
                // TODO: handle exception
                e2.printStackTrace();
            }
        }
        return n > 0;
    }

    /**
     * Sửa phòng
     *
     * @param ro: phòng
     * @return {@code boolean}: true/false
     */
    public boolean update(Room ro) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            String sql = "update dbo.Phong" + " set maLoaiPhong = ?, tinhTrang=?, viTri = ?, giaPhong = ?"
                    + " where maPhong = ?";
            statement = con.prepareStatement(sql);
            statement.setString(5, ro.getMaPhong());
            statement.setString(1, ro.getLoaiPhong().getMaLoaiPhong());
            statement.setString(2, ro.getTinhTrang());
            statement.setString(3, ro.getViTri());
            statement.setDouble(4, ro.getGiaPhong());
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return n > 0;
    }

    /**
     * Xóa phòng
     *
     * @param ro: phòng
     * @return {@code boolean}: true/false
     */
    public boolean delete(Room ro) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            String sql = "delete from dbo.Phong" + " where maPhong = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, ro.getMaPhong());
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    /**
     * Lấy danh sách phòng dựa trên mã phòng và tình trạng
     *
     * @param roomID: mã phòng
     * @param status: tình trạng phòng
     * @return {@code ArrayList<Room>}: danh sách phòng
     */
    public ArrayList<Room> getRoomsByRoomIdAndStatus(String roomID, String status) {
        ArrayList<Room> rooms = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT p.maPhong, p.maLoaiPhong, p.tinhTrang , p.viTri, p.giaPhong, lp.maLoaiPhong, lp.tenLoaiPhong, lp.sucChua " +
                    "FROM dbo.Phong p, dbo.LoaiPhong lp " +
                    "WHERE p.maLoaiPhong = lp.maLoaiPhong AND p.maPhong = ? AND p.tinhTrang = ?";

            stmt = con.prepareStatement(sql);
            stmt.setString(1, roomID);
            stmt.setString(2, status);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rooms.add(new Room(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rooms;
    }

    /**
     * Phát sinh mã phòng tự động (P1ii, i++)
     *
     * @return {@code String}
     */
    public String generateNextRoomId() {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String nextRoomId = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT TOP 1 maPhong FROM dbo.Phong ORDER BY maPhong DESC";
            statement = con.prepareStatement(sql);
            rs = statement.executeQuery();

            if (rs.next()) {
                String lastRoomId = rs.getString("maPhong");
                String numericPart = lastRoomId.substring(2); // Lấy phần số từ mã phòng cuối cùng
                int counter = Integer.parseInt(numericPart) + 1; // Tăng giá trị số lên 1
                nextRoomId = "P1" + String.format("%02d", counter); // Định dạng lại giá trị số thành chuỗi 3 chữ số, sau đó ghép với tiền tố "DV"
            } else {
                nextRoomId = "P101";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextRoomId;
    }

    /**
     * Đổi phòng dựa trên mã hóa đơn
     *
     * @param billId:    mã hóa đơn
     * @param oldRoomId: mã phòng cũ
     * @param newRoomId: mã phòng mới
     * @return {@code boolean}: true/false
     */
    public boolean switchRoom(String billId, String oldRoomId, String newRoomId) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            // Cập nhật tình trạng phòng mới thành đã được sử dụng (1)
            String updateNewRoomSql = "UPDATE dbo.Phong SET tinhTrang = 'Dang su dung' WHERE maPhong = ?";
            statement = con.prepareStatement(updateNewRoomSql);
            statement.setString(1, newRoomId);
            statement.executeUpdate();

            // Kiểm tra xem phòng cũ có phiếu đặt phòng không
            String checkReservationSql = "SELECT trangThai FROM dbo.PhieuDatPhong WHERE maPhong = ?;";
            statement = con.prepareStatement(checkReservationSql);
            statement.setString(1, oldRoomId);
            ResultSet rsReservation = statement.executeQuery();

            boolean hasWaitStatusReservation = false;

            while (rsReservation.next()) {
                int trangThaiPhieu = rsReservation.getInt("trangThai");

                if (trangThaiPhieu == 2) {
                    hasWaitStatusReservation = true;
                    break;
                }
            }

            // Cập nhật tình trạng phòng cũ
            String updateOldRoomStatusSql = hasWaitStatusReservation ?
                    "UPDATE dbo.Phong SET tinhTrang = 'Cho' WHERE maPhong = ?" :
                    "UPDATE dbo.Phong SET tinhTrang = 'Trong' WHERE maPhong = ?";
            statement = con.prepareStatement(updateOldRoomStatusSql);
            statement.setString(1, oldRoomId);
            statement.executeUpdate();

            // Cập nhật hóa đơn với mã phòng mới
            String updateBillSql = "UPDATE dbo.HoaDon SET maPhong = ? WHERE maHoaDon = ? AND tinhTrangHD = 0";
            statement = con.prepareStatement(updateBillSql);
            statement.setString(1, newRoomId);
            statement.setString(2, billId);
            int rowsAffected = statement.executeUpdate();

            // Kiểm tra xem có hóa đơn nào có mã phòng mới và mã hóa đơn không
            String checkBillSql = "SELECT TOP 1 1 FROM dbo.HoaDon WHERE maPhong = ? AND maHoaDon = ?";
            statement = con.prepareStatement(checkBillSql);
            statement.setString(1, newRoomId);
            statement.setString(2, billId);
            boolean billExists = statement.executeQuery().next();

            return rowsAffected > 0 && billExists;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
