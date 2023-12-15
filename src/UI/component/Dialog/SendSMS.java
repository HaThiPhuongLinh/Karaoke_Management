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
    private static final String ACCOUNT_SID = "AC4a660966f8ce839f876e62e9f4302604";
    private static final String AUTH_TOKEN = "1ee4cc60e253db8d2c8c2da3db661cfc";
    private static final String TWILIO_PHONE_NUMBER = "+12056724591";

    /**
     * Gửi SMS đến số điện thoại
     * @param toPhoneNumber: sdt cần gửi
     * @param roomName: mã phòng đã đặt
     * @param typeOfRoom: loại phòng đã đặt
     * @param date: ngày đặt
     * @param startTime: giờ đặt
     */
    public static void sendConfirmationSMS(String toPhoneNumber, String cusName, String roomName, String typeOfRoom,  String date, String startTime) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String messageBody = "Chào " + cusName + ",\n" +
                "Phiếu đặt phòng của bạn tại Karaoke ROSIE đã được xác nhận!\n\n" +
                "Ngày đặt: " + date + "\n" +
                "Giờ đặt: " + startTime + "\n" +
                "Phòng hát: " + roomName + "\n" +
                "Loại phòng: " + typeOfRoom + "\n\n" +
                "Vui lòng nhận phòng trong vòng 30 phút so với giờ đặt. Sau 30 phút phiếu đặt sẽ bị hủy. Mọi thắc mắc xin liên hệ 0999.999.999\n\n" +
                "Trân trọng,\nKaraoke ROSIE";

        Message message = Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        messageBody)
                .create();

        System.out.println("Tin nhắn đã được gửi: " + message.getSid());
    }
}
