package Card;

public class LogAdmin {
    public static void addRoomToGuest(GuestAccessCard guestCard, String room) {
        if (Room.isValidRoom(room)) {
            guestCard.addRoomAccess(room);
            System.out.println("✅ Room " + room + " added to Guest Card: " + guestCard.getCardID());
        } else {
            System.out.println("❌ Invalid room number!");
        }
    }

    public static void removeRoomFromGuest(GuestAccessCard guestCard, String room) {
        guestCard.removeRoomAccess(room);
        System.out.println("🚫 Room " + room + " removed from Guest Card: " + guestCard.getCardID());
    }
}
