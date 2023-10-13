package DAOs;
import java.sql.*;
import java.util.List;
import ConnectDB.ConnectDB;
import Entity.Room;
import Entity.TypeOfRoom;
import Entity.TypeOfService;
import Entity.Service;
import java.util.ArrayList;

public class ServiceDAO {
    public List<Service> getAllDichVu() {
        List<Service> dsDichVu = new ArrayList<Service>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "Select * from DichVu";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsDichVu.add(
                        new Service(rs.getString(1), rs.getString(2), new TypeOfService(rs.getString(3)), rs.getString(4),rs.getInt(5), rs.getDouble(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsDichVu;
    }

    public ArrayList<Service> getServiceByName(String name) {
        ArrayList<Service> lst = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT * FROM DichVu where tenDichVu like ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lst.add(
                        new Service(rs.getString(1), rs.getString(2), new TypeOfService(rs.getString(3)), rs.getString(4),rs.getInt(5), rs.getDouble(6)));
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

    public boolean insert(Service s){
        ConnectDB.getInstance();
        Connection con = new ConnectDB().getConnection();
        PreparedStatement statement = null;
        int n=0;
        try{
            String sql = "insert into dbo.DichVu (maDichVu,tenDichVu,maLoaiDichVu,donViTinh,soLuongTon,giaBan)"+"values (?,?,?,?,?,?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, s.getMaDichVu());
            statement.setString(2, s.getTenDichVu());
            statement.setString(3,s.getMaLoaiDichVu().getMaLoaiDichVu());
            statement.setString(4,s.getDonViTinh());
            statement.setInt(5,s.getSoLuongTon());
            statement.setDouble(6, s.getGiaBan());

            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n>0;
    }
    public boolean update(Service s) {
        int n = 0;
        PreparedStatement stmt = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String sql = "update dbo.DichVu set tenDichVu = ?, giaBan = ?, soLuongTon = ?,maLDV = ?" + " Where maDichVu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, s.getTenDichVu());
            stmt.setDouble(2, s.getGiaBan());
            stmt.setInt(3, s.getSoLuongTon());
            stmt.setString(4,s.getMaLoaiDichVu().getMaLoaiDichVu());
            stmt.setString(5, s.getMaDichVu());

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
        String sql = "delete from dbo.DichVu where maDichVu = ?";
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
    public ArrayList<Service> getDichVuTheoMa(String maDichVu) {
        ArrayList<Service> dsdv = new ArrayList<Service>();
        PreparedStatement statement = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "Select * from dbo.DichVu where maDichVu = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, maDichVu);
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery();
            // Duyệt kết quả trả về
            while (rs.next()) {
                dsdv.add(
                        new Service(rs.getString(1), rs.getString(2), new TypeOfService(rs.getString(3)), rs.getString(4),rs.getInt(5), rs.getDouble(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }

        return dsdv;

    }

    public ArrayList<Service> getDichVuTheoGia(double giaBan) {
        ArrayList<Service> dsdv = new ArrayList<Service>();
        PreparedStatement statement = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "SELECT * FROM DichVu where giaBan like ?";
            statement = con.prepareStatement(sql);
            statement.setDouble(1, giaBan);
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery();
            // Duyệt kết quả trả về
            while (rs.next()) {
                dsdv.add(
                        new Service(rs.getString(1), rs.getString(2), new TypeOfService(rs.getString(3)), rs.getString(4),rs.getInt(5), rs.getDouble(6)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }

        return dsdv;

    }

//    public ArrayList<TypeOfService> getTypeOfServiceByName(String name) {
//        ArrayList<TypeOfService> lst = new ArrayList<TypeOfService>();
//        ConnectDB.getInstance();
//        Connection con = ConnectDB.getConnection();
//        PreparedStatement stmt = null;
//
//        try {
//            String sql = "SELECT * FROM LoaiDichVu where tenoaiDichVu like ?";
//            stmt = con.prepareStatement(sql);
//            stmt.setString(1, "%" + name + "%");
//
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                String maLDV = rs.getString("maLoaiDichVu");
//                String tenLDV = rs.getString("tenoaiDichVu");
//                TypeOfService typeOfService = new TypeOfService(maLDV, tenLDV);
//                lst.add(typeOfService);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                stmt.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return lst;
//    }
}
