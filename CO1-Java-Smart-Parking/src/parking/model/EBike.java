package parking.model;

public class EBike extends Vehicle {
    private static final double FLAT_FEE = 15.0;
    public EBike(String regNo) { super(regNo); }
    @Override public double ratePerHour() { return FLAT_FEE; }
    @Override public double calculateFee(long minutes) {
        if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative.");
        return minutes == 0 ? 0.0 : FLAT_FEE; // flat fee per parking visit
    }
}
