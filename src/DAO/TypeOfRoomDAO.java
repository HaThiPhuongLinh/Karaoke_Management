package DAO;

import java.sql.*;
import java.util.List;

import ConnectDB.ConnectDB;
import Entity.TypeOfRoom;

import javax.swing.*;
import java.util.ArrayList;

public class TypeOfRoomDAO {
    public static List<TypeOfRoom> getAllLoaiPhong() {
        List<TypeOfRoom> dsLoaiPhong = new ArrayList<TypeOfRoom>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "Select * from LoaiPhong";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsLoaiPhong.add(
                        new TypeOfRoom(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLoaiPhong;
    }


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
            if (!rs.next())
                return null;
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
                TypeOfRoom typeOfRoom = new TypeOfRoom(maLP, tenLP,succhua);
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
    public boolean insert(TypeOfRoom s){
        ConnectDB.getInstance();
        Connection con = new ConnectDB().getConnection();
        PreparedStatement statement = null;
        int n=0;
        try{
            String sql = "insert into dbo.LoaiPhong (maLoaiPhong,tenLoaiPhong,sucChua)"+"values (?,?,?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, s.getMaLoaiPhong());
            statement.setString(2, s.getTenLoaiPhong());
            statement.setInt(3, s.getSucChua());

            n = statement.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains(s.getMaLoaiPhong()))
                JOptionPane.showMessageDialog(null, "Trùng mã loại phòng");
        }
        return n>0;
    }
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
