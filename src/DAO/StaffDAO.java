package DAO;

import ConnectDB.ConnectDB;
import Entity.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    private static StaffDAO instance = new StaffDAO();

    public static StaffDAO getInstance() {
        return instance;
    }

    public ArrayList<Staff> getAllStaff() {
        ArrayList<Staff> dsStaff = new ArrayList<Staff>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT nv.maNhanVien, nv.tenNhanVien, nv.soDienThoai, nv.CCCD, nv.gioiTinh, nv.ngaySinh, " +
                    "nv.diaChi, nv.chucVu, nv.trangThai, " +
                    "nv.taiKhoan, " +
                    "tk.taiKhoan, tk.matKhau, tk.tinhTrang " +
                    "FROM dbo.TaiKhoan tk, dbo.NhanVien nv " +
                    "WHERE tk.taiKhoan = nv.taiKhoan ";

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Staff nhanVien = new Staff(rs);
                dsStaff.add(nhanVien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsStaff;
    }

    public Staff getStaffByUsername(String username) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String query = "SELECT TOP 1 nv.maNhanVien, nv.tenNhanVien, nv.soDienThoai, nv.CCCD, nv.gioiTinh,  nv.ngaySinh, " +
                "nv.diaChi, nv.chucVu, nv.trangThai, " +
                "nv.taiKhoan, " +
                "tk.taiKhoan, tk.matKhau, tk.tinhTrang " +
                "FROM dbo.TaiKhoan tk, dbo.NhanVien nv " +
                "WHERE tk.taiKhoan = nv.taiKhoan " +
                "AND tk.taiKhoan = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Staff staff = new Staff(rs);
                    return staff;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Staff> getListNhanVienByName(String name) {
        ArrayList<Staff> staffList = new ArrayList<Staff>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String query = "SELECT * FROM NhanVien where tenNhanVien like ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + name + "%");

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Staff staff = new Staff(rs);
                    staffList.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public List<Staff> getListNhanVienBySDT(String sdt) {
        List<Staff> sdtlistt = new ArrayList<Staff>();
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien where soDienThoai like ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + sdt + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Staff c = new Staff(rs);
                sdtlistt.add(c);
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
        return sdtlistt;
    }

    public List<Staff> getListNhanVienByCCCD(String cccd) {
        List<Staff> cccdlistt = new ArrayList<Staff>();
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien where CCCD like ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + cccd + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Staff c = new Staff(rs);
                cccdlistt.add(c);
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
        return cccdlistt;
    }

    public boolean addStaff(Staff staff) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            // Kiểm tra xem tài khoản đã tồn tại hay chưa
            String checkAccountQuery = "SELECT COUNT(*) FROM TaiKhoan WHERE taiKhoan = ?";
            stmt = con.prepareStatement(checkAccountQuery);
            stmt.setString(1, staff.getTaiKhoan().getTaiKhoan());
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) == 0) {
                // Tài khoản chưa tồn tại, thêm tài khoản mới
                String insertAccountQuery = "INSERT INTO TaiKhoan (taiKhoan, matKhau, tinhTrang) VALUES (?, ?, ?)";
                stmt = con.prepareStatement(insertAccountQuery);
                stmt.setString(1, staff.getTaiKhoan().getTaiKhoan());
                stmt.setString(2, "1"); // Mật khẩu mặc định là "1"
                stmt.setBoolean(3, true);
                stmt.executeUpdate();
            }

            // Thêm thông tin nhân viên vào bảng NhanVien
            String insertStaffQuery = "INSERT INTO NhanVien (maNhanVien, tenNhanVien, soDienThoai, CCCD, gioiTinh, ngaySinh, diaChi, chucVu, trangThai, taiKhoan) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = con.prepareStatement(insertStaffQuery);
            stmt.setString(1, staff.getMaNhanVien());
            stmt.setString(2, staff.getTenNhanVien());
            stmt.setString(3, staff.getSoDienThoai());
            stmt.setString(4, staff.getCCCD());
            stmt.setBoolean(5, staff.isGioiTinh());
            stmt.setDate(6, (Date) staff.getNgaySinh());
            stmt.setString(7, staff.getDiaChi());
            stmt.setString(8, staff.getChucVu());
            stmt.setString(9, staff.getTrangThai());
            stmt.setString(10, staff.getTaiKhoan().getTaiKhoan());
            stmt.executeUpdate();

            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean updateStaff(Staff s, boolean isResigning) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;

        try {
            String sql = "update dbo.NhanVien" +
                    " set tenNhanVien = ?, soDienThoai = ?, CCCD = ?, gioiTinh = ?, ngaySinh = ?," +
                    " diaChi = ?, chucVu = ?, trangThai = ?, taiKhoan = ?" +
                    " where maNhanVien = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, s.getTenNhanVien());
            statement.setString(2, s.getSoDienThoai());
            statement.setString(3, s.getCCCD());
            statement.setBoolean(4, s.isGioiTinh());
            statement.setDate(5, (Date) s.getNgaySinh());
            statement.setString(6, s.getDiaChi());
            statement.setString(7, s.getChucVu());

            if (isResigning) {
                statement.setString(8, "Đã nghỉ");
            } else {
                statement.setString(8, "Đang làm");
            }

            statement.setString(9, s.getTaiKhoan().getTaiKhoan());
            statement.setString(10, s.getMaNhanVien());


            n = statement.executeUpdate();

            if (isResigning) {
                // Cập nhật trạng thái tài khoản khi nhân viên nghỉ việc
                String updateAccountStatusQuery = "UPDATE TaiKhoan SET tinhTrang = ? WHERE taiKhoan = ?";
                statement = con.prepareStatement(updateAccountStatusQuery);
                statement.setBoolean(1, false); // Cập nhật trạng thái tài khoản thành 0
                statement.setString(2, s.getTaiKhoan().getTaiKhoan());
                statement.executeUpdate();
            } else {
                // Nếu nhân viên đang làm việc, cập nhật trạng thái tài khoản thành 1
                String updateAccountStatusQuery = "UPDATE TaiKhoan SET tinhTrang = ? WHERE taiKhoan = ?";
                statement = con.prepareStatement(updateAccountStatusQuery);
                statement.setBoolean(1, true); // Cập nhật trạng thái tài khoản thành 1
                statement.setString(2, s.getTaiKhoan().getTaiKhoan());
                statement.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return n > 0;
    }


    public Staff getStaffByID(String maNhanVien) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String query = "SELECT nv.maNhanVien, nv.tenNhanVien, nv.soDienThoai, nv.CCCD, nv.gioiTinh,  nv.ngaySinh, " +
                "nv.diaChi, nv.chucVu, nv.trangThai, " +
                "nv.taiKhoan, " +
                "tk.taiKhoan, tk.matKhau, tk.tinhTrang " +
                "FROM dbo.TaiKhoan tk, dbo.NhanVien nv " +
                "WHERE tk.taiKhoan = nv.taiKhoan " +
                "AND nv.maNhanVien = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, maNhanVien);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Staff staff = new Staff(rs);
                    return staff;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String generateNextStaffId() {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String nextStaffId = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT TOP 1 maNhanVien FROM dbo.NhanVien ORDER BY maNhanVien DESC";
            statement = con.prepareStatement(sql);
            rs = statement.executeQuery();

            if (rs.next()) {
                String lastStaffId = rs.getString("maNhanVien");
                String numericPart = lastStaffId.substring(2); // Lấy phần số từ mã nhân viên cuối cùng
                int counter = Integer.parseInt(numericPart) + 1; // Tăng giá trị số lên 1
                nextStaffId = "NV" + String.format("%02d", counter); // Định dạng lại giá trị số thành chuỗi 3 chữ số, sau đó ghép với tiền tố "NV"
            } else {
                nextStaffId = "NV01";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextStaffId;
    }

    public Staff getStaffByBillId(String maHoaDon) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String query = "SELECT NhanVien.* " +
                "FROM NhanVien " +
                "INNER JOIN HoaDon ON NhanVien.maNhanVien = HoaDon.maNhanVien " +
                "WHERE HoaDon.maHoaDon = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, maHoaDon);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Staff staff = new Staff(rs);
                return staff;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
