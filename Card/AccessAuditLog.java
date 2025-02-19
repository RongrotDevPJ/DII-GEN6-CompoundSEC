package Card;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccessAuditLog {
    private static List<String> logEntries = new ArrayList<>();

    public static void logAccess(String cardID, int floor, String room, boolean granted) {
        String entry = "Log Entry: Card " + cardID + " attempted to access Floor " + floor + ", Room " + room +
                ". Access " + (granted ? "✅ Granted" : "❌ Denied") + " at " + LocalDateTime.now();
        logEntries.add(entry);
        System.out.println(entry);
    }

    public static void logCardAction(String cardID, String action) {
        String entry = "Log Entry: Card " + cardID + " was " + action + " at " + LocalDateTime.now();
        logEntries.add(entry);
        System.out.println(entry);
    }

    public static List<String> getLogs() {
        return logEntries;
    }
}
