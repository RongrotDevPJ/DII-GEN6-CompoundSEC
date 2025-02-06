package Card;
import java.util.*; // Array & List
public class AccessCard {
    private int cardID;
    private List<Integer> accessibleRoom;

    public AccessCard(int cardID) {
        this.cardID = cardID;
        this.accessibleRoom = new ArrayList<>();
    }
    public int getCardID(){
        return cardID;
    }
    public void addRoomAccess(int roomNumber){
        accessibleRoom.add(roomNumber);
        System.out.println("Card "+cardID+"granted access Room :"+roomNumber);
    }
    public void reRoomAccess(int roomNumber){
        accessibleRoom.remove(roomNumber);//*****
        System.out.println("Card "+cardID+"access remove Room :"+roomNumber);
    }
    public boolean hasAccess(int roomNumber){
        return accessibleRoom.contains(roomNumber);
    }
}
