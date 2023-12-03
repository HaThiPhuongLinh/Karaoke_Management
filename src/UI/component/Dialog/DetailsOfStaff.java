package UI.component.Dialog;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import ConnectDB.ConnectDB;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import Entity.Account;
import Entity.Staff;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

/**
 * Giao diện thông tin nhân viên
 * Người tham gia thiết kế: Hà Thị Phương Linh
 * Ngày tạo: 03/12/2023
 * Lần cập nhật cuối: 03/12/2023
 */
public class DetailsOfStaff extends JFrame implements ActionListener {
  private JButton btnHinhAnh, btnBack;
  private JLabel lblTen, lblNgaySinh, lblEmail, lblSDT, lblDiaChi, lblViTriCongViec, lblGioiTinh;
  private JLabel txtNgaySinh, txtEmail, txtSDT, txtDiaChi, txtDiaChiBreak, txtGioiTinh;
  private JPanel pnlInfo;
  private Cursor handleCursor;
  private Staff staff;
  private Account account;
  public static Staff staffLogin = null;
  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

  public DetailsOfStaff(Staff staff) throws MalformedURLException {
    setSize(400, 420);
    setTitle("Thông tin nhân viên");
    setLocationRelativeTo(null);
    setResizable(false);
    setLayout(null);
    handleCursor = new Cursor(Cursor.HAND_CURSOR);
    setAlwaysOnTop(true);
    try {
      ConnectDB.getInstance().connect();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    this.staffLogin = staff;
    account = staff.getTaiKhoan();

    pnlInfo = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth(), h = getHeight();
        Color color1 = Color.decode("#3fb5fb");
        Color color2 = Color.decode("#ee4062");
        GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
      }

    };
    pnlInfo.setLayout(null);
    pnlInfo.setBounds(0, 0, 400, 440);
    pnlInfo.setBackground(Color.WHITE);
    add(pnlInfo);

    btnHinhAnh = new JButton("");
    btnHinhAnh.setIcon(FontIcon.of(FontAwesomeSolid.USER, 70, Color.BLACK));
    btnHinhAnh.setFont(new Font("Arial", Font.BOLD, 20));
    btnHinhAnh.setBounds(145, 10, 100, 100);
    btnHinhAnh.setBorderPainted(false);
    btnHinhAnh.setFocusPainted(false);
    btnHinhAnh.setContentAreaFilled(false);
    btnHinhAnh.setOpaque(false);
    pnlInfo.add(btnHinhAnh);

    lblTen = new JLabel(staff.getTenNhanVien());
    lblTen.setFont(new Font("Arial", Font.BOLD, 20));
    lblTen.setBounds(125, 120, 200, 30);
    pnlInfo.add(lblTen);

    lblViTriCongViec = new JLabel(staff.getChucVu());
    lblViTriCongViec.setFont(new Font("Arial", Font.PLAIN, 15));
    lblViTriCongViec.setBounds(165, 150, 200, 30);
    lblViTriCongViec.setForeground(Color.BLUE);
    pnlInfo.add(lblViTriCongViec);

    lblNgaySinh = new JLabel("Ngày sinh:");
    lblNgaySinh.setFont(new Font("Arial", Font.PLAIN, 15));
    lblNgaySinh.setBounds(40, 180, 100, 30);
    pnlInfo.add(lblNgaySinh);

    Date formatNgaySinh = staff.getNgaySinh();
    txtNgaySinh = new JLabel(dateFormat.format(formatNgaySinh));
    txtNgaySinh.setFont(new Font("Arial", Font.PLAIN, 15));
    txtNgaySinh.setBounds(140, 180, 250, 30);
    pnlInfo.add(txtNgaySinh);

    lblGioiTinh = new JLabel("Giới tính:");
    lblGioiTinh.setFont(new Font("Arial", Font.PLAIN, 15));
    lblGioiTinh.setBounds(40, 220, 100, 30);
    pnlInfo.add(lblGioiTinh);

    txtGioiTinh = new JLabel(staff.isGioiTinh() ? "Nam" : "Nữ");
    txtGioiTinh.setFont(new Font("Arial", Font.PLAIN, 15));
    txtGioiTinh.setBounds(140, 220, 250, 30);
    pnlInfo.add(txtGioiTinh);

    lblSDT = new JLabel("SDT:");
    lblSDT.setFont(new Font("Arial", Font.PLAIN, 15));
    lblSDT.setBounds(40, 260, 100, 30);
    pnlInfo.add(lblSDT);

    txtSDT = new JLabel(staff.getSoDienThoai());
    txtSDT.setFont(new Font("Arial", Font.PLAIN, 15));
    txtSDT.setBounds(140, 260, 250, 30);
    pnlInfo.add(txtSDT);

    lblEmail = new JLabel("Tài khoản:");
    lblEmail.setFont(new Font("Arial", Font.PLAIN, 15));
    lblEmail.setBounds(40, 300, 100, 30);
    pnlInfo.add(lblEmail);

    txtEmail = new JLabel(account.getTaiKhoan());
    txtEmail.setFont(new Font("Arial", Font.PLAIN, 15));
    txtEmail.setBounds(140, 300, 250, 30);
    pnlInfo.add(txtEmail);

    lblDiaChi = new JLabel("Địa chỉ:");
    lblDiaChi.setFont(new Font("Arial", Font.PLAIN, 15));
    lblDiaChi.setBounds(40, 340, 100, 30);
    pnlInfo.add(lblDiaChi);

    String text = staff.getDiaChi();
    String[] diaChi = text.split(" ");
    String[] primary = new String[5];
    List<String> secodary = new ArrayList<String>();
    for (int i = 0; i < diaChi.length; i++) {
      for (int j = 0; j < primary.length; j++) {
        if (primary[j] == null) {
          primary[j] = diaChi[i];
          break;
        }
      }
      if (i > 4) {
        secodary.add(diaChi[i]);
      }
    }
    String header = "";
    for (String string : primary) {
      if (string != null)
        header += string + " ";
    }
    txtDiaChi = new JLabel(header);
    txtDiaChi.setFont(new Font("Arial", Font.PLAIN, 15));
    txtDiaChi.setBounds(140, 340, 250, 30);
    pnlInfo.add(txtDiaChi);

    txtDiaChiBreak = new JLabel();
    txtDiaChiBreak.setFont(new Font("Arial", Font.PLAIN, 15));
    txtDiaChiBreak.setBounds(140, 370, 250, 30);
    pnlInfo.add(txtDiaChiBreak);
    String sub = "";
    for (String string : secodary) {
      sub += string + " ";
    }
    if (secodary.size() > 0) {
      txtDiaChiBreak.setText(sub);
    }


    btnBack = new JButton();
    btnBack.setBorder(null);
    btnBack.setBorderPainted(false);
    btnBack.setContentAreaFilled(false);
    btnBack.setFocusPainted(false);
    btnBack.setOpaque(false);
    btnBack.setCursor(handleCursor);
    pnlInfo.add(btnBack);

    btnBack.addActionListener(this);
    btnBack.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
        "btnBack");
    btnBack.getActionMap().put("btnBack", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnBack.doClick();
      }
    });
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnBack)
      this.dispose();
  }
}
