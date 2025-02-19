package Card;
import java.time.LocalDateTime;
import java.util.List;

public abstract class AccessCard {
    protected String cardID;
    protected String ownerName;
    protected List<Integer> accessFloors;
    protected List<String> accessRooms;
    protected LocalDateTime expirationTime;
    protected boolean isActive;

    public AccessCard(String cardID, String ownerName, List<Integer> accessFloors, List<String> accessRooms, LocalDateTime expirationTime) {
        this.cardID = cardID;
        this.ownerName = ownerName;
        this.accessFloors = accessFloors;
        this.accessRooms = accessRooms;
        this.expirationTime = expirationTime;
        this.isActive = true;
    }

    public abstract boolean validateAccess(int floor, String room);

    public void revokeAccess() {
        this.isActive = false;
        AccessAuditLog.logCardAction(cardID, "Revoked");
    }

    public String getCardID() {
        return cardID;
    }

    public boolean isActive() {
        return isActive;
    }
}
