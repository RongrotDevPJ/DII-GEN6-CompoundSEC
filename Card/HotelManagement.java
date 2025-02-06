import java.util.HashMap;
import java.util.Map;

public class HotelManagement {
    private Map<Integer, Room> rooms = new HashMap<>();
    private Map<Integer, AccessCard> cards = new HashMap<>();
    public void addRoom(Room room) {
        rooms.put(room.getRoomNumber(), room);
        System.out.println("Room " + room.getRoomNumber() + " added to the system.");
    }
    public void createCard(int cardID) {
        cards.put(cardID, new AccessCard(cardID));
        System.out.println("Access Card " + cardID + " created.");
    }
    public void grantAccess(int cardID, int roomNumber) {
        if (cards.containsKey(cardID) && rooms.containsKey(roomNumber)) {
            cards.get(cardID).addRoomAccess(roomNumber);
        } else {
            System.out.println("Invalid card ID or room number.");
        }
    }
    public void revokeAccess(int cardID, int roomNumber) {
        if (cards.containsKey(cardID)) {
            cards.get(cardID).revokeRoomAccess(roomNumber);
        } else {
            System.out.println("Invalid card ID.");
        }
    }
    public void accessRoom(int cardID, int roomNumber) {
        if (cards.containsKey(cardID) && rooms.containsKey(roomNumber)) {
            AccessCard card = cards.get(cardID);
            Room room = rooms.get(roomNumber);
            if (card.hasAccess(roomNumber)) {
                room.unlock();
                System.out.println("Card " + cardID + " used to unlock Room " + roomNumber + ".");
            } else {
                System.out.println("Access denied for Card " + cardID + " to Room " + roomNumber + ".");
            }
        } else {
            System.out.println("Invalid card ID or room number.");
        }
    }
}