import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Abstract class for common properties and methods
abstract class Card {
    protected String cardId;
    protected String userId;
    protected boolean active;

    public Card(String cardId, String userId) {
        this.cardId = cardId;
        this.userId = userId;
        this.active = true;
    }

    public abstract boolean hasAccess(String floor, String room);

    public void revokeAccess() {
        this.active = false;
    }

    public String getCardId() {
        return cardId;
    }

    public boolean isActive() {
        return active;
    }
}

// Concrete class for AccessCard with floor and room access
class AccessCard extends Card {
    private List<String> allowedFloors;
    private List<String> allowedRooms;

    public AccessCard(String cardId, String userId) {
        super(cardId, userId);
        this.allowedFloors = new ArrayList<>();
        this.allowedRooms = new ArrayList<>();
    }

    public void addFloor(String floor) {
        allowedFloors.add(floor);
    }

    public void addRoom(String room) {
        allowedRooms.add(room);
    }

    @Override
    public boolean hasAccess(String floor, String room) {
        return active && allowedFloors.contains(floor) && allowedRooms.contains(room);
    }
}

// Access Control System class to manage cards and logs
class AccessControlSystem {
    private Map<String, AccessCard> cardDatabase;
    private List<String> accessLog;

    public AccessControlSystem() {
        cardDatabase = new HashMap<>();
        accessLog = new ArrayList<>();
    }

    public void addCard(String cardId, String userId) {
        AccessCard card = new AccessCard(cardId, userId);
        cardDatabase.put(cardId, card);
        logAccess("Card created: " + cardId);
    }

    public void modifyCard(String cardId, String floor, String room) {
        AccessCard card = cardDatabase.get(cardId);
        if (card != null) {
            card.addFloor(floor);
            card.addRoom(room);
            logAccess("Card modified: " + cardId);
        } else {
            System.out.println("Card not found!");
        }
    }

    public void revokeCard(String cardId) {
        AccessCard card = cardDatabase.get(cardId);
        if (card != null) {
            card.revokeAccess();
            logAccess("Card revoked: " + cardId);
        } else {
            System.out.println("Card not found!");
        }
    }

    public void attemptAccess(String cardId, String floor, String room) {
        AccessCard card = cardDatabase.get(cardId);
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

// Main class to run the program
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
                        System.out.print("Enter Card ID: ");
                        String newCardId = scanner.nextLine();
                        System.out.print("Enter User ID: ");
                        String userId = scanner.nextLine();
                        system.addCard(newCardId, userId);
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
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}
