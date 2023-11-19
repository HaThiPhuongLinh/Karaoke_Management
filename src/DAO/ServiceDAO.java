package DAO;

import ConnectDB.ConnectDB;
import Entity.Service;
import Entity.TypeOfService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code ServiceDAO}
 * <p>
 * Người tham gia thiết kế: Nguyễn Quang Duy, Hà Thị Phương Linh
 * <p>
 * Ngày tạo: 07/10/2023
 * <p>
 * Lần cập nhật cuối: 7/11/2023
 * <p>
 * Nội dung cập nhật: thêm javadoc
 */
public class ServiceDAO {
    /**
     * lấy danh sách thông tin tất cả dịch vụ
     *
     * @return {@code ArrayList<Service>}: danh sách dịch vụ
     */
    public List<Service> getAllDichVu() {
        List<Service> dsDichVu = new ArrayList<Service>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "Select * from dbo.DichVu";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsDichVu.add(
                        new Service(rs.getString(1), rs.getString(2), new TypeOfService(rs.getString(3)), rs.getString(4), rs.getInt(5), rs.getDouble(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsDichVu;
    }

    /**
     * Lấy danh sách dịch vụ theo tên dịch vụ
     *
     * @param name tên dịch vụ
     * @return {@code ArrayList<Service>}: danh sách theo tên dịch vụ
     */

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
                        new Service(rs.getString(1), rs.getString(2), new TypeOfService(rs.getString(3)), rs.getString(4), rs.getInt(5), rs.getDouble(6)));
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
     * Thêm dịch vụ mới vào cơ sở dữ liệu
     *
     * @param s {@code Service}: dịch vụ cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     * <ul>
     *     <li>Nếu thêm thành công thì trả về {@code true}</li>
     *     <li>Nếu thêm thất bại thì trả về {@code false}</li>
     * </ul>
     */
    public boolean insert(Service s) {
        ConnectDB.getInstance();
        Connection con = new ConnectDB().getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            String sql = "insert into dbo.DichVu (maDichVu,tenDichVu,maLoaiDichVu,donViTinh,soLuongTon,giaBan)" + "values (?,?,?,?,?,?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, s.getMaDichVu());
            statement.setString(2, s.getTenDichVu());
            statement.setString(3, s.getMaLoaiDichVu().getMaLoaiDichVu());
            statement.setString(4, s.getDonViTinh());
            statement.setInt(5, s.getSoLuongTon());
            statement.setDouble(6, s.getGiaBan());

            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    /**
     * Cập nhật thông tin dịch vụ vào cơ sở dữ liệu
     *
     * @param s {@code: Service}: dịch vụ cần cập nhật
     * @return {@code: boolean}: kết quả trả về của câu truy vấn
     * <ul>
     *     <li>Nếu cập nhật thành công thì trả về {@code: true}</li>
     *     <li>Nếu cập nhật thất bại thì trả về {@code: false}</li>
     * </ul>
     */
    public boolean update(Service s) {
        int n = 0;
        PreparedStatement stmt = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String sql = "update dbo.DichVu set tenDichVu = ?,maLoaiDichVu = ?, donViTinh = ?, soLuongTon = ?, giaBan = ?" + " Where maDichVu = ?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, s.getTenDichVu());
            stmt.setString(2, s.getMaLoaiDichVu().getMaLoaiDichVu());
            stmt.setString(3, s.getDonViTinh());
            stmt.setInt(4, s.getSoLuongTon());
            stmt.setDouble(5, s.getGiaBan());
            stmt.setString(6, s.getMaDichVu());

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
     * Xóa thông tin dịch vụ trong cơ sở dữ liệu
     *
     * @param id {@code: Service}: mã dịch vụ cần cập nhật
     * @return {@code: boolean}: kết quả trả về của câu truy vấn
     * <ul>
     *     <li>Nếu xóa thành công thì trả về {@code: true}</li>
     *     <li>Nếu xóa thất bại thì trả về {@code: false}</li>
     * </ul>
     */
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

    /**
     * lấy dịch vụ theo mã dịch vụ
     *
     * @param maDichVu mã dịch vụ
     * @return dịch vụ theo mã dịch vụ được tìm
     */
    public Service getDichVuByMaDichVu(String maDichVu) {
        Service service = null;
        PreparedStatement stmt = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String sql = "SELECT * FROM dbo.DichVu WHERE maDichVu = ?";

        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maDichVu);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                service = new Service(
                        rs.getString(1),
                        rs.getString(2),
                        new TypeOfService(rs.getString(3)),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getDouble(6)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return service;
    }

    /**
     * Tạo mã dịch vụ phát sinh tự động bằng cách lấy mã cuối cùng trong database tăng lên 1
     *
     * @return mã dịch vụ mới
     */
    public String generateNextServiceId() {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String nextServiceId = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT TOP 1 maDichVu FROM dbo.DichVu ORDER BY maDichVu DESC";
            statement = con.prepareStatement(sql);
            rs = statement.executeQuery();

            if (rs.next()) {
                String lastServiceId = rs.getString("maDichVu");
                String numericPart = lastServiceId.substring(2); // Lấy phần số từ mã dịch vụ cuối cùng
                int counter = Integer.parseInt(numericPart) + 1; // Tăng giá trị số lên 1
                nextServiceId = "DV" + String.format("%03d", counter); // Định dạng lại giá trị số thành chuỗi 3 chữ số, sau đó ghép với tiền tố "DV"
            } else {
                nextServiceId = "DV001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextServiceId;
    }

    /**
     * Lấy danh sách dịch vụ theo tên loại dịch vụ
     *
     * @param serviceTypeName tên loại dịch vụ
     * @return {@code ArrayList<Service>}: danh sách theo tên loại dịch vụ
     */
    public ArrayList<Service> getServiceListByServiceTypeName(String serviceTypeName) {
        ArrayList<Service> serviceList = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();

        try {
            String sql = "SELECT dv.maDichVu, dv.giaBan, dv.soLuongTon, dv.tenDichVu, dv.donViTinh, ldv.tenLoaiDichVu, ldv.maLoaiDichVu " +
                    "FROM dbo.DichVu dv, dbo.LoaiDichVu ldv " +
                    "WHERE dv.maLoaiDichVu = ldv.maLoaiDichVu AND ldv.tenLoaiDichVu = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, serviceTypeName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String maDichVu = resultSet.getString("maDichVu");
                double giaBan = resultSet.getDouble("giaBan");
                int soLuongTon = resultSet.getInt("soLuongTon");
                String tenDichVu = resultSet.getString("tenDichVu");
                String maLDV = resultSet.getString("maLoaiDichVu");
                String donViTinh = resultSet.getString("donViTinh");

                Service service = new Service(maDichVu, tenDichVu, new TypeOfService(maLDV), donViTinh, soLuongTon, giaBan);
                serviceList.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    /**
     * Lấy danh sách dịch vụ theo tên dịch vụ và tên loại dịch vụ
     *
     * @param serviceName     tên dịch vụ
     * @param serviceTypeName tên loại dịch vụ
     * @return {@code ArrayList<Service>}: danh sách theo tên dịch vụ và tên loại dịch vụ
     */
    public ArrayList<Service> getServiceListByNameAndServiceTypeName(String serviceName, String serviceTypeName) {
        ArrayList<Service> serviceList = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();

        try {
            String sql = "SELECT dv.maDichVu, dv.tenDichVu, dv.giaBan, dv.soLuongTon, dv.donViTinh, ldv.maLoaiDichVu, ldv.tenLoaiDichVu " +
                    "FROM dbo.DichVu dv, dbo.LoaiDichVu ldv " +
                    "WHERE dv.maLoaiDichVu = ldv.maLoaiDichVu " +
                    "AND dv.tenDichVu LIKE ? " +
                    "AND ldv.tenLoaiDichVu = ? ";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, "%" + serviceName + "%");
            statement.setString(2, serviceTypeName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String maDichVu = resultSet.getString("maDichVu");
                double giaBan = resultSet.getDouble("giaBan");
                int soLuongTon = resultSet.getInt("soLuongTon");
                String tenDichVu = resultSet.getString("tenDichVu");
                String maLDV = resultSet.getString("maLoaiDichVu");
                String donViTinh = resultSet.getString("donViTinh");

                Service service = new Service(maDichVu, tenDichVu, new TypeOfService(maLDV), donViTinh, soLuongTon, giaBan);
                serviceList.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceList;
    }

}