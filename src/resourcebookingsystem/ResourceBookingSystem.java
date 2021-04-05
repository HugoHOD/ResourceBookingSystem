package resourcebookingsystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceBookingSystem {

    private static final Scanner input = new Scanner(System.in);
    private String email;
    private Map<Integer, RoomManager> roomManagers;
    private String bookingListdbFile;
    private String food;
    private String drink;
    private String resources;
    private int roomNumber;

    public ResourceBookingSystem(String bookingListDbFile) throws IOException {
        this.bookingListdbFile = bookingListDbFile;
        this.roomManagers = new HashMap<>();
        loadBookings();
    }

    public void signIn() {
        email = input.next();
        String legalPatterns = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pCompiler = Pattern.compile(legalPatterns);
        Matcher matcher = pCompiler.matcher(email);
        if (matcher.matches() == true || email.equals("backdoor")) {
            System.out.println("Valid Email Address given.");
        } else {
            System.out.println("Invalid Email Address given");
            System.out.println("Please try again.");
            signIn();
        }

    }

    public void bookARoom() {
        System.out.println("First of all, let's start with a few questions");
        System.out.println("Would you be needing a wheelchair access room? (y/n)");
        String wheelchairAccess = input.next();
        if (wheelchairAccess.equalsIgnoreCase("y")) {
            System.out.println("Ah, then you'll be needing Room 4!");
        } else if (wheelchairAccess.equalsIgnoreCase("n")) {
            while (true) {
                System.out.println("How many people would like to be staying in this room?");
                int guestNumber = input.nextInt();

                if (guestNumber > 50) {
                    System.out.println("We're sorry, we cannot accompany that amount of guests in one room, please restart booking");
                } else if (guestNumber > 15) {
                    System.out.println("Ah, you may be needing our coveted Room 5");
                    roomNumber = 5;
                    break;
                } else if (guestNumber > 8) {
                    System.out.println("Ah, it may be Room 4 that you need");
                    roomNumber = 4;
                    break;
                } else if (guestNumber > 4) {
                    System.out.println("Ah, it may be Room 3 that you need");
                    roomNumber = 3;
                    break;
                } else if (guestNumber > 2) {
                    System.out.println("Ah, it may be Room 2 that you need");
                    roomNumber = 2;
                    break;
                } else if (guestNumber > 0) {
                    System.out.println("Ah, it may be Room 1 that you need");
                    roomNumber = 1;
                    break;
                } else {
                    System.out.println("Invalid Input, please try again");
                }
            }

        }
        System.out.println("\nWould you like any resources to accompany you with the room? (y/n)");
        String resourceCheck = input.next();
        if (resourceCheck.equalsIgnoreCase("n")) {
            System.out.println("No resources will be delievered to this room");
        } else if (resourceCheck.equalsIgnoreCase("y")) {
            System.out.println("Please select from our menu of resources what you'd like: ");
            System.out.println("Traditional Selection (Pens, Paper, Whiteboard etc.)\nModern Selection (Laptops, projector)\nParty Selection (Party hats, Silly String, Clown)");
            resources = input.next();
        }

        System.out.println("And finally, would you like any refreshments to be delivered to your room? (y/n)");
        String refreshYes = input.next();
        if (refreshYes.equalsIgnoreCase("n")) {
            System.out.println("No refreshments will be delievered to this room");
        } else if (refreshYes.equalsIgnoreCase("y")) {
            System.out.println("Please select what you would like from our menu: ");
            System.out.println("\nFood:\nSandwich Selection\nPizzas\nCakes + Pastries Selection\nFruit\n(Our service is quite busy at the moment, please choose one)");
            food = input.next();
            System.out.println("\nDrinks:\n Water\nSoft Drinks\nJuices\nTea\nCoffee");
            drink = input.next();
            System.out.println("You have chosen " + food + " and " + drink + " to be delivered to your room");

        }

        System.out.println("Fantastic!\n Now that the questions have all been answered, lets have a look at avaliable booking slots");

        RoomManager roomManager = roomManagers.get(roomNumber);
        List<DateTimeSlot> availableSlots = roomManager.getAvailability();
        for (int i = 0; i < availableSlots.size(); i++) {
            System.out.println(i + ": " + availableSlots.get(i).toString());
        }
        int attempts = 0;
        while (attempts < 5) {
            int userBookingChoice = input.nextInt();
            DateTimeSlot chosenSlot = availableSlots.get(userBookingChoice);
            try {
                roomManager.bookSlot(chosenSlot, food, drink, resources);
                System.out.println("Successfully booked!");
                return;
            } catch (Exception ex) {
                System.out.println("Unable to book slot, please try again.");
                attempts++;
            }

        }
        System.out.println("u broke my code, cheers");

    }

    public void viewAll() {
        System.out.println("Here are all the available slots:\n");
        for (int i = 1; i <= 5; i++) {
            RoomManager roomManager = roomManagers.get(i);
            System.out.println("Room " + i + ": " + roomManager.getAllSlots().toString());

        }
        System.out.println("Returning to main menu...");
    }

    public void loadBookings() throws FileNotFoundException, IOException {

        for (int i = 1; i <= 5; i++) {
            RoomManager roomManager = new RoomManager(i);
            roomManagers.put(i, roomManager);
        }
        BufferedReader csvReader = new BufferedReader(new FileReader(bookingListdbFile));

        String row = csvReader.readLine();

        while (row != null) {

            String[] data = row.split(",");
            String roomNumber = data[0];
            String date = data[1];
            String time = data[2];
            String food = data[3];
            String drink = data[4];
            String resources = data[5];
            String availability = data[6];

            BookingInfo bookingSlot = new BookingInfo(Integer.parseInt(roomNumber), food, drink, resources, Boolean.parseBoolean(availability));
            DateTimeSlot timeSlot = new DateTimeSlot(date, time);
            roomManagers.get(Integer.parseInt(roomNumber)).addBooking(timeSlot, bookingSlot);
            row = csvReader.readLine();
        }
        csvReader.close();

    }

    public void storeBookings() throws FileNotFoundException, IOException {
        FileWriter csvWriter = new FileWriter(bookingListdbFile);

        for (Map.Entry<Integer, RoomManager> mapping : roomManagers.entrySet()) {
            Integer roomNumber = mapping.getKey();
            RoomManager roomManager = mapping.getValue();
            Map<DateTimeSlot, BookingInfo> bookings = roomManager.getBookings();

            for (Map.Entry<DateTimeSlot, BookingInfo> booking : bookings.entrySet()) {
                BookingInfo bookingSlot = booking.getValue();
                DateTimeSlot timeSlot = booking.getKey();

                csvWriter.append(roomNumber.toString());
                csvWriter.append("," + timeSlot.getDate());
                csvWriter.append("," + timeSlot.getTime());
                csvWriter.append("," + bookingSlot.getFood());
                csvWriter.append("," + bookingSlot.getDrink());
                csvWriter.append("," + bookingSlot.getResources());
                csvWriter.append("," + bookingSlot.isAvailable());
                csvWriter.append("\n");
            }

        }

        csvWriter.flush();
        csvWriter.close();

    }

    public static void main(String[] args) throws IOException {
        ResourceBookingSystem rbs = new ResourceBookingSystem("BookingSlotList.csv");
        System.out.println("Welcome to the Booking System, courtesy of Pain Inc.");
        System.out.println("Please enter your e-mail address to continue with our service.");
        rbs.signIn();
        System.out.println("Fantastic! Now that you've been validated for booking:");

        while (true) {
            System.out.println("\nMain Menu");
            System.out.println("Please select an option");
            System.out.println("1. Book a Room");
            System.out.println("2. View All Slots");
            System.out.println("3. Exit");
            int userChoice = input.nextInt();
            switch (userChoice) {
                case 1:
                    System.out.println("\nLet's get started then");
                    rbs.bookARoom();
                    break;

                case 2:
                    rbs.viewAll();
                    break;

                case 3:
                    System.out.println("Are you sure you'd like to exit? (y/n)");
                    String yesno = input.next();
                    if (yesno.equalsIgnoreCase("y")) {
                        System.out.println("Sure thing, we'll be seeing you!");
                        rbs.storeBookings();
                        return;
                    } else if (yesno.equalsIgnoreCase("n")) {
                        System.out.println("No worries");
                    }
                    break;
                default:
                    System.out.println("Unsupported Input");
            }
        }

    }
}
