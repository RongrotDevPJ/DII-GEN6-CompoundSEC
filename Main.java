import java.time.LocalDateTime;
import java.util.*;

// Draft 1: Abstraction - ใช้ Abstract Class กำหนดโครงสร้างของบัตร
abstract class Card {
    // Draft 2: Encapsulation - ซ่อนข้อมูลสำคัญ ไม่ให้แก้ไขโดยตรง
    protected List<String> cardIds; // Multi-facades ID
    protected String userId;
    private boolean active;
    protected LocalDateTime expirationTime; // Time-based encryption

    public Card(List<String> cardIds, String userId, LocalDateTime expirationTime) {
        this.cardIds = cardIds;
        this.userId = userId;
        this.active = true;
        this.expirationTime = expirationTime;
    }

    // Draft 4: Polymorphism - เมธอดนี้จะถูก override ในคลาสลูก
    public abstract boolean hasAccess(String floor, String room);

    public void revokeAccess() {
        this.active = false;
    }

    // Draft 2: Encapsulation - ใช้ Getter เพื่อควบคุมการเข้าถึงข้อมูล
    public List<String> getCardIds() {
        return cardIds;
    }

    public boolean isActive() {
        return active && LocalDateTime.now().isBefore(expirationTime);
    }
}

// Draft 5: Method Overloading - รองรับการสร้างบัตรหลายแบบ
class AccessCard extends Card {
    private List<String> allowedFloors;
    private List<String> allowedRooms;

    public AccessCard(List<String> cardIds, String userId, LocalDateTime expirationTime) {
        super(cardIds, userId, expirationTime);
        this.allowedFloors = new ArrayList<>();
        this.allowedRooms = new ArrayList<>();
    }

    public AccessCard(String cardId, String userId, LocalDateTime expirationTime) {
        this(List.of(cardId), userId, expirationTime);
    }

    public void addFloor(String floor) {
        allowedFloors.add(floor);
    }

    public void addRoom(String room) {
        allowedRooms.add(room);
    }

    // Draft 6: Dynamic Binding - เมธอดนี้จะถูกเรียกเมื่อใช้ Polymorphism
    @Override
    public boolean hasAccess(String floor, String room) {
        return isActive() && allowedFloors.contains(floor) && allowedRooms.contains(room);
    }
}

// Draft 3: Upcasting - ใช้ Map<String, Card> แทน AccessCard เพื่อรองรับบัตรอื่น ๆ
class AccessControlSystem {
    private Map<String, Card> cardDatabase;
    private List<String> accessLog;

    public AccessControlSystem() {
        cardDatabase = new HashMap<>();
        accessLog = new ArrayList<>();
    }

    // Draft 12: Factory Pattern - ใช้ Factory สร้างบัตรใหม่
    public void addCard(String type, List<String> cardIds, String userId, int validDays) {
        Card card = CardFactory.createCard(type, cardIds, userId, validDays);
        for (String id : cardIds) {
            cardDatabase.put(id, card);
        }
        logAccess("Card created: " + cardIds);
    }

    public void modifyCard(String cardId, String floor, String room) {
        Card card = cardDatabase.get(cardId);
        if (card instanceof AccessCard) {
            ((AccessCard) card).addFloor(floor);
            ((AccessCard) card).addRoom(room);
            logAccess("Card modified: " + cardId);
        } else {
            System.out.println("Card not found!");
        }
    }

    public void revokeCard(String cardId) {
        Card card = cardDatabase.get(cardId);
        if (card != null) {
            card.revokeAccess();
            logAccess("Card revoked: " + cardId);
        } else {
            System.out.println("Card not found!");
        }
    }

    public void attemptAccess(String cardId, String floor, String room) {
        Card card = cardDatabase.get(cardId);
        boolean success = card != null && card.hasAccess(floor, room);
        logAccess("Access attempt: " + cardId + " to " + floor + ", " + room + " - " + (success ? "Success" : "Failed"));
    }

    private void logAccess(String message) {
        accessLog.add(message);
        System.out.println(message);
    }

    public void printAccessLog() {
        System.out.println("Access Logs:");
        for (String log : accessLog) {
            System.out.println(log);
        }
    }
}

// Draft 12: Factory Pattern - สร้างบัตรประเภทต่าง ๆ
class CardFactory {
    public static Card createCard(String type, List<String> cardIds, String userId, int validDays) {
        LocalDateTime expirationTime = LocalDateTime.now().plusDays(validDays);
        return switch (type) {
            case "AccessCard" -> new AccessCard(cardIds, userId, expirationTime);
            default -> throw new IllegalArgumentException("Unknown card type");
        };
    }
}


// Draft 11: Use Case Diagram - ฟังก์ชันหลักในระบบ
public class Main {
    public static void main(String[] args) {
        AccessControlSystem system = new AccessControlSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nAccess Control System Menu:");
            System.out.println("1. Add Card");
            System.out.println("2. Modify Card");
            System.out.println("3. Revoke Card");
            System.out.println("4. Attempt Access");
            System.out.println("5. View Access Logs");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter Card Type: ");
                        String type = scanner.nextLine();
                        System.out.print("Enter Card IDs (comma-separated): ");
                        List<String> newCardIds = Arrays.asList(scanner.nextLine().split(","));
                        System.out.print("Enter User ID: ");
                        String userId = scanner.nextLine();
                        System.out.print("Enter Validity (days): ");
                        int validDays = Integer.parseInt(scanner.nextLine());
                        system.addCard(type, newCardIds, userId, validDays);
                        break;
                    case 2:
                        System.out.print("Enter Card ID to modify: ");
                        String cardIdToModify = scanner.nextLine();
                        System.out.print("Enter Floor: ");
                        String floor = scanner.nextLine();
                        System.out.print("Enter Room: ");
                        String room = scanner.nextLine();
                        system.modifyCard(cardIdToModify, floor, room);
                        break;
                    case 3:
                        System.out.print("Enter Card ID to revoke: ");
                        String cardIdToRevoke = scanner.nextLine();
                        system.revokeCard(cardIdToRevoke);
                        break;
                    case 4:
                        System.out.print("Enter Card ID to access: ");
                        String cardIdToAccess = scanner.nextLine();
                        System.out.print("Enter Floor: ");
                        String accessFloor = scanner.nextLine();
                        System.out.print("Enter Room: ");
                        String accessRoom = scanner.nextLine();
                        system.attemptAccess(cardIdToAccess, accessFloor, accessRoom);
                        break;
                    case 5:
                        system.printAccessLog();
                        break;
                    case 6:
                        System.out.println("Exiting the system.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
