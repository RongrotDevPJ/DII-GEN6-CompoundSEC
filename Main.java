import Card.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HotelManagement hotelManager = new HotelManagement();
        Scanner scanner = new Scanner(System.in);

        // สร้างบัตรสำหรับแขก
        GuestAccessCard guestCard = hotelManager.createGuestCard("Guest123", Arrays.asList("203"), LocalDateTime.now().plusDays(3));

        while (true) {
            System.out.println("\n1. Check Guest Access");
            System.out.println("2. Add Room Access");
            System.out.println("3. Remove Room Access");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            if (choice == 4) break;

            switch (choice) {
                case 1: // ตรวจสอบการเข้าถึง
                    System.out.print("Enter room number: ");
                    String room = scanner.nextLine();
                    int floor = Room.getFloor(room);
                    boolean granted = guestCard.validateAccess(floor, room);
                    System.out.println("Guest Access " + (granted ? "✅ Granted" : "❌ Denied"));
                    break;
                case 2: // เพิ่มสิทธิ์ห้องให้ Guest
                    System.out.print("Enter room number to add: ");
                    String addRoom = scanner.nextLine();
                    LogAdmin.addRoomToGuest(guestCard, addRoom);
                    break;
                case 3: // ลบสิทธิ์ห้องของ Guest
                    System.out.print("Enter room number to remove: ");
                    String removeRoom = scanner.nextLine();
                    LogAdmin.removeRoomFromGuest(guestCard, removeRoom);
                    break;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }

        scanner.close();
    }
}
