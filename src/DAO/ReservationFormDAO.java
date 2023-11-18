package DAO;

import ConnectDB.ConnectDB;
import Entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Người tham gia thiết kế: Hà Thị Phương Linh
 * <p>
 * Ngày tạo: 24/09/2023
 * <p>
 * Lần cập nhật cuối: 18/11/2023
 * <p>
 * Nội dung cập nhật: cập nhật chức năng tìm phiếu đang chờ (getReservationsByRoomID)
 */
public class ReservationFormDAO {

    /**
     * Lấy ra toàn bộ phiếu đặt phòng
     * @return  List<ReservationForm>
     */
    public List<ReservationForm> getAllForm() {
        List<ReservationForm> listForm = new ArrayList<ReservationForm>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "Select * from PhieuDatPhong";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                listForm.add(
                        new ReservationForm(rs.getString(1), rs.getTimestamp(2), rs.getTimestamp(3), new Staff(rs.getString(4)), new Customer(rs.getString(5)),new Room(rs.getString(6)), rs.getInt(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listForm;
    }

    /**
     * Thêm phiếu đặt phòng
     * @param form: phiếu đặt phòng
     * @return true/false
     */
    public boolean addReservationForm(ReservationForm form) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "INSERT INTO PhieuDatPhong (maPhieuDat, thoiGianGoi, thoiGianDat, maNhanVien, maKhachHang, maPhong, trangThai) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            statement = con.prepareStatement(sql);
            statement.setString(1, form.getMaPhieuDat());
            statement.setTimestamp(2, form.getThoiGianGoi());
            statement.setTimestamp(3, form.getThoiGianDat());
            statement.setString(4, form.getMaNhanVien().getMaNhanVien());
            statement.setString(5, form.getMaKhachHang().getMaKhachHang());
            statement.setString(6, form.getMaPhong().getMaPhong());
            statement.setInt(7, form.getTrangThai());

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Xóa phiếu đặt phòng thông qua mã phiếu
     * @param maPhieuDat: mã phiếu đặt
     * @return {@code boolean}: true/false
     */
    public boolean deleteReservationForm(String maPhieuDat) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectDB.getInstance().getConnection();
            String query = "DELETE FROM PhieuDatPhong WHERE maPhieuDat = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, maPhieuDat);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return true;
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

        return false;
    }

    /**
     * Lấy ra phiếu đặt phòng bằng mã phòng
     * @param roomID: mã phòng
     * @return {@code ReservationForm}: phiếu đặt phòng
     */
    public ReservationForm getReservationFormByRoomId(String roomID) {
        ReservationForm reservationForm = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM PhieuDatPhong WHERE maPhong = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, roomID);
            rs = statement.executeQuery();

            if (rs.next()) {
                reservationForm = new ReservationForm(
                        rs.getString("maPhieuDat"),
                        rs.getTimestamp("thoiGianGoi"),
                        rs.getTimestamp("thoiGianDat"),
                        new Staff(rs.getString("maNhanVien")),
                        new Customer(rs.getString("maKhachHang")),
                        new Room(rs.getString("maPhong")),
                        rs.getInt("trangThai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return reservationForm;
    }

    /**
     * Lấy ra phiếu đặt phòng bằng mã phiếu đặt
     * @param formID: mã phiếu đặt
     * @return {@code ReservationForm}: phiếu đặt phòng
     */
    public ReservationForm getReservationFormByFormId(String formID) {
        ReservationForm reservationForm = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM PhieuDatPhong WHERE maPhieuDat = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, formID);
            rs = statement.executeQuery();

            if (rs.next()) {
                reservationForm = new ReservationForm(
                        rs.getString("maPhieuDat"),
                        rs.getTimestamp("thoiGianGoi"),
                        rs.getTimestamp("thoiGianDat"),
                        new Staff(rs.getString("maNhanVien")),
                        new Customer(rs.getString("maKhachHang")),
                        new Room(rs.getString("maPhong")),
                        rs.getInt("trangThai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return reservationForm;
    }

    /**
     * Phát sinh mã phiếu đặt phòng ngẫu nhiên PDP+xxxx (x++)
     * @return String
     */
    public String generateNextFormId() {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String nextId = null;
        try {
            String sql = "SELECT TOP 1 maPhieuDat FROM PhieuDatPhong ORDER BY maPhieuDat DESC";
            statement = con.prepareStatement(sql);
            rs = statement.executeQuery();

            if (rs.next()) {
                String lastServiceId = rs.getString("maPhieuDat");
                String numericPart = lastServiceId.substring(4); // Lấy phần số từ mã dịch vụ cuối cùng
                int counter = Integer.parseInt(numericPart) + 1; // Tăng giá trị số lên 1
                nextId = "PDP" + String.format("%04d", counter); // Định dạng lại giá trị số thành chuỗi 3 chữ số, sau đó ghép với tiền tố "PDP"
            } else {
                nextId = "PDP0001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextId;
    }

    /**
     * Lấy ra danh sách phiếu đặt phòng
     * @param roomID: mã phòng
     * @return {@code ArrayList<Bill>}: danh sách phiếu đặt phòng
     */
    public ArrayList<ReservationForm> getReservationsByRoomID(String roomID) {
        ArrayList<ReservationForm> reservations = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getInstance().getConnection();
            String query = "SELECT * FROM PhieuDatPhong WHERE maPhong = ? AND trangThai = 2;";
            stmt = con.prepareStatement(query);
            stmt.setString(1, roomID);

            rs = stmt.executeQuery();

            while (rs.next()) {
                ReservationForm reservation = new ReservationForm(
                        rs.getString("maPhieuDat"),
                        rs.getTimestamp("thoiGianGoi"),
                        rs.getTimestamp("thoiGianDat"),
                        new Staff(rs.getString("maNhanVien")),
                        new Customer(rs.getString("maKhachHang")),
                        new Room(rs.getString("maPhong")),
                        rs.getInt("trangThai")
                );
                reservations.add(reservation);
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
        return reservations;
    }

    /**
     * Cập nhật hiệu lực và trạng thái của phiếu đặt phòng
     * @param formID: mã phiếu đặt
     * @param trangThai: giá trị trạng thái mới (0 - Đã hủy, 1 - Đã nhận, 2 - Đang chờ)
     * @return {@code boolean}: true/false
     */
    public boolean updateReservationStatus(String formID,  int trangThai) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectDB.getInstance().getConnection();
            String query = "UPDATE PhieuDatPhong SET trangThai = ? WHERE maPhieuDat = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, trangThai);
            stmt.setString(2, formID);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
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

        return false;
    }

    /**
     * Kiểm tra xem có phiếu đặt nào cho phòng có trạng thái khác 0 hay không
     * @param roomID: mã phòng
     * @return (@boolean): true/false
     */
    public boolean hasActiveReservationsForRoom(String roomID) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "SELECT COUNT(*) AS count FROM PhieuDatPhong WHERE maPhong = ? AND trangThai NOT IN (0, 1);";
            statement = con.prepareStatement(sql);
            statement.setString(1, roomID);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Lấy ra danh sách phiếu đặt phòng có trạng thái khác 0 của một phòng
     * @param roomID: mã phòng
     * @return {@code ArrayList<ReservationForm>}: danh sách phiếu đặt phòng có trạng thái khác 0
     */
    public ArrayList<ReservationForm> getActiveReservationsForRoom(String roomID) {
        ArrayList<ReservationForm> activeReservations = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getInstance().getConnection();
            String query = "SELECT * FROM PhieuDatPhong WHERE maPhong = ? AND trangThai <> 0;";
            stmt = con.prepareStatement(query);
            stmt.setString(1, roomID);

            rs = stmt.executeQuery();

            while (rs.next()) {
                ReservationForm reservation = new ReservationForm(
                        rs.getString("maPhieuDat"),
                        rs.getTimestamp("thoiGianGoi"),
                        rs.getTimestamp("thoiGianDat"),
                        new Staff(rs.getString("maNhanVien")),
                        new Customer(rs.getString("maKhachHang")),
                        new Room(rs.getString("maPhong")),
                        rs.getInt("trangThai")
                );
                activeReservations.add(reservation);
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
        return activeReservations;
    }

    /**
     * Tìm phiếu đặt phòng dựa trên trạng thái
     * @param status: Trạng thái cần tìm kiếm (0 - Đã hủy, 1 - Đã nhận, 2 - Đang chờ)
     * @return {@code ArrayList<ReservationForm> }:Danh sách phiếu đặt phòng
     */
    public ArrayList<ReservationForm> findReservationFormsByStatus(int status) {
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
                    "WHERE pdp.trangThai = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, status);

            rs = stmt.executeQuery();

            while (rs.next()) {
                ReservationForm reservationForm = new ReservationForm();
                reservationForm.setMaPhieuDat(rs.getString("maPhieuDat"));
                reservationForm.setThoiGianDat(rs.getTimestamp("thoiGianDat"));
                reservationForm.setThoiGianGoi(rs.getTimestamp("thoiGianGoi"));
                reservationForm.setTrangThai(rs.getInt("trangThai"));

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

}
