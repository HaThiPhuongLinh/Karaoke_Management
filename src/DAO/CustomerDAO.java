package DAO;

import ConnectDB.ConnectDB;
import Entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private static CustomerDAO instance = new CustomerDAO();

    public static CustomerDAO getInstance() {
        return instance;
    }

    /**
     * Cập nhật khách hàng
     * @param kh:Khách hàng cần cập nhật
     * @return {@code boolean}: True or false
     */
    public static boolean update(Customer kh) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            String sql = "update dbo.KhachHang" + " set tenKhachHang = ?, soDienThoai=?, CCCD = ?, gioiTinh = ?,ngaySinh=?"
                    + " where maKhachHang = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, kh.getTenKhachHang());
            statement.setString(2, kh.getSoDienThoai());
            statement.setString(3, kh.getCCCD());
            statement.setBoolean(4, kh.isGioiTinh());
            statement.setDate(5, kh.getNgaySinh());
            statement.setString(6, kh.getMaKhachHang());
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    /**
     * Lấy ra danh sách toàn bộ khách hàng
     * @return {@code List<Customer> }:Danh sách khách hàng
     */

    public List<Customer> getAllKhachHang() {
        List<Customer> dsKhachHang = new ArrayList<Customer>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "Select * from KhachHang";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsKhachHang.add(
                        new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getDate(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }

    /**
     * Lấy ra mã khách hàng dựa trên tên khách hàng
     * @param tenKhachHang:Tên khách hàng cần lấy mã
     * @return
     */
    public String getIdByTenKhachHang(String tenKhachHang) {
        String id = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT maKhachHang FROM KhachHang WHERE tenKhachHang = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, tenKhachHang);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return id;
    }

    /**
     * Lấy ra khách hàng dựa trên mã khách hàng
     * @param id
     * @return {@code Customer }: khách hàng
     */
    public Customer getKhachHangById(String id) {
        Customer customer = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = new Customer(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return customer;
    }

    /**
     * Lấy ra danh sách khách hàng dựa theo tên khách hàng
     * @param name:Tên khách hàng cần tìm
     * @return {@code List<Customer> }:Danh sách khách hàng
     */
    public List<Customer> getListKhachHangByName(String name) {
        List<Customer> namelist = new ArrayList<Customer>();
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang where tenKhachHang like ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer c = new Customer(rs);
                namelist.add(c);
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
        return namelist;
    }

    /**
     * Lấy ra danh sách khách hàng dựa trên số điện thoại
     * @param sdt:Số điện thoại của khách hàng cần tìm
     * @return {@code List<Customer> }:Danh sách khách hàng
     */
    public List<Customer> getListKhachHangBySDT(String sdt) {
        List<Customer> sdtlist = new ArrayList<Customer>();
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang where soDienThoai like ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + sdt + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer c = new Customer(rs);
                sdtlist.add(c);
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
        return sdtlist;
    }

    /**
     * Lấy ra danh sách khách hàng dựa trên cccd
     * @param cccd:Căn cước công dân của khách hàng cần tìm
     * @return {@code List<Customer> }:Danh sách hách hàng
     */
    public List<Customer> getListKhachHangByCCCD(String cccd) {
        List<Customer> cccdlist = new ArrayList<Customer>();
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang where CCCD like ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + cccd + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer c = new Customer(rs);
                cccdlist.add(c);
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
        return cccdlist;
    }

    /**
     * Lấy ra khách hàng dựa trên số điện thoại
     * @param sdt:Số điện thoại của khách hàng cần tìm
     * @return {@code Customer }:Khách hàng
     */
    public  Customer getKhachHangBySDT(String sdt) {
       Customer c = null;
        ConnectDB.getInstance();
        PreparedStatement stmt = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang where soDienThoai = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, sdt);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
               c = new Customer(rs);
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
        return c;
    }

    /**
     * Tìm phiếu đặt phòng dựa trên tên khách hàng
     * @param customerName:Tên khách hàng cần tìm phiếu đặt phòng
     * @return {@code ArrayList<ReservationForm> }:Danh sách phiếu đặt phòng
     */
    public ArrayList<ReservationForm> findReservationFormsByCustomerName(String customerName) {
        ArrayList<ReservationForm> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getInstance().getConnection();
            String query = "SELECT pdp.*, kh.tenKhachHang, p.maPhong, lp.tenLoaiPhong " +
                    "FROM PhieuDatPhong pdp " +
                    "INNER JOIN KhachHang kh ON pdp.maKhachHang = kh.maKhachHang " +
                    "INNER JOIN Phong p ON pdp.maPhong = p.maPhong " +
                    "INNER JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
                    "WHERE kh.tenKhachHang LIKE ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + customerName + "%");

            rs = stmt.executeQuery();

            while (rs.next()) {
                ReservationForm reservationForm = new ReservationForm();
                reservationForm.setMaPhieuDat(rs.getString("maPhieuDat"));
                reservationForm.setThoiGianDat(rs.getTimestamp("thoiGianDat"));

                Customer customer = new Customer();
                customer.setTenKhachHang(rs.getString("tenKhachHang"));
                reservationForm.setMaKhachHang(customer);

                Room room = new Room();
                room.setMaPhong(rs.getString("maPhong"));
                reservationForm.setMaPhong(room);

                TypeOfRoom roomType = new TypeOfRoom();
                roomType.setTenLoaiPhong(rs.getString("tenLoaiPhong"));
                room.setLoaiPhong(roomType);

                result.add(reservationForm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * Lấy ra khách hàng dựa trên mã Hóa đơn
     * @param maHoaDon:Mã hóa đơn của khách hàng cần lấy
     * @return {@code Customer }:Khách hàng
     */
    public Customer getCustomerByBillId(String maHoaDon) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String query = "SELECT KhachHang.* " +
                "FROM KhachHang " +
                "INNER JOIN HoaDon ON KhachHang.maKhachHang = HoaDon.maKhachHang " +
                "WHERE HoaDon.maHoaDon = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, maHoaDon);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer(rs);
                    return customer;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Thêm khách hàng
     * @param kh:Khách hàng cần thêm
     * @return {@code boolean} :True or false
     * @throws SQLException
     */

    public boolean insert(Customer kh) throws SQLException {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            String sql = "insert into dbo.KhachHang (maKhachHang, tenKhachHang, soDienThoai, CCCD, gioiTinh,ngaySinh)" + "values (?,?,?,?,?,?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, kh.getMaKhachHang());
            statement.setString(2, kh.getTenKhachHang());
            statement.setString(3, kh.getSoDienThoai());
            statement.setString(4, kh.getCCCD());
            statement.setBoolean(5, kh.isGioiTinh());
            statement.setDate(6, kh.getNgaySinh());
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
     * Xóa khách hàng
     * @param kh:Khách hàng cần xóa
     * @return {@code boolean} :True or false
     */
    public boolean delete(Customer kh) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            String sql = "delete from dbo.KhachHang" + " where maKhachHang = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, kh.getMaKhachHang());
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }


}
