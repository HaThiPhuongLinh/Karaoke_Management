package Entity;

public class DetailsOfReservationForm {
    private ReservationForm reservationForm;
    private Room room;
    private int giaPhong;

    public DetailsOfReservationForm(ReservationForm reservationForm, Room room, int giaPhong) {
        this.reservationForm = reservationForm;
        this.room = room;
        this.giaPhong = giaPhong;
    }

    public ReservationForm getReservationForm() {
        return reservationForm;
    }

    public void setReservationForm(ReservationForm reservationForm) {
        this.reservationForm = reservationForm;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(int giaPhong) {
        this.giaPhong = giaPhong;
    }
}
