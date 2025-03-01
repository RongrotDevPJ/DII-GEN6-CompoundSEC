import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

// Draft 1: Abstraction - ใช้ Abstract Class กำหนดโครงสร้างของบัตร
abstract class Card {
// Draft 2: Encapsulation - ซ่อนข้อมูลสำคัญ ไม่ให้แก้ไขโดยตรง
    protected List<String> cardIds; // รายการ ID ของบัตร
    protected String userId; // ID ของผู้ใช้
    private boolean active; // สถานะของบัตร (เปิดหรือปิด)
    protected LocalDateTime validFrom; // เวลาที่บัตรเริ่มใช้งาน
    protected LocalDateTime validUntil; // เวลาที่บัตรหมดอายุ
    protected LocalTime decryptionStartTime; // เวลาที่เริ่มสามารถถอดรหัสได้
    protected LocalTime decryptionEndTime; // เวลาที่สิ้นสุดการถอดรหัส

    // คอนสตรัคเตอร์สำหรับสร้างบัตร
    public Card(List<String> cardIds, String userId, LocalDateTime validFrom, LocalDateTime validUntil, LocalTime decryptionStartTime, LocalTime decryptionEndTime) {
        this.cardIds = cardIds;
        this.userId = userId;
        this.active = true; // บัตรเริ่มต้นเป็นสถานะเปิด
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.decryptionStartTime = decryptionStartTime;
        this.decryptionEndTime = decryptionEndTime;
    }

    // Draft 4: Polymorphism - เมธอดนี้จะถูก override ในคลาสลูก
    public abstract boolean hasAccess(String floor, String room);

    // เมธอดสำหรับยกเลิกการเข้าถึง
    public void revokeAccess() {
        this.active = false;
    }

    // Draft 2: Encapsulation - ใช้ Getter เพื่อควบคุมการเข้าถึงข้อมูล
    public List<String> getCardIds() {
        return cardIds;
    }

    // เช็คสถานะการใช้งานของบัตร
    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return active && now.isAfter(validFrom) && now.isBefore(validUntil); // เช็คว่าเวลาในปัจจุบันอยู่ในช่วงเวลาที่บัตรใช้งานได้
    }

    // เช็คว่าเวลาปัจจุบันอยู่ในช่วงเวลาที่สามารถถอดรหัสได้หรือไม่
    public boolean isWithinDecryptionTime() {
        LocalTime now = LocalTime.now();
        return now.isAfter(decryptionStartTime) && now.isBefore(decryptionEndTime);
    }
}

// Draft 5: Method Overloading - รองรับการสร้างบัตรหลายแบบ
class AccessCard extends Card {
    private List<String> allowedFloors; // รายการชั้นที่สามารถเข้าได้
    private List<String> allowedRooms; // รายการห้องที่สามารถเข้าได้

    // คอนสตรัคเตอร์สำหรับสร้าง AccessCard
    public AccessCard(List<String> cardIds, String userId, LocalDateTime validFrom, LocalDateTime validUntil, LocalTime decryptionStartTime, LocalTime decryptionEndTime) {
        super(cardIds, userId, validFrom, validUntil, decryptionStartTime, decryptionEndTime);
        this.allowedFloors = new ArrayList<>();
        this.allowedRooms = new ArrayList<>();
    }

    // Draft 6: Dynamic Binding - เมธอดนี้จะถูกเรียกเมื่อใช้ Polymorphism
    @Override
    public boolean hasAccess(String floor, String room) {
        return isActive() && isWithinDecryptionTime() && allowedFloors.contains(floor) && allowedRooms.contains(room);
    }

    // เมธอดสำหรับเพิ่มชั้นที่อนุญาตให้เข้า
    public void addFloor(String floor) {
        allowedFloors.add(floor);
    }

    // เมธอดสำหรับเพิ่มห้องที่อนุญาตให้เข้า
    public void addRoom(String room) {
        allowedRooms.add(room);
    }
}

// Draft 3: Upcasting - ใช้ Map<String, Card> แทน AccessCard เพื่อรองรับบัตรอื่น ๆ
class AccessControlSystem {
    private Map<String, Card> cardDatabase; // ฐานข้อมูลของบัตรทั้งหมด
    private List<String> accessLog; // รายการบันทึกการเข้าถึง
    public void revokeCard(String cardId) {
        Card card = cardDatabase.getOrDefault(cardId, null); // ค้นหาบัตรในฐานข้อมูล
        if (card == null) {
            System.out.println("Error: Card not found!"); // ถ้าบัตรไม่พบ
            return;
        }
        card.revokeAccess(); // ยกเลิกการเข้าถึงของบัตร
        logAccess("Card revoked: " + cardId); // บันทึกการยกเลิกบัตร
    }
    // คอนสตรัคเตอร์สำหรับสร้างระบบการเข้าถึง
    public AccessControlSystem() {
        cardDatabase = new HashMap<>();
        accessLog = new ArrayList<>();
    }

    // Draft 12: Factory Pattern - ใช้ Factory สร้างบัตรใหม่ โดยใช้ validFrom/validUntil ที่ระบุโดยผู้ใช้
    public void addCard(String type, List<String> cardIds, String userId, LocalTime decryptionStartTime, LocalTime decryptionEndTime) {
        Card card = CardFactory.createCard(type, cardIds, userId, decryptionStartTime, decryptionEndTime);
        for (String id : cardIds) {
            cardDatabase.put(id, card); // เพิ่มบัตรในฐานข้อมูล
        }
        logAccess("Card created: " + cardIds + " with decryption time " + decryptionStartTime + "-" + decryptionEndTime); // บันทึกการสร้างบัตร
    }

    // เมธอดสำหรับพยายามเข้าถึง
    public void attemptAccess(String cardId, String floor, String room) {
        Card card = cardDatabase.getOrDefault(cardId, null); // ค้นหาบัตรในฐานข้อมูล
        if (card == null) {
            System.out.println("Error: Card not found!"); // ถ้าบัตรไม่พบ
            return;
        }
        if (!card.isWithinDecryptionTime()) {
            System.out.println("Error: Cannot decrypt card outside allowed time (" + card.decryptionStartTime + "-" + card.decryptionEndTime + ")");
            return;
        }
        boolean success = card.hasAccess(floor, room); // เช็คสิทธิ์การเข้าถึง
        logAccess("Access attempt: " + cardId + " to " + floor + ", " + room + " - " + (success ? "Success" : "Failed")); // บันทึกการพยายามเข้าถึง
    }

    // เมธอดสำหรับแก้ไขบัตร
    public void modifyCard(String cardId) {
        Card card = cardDatabase.getOrDefault(cardId, null);
        if (card == null) {
            System.out.println("Error: Card not found!"); // ถ้าบัตรไม่พบ
            return;
        }

        if (card instanceof AccessCard) { // เช็คว่าบัตรเป็น AccessCard หรือไม่
            AccessCard accessCard = (AccessCard) card;
            Scanner scanner = new Scanner(System.in);

            // เพิ่มชั้น
            System.out.print("Enter Floor to add: ");
            String floor = scanner.nextLine();
            accessCard.addFloor(floor);

            // เพิ่มห้อง
            System.out.print("Enter Room to add: ");
            String room = scanner.nextLine();
            accessCard.addRoom(room);

            logAccess("Card modified: " + cardId); // บันทึกการแก้ไขบัตร
        } else {
            System.out.println("Error: Invalid card type for modification.");
        }
    }

    // เมธอดสำหรับพิมพ์บันทึกการเข้าถึง
    public void printAccessLog() {
        System.out.println("Access Logs:");
        for (String log : accessLog) {
            System.out.println(log);
        }
    }

    // เมธอดสำหรับบันทึกการเข้าถึง
    private void logAccess(String message) {
        accessLog.add(message);
        System.out.println(message);
    }
}

// Draft 12: Factory Pattern - สร้างบัตรประเภทต่าง ๆ โดยรับ validFrom/validUntil จากผู้ใช้
class CardFactory {
    public static Card createCard(String type, List<String> cardIds, String userId, LocalTime decryptionStartTime, LocalTime decryptionEndTime) {
        LocalDateTime validFrom = LocalDateTime.now();
        LocalDateTime validUntil = validFrom.plusHours(1);
        return switch (type) {
            case "AccessCard" -> new AccessCard(cardIds, userId, validFrom, validUntil, decryptionStartTime, decryptionEndTime);
            default -> throw new IllegalArgumentException("Unknown card type"); // ถ้าประเภทบัตรไม่รู้จัก
        };
    }
}
// Draft 11: Use Case Diagram - ฟังก์ชันหลักในระบบ (Main class)
public class Main {
    public static void main(String[] args) {
        AccessControlSystem system = new AccessControlSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // เมนูหลัก
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
                        // เพิ่มบัตรใหม่
                        System.out.print("Enter Card Type: ");
                        String type = scanner.nextLine();
                        System.out.print("Enter Card IDs (comma-separated): ");
                        List<String> newCardIds = Arrays.asList(scanner.nextLine().split(","));
                        System.out.print("Enter User ID: ");
                        String userId = scanner.nextLine();
                        System.out.print("Set Decryption Time Range (HH:mm-HH:mm): ");
                        String[] timeRange = scanner.nextLine().split("-");
                        LocalTime start = LocalTime.parse(timeRange[0]);
                        LocalTime end = LocalTime.parse(timeRange[1]);
                        system.addCard(type, newCardIds, userId, start, end);
                        break;
                    case 2:
                        // แก้ไขบัตร
                        System.out.print("Enter Card ID to modify: ");
                        String cardIdToModify = scanner.nextLine();
                        system.modifyCard(cardIdToModify);
                        break;
                    case 3:
                        // ยกเลิกบัตร
                        System.out.print("Enter Card ID to revoke: ");
                        String cardIdToRevoke = scanner.nextLine();
                        system.revokeCard(cardIdToRevoke);
                        break;
                    case 4:
                        // พยายามเข้าถึง
                        System.out.print("Enter Card ID to access: ");
                        String cardIdToAccess = scanner.nextLine();
                        System.out.print("Enter Floor: ");
                        String accessFloor = scanner.nextLine();
                        System.out.print("Enter Room: ");
                        String accessRoom = scanner.nextLine();
                        system.attemptAccess(cardIdToAccess, accessFloor, accessRoom);
                        break;
                    case 5:
                        // แสดงบันทึกการเข้าถึง
                        system.printAccessLog();
                        break;
                    case 6:
                        // ออกจากระบบ
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
