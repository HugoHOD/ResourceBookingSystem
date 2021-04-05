package resourcebookingsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomManager {

    public Map<DateTimeSlot, BookingInfo> bookings;
    private int roomNumber;

    public RoomManager(int roomNumber) {
        this.roomNumber = roomNumber;
        this.bookings = new HashMap<>();
    }

    public List<DateTimeSlot> getAvailability() {
        List<DateTimeSlot> availableSlots = new ArrayList<>();

        for (Map.Entry<DateTimeSlot, BookingInfo> bookingSlot : bookings.entrySet()) {
            DateTimeSlot dateTime = bookingSlot.getKey();
            BookingInfo bookingInfo = bookingSlot.getValue();
            if (bookingInfo.isAvailable()) {
                availableSlots.add(dateTime);
            }
        }
        return availableSlots;

    }

    public List<DateTimeSlot> getAllSlots() {
        List<DateTimeSlot> allSlots = new ArrayList<>();
        for (Map.Entry<DateTimeSlot, BookingInfo> bookingSlot : bookings.entrySet()) {
            DateTimeSlot dateTime = bookingSlot.getKey();
            BookingInfo bookingInfo = bookingSlot.getValue();
                allSlots.add(dateTime);
            
        }
        return allSlots;

    }
    
    
    
    
    public Map<DateTimeSlot, BookingInfo> getBookings() {
        return bookings;
    }

    
    
    
    public void bookSlot(DateTimeSlot slot, String food, String drink, String resources) throws Exception {
        BookingInfo info = this.bookings.get(slot); 
        
        if (!info.isAvailable()) {
            throw new Exception("Slot already booked."); 
        }
        info.setAvailable(false);
        info.setFood(food);
        info.setDrink(drink);
        info.setResources(resources);
        bookings.put(slot, info);
    
    
    }

    public void addBooking(DateTimeSlot timeSlot, BookingInfo bookingSlot) {
        bookings.put(timeSlot, bookingSlot);
    }

}
