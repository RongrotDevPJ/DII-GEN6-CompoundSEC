package Card;
import java.time.LocalDateTime;

public class GuestAccessCard extends AccessCard {
    private int roomNumber;
    private LocalDateTime expirationTime;

    public GuestAccessCard(int cardID, int roomNumber, LocalDateTime expirationTime) {
        super(cardID);
        this.roomNumber = roomNumber;
        this.expirationTime = expirationTime;
        addRoomAccess(roomNumber);
    }

    public boolean isValid() {
        return LocalDateTime.now().isBefore(expirationTime);
    }

    public void displayInfo() {
        System.out.println("Guest Access Card ID: " + getCardID());
        System.out.println("Assigned Room: " + roomNumber);
        System.out.println("Valid Until: " + expirationTime);
    }
}
