package Card;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private static final Map<String, Integer> rooms = new HashMap<>();

    static {
        rooms.put("101", 1);
        rooms.put("102", 1);
        rooms.put("103", 1);
        rooms.put("201", 2);
        rooms.put("202", 2);
        rooms.put("203", 2);
        rooms.put("301", 3);
        rooms.put("302", 3);
        rooms.put("303", 3);
    }

    public static Integer getFloor(String roomID) {
        return rooms.get(roomID);
    }

    public static boolean isValidRoom(String roomID) {
        return rooms.containsKey(roomID);
    }
}
