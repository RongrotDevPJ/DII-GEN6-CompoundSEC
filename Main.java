import Card.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HotelManagement hotelManager = new HotelManagement();
        Scanner scanner = new Scanner(System.in);

        GuestAccessCard guestCard = hotelManager.createGuestCard("Guest123", Arrays.asList("203"), LocalDateTime.now().plusDays(3));

        while (true) {
            System.out.println("\n1. Check Guest Access");
            System.out.println("2. Add Room Access");
            System.out.println("3. Remove Room Access");
            System.out.println("4. Revoke Card");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 5) break;

            switch (choice) {
                case 1:
                    System.out.print("Enter room number: ");
                    String room = scanner.nextLine();
                    int floor = Room.getFloor(room);
                    boolean granted = guestCard.validateAccess(floor, room);
                    System.out.println("Guest Access " + (granted ? "✅ Granted" : "❌ Denied"));
                    break;
                case 2:
                    System.out.print("Enter room number to add: ");
                    LogAdmin.addRoomToGuest(guestCard, scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Enter room number to remove: ");
                    LogAdmin.removeRoomFromGuest(guestCard, scanner.nextLine());
                    break;
                case 4:
                    guestCard.revokeAccess();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }
}
