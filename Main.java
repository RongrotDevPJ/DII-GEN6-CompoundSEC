import Card.*;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        HotelManagement hotel = new HotelManagement();

        Room room101 = new StandardRoom(101);
        Room room102 = new StandardRoom(102);
        hotel.addRoom(room101);
        hotel.addRoom(room102);


    }
}
