package parking.model;

public class ParkingSlot {
    public enum SlotType { STANDARD, EV_CHARGING }
    private final int floorNumber;
    private final int slotNumber;
    private final SlotType type;
    private Vehicle vehicle;

    public ParkingSlot(int floorNumber, int slotNumber, SlotType type) {
        if (floorNumber < 0 || slotNumber < 0 || type == null) throw new IllegalArgumentException("Invalid slot.");
        this.floorNumber = floorNumber; this.slotNumber = slotNumber; this.type = type;
    }
    public int getFloorNumber() { return floorNumber; }
    public int getSlotNumber() { return slotNumber; }
    public SlotType getType() { return type; }
    public boolean isFree() { return vehicle == null; }
    public Vehicle getVehicle() { return vehicle; }
    public boolean canFit(Vehicle v) {
        return isFree() && (!v.needsChargingSlot() || type == SlotType.EV_CHARGING);
    }
    public void park(Vehicle v) {
        if (v == null || !canFit(v)) throw new IllegalStateException("Slot is occupied or unsuitable.");
        vehicle = v;
    }
    public void release() {
        if (isFree()) throw new IllegalStateException("Slot is already free.");
        vehicle = null;
    }
}
