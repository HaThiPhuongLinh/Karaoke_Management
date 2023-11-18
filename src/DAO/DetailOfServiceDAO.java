package DAO;

import ConnectDB.ConnectDB;
import Entity.*;

import java.sql.*;
//import java.sql.Date;
import java.util.*;
import java.util.Date;
/**
 * Lấy dữ liệu từ database cho lớp {@code DetailOfServiceDAO}
 * <p>
 * Người tham gia thiết kế: Hà Thị Phương Linh, Nguyễn Quang Duy, Nguyễn Đình Dương
 * <p>
 * Ngày tạo: 07/10/2023
 * <p>
 * Lần cập nhật cuối: 7/11/2023
 * <p>
 * Nội dung cập nhật: thêm javadoc
 */
public class DetailOfServiceDAO {
    private static DetailOfServiceDAO instance = new DetailOfServiceDAO();

    public static DetailOfServiceDAO getInstance() {
        return instance;
    }
    /**
     * lấy danh sách thông tin tất cả chi tiết dịch vụ
     * @return {@code ArrayList<DetailsOfService>}: danh sách chi tiết dịch vụ
     */
    public ArrayList<DetailsOfService> getAllDetailsOfService() {
        ArrayList<DetailsOfService> dsDOS = new ArrayList<DetailsOfService>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "Select * from dbo.ChiTietDichVu";
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

    /**
     * Lấy danh sách chi tiết dịch vụ trong khoản từ ngày bắt đầu đến ngày kết thúc
     * @param tuNgay ngày bắt đầu
     * @param denNgay ngày kết thúc
     * @return {@code ArrayList<DetailsOfService>} danh sách chi tiết dịch vụ
     */
    public ArrayList<DetailsOfService> getListCTDVByDate(Date tuNgay, Date denNgay) {
        ArrayList<DetailsOfService> dataList = new ArrayList<DetailsOfService>();
        ConnectDB.getInstance();
        PreparedStatement statement = null;
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "SELECT ct.* FROM ChiTietDichVu ct JOIN HoaDon hd ON ct.maHoaDon = hd.maHoaDon WHERE hd.ngayGioTra >= ? AND hd.ngayGioTra <= ?";
//            String sql = "SELECT ct.maDichVu, SUM(ct.soLuong) as soLuongBan FROM ChiTietDichVu ct JOIN HoaDon hd ON ct.maHoaDon = hd.maHoaDon WHERE hd.ngayGioTra >= ? AND hd.ngayGioTra <= ? GROUP BY ct.maDichVu";
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

    /**
     * Lấy danh sách dịch vụ theo mã phòng
     * @param roomId mã phòng
     * @return {@code ArrayList<DetailsOfService>} danh sách chi tiết dịch vụ
     */
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

    /**
     * Lấy danh sách chi tiết dịch vụ theo mã hóa đơn
     * @param billID mã hóa đơn
     * @return {@code ArrayList<DetailsOfService>} danh sách chi tiết dịch vụ
     */
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

    /**
     * Lấy chi tiết dịch vụ theo mã hóa đơn và mã dịch vụ
     * @param billId mã hóa đơn
     * @param serviceId mã dịch vụ
     * @return chi tiết dịch vụ được tìm thấy
     */
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

    /**
     * cập nhật chi tiết dịch vụ
     * @param detailsOfService chi tiết dịch vụ cần cập nhật
     * @param soLuongDat số lượng đặt
     * @param maHoaDon mã hóa đơn
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *          <ul>
     *              <li>Nếu cập nhật thành công thì trả về {@code true}</li>
     *              <li>Nếu cập nhật thất bại thì trả về {@code false}</li>
     *          </ul>
     */
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


    public ArrayList<DetailsOfService> getBillByServiceID(String ServiceID) {
        ArrayList<DetailsOfService> lst = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getInstance().getConnection();
            String query = "SELECT * FROM ChiTietDichVu WHERE maDichVu like ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, ServiceID);

            rs = stmt.executeQuery();

            while (rs.next()) {
                String maHoaDon = rs.getString("maHoaDon");
                String maDichVu = rs.getString("maDichVu");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("giaBan");

                Bill bill = new Bill(maHoaDon);
                Service service = new Service(maDichVu);
                DetailsOfService dos = new DetailsOfService(bill, service, soLuong, donGia);

                lst.add(dos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }
}