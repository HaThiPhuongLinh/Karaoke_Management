package DAO;

import ConnectDB.ConnectDB;
import Entity.TypeOfRoom;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Người tham gia thiết kế: Nguyễn Đình Dương, Hà Thị Phương Linh
 * <p>
 * Ngày tạo: 23/09/2023
 * <p>
 * Lần cập nhật cuối: 11/11/2023
 * <p>
 * Nội dung cập nhật: cập nhật lịch sử code
 */
public class TypeOfRoomDAO {

    /**
     * Lấy ra danh sách tất cả loại phòng
     *
     * @return {@code List<TypeOfRoom>}: danh sách loại phòng
     */
    public static List<TypeOfRoom> getAllLoaiPhong() {
        List<TypeOfRoom> dsLoaiPhong = new ArrayList<TypeOfRoom>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "Select * from LoaiPhong";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsLoaiPhong.add(new TypeOfRoom(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLoaiPhong;
    }

    /**
     * Lấy ra danh sách loại phòng dựa trên tên loại phòng
     *
     * @param name: tên loại phòng
     * @return {@code List<TypeOfRoom>}: danh sách loại phòng
     */
    public static ArrayList<TypeOfRoom> getTypeOfRoomByName(String name) {
        ArrayList<TypeOfRoom> lst = new ArrayList<TypeOfRoom>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT * FROM LoaiPhong where tenLoaiPhong like ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maLP = rs.getString("maLoaiPhong");
                String tenLP = rs.getString("tenLoaiPhong");
                int succhua = rs.getInt("sucChua");
                TypeOfRoom typeOfRoom = new TypeOfRoom(maLP, tenLP, succhua);
                lst.add(typeOfRoom);
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
        return lst;
    }

    /**
     * Lấy ra loại phòng dựa theo mã loại phòng
     *
     * @param roomID: mã loại phòng
     * @return {@code TypeOfRoom}: loại phòng
     */
    public TypeOfRoom getRoomTypeByID(String roomID) {
        TypeOfRoom room = null;
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM LoaiPhong where maLoaiPhong = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, roomID);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) return null;
            room = new TypeOfRoom(rs);
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return room;
    }

    /**
     * Lấy ra loại phòng dựa theo mã phòng
     *
     * @param roomID: mã phòng
     * @return tên loại phòng
     */
    public String getRoomTypeNameByRoomID(String roomID) {
        String name = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT tenLoaiPhong FROM LoaiPhong WHERE maLoaiPhong = (SELECT maLoaiPhong FROM Phong WHERE maPhong = ?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, roomID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                name = rs.getString("tenLoaiPhong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
    }

    /**
     * Thêm loại phòng
     *
     * @param s: loại phòng
     * @return {@code boolean}: true/false
     */
    public boolean insert(TypeOfRoom s) {
        ConnectDB.getInstance();
        Connection con = new ConnectDB().getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            String sql = "insert into dbo.LoaiPhong (maLoaiPhong,tenLoaiPhong,sucChua)" + "values (?,?,?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, s.getMaLoaiPhong());
            statement.setString(2, s.getTenLoaiPhong());
            statement.setInt(3, s.getSucChua());

            n = statement.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains(s.getMaLoaiPhong())) JOptionPane.showMessageDialog(null, "Trùng mã loại phòng");
        }
        return n > 0;
    }

    /**
     * Cập nhật loại phòng
     *
     * @param s: loại phòng
     * @return {@code boolean}: true/false
     */
    public boolean update(TypeOfRoom s) {
        int n = 0;
        PreparedStatement stmt = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String sql = "update dbo.LoaiPhong set tenLoaiPhong = ? ,sucChua=?" + " Where maLoaiPhong = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, s.getTenLoaiPhong());
            stmt.setInt(2, s.getSucChua());
            stmt.setString(3, s.getMaLoaiPhong());
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return n > 0;
    }

    /**
     * Xóa loại phòng
     *
     * @param id: mã loại phòng
     * @return {@code boolean}: true/false
     */
    public boolean delete(String id) {
        int n = 0;
        PreparedStatement stmt = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String sql = "delete from dbo.LoaiPhong where maLoaiPhong = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, id);

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return n > 0;
    }


}
