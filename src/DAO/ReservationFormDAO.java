package DAO;

import ConnectDB.ConnectDB;
import Entity.Customer;
import Entity.ReservationForm;
import Entity.Room;
import Entity.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                        new ReservationForm(rs.getString(1), rs.getTimestamp(2), new Staff(rs.getString(3)), new Customer(rs.getString(4)),new Room(rs.getString(5))));
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
            String sql = "INSERT INTO PhieuDatPhong (maPhieuDat, thoiGianDat, maNhanVien, maKhachHang, maPhong) " +
                    "VALUES (?, ?, ?, ?, ?)";

            statement = con.prepareStatement(sql);
            statement.setString(1, form.getMaPhieuDat());
            statement.setTimestamp(2, form.getThoiGianDat());
            statement.setString(3, form.getMaNhanVien().getMaNhanVien());
            statement.setString(4, form.getMaKhachHang().getMaKhachHang());
            statement.setString(5, form.getMaPhong().getMaPhong());

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
     * @return true/false
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
     * @param roomID
     * @return
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
                        rs.getTimestamp("thoiGianDat"),
                        new Staff(rs.getString("maNhanVien")),
                        new Customer(rs.getString("maKhachHang")),
                        new Room(rs.getString("maPhong"))
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
                        rs.getTimestamp("thoiGianDat"),
                        new Staff(rs.getString("maNhanVien")),
                        new Customer(rs.getString("maKhachHang")),
                        new Room(rs.getString("maPhong"))
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

    public ReservationForm getFormByRoomID(String roomID) {
        ReservationForm reservationForm = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT TOP 1 * FROM PhieuDatPhong WHERE maPhong = ? ORDER BY thoiGianDat DESC";
            statement = con.prepareStatement(sql);
            statement.setString(1, roomID);
            rs = statement.executeQuery();

            if (rs.next()) {
                reservationForm = new ReservationForm(
                        rs.getString("maPhieuDat"),
                        rs.getTimestamp("thoiGianDat"),
                        new Staff(rs.getString("maNhanVien")),
                        new Customer(rs.getString("maKhachHang")),
                        new Room(rs.getString("maPhong"))
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

    public ArrayList<ReservationForm> getReservationsByRoomID(String roomID) {
        ArrayList<ReservationForm> reservations = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getInstance().getConnection();
            String query = "SELECT * FROM PhieuDatPhong WHERE maPhong = ? ORDER BY thoiGianDat ASC;";
            stmt = con.prepareStatement(query);
            stmt.setString(1, roomID);

            rs = stmt.executeQuery();

            while (rs.next()) {
                ReservationForm reservation = new ReservationForm(
                        rs.getString("maPhieuDat"),
                        rs.getTimestamp("thoiGianDat"),
                        new Staff(rs.getString("maNhanVien")),
                        new Customer(rs.getString("maKhachHang")),
                        new Room(rs.getString("maPhong"))
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

}
