package DAOs;
import java.sql.*;
import java.util.List;
import ConnectDB.ConnectDB;
import Entity.Room;
import Entity.Service;
import Entity.TypeOfRoom;
import Entity.TypeOfService;

import java.util.ArrayList;

public class TypeOfServiceDAO {
    public List<TypeOfService> getAllLoaiDichVu() {
        List<TypeOfService> dsLoaiDichVu = new ArrayList<TypeOfService>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "Select * from LoaiDichVu";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsLoaiDichVu.add(
                        new TypeOfService(rs.getString(1), rs.getString(2)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLoaiDichVu;
    }

    public TypeOfService getServiceTypeByID(String typeID) {
        TypeOfService tr = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM LoaiDichVu WHERE maLoaiDichVu = ?";

            stmt = con.prepareStatement(sql);
            stmt.setString(1, typeID);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next())
                return null;
            tr = new TypeOfService(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tr;
    }

    public ArrayList<TypeOfService> getTypeOfServiceByName(String name) {
        ArrayList<TypeOfService> lst = new ArrayList<TypeOfService>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT * FROM LoaiDichVu where tenoaiDichVu like ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maLDV = rs.getString("maLoaiDichVu");
                String tenLDV = rs.getString("tenoaiDichVu");
                TypeOfService typeOfService = new TypeOfService(maLDV, tenLDV);
                lst.add(typeOfService);
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

    public boolean insert(TypeOfService s){
        ConnectDB.getInstance();
        Connection con = new ConnectDB().getConnection();
        PreparedStatement statement = null;
        int n=0;
        try{
            String sql = "insert into dbo.LoaiDichVu (maLoaiDichVu,tenoaiDichVu)"+"values (?,?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, s.getMaLoaiDichVu());
            statement.setString(2, s.getTenLoaiDichVu());
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n>0;
    }
    public boolean update(TypeOfService s) {
        int n = 0;
        PreparedStatement stmt = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String sql = "update dbo.LoaiDichVu set tenoaiDichVu = ?" + " Where maLoaiDichVu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, s.getTenLoaiDichVu());
            stmt.setString(2, s.getMaLoaiDichVu());
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
        String sql = "delete from dbo.LoaiDichVu where maLoaiDichVu = ?";
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
