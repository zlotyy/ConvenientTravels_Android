package mvc.com.model;


import java.io.Serializable;

public class BookingModel implements Serializable {

    private long bookingId;

    private boolean isConfirmed;

    // Polaczenie 1 User do N Rezerwacji
    private UserModel user;

    // Polaczenie 1 Przejazd do N Rezerwacji
    private DriveModel drive;





    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public DriveModel getDrive() {
        return drive;
    }

    public void setDrive(DriveModel drive) {
        this.drive = drive;
    }
}
