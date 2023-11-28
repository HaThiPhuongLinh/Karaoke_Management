package UI.component.Dialog;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 * Gửi SMS về số điện thoại khách hàng (sử dụng Twilio)
 * Người tham gia thiết kế: Hà Thị Phương Linh
 * Ngày tạo: 29/11/2023
 * Lần cập nhật cuối: 29/11/2023
 */
public class SendSMS {
    private static final String ACCOUNT_SID = "ACe4bd51360b9c8984b6780571ce8d15ab";
    private static final String AUTH_TOKEN = "e29e082f6ddd50e803869d60df56af9e";
    private static final String TWILIO_PHONE_NUMBER = "+17013544221";

    /**
     * Gửi SMS đến số điện thoại
     * @param toPhoneNumber: sdt cần gửi
     * @param roomID: mã phòng đã đặt
     * @param typeOfRoom: loại phòng đã đặt
     * @param dateTime: ngày giờ đặt
     */
    public static void sendConfirmationSMS(String toPhoneNumber, String roomID, String typeOfRoom, String dateTime) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String messageBody = "Xác nhận đặt phòng " + roomID + " (" + typeOfRoom + ") vào lúc " + dateTime +
                " tại Karaoke ROSIE. Vui lòng nhận phòng trong vòng 30 phút so với giờ đặt. Sau 30 phút phiếu đặt sẽ bị hủy. Xin cảm ơn!";

        Message message = Message.creator(
                            new PhoneNumber(toPhoneNumber),
                            new PhoneNumber(TWILIO_PHONE_NUMBER),
                            messageBody)
                    .create();

            System.out.println("Tin nhắn đã được gửi: " + message.getSid());

    }

    public static void main(String[] args) {
        sendConfirmationSMS("SỐ_ĐIỆN_THOẠI_KHÁCH_HÀNG", "PHÒNG_SỐ", "LOẠI PHÒNG", "NGÀY_GIỜ");
    }
}
