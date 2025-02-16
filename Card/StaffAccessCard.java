package Card;
import java.time.LocalDateTime;
import java.util.List;

public class StaffAccessCard extends AccessCard {
    public StaffAccessCard(String cardID, String ownerName, List<Integer> accessFloors, List<String> accessRooms, LocalDateTime expirationTime) {
        super(cardID, ownerName, accessFloors, accessRooms, expirationTime);
    }

    @Override
    public boolean validateAccess(int floor, String room) {
        boolean granted = isActive && LocalDateTime.now().isBefore(expirationTime) &&
                accessFloors.contains(floor) && accessRooms.contains(room);
        AccessAuditLog.logAccess(cardID, floor, room, granted);
        return granted;
    }
}
