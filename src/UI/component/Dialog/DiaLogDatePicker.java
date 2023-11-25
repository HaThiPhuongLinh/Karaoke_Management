package UI.component.Dialog;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Calendar;
import java.sql.*;

/**
 * Giao diện lịch
 * Người tham gia thiết kế: Hà Thị Phương Linh, Nguyễn Đình Dương, Nguyễn Quang Duy
 * Ngày tạo: 26/09/2023
 * Lần cập nhật cuối: 17/10/2023
 * Nội dung cập nhật: chỉnh sửa màu lịch
 */
public class DiaLogDatePicker extends JDialog implements ActionListener, ChangeListener {
    private int width = 450, heightPn = 210, widthPn = width - 20;
    private JButton[] btn = new JButton[49];
    private String day = "";
    private int month = Calendar.getInstance().get(Calendar.MONTH);
    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private JButton btnPre, btnNext, btnCancel, btnSubmit;
    private SpinnerNumberModel spinYearModel;
    private JSpinner spinYear;
    private int check = 0, viTri = -1;
    private JLabel lblMonth, lblYear, lblThu, lblNgayThang, lblToDay;
    private String blueColor = "#5F009D";
    private String whiteColor = "#fafafa";

    public DiaLogDatePicker() {
        setTitle("Chọn ngày");
        setSize(447, 240);
        setResizable(false);
        setLocationRelativeTo(null);

        createFormDatePicker();
    }

    public void createFormDatePicker() {
        JPanel pnMain = new JPanel();
        pnMain.setBounds(0, 0, widthPn, heightPn);
        pnMain.setBackground(Color.decode(whiteColor));
        pnMain.setLayout(null);

        JPanel pnShowTime = new JPanel();
        pnShowTime.setBounds(0, 0, 100, 202);
        pnShowTime.setBackground(Color.decode(blueColor));
        pnMain.add(pnShowTime);
        pnShowTime.setLayout(null);

        lblYear = new JLabel("year");
        lblYear.setFont(new Font("Dialog", Font.BOLD, 15));
        lblYear.setBounds(12, 12, 107, 16);
        lblYear.setForeground(Color.decode("#aeb5df"));
        pnShowTime.add(lblYear);

        lblThu = new JLabel("thứ");
        lblThu.setFont(new Font("Dialog", Font.BOLD, 18));
        lblThu.setBounds(12, 40, 107, 25);
        lblThu.setForeground(Color.WHITE);
        pnShowTime.add(lblThu);

        lblNgayThang = new JLabel("tháng ngày");
        lblNgayThang.setFont(new Font("Dialog", Font.BOLD, 18));
        lblNgayThang.setBounds(12, 68, 107, 25);
        lblNgayThang.setForeground(Color.WHITE);
        pnShowTime.add(lblNgayThang);

        String[] header = { "S", "M", "T", "W", "T", "F", "S" };
        JPanel pnDateTable = new JPanel(new GridLayout(7, 7));
        pnDateTable.setBackground(Color.decode(whiteColor));

        for (int i = 0; i < btn.length; i++) {
            final int selection = i;
            btn[i] = new JButton();
            btn[i].setFocusPainted(false);
            btn[i].setBackground(Color.WHITE);
            if (i < 7) {
                btn[i].setText(header[i]);
                btn[i].setEnabled(false);
                btn[i].setForeground(Color.decode(blueColor));
            } else {
                btn[i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        day = btn[selection].getActionCommand();
                        if (viTri != -1) {
                            btn[viTri].setBackground(Color.decode(whiteColor));
                            btn[viTri].setForeground(Color.BLACK);
                        }
                        viTri = selection;
                        btn[selection].setBackground(Color.decode(blueColor));
                        btn[selection].setForeground(Color.decode(whiteColor));
                        int day = Integer.parseInt(btn[selection].getText());
                        displayShowDate(day);
                    }
                });
            }
            btn[i].setBorder(null);
            btn[i].setPreferredSize(new Dimension(20, 20));
            btn[i].setBackground(Color.decode(whiteColor));
            pnDateTable.add(btn[i]);
        }

        JPanel pnBtn = new JPanel();
        pnBtn.setLayout(null);
        pnBtn.setBackground(Color.decode(whiteColor));

        btnPre = new JButton("<");
        btnNext = new JButton(">");

        spinYearModel = new SpinnerNumberModel(year, 1900, null, 1);
        spinYear = new JSpinner(spinYearModel);
        lblMonth = new JLabel("tháng");
        lblMonth.setHorizontalAlignment(SwingConstants.CENTER);
        lblMonth.setFont(new Font("Dialog", Font.BOLD, 14));

        int h = 25;
        btnPre.setBounds(3, 0, 41, h);
        btnPre.setBackground(Color.decode(whiteColor));
        btnPre.setForeground(Color.decode(blueColor));
        btnPre.setBorder(null);
        lblMonth.setBounds(62, 2, 105, 25);
        spinYear.setBounds(165, 2, 70, h);
        btnNext.setBounds(287, 0, 41, h);
        btnNext.setBackground(Color.decode(whiteColor));
        btnNext.setForeground(Color.decode(blueColor));
        btnNext.setBorder(null);

        pnBtn.setBounds(101, 0, 330, 30);
        pnDateTable.setBounds(101, 30, 330, 140);

        pnBtn.add(btnPre);
        pnBtn.add(lblMonth);
        pnBtn.add(spinYear);
        pnBtn.add(btnNext);

        pnMain.add(pnBtn);
        pnMain.add(pnDateTable);

        JPanel pnSubmit = new JPanel();
        pnSubmit.setBackground(Color.decode(whiteColor));
        pnSubmit.setLayout(null);
        pnSubmit.setBounds(101, 172, 330, 30);
        pnMain.add(pnSubmit);

        btnSubmit = new JButton("OK");
        btnSubmit.setBounds(268, 0, 60, 26);
        btnSubmit.setBackground(Color.decode(whiteColor));
        btnSubmit.setForeground(Color.decode(blueColor));
        btnSubmit.setBorder(null);
        pnSubmit.add(btnSubmit);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(182, 0, 74, 26);
        btnCancel.setBackground(Color.decode(whiteColor));
        btnCancel.setForeground(Color.decode(blueColor));
        btnCancel.setBorder(null);
        pnSubmit.add(btnCancel);

        lblToDay = new JLabel("Today: ");
        lblToDay.setBounds(10, 6, 162, 14);
        pnSubmit.add(lblToDay);

        displayDate();
        showToDay();
        getContentPane().add(pnMain);

        btnNext.addActionListener(this);
        btnPre.addActionListener(this);
        btnSubmit.addActionListener(this);
        btnCancel.addActionListener(this);
        spinYear.addChangeListener(this);
    }

//    public static void main(String[] args) {
//        new DiaLogDatePicker().setVisible(true);
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnPre)) {
            month--;
            displayDate();
        } else if (o.equals(btnNext)) {
            month++;
            displayDate();
        } else if (o.equals(btnSubmit)) {
            check = 1;
            dispose();
        } else if (o.equals(btnCancel)) {
            check = 0;
            dispose();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Object o = e.getSource();
        if (o.equals(spinYear)) {
            displayDate();
        }
    }

    // thay đổi lịch theo tháng năm
    public void displayDate() {
        for (int i = 7; i < btn.length; i++)
            btn[i].setText("");
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MMMM");
        Calendar cal = Calendar.getInstance();
        int y = (int) spinYear.getValue();
        if (y != year)
            year = y;

        cal.set(year, month, 1);

        lblMonth.setText(sdfMonth.format(cal.getTime()));
        spinYear.setValue(year);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < 6 + dayOfWeek; i++) {
            btn[i].setEnabled(false);
        }
        for (int i = 6 + dayOfWeek, day = 1; day <= daysInMonth; i++, day++) {
            btn[i].setText("" + day);
            btn[i].setEnabled(true);
        }
        for (int i = 6 + dayOfWeek + daysInMonth; i < btn.length; i++) {
            btn[i].setEnabled(false);
        }
    }

    // hiện ngày hiện tại
    public void showToDay() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        lblNgayThang.setText(sdf.format(cal.getTime()));
        sdf = new SimpleDateFormat("E");

        lblThu.setText(sdf.format(cal.getTime()) + ",");
        sdf = new SimpleDateFormat("yyyy");

        lblYear.setText(sdf.format(cal.getTime()));
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        lblToDay.setText("Today: " + sdf.format(cal.getTime()));
    }

    // hiện ngày đã chọn
    public void displayShowDate(int day) {
        Calendar cal = Calendar.getInstance();
        int y = (int) spinYear.getValue();
        if (y != year)
            year = y;
        cal.set(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        lblNgayThang.setText(sdf.format(cal.getTime()));

        sdf = new SimpleDateFormat("E");
        lblThu.setText(sdf.format(cal.getTime()) + ",");

        sdf = new SimpleDateFormat("yyyy");
        lblYear.setText(sdf.format(cal.getTime()));
    }

    // lấy ngày chọn từ lịch
    private String getPickedDate() {
        if (day.equals(""))
            return day;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
    }

    // lấy ngày chọn từ lịch
    public Date getDate() {
        if (day.equals(""))
            day = "0";
        Calendar cal = Calendar.getInstance();
        int date = Integer.parseInt(day);
        cal.set(year, month, date);
        return (Date) cal.getTime();
    }

    // lấy ngày hiện tại dạng string
    public static String getToDay() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(cal.getTimeInMillis());
    }

    // trả về ngày được chọn
    public String getValueString() {
        String re = "";
        if (check == 1)
            re = getPickedDate();
        return re;
    }
}
