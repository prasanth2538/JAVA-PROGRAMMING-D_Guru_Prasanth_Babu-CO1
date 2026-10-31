package parking.app;

import parking.model.*;
import parking.service.ParkingFacility;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final Scanner SC = new Scanner(System.in);
    private static final ParkingFacility FACILITY = new ParkingFacility(new int[]{5, 6, 4});

    public static void main(String[] args) {
        System.out.println("===== CAMPUS SMART PARKING SYSTEM =====");
        boolean running = true;
        while (running) {
            try {
                System.out.println("\n1. Vehicle Entry\n2. Vehicle Exit\n3. Display Parking Status\n4. Exit");
                int choice = readInt("Choose: ");
                switch (choice) {
                    case 1 -> vehicleEntry();
                    case 2 -> vehicleExit();
                    case 3 -> FACILITY.displayStatus();
                    case 4 -> running = false;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (RuntimeException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
        System.out.println("Thank you for using Smart Parking System.");
    }

    private static void vehicleEntry() {
        System.out.println("Vehicle type: 1.TwoWheeler 2.Car 3.ElectricCar 4.EBike 5.DeliveryVan");
        int type = readInt("Select vehicle type: ");
        String reg = readLine("Registration number: ");
        Vehicle vehicle = switch (type) {
            case 1 -> new TwoWheeler(reg);
            case 2 -> new Car(reg);
            case 3 -> new ElectricCar(reg);
            case 4 -> new EBike(reg);
            case 5 -> new DeliveryVan(reg);
            default -> throw new IllegalArgumentException("Unknown vehicle type.");
        };

        System.out.println("Customer type: 1.Student 2.Staff 3.Visitor");
        int customerType = readInt("Select customer type: ");
        String id = readLine("Customer ID: ");
        String name = readLine("Customer name: ");
        Customer customer;
        if (customerType == 1) {
            LocalDate expiry = readDate("Monthly pass expiry (YYYY-MM-DD), or 2000-01-01 if no valid pass: ");
            customer = new Student(id, name, expiry);
        } else if (customerType == 2) {
            LocalDate expiry = readDate("Monthly pass expiry (YYYY-MM-DD), or 2000-01-01 if no valid pass: ");
            customer = new Staff(id, name, expiry);
        } else if (customerType == 3) {
            String pass = readLine("One-day pass date (YYYY-MM-DD), or 2000-01-01 if no pass: ");
            customer = new Visitor(id, name, LocalDate.parse(pass));
        } else {
            throw new IllegalArgumentException("Unknown customer type.");
        }

        ParkingTicket ticket = FACILITY.vehicleEntry(vehicle, customer, LocalDateTime.now());
        System.out.println("Vehicle parked successfully.");
        System.out.println("Ticket number: " + ticket.getTicketNumber());
        System.out.println("Floor: " + (ticket.getFloor() + 1) + ", Slot: " + (ticket.getSlot() + 1));
        System.out.println("Entry time: " + ticket.getEntryTime());
    }

    private static void vehicleExit() {
        String reg = readLine("Vehicle registration number: ");
        int kWh = readInt("Charging units in kWh (enter 0 for non-EV): ");
        double fee = FACILITY.vehicleExit(reg, LocalDateTime.now(), kWh);
        System.out.printf("Total amount: Rs %.2f%n", fee);
        System.out.println("Parking slot released successfully.");
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        String rawValue = SC.nextLine().trim();
        try {
            return Integer.parseInt(rawValue);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Input must be a whole number.");
        }
    }
    private static String readLine(String prompt) {
        System.out.print(prompt); return SC.nextLine().trim();
    }
    private static LocalDate readDate(String prompt) {
        try { return LocalDate.parse(readLine(prompt)); }
        catch (DateTimeParseException ex) { throw new IllegalArgumentException("Date must use YYYY-MM-DD."); }
    }
}
