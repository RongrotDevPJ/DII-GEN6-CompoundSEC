package Card;
import java.time.LocalDateTime;
import java.util.*;

public class HotelManagement {
    private Map<String, AccessCard> cards = new HashMap<>();

    public StaffAccessCard createStaffCard(String ownerName, List<Integer> floors, List<String> rooms, LocalDateTime expiration) {
        String cardID = UUID.randomUUID().toString();
        StaffAccessCard newCard = new StaffAccessCard(cardID, ownerName, floors, rooms, expiration);
        cards.put(cardID, newCard);
        AccessAuditLog.logCardAction(cardID, "Staff Card Created");
        return newCard;
    }

    public GuestAccessCard createGuestCard(String ownerName, List<String> rooms, LocalDateTime expiration) {
        String cardID = UUID.randomUUID().toString();
        GuestAccessCard newCard = new GuestAccessCard(cardID, ownerName, rooms, expiration);
        cards.put(cardID, newCard);
        AccessAuditLog.logCardAction(cardID, "Guest Card Created");
        return newCard;
    }

    public void modifyCard(String cardID, List<Integer> newFloors, List<String> newRooms, LocalDateTime newExpiration) {
        AccessCard card = cards.get(cardID);
        if (card != null) {
            card.accessFloors = newFloors;
            card.accessRooms = newRooms;
            card.expirationTime = newExpiration;
            AccessAuditLog.logCardAction(cardID, "Modified");
        }
    }

    public void revokeCard(String cardID) {
        AccessCard card = cards.get(cardID);
        if (card != null) {
            card.revokeAccess();
            AccessAuditLog.logCardAction(cardID, "Revoked");
        }
    }

    public AccessCard getCard(String cardID) {
        return cards.get(cardID);
    }
}
