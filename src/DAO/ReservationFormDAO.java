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
                        new ReservationForm(rs.getString(1), rs.getTimestamp(2), rs.getTimestamp(3), new Staff(rs.getString(4)), new Customer(rs.getString(5)),new Room(rs.getString(6))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listForm;
    }

    public boolean addReservationForm(ReservationForm form) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "INSERT INTO PhieuDatPhong (maPhieuDat, thoiGianDat, thoiGianNhanPhong, maNhanVien, maKhachHang, maPhong) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            statement = con.prepareStatement(sql);
            statement.setString(1, form.getMaPhieuDat());
            statement.setTimestamp(2, form.getThoiGianDat());
            statement.setTimestamp(3, form.getThoiGianNhanPhong());
            statement.setString(4, form.getMaNhanVien().getMaNhanVien());
            statement.setString(5, form.getMaKhachHang().getMaKhachHang());
            statement.setString(6, form.getMaPhong().getMaPhong());

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
                        rs.getTimestamp("thoiGianNhanPhong"),
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
//    public ArrayList<ReservationForm> getListReservationFormByRoomId(String ma) {
//        ArrayList<ReservationForm> rsvfList = new ArrayList<ReservationForm>();
//        ConnectDB.getInstance();
//        Connection con = ConnectDB.getConnection();
//        String query =  "SELECT * FROM PhieuDatPhong where maPhong like ?";
//
//        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
//            preparedStatement.setString(1, "%" + ma + "%");
//
//            try (ResultSet rs = preparedStatement.executeQuery()) {
//                while (rs.next()) {
//                    ReservationForm rsvf;
//                    rsvf = new ReservationForm(rs);
//                    rsvfList.add(rsvf);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return rsvfList;
//    }
}
