package DAO;

import ConnectDB.ConnectDB;
import Entity.Bill;
import Entity.DetailsOfService;
import Entity.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailOfServiceDAO {
    private static DetailOfServiceDAO instance = new DetailOfServiceDAO();

    public static DetailOfServiceDAO getInstance() {
        return instance;
    }

    public ArrayList<DetailsOfService> getAllDetailsOfService() {
        ArrayList<DetailsOfService> dsDOS = new ArrayList<DetailsOfService>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "Select * from dbo.CTDichVu";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Bill maHoaDon = new Bill(rs.getString(1));
                Service maDichVu = new Service(rs.getString(2));
                int soLuong = rs.getInt(3);
                double donGia = rs.getDouble(4);

                DetailsOfService dos = new DetailsOfService(maHoaDon, maDichVu, soLuong, donGia);

                dsDOS.add(dos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsDOS;
    }

    public ArrayList<DetailsOfService> getListCTDVByDate(Date tuNgay, Date denNgay) {
        ArrayList<DetailsOfService> dataList = new ArrayList<DetailsOfService>();
        ConnectDB.getInstance();
        PreparedStatement statement = null;
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "SELECT ct.* FROM ChiTietDichVu ct JOIN HoaDon hd ON ct.maHoaDon = hd.maHoaDon WHERE hd.ngayGioTra >= ? AND hd.ngayGioTra <= ?";
            statement = con.prepareStatement(sql);

            statement.setDate(1, (java.sql.Date) tuNgay);
            statement.setDate(2, (java.sql.Date) denNgay);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Bill maHoaDon = new Bill(rs.getString(1));
                Service maDichVu = new Service(rs.getString(2));
                int soLuong = rs.getInt(3);
                double donGia = rs.getDouble(4);

                DetailsOfService dos = new DetailsOfService(maHoaDon, maDichVu, soLuong, donGia);

                dataList.add(dos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public boolean addDetailOfService(DetailsOfService dos) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "INSERT INTO ChiTietDichVu (maHoaDon, maDichVu, soLuong, giaBan) VALUES (?, ?, ?, ?)";
            statement = con.prepareStatement(sql);

            statement.setString(1, dos.getMaHoaDon().getMaHoaDon());
            statement.setString(2, dos.getMaDichVu().getMaDichVu());
            statement.setInt(3, dos.getSoLuong());
            statement.setDouble(4, dos.getGiaBan());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return true;
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

    public ArrayList<DetailsOfService> getDetailsOfServiceListByRoomId(String roomId) {
        ArrayList<DetailsOfService> dsDOS = new ArrayList<DetailsOfService>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "SELECT ctdv.soLuong, ctdv.giaBan, dv.maDichVu, dv.giaBan, dv.soLuongTon, dv.tenDichVu, " +
                    "ldv.maLoaiDichVu, ldv.tenLoaiDichVu, hd.maHoaDon, hd.ngayGioDat," +
                    " hd.ngayGioTra, hd.maKhachHang, hd.maNhanVien, hd.tinhTrangHD, hd.maPhong " +
                    "FROM dbo.ChiTietDichVu ctdv, dbo.HoaDon hd, dbo.DichVu dv, dbo.LoaiDichVu ldv " +
                    "WHERE ctdv.maHoaDon = hd.maHoaDon AND hd.maPhong = ? AND ctdv.maDichVu = dv.maDichVu" +
                    " AND dv.maLoaiDichVu = ldv.maLoaiDichVu AND hd.tinhTrangHD = 0";

            statement = con.prepareStatement(sql);
            statement.setString(1, roomId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Bill maHoaDon = new Bill(rs.getString("maHoaDon"));
                Service maDichVu = new Service(rs.getString("maDichVu"));
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("giaBan");

                DetailsOfService dos = new DetailsOfService(maHoaDon, maDichVu, soLuong, donGia);

                dsDOS.add(dos);
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

        return dsDOS;
    }

    public ArrayList<DetailsOfService> getDetailsOfServiceForBill(String billID) {
        ArrayList<DetailsOfService> dataList = new ArrayList<DetailsOfService>();
        ConnectDB.getInstance();
        PreparedStatement statement = null;
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "SELECT * FROM ChiTietDichVu WHERE maHoaDon = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, billID);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String maHoaDon = rs.getString("maHoaDon");
                String maDichVu = rs.getString("maDichVu");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("giaBan");

                Bill bill = new Bill(maHoaDon);
                Service service = new Service(maDichVu);
                DetailsOfService dos = new DetailsOfService(bill, service, soLuong, donGia);

                dataList.add(dos);
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

        return dataList;
    }


    public DetailsOfService getDetailsOfServiceByBillIdAndServiceId(String billId, String serviceId) {
        DetailsOfService dos = null;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "SELECT TOP 1 ctdv.soLuong, ctdv.giaBan, ctdv.maHoaDon," +
                    "dv.maDichVu, dv.giaBan, dv.soLuongTon, dv.tenDichVu," +
                    "ldv.maLoaiDichVu, ldv.tenLoaiDichVu," +
                    "hd.maHoaDon, hd.ngayGioDat, hd.ngayGioTra, " +
                    "hd.maKhachHang, hd.maNhanVien, hd.tinhTrangHD, hd.maPhong " +
                    "FROM dbo.ChiTietDichVu ctdv, dbo.HoaDon hd, dbo.DichVu dv, dbo.LoaiDichVu ldv, dbo.Phong p " +
                    "WHERE ctdv.maHoaDon = hd.maHoaDon AND ctdv.maDichVu = dv.maDichVu " +
                    "AND dv.maLoaiDichVu = ldv.maLoaiDichVu AND hd.maPhong = p.maPhong " +
                    "AND hd.maHoaDon = ? AND dv.maDichVu = ?";

            statement = con.prepareStatement(sql);
            statement.setString(1, billId);
            statement.setString(2, serviceId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Bill maHoaDon = new Bill(rs.getString("maHoaDon"));
                Service maDichVu = new Service(rs.getString("maDichVu"));
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("giaBan");

                dos = new DetailsOfService(maHoaDon, maDichVu, soLuong, donGia);
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

        return dos;
    }

    public boolean updateServiceDetail(DetailsOfService detailsOfService, int soLuongDat, String maHoaDon) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            String checkPaymentStatusSQL = "SELECT tinhTrangHD FROM dbo.HoaDon WHERE maHoaDon = ?";
            statement = con.prepareStatement(checkPaymentStatusSQL);
            statement.setString(1, maHoaDon);
            ResultSet paymentResult = statement.executeQuery();

            if (paymentResult.next()) {
                int isPayment = paymentResult.getInt("tinhTrangHD");
                if (isPayment == 0) {
                    String getQuantityInStockSQL = "SELECT soLuongTon FROM dbo.DichVu WHERE maDichVu = ?";
                    statement = con.prepareStatement(getQuantityInStockSQL);
                    statement.setString(1, detailsOfService.getMaDichVu().getMaDichVu());
                    ResultSet quantityInStockResult = statement.executeQuery();

                    int quantityInStock = 0;
                    if (quantityInStockResult.next()) {
                        quantityInStock = quantityInStockResult.getInt("soLuongTon");
                    }

                    String getExistingServiceDetailSQL = "SELECT maHoaDon, soLuong FROM dbo.ChiTietDichVu " +
                            "WHERE maHoaDon = ? AND maDichVu = ?";
                    statement = con.prepareStatement(getExistingServiceDetailSQL);
                    statement.setString(1, maHoaDon);
                    statement.setString(2, detailsOfService.getMaDichVu().getMaDichVu());
                    ResultSet existingServiceDetailResult = statement.executeQuery();

                    if (existingServiceDetailResult.next()) {
                        String existingBillId = existingServiceDetailResult.getString("maHoaDon");
                        int oldQuantity = existingServiceDetailResult.getInt("soLuong");
                        int newQuantity = soLuongDat + oldQuantity;

                        if (newQuantity > 0) {
                            String updateServiceDetailSQL = "UPDATE dbo.ChiTietDichVu SET soLuong = ? WHERE maHoaDon = ? AND maDichVu = ?";
                            statement = con.prepareStatement(updateServiceDetailSQL);
                            statement.setInt(1, newQuantity);
                            statement.setString(2, maHoaDon);
                            statement.setString(3, detailsOfService.getMaDichVu().getMaDichVu());
                            statement.executeUpdate();
                        } else {
                            String deleteServiceDetailSQL = "DELETE FROM dbo.ChiTietDichVu WHERE maHoaDon = ? AND maDichVu = ?";
                            statement = con.prepareStatement(deleteServiceDetailSQL);
                            statement.setString(1, maHoaDon);
                            statement.setString(2, detailsOfService.getMaDichVu().getMaDichVu());
                            statement.executeUpdate();
                        }

                        String updateQuantityInStockSQL = "UPDATE dbo.DichVu SET soLuongTon = ? WHERE maDichVu = ?";
                        statement = con.prepareStatement(updateQuantityInStockSQL);
                        statement.setInt(1, quantityInStock - soLuongDat);
                        statement.setString(2, detailsOfService.getMaDichVu().getMaDichVu());
                        statement.executeUpdate();
                    } else {
                        String insertServiceDetailSQL = "INSERT INTO dbo.ChiTietDichVu (maHoaDon, maDichVu, soLuong, giaBan) " +
                                "VALUES (?, ?, ?, ?)";
                        statement = con.prepareStatement(insertServiceDetailSQL);
                        statement.setString(1, maHoaDon);
                        statement.setString(2, detailsOfService.getMaDichVu().getMaDichVu());
                        statement.setInt(3, soLuongDat);
                        statement.setDouble(4, detailsOfService.getGiaBan());
                        statement.executeUpdate();

                        String updateQuantityInStockSQL = "UPDATE dbo.DichVu SET soLuongTon = ? WHERE maDichVu = ?";
                        statement = con.prepareStatement(updateQuantityInStockSQL);
                        statement.setInt(1, quantityInStock - soLuongDat);
                        statement.setString(2, detailsOfService.getMaDichVu().getMaDichVu());
                        statement.executeUpdate();
                    }

                    return true;
                }
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

}
