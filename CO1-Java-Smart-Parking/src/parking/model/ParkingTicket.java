package parking.model;

import java.time.LocalDateTime;

public class ParkingTicket {
    private static int nextTicketNumber = 1000;
    private final int ticketNumber;
    private final Vehicle vehicle;
    private final Customer customer;
    private final int floor;
    private final int slot;
    private final LocalDateTime entryTime;

    public ParkingTicket(Vehicle vehicle, Customer customer, int floor, int slot, LocalDateTime entryTime) {
        if (vehicle == null || customer == null || entryTime == null) throw new IllegalArgumentException("Ticket data missing.");
        this.ticketNumber = ++nextTicketNumber;
        this.vehicle = vehicle; this.customer = customer;
        this.floor = floor; this.slot = slot; this.entryTime = entryTime;
    }
    public int getTicketNumber() { return ticketNumber; }
    public Vehicle getVehicle() { return vehicle; }
    public Customer getCustomer() { return customer; }
    public int getFloor() { return floor; }
    public int getSlot() { return slot; }
    public LocalDateTime getEntryTime() { return entryTime; }
}
