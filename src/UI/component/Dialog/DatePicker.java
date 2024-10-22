package UI.component.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.sql.Date;
import java.util.Calendar;

/**
 * Giao diện lịch
 * Người tham gia thiết kế: Hà Thị Phương Linh, Nguyễn Đình Dương, Nguyễn Quang Duy
 * Ngày tạo: 26/09/2023
 * Lần cập nhật cuối: 22/10/2023
 * Nội dung cập nhật: thêm tính năng getDatesFromToday (lấy ra ngày từ ngày hiện tại)
 */
public class DatePicker extends JPanel implements ActionListener {
    private JTextField txt;
    private JButton btn;
    private int widthDefault = 80;
    private boolean isActive = true;
    DiaLogDatePicker f = new DiaLogDatePicker();
    ImageIcon calenderIcon = new ImageIcon("data/images/calender_16.png");

    public DatePicker() {
        setLayout(null);
        createGUI();
    }

    public DatePicker(int width) {
        setLayout(null);
        setBounds(0, 0, width, 30);
        widthDefault = width;
        createGUI();
    }

    private void createGUI() {
        txt = new JTextField();
        txt.setBounds(0, 0, widthDefault - 70, 30);
        txt.setEditable(false);
        txt.setText(DiaLogDatePicker.getToDay());

        btn = new JButton(calenderIcon);
        btn.setBounds(widthDefault - 70, 0, 30, 30);

        this.add(txt);
        this.add(btn);
        btn.addActionListener(this);
    }

    public static void main(String[] args) {
        new DatePicker().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btn)) {
            f.setModal(true);
            f.setVisible(true);
            String date = f.getValueString();
            if (!(date.equals(""))) {
                txt.setText(date);
            }
        }
    }

    // lấy giá trị từ textField
    public String getValue() {
        return txt.getText();
    }

    // set giá trị về ngày hiện tại
    public void setValueToDay() {
        txt.setText(DiaLogDatePicker.getToDay());
    }

    // set giá trị về ngày hiện tại
    public Date getValueToDay() throws ParseException {
        String strDate = DiaLogDatePicker.getToDay();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = sdf.parse(strDate);
        Date sqlDate = new Date(date.getTime());
        return sqlDate;
    }
    // Lấy ngày hiện tại công thêm 1 ngày
    public Date getCurrentDatePlusOneDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1); // Cộng thêm 1 ngày
        java.util.Date newDate = calendar.getTime();
        Date sqlDate = new Date(newDate.getTime());
        return sqlDate;
    }

    // cập nhật giá trị với input java.sql.Date
    public void setValue(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        txt.setText(sdf.format(date));
    }

    // cập nhật giá trị với input String
    public void setValue(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = sdf.parse(strDate);
        txt.setText(sdf.format(date));
    }

    // lấy ra giá trị ngày dưới dạng java.sql.Date
    public Date getFullDate() throws ParseException {
        String strDate = txt.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = sdf.parse(strDate);
        Date sqlDate = new Date(date.getTime());
        return sqlDate;
    }
//     lấy ra giá trị ngày và cộng thêm 1 ngày
    public Date addOneDay() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = sdf.parse(txt.getText());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1); // Cộng thêm 1 ngày
        java.util.Date newDate = calendar.getTime();
        Date sqlDate = new Date(newDate.getTime());
        return sqlDate;
    }

    // lấy ra ngày
    public int getDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String date = sdf.format(txt.getText());
        return Integer.parseInt(date);
    }

    // lấy ra tháng
    public int getMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String date = sdf.format(txt.getText());
        return Integer.parseInt(date);
    }

    // lấy ra năm
    public int getYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String date = sdf.format(txt.getText());
        return Integer.parseInt(date);
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
        btn.setEnabled(isActive);
        if (isActive) {
            txt.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            txt.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public Date getDatesFromToday(int timeUnit, int number) {
        String strDate = DiaLogDatePicker.getToDay();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = null;
        Calendar cal = Calendar.getInstance();
        try {
            date = sdf.parse(strDate);
            cal.setTime(date);
            cal.add(timeUnit, number);
            date = cal.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date sqlDate = new Date(date.getTime());
        return sqlDate;
    }
    public Date setDatesFromToday(int timeUnit, int numberOfMonths) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = getDatesFromToday(timeUnit, numberOfMonths);
        txt.setText(sdf.format(date.getTime()));
        return date;
    }

}
