package resourcebookingsystem;


public class BookingInfo {
    
    private int roomNumber;
    private String food;
    private String drink;
    private String resources;
    private boolean available;

    public BookingInfo(int roomNumber, String food, String drink, String resources, boolean available) {
        this.roomNumber = roomNumber;
        this.food = food;
        this.drink = drink;
        this.resources = resources;
        this.available = available;
    }
    
    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
    
    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
