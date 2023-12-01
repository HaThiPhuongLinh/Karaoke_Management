package UI.component.Dialog;

import Entity.Staff;
import UI.CustomUI.Custom;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.tree.*;


/**
 * Giao diện hỗ trợ sử dụng ứng dụng
 * Người tham gia thiết kế: Hà Thị Phương Linh
 * Ngày tạo: 27/11/2023
 */
public class Support extends JDialog implements MouseListener {
    private JTree tree;
    private JScrollPane sc;
    private JPanel pnTree;
    private JTextPane txtArea;
    private DefaultMutableTreeNode nodeTimKiemNhanVien, nodeCapNhatNhanVien, rootHDSD, nodeTimKiemKhachHang, nodeCapNhatKhachHang, nodeTimKiemPhong;
    private DefaultMutableTreeNode nodeCapNhatDichVu, nodeCapNhatPhong, nodeLoaiPhong, nodeLoaiDichvu, nodeTimKiemHoaDon, nodeDatPhong, nodeDatDichVu, nodeLapHoaDon;
    private DefaultMutableTreeNode nodeThongKeDoanhThu, nodeThongKeDichVu, nodeThongKeKhachHang, nodeTimKiemDichVu;
    private final String MANAGER = "Quản lý";
    private Staff staffLogin;
    private String pathImages = Custom.pathImages;

    public Support(Staff staff) {
        this.staffLogin = staff;
        setTitle("Support");
        setSize(1536, 818);
        //setIconImage(logoApp.getImage());
        setLocationRelativeTo(null);
        setResizable(false);
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JPanel pnNorth = new JPanel();
        pnNorth.setBackground(Color.decode("#5F009D"));
        JLabel lblTaiLieu = new JLabel("Hướng Dẫn Sử Dụng Ứng Dụng");
        lblTaiLieu.setFont(new Font("Tahoma", Font.BOLD, 24));
        pnNorth.add(lblTaiLieu);

        rootHDSD = new DefaultMutableTreeNode("Hướng dẫn sử dụng");
        tree = new JTree(rootHDSD);
        tree.setFont(new Font("Tahoma", Font.BOLD, 15));
        sc = new JScrollPane(tree);
        sc.setPreferredSize(new Dimension(220, 0));

        pnTree = new JPanel();
        pnTree.setLayout(new BorderLayout());

        ImageIcon leafIcon = new ImageIcon();

        if (leafIcon != null) {
            DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
            renderer.setLeafIcon(leafIcon);
            tree.setCellRenderer(renderer);
        }

        nodeTimKiemKhachHang = new DefaultMutableTreeNode("Tìm Kiếm Khách Hàng");
        nodeCapNhatKhachHang = new DefaultMutableTreeNode("Cập Nhật Khách Hàng");
        nodeTimKiemNhanVien = new DefaultMutableTreeNode("Tìm kiếm Nhân Viên");
        nodeCapNhatNhanVien = new DefaultMutableTreeNode("Cập Nhật Nhân Viên");
        nodeTimKiemPhong = new DefaultMutableTreeNode("Tìm kiếm phòng");
        nodeCapNhatPhong = new DefaultMutableTreeNode("Cập Nhật Phòng");
        nodeLoaiPhong = new DefaultMutableTreeNode("Loại Phòng");
        nodeDatPhong = new DefaultMutableTreeNode("Đặt Phòng");
        nodeTimKiemDichVu = new DefaultMutableTreeNode("Tìm Kiếm Dịch Vụ");
        nodeCapNhatDichVu = new DefaultMutableTreeNode("Cập Nhật Dịch Vụ");
        nodeLoaiDichvu = new DefaultMutableTreeNode("Loại Dịch Vụ");
        nodeDatDichVu = new DefaultMutableTreeNode("Đặt Dịch Vụ");
        nodeLapHoaDon = new DefaultMutableTreeNode("Lập Hóa Đơn");
        nodeTimKiemHoaDon = new DefaultMutableTreeNode("Xem Hóa Đơn");
        nodeThongKeDoanhThu = new DefaultMutableTreeNode("Thống Kê Doanh Thu");
        nodeThongKeDichVu = new DefaultMutableTreeNode("Thống Kê Dịch Vụ");
        nodeThongKeKhachHang = new DefaultMutableTreeNode("Thống Kê Khách Hàng");


        if (staffLogin.getChucVu().equalsIgnoreCase(MANAGER)) {
            rootHDSD.add(nodeTimKiemNhanVien);
            rootHDSD.add(nodeCapNhatNhanVien);
            rootHDSD.add(nodeCapNhatPhong);
            rootHDSD.add(nodeLoaiPhong);
            rootHDSD.add(nodeCapNhatDichVu);
            rootHDSD.add(nodeLoaiDichvu);
            rootHDSD.add(nodeTimKiemHoaDon);
            rootHDSD.add(nodeThongKeDoanhThu);
            rootHDSD.add(nodeThongKeDichVu);
            rootHDSD.add(nodeThongKeKhachHang);
        }

        rootHDSD.add(nodeTimKiemKhachHang);
        rootHDSD.add(nodeCapNhatKhachHang);
        rootHDSD.add(nodeTimKiemPhong);
        rootHDSD.add(nodeDatPhong);
        rootHDSD.add(nodeTimKiemDichVu);
        rootHDSD.add(nodeDatDichVu);
        rootHDSD.add(nodeLapHoaDon);

        tree.setRowHeight(35);
        tree.expandRow(0);
        pnTree.add(sc, BorderLayout.WEST);

        txtArea = new JTextPane();
        txtArea.setEditable(false);
        txtArea.setContentType("text/html");
        txtArea.setEditable(false);
        txtArea.setBackground(Color.decode("#D7FFF6"));
        String html = "<html>" + "<div style='text-align:left;'>" + "<div style='text-align:center;'>"
                + "<img style='text-align:center;' src='"
                + getClass().getResource(pathImages + "Logo_IUH.png").toString() + "'/>" + "</div>" + "<hr>"
                + "<h1 style='text-align:center; font-size:22px '>PHẦN MỀM QUẢN LÝ QUÁN KARAOKE - NHÓM 05</h1>"
                + "<hr>" + "<p style='text-align:center; font-size:18px;'>TÀI LIỆU HƯỚNG DẪN SỬ DỤNG</p>"
                + "<p style='text-align:center; font-size:17px;'><b>NỘI DUNG</b></p>" + getPHTML(0)
                + "<b>1.	GIỚI THIỆU ỨNG DỤNG </b></p>" + getPHTML(1)
                + "&#32&#32&#32&#32&#32Phần mềm quản lý quán Karaoke  không chỉ tập trung vào việc giúp nhân viên thực hiện các công việc cơ bản như thêm, xóa, sửa thông tin mà còn đặt trọng tâm vào những khía cạnh quan trọng như đặt phòng, quản lý hóa đơn, và theo dõi doanh thu. Điều này không chỉ giúp giảm áp lực công việc cho nhân viên mà còn tạo ra cơ hội để quán karaoke tối ưu hóa nguồn lực và tăng cường chất lượng phục vụ.</p>"
                + getPHTML(1)
                + "&#32&#32&#32&#32&#32Với tính năng linh hoạt, phần mềm hỗ trợ nhân viên thu ngân trong việc quản lý đặt và trả phòng, tìm kiếm thông tin khách hàng, và thậm chí chuyển phòng một cách thuận tiện. Ngoài ra, phần mềm còn giúp quản lý nhân sự bằng cách quản lý thông tin nhân viên, tạo điều kiện thuận lợi cho quá trình quản lý nhân sự.</p>"
                + getPHTML(1)
                + "&#32&#32&#32&#32&#32Không chỉ đơn thuần là một công cụ quản lý, phần mềm quản lý quán karaoke còn là một trợ lý đắc lực cho quán karaoke trong việc xây dựng và duy trì mối quan hệ với khách hàng. Tính năng thống giảm giá trong ngày sinh nhật khách hàng giúp áp dụng ưu đãi linh hoạt và tạo ra các chương trình khuyến mãi hấp dẫn</p>"
                + getPHTML(1)
                + "&#32&#32&#32&#32&#32Cuối cùng, với phần mềm này, mọi góc nhìn của quản lý và nhân viên trở nên rõ ràng hơn, giúp họ có cái nhìn toàn diện về hoạt động kinh doanh. Đây không chỉ là một công cụ quản lý mà còn là một bước tiến vững chắc để nâng cao chất lượng phục vụ và trải nghiệm của khách hàng trong không gian giải trí karaoke.</p>"
                + getPHTML(0) + "<b>2.	CẤU HÌNH PHẦN CỨNG - PHẦN MỀM </b></p>" + getPHTML(1)
                + "<b>2.1	Phần cứng</b></p>" + getPHTML(2) + "-&#32&#32 200MB không gian trống trên ổ đĩa.</p>"
                + getPHTML(2) + "-&#32&#32 Trống 512 MB RAM.</p>" + getPHTML(2)
                + "-&#32&#32 Bộ vi xử lý với xung nhịp 4 GHz hoặc cao hơn.</p>" + getPHTML(2)
                + "-&#32&#32 2 GB RAM (Hệ điều hành 64-bit).</p>" + getPHTML(2)
                + "-&#32&#32 Card đồ hoạ: NVIDIA GTX 1050 8 GB / AMD HD7870 4 GB.</p>" + getPHTML(1)
                + "<b>2.1	Phần mềm</b></p>" + getPHTML(2) + "-&#32&#32 SQL Server 2022</p>" + getPHTML(2)
                + "-&#32&#32 Hệ điều hành: Win 10 trở lên (64-bit).</p>" + getPHTML(2) + "-&#32&#32 Java 8.</p>"
                + "</div>" + "</html>";
        txtArea.setText(html);
        JScrollPane sc1 = new JScrollPane(txtArea);
        sc1.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                txtArea.select(0, 0);
            }
        });
        pnTree.add(sc1, BorderLayout.CENTER);
        tree.addMouseListener(this);

        this.add(pnNorth, BorderLayout.NORTH);
        this.add(pnTree);

    }

    public ImageIcon ResizeImageIcon(URL url) {
        ImageIcon MyImage = new ImageIcon(url);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        DefaultMutableTreeNode nodeSelected = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (e.getSource().equals(tree)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<div style='text-align:left;'>" + "<div style='text-align:center;'>"
                    + "<img style='text-align:center;' src='"
                    + getClass().getResource(pathImages + "Logo_IUH.png").toString() + "'/>" + "</div>" + "<hr>"
                    + "<h1 style='text-align:center; font-size:22px '>PHẦN MỀM QUẢN LÝ QUÁN KARAOKE - NHÓM 05</h1>"
                    + "<hr>" + "<p style='text-align:center; font-size:18px;'>TÀI LIỆU HƯỚNG DẪN SỬ DỤNG</p>"
                    + "<p style='text-align:center; font-size:17px;'><b>NỘI DUNG</b></p>" + getPHTML(0)
                    + "<b>1.	GIỚI THIỆU ỨNG DỤNG </b></p>" + getPHTML(1)
                    + "&#32&#32&#32&#32&#32Phần mềm quản lý quán Karaoke  không chỉ tập trung vào việc giúp nhân viên thực hiện các công việc cơ bản như thêm, xóa, sửa thông tin mà còn đặt trọng tâm vào những khía cạnh quan trọng như đặt phòng, quản lý hóa đơn, và theo dõi doanh thu. Điều này không chỉ giúp giảm áp lực công việc cho nhân viên mà còn tạo ra cơ hội để quán karaoke tối ưu hóa nguồn lực và tăng cường chất lượng phục vụ.</p>"
                    + getPHTML(1)
                    + "&#32&#32&#32&#32&#32Với tính năng linh hoạt, phần mềm hỗ trợ nhân viên thu ngân trong việc quản lý đặt và trả phòng, tìm kiếm thông tin khách hàng, và thậm chí chuyển phòng một cách thuận tiện. Ngoài ra, phần mềm còn giúp quản lý nhân sự bằng cách quản lý thông tin nhân viên, tạo điều kiện thuận lợi cho quá trình quản lý nhân sự.</p>"
                    + getPHTML(1)
                    + "&#32&#32&#32&#32&#32Không chỉ đơn thuần là một công cụ quản lý, phần mềm quản lý quán karaoke còn là một trợ lý đắc lực cho quán karaoke trong việc xây dựng và duy trì mối quan hệ với khách hàng. Tính năng thống giảm giá trong ngày sinh nhật khách hàng giúp áp dụng ưu đãi linh hoạt và tạo ra các chương trình khuyến mãi hấp dẫn</p>"
                    + getPHTML(1)
                    + "&#32&#32&#32&#32&#32Cuối cùng, với phần mềm này, mọi góc nhìn của quản lý và nhân viên trở nên rõ ràng hơn, giúp họ có cái nhìn toàn diện về hoạt động kinh doanh. Đây không chỉ là một công cụ quản lý mà còn là một bước tiến vững chắc để nâng cao chất lượng phục vụ và trải nghiệm của khách hàng trong không gian giải trí karaoke.</p>"
                    + getPHTML(0) + "<b>2.	CẤU HÌNH PHẦN CỨNG - PHẦN MỀM </b></p>" + getPHTML(1)
                    + "<b>2.1	Phần cứng</b></p>" + getPHTML(2) + "-&#32&#32 200MB không gian trống trên ổ đĩa.</p>"
                    + getPHTML(2) + "-&#32&#32 Trống 512 MB RAM.</p>" + getPHTML(2)
                    + "-&#32&#32 Bộ vi xử lý với xung nhịp 4 GHz hoặc cao hơn.</p>" + getPHTML(2)
                    + "-&#32&#32 2 GB RAM (Hệ điều hành 64-bit).</p>" + getPHTML(2)
                    + "-&#32&#32 Card đồ hoạ: NVIDIA GTX 1050 8 GB / AMD HD7870 4 GB.</p>" + getPHTML(1)
                    + "<b>2.1	Phần mềm</b></p>" + getPHTML(2) + "-&#32&#32 SQL Server 2022</p>" + getPHTML(2)
                    + "-&#32&#32 Hệ điều hành: Win 10 trở lên (64-bit).</p>" + getPHTML(2) + "-&#32&#32 Java 8.</p>"
                    + "</div>" + "</html>";
            txtArea.setText(html);
        }  if (nodeSelected.equals(nodeDatPhong)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Đặt phòng</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Đặt phòng” trên menu.</p>" + getImageHTML("image011.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Chọn phòng cần đặt trong danh sách phòng.</p>" + getImageHTML("image013.png") + getPHTML(1)
                    + "<b>Bước 3: </b>Chọn nút “+” để hiển thị danh sách khách hàng.</p>" + getImageHTML("image015.png") + getPHTML(1)
                    + "<b>Bước 4: </b>Chọn khách hàng cần đặt phòng và nhấn nút chọn.</p>" + getImageHTML("image017.png") + getPHTML(1)
                    + "<b>Bước 5: </b>Nhấn nút “Đặt phòng”.</p>" + getImageHTML("image019.png") + getPHTML(1)
                    + "<b>Bước 6: </b>CHoàn tất, hiển thị thông báo “Cho thuê phòng thành công”.</p>" + getImageHTML("image021.png") + getPHTML(1)
                    + "</div>" + "</html>";
            txtArea.setText(html);
        } else if (nodeSelected.equals(nodeDatDichVu)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Đặt dịch vụ</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Bước 1. Chọn chức năng “Đặt dịch vụ” trên menu.</p>" + getImageHTML("image078.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Chọn phòng muốn đặt dịch vụ.</p>" + getImageHTML("image080.png") + getPHTML(1)
                    + "<b>Bước 3: </b>Chọn dịch vụ muốn sử dụng và điều chỉnh số lượng.</p>" + getImageHTML("image082.png") + getPHTML(1)
                    + "<b>Bước 4: </b>Chọn nút “Thêm”.</p>" + getImageHTML("image084.png") + getPHTML(1)
                    + "<b>Bước 5: </b>Hoàn tất, dịch vụ được thêm vào phòng.</p>" + getImageHTML("image086.png") + getPHTML(1)
                    + "</div>" + "</html>";
            txtArea.setText(html);
        } else if (nodeSelected.equals(nodeLapHoaDon)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Lập hóa đơn</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Lập hóa đơn” trên menu.</p>" + getImageHTML("image104.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Chọn phòng cần lập hóa đơn, kiểm tra chi tiết dịch vụ được phòng sử dụng.</p>" + getImageHTML("image106.png") + getPHTML(1)
                    + "<b>Bước 3: </b>Chọn nút “Lập hóa đơn”.</p>" + getImageHTML("image108.png") + getPHTML(1)
                    + "<b>Bước 4: </b>Hóa đơn thanh toán được hiển thị. Xác nhận thông tin với khách hàng.</p>" + getImageHTML("image110.png") + getPHTML(1)
                    + "<b>Bước 5: </b>Chọn nút “Thanh toán”.</p>" + getImageHTML("image112.png") + getPHTML(1)
                    + "<b>Bước 6: </b>Hoàn tất, hiển thị thông báo “Thanh toán hóa đơn thành công”. Hiển thị hóa đơn dạng pdf.</p>" + getImageHTML("image114.png") + getPHTML(1)
                    + getImageHTML("image116.png") + getPHTML(1)
                    + "</div>" + "</html>";
            txtArea.setText(html);
        } else if (nodeSelected.equals(nodeThongKeDoanhThu)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Thống kê doanh thu</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Thống kê doanh thu” trên menu.</p>" + getImageHTML("image145.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Chọn ngày thống kê.</p>" + getPHTML(2)
                    + "<b>2.1: </b>Chọn ngày bắt đầu và ngày kết thúc.</p>" + getImageHTML("image147.png") + getPHTML(2)
                    + "<b>2.1.a: </b>Chọn nút “Thống kê”.</p>" + getImageHTML("image149.png") + getPHTML(2)
                    + "<b>2.1.b: </b>Hiển thị biểu đồ doanh thu và tổng doanh thu .</p>" + getImageHTML("image151.png") + getPHTML(2)
                    + "<b>2.2: </b>Chọn combobox để hiển thị các khoảng thời gian thống kê nhanh .</p>" + getImageHTML("image153.png") + getPHTML(2)
                    + "<b>2.2.a: </b>Chọn khoảng thời gian cần thống kê, hệ thống hiển thị biểu đồ doanh thu và tổng doanh thu.</p>" + getImageHTML("image155.png") + getPHTML(2)
                    + "</div>" + "</html>";
            txtArea.setText(html);
        } else if (nodeSelected.equals(nodeThongKeDichVu)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Thống kê dịch vụ</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Thống kê dịch vụ” trên menu.</p>" + getImageHTML("image175.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Chọn ngày thống kê.</p>" + getPHTML(2)
                    + "<b>2.1: </b>Chọn ngày bắt đầu và ngày kết thúc.</p>" + getImageHTML("image177.png") + getPHTML(2)
                    + "<b>2.1.a: </b>Chọn nút “Thống kê”.</p>" + getImageHTML("image179.png") + getPHTML(2)
                    + "<b>2.1.b: </b>Hiển thị doanh thu theo dịch vụ lên bảng.</p>" + getImageHTML("image181.png") + getPHTML(2)
                    + "<b>2.1.c: </b>Chọn dịch vụ cần xem chi tiết hóa đơn.</p>" + getImageHTML("image183.png") + getPHTML(2)
                    + "<b>2.1.d: </b>Hiển thị chi tiết các hóa đơn có sử dụng dịch vụ.</p>" + getImageHTML("image185.png") + getPHTML(2)
                    + "<b>2.2: </b>Chọn khoảng thời gian cần thống kê, hệ thống hiển thị doanh thu theo dịch vụ lên bảng.</p>" + getImageHTML("image187.png") + getPHTML(2)
                    + "<b>2.2.a: </b>Chọn dịch vụ cần xem chi tiết hóa đơn.</p>" + getImageHTML("image183.png") + getPHTML(2)
                    + "<b>2.2.b: </b>Hiển thị chi tiết các hóa đơn có sử dụng dịch vụ.</p>" + getImageHTML("image191.png") + getPHTML(2)
                    + "</div>" + "</html>";
            txtArea.setText(html);
        } else if (nodeSelected.equals(nodeThongKeKhachHang)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Thống kê khách hàng</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Thống kê khách hàng” trên menu.</p>" + getImageHTML("image157.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Chọn ngày thống kê.</p>" + getPHTML(2)
                    + "<b>2.1: </b>Chọn ngày bắt đầu và ngày kết thúc.</p>" + getImageHTML("image159.png") + getPHTML(2)
                    + "<b>2.1.a: </b>Chọn nút “Thống kê”.</p>" + getImageHTML("image161.png") + getPHTML(2)
                    + "<b>2.1.b: </b>Hiển thị doanh thu theo khách hàng lên bảng.</p>" + getImageHTML("image163.png") + getPHTML(2)
                    + "<b>2.1.c: </b>Chọn khách hàng cần xem chi tiết hóa đơn.</p>" + getImageHTML("image165.png") + getPHTML(2)
                    + "<b>2.1.d: </b>Hiển thị chi tiết các hóa đơn của khách hàng.</p>" + getImageHTML("image167.png") + getPHTML(2)
                    + "<b>2.2: </b>Chọn combobox để hiển thị các khoảng thời gian thống kê nhanh.</p>" + getImageHTML("image169.png") + getPHTML(2)
                    + "<b>2.2.a: </b>Chọn khoảng thời gian cần thống kê, hệ thống hiển thị doanh thu theo khách hàng lên bảng.</p>" + getImageHTML("image171.png") + getPHTML(2)
                    + "<b>2.2.b: </b>Chọn khách hàng cần xem chi tiết hóa đơn.</p>" + getImageHTML("image165.png") + getPHTML(2)
                    + "<b>2.2.c: </b>Hiển thị chi tiết các hóa đơn của khách hàng.</p>" + getImageHTML("image173.png") + getPHTML(2)
                    + "</div>" + "</html>";
            txtArea.setText(html);
        } else if (nodeSelected.equals(nodeTimKiemHoaDon)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Xem hóa đơn đã thanh toán</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Hóa đơn” trên menu.</p>" + getImageHTML("image118.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Hệ thống hiển thị danh sách hóa đơn đã thanh toán và tổng doanh thu.</p>" + getImageHTML("image120.png")  + getPHTML(1)
                    + "<b>Bước 3: </b>Chọn hóa đơn cần xem chi tiết.</p>" + getImageHTML("image122.png") + getPHTML(1)
                    + "<b>Bước 4: </b>Hiển thị chi tiết hóa đơn thanh toán.</p>" + getImageHTML("image124.png") + getPHTML(2)
                    + "<b>a: </b>Lọc hóa đơn theo ngày bắt đầu và ngày kết thúc.</p>" + getPHTML(3)
                    + "<b>Bước 1: </b>Chọn ngày bắt đầu và ngày kết thúc.</p>" + getImageHTML("image126.png") + getPHTML(3)
                    + "<b>Bước 2: </b>Chọn nút “Xem”.</p>" + getImageHTML("image128.png") + getPHTML(3)
                    + "<b>Bước 3: </b>Hiển thị danh sách hóa đơn và tổng doanh thu.</p>" + getImageHTML("image130.png") + getPHTML(3)
                    + "<b>Bước 4: </b>Chọn hóa đơn cần xem chi tiết.</p>" + getImageHTML("image132.png") + getPHTML(3)
                    + "<b>Bước 5: </b>Hiển thị chi tiết hóa đơn thanh toán.</p>" + getImageHTML("image135.png") + getPHTML(2)
                    + "<b>b: </b>Lọc hóa đơn theo khoảng thời gian có sẵn.</p>" + getPHTML(2)
                    + "<b>Bước 1: </b>Chọn khoảng thời gian cần lọc  .</p>" + getImageHTML("image137.png") + getPHTML(3)
                    + "<b>Bước 2: </b>Hiển thị danh sách hóa đơn và tổng doanh thu.</p>" + getImageHTML("image139.png") + getPHTML(3)
                    + "<b>Bước 3: </b>Chọn hóa đơn cần xem chi tiết.</p>" + getImageHTML("image141.png") + getPHTML(3)
                    + "<b>Bước 4: </b>Hiển thị chi tiết hóa đơn thanh toán.</p>" + getImageHTML("image143.png") + getPHTML(3)
                    + "</div>" + "</html>";
            txtArea.setText(html);
        } else if (nodeSelected.equals(nodeTimKiemKhachHang)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Tìm kiếm khách hàng</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Tìm kiếm khách hàng” trên menu.</p>" + getImageHTML("image002.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Nhập thông tin khách hàng cần tìm kiếm (tên, số điện thoại, CCCD).</p>" + getImageHTML("image004.png") + getPHTML(1)
                    + "<b>Bước 3: </b>Chọn nút tìm kiếm.</p>" + getImageHTML("image006.png") + getPHTML(1)
                    + "<b>Bước 4: </b>Hiển thị chi tiết thông tin khách hàng.</p>" + getImageHTML("image008.png") + getPHTML(2)
                    + "<b>4.1: </b>Cập nhật thông tin khách hàng.</p>" + getPHTML(3)
                    + "<b>4.1.a: </b> Chọn khách hàng cần cập nhật.</p>" + getImageHTML("image010.png") + getPHTML(3)
                    + "<b>4.1.b: </b>Chọn nút cập nhật. </p>" + getImageHTML("image012.png") + getPHTML(3)
                    + "<b>4.1.c: </b>Chuyển sang giao diện quản lý khách hàng.</p>" + getImageHTML("image014.png") + getPHTML(1)
                    + "<b>Bước 5: </b>Chọn nút làm mới để reset dữ liệu.</p>" + getImageHTML("image016.png")
                    + "</div>" + "</html>";
            txtArea.setText(html);
        }
        else if (nodeSelected.equals(nodeTimKiemNhanVien)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Tìm kiếm nhân viên</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Tìm kiếm nhân viên” trên menu.</p>" + getImageHTML("image200.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Nhập thông tin hoặc tình trạng của nhân viên cần tìm kiếm (tên, số điện thoại, CCCD, tình trạng).</p>" + getImageHTML("image400.png") + getPHTML(1)
                    + "<b>Bước 3: </b>Chọn nút tìm kiếm.</p>" + getImageHTML("image600.png") + getPHTML(1)
                    + "<b>Bước 4: </b>Hiển thị chi tiết thông tin nhân viên.</p>" + getImageHTML("image800.png") + getPHTML(2)
                    + "<b>4.1: </b>Cập nhật thông tin nhân viên.</p>" + getPHTML(3)
                    + "<b>4.1.a: </b> Chọn nhân viên cần cập nhật.</p>" + getImageHTML("image1000.png") + getPHTML(3)
                    + "<b>4.1.b: </b>Chọn nút cập nhật. </p>" + getImageHTML("image1200.png") + getPHTML(3)
                    + "<b>4.1.c: </b>Chuyển sang giao diện quản lý nhân viên.</p>" + getImageHTML("image1400.png") + getPHTML(1)
                    + "<b>Bước 5: </b>Chọn nút làm mới để reset dữ liệu.</p>" + getImageHTML("image1600.png")
                    + "</div>" + "</html>";
            txtArea.setText(html);
        }else if (nodeSelected.equals(nodeTimKiemPhong)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Tìm kiếm phòng</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Tìm kiếm phòng” trên menu.</p>" + getImageHTML("image002p.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Chọn loại phòng và giá phòng của phòng cần tìm kiếm .</p>" + getImageHTML("image004p.png") + getPHTML(1)
                    + "<b>Bước 3: </b>Hiển thị chi tiết thông tin phòng.</p>" + getImageHTML("image006p.png") + getPHTML(2)
                    + "<b>Bước 4: </b>Chọn nút làm mới để reset dữ liệu.</p>" + getImageHTML("image008p.png")
                    + "</div>" + "</html>";
            txtArea.setText(html);
        }else if (nodeSelected.equals(nodeTimKiemDichVu)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Tìm kiếm dịch vụ</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Tìm kiếm dịch vụ” trên menu.</p>" + getImageHTML("image004dv.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Nhập và chọn thông tin  của dịch vụ cần tìm kiếm .</p>" + getImageHTML("image006dv.png") + getPHTML(1)
                    + "<b>Bước 3: </b>Chọn nút tìm kiếm.</p>" + getImageHTML("image008dv.png") + getPHTML(1)
                    + "<b>Bước 4: </b>Hiển thị chi tiết thông tin dịch vụ.</p>" + getImageHTML("image010dv.png") + getPHTML(2)
                    + "<b>Bước 5: </b>Chọn nút làm mới để reset dữ liệu.</p>" + getImageHTML("image012dv.png")
                    + "</div>" + "</html>";
            txtArea.setText(html);
        }else if (nodeSelected.equals(nodeCapNhatNhanVien)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Cập nhât nhân viên</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Cập nhật nhân viên” trên menu.</p>" + getImageHTML("image002cnnv.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Thêm nhân viên.</p>" + getPHTML(2)
                    + "<b>2.1: </b>Nhập thông tin của nhân viên cần thêm</p>" + getImageHTML("image004cnnv.png") + getPHTML(2)
                    + "<b>2.2: </b>Chọn nút “Thêm nhân viên”.</p>" + getImageHTML("image006cnnv.png") + getPHTML(2)
                    + "<b>2.3: </b>Hiển thị danh sách nhân viên sau khi cập nhật lên bảng lên bảng.</p>" + getImageHTML("image008cnnv.png") + getPHTML(1)
                    + "<b>Bước 3: </b>Sửa nhân viên.</p>" + getPHTML(2)
                    + "<b>3.1.: </b>Chọn nhân viên cần sửa thông tin.</p>" + getImageHTML("image010cnnv.png") + getPHTML(2)
                    + "<b>3.2: </b>Sửa thông tin của nhân viên.</p>" + getImageHTML("image004cnnv.png") + getPHTML(2)
                    + "<b>3.3: </b>Chọn nút “Sửa”.</p>" + getImageHTML("image012cnnv.png") + getPHTML(2)
                    + "<b>3.4: </b>Hiển thị danh sách nhân viên sau khi cập nhật lên bảng.</p>" + getImageHTML("image008cnnv.png") + getPHTML(1)
                    + "<b>Bước 4: </b>Chọn nút làm mới để reset dữ liệu.</p>" + getImageHTML("image014cnnv.png") + getPHTML(2)
                    + "</div>" + "</html>";
            txtArea.setText(html);
        }else if (nodeSelected.equals(nodeCapNhatPhong)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Cập nhật phòng</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Cập nhật phòng” trên menu.</p>" + getImageHTML("image004cnp.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Thêm phòng.</p>" + getPHTML(2)
                    + "<b>2.1: </b>Nhập thông tin của phòng cần thêm</p>" + getImageHTML("image006cnp.png") + getPHTML(2)
                    + "<b>2.2: </b>Chọn nút “Thêm phòng”.</p>" + getImageHTML("image008cnp.png") + getPHTML(2)
                    + "<b>2.3: </b>Hiển thị danh sách phòng sau khi cập nhật lên bảng lên bảng.</p>" + getImageHTML("image010cnp.png") + getPHTML(1)
                    + "<b>Bước 3: </b>Sửa phòng.</p>" + getPHTML(2)
                    + "<b>3.1.: </b>Chọn phòng cần sửa thông tin.</p>" + getImageHTML("image012cnp.png") + getPHTML(2)
                    + "<b>3.2: </b>Sửa thông tin của phòng.</p>" + getImageHTML("image006cnp.png") + getPHTML(2)
                    + "<b>3.3: </b>Chọn nút “Sửa phòng”.</p>" + getImageHTML("image014cnp.png") + getPHTML(2)
                    + "<b>3.4: </b>Hiển thị danh sách phòng sau khi cập nhật lên bảng.</p>" + getImageHTML("image010cnp.png") + getPHTML(1)
                    + "<b>Bước 4: </b>Xóa phòng.</p>" + getPHTML(2)
                    + "<b>4.1.: </b>Chọn phòng cần xóa thông tin.</p>" + getImageHTML("image012cnp.png") + getPHTML(2)
                    + "<b>4.2: </b>Chọn nút “Xóa ”.</p>" + getImageHTML("image016cnp.png") + getPHTML(2)
                    + "<b>4.3: </b>Hiển thị danh sách phòng sau khi cập nhật lên bảng.</p>" + getImageHTML("image010cnp.png") + getPHTML(1)
                    + "<b>Bước 5: </b>Chọn nút làm mới để reset dữ liệu.</p>" + getImageHTML("image018cnp.png") + getPHTML(2)
                    + "</div>" + "</html>";
            txtArea.setText(html);
        }else if (nodeSelected.equals(nodeCapNhatKhachHang)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Cập nhât khách hàng</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Cập nhật khách hàng” trên menu.</p>" + getImageHTML("image004cnkh.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Thêm khách hàng.</p>" + getPHTML(2)
                    + "<b>2.1: </b>Nhập thông tin của khách hàng cần thêm</p>" + getImageHTML("image006cnkh.png") + getPHTML(2)
                    + "<b>2.2: </b>Chọn nút “Thêm khách hàng”.</p>" + getImageHTML("image008cnkh.png") + getPHTML(2)
                    + "<b>2.3: </b>Hiển thị danh sách khách hàng sau khi cập nhật lên bảng lên bảng.</p>" + getImageHTML("image010cnkh.png") + getPHTML(1)
                    + "<b>Bước 3: </b>Sửa khách hàng.</p>" + getPHTML(2)
                    + "<b>3.1.: </b>Chọn khách hàng cần sửa thông tin.</p>" + getImageHTML("image012cnkh.png") + getPHTML(2)
                    + "<b>3.2: </b>Sửa thông tin của khách hàng.</p>" + getImageHTML("image006cnkh.png") + getPHTML(2)
                    + "<b>3.3: </b>Chọn nút “Sửa”.</p>" + getImageHTML("image014cnkh.png") + getPHTML(2)
                    + "<b>3.4: </b>Hiển thị danh sách khách hàng sau khi cập nhật lên bảng.</p>" + getImageHTML("image010cnkh.png") + getPHTML(1)
                    + "<b>Bước 4: </b>Chọn nút làm mới để reset dữ liệu.</p>" + getImageHTML("image016cnkh.png") + getPHTML(2)
                    + "</div>" + "</html>";
            txtArea.setText(html);
        }else if (nodeSelected.equals(nodeLoaiDichvu)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Cập nhật dịch vụ</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Cập nhật dịch vụ” trên menu.</p>" + getImageHTML("image004ldv.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Thêm dịch vụ.</p>" + getPHTML(2)
                    + "<b>2.1: </b>Nhập thông tin của dịch vụ cần thêm</p>" + getImageHTML("image006ldv.png") + getPHTML(2)
                    + "<b>2.2: </b>Chọn nút “Thêm ”.</p>" + getImageHTML("image008ldv.png") + getPHTML(2)
                    + "<b>2.3: </b>Hiển thị danh sách dịch vụ sau khi cập nhật lên bảng lên bảng.</p>" + getImageHTML("image010ldv.png") + getPHTML(1)
                    + "<b>Bước 3: </b>Sửa dịch vụ.</p>" + getPHTML(2)
                    + "<b>3.1.: </b>Chọn dịch vụ cần sửa thông tin.</p>" + getImageHTML("image012ldv.png") + getPHTML(2)
                    + "<b>3.2: </b>Sửa thông tin của dịch vụ.</p>" + getImageHTML("image006ldv.png") + getPHTML(2)
                    + "<b>3.3: </b>Chọn nút “Sửa ”.</p>" + getImageHTML("image014ldv.png") + getPHTML(2)
                    + "<b>3.4: </b>Hiển thị danh sách dịch vụ sau khi cập nhật lên bảng.</p>" + getImageHTML("image010ldv.png") + getPHTML(1)
                    + "<b>Bước 4: </b>Xóa dịch vụ.</p>" + getPHTML(2)
                    + "<b>4.1.: </b>Chọn dịch vụ cần xóa thông tin.</p>" + getImageHTML("image012ldv.png") + getPHTML(2)
                    + "<b>4.2: </b>Chọn nút “Xóa ”.</p>" + getImageHTML("image016ldv.png") + getPHTML(2)
                    + "<b>4.3: </b>Hiển thị danh sách dịch vụ sau khi cập nhật lên bảng.</p>" + getImageHTML("image010ldv.png") + getPHTML(1)
                    + "<b>Bước 5: </b>Chọn nút làm mới để reset dữ liệu.</p>" + getImageHTML("image018ldv.png") + getPHTML(2)
                    + "</div>" + "</html>";
            txtArea.setText(html);
        }else if (nodeSelected.equals(nodeCapNhatDichVu)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Loại dịch vụ</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Loại dịch vụ” trên menu.</p>" + getImageHTML("image004cndv.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Thêm loại dịch vụ.</p>" + getPHTML(2)
                    + "<b>2.1: </b>Nhập thông tin của loại dịch vụ cần thêm</p>" + getImageHTML("image006cndv.png") + getPHTML(2)
                    + "<b>2.2: </b>Chọn nút “Thêm ”.</p>" + getImageHTML("image008cndv.png") + getPHTML(2)
                    + "<b>2.3: </b>Hiển thị danh sách loại dịch vụ sau khi cập nhật lên bảng lên bảng.</p>" + getImageHTML("image010cndv.png") + getPHTML(1)
                    + "<b>Bước 3: </b>Sửa loại dịch vụ.</p>" + getPHTML(2)
                    + "<b>3.1.: </b>Chọn loại dịch vụ cần sửa thông tin.</p>" + getImageHTML("image012cndv.png") + getPHTML(2)
                    + "<b>3.2: </b>Sửa thông tin của loại dịch vụ.</p>" + getImageHTML("image006cndv.png") + getPHTML(2)
                    + "<b>3.3: </b>Chọn nút “Sửa ”.</p>" + getImageHTML("image014cndv.png") + getPHTML(2)
                    + "<b>3.4: </b>Hiển thị danh sách loại dịch vụ sau khi cập nhật lên bảng.</p>" + getImageHTML("image010cndv.png") + getPHTML(1)
                    + "<b>Bước 4: </b>Xóa loại dịch vụ.</p>" + getPHTML(2)
                    + "<b>4.1.: </b>Chọn loại dịch vụ cần xóa thông tin.</p>" + getImageHTML("image012cndv.png") + getPHTML(2)
                    + "<b>4.2: </b>Chọn nút “Xóa ”.</p>" + getImageHTML("image016cndv.png") + getPHTML(2)
                    + "<b>4.3: </b>Hiển thị danh sách loại dịch vụ sau khi cập nhật lên bảng.</p>" + getImageHTML("image010cndv.png") + getPHTML(1)
                    + "<b>Bước 5: </b>Chọn nút làm mới để reset dữ liệu.</p>" + getImageHTML("image018cndv.png") + getPHTML(2)
                    + "</div>" + "</html>";
            txtArea.setText(html);
        }else if (nodeSelected.equals(nodeLoaiPhong)) {
            txtArea.setContentType("text/html");
            txtArea.setEditable(false);
            txtArea.setBackground(Color.decode("#D7FFF6"));
            String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Loại phòng</h1>" + "<div>"
                    + getPHTML(1)
                    + "<b>Bước 1: </b>Chọn chức năng “Loại phòng” trên menu.</p>" + getImageHTML("image004lp.png") + getPHTML(1)
                    + "<b>Bước 2: </b>Thêm loại phòng.</p>" + getPHTML(2)
                    + "<b>2.1: </b>Nhập thông tin của loại phòng cần thêm</p>" + getImageHTML("image006lp.png") + getPHTML(2)
                    + "<b>2.2: </b>Chọn nút “Thêm ”.</p>" + getImageHTML("image008lp.png") + getPHTML(2)
                    + "<b>2.3: </b>Hiển thị danh sách loại phòng sau khi cập nhật lên bảng lên bảng.</p>" + getImageHTML("image010lp.png") + getPHTML(1)
                    + "<b>Bước 3: </b>Sửa loại phòng.</p>" + getPHTML(2)
                    + "<b>3.1.: </b>Chọn loại phòng cần sửa thông tin.</p>" + getImageHTML("image012lp.png") + getPHTML(2)
                    + "<b>3.2: </b>Sửa thông tin của loại phòng.</p>" + getImageHTML("image006lp.png") + getPHTML(2)
                    + "<b>3.3: </b>Chọn nút “Sửa ”.</p>" + getImageHTML("image014lp.png") + getPHTML(2)
                    + "<b>3.4: </b>Hiển thị danh sách loại phòng sau khi cập nhật lên bảng.</p>" + getImageHTML("image010lp.png") + getPHTML(1)
                    + "<b>Bước 4: </b>Xóa loại phòng.</p>" + getPHTML(2)
                    + "<b>4.1.: </b>Chọn loại phòng cần xóa thông tin.</p>" + getImageHTML("image012lp.png") + getPHTML(2)
                    + "<b>4.2: </b>Chọn nút “Xóa ”.</p>" + getImageHTML("image016lp.png") + getPHTML(2)
                    + "<b>4.3: </b>Hiển thị danh sách loại phòng sau khi cập nhật lên bảng.</p>" + getImageHTML("image010lp.png") + getPHTML(1)
                    + "<b>Bước 5: </b>Chọn nút làm mới để reset dữ liệu.</p>" + getImageHTML("image018lp.png") + getPHTML(2)
                    + "</div>" + "</html>";
            txtArea.setText(html);
        }



    }

    public String getPHTML(int x) {
        if (x == 0)
            return "<p style='text-align:left; font-size:17px ;margin-left: 25px'>";
        if (x == 1)
            return "<p style='text-align:left; font-size:17px ;margin-left: 75px'>";
        if (x == 2)
            return "<p style='text-align:left; font-size:17px ;margin-left: 125px'>";
        if (x == 3)
            return "<p style='text-align:left; font-size:17px ;margin-left: 150px'>";
        return "";
    }

    public String getImageHTML(String fileImage) {
        return "<div style='text-align:center;'>" + "<img  style='text-align:center;' src='"
                + getClass().getResource(pathImages + fileImage).toString() + "'/>" + "</div>";
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
