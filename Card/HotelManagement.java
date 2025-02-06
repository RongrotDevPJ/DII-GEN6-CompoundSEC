import java.util.*;
import java.time.LocalDateTime;

public class HotelManagement {
    private Map<Integer, Room> rooms = new HashMap<>();
    private Map<Integer, AccessCard> cards = new HashMap<>();

    public void addRoom(Room room) {
        rooms.put(room.getRoomNumber(), room);
        System.out.println("Room " + room.getRoomNumber() + " added to the system.");
    }
    public void createGuestCard(int cardID, int roomNumber, int hoursValid) {
        if (rooms.containsKey(roomNumber)) {
            LocalDateTime expirationTime = LocalDateTime.now().plusHours(hoursValid);
            GuestAccessCard guestCard = new GuestAccessCard(cardID, roomNumber, expirationTime);
            cards.put(cardID, guestCard);
            System.out.println("Guest Access Card " + cardID + " created for Room " + roomNumber);
        } else {
            System.out.println("Invalid room number.");
        }
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
            if (card instanceof GuestAccessCard) {
                GuestAccessCard guestCard = (GuestAccessCard) card;
                if (!guestCard.isValid()) {
                    System.out.println("Access denied! Card expired.");
                    return;
                }
            }
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
