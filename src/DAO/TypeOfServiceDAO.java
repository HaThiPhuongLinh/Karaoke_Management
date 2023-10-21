package DAO;
import java.sql.*;
import java.util.List;
import ConnectDB.ConnectDB;
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
//    public TypeOfService getServiceTypeByName1(String serviceTypeName) {
//        String query = "SELECT * FROM LoaiDichVu WHERE tenoaiDichVu = ?";
//        Object[] params = { serviceTypeName };
//        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
//        TypeOfService result = null;
//        try {
//            while (rs.next()) {
//                result = new TypeOfService(rs);
//                break;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

//    public String getLastServiceTypeID() {
//        String query = "{CALL USP_getLastServiceTypeID()}";
//        Object obj = DataProvider.getInstance().executeScalar(query, null);
//        String result = obj != null ? obj.toString() : "";
//        return result;
//    }

    public String getServiceCodeByName(String serviceName) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String serviceCode = null;

        try {
            con = ConnectDB.getConnection();  // Thiết lập kết nối đến cơ sở dữ liệu

            // Tạo câu lệnh truy vấn SQL để lấy mã dịch vụ từ tên dịch vụ
            String sql = "SELECT maLoaiDichVu FROM dbo.LoaiDichVu WHERE tenoaiDichVu = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, serviceName);

            // Thực thi câu lệnh truy vấn
            rs = statement.executeQuery();

            // Xử lý kết quả trả về (nếu có)
            if (rs.next()) {
                // Lấy thông tin mã dịch vụ từ ResultSet
                serviceCode = rs.getString("maLoaiDichVu");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý các ngoại lệ hoặc thông báo lỗi tại đây...
        }
//        finally {
//            // Đảm bảo đóng tất cả các kết nối, câu lệnh và tài nguyên dùng trong lúc thực hiện
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//
//                if (statement != null) {
//                    statement.close();
//                }
//
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e2) {
//                e2.printStackTrace();
//            }
//        }

        return serviceCode;
    }
    public String generateNextTypeOfServiceId() {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String nextServiceId = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT TOP 1 maLoaiDichVu FROM dbo.LoaiDichVu ORDER BY maLoaiDichVu DESC";
            statement = con.prepareStatement(sql);
            rs = statement.executeQuery();

            if (rs.next()) {
                String lastServiceId = rs.getString("maLoaiDichVu");
                String numericPart = lastServiceId.substring(3); // Lấy phần số từ mã dịch vụ cuối cùng
                int counter = Integer.parseInt(numericPart) + 1; // Tăng giá trị số lên 1
                nextServiceId = "LDV" + String.format("%02d", counter); // Định dạng lại giá trị số thành chuỗi 3 chữ số, sau đó ghép với tiền tố "DV"
            } else {
                nextServiceId = "LDV01";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//
//                if (statement != null) {
//                    statement.close();
//                }
//
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e2) {
//                e2.printStackTrace();
//            }
//        }

        return nextServiceId;
    }
}
