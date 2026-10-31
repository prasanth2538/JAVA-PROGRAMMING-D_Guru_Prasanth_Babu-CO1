package parking.service;

import parking.model.*;
import java.time.LocalDateTime;
import java.util.*;

public class ParkingFacility {
    private final ParkingSlot[][] floors;
    private final Map<String, ParkingTicket> activeTickets = new HashMap<>();

    // Each integer is the number of slots on that floor. The first slot on each
    // floor is an EV charging slot when the floor has at least one slot.
    public ParkingFacility(int[] slotsPerFloor) {
        if (slotsPerFloor == null || slotsPerFloor.length == 0) throw new IllegalArgumentException("At least one floor is required.");
        floors = new ParkingSlot[slotsPerFloor.length][];
        for (int f = 0; f < slotsPerFloor.length; f++) {
            if (slotsPerFloor[f] < 1) throw new IllegalArgumentException("Each floor needs at least one slot.");
            floors[f] = new ParkingSlot[slotsPerFloor[f]];
            for (int s = 0; s < slotsPerFloor[f]; s++) {
                ParkingSlot.SlotType type = (s == 0) ? ParkingSlot.SlotType.EV_CHARGING : ParkingSlot.SlotType.STANDARD;
                floors[f][s] = new ParkingSlot(f, s, type);
            }
        }
    }

    public ParkingTicket vehicleEntry(Vehicle vehicle, Customer customer, LocalDateTime entryTime) {
        Objects.requireNonNull(vehicle); Objects.requireNonNull(customer); Objects.requireNonNull(entryTime);
        String key = vehicle.getRegNo();
        if (activeTickets.containsKey(key)) throw new IllegalArgumentException("This vehicle is already parked.");
        for (ParkingSlot[] floor : floors) {
            for (ParkingSlot slot : floor) {
                if (slot.canFit(vehicle)) {
                    slot.park(vehicle);
                    ParkingTicket ticket = new ParkingTicket(vehicle, customer, slot.getFloorNumber(), slot.getSlotNumber(), entryTime);
                    activeTickets.put(key, ticket);
                    return ticket;
                }
            }
        }
        throw new IllegalStateException("No suitable parking slot is available.");
    }

    public double vehicleExit(String regNo, LocalDateTime exitTime, int chargingKWh) {
        ParkingTicket ticket = activeTickets.get(normalize(regNo));
        if (ticket == null) throw new IllegalArgumentException("No active ticket found for this vehicle.");
        if (exitTime == null || exitTime.isBefore(ticket.getEntryTime())) throw new IllegalArgumentException("Invalid exit time.");

        double parkingFee;
        Customer customer = ticket.getCustomer();
        if (customer.hasFreeParking()) {
            parkingFee = 0.0;
        } else if (customer instanceof Visitor visitor && visitor.hasValidOneDayPass(exitTime.toLocalDate())) {
            parkingFee = 0.0;
        } else {
            parkingFee = ticket.getVehicle().calculateFee(ticket.getEntryTime(), exitTime);
        }

        double chargingFee = 0.0;
        if (ticket.getVehicle() instanceof Chargeable chargeable) {
            chargingFee = chargeable.chargingFee(chargingKWh);
        } else if (chargingKWh != 0) {
            throw new IllegalArgumentException("Charging units can only be entered for an EV.");
        }

        floors[ticket.getFloor()][ticket.getSlot()].release();
        activeTickets.remove(normalize(regNo));
        return parkingFee + chargingFee;
    }

    public int getAvailableSlots() {
        int count = 0;
        for (ParkingSlot[] floor : floors) for (ParkingSlot slot : floor) if (slot.isFree()) count++;
        return count;
    }

    public int getTotalSlots() {
        int count = 0; for (ParkingSlot[] floor : floors) count += floor.length; return count;
    }

    public void displayStatus() {
        System.out.println("Total slots: " + getTotalSlots());
        System.out.println("Available slots: " + getAvailableSlots());
        System.out.println("Active vehicles: " + activeTickets.size());
        for (ParkingSlot[] floor : floors) {
            for (ParkingSlot slot : floor) {
                System.out.printf("Floor %d Slot %d [%s] : %s%n",
                    slot.getFloorNumber() + 1, slot.getSlotNumber() + 1, slot.getType(),
                    slot.isFree() ? "FREE" : slot.getVehicle().getRegNo());
            }
        }
    }

    public ParkingTicket findTicket(String regNo) { return activeTickets.get(normalize(regNo)); }
    private String normalize(String regNo) {
        if (regNo == null) throw new IllegalArgumentException("Registration number required.");
        return regNo.trim().toUpperCase();
    }
}
