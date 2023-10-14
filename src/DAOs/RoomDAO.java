package DAOs;

import Entity.Room;

import java.sql.*;
import java.util.List;

import ConnectDB.ConnectDB;
import Entity.TypeOfRoom;

import java.util.ArrayList;

public class RoomDAO {

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
     * Lầy trạng thái phòng dựa trên ID phòng
     *
     * @param roomID: ID phòng
     * @return {@code String}: trạng thái phòng
     */
    public String getSatusRoomByID(String roomID) {
        String status = "";
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT tinhTrang FROM Phong where maPhong = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, roomID);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                return null;
            status = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status;

    }

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


}
