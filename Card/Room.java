package Card;

public abstract class Room {
    protected int roomNumber;
    protected boolean isLocked;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isLocked = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isLocked() {
        return isLocked();
    }

    public void lock() {
        isLocked = true;
        System.out.println("Room " + roomNumber + " is locked");
    }

    public void unlock() {
        isLocked = false;
        System.out.println("Room " + roomNumber + " is unlocked");
    }

    public abstract void displayRoominfo();
}
