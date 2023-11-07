package DAO;
import java.sql.*;
import java.util.List;
import ConnectDB.ConnectDB;
import Entity.TypeOfService;

import java.util.ArrayList;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code TypeOfServiceDAO}
 * <p>
 * Người tham gia thiết kế: Nguyễn Quang Duy
 * <p>
 * Ngày tạo: 07/10/2023
 * <p>
 * Lần cập nhật cuối: 7/11/2023
 * <p>
 * Nội dung cập nhật: thêm javadoc
 */

public class TypeOfServiceDAO {
    /**
     * lấy danh sách thông tin tất cả loại dịch vụ
     * @return {@code ArrayList<Service>}: danh sách dịch vụ
     */
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
    /**
     * lấy loại dịch vụ theo mã loại dịch vụ
     * @param typeID mã loại dịch vụ
     * @return dịch vụ theo mã loại dịch vụ được tìm
     */
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
    /**
     * Lấy danh sách loại dịch vụ theo tên loại dịch vụ
     * @param name tên loại dịch vụ
     * @return {@code ArrayList<Service>}: danh sách theo tên loại dịch vụ
     */
    public ArrayList<TypeOfService> getTypeOfServiceByName(String name) {
        ArrayList<TypeOfService> lst = new ArrayList<TypeOfService>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT * FROM LoaiDichVu where tenLoaiDichVu like ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maLDV = rs.getString("maLoaiDichVu");
                String tenLDV = rs.getString("tenLoaiDichVu");
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
    /**
     * Thêm loại dịch vụ mới vào cơ sở dữ liệu
     * @param s {@code Service}: loại dịch vụ cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *          <ul>
     *              <li>Nếu thêm thành công thì trả về {@code true}</li>
     *              <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *          </ul>
     */
    public boolean insert(TypeOfService s){
        ConnectDB.getInstance();
        Connection con = new ConnectDB().getConnection();
        PreparedStatement statement = null;
        int n=0;
        try{
            String sql = "insert into dbo.LoaiDichVu (maLoaiDichVu,tenLoaiDichVu)"+"values (?,?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, s.getMaLoaiDichVu());
            statement.setString(2, s.getTenLoaiDichVu());
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n>0;
    }
    /**
     * Cập nhật thông tin loại dịch vụ vào cơ sở dữ liệu
     * @param s {@code: Service}: loại dịch vụ cần cập nhật
     * @return {@code: boolean}: kết quả trả về của câu truy vấn
     *          <ul>
     *              <li>Nếu cập nhật thành công thì trả về {@code: true}</li>
     *              <li>Nếu cập nhật thất bại thì trả về {@code: false}</li>
     *          </ul>
     */
    public boolean update(TypeOfService s) {
        int n = 0;
        PreparedStatement stmt = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String sql = "update dbo.LoaiDichVu set tenLoaiDichVu = ?" + " Where maLoaiDichVu = ?";
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
    /**
     * Xóa thông tin loại dịch vụ trong cơ sở dữ liệu
     * @param id {@code: Service}: mã loại dịch vụ cần cập nhật
     * @return {@code: boolean}: kết quả trả về của câu truy vấn
     *          <ul>
     *              <li>Nếu xóa thành công thì trả về {@code: true}</li>
     *              <li>Nếu xóa thất bại thì trả về {@code: false}</li>
     *          </ul>
     */
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
    /**
     * tìm mã loại dịch vụ theo tên loại dịch vụ
     * @param TypeOfserviceName tên loại dịch vụ
     * @return {@code ArrayList<Service>}: mã loại dịch vụ theo tên loại dịch vụ
     */
    public String getServiceCodeByName(String TypeOfserviceName) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String serviceCode = null;

        try {
            con = ConnectDB.getConnection();  // Thiết lập kết nối đến cơ sở dữ liệu

            // Tạo câu lệnh truy vấn SQL để lấy mã dịch vụ từ tên dịch vụ
            String sql = "SELECT maLoaiDichVu FROM dbo.LoaiDichVu WHERE tenLoaiDichVu = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, TypeOfserviceName);

            // Thực thi câu lệnh truy vấn
            rs = statement.executeQuery();

            // Xử lý kết quả trả về (nếu có)
            if (rs.next()) {
                // Lấy thông tin mã dịch vụ từ ResultSet
                serviceCode = rs.getString("maLoaiDichVu");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceCode;
    }
    /**
     * Tạo mã loại dịch vụ phát sinh tự động bằng cách lấy mã cuối cùng trong database tăng lên 1
     * @return mã loại dịch vụ mới
     */
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
        return nextServiceId;
    }
}
