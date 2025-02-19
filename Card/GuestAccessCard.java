package Card;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GuestAccessCard extends AccessCard {
    public GuestAccessCard(String cardID, String ownerName, List<String> accessRooms, LocalDateTime expirationTime) {
        super(cardID, ownerName, List.of(), new ArrayList<>(accessRooms), expirationTime);
    }

    @Override
    public boolean validateAccess(int floor, String room) {
        boolean granted = isActive && LocalDateTime.now().isBefore(expirationTime) && accessRooms.contains(room);
        AccessAuditLog.logAccess(cardID, floor, room, granted);
        return granted;
    }

    public void addRoomAccess(String room) {
        if (!accessRooms.contains(room)) {
            accessRooms.add(room);
            AccessAuditLog.logCardAction(cardID, "Added access to Room " + room);
        }
    }

    public void removeRoomAccess(String room) {
        if (accessRooms.contains(room)) {
            accessRooms.remove(room);
            AccessAuditLog.logCardAction(cardID, "Removed access to Room " + room);
        }
    }
}
